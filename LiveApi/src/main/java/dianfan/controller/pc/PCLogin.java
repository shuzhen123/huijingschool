package dianfan.controller.pc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import dianfan.annotations.LogOp;
import dianfan.annotations.UnCheckedFilter;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultMsg;
import dianfan.dao.mapper.pc.PcUserMapper;
import dianfan.entities.ChannelBean;
import dianfan.entities.ResultBean;
import dianfan.entities.TokenModel;
import dianfan.entities.UserInfo;
import dianfan.exception.SQLExecutorException;
import dianfan.service.RedisTokenService;
import dianfan.service.pc.PcUserService;

/**
 * @ClassName PCLogin
 * @Description pc端登录
 * @author cjy
 * @date 2018年1月12日 下午2:43:42
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/pc")
public class PCLogin {
	@Autowired
	private PcUserService pcUserService;
	@Autowired
	private RedisTokenService tokenService;
	@Autowired
	private PcUserMapper pcUserMapper;

	/**
	 * @Title: login
	 * @Description: 直播用户登录
	 * @param session
	 * @param mac 客户端唯一值（极其重要，做接口访问安全校验）
	 * @param account 账号
	 * @param password 密码
	 * @return
	 * @throws:
	 * @time: 2018年1月24日 下午2:31:40
	 */
	@LogOp(method = "login", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "直播用户登录")
	@ApiOperation(value = "直播用户登录", httpMethod = "POST", notes = "直播用户登录", response = ResultBean.class)
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@UnCheckedFilter
	public @ResponseBody ResultBean login(HttpSession session, 
			@ApiParam("客户端唯一mac值（极其重要，做接口访问安全校验）") @RequestParam String mac, 
			@ApiParam("账号") @RequestParam String account, 
			@ApiParam("密码") @RequestParam String password) {
		//验证账号密码
		if(account == null || "".equals(account.trim()) || password == null || "".equals(password.trim())) {
			return new ResultBean("008", ResultMsg.C_008);
		}
		//验证登录
		try {
			UserInfo userInfo = pcUserService.userLogin(account, password);
			if(userInfo != null && userInfo.getUserid() != null) {
				//登录成功，缓存token
				String token = tokenService.createToken(userInfo.getUserid());
				//获取直播频道数据
				ChannelBean channelInfo = pcUserMapper.getTeacherChannelInfo(userInfo.getUserid());
				
				Map<String, Object> result = new HashMap<>();
				result.put(ConstantIF.ACCESSTOKEN, userInfo.getUserid()+"_"+token);
				result.put("channel", channelInfo);
				return new ResultBean(result);
			}else {
				//登录失败
				return new ResultBean("008", ResultMsg.C_008);
			}
		} catch (SQLExecutorException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: goLiveRoom
	 * @Description: 进入直播间
	 * @param session
	 * @param roompwd 房间新密码
	 * @param transcribe 是否录播（y是, n否）
	 * @return
	 * @throws:
	 * @time: 2018年1月23日 上午10:14:49
	 */
	@LogOp(method = "goLiveRoom", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "进入直播间")
	@ApiOperation(value = "进入直播间", httpMethod = "POST", notes = "进入直播间", response = ResultBean.class)
	@RequestMapping(value = "/liveroomsetting", method = RequestMethod.POST)
	public @ResponseBody ResultBean goLiveRoom(HttpSession session, 
			@ApiParam("accesstoken（极其重要，做接口访问安全校验）") @RequestParam String accesstoken,
			@ApiParam("房间新密码") @RequestParam(value = "roompwd", required = false) String roompwd, 
			@ApiParam("是否录播（y是, n否）") @RequestParam(value = "transcribe") String transcribe) {
		//获取userid
		TokenModel model = tokenService.getToken(accesstoken);
		//更改房间密码
		try {
			pcUserService.changeLiveRoomPassword(model.getUserId(), roompwd, transcribe);
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
		
		//录播设置
		try {
			Map status = pcUserService.changeTranscribeStatus(model.getUserId(), transcribe);
			if(status != null && ("200".equals(status.get("code")) || (int)status.get("code") == 200)) {
				return new ResultBean();
			}else {
				return new ResultBean("500", ResultMsg.C_500);
			}
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		} catch (ClientProtocolException e) {
			return new ResultBean("500", ResultMsg.C_500);
		} catch (IOException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
}
