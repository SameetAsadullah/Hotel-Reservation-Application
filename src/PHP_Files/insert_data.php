<?php
    include "conn.php";

    $response = array();
    
    if (isset($_POST["tableName"])) {
        $tableName = $_POST["tableName"];

        if ($tableName == "customers" || $tableName == "vendors") {
            if (isset($_POST["name"], $_POST["email"], $_POST["password"], $_POST["phoneno"], $_POST["cnic"], $_POST["accountno"], $_POST["address"], $_POST["dp"])) {
                $name = $_POST["name"];
                $email = $_POST["email"];
                $password = $_POST["password"];
                $phoneno = $_POST["phoneno"];
                $cnic = $_POST["cnic"];
                $accountno = $_POST["accountno"];
                $address = $_POST["address"];
                $dp = $_POST["dp"];

                $query = "INSERT INTO `$tableName`(`name`, `email`, `password`, `phoneno`, `cnic`, `accountno`, `address`, `dp`) VALUES 
                            ('$name','$email','$password','$phoneno','$cnic','$accountno','$address','$dp')";
            }
            else {
                $response['id'] = "NA";
                $response['reqmsg'] = "Incomplete Request";
                $response['reqcode'] = "0";
            }
        }

        else if ($tableName == "hotels") {
            if (isset($_POST["name"], $_POST["address"], $_POST["location"], $_POST["single_rooms"], $_POST["double_rooms"], $_POST["single_room_price"], $_POST["double_room_price"], $_POST["registered_by"])) {
                $name = $_POST["name"];
                $address = $_POST["address"];
                $location = $_POST["location"];
                $single_rooms = $_POST["single_rooms"];
                $double_rooms = $_POST["double_rooms"];
                $single_room_price = $_POST["single_room_price"];
                $double_room_price = $_POST["double_room_price"];
                $registered_by = $_POST["registered_by"];
        
                $query = "INSERT INTO `hotels`(`name`, `address`, `location`, `single_rooms`, `double_rooms`, `single_room_price`, `double_room_price`, `registered_by`) VALUES
                            ('$name','$address','$location','$single_rooms','$double_rooms','$single_room_price','$double_room_price', '$registered_by')";
            }
            else {
                $response['id'] = "NA";
                $response['reqmsg'] = "Incomplete Request";
                $response['reqcode'] = "0";
            }
        }
        else if ($tableName == "rooms") {
            if (isset($_POST["hotel_id"], $_POST["roomno"], $_POST["type"], $_POST["available_date"], $_POST["is_available"])) {
                $hotel_id = $_POST["hotel_id"];
                $roomno = $_POST["roomno"];
                $type = $_POST["type"];
                $available_date = $_POST["available_date"];
                $is_available = $_POST["is_available"];

                $query = "INSERT INTO `rooms`(`hotel_id`, `roomno`, `type`, `available_date`, `is_available`) VALUES 
                            ('$hotel_id','$roomno','$type','$available_date','$is_available')";
            }
            else {
                $response['id'] = "NA";
                $response['reqmsg'] = "Incomplete Request";
                $response['reqcode'] = "0";
            }
        }
        else if ($tableName == "reservations") {
            if (isset($_POST["hotel_name"], $_POST["hotel_location"], $_POST["total_rooms"], $_POST["room_numbers"], $_POST["total_price"], $_POST["check_in_date"], $_POST["check_out_date"], $_POST["reserved_by"])) {
                $hotel_name = $_POST["hotel_name"];
                $hotel_location = $_POST["hotel_location"];
                $total_rooms = $_POST["total_rooms"];
                $room_numbers = $_POST["room_numbers"];
                $total_price = $_POST["total_price"];
                $check_in_date = $_POST["check_in_date"];
                $check_out_date = $_POST["check_out_date"];
                $reserved_by = $_POST["reserved_by"];

                $query = "INSERT INTO `reservations`(`hotel_name`, `hotel_location`, `total_rooms`, `room_numbers`, `total_price`, `check_in_date`, `check_out_date`, `reserved_by`) 
                            VALUES ('$hotel_name','$hotel_location','$total_rooms','$room_numbers','$total_price','$check_in_date','$check_out_date','$reserved_by')";
            }
            else {
                $response['id'] = "NA";
                $response['reqmsg'] = "Incomplete Request";
                $response['reqcode'] = "0";
            }
        }

        $res = mysqli_query($con, $query);

        if ($res) {
            $response['id'] = mysqli_insert_id($con);
            $response['reqmsg'] = "Data Inserted!";
            $response['reqcode'] = "1";
        }
        else {
            $response['id'] = "NA";
            $response['reqmsg'] = "Error Inserting Data!";
            $response['reqcode'] = "0";
        }
    }
    else {
        $response['id'] = "NA";
        $response['reqmsg'] = "Incomplete Request";
        $response['reqcode'] = "0";
    }

    $x = json_encode($response);
    echo $x;
?>