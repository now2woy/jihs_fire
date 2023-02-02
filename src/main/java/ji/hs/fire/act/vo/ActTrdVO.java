package ji.hs.fire.act.vo;

import java.math.BigDecimal;
import java.util.List;

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
	private String trdSeq;
	/**
	 * 관련거래일련번호
	 */
	private String relTrdSeq;
	/**
	 * 계좌일련번호
	 */
	private String actSeq;
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
	private BigDecimal amt;
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
	private BigDecimal qty;
	/**
	 * 수수료
	 */
	private BigDecimal fee;
	/**
	 * 세금
	 */
	private BigDecimal tax;
	/**
	 * 비고
	 */
	private String note;
	/**
	 * 텔레그램ID
	 */
	private String tlgrmId;
	/**
	 * 텔레그램메시지ID
	 */
	private String tlgrmMsgId;
	/**
	 * 파일일련번호
	 */
	private String fileSeq;
	/**
	 * 거래일시
	 */
	private String trdDt;
	/**
	 * 만기일시
	 */
	private String edDt;
	/**
	 * 계좌번호
	 */
	private String actNo;
	/**
	 * 단가
	 */
	private BigDecimal prc;
	/**
	 * 거래코드목록
	 */
	private List<String> trdCds;
	/**
	 * 검색건수
	 */
	private int limit;
	/**
	 * 페이지번호
	 */
	private int offset;
}
