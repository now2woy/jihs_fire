package ji.hs.fire.act.vo;

import lombok.Data;

/**
 * 계좌 거래 정보 VO
 * @author now2w
 *
 */
@Data
public class ActTrdVO {
	/**
	 * 거래일련번호
	 */
	private int trdSeq;
	/**
	 * 관련거래일련번호
	 */
	private int relTrdSeq;
	/**
	 * 계좌일련번호
	 */
	private int actSeq;
	/**
	 * 거래코드
	 */
	private String trdCd;
	/**
	 * 거래코드명
	 */
	private String trdNm;
	/**
	 * 금액
	 */
	private int amt;
	/**
	 * 종목코드
	 */
	private String itmCd;
	/**
	 * 종목명
	 */
	private String itmNm;
	/**
	 * 수량
	 */
	private int qty;
	/**
	 * 비고
	 */
	private String note;
	/**
	 * 거래일시
	 */
	private String trdDt;
	/**
	 * 만기일시
	 */
	private String edDt;
	/**
	 * 검색건수
	 */
	private int limit;
	/**
	 * 페이지번호
	 */
	private int offset;
}
