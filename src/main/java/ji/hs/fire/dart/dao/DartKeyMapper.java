package ji.hs.fire.dart.dao;

import java.util.List;

public interface DartKeyMapper {
	/**
	 * Dart Key 전체 조회
	 * @return
	 * @throws Exception
	 */
	public List<DartKeyVO> selectAllDartKey() throws Exception;
	
	/**
	 * Dart Key 단건 조회
	 * @return
	 * @throws Exception
	 */
	public DartKeyVO selectOneDartKey(int ordNum) throws Exception;
}
