package cn.superdestroy.springcache.ehcache2xsingle.configuration;

import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * 2019-01-09 18:09
 * 通过编程方式配置Cache管理 使用时候需要注释 application.properties 中的  spring.cache.ehcache.config
 * @author zhangningwei
 */
@Configuration
public class ConfigEhcache {

//    @Bean
    public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean bean){
        return new EhCacheCacheManager(bean.getObject());
    }

//    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean(){
        EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
        ehCacheManagerFactoryBean.setShared(true);
        return ehCacheManagerFactoryBean;
    }

}
