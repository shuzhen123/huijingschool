package dianfan.controller.notify;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wordnik.swagger.annotations.ApiOperation;

import dianfan.annotations.LogOp;
import dianfan.annotations.UnCheckedFilter;
import dianfan.constant.ConstantIF;
import dianfan.entities.ResultBean;
import dianfan.exception.SQLExecutorException;
import dianfan.service.VCloudNotifyService;

/**
 * @ClassName VCloudLiveNotify
 * @Description 网易云直播视频录制回调
 * @author cjy
 * @date 2018年4月11日 上午9:29:58
 */
@Scope("request")
@Controller
@RequestMapping(value = "/notify/vcloud/live")
public class VCloudLiveNotify {

	@Autowired
	private VCloudNotifyService vCloudNotifyService;
	
	public static Logger log = Logger.getLogger(CourseNotify.class);

	/**
	 * @Title: liveCourseNotify
	 * @Description: 直播视频录制回调
	 * @param request
	 * @throws IOException 
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年4月28日 下午3:54:46
	 */
	@LogOp(method = "liveCourseNotify", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "直播视频录制回调")
	@ApiOperation(value = "直播视频录制回调", httpMethod = "", notes = "直播视频录制回调", response = String.class)
	@RequestMapping(value = "livecoursenotify")
	@UnCheckedFilter
	public void liveCourseNotify(HttpServletRequest request) throws IOException, SQLExecutorException {
		/*
		 * "vid":"1519101544",
			"orig_video_key":"2c9781cf45634edcbc384395fe3db1c7_1524905223336_1524905242314_333611259-00000.mp4",
			"video_name":"测试讲师_20180428-164703_20180428-164722",
			"uid":"85097",
			"nId":"nId2331843971",
			"beginTime":"1524905223336",
			"endTime":"1524905242314",
			"origUrl":"http://vodcvzretw1.vod.126.net/vodcvzretw1/2c9781cf45634edcbc384395fe3db1c7_1524905223336_1524905242314_333611259-00000.mp4",
			"cid":"2c9781cf45634edcbc384395fe3db1c7"  频道id
		 */
		
		BufferedReader br = null;  
	        br = new BufferedReader(new InputStreamReader(request.getInputStream()));  
	        String line = null;  
	        StringBuilder sb = new StringBuilder();  
	        while ((line = br.readLine()) != null) {  
	            sb.append(line);  
	        }  
	        
	        ObjectMapper mapper = new ObjectMapper();
			Map<String, String> ret = mapper.readValue(sb.toString(), Map.class);
			//直播视频回调更新
			vCloudNotifyService.updateLiveCourse(ret.get("cid"), ret.get("origUrl"));
//	    try {  
//	    	br = new BufferedReader(new InputStreamReader(request.getInputStream()));  
//	    	String line = null;  
//	    	StringBuilder sb = new StringBuilder();  
//	    	while ((line = br.readLine()) != null) {  
//	    		sb.append(line);  
//	    	}  
//	    	
//	    	ObjectMapper mapper = new ObjectMapper();
//	    	Map<String, String> ret = mapper.readValue(sb.toString(), Map.class);
//	    	//直播视频回调更新
//	    	vCloudNotifyService.updateLiveCourse(ret.get("cid"), ret.get("origUrl"));
//	    } catch (IOException | SQLExecutorException e) {  
//	    } finally {
//	    	try {
//	    		if(br != null) {
//	    			br.close();
//	    		}
//	    	} catch (IOException e) {
//	    		br = null;
//	    	}
//	    }
	}
	
	/**
	 * @Title: liveEventNotify
	 * @Description: 网易云消息抄送
	 * @param request
	 * @throws:
	 * @time: 2018年5月4日 上午10:34:42
	 */
	@LogOp(method = "liveEventNotify", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "网易云消息抄送")
	@ApiOperation(value = "网易云消息抄送", httpMethod = "", notes = "网易云消息抄送", response = String.class)
	@RequestMapping(value = "liveeventnotify")
	@UnCheckedFilter
	public @ResponseBody ResultBean liveEventNotify(HttpServletRequest request) {
		BufferedReader br = null;  
		try {  
			br = new BufferedReader(new InputStreamReader(request.getInputStream()));  
			String line = null;  
			StringBuilder sb = new StringBuilder();  
			while ((line = br.readLine()) != null) {  
				sb.append(line);  
			}  
			
			ObjectMapper mapper = new ObjectMapper();
			Map<String, String> ret = mapper.readValue(sb.toString(), Map.class);
			log.error("消息抄送：" + ret);
		} catch (IOException e) {  
		} finally {
			try {
				if(br != null) {
					br.close();
				}
			} catch (IOException e) {
				br = null;
			}
		}
		return new ResultBean();
	}
	
	/**
	 * @Title: videoCallBack
	 * @Description: 网易云点播回调
	 * @param request
	 * @return
	 * @throws:
	 * @time: 2018年5月16日 下午4:56:20
	 */
	@LogOp(method = "videoCallBack", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "网易云点播回调")
	@ApiOperation(value = "网易云点播回调", httpMethod = "", notes = "网易云点播回调", response = String.class)
	@RequestMapping(value = "videocallback")
	@UnCheckedFilter
	public @ResponseBody ResultBean videoCallBack(HttpServletRequest request) {
		BufferedReader br = null;  
		try {  
			br = new BufferedReader(new InputStreamReader(request.getInputStream()));  
			String line = null;  
			StringBuilder sb = new StringBuilder();  
			while ((line = br.readLine()) != null) {  
				sb.append(line);  
			}  
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> ret = mapper.readValue(sb.toString(), Map.class);
			
			if(ret == null) return new ResultBean();
			
			String notify_type = ret.get("type").toString().toLowerCase();
			
			if("upload".equals(notify_type)) {
				log.error("视频上传回调数据：" + ret);
				//视频上传回调
				
				/*
				 * type=upload, 
					vid=1576564818, 
					name=test.mp4, 
					origAddr=http://vodcvzretw1.vod.126.net/vodcvzretw1/844a07e5-9018-4641-8a14-b912af83c073.mp4
				 */
				vCloudNotifyService.updateFromVideoUpload(ret);
			}else if("transcode".equals(notify_type)) {
				log.error("视频转码回调数据：" + ret);
				//视频转码回调
				
				/*
				 * vid=1572912353, 
					type=transcode, 
					shdMp4Addr=http://vodcvzretw1.vod.126.net/vodcvzretw1/JOtqwjtJ_1572912353_shd.mp4, 
					shdFlvAddr=http://vodcvzretw1.vod.126.net/vodcvzretw1/ZTxZNoNB_1572912353_shd.flv, 
					shdHlsAddr=http://vodcvzretw1.vod.126.net/vodcvzretw1/uR3grrMk_1572912353_shd.m3u8, 
					name=娉㈡氮鐞嗚.flv
				 */
				vCloudNotifyService.updateFromVideoTranscode(ret);
			}
		} catch (IOException | SQLExecutorException e) {
			
		} finally {
			try {
				if(br != null) {
					br.close();
				}
			} catch (IOException e) {
				br = null;
			}
		}
		return new ResultBean();
	}

}
