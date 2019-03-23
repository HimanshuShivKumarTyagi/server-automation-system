<!DOCTYPE html>
<html lang="en">
 <head>
  <meta name="viewport" content="width=device-width,initial-scale=1">
  <title>Automation</title>
  		<link href="css/bootstrap.min.css" rel="stylesheet" >
		<link href="css/style.css" rel="stylesheet" >
		<script src="js/jquery-3.3.1.js"></script>
		<script src="js/bootstrap.min.js"></script>
		<script type="text/javascript">
				$(document).ready(function(){
						$("#btnshutdown").click(function(event){
							var machine_name = $('#poweroffname').val().trim();
							if(machine_name.length==0)
							{
								$('#message').html("Enter the Name of machine to be Powered off");
								return;
							}
							$.ajax({
								url:"poweroff.jsp",
								type:"Post",
								data:{name:machine_name},
								success:function(data){
									$('#comment').html(data);
									$('#poweroffname').val("");
								},
								error:function(){
									$('#message').html("An error occured in request");
								}
							});
						});
						
						$("#btnpoweron").click(function(event){
							var machine_name = $('#poweronname').val().trim();
							if(machine_name.length==0)
							{
								$('#message').html("Enter the Name of machine to be Powered on");
								return;
							}
							$.ajax({
								url:"poweron.jsp",
								type:"Post",
								data:{name:machine_name},
								success:function(data){
									$('#comment').html(data);
									$('#poweronname').val("");
								},
								error:function(){
									$('#message').html("An error occured in request");
								}
							});
						});
				});

		 
		</script>
 </head>

 <body>
    <nav class = "navbar navbar-expand-sm bg-primary navbar-dark">
			<div class= "container">
				<div class="navbar-header">
					<a class = "navbar-brand" href="#">Welcome To Virtualisation Automation</a>
				</div>
			
	</nav>
		
	<div class="container">
		<div class="formsection">
	<h1 class="text-center">Connect To Esxi Server</h1>
			<form name="myForm" >
			<div class="form-group">
					<label>Click The Link To Get VM Status*:</label><br>
					<a href="Status.jsp">Get Status</a>
				</div>
				<div class="form-group">
					<label>Name of Machine to be ShutDown*:</label>
					<input id="poweroffname" type="text" placeholder="Enter name" maxlength=20 class="form-control"><br>
					<input type="button" id="btnshutdown" value="ShutDown" class="btn btn-primary">
				</div>
				<div class="form-group">
					<label>Name of Machine to be Powered-on*:</label>
					<input id="poweronname" type="text" placeholder="Enter name" maxlength=20 class="form-control"><br>
					<input type="button" id="btnpoweron" value="Power-on" class="btn btn-primary">
				</div>
				<div class="form-group">
					<label>Result*:</label><br>
					<textarea id="comment" name="comment" maxlength=500 rows=20 cols=30 class="form-control"></textarea><br>
					<input type="button" id="btnstatus" value="Get Status of Machines" class="btn btn-primary">
				</div>
				<div class="form-group"><span id="message" class="error">Fields marked with * are mandatory.</span>
				</div>
			</form>
		</div>
	</div>
  <div class="footer">
			<div class="container">
			Design &amp; Developed by: <a href="https://www.facebook.com/him.tyagi.90">HimanshuVivek</a>
			</div>
  </div>
 </body>
</html>