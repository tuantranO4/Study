<?php
session_start();
$users = json_decode(file_get_contents("users.json"), true); //read users.json

if (isset($_GET["id"]) && isset($_SESSION['userid']) && ($users[$_SESSION['userid']]['isAdmin'] || $_SESSION['userid'] === $_GET["id"])) { //check if the user in session
    
    $reg = json_decode(file_get_contents("data.json"), true);
    if (isset($reg[$_GET["id"]])) { //Checks if the requested user ID exists in $reg
        $current_user = $reg[$_GET["id"]]; //If yes, the user's data is stored in $current_user
    } else header('location: index.php');//If no, redirects to index.php

} else header('location: index.php'); //user is not in session, redirects to index.php
?>

<!DOCTYPE html>
<html lang="hu">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Show User Data</title> <!-- Title of the page -->
    <link rel="stylesheet" href="style.css">
</head>

<body>
    <h1><?= $current_user["fullname"] ?></h1> <!-- Display the current_user's name -->
    <img src="./img/<?= $current_user["img"] ?>" alt="">
    <ul> <!-- Display the current_user's basic details -->
        <li><a href="mailto:<?= $current_user["email"] ?>"><?= $current_user["email"] ?></a></li>
        <li><?= $current_user["age"] ?></li>
        <li><?= $current_user["gender"] == "m" ? "male" : "female" ?></li>
        <li><?= $current_user["regdate"] ?></li>
        <li><?= $current_user["notes"] ?></li>
    </ul>

    <a href="index.php">Back to Home</a> <!-- Link to the home page -->
</body>

</html>