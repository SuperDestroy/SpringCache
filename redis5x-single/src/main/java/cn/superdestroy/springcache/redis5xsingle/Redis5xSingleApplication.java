package cn.superdestroy.springcache.redis5xsingle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Redis5xSingleApplication {

    public static void main(String[] args) {
        SpringApplication.run(Redis5xSingleApplication.class, args);
    }

}

