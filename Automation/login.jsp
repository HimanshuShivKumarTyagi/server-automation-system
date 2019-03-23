<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
 <head>
  <meta name="viewport" content="width=device-width,initial-scale=1">
  <title>Student Enquiry System</title>
  		<link href="css/bootstrap.min.css" rel="stylesheet" >
		<!--custom style for this template-->
		<link href="css/style.css" rel="stylesheet" >
				<script src="js/jquery-3.3.1.js"></script>
		<script src="js/bootstrap.min.js"></script>
		<script type="text/javascript">
                    function validate()
                    {
                        var uname = document.myForm.username.value;
                        if(uname=="")
                        {
                            alert("Please Enter Admin Name");
                            document.myForm.username.focus();
                            return false;
                        }
                        var password = document.myForm.password.value;
                        if(password=="")
                        {
                         alert("Please Enter The Password");
                         
                            document.myForm.password.focus();
                            return false;
                        }
                        else if(password.length<4 || password.length>15)
                        {
                            alert("password should be in range between 7 and 15");
                        }
                    }
		</script>
 </head>

 <body>
  <nav class = "navbar navbar-expand-sm bg-primary navbar-dark">
			<div class= "container">
				<div class="navbar-header">
					<a class = "navbar-brand" href="#">Welcome To Virtualisation Automation</a>
				</div>
				<ul class="navbar-nav">
					
				</ul>
			</div>
		</nav>
		
	<div class="container">
		<div class="formsection">
			<h1 class="text-center">Login</h1>
			<form name="myForm" method="post" action="loginverify2.jsp">
				<div class="form-group">
					<label>Email Address*:</label>
					<input name="username" type="text" maxlength=50 placeholder="Enter userId" class="form-control">
				</div>
				<div class="form-group">
					<label>Password*:</label>
					<input name="password" type="password" placeholder="Enter Password" maxlength=20 class="form-control">
				</div>
                            <button type="submit" name="action" onClick="return validate();" value="signin" class="btn btn-primary">Login</button>
				<div class="form-group"><span id="message" class="error">Fields marked with * are mandatory.</span>
				</div>
			</form>
		</div>
	</div>
  <div class="footer">
			<div class="container">
			Design &amp; Developed by: <a href="https://www.facebook.com/him.tyagi.90">HSS foundation</a>
			</div>
  </div>
 </body>
</html>