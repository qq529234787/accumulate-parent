package com.wme.rms.manager.impl;

import com.wme.base.manager.impl.BaseManagerImpl;
import com.wme.rms.entity.collect.CollectUrl;
import com.wme.rms.manager.CollectUrlManager;
import com.wme.rms.persistence.mysql.CollectUrlMapper;
import org.springframework.stereotype.Service;

/**
 * Created by ming on 2016/3/28.
 */
@Service(value = "collectUrlManager")
public class CollectUrlManagerImpl extends BaseManagerImpl<CollectUrlMapper,CollectUrl> implements CollectUrlManager {


}
