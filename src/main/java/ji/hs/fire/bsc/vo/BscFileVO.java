package ji.hs.fire.bsc.vo;

import lombok.Data;

/**
 * 파일 정보 VO
 * @author now2w
 *
 */
@Data
public class BscFileVO {
	/**
	 * 파일일련번호
	 */
	private int fileSeq;
	/**
	 * 파일명
	 */
	private String fileNm;
	/**
	 * 원본파일명
	 */
	private String orgFileNm;
}
