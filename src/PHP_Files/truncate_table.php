<?php
    include "conn.php";

    $response = array();

    if (isset($_POST["tableName"])) {
        $tableName = $_POST["tableName"];

        $query = "TRUNCATE TABLE `$tableName`";
        $res = mysqli_query($con, $query);

        if ($res) {
            $response['reqmsg'] = "Table Truncated!";
            $response['reqcode'] = "1";
        }
        else {
            $response['reqmsg'] = "Error Truncating Table!";
            $response['reqcode'] = "0";
        }
    }
    else {
        $response['reqmsg'] = "Incomplete Request";
        $response['reqcode'] = "0";
    }

    $x = json_encode($response);
    echo $x;
?>