package ji.hs.fire.dart.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ji.hs.fire.dart.svc.DartItmService;
import ji.hs.fire.dart.vo.DartKeyVO;
import lombok.extern.slf4j.Slf4j;

/**
 * 전자공시시스템 종목 마스터 CTRL
 * @author now2w
 *
 */
@Slf4j
@RestController
@RequestMapping("/api/dartItm")
public class DartItmCtrl {
	@Autowired
	private DartItmService dartItmService;
	
	/**
	 * 전자공시시스템 종목 입력
	 * @return
	 * @throws Exception
	 */
	@GetMapping("")
	public ResponseEntity<DartKeyVO> insert() throws Exception {
		
		//  전자공시시스템 종목 코드 파일 다운로드
		if(dartItmService.dartCoprCdDownload()) {
			log.info("전자공시시스템 종목 코드 파일 다운로드 성공");
			
			// 다운로드한 파일을 처리 한다.
			dartItmService.dartCoprCdCollection();
		}
		
		// 성공 리턴
		return ResponseEntity.status(HttpStatus.CREATED).body(null);
	}
}
