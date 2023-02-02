package ji.hs.fire.krx.vo;

import lombok.Data;

/**
 * 한국거래소 주식종목 동의어 VO
 * @author now2w
 *
 */
@Data
public class KrxItmSynVO {
	/**
	 * 동의어일련번호
	 */
	private int synSeq;
	/**
	 * 종목코드
	 */
	private String itmCd;
	/**
	 * 종목명
	 */
	private String itmNm;
}
