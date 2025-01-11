<?php
session_start();

// Load cars from JSON file
$cars = json_decode(file_get_contents('cars.json'), true);

// Filter logic
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $filters = [
        'transmission' => $_POST['transmission'] ?? '',
        'min_passengers' => $_POST['min_passengers'] ?? 0,
        'min_price' => $_POST['min_price'] ?? 0,
        'max_price' => $_POST['max_price'] ?? PHP_INT_MAX,
        'start_date' => $_POST['start_date'] ?? '',
        'end_date' => $_POST['end_date'] ?? '',
    ];

    $filtered_cars = array_filter($cars, function ($car) use ($filters) {
        // Check transmission type
        if ($filters['transmission'] && $car['transmission'] !== $filters['transmission']) {
            return false;
        }

        // Check passenger capacity
        if ($filters['min_passengers'] > 0 && $car['passengers'] < $filters['min_passengers']) {
            return false;
        }

        // Check price range
        if ($car['daily_price_huf'] < $filters['min_price'] || $car['daily_price_huf'] > $filters['max_price']) {
            return false;
        }

        // Check availability (mock logic, replace with booking checks if implemented)
        // For now, assume all cars are available.

        return true;
    });
} else {
    $filtered_cars = $cars; // Default to all cars.
}
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>iKarRental - Homepage</title>
    <link rel="stylesheet" href="styles.css"> <!-- Use a CSS file for styling -->
</head>
<body>
    <header>
        <h1>Rent Cars Easily!</h1>
        <nav>
            <?php if (isset($_SESSION['user'])): ?>
                <a href="profile.php">Profile</a>
                <a href="logout.php">Logout</a>
            <?php else: ?>
                <a href="login.php">Login</a>
                <a href="register.php">Register</a>
            <?php endif; ?>
        </nav>
    </header>

    <main>
        <section class="filter-section">
            <form method="POST">
                <label>
                    Transmission:
                    <select name="transmission">
                        <option value="">Any</option>
                        <option value="Automatic">Automatic</option>
                        <option value="Manual">Manual</option>
                    </select>
                </label>
                <label>
                    Minimum Passengers:
                    <input type="number" name="min_passengers" min="0">
                </label>
                <label>
                    Price Range:
                    <input type="number" name="min_price" placeholder="Min Price">
                    <input type="number" name="max_price" placeholder="Max Price">
                </label>
                <label>
                    Available From:
                    <input type="date" name="start_date">
                </label>
                <label>
                    Until:
                    <input type="date" name="end_date">
                </label>
                <button type="submit">Filter</button>
            </form>
        </section>

        <section class="car-listing">
            <?php if (empty($filtered_cars)): ?>
                <p>No cars found matching your criteria.</p>
            <?php else: ?>
                <?php foreach ($filtered_cars as $car): ?>
                    <div class="car-card">
                        <img src="<?= htmlspecialchars($car['image']) ?>" alt="<?= htmlspecialchars($car['brand'] . ' ' . $car['model']) ?>">
                        <h3><?= htmlspecialchars($car['brand'] . ' ' . $car['model']) ?></h3>
                        <p>Year: <?= htmlspecialchars($car['year']) ?></p>
                        <p>Passengers: <?= htmlspecialchars($car['passengers']) ?></p>
                        <p>Transmission: <?= htmlspecialchars($car['transmission']) ?></p>
                        <p>Price: <?= htmlspecialchars(number_format($car['daily_price_huf'], 0)) ?> HUF/day</p>
                        <?php if (isset($_SESSION['user'])): ?>
                            <a href="details.php?id=<?= htmlspecialchars($car['id']) ?>">Book</a>
                        <?php else: ?>
                            <p><a href="login.php">Login to book</a></p>
                        <?php endif; ?>
                    </div>
                <?php endforeach; ?>
            <?php endif; ?>
        </section>
    </main>
</body>
</html>
