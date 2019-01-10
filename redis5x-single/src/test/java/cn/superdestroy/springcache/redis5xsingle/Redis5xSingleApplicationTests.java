package cn.superdestroy.springcache.redis5xsingle;

import cn.superdestroy.springcache.redis5xsingle.CacheVO.UserCache;
import cn.superdestroy.springcache.redis5xsingle.CacheVO.UserVO;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class Redis5xSingleApplicationTests {

    /**
     * 默认为 K:String V:Object(只支持原始类型 如：String, Integer ...)
     */
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 默认为 K:String V:String
     */
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    /**
     * 使用Jackson序列化
     * K:String V:Object(对象通过Jackson转化为String)
     */
    private RedisTemplate<String, Object> stringObjectRedisTemplate;
    /**
     *
     */
    @Autowired
    private UserCache userCache;
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Before
    public void before(){
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        stringObjectRedisTemplate = new RedisTemplate<>();
        stringObjectRedisTemplate.setConnectionFactory(redisConnectionFactory);
        stringObjectRedisTemplate.setKeySerializer(jackson2JsonRedisSerializer);
        stringObjectRedisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        stringObjectRedisTemplate.setHashKeySerializer(jackson2JsonRedisSerializer);
        stringObjectRedisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        stringObjectRedisTemplate.afterPropertiesSet();
    }

    @Test
    @Order(0)
    public void testRedis() {
        /**
         * redisTemplate有两个方法经常用到,一个是opsForXXX一个是boundXXXOps,XXX是value的类型,
         * 前者获取到一个Operation,但是没有指定操作的key,可以在一个连接(事务)内操作多个key以及对应的value;
         * 后者会获取到一个指定了key的operation,在一个连接内只操作这个key对应的value.
         */
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(" ---  opsValue  ---");
        redisTemplate.opsForValue().set("KEY_1", UserVO.create("1").toString());
        try {
            redisTemplate.opsForValue().set("KEY_1", objectMapper.writeValueAsString(UserVO.create("1")));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
//        stringRedisTemplate.opsForValue().set("KEY_1", UserVO.create("1").toString());
//        stringObjectRedisTemplate.opsForValue().set("KEY_1", UserVO.create("1"));
        try {
            stringRedisTemplate.opsForValue().set("KEY_2", objectMapper.writeValueAsString(UserVO.create("2")));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        stringObjectRedisTemplate.opsForValue().set("KEY_3", UserVO.create("3"));
        System.out.println(stringObjectRedisTemplate.opsForValue().get("KEY_3").getClass().getName());
        System.out.println(" ---  opsList  ---");
        stringObjectRedisTemplate.opsForList().rightPush("list_1", UserVO.create("1"));
        stringObjectRedisTemplate.opsForList().rightPush("list_1", UserVO.create("2"));
        stringObjectRedisTemplate.opsForList().rightPush("list_1", UserVO.create("3"));

    }

//    @Test
//    @Order(1)
    public void testUserCache(){
        userCache.put("Cache_1");
        userCache.put("Cache_2");
        System.out.println(userCache.get("Cache_1"));
        System.out.println(userCache.get("Cache_2"));
    }
}

