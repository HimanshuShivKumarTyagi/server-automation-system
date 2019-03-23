package studbean;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseBean implements java.io.Serializable
{
    int id;
    String coursename,duration,imagename;
    String description,status="Active";
    byte[] contents;
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
    
    public int getId()
    {
      return id;
    }
    
    public void setId(int id)
    {
      this.id = id;
    }
    
    public String getCoursename()
    {
      return coursename;
    }
    
    public void setCoursename(String coursename)
    {
      this.coursename=coursename;
    }
    
    public String getDuration()
    {
      return duration;
    }
    
    public void setDuration(String duration)
    {
       this.duration=duration;
    }
    public String getImagename()
    {
      return imagename;
    }
    
    public void setImagename(String imagename)
    {
      this.imagename=imagename;
    }
    
     public String getDescription()
    {
      return description;
    }
    
    public void setDescription(String description)
    {
      this.description=description;
    }
    
     public String getStatus()
    {
      return status;
    }
    
    public void setStatus(String status)
    {
      this.status=status;
    }
     public byte[] getContents()
    {
      return contents;
    }
    
    public void setContents(byte[] contents)
    {
      this.contents=contents;
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

        if(coursename==null || coursename.trim().length()==0)
	{
		message = message+ "<br>CourseName can not be blank!";
		state=false;
	}    
        if(duration==null || duration.trim().length()==0)
	{
		message = message+ "<br>Duration can not be blank!";
		state=false;
	}
        
        if(description==null || description.trim().length()==0)
	{
		message = message+ "<br>Description can not be blank!";
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
            String sql = "select id from courses where coursename=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,coursename);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
				message = message+ " Given Course name is already exist!";
				state = false;
			}
	    rs.close();
				 
	    			 
	    if(state==true)
	    {
		 sql="insert into courses(coursename,duration,imagename,description,contents,status) values(?,?,?,?,?,?)";
		 ps = con.prepareStatement(sql);
		 ps.setString(1,coursename);
		 ps.setString(2,duration);
		 ps.setString(3,imagename);
		 ps.setString(4,description);
                 Blob blob = con.createBlob();
		 blob.setBytes(1, contents);
                 ps.setBlob(5, blob);
		 ps.setString(6,status);
		 int n=ps.executeUpdate();
         message = coursename+ "<br>Saved successfully</br>";
            }
            con.close();
        }
        catch(SQLException ex)
        {
            state=false;
            message = "Error in database operation: "+ex;
        }
        return state;
    }
    public List<CourseBean> getCourses()
    {
       List<CourseBean> list = new ArrayList<CourseBean>();
       
       try
        {
            Connection con = DriverManager.getConnection("jdbc:mysql://youtube.chzwitk6faqq.us-east-2.rds.amazonaws.com/studappdb","root","hadoop991998");
            String sql = "select * from courses order by coursename;";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                CourseBean sb = new CourseBean();
                sb.setId(rs.getInt("id"));
                sb.setCoursename(rs.getString(2));
                sb.setDuration(rs.getString(3));
                sb.setImagename(rs.getString(4));
                sb.setDescription(rs.getString(5));
                Blob blob = rs.getBlob(6);
                byte[] data = blob.getBytes(1,(int)blob.length());
                sb.setContents(data);
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
}
