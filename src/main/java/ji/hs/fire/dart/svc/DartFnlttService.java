package ji.hs.fire.dart.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ji.hs.fire.dart.mpr.DartKeyMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DartFnlttService {
	/**
	 * DART 종목 / 년도 / 분기 재무제표 URL
	 */
	@Value("${constant.dart.url.fnltt}")
	private String dartFnlttUrl;
	
	@Autowired
	private DartKeyMapper dartKeyMapper;
	
	public void dartFnlttCollection(String yr, String qt) throws Exception {
		log.info("{}년도 {}분기 재무제표 수집 시작", yr, qt);
		
		
		
		log.info("{}년도 {}분기 재무제표 수집 종료", yr, qt);
	}
}
