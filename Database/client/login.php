<?PHP 
    include_once("connection.php"); 
	if( isset($_POST['txtBoxname']) ) {
            $boxname = $_POST['txtBoxname'];


            $query = "SELECT * FROM box_table ".
            " WHERE BoxID = '$boxname' ";

            $result = mysqli_query($conn, $query);
			
			/*
			while($row = $result->fetch_assoc()) {
					$jsonObj = array(
					'BoxID' => $row["BoxID"],
					'BoxName' => $row["BoxName"],
					'BoxImage' => base64_encode($row["BoxImage"])
			);
			}
			echo json_encode($jsonObj); */
            
				
                if(isset($_POST['mobile']) && $_POST['mobile'] == "android"){
                    
					while($row = $result->fetch_assoc()) {
					$jsonObj = array(
					'BoxID' => $row["BoxID"],
					'BoxName' => $row["BoxName"],
					'BoxImage' => base64_encode($row["BoxImage"])
			);
			}
			echo json_encode($jsonObj);
                    exit;
                }

            } else{
                echo "Login Failed <br/>";
            }
?>
<html>
<head><title>Login</title></head>
    <body>
        <h1>Login Example</h1>
        <form action="<?PHP $_PHP_SELF ?>" method="post">
            Boxname <input type="text" name="txtBoxname" value="" /><br/>
            <input type="submit" name="btnLogin" value="Login"/>
        </form>
    </body>
</html>

<?PHP 
/*
        if( isset($_POST['txtUsername']) ) {
            $username = $_POST['txtUsername'];


            $query = "SELECT Name FROM user_table ".
            " WHERE UserID = '$username' ";

            $result = mysqli_query($conn, $query);

            if($result->num_rows > 0){
                if(isset($_POST['mobile']) && $_POST['mobile'] == "android"){
                    echo "success";
                    exit;
                }

            } else{
                echo "Login Failed <br/>";
            }
        }*/
?>