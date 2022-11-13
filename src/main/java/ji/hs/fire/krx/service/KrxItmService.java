package ji.hs.fire.krx.service;

import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ji.hs.fire.bsc.mapper.BscCdMapper;
import ji.hs.fire.bsc.util.BscUtils;
import ji.hs.fire.bsc.vo.BscCdVO;
import ji.hs.fire.krx.mapper.KrxItmMapper;
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
	@Transactional
	public boolean krxItmCollection() throws Exception {
		// 시장코드 조회
		final List<BscCdVO> bscCdList = bscCdMapper.selectAllByCdCol("MKT_CD");
		boolean result = true;
		
		// 시장코드별로 URL 호출
		for(BscCdVO bscCdVO : bscCdList) {
			log.info("{} 수집 시작", bscCdVO.getCd());
			
			// KRX URL 호출
			Document doc = Jsoup.connect(krxJsonUrl)
					.data("bld", "dbms/MDC/STAT/standard/MDCSTAT01901")
					.data("mktId", bscCdVO.getCd())
					.get();
			
			Map<String, Object> map = BscUtils.jsonParse(doc.text());
			
			for(Map<String, String> json : (List<Map<String, String>>)map.get("OutBlock_1")) {
				KrxItmVO krxItmVO = new KrxItmVO();
				
				krxItmVO.setItmCd(json.get("ISU_SRT_CD"));
				krxItmVO.setItmNm(json.get("ISU_NM"));
				krxItmVO.setMktCd(bscCdVO.getCd());
				krxItmVO.setPubDt(json.get("LIST_DD"));
				krxItmVO.setStdItmCd(json.get("ISU_CD"));
				
				if(krxItmMapper.selectCount(krxItmVO) != 1) {
					krxItmMapper.insert(krxItmVO);
				}
			}
			
			log.info("{} 수집 종료", bscCdVO.getCd());
		}
		
		return result;
	}
}
