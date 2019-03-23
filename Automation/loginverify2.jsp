<%@ page import="java.sql.*" %>

<%
	String username = request.getParameter("username");
	String pwd = request.getParameter("password");
	
	  try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost/studappdb","root","Himanshu@1998");
                String sql="select * from students where email=? and passwd=?";
                PreparedStatement ps=con.prepareStatement(sql);
                ps.setString(1,username);
                ps.setString(2,pwd);
                ResultSet rs = ps.executeQuery();
                if(rs.next())
                    {
								response.sendRedirect("new.html");
					}
                else
                {
                    out.println("error");
                    
                }
                rs.close();
                con.close();
            }
           catch(SQLException ex)
             {
                out.println("Error in database operation");     
             }
	
        

%>