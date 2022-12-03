package ji.hs.fire.krx.web;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ji.hs.fire.bsc.util.BscConstants;
import ji.hs.fire.bsc.util.BscUtils;
import ji.hs.fire.krx.svc.KrxService;
import lombok.RequiredArgsConstructor;

/**
 * 한국거래소 종목 마스터 CTRL
 * @author now2w
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/krxs")
public class KrxApiCtrl {
	/**
	 * 
	 */
	private final KrxService krxService;
	
	/**
	 * 한국거래소 종목 기본 정보 수집
	 * @param dartKeyVO
	 * @return
	 */
	@PostMapping("/batchs/bsc")
	public ResponseEntity<String> bscCollection() throws Exception {
		// 02시 실행
		if(BscUtils.isRunTime("02")){
			// 한국거래소 종목 기본 정보 수집
			krxService.bscCollection();
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(BscConstants.SUCCESS);
	}
	
	/**
	 * 한국거래소 종목 기본 정보 수집
	 * @param dartKeyVO
	 * @return
	 */
	@PostMapping("/batchs/trd")
	public ResponseEntity<Map<String, String>> trdCollection() throws Exception {
		// 한국거래소 종목 거래 정보 수집
		return ResponseEntity.status(HttpStatus.CREATED).body(krxService.trdCollection());
	}
}
