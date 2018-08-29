package com.ace.smart.common.serviceimpl;

import com.ace.smart.common.entity.PuImg;
import com.ace.smart.common.mapper.PuImgMapper;
import com.ace.smart.common.service.PuImgService;
import com.ace.smart.common.util.DateUtil;
import com.ace.smart.common.util.IdGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service
public class PuImgServiceImpl implements PuImgService{
    @Autowired
    private PuImgMapper puImgMapper;

    @Override
    public int deleteByPrimaryKey(String imgId) {
        return 0;
    }

    @Override
    public int insert(PuImg record) {
        return 0;
    }

    @Override
    public int insertSelective(PuImg record) {
        if (record != null) {
            record.setImgId(IdGen.getAtomicCounter()+"");
            record.setUploadTime(DateUtil.getCurrentDate());
            return puImgMapper.insertSelective(record);
        }
        return 0;
    }

    @Override
    public PuImg selectByPrimaryKey(String imgId) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(PuImg record) {
        if (record.getUserId() !=null && !record.getUserId().isEmpty()) {
            record.setUploadTime(DateUtil.getCurrentDate());
            return puImgMapper.updateByPrimaryKeySelective(record);
        }
        return 0;
    }

    @Override
    public int updateByPrimaryKey(PuImg record) {
        if (record.getUserId() !=null && !record.getUserId().isEmpty()) {
            record.setUploadTime(DateUtil.getCurrentDate());
            return puImgMapper.updateByPrimaryKey(record);
        }
        return 0;
    }

    /**
     * @desc 根据用户id 和 图片保存类型 是头像 还是图片集
     * @author zzh
     * @date 2018/6/4 9:18
     * @param   
     * @return 
     */
    @Override
    public List<PuImg> selectByUserIdAndType(PuImg puImg) {
        Assert.notNull(puImg,"不可以为空");
        String user_id = puImg.getUserId();
        String type = puImg.getType();

        if (user_id !=null && !user_id.isEmpty() && type!=null && !type.isEmpty()) {
            return puImgMapper.selectByUserIdAndType(puImg);
        }
        return new ArrayList<>();
    }

}
