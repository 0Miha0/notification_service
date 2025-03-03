package faang.school.notificationservice.config.async;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsyncConfig {

    @Value("${async.event-listener.corePoolSize}")
    private int corePoolSize;

    @Value("${async.event-listener.maxPoolSize}")
    private int maxPoolSize;

    @Value("${async.event-listener.queueCapacity}")
    private int queueCapacity;

    @Bean(name = "eventListenerTaskExecutor")
    public ThreadPoolTaskExecutor eventListenerTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("eventListenerTaskExecutor-");
        executor.initialize();
        return executor;
    }
}
