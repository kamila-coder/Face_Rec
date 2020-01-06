/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package face_rec;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {
    public void sendMail(String address, String subject, String message) 
	 {
             try
             {
		 String from = "idm.neliyamanagement@gmail.com";
	         String pass = "idm@neliya123";
		 
		 String[] to = {address};
		 String host = "smtp.gmail.com";
		 
		 Properties prop = System.getProperties();
		 prop.put("mail.smtp.starttis.enable", "true");
		 prop.put("mail.smtp.host", host);
		 prop.put("mail.smtp.user", from);
		 prop.put("mail.smtp.password", pass);
		 prop.put("mail.smtp.port", "25");
		 prop.put("mail.smtp.auth", "true");
		 prop.put("mail.smtp.socketFactory.port", "25"); 
                 prop.put("mail.smtp.socketFactory.fallback", "false"); 
                 prop.put("mail.smtp.starttls.enable", "true"); 
                 prop.put("mail.smtp.EnableSSL.enable","true");
                 
                prop.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");   
                prop.setProperty("mail.smtp.socketFactory.fallback", "false");   
                prop.setProperty("mail.smtp.port", "465");   
                prop.setProperty("mail.smtp.socketFactory.port", "465"); 
                 
                 SecurityManager security = System.getSecurityManager();
                 
		 //Session session = Session.getDefaultInstance(prop);
                 Session session = Session.getInstance(prop);
		 MimeMessage msg = new MimeMessage(session);
		 msg.setFrom(new InternetAddress(from));
		 InternetAddress[] toaddress = new InternetAddress[to.length];
		 
		 for(int i = 0; i< to.length; i++)
		 {
			 toaddress[i] = new InternetAddress(to[i]);	 
		 }
		 
		for(int i = 0; i< to.length; i++)
		 {
                         msg.setRecipient(Message.RecipientType.TO, toaddress[i]); 	 
		 }
	
            msg.setSubject(subject); 
            msg.setContent(message, "text/html");
        
		 Transport transport = session.getTransport("smtp");
		 transport.connect(host, from, pass);
		 transport.sendMessage(msg, msg.getAllRecipients());
		 transport.close();
             }catch (Exception ex)
             {
                 System.out.println(ex);
             }
             
	 }
}
