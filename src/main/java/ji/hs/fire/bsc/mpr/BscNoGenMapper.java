package ji.hs.fire.bsc.mpr;

import ji.hs.fire.bsc.vo.BscNoGenVO;

/**
 * 채번 Mapper
 * @author now2w
 *
 */
public interface BscNoGenMapper {
	/**
	 * 채번 단건 조회
	 * @param genKey
	 * @return
	 * @throws Exception
	 */
	public BscNoGenVO selectOne(String genKey) throws Exception;
	/**
	 * 채번 입력
	 * @param genKey
	 * @return
	 * @throws Exception
	 */
	public int insert(String genKey) throws Exception;
	/**
	 * 채번 수정
	 * @param genKey
	 * @return
	 * @throws Exception
	 */
	public int update(String genKey) throws Exception;
}
