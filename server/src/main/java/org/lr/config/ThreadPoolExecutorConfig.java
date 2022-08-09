package org.lr.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@Slf4j
@EnableAsync
public class ThreadPoolExecutorConfig {

    private static final int CORE_NUMB = Runtime.getRuntime().availableProcessors();

    @Bean("MyThreadPoolExecutor")
    public Executor myThreadPoolExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(CORE_NUMB);
        executor.setMaxPoolSize(CORE_NUMB*2);
        executor.setQueueCapacity(Integer.MAX_VALUE);
        executor.setThreadNamePrefix("cManager-");
        executor.initialize();
        return executor;
    }
}
