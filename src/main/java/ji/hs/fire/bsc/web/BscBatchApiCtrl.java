package ji.hs.fire.bsc.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ji.hs.fire.bsc.mpr.BscBatchMapper;
import ji.hs.fire.bsc.svc.BscBatchService;
import ji.hs.fire.bsc.vo.BscBatchVO;
import lombok.RequiredArgsConstructor;

/**
 * 배치 정보 API CTRL
 * @author now2w
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/batchs")
public class BscBatchApiCtrl {
	/**
	 * 배치 정보 Mapper
	 */
	private final BscBatchMapper bscBatchMapper;
	/**
	 * 배치 정보 Service
	 */
	private final BscBatchService bscBatchService;
	
	/**
	 * 배치 정보 목록 조회
	 * @param schBatchCd
	 * @param schExeYn
	 * @param limit
	 * @param offset
	 * @return
	 * @throws Exception
	 */
	@GetMapping("")
	public ResponseEntity<Map<String, Object>> list(@RequestParam(required = false) String schBatchCd
											   , @RequestParam(required = false) String schExeYn
											   , @RequestParam(defaultValue = "10") int limit
											   , @RequestParam(defaultValue = "0") int offset) throws Exception {
		BscBatchVO bscBatchVO = new BscBatchVO();
		bscBatchVO.setBatchCd(schBatchCd);
		bscBatchVO.setExeYn(schExeYn);
		bscBatchVO.setLimit(limit);
		bscBatchVO.setOffset(offset);
		bscBatchVO.setOrder(2);
		
		Map<String, Object> result = new HashMap<>();
		result.put("count", bscBatchMapper.selectCount(bscBatchVO));
		result.put("data", bscBatchMapper.selectAll(bscBatchVO));
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	/**
	 * 배치 정보 입력
	 * @param bscBatchVO
	 * @return
	 * @throws Exception
	 */
	@PostMapping("")
	public ResponseEntity<BscBatchVO> insert(@RequestBody BscBatchVO bscBatchVO) throws Exception {
		
		bscBatchService.insert(bscBatchVO);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(bscBatchVO);
	}
}
