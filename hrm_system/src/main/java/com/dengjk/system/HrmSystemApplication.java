package com.dengjk.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.session.data.redis.RedisFlushMode;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @MapperScan该注解等同于dao接口@mapper,通过动态代理生成实现 在tkmapper 2.0以上需要使用tk.mybatis的MapperScan
 * @EntityScan(value = {"com.dengjk"})
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class, scanBasePackages = {"com.dengjk.system","com.dengjk.common"})
@EnableJpaRepositories(basePackages = {"com.dengjk.system.dao"})
@EnableCaching
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 32400, redisNamespace = "hrm", redisFlushMode = RedisFlushMode.ON_SAVE)
public class HrmSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(HrmSystemApplication.class, args);
    }
}
