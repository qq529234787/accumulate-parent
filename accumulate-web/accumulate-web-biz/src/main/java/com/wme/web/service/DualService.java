package com.wme.web.service;


import com.wme.base.service.BaseService;
import com.wme.web.entity.DualEntity;
import com.wme.web.persistence.mysql.DualMapper;

import java.util.Date;

/**
 * Created by Wangmingen on 2015/9/15.
 */
public interface DualService extends BaseService<DualMapper,DualEntity> {

    Date getSystemTime();

}
