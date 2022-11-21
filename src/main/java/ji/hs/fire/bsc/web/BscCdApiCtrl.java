package ji.hs.fire.bsc.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ji.hs.fire.bsc.mpr.BscCdMapper;
import ji.hs.fire.bsc.svc.BscCdService;
import ji.hs.fire.bsc.util.BscConstants;
import ji.hs.fire.bsc.util.BscUtils;
import ji.hs.fire.bsc.vo.BscCdVO;
import lombok.RequiredArgsConstructor;

/**
 * 코드 정보 API CTRL
 * @author now2w
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/codes")
public class BscCdApiCtrl {
	/**
	 * 코드 정보 Mapper
	 */
	private final BscCdMapper bscCdMapper;
	/**
	 * 코드 정보 Service
	 */
	private final BscCdService bscCdService;
	
	/**
	 * 코드 정보 목록 조회
	 * @return
	 */
	@GetMapping("")
	public ResponseEntity<List<BscCdVO>> list() throws Exception {
		return ResponseEntity.status(HttpStatus.OK).body(bscCdMapper.selectAll(new BscCdVO()));
	}
		
	/**
	 * 코드 정보 목록 조회
	 * @param cdCol
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/{cdCol}")
	public ResponseEntity<List<BscCdVO>> list(@PathVariable("cdCol") String cdCol) throws Exception {
		BscCdVO bscCdVO = new BscCdVO();
		bscCdVO.setCdCol(cdCol);
		
		return ResponseEntity.status(HttpStatus.OK).body(bscCdMapper.selectAll(bscCdVO));
	}
	
	/**
	 * 코드 정보 목록 조회
	 * @param cdCol
	 * @param cd
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/{cdCol}/{cd}")
	public ResponseEntity<List<BscCdVO>> list(@PathVariable("cdCol") String cdCol, @PathVariable("cd") String cd) throws Exception {
		BscCdVO bscCdVO = new BscCdVO();
		bscCdVO.setCdCol(cdCol);
		bscCdVO.setCd(cd);
		
		return ResponseEntity.status(HttpStatus.OK).body(bscCdMapper.selectAll(bscCdVO));
	}
	
	/**
	 * 코드 정보 입력
	 * @param bscCdVO
	 * @return
	 * @throws Exception
	 */
	@PostMapping("")
	public ResponseEntity<BscCdVO> insert(@RequestBody BscCdVO bscCdVO) throws Exception {
		
		bscCdMapper.insert(bscCdVO);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(bscCdVO);
	}
	
	/**
	 * 코드 정보 수집
	 * @return
	 */
	@PostMapping("/batch")
	public ResponseEntity<String> insertBatch() throws Exception {
		// 01시 동작
		if(BscUtils.isRunTime("01")){
			// KRX 코드 수집
			bscCdService.krxCdCollection();
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(BscConstants.SUCCESS);
	}
}
