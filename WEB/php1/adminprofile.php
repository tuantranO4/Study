<?php
require_once "auth.php";
require_once "storage.php";

// only admin 
checkAdmin();

$bookingStorage = new Storage(new JsonIO("storage/bookings.json"));
$userStorage = new Storage(new JsonIO("storage/users.json"));
$carStorage = new Storage(new JsonIO("storage/cars.json"));

$bookings = $bookingStorage->findAll();
foreach ($bookings as &$booking) {
    $user = $userStorage->findById($booking['user_id']);
    $car = $carStorage->findById($booking['car_id']);
    $booking['user'] = $user;
    $booking['car'] = $car;
}
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Profile - iKarRental</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
</head>
<body>
    <?php include "navbar.php"; ?>
    
    <div class="container mx-auto px-4 py-8">
        <div class="bg-white p-8 border rounded-lg shadow-lg">
            <h2 class="text-2xl font-bold mb-6">Admin Dashboard</h2>
            
            <div class="mb-8">
                <h3 class="text-xl font-semibold mb-4">All Bookings</h3>
                <?php if (empty($bookings)): ?>
                    <p class="text-gray-600">No bookings in the system.</p>
                <?php else: ?>
                    <div class="space-y-4">
                        <?php foreach ($bookings as $booking): ?>
                        <div class="border p-4 rounded">
                            <div class="flex justify-between items-start">
                                <div>
                                    <h4 class="font-semibold">
                                        User: <?= htmlspecialchars($booking['user']['full_name']) ?>
                                    </h4>
                                    <p>
                                        Car: <?= htmlspecialchars($booking['car']['brand'] . ' ' . $booking['car']['model']) ?>
                                    </p>
                                    <p class="text-gray-600">
                                        Period: <?= htmlspecialchars($booking['start_date']) ?> to 
                                        <?= htmlspecialchars($booking['end_date']) ?>
                                    </p>
                                    <p class="mt-2">
                                        <span class="font-medium">Total Cost:</span> 
                                        <?= number_format($booking['total_cost']) ?> HUF
                                    </p>
                                </div>
                                <div>
                                    <img src="<?= htmlspecialchars($booking['car']['image']) ?>" 
                                         alt="Car Image"
                                         class="w-32 h-24 object-cover rounded">
                                </div>
                            </div>
                        </div>
                        <?php endforeach; ?>
                    </div>
                <?php endif; ?>
            </div>

            <div class="mt-8 border-t pt-8">
                <h3 class="text-xl font-semibold mb-4">Quick Actions</h3>
                <div class="space-x-4">
                    <a href="manage_cars.php" class="inline-block bg-blue-500 text-white px-4 py-2 rounded">
                        Manage Cars
                    </a>
                    <a href="logout.php" class="inline-block bg-red-500 text-white px-4 py-2 rounded">
                        Logout
                    </a>
                </div>
            </div>
        </div>
    </div>
</body>
</html>