package dianfan.test;

import java.io.IOException;
import java.util.Map;

import org.junit.Test;

import com.alipay.api.AlipayApiException;
import com.github.wxpay.sdk.WXPayConfig;

import dianfan.entities.alipay.AlipayQueryBean;
import dianfan.entities.alipay.PublicPayParam;
import dianfan.entities.alipay.WapPayBean;
import dianfan.entities.wx.UnifiedorderBean;
import dianfan.entities.wx.WxOrderQueryBean;
import dianfan.pay.alipay.AlipayCore;
import dianfan.pay.config.wx.WxPayConfigImpl;
import dianfan.pay.wx.WxPayCore;

public class ReadPropTest {
/*
	@Test
	public void field() throws IOException, AlipayApiException  {
		PublicPayParam base = new PublicPayParam();
		base.setApp_id("2017112800218440");
		base.setApp_private_key("MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCbBAPP2m3dgYquQLhaYJbOoLWqJHEl9R2TNUicfTmElO9CmrjkbeaCBNJMo1MzRY3vfXdW6pvSHOYSCu0J4sgTbsvEfsx61A1QJFcHWybiwyIEs4YQdQyu2ZhX+ut0msRGvlnnYZToO4U4vLLQ/uYWMEh2vwzA+zi7MUe9JnsCphNWxPd2h+33fh+S92MOlu6m/k2gojN3IiXFmAMGLlcjcHabXOEnsiTWHZxseu4lHTXymAmRGEpttbec88z30ok4ulM59xUgJm/WdlGm/KgLszRX4rcuIP2AYjt9ssOL+TIFvX8SQuTcBpgoZW7PUjXkFstsWnI6iy4nK5AUIGydAgMBAAECggEBAIHW2g6GCVHoSC1uHjvs/UPzDlaFkZRWe8AqeDhDFyM60DC8gbMrblBnE3KHto+SabE4KPZPKdbSx85DAoouPqEwQ1LBVipnZQNEfPS3X5m+EcdYSymLsOTfcx/2FrOjUlVi6NDKRjxCZnLAcxJk/nmFVZzIpzkY80qvm0QOcCglfUBUn2lI0Fz9hl/ap8ZubaLx0GGm2LsnFH3EMe1qRCJEB3QXeplHTBQchiFpBwREsfSyb682Kb0OS3lBW3aMSYQ19bboNlkOmgLICCHln9cYKkWbgokiguKwdLGrFY3Y1QH8wI1PYy3Y0IwQQekFEQsyQCr70K9qM+L/xMXdV2ECgYEA3Mid3HFZssDdVRyFbEM98hDMwYQddNOIlugTDacwfWgqO7gFiHbA5iZvBalOY63uzO5uhtnN1m+YLz9pe8vtufVPcmUvImKwFOINIWW1MGMq4hqemMfMiRC1lwDaMkkymS04ntpJQmh6PNuVfz58/wRnhfR931qxG8+pPyY9nIUCgYEAs73bhNHLuSyJWr+j+UeV3+KSeqYNX3f2lu8gQPTFqlP6peh/3Yt4fif8jd1RR2RbCQvm5MnyLiwus59favKdy+FBZBulI0S4sxmZL9XqG5AAeRacQXxcUU3juQVhDBPacjL/wBc32YEYmpJpumKd1DRRkXhYwhLmfBtqTxMoNzkCgYEA0fTKAl+csFuuX4ju7MSJPRBy1zh/FqEvDGsH63zppOiQ+ReH030y9erou1aFIE/Afwvi+cRYdarL62p/MmX83VGGSsAkcautzT4mxHLHOoujtT8hQSMHw/Sq6z25QgbP4lDZvl7ROiDG1ebvpQDUt2MF26JkrSm+sTbquQ3xm00CgYAV6dG3kbRasRlV3QNqwtV85Cyjk1cSCpgfsqul/7GRoIdwbrYYFobsKofZ69+a6ptGsRLHVe+WvYA8GfQSH94pGx9TyyMI97nk6/wHHvdZwOat3JnUSgsfRnW6+vLn/Aun3JvdmXcPp3OOdMc7GAbR9kbzi06W6qL7AoSle1pt0QKBgQCXaTdVmsO43Bsi1t4FLdF0u7MxA08Y+kLOKZRH3F/QwAB++1UOuyS52plh8C/bc+773HTifX5Ne1S7gOsF3/xHHPxWD0zwE4srUWt8UVbR/s2+/26jVVvgyBbp1OGNlZRJhnQA1IFp+nocweZpWpDWwX8CpVxL/YEsiOW3yM4uxw==");
		base.setAlipay_public_key("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiOrgpv0QIA/B9Vw7U2oZLTBKp/6nPGtA8iEPRniXrEpXVhvKsmtDxuXPUwsfkb0S0+Ts6PemrGMRHBlWgxa2dS/ZfIbTRP9VBX36emd1vVmYWVYhucejbXRD6lQipxlYbFR9sQoX7IIteOC+yhrNxTDsInNPurBHAsRHMivTyJGs1YSdPoTuptmjeBbkV2UUdyIqtXDv/dfhbNzYAAcbIccV2mxlSd55DHbY0++X8TyWEaVmEPzMJv8/aQv/RPhAumP0WAU5gX6EWEZR9bDBd6/2cOjXh8S9i8ogD9A761os1kkXAgXBR6GDteh/3M9bjbdQXolc+GAruaXgCdfCUQIDAQAB");
//		base.setReturn_url("http://www.baidu.com");
		base.setNotify_url("http://test.huijingschool.com/LiveApi/notify/goods/alipay");
		
		WebPayBean biz = new WebPayBean();
		biz.setSubject("商品的标题587");
//		biz.setOut_trade_no("2018052710567845124579");
//		biz.setOut_trade_no("2018052710567845124580");
//		biz.setOut_trade_no("2018052710567845124581");
//		biz.setOut_trade_no("2018052710567845124582");
//		biz.setOut_trade_no("2018052710567845124583");
//		biz.setOut_trade_no("2018052710567845124584");
//		biz.setOut_trade_no("2018052710567845124585");
//		biz.setOut_trade_no("2018052710567845124586");
//		biz.setOut_trade_no("2018052710567845124587");
		biz.setOut_trade_no("2018052710567845124588");
		biz.setTotal_amount("0.01");
		
//		String string = new AlipayCore(base).alipayWebPayment(biz);
		String string = new AlipayCore(base).alipayAppTrade(biz);
		
		System.out.println(string);
	}
	
	@Test
	public void wxpay() throws Exception  {
		WXPayConfig conf = new WxPayConfigImpl("wx8c4516bf5d6c0687", "1501390911", "f53fce7d736f438f9cc68a3e345247f5");
		UnifiedorderBean order = new UnifiedorderBean();
		order.setBody("商品描述");
		order.setOut_trade_no("2018052916457896541263");
		order.setTotal_fee(2345);
		order.setSpbill_create_ip("32.152.233.1");
		order.setNotify_url("http://www.domain.com/notify");
		order.setTrade_type("APP");
		order.setOpenid("oRxPxwvOMRfJB-HBHq_oA9JPOIM0");
		Map<String, String> map = new WxPayCore(conf).unifiedOrder(order);
		System.out.println(map);
	}
	
	@Test
	public void query() throws Exception  {
		//2018052916457896541263
		WXPayConfig conf = new WxPayConfigImpl("wx8c4516bf5d6c0687", "1501390911", "f53fce7d736f438f9cc68a3e345247f5");
		WxOrderQueryBean query = new WxOrderQueryBean();
		query.setOut_trade_no("2018052916457896541263");
		Map<String, String> map = new WxPayCore(conf).orderQuery(query);
		System.out.println(map);
	}
	
	@Test
	public void queryAlipay() throws Exception  {
		PublicPayParam base = new PublicPayParam();
		base.setApp_id("2017112800218440");
		base.setApp_private_key("MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCbBAPP2m3dgYquQLhaYJbOoLWqJHEl9R2TNUicfTmElO9CmrjkbeaCBNJMo1MzRY3vfXdW6pvSHOYSCu0J4sgTbsvEfsx61A1QJFcHWybiwyIEs4YQdQyu2ZhX+ut0msRGvlnnYZToO4U4vLLQ/uYWMEh2vwzA+zi7MUe9JnsCphNWxPd2h+33fh+S92MOlu6m/k2gojN3IiXFmAMGLlcjcHabXOEnsiTWHZxseu4lHTXymAmRGEpttbec88z30ok4ulM59xUgJm/WdlGm/KgLszRX4rcuIP2AYjt9ssOL+TIFvX8SQuTcBpgoZW7PUjXkFstsWnI6iy4nK5AUIGydAgMBAAECggEBAIHW2g6GCVHoSC1uHjvs/UPzDlaFkZRWe8AqeDhDFyM60DC8gbMrblBnE3KHto+SabE4KPZPKdbSx85DAoouPqEwQ1LBVipnZQNEfPS3X5m+EcdYSymLsOTfcx/2FrOjUlVi6NDKRjxCZnLAcxJk/nmFVZzIpzkY80qvm0QOcCglfUBUn2lI0Fz9hl/ap8ZubaLx0GGm2LsnFH3EMe1qRCJEB3QXeplHTBQchiFpBwREsfSyb682Kb0OS3lBW3aMSYQ19bboNlkOmgLICCHln9cYKkWbgokiguKwdLGrFY3Y1QH8wI1PYy3Y0IwQQekFEQsyQCr70K9qM+L/xMXdV2ECgYEA3Mid3HFZssDdVRyFbEM98hDMwYQddNOIlugTDacwfWgqO7gFiHbA5iZvBalOY63uzO5uhtnN1m+YLz9pe8vtufVPcmUvImKwFOINIWW1MGMq4hqemMfMiRC1lwDaMkkymS04ntpJQmh6PNuVfz58/wRnhfR931qxG8+pPyY9nIUCgYEAs73bhNHLuSyJWr+j+UeV3+KSeqYNX3f2lu8gQPTFqlP6peh/3Yt4fif8jd1RR2RbCQvm5MnyLiwus59favKdy+FBZBulI0S4sxmZL9XqG5AAeRacQXxcUU3juQVhDBPacjL/wBc32YEYmpJpumKd1DRRkXhYwhLmfBtqTxMoNzkCgYEA0fTKAl+csFuuX4ju7MSJPRBy1zh/FqEvDGsH63zppOiQ+ReH030y9erou1aFIE/Afwvi+cRYdarL62p/MmX83VGGSsAkcautzT4mxHLHOoujtT8hQSMHw/Sq6z25QgbP4lDZvl7ROiDG1ebvpQDUt2MF26JkrSm+sTbquQ3xm00CgYAV6dG3kbRasRlV3QNqwtV85Cyjk1cSCpgfsqul/7GRoIdwbrYYFobsKofZ69+a6ptGsRLHVe+WvYA8GfQSH94pGx9TyyMI97nk6/wHHvdZwOat3JnUSgsfRnW6+vLn/Aun3JvdmXcPp3OOdMc7GAbR9kbzi06W6qL7AoSle1pt0QKBgQCXaTdVmsO43Bsi1t4FLdF0u7MxA08Y+kLOKZRH3F/QwAB++1UOuyS52plh8C/bc+773HTifX5Ne1S7gOsF3/xHHPxWD0zwE4srUWt8UVbR/s2+/26jVVvgyBbp1OGNlZRJhnQA1IFp+nocweZpWpDWwX8CpVxL/YEsiOW3yM4uxw==");
		base.setAlipay_public_key("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiOrgpv0QIA/B9Vw7U2oZLTBKp/6nPGtA8iEPRniXrEpXVhvKsmtDxuXPUwsfkb0S0+Ts6PemrGMRHBlWgxa2dS/ZfIbTRP9VBX36emd1vVmYWVYhucejbXRD6lQipxlYbFR9sQoX7IIteOC+yhrNxTDsInNPurBHAsRHMivTyJGs1YSdPoTuptmjeBbkV2UUdyIqtXDv/dfhbNzYAAcbIccV2mxlSd55DHbY0++X8TyWEaVmEPzMJv8/aQv/RPhAumP0WAU5gX6EWEZR9bDBd6/2cOjXh8S9i8ogD9A761os1kkXAgXBR6GDteh/3M9bjbdQXolc+GAruaXgCdfCUQIDAQAB");
//		base.setReturn_url("http://www.baidu.com");
		base.setNotify_url("http://test.huijingschool.com/LiveApi/notify/goods/alipay");
		
		AlipayQueryBean aqb = new AlipayQueryBean();
		aqb.setOut_trade_no("2018052710567845124579");
		
		String string = new AlipayCore(base).tradeQuery(aqb);
		System.out.println(string);
	}
	*/
	@Test
	public void queryAlipay() throws Exception  {
		System.out.println(System.currentTimeMillis()/1000); 
	}
}
