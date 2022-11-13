package ji.hs.fire.krx.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ji.hs.fire.krx.service.KrxItmService;
import ji.hs.fire.krx.vo.KrxItmVO;

/**
 * 한국거래소 종목 마스터 CTRL
 * @author now2w
 *
 */
@RestController
@RequestMapping("/api/krxItm")
public class KrxItmCtrl {
	@Autowired
	private KrxItmService krxItmService;
	
	/**
	 * 한국거래소 종목 기본 정보 수집
	 * @param dartKeyVO
	 * @return
	 */
	@GetMapping("")
	public ResponseEntity<KrxItmVO> insert() throws Exception {
		krxItmService.krxItmCollection();
		
		return ResponseEntity.status(HttpStatus.CREATED).body(null);
	}
}
