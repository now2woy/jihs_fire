package ji.hs.fire.krx.web;

import java.util.HashMap;
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
import ji.hs.fire.krx.mpr.KrxItmMapper;
import ji.hs.fire.krx.svc.KrxService;
import ji.hs.fire.krx.svc.KrxTrdService;
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
	 * 한국거래소 Service
	 */
	private final KrxService krxService;
	/**
	 * 한국거래소 Service
	 */
	private final KrxTrdService krxTrdService;
	
	/**
	 * 한국거래소 종목 목록 조회
	 * @param schBatchCd
	 * @param schExeYn
	 * @return
	 * @throws Exception
	 */
	@GetMapping("")
	public ResponseEntity<Map<String, Object>> list(@RequestParam(required = false) String schMktCd
												  , @RequestParam(required = false) String schItmNm
												  , @RequestParam(required = false) String schSpacYn) throws Exception {
		KrxItmVO krxItmVO = new KrxItmVO();
		krxItmVO.setMktCd(schMktCd);
		krxItmVO.setItmNm(schItmNm);
		krxItmVO.setSpacYn(schSpacYn);
		
		Map<String, Object> result = new HashMap<>();
		result.put("count", krxItmMapper.selectCount(krxItmVO));
		result.put("data", krxItmMapper.selectAll(krxItmVO));
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
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
	 * 한국거래소 종목 거래 정보 수집
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/batchs/trd")
	public ResponseEntity<Map<String, String>> trdCollection(@RequestParam(defaultValue = "10") int limit) throws Exception {
		Map<String, String> result = null;
		// 04시 실행
		if(BscUtils.isRunTime("04")){
			// 한국거래소 종목 거래 정보 수집
			result = krxTrdService.trdCollection(limit);
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}
}
