package ji.hs.fire.krx.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ji.hs.fire.krx.svc.KrxItmService;
import ji.hs.fire.krx.vo.KrxItmVO;
import lombok.RequiredArgsConstructor;

/**
 * 한국거래소 종목 마스터 CTRL
 * @author now2w
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/krxItm")
public class KrxItmCtrl {
	private final KrxItmService krxItmService;
	
	/**
	 * 한국거래소 종목 기본 정보 수집
	 * @param dartKeyVO
	 * @return
	 */
	@GetMapping("")
	public ResponseEntity<KrxItmVO> insert() throws Exception {
		// 한국거래소 종목 기본 정보 수집
		krxItmService.krxItmCollection();
		
		// 한국거래소 종목 스팩여부 정보 수집
		krxItmService.krxItmSpacYnCollection();
		
		return ResponseEntity.status(HttpStatus.CREATED).body(null);
	}
}
