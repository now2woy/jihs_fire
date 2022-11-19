package ji.hs.fire.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 비동기 설정
 * @author now2w
 *
 */
@EnableAsync
@Configuration
public class AsyncConfig extends AsyncConfigurerSupport {
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
	 */
	@Override
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(CORE_POOL_SIZE);
		executor.setMaxPoolSize(MAX_POOL_SIZE);
		executor.setQueueCapacity(QUEUE_CAPACITY);
		executor.setThreadNamePrefix("JIHS-FIRE-ASYNC-");
		executor.initialize();
		
		return executor;
	}
}
