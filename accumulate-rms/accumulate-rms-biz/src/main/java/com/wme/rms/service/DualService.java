package com.wme.rms.service;


import com.wme.base.service.BaseService;
import com.wme.rms.entity.DualEntity;
import com.wme.rms.persistence.mysql.DualMapper;

import java.util.Date;

/**
 * Created by Wangmingen on 2015/9/15.
 */
public interface DualService extends BaseService<DualMapper,DualEntity> {

    Date getSystemTime();

}
