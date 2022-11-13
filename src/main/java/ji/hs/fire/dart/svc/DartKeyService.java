package ji.hs.fire.dart.svc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ji.hs.fire.dart.mpr.DartKeyMapper;
import ji.hs.fire.dart.vo.DartKeyVO;

@Service
public class DartKeyService {
	@Autowired
	private DartKeyMapper dartKeyMapper;
	
	/**
	 * Dart Key 전체 조회
	 * @return
	 * @throws Exception
	 */
	public List<DartKeyVO> selectAll() throws Exception {
		return dartKeyMapper.selectAll();
	}
	
	/**
	 * Dart Key 단건 조회
	 * @param ordNum
	 * @return
	 * @throws Exception
	 */
	public DartKeyVO selectOne(int ord) throws Exception {
		return dartKeyMapper.selectOne(ord);
	}
}
