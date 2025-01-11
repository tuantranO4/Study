<?php
require_once "storage.php";
require_once "auth.php";

authorize(["user", "admin"]);

$userStorage = new Storage(new JsonIO("storage/users.json"));
$bookingStorage = new Storage(new JsonIO("storage/bookings.json"));
$carStorage = new Storage(new JsonIO("storage/cars.json"));

$user = $userStorage->findById($_SESSION["user"]["id"]);
$bookings = $bookingStorage->findAll(["user_id" => $_SESSION["user"]["id"]]);

// For each booking, attach car details
foreach ($bookings as &$booking) {
    $car = $carStorage->findById($booking["car_id"]);
    $booking["car"] = $car;
}
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Profile - iKarRental</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
</head>
<body>
    <?php include "navbar.php"; ?>
    
    <div class="container mx-auto px-4 py-8">
        <div class="bg-white p-8 border rounded-lg shadow-lg">
            <h2 class="text-2xl font-bold mb-6">Profile</h2>
            
            <div class="mb-8">
                <h3 class="text-xl font-semibold mb-4">User Information</h3>
                <p><strong>Name:</strong> <?= htmlspecialchars($user["full_name"]) ?></p>
                <p><strong>Email:</strong> <?= htmlspecialchars($user["email"]) ?></p>
                <p><strong>Role:</strong> <?= $user["is_admin"] ? "Administrator" : "User" ?></p>
            </div>

            <div>
                <h3 class="text-xl font-semibold mb-4">Your Bookings</h3>
                <?php if (empty($bookings)): ?>
                    <p class="text-gray-600">No bookings yet.</p>
                <?php else: ?>
                    <div class="space-y-4">
                        <?php foreach ($bookings as $booking): ?>
                        <div class="border p-4 rounded">?>
                            <div class="flex justify-between items-start">
                                <div>
                                    <h4 class="font-semibold">
                                        <?= htmlspecialchars($booking["car"]["brand"] . " " . $booking["car"]["model"]) ?>
                                    </h4>
                                    <p class="text-gray-600">
                                        Booking period: <?= htmlspecialchars($booking["start_date"]) ?> to 
                                        <?= htmlspecialchars($booking["end_date"]) ?>
                                    </p>
                                    <?php
                                        $start = new DateTime($booking["start_date"]);
                                        $end = new DateTime($booking["end_date"]);
                                        $days = $end->diff($start)->days + 1;
                                        $total = $days * $booking["car"]["daily_price_huf"];
                                    ?>
                                    <p class="mt-2">
                                        <span class="font-medium">Duration:</span> <?= $days ?> days<br>
                                        <span class="font-medium">Daily Rate:</span> <?= number_format($booking["car"]["daily_price_huf"]) ?> HUF<br>
                                        <span class="font-medium">Total Cost:</span> <?= number_format($total) ?> HUF
                                    </p>
                                </div>
                                <div>
                                    <img src="<?= htmlspecialchars($booking["car"]["image"]) ?>" 
                                         alt="<?= htmlspecialchars($booking["car"]["brand"] . " " . $booking["car"]["model"]) ?>"
                                         class="w-32 h-24 object-cover rounded">
                                </div>
                            </div>
                            <?php if (strtotime($booking["end_date"]) >= strtotime('today')): ?>
                                <div class="mt-4 p-2 bg-green-100 text-green-700 rounded">
                                    Active Booking
                                </div>
                            <?php else: ?>
                                <div class="mt-4 p-2 bg-gray-100 text-gray-700 rounded">
                                    Past Booking
                                </div>
                            <?php endif; ?>
                        </div>
                        <?php endforeach; ?>
                    </div>
                <?php endif; ?>
            </div>

            <?php if ($user["is_admin"]): ?>
            <div class="mt-8">
                <h3 class="text-xl font-semibold mb-4">Administrator Actions</h3>
                <div class="space-x-4">
                    <a href="admin/cars.php" class="inline-block bg-blue-500 text-white px-4 py-2 rounded">
                        Manage Cars
                    </a>
                    <a href="admin/bookings.php" class="inline-block bg-blue-500 text-white px-4 py-2 rounded">
                        View All Bookings
                    </a>
                </div>
            </div>
            <?php endif; ?>

            <div class="mt-8 pt-8 border-t">
                <a href="logout.php" class="inline-block bg-red-500 text-white px-4 py-2 rounded">
                    Logout
                </a>
            </div>
        </div>
    </div>
</body>
</html>