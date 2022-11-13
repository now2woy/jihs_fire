package ji.hs.fire.dart.svc;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ji.hs.fire.bsc.util.BscUtils;
import ji.hs.fire.dart.mpr.DartItmMapper;
import ji.hs.fire.dart.mpr.DartKeyMapper;
import ji.hs.fire.dart.vo.DartItmVO;
import ji.hs.fire.dart.vo.DartKeyVO;
import lombok.extern.slf4j.Slf4j;

/**
 * 전자공시시스템 종목 마스터 Service
 * @author now2w
 *
 */
@Slf4j
@Service
public class DartItmService {
	@Autowired
	private DartKeyMapper dartKeyMapper;
	
	@Autowired
	private DartItmMapper dartItmMapper;
	
	/**
	 * DART COPR CD URL
	 */
	@Value("${constant.dart.url.coprCd}")
	private String dartCoprCdUrl;
	
	/**
	 * DART 종목코드는 압축파일로 다운로드 되어 파일 저장 경로가 필요
	 */
	@Value("${constant.path.download}")
	private String downloadPath;
	
	/**
	 * 전자공시시스템 종목 코드 파일 다운로드
	 * @throws Exception
	 */
	public boolean dartCoprCdDownload() throws Exception {
		log.info("전자공시시스템 종목 코드 파일 다운로드 시작");
		
		boolean result = true;
		
		InputStream is = null;
		
		try {
			DartKeyVO dartKeyVO = dartKeyMapper.selectOne(1);
			
			URL url = new URL(dartCoprCdUrl + "?crtfc_key=" + dartKeyVO.getApiKey());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			
			is = conn.getInputStream();
			
			BscUtils.unzip(is, new File(downloadPath), "UTF-8");
		} catch(Exception e) {
			result = false;
			log.error("", e);
		} finally {
			IOUtils.closeQuietly(is);
		}
		
		log.info("전자공시시스템 종목 코드 파일 다운로드 종료");
		
		return result;
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	@Transactional
	public void dartCoprCdCollection() throws Exception {
		log.info("전자공시시스템 종목 파일 처리 시작");
		
		XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
		XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(downloadPath + "/CORPCODE.xml"));
		
		DartItmVO dartItmVO = null;
		
		while (reader.hasNext()) {
			XMLEvent nextEvent = reader.nextEvent();
			
			// 여는 태그
			if (nextEvent.isStartElement()) {
				StartElement startElement = nextEvent.asStartElement();
				// 여는 태그가 list 일 경우
				if (startElement.getName().getLocalPart().equals("list")) {
					// 객체 초기화
					dartItmVO = new DartItmVO();
					
				// DART_ITM_CD
				}else if(startElement.getName().getLocalPart().equals("corp_code")) {
					nextEvent = reader.nextEvent();
					dartItmVO.setDartItmCd(nextEvent.asCharacters().getData());
					
				// DART_ITM_CD_NM
				}else if(startElement.getName().getLocalPart().equals("corp_name")) {
					nextEvent = reader.nextEvent();
					dartItmVO.setDartItmCdNm(nextEvent.asCharacters().getData());
					
				// ITM_CD
				}else if(startElement.getName().getLocalPart().equals("stock_code")) {
					nextEvent = reader.nextEvent();
					dartItmVO.setItmCd(nextEvent.asCharacters().getData().trim());
					
				// 수정일자
				}else if(startElement.getName().getLocalPart().equals("modify_date")) {
					nextEvent = reader.nextEvent();
					dartItmVO.setModDt(nextEvent.asCharacters().getData().trim());
				}
			}
			
			// 닫는태그
			if (nextEvent.isEndElement()) {
				// 닫는태그명이 list 일 경우
				if (nextEvent.asEndElement().getName().getLocalPart().equals("list")) {
					// 데이터가 있을 경우 수정
					if(dartItmMapper.selectCount(dartItmVO) == 1) {
						// 수정
						dartItmMapper.update(dartItmVO);
						
					// 데이터가 없을 경우 입력
					} else {
						// 입력
						dartItmMapper.insert(dartItmVO);
					}
				}
			}
		}
		
		log.info("전자공시시스템 종목 파일 처리 종료");
	}
}
