package ji.hs.fire.act.mpr;

import java.util.List;

import ji.hs.fire.act.vo.ActVO;

/**
 * 계좌 정보 Mapper
 * @author now2w
 *
 */
public interface ActMapper {
	/**
	 * 계좌 정보 전체 조회
	 * @param actVO
	 * @return
	 * @throws Exception
	 */
	public List<ActVO> selectAll(ActVO actVO) throws Exception;
	/**
	 * 계좌 정보 1건 조회
	 * @param actVO
	 * @return
	 * @throws Exception
	 */
	public ActVO selectOne(ActVO actVO) throws Exception;
	/**
	 * 계좌 정보 건수 조회
	 * @param actVO
	 * @return
	 * @throws Exception
	 */
	public int selectCount(ActVO actVO) throws Exception;
	/**
	 * 계좌일련번호 조회
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public String selectActSeqByActNo(String value) throws Exception;
	/**
	 * 계좌일련번호 조회
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public String selectActSeqByTlgrmId(String value) throws Exception;
	/**
	 * 계좌 정보 입력
	 * @param actVO
	 * @return
	 * @throws Exception
	 */
	public int insert(ActVO actVO) throws Exception;
}
