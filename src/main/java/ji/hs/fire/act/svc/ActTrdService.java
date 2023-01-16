package ji.hs.fire.act.svc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import ji.hs.fire.act.mpr.ActMapper;
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
	private final ActMapper actMapper;
	/**
	 * 계좌 Mapper
	 */
	private final ActTrdMapper actTrdMapper;
	/**
	 * 채번 Service
	 */
	private final BscNoGenService bscNoGenService;
	
	/**
	 * 계좌 거래 정보 목록
	 * @param actTrdVO
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> list(ActTrdVO actTrdVO) throws Exception {
		Map<String, Object> result = new HashMap<>();
		result.put("count", actTrdMapper.selectCount(actTrdVO));
		result.put("data", actTrdMapper.selectAll(actTrdVO));
		
		List<String> trdCds = new ArrayList<>();
		
		trdCds.add("00001");
		
		actTrdVO.setTrdCds(trdCds);
		
		// 총입금액
		result.put("inSumAmt", actTrdMapper.selectSumAmt(actTrdVO));
		
		trdCds = new ArrayList<>();
		
		trdCds.add("00002");
		
		actTrdVO.setTrdCds(trdCds);
		
		// 총출금액
		result.put("outSumAmt", actTrdMapper.selectSumAmt(actTrdVO));
		
		trdCds = new ArrayList<>();
		
		trdCds.add("00001");
		trdCds.add("00002");
		
		actTrdVO.setTrdCds(trdCds);
		
		// 입출금 합계
		result.put("inOutSumAmt", actTrdMapper.selectSumAmt(actTrdVO));
		
		trdCds = new ArrayList<>();
		
		trdCds.add("00003");
		
		actTrdVO.setTrdCds(trdCds);
		
		// 이자 합계
		result.put("itrstSumAmt", actTrdMapper.selectSumAmt(actTrdVO));
		
		trdCds = new ArrayList<>();
		
		trdCds.add("00004");
		trdCds.add("00005");
		
		actTrdVO.setTrdCds(trdCds);
		
		// 배당 분배금 합계
		result.put("dvdnSumAmt", actTrdMapper.selectSumAmt(actTrdVO));
		
		return result;
	}
	
	/**
	 * 계좌 거래 정보 입력
	 * @param actTrdVO
	 * @return
	 * @throws Exception
	 */
	public int insert(ActTrdVO actTrdVO) throws Exception {
		actTrdVO.setTrdSeq(Integer.toString(bscNoGenService.generate("AC_DT.TRD_SEQ")));
		
		return actTrdMapper.insert(actTrdVO);
	}
	
	/**
	 * 봇을 통해 계좌 거래 정보를 입력 한다.
	 * @param actNo
	 * @param trdCd
	 * @param amt
	 * @param itmCd
	 * @param qty
	 * @param note
	 * @param trdDt
	 * @param edDt
	 * @return
	 * @throws Exception
	 */
	public int botInsert(String actNo, String trdCd, String amt, String itmCd, String qty, String note, String trdDt, String edDt, String tlgrmMsgId) throws Exception {
		ActTrdVO actTrdVO = new ActTrdVO();
		
		// 계좌번호로 계좌일련번호 조회
		actTrdVO.setActSeq(actMapper.selectAsActSeq(actNo.replaceAll("\\*", "_")));
		actTrdVO.setRelTrdSeq("0");
		actTrdVO.setTrdCd(trdCd);
		actTrdVO.setAmt(amt.replaceAll(",", ""));
		actTrdVO.setItmCd(itmCd);
		actTrdVO.setQty(qty);
		actTrdVO.setNote(note);
		actTrdVO.setTrdDt(trdDt);
		actTrdVO.setEdDt(edDt);
		actTrdVO.setTlgrmMsgId(tlgrmMsgId);
		
		return insert(actTrdVO);
	}
}
