package com.wme.rms.persistence.mysql;


import com.wme.base.persistance.BaseMapper;
import com.wme.rms.entity.DualEntity;

import java.util.Date;

/**
 * Created by Wangmingen on 2015/8/24.
 */
public interface DualMapper extends BaseMapper<DualEntity> {

    Date getSystemTime();

}
