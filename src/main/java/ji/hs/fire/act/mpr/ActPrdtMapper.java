package ji.hs.fire.act.mpr;

import java.util.List;

import ji.hs.fire.act.vo.ActPrdtVO;

/**
 * 계좌 상품 거래 정보 Mapper
 * @author now2w
 *
 */
public interface ActPrdtMapper {
	/**
	 * 계좌 상품 거래 정보 목록 조회
	 * @param actPrdtVO
	 * @return
	 * @throws Exception
	 */
	public List<ActPrdtVO> selectAll(ActPrdtVO actPrdtVO) throws Exception;
	/**
	 * 계좌 상품 거래 정보 입력
	 * @param actPrdtVO
	 * @return
	 * @throws Exception
	 */
	public int insert(ActPrdtVO actPrdtVO) throws Exception;
	/**
	 * 계좌 상품 거래 정보 수정
	 * @param actPrdtVO
	 * @return
	 * @throws Exception
	 */
	public int update(ActPrdtVO actPrdtVO) throws Exception;
	/**
	 * 계좌 상품 거래 정보의 계쫘일련번호를 수정
	 * @param actPrdtVO
	 * @return
	 * @throws Exception
	 */
	public int updateActSeqByByTlgrmMsgId(ActPrdtVO actPrdtVO) throws Exception;
}
