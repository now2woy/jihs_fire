package ji.hs.fire.act.mpr;

import ji.hs.fire.act.vo.ActPrdtVO;

/**
 * 계좌 상품 거래 정보 Mapper
 * @author now2w
 *
 */
public interface ActPrdtMapper {
	/**
	 * 계좌 상품 거래 정보 입력
	 * @param actPrdtVO
	 * @return
	 * @throws Exception
	 */
	public int insert(ActPrdtVO actPrdtVO) throws Exception;
}
