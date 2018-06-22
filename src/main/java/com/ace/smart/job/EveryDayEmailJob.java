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
            {
            };

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


}
