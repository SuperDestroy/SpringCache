# SpringCache
学习使用SpringCache的Demo项目。涉及Ehcache2.x、Ehcache3.x、Redis 

# 在Spring Boot中通过@EnableCaching注解自动化配置合适的缓存管理器（CacheManager），Spring Boot根据下面的顺序去侦测缓存提供者：
Generic > JCache (JSR-107) 推测在使用Ehcache3.x时（@EnableCaching）需要采用JCache的配置方式 > EhCache 2.x > Hazelcast > Infinispan > Redis > Guava > Simple

