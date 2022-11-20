package ji.hs.fire.dart.mpr;

import ji.hs.fire.dart.vo.DartFnlttVO;

/**
 * 전자공시시스템 재무제표 정보 Mapper
 * @author now2w
 *
 */
public interface DartFnlttMapper {
	/**
	 * 전자공시시스템 재무제표 입력
	 * @param dartItmVO
	 * @throws Exception
	 */
	public void insert(DartFnlttVO dartFnlttVO) throws Exception;
}
