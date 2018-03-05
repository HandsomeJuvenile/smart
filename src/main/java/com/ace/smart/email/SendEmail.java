package com.ace.smart.email;

import com.ace.smart.entity.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.Properties;

//                            _ooOoo_
//                           o8888888o
//                           88" . "88
//                           (| -_- |)
//                            O\ = /O
//                        ____/`---'\____
//                      .   ' \\| |// `.
//                       / \\||| : |||// \
//                     / _||||| -:- |||||- \
//                       | | \\\ - /// | |
//                     | \_| ''\---/'' | |
//                      \ .-\__ `-` ___/-. /
//                   ___`. .' /--.--\ `. . __
//                ."" '< `.___\_<|>_/___.' >'"".
//               | | : `- \`.;`\ _ /`;.`/ - ` : | |
//                 \ \ `-. \_ __\ /__ _/ .-` / /
//         ======`-.____`-.___\_____/___.-`____.-'======
//                            `=---='
//
//         .............................................
//                  佛祖镇楼                  BUG辟易
//          佛曰:
//                  写字楼里写字间，写字间里程序员；
//                  程序人员写程序，又拿程序换酒钱。
//                  酒醒只在网上坐，酒醉还来网下眠；
//                  酒醉酒醒日复日，网上网下年复年。
//                  但愿老死电脑间，不愿鞠躬老板前；
//                  奔驰宝马贵者趣，公交自行程序员。
//                  别人笑我忒疯癫，我笑自己命太贱；
//                  不见满街漂亮妹，哪个归得程序员？
@Service
public class SendEmail {
    private static final Logger logger = LoggerFactory.getLogger(SendEmail.class);

    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String Sender; //读取配置文件中的参数
    @Autowired
    private TemplateEngine templateEngine;

    /**
     * 发送普通邮件  只可以发送文本内容
     */
    public void sendTimerSimpleMail(Email email) {
        validation(email);
        valRecives(email);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(Sender);
        message.setTo(email.getRecives());
        message.setSubject(email.getSubject());
        message.setText(email.getContent());
        javaMailSender.send(message);
    }

    /**
     * 发送HTML邮件
     */
    public void sendHtmlMail(Email email) {
        validation(email);
        valRecive(email);
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(Sender);
            helper.setTo(email.getRecive()); //自己给自己发送邮件
            helper.setSubject(email.getSubject());
            helper.setText(email.getContent(),true); //  这里需要设置成为true 那么发送的才是html格式
            javaMailSender.send(message);
            logger.info("邮件发送成功!");
        } catch (MessagingException e) {
            e.printStackTrace();
            logger.info("邮件发送失败!");
        }
    }

    /**
     * 发送html模板邮件
     */
    public void sendHappyMail(Email email){
        validation(email);
        valRecives(email);
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            IContext context = new Context();
            // 获取模板html代码
            String process =templateEngine.process(email.getFilePath(),context);
            helper.setFrom(Sender);
            helper.setSubject(email.getSubject());
            helper.setTo(email.getRecives());
            helper.setText(process,true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(message);
    }

    /**
     * 发送图片邮件  "/static/ace.png"
     */
    public void sendPicEmail(Email email) throws MessagingException {
        validation(email);
        valRecive(email);
        // 配置文件对象
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true"); // 是否进行验证
        Session session = Session.getInstance(props);
        MimeMessage mail = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mail, true);
        helper.setTo(email.getRecive()); // 发送给谁
        helper.setSubject(email.getSubject()); // 标题
        helper.setFrom(Sender); // 来自
        // 邮件内容，第二个参数指定发送的是HTML格式
        if(email.getContent()!=null && !"".equals(email.getContent())) {
            helper.setText(email.getContent()+"<div align=\"center\"><img height=\"648\" width=\"1152\" src='cid:myImg'><div/>", true);
        }else{
            helper.setText("<div align=\"center\"><img height=\"648\" width=\"1152\" src='cid:myImg'><div/>", true);

        }
        // 增加CID内容
        ClassPathResource img = new ClassPathResource(email.getImgPath());
        helper.addInline("myImg", img);
        javaMailSender.send(mail); // 发送
        logger.info("向"+email.getRecive()+"发送邮件成功!");
    }

    /**
     *
     * @param email
     */
    public void sendRegisterEmail(Email email) {
        email.setSubject("您好");
        validation(email);
        valRecive(email);
        String content = "<html><body><p>您好 O(∩_∩)O~~</p><p>欢迎加入Ace !<br><br>帐户需要激活才能使用，赶紧激活成为Ace正式的一员吧.</P><p>请在24小时内输入下面的激活码立即激活帐户:"+email.getToken()+"</p></body></html>";
        email.setContent(content);
        sendHtmlMail(email);
        logger.info("向"+email.getRecive()+"发送注册验证邮件成功!");
    }

    /**
     * 添加附件邮件
     */
    public void testFujianMail(Email email){
        validation(email);
        valRecives(email);
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(Sender);
            helper.setSubject(email.getSubject());
            helper.setTo(email.getRecives());
            helper.setText(email.getContent());
            FileSystemResource fileSystemResource = new FileSystemResource(email.getFilePath());
            helper.addAttachment("pic.jpg",fileSystemResource);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(message);
        logger.info("向"+ Arrays.asList(email.getRecives())+"发送附件邮件成功!");
    }

    private void validation(Email email) {
        if (email == null) {
            throw new RuntimeException("email不可以为空");
        } else if (email.getSubject() == null || "".equals(email.getSubject())) {
            throw new RuntimeException("主题");
        }
    }


    private void valRecives(Email email){
        if(email.getRecives() ==null || "".equals(Arrays.asList(email.getRecives()))){
            throw new RuntimeException("发送多人");
        }
    }

    private void valRecive(Email email){
        if(email.getRecive() ==null || "".equals(email.getRecive())){
            throw new RuntimeException("发送一人");
        }
    }
}
