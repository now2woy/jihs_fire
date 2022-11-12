package ji.hs.fire.dart.vo;

import lombok.Data;

/**
 * 전자공시시스템 재무제표 원본 VO
 * @author now2w
 *
 */
@Data
public class DartFnlttVO {
	
	/**
	 * 종목코드
	 */
	private String itmCd;
	
	/**
	 * 연도(년도)
	 */
	private String yr;
	
	/**
	 * 분기
	 */
	private String qt;
	
	/**
	 * 일련번호
	 */
	private int seq;
	
	/**
	 * 시장코드
	 */
	private String mktCd;
	
	/**
	 * 분기코드
	 */
	private String reprtCd;
	
	/**
	 * 구분코드
	 */
	private String sjCd;
	
	/**
	 * 구분명
	 */
	private String sjNm;
	
	/**
	 * 계정ID
	 */
	private String acntId;
	
	/**
	 * 계정명
	 */
	private String acntNm;
	
	/**
	 * 계정상세
	 */
	private String acntDtl;
	
	/**
	 * 이번분기명
	 */
	private String thTmNm;
	
	/**
	 * 이번분기값
	 */
	private String thTmAmt;
	
	/**
	 * 이번분기누적값
	 */
	private String thTmAddAmt;
	
	/**
	 * 이전분기명
	 */
	private String frmTmNm;
	
	/**
	 * 이전분기값
	 */
	private String frmTmAmt;
	
	/**
	 * 이전분기누적값
	 */
	private String frmTmAddAmt;
	
	/**
	 * 순번
	 */
	private int ord;
}
