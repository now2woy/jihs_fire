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
	 * 코드 컬럼 전체 조회
	 * @return
	 * @throws Exception
	 */
	public List<BscCdVO> selectAllCdColumn() throws Exception;
	
	/**
	 * 코드 상세 전체 조회
	 * @return
	 * @throws Exception
	 */
	public List<BscCdVO> selectAll(BscCdVO bscCdVO) throws Exception;
	
	/**
	 * CD_COL, CD 조건 코드 상세 단건 조회
	 * @param cdCol
	 * @param cd
	 * @return
	 * @throws Exception
	 */
	public List<BscCdVO> selectOne(BscCdVO bscCdVO) throws Exception;
	
	/**
	 * 코드 상세 Count 조회
	 * @param bscCdVO
	 * @return
	 * @throws Exception
	 */
	public int selectCount(BscCdVO bscCdVO) throws Exception;
	
	/**
	 * 코드 상세 입력
	 * @param bscCdVO
	 * @throws Exception
	 */
	@Flush
	public int insert(BscCdVO bscCdVO) throws Exception;
	
	/**
	 * 코드 상세 수정
	 * @param bscCdVO
	 * @throws Exception
	 */
	@Flush
	public int update(BscCdVO bscCdVO) throws Exception;
	
	/**
	 * 코드 상세 삭제
	 * @param bscCdVO
	 * @throws Exception
	 */
	@Flush
	public int delete(BscCdVO bscCdVO) throws Exception;
}
