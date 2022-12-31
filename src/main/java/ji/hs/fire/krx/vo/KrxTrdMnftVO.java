package ji.hs.fire.krx.vo;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 한국거래소 거래 가공 정보 VO
 * @author now2w
 *
 */
@Data
public class KrxTrdMnftVO {
	/**
	 * 종목코드
	 */
	private String itmCd;
	/**
	 * 거래일자
	 */
	private String dt;
	/**
	 * 거래회전율
	 */
	private BigDecimal tnovRt;
	/**
	 * 5일 이동평균금액
	 */
	private BigDecimal dy005AvgAmt;
	/**
	 * 20일 이동평균금액
	 */
	private BigDecimal dy020AvgAmt;
	/**
	 * 60일 이동평균금액
	 */
	private BigDecimal dy060AvgAmt;
	/**
	 * 120일 이동평균금액
	 */
	private BigDecimal dy120AvgAmt;
	/**
	 * EPS(주당순이익)
	 */
	private BigDecimal eps;
	/**
	 * BPS(주당순자산가치)
	 */
	private BigDecimal bps;
	/**
	 * CPS(주당현금흐름)
	 */
	private BigDecimal cps;
	/**
	 * SPS(주당매출액)
	 */
	private BigDecimal sps;
	/**
	 * PER(주가수익비율)
	 */
	private BigDecimal per;
	/**
	 * PBR(주가순자산비율)
	 */
	private BigDecimal pbr;
	/**
	 * PCR(주가현금흐름비율)
	 */
	private BigDecimal pcr;
	/**
	 * PSR(주가매출비율)
	 */
	private BigDecimal psr;
}
