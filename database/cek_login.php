<?php 
    error_reporting(E_ERROR | E_PARSE);
    $conn = new mysqli("localhost", "root", "mysql", "mydb");
    if($conn->connect_errno) {
    $error = array('result' => 'ERROR', 'message' => 'Failed to connect');
    echo json_encode($error);
    die();
    }

    if (isset($_POST["username"]) && isset($_POST["password"])) {
        $username = $_POST["username"];
        $pass = $_POST["password"];
        $conn->set_charset("UTF8");
        $sql = "SELECT * FROM users where username=? AND password=?";
        $stmt = $conn->prepare($sql);
        $stmt->bind_param("ss", $username, $pass);
        $stmt->execute();
        $result = $stmt->get_result();
        if ($result->num_rows > 0) {
            while ($obj = $result->fetch_object()) {
                if ($obj->username === $username && $obj->password === $pass) {
                    echo json_encode(array("result" => "OK", "msg" => "Login successful"));
                } else {
                    echo json_encode(array("result" => "ERROR", "msg" => "Invalid username/password"));
                    die();
                }
            }
        } else {
            echo json_encode(array("result" => "ERROR", "msg" => "No data found"));
            die();
        }
    }
    
?>