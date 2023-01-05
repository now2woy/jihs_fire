package ji.hs.fire.bsc.mpr;

import java.util.List;

import ji.hs.fire.bsc.vo.BscCdVO;

/**
 * 코드 정보 Mapper
 * @author now2w
 *
 */
public interface BscCdMapper {
	/**
	 * 코드 정보 전체 조회
	 * @param bscCdVO
	 * @return
	 * @throws Exception
	 */
	public List<BscCdVO> selectAll(BscCdVO bscCdVO) throws Exception;
	/**
	 * 코드 정보 1건 조회
	 * @param bscCdVO
	 * @return
	 * @throws Exception
	 */
	public BscCdVO selectOne(BscCdVO bscCdVO) throws Exception;
	/**
	 * 코드 정보 Count 조회
	 * @param bscCdVO
	 * @return
	 * @throws Exception
	 */
	public int selectCount(BscCdVO bscCdVO) throws Exception;
	/**
	 * 코드 정보 입력
	 * @param bscCdVO
	 * @throws Exception
	 */
	public int insert(BscCdVO bscCdVO) throws Exception;
	/**
	 * 코드 정보 입력
	 * @param bscCdVO
	 * @throws Exception
	 */
	public int insertBatch(BscCdVO bscCdVO) throws Exception;
}
