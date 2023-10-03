package ji.hs.fire.act.vo;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 계좌 잔고 집계 정보 VO
 * @author now2w
 *
 */
@Data
public class ActBlcAggVO {
	/**
	 * 계좌일련번호
	 */
	private String actSeq;
	/**
	 * 집계일자
	 */
	private String aggDt;
	/**
	 * 잔고금액
	 */
	private BigDecimal blcAmt;
}
