package ji.hs.fire.bsc.mpr;

import java.util.List;

import ji.hs.fire.bsc.vo.BscUserVO;

/**
 * 사용자 정보 Mapper
 * @author now2w
 *
 */
public interface BscUserMapper {
	/**
	 * 사용자 정보 전체 조회
	 * @param bscUserVO
	 * @return
	 * @throws Exception
	 */
	public List<BscUserVO> selectAll(BscUserVO bscUserVO) throws Exception;
	/**
	 * 사용자 정보 건수 조회
	 * @param bscUserVO
	 * @return
	 * @throws Exception
	 */
	public int selectCount(BscUserVO bscUserVO) throws Exception;
	/**
	 * 사용자 정보 수정
	 * @param bscUserVO
	 * @return
	 * @throws Exception
	 */
	public int update(BscUserVO bscUserVO) throws Exception;
	/**
	 * 사용자 정보 입력
	 * @param bscUserVO
	 * @return
	 * @throws Exception
	 */
	public int insert(BscUserVO bscUserVO) throws Exception;
}
