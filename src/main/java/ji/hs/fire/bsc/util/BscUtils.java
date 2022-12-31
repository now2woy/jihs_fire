package ji.hs.fire.bsc.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BscUtils {
	/**
	 * 기본 반올림 방법
	 */
	private static final RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.HALF_EVEN;
	/**
	 * 현재 시간이 배치 실행시간과 같은지 확인하여 리턴
	 * @param hh
	 * @return
	 */
	public static boolean isRunTime(String hh) {
		return hh.equals(new SimpleDateFormat("HH").format(new Date()));
	}
	
	/**
	 * 현재 시간을 리턴
	 * @return
	 */
	public static String thisDateTime() {
		return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
	}
	
	/**
	 * json을 Map형식으로 리턴
	 * @param json
	 * @return
	 */
	public static Map<String, Object> jsonParse(String json){
		Map<String, Object> map = null;
		try {
			final ObjectMapper mapper = new ObjectMapper();
			map = mapper.readValue(json, mapper.getTypeFactory().constructMapLikeType(Map.class, String.class, Object.class));
		}catch(Exception e) {
			log.error("", e);
		}
		
		return map;
	}
	
	/**
	 * 압축을 푼다.
	 * @param is
	 * @param destDir
	 * @param charsetName
	 * @throws IOException
	 */
	public static boolean unzip(InputStream is, File destDir, String charsetName) throws IOException {
		ZipArchiveInputStream zis = null;
		ZipArchiveEntry entry = null;
		String name = null;
		File target = null;
		FileOutputStream fos = null;
		boolean isUnzip = true;
		
		try {
			zis = new ZipArchiveInputStream(is, charsetName, false);
			
			while ((entry = zis.getNextZipEntry()) != null){
				name = entry.getName();
				
				target = new File (destDir, name);
				
				if(entry.isDirectory()){
					target.mkdirs();
				} else {
					target.createNewFile();
					
					try {
						fos = new FileOutputStream(target);
						IOUtils.copy(zis, fos);
					}catch(Exception e) {
						isUnzip = false;
						log.error("", e);
					}finally {
						IOUtils.closeQuietly(fos);
					}
				}
			}
		}catch(Exception e) {
			log.error("", e);
			isUnzip = false;
		}finally {
			IOUtils.closeQuietly(zis);
			IOUtils.closeQuietly(fos);
		}
		
		return isUnzip;
	}
	
	/**
	 * a + b
	 * CASE : a가 null일 경우 0으로 대입
	 * CASE : b가 null일 경우 0으로 대입
	 * CASE : a와 b가 모두 null일 경우 null 리턴
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static BigDecimal add(BigDecimal a, BigDecimal b) {
		// a와 b 모두 null 일 경우
		if(a == null && b == null){
			return null;
		}
		
		// a가 null일 경우
		if(a == null) {
			a = BigDecimal.ZERO;
		}
		
		// b가 null일 경우
		if(b == null){
			b = BigDecimal.ZERO;
		}
		
		return a.add(b);
	}
	
	/**
	 * a * b
	 * CASE : a 가 null일 경우 null
	 * CASE : b 가 null일 경우 null
	 * 
	 * @param a
	 * @param b
	 * @param scale
	 * @return
	 */
	public static BigDecimal multiply(BigDecimal a, BigDecimal b, int scale) {
		return multiply(a, b, scale, DEFAULT_ROUNDING_MODE);
	}
	
	/**
	 * a * b
	 * CASE : a 가 null일 경우 null
	 * CASE : b 가 null일 경우 null
	 * 
	 * @param a
	 * @param b
	 * @param scale
	 * @param roundingMode
	 * @return
	 */
	public static BigDecimal multiply(BigDecimal a, BigDecimal b, int scale, RoundingMode roundingMode) {
		if(a == null || b == null) {
			return null;
		}
		
		return a.multiply(b).setScale(scale, roundingMode);
	}
	
	/**
	 * * a / b
	 * CASE : a가 null일 경우 null
	 * CASE : a가 0일 경우 null
	 * CASE : b가 null일 경우 null
	 * CASE : b가 0일 경우 null
	 * 
	 * @param a
	 * @param b
	 * @param scale
	 * @return
	 */
	public static BigDecimal divide(BigDecimal a, BigDecimal b, int scale) {
		return divide(a, b, scale, DEFAULT_ROUNDING_MODE);
	}
	
	/**
	 * a / b
	 * CASE : a가 null일 경우 null
	 * CASE : a가 0일 경우 null
	 * CASE : b가 null일 경우 null
	 * CASE : b가 0일 경우 null
	 * 
	 * @param a
	 * @param b
	 * @param scale
	 * @param roundingMode
	 * @return
	 */
	public static BigDecimal divide(BigDecimal a, BigDecimal b, int scale, RoundingMode roundingMode) {
		// null 이거나 0일 경우 나눌 수 없다.
		if(a == null || a.compareTo(BigDecimal.ZERO) == 0) {
			return null;
		}
		
		// null 이거나 0일 경우 나눌 수 없다.
		if(b == null || b.compareTo(BigDecimal.ZERO) == 0) {
			return null;
		}
		
		return a.divide(b, scale, roundingMode);
	}
}
