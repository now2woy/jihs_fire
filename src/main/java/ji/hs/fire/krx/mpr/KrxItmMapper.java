package ji.hs.fire.krx.mpr;

import java.util.List;

import ji.hs.fire.krx.vo.KrxItmVO;

/**
 * 한국거래소 종목 정보 Mapper
 * @author now2w
 *
 */
public interface KrxItmMapper {
	/**
	 *  ITM_CD 조건 한국거래소 종목 정보 Count 조회
	 * @param krxItmVO
	 * @return
	 * @throws Exception
	 */
	public int selectCount(KrxItmVO krxItmVO) throws Exception;
	
	/**
	 * ITM_CD 조건 한국거래소 종목 정보 단건 조회
	 * @param krxItmVO
	 * @return
	 * @throws Exception
	 */
	public KrxItmVO selectOne(KrxItmVO krxItmVO) throws Exception;
	
	/**
	 * 종목코드를 종목명으로 조회
	 * @param krxItmVO
	 * @return
	 * @throws Exception
	 */
	public String selectItmCdByItmNm(String value) throws Exception;
	
	/**
	 * ITM_CD 조건 한국거래소 종목 정보 목록 조회
	 * @param krxItmVO
	 * @return
	 * @throws Exception
	 */
	public List<KrxItmVO> selectAll(KrxItmVO krxItmVO) throws Exception;
	
	/**
	 * 한국거래소 종목 입력
	 * @param krxItmVO
	 * @throws Exception
	 */
	public int insert(KrxItmVO krxItmVO) throws Exception;
	
	/**
	 * 한국거래소 종목 수정
	 * @param krxItmVO
	 * @throws Exception
	 */
	public int update(KrxItmVO krxItmVO) throws Exception;
}
