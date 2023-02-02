package ji.hs.fire.act.web;

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
import org.springframework.web.multipart.MultipartFile;

import ji.hs.fire.act.svc.ActTrdService;
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
	 * 계좌 거래 Service
	 */
	private final ActTrdService actTrdService;
	
	/**
	 * 계좌 거래 정보 목록 조회
	 * @param limit
	 * @param offset
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/{actSeq}")
	public ResponseEntity<Map<String, Object>> list(@PathVariable("actSeq") String actSeq
												  , @RequestParam(defaultValue = "10") int limit
												  , @RequestParam(defaultValue = "0") int offset) throws Exception {
		ActTrdVO actTrdVO = new ActTrdVO();
		actTrdVO.setActSeq(actSeq);
		actTrdVO.setLimit(limit);
		actTrdVO.setOffset(offset);
		
		return ResponseEntity.status(HttpStatus.OK).body(actTrdService.list(actTrdVO));
	}
	
	/**
	 * 계좌 거래 정보 입력
	 * @param actVO
	 * @return
	 * @throws Exception
	 */
	@PostMapping("")
	public ResponseEntity<ActTrdVO> insert(@RequestBody ActTrdVO actTrdVO) throws Exception {
		actTrdService.insert(actTrdVO);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(actTrdVO);
	}
	
	/**
	 * 계좌 거래 정보 액셀 입력
	 * @param actVO
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/excelUpload")
	public ResponseEntity<ActTrdVO> excelUpload(@RequestParam(required = true) MultipartFile file
											  , @RequestParam(required = true) String actSeq
											  , @RequestParam(defaultValue = "") String note) throws Exception {
		
		actTrdService.excelUpload(file, actSeq, note);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(null);
	}
}
