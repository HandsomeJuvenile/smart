package com.ace.smart.common.listener;

import com.ace.smart.common.quartz.utils.QuartzManager;
import com.ace.smart.common.service.SysTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

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
