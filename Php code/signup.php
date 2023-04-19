<?php

if($_SERVER['REQUEST_METHOD']=='POST'){
    
    $email = $_POST['email'];
    $password = md5($_POST['password']);
    $username = $_POST['username'];
    $address=$_POST['address'];
    $age=$_POST['age'];
    
    require_once('db_config.php');
    
    $sql = "INSERT INTO `users` (`username`, `email`, `password`, `age`, `address`) VALUES ('$username', '$email', '$password', '$age', '$address');";
    
    if(mysqli_query($con, $sql)){
        $user_info = array(
            'username' => $username,
            'age' => $age,
            'email' => $email,
            'address' => $address
        );
        // Return user information
        echo "success";
    }else{
        echo "error";
    }
    
    // Close database connection
    mysqli_close($con);
}

?>