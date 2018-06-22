package com.ace.smart.job;

import com.ace.smart.email.SendEmail;
import com.ace.smart.entity.Email;
import com.ace.smart.util.DateUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.*;

@Component
public class EveryDayEmailJob implements Job{
    private static final Logger logger = LoggerFactory.getLogger(EveryDayEmailJob.class);
    @Autowired
    private SendEmail sendEmail;
    private static final String [] emalis =
            {       "1837400570@qq.com","1270943107@qq.com","1208114563@qq.com",
                    "1319759684@qq.com","595186151@qq.com","1344613837@qq.com",
                    "18356090837@163.com","xiangrikuiforever@qq.com","1097337990@qq.com",
                    "35474616@qq.com","2627021941@qq.com","1056350018@qq.com",
                    "372129190@qq.com","791217653@qq.com","2444017726@qq.com",
                    "2779902638@qq.com","1151857475@qq.com","1518678988@qq.com",
                    "2592684466@qq.com","1322843232@qq.com","2752380184@qq.com",
                    "772361744@qq.com","940003078@qq.com","1642518875@qq.com",
                    "2586513592@qq.com"
            };
    private String emaill = "1414790478@qq.com";

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Email email = new Email();
        StringBuilder imgPath = new StringBuilder("/static/img/");
        File file = new File("/static/img/"+ DateUtil.getPicCurrentDay()+".jpg");
        imgPath.append(DateUtil.getPicCurrentDay()+".jpg");
        email.setImgPath(imgPath.toString());
        email.setSubject("早安");
        for (String revice:emalis) {
            try {
                email.setRecive(revice);
                sendEmail.sendPicEmail(email);
            }  catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        logger.info("早安结束，新的一天开始！");
    }

    public void emaill() throws JobExecutionException {
        Email email = new Email();
        StringBuilder imgPath = new StringBuilder("f:/image/email/");
        File file = new File("f:/image/email/2018.06.11.jpg");
        imgPath.append("2018.06.11.jpg");
        email.setImgPath(imgPath.toString());
        email.setSubject("早安");
            try {
                email.setRecive(emaill);
                sendEmail.sendPicEmail(email);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
    }
}
