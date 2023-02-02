package ji.hs.fire.act.svc;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ji.hs.fire.act.mpr.ActMapper;
import ji.hs.fire.act.mpr.ActTrdMapper;
import ji.hs.fire.act.vo.ActTrdVO;
import ji.hs.fire.act.vo.ActVO;
import ji.hs.fire.bsc.mpr.BscFileMapper;
import ji.hs.fire.bsc.svc.BscNoGenService;
import ji.hs.fire.bsc.util.BscUtils;
import ji.hs.fire.bsc.vo.BscFileVO;
import ji.hs.fire.krx.svc.KrxItmSynService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 계좌 거래 정보 Service
 * @author now2w
 *
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ActTrdService {
	/**
	 * 계좌 Mapper
	 */
	private final ActMapper actMapper;
	/**
	 * 계좌 Mapper
	 */
	private final ActTrdMapper actTrdMapper;
	/**
	 * 파일 Mapper
	 */
	private final BscFileMapper bscFileMapper;
	/**
	 * 계좌 상품 거래 정보 Service
	 */
	private final ActPrdtService actPrdtService;
	/**
	 * 채번 Service
	 */
	private final BscNoGenService bscNoGenService;
	/**
	 * 한국거래소 주식종목 동의어 Service
	 */
	private final KrxItmSynService krxItmSynService;
	
	/**
	 * 파일 업로드 경로
	 */
	@Value("${constant.path.upload}")
	private String uploadPath;
	
	/**
	 * 계좌 거래 정보 목록
	 * @param actTrdVO
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> list(ActTrdVO actTrdVO) throws Exception {
		Map<String, Object> result = new HashMap<>();
		result.put("count", actTrdMapper.selectCount(actTrdVO));
		result.put("data", actTrdMapper.selectAll(actTrdVO));
		
		// 계좌 정보 조회
		ActVO actVO = new ActVO();
		actVO.setActSeq(actTrdVO.getActSeq());
		
		result.put("actData", actMapper.selectOne(actVO));
		
		List<String> trdCds = new ArrayList<>();
		
		trdCds.add("00001");
		
		actTrdVO.setTrdCds(trdCds);
		
		// 총입금액
		result.put("inSumAmt", actTrdMapper.selectSumAmt(actTrdVO));
		
		trdCds = new ArrayList<>();
		
		trdCds.add("00002");
		
		actTrdVO.setTrdCds(trdCds);
		
		// 총출금액
		result.put("outSumAmt", actTrdMapper.selectSumAmt(actTrdVO));
		
		trdCds = new ArrayList<>();
		
		trdCds.add("00001");
		trdCds.add("00002");
		
		actTrdVO.setTrdCds(trdCds);
		
		// 입출금 합계
		result.put("inOutSumAmt", actTrdMapper.selectSumAmt(actTrdVO));
		
		trdCds = new ArrayList<>();
		
		trdCds.add("00003");
		
		actTrdVO.setTrdCds(trdCds);
		
		// 이자 합계
		result.put("itrstSumAmt", actTrdMapper.selectSumAmt(actTrdVO));
		
		trdCds = new ArrayList<>();
		
		trdCds.add("00004");
		trdCds.add("00005");
		
		actTrdVO.setTrdCds(trdCds);
		
		// 배당 분배금 합계
		result.put("dvdnSumAmt", actTrdMapper.selectSumAmt(actTrdVO));
		
		return result;
	}
	
	/**
	 * 계좌 거래 정보 입력
	 * @param actTrdVO
	 * @return
	 * @throws Exception
	 */
	public int insert(ActTrdVO actTrdVO) throws Exception {
		actTrdVO.setTrdSeq(Integer.toString(bscNoGenService.generate("AC_DT.TRD_SEQ")));
		
		return actTrdMapper.insert(actTrdVO);
	}
	
	/**
	 * 봇을 통해 계좌 거래 정보를 입력 한다.
	 * @param actTrdVO
	 * @return
	 * @throws Exception
	 */
	public int botInsert(ActTrdVO actTrdVO) throws Exception {
		// 계좌번호가 있을 경우
		if(StringUtils.isNotEmpty(actTrdVO.getActNo())) {
			// 계좌번호로 계좌일련번호 생성
			actTrdVO.setActSeq(actMapper.selectActSeqByActNo(actTrdVO.getActNo()));
			
		// 계좌번호가 없을 경우
		} else {
			// 계좌일련번호가 없을 경우
			if(StringUtils.isEmpty(actTrdVO.getActSeq())) {
				// 사용자의 기본 계좌번호를 가져온다.
				actTrdVO.setActSeq(actMapper.selectActSeqByTlgrmId(actTrdVO.getTlgrmId()));
			}
		}
		
		return insert(actTrdVO);
	}
	
	/**
	 * 봇을 통해 계좌 거래 정보의 계쫘일련번호를 수정 한다.
	 * @param tlgrmMsgId
	 * @param actSeq
	 * @return
	 * @throws Exception
	 */
	public int updateActSeqByTlgrmMsgId(String tlgrmMsgId, String actSeq) throws Exception {
		ActTrdVO actTrdVO = new ActTrdVO();
		actTrdVO.setTlgrmMsgId(tlgrmMsgId);
		actTrdVO.setActSeq(actSeq);
		
		return actTrdMapper.updateActSeqByTlgrmMsgId(actTrdVO);
	}
	
	/**
	 * 액셀 업로드 후 DB 입력
	 * @param file
	 * @param actSeq
	 * @throws Exception
	 */
	@Transactional
	public void excelUpload(MultipartFile file, String actSeq, String note) throws Exception {
		try {
			String name = UUID.randomUUID().toString();
			String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			
			Files.copy(file.getInputStream(), Paths.get(uploadPath + File.separator + name + ext), StandardCopyOption.REPLACE_EXISTING);
			
			BscFileVO bscFileVO = new BscFileVO();
			bscFileVO.setFileSeq(bscNoGenService.generate("BC_FILE_MT.FILE_SEQ"));
			bscFileVO.setFileNm(name + ext);
			bscFileVO.setOrgFileNm(file.getOriginalFilename());
			
			bscFileMapper.insert(bscFileVO);
			
			Document doc = Jsoup.parse(new File(uploadPath + File.separator + name + ext), "euc-kr", "");
			Elements trs = doc.getElementsByTag("tr");
			
			if(trs.size() != 3) {
				for(int i = 0; i < trs.size(); i = i + 2) {
					Elements tds1st = trs.get(i).getElementsByTag("td");
					Elements tds2nd = trs.get(i + 1).getElementsByTag("td");
					
					if(tds1st.size() != 0) {
						ActTrdVO actTrdVO = new ActTrdVO();
						actTrdVO.setActSeq(actSeq);
						actTrdVO.setFileSeq(Integer.toString(bscFileVO.getFileSeq()));
						actTrdVO.setTrdDt(tds1st.get(0).text().replaceAll("\\.", "-").replaceAll(" ", "T").replaceAll("\\(", "").substring(0, 16));
						actTrdVO.setAmt(new BigDecimal(tds1st.get(5).text().replaceAll(",", "")));
						actTrdVO.setFee(new BigDecimal(tds1st.get(8).text().replaceAll(",", "")));
						actTrdVO.setTax(new BigDecimal(tds2nd.get(4).text().replaceAll(",", "")));
						actTrdVO.setNote(tds1st.get(2).text());
						// 전체 메모가 있을 경우 추가 입력
						if(StringUtils.isNotEmpty(note)) {
							actTrdVO.setNote(actTrdVO.getNote() + ", " + note);
						}
						
						// 대분류 입금
						if("입금".equals(tds1st.get(1).text())) {
							// 소분류 배당금
							if("배당금".equals(tds1st.get(2).text())) {
								actTrdVO.setTrdCd("00004");
								
							// 소분류 예탁금이용료
							} else if("예탁금이용료".equals(tds1st.get(2).text())) {
								actTrdVO.setTrdCd("00003");
								
							} else if("ETF분배금입금".equals(tds1st.get(2).text())) {
								actTrdVO.setTrdCd("00005");
								
							// 그 외의 경우 입금
							} else {
								actTrdVO.setTrdCd("00001");
							}
							
						// 대분류 출금
						} else if("출금".equals(tds1st.get(1).text())) {
							actTrdVO.setTrdCd("00002");
							// 출금의 경우 마이너스로 표기 해야 하므로 -1을 곱한다.
							actTrdVO.setAmt(BscUtils.multiply(actTrdVO.getAmt(), new BigDecimal("-1"), 0));
							
						// 대분류 매수, 입고
						} else if("매수".equals(tds1st.get(1).text()) || "입고".equals(tds1st.get(1).text())) {
							// 유상채권입고의 경우 생략
							if(!"유상채권입고".equals(tds1st.get(2).text())) {
								actTrdVO.setTrdCd("00006");
								actTrdVO.setQty(new BigDecimal(tds1st.get(4).text().replaceAll(",", "")));
								actTrdVO.setPrc(new BigDecimal(tds2nd.get(0).text().replaceAll(",", "")));
								// 매수의 경우 마이너스로 표기 해야 하므로 -1을 곱한다.
								actTrdVO.setAmt(BscUtils.multiply(actTrdVO.getAmt(), new BigDecimal("-1"), 0));
							}
							
							
						// 대분류 매도
						} else if("매도".equals(tds1st.get(1).text())) {
							actTrdVO.setTrdCd("00007");
							actTrdVO.setQty(new BigDecimal(tds1st.get(4).text().replaceAll(",", "")));
							actTrdVO.setPrc(new BigDecimal(tds2nd.get(0).text().replaceAll(",", "")));
							
						} else if("환전".equals(tds1st.get(1).text())) {
							actTrdVO.setTrdCd("00008");
							
							// 외화매수, 외화매수의 경우 마이너스로 표기 해야 하므로 -1을 곱한다.
							if("외화매수".equals(tds1st.get(2).text())) {
								actTrdVO.setAmt(BscUtils.multiply(actTrdVO.getAmt(), new BigDecimal("-1"), 0));
							}
						}
						
						// 종목 정보가 있을 경우
						if(StringUtils.isNotEmpty(tds1st.get(3).text())) {
							actTrdVO.setItmCd(krxItmSynService.selectItmCdByItmNm(tds1st.get(3).text().substring(0, tds1st.get(3).text().length() - 7)));
							
							// 종목코드가 없을 경우 동의어를 입력한다.
							if(StringUtils.isEmpty(actTrdVO.getItmCd())) {
								// 종목코드는 파일에서 가져온 자료 사용
								actTrdVO.setItmCd(tds1st.get(3).text().substring(tds1st.get(3).text().length() - 6));
								krxItmSynService.insert(tds1st.get(3).text().substring(tds1st.get(3).text().length() - 6), tds1st.get(3).text().substring(0, tds1st.get(3).text().length() - 7));
							}
						}
						
						// 거래코드가 없을 경우 입력하지 않는다.
						if(StringUtils.isNotEmpty(actTrdVO.getTrdCd())) {
							// 입력
							insert(actTrdVO);
							
							// 주식이 계좌에 들어왔을 경우
							if("00006".equals(actTrdVO.getTrdCd())) {
								// 계좌 상품 거래 정보 입력
								actPrdtService.insert(actTrdVO);
								
								// 주식이 계좌에서 빠졌을 경우
							} else if("00007".equals(actTrdVO.getTrdCd())) {
								// 계좌 상품 거래 정보 수정
								actPrdtService.update(actTrdVO);
							}
						}
					}
				}
			}
		} catch(Exception e) {
			log.error("", e);
		}
	}
}
