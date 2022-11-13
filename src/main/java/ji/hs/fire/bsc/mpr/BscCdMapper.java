package ji.hs.fire.bsc.mpr;

import java.util.List;

import org.apache.ibatis.annotations.Flush;

import ji.hs.fire.bsc.vo.BscCdVO;

/**
 * 코드 상세 Mapper
 * @author now2w
 *
 */
public interface BscCdMapper {
	/**
	 * 코드 상세 전체 조회
	 * @return
	 * @throws Exception
	 */
	public List<BscCdVO> selectAll() throws Exception;
	
	/**
	 * CD_COL 조건 코드 상세 전체 조회
	 * @param cdCol
	 * @return
	 * @throws Exception
	 */
	public List<BscCdVO> selectAllByCdCol(String cdCol) throws Exception;
	
	/**
	 * CD_COL, CD 조건 코드 상세 단건 조회
	 * @param cdCol
	 * @param cd
	 * @return
	 * @throws Exception
	 */
	public List<BscCdVO> selectOne(BscCdVO bscCdVO) throws Exception;
	
	/**
	 * CD_COL, CD 조건 코드 상세 Count 조회
	 * @param bscCdVO
	 * @return
	 * @throws Exception
	 */
	public int selectCountByCdColAndCdNm(BscCdVO bscCdVO) throws Exception;
	
	/**
	 * 코드 상세 입력
	 * @param bscCdVO
	 * @throws Exception
	 */
	@Flush
	public int insert(BscCdVO bscCdVO) throws Exception;
}
