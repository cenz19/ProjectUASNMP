<?php 
    error_reporting(E_ERROR | E_PARSE);

    $conn = new mysqli("localhost", "root", "", "mydb");
    
    if ($conn->connect_errno) {
        $arr = array("result" => "ERROR", "message" => "Failed to connect");
        echo json_encode($arr);
        die();
    }

    if (isset($_POST["id"])) {
        $id = $_POST["id"];
        $sql = "SELECT c.id, title, description, num_likes, access, num_paragraph, genre, url_gambar, waktu_dibuat, username FROM cerbungs c LEFT JOIN approval_cerbung ac ON c.id=ac.cerbung_id LEFT JOIN users u ON u.id=ac.author_id WHERE c.id=?";
        $stmt = $conn->prepare($sql);
        $stmt->bind_param("i", $id);
        $stmt->execute();
        $array = array();
        $result = $stmt->get_result();
        if ($result->num_rows > 0) {
            while ($obj = $result->fetch_object()) {
                $array[] = $obj;
                echo json_encode(array("result" => "OK", "data" => $array));
            }
        } else {
            echo json_encode(array("result" => "ERROR", "data" => "No data found"));
            die();
        }
    }
    
?>