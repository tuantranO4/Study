<?php
if (session_status() === PHP_SESSION_NONE) {
    session_start();
}
require_once "storage.php";
require_once "auth.php";

$carStorage = new Storage(new JsonIO("storage/cars.json"));
$bookingStorage = new Storage(new JsonIO("storage/bookings.json"));
$carId = isset($_GET['id']) ? (int)$_GET['id'] : 0;

error_log("Received car ID: " . $carId);

$car = $carStorage->findById($carId);

if (!$car) {
    error_log("Car not found for ID: " . $carId);
    header("Location: index.php");
    exit();
}

$isGuest = !isset($_SESSION['user']);

$existingBookings = $bookingStorage->findMany(function($booking) use ($carId) {
    return $booking['car_id'] === $carId;
});

?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Car Details - <?= htmlspecialchars($car['brand'] . ' ' . $car['model']) ?></title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
</head>
<body class="bg-gray-100">
    <?php include "navbar.php"; ?>

    <div class="container mx-auto px-4 py-8">
        <div class="bg-white rounded-lg shadow-lg overflow-hidden">
            <div class="md:flex">
                <div class="md:flex-shrink-0">
                    <img class="h-96 w-full object-cover md:w-96" 
                         src="<?= htmlspecialchars($car['image']) ?>" 
                         alt="<?= htmlspecialchars($car['brand'] . ' ' . $car['model']) ?>">
                </div>
                <div class="p-8">
                    <h1 class="text-3xl font-bold mb-4">
                        <?= htmlspecialchars($car['brand'] . ' ' . $car['model']) ?>
                    </h1>

                    <div class="grid grid-cols-2 gap-4 mb-6">
                        <div>
                            <h3 class="font-semibold">Year</h3>
                            <p><?= htmlspecialchars($car['year']) ?></p>
                        </div>
                        <div>
                            <h3 class="font-semibold">Transmission</h3>
                            <p><?= htmlspecialchars($car['transmission']) ?></p>
                        </div>
                        <div>
                            <h3 class="font-semibold">Fuel Type</h3>
                            <p><?= htmlspecialchars($car['fuel_type']) ?></p>
                        </div>
                        <div>
                            <h3 class="font-semibold">Passengers</h3>
                            <p><?= htmlspecialchars($car['passengers']) ?></p>
                        </div>
                    </div>

                    <div class="mb-6">
                        <h3 class="font-semibold text-xl mb-2">Daily Price Rate</h3>
                        <p class="text-2xl font-bold text-blue-600">
                            <?= number_format($car['daily_price_huf']) ?> HUF
                        </p>
                    </div>

                    <?php if ($isGuest): ?>
                        <div class="bg-gray-100 p-4 rounded-lg">
                            <p class="text-gray-700">Please <a href="login.php" class="text-blue-600 hover:underline">log in</a> 
                               or <a href="register.php" class="text-blue-600 hover:underline">register</a> to book this car.</p>
                        </div>
                    <?php else: ?>
                        <script>
                        document.addEventListener('DOMContentLoaded', function() {
                            const form = document.querySelector('form');
                            const startDate = document.querySelector('input[name="start_date"]');
                            const endDate = document.querySelector('input[name="end_date"]');

                            form.addEventListener('submit', function(e) {
                                const start = new Date(startDate.value);
                                const end = new Date(endDate.value);
                                const today = new Date();
                                today.setHours(0,0,0,0);

                                if (start < today) {
                                    e.preventDefault();
                                    alert('Start date must be today or later');
                                    return;
                                }

                                if ((end - start) / (1000 * 60 * 60 * 24) < 1) {
                                    e.preventDefault();
                                    alert('Booking must be for at least 1 day');
                                    return;
                                }
                            });
                        });
                        </script>
                        <form action="book_car.php" method="post" class="space-y-4">
                            <input type="hidden" name="car_id" value="<?= htmlspecialchars($car['id']) ?>">

                            <div>
                                <label class="block text-sm font-medium text-gray-700">Start Date</label>
                                <input type="date" name="start_date" 
                                       min="<?= date('Y-m-d') ?>" 
                                       required 
                                       class="mt-1 block w-full rounded-md border-gray-300 shadow-sm">
                            </div>

                            <div>
                                <label class="block text-sm font-medium text-gray-700">End Date</label>
                                <input type="date" name="end_date" 
                                       min="<?= date('Y-m-d') ?>" 
                                       required 
                                       class="mt-1 block w-full rounded-md border-gray-300 shadow-sm">
                            </div>

                            <button type="submit" 
                                    class="w-full bg-blue-600 text-white py-2 px-4 rounded-md hover:bg-blue-700">
                                Book Now
                            </button>
                        </form>

                        <?php if ($existingBookings): ?>
                            <div class="mt-6">
                                <h3 class="font-semibold mb-2">Current Bookings</h3>
                                <div class="text-sm text-gray-600">
                                    <?php foreach ($existingBookings as $booking): ?>
                                        <p>• <?= htmlspecialchars($booking['start_date']) ?> to 
                                           <?= htmlspecialchars($booking['end_date']) ?></p>
                                    <?php endforeach; ?>
                                </div>
                            </div>
                        <?php endif; ?>
                    <?php endif; ?>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
