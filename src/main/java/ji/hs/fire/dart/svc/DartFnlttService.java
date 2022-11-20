package ji.hs.fire.dart.svc;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import ji.hs.fire.bsc.mpr.BscBatchMapper;
import ji.hs.fire.bsc.vo.BscBatchVO;
import ji.hs.fire.dart.mpr.DartKeyMapper;
import ji.hs.fire.dart.mpr.DartFnlttMapper;
import ji.hs.fire.dart.mpr.DartItmMapper;
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
	 * @throws Exception
	 */
	@Transactional
	@Async
	public void dartFnlttCollection() throws Exception {
		final ObjectMapper mapper = new ObjectMapper();
		
		// DART API KEY 조회
		List<DartKeyVO> keyList = dartKeyMapper.selectAll();
		
		BscBatchVO parmBatchVO = new BscBatchVO();
		parmBatchVO.setBatchCd("00001");
		parmBatchVO.setExeYn("N");
		parmBatchVO.setSrchCnt(1);
		
		int keyNum = 0;
		
		// 배치 실행 대사 조회
		for(BscBatchVO bscBatchVO : bscBatchMapper.selectAll(parmBatchVO)) {
			log.info("{}년도 {}분기 재무제표 수집 시작", bscBatchVO.getParm1st(), bscBatchVO.getParm2nd());
			
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
							
						// 여러번 조회한 뒤에도 정상적인 값이 아닐 경우 배치 다시 실행
						} else {
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
			
			// 베치 결과 UPDATE
			bscBatchMapper.update(bscBatchVO);
			
			log.info("{}년도 {}분기 재무제표 수집 종료", bscBatchVO.getParm1st(), bscBatchVO.getParm2nd());
		}
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
}
