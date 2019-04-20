package com.dengjk.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @MapperScan该注解等同于dao接口@mapper,通过动态代理生成实现 在tkmapper 2.0以上需要使用tk.mybatis的MapperScan
 * @EntityScan(value = {"com.dengjk"})
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class, scanBasePackages = "com.dengjk")
@EnableJpaRepositories(basePackages = {"com.dengjk.system.dao"})
@EnableCaching
public class HrmSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(HrmSystemApplication.class, args);
    }
}
