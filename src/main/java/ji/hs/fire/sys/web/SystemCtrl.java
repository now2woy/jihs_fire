package ji.hs.fire.sys.web;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 시스템 CTRL
 * @author now2w
 *
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class SystemCtrl {
	private final ApplicationContext applicationContext;
	
	/**
	 * 시스템을 종료한다.
	 */
	@DeleteMapping("/sys/shutdown")
	public void shutdown() {
		log.info("시스템을 종료 합니다.");
		SpringApplication.exit(applicationContext, () -> 0);
	}
}
