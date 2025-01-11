<?php
    session_start(); // Starts or resumes a session, allowing data to persist across page requests using a unique session ID.
    $users = json_decode(file_get_contents("users.json"), true); // Reads the contents of the "users.json" file, decodes the JSON to PHP array, and assigns it to $users.
    if (isset($_GET["id"]) && isset($_SESSION['userid']) && $users[$_SESSION['userid']]['isAdmin']) { //isset: if a variable exists and is not null
        
        //$users[$_SESSION['userid']]['isAdmin']: Accesses the 'isAdmin' property of the user identified by the 'userid' in the session, from the $users associative array.

        $reg = json_decode(file_get_contents("data.json"), true); //'true': JSON data from data.json is decoded into an associative array, else decoded to an object.
        if (isset($reg[$_GET["id"]])) { 
            unset($reg[$_GET["id"]]); //removes the element with the specified key 'id' from the array
            file_put_contents('data.json',json_encode($reg,JSON_PRETTY_PRINT)); //write data to a file
        } 
    }
    
    header('location: index.php'); // Redirects the user to the index.php page
?>

<!-- 
NOTE:
$_GET: 
Purpose: Used to collect data sent via the URL's query string (e.g., example.com/page.php?key=value).
Scope: Superglobal array accessible across your PHP script.
Data Source: Query parameters in the URL.
Example Usage: $user_id = $_GET['id']; example.com/page.php?id=42

$_SESSION:
session_start() initializes or resumes the session.
$_SESSION allows storing key-value pairs (e.g., ['user_id' => 42]).
Data is stored server-side and linked to the client via a session ID.
session_start();
Example Usage: 
$_SESSION['user_id'] = 42; // Store data in the session
echo $_SESSION['user_id']; // Outputs: 42
-->

<!-- 
session_start(); // Start session

// Using $_GET
$user_id = $_GET['id'];
echo "User ID from URL: " . $user_id;

// Using $_SESSION
$_SESSION['user_id'] = $user_id; // Store in session
echo "User ID stored in session: " . $_SESSION['user_id']; -->