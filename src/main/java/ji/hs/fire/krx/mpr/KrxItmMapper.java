package ji.hs.fire.krx.mpr;

import org.apache.ibatis.annotations.Flush;

import ji.hs.fire.krx.vo.KrxItmVO;

/**
 * 한국거래소 종목 마스터 Mapper
 * @author now2w
 *
 */
public interface KrxItmMapper {
	/**
	 *  ITM_CD 조건 한국거래소 종목 마스터 Count 조회
	 * @param krxItmVO
	 * @return
	 * @throws Exception
	 */
	public int selectCount(KrxItmVO krxItmVO) throws Exception;
	
	/**
	 * ITM_CD 조건 한국거래소 종목 마스터 단건 조회
	 * @param krxItmVO
	 * @return
	 * @throws Exception
	 */
	public KrxItmVO selectOne(KrxItmVO krxItmVO) throws Exception;
	
	/**
	 * 한국거래소 종목 입력
	 * @param krxItmVO
	 * @throws Exception
	 */
	@Flush
	public int insert(KrxItmVO krxItmVO) throws Exception;
	
	/**
	 * 한국거래소 종목 수정
	 * @param krxItmVO
	 * @throws Exception
	 */
	@Flush
	public int update(KrxItmVO krxItmVO) throws Exception;
}
