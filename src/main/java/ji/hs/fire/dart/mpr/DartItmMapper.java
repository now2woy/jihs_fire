package ji.hs.fire.dart.mpr;

import java.util.List;

import ji.hs.fire.dart.vo.DartItmVO;

/**
 * 전자공시시스템 종목 정보 Mapper
 * @author now2w
 *
 */
public interface DartItmMapper {
	/**
	 * 전자공시시스템 종목 전체 조회
	 * @return
	 * @throws Exception
	 */
	public List<DartItmVO> selectAll(DartItmVO dartItmVO) throws Exception;
	/**
	 *  DART_ITM_CD 조건 전자공시시스템 종목 Count 조회
	 * @param dartItmVO
	 * @return
	 * @throws Exception
	 */
	public int selectCount(DartItmVO dartItmVO) throws Exception;
	/**
	 * 전자공시시스템 종목 입력
	 * @param dartItmVO
	 * @throws Exception
	 */
	public void insert(DartItmVO dartItmVO) throws Exception;
	/**
	 * 전자공시시스템 종목 수정
	 * @param dartItmVO
	 * @throws Exception
	 */
	public void update(DartItmVO dartItmVO) throws Exception;
}
