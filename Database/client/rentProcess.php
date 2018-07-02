<?PHP 
    include_once("connection.php"); 

	if(isset($_POST['userID']) && isset($_POST['boxID'])){
		
			$userID = $_POST['userID'];
			$boxID = $_POST['boxID'];
			$returnTime = NULL;
			
            $query = "SELECT * FROM rent_table ".
            " WHERE UserID = '$userID' AND BoxID = '$boxID' AND ReturnTime IS NULL";

            $result = mysqli_query($conn, $query);
			
			while($row = $result->fetch_assoc()) {
					$jsonObj = array(
					'UserID' => $row["UserID"],
					'BoxID' => $row["BoxID"],
					'BorrowTime' => $row["BorrowTime"],
					'ReturnTime' => $row["ReturnTime"]
			);
			echo json_encode($jsonObj);
			}
	} else 
		echo "Fail!";
?>
<html>
<head><title>Login</title></head>
    <body>
        <h1>Login Example</h1>
        <form action="<?PHP $_PHP_SELF ?>" method="post">
            Boxname <input type="text" name="userID" value="15" /><br/>
            <input type="submit" name="btnLogin" value="Login"/>
        </form>
    </body>
</html>

<?PHP
/*
$userID = 13;//$_POST['userID'];
			$boxID = "Test";//$_POST['boxID'];
			$borrowTime = date('Y-m-d');//$_POST['borrowTime'];
			$returnTime = NULL;//$_POST['returnTime'];
			$estimated = 2;//$_POST['estimated'];
			*/
			?>