package ji.hs.fire.act.svc;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import ji.hs.fire.act.mpr.ActPrdtMapper;
import ji.hs.fire.act.vo.ActPrdtVO;
import ji.hs.fire.act.vo.ActTrdVO;
import ji.hs.fire.bsc.svc.BscNoGenService;
import ji.hs.fire.bsc.util.BscUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 계좌 상품 거래 정보 Service
 * @author now2w
 *
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ActPrdtService {
	/**
	 * 계좌 상품 거래 정보 Mapper
	 */
	private final ActPrdtMapper actPrdtMapper;
	/**
	 * 채번 Service
	 */
	private final BscNoGenService bscNoGenService;
	
	/**
	 * 봇을 통해 계좌 상품 거래 정보를 입력 한다.
	 * @return
	 */
	public int botInsert(ActTrdVO actTrdVO) throws Exception {
		int cnt = 0;
		
		for(int i = 1; i <= Integer.parseInt(actTrdVO.getQty()); i++) {
			ActPrdtVO actPrdtVO = new ActPrdtVO();
			actPrdtVO.setPrdtTrdSeq(Integer.toString(bscNoGenService.generate("AC_PRDT_DT.PRDT_TRD_SEQ")));
			actPrdtVO.setItmCd(actTrdVO.getItmCd());
			actPrdtVO.setByTrdSeq(actTrdVO.getTrdSeq());
			actPrdtVO.setByAmt(BscUtils.divide(new BigDecimal(actTrdVO.getAmt()), new BigDecimal(actTrdVO.getQty()), 0));
			actPrdtVO.setByTlgrmMsgId(actTrdVO.getTlgrmMsgId());
			actPrdtVO.setByDt(actTrdVO.getTrdDt());
			
			cnt = cnt + actPrdtMapper.insert(actPrdtVO);
		}
		
		return cnt;
	}
}
