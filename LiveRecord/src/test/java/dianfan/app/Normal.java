package dianfan.app;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import dianfan.util.GenRandomNumUtil;

public class Normal {

	@Test
	public void todayStrtTime () {
		try {
			String cmd2 = "cmd /c start D:\\ffmpeg.exe -i D:\\ios.m4a D:\\abc.mp3";
//			Runtime.getRuntime().exec(cmd2).waitFor();
			Runtime.getRuntime().exec(cmd2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void getDateNo () {
		String dateNo = GenRandomNumUtil.getDateNo();
		System.out.println(dateNo);
	}
}
