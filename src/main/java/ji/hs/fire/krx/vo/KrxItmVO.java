package ji.hs.fire.krx.vo;

import lombok.Data;

/**
 * 한국거래소 종목 정보 VO
 * @author now2w
 *
 */
@Data
public class KrxItmVO {
	/**
	 * 종목코드
	 */
	private String itmCd;
	/**
	 * 종목명
	 */
	private String itmNm;
	/**
	 * 시장코드
	 */
	private String mktCd;
	/**
	 * 시장명
	 */
	private String mktNm;
	/**
	 * 표준종목코드
	 */
	private String stdItmCd;
	/**
	 * 상장일자
	 */
	private String pubDt;
	/**
	 * 스팩여부
	 */
	private String spacYn;
	/**
	 * 주식종류코드
	 */
	private String itmKndCd;
	/**
	 * 주식종류명
	 */
	private String itmKndNm;
	/**
	 * 주식구분코드
	 */
	private String itmClCd;
	/**
	 * 주식구분명
	 */
	private String itmClNm;
}
