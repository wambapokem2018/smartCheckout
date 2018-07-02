<?PHP 
    include_once("connection.php"); 

	if(isset($_POST['boxID'])){

			$boxID = $_POST['boxID'];
			
            $query = "SELECT *,COUNT(*) FROM tool_table ".
            " WHERE BoxID = '$boxID'";

            $result = mysqli_query($conn, $query);
			
			foreach ($_GET['boxID'] as $selectedOption){
			while($row = $result->fetch_assoc()) {
					$jsonObj = array(
					'ToolID' => $row["ToolID"],
					'BoxID' => $row["BoxID"],
					'Tool' => $row["Tool"],
					'Image' => base64_encode($row["Image"]),
					'Defect' => $row["Defect"]
			);
			echo json_encode($jsonObj);
			}}
				
            echo "Sucess";
	} else 
		echo "Fail!";
?>
<html>
<head><title>Login</title></head>
    <body>
        <h1>Login Example</h1>
        <form action="<?PHP $_PHP_SELF ?>" method="post">
            Boxname <input type="text" name="boxID" value="15" /><br/>
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