package ji.hs.fire.bsc.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ji.hs.fire.bsc.svc.BscCdService;
import ji.hs.fire.bsc.vo.BscCdVO;
import lombok.RequiredArgsConstructor;

/**
 * 코드 상세 CTRL
 * @author now2w
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/codes")
public class BscCdCtrl {
	private final BscCdService bscCdService;
	
	/**
	 * 코드 목록 조회
	 * @return
	 */
	@GetMapping("")
	public ResponseEntity<List<BscCdVO>> list() throws Exception {
		return ResponseEntity.status(HttpStatus.OK).body(bscCdService.list(BscCdVO.builder().build()));
	}
		
	/**
	 * 코드 목록 조회
	 * @param cdCol
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/{cdCol}")
	public ResponseEntity<List<BscCdVO>> list(@PathVariable("cdCol") String cdCol) throws Exception {
		return ResponseEntity.status(HttpStatus.OK).body(bscCdService.list(BscCdVO.builder().cdCol(cdCol).build()));
	}
	
	/**
	 * 코드 목록 조회
	 * @param cdCol
	 * @param cd
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/{cdCol}/{cd}")
	public ResponseEntity<List<BscCdVO>> list(@PathVariable("cdCol") String cdCol, @PathVariable("cd") String cd) throws Exception {
		return ResponseEntity.status(HttpStatus.OK).body(bscCdService.list(BscCdVO.builder().cdCol(cdCol).cd(cd).build()));
	}
	
	/**
	 * 코드 상세 입력
	 * @param bscCdVO
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/")
	public ResponseEntity<BscCdVO> insert(@RequestBody BscCdVO bscCdVO) throws Exception {
		return ResponseEntity.status(HttpStatus.CREATED).body(bscCdService.insert(bscCdVO));
	}
	
	/**
	 * 코드 상세 수정
	 * @param cdCol
	 * @param cd
	 * @param bscCdVO
	 * @return
	 * @throws Exception
	 */
	@PutMapping("/")
	public ResponseEntity<BscCdVO> update(@RequestBody BscCdVO bscCdVO) throws Exception {
		return ResponseEntity.status(HttpStatus.CREATED).body(bscCdService.update(bscCdVO));
	}
	
	/**
	 * 코드 상세 입력
	 * @param cdCol
	 * @param cd
	 * @param bscCdVO
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping("/")
	public ResponseEntity<BscCdVO> delete(@RequestBody BscCdVO bscCdVO) throws Exception {
		return ResponseEntity.status(HttpStatus.CREATED).body(bscCdService.delete(bscCdVO));
	}
	
	/**
	 * 코드 상세 입력
	 * @return
	 */
	@PostMapping("/batch")
	public ResponseEntity<BscCdVO> insertBatch() throws Exception {
		// KRX 코드 수집
		bscCdService.krxCdCollection();
		
		return ResponseEntity.status(HttpStatus.CREATED).body(null);
	}
}
