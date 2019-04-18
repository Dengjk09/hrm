package com.dengjk.system.config;

import com.dengjk.common.utils.SnowflakeIdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Dengjk
 * @create 2019-04-17 20:33
 * @desc
 **/
@Configuration
public class IdGeneratorConfig {


    /**
     * 使用雪花生成id
     */
    @Bean
    public SnowflakeIdWorker idGenerator() {
        return new SnowflakeIdWorker(10, 10);
    }
}
