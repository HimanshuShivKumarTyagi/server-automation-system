package studbean;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
        
public class MailBean 
{
    private final String username="ht073191@gmail.com";
    private final String password="iloveshubbu";
    private String subject,message,to;
    private String error="";
    
    public void setSubject(String subject)
    {
        this.subject= subject;
    }
    
    public void setMessage(String message)
    {
        this.message=message;
    }
    
    public void setTo(String to)
    {
        this.to=to;
    }
    
    public String getError()
    {
        return error;
    }
    
    public boolean sendMail()
    {
        boolean state = false;
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port","587");
        try
        {
            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            
                protected PasswordAuthentication getPasswordAuthentication()
                {
                    return new PasswordAuthentication(username,password);
                }
            });
            
            Message mes = new MimeMessage(session);
            mes.setFrom(new InternetAddress(username));
            mes.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            mes.setSubject(subject);
            mes.setText(message);
            Transport.send(mes);
            state = true;
            }catch(MessagingException e)
            {
                error = e.getMessage();
                e.printStackTrace();
            }
        return state;
    }
}
