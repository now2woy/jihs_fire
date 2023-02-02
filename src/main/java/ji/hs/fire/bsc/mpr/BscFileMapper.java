package ji.hs.fire.bsc.mpr;

import ji.hs.fire.bsc.vo.BscFileVO;

/**
 * 파일 정보 Mapper
 * @author now2w
 *
 */
public interface BscFileMapper {
	/**
	 * 파일 정보 입력
	 * @param bscFileVO
	 * @throws Exception
	 */
	public int insert(BscFileVO bscFileVO) throws Exception;
}
