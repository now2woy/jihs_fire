package ji.hs.fire.bsc.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ji.hs.fire.bsc.mpr.BscCdColMapper;
import ji.hs.fire.bsc.vo.BscCdColVO;
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
	 * 코드 컬럼 정보 Mapper
	 */
	private final BscCdColMapper bscCdColMapper;
	
	/**
	 * 코드 컬럼 정보 조회
	 * @return
	 */
	@GetMapping("")
	public ResponseEntity<List<BscCdColVO>> list() throws Exception {
		return ResponseEntity.status(HttpStatus.OK).body(bscCdColMapper.selectAll());
	}
	
	/**
	 * 코드 컬럼 정보 입력
	 * @param bscCdColVO
	 * @return
	 * @throws Exception
	 */
	@PostMapping("")
	public ResponseEntity<BscCdColVO> insert(@RequestBody BscCdColVO bscCdColVO) throws Exception {
		bscCdColMapper.insert(bscCdColVO);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(bscCdColVO);
	}
	
	/**
	 * 코드 컬럼 정보 수정
	 * @param bscCdColVO
	 * @return
	 * @throws Exception
	 */
	@PutMapping("")
	public ResponseEntity<BscCdColVO> update(@RequestBody BscCdColVO bscCdColVO) throws Exception {
		bscCdColMapper.update(bscCdColVO);
		
		return ResponseEntity.status(HttpStatus.OK).body(bscCdColVO);
	}
}
