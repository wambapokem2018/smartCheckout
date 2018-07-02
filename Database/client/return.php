<?PHP 
    include_once("connection.php"); 

	if(isset($_POST['userID']) && isset($_POST['boxID'])){
			$userID = $_POST['userID'];
			$boxID = $_POST['boxID'];
			$returnTime = date("Y-m-d H:i:s");
			
            $query = "UPDATE rent_table SET ReturnTime='$returnTime' WHERE UserID='$userID' AND BoxID='$boxID'";

            $result = mysqli_query($conn, $query);
				
            echo "Sucess";
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