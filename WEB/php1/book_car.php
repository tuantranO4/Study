<?php
require_once "storage.php";
require_once "auth.php";

authorize(["user", "admin"]);

$carStorage = new Storage(new JsonIO("storage/cars.json"));
$bookingStorage = new Storage(new JsonIO("storage/bookings.json"));

$start_date = new DateTime($_POST['start_date']);
$end_date = new DateTime($_POST['end_date']);
$car_id = $_POST['car_id'];

$days = $end_date->diff($start_date)->days;
$car = $carStorage->findById($car_id);
$total_cost = $days * $car['daily_price_huf'];

$booking = [
    "car_id" => $car_id,
    "user_id" => $_SESSION['user']['id'],
    "start_date" => $start_date->format('Y-m-d'),
    "end_date" => $end_date->format('Y-m-d'),
    "total_cost" => $total_cost
];

$booking_id = $bookingStorage->add($booking);
header("Location: confirmation.php?booking_id=$booking_id");
exit();
