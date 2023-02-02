package ji.hs.fire.act.vo;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 계좌 상품 거래 정보 VO
 * @author now2w
 *
 */
@Data
public class ActPrdtVO {
	/**
	 * 상품거래일련번호
	 */
	private String prdtTrdSeq;
	/**
	 * 계좌일련번호
	 */
	private String actSeq;
	/**
	 * 종목코드
	 */
	private String itmCd;
	/**
	 * 매수거래일련번호
	 */
	private String byTrdSeq;
	/**
	 * 매수금액
	 */
	private BigDecimal byAmt;
	/**
	 * 매수텔레그램메시지ID
	 */
	private String byTlgrmMsgId;
	/**
	 * 매수일시
	 */
	private String byDt;
	/**
	 * 매도거래일련번호
	 */
	private String slTrdSeq;
	/**
	 * 매도금액
	 */
	private BigDecimal slAmt;
	/**
	 * 매도텔레그램메시지ID
	 */
	private String slTlgrmMsgId;
	/**
	 * 매도일시
	 */
	private String slDt;
	/**
	 * 검색건수
	 */
	private int limit;
}
