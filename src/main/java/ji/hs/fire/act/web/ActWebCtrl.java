package ji.hs.fire.act.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

/**
 * 계좌
 * @author now2w
 *
 */
@Controller
@RequiredArgsConstructor
public class ActWebCtrl {
	/**
	 * 계좌 조회
	 * @return
	 */
	@GetMapping("/act/account.do")
	public String act() {
		return "/act/account";
	}
}
