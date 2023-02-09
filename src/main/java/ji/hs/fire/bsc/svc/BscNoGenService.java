package ji.hs.fire.bsc.svc;

import org.springframework.stereotype.Service;

import ji.hs.fire.bsc.mpr.BscNoGenMapper;
import ji.hs.fire.bsc.vo.BscNoGenVO;
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
		BscNoGenVO bscNoGenVO = bscNoGenMapper.selectOne(genKey);
		int num = 1;
		
		if(bscNoGenVO == null) {
			// 테이블에 값이 없기 때문에 입력한다.
			bscNoGenMapper.insert(genKey);
			
		} else {
			// 조회된 값에 1 증가
			num = num + bscNoGenVO.getNum().intValue();
			
			// 테이블에 값이 있기 때문에 수정한다.
			bscNoGenMapper.update(genKey);
		}
		
		return num;
	}
}
