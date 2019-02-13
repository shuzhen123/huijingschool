package dianfan.service.agent;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.constant.ConstantIF;
import dianfan.dao.mapper.agent.AgentTeacherMapper;
import dianfan.entities.ChannelBean;
import dianfan.entities.DataTable;
import dianfan.entities.UserInfo;
import dianfan.exception.SQLExecutorException;
import dianfan.exception.VCloudException;
import dianfan.util.UUIDUtil;
import dianfan.vcloud.core.VCloudManage;

/**
 * @ClassName AgentTeacherService
 * @Description 教师相关服务
 * @author cjy
 * @date 2018年3月20日 下午4:27:47
 */
@Service
public class AgentTeacherService {
	@Autowired
	private AgentTeacherMapper teacherMapper;

	/**
	 * @Title: findTeachers
	 * @Description: 教师列表
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月20日 下午4:31:19
	 */
	public DataTable findTeachers(Map<String, Object> param) throws SQLExecutorException {
		DataTable dt = new DataTable();
		// 根据条件获取教师总条数
		int count = teacherMapper.findTeacherCount(param);
		if (count == 0) {
			// 无数据
			return dt;
		} else {
			// 设置总条数
			dt.setRecordsTotal(count);
			// 设置请求条数
			dt.setRecordsFiltered(count);
		}

		// 2、获取需求数据
		List<UserInfo> teachers = teacherMapper.findTeachers(param);

		for (UserInfo info : teachers) {
			info.setIconurl(ConstantIF.PROJECT + info.getIconurl());
			info.setTeacherurl(ConstantIF.PROJECT + info.getTeacherurl());
		}

		// 设置数据
		dt.setData(teachers);
		return dt;
	}

	/**
	 * @Title: addTeacherInfo
	 * @Description: 添加教师数据
	 * @param info
	 * @throws SQLExecutorException
	 * @throws IOException
	 * @throws ClientProtocolException
	 * @throws VCloudException
	 * @throws:
	 * @time: 2018年3月20日 下午5:42:10
	 */
	@Transactional
	public void addTeacherInfo(UserInfo info)
			throws SQLExecutorException, ClientProtocolException, IOException, VCloudException {
		// 1、开通直播频道
		// ret={
		// httpPullUrl=http://flvdbe526a0.live.126.net/live/9e07d1592f56461098a4d23347bd5467.flv?netease=flvdbe526a0.live.126.net, -http拉流地址
		// hlsPullUrl=http://pullhlsdbe526a0.live.126.net/live/9e07d1592f56461098a4d23347bd5467/playlist.m3u8, -hls拉流地址
		// rtmpPullUrl=rtmp://vdbe526a0.live.126.net/live/9e07d1592f56461098a4d23347bd5467, -rtmp拉流地址
		// name=0ce37a88aead491f8ea489bbf487f566, -频道名称
		// pushUrl=rtmp://pdbe526a0.live.126.net/live/9e07d1592f56461098a4d23347bd5467?wsSecret=39f73eb2272ec346c74cda85673ddca1&wsTime=1524539358, -推流地址
		// ctime=1524539358659, -创建频道的时间戳
		// cid=9e07d1592f56461098a4d23347bd5467 -频道ID，32位字符串
		// },
		// requestId=livea50323e6cb2149b08332d763f67911ce,
		// code=200  -状态码
		Map<String, Object> vparam = new HashMap<>();
		vparam.put("name", info.getRealname()); //频道名称
		vparam.put("type", 0); //频道类型（0:rtmp）
		Map<String, Object> channel_result = VCloudManage.channel("vcloud.channel.create", vparam);
		if (channel_result == null) {
			// 频道创建失败
			throw new VCloudException();
		} else if ((int) channel_result.get("code") != 200) {
			throw new VCloudException((String) channel_result.get("msg"));
		}
		// 开通直播频道成功
		Map<String, Object> ret = (Map<String, Object>) channel_result.get("ret");
		
		//2、创建网易云通信ID
		Map<String, String> im_crt_param = new HashMap<>();
		im_crt_param.put("accid", info.getUserid()); //网易云通信ID
		im_crt_param.put("name", info.getRealname()); //网易云通信ID昵称
		im_crt_param.put("icon", info.getIconurl()); //网易云通信ID头像URL
		im_crt_param.put("token", UUIDUtil.getUUID()); //网易云通信token
		Map<String, Object> create_mi_result = VCloudManage.im("vcloud.mi.createid", im_crt_param);
		if (create_mi_result == null) {
			// 云通信ID创建失败
			throw new VCloudException();
		} else if ((int) create_mi_result.get("code") != 200) {
			throw new VCloudException((String) create_mi_result.get("msg"));
		}
		
		//3、创建聊天室
		/*
		 * chatroom={
				roomid=24203455, 房间号
				valid=true, 有效的
				announcement=null,  公告，长度限制4096个字符
				queuelevel=0, 队列管理权限：0:所有人都有权限变更队列，1:只有主播管理员才能操作变更。默认0
				muted=false, 
				name=测试讲师（请暂时不要删除）-直播间, 
				broadcasturl=null, 直播地址，长度限制1024个字符
				ext=, 扩展字段，最长4096字符
				creator=b7c37361342842a6acdc74b158799b01 聊天室属主的账号accid
			}, 
			code=200
		 */
		Map<String, String> im_chat_param = new HashMap<>();
		im_chat_param.put("creator ", info.getUserid()); //聊天室属主的账号accid
		im_chat_param.put("name", info.getRealname() + "-直播间"); //聊天室名称
		Map<String, Object> room_result = VCloudManage.im("vcloud.room.create", im_chat_param);
		if (room_result == null) {
			// 创建直播间失败
			throw new VCloudException();
		} else if ((int) room_result.get("code") != 200) {
			throw new VCloudException((String) room_result.get("msg"));
		}
		// 创建直播间成功
		Map<String, Object> room_ret = (Map<String, Object>) room_result.get("chatroom");
		
		ChannelBean channel = new ChannelBean();
		channel.setId(UUIDUtil.getUUID());
		channel.setAccid(info.getUserid());
		channel.setToken(im_crt_param.get("token"));
		channel.setCid((String) ret.get("cid"));
		channel.setChannelname((String) ret.get("name"));
		channel.setRoomid(room_ret.get("roomid").toString());
		channel.setChatroomname((String) room_ret.get("name"));
		channel.setPushUrl((String) ret.get("pushUrl"));
		channel.setHttpPullUrl((String) ret.get("httpPullUrl"));
		channel.setHlsPullUrl((String) ret.get("hlsPullUrl"));
		channel.setRtmpPullUrl((String) ret.get("rtmpPullUrl"));
		channel.setCtime(new Timestamp((long) ret.get("ctime")));
		
		//添加教师频道数据
		teacherMapper.addTeacherChannel(channel);
		
		// 添加教师数据
		info.setCid(channel.getCid());
		teacherMapper.addTeacherInfo(info);
		
		// 添加代理商-讲师关系
		teacherMapper.addAgentTeacherRelation(info);
	}

	/**
	 * @Title: editTeacherInfo
	 * @Description: 更新讲师数据
	 * @param info
	 * @throws SQLExecutorException
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws:
	 * @time: 2018年3月20日 下午6:28:46
	 */
	@Transactional
	public void editTeacherInfo(UserInfo info) throws SQLExecutorException, ClientProtocolException, IOException {
		if (info.getCidpassword() == null || info.getCidpassword().isEmpty()) {
			info.setCidpassword(null);
		}
		teacherMapper.editTeacherInfo(info);
		
		//同步网易云id数据
		Map<String, String> param = new HashMap<>();
		param.put("accid", info.getUserid());
		param.put("name", info.getRealname());
		if(info.getIconurl() != null) {
			param.put("icon", ConstantIF.PROJECT + info.getIconurl());
		}
		VCloudManage.im("vcloud.userinfo.update", param);
	}

}