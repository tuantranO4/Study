<?php
require_once "storage.php";
require_once "auth.php";

$carStorage = new Storage(new JsonIO("storage/cars.json"));
$carId = $_GET['id'] ?? '';
$car = $carStorage->findById($carId);

if (!$car) {
    // Redirect if car is not found or ID is invalid
    header("Location: index.php");
    exit();
}
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><?= htmlspecialchars($car["brand"] . " " . $car["model"]) ?> - Details</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
</head>
<body>
    <?php include "navbar.php"; ?>
    
    <div class="container mx-auto px-4 py-8">
        <div class="bg-white p-8 border rounded-lg shadow-lg">
            <h2 class="text-2xl font-bold"><?= htmlspecialchars($car["brand"] . " " . $car["model"]) ?></h2>
            <img src="<?= htmlspecialchars($car["image"]) ?>" alt="<?= htmlspecialchars($car["brand"] . " " . $car["model"]) ?>" class="w-full max-h-64 object-cover mt-4">
            <p><strong>Year:</strong> <?= htmlspecialchars($car["year"]) ?></p>
            <p><strong>Transmission:</strong> <?= htmlspecialchars($car["transmission"]) ?></p>
            <p><strong>Passengers:</strong> <?= htmlspecialchars($car["passengers"]) ?></p>
            <p><strong>Price:</strong> <?= number_format($car["daily_price_huf"]) ?> HUF/day</p>
            <form action="book_car.php" method="post">
                <input type="hidden" name="car_id" value="<?= htmlspecialchars($car['id']) ?>">
                <label for="start_date">Start Date:</label>
                <input type="date" name="start_date" required>
                <label for="end_date">End Date:</label>
                <input type="date" name="end_date" required>
                <button type="submit" class="bg-blue-500 text-white px-4 py-2 rounded">Book Car</button>
            </form>
        </div>
    </div>
</body>
</html>
