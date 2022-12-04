package ji.hs.fire.krx.web;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ji.hs.fire.bsc.util.BscConstants;
import ji.hs.fire.bsc.util.BscUtils;
import ji.hs.fire.bsc.vo.BscBatchVO;
import ji.hs.fire.krx.mpr.KrxItmMapper;
import ji.hs.fire.krx.svc.KrxService;
import ji.hs.fire.krx.vo.KrxItmVO;
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
	 * KX_ITM_MT 쿼리
	 */
	private final KrxItmMapper krxItmMapper;
	/**
	 * 
	 */
	private final KrxService krxService;
	
	/**
	 * 배치 정보 목록 조회
	 * @param schBatchCd
	 * @param schExeYn
	 * @return
	 * @throws Exception
	 */
	@GetMapping("")
	public ResponseEntity<List<KrxItmVO>> list(@RequestParam(required = false) String schMktCd, @RequestParam(required = false) String schItmNm, @RequestParam(required = false) String schSpacYn) throws Exception {
		KrxItmVO krxItmVO = new KrxItmVO();
		krxItmVO.setMktCd(schMktCd);
		krxItmVO.setItmNm(schItmNm);
		krxItmVO.setSpacYn(schSpacYn);
		return ResponseEntity.status(HttpStatus.OK).body(krxItmMapper.selectAll(krxItmVO));
	}
	
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
