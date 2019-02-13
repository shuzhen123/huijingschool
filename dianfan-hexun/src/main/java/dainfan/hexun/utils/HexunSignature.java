package dainfan.hexun.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HexunSignature {
	private HexunSignature() {}
	/**
	 * @Title: createSignContent
	 * @Description: 获取待签名字符串
	 * @param data
	 * @param hexunCnf
	 * @return
	 * @throws:
	 * @time: 2017年12月27日 下午3:55:39
	 */
	public static String createSignContent(Map<String, String> singMap){
		singMap.remove("sign");
		singMap.remove("sign_type");
		
		//去空
		Map<String, String> map = removeNullAndSpace(singMap);
		
		String[] strs = new String[map.size()];
		int i=0;
		//key-val连接
		for(Map.Entry<String, String> m : map.entrySet()){
			strs[i] = m.getKey() + "=" + m.getValue();
			i++;
		}
		//按key排序
		Arrays.sort(strs);
		
		StringBuilder signcontent = new StringBuilder();
		//数组拼接
		for(String param : strs){
			signcontent.append(param).append("&");
		}
		signcontent.deleteCharAt(signcontent.length()-1);
		//待签名字符串
		return signcontent.toString();
	}
	
	/**
	 * @Title: getContent
	 * @Description: 集合去空、去null
	 * @param singMap
	 * @return
	 * @throws:
	 * @time: 2017年12月28日 上午10:44:28
	 */
	public static Map<String, String> removeNullAndSpace(Map<String, String> singMap){
		Map<String, String> new_data = new HashMap<>();
		for(Map.Entry<String, String> entry : singMap.entrySet()) {
			if(entry.getValue() == null || !"".equals(entry.getValue().trim())) {
				new_data.put(entry.getKey(), entry.getValue());
			}
		}
		return new_data;
	}
	
}
