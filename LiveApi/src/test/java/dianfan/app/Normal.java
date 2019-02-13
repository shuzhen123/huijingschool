package dianfan.app;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dianfan.entities.TokenModel;
import dianfan.util.wx.MessageUtil;
import dianfan.util.wx.TextMessage;

public class Normal {

	@Test
	public void todayStartTime () throws JsonParseException, JsonMappingException, IOException {
		Date date = new Date();  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(date);  
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);  
        if (dayofweek == 1) {  
            dayofweek += 7;  
        }  
        cal.add(Calendar.DATE, 2 - dayofweek);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(sdf.format(cal.getTime()));
		
		
		 Calendar cal1 = Calendar.getInstance();  
        cal1.setTime(cal.getTime());    
        cal1.add(Calendar.DAY_OF_WEEK, 4);   
        System.out.println(sdf.format(cal1.getTime()));
	}
	
	@Test
	public void str () throws JsonParseException, JsonMappingException, IOException {
		String origAddr = "http://vodcvzretw1.vod.126.net/vodcvzretw1/82e16198-055e-4465-875b-bbb5f45f27d6.flv";
		String videoname = origAddr.substring(origAddr.lastIndexOf("/") + 1, origAddr.length());
		
		
		System.out.println(videoname.indexOf(".mp4") == -1);
	}
	@Test
	public void pow () throws JsonParseException, JsonMappingException, IOException {
		String accesstoken = "";
		if (accesstoken == null || accesstoken.length() == 0) {
			return;
		}
		String[] param = accesstoken.split("_");
		if (param.length != 2) {
			return;
		}
		// 使用userId和源token简单拼接成的token，可以增加加密措施
		String userId = param[0];
		String token = param[1];
	}
	
}
