package ji.hs.fire.krx.svc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ji.hs.fire.bsc.mpr.BscCdMapper;
import ji.hs.fire.bsc.util.BscUtils;
import ji.hs.fire.bsc.vo.BscCdVO;
import ji.hs.fire.krx.mpr.KrxItmMapper;
import ji.hs.fire.krx.vo.KrxItmVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 한국거래소 종목 마스터 Service
 * @author now2w
 *
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class KrxItmService {
	/**
	 * BC_CD_DT 쿼리
	 */
	private final BscCdMapper bscCdMapper;
	/**
	 * KX_ITM_MT 쿼리
	 */
	private final KrxItmMapper krxItmMapper;
	
	/**
	 * 한국거래소 JSON URL
	 */
	@Value("${constant.krx.url.json}")
	private String krxJsonUrl;
	
	/**
	 * 한국거래소 종목 기본 정보 수집
	 * - 종목정보
	 * - 스팩여부
	 * 
	 * @return
	 */
	@Async
	@Transactional
	public Map<String, String> krxBscCollection() throws Exception {
		String thisDateTime = BscUtils.thisDateTime();
		
		log.info("{} 한국거래소 종목 기본 정보 수집 시작", thisDateTime);
		
		Map<String, String> result = new HashMap<>();
		
		
		// 한국거래소 종목 수집
		krxItmCollection(result);
		
		// 한국거래소 종목의 스팩여부 수집
		krxItmSpacYnCollection(result);
		
		log.info("{} 한국거래소 종목 기본 정보 수집 종료", thisDateTime);
		
		return result;
	}
	
	/**
	 * 한국거래소 종목 수집
	 * @param result
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	private void krxItmCollection(Map<String, String> result) throws Exception {
		Map<String, String> itmKndCd = createCdMap("ITM_KND_CD");
		Map<String, String> itmClCd = createCdMap("ITM_CL_CD");
		
		// 시장코드별로 URL 호출
		for(BscCdVO bscCdVO : bscCdMapper.selectAll(BscCdVO.builder().cdCol("MKT_CD").build())) {
			log.info("{} 종목 정보 수집 시작", bscCdVO.getCd());
			
			int cnt = 0;
			
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
				krxItmVO.setItmKndCd(itmKndCd.get(json.get("KIND_STKCERT_TP_NM")));
				krxItmVO.setItmClCd(itmClCd.get(json.get("SECUGRP_NM")));
				krxItmVO.setSpacYn("N");
				
				// 데이터가 있을 경우 수정
				if(krxItmMapper.selectCount(krxItmVO) == 1) {
					krxItmMapper.update(krxItmVO);
					
				// 데이터가 없을 경우 입력
				} else {
					krxItmMapper.insert(krxItmVO);
				}
				
				cnt++;
			}
			
			result.put(bscCdVO.getCdNm() + " CNT", Integer.toString(cnt));
			
			log.info("{} 종목 정보 수집 종료", bscCdVO.getCd());
		}
	}
	
	/**
	 * 한국거래소 종목의 스팩여부 수집
	 * @param result
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	private void krxItmSpacYnCollection(Map<String, String> result) throws Exception {
		log.info("SPAC 여부 수집 시작");
		
		int cnt = 0;
		
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
				
				cnt++;
			}
		}
		
		// 결과를 맵에 담는다.
		result.put("SPAC CNT", Integer.toString(cnt));
		
		log.info("SPAC 여부 수집 종료");
	}
	
	/**
	 * 코드컬럼에 해당하는 코드를 Map 헝태로 생성
	 * @param cdCol
	 * @return
	 * @throws Exception
	 */
	private Map<String, String> createCdMap(String cdCol) throws Exception {
		Map<String, String> result = new HashMap<>();
		
		for(BscCdVO bscCdVO : bscCdMapper.selectAll(BscCdVO.builder().cdCol(cdCol).build())) {
			result.put(bscCdVO.getCdNm(), bscCdVO.getCd());
		}
		
		return result;
	}
}
