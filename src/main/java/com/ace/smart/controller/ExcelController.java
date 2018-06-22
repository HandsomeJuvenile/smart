package com.ace.smart.controller;

import com.ace.smart.annotation.service.RowNameService;
import com.ace.smart.entity.PUser;
import com.ace.smart.excel.ExportExcel;
import com.ace.smart.excel.entity.ExcelData;
import com.ace.smart.service.PUserService;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ExcelController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelController.class);

    @Autowired
    private PUserService pUserService;

    /**
     * 导出用户excel数据
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/userExcel", method = RequestMethod.GET)
    public void excel(HttpServletResponse response) throws Exception {
        ExcelData data = new ExcelData();
        data.setName("用户");
        data.setTitles(RowNameService.load(PUser.class));
        List<PUser> pUsersList =  pUserService.selectAll(0,100).getList();
        List<List<Object>> rows = new ArrayList();
        for (PUser pUser:pUsersList) {
            List<Object> row = new ArrayList();
            row.add(pUser.getUserLoginName());
            row.add(pUser.getNickname());
            row.add(pUser.getUAge());
            row.add(pUser.getUAddress());
            row.add(pUser.getEmail());
            row.add(pUser.getPhone());
            row.add(pUser.getCreateTime());
            row.add(pUser.getSelfIntroduction());
            rows.add(row);
        }
        data.setRows(rows);
        String fileName = System.currentTimeMillis()+".xlsx";
        ExportExcel.exportExcel(response,fileName,data);
        if (LOGGER.isInfoEnabled()){
            LOGGER.info("用户excel导出成功"+fileName);
        }
    }

}
