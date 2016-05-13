package service;

import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import po.EmailAutherticator;
import po.EmailData;

/**
 *
 * @author Administrator
 */
public class PostOffice {

    private Properties props = new Properties();
    private String host = "";
    private String userName = "";
    private String password;

    public PostOffice() {
    }

    public PostOffice(String host, String userName, String password) {
        this.host = host;
        this.userName = userName;
        this.password = password;
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");

    }

    public Boolean sendMail(EmailData email) {
        try {
            EmailAutherticator auth = new EmailAutherticator(userName, password);
            Session session = Session.getDefaultInstance(props, auth);
            MimeMessage message = new MimeMessage(session);
            message.setSubject(MimeUtility.encodeText(new String(email.getSubject().getBytes(), "GB2312"), "GB2312", "B")); // 设置邮件主题
            if (email.getContentType() == null || email.getContentType().equals("text")) {
                message.setText(email.getContent());
            } else if (email.getContentType().equals("html")) {
                //给消息对象设置内容
           /*     BodyPart bodyPart = new MimeBodyPart(); //新建一个存放信件内容的BodyPart对象
                 bodyPart.setContent(email.getContent(), "text/html;charset=gb2312");//给BodyPart对象设置内容和格式/编码方式
                 Multipart mmp = new MimeMultipart();//新建一个MimeMultipart对象用来存放BodyPart对象(事实上可以存放多个)
                 //设置邮件附件
                 BodyPart bodyPart2 = new MimeBodyPart();
                 FileDataSource fileDataSource = new FileDataSource(fileName);
                 bodyPart2.setDataHandler(new DataHandler(fileDataSource));
                 bodyPart2.setFileName("=?GB2312?B?" + enc.encode(fileName.getBytes()) + "?=");


                 Multipart multipart = new MimeMultipart();
                 multipart.addBodyPart(bodyPart);
                 multipart.addBodyPart(bodyPart2);

                 mmp.addBodyPart(mdp);//将BodyPart加入到MimeMultipart对象中(可以加入多个BodyPart)
                 msg.setContent(mmp);//把mm作为消息对象的内容*/
            }  // 设置邮件正文
            message.setHeader("test", "test"); // 设置邮件标题
            message.setSentDate(new Date()); // 设置邮件发送日期
            Address address = new InternetAddress(email.getFrom(), "郑栋");
            message.setFrom(address); // 设置邮件发送者的地址
            for (int i = 0; i < email.getRecipients().length; i++) {
                Address toAddress = new InternetAddress(email.getRecipients()[i]); // 设置邮件接收方的地址
                message.addRecipient(Message.RecipientType.TO, toAddress);
            }
            
            Transport.send(message); // 发送邮件
       //     System.out.println("send ok!");

        } catch (Exception ex) {
            Logger.getLogger(PostOffice.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
}