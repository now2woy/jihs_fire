package ji.hs.fire.bsc.vo;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 채번 정보 VO
 * @author now2w
 *
 */
@Data
public class BscNoGenVO {
	/**
	 * 채번키
	 */
	private String genKey;
	/**
	 * 번호
	 */
	private BigDecimal num;
}
