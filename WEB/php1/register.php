<?php
// register.php
require_once "storage.php";
require_once "auth.php";

if (is_logged_in()) {
    redirect("index.php");
}

$errors = [];

if ($_POST) {
    $full_name = $_POST["full_name"] ?? "";
    $email = $_POST["email"] ?? "";
    $password = $_POST["password"] ?? "";
    
    // Validation
    if (strlen($full_name) < 5) {
        $errors[] = "Full name must be at least 5 characters long";
    }
    if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
        $errors[] = "Invalid email format";
    }
    if (strlen($password) < 6) {
        $errors[] = "Password must be at least 6 characters long";
    }
    
    if (empty($errors)) {
        $userStorage = new Storage(new JsonIO("storage/users.json"));
        
        // Check email exist
        if ($userStorage->findOne(["email" => $email])) {
            $errors[] = "Email already registered";
        } else {
            // Create user
            $user = [
                "full_name" => $full_name,
                "email" => $email,
                "password" => password_hash($password, PASSWORD_DEFAULT),
                "is_admin" => false
            ];
            
            $userStorage->add($user);
            login($user);
            redirect("index.php");
        }
    }
}
?>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - iKarRental</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
</head>
<body>
    <?php include "navbar.php"; ?>
    
    <div class="container mx-auto px-4 py-8">
        <div class="max-w-md mx-auto bg-white p-8 border rounded-lg shadow-lg">
            <h2 class="text-2xl font-bold mb-6">Register</h2>
            
            <?php if ($errors): ?>
            <div class="bg-red-100 border-red-400 text-red-700 px-4 py-3 rounded mb-4">
                <?php foreach ($errors as $error): ?>
                    <p><?= htmlspecialchars($error) ?></p>
                <?php endforeach; ?>
            </div>
            <?php endif; ?>

            <form method="POST" novalidate>
                <div class="mb-4">
                    <label class="block mb-2">Full Name</label>
                    <input type="text" name="full_name" required class="w-full px-3 py-2 border rounded">
                </div>
                <div class="mb-4">
                    <label class="block mb-2">Email Address</label>
                    <input type="email" name="email" required class="w-full px-3 py-2 border rounded">
                </div>
                <div class="mb-6">
                    <label class="block mb-2">Password</label>
                    <input type="password" name="password" required class="w-full px-3 py-2 border rounded">
                </div>
                <button type="submit" class="w-full bg-blue-500 text-white px-4 py-2 rounded">Register</button>
            </form>
            
            <p class="mt-4 text-center">
                Already have an account? <a href="login.php" class="text-blue-500">Login</a>
            </p>
        </div>
    </div>
</body>
</html>
