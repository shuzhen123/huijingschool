package dianfan.service.app;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import common.propertymanager.PropertyUtil;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultMsg;
import dianfan.dao.mapper.app.AppCourseMapper;
import dianfan.dao.mapper.app.AppUserMapper;
import dianfan.dao.mapper.app.PayNotifyMapper;
import dianfan.entities.ResultBean;
import dianfan.entities.UserInfo;
import dianfan.exception.SQLExecutorException;
import dianfan.service.RedisService;
import dianfan.service.RedisTokenService;
import dianfan.util.GenRandomNumUtil;
import dianfan.util.HttpClientHelper;
import dianfan.util.UUIDUtil;
import dianfan.vcloud.core.VCloudManage;

/**
 * @ClassName BannerService
 * @Description 用户相关服务
 * @author cjy
 * @date 2018年1月23日 下午2:22:37
 */
@Service
public class AppUserService {
	@Autowired
	private RedisService<?, ?> redisService;
	@Autowired
	private RedisTokenService tokenService;
	@Autowired
	private AppUserMapper appUserMapper;
	@Autowired
	private PayNotifyMapper payNotifyMapper;
	@Autowired
	private AppCourseMapper appCourseMapper;

	/**
	 * @Title: register
	 * @Description: 用户注册
	 * @param telno
	 *            手机号码
	 * @param smscode
	 *            短信验证码
	 * @param password
	 *            密码
	 * @param invite_code
	 *            邀请码
	 * @param salerid
	 *            业务员id
	 * @throws SQLExecutorException 
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws:
	 * @time: 2017年12月20日 上午11:38:59
	 */
	@Transactional
	public ResultBean register(String telno, String smscode, String password, String invite_code, String salerid) throws SQLExecutorException, ClientProtocolException, IOException {
		// 1、根据telno获取缓存的验证码， 并验证验证码正确性
		String save_code = redisService.get(telno + "reg");
		if (save_code == null || !smscode.equals(save_code)) {
			return new ResultBean("005", ResultMsg.C_005);
		}
		// 如果填写了邀请码，邀请码优先，检测邀请码对应业务员信息
		if (invite_code != null && !invite_code.trim().isEmpty()) {
			try {
				salerid = appUserMapper.checkInviteCode(invite_code);
				if (salerid == null) {
					return new ResultBean("021", ResultMsg.C_021);
				}
			} catch (SQLExecutorException e) {
				return new ResultBean("500", ResultMsg.C_500);
			}
		}
		
		//检测是否已导入
		UserInfo info = appUserMapper.checkImportUser(telno);
		if(info == null) {
			//第一次注册
			//构造实体数据
			info = new UserInfo();
			info.setUserid(UUIDUtil.getUUID());
			info.setPassword(password);
			info.setTelno(telno);
			info.setInvitecode(invite_code);
			info.setNickname(GenRandomNumUtil.getRandomName());
			info.setIconurl(ConstantIF.DEFAULT_ICON);
			info.setTokenid(UUIDUtil.getUUID());
			
			//持久化用户数据
			appUserMapper.addUser(info);
			//写入所属代理商-业务员数据
			Map<String, String> param = new HashMap<>();
			param.put("userid", info.getUserid());
			if (salerid != null && !salerid.isEmpty()) {
				// 获取业务员的上级代理商id
				String agentid = appUserMapper.findAgentidBySalerid(salerid);
				if (agentid == null) {
					agentid = salerid;
					salerid = null;
				}
				param.put("agentid", agentid);
				param.put("dtsmuserid", salerid);
			}else {
				//普通注册用户全都导入agent代理商
				param.put("agentid", ConstantIF.AGENT_ID);
			}
			appUserMapper.addCustomerRelation(param);
			//写入用户分类数据
			appUserMapper.addUserLevelInfo(info.getUserid());
		}else {
			//导入用户，用户注册行为
			info.setPassword(password);
			info.setInvitecode(invite_code);
			info.setIconurl(ConstantIF.DEFAULT_ICON);
			info.setTokenid(UUIDUtil.getUUID());
			appUserMapper.updateReregUserInfo(info);
			
			//更新所属代理商-业务员数据
			if (salerid != null && !salerid.isEmpty()) {
				// 获取业务员的上级代理商id
				Map<String, String> param = new HashMap<>();
				param.put("userid", info.getUserid());
				String agentid = appUserMapper.findAgentidBySalerid(salerid);
				if (agentid == null) {
					agentid = salerid;
					salerid = null;
				}
				param.put("agentid", agentid);
				param.put("dtsmuserid", salerid);
				appUserMapper.updateCustomerRelation(param);
			}
		}
		
		redisService.del(telno + "reg");
		//赠送新用户注册代金券、体验券
		//获取代金券时长
		int validityDay = appUserMapper.getCouponValidityDay(ConstantIF.REG_COUPON_ID);
		appUserMapper.provideCoupon(ConstantIF.REG_COUPON_ID, info.getUserid(), validityDay);
		//获取代金券时长
		validityDay = appUserMapper.getCouponValidityDay(ConstantIF.REG_EXP_ID);
		appUserMapper.provideCoupon(ConstantIF.REG_EXP_ID, info.getUserid(), validityDay);
		
		//开通网易云id
		Map<String, String> im_crt_param = new HashMap<>();
		im_crt_param.put("accid", info.getUserid()); //网易云通信ID
		im_crt_param.put("name", info.getNickname()); //网易云通信ID昵称
		im_crt_param.put("icon", ConstantIF.PROJECT + info.getIconurl()); //网易云通信ID头像URL
		im_crt_param.put("token", info.getTokenid()); //网易云通信token
		VCloudManage.im("vcloud.mi.createid", im_crt_param);

		return new ResultBean();
	}

	/**
	 * @Title: userLogin
	 * @Description: 登录验证
	 * @param telno
	 *            手机号
	 * @param password
	 *            密码
	 * @return ResultBean ResultBean
	 * @throws SQLExecutorException
	 *             sql执行异常
	 * @throws:
	 * @time: 2018年4月17日 下午4:38:25
	 */
	public ResultBean userLogin(String telno, String password) throws SQLExecutorException {
		// 1、验证是否短信验证码登录
		String save_code = redisService.get(telno + "login");

		UserInfo userInfo = new UserInfo();
		userInfo.setTelno(telno);
		userInfo.setPassword(password);

		if (save_code == null || !password.equals(save_code)) {
			// 密码码登录
			// 根据手机号码和密码获取用户数量
			int count = appUserMapper.checkLogin(userInfo);
			if (count == 0) {
				// 登录验证失败
				return new ResultBean("008", ResultMsg.C_008);
			}
		}

		/* -----登录验证成功----- */

		// 4、验证通过，获取用户信息
		userInfo = appUserMapper.findUserInfo(userInfo);

		// 5、缓存token
		String token = tokenService.createToken(userInfo.getUserid());

		// 构建返回实体
		Map<String, Object> retult = new HashMap<>();

		retult.put("userid", userInfo.getUserid());
		retult.put("agentid", userInfo.getAgentid());
		retult.put("eval", (userInfo.getRiskratingfalg() == null || userInfo.getRiskratingfalg().isEmpty()) ? 0 : 1);
		retult.put("has_openid", userInfo.getOpenid() == null ? 0 : 1);
		if (userInfo.getOpenid() == null) {
			retult.put("appid", PropertyUtil.getProperty("appid"));
		}
		retult.put(ConstantIF.ACCESSTOKEN, userInfo.getUserid() + "_" + token);
		
		// 缓存已购买的课程id
		List<String> courseids = payNotifyMapper.getByedCourse(userInfo.getUserid());
		// 清空课程缓存
		redisService.del(userInfo.getUserid() + ConstantIF.USER_COURSE_CACHE_SUFFIX);
		// 缓存课程id
		redisService.Sadd(userInfo.getUserid() + ConstantIF.USER_COURSE_CACHE_SUFFIX, courseids);
		
		return new ResultBean(retult);
	}
	
	/**
	 * @Title: userAutoLogin
	 * @Description: 
	 * @param userId
	 * @return
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年4月27日 下午7:23:06
	 */
	public ResultBean userAutoLogin(String userId) throws SQLExecutorException {
		// 4、验证通过，获取用户信息
		UserInfo userInfo = appUserMapper.findUserInfoById(userId);

		// 5、缓存token
		String token = tokenService.createToken(userInfo.getUserid());

		// 构建返回实体
		Map<String, Object> retult = new HashMap<>();

		retult.put("userid", userInfo.getUserid());
		retult.put("eval", (userInfo.getRiskratingfalg() == null || userInfo.getRiskratingfalg().isEmpty()) ? 0 : 1);
		retult.put("has_openid", userInfo.getOpenid() == null ? 0 : 1);
		if (userInfo.getOpenid() == null) {
			retult.put("appid", PropertyUtil.getProperty("appid"));
		}
		retult.put(ConstantIF.ACCESSTOKEN, userInfo.getUserid() + "_" + token);
		
		// 缓存已购买的课程id
		List<String> courseids = payNotifyMapper.getByedCourse(userInfo.getUserid());
		// 清空课程缓存
		redisService.del(userInfo.getUserid() + ConstantIF.USER_COURSE_CACHE_SUFFIX);
		// 缓存课程id
		redisService.Sadd(userInfo.getUserid() + ConstantIF.USER_COURSE_CACHE_SUFFIX, courseids);
		
		return new ResultBean(retult);
	}

	/**
	 * @Title: resetPassword
	 * @Description: 重置密码
	 * @param telno
	 * @param smscode
	 * @param password
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年1月22日 上午11:18:14
	 */
	@Transactional
	public ResultBean resetPassword(String telno, String smscode, String password) throws SQLExecutorException {
		// 1、根据telno获取缓存的验证码， 并验证验证码正确性
		String save_code = redisService.get(telno + "resetpwd");
		if (save_code == null || !smscode.equals(save_code)) {
			return new ResultBean("005", ResultMsg.C_005);
		}
		// 验证通过，重置密码
		Map<String, String> param = new HashMap<>();
		param.put("telno", telno);
		param.put("password", password);
		appUserMapper.resetPassword(param);
		return new ResultBean();
	}

	/**
	 * @Title: addCustomerUser
	 * @Description: 保存用户数据
	 * @param phone
	 * @param realname
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2017年12月21日 下午1:03:07
	 */
	@Transactional
	public void addCustomerUser(String phone, String realname) throws SQLExecutorException {
		Map<String, String> param = new HashMap<>();
		param.put("phone", phone);
		param.put("realname", realname);
		appUserMapper.addCustomerUser(param);
	}

	/**
	 * @Title: getUserOpenid
	 * @Description: 获取用户openid
	 * @param userid
	 *            用户id
	 * @param code
	 *            code
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月17日 下午5:22:39
	 */
	@Transactional
	public void getUserOpenid(String userid, String code)
			throws JsonParseException, JsonMappingException, IOException, SQLExecutorException {
		String[] param = { PropertyUtil.getProperty("appid"), PropertyUtil.getProperty("secret"), code };
		String openid_url = PropertyUtil.getProperty("mp_openid_url");
		for (int i = 0; i < param.length; i++) {
			openid_url = openid_url.replaceFirst("@", param[i]);
		}
		String res = HttpClientHelper.sendGet(openid_url, null, "UTF-8");
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> value = mapper.readValue(res, Map.class);
		if (value.get("errcode") == null) {
			// openid获取成功
			String openid = value.get("openid");
			// 保存openid
			Map<String, String> data = new HashMap<>();
			data.put("userid", userid);
			data.put("openid", openid);
			appUserMapper.saveOpenid(data);
		}

	}

}
