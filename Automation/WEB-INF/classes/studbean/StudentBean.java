package studbean;
import java.io.Serializable;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class StudentBean implements java.io.Serializable
{
    int id;
    String username,usergender,add1,add2;
    String cityname,contactno,emailid,password;
    String message="";
    static 
    {
      try
		{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver successfully loaded");
		}
		catch(ClassNotFoundException ex)
		{
			System.out.println("Problem loading driver: "+ex);
		}
    }
    
    public boolean isDigit(String text)
    {
	return text.matches("[0-9]+"); //allows digits
    }

    public boolean isAlpha(String text)
    {
	return text.matches("[a-zA-Z]+");
    }
		
    public boolean isEmail(String text)		
    {
        return text.matches("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}");
    }
    
    public int getId()
    {
      return id;
    }
    
    public void setId(int id)
    {
      this.id = id;
    }
    
    public String getUsername()
    {
      return username;
    }
    
    public void setUsername(String username)
    {
      this.username=username;
    }
    
    public String getUsergender()
    {
      return usergender;
    }
    
    public void setUsergender(String usergender)
    {
       this.usergender=usergender;
    }
    public String getAdd1()
    {
      return add1;
    }
    
    public void setAdd1(String add1)
    {
      this.add1=add1;
    }
    
     public String getAdd2()
    {
      return add2;
    }
    
    public void setAdd2(String add2)
    {
      this.add2=add2;
    }
    
     public String getCityname()
    {
      return cityname;
    }
    
    public void setCityname(String cityname)
    {
      this.cityname=cityname;
    }
     public String getContactno()
    {
      return contactno;
    }
    
    public void setContactno(String contactno)
    {
      this.contactno=contactno;
    }
    
     public String getEmailid()
    {
      return emailid;
    }
    
    public void setEmailid(String emailid)
    {
      this.emailid=emailid;
    }
    
     public String getPassword()
    {
      return password;
    }
    
    public void setPassword(String password)
    {
      this.password=password;
    }
    
    public String getMessage()
    {
      return message;
    }
    
    public void setMessage(String message)
    {
      this.message=message;
    }
    
    public boolean validate()
    {
        message="";
        boolean state=true;

        if(username==null || username.trim().length()==0)
	{
		message = message+ "<br>Name can not be blank!";
		state=false;
	}
		
	if(username!=null && username.trim().length()>0)
	{
		
		if(!isAlpha(username))
		{
			message = message+ "<br>Name can not have numbers";
			state=false;
		}
	}
	
	
	if(cityname==null || cityname.trim().length()==0)
	{
		message = message+ "<br>City can not be blank!";
		state=false;
	}
	
	
	if(cityname!=null && cityname.trim().length()>0)
	{
		
		if(!isAlpha(cityname))
		{
			message = message+ "<br>city can not have numbers";
			state=false;
		}
	}
	
	if(contactno==null || contactno.trim().length()==0)
	{
		message = message+ "<br>Contact can not be blank!";
		state=false;
	}
	
	
	if(contactno!=null && contactno.trim().length()>0)
	{
		
		if(!isDigit(contactno))
		{
			message = message+ "<br>Contact number can only have numbers";
			state=false;
		}
                
	}
	
	if(emailid==null || emailid.trim().length()==0)
	{
		message = message +"<br>email can not be blank!";
		state=false;
	}
	
	
	if(emailid!=null && emailid.trim().length()>0)
	{
		
		if(!isEmail(emailid))
		{
			message = message+ "<br>enter the valid email address";
			state=false;
		}
	}
	
	if(password==null || password.trim().length()==0)
	{
		message = message+ "<br>Password can not be blank!";
		state=false;
	}
	
	
	if(password!=null && password.trim().length()>0)
	{
		
		if(password.length()<5)
		{
			message = message+ "<br>Password must have atleast 5 characters";
			state=false;
		}
	}
        
        return state;
    }
    
    public int save()
    {
        int saveState=-1;
        boolean state=true;
        
        try
        {
            Connection con = DriverManager.getConnection("jdbc:mysql://youtube.chzwitk6faqq.us-east-2.rds.amazonaws.com/studappdb","root","hadoop991998");
            String sql = "select id from students where contactno=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,contactno);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
		 message = message+ " Given number is already exist!";
		 state = false;
	    }
	    rs.close();
				 
	    sql = "select id from students where email=?";	
            ps = con.prepareStatement(sql);
	    ps.setString(1,emailid);
	    rs = ps.executeQuery();
            if(rs.next())
	    {
                message = message+" Given email is already exist!";
		state = false;
	    }
	    rs.close();
				 
	    if(state==true)
	    {
		 sql="insert into students(stdname,gender,address1,address2,city,contactno,email,passwd) values(?,?,?,?,?,?,?,?)";
		 ps = con.prepareStatement(sql);
		 ps.setString(1,username);
		 ps.setString(2,usergender);
		 ps.setString(3,add1);
		 ps.setString(4,add2);
		 ps.setString(5,cityname);
		 ps.setString(6,contactno);
		 ps.setString(7,emailid);
		 ps.setString(8,password);
		 int n=ps.executeUpdate();
                 message = username+" <br>registered successfully</br>";
                 saveState=1;
            }
            con.close();
        }
        catch(SQLException ex)
        {
            message = "Error in database operation: "+ex;
        }
        return saveState;
                
    }
	public List<StudentBean> getStudents()
    {
       List<StudentBean> list = new ArrayList<StudentBean>();
       
       try
        {
            Connection con = DriverManager.getConnection("jdbc:mysql://youtube.chzwitk6faqq.us-east-2.rds.amazonaws.com/studappdb","root","hadoop991998");
            String sql = "select * from students order by stdname";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                StudentBean sb = new StudentBean();
                sb.setId(rs.getInt(1));
                sb.setUsername(rs.getString(2));
                sb.setUsergender(rs.getString(3));
                sb.setAdd1(rs.getString(4));
                sb.setAdd2(rs.getString(5));
                sb.setCityname(rs.getString(6));
                sb.setContactno(rs.getString(7));
                sb.setEmailid(rs.getString(8));
                list.add(sb);
                
            }
            rs.close();
            con.close();
        }
       catch(SQLException ex)
        {
           message = "Error in database operation: "+ex; 
        }
       return list;
    }
	
	   public StudentBean getStudent(int id)
	   {
        StudentBean sb=null;
            try{
                Connection con = DriverManager.getConnection("jdbc:mysql://youtube.chzwitk6faqq.us-east-2.rds.amazonaws.com/studappdb","root","hadoop991998");
                String sql="select * from students where id=?";
                PreparedStatement ps=con.prepareStatement(sql);
                ps.setInt(1,id);
                ResultSet rs = ps.executeQuery();
                if(rs.next())
                    {
                        sb=new StudentBean();
                        sb.setId(rs.getInt(1));
                        sb.setUsername(rs.getString(2));
                        sb.setUsergender(rs.getString(3));
                        sb.setAdd1(rs.getString(4));
                        sb.setAdd2(rs.getString(5));
                        sb.setCityname(rs.getString(6));
                        sb.setContactno(rs.getString(7));
                        sb.setEmailid(rs.getString(8));
                    }
                rs.close();
                con.close();
            }
           catch(SQLException ex)
             {
                message="error in database operation:"+ex;
             }
		return sb;
	   }
	
	
	
}
