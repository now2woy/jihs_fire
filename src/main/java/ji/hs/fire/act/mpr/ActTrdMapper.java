package ji.hs.fire.act.mpr;

import java.util.List;

import ji.hs.fire.act.vo.ActTrdVO;

/**
 * 계좌 거래 정보 Mapper
 * @author now2w
 *
 */
public interface ActTrdMapper {
	/**
	 * 계좌 거래 정보 전체 조회
	 * @param actTrdVO
	 * @return
	 * @throws Exception
	 */
	public List<ActTrdVO> selectAll(ActTrdVO actTrdVO) throws Exception;
	/**
	 * 계좌 거래 정보 건수 조회
	 * @param actTrdVO
	 * @return
	 * @throws Exception
	 */
	public int selectCount(ActTrdVO actTrdVO) throws Exception;
	/**
	 * 계좌 입출금 합계 금액 조회
	 * @param actTrdVO
	 * @return
	 * @throws Exception
	 */
	public String selectInOutSumAmt(ActTrdVO actTrdVO) throws Exception;
	/**
	 * 계좌 거래 정보 입력
	 * @param actTrdVO
	 * @return
	 * @throws Exception
	 */
	public int insert(ActTrdVO actTrdVO) throws Exception;
}
