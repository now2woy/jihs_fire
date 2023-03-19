package ji.hs.fire.dart.vo;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 전자공시시스템 재무제표 데이터 가공 VO
 * @author now2w
 *
 */
@Data
public class DartFnlttDtMfcVO {
	/**
	 * 전자공시시스템 종목코드
	 */
	private String dartItmCd;
	/**
	 * 연도(년도)
	 */
	private String tr;
	/**
	 * 분기코드
	 */
	private String qtCd;
	/**
	 * 자본
	 */
	private BigDecimal bscCpt;
	/**
	 * 지배 자본
	 */
	private BigDecimal ownCpt;
	/**
	 * 자산총계
	 */
	private BigDecimal astAmt;
	/**
	 * 부채총계
	 */
	private BigDecimal debtAmt;
	/**
	 * 매출액
	 */
	private BigDecimal salAmt;
	/**
	 * 매출총이익
	 */
	private BigDecimal salTotIncmAmt;
	/**
	 * 영업이익금액
	 */
	private BigDecimal oprIncmAmt;
	/**
	 * 당기순이익금액
	 */
	private BigDecimal tsNetIncmAmt;
	/**
	 * 지배주주당기순이익금액
	 */
	private BigDecimal ownTsNetIncmAmt;
	/**
	 * 당기기본주당순이익
	 */
	private BigDecimal tsBscEps;
	/**
	 * 당기희석주당순이익
	 */
	private BigDecimal tsDltdEps;
	/**
	 * 계속영업기본주당순이익
	 */
	private BigDecimal oprBscEps;
	/**
	 * 계속영업희석주당순이익
	 */
	private BigDecimal oprDltdEps;
	/**
	 * 영업활동현금흐름
	 */
	private BigDecimal oprCsflw;
	/**
	 * 부채비율
	 */
	private BigDecimal debtRt;
	/**
	 * 합계 매출액
	 */
	private BigDecimal addSalAmt;
	/**
	 * 합계 매출총이익
	 */
	private BigDecimal addSalTotIncmAmt;
	/**
	 * 합계 영업이익금액
	 */
	private BigDecimal addOprIncmAmt;
	/**
	 * 합계 당기순이익금액
	 */
	private BigDecimal addTsNetIncmAmt;
	/**
	 * 합계 지배주주당기순이익금액
	 */
	private BigDecimal addOwnTsNetIncmAmt;
	/**
	 * 합계 당기기본주당순이익
	 */
	private BigDecimal addTsBscEps;
	/**
	 * 합계 당기희석주당순이익
	 */
	private BigDecimal addTsDltdEps;
	/**
	 * 합계 계속영업기본주당순이익
	 */
	private BigDecimal addOprBscEps;
	/**
	 * 합계 계속영업희석주당순이익
	 */
	private BigDecimal addOprDltdEps;
}
