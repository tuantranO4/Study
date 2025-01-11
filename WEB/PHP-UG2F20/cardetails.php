<?php
session_start();

// Load cars from JSON
$cars = json_decode(file_get_contents('cars.json'), true);

// Check if car ID is provided dynamically via GET parameter
if (!isset($_GET['id']) || !is_numeric($_GET['id'])) {
    die('Car ID not specified or invalid.');
}

define('CAR_ID', (int)$_GET['id']);
$car = array_filter($cars, function ($c) {
    return $c['id'] === CAR_ID;
});

if (empty($car)) {
    die('Car not found.');
}

$car = array_values($car)[0];

// Helper function for escaping HTML
function escape($value) {
    return htmlspecialchars($value, ENT_QUOTES, 'UTF-8');
}
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><?= escape($car['brand'] . ' ' . $car['model']) ?> - Details</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <header>
        <h1>Car Details</h1>
        <nav>
            <?php if (isset($_SESSION['user'])): ?>
                <a href="profile.php">Profile</a>
                <a href="logout.php">Logout</a>
            <?php else: ?>
                <a href="login.php">Login</a>
                <a href="register.php">Registration</a>
            <?php endif; ?>
        </nav>
    </header>

    <main>
        <section class="car-details">
            <img src="<?= escape($car['image']) ?>" alt="<?= escape($car['brand'] . ' ' . $car['model']) ?>">
            <h2><?= escape($car['brand'] . ' ' . $car['model']) ?></h2>
            <ul>
                <li><strong>Year of Manufacture:</strong> <?= escape($car['year']) ?></li>
                <li><strong>Shifter:</strong> <?= escape($car['transmission']) ?></li>
                <li><strong>Fuel:</strong> <?= escape($car['fuel_type']) ?></li>
                <li><strong>Number of Seats:</strong> <?= escape($car['passengers']) ?></li>
                <li><strong>Price:</strong> <?= escape(number_format($car['daily_price_huf'], 0)) ?> HUF/Day</li>
            </ul>

            <?php if (isset($_SESSION['user'])): ?>
                <form action="book.php" method="POST">
                    <input type="hidden" name="car_id" value="<?= escape($car['id']) ?>">
                    <label for="start_date">Start Date:</label>
                    <input type="date" name="start_date" id="start_date" required>

                    <label for="end_date">End Date:</label>
                    <input type="date" name="end_date" id="end_date" required>

                    <button type="submit">Book</button>
                </form>
            <?php else: ?>
                <p><a href="login.php">Login to book this car</a></p>
            <?php endif; ?>
        </section>
    </main>
</body>
</html>
