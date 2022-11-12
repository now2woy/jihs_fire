package ji.hs.fire.basic.mapper;

import java.util.List;

import ji.hs.fire.basic.vo.BasicCdVO;

/**
 * 코드 상세 Mapper
 * @author now2w
 *
 */
public interface BasicCdMapper {
	/**
	 * 코드 전체 조회
	 * @return
	 * @throws Exception
	 */
	public List<BasicCdVO> selectAllBasicCd() throws Exception;
	
	/**
	 * 코드 전체 조회
	 * @param cdCol
	 * @return
	 * @throws Exception
	 */
	public List<BasicCdVO> selectAllBasicCdByCdCol(String cdCol) throws Exception;
}
