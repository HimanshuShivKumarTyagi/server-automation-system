<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
 <head>
  <meta name="viewport" content="width=device-width,initial-scale=1">
  <title>Automation</title>
  		<link href="css/bootstrap.min.css" rel="stylesheet" >
		<!--custom style for this template-->
		<link href="css/style.css" rel="stylesheet" >
				<script src="js/jquery-3.3.1.js"></script>
				<script src="js/bootstrap.min.js"></script>

 </head>

 <body>
  <nav class = "navbar navbar-expand-sm bg-primary navbar-dark">
			<div class= "container">
				<div class="navbar-header">
					<a class = "navbar-brand" href="#">Welcome To Virtualisation Automation</a>
				</div>
			</div>
		</nav>
		
	<div class="container">
		<div class="formsection">
						<%@ page import="java.io.*" %>
						<%@ page import="java.io.File" %>
						<%@ page import="java.io.BufferedReader" %>
						<%@ page import="java.io.InputStreamReader" %>
						<%@ page import="java.io.PrintWriter" %>
						<%@ page import="java.io.IOException" %>
						
						<%
							String ip = request.getParameter("ipaddress");
							String username = request.getParameter("username");
							String pwd = request.getParameter("password");
	
							try
								{
									String strPath = "F:\\connection.ps1";
									File strfile = new File(strPath);
									boolean filecreated = true;
									if(strfile.exists())
									{
										strfile.delete();
										strfile.createNewFile();
										filecreated = true;
									}
									if(filecreated)
									{
										PrintWriter pw = new PrintWriter(strfile);
										pw.println("Connect-ViServer -Server "+ip+" -User " +username+" -Password "+ pwd);
										pw.println("timeout 1");
										pw.println("get-vm > F://getvm.text");
										pw.close();
										String command = "C:\\WINDOWS\\system32\\WindowsPowerShell\\v1.0\\powershell.exe  \"F:\\connection.ps1\"";
										Process powershellprocess = Runtime.getRuntime().exec(command);
										powershellprocess.getOutputStream().close();
										String line;
										out.println("Standard Output:");
										BufferedReader stdout = new BufferedReader(new InputStreamReader(powershellprocess.getInputStream()));
										while ((line = stdout.readLine()) != null)
											{
														out.println(line);
											}
										stdout.close();
										out.println("Done");
						%>
										<h1>Read Connection Status</h1>
										<a href = "Automation.jsp">Go To Controller Page</a> 
						<%				
									}else
									{
										out.println("Connection can not be Created");
									}
								}
							catch(IOException ex)
								{
									out.println("Connection can not be created" + ex);     
								}
						%>
	</div>
  <div class="footer">
			<div class="container">
			Design &amp; Developed by: <a href="https://www.facebook.com/him.tyagi.90">HimanshuVivek</a>
			</div>
  </div>
 </body>
</html>