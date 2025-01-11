<?php
require_once "storage.php";
require_once "auth.php";

authorize(["user", "admin"]);

// Initialize the car storage and fetch the car details
$carStorage = new Storage(new JsonIO("storage/cars.json"));
$carId = $_GET['id'] ?? '';
$car = $carStorage->findById($carId);

if (!$car) {
    header("Location: index.php"); // Redirect if car is not found
    exit();
}
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><?= htmlspecialchars($car["brand"] . " " . $car["model"]) ?> - iKarRental</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
</head>
<body>
    <?php include "navbar.php"; ?>
    
    <div class="container mx-auto px-4 py-8">
        <div class="bg-white p-8 border rounded-lg shadow-lg">
            <h2 class="text-2xl font-bold"><?= htmlspecialchars($car["brand"] . " " . $car["model"]) ?></h2>
            <img src="<?= htmlspecialchars($car["image"]) ?>" alt="<?= htmlspecialchars($car["brand"] . " " . $car["model"]) ?>" class="w-full h-64 object-cover mt-4">
            <p class="text-lg mt-4"><strong>Year:</strong> <?= htmlspecialchars($car["year"]) ?></p>
            <p class="text-lg"><strong>Transmission:</strong> <?= htmlspecialchars($car["transmission"]) ?></p>
            <p class="text-lg"><strong>Passengers:</strong> <?= htmlspecialchars($car["passengers"]) ?></p>
            <p class="text-lg"><strong>Daily Price:</strong> <?= number_format($car["daily_price_huf"]) ?> HUF</p>
            <a href="booking.php?car_id=<?= $car["id"] ?>" class="mt-4 bg-blue-500 text-white px-4 py-2 rounded">Book This Car</a>
            <form action="book_car.php" method="post">
            <input type="hidden" name="car_id" value="<?= $car['id'] ?>">
            <label for="start_date">Start Date:</label>
            <input type="date" name="start_date" required>
            <label for="end_date">End Date:</label>
            <input type="date" name="end_date" required>
            <button type="submit">Book Car</button>
            </form>
        </div>
    </div>
</body>
</html>
