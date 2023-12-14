<?php 
    error_reporting(E_ERROR | E_PARSE);

    $conn = new mysqli("localhost", "root", "", "mydb");
    
    if ($conn->connect_errno) {
        $arr = array("result" => "ERROR",
                    "message" => "Failed to connect");
        echo json_encode($arr);
        die();
    }

    if (isset($_POST["title"]) && isset($_POST["description"]) && isset($_POST["num_likes"]) && isset($_POST["status"]) && isset($_POST["num_paragraph"]) 
        && isset($_POST["genre"]) && isset($_POST["waktu_dibuat"])) {
        $title = $_POST["title"];
        $desc = $_POST["description"];
        $num_likes = $_POST["num_likes"];
        $status = $_POST["status"];
        $num_paragraph = $_POST["num_paragraph"];
        $genre = $_POST["genre"];
        $waktu_dibuat = $_POST["waktu_dibuat"];

        $sql = "INSERT into cerbungs (title, description, num_likes, status, num_paragraph, genre, waktu_dibuat) VALUES (?,?,?,?,?,?,?)";
        $stmt = $conn->prepare($sql);
        $stmt->bind_param("ssiiiss", $title, $desc, $num_likes, $status, $num_paragraph, $genre, $waktu_dibuat);
        $stmt->execute();
        $arr = array("result" => "OK");
        echo json_encode($arr);
    } else {
        $arr = array("result" => "ERROR", "msg" => "Error making cerbung");
        echo json_encode($arr);
    }
?>