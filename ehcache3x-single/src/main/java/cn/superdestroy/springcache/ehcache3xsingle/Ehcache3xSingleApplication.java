package cn.superdestroy.springcache.ehcache3xsingle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Ehcache3xSingleApplication {

    public static void main(String[] args) {
        SpringApplication.run(Ehcache3xSingleApplication.class, args);
    }

}

