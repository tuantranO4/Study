<?php
session_start();
$users = json_decode(file_get_contents("users.json"), true); // Reads the contents of the "users.json" file, decodes the JSON to PHP array, and assigns it to $users.
$result = []; //empty array

if (isset($_SESSION['userid']) && $users[$_SESSION['userid']]['isAdmin']) { 
    //isset($_SESSION['userid']): Checks if a session variable named userid exists 
    //&& $users[$_SESSION['userid']]['isAdmin']: Checks if the user is an admin

    $filter =  $_GET['filter'] ?? '';
    // Retrieves the 'filter' parameter from the URL query string (e.g., `script.php?filter=abc`).
    // If 'filter' is not set, it defaults to an empty string.

    $data = json_decode(file_get_contents("data.json"), true);

    foreach ($data as $value) { //Loops through each entry in the $data array

        if (trim($filter === '' || strpos($value['fullname'], $filter) !== false)) { //trim: Removes whitespace from the beginning and end of a string
            //If $filter is an empty string → Include the entry (no filtering).
            //If $filter is not empty → Include the entry only if $filter exists in fullname

            //$position = strpos($string, $substring); //Returns the position of the first occurrence of $substring in $string. Returns false if not found.

            $value['isAdmin'] = $users[$_SESSION['userid']]['isAdmin']; //Adds the 'isAdmin' status of the current user to the users.json entry
            $result[] = $value; //Appends the entry to the $result array
        }
    }
}

echo json_encode($result, JSON_PRETTY_PRINT); //Encodes the $result array as a JSON string and outputs it with proper indentation (JSON_PRETTY_PRINT)

// CONVERT users.json:
// $users = [
//     "010101001" => [
//         "id" => "010101001",
//         "username" => "chip.board",
//         "password" => "$2y$10$\/76dj4CVGQi1X9fn.p.OEuKAr1R4AvPKNk\/Rw.ZX0PwSb4I71klBK",
//         "isAdmin" => true
//     ],
//     "010101002" => [
//         "id" => "010101002",
//         "username" => "al.gorithm",
//         "password" => "$2y$10$yjLdFSiImJFK2EuCbj\/tReRGQqkVPmWmljg8\/F6GADyDaH9YxnKna",
//         "isAdmin" => false
//     ]
// ];

