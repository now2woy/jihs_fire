package ji.hs.fire.bsc.util;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BscUtils {
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
}
