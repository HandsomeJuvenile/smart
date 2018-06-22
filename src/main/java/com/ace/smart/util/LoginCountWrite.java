package com.ace.smart.util;

import com.ace.smart.entity.PUser;
import com.ace.smart.service.PUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Component
public class LoginCountWrite {

    @Autowired
    private  PUserService pUserService;

    private static final String url  = "E:/centerThings/loginCount.txt";
    private static File file;

    static {
        file = new File(url);
    }

    public   void writeLogin() {
        List<PUser> list = pUserService.selectAll(0,5000).getList();
        for (PUser pUser:list) {
            OutputStream out = null;
            try {
                out = new FileOutputStream(file, true);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(pUser.getUserLoginName()).append(",").append("111111").append("\r\n");
                byte[] b = stringBuilder.toString().getBytes();
                out.write(b);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                closeIO(out);
            }
        }



    }

    private static void  closeIO(OutputStream out){
        if (out!=null) {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
