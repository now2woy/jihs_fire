package ji.hs.fire.act.web;

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

import ji.hs.fire.act.mpr.ActMapper;
import ji.hs.fire.act.svc.ActService;
import ji.hs.fire.act.vo.ActVO;
import lombok.RequiredArgsConstructor;

/**
 * 계좌 정보 API CTRL
 * @author now2w
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/act/accounts")
public class ActApiCtrl {
	/**
	 * 계좌 Mapper
	 */
	private final ActMapper actMapper;
	/**
	 * 계좌 Mapper
	 */
	private final ActService actService;
	
	/**
	 * 계좌 정보 목록 조회
	 * @param limit
	 * @param offset
	 * @return
	 * @throws Exception
	 */
	@GetMapping("")
	public ResponseEntity<Map<String, Object>> list(@RequestParam(defaultValue = "10") int limit
											   , @RequestParam(defaultValue = "0") int offset) throws Exception {
		ActVO actVO = new ActVO();
		
		actVO.setLimit(limit);
		actVO.setOffset(offset);
		
		Map<String, Object> result = new HashMap<>();
		result.put("count", actMapper.selectCount(actVO));
		result.put("data", actMapper.selectAll(actVO));
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	/**
	 * 계좌 정보 입력
	 * @param actVO
	 * @return
	 * @throws Exception
	 */
	@PostMapping("")
	public ResponseEntity<ActVO> insert(@RequestBody ActVO actVO) throws Exception {
		actService.insert(actVO);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(actVO);
	}
}
