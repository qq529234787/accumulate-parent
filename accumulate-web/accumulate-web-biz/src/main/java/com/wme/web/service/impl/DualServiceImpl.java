package com.wme.web.service.impl;

import com.wme.base.service.impl.BaseServiceImpl;
import com.wme.web.entity.DualEntity;
import com.wme.web.persistence.mysql.DualMapper;
import com.wme.web.service.DualService;
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
