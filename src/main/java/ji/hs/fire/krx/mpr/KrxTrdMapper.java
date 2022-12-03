package ji.hs.fire.krx.mpr;

import ji.hs.fire.krx.vo.KrxTrdVO;

/**
 * 한국거래소 종목 거래 정보 Mapper
 * @author now2w
 *
 */
public interface KrxTrdMapper {
	
	/**
	 * 한국거래소 거래 정보 입력
	 * @param krxTrdVO
	 * @throws Exception
	 */
	public int insert(KrxTrdVO krxTrdVO) throws Exception;
}
