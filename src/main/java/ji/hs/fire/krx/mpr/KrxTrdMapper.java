package ji.hs.fire.krx.mpr;

import java.util.List;

import ji.hs.fire.krx.vo.KrxTrdVO;

/**
 * 한국거래소 종목 거래 정보 Mapper
 * @author now2w
 *
 */
public interface KrxTrdMapper {
	/**
	 *  한국거래소 거래 정보 건수 조회
	 * @param krxTrdVO
	 * @return
	 * @throws Exception
	 */
	public int selectCount(KrxTrdVO krxTrdVO) throws Exception;
	/**
	 * 한국거래소 거래 정보 조회
	 * @param krxTrdVO
	 * @return
	 * @throws Exception
	 */
	public List<KrxTrdVO> selectAll(KrxTrdVO krxTrdVO) throws Exception;
	/**
	 * 한국거래소 거래 정보 입력
	 * @param krxTrdVO
	 * @throws Exception
	 */
	public int insert(KrxTrdVO krxTrdVO) throws Exception;
}
