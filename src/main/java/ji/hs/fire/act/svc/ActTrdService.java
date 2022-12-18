package ji.hs.fire.act.svc;

import org.springframework.stereotype.Service;

import ji.hs.fire.act.mpr.ActTrdMapper;
import ji.hs.fire.act.vo.ActTrdVO;
import ji.hs.fire.bsc.svc.BscNoGenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 계좌 거래 정보 Service
 * @author now2w
 *
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ActTrdService {
	/**
	 * 계좌 Mapper
	 */
	private final ActTrdMapper actTrdMapper;
	/**
	 * 채번 Service
	 */
	private final BscNoGenService bscNoGenService;
	
	/**
	 * 
	 * @param actTrdVO
	 * @return
	 * @throws Exception
	 */
	public int insert(ActTrdVO actTrdVO) throws Exception {
		actTrdVO.setTrdSeq(bscNoGenService.generate("AC_DT.TRD_SEQ"));
		
		return actTrdMapper.insert(actTrdVO);
	}
}
