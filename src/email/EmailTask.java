package email;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @Description 增加多线程发送邮件机制
 * @Author pc
 * @Date 2019/10/03 14:45
 */
public class EmailTask implements Runnable {

    //这里不能用lombok插件的log，不知道为什么，只好用这个log了
    Logger logger = Logger.getLogger(EmailTask.class.getName());

    private String mailSubject;
    private String mailBody;

    public EmailTask(String mailSubject, String mailBody) {
        this.mailSubject = mailSubject;
        this.mailBody = mailBody;
    }

    @Override
    public void run() {
        logger.info("[EmailTask] 开始发送邮件------->");
        synchronized (this) {
            EmailUtil emailUtil = new EmailUtil();
            try {
                boolean result = emailUtil.sendMail(mailSubject, mailBody);
                if (result) {
                    logger.info("[EmailTask] 发送邮件成功------->");
                } else {
                    logger.info("[EmailTask] 发送邮件失败------->");
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.log(Level.WARNING, "[EmailTask] 邮件发送异常!");
            }
        }
    }
}
