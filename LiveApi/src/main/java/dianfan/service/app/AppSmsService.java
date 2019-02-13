package dianfan.service.app;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yunpian.sdk.model.Result;

import common.propertymanager.PropertyUtil;
import dianfan.constant.ResultMsg;
import dianfan.entities.ResultBean;
import dianfan.nimsdk.YunpianSms;
import dianfan.nimsdk.YunpianSms1;
import dianfan.service.RedisService;
import dianfan.util.GenRandomNumUtil;

@Service
public class AppSmsService {
	@Autowired
	private RedisService redisService;

	/**
	 * @Title: getCode
	 * @Description: 获取验证码 
	 * 				   发送策略为：无发送记录，则发送，保存验证码和时间到redis，
	 * 				   若在重发时间间隔内，则不发送，若超间隔时间且有未使用的验证码数据，
	 * 				   则重发短信，验证码为记录的原验证码
	 * @param telephone
	 * @param type 发送类型（login:登录、reg:注册）
	 * @return
	 * @throws:
	 * @time: 2017年12月21日 下午3:02:20
	 */
	public ResultBean getCode(String telephone, String type) {
		// 查看是否有验证码缓存
		String last_sms = (String) redisService.get(telephone+type);
		
		// 验证码有效时间(秒)
		int invalidSecs = Integer.parseInt(PropertyUtil.getProperty("smsvalidity")) * 60;
		
		// 随机验证码
		String codeRadom = null;
		
		if (last_sms != null) {
			// 查看剩余时间(秒)
			Long time = redisService.getExpire(telephone+type);
			// 验证码发送间隔(秒)
			int sendInterval = Integer.parseInt(PropertyUtil.getProperty("smssendinterval")) * 60;
			//时间判断
			if(invalidSecs - time < sendInterval) {
				//发送频率为间隔时间内，无须重发
				return new ResultBean();
			}
			// 上次的验证码
			codeRadom = last_sms;
		} else {
			// 无记录，创建新的随机验证码
			codeRadom = GenRandomNumUtil.getRandomNum(6);
		}
		
		redisService.set(telephone+type, codeRadom, invalidSecs);
		
		String[] data = {codeRadom};
		//发送短信验证码
		Map<String, Object> result = YunpianSms.getInstance().sendSignleSms(telephone, data);
//		Map<String, Object> result = new HashMap<>();
		
		// 返回验证码状态信息
		if (result.get("code") == null || ("0".equals(result.get("code")) || ((int) result.get("code")) == 0)) {
			//发送成功
//			redisService.set(telephone+type, codeRadom, invalidSecs);
			return new ResultBean("200", ResultMsg.C_200 +"验证码："+ codeRadom + ", 有效期：" + (invalidSecs / 60) + "分钟");
		} else if(!"500".equals(result.get("code"))){
			byte[] b = ((String)result.get("msg")).getBytes();
			String msg;
			try {
				msg = new String(b, "UTF-8").toString();
			} catch (UnsupportedEncodingException e) {
				msg = "未知";
			}
			return new ResultBean("003", ResultMsg.C_003 + msg + "验证码："+codeRadom + ", 有效期：" + (invalidSecs / 60) + "分钟");
		} else {
			return new ResultBean("003", ResultMsg.C_003 + ResultMsg.C_500);
		}
	}
}
