package dianfan.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.constant.ConstantIF;
import dianfan.controller.notify.CourseNotify;
import dianfan.dao.mapper.app.VCloudNotifyMapper;
import dianfan.entities.Course;
import dianfan.entities.Video;
import dianfan.exception.SQLExecutorException;
import dianfan.vcloud.core.VCloudManage;

/**
 * @ClassName VCloudNotifyService
 * @Description 视频直播回调服务
 * @author cjy
 * @date 2018年5月3日 上午9:38:51
 */
@Service
public class VCloudNotifyService {
	/**
	 * dao
	 */
	@Autowired
	private VCloudNotifyMapper vCloudNotifyMapper;
	
	public static Logger log = Logger.getLogger(VCloudNotifyService.class);

	/**
	 * @Title: updateLiveCourse
	 * @Description: 直播视频回调更新
	 * @param cid 频道id
	 * @param origUrl 视频录制地址
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年5月3日 上午9:45:42
	 */
	@Transactional
	public void updateLiveCourse(String cid, String origUrl) throws SQLExecutorException {
		//1、根据频道id获取讲师id
		String teacherid = vCloudNotifyMapper.getTeacherIdByCid(cid);
		if(teacherid != null) {
			//2、根据讲师id获取讲师正在直播的课程
			Course course = vCloudNotifyMapper.findLiveCourseByTeacherid(teacherid);
			if(course != null) {
				//3、查看是否为录播（此处暂时默认为录播）
				//4、更改直播课程状态信息
				vCloudNotifyMapper.updateLiveCourseInfo(course.getCourseid());
				//5、插入直播课程视频
				Video v = new Video();
				v.setCourseid(course.getCourseid());
				v.setVideoppicurl(course.getCoursepic());
				v.setVideourl(origUrl);
				v.setVideoname(course.getCoursename());
				v.setVideointroduce(course.getCoursedes());
				v.setVideotype(course.getCoursekind());
				v.setUserid(teacherid);
				vCloudNotifyMapper.insertLiveCourseVideo(v);
			}
		}
	}

	/**
	 * @Title: updateFromVideoUpload
	 * @Description: 视频上传回调
	 * @param ret
	 * @throws SQLExecutorException 
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws:
	 * @time: 2018年5月16日 下午5:01:22
	 */
	@Transactional
	public void updateFromVideoUpload(Map<String, Object> ret) throws SQLExecutorException, ClientProtocolException, IOException {
		/*
		 * type=upload, 
			vid=1576564818, 
			name=test.mp4, 
			origAddr=http://vodcvzretw1.vod.126.net/vodcvzretw1/844a07e5-9018-4641-8a14-b912af83c073.mp4
		 */
		//网易云视频连接
		String origAddr = ret.get("origAddr").toString();
		//网易云视频名称
		String videoname = origAddr.substring(origAddr.lastIndexOf("/") + 1, origAddr.length());
		//根据视频名称查找视频信息
		Video videoInfo = vCloudNotifyMapper.getVideoInfoByVideoName(videoname);
		if(videoInfo != null) {
			//有需要处理的视频信息
			//更新视频数据
			String vid = ret.get("vid").toString();
			videoInfo.setVid(vid);
			videoInfo.setVideourl(origAddr);
			
			if(videoname.indexOf(".mp4") == -1) {
				//需要转码
				videoInfo.setEntkbn(1);
				Map<String, Object> param = new HashMap<>();
				List<String> ids = new ArrayList<>();
				ids.add(vid);
				param.put("vids", ids); //多个视频Id组成的列表
				param.put("presetId", ConstantIF.TRANSCODE_TPL_ID); //转码模板Id
				VCloudManage.channel("vcloud.video.transcode", param);
			}else {
				//无须转码
				videoInfo.setEntkbn(0);
			}
			vCloudNotifyMapper.updateCourseVideoInfo(videoInfo);
		}
	}

	/**
	 * @Title: updateFromVideoTranscode
	 * @Description: 视频转码回调
	 * @param ret
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年5月16日 下午5:01:37
	 */
	public void updateFromVideoTranscode(Map<String, Object> ret) throws SQLExecutorException {
		//根据视频vid查找视频信息
		Video videoInfo = vCloudNotifyMapper.getVideoInfoByVideoVid(ret.get("vid").toString());
		if(videoInfo != null) {
			videoInfo.setVideourl(ret.get("shdMp4Addr").toString());
			videoInfo.setEntkbn(0);
			vCloudNotifyMapper.updateCourseVideoInfo(videoInfo);
		}
	}

}
