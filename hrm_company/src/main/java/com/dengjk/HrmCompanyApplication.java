package com.dengjk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @MapperScan该注解等同于dao接口@mapper,通过动态代理生成实现 在tkmapper 2.0以上需要使用tk.mybatis的MapperScan
 * @EntityScan(value = {"com.dengjk"})
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableJpaRepositories(basePackages = {"com.dengjk.dao"})
@EnableCaching
public class HrmCompanyApplication {

    public static void main(String[] args) {
        SpringApplication.run(HrmCompanyApplication.class, args);
    }
}
