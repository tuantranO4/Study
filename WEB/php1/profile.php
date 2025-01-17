<?php
require_once "storage.php";
require_once "auth.php";
require_once "auth.php";
if (session_status() === PHP_SESSION_NONE) {
    session_start();
}

authorize(["user", "admin"]);
if (!isset($_SESSION['user'])) {
    header("Location: login.php");
    exit;
}

$bookingStorage = new Storage(new JsonIO("storage/bookings.json"));
$carStorage = new Storage(new JsonIO("storage/cars.json"));

$userId = $_SESSION['user']['id'];
$bookings = $bookingStorage->findMany(function ($booking) use ($userId) {
    return $booking['user_id'] === $userId;
});

// Sort bookings recent first
usort($bookings, function($a, $b) {
    return strtotime($b['start_date']) - strtotime($a['start_date']);
});
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Profile - iKarRental</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
</head>
<body class="bg-gray-100">
    <?php include "navbar.php"; ?>
    
    <div class="container mx-auto px-4 py-8">
        <div class="bg-white rounded-lg shadow-lg p-6">
            <div class="flex justify-between items-center mb-6">
                <h1 class="text-2xl font-bold">My Reservations</h1>
                <a href="logout.php" 
                   class="bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600">
                    Log Out
                </a>
            </div>

            <?php if (empty($bookings)): ?>
                <div class="text-center py-8">
                    <p class="text-gray-600">You don't have any reservations yet.</p>
                    <a href="index.php" class="text-blue-500 hover:underline mt-2 inline-block">
                        Browse Available Cars
                    </a>
                </div>
            <?php else: ?>
                <div class="space-y-4">
                    <?php foreach ($bookings as $booking): ?>
                        <?php $car = $carStorage->findById($booking['car_id']-1); ?>
                        <div class="border rounded-lg p-4 flex items-start space-x-4">
                            <img src="<?= htmlspecialchars($car['image']) ?>" 
                                 alt="<?= htmlspecialchars($car['brand'] . ' ' . $car['model']) ?>"
                                 class="w-32 h-24 object-cover rounded">
                            
                            <div class="flex-grow">
                                <h3 class="font-bold text-lg">
                                    <?= htmlspecialchars($car['brand'] . ' ' . $car['model']) ?>
                                </h3>
                                <p class="text-gray-600">
                                    <?= htmlspecialchars($booking['start_date']) ?> to 
                                    <?= htmlspecialchars($booking['end_date']) ?>
                                </p>
                                <p class="mt-2">
                                    <span class="font-medium">Total Cost:</span> 
                                    <?= number_format($booking['total_cost']) ?> HUF
                                </p>
                            </div>

                            <?php
                                $start_date = new DateTime($booking['start_date']);
                                $current_date = new DateTime();
                                $is_future_booking = $start_date > $current_date;
                            ?>
                            
                            <?php if ($is_future_booking): ?>
                                <span class="bg-green-100 text-green-800 px-2 py-1 rounded text-sm">
                                    Upcoming
                                </span>
                            <?php else: ?>
                                <span class="bg-gray-100 text-gray-800 px-2 py-1 rounded text-sm">
                                    Completed
                                </span>
                            <?php endif; ?>
                        </div>
                    <?php endforeach; ?>
                </div>
            <?php endif; ?>
        </div>
    </div>
</body>
</html>