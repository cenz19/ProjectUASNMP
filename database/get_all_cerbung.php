<?php 
    error_reporting(E_ERROR | E_PARSE);

    $conn = new mysqli("localhost", "root", "", "mydb");

    if($conn->connect_errno) {
    $error = array('result' => 'ERROR', 'message' => 'Failed to connect');
    echo json_encode($error);
    die();
    }

    $conn->set_charset("UTF8");
    $sql = "SELECT c.id, title, description, num_likes, access, num_paragraph, genre, url_gambar, waktu_dibuat, username FROM cerbungs c LEFT JOIN approval_cerbung ac ON c.id=ac.cerbung_id LEFT JOIN users u ON u.id=ac.author_id";
    $result = $conn->query($sql);
    $array = array();
    if ($result->num_rows > 0) {
        while ($obj = $result->fetch_object()) {
            $array[] = $obj;
        }
        echo json_encode(array("result" => "OK", "data" => $array));
    } else {
        echo json_encode(array("result" => "ERROR", "message" => "No data found"));
        die();
    }
    
?>