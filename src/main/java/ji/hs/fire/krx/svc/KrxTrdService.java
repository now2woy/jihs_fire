package ji.hs.fire.krx.svc;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.time.DateUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ji.hs.fire.bsc.mpr.BscBatchMapper;
import ji.hs.fire.bsc.mpr.BscCdMapper;
import ji.hs.fire.bsc.svc.BscBatchService;
import ji.hs.fire.bsc.svc.BscNoGenService;
import ji.hs.fire.bsc.util.BscConstants;
import ji.hs.fire.bsc.util.BscUtils;
import ji.hs.fire.bsc.vo.BscBatchVO;
import ji.hs.fire.bsc.vo.BscCdVO;
import ji.hs.fire.krx.mpr.KrxItmMapper;
import ji.hs.fire.krx.mpr.KrxTrdMapper;
import ji.hs.fire.krx.mpr.KrxTrdMnftMapper;
import ji.hs.fire.krx.vo.KrxItmVO;
import ji.hs.fire.krx.vo.KrxTrdMnftVO;
import ji.hs.fire.krx.vo.KrxTrdVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 한국거래소 종목 거래 정보 Service
 * @author now2w
 *
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class KrxTrdService {
	/**
	 * 코드 정보 Mapper
	 */
	private final BscCdMapper bscCdMapper;
	/**
	 * 배치 정보 Mapper
	 */
	private final BscBatchMapper bscBatchMapper;
	/**
	 * 한국거래소 종목 정보 Mapper
	 */
	private final KrxItmMapper krxItmMapper;
	/**
	 * 한국거래소 종목 거래 정보 Mapper
	 */
	private final KrxTrdMapper krxTrdMapper;
	/**
	 * 한국거래소 종목 거래 가공 정보 Mapper
	 */
	private final KrxTrdMnftMapper krxTrdMnftMapper;
	/**
	 * 채번 Service
	 */
	private final BscNoGenService bscNoGenService;
	/**
	 * 한국거래소 JSON URL
	 */
	@Value("${constant.krx.url.json}")
	private String krxJsonUrl;
	/**
	 * 한국거래소 종목 거래 정보 수집 배치 코드
	 */
	private static final String BATCH_CD_00002 = "00002";
	/**
	 * 한국거래소 종목 거래 정보 가공 배치 코드
	 */
	private static final String BATCH_CD_00003 = "00003";
	
	/**
	 * 한국거래소 종목 거래 정보 수집
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	@Async
	@Transactional
	public Map<String, String> trdCollection(int limit) throws Exception {
		MDC.put(BscConstants.LOG_KEY, BscConstants.LOG_KEY_KRX);
		
		Map<String, String> result = new HashMap<>();
		
		int cnt = 0;
		
		BscCdVO parmBscCdVO = new BscCdVO();
		parmBscCdVO.setCdCol("MKT_CD");
		List<BscCdVO> mktCdList = bscCdMapper.selectAll(parmBscCdVO);
		
		// 배치 대상을 조회한다.
		List<BscBatchVO> batchList = selectBatchTargetList(BATCH_CD_00002, limit);
		
		// 배치 대상이 있을 경우
		if(!batchList.isEmpty()) {
			// 조회된 배치를 처리 한다.
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
							krxTrdVO.setMktTotAmt(new BigDecimal(json.get("MKTCAP").replaceAll(",", "")));
							krxTrdVO.setIsuStkQty(new BigDecimal(json.get("LIST_SHRS").replaceAll(",", "")));
							
							// 데이터 입력
							krxTrdMapper.insert(krxTrdVO);
						}
						
						// 처리 건수를 증가한다.
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
		}
		
		// 처리된 ROW 수를 리턴한다.
		result.put("CNT", Integer.toString(cnt));
		
		// 로그 대상 정보 초기화
		MDC.clear();
		
		return result;
	}
	
	/**
	 * 한국거래소 종목 거래 정보 가공
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	@Async
	@Transactional
	public Map<String, String> trdDataManufacture(int limit) throws Exception {
		MDC.put(BscConstants.LOG_KEY, BscConstants.LOG_KEY_KRX);
		
		Map<String, String> result = new HashMap<>();
		int cnt = 0;
		
		// 배치 대상을 조회한다.
		List<BscBatchVO> batchList = selectBatchTargetList(BATCH_CD_00003, limit);
		
		// 처리해야 할 종목 목록
		List<KrxItmVO> krxItmList = krxItmMapper.selectAll(new KrxItmVO());
		
		// 배치 대상이 있을 경우
		if(!batchList.isEmpty()) {
			// 조회된 배치를 처리한다.
			for(BscBatchVO bscBatchVO : batchList) {
				log.info("{} 일자 한국거래소 종목 거래 정보 가공 시작", bscBatchVO.getParm1st());
				
				// 1 : 배치 시작
				bscBatchVO.setUpdCnt(1);
				
				// 배치 실행 시간 입력
				bscBatchMapper.update(bscBatchVO);
				
				// 배치 실행 했으니 일단 완료
				bscBatchVO.setExeYn("Y");
				
				KrxTrdVO parmKrxTrdVO = new KrxTrdVO();
				parmKrxTrdVO.setDt(bscBatchVO.getParm1st());
				
				// 거래일 이었는지 확인
				if(krxTrdMapper.selectCount(parmKrxTrdVO) != 0) {
					// 종목 단위로 정보 조회
					for(KrxItmVO krxItmVO : krxItmList) {
						parmKrxTrdVO.setItmCd(krxItmVO.getItmCd());
						
						// 종목이 해당일자에 데이터가 있는지 확인
						if(krxTrdMapper.selectCount(parmKrxTrdVO) != 0) {
							KrxTrdMnftVO krxTrdMnftVO = new KrxTrdMnftVO();
							parmKrxTrdVO.setLimit(120);
							
							List<KrxTrdVO> krxTrdList = krxTrdMapper.selectAll(parmKrxTrdVO);
							
							krxTrdMnftVO.setItmCd(parmKrxTrdVO.getItmCd());
							krxTrdMnftVO.setDt(parmKrxTrdVO.getDt());
							
							// 당일거래수가 0 이상이고, 전체주식수가 0이상 일 경우
							if(krxTrdList.get(0).getTrdQty().compareTo(BigDecimal.ZERO) > 0 && krxTrdList.get(0).getIsuStkQty().compareTo(BigDecimal.ZERO) > 0) {
								// 거래회전율 생성 : ROUND((당일거래수 / 전체주식수) * 100, 2)
								krxTrdMnftVO.setTnovRt(BscUtils.multiply(BscUtils.divide(krxTrdList.get(0).getTrdQty(), krxTrdList.get(0).getIsuStkQty(), 10), new BigDecimal("100"), 2));
							} else {
								krxTrdMnftVO.setTnovRt(BigDecimal.ZERO);
							}
							
							// 거래정보가 5건 이상일 경우
							if(krxTrdList.size() >= 5) {
								// 5일이동평균금액을 생성한다.
								krxTrdMnftVO.setDy005AvgAmt(createAvgAmt(krxTrdList, 5));
								
								if(krxTrdList.size() >= 20) {
									// 20일이동평균금액을 생성한다.
									krxTrdMnftVO.setDy020AvgAmt(createAvgAmt(krxTrdList, 20));
									
									if(krxTrdList.size() >= 60) {
										// 60일이동평균금액을 생성한다.
										krxTrdMnftVO.setDy060AvgAmt(createAvgAmt(krxTrdList, 60));
										
										if(krxTrdList.size() >= 120) {
											// 120일이동평균금액을 생성한다.
											krxTrdMnftVO.setDy120AvgAmt(createAvgAmt(krxTrdList, 120));
										}
									}
								}
							}
							
							// 기존에 자료가 없을 경우 입력
							if(krxTrdMnftMapper.selectCount(krxTrdMnftVO) == 0) {
								// 입력 성공
								if(krxTrdMnftMapper.insert(krxTrdMnftVO) == 1) {
									// 처리 건수를 증가한다.
									cnt++;
								}
								
							// 기존에 자료가 있을 경우 수정
							} else {
								// 수정 성공
								if(krxTrdMnftMapper.update(krxTrdMnftVO) == 1) {
									// 처리 건수를 증가한다.
									cnt++;
								}
							}
						}
					}
				}
				
				// 배치가 정상 실행되었을 경우 성공여부가 'Y'이다.
				if("Y".equals(bscBatchVO.getExeYn())) {
					bscBatchVO.setSucYn("Y");
				}
				
				// 2 : 배치 종료
				bscBatchVO.setUpdCnt(2);
				
				// 베치 결과 UPDATE
				bscBatchMapper.update(bscBatchVO);
				
				log.info("{} 일자 한국거래소 종목 거래 정보 가공 종료", bscBatchVO.getParm1st());
			}
		}
		
		// 처리된 ROW 수를 리턴한다.
		result.put("CNT", Integer.toString(cnt));
		
		// 로그 대상 정보 초기화
		MDC.clear();
		
		return result;
	}
	
	/**
	 * 배치코드에 해당하는 정보를 조회하여 리턴한다.
	 * @param batchCd
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	private List<BscBatchVO> selectBatchTargetList(String batchCd, int limit) throws Exception {
		BscBatchVO parmBatchVO = new BscBatchVO();
		parmBatchVO.setBatchCd(batchCd);
		parmBatchVO.setExeYn("N");
		parmBatchVO.setLimit(limit);
		List<BscBatchVO> batchList = bscBatchMapper.selectAll(parmBatchVO);
		
		// 조회된 배치가 없을 경우
		if(batchList.isEmpty()) {
			parmBatchVO = new BscBatchVO();
			parmBatchVO.setLimit(1);
			// SEQ DESC
			parmBatchVO.setOrder(2);
			
			parmBatchVO.setBatchCd(batchCd);
			
			// 마지막 배치 건 조회
			BscBatchVO tempBatchVO = bscBatchMapper.selectAll(parmBatchVO).get(0);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			
			String yesterday = sdf.format(DateUtils.addDays(new Date(), -1));
			
			// limit만큼 반복한다.
			for(int i = 1; i <= limit; i++) {
				String parm1st = sdf.format(DateUtils.addDays(sdf.parse(tempBatchVO.getParm1st()), i));
				
				// 파라미터1이 어제보다 작거나 같을 경우
				if(Integer.parseInt(parm1st) <= Integer.parseInt(yesterday)) {
					BscBatchVO targetBscBatchVO = new BscBatchVO();
					targetBscBatchVO.setSeq(bscNoGenService.generate("BC_BATCH_MT.SEQ"));
					targetBscBatchVO.setBatchCd(tempBatchVO.getBatchCd());
					targetBscBatchVO.setParm1st(parm1st);
					targetBscBatchVO.setExeYn("N");
					
					batchList.add(targetBscBatchVO);
					
					// 배치 정보 테이블에 데이터 입력
					bscBatchMapper.insert(targetBscBatchVO);
				}
			}
		}
		
		return batchList;
	}
	
	/**
	 * scale 만큼 평균을 구한다.
	 * 
	 * @param itmTrd
	 * @param scale
	 * @return
	 */
	private BigDecimal createAvgAmt(List<KrxTrdVO> krxTrdList, int scale) {
		BigDecimal avgAmt = BigDecimal.ZERO;
		
		List<KrxTrdVO> list = krxTrdList.stream().limit(scale).collect(Collectors.toList());
		
		for(KrxTrdVO krxTrdVO : list) {
			avgAmt = BscUtils.add(avgAmt, krxTrdVO.getEdAmt());
		}
		
		return BscUtils.divide(avgAmt, new BigDecimal(scale), 0);
	}
}
