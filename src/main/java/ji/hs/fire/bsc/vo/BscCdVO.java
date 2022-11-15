package ji.hs.fire.bsc.vo;

import lombok.Builder;
import lombok.Data;

/**
 * 코드 상세 VO
 * @author now2w
 *
 */
@Data
@Builder
public class BscCdVO {
	/**
	 * 코드컬럼
	 */
	private String cdCol;
	
	/**
	 * 코드
	 */
	private String cd;
	
	/**
	 * 코드명
	 */
	private String cdNm;
	
	/**
	 * 순번
	 */
	private int ord;
	
	/**
	 * 사용여부
	 */
	private String useYn;
}
