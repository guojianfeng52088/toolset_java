import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @Description 发送邮件工具类
 * @Author pc
 * @Date 2019/10/03 14:06
 */
public class EmailUtil {

    //收件人邮箱地址，可以写多个
    private static String[] receiveUserMailaddress = {"address1", "address2", "address3"};

    private static String userName = "mailAddress"; //发件人的邮箱地址
    private static String password = "mailPassword"; //发件人邮箱密码
    private static String mailHost = "smtp.×××.com"; //smtp设置


    /**
     * @param title   邮件标题
     * @param content 邮件内容
     * @Description 发送邮件
     * @Author guojianfeng
     * @Date 2019/10/03 14:13
     * @Return
     */
    public boolean sendMail(String title, String content) {

        //配置对应参数
        Properties properties = new Properties();
        properties.put("mail.smtp.host", mailHost);
        properties.put("mail.smtp.auth", "true"); // 需要请求认证
        properties.put("mail.transport.protocol", "smtp");// 使用的协议（JavaMail规范要求）
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.checkserveridentity", "false");
        properties.put("mail.smtp.ssl.trust", mailHost);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", "false");
        properties.put("username", userName);
        properties.put("password", password);

        return sendMail(properties, title, content);
    }


    /**
     * @param properties 邮件属性
     * @param title      邮件标题
     * @param content    邮件内容
     * @Description 发送邮件private方法
     * @Author guojianfeng
     * @Date 2019/10/03 14:17
     * @Return
     */
    private boolean sendMail(Properties properties, String title, String content) {

        //获取session对象,发件人用户名密码验证
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        });

        try {
            //判断收件人地址是否存在
            int length = receiveUserMailaddress.length;
            if (length <= 0) {
                throw new AddressException("收件人地址不存在！");
            }
            Address[] addresses = new Address[length];
            for (int i = 0; i < addresses.length; i++) {
                addresses[i] = new InternetAddress(receiveUserMailaddress[i]);
            }

            //设置邮件格式
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(userName));
            message.addRecipients(Message.RecipientType.TO, addresses);
            message.setSubject(title);
            message.setContent(content, "text/html;charset=utf-8");

            //发送邮件
            Transport transport = session.getTransport("smtp");
            transport.connect(mailHost, userName, password);
            transport.sendMessage(message, message.getAllRecipients());
        } catch (AddressException e) {
            e.printStackTrace();
            return false;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}

