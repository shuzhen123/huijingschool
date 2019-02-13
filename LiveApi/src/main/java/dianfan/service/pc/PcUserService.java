package dianfan.service.pc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.dao.mapper.pc.PcUserMapper;
import dianfan.entities.ChannelBean;
import dianfan.entities.UserInfo;
import dianfan.exception.SQLExecutorException;
import dianfan.vcloud.core.VCloudManage;

/**
 * @ClassName PcUserService
 * @Description pc用户数据相关服务
 * @author cjy
 * @date 2018年1月23日 上午9:50:09
 */
@Service
public class PcUserService {
	@Autowired
	private PcUserMapper pcUserMapper;
	
	/**
	 * @Title: userLogin
	 * @Description: pc用户登录验证
	 * @param account
	 * @param password
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年1月23日 上午9:52:16
	 */
	public UserInfo userLogin(String account, String password) throws SQLExecutorException {
		UserInfo userInfo = new UserInfo();
		userInfo.setUsername(account);
		userInfo.setPassword(password);
		//获取用户数据
		return pcUserMapper.checkLogin(userInfo);
	}

	/**
	 * @Title: changeLiveRoomPassword
	 * @Description: 更改房间密码
	 * @param userid
	 * @param roompwd
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年1月23日 上午10:19:06
	 */
	@Transactional
	public void changeLiveRoomPassword(String userid, String roompwd, String transcribe) throws SQLExecutorException {
		Map<String, String> param = new HashMap<>();
		param.put("userid", userid);
		//录播
		if(transcribe != null && "y".equals(transcribe.trim())) {
			param.put("liveuploadflag", "1");
		}else {
			param.put("liveuploadflag", "0");
		}
		//房间密码
		if(roompwd == null || "".equals(roompwd.trim())) {
			param.put("cidpassword", null);
		}else {
			param.put("cidpassword", roompwd);
		}
		pcUserMapper.updateRoomPasswordByUserid(param);
	}

	/**
	 * @Title: changeTranscribeStatus
	 * @Description: 录播设置
	 * @param userid 讲师id
	 * @param transcribe 是否录播（y是, n否）
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年1月23日 上午10:23:49
	 */
	public Map changeTranscribeStatus(String userid, String transcribe) throws ClientProtocolException, IOException, SQLExecutorException {
		/*
		 * cid 			String 	频道ID，32位字符串 	是
		 * needRecord 	int 	1-开启录制； 0-关闭录制 	是
		 * format 		int 	1-flv； 0-mp4 	是
		 * duration 	int 	录制切片时长(分钟)，5~120分钟 	是
		 * filename 	String 	录制后文件名（只支持中文、字母和数字），格式为filename_YYYYMMDD-HHmmssYYYYMMDD-HHmmss, 
		 * 						文件名录制起始时间（年月日时分秒) -录制结束时间（年月日时分秒) 	否
		 */
		
		//根据讲师id获取直播频道cid
		ChannelBean channelInfo = pcUserMapper.getTeacherChannelInfo(userid);
		
		//网易云直播请求参数
		Map<String, Object> requestParam = new HashMap<>();
		
		//频道ID
		requestParam.put("cid", channelInfo.getCid());
		//开启/关闭录制
		requestParam.put("needRecord", "y".equals(transcribe)?"1":"0");
		//视频格式
		requestParam.put("format", "0");
		//录制切片时长(分钟)
		requestParam.put("duration", "120");
		//请求操作
		return VCloudManage.channel("vcloud.channel.record", requestParam);
	}

}
