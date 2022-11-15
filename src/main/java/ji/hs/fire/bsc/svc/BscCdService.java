package ji.hs.fire.bsc.svc;

import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ji.hs.fire.bsc.mpr.BscCdMapper;
import ji.hs.fire.bsc.util.BscUtils;
import ji.hs.fire.bsc.vo.BscCdVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 코드 상세 Service
 * @author now2w
 *
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BscCdService {
	private final BscCdMapper bscCdMapper;
	
	/**
	 * 한국거래소 JSON URL
	 */
	@Value("${constant.krx.url.json}")
	private String krxJsonUrl;
	
	/**
	 * 코드 상세 목록 조회
	 * @param bscCdVO
	 * @return
	 * @throws Exception
	 */
	public List<BscCdVO> list(BscCdVO bscCdVO) throws Exception {
		return bscCdMapper.selectAll(bscCdVO);
	}
	
	/**
	 * 코드 상세 입력
	 * @param bscCdVO
	 * @return
	 * @throws Exception
	 */
	public BscCdVO insert(BscCdVO bscCdVO) throws Exception {
		bscCdMapper.insert(bscCdVO);
		
		return bscCdVO;
	}
	
	/**
	 * 코드 상세 수정
	 * @param bscCdVO
	 * @return
	 * @throws Exception
	 */
	public BscCdVO update(BscCdVO bscCdVO) throws Exception {
		bscCdMapper.update(bscCdVO);
		
		return bscCdVO;
	}
	
	/**
	 * 코드 상세 삭제
	 * @param bscCdVO
	 * @return
	 * @throws Exception
	 */
	public BscCdVO delete(BscCdVO bscCdVO) throws Exception {
		bscCdMapper.delete(bscCdVO);
		
		return null;
	}
	
	/**
	 * KRX 코드 수집
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void krxCdCollection() throws Exception {
		// 시장코드별로 URL 호출
		for(BscCdVO bscCdVO : bscCdMapper.selectAll(BscCdVO.builder().cdCol("MKT_CD").build())) {
			// KRX URL 호출
			Document doc = Jsoup.connect(krxJsonUrl)
				.data("bld", "dbms/MDC/STAT/standard/MDCSTAT01901")
				.data("mktId", bscCdVO.getCd())
				.get();
			
			List<Map<String, String>> outBlockList = (List<Map<String, String>>)BscUtils.jsonParse(doc.text()).get("OutBlock_1");
			
			// ITM_KND_CD 수집
			itmKndCdCollection(outBlockList);
			
			// ITM_CL_CD 수집
			itmClCdCollection(outBlockList);
		}
	}
	
	/**
	 * KRX > ITM_KND_CD 수집
	 * @param list
	 * @throws Exception
	 */
	private void itmKndCdCollection(List<Map<String, String>> outBlockList) throws Exception {
		log.info("ITM_KND_CD 수집 시작");
		
		for(Map<String, String> json : outBlockList) {
			BscCdVO bscCdVO = BscCdVO.builder()
					.cdCol("ITM_KND_CD")
					.cdNm(json.get("KIND_STKCERT_TP_NM"))
					.build();
			
			// 입력된 코드가 없을 경우 입력
			if(bscCdMapper.selectCount(bscCdVO) != 1) {
				bscCdMapper.insert(bscCdVO);
			}
		}
		
		log.info("ITM_KND_CD 수집 종료");
	}
	
	/**
	 * KRX > ITM_CL_CD 수집
	 * @param list
	 * @throws Exception
	 */
	private void itmClCdCollection(List<Map<String, String>> outBlockList) throws Exception {
		log.info("ITM_CL_CD 수집 시작");
		
		for(Map<String, String> json : outBlockList) {
			BscCdVO bscCdVO = BscCdVO.builder()
					.cdCol("ITM_CL_CD")
					.cdNm(json.get("SECUGRP_NM"))
					.build();
			
			// 입력된 코드가 없을 경우 입력
			if(bscCdMapper.selectCount(bscCdVO) != 1) {
				bscCdMapper.insert(bscCdVO);
			}
		}
		
		log.info("ITM_CL_CD 수집 종료");
	}
}
