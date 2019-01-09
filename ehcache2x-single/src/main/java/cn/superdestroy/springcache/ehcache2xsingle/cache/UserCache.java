package cn.superdestroy.springcache.ehcache2xsingle.cache;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * 2019-01-09 17:21
 * 用于使用SpringCache管理 UserDO 的缓存操作
 * @author zhangningwei
 */
@Component
@CacheConfig(cacheNames = "UserCache")
public class UserCache {

    @CachePut(key = "#loginId")
//    @CachePut(key = "#p0") 第一个参数作为key， 与 key = "#loginId" 等同
    public UserDO put(String loginId){
        System.out.println("-- 放置 用户:" + loginId + " 缓存中 --");
        return new UserDO(){
            {
                this.setLoginId(loginId);
                this.setName("用户" + loginId);
            }
        };
    }

    /**
     * 失效某个对象
     * @param loginId
     */
    @CacheEvict(key = "#loginId")
    public void evict(String loginId){
    }

    /**
     * 失效所有对象
     */
    @CacheEvict(allEntries = true)
    public void evictAll(){
    }

    /**
     * 从缓存中获取
     * @param loginId
     * @return
     */
    @Cacheable(key = "#loginId")
    public UserDO get(String loginId){
        System.out.println("-- 获取 用户:" + loginId + " 如果没有则返回 null 也在可以在此处创建对象其后会自动放置至缓存中 --");
        System.out.println("-- 生成缓存对象 --");
        return new UserDO(){
            {
                this.setLoginId(loginId);
                this.setName("用户" + loginId);
            }
        };
    }
}
