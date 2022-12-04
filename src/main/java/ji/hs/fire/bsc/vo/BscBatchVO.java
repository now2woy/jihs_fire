package ji.hs.fire.bsc.vo;

import lombok.Data;

/**
 * 배치 정보 VO
 * @author now2w
 *
 */
@Data
public class BscBatchVO {
	/**
	 * 일련번호
	 */
	private int seq;
	/**
	 * 배치코드
	 */
	private String batchCd;
	/**
	 * 배치명
	 */
	private String batchNm;
	/**
	 * 파라미터 1
	 */
	private String parm1st;
	/**
	 * 파라미터 2
	 */
	private String parm2nd;
	/**
	 * 파라미터 3
	 */
	private String parm3rd;
	/**
	 * 파라미터 4
	 */
	private String parm4th;
	/**
	 * 실행여부
	 */
	private String exeYn;
	/**
	 * 성공여부
	 */
	private String sucYn;
	/**
	 * 실행시작일시
	 */
	private String exeStDt;
	/**
	 * 실행종료일시
	 */
	private String exeEdDt;
	/**
	 * 검색건수
	 */
	private int limit;
	/**
	 * 페이지번호
	 */
	private int offset;
	/**
	 * 정렬 : 1 : SEQ ASC, 2 : SEQ DESC
	 */
	private int order;
	/**
	 * 업데이트숫자 = 1 : 배치 시작, 2 : 배치 종료
	 */
	private int updCnt;
}
