<?php
    include "conn.php";

    $response = array();

    if (isset($_FILES["image"]["tmp_name"])) {
        $tmp_file = $_FILES["image"]["tmp_name"];
        $img_name = $_FILES["image"]["name"];
        $upload_dir = "images/".time().$img_name;

        $up = move_uploaded_file($tmp_file, $upload_dir);
        if ($up) {
            $response['msg'] = "Upload success!";
            $response['url'] = $upload_dir;
            $response['code'] = "1";
        }
        else {
            $response['msg'] = "Could not upload!";
            $response['code'] = "0";
        }
    }
    else {
        $response['msg'] = "Incomplete Request!";
        $response['code'] = "0";
    }

    $x = json_encode($response);
    echo $x;
?>