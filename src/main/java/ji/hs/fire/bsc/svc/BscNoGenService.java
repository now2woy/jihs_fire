package ji.hs.fire.bsc.svc;

import org.springframework.stereotype.Service;

import ji.hs.fire.bsc.mpr.BscNoGenMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 채번 Service
 * @author now2w
 *
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BscNoGenService {
	/**
	 * 채번 Mapper
	 */
	private final BscNoGenMapper bscNoGenMapper;
	
	/**
	 * 채번
	 * @param genKey
	 * @return
	 * @throws Exception
	 */
	public int generate(String genKey) throws Exception {
		int num = bscNoGenMapper.selectOne(genKey);
		
		// 채번된 값이 0일 경우
		if(num == 0) {
			// 값을 1 증가 한다.
			num = num + 1;
			
			// 테이블에 값이 없기 때문에 입력한다.
			bscNoGenMapper.insert(genKey);
			
		// 채번된 값이 0이 아닐 경우
		}else {
			// 테이블에 값이 있기 때문에 수정한다.
			bscNoGenMapper.update(genKey);
		}
		
		return num;
	}
}
