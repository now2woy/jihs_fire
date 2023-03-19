package ji.hs.fire.dart.svc;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import ji.hs.fire.bsc.mpr.BscBatchMapper;
import ji.hs.fire.bsc.util.BscConstants;
import ji.hs.fire.bsc.util.BscUtils;
import ji.hs.fire.bsc.vo.BscBatchVO;
import ji.hs.fire.dart.mpr.DartKeyMapper;
import ji.hs.fire.dart.mpr.DartFnlttMapper;
import ji.hs.fire.dart.mpr.DartItmMapper;
import ji.hs.fire.dart.vo.DartFnlttDtMfcVO;
import ji.hs.fire.dart.vo.DartFnlttVO;
import ji.hs.fire.dart.vo.DartItmVO;
import ji.hs.fire.dart.vo.DartKeyVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 전자공시시스템 재무제표 Service
 * @author now2w
 *
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DartFnlttService {
	/**
	 * 배치 정보
	 */
	private final BscBatchMapper bscBatchMapper;
	/**
	 * DART 키 정보
	 */
	private final DartKeyMapper dartKeyMapper;
	/**
	 * DART 종목 정보
	 */
	private final DartItmMapper dartItmMapper;
	/**
	 * DART 재무제표 정보
	 */
	private final DartFnlttMapper dartFnlttMapper;
	
	/**
	 * DART 종목 / 년도 / 분기 재무제표 URL
	 */
	@Value("${constant.dart.url.fnltt}")
	private String dartFnlttUrl;
	
	/**
	 * Dart 재무제표 수집
	 * @param limit
	 * @throws Exception
	 */
	@Async
	@Transactional
	public void dartFnlttCollection(int limit) throws Exception {
		MDC.put(BscConstants.LOG_KEY, BscConstants.LOG_KEY_DART);
		
		final ObjectMapper mapper = new ObjectMapper();
		
		// DART API KEY 조회
		List<DartKeyVO> keyList = dartKeyMapper.selectAll();
		
		BscBatchVO parmBatchVO = new BscBatchVO();
		parmBatchVO.setBatchCd("00001");
		parmBatchVO.setExeYn("N");
		parmBatchVO.setLimit(limit);
		
		int keyNum = 0;
		
		// 배치 실행 대사 조회
		for(BscBatchVO bscBatchVO : bscBatchMapper.selectAll(parmBatchVO)) {
			log.info("{}년도 {}분기 재무제표 수집 시작", bscBatchVO.getParm1st(), bscBatchVO.getParm2nd());
			
			// 1 : 배치 시작
			bscBatchVO.setUpdCnt(1);
			
			// 배치 실행 시간 입력
			bscBatchMapper.update(bscBatchVO);
			
			// 배치 실행 했으니 일단 완료
			bscBatchVO.setExeYn("Y");
			
			DartItmVO parmDartItmVO = new DartItmVO();
			parmDartItmVO.setYr(bscBatchVO.getParm1st());
			parmDartItmVO.setQtCd(bscBatchVO.getParm2nd());
			
			// 수집 대상 종목 조회
			for(DartItmVO dartItmVO : dartItmMapper.selectAll(parmDartItmVO)) {
				
				// 수집여부가 N인것만 수집
				if("N".equals(dartItmVO.getCltYn())) {
					// 사용할 수 있는 API KEY가 있는지 확인
					if(keyList.size() >= (keyNum + 1)) {
						String fsCd = "CFS";
						
						Map<String, Object> map = mapper.readValue(getDataFromUrl(keyList.get(keyNum).getApiKey(), dartItmVO.getDartItmCd(), bscBatchVO.getParm1st(), bscBatchVO.getParm2nd(), fsCd), mapper.getTypeFactory().constructMapLikeType(Map.class, String.class, Object.class));
						
						// 데이터 없음
						if("013".equals(map.get("status"))) {
							fsCd = "OFS";
							map = mapper.readValue(getDataFromUrl(keyList.get(keyNum).getApiKey(), dartItmVO.getDartItmCd(), bscBatchVO.getParm1st(), bscBatchVO.getParm2nd(), fsCd), mapper.getTypeFactory().constructMapLikeType(Map.class, String.class, Object.class));
							
							// API KEY 사용한도 초과
							if("020".equals(map.get("status"))) {
								// 다음 키를 사용하도록 변경
								keyNum = keyNum + 1;
								
								// 사용할 수 있는 API KEY가 있는지 확인
								if(keyList.size() >= (keyNum + 1)) {
									map = mapper.readValue(getDataFromUrl(keyList.get(keyNum).getApiKey(), dartItmVO.getDartItmCd(), bscBatchVO.getParm1st(), bscBatchVO.getParm2nd(), fsCd), mapper.getTypeFactory().constructMapLikeType(Map.class, String.class, Object.class));
								}
							}
							
							// API KEY 사용한도 초과
						} else if("020".equals(map.get("status"))) {
							keyNum = keyNum + 1;
							
							// 사용할 수 있는 API KEY가 있는지 확인
							if(keyList.size() >= (keyNum + 1)) {
								map = mapper.readValue(getDataFromUrl(keyList.get(keyNum).getApiKey(), dartItmVO.getDartItmCd(), bscBatchVO.getParm1st(), bscBatchVO.getParm2nd(), fsCd), mapper.getTypeFactory().constructMapLikeType(Map.class, String.class, Object.class));
							}
						}
						
						// URL 조회 결과가 정상일 경우
						if("000".equals(map.get("status"))) {
							for(Map<String, String> block : (List<Map<String, String>>)map.get("list")) {
								DartFnlttVO dartFnlttVO = new DartFnlttVO();
								
								dartFnlttVO.setYr(bscBatchVO.getParm1st());
								dartFnlttVO.setDartItmCd(dartItmVO.getDartItmCd());
								dartFnlttVO.setItmCd(dartItmVO.getItmCd());
								dartFnlttVO.setFsCd(fsCd);
								
								if(block.containsKey("reprt_code")) {
									dartFnlttVO.setQtCd(block.get("reprt_code"));
								}
								if(block.containsKey("sj_div")) {
									dartFnlttVO.setSjCd(block.get("sj_div"));
								}
								if(block.containsKey("sj_nm")) {
									dartFnlttVO.setSjNm(block.get("sj_nm"));
								}
								if(block.containsKey("account_id")) {
									dartFnlttVO.setAcntId(block.get("account_id"));
								}
								if(block.containsKey("account_nm")) {
									dartFnlttVO.setAcntNm(block.get("account_nm"));
								}
								if(block.containsKey("account_detail")) {
									dartFnlttVO.setAcntDtl(block.get("account_detail"));
								}
								if(block.containsKey("thstrm_nm")) {
									dartFnlttVO.setThTmNm(block.get("thstrm_nm"));
								}
								if(block.containsKey("thstrm_amount")) {
									dartFnlttVO.setThTmAmt(block.get("thstrm_amount"));
								}
								if(block.containsKey("thstrm_add_amount")) {
									dartFnlttVO.setThTmAddAmt(block.get("thstrm_add_amount"));
								}
								if(block.containsKey("frmtrm_nm")) {
									dartFnlttVO.setFrmTmNm(block.get("frmtrm_nm"));
								}
								if(block.containsKey("frmtrm_q_nm")) {
									dartFnlttVO.setFrmTmNm(block.get("frmtrm_q_nm"));
								}
								if(block.containsKey("frmtrm_q_amount")) {
									dartFnlttVO.setFrmTmAmt(block.get("frmtrm_q_amount"));
								}
								if(block.containsKey("frmtrm_add_amount")) {
									dartFnlttVO.setFrmTmAddAmt(block.get("frmtrm_add_amount"));
								}
								if(block.containsKey("ord")) {
									dartFnlttVO.setOrd(Integer.parseInt(block.get("ord")));
								}
								if(block.containsKey("currency")) {
									dartFnlttVO.setCrcCd(block.get("currency"));
								}
								
								dartFnlttMapper.insert(dartFnlttVO);
							}
							
							log.info("{}({}) {} 수집", dartItmVO.getItmCd(), dartItmVO.getDartItmCd(), dartItmVO.getDartItmCdNm());
						
						// 13번은 오류 아님
						} else if("013".equals(map.get("status"))) {
							
						// 여러번 조회한 뒤에도 정상적인 값이 아닐 경우 배치 다시 실행
						} else {
							log.info("{}({}) {} {} 실패", dartItmVO.getItmCd(), dartItmVO.getDartItmCd(), dartItmVO.getDartItmCdNm(), map.get("status"));
							bscBatchVO.setExeYn("N");
						}
					// 조회할 데이터가 있는데 키가 없을 경우 배치 다시 실행
					}else {
						bscBatchVO.setExeYn("N");
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
			
			log.info("{}년도 {}분기 재무제표 수집 종료", bscBatchVO.getParm1st(), bscBatchVO.getParm2nd());
		}
		
		MDC.clear();
	}
	
	
	/**
	 * Dart 재무제표 가공
	 * @param limit
	 * @throws Exception
	 */
	@Async
	@Transactional
	public void dartFnlttDataManufacture(int limit) throws Exception {
		MDC.put(BscConstants.LOG_KEY, BscConstants.LOG_KEY_DART);
		
		BscBatchVO parmBatchVO = new BscBatchVO();
		parmBatchVO.setBatchCd("00004");
		parmBatchVO.setExeYn("N");
		parmBatchVO.setLimit(limit);
		
		// 배치 실행 대사 조회
		for(BscBatchVO bscBatchVO : bscBatchMapper.selectAll(parmBatchVO)) {
			log.info("{}년도 {}분기 재무제표 가공 시작", bscBatchVO.getParm1st(), bscBatchVO.getParm2nd());
			
			// 1 : 배치 시작
			bscBatchVO.setUpdCnt(1);
			
			// 배치 실행 시간 입력
			bscBatchMapper.update(bscBatchVO);
			
			// 배치 실행 했으니 일단 완료
			bscBatchVO.setExeYn("Y");
			
			DartItmVO parmDartItmVO = new DartItmVO();
			parmDartItmVO.setYr(bscBatchVO.getParm1st());
			parmDartItmVO.setQtCd(bscBatchVO.getParm2nd());
			
			// 수집 대상 종목 조회
			for(DartItmVO dartItmVO : dartItmMapper.selectAll(parmDartItmVO)) {
				// TODO findByItmCdAndYrAndQtOrderBySeqAsc 조회, 쿼리 만들어야 됨
				
				// TODO 조회 결과 반복
				
				// TODO 반복 중 setItmFincSts 처리
				
				// TODO 반복 후 아래 로직 추가 처리 필요
				
//				itmFincStss.stream().forEach(itmFincSts -> {
//					// TODO 요기 일단 처리 안됨
//					// 당기순이익이 없고 법인세비용차감전순이익과 법인세비용이 있을 경우 계산한다.
//					if(itmFincSts.getTsNetIncmAmt() == null && StringUtils.isNotEmpty(itmFincSts.getTemp1()) && StringUtils.isNotEmpty(itmFincSts.getTemp2())){
//						/** 당기순이익 = 법인세비용차감전순이익 - 법인세비용 */
//						itmFincSts.setTsNetIncmAmt(new BigDecimal(itmFincSts.getTemp1()).subtract(new BigDecimal(itmFincSts.getTemp2())));
//					}
//					
//					// 4분기 데이터의 경우 동년 전분기 데이터로 빼야 한다.
//					if("4".equals(itmFincSts.getQt())) {
//						qtDataSubtract(itmFincSts);
//					}
//					
//					// 년도가 같고 현재 분기 이전 데이터 조회
//					List<ItmFincSts> csflwCalcValues = itmFincStsRepo.findByItmCdAndYrAndQtLessThan(itmFincSts.getItmCd(), itmFincSts.getYr(), itmFincSts.getQt());
//					
//					// 데이터가 있을 경우 반복
//					csflwCalcValues.stream().forEach(csflwCalcValue -> {
//						/** 영업활동현금흐름 =  당기 영업활동현금흐름 - 이전분기 영업활동현금흐름 */
//						itmFincSts.setOprCsflw(Utils.subtract(itmFincSts.getOprCsflw(), csflwCalcValue.getOprCsflw()));
//					});
//				});
			}
			
			// 배치가 정상 실행되었을 경우 성공여부가 'Y'이다.
			if("Y".equals(bscBatchVO.getExeYn())) {
				bscBatchVO.setSucYn("Y");
			}
			
			// 2 : 배치 종료
			bscBatchVO.setUpdCnt(2);
			
			// 베치 결과 UPDATE
			bscBatchMapper.update(bscBatchVO);
			
			log.info("{}년도 {}분기 재무제표 가공 종료", bscBatchVO.getParm1st(), bscBatchVO.getParm2nd());
		}
		
		
		MDC.clear();
	}
	
	/**
	 * URL로부터 데이터를 가져온다.
	 * @param itmCd
	 * @param yr
	 * @param qt
	 * @param fsDiv
	 * @return
	 */
	private String getDataFromUrl(final String dartApiKey, final String dartItmCd, final String yr, final String qt, String fsDiv) {
		String result = null;
		InputStream is = null;
		try {
			// 1분에 1000건이 넘으면 24시간 차단, 1건당 60ms을 사용할 경우 정확하게 1분에 1000건 처리 가능, 여유를 두고 65ms 으로 세팅
			Thread.sleep(65L);
			
			URL url = new URL(dartFnlttUrl + "?crtfc_key=" + dartApiKey + "&corp_code=" + dartItmCd + "&bsns_year=" + yr + "&reprt_code=" + qt + "&fs_div=" + fsDiv);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			
			is = conn.getInputStream();
			
			result = IOUtils.toString(is, "UTF-8");
		}catch(Exception e) {
			log.error("", e);
		}finally {
			IOUtils.closeQuietly(is);
		}
		
		return result;
	}
	
	/**
	 * 코드값에 맞춰 데이터를 매핑한다.
	 * @param dartFnlttDtMfcVO
	 * @param dartFnlttVO
	 */
//	private void setItmFincSts(DartFnlttDtMfcVO dartFnlttDtMfcVO, DartFnlttVO dartFnlttVO) {
//		String cd = getAcntIdCd(dartFnlttVO.getAcntId());
//		
//		if("9999".equals(cd)) {
//			cd = getAcntNmCd(dartFnlttVO.getAcntNm());
//		}
//		
//		// 손익계산서와 포괄손익계산서 일 경우
//		if("IS".equals(dartFnlttVO.getSjCd()) || "CIS".equals(dartFnlttVO.getSjCd())) {
//			switch (cd) {
//				// 매출액
//				case "100":
//					dartFnlttDtMfcVO.setSalAmt(BscUtils.stringToBigDecimal(dartFnlttVO.getThTmAmt()));
//					dartFnlttDtMfcVO.setAddSalAmt(BscUtils.stringToBigDecimal(dartFnlttVO.getThTmAddAmt()));
//					
//					// 매출총이익이 명칭으로 있을 경우 매출총이익에도 넣는다.
//					if("Ⅲ.매출총이익".equals(dartFnlttVO.getAcntNm())){
//						dartFnlttDtMfcVO.setSalTotIncmAmt(BscUtils.stringToBigDecimal(dartFnlttVO.getThTmAddAmt()));
//						dartFnlttDtMfcVO.setAddSalTotIncmAmt(BscUtils.stringToBigDecimal(dartFnlttVO.getThTmAddAmt()));
//					}
//					
//					break;
//					
//				// 영업이익
//				case "200":
//					dartFnlttDtMfcVO.setOprIncmAmt(BscUtils.stringToBigDecimal(dartFnlttVO.getThTmAmt()));
//					dartFnlttDtMfcVO.setAddOprIncmAmt(BscUtils.stringToBigDecimal(dartFnlttVO.getThTmAddAmt()));
//					break;
//					
//				// 당기순이익
//				case "300":
//					// 당기순이익의 경우 여러 태그가 있는데 이중 두가지
//					if("연결재무제표 [member]".equals(dartFnlttVO.getAcntDtl())
//					|| "-".equals(dartFnlttVO.getAcntDtl())){
//						dartFnlttDtMfcVO.setTsNetIncmAmt(BscUtils.stringToBigDecimal(dartFnlttVO.getThTmAmt()));
//						dartFnlttDtMfcVO.setAddTsNetIncmAmt(BscUtils.stringToBigDecimal(dartFnlttVO.getThTmAddAmt()));
//					}
//					break;
//					
//				// 지배 당기순이익
//				case "400":
//					if("-".equals(dartFnlttVO.getAcntDtl())) {
//						// 동일한 태그가 반복 되어 이전 태그값을 가져오기 위해 제한
//						// 226360 이놈 때문임
//						if(dartFnlttDtMfcVO.getOwnTsNetIncmAmt() == null) {
//							dartFnlttDtMfcVO.setOwnTsNetIncmAmt(BscUtils.stringToBigDecimal(dartFnlttVO.getThTmAmt()));
//						}
//						dartFnlttDtMfcVO.setAddOwnTsNetIncmAmt(BscUtils.stringToBigDecimal(dartFnlttVO.getThTmAddAmt()));
//					}
//					break;
//					
//				// 당기기본주당순이익
//				case "700":
//					if("-".equals(dartFnlttVO.getAcntDtl())) {
//						dartFnlttDtMfcVO.setTsBscEps(BscUtils.stringToBigDecimal(dartFnlttVO.getThTmAmt()));
//						dartFnlttDtMfcVO.setAddTsBscEps(BscUtils.stringToBigDecimal(dartFnlttVO.getThTmAddAmt()));
//					}
//					break;
//					
//				// 당기희석주당순이익
//				case "800":
//					if("-".equals(dartFnlttVO.getAcntDtl())) {
//						dartFnlttDtMfcVO.setTsDltdEps(BscUtils.stringToBigDecimal(dartFnlttVO.getThTmAmt()));
//						dartFnlttDtMfcVO.setAddTsDltdEps(BscUtils.stringToBigDecimal(dartFnlttVO.getThTmAddAmt()));
//					}
//					break;
//					
//				// 당기기본주당순이익 + 당기희석주당순이익
//				case "810":
//					if("-".equals(dartFnlttVO.getAcntDtl())) {
//						// 당기기본주당순이익
//						dartFnlttDtMfcVO.setTsBscEps(BscUtils.stringToBigDecimal(dartFnlttVO.getThTmAmt()));
//						// 당기희석주당순이익
//						dartFnlttDtMfcVO.setTsDltdEps(BscUtils.stringToBigDecimal(dartFnlttVO.getThTmAmt()));
//						// 당기기본주당순이익 합계
//						dartFnlttDtMfcVO.setAddTsBscEps(BscUtils.stringToBigDecimal(dartFnlttVO.getThTmAddAmt()));
//						// 당기희석주당순이익 합계
//						dartFnlttDtMfcVO.setAddTsDltdEps(BscUtils.stringToBigDecimal(dartFnlttVO.getThTmAddAmt()));
//					}
//					break;
//					
//				// 당기기본주당순이익 + 당기희석주당순이익, 특정태그만 해당이라 한번 더 필터링
//				case "820":
//					if("기본 및 희석 주당이익".equals(dartFnlttVO.getAcntNm())) {
//						if("-".equals(dartFnlttVO.getAcntDtl())) {
//							dartFnlttDtMfcVO.setTsBscEps(BscUtils.stringToBigDecimal(dartFnlttVO.getThTmAmt()));
//							dartFnlttDtMfcVO.setTsDltdEps(BscUtils.stringToBigDecimal(dartFnlttVO.getThTmAmt()));
//							dartFnlttDtMfcVO.setAddTsBscEps(BscUtils.stringToBigDecimal(dartFnlttVO.getThTmAddAmt()));
//							dartFnlttDtMfcVO.setAddTsDltdEps(BscUtils.stringToBigDecimal(dartFnlttVO.getThTmAddAmt()));
//						}
//					}
//					break;
//					
//				// 계속영업기본주당순이익
//				case "900":
//					dartFnlttDtMfcVO.setOprBscEps(BscUtils.stringToBigDecimal(dartFnlttVO.getThTmAmt()));
//					dartFnlttDtMfcVO.setAddOprBscEps(BscUtils.stringToBigDecimal(dartFnlttVO.getThTmAddAmt()));
//					break;
//					
//				// 계속영업희석주당순이익
//				case "1000":
//					dartFnlttDtMfcVO.setOprDltdEps(BscUtils.stringToBigDecimal(dartFnlttVO.getThTmAmt()));
//					dartFnlttDtMfcVO.setAddOprDltdEps(BscUtils.stringToBigDecimal(dartFnlttVO.getThTmAddAmt()));
//					break;
//					
//				// 계속영업기본주당순이익 + 계속영업희석주당순이익
//				case "1010":
//					dartFnlttDtMfcVO.setOprBscEps(BscUtils.stringToBigDecimal(dartFnlttVO.getThTmAmt()));
//					dartFnlttDtMfcVO.setOprDltdEps(BscUtils.stringToBigDecimal(dartFnlttVO.getThTmAmt()));
//					dartFnlttDtMfcVO.setAddOprBscEps(BscUtils.stringToBigDecimal(dartFnlttVO.getThTmAddAmt()));
//					dartFnlttDtMfcVO.setAddOprDltdEps(BscUtils.stringToBigDecimal(dartFnlttVO.getThTmAddAmt()));
//					break;
//					
//				// 매출총이익
//				case "1600":
//					dartFnlttDtMfcVO.setSalTotIncmAmt(BscUtils.stringToBigDecimal(dartFnlttVO.getThTmAmt()));
//					dartFnlttDtMfcVO.setAddSalTotIncmAmt(BscUtils.stringToBigDecimal(dartFnlttVO.getThTmAddAmt()));
//					
//					// 매출액 관련 문구에 해당할 경우 매출액에도 넣는다.
//					if("영업수익".equals(dartFnlttVO.getAcntNm())
//					|| "I.영업수익".equals(dartFnlttVO.getAcntNm())
//					|| "영업수익(매출액)".equals(dartFnlttVO.getAcntNm())
//					|| "매출총이익(영업수익)".equals(dartFnlttVO.getAcntNm())){
//						dartFnlttDtMfcVO.setSalAmt(BscUtils.stringToBigDecimal(dartFnlttVO.getThTmAmt()));
//						dartFnlttDtMfcVO.setAddSalAmt(BscUtils.stringToBigDecimal(dartFnlttVO.getThTmAddAmt()));
//					}
//					break;
//					
//				default:
//					break;
//			}
//		}else if("BS".equals(dartFnlttVO.getSjCd())) {
//			switch (cd) {
//				// 자본
//				case "1100":
//					dartFnlttDtMfcVO.setBscCpt(BscUtils.stringToBigDecimal(dartFnlttVO.getThTmAmt()));
//					break;
//					
//				// 지배자본
//				case "1200":
//					dartFnlttDtMfcVO.setOwnCpt(BscUtils.stringToBigDecimal(dartFnlttVO.getThTmAmt()));
//					break;
//					
//				// 자산총계
//				case "1300":
//					dartFnlttDtMfcVO.setAstAmt(BscUtils.stringToBigDecimal(dartFnlttVO.getThTmAmt()));
//					break;
//					
//				// 부채총계
//				case "1400":
//					dartFnlttDtMfcVO.setDebtAmt(BscUtils.stringToBigDecimal(dartFnlttVO.getThTmAmt()));
//					break;
//					
//				default:
//					break;
//			}
//		} else if("CF".equals(dartFnlttVO.getSjCd())) {
//			switch (cd) {
//				// 영업활동현금흐름
//				case "1500":
//					dartFnlttDtMfcVO.setOprCsflw(BscUtils.stringToBigDecimal(dartFnlttVO.getThTmAmt()));
//					break;
//					
//				default:
//					break;
//			}
//		}
//	}
}
