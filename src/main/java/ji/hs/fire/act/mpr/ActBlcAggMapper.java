package ji.hs.fire.act.mpr;

import ji.hs.fire.act.vo.ActBlcAggVO;

/**
 * 계좌 잔고 집계 정보 Mapper
 * @author now2w
 *
 */
public interface ActBlcAggMapper {
	/**
	 * 하루치 계좌 집계 정보 조회
	 * @param actBlcAggVO
	 * @return
	 * @throws Exception
	 */
	public ActBlcAggVO selectOne(ActBlcAggVO actBlcAggVO) throws Exception;
	/**
	 * 계좌 잔고 집계 정보 입력
	 * @param actBlcAggVO
	 * @return
	 * @throws Exception
	 */
	public int insert(ActBlcAggVO actBlcAggVO) throws Exception;
}
