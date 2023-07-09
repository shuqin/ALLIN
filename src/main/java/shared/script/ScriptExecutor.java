package shared.script;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import groovy.lang.Binding;
import groovy.lang.Script;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import shared.config.GlobalConfig;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component("scriptExecutor")
public class ScriptExecutor {

    private static Logger logger = LoggerFactory.getLogger(ScriptExecutor.class);

    private LoadingCache<String, GenericObjectPool<Script>> scriptCache;

    @Resource
    private GlobalConfig globalConfig;

    @PostConstruct
    public void init() {
        scriptCache = CacheBuilder
                .newBuilder().build(new CacheLoader<String, GenericObjectPool<Script>>() {
                    @Override
                    public GenericObjectPool<Script> load(String script) {
                        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
                        poolConfig.setMaxTotal(globalConfig.getCacheMaxTotal());
                        poolConfig.setMaxWaitMillis(globalConfig.getMaxWaitMillis());
                        return new GenericObjectPool<Script>(new ScriptPoolFactory(script), poolConfig);
                    }
                });
        logger.info("success init scripts cache.");
    }

    public Object exec(String scriptPassed, Binding binding) {
        GenericObjectPool<Script> scriptPool = null;
        Script script = null;
        try {
            scriptPool = scriptCache.get(scriptPassed);
            script = scriptPool.borrowObject();
            script.setBinding(binding);
            Object value = script.run();
            script.setBinding(null);
            return value;
        } catch (Exception ex) {
            logger.error("exxec script error: " + ex.getMessage(), ex);
            return null;
        } finally {
            if (scriptPool != null && script != null) {
                scriptPool.returnObject(script);
            }
        }

    }

}
