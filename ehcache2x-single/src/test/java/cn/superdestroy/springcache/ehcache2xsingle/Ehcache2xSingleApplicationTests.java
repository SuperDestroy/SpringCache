package cn.superdestroy.springcache.ehcache2xsingle;

import cn.superdestroy.springcache.ehcache2xsingle.cache.UserCache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Ehcache2xSingleApplicationTests {

    @Autowired
    private UserCache userCache;

    @Test
    public void contextLoads() {
        userCache.put("1");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(userCache.get("1"));
    }

}

