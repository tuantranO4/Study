<?php
    session_start();
    session_destroy(); //ends the current session and clears all data stored in $_SESSION

    header('location: index.php');
