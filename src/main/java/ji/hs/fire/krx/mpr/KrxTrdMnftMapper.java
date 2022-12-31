package ji.hs.fire.krx.mpr;

import ji.hs.fire.krx.vo.KrxTrdMnftVO;

/**
 * 한국거래소 종목 거래 가공 정보 Mapper
 * @author now2w
 *
 */
public interface KrxTrdMnftMapper {
	/**
	 *  한국거래소 거래 가공 정보 건수 조회
	 * @param krxTrdMnftVO
	 * @return
	 * @throws Exception
	 */
	public int selectCount(KrxTrdMnftVO krxTrdMnftVO) throws Exception;
	/**
	 * 한국거래소 거래 가공 정보 입력
	 * @param krxTrdMnftVO
	 * @throws Exception
	 */
	public int insert(KrxTrdMnftVO krxTrdMnftVO) throws Exception;
	/**
	 * 한국거래소 거래 가공 정보 수정
	 * @param krxTrdMnftVO
	 * @throws Exception
	 */
	public int update(KrxTrdMnftVO krxTrdMnftVO) throws Exception;
}
