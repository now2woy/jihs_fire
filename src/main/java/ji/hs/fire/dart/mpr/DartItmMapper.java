package ji.hs.fire.dart.mpr;

import org.apache.ibatis.annotations.Flush;

import ji.hs.fire.dart.vo.DartItmVO;

public interface DartItmMapper {
	/**
	 *  DART_ITM_CD 조건 전자공시시스템 종목 마스터 Count 조회
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
	@Flush
	public void insert(DartItmVO dartItmVO) throws Exception;
	
	/**
	 * 전자공시시스템 종목 수정
	 * @param dartItmVO
	 * @throws Exception
	 */
	@Flush
	public void update(DartItmVO dartItmVO) throws Exception;
}
