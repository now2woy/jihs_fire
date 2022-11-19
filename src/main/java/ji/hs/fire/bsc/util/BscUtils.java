package ji.hs.fire.bsc.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
}
