package ji.hs.fire.act.vo;

import lombok.Data;

/**
 * 계좌 정보 VO
 * @author now2w
 *
 */
@Data
public class ActVO {
	/**
	 * 계좌일련번호
	 */
	private int actSeq;
	/**
	 * 계좌명
	 */
	private String actNm;
	/**
	 * 계좌코드
	 */
	private String actCd;
	/**
	 * 계좌코드명
	 */
	private String actCdNm;
	/**
	 * 은행코드
	 */
	private String bkCd;
	/**
	 * 은행명
	 */
	private String bkNm;
	/**
	 * 사용자ID(소유주)
	 */
	private String usrId;
	/**
	 * 사용자성명(소유주)
	 */
	private String usrNm;
	/**
	 * 사용여부
	 */
	private String useYn;
	/**
	 * 검색건수
	 */
	private int limit;
	/**
	 * 페이지번호
	 */
	private int offset;
}
