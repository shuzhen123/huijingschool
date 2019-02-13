package dianfan.service.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dianfan.constant.ConstantIF;
import dianfan.constant.ResultMsg;
import dianfan.dao.mapper.app.AppLiveCourseMapper;
import dianfan.dao.mapper.app.AppMyInfoMapper;
import dianfan.dao.mapper.app.AppUserMapper;
import dianfan.entities.ChannelBean;
import dianfan.entities.CourseList;
import dianfan.entities.LiveCourse;
import dianfan.entities.ResultBean;
import dianfan.exception.SQLExecutorException;
import dianfan.vcloud.config.VcoludConf;
import dianfan.vcloud.core.VCloudManage;

/**
 * @ClassName AppLiveCourseService
 * @Description 直播服务
 * @author cjy
 * @date 2018年5月4日 下午2:24:54
 */
@Service
public class AppLiveCourseService {
	@Autowired
	private AppLiveCourseMapper appLiveCourseMapper;
	@Autowired
	private AppUserMapper appUserMapper;
	@Autowired
	private AppMyInfoMapper appMyInfoMapper;

	/**
	 * @Title: getCourseList
	 * @Description: 往期直播列表
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2017年12月25日 下午6:00:22
	 */
	public Map<String, Object> findPastLiveCourse(Map<String, Object> param) throws SQLExecutorException {
		// 响应数据
		Map<String, Object> data = new HashMap<>();
		
		// 根据条件获取往期直播课程总条数
		int count = appLiveCourseMapper.getPastLiveCourse(param);
		// 总页数
		int totalPage;
		if (count % ConstantIF.PAGE_OFFSET != 0) {
			totalPage = count / ConstantIF.PAGE_OFFSET + 1;
		} else {
			totalPage = count / ConstantIF.PAGE_OFFSET;
		}

		//当前请求页
		int page = (int) param.get("page");
		
		// 总页数
		data.put("totalpage", totalPage);
		// 当前页
		data.put("currentpage", page);

		// 查看是否已超总页数
		if (totalPage < page) {
			data.put("courselist", new ArrayList<>());
			return data;
		}

		param.put("start", (page - 1) * ConstantIF.PAGE_OFFSET);
		param.put("offset", ConstantIF.PAGE_OFFSET);
		// 获取往期直播课程列表
		List<CourseList> courseList = appLiveCourseMapper.getPastLiveCourseList(param);
		for (CourseList course : courseList) {
			course.setCoursepic(ConstantIF.PROJECT + course.getCoursepic());
		}

		data.put("courselist", courseList);
		return data;
	}

	/**
	 * @Title: liveCourseRoom
	 * @Description: 直播间
	 * @param userid
	 * @param courseid
	 * @return
	 * @throws SQLExecutorException
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws:
	 * @time: 2018年5月5日 上午11:18:12
	 */
	public ResultBean liveCourseRoom(String userid, String courseid, String clienttype) throws SQLExecutorException, ParseException, IOException {
		//1、查看直播课程类型【1：免费直播课程2：vip实战直播课程】
		LiveCourse liveCourse = appLiveCourseMapper.getLiveCourseDateByCourseid(courseid);
		liveCourse.setCoursepic(ConstantIF.PROJECT + liveCourse.getCoursepic());
		
		//直播课程liveflag(1正在直播，2往期直播，3预告直播)
		if(liveCourse.getLiveflag() == 3) {
			return new ResultBean("030", ResultMsg.C_030);
		}
		
		//vip课程，检测用户vip等级
		if(liveCourse.getCoursekind() == 2) {
			//检测是否是内部员工
			boolean isSaler = appMyInfoMapper.checkUserIsSaler(userid);
			if(!isSaler) {
				//非内部员工，获取用户vip等级列表
				List<String> vipLevel = appUserMapper.getUserVipLevel(userid);
				if(vipLevel == null || !vipLevel.contains(liveCourse.getCoursetypecode())) {
					//当前用户无vip等级权限，无法观看当前直播
					return new ResultBean("029", ResultMsg.C_029);
				}
			}
		}
		
		//频道信息
		ChannelBean channel = appLiveCourseMapper.getPullUrlByTeaid(liveCourse.getUserid());
		
		//2、当前用户满足观看条件
		Map<String, Object> data = new HashMap<>();
		data.put("course", liveCourse);
		
		if(liveCourse.getLiveflag() == 1) {
			//正在直播
			//根据讲师id获取频道信息
			data.put("room", channel);
		}else if(liveCourse.getLiveflag() == 2) {
			//往期直播
			//根据课程id获取视频地址
			String videourl = appLiveCourseMapper.getCourseVideo(courseid);
			liveCourse.setVideourl(videourl);
			data.put("room", new HashMap<>());
		}
		
		Map<String, Object> chat = new HashMap<>();
		//获取用户网易云token
		String token = appLiveCourseMapper.getUserVCloudToken(userid);
		chat.put("appkey", VcoludConf.config().getConfig("vcloud.key"));
		chat.put("accid", userid);
		chat.put("tokenid", token);
		chat.put("roomid", channel.getRoomid());
		//获取聊天室地址
		Map<String, String> param = new HashMap<>();
		param.put("roomid", channel.getRoomid()); //聊天室id
		param.put("accid", userid); //进入聊天室的账号
		param.put("clienttype", clienttype); //1:weblink（客户端为web端时使用）; 2:commonlink（客户端为非web端时使用）, 默认1
		Map<String, Object> chatRoomAddr = VCloudManage.im("vcloud.chatroom_addr", param);
		chat.put("addr", chatRoomAddr.get("addr"));
		data.put("chat", chat);
		
		return new ResultBean(data);
	}

}
