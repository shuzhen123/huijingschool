package dianfan.member;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dianfan.constant.ConstantIF;
import dianfan.vcloud.core.VCloudManage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml", "classpath:spring-redis.xml" })
public class VCloudTest {

	// 开通账号密码
	@Test
	public void createId() throws ClientProtocolException, IOException {
		//开通网易云id
		Map<String, String> im_crt_param = new HashMap<>();
		im_crt_param.put("accid", "036b8e60e0f44d11af0b493da0f9eaed"); //网易云通信ID
		im_crt_param.put("name", "吴彦祖"); //网易云通信ID昵称
		im_crt_param.put("icon", ConstantIF.PROJECT + "/upload/img/default.jpg"); //网易云通信ID头像URL
		im_crt_param.put("token", "6bdd5b9464a211e88c1400163e0028c4"); //网易云通信token
		Map<String, Object> im = VCloudManage.im("vcloud.mi.createid", im_crt_param);
		//{ret={presetId=104254630}, requestId=vodb4e0239b-97d6-4932-a918-c7c0304aee6e, code=200}
		System.out.println(im);
	}
	
	// 创建视频转码模板
	@Test
	public void videoChangCode() throws ClientProtocolException, IOException {
		Map<String, Object> param = new HashMap<>();
		param.put("presetName","全高清模板"); //视频转码模板的名称
		param.put("sdMp4","0"); //流畅Mp4格式（1表示选择，0表示不选择）
		param.put("hdMp4","0"); //标清Mp4格式（1表示选择，0表示不选择）
		param.put("shdMp4","1"); //高清Mp4格式（1表示选择，0表示不选择）
		param.put("sdFlv","0"); //流畅Flv格式（1表示选择，0表示不选择）
		param.put("hdFlv","0"); //标清Flv格式（1表示选择，0表示不选择）
		param.put("shdFlv","1"); //高清Flv格式（1表示选择，0表示不选择）
		param.put("sdHls","0"); //流畅Hls格式（1表示选择，0表示不选择）
		param.put("hdHls","0"); //标清Hls格式（1表示选择，0表示不选择）
		param.put("shdHls","1"); //高清Hls格式（1表示选择，0表示不选择）
		Map<String, Object> channel = VCloudManage.channel("vcloud.video.model", param);
		System.out.println(channel);
		//{ret={presetId=104254630}, requestId=vodb4e0239b-97d6-4932-a918-c7c0304aee6e, code=200}
	}
	
	// 设置转码回调地址
	@Test
	public void videoTcallback() throws ClientProtocolException, IOException {
		Map<String, Object> param = new HashMap<>();
		param.put("callbackUrl","http://api.huijingschool.com/LiveApi/notify/vcloud/live/videocallback"); //视频转码模板的名称
		Map<String, Object> channel = VCloudManage.channel("vcloud.video.tcallback", param);
		System.out.println(channel);
		//{ret={}, requestId=vod2f2fc602-3206-4494-b444-162b63182853, code=200}
	}
	
	// 视频文件转码
	@Test
	public void videoTranscode() throws ClientProtocolException, IOException {
		Map<String, Object> param = new HashMap<>();
		List<Integer> ids = new ArrayList<>();
		ids.add(1580409919);

		param.put("vids", ids); //多个视频Id组成的列表
		param.put("presetId","104254630"); //转码模板Id
		Map<String, Object> channel = VCloudManage.channel("vcloud.video.transcode", param);
		System.out.println(channel);
		//{ret={passCount=0, failCount=0, successCount=1}, requestId=vod4d1cca06-58e8-49bb-b39d-571511cf8ad9, code=200}
	}
	
	// 视频上传回调地址设置
	@Test
	public void videoUploadCallBack() throws ClientProtocolException, IOException {
		Map<String, Object> param = new HashMap<>();
		param.put("callbackUrl", "http://api.huijingschool.com/LiveApi/notify/vcloud/live/videocallback"); //上传成功后回调客户端的URL地址
		Map<String, Object> channel = VCloudManage.channel("vcloud.upload.callback", param);
		System.out.println(channel);
		//{ret={}, requestId=vod5240e5ba-d1d7-4e8e-920e-526d5442d6e5, code=200}
	}

}
