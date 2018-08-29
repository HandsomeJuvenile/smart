package com.ace.smart.common.controller;

import com.ace.smart.common.annotation.Log;
import com.ace.smart.common.entity.PuImg;
import com.ace.smart.common.entity.UploadProperties;
import com.ace.smart.common.service.LoginService;
import com.ace.smart.common.service.PuImgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;


@Component
@Controller
@RequestMapping("upload")
public class FileUploadController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadController.class);
    @Resource
    private ResourceLoader resourceLoader;
    @Resource
    private UploadProperties uploadProperties;
    @Autowired
    private PuImgService puImgService;
    @Autowired
    private LoginService loginService;

    /**
     * @desc 头像上传
     * @author zzh
     * @date 2018/5/28 9:36
     * @param   
     * @return 
     */
    @Log("头像上传")
    @RequestMapping("/user")
    @ResponseBody
    public Map<String ,String> uploadHeadImg(@RequestParam("file")MultipartFile[] files, HttpServletRequest request) {
        Map<String ,String> map = new HashMap<>();
        String basePath = "f:/image/user/";
        upload(basePath,files,map);
        return map;
    }

    /**
     * @desc basePath: 基础目录
     * @author zzh
     * @date 2018/6/1 16:39
     * @param   
     * @return 
     */

    // TODO: 2018/6/4  如果上传的文件 是多文件的话  那么map返回的src属性 每一次都会被覆盖 当多文件上传时 在修改
    public Map<String ,String> upload(String basePath, MultipartFile[] files, Map<String ,String> map) {
        // 判断文件是否存在
        File targetFile = new File(basePath);
        if (!targetFile.exists()) {
            targetFile.mkdir();
        }
        for (MultipartFile file:files) {
            // 得到文件名
            String fileName = file.getOriginalFilename();
            // 文件后缀名
            String type = splitAfterName(fileName);
            // 重新生成一个唯一的文件名
            String newFileName = System.currentTimeMillis()+""+"."+type;
            try {
                Files.copy(file.getInputStream(),Paths.get(basePath,newFileName), StandardCopyOption.REPLACE_EXISTING);
                map.put("code","0");
                // src 的value 变成list数组
                map.put("src",newFileName);
                return saveData(newFileName,map);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        map.put("code","1");
        map.put("msg","头像修改失败!");
        return map;
    }

    /**
     * 头像上传 map返回
     * @param map
     * @param affect
     * @param operate
     * @return
     */
    private Map<String ,String > isSuccess(Map<String ,String> map,int affect,String operate){
        if (affect > 0) {
            map.put("code","0");
            map.put("msg","头像"+operate+"成功!");
            return map;
        }else {
            map.put("code","1");
            map.put("msg","头像"+operate+"失败!");
            return map;
        }
    }

    private  String splitAfterName(String fileName){
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * 头像上传或者更新 数据库保存
     * @param newFileName
     * @param map
     * @return
     */
    private Map<String,String > saveData(String newFileName,Map<String,String> map){
        String user_id = loginService.getLoginUser().getId()+"";
        // 查询该用户是否已经上传过头像
        PuImg puImg = new PuImg();
        puImg.setJpgurl("/userImg/"+newFileName);
        puImg.setType("0"); // 0 代表用户头像
        puImg.setUserId(user_id);
        if (puImgService.selectByUserIdAndType(puImg).size() > 0) {
            // 更新数据库
            int affect = puImgService.updateByPrimaryKey(puImg);
            map = isSuccess(map,affect,"修改");
        }else {
            // 加入到数据库中
            int affect = puImgService.insertSelective(puImg);
            map = isSuccess(map,affect,"上传");
        }
        return map;
    }


}
