package com.wme.rms.persistence.mysql;

import com.wme.base.persistance.BaseMapper;
import com.wme.rms.entity.menu.AdminMenuProperty;

/**
 * Created by ming on 2016/3/27.
 */
public interface AdminMenuPropertyMapper extends BaseMapper<AdminMenuProperty> {

    int deleteByPrimaryKey(Long menuPropertyId);

    int updateByPrimaryKey(AdminMenuProperty record);
}
