package com.wme.web.persistence.mysql;



import com.wme.base.persistance.BaseMapper;
import com.wme.web.entity.DualEntity;

import java.util.Date;

/**
 * Created by Wangmingen on 2015/8/24.
 */
public interface DualMapper extends BaseMapper<DualEntity> {

    Date getSystemTime();

}
