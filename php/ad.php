<?php
$servername = "localhost";
$username = "admin";
$password = "admin";
$dbname = "ad";
$id= $_GET['id'];
$count = $_GET['count'];
$price= $_GET['price'];
$name = $_GET['name'];
$address= $_GET['address'];
$phone = $_GET['phone'];
$note = $_GET['note'];
$mode = $_GET['mode'];

// Create connection
//https://localhost/ad.php?id=0&count=5&price=100&name=abc&address=asd&phone=123456789&note=123&mode=0
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}

$sql = "INSERT INTO `cart` (`prime`, `id`, `count`, `price`, `Name`, `Address`, `Phone`, `Note`, `mode`) VALUES (NULL, '".$id."', '".$count."', '".$price."', '".$name."', '".$address."', '".$phone."', ".$note.", '".$mode."'); ";


if ($conn->query($sql) === TRUE) {
  echo "1";
} else {
  echo "Error: " . $sql . "<br>" . $conn->error;
}

$conn->close();
?>