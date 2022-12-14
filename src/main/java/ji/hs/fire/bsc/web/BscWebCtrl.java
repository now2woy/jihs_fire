package ji.hs.fire.bsc.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

/**
 * 기본 정보 CTRL
 * @author now2w
 *
 */
@Controller
@RequiredArgsConstructor
public class BscWebCtrl {
	/**
	 * 사용자 조회
	 * @return
	 */
	@GetMapping("/bsc/user.do")
	public String user() {
		return "/bsc/user";
	}
	/**
	 * 코드 컬럼 조회
	 * @return
	 */
	@GetMapping("/bsc/column.do")
	public String column() {
		return "/bsc/column";
	}
	
	/**
	 * 코드 조회
	 * @return
	 */
	@GetMapping("/bsc/code.do")
	public String code() {
		return "/bsc/code";
	}
	
	/**
	 * 배치 조회
	 * @return
	 */
	@GetMapping("/bsc/batch.do")
	public String batch() {
		return "/bsc/batch";
	}
}
