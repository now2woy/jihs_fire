package ji.hs.fire.act.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ji.hs.fire.act.mpr.ActTrdMapper;
import ji.hs.fire.act.vo.ActTrdVO;
import lombok.RequiredArgsConstructor;

/**
 * 계좌 거래 정보 API CTRL
 * @author now2w
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/act/trades")
public class ActTrdApiCtrl {
	/**
	 * 계좌 Mapper
	 */
	private final ActTrdMapper actTrdMapper;
	
	/**
	 * 계좌 정보 목록 조회
	 * @param limit
	 * @param offset
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/{actSeq}")
	public ResponseEntity<Map<String, Object>> list(@PathVariable("actSeq") int actSeq
												  , @RequestParam(defaultValue = "10") int limit
												  , @RequestParam(defaultValue = "0") int offset) throws Exception {
		ActTrdVO actTrdVO = new ActTrdVO();
		
		actTrdVO.setActSeq(actSeq);
		actTrdVO.setLimit(limit);
		actTrdVO.setOffset(offset);
		
		Map<String, Object> result = new HashMap<>();
		result.put("count", actTrdMapper.selectCount(actTrdVO));
		result.put("data", actTrdMapper.selectAll(actTrdVO));
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	/**
	 * 계좌 정보 입력
	 * @param actVO
	 * @return
	 * @throws Exception
	 */
	@PostMapping("")
	public ResponseEntity<ActTrdVO> insert(@RequestBody ActTrdVO actTrdVO) throws Exception {
		actTrdMapper.insert(actTrdVO);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(actTrdVO);
	}
}
