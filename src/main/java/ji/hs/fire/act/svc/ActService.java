package ji.hs.fire.act.svc;

import org.springframework.stereotype.Service;

import ji.hs.fire.act.mpr.ActMapper;
import ji.hs.fire.act.vo.ActVO;
import ji.hs.fire.bsc.svc.BscNoGenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 계좌 정보 Service
 * @author now2w
 *
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ActService {
	/**
	 * 계좌 Mapper
	 */
	private final ActMapper actMapper;
	/**
	 * 채번 Service
	 */
	private final BscNoGenService bscNoGenService;
	
	/**
	 * 계좌 정보 입력
	 * @param actVO
	 * @return
	 */
	public int insert(ActVO actVO) throws Exception {
		actVO.setActSeq(bscNoGenService.generate("AC_MT.ACT_SEQ"));
		
		return actMapper.insert(actVO);
	}
}
