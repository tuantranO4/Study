    <?php
    // auth.php
    session_start();

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