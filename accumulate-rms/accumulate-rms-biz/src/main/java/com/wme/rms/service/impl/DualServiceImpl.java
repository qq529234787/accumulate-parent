package com.wme.rms.service.impl;

import com.wme.base.service.impl.BaseServiceImpl;
import com.wme.rms.entity.DualEntity;
import com.wme.rms.persistence.mysql.DualMapper;
import com.wme.rms.service.DualService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Wangmingen on 2015/9/15.
 */

@Service(value = "dualService")
public class DualServiceImpl extends BaseServiceImpl<DualMapper,DualEntity> implements DualService {

    @Override
    public Date getSystemTime() {
        return mapper.getSystemTime();
    }
}
