package ji.hs.fire.dart.vo;

import lombok.Data;

/**
 * 전자공시시스템 종목 마스터 VO
 * @author now2w
 *
 */
@Data
public class DartItmVO {
	/**
	 * 전자공시시스템 종목코드
	 */
	private String dartItmCd;
	
	/**
	 * 전자공시시스템 종목코드명
	 */
	private String dartItmCdNm;
	
	/**
	 * 종목코드
	 */
	private String itmCd;
	
	/**
	 * 상장일자
	 */
	private String modDt;
}
