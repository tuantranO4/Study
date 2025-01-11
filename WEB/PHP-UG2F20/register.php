<?php
session_start();

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $users = json_decode(file_get_contents('userdata.json'), true) ?? [];

    $fullname = $_POST['fullname'] ?? '';
    $email = $_POST['email'] ?? '';
    $password = $_POST['password'] ?? '';
    $password_confirm = $_POST['password_confirm'] ?? '';

    $errors = [];

    // Validate input
    if (empty($fullname) || empty($email) || empty($password) || empty($password_confirm)) {
        $errors[] = 'All fields are required.';
    } elseif (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
        $errors[] = 'Invalid email format.';
    } elseif ($password !== $password_confirm) {
        $errors[] = 'Passwords do not match.';
    } elseif (strlen($password) < 6) {
        $errors[] = 'Password must be at least 6 characters long.';
    } else {
        foreach ($users as $user) {
            if ($user['email'] === $email) {
                $errors[] = 'Email is already registered.';
                break;
            }
        }
    }

    if (empty($errors)) {
        // Generate unique ID for user
        $userId = bin2hex(random_bytes(8));
        $users[$userId] = [
            'fullname' => $fullname,
            'email' => $email,
            'password' => password_hash($password, PASSWORD_DEFAULT),
            'isAdmin' => false, // Regular users are not admins
        ];

        // Save to userdata.json
        file_put_contents('userdata.json', json_encode($users, JSON_PRETTY_PRINT));
        header('Location: login.php');
        exit();
    }
}
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <h1>Register</h1>
    <form method="POST">
        <label>Full Name:</label>
        <input type="text" name="fullname" required>
        <label>Email:</label>
        <input type="email" name="email" required>
        <label>Password:</label>
        <input type="password" name="password" required>
        <label>Confirm Password:</label>
        <input type="password" name="password_confirm" required>
        <button type="submit">Register</button>
    </form>
    <?php if (!empty($errors)): ?>
        <ul style="color: red;">
            <?php foreach ($errors as $error): ?>
                <li><?= htmlspecialchars($error) ?></li>
            <?php endforeach; ?>
        </ul>
    <?php endif; ?>
</body>
</html>
