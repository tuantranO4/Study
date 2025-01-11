<?php
require_once "auth.php";
checkAdmin();

require_once "storage.php";
$bookingStorage = new Storage(new JsonIO("storage/bookings.json"));
$carStorage = new Storage(new JsonIO("storage/cars.json"));
$bookings = $bookingStorage->findAll();
$cars = $carStorage->findAll();
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
</head>
<body>
    <h1>Admin Dashboard</h1>
    <h2>All Bookings</h2>
    <?php foreach ($bookings as $booking): ?>
        <p><?= htmlspecialchars($booking['details']) ?> - <a href="editbooking.php?id=<?= $booking['id'] ?>">Edit</a></p>
    <?php endforeach; ?>

    <h2>Manage Cars</h2>
    <a href="addcar.php">Add New Car</a>
    <?php foreach ($cars as $car): ?>
        <div>
            <p><?= htmlspecialchars($car['brand']) ?> <?= htmlspecialchars($car['model']) ?></p>
            <a href="editcar.php?id=<?= $car['id'] ?>">Edit</a>
            <a href="deletecar.php?id=<?= $car['id'] ?>" onclick="return confirm('Are you sure?');">Delete</a>
        </div>
    <?php endforeach; ?>
</body>
</html>
