package dianfan.member;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dianfan.exception.SQLExecutorException;

public class Normal {

	@Test
	public void time() {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		int type = 1;
		if (type == 1) {
			// 今日收益
			// 今日00:00:00时间
			System.out.println(new Timestamp(calendar.getTime().getTime()));
		} else if (type == 2) {
			// 近一周收益
			// 一周前的00:00:00时间
			calendar.add(calendar.DATE, -7);// 把日期往后增加一天.整数往后推,负数往前移动
			System.out.println(new Timestamp(calendar.getTime().getTime()));
		} else if (type == 3) {
			// 近一月收益
			// 一月前的00:00:00时间
			calendar.add(Calendar.MONTH, -1);
			System.out.println(new Timestamp(calendar.getTime().getTime()));
		}
	}

	@Test
	public void testExcel() throws IOException {
		// 创建HSSFWorkbook对象(excel的文档对象)
		HSSFWorkbook wb = new HSSFWorkbook();
		// 建立新的sheet对象（excel的表单）
		HSSFSheet sheet = wb.createSheet("成绩表");
		// 在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
		HSSFRow row1 = sheet.createRow(0);
		// 创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
		HSSFCell cell = row1.createCell(0);
		// 设置单元格内容
		cell.setCellValue("学员考试成绩一览表");
		// 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
		// 在sheet里创建第二行
		HSSFRow row2 = sheet.createRow(1);
		// 创建单元格并设置单元格内容
		row2.createCell(0).setCellValue("姓名");
		row2.createCell(1).setCellValue("班级");
		row2.createCell(2).setCellValue("笔试成绩");
		row2.createCell(3).setCellValue("机试成绩");
		// 在sheet里创建第三行
		HSSFRow row3 = sheet.createRow(2);
		row3.createCell(0).setCellValue("李明");
		row3.createCell(1).setCellValue("As178");
		row3.createCell(2).setCellValue(87);
		row3.createCell(3).setCellValue(78);

		// 输出Excel文件
		FileOutputStream output=new FileOutputStream("d:\\workbook.xls"); 
		wb.write(output);
		output.close();
	}
	
	@Test
	public void workspace() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		 YearMonth yearMonth = YearMonth.of(2018, 2);  
        LocalDate localDate = yearMonth.atDay(1);  
        LocalDateTime startOfDay = localDate.atStartOfDay();  
        ZonedDateTime zonedDateTime = startOfDay.atZone(ZoneId.of("Asia/Shanghai"));  
  
        System.out.println(sdf.format(Date.from(zonedDateTime.toInstant())));
        
        LocalDate endOfMonth = yearMonth.atEndOfMonth();  
        LocalDateTime localDateTime = endOfMonth.atTime(23, 59, 59, 999);  
        zonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Shanghai"));  
        System.out.println(sdf.format(Date.from(zonedDateTime.toInstant())));
	}
	@Test
	public void addPopuInfo() throws SQLExecutorException, ParseException {
		int year=2018, month=2;
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		Date date = format1.parse(year + "-" + (month > 10 ? month : "0" + month) + "-01");
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		System.out.println(new Timestamp(calendar.getTime().getTime()));
	}
	@Test
	public void add() throws SQLExecutorException, ParseException {
		Calendar calendar = new GregorianCalendar();
		Timestamp t = new Timestamp(System.currentTimeMillis());
		calendar.setTime(new Date(t.getTime()));
		calendar.set(Calendar.HOUR, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.add(calendar.DATE, 180);// 把日期往后增加一天.整数往后推,负数往前移动
		System.out.println(new Timestamp(calendar.getTime().getTime()));
	}
	
	@Test
	public void regx() {
		String s = "0d1a88f9deb248b993fb7d18bfd22db6//@5be6463f961c432a8c3cda7c5a5377b3//@121f3e6a0ca846cb8c0b6a1f6a9e3da4";
		// 把要匹配的字符串写成正则表达式，然后要提取的字符使用括号括起来
		// 在这里，我们要提取最后一个数字，正则规则就是“一个数字加上大于等于0个非数字再加上结束符”
		Pattern pattern = Pattern.compile("[0-9a-z]{32}");
		Matcher matcher = pattern.matcher(s);
        matcher.find();  
		String group = matcher.group(0);
		System.out.println(group);
		matcher.find();  
		String group1 = matcher.group(0);
		System.out.println(group1);
		matcher.find();  
		String group2 = matcher.group(0);
		System.out.println(group2);
	}
	
	@Test
	public void time1() {
		Calendar calendar = new GregorianCalendar();
		 int y = calendar.get(Calendar.YEAR);
		 int i = calendar.get(Calendar.MONTH);
		 int d = calendar.get(Calendar.DATE);
		 
		 int u = Integer.parseInt(y + "" +i + d + "00");u++;
		 System.out.println(u);
	}
	
	@Test
	public void strToLong() {
		String telno = "15156893499,13812345678,15951567102,15221323231,18657173135,15062621432,18768961002,13615811803,13107705057,15857193139,13212873831,13720373527,18658862293,18280341170,13045916833,18657107689,13395481215,18205037597,18650181527,15557128060,18818447091,18179182387,11888888888,15088750161,18602176825,15251419196,15157029380,18822222222,13526567685,18812312311,13961665706,18861528826,13826090970,13205815175,18833333333,18860423473,18317261725,18812341234,18811111111,18183636007,18879399962,15906856082";
		String[] tel = telno.split(",");
		
		
		System.out.println(tel.toString());
	}
	@Test
	public void substr() throws JsonParseException, JsonMappingException, IOException {
		String json = "{" + 
				"    \"code\": \"200\"," + 
				"    \"msg\": \"OK\"," + 
				"    \"data\": {" + 
				"        \"orderno\": \"2018052510393132562624\"," + 
				"        \"trannum\": null," + 
				"        \"userid\": \"bd40e1e5863845128f7897d675f04d72\"," + 
				"        \"cashcouponid\": null," + 
				"        \"cpname\": null," + 
				"        \"price\": null," + 
				"        \"source\": null," + 
				"        \"money\": 1980," + 
				"        \"paystatus\": 0," + 
				"        \"paytype\": \"2\"," + 
				"        \"paytime\": null," + 
				"        \"endpaytime\": \"2018-05-25 11:09:31\"," + 
				"        \"createtime\": \"2018-05-25 10:39:31\"," + 
				"        \"courselist\": [" + 
				"            {" + 
				"                \"id\": null," + 
				"                \"userid\": null," + 
				"                \"courseid\": \"6871fb4924594a90b7f0173ab7e1f525\"," + 
				"                \"coursename\": \"股市易经系列—群龙无首\"," + 
				"                \"coursepic\": \"http://www.huijingschool.com/upload/img/2018041916504031505443.jpg\"," + 
				"                \"coursemoney\": \"1980.00\"" + 
				"            }" + 
				"        ]," + 
				"        \"second\": 1755," + 
				"        \"handler\": null" + 
				"    }" + 
				"}";
		ObjectMapper mapper = new ObjectMapper();
		
		Map value = mapper.readValue(json, Map.class);
		System.out.println(value.get("trannum"));
	}
}

