<?php
    $con = mysqli_connect("localhost", "root", "") or 
    die ("i cannot connect to the database".mysql_error());

    mysqli_select_db($con, "hotel_reservation_application");
    mysqli_query($con, "SET NAMES 'utf8'");
?>