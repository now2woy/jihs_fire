package ji.hs.fire.krx.mpr;

import ji.hs.fire.krx.vo.KrxItmSynVO;

/**
 * 한국거래소 주식종목 동의어 정보 Mapper
 * @author now2w
 *
 */
public interface KrxItmSynMapper {
	/**
	 * 한국거래소 주식종목 동의어 정보 Count 조회
	 * @param krxItmVO
	 * @return
	 * @throws Exception
	 */
	public int selectCount(KrxItmSynVO krxItmSynVO) throws Exception;
	/**
	 * 종목코드를 종목명으로 조회
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public String selectItmCdByItmNm(String value) throws Exception;
	/**
	 * 한국거래소 주식종목 동의어 정보 입력
	 * @param krxItmSynVO
	 * @return
	 * @throws Exception
	 */
	public int insert(KrxItmSynVO krxItmSynVO) throws Exception;
}
