package ji.hs.fire.krx.svc;

import org.springframework.stereotype.Service;

import ji.hs.fire.bsc.svc.BscNoGenService;
import ji.hs.fire.krx.mpr.KrxItmSynMapper;
import ji.hs.fire.krx.vo.KrxItmSynVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 한국거래소 주식종목 동의어 Service
 * @author now2w	
 *
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class KrxItmSynService {
	/**
	 * 한국거래소 주식종목 동의어 Mapper
	 */
	private final KrxItmSynMapper krxItmSynMapper;
	/**
	 * 채번 Service
	 */
	private final BscNoGenService bscNoGenService;
	
	/**
	 * 한국거래소 주식종목 동의어 입력
	 * @param itmCd
	 * @param itmNm
	 */
	public void insert(String itmCd, String itmNm) throws Exception {
		KrxItmSynVO krxItmSynVO = new KrxItmSynVO();
		krxItmSynVO.setItmCd(itmCd);
		krxItmSynVO.setItmNm(itmNm);
		
		if(krxItmSynMapper.selectCount(krxItmSynVO) == 0) {
			krxItmSynVO.setSynSeq(bscNoGenService.generate("KX_ITM_SYN_DT.SYN_SEQ"));
			krxItmSynMapper.insert(krxItmSynVO);
		}
	}
	
	/**
	 * 종목코드를 종목명으로 조회
	 * @param itmNm
	 * @return
	 */
	public String selectItmCdByItmNm(String itmNm) throws Exception {
		return krxItmSynMapper.selectItmCdByItmNm(itmNm);
	}
}
