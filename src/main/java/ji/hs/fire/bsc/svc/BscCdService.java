package ji.hs.fire.bsc.svc;

import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ji.hs.fire.bsc.mpr.BscCdMapper;
import ji.hs.fire.bsc.util.BscUtils;
import ji.hs.fire.bsc.vo.BscCdVO;
import lombok.extern.slf4j.Slf4j;

/**
 * 코드 상세 Service
 * @author now2w
 *
 */
@Slf4j
@Service
public class BscCdService {
	@Autowired
	private BscCdMapper bscCdMapper;
	
	/**
	 * 한국거래소 JSON URL
	 */
	@Value("${constant.krx.url.json}")
	private String krxJsonUrl;
	
	/**
	 * KRX 코드 수집
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void krxCdCollection() throws Exception {
		// 시장코드별로 URL 호출
		for(BscCdVO bscCdVO : bscCdMapper.selectAllByCdCol("MKT_CD")) {
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
			BscCdVO bscCdVO = new BscCdVO();
			
			bscCdVO.setCdCol("ITM_KND_CD");
			bscCdVO.setCdNm(json.get("KIND_STKCERT_TP_NM"));
			
			// 입력된 코드가 없을 경우 입력
			if(bscCdMapper.selectCountByCdColAndCdNm(bscCdVO) != 1) {
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
			BscCdVO bscCdVO = new BscCdVO();
			
			bscCdVO.setCdCol("ITM_CL_CD");
			bscCdVO.setCdNm(json.get("SECUGRP_NM"));
			
			// 입력된 코드가 없을 경우 입력
			if(bscCdMapper.selectCountByCdColAndCdNm(bscCdVO) != 1) {
				bscCdMapper.insert(bscCdVO);
			}
		}
		
		log.info("ITM_CL_CD 수집 종료");
	}
}
