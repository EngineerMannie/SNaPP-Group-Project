<html lang="eng">
	<head>
		<meta charset="utf-8">
		<title>LogIn</title>
	</head>
	<body>
		<?php
			echo "Test<br />";
			$con = mysqli_connect("lochnagar.abertay.ac.uk","sql1304883","lVYCiEHZ","sql1304883"); 
			// Check connection
			if (mysqli_connect_errno()) {
				echo "Failed to connect to MySQL: " . mysqli_connect_error(); 
			}
			else {
				echo "Connected to MySQL<br />";
				// Verification of credentials
				$req = $bdd->prepare('SELECT id FROM membre WHERE login = :pseudo AND pass = :pass');
				$req->execute(array('pseudo' => $_POST['log'], 'pass' => $_POST['pwd']));
				//sha1($_POST['pwd']);
				//echo "Test 2<br />"; 
				$resultat = $req->fetch();
				if (!$resultat)
				{
				    echo "Wrong username or password!<br />";
				}
				else
				{
				    session_start();
				    $_SESSION['id'] = $resultat['id'];
				    $_SESSION['pseudo'] = $pseudo;
				    echo "You are connected!<br />";
					// on redirige notre visiteur vers une page de notre section membre
					header ('location: home.php');
				}
				//echo "Test 3<br />"; 
			}
			mysqli_close($con);	
		?>
	</body>
</html>