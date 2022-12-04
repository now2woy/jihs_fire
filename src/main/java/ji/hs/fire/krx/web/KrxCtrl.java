package ji.hs.fire.krx.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

/**
 * 한국거래소 정보 CTRL
 * @author now2w
 *
 */
@Controller
@RequiredArgsConstructor
public class KrxCtrl {
	/**
	 * 한국거래소 조회
	 * @return
	 */
	@GetMapping("/krx/stock.do")
	public String column() {
		return "/krx/stock";
	}
}
