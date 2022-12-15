package ji.hs.fire.bsc.vo;

import lombok.Data;

/**
 * 사용자 정보 VO
 * @author now2w
 *
 */
@Data
public class BscUserVO {
	/**
	 * 사용자ID
	 */
	private String usrId;
	/**
	 * 사용자성명
	 */
	private String usrNm;
	/**
	 * 검색건수
	 */
	private int limit;
	/**
	 * 페이지번호
	 */
	private int offset;
}
