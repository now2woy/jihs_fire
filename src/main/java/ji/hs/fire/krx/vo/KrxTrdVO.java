package ji.hs.fire.krx.vo;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 한국거래소 거래 정보 VO
 * @author now2w
 *
 */
@Data
public class KrxTrdVO {
	/**
	 * 종목코드
	 */
	private String itmCd;
	/**
	 * 거래일자
	 */
	private String dt;
	/**
	 * 시가
	 */
	private BigDecimal stAmt;
	/**
	 * 종가
	 */
	private BigDecimal edAmt;
	/**
	 * 저가
	 */
	private BigDecimal lwAmt;
	/**
	 * 고가
	 */
	private BigDecimal hgAmt;
	/**
	 * 증감액
	 */
	private BigDecimal incrAmt;
	/**
	 * 거래량
	 */
	private BigDecimal trdQty;
	/**
	 * 시가총액
	 */
	private BigDecimal mktTotAmt;
	/**
	 * 발행주식수
	 */
	private BigDecimal isuStkQty;
	/**
	 * 검색건수
	 */
	private int limit;
}
