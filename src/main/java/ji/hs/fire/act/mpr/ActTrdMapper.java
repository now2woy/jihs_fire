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
	 * 계좌 거래 정보 건수 조회
	 * @param actTrdVO
	 * @return
	 * @throws Exception
	 */
	public int countByActSeq(ActTrdVO actTrdVO) throws Exception;
	/**
	 * 계좌 거래정보 입력 전 중복 데이터 확인을 위한 건수 조회
	 * @param actTrdVO
	 * @return
	 * @throws Exception
	 */
	public int countByActSeqAndTrdCdAndTrdDtAndAmt(ActTrdVO actTrdVO) throws Exception;
	/**
	 * 계좌 거래 정보 전체 조회
	 * @param actTrdVO
	 * @return
	 * @throws Exception
	 */
	public List<ActTrdVO> selectAll(ActTrdVO actTrdVO) throws Exception;
	/**
	 * 계좌 합계 금액 조회
	 * @param actTrdVO
	 * @return
	 * @throws Exception
	 */
	public String selectSumAmt(ActTrdVO actTrdVO) throws Exception;
	/**
	 * 계좌 거래 정보 입력
	 * @param actTrdVO
	 * @return
	 * @throws Exception
	 */
	public int insert(ActTrdVO actTrdVO) throws Exception;
	/**
	 * 계좌 거래 정보 수정
	 * @param actTrdVO
	 * @return
	 * @throws Exception
	 */
	public int updateActSeqByTlgrmMsgId(ActTrdVO actTrdVO) throws Exception;
}
