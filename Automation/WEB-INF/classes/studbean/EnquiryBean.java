package studbean;
import java.io.Serializable;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class EnquiryBean implements Serializable
{
    int id;
    String name,contact,email,comment;
    int courses[];
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
    
    public String getName()
    {
      return name;
    }
    
    public void setName(String name)
    {
      this.name=name;
    }
    
    public String getContact()
    {
      return contact;
    }
    
    public void setContact(String contact)
    {
       this.contact=contact;
    }
    public String getEmail()
    {
      return email;
    }
    
    public void setEmail(String email)
    {
      this.email=email;
    }
    
     public int[] getCourses()
    {
      return courses;
    }
    
    public void setCourses(int[] value)
    {
      courses=value;
    }
    
    public int getCourses(int index)
    {
        if(index>=0 && index < courses.length)
        {
            return courses[index];
        }
        return -1;
    }
    
    public void setCourses(int index, int value)
    {
            if(index>=0 && index < courses.length)
            {
                courses[index]=value;
            }
    }
    
    public String getComment()
    {
      return comment;
    }
    
    public void setComment(String comment)
    {
        this.comment = comment;
    }
    
    public String getMessage()
    {
        return message;
    }
    
    public boolean validate()
    {
        message="";
        boolean state=true;

        if(name==null || name.trim().length()==0)
	{
		message = message+ "<br>Name can not be blank!";
		state=false;
	}
		
	if(name!=null && name.trim().length()>0)
	{
		
		if(!isAlpha(name))
		{
			message = message+ "<br>Name can not have numbers";
			state=false;
		}
	}
	
	
	if(contact==null || contact.trim().length()==0)
	{
		message = message+ "<br>Contact can not be blank!";
		state=false;
	}
	
	
	if(contact!=null && contact.trim().length()>0)
	{
		
		if(!isDigit(contact))
		{
			message = message+ "<br>Contact can not have Alphabets";
			state=false;
		}
	}
	
	if(email==null || email.trim().length()==0)
	{
		message = message +"<br>email can not be blank!";
		state=false;
	}
	
	
	if(email!=null && email.trim().length()>0)
	{
		
		if(!isEmail(email))
		{
			message = message+ "<br>Enter the valid email address";
			state=false;
		}
	}
	
	if(comment==null && comment.trim().length()==0)
	{
		
			message = message+ "<br>Please ask your Query";
			state=false;
		
	}
        
        return state;
    }
    
    public boolean save()
    {
        boolean state=true;
        
        try
        {
            Connection con = DriverManager.getConnection("jdbc:mysql://youtube.chzwitk6faqq.us-east-2.rds.amazonaws.com/studappdb","root","hadoop991998");
         			 
	   
		String sql="insert into enquiry(stdname,contactno,email,comment) values(?,?,?,?)";
                PreparedStatement ps = con.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
		 ps.setString(1,name);
		 ps.setString(2,contact);
		 ps.setString(3,email);
		 ps.setString(4,comment);
		 int n=ps.executeUpdate();
                 ResultSet rs = ps.getGeneratedKeys();
                 int prim_Key =1;
                 if(rs!=null && rs.next())
                 {
                      prim_Key = rs.getInt(1);
                 }
                 
                 sql="insert into enquiry_course(enqid,courseid) values(?,?)";
                 ps = con.prepareStatement(sql);
                 for(int cid:courses)
                 {
                     ps.setInt(1, prim_Key);
                     ps.setInt(2, cid);
                     ps.executeUpdate();
                     ps.clearParameters();
                 }
                 message = "Hello "+name+" Thanks for taking intrest in our courses. Some one will contact you shortly.";
            con.close();
        }
        catch(SQLException ex)
        {
            state = false;
            message = "Error in database operation: "+ex;
        }
        return state;
                
    }
    public List<EnquiryBean> getEnquiries()
    {
       List<EnquiryBean> list = new ArrayList<EnquiryBean>();
       
       try
        {
            Connection con = DriverManager.getConnection("jdbc:mysql://youtube.chzwitk6faqq.us-east-2.rds.amazonaws.com/studappdb","root","hadoop991998");
            String sql = "select * from enquiry order by stdname";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            String sql1 = "select courseid from enquiry_course where enqid = ?";
            PreparedStatement ps1 = con.prepareStatement(sql1);
            while(rs.next())
            {
                EnquiryBean eb = new EnquiryBean();
                eb.setId(rs.getInt(1));
                eb.setName(rs.getString(2));
                eb.setContact(rs.getString(3));
                eb.setEmail(rs.getString(4));
                eb.setComment(rs.getString(5));
                ps1.setInt(1, eb.getId());
                ResultSet rs1 = ps1.executeQuery();
                List<Integer> ls = new ArrayList<Integer>();
                while(rs1.next())
                {
                  ls.add(rs1.getInt(1));
                }
                rs1.close();
                ps1.clearParameters();
                int crs[] = new int[ls.size()];
                for(int i=0; i<ls.size(); i++)
                {
                  crs[i]=ls.get(i);
                }
                eb.setCourses(crs);
                list.add(eb);
                
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
        public EnquiryBean getEnquiry(int id)
        {
          EnquiryBean eb = null;
          try
          {
            Connection con = DriverManager.getConnection("jdbc:mysql://youtube.chzwitk6faqq.us-east-2.rds.amazonaws.com/studappdb","root","hadoop991998");
            String sql = "select * from enquiry where id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            String sql1 = "select courseid from enquiry_course where enqid = ?";
            PreparedStatement ps1 = con.prepareStatement(sql1);
            if(rs.next())
            {
                eb = new EnquiryBean();
                eb.setId(rs.getInt(1));
                eb.setName(rs.getString(2));
                eb.setContact(rs.getString(3));
                eb.setEmail(rs.getString(4));
                eb.setComment(rs.getString(5));
                ps1.setInt(1, eb.getId());
                ResultSet rs1 = ps1.executeQuery();
                List<Integer> ls = new ArrayList<Integer>();
                while(rs1.next())
                {
                    ls.add(rs1.getInt(1));
                }
                rs1.close();
                ps1.clearParameters();
                int crs[] = new int[ls.size()];
                for(int i =0; i<ls.size();i++)
                {
                    crs[i] = ls.get(i);
                }
                eb.setCourses(crs);
            }
            rs.close();
            con.close();
          }
          catch(SQLException ex)
          {
            message = "Error occured in database operation "+ex;
          }
          
          return eb;
        }
        
}

