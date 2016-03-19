package cc.lovesq.service.impl;

import cc.lovesq.annotations.Timecost;
import cc.lovesq.components.JedisLocalClient;
import cc.lovesq.dao.CreativeMapper;
import cc.lovesq.model.CreativeDO;
import cc.lovesq.query.CreativeQuery;
import cc.lovesq.service.CreativeService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import shared.utils.JsonUtil;

import javax.annotation.Resource;
import java.util.List;

@Component("creativeService")
public class CreativeServiceImpl extends BaseServiceImpl<CreativeDO> implements CreativeService {

    private static final String CREATIVE_KEY = "creative:";
    @Autowired
    private CreativeMapper creativeMapper;
    @Resource
    private JedisLocalClient jedisLocalClient;

    @Timecost
    public CreativeDO get(Long creativeId) {

        String creativeJson = jedisLocalClient.get(CREATIVE_KEY + creativeId);
        if (!StringUtils.isBlank(creativeJson)) {
            return JsonUtil.toObject(creativeJson, CreativeDO.class);
        }
        CreativeDO c = creativeMapper.findByCreativeId(creativeId);
        jedisLocalClient.set(CREATIVE_KEY + creativeId, JSON.toJSONString(c));
        return c;
    }

    public void save(CreativeDO creative) {
        creativeMapper.insert(creative);
    }

    public void update(CreativeDO creative) {
        creativeMapper.update(creative);
    }

    public void delete(Long creativeId) {
        creativeMapper.delete(creativeId);
    }

    public List<CreativeDO> search(CreativeQuery query) {
        return creativeMapper.list(query);
    }

    public Integer count(CreativeQuery query) {
        return creativeMapper.count(query);
    }

}
