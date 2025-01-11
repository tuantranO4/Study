<?php
$current_user = $_SESSION["user"] ?? null;
?>
<nav class="bg-blue-600 text-white p-4">
    <div class="container mx-auto flex justify-between items-center">
        <a href="index.php" class="text-xl font-bold">iKarRental</a>
        <div class="space-x-4">
            <?php if ($current_user): ?>
                <a href="profile.php"><?= htmlspecialchars($current_user["full_name"]) ?></a>

                <a href="logout.php">Logout</a>
            <?php else: ?>
                <a href="login.php">Login</a>
                <a href="register.php">Register</a>
            <?php endif; ?>
        </div>
    </div>
</nav>