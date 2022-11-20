package ji.hs.fire.bsc.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ji.hs.fire.bsc.svc.BscCdService;
import ji.hs.fire.bsc.vo.BscCdVO;
import lombok.RequiredArgsConstructor;

/**
 * 코드 컬럼 정보 API CTRL
 * @author now2w
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/code/columns")
public class BscCdColApiCtrl {
	/**
	 * 
	 */
	private final BscCdService bscCdService;
	
	/**
	 * 코드 컬럼 조회
	 * @return
	 */
	@GetMapping("")
	public ResponseEntity<List<BscCdVO>> list() throws Exception {
		return ResponseEntity.status(HttpStatus.OK).body(bscCdService.listCdColumn());
	}
}
