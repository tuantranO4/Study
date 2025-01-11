<?php
    session_start();
    
    if(!isset($_SESSION['userid']) && count($_POST) > 0){  //check that the user is not login and (POST > 0): if no data has been submitted, $_POST is an empty array (=0)
        $username = $_POST['username'] ?? '';
        $password = $_POST['password'] ?? '';//null coalescing operator (??) to ensure default empty strings if 'username' or 'password' is not set

        $users = json_decode(file_get_contents("users.json"), true);// Reads the users.json

        $id = array_keys(array_filter($users, fn($u) => $u['username'] === $username))[0]; 
        //lambda func: Filters the $users array to find entries where username matches the submitted value

        if($id !== null){
            if(password_verify($password, $users[$id]['password']))  //password_verify(string $password, string $hash): bool
                $_SESSION['userid'] = $id; //if true, store userid in session
            else $_SESSION['loginerror'] = 2; //2: password error
        }else $_SESSION['loginerror'] = 1; //   1: username error
    }

    header('location: index.php');//Redirects the browser to the main page (index.php) after processing the login attempt