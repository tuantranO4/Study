<?php
session_start(); // Starts or resumes a session, allowing data to persist across page requests using a unique session ID.
$reg = json_decode(file_get_contents("data.json"), true); //read data.json file and decode it to an associative array
$loginerror = ''; //storing login error message
$current_user = [];//storing current user data

if (isset($_SESSION['loginerror'])) {
    switch ($_SESSION['loginerror']) {
        case 1:
            $loginerror = 'The username is incorrect';
            break;
        case 2:
            $loginerror = 'Wrong Password';
            break;
    }
}

if (isset($_SESSION['userid'])) {
    $current_user = $reg[$_SESSION['userid']];
}// If a user is logged in (indicated by $_SESSION['userid'] - userid exist in session), their details are retrieved from $reg using their ID as key.
var_dump($_SESSION); //DEBUG
?>

<!DOCTYPE html>
<html lang="hu">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Main Page</title>
    <link rel="stylesheet" href="style.css"> <!-- BASIC HTML SETTING -->
</head>

<body>
    <?php if (isset($_SESSION['userid'])): ?> <!-- If a user is logged in -->
        <h1>Hello, <?= $current_user["fullname"] ?>! (<a href="logout.php">Logout </a> )</h1> <!-- Display the user's name and a logout link -->
        <img src="./img/<?= $current_user["img"] ?>" alt=""> <!-- Display the user's image -->

        <ul> <!-- Display the user's details since it was populate earlier (LINE 18) -->
            <li><a href="mailto:<?= $current_user["email"] ?>"><?= $current_user["email"] ?></a></li> 
            <!-- href=mailto: create a link that opens the user's default email client  -->

            <li><?= $current_user["age"] ?></li>
            <li><?= $current_user["gender"] == "m" ? "male" : "female" ?></li>
            <li><?= $current_user["notes"] ?></li>
        </ul>

        <?php
        $users = json_decode(file_get_contents("users.json"), true); 
        ?>


        <?php if ($users[$_SESSION['userid']]['isAdmin']): ?> <!-- If the logged-in user is an admin: Can see all registered patients FUNCTION FOR ADMIN-->

            <h1>Registered patient (<a href="reg.php">+ Add new </a> )</h1> <!-- Display the header and a link to add a new patient (reg.php) -->

            <label for="filter">Search:</label><input type="text" name="filter" id="filter"><button id='refresh'>Refresh</button> <!--querySelector: refresh (button js)-->
            <table>
                <?php foreach ($reg as $value): ?>
                    <tr>
                        <td><a href="show.php?id=<?= $value["id"] ?>"><?= $value["fullname"] ?></a></td> <!-- Display the patient's name (show.php) -->
                        <td><a href="mailto:<?= $value["email"] ?>"><?= $value["email"] ?></a></td>
                        <td><a href="delete.php?id=<?= $value["id"] ?>">DELETE</a></td> <!-- Delete link (delete.php)-->
                        <td><a href="reg.php?id=<?= $value["id"] ?>">MODIFY</a></td> <!-- Modify link (reg.php)-->
                    </tr>
                <?php endforeach; ?>    
            </table>
            <script src="refreshTable.js"></script>
        <?php endif; ?>


    <?php else: ?> <!-- If no user is logged in -->


        <h1>Login</h1>
        <form action="login.php" method="post"> <!-- Login form (login.php)-->
            <label for="username">Username: </label><input type="text" name="username" id="username"><br>
            <label for="password">Password: </label><input type="password" name="password" id="password"><br>
            <button type="submit">Login</button>
        </form>

        <span style='color:red'><?= $loginerror ?? '' ?></span>
    <?php endif; ?>


</body>

</html>