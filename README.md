# SpringCache
学习使用SpringCache的Demo项目。涉及Ehcache2.x、Ehcache3.x、Redis 

# 在Spring Boot中通过@EnableCaching注解自动化配置合适的缓存管理器（CacheManager），Spring Boot根据下面的顺序去侦测缓存提供者：
Generic<br>
JCache (JSR-107) 推测在使用Ehcache3.x时（@EnableCaching）需要采用JCache的配置方式<br>
EhCache 2.x<br>
Hazelcast<br>
Infinispan<br>
Redis<br>
Guava<br>
Simple<br>

**在配置使用Ehcache3.x采用SpringCache的注解，比较奇怪的是必须要在对象实现 Serializable 接口，不然会报错囧 暂时没有找到问题或者原因。带后期查看**
```
@Component
@CacheConfig(cacheNames = "UserCache")
public class UserCache **implements Serializable** {
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
```
