<?php
session_start();

if (!isset($_SESSION['user'])) {
    header('Location: login.php');
    exit();
}

$user = $_SESSION['user'];
$isAdmin = $user['isAdmin'];

// Load bookings and cars
$bookings = json_decode(file_get_contents('bookings.json'), true) ?? [];
$cars = json_decode(file_get_contents('cars.json'), true) ?? [];

function renderAdminPage($bookings, $cars) {
    ?>
    <h2>All Bookings</h2>
    <table>
        <tr>
            <th>Car</th>
            <th>User</th>
            <th>Start Date</th>
            <th>End Date</th>
        </tr>
        <?php foreach ($bookings as $booking): ?>
            <tr>
                <td><?= htmlspecialchars($booking['car_id']) ?></td>
                <td><?= htmlspecialchars($booking['user_id']) ?></td>
                <td><?= htmlspecialchars($booking['start_date']) ?></td>
                <td><?= htmlspecialchars($booking['end_date']) ?></td>
            </tr>
        <?php endforeach; ?>
    </table>
    <h2>Car Management</h2>
    <a href="add_car.php">Add Car</a>
    <?php
}

?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Profile</title>
</head>
<body>
    <header>
        <h1>Welcome, <?= htmlspecialchars($user['fullname']) ?></h1>
        <a href="logout.php">Logout</a>
    </header>
    <main>
        <?php if ($isAdmin): ?>
            <?php renderAdminPage($bookings, $cars); ?>
        <?php else: ?>
            <h2>Your Reservations</h2>
            <!-- User-specific reservations -->
        <?php endif; ?>
    </main>
</body>
</html>
