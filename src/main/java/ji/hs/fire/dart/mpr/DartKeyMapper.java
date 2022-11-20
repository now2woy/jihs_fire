package ji.hs.fire.dart.mpr;

import java.util.List;

import ji.hs.fire.dart.vo.DartKeyVO;

/**
 * Dart Key 정보 Mapper
 * @author now2w
 *
 */
public interface DartKeyMapper {
	/**
	 * Dart Key 전체 조회
	 * @return
	 * @throws Exception
	 */
	public List<DartKeyVO> selectAll() throws Exception;
	/**
	 * Dart Key 단건 조회
	 * @param ord
	 * @return
	 * @throws Exception
	 */
	public DartKeyVO selectOne(int ord) throws Exception;
}
