package ji.hs.fire.act.svc;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ji.hs.fire.act.mpr.ActBlcAggMapper;
import ji.hs.fire.act.vo.ActBlcAggVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 계좌 잔고 집계 정보 Service
 * @author now2w
 *
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ActBlcAggService {
	/**
	 * 계좌 잔고 집계 정보 Mapper
	 */
	private final ActBlcAggMapper actBlcAggMapper;
	
	/**
	 * 계좌일련번호의 집계되지 않은 가장 오래된 정보를 집계한다.
	 * @param actSeq
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public Map<String, Object> blcAggregate(String actSeq) throws Exception {
		Map<String, Object> result = new HashMap<>();
		int cnt = 0;
		boolean isSuccess = false;
		
		try {
			ActBlcAggVO param = new ActBlcAggVO();
			param.setActSeq(actSeq);
			
			// 오류가 없을 경우 성공
			isSuccess = true;
			
			while(isSuccess) {
				ActBlcAggVO vo = actBlcAggMapper.selectOne(param);
				
				// 조회된 결과가 있을 경우
				if(vo != null) {
					// 집계 테이블에 자료 저장
					cnt += actBlcAggMapper.insert(vo);
					
					log.info("[계좌 잔고 집계] 계좌일련번호 {}의 {}일 자료 집계, 누적건수 {}", actSeq, vo.getAggDt(), cnt);
					
					// 조회된 결과가 없을 경우
				} else {
					isSuccess = false;
					log.info("[계좌 잔고 집계] 계좌일련번호 {}의 최종 집계 이후 거래 내역이 없습니다.", actSeq);
				}
			}
			
		}catch(Exception e) {
			log.error("[계좌 잔고 집계] ", e);
		}
		
		result.put("SUCCESS_CNT", cnt);
		result.put("IS_SUCCESS", isSuccess);
		
		return result;
	}
}
