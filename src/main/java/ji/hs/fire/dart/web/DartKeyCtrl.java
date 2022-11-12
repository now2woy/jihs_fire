package ji.hs.fire.dart.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ji.hs.fire.dart.dao.DartKeyVO;
import ji.hs.fire.dart.svc.DartKeyService;

@RestController
@RequestMapping("/api/dartKey")
public class DartKeyCtrl {
	@Autowired
	private DartKeyService dartKeyService;
	
	/**
	 * Dart Key 전체 조회
	 * @return
	 */
	@GetMapping("")
	public ResponseEntity<List<DartKeyVO>> selectAll() throws Exception {
		
		return ResponseEntity.status(HttpStatus.OK).body(dartKeyService.selectAll());
	}
	
	/**
	 * Dart Key 단건 조회
	 * @return
	 */
	@GetMapping("/{ordNum}")
	public ResponseEntity<DartKeyVO> selectOne(@PathVariable("ordNum") int ordNum) throws Exception {
		
		return ResponseEntity.status(HttpStatus.OK).body(dartKeyService.selectOne(ordNum));
	}
	
	/**
	 * Dart Key 입력
	 * @param dartKeyVO
	 * @return
	 */
	@PostMapping("")
	public ResponseEntity<DartKeyVO> insert(@RequestBody DartKeyVO dartKeyVO) throws Exception {
		
		return ResponseEntity.status(HttpStatus.CREATED).body(null);
	}
	
	/**
	 * Dart Key 수정
	 * @param dartKeyVO
	 * @return
	 */
	@PutMapping("/{ordNum}")
	public ResponseEntity<DartKeyVO> update(@PathVariable("ordNum") int ordNum, @RequestBody DartKeyVO dartKeyVO) throws Exception {
		
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
	
	/**
	 * Dart Key 삭제
	 * @param dartKeyVO
	 * @return
	 */
	@DeleteMapping("/{ordNum}")
	public ResponseEntity<DartKeyVO> delete(@PathVariable("ordNum") int ordNum) throws Exception {
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}
}
