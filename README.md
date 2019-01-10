SpringCache

学习使用SpringCache的Demo项目。涉及Ehcache2.x、Ehcache3.x、Redis

在Spring Boot中通过@EnableCaching注解自动化配置合适的缓存管理器（CacheManager），Spring Boot根据下面的顺序去侦测缓存提供者：
Generic
JCache (JSR-107) 推测在使用Ehcache3.x时（@EnableCaching）需要采用JCache的配置方式
EhCache 2.x
Hazelcast
Infinispan
Redis
Guava
Simple
由于采用SpringBoot一定要注意SpringBoot的默认设置事项，避免个人设置出现无效的情况

在配置使用Ehcache3.x采用SpringCache的注解，比较奇怪的是必须要在对象实现 Serializable 接口，不然会报错囧 暂时没有找到问题或者原因。待后期查看
@Component
@CacheConfig(cacheNames = "UserCache")
public class UserCache implements Serializable {
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
Redis 使用注意事项（采用的版本SrpingBoot2.x,Redis5.x）
如何不同的缓存设置不同的过期时间例子如下

@Bean
public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory){
    RedisCacheManager redisCacheManager = new RedisCacheManager(
            RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory),
            this.getRedisCacheConfigurationWidthTTL(Duration.ofSeconds(0L)),
            new HashMap<String, RedisCacheConfiguration>(){
                {
                    this.put("UserCache", getRedisCacheConfigurationWidthTTL(Duration.ofSeconds(60L)));
                }
            }
    );
    return redisCacheManager;
}
设置RedisCacheConfiguration使用链式操作要注意事项例子如下

private RedisCacheConfiguration getRedisCacheConfigurationWidthTTL(Duration duration){
    RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
    /**
     * 这里一定要注意所有操作都是返回一个新的对象所以需要赋值返回或者直接返回。不然设置无效。
     */
    //方法.1
//        redisCacheConfiguration = redisCacheConfiguration.entryTtl(duration);
//        redisCacheConfiguration = redisCacheConfiguration.serializeValuesWith(RedisSerializationContext.SerializationPair
//                .fromSerializer(this.jackson2JsonRedisSerializer()));
    //方法.2
//        redisCacheConfiguration = redisCacheConfiguration.entryTtl(duration)
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(this.jackson2JsonRedisSerializer()));
//        return redisCacheConfiguration;
    //方法.3
    return redisCacheConfiguration
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(this.jackson2JsonRedisSerializer()))
            .entryTtl(duration);
}
``
