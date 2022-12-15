package ji.hs.fire.bsc.mpr;

import java.util.List;

import ji.hs.fire.bsc.vo.BscBatchVO;

/**
 * 배치 정보 Mapper
 * @author now2w
 *
 */
public interface BscBatchMapper {
	/**
	 * 배치 정보 전체 조회
	 * @param bscBatchVO
	 * @return
	 * @throws Exception
	 */
	public List<BscBatchVO> selectAll(BscBatchVO bscBatchVO) throws Exception;
	/**
	 * 배치 정보 건수 조회
	 * @param bscBatchVO
	 * @return
	 * @throws Exception
	 */
	public int selectCount(BscBatchVO bscBatchVO) throws Exception;
	/**
	 * 배치 정보 수정
	 * @param bscBatchVO
	 * @return
	 * @throws Exception
	 */
	public int update(BscBatchVO bscBatchVO) throws Exception;
	/**
	 * 배치 정보 입력
	 * @param bscBatchVO
	 * @return
	 * @throws Exception
	 */
	public int insert(BscBatchVO bscBatchVO) throws Exception;
}
