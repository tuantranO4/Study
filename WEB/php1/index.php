<?php
require_once "storage.php";
require_once "auth.php";

$carStorage = new Storage(new JsonIO("storage/cars.json"));
$cars = $carStorage->findAll();

// Apply filters if they exist
if ($_GET) {
    $filters = [];
    if (!empty($_GET["transmission"])) {
        $filters["transmission"] = $_GET["transmission"];
    }
    if (!empty($_GET["passengers"])) {
        $cars = array_filter($cars, fn($car) => $car["passengers"] >= $_GET["passengers"]);
    }
    if (!empty($_GET["price_min"])) {
        $cars = array_filter($cars, fn($car) => $car["daily_price_huf"] >= $_GET["price_min"]);
    }
    if (!empty($_GET["price_max"])) {
        $cars = array_filter($cars, fn($car) => $car["daily_price_huf"] <= $_GET["price_max"]);
    }
    if (!empty($_GET["start_date"]) && !empty($_GET["end_date"])) {
        $bookingStorage = new Storage(new JsonIO("storage/bookings.json"));
        $startDate = $_GET["start_date"];
        $endDate = $_GET["end_date"];
        $cars = array_filter($cars, function($car) use ($bookingStorage, $startDate, $endDate) {
            $conflicting_bookings = $bookingStorage->findMany(function($booking) use ($car, $startDate, $endDate) {
                return $booking["car_id"] === $car["id"] &&
                       strtotime($startDate) <= strtotime($booking["end_date"]) &&
                       strtotime($endDate) >= strtotime($booking["start_date"]);
            });
            return empty($conflicting_bookings);
        });
    }
    if (!empty($filters)) {
        $cars = $carStorage->findAll($filters);
    }
}
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>iKarRental - Available Cars</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
</head>
<body>
    <?php include "navbar.php"; ?>
    
    <div class="container mx-auto px-4 py-8">
        <!-- Filters -->
            <form method="GET" class="mb-8 p-4 bg-gray-100 rounded">
            <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
            <div>
                <label>Transmission</label>
                <select name="transmission" class="w-full">
                    <option value="">Any</option>
                    <option value="Manual">Manual</option>
                    <option value="Automatic">Automatic</option>
                </select>
            </div>
            <div>
                <label>Min Passengers</label>
                <input type="number" name="passengers" min="1" class="w-full">
            </div>
            <div>
                <label>Year of Manufacture</label>
                <input type="number" name="year_min" placeholder="From Year" class="w-full">
            </div>
            <div>
                <label>Price Range (HUF)</label>
                <div class="flex gap-2">
                    <input type="number" name="price_min" placeholder="Min" class="w-full">
                    <input type="number" name="price_max" placeholder="Max" class="w-full">
                </div>
            </div>
            </div>
            <button type="submit" class="mt-4 bg-blue-500 text-white px-4 py-2 rounded">Filter</button>
            </form>

        <!-- Car listing -->
        <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
            <?php foreach ($cars as $car): ?>
            <div class="border rounded-lg overflow-hidden shadow-lg">
                <img src="<?= htmlspecialchars($car["image"]) ?>" alt="<?= htmlspecialchars($car["brand"] . " " . $car["model"]) ?>" class="w-full h-48 object-cover">
                <div class="p-4">
                    <h3 class="text-xl font-bold"><?= htmlspecialchars($car["brand"] . " " . $car["model"]) ?></h3>
                    <p class="text-gray-600">Year: <?= htmlspecialchars($car["year"]) ?></p>
                    <p class="text-gray-600">Transmission: <?= htmlspecialchars($car["transmission"]) ?></p>
                    <p class="text-gray-600">Passengers: <?= htmlspecialchars($car["passengers"]) ?></p>
                    <p class="text-lg font-bold mt-2"><?= number_format($car["daily_price_huf"]) ?> HUF/day</p>
                    <a href="car.php?id=<?= $car['id'] ?>"><img src="<?= htmlspecialchars($car['image']) ?>" alt="View Car"></a>
                </div>
            </div>
            <?php endforeach; ?>
        </div>
    </div>
</body>
</html>
