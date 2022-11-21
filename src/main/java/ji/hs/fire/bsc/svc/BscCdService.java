package ji.hs.fire.bsc.svc;

import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ji.hs.fire.bsc.mpr.BscCdMapper;
import ji.hs.fire.bsc.util.BscConstants;
import ji.hs.fire.bsc.util.BscUtils;
import ji.hs.fire.bsc.vo.BscCdVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 코드 정보 Service
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
	 * KRX 코드 수집
	 * @throws Exception
	 */
	@Async
	@Transactional
	@SuppressWarnings("unchecked")
	public void krxCdCollection() throws Exception {
		MDC.put(BscConstants.LOG_KEY, BscConstants.LOG_KEY_BSC);
		
		BscCdVO bscCdVO = new BscCdVO();
		bscCdVO.setCdCol("MKT_CD");
		
		// 시장코드별로 URL 호출
		for(BscCdVO resultVO : bscCdMapper.selectAll(bscCdVO)) {
			// KRX URL 호출
			Document doc = Jsoup.connect(krxJsonUrl)
				.data("bld", "dbms/MDC/STAT/standard/MDCSTAT01901")
				.data("mktId", resultVO.getCd())
				.get();
			
			List<Map<String, String>> outBlockList = (List<Map<String, String>>)BscUtils.jsonParse(doc.text()).get("OutBlock_1");
			
			// ITM_KND_CD 수집
			itmKndCdCollection(outBlockList);
			
			// ITM_CL_CD 수집
			itmClCdCollection(outBlockList);
		}
		
		MDC.clear();
	}
	
	/**
	 * KRX > ITM_KND_CD 수집
	 * @param list
	 * @throws Exception
	 */
	private void itmKndCdCollection(List<Map<String, String>> outBlockList) throws Exception {
		log.info("ITM_KND_CD 수집 시작");
		
		for(Map<String, String> json : outBlockList) {
			BscCdVO bscCdVO = new BscCdVO();
			bscCdVO.setCdCol("ITM_KND_CD");
			bscCdVO.setCdNm(json.get("KIND_STKCERT_TP_NM"));
			
			// 입력된 코드가 없을 경우 입력
			if(bscCdMapper.selectCount(bscCdVO) != 1) {
				bscCdMapper.insertBatch(bscCdVO);
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
			BscCdVO bscCdVO = new BscCdVO();
			bscCdVO.setCdCol("ITM_CL_CD");
			bscCdVO.setCdNm(json.get("SECUGRP_NM"));
			
			// 입력된 코드가 없을 경우 입력
			if(bscCdMapper.selectCount(bscCdVO) != 1) {
				bscCdMapper.insertBatch(bscCdVO);
			}
		}
		
		log.info("ITM_CL_CD 수집 종료");
	}
}
