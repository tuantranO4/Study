<?php
require_once "storage.php";
require_once "auth.php";

authorize(["user", "admin"]);

$bookingStorage = new Storage(new JsonIO("storage/bookings.json"));
$carStorage = new Storage(new JsonIO("storage/cars.json"));

$booking_id = $_GET['booking_id'] ?? '';
$booking = $bookingStorage->findById($booking_id);

if (!$booking || $booking['user_id'] !== $_SESSION['user']['id']) {
    header("Location: index.php");
    exit();
}

$car = $carStorage->findById($booking['car_id']-1);
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Booking Confirmation - iKarRental</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
</head>
<body class="bg-gray-100">
    <?php include "navbar.php"; ?>
    
    <div class="container mx-auto px-4 py-8">
        <div class="max-w-2xl mx-auto bg-white p-8 rounded-lg shadow-lg">
            <div class="text-center mb-8">
                <div class="mb-4">
                    <svg class="w-16 h-16 text-green-500 mx-auto" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"></path>
                    </svg>
                </div>
                <h1 class="text-3xl font-bold text-gray-900">Booking Confirmed!</h1>
                <p class="text-gray-600 mt-2">Your reservation has been successfully processed.</p>
            </div>

            <div class="border-t border-b border-gray-200 py-4 my-4">
                <div class="flex justify-between items-start">
                    <div>
                        <h2 class="text-xl font-bold">
                            <?= htmlspecialchars($car['brand'] . ' ' . $car['model']) ?>
                        </h2>
                        <p class="text-gray-600">
                            <?= htmlspecialchars($car['year']) ?> • 
                            <?= htmlspecialchars($car['transmission']) ?> • 
                            <?= htmlspecialchars($car['fuel_type']) ?>
                        </p>
                    </div>
                    <div>
                        <img src="<?= htmlspecialchars($car['image']) ?>" 
                             alt="<?= htmlspecialchars($car['brand'] . ' ' . $car['model']) ?>" 
                             class="w-32 h-24 object-cover rounded">
                    </div>
                </div>
            </div>

            <div class="space-y-4">
                <div>
                    <h3 class="font-semibold">Booking Details</h3>
                    <p><strong>Start Date:</strong> <?= htmlspecialchars($booking['start_date']) ?></p>
                    <p><strong>End Date:</strong> <?= htmlspecialchars($booking['end_date']) ?></p>
                    <?php
                        $start = new DateTime($booking['start_date']);
                        $end = new DateTime($booking['end_date']);
                        $days = $end->diff($start)->days + 1;
                    ?>
                    <p><strong>Duration:</strong> <?= $days ?> days</p>
                </div>

                <div class="bg-gray-50 p-4 rounded">
                    <h3 class="font-semibold">Payment Summary</h3>
                    <div class="flex justify-between mt-2">
                        <span>Daily Rate:</span>
                        <span><?= number_format($car['daily_price_huf']) ?> HUF</span>
                    </div>
                    <div class="flex justify-between mt-1">
                        <span>Number of Days:</span>
                        <span><?= $days-1 ?></span>
                    </div>
                    <div class="flex justify-between mt-2 pt-2 border-t border-gray-200 font-bold">
                        <span>Total Amount:</span>
                        <span><?= number_format($car['daily_price_huf'] * ($days-1)) ?> HUF</span>
                    </div>
                </div>
            </div>

            <div class="mt-8 text-center">
                <a href="profile.php" class="inline-block bg-blue-500 text-white px-6 py-2 rounded hover:bg-blue-600">
                    View My Bookings
                </a>
                <a href="index.php" class="inline-block ml-4 text-blue-500 hover:underline">
                    Return to Home
                </a>
            </div>
        </div>
    </div>
</body>
</html>