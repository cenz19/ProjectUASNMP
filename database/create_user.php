<?php 
    error_reporting(E_ERROR | E_PARSE);

    $conn = new mysqli("localhost", "root", "", "mydb");
    
    if ($conn->connect_errno) {
        $arr = array("result" => "ERROR",
                    "message" => "Failed to connect");
        echo json_encode($arr);
        die();
    }

    if (isset($_POST["username"]) && isset($_POST["password"]) && isset($_POST["num_follower"])) {
        $username = $_POST["username"];
        $pass = $_POST["password"];
        $num_foll = $_POST["num_follower"];

        $sql = "INSERT into users (username, password, num_follower) VALUES (?, ?, ?)";
        $stmt = $conn->prepare($sql);
        $stmt->bind_param("ssi", $username, $pass, $num_foll);
        $stmt->execute();
        $array = array("result" => "OK");
        echo json_encode($array);
    } else {
        echo json_encode(array("result" => "ERROR", "msg" => "Error making user"));       
    }
?>