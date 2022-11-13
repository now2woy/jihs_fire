package ji.hs.fire.krx.svc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ji.hs.fire.bsc.mpr.BscCdMapper;
import ji.hs.fire.bsc.util.BscUtils;
import ji.hs.fire.bsc.vo.BscCdVO;
import ji.hs.fire.krx.mpr.KrxItmMapper;
import ji.hs.fire.krx.vo.KrxItmVO;
import lombok.extern.slf4j.Slf4j;

/**
 * 한국거래소 종목 마스터 Service
 * @author now2w
 *
 */
@Slf4j
@Service
public class KrxItmService {
	@Autowired
	private BscCdMapper bscCdMapper;
	
	@Autowired
	private KrxItmMapper krxItmMapper;
	
	/**
	 * 한국거래소 JSON URL
	 */
	@Value("${constant.krx.url.json}")
	private String krxJsonUrl;
	
	/**
	 * 한국거래소 종목 수집
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public void krxItmCollection() throws Exception {
		Map<String, String> itmKndCd = createCdMap("ITM_KND_CD");
		Map<String, String> itmClCd = createCdMap("ITM_CL_CD");
		
		// 시장코드별로 URL 호출
		for(BscCdVO bscCdVO : bscCdMapper.selectAllByCdCol("MKT_CD")) {
			log.info("{} 수집 시작", bscCdVO.getCd());
			
			// KRX URL 호출
			Document doc = Jsoup.connect(krxJsonUrl)
				.data("bld", "dbms/MDC/STAT/standard/MDCSTAT01901")
				.data("mktId", bscCdVO.getCd())
				.get();
			
			for(Map<String, String> json : (List<Map<String, String>>)BscUtils.jsonParse(doc.text()).get("OutBlock_1")) {
				KrxItmVO krxItmVO = new KrxItmVO();
				
				krxItmVO.setItmCd(json.get("ISU_SRT_CD"));
				krxItmVO.setItmNm(json.get("ISU_NM"));
				krxItmVO.setMktCd(bscCdVO.getCd());
				krxItmVO.setPubDt(json.get("LIST_DD"));
				krxItmVO.setStdItmCd(json.get("ISU_CD"));
				krxItmVO.setItmKndCd(itmKndCd.get("KIND_STKCERT_TP_NM"));
				krxItmVO.setItmClCd(itmClCd.get("SECUGRP_NM"));
				
				if(krxItmMapper.selectCount(krxItmVO) != 1) {
					krxItmMapper.insert(krxItmVO);
				}
			}
			
			log.info("{} 수집 종료", bscCdVO.getCd());
		}
	}
	
	/**
	 * 한국거래소 종목의 스팩여부 수집
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public void krxItmSpacYnCollection() throws Exception {
		Document doc = Jsoup.connect(krxJsonUrl)
			.data("bld", "dbms/MDC/STAT/standard/MDCSTAT03402")
			.data("mktTpCd", "0")
			.data("isuSrtCd2", "ALL")
			.get();
		
		for(Map<String, String> block : (List<Map<String, String>>)BscUtils.jsonParse(doc.text()).get("block1")) {
			if("SPAC(소속부없음)".equals(block.get("SECT_TP_NM"))) {
				KrxItmVO krxItmVO = new KrxItmVO();
				krxItmVO.setItmCd(block.get("REP_ISU_SRT_CD"));
				krxItmVO.setSpacYn("Y");
				
				krxItmMapper.update(krxItmVO);
			}
		}
	}
	
	/**
	 * 코드컬럼에 해당하는 코드를 Map 헝태로 생성
	 * @param cdCol
	 * @return
	 * @throws Exception
	 */
	private Map<String, String> createCdMap(String cdCol) throws Exception {
		Map<String, String> result = new HashMap<>();
		
		for(BscCdVO bscCdVO : bscCdMapper.selectAllByCdCol(cdCol)) {
			result.put(bscCdVO.getCdNm(), bscCdVO.getCd());
		}
		
		return result;
	}
}
