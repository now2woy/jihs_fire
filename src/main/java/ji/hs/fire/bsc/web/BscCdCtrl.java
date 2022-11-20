package ji.hs.fire.bsc.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

/**
 * 코드 정보 CTRL
 * @author now2w
 *
 */
@Controller
@RequiredArgsConstructor
public class BscCdCtrl {
	/**
	 * 코드 컬럼 조회
	 * @return
	 */
	@GetMapping("/bsc/columns.do")
	public String column() {
		return "/bsc/columns";
	}
	
	/**
	 * 코드 조회
	 * @return
	 */
	@GetMapping("/bsc/codes.do")
	public String codes() {
		return "/bsc/codes";
	}
}
