package ji.hs.fire.bsc.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ji.hs.fire.bsc.mpr.BscBatchMapper;
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
	 * 배치 정보 Service
	 */
	private final BscBatchMapper bscBatchMapper;
	
	/**
	 * 배치 정보 목록 조회
	 * @return
	 */
	@GetMapping("")
	public ResponseEntity<List<BscBatchVO>> list(@RequestBody(required = false) BscBatchVO bscBatchVO) throws Exception {
		return ResponseEntity.status(HttpStatus.OK).body(bscBatchMapper.selectAll(bscBatchVO));
	}
	
	/**
	 * 배치 정보 입력
	 * @param bscBatchVO
	 * @return
	 * @throws Exception
	 */
	@PostMapping("")
	public ResponseEntity<BscBatchVO> insert(@RequestBody BscBatchVO bscBatchVO) throws Exception {
		
		bscBatchMapper.insert(bscBatchVO);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(bscBatchVO);
	}
}
