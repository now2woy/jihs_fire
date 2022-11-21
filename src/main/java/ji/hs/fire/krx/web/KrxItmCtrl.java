package ji.hs.fire.krx.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ji.hs.fire.bsc.util.BscUtils;
import ji.hs.fire.krx.svc.KrxItmService;
import lombok.RequiredArgsConstructor;

/**
 * 한국거래소 종목 마스터 CTRL
 * @author now2w
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/krxs")
public class KrxItmCtrl {
	/**
	 * 
	 */
	private final KrxItmService krxItmService;
	
	private static final String MSG_SUCCESS = "success";
	
	/**
	 * 한국거래소 종목 기본 정보 수집
	 * @param dartKeyVO
	 * @return
	 */
	@PostMapping("/batch")
	public ResponseEntity<String> insert() throws Exception {
		// 02시 실행
		if(BscUtils.isRunTime("02")){
			// 한국거래소 종목 기본 정보 수집
			krxItmService.krxBscCollection();
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(MSG_SUCCESS);
	}
}
