package cn.superdestroy.springcache.ehcache2xsingle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
//启用SpringCache的必要注解
@EnableCaching
public class Ehcache2xSingleApplication {

    public static void main(String[] args) {
        SpringApplication.run(Ehcache2xSingleApplication.class, args);
    }

}

