package cn.superdestroy.springcache.redis5xsingle.CacheVO;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * 2019-01-10 17:32
 *
 * @author zhangningwei
 */
@Component
@CacheConfig(cacheNames = "UserCache")
public class UserCache {

    @CachePut
    public UserVO put(String id){
        /**
         *放入缓存
         */
        System.out.println(id+ "--- 放入缓存!");
        return UserVO.create(id);
    }

    @Cacheable
    public UserVO get(String id){
        return UserVO.create(id);
    }

}
