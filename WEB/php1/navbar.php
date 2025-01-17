<?php
if (session_status() === PHP_SESSION_NONE) {
    session_start();
}
$current_user = $_SESSION["user"] ?? null;
?>
<nav class="bg-blue-600 text-white p-4">
    <div class="container mx-auto flex justify-between items-center">
        <a href="index.php" class="text-xl font-bold">iKarRental</a>
        <div class="space-x-4">
            <?php if ($current_user): ?>
                <span>Welcome, <?= htmlspecialchars($current_user["full_name"]) ?></span>
                
                <?php if (!empty($current_user["is_admin"]) && $current_user["is_admin"]): ?>
                    <a href="manage_cars.php">Manage Cars</a>
                    <a href="adminprofile.php">Profile</a>
                <?php else: ?>
                    <a href="profile.php">Profile</a>
                <?php endif; ?>
                
                <a href="logout.php">Logout</a>
            <?php else: ?>
                <a href="login.php">Login</a>
                <a href="register.php">Register</a>
            <?php endif; ?>
        </div>
    </div>
</nav>



