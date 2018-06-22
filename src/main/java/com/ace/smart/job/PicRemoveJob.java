package com.ace.smart.job;

import com.ace.smart.util.DateUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class PicRemoveJob implements Job{

    private static final Logger logger = LoggerFactory.getLogger(PicRemoveJob.class);


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        StringBuilder sb = new StringBuilder("C:\\Users\\Administrator\\Documents\\Tencent Files\\1414790478\\FileRecv\\");
        sb.append(DateUtil.getPicCurrentDay());
        sb.append(".jpg");
        File file = new File(sb.toString()); // 源文件
        if(!file.exists()){
            logger.info("文件还未下载!");
            return;
        }
        File pastfile  = new File("E:\\code\\smart\\src\\main\\resources\\static\\img\\"+DateUtil.getPicCurrentDay()+".jpg"); // 目标文件
        if(pastfile.exists()){
            logger.info("文件已存在!");
            return;
        }
        BufferedInputStream is = null;
        BufferedOutputStream bufferedOutputStream = null;
        try {
            is = new BufferedInputStream(new FileInputStream(file));
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(pastfile,true));
            byte [] fileB = new byte[1024];
            int len = 0;
            while(-1 !=(len = is.read(fileB))){
                bufferedOutputStream.write(fileB);
            }
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.info("文件移动成功！");
    }
}
