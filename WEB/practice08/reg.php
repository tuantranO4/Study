<?php
session_start();
$users = json_decode(file_get_contents("users.json"), true);

$regdate = $_POST['regdate'] ?? date('Y-m-d');
$fullname = $_POST['fullname'] ?? ''; 
$img = $_POST['img'] ?? 'default.svg';
$email = $_POST['email'] ?? '';
$id = $_POST['id'] ?? '';
$age = $_POST['age'] ?? '';
$gender = $_POST['gender'] ?? '';
$accept = $_POST['accept'] ?? false;
$accept = filter_var($accept, FILTER_VALIDATE_BOOLEAN);
$needProfile = $_POST['needProfile'] ?? false;
$needProfile = filter_var($needProfile, FILTER_VALIDATE_BOOLEAN); //Ensures that $needProfile is explicitly converted to a boolean value
$notes = $_POST['notes'] ?? '';
$errors = [];
$current_user = []; //Reads users.json to init user account data with null coallescing operator (?? '')

if (isset($_SESSION['userid']) && $users[$_SESSION['userid']]['isAdmin']) { //If the user is logged in and is an admin

    if (isset($_GET["id"])) { //If the ID is set in the URL
        $reg = json_decode(file_get_contents("data.json"), true);
        if (isset($reg[$_GET["id"]])) {
            $current_user = $reg[$_GET["id"]]; //If the ID exists in the data.json, the user data is stored in $current_user []
            unset($_GET["id"]);
        }
    }

    if (count($_POST) > 0) { //If the form is submitted
        $errors = [];
        $current_user = [];

        if (trim($fullname) === '') //BUNCH OF IF ELSE TO VALIDATE FORM DATA BY POPULATING $errors ARRAY WITH ERROR MESSAGES
            $errors['fullname'] = 'Name field is required!'; 
        else if (count(explode(' ', trim($fullname))) < 2)
            $errors['fullname'] = 'The name should contain at least two words!';

        if (!filter_var($email, FILTER_VALIDATE_EMAIL))
            $errors['email'] = 'The e-mail address is not valid';

        if (strlen($id) != 9)
            $errors['id'] = 'The ID must be 9 digit long!';
        else {
            if (count(array_filter(str_split($id), fn($char) => $char >= '0' && $char <= '9')) != 9)
                $errors['id'] = 'The ID must contain only numbers!';
        }

        if (filter_var($age, FILTER_VALIDATE_INT) === false) {
            $errors['age'] = 'Age must be an integer';
        } else {
            $age = intval($age);
            if ($age < 1) $errors['age'] = 'Age must be positive';
        }

        if (trim($gender) === '') {
            $errors['gender'] = 'Gender field is required';
        } else if ($gender !== 'm' && $gender !== 'f') {
            $errors['gender'] = 'The gender could be only Male or Female';
        }

        if (!$accept)
            $errors['accept'] = 'Please Accept the EULA'; 

        $errors = array_map(fn($e) => "<span style='color: red'> $e </span>", $errors); //Formats validation errors with red text for display in the form

        if (count($errors) == 0) { //if there are no errors
            $reg = json_decode(file_get_contents("data.json"), true);  //Reads the data.json file and decodes the JSON to a PHP array
            $reg[$id] = [
                'fullname' => $fullname,
                'id' => $id,
                'email' => $email,
                'img' => $img,
                'age' => $age,
                'gender' => $gender,
                'regdate' => $regdate,
                'notes' => $notes
            ]; //Populates the $reg array with the form data
            file_put_contents('data.json', json_encode($reg, JSON_PRETTY_PRINT)); //Writes the updated $reg array to the data.json file

            if ($needProfile) { //If the 'Generate User Profile' checkbox is checked (in the HTML form below)
                $users = json_decode(file_get_contents("users.json"), true);
                $users[$id] = [
                    'id' => $id, //Redirects non-admin users to the home page
                    'username' => explode("@", $email)[0], //Extracts the part of the email before @ symbol to create the username "john.doe@example.com" -> "john.doe"
                    'password' => password_hash('user', PASSWORD_DEFAULT), //password_hash creates a cryptographically secure hashed password
                    'isAdmin' => false,
                ];
                file_put_contents('users.json', json_encode($users, JSON_PRETTY_PRINT));
            } //If the 'Generate User Profile' checkbox is checked, a new user profile is created in the users.json file
        }
    }
} else header('location: index.php'); //Redirects non-admin users to the home page
?>

<!DOCTYPE html>
<html lang="hu">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><?= isset($_GET["id"]) ? "Modification" : "Registration" ?></title> <!--If id exists: Title is "Modification." Otherwise: "Registration."-->

    <link rel="stylesheet" href="style.css">
</head>

<body>
    <?php if (count($_POST) > 0 && count($errors) == 0): ?>
        <span style="color: green;">Successfully saved!</span><br> <!--Displays a success message with stylesheet if the form data is saved successfully-->
    <?php endif; ?>

    <form action="reg.php" method="post"> <!--Form to register or modify user data, with dynamic value, error msg if fail -->
        Full name: <input type="text" name="fullname" value="<?= $current_user["fullname"] ?? $fullname ?>"> <?= $errors['fullname'] ?? '' ?> <br>
        E-mail: <input type="text" name="email" value="<?= $current_user["email"] ??  $email ?>"> <?= $errors['email'] ?? '' ?><br>
        Profile picture: <input type="text" name="img" value="<?= $profile ?? 'default.svg' ?>" accept="image/svg"><br>
        Healthcare ID: <input type="text" name="id" value="<?= $current_user["id"] ?? $id ?>"> <?= $errors['id'] ?? '' ?><br>
        Age: <input type="text" name="age" value="<?= $current_user["age"] ?? $age ?>"> <?= $errors['id'] ?? '' ?><br>
        Gender:
        <input type="radio" name="gender" value="m" <?= $gender == "m" ? 'checked' : '' ?>>Male
        <input type="radio" name="gender" value="f" <?= $gender == "f" ? 'checked' : '' ?>>Female
        <?= $errors['gender'] ?? '' ?><br>
        Registration date: <input type="date" name="regdate" value=" <?= $current_user["regdate"] ?? $regdate ?>"><br>
        Note: <br><textarea name="notes"><?= $current_user["notes"] ?? $notes ?></textarea><br>
        <input type="checkbox" name="accept" <?= $accept ? 'checked' : '' ?>> Accept EULA: <?= $errors['accept'] ?? '' ?> <br>
        <input type="checkbox" name="needProfile"> Generate User Profile<br> <!-- Checkbox to generate a user profile $needProfile -->
        <button type="submit">Save</button>
    </form>

    <a href="index.php">Back to Home</a>
</body>

</html>