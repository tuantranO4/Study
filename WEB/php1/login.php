<?php
// login.php
require_once "storage.php";
require_once "auth.php";

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
    <title>Login - iKarRental</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
</head>
<body>
    <?php include "navbar.php"; ?>
    
    <div class="container mx-auto px-4 py-8">
        <div class="max-w-md mx-auto bg-white p-8 border rounded-lg shadow-lg">
            <h2 class="text-2xl font-bold mb-6">Login</h2>
            
            <?php if ($errors): ?>
            <div class="bg-red-100 border-red-400 text-red-700 px-4 py-3 rounded mb-4">
                <?php foreach ($errors as $error): ?>
                    <p><?= htmlspecialchars($error) ?></p>
                <?php endforeach; ?>
            </div>
            <?php endif; ?>

            <form method="POST" novalidate>
                <div class="mb-4">
                    <label class="block mb-2">Email Address</label>
                    <input type="email" name="email" required class="w-full px-3 py-2 border rounded">
                </div>
                <div class="mb-6">
                    <label class="block mb-2">Password</label>
                    <input type="password" name="password" required class="w-full px-3 py-2 border rounded">
                </div>
                <button type="submit" class="w-full bg-blue-500 text-white px-4 py-2 rounded">Login</button>
            </form>
            
            <p class="mt-4 text-center">
                Don't have an account? <a href="register.php" class="text-blue-500">Register</a>
            </p>
        </div>
    </div>
</body>
</html>