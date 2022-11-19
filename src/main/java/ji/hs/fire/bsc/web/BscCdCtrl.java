package ji.hs.fire.bsc.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

/**
 * 코드 상세 CTRL
 * @author now2w
 *
 */
@Controller
@RequiredArgsConstructor
public class BscCdCtrl {
	@GetMapping("/bsc/codes.do")
	public String test() {
		
		
		return "/bsc/codes";
	}
}
