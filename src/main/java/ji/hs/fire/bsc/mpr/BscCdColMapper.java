package ji.hs.fire.bsc.mpr;

import java.util.List;

import ji.hs.fire.bsc.vo.BscCdColVO;

/**
 * 코드 컬럼 정보 Mapper
 * @author now2w
 *
 */
public interface BscCdColMapper {
	/**
	 * 코드 컬럼 정보 전체 조회
	 * @return
	 * @throws Exception
	 */
	public List<BscCdColVO> selectAll() throws Exception;
	/**
	 * 코드 컬럼 정보 건수 조회
	 * @param bscCdColVO
	 * @return
	 * @throws Exception
	 */
	public int selectCount(BscCdColVO bscCdColVO) throws Exception;
	/**
	 * 코드 컬럼 정보 입력
	 * @param bscCdColVO
	 * @throws Exception
	 */
	public int insert(BscCdColVO bscCdColVO) throws Exception;
	/**
	 * 코드 컬럼 정보 수정
	 * @param bscCdColVO
	 * @throws Exception
	 */
	public int update(BscCdColVO bscCdColVO) throws Exception;
}
