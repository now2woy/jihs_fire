package ji.hs.fire.bsc.vo;

import lombok.Data;

/**
 * 코드 정보 VO
 * @author now2w
 *
 */
@Data
public class BscCdColVO {
	/**
	 * 코드컬럼
	 */
	private String cdCol;
	/**
	 * 코드컬럼명
	 */
	private String cdColNm;
	/**
	 * 사용여부
	 */
	private String useYn;
}
