<?php
require_once "auth.php";
checkAdmin();

require_once "storage.php";
$carStorage = new Storage(new JsonIO("storage/cars.json"));
$bookingStorage = new Storage(new JsonIO("storage/bookings.json"));

$success_message = '';
$error_message = '';

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    if (isset($_POST['action'])) {
        $action = $_POST['action'];

        try {
            if ($action === 'add') { 
                $cars = $carStorage->findAll();
                $latestId = 0;
                foreach ($cars as $car) {
                    if ((int)$car['id'] > $latestId) {
                        $latestId = (int)$car['id'];
                    }
                }
                $newCar = [
                    "id" => $latestId + 1,
                    "brand" => $_POST['brand'],
                    "model" => $_POST['model'],
                    "year" => (int)$_POST['year'],
                    "transmission" => $_POST['transmission'],
                    "fuel_type" => $_POST['fuel_type'],
                    "passengers" => (int)$_POST['passengers'],
                    "daily_price_huf" => (int)$_POST['daily_price_huf'],
                    "image" => $_POST['image'],
                ];
                $carStorage->add($newCar);
                $success_message = "Car added successfully!";
            }

            if ($action === 'edit') {
                $carId = $_POST['id']; 
                $updatedCar = [
                    "brand" => $_POST['brand'],
                    "model" => $_POST['model'],
                    "year" => (int)$_POST['year'],
                    "transmission" => $_POST['transmission'],
                    "fuel_type" => $_POST['fuel_type'],
                    "passengers" => (int)$_POST['passengers'],
                    "daily_price_huf" => (int)$_POST['daily_price_huf'],
                    "image" => $_POST['image'],
                ];
                if ($carStorage->update($carId, $updatedCar)) {
                    $success_message = "Car updated successfully!";
                } else {
                    $error_message = "Car not found!";
                }
            }
            
                        

            if ($action === 'delete') {
                $carId = $_POST['id'];
                $carStorage->delete($carId);
                $bookings = $bookingStorage->findAll(["car_id" => $carId]);
                foreach ($bookings as $booking) {
                    $bookingStorage->delete($booking['id']);
                }
                $success_message = "Car and related bookings deleted successfully!";
            }
        } catch (Exception $e) {
            $error_message = "An error occurred: " . $e->getMessage();
        }
    }
}

$cars = $carStorage->findAll();
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Cars - iKarRental</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
</head>
<body class="bg-gray-100">
    <?php include "navbar.php"; ?>
    
    <div class="container mx-auto px-4 py-8">
        <?php if ($success_message): ?>
            <div class="bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded relative mb-4" role="alert">
                <span class="block sm:inline"><?= htmlspecialchars($success_message) ?></span>
            </div>
        <?php endif; ?>

        <?php if ($error_message): ?>
            <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative mb-4" role="alert">
                <span class="block sm:inline"><?= htmlspecialchars($error_message) ?></span>
            </div>
        <?php endif; ?>

        <!-- Add new ccar-->
        <div class="bg-white p-6 rounded-lg shadow-lg mb-8">
            <h2 class="text-2xl font-bold mb-4">Add New Car</h2>
            <form method="post" class="grid grid-cols-1 md:grid-cols-2 gap-4">
                <input type="hidden" name="action" value="add">
                
                <div>
                    <label class="block text-sm font-medium text-gray-700">Brand</label>
                    <input type="text" name="brand" required class="mt-1 block w-full rounded-md border-gray-300 shadow-sm">
                </div>
                
                <div>
                    <label class="block text-sm font-medium text-gray-700">Model</label>
                    <input type="text" name="model" required class="mt-1 block w-full rounded-md border-gray-300 shadow-sm">
                </div>
                
                <div>
                    <label class="block text-sm font-medium text-gray-700">Year</label>
                    <input type="number" name="year" required min="1900" max="2024" class="mt-1 block w-full rounded-md border-gray-300 shadow-sm">
                </div>
                
                <div>
                    <label class="block text-sm font-medium text-gray-700">Transmission</label>
                    <select name="transmission" required class="mt-1 block w-full rounded-md border-gray-300 shadow-sm">
                        <option value="Manual">Manual</option>
                        <option value="Automatic">Automatic</option>
                    </select>
                </div>

                <div>
                    <label class="block text-sm font-medium text-gray-700">Fuel Type</label>
                    <select name="fuel_type" required class="mt-1 block w-full rounded-md border-gray-300 shadow-sm">
                        <option value="Petrol">Petrol</option>
                        <option value="Diesel">Diesel</option>
                        <option value="Electric">Electric</option>
                        <option value="Hybrid">Hybrid</option>
                    </select>
                </div>
                
                <div>
                    <label class="block text-sm font-medium text-gray-700">Passengers</label>
                    <input type="number" name="passengers" required min="1" max="9" class="mt-1 block w-full rounded-md border-gray-300 shadow-sm">
                </div>
                
                <div>
                    <label class="block text-sm font-medium text-gray-700">Daily Price (HUF)</label>
                    <input type="number" name="daily_price_huf" required min="0" class="mt-1 block w-full rounded-md border-gray-300 shadow-sm">
                </div>
                
                <div class="md:col-span-2">
                    <label class="block text-sm font-medium text-gray-700">Image URL</label>
                    <input type="url" name="image" required class="mt-1 block w-full rounded-md border-gray-300 shadow-sm">
                </div>
                
                <div class="md:col-span-2">
                    <button type="submit" class="w-full bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600">
                        Add New Car
                    </button>
                </div>
            </form>
        </div>

        <!-- Exist car -->
        <div class="bg-white p-6 rounded-lg shadow-lg">
            <h2 class="text-2xl font-bold mb-4">Manage Existing Cars</h2>
            <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                <?php foreach ($cars as $car): ?>
                <div class="border rounded-lg p-4 space-y-4">
                    <img src="<?= htmlspecialchars($car['image']) ?>" 
                         alt="<?= htmlspecialchars($car['brand'] . ' ' . $car['model']) ?>"
                         class="w-full h-48 object-cover rounded-lg">
                    
                    <form method="post" class="space-y-3">
                        <input type="hidden" name="id" value="<?= htmlspecialchars($car['id']) ?>">
                        
                        <div>
                            <label class="block text-sm font-medium text-gray-700">Brand</label>
                            <input type="text" name="brand" value="<?= htmlspecialchars($car['brand']) ?>" required 
                                   class="mt-1 block w-full rounded-md border-gray-300 shadow-sm">
                        </div>
                        
                        <div>
                            <label class="block text-sm font-medium text-gray-700">Model</label>
                            <input type="text" name="model" value="<?= htmlspecialchars($car['model']) ?>" required 
                                   class="mt-1 block w-full rounded-md border-gray-300 shadow-sm">
                        </div>
                        
                        <div class="grid grid-cols-2 gap-2">
                            <div>
                                <label class="block text-sm font-medium text-gray-700">Year</label>
                                <input type="number" name="year" value="<?= htmlspecialchars($car['year']) ?>" required 
                                       class="mt-1 block w-full rounded-md border-gray-300 shadow-sm">
                            </div>
                            
                            <div>
                                <label class="block text-sm font-medium text-gray-700">Passengers</label>
                                <input type="number" name="passengers" value="<?= htmlspecialchars($car['passengers']) ?>" required 
                                       class="mt-1 block w-full rounded-md border-gray-300 shadow-sm">
                            </div>
                        </div>
                        
                        <div>
                            <label class="block text-sm font-medium text-gray-700">Transmission</label>
                            <select name="transmission" required class="mt-1 block w-full rounded-md border-gray-300 shadow-sm">
                                <option value="Manual" <?= $car['transmission'] === 'Manual' ? 'selected' : '' ?>>Manual</option>
                                <option value="Automatic" <?= $car['transmission'] === 'Automatic' ? 'selected' : '' ?>>Automatic</option>
                            </select>
                        </div>

                        <div>
                            <label class="block text-sm font-medium text-gray-700">Fuel Type</label>
                            <select name="fuel_type" required class="mt-1 block w-full rounded-md border-gray-300 shadow-sm">
                                <option value="Petrol" <?= $car['fuel_type'] === 'Petrol' ? 'selected' : '' ?>>Petrol</option>
                                <option value="Diesel" <?= $car['fuel_type'] === 'Diesel' ? 'selected' : '' ?>>Diesel</option>
                                <option value="Electric" <?= $car['fuel_type'] === 'Electric' ? 'selected' : '' ?>>Electric</option>
                                <option value="Hybrid" <?= $car['fuel_type'] === 'Hybrid' ? 'selected' : '' ?>>Hybrid</option>
                            </select>
                        </div>
                        
                        <div>
                            <label class="block text-sm font-medium text-gray-700">Daily Price (HUF)</label>
                            <input type="number" name="daily_price_huf" value="<?= htmlspecialchars($car['daily_price_huf']) ?>" required 
                                   class="mt-1 block w-full rounded-md border-gray-300 shadow-sm">
                        </div>
                        
                        <div>
                            <label class="block text-sm font-medium text-gray-700">Image URL</label>
                            <input type="url" name="image" value="<?= htmlspecialchars($car['image']) ?>" required 
                                   class="mt-1 block w-full rounded-md border-gray-300 shadow-sm">
                        </div>
                        
                        <div class="flex space-x-2">
                            <button type="submit" name="action" value="edit" 
                                    class="flex-1 bg-yellow-500 text-white px-4 py-2 rounded hover:bg-yellow-600">
                                Update
                            </button>
                            <button type="submit" name="action" value="delete" 
                                    class="flex-1 bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600"
                                    onclick="return confirm('Are you sure you want to delete this car? All related bookings will also be deleted.')">
                                Delete
                            </button>
                        </div>
                    </form>
                </div>
                <?php endforeach; ?>
            </div>
        </div>
    </div>
</body>
</html>