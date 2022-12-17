package ji.hs.fire.bsc.svc;

import org.springframework.stereotype.Service;

import ji.hs.fire.bsc.mpr.BscBatchMapper;
import ji.hs.fire.bsc.vo.BscBatchVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 배치 정보 Service
 * @author now2w
 *
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BscBatchService {
	/**
	 * 배치 정보 Mapper
	 */
	private final BscBatchMapper bscBatchMapper;
	
	private final BscNoGenService bscNoGenService;
	
	/**
	 * 배치 정보 입력
	 * @param bscBatchVO
	 * @return
	 * @throws Exception
	 */
	public int insert(BscBatchVO bscBatchVO) throws Exception {
		if(bscBatchVO.getSeq() == 0) {
			BscBatchVO parmBscBatchVO = new BscBatchVO();
			parmBscBatchVO.setOrder(2);
			parmBscBatchVO.setLimit(1);
			
			bscBatchVO.setSeq(bscNoGenService.generate("BC_BATCH_MT.SEQ"));
		}
		
		return bscBatchMapper.insert(bscBatchVO);
	}
}
