<?php
    include "conn.php";

    $response = array();

    if (isset($_POST["tableName"])) {
        $tableName = $_POST["tableName"];

        $query = "SELECT * FROM `$tableName` WHERE 1";
        $res = mysqli_query($con, $query);

        if ($res) {
            $response['data'] = array();
            if ($tableName == "vendors" || $tableName == "customers") {
                while ($row = mysqli_fetch_array($res)) {
                    $data = array();
                    $data['id'] = $row['id'];
                    $data['name'] = $row['name'];
                    $data['email'] = $row['email'];
                    $data['password'] = $row['password'];
                    $data['phoneno'] = $row['phoneno'];
                    $data['cnic'] = $row['cnic'];
                    $data['accountno'] = $row['accountno'];
                    $data['address'] = $row['address'];
                    $data['dp'] = $row['dp'];
                    array_push($response['data'], $data);
                }
            }

            else if ($tableName == "rooms") {
                while ($row = mysqli_fetch_array($res)) {
                    $data = array();
                    $data['hotel_id'] = $row['hotel_id'];
                    $data['roomno'] = $row['roomno'];
                    $data['type'] = $row['type'];
                    $data['available_date'] = $row['available_date'];
                    $data['is_available'] = $row['is_available'];
                    array_push($response['data'], $data);
                }
            }

            else if ($tableName == "hotels") {
                while ($row = mysqli_fetch_array($res)) {
                    $data = array();
                    $data['id'] = $row['id'];
                    $data['name'] = $row['name'];
                    $data['address'] = $row['address'];
                    $data['location'] = $row['location'];
                    $data['single_rooms'] = $row['single_rooms'];
                    $data['double_rooms'] = $row['double_rooms'];
                    $data['single_room_price'] = $row['single_room_price'];
                    $data['double_room_price'] = $row['double_room_price'];
                    $data['registered_by'] = $row['registered_by'];
                    array_push($response['data'], $data);
                }
            }

            else if ($tableName == "reservations") {
                while ($row = mysqli_fetch_array($res)) {
                    $data = array();
                    $data['hotel_name'] = $row['hotel_name'];
                    $data['hotel_location'] = $row['hotel_location'];
                    $data['total_rooms'] = $row['total_rooms'];
                    $data['room_numbers'] = $row['room_numbers'];
                    $data['total_price'] = $row['total_price'];
                    $data['check_in_date'] = $row['check_in_date'];
                    $data['check_out_date'] = $row['check_out_date'];
                    $data['reserved_by'] = $row['reserved_by'];
                    array_push($response['data'], $data);
                }
            }
            $response['reqmsg'] = "Data Retrieved!";
            $response['reqcode'] = "1";
        }

        else {
            $response['reqmsg'] = "Error Retrieving Data!";
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