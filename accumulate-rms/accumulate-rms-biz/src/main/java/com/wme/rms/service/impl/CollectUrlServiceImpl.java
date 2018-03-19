package com.wme.rms.service.impl;

import com.wme.base.service.impl.BaseServiceImpl;
import com.wme.rms.entity.collect.CollectUrl;
import com.wme.rms.persistence.mysql.CollectUrlMapper;
import com.wme.rms.service.CollectUrlService;
import org.springframework.stereotype.Service;

/**
 * Created by ming on 2016/3/27.
 */

@Service(value = "collectUrlService")
public class CollectUrlServiceImpl extends BaseServiceImpl<CollectUrlMapper,CollectUrl> implements CollectUrlService {


}
