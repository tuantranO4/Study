<?php
require_once "auth.php";
require_once "storage.php";

// Ensure only admins can access this page
checkAdmin();

$carStorage = new Storage(new JsonIO("storage/cars.json"));

// Initialize variables to handle form values
$brand = $model = "";
$year = 0;
$id = isset($_GET['id']) ? $_GET['id'] : null;
$action = isset($_GET['action']) ? $_GET['action'] : 'add';

// Handling form submission
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $brand = $_POST['brand'];
    $model = $_POST['model'];
    $year = $_POST['year'];

    switch ($action) {
        case 'add':
            $carStorage->add(['brand' => $brand, 'model' => $model, 'year' => $year]);
            break;
        case 'edit':
            if ($id) {
                $carStorage->update($id, ['brand' => $brand, 'model' => $model, 'year' => $year]);
            }
            break;
        case 'delete':
            if ($id) {
                $carStorage->delete($id);
            }
            break;
    }
    header("Location: adminprofile.php");
    exit();
}

// Fetch existing data for edit
if ($action == 'edit' && $id) {
    $car = $carStorage->findById($id);
    if ($car) {
        $brand = $car['brand'];
        $model = $car['model'];
        $year = $car['year'];
    }
}

// Define the form action URL
$formAction = "admincarmod.php?action=$action" . ($id ? "&id=$id" : "");
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><?= ucfirst($action) ?> Car</title>
</head>
<body>
    <h1><?= ucfirst($action) ?> Car</h1>
    <form action="<?= htmlspecialchars($formAction) ?>" method="post">
        Brand: <input type="text" name="brand" value="<?= htmlspecialchars($brand) ?>" required><br>
        Model: <input type="text" name="model" value="<?= htmlspecialchars($model) ?>" required><br>
        Year: <input type="number" name="year" value="<?= htmlspecialchars($year) ?>" required><br>
        <?php if ($action === 'delete'): ?>
            <p>Are you sure you want to delete this car?</p>
            <input type="submit" value="Delete Car">
        <?php else: ?>
            <input type="submit" value="<?= ucfirst($action) ?> Car">
        <?php endif; ?>
    </form>
    <a href="adminprofile.php">Back to Dashboard</a>
</body>
</html>
