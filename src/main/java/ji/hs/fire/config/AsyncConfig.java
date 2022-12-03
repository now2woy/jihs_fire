package ji.hs.fire.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 비동기 설정
 * @author now2w
 *
 */
@EnableAsync
@Configuration
public class AsyncConfig {
	/**
	 * 
	 */
	private static final int CORE_POOL_SIZE = 5;
	/**
	 * 
	 */
	private static final int MAX_POOL_SIZE = 30;
	/**
	 * 
	 */
	private static final int QUEUE_CAPACITY = 50;
	
	/**
	 * 
	 * @return
	 */
	@Bean(destroyMethod = "destroy")
	public Executor threadPoolTaskExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(CORE_POOL_SIZE);
		taskExecutor.setMaxPoolSize(MAX_POOL_SIZE);
		taskExecutor.setQueueCapacity(QUEUE_CAPACITY);
		taskExecutor.setThreadNamePrefix("JIHS-FIRE-ASYNC-");
		return taskExecutor;
	}
}
