package ji.hs.fire.act.svc;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ji.hs.fire.act.mpr.ActPrdtMapper;
import ji.hs.fire.act.vo.ActPrdtVO;
import ji.hs.fire.act.vo.ActTrdVO;
import ji.hs.fire.bsc.svc.BscNoGenService;
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
	 * 계좌 상품 거래 정보를 입력 한다.
	 * @return
	 */
	@Transactional
	public int insert(ActTrdVO actTrdVO) throws Exception {
		int cnt = 0;
		
		for(int i = 1; i <= actTrdVO.getQty().intValue(); i++) {
			ActPrdtVO actPrdtVO = new ActPrdtVO();
			actPrdtVO.setPrdtTrdSeq(Integer.toString(bscNoGenService.generate("AC_PRDT_DT.PRDT_TRD_SEQ")));
			actPrdtVO.setActSeq(actTrdVO.getActSeq());
			actPrdtVO.setItmCd(actTrdVO.getItmCd());
			actPrdtVO.setByTrdSeq(actTrdVO.getTrdSeq());
			actPrdtVO.setByAmt(actTrdVO.getPrc());
			actPrdtVO.setByTlgrmMsgId(actTrdVO.getTlgrmMsgId());
			actPrdtVO.setByDt(actTrdVO.getTrdDt());
			
			cnt = cnt + actPrdtMapper.insert(actPrdtVO);
		}
		
		return cnt;
	}
	
	/**
	 * 계좌 상품 거래 정보를 수정 한다.
	 * @param actTrdVO
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public int update(ActTrdVO actTrdVO) throws Exception {
		int cnt = 0;
		ActPrdtVO paramVO = new ActPrdtVO();
		paramVO.setItmCd(actTrdVO.getItmCd());
		paramVO.setLimit(actTrdVO.getQty().intValue());
		
		List<ActPrdtVO> list = actPrdtMapper.selectAll(paramVO);
		
		for(ActPrdtVO actPrdtVO : list) {
			actPrdtVO.setSlTrdSeq(actTrdVO.getTrdSeq());
			actPrdtVO.setSlAmt(actTrdVO.getPrc());
			actPrdtVO.setSlDt(actTrdVO.getTrdDt());
			actPrdtVO.setSlTlgrmMsgId(actTrdVO.getTlgrmMsgId());
			
			cnt = cnt + actPrdtMapper.update(actPrdtVO);
		}
		
		return cnt;
	}
	
	/**
	 * 봇을 통해 계좌 상품 거래 정보를 입력 한다.
	 * @return
	 */
	@Transactional
	public int botInsert(ActTrdVO actTrdVO) throws Exception {
		return insert(actTrdVO);
	}
	
	/**
	 * 봇을 통해 계좌 상품 거래 정보의 계쫘일련번호를 수정 한다.
	 * @param byTlgrmMsgId
	 * @param actSeq
	 * @return
	 */
	public int updateActSeqByByTlgrmMsgId(String byTlgrmMsgId, String actSeq) throws Exception {
		ActPrdtVO actPrdtVO = new ActPrdtVO();
		actPrdtVO.setActSeq(actSeq);
		actPrdtVO.setByTlgrmMsgId(byTlgrmMsgId);
		
		return actPrdtMapper.updateActSeqByByTlgrmMsgId(actPrdtVO);
	}
}
