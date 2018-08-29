package com.ace.smart.common.serviceimpl;

import com.ace.smart.common.entity.SysTask;
import com.ace.smart.common.mapper.SysTaskMapper;
import com.ace.smart.common.quartz.utils.QuartzManager;
import com.ace.smart.common.service.LoginService;
import com.ace.smart.common.service.SysTaskService;
import com.ace.smart.common.util.CollectionUtil;
import com.ace.smart.common.util.DateUtil;
import com.ace.smart.common.util.IdGen;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SysTaskServiceImpl implements SysTaskService{
    @Autowired
    private LoginService loginService;
    @Autowired
    private SysTaskMapper sysTaskMapper;
    @Autowired
    private QuartzManager quartzManager;

    @Override
    public int deleteByPrimaryKey(Long id) {
        try {
            quartzManager.deleteJob(selectByPrimaryKey(id));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return sysTaskMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SysTask record) {
        record.setCreateBy(loginService.getLoginName());
        record.setCreateDate(DateUtil.getCurrentDate());
        record.setId(IdGen.getAtomicCounter());
        record.setJobStatus("0");
        return sysTaskMapper.insert(record);
    }

    @Override
    public int insertSelective(SysTask record) {
        return 0;
    }

    @Override
    public SysTask selectByPrimaryKey(Long id) {
        return sysTaskMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(SysTask record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(SysTask record) {
        return 0;
    }

    @Override
    public PageInfo selectAll(int currentPage, int pageSize,String name) {
        PageHelper.startPage(currentPage,pageSize);
        List<SysTask> list = sysTaskMapper.selectAll(name);
        if(list!=null && list.size()>0){
            PageInfo pageInfo = new PageInfo(list);
            return pageInfo;
        }
        return new PageInfo(new ArrayList<SysTask>());
    }

    @Override
    public List<SysTask> selectAll() {
        List<SysTask> list = sysTaskMapper.selectAll("");
        if(list!=null && list.size()>0){
            return list;
        }
        return new ArrayList<>();
    }

    /**
     * @desc 监听器在系统启动的时候 就会去开始调用开启的任务
     * @author zzh
     * @date 2018/6/11 15:50
     * @param
     * @return
     */
    @Override
    public void initSchedule() {
        List<SysTask> sysTaskList = this.selectAll();
        for (SysTask sysTask :sysTaskList){
            if ("1".equals(sysTask.getJobStatus())) {
                quartzManager.addJob(sysTask);
            }
        }
    }

    @Override
    public int updateState(Map<String,String> map) {
        try {
            if ("1".equals(map.get("jobStatus"))) {
                quartzManager.addJob(selectByPrimaryKey(Long.parseLong(map.get("id"))));
            }else {
                quartzManager.deleteJob(selectByPrimaryKey(Long.parseLong(map.get("id"))));
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return sysTaskMapper.updateState(map);
    }

    @Override
    public int batchDelete(List<Long> list) {
        return !CollectionUtil.listIsNull(list)?0:sysTaskMapper.batchDelete(list);    }
}
