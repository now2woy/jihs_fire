package ji.hs.fire.dart.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ji.hs.fire.bsc.util.BscUtils;
import ji.hs.fire.dart.svc.DartFnlttService;
import lombok.RequiredArgsConstructor;

/**
 * 전자공시시스템 재무제표 정보 API CTRL
 * @author now2w
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dart/fnltts")
public class DartFnlttApiCtrl {
	/**
	 * 전자공시시스템 재무제표 Service
	 */
	private final DartFnlttService dartFnlttService;
	/**
	 * 성공 리턴 값
	 */
	private static final String SUCCESS = "{message : \"success\"}";
	
	/**
	 * 전자공시시스템 재무제표 배치
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/batch")
	public ResponseEntity<String> batch(@RequestParam(defaultValue = "1") int limit) throws Exception {
		// 03시 실행
		if(BscUtils.isRunTime("03")){
			// 비동기 방식으로 데이터 수집
			dartFnlttService.dartFnlttCollection(limit);
		}
		
		// 성공 리턴
		return ResponseEntity.status(HttpStatus.CREATED).body(SUCCESS);
	}
}
