<?php
require_once "storage.php";
require_once "auth.php";

if ($_SERVER['REQUEST_METHOD'] === 'POST' && !empty($_POST['email']) && !empty($_POST['password'])) {
    if (verifyLogin($_POST['email'], $_POST['password'])) {
        $redirect = $_SESSION['redirect_after_login'] ?? 'index.php';
        unset($_SESSION['redirect_after_login']);
        header("Location: " . $redirect);
        exit();
    } else {
        $error = "Login failed. Invalid email or password.";
    }
}
if (is_logged_in()) {
    redirect("index.php");
}

$errors = [];

if ($_POST) {
    $email = $_POST["email"] ?? "";
    $password = $_POST["password"] ?? "";
    
    $userStorage = new Storage(new JsonIO("storage/users.json"));
    $user = $userStorage->findOne(["email" => $email]);
    
    if ($user && password_verify($password, $user["password"])) {
        login($user);
        redirect("index.php");
    } else {
        $errors[] = "Invalid email or password";
    }
}
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; }
        form { max-width: 300px; margin: auto; background: white; padding: 20px; box-shadow: 2px 2px 5px rgba(0,0,0,0.1); }
        input[type="email"], input[type="password"] { width: 100%; padding: 8px; margin: 10px 0; }
        button { width: 100%; padding: 10px; background-color: #007BFF; color: white; border: none; }
        button:hover { background-color: #0056b3; }
        .footer { text-align: center; margin-top: 20px; }
    </style>
</head>
<body>
    <?php if (isset($error)): ?>
        <p style="color: red; text-align: center;"><?= htmlspecialchars($error) ?></p>
    <?php endif; ?>
    <form action="login.php" method="post">
        <div>
            <label for="email">Email Address</label>
            <input type="email" id="email" name="email" required>
        </div>
        <div>
            <label for="password">Password</label>
            <input type="password" id="password" name="password" required>
        </div>
        <button type="submit">Login</button>
    </form>
    <div class="footer">
        Don't have an account? <a href="register.php">Register</a>
    </div>
</body>
</html>
