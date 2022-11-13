package ji.hs.fire.bsc.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ji.hs.fire.bsc.svc.BscCdService;
import ji.hs.fire.bsc.vo.BscCdVO;

/**
 * 코드 상세 CTRL
 * @author now2w
 *
 */
@RestController
@RequestMapping("/api/bscCd")
public class BscCdCtrl {
	@Autowired
	private BscCdService bscCdService;
	
	/**
	 * 코드 상세 입력
	 * @param dartKeyVO
	 * @return
	 */
	@GetMapping("")
	public ResponseEntity<BscCdVO> insert() throws Exception {
		// KRX 코드 수집
		bscCdService.krxCdCollection();
		
		return ResponseEntity.status(HttpStatus.CREATED).body(null);
	}
}
