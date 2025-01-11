    <?php
    // auth.php
    session_start();
    require_once "storage.php";

    $userStorage = new Storage(new JsonIO("storage/users.json")); 
   function getUserByEmail($email) {
        global $userStorage;
        $users = $userStorage->findAll();
        foreach ($users as $user) {
            if ($user['email'] === $email) {
                return $user;
            }
        }
        return null;
    }
    
    function verifyLogin($email, $password) {
        $user = getUserByEmail($email);
        if ($user && $user['password'] === $password) {
            session_start();
            $_SESSION['user'] = $user;
            if ($user['is_admin']) {
                header("Location: adminprofile.php"); 
            } else {
                header("Location: profile.php"); 
            }
            exit();
        }
        return false;
    }
    
    function redirect(string $page) {
        header("Location: $page");
        exit();
    }

    function is_logged_in(): bool {
        return isset($_SESSION["user"]);
    }

    function is_admin(): bool {
        return is_logged_in() && $_SESSION["user"]["is_admin"] === true;
    }


function checkAdmin() {
    if (!isset($_SESSION['user']) || $_SESSION['user']['is_admin'] !== true) {
        header("Location: login.php");
        exit();
    }
}

    function login($user) {
        $_SESSION["user"] = [
            "id" => $user["id"],
            "email" => $user["email"],
            "full_name" => $user["full_name"],
            "is_admin" => $user["is_admin"] ?? false
        ];
    }

    function logout() {
        session_unset();
        session_destroy();
    }

    function authorize($allowed_roles = ["user"]) {
        if (!is_logged_in()) {
            redirect("login.php");
        }
        if (in_array("admin", $allowed_roles) && !is_admin()) {
            redirect("index.php");
        }
    }