package dianfan.mp.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

public class Normal {

	@Test
	public void uuid() {  
		System.out.println(UUID.randomUUID().toString().replace("-", ""));
		System.out.println(UUID.randomUUID().toString().replace("-", ""));
		System.out.println(UUID.randomUUID().toString().replace("-", ""));
		System.out.println(UUID.randomUUID().toString().replace("-", ""));
		System.out.println(UUID.randomUUID().toString().replace("-", ""));
		System.out.println(UUID.randomUUID().toString().replace("-", ""));
	}
	
	@Test
	public void time() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Calendar cal = Calendar.getInstance();
		String format = sdf.format(cal.getTime());
		System.out.println(format);
		
		cal.add(Calendar.HOUR_OF_DAY, +1);
		String format1 = sdf.format(cal.getTime());
		System.out.println(format1);
	}
    
	@Test
	public void sign() {
		List<String> list = new ArrayList<>();
		
		list.add("abc,def");
		
		System.out.println(list.contains("abc"));
	}
	
	
	
}
