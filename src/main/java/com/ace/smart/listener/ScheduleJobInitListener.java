package com.ace.smart.listener;

import com.ace.smart.entity.SysTask;
import com.ace.smart.quartz.utils.QuartzManager;
import com.ace.smart.service.SysTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(value = 1)
public class ScheduleJobInitListener implements CommandLineRunner{

    @Autowired
    private SysTaskService sysTaskService;

    @Autowired
    QuartzManager quartzManager;

    @Override
    public void run(String... arg0) throws Exception {
        sysTaskService.initSchedule();
    }

}
