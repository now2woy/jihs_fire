package ji.hs.fire.dart.vo;

import lombok.Data;

/**
 * 전자공시시스템 API KEY VO
 * @author now2w
 *
 */
@Data
public class DartKeyVO {
	/**
	 * API KEY
	 */
	private String apiKey;
	
	/**
	 * 순번
	 */
	private int ord;
}
