package ji.hs.fire.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloCtrl {
	@GetMapping("/test")
	public String test() throws Exception {
		return "{name : hello World!!!}";
	}
}
