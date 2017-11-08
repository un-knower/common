package com.postss.common.util;

/**
 * ssl错误为jdk1.8版本的解决：
 * http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html下载文件
 * 解压后覆盖\jdk1.8.0_77\jre\lib\security中的2个jar
 * ClassName: MailUtil 
 * @author jwSun
 * @date 2016年10月7日下午1:20:15
 */
public class MailUtil {

    /*private static Logger log = Logger.getLogger(MailUtil.class);
    
    @SuppressWarnings("restriction")
    private static com.sun.net.ssl.internal.ssl.Provider provider = new com.sun.net.ssl.internal.ssl.Provider();
    
    private static Properties props = System.getProperties();
    
    static {
        Security.addProvider(provider);
        props.setProperty("mail.store.protocol", Constant.MAIL.PROTOCAL);
        props.setProperty("mail.smtp.host", Constant.MAIL.SMTP_HOST);
        props.setProperty("mail.smtp.port", Constant.MAIL.PORT);
        props.setProperty("mail.smtp.socketFactory.fallback", Constant.MAIL.SOCKETFACTORY_FALLBACK);
        props.setProperty("mail.smtp.socketFactory.class", Constant.MAIL.SOCKETFACTORY_CLASS);
        props.setProperty("mail.smtp.socketFactory.port", Constant.MAIL.SOCKETFACTORY_PORT);
        props.put("mail.smtp.auth", Constant.MAIL.AUTH);
        props.put("mail.smtp.starttls.enable", "true");
    }
    
    public static void sendMailByTo(String toEmail, String content) {
        try {
    
            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(Constant.MAIL.USERNAME, Constant.MAIL.PASSWORD);
                }
            });
    
            Message msg = new MimeMessage(session);
    
            msg.setFrom(new InternetAddress(Constant.MAIL.USERNAME));// 设置发件人
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));// 设置收件人
            msg.setSubject("douyuwebServer注册邮件");
            String realContent = MailTemplate.getMailTemplate(content);
            msg.setContent(realContent, "text/html;charset=utf-8");
    
            Transport.send(msg);
        } catch (Exception e) {
            log.error("发送邮件失败,邮件地址：" + toEmail, e.getCause());
        }
    }
    
    public static void sendMailByToFormJMS(String toEmail, String token, Integer userId) {
        try {
            System.out.println("ok!");
            Long longDate = System.currentTimeMillis();
            String newToken = token.substring(0, 16) + longDate + token.substring(16);
            String content = MailTemplate.registTemplate + "<p>http://www.douyuweb.com/user/updateActivation?userId="
                    + userId + "&token=" + newToken + "</p>";
            sendMailByTo(toEmail, content);
        } catch (Exception e) {
            log.error("传入参数错误");
        }
    }
    
    public static void main(String[] args) {
        sendMailByTo("332755645@qq.com", "123321");
    }*/

}
