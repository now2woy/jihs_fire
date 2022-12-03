package ji.hs.fire.krx.svc;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ji.hs.fire.bsc.mpr.BscBatchMapper;
import ji.hs.fire.bsc.mpr.BscCdMapper;
import ji.hs.fire.bsc.util.BscConstants;
import ji.hs.fire.bsc.util.BscUtils;
import ji.hs.fire.bsc.vo.BscBatchVO;
import ji.hs.fire.bsc.vo.BscCdVO;
import ji.hs.fire.krx.mpr.KrxItmMapper;
import ji.hs.fire.krx.mpr.KrxTrdMapper;
import ji.hs.fire.krx.vo.KrxItmVO;
import ji.hs.fire.krx.vo.KrxTrdVO;
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
public class KrxService {
	/**
	 * BC_CD_DT 쿼리
	 */
	private final BscCdMapper bscCdMapper;
	/**
	 * 배치 정보
	 */
	private final BscBatchMapper bscBatchMapper;
	/**
	 * KX_ITM_MT 쿼리
	 */
	private final KrxItmMapper krxItmMapper;
	/**
	 * KX_TRD_MT 쿼리
	 */
	private final KrxTrdMapper krxTrdMapper;
	
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
	public Map<String, String> bscCollection() throws Exception {
		MDC.put(BscConstants.LOG_KEY, BscConstants.LOG_KEY_KRX);
		
		String thisDateTime = BscUtils.thisDateTime();
		
		log.info("{} 한국거래소 종목 기본 정보 수집 시작", thisDateTime);
		
		Map<String, String> result = new HashMap<>();
		
		
		// 한국거래소 종목 수집
		itmCollection(result);
		
		// 한국거래소 종목의 스팩여부 수집
		itmSpacYnCollection(result);
		
		log.info("{} 한국거래소 종목 기본 정보 수집 종료", thisDateTime);
		
		MDC.clear();
		
		return result;
	}
	
	/**
	 * 한국거래소 종목 거래 정보 수집
	 * @return
	 * @throws Exception
	 */
	@Async
	@Transactional
	public Map<String, String> trdCollection() throws Exception {
		MDC.put(BscConstants.LOG_KEY, BscConstants.LOG_KEY_KRX);
		
		Map<String, String> result = new HashMap<>();
		
		BscCdVO parmBscCdVO = new BscCdVO();
		parmBscCdVO.setCdCol("MKT_CD");
		List<BscCdVO> mktCdList = bscCdMapper.selectAll(parmBscCdVO);
		
		BscBatchVO parmBatchVO = new BscBatchVO();
		parmBatchVO.setBatchCd("00002");
		parmBatchVO.setExeYn("N");
		parmBatchVO.setLimit(10);
		List<BscBatchVO> batchList = bscBatchMapper.selectAll(parmBatchVO);
		
		int cnt = 0;
		
		for(BscBatchVO bscBatchVO : batchList) {
			log.info("{} 일자 한국거래소 종목 거래 정보 수집 시작", bscBatchVO.getParm1st());
			
			// 1 : 배치 시작
			bscBatchVO.setUpdCnt(1);
			
			// 배치 실행 시간 입력
			bscBatchMapper.update(bscBatchVO);
			
			// 배치 실행 했으니 일단 완료
			bscBatchVO.setExeYn("Y");
			
			// 시장 단위로 처리
			for(BscCdVO bscCdVO : mktCdList) {
				log.info("한국거래소 종목 거래 정보 수집 / 시장 : {} 시작", bscCdVO.getCdNm());
				
				// KRX URL 호출
				Document doc = Jsoup.connect(krxJsonUrl)
						.data("bld", "dbms/MDC/STAT/standard/MDCSTAT01501")
						.data("mktId", bscCdVO.getCd())
						.data("trdDd", bscBatchVO.getParm1st())
						.get();
				
				for(Map<String, String> json : (List<Map<String, String>>)BscUtils.jsonParse(doc.text()).get("OutBlock_1")) {
					// 종가가 "-" 일 경우 휴장
					if("-".equals(json.get("TDD_CLSPRC"))) {
						log.info("한국거래소 종목 거래 정보 수집 / 시장 : {}, 거래일 아님", bscCdVO.getCdNm(), bscBatchVO.getParm1st());
						break;
						
					} else {
						KrxTrdVO krxTrdVO = new KrxTrdVO();
						
						krxTrdVO.setItmCd(json.get("ISU_SRT_CD"));
						krxTrdVO.setDt(bscBatchVO.getParm1st());
						krxTrdVO.setStAmt(new BigDecimal(json.get("TDD_OPNPRC").replaceAll(",", "")));
						krxTrdVO.setEdAmt(new BigDecimal(json.get("TDD_CLSPRC").replaceAll(",", "")));
						krxTrdVO.setLwAmt(new BigDecimal(json.get("TDD_LWPRC").replaceAll(",", "")));
						krxTrdVO.setHgAmt(new BigDecimal(json.get("TDD_HGPRC").replaceAll(",", "")));
						krxTrdVO.setIncrAmt(new BigDecimal(json.get("CMPPREVDD_PRC").replaceAll(",", "")));
						krxTrdVO.setTrdQty(new BigDecimal(json.get("ACC_TRDVOL").replaceAll(",", "")));
						
						// 데이터 입력
						krxTrdMapper.insert(krxTrdVO);
					}
					
					cnt++;
				}
				
				log.info("한국거래소 종목 거래 정보 수집 / 시장 : {} 종료", bscCdVO.getCdNm());
			}
			
			// 배치가 정상 실행되었을 경우 성공여부가 'Y'이다.
			if("Y".equals(bscBatchVO.getExeYn())) {
				bscBatchVO.setSucYn("Y");
			}
			
			// 2 : 배치 종료
			bscBatchVO.setUpdCnt(2);
			
			// 베치 결과 UPDATE
			bscBatchMapper.update(bscBatchVO);
			
			log.info("{} 일자 한국거래소 종목 거래 정보 수집 종료", bscBatchVO.getParm1st());
		}
		
		result.put("CNT", Integer.toString(cnt));
		
		MDC.clear();
		
		return result;
	}
	
	/**
	 * 한국거래소 종목 수집
	 * @param result
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	private void itmCollection(Map<String, String> result) throws Exception {
		Map<String, String> itmKndCd = createCdMap("ITM_KND_CD");
		Map<String, String> itmClCd = createCdMap("ITM_CL_CD");
		
		BscCdVO bscCdVO = new BscCdVO();
		bscCdVO.setCdCol("MKT_CD");
		
		// 시장코드별로 URL 호출
		for(BscCdVO resultVO : bscCdMapper.selectAll(bscCdVO)) {
			log.info("{} 종목 정보 수집 시작", resultVO.getCd());
			
			int cnt = 0;
			
			// KRX URL 호출
			Document doc = Jsoup.connect(krxJsonUrl)
				.data("bld", "dbms/MDC/STAT/standard/MDCSTAT01901")
				.data("mktId", resultVO.getCd())
				.get();
			
			for(Map<String, String> json : (List<Map<String, String>>)BscUtils.jsonParse(doc.text()).get("OutBlock_1")) {
				KrxItmVO krxItmVO = new KrxItmVO();
				
				krxItmVO.setItmCd(json.get("ISU_SRT_CD"));
				krxItmVO.setItmNm(json.get("ISU_NM"));
				krxItmVO.setMktCd(resultVO.getCd());
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
			
			result.put(resultVO.getCdNm() + " CNT", Integer.toString(cnt));
			
			log.info("{} 종목 정보 수집 종료", resultVO.getCd());
		}
	}
	
	/**
	 * 한국거래소 종목의 스팩여부 수집
	 * @param result
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	private void itmSpacYnCollection(Map<String, String> result) throws Exception {
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
		
		BscCdVO bscCdVO = new BscCdVO();
		bscCdVO.setCdCol(cdCol);
		
		for(BscCdVO resultVO : bscCdMapper.selectAll(bscCdVO)) {
			result.put(resultVO.getCdNm(), resultVO.getCd());
		}
		
		return result;
	}
}
