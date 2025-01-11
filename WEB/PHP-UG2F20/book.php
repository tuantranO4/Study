<?php
session_start();

// Redirect if the user is not logged in
if (!isset($_SESSION['user'])) {
    header('Location: login.php');
    exit();
}

// Load cars and bookings data
$cars = json_decode(file_get_contents('cars.json'), true);
$bookings = json_decode(file_get_contents('bookings.json'), true) ?? [];

// Validate POST data
if (!isset($_POST['car_id'], $_POST['start_date'], $_POST['end_date'])) {
    die('Missing booking information.');
}

$car_id = (int)$_POST['car_id'];
$start_date = $_POST['start_date'];
$end_date = $_POST['end_date'];

// Find the car by ID
$car = array_filter($cars, function ($c) use ($car_id) {
    return $c['id'] === $car_id;
});

if (empty($car)) {
    die('Car not found.');
}

$car = array_values($car)[0];

// Check for overlapping bookings
foreach ($bookings as $booking) {
    if ($booking['car_id'] === $car_id && (
        ($start_date >= $booking['start_date'] && $start_date <= $booking['end_date']) ||
        ($end_date >= $booking['start_date'] && $end_date <= $booking['end_date'])
    )) {
        echo "<div style='text-align:center;'>
                <h1>Booking Failed!</h1>
                <p>The {$car['brand']} {$car['model']} is not available from $start_date to $end_date.</p>
                <a href='index.php'>Back to Homepage</a>
              </div>";
        exit();
    }
}

// Calculate total price
$start = new DateTime($start_date);
$end = new DateTime($end_date);
$days = $end->diff($start)->days + 1; // Include the start date
$total_price = $days * $car['daily_price_huf'];

// Save the booking
$bookings[] = [
    'car_id' => $car_id,
    'user_id' => $_SESSION['user']['id'],
    'start_date' => $start_date,
    'end_date' => $end_date,
];
file_put_contents('bookings.json', json_encode($bookings));

// Show success message
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Booking Success</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div style="text-align: center;">
        <h1>Successful Booking!</h1>
        <p>The <?= htmlspecialchars($car['brand']) ?> <?= htmlspecialchars($car['model']) ?> has been successfully booked for the interval <?= htmlspecialchars($start_date) ?> to <?= htmlspecialchars($end_date) ?>.</p>
        <p>Total Price: <?= number_format($total_price) ?> HUF</p>
        <a href="profile.php">My Profile</a>
    </div>
</body>
</html>
