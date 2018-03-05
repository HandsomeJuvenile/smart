package com.ace.smart.timer;

import com.ace.smart.email.SendEmail;
import com.ace.smart.entity.Email;
import com.ace.smart.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.*;


@Component
public class EmailTimmer {
    private static final Logger logger = LoggerFactory.getLogger(SendEmail.class);
    @Autowired
    private SendEmail sendEmail;
    private static final String [] strings =
            {      "1837400570@qq.com","1270943107@qq.com","1208114563@qq.com",
                    "1319759684@qq.com","595186151@qq.com","1344613837@qq.com",
                    "18356090837@163.com","zhuzhenzhen@ofo.com","1097337990@qq.com",
                    "35474616@qq.com","2627021941@qq.com",
                    "372129190@qq.com","791217653@qq.com","2444017726@qq.com",
                    "2779902638@qq.com","1151857475@qq.com","1518678988@qq.com",
                    "2592684466@qq.com","1322843232@qq.com","2752380184@qq.com",
                    "772361744@qq.com","940003078@qq.com","1642518875@qq.com"
            };

    /*@Scheduled(cron = "0 00 17 * * ?"*//*每天下午17:00触发*//*)
    @Async // 异步发送
    public  void setTimerEmail(){
        Email email = new Email();
        String [] strings = {"1270943107@qq.com","1208114563@qq.com","1319759684@qq.com","595186151@qq.com","1344613837@qq.com",
                "18356090837@163.com","zhuzhenzhen@ofo.com"};
        email.setSubject("您好");
        email.setRecives(strings);
        email.setContent(Encouragement.getEncouragement());
        sendEmail.sendTimerSimpleMail(email);
        logger.info("定时发送邮件");
    }*/

    //  (fixedRate=20000)
    @Scheduled(cron = "00 00 09 * * ?"/*每天9:00触发*/)
    @Async // 异步发送
    public  void setTimerPicEmail() throws MessagingException{
        Email email = new Email();

        //String [] strings1 = {"18356090837@163.com"};
        StringBuilder imgPath = new StringBuilder("/static/img/");
        File file = new File("/static/img/"+DateUtil.getPicCurrentDay()+".png");
        imgPath.append(DateUtil.getPicCurrentDay()+".png");
        email.setImgPath(imgPath.toString());
        email.setSubject("早安");
        for (String revice:strings) {
            try {
                email.setRecive(revice);
                sendEmail.sendPicEmail(email);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        logger.info("早安结束，新的一天开始！");
    }

    //
    //@Scheduled(fixedRate=10000/*每天9:00触发*/)
    @Async // 异步发送
    public  void setTimerTejiEmail() throws MessagingException{
        Email email = new Email();
        String [] strings1 = {"18356090837@163.com"};
        String imgPath = "/static/img/ACE特辑.png";
        File file = new File("/static/img/ACE特辑.png");
        email.setImgPath(imgPath);
        email.setSubject("私人订制—ACE特辑");
        for (String revice:strings1) {
            try {
                email.setRecive(revice);
                sendEmail.sendPicEmail(email);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Scheduled(cron = "00 40 08 * * ?"/*每天9:00触发*/)
    @Async // 异步发送
    public void copyPic() throws IOException {
        StringBuilder sb = new StringBuilder("C:\\Users\\Administrator\\Documents\\Tencent Files\\1414790478\\FileRecv\\");
        sb.append(DateUtil.getPicCurrentDay());
        sb.append(".png");
        File file = new File(sb.toString()); // 源文件
        if(!file.exists()){
            logger.info("文件还未下载!");
            return;
        }
        File pastfile  = new File("E:\\code\\smart\\src\\main\\resources\\static\\img\\"+DateUtil.getPicCurrentDay()+".png"); // 目标文件
        if(pastfile.exists()){
            logger.info("文件已存在!");
            return;
        }
        BufferedInputStream is = new BufferedInputStream(new FileInputStream(file));
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(pastfile,true));
        byte [] fileB = new byte[1024];
        int len = 0;
        while(-1 !=(len = is.read(fileB))){
            bufferedOutputStream.write(fileB);
        }
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
        is.close();
        logger.info("文件移动成功！");
    }
}
