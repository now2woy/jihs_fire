package ji.hs.fire.act.svc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
