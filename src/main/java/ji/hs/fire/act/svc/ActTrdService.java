package ji.hs.fire.act.svc;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ji.hs.fire.act.mpr.ActMapper;
import ji.hs.fire.act.mpr.ActTrdMapper;
import ji.hs.fire.act.vo.ActTrdVO;
import ji.hs.fire.act.vo.ActVO;
import ji.hs.fire.bsc.svc.BscNoGenService;
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
	 * 채번 Service
	 */
	private final BscNoGenService bscNoGenService;
	
	/**
	 * DART 종목코드는 압축파일로 다운로드 되어 파일 저장 경로가 필요
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
	 * 
	 * @param file
	 * @param actSeq
	 * @throws Exception
	 */
	public void excelUpload(MultipartFile file, String actSeq) throws Exception {
		
		String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		
		Files.copy(file.getInputStream(), Paths.get(uploadPath + File.separator + UUID.randomUUID().toString() + ext), StandardCopyOption.REPLACE_EXISTING);
	}
}
