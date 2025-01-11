<?php
session_start();

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $email = $_POST['email'] ?? '';
    $password = $_POST['password'] ?? '';

    // Load users from userdata.json
    $users = json_decode(file_get_contents('userdata.json'), true) ?? [];

    // Default admin credentials
    if ($email === 'admin@ikarrental.hu' && $password === 'admin') {
        $_SESSION['user'] = [
            'fullname' => 'Administrator',
            'email' => $email,
            'isAdmin' => true,
        ];
        header('Location: profile.php');
        exit();
    }

    // Check user credentials
    foreach ($users as $userId => $user) {
        if ($user['email'] === $email && password_verify($password, $user['password'])) {
            $_SESSION['user'] = [
                'id' => $userId,
                'fullname' => $user['fullname'],
                'email' => $user['email'],
                'isAdmin' => $user['isAdmin'],
            ];
            header('Location: profile.php');
            exit();
        }
    }

    $error = 'Invalid email or password.';
}
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <h1>Login</h1>
    <form method="POST">
        <label>Email Address:</label>
        <input type="email" name="email" required>
        <label>Password:</label>
        <input type="password" name="password" required>
        <button type="submit">Login</button>
    </form>
    <?php if (isset($error)): ?>
        <p style="color: red;"> <?= htmlspecialchars($error) ?> </p>
    <?php endif; ?>
</body>
</html>
