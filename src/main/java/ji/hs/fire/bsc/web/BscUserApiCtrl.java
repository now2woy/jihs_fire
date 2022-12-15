package ji.hs.fire.bsc.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ji.hs.fire.bsc.mpr.BscUserMapper;
import ji.hs.fire.bsc.vo.BscUserVO;
import lombok.RequiredArgsConstructor;

/**
 * 사용자 정보 API CTRL
 * @author now2w
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class BscUserApiCtrl {
	/**
	 * 사용자 정보 Mapper
	 */
	private final BscUserMapper bscUserMapper;
	
	/**
	 * 사용자 정보 목록 조회
	 * @param limit
	 * @param offset
	 * @return
	 * @throws Exception
	 */
	@GetMapping("")
	public ResponseEntity<Map<String, Object>> list(@RequestParam(defaultValue = "10") int limit
												  , @RequestParam(defaultValue = "0") int offset) throws Exception {
		
		BscUserVO bscUserVO = new BscUserVO();
		bscUserVO.setLimit(limit);
		bscUserVO.setOffset(offset);
		
		Map<String, Object> result = new HashMap<>();
		result.put("count", bscUserMapper.selectCount(bscUserVO));
		result.put("data", bscUserMapper.selectAll(bscUserVO));
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	/**
	 * 사용자 정보 입력
	 * @param bscUserVO
	 * @return
	 * @throws Exception
	 */
	@PostMapping("")
	public ResponseEntity<BscUserVO> insert(@RequestBody BscUserVO bscUserVO) throws Exception {
		bscUserMapper.insert(bscUserVO);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(bscUserVO);
	}
	
	/**
	 * 사용자 정보 수정
	 * @param bscUserVO
	 * @return
	 * @throws Exception
	 */
	@PutMapping("")
	public ResponseEntity<BscUserVO> update(@RequestBody BscUserVO bscUserVO) throws Exception {
		bscUserMapper.update(bscUserVO);
		
		return ResponseEntity.status(HttpStatus.OK).body(bscUserVO);
	}
}
