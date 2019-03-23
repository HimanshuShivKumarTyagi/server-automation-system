<%@ page import="java.io.*" %>
<%@ page import="java.io.File" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.io.IOException" %>
				
	<%
		String Machine_name = request.getParameter("name");
		if(Machine_name==null||Machine_name.length()==0)
		{
			out.println("Give The name of machine to be Powered On");
		}
		else
		{
			FileInputStream instream = null;
			FileOutputStream outstream = null;
        
            try
				{
					File infile = new File("F://connection.ps1");
					File outfile = new File("F://PowerOff.ps1");
            
					if(outfile.exists())
						{
							outfile.delete();
							outfile.createNewFile();
						}
					instream = new FileInputStream(infile);
					outstream = new FileOutputStream(outfile);
            
					byte[] buffer = new byte[1024];
					int length;
            
					while((length = instream.read(buffer))>0)
						{
							outstream.write(buffer,0,length);
						}
            
					FileWriter fw = new FileWriter("F://PowerOff.ps1",true);
					BufferedWriter bw = new BufferedWriter(fw);
					PrintWriter output = new PrintWriter(bw);
					output.println("Start-VM "+Machine_name);
					output.println("get-vm > F://getvm.text");
					output.close();
					instream.close();
					outstream.close();
					String command = "C:\\WINDOWS\\system32\\WindowsPowerShell\\v1.0\\powershell.exe  \"F:\\PowerOff.ps1\"";
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
					out.println("Standard Error:");
					BufferedReader stderr = new BufferedReader(new InputStreamReader(
					powershellprocess.getErrorStream()));
					while ((line = stderr.readLine()) != null) 
						{
							out.println(line);
						}
					stderr.close();
					out.println("Done");
				}
				catch(IOException ex)
				{
					out.println("Error "+ex);
				}
		}
	%>