package dianfan.service.agent;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.WriterException;

import common.propertymanager.PropertyUtil;
import dianfan.constant.ConstantIF;
import dianfan.dao.mapper.agent.AgentQRMapper;
import dianfan.entities.Popu;
import dianfan.exception.SQLExecutorException;
import dianfan.service.RedisTokenService;
import dianfan.util.BarAndQrUtil;
import dianfan.util.HttpClientHelper;
import dianfan.util.MD5Utils;

/**
 * @ClassName AgentCourseService
 * @Description 代理商课程服务
 * @author cjy
 * @date 2018年1月18日 下午5:18:10
 */
@Service
public class AgentQRService {
	@Autowired
	private AgentQRMapper agentQRMapper;
	@Autowired
	private RedisTokenService redisTokenService;

	public static Logger log = Logger.getLogger(AgentQRService.class);

	/**
	 * @Title: checkQR
	 * @Description: 推广链接验证
	 * @param popu
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月2日 下午5:08:41
	 */
	public Popu checkQR(Popu popu) throws SQLExecutorException {
		Popu qr = agentQRMapper.getUserQR(popu);
		// 用户参数生成的token
		String token = MD5Utils.getUpperMD5(popu.getUpdatetime().getTime() + popu.getUserid());
		// 验证
		if (token != null && qr != null && token.equals(qr.getCheckcode())) {
			if (popu.getType() == 2) {
				// 获取直播间教师id
				String agentId = agentQRMapper.findAgentInfo(popu.getUserid());
				if (agentId == null) {
					qr.setId(popu.getUserid());
				} else {
					qr.setId(agentId);
				}
			}
			return qr;
		} else {
			return null;
		}
	}

	/**
	 * @Title: findUserQR
	 * @Description: 获取二维码
	 * @param userid
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年1月30日 下午4:38:08
	 */
	public Popu findUserQR(String userid, int type) throws SQLExecutorException {
		Popu popu = new Popu();
		popu.setUserid(userid);
		popu.setType(type);
		popu = agentQRMapper.getUserQR(popu);
		return popu;
	}

	/**
	 * @Title: createQRCode
	 * @Description: 生成二维码(生成策略：使用uid与最后更新的时间进行MD5加签，得到check参数)
	 * @param userid
	 * @param type
	 *            类型（1二维码推广，2直播间推广）
	 * @param filepath
	 *            二维码生成根路径
	 * @throws SQLExecutorException
	 * @throws WriterException
	 * @throws IOException
	 * @throws:
	 * @time: 2018年2月2日 下午12:59:46
	 */
	@Transactional
	public Popu updateQRCode(String userid, int type) throws SQLExecutorException, WriterException, IOException {
		Popu popu = new Popu();
		popu.setUserid(userid);
		popu.setType(type);
		// 获取上级代理商数据
		String agentid = agentQRMapper.getAgentIdBySalerId(userid);
		if (agentid == null) {
			agentid = userid;
		}
		// 更新二维码数据
		popu = createQRCode(popu, agentid);
		// 保存
		agentQRMapper.updatePopuInfo(popu);

		popu.setId(null);
		popu.setCheckcode(null);
		popu.setUpdatetime(null);
		popu.setQrurl(ConstantIF.PROJECT + popu.getQrurl());
		return popu;
	}

	/**
	 * @Title: createQRCode
	 * @Description: 生成推广数据
	 * @param popu
	 * @param agentId
	 * @param filepath
	 * @return
	 * @throws SQLExecutorException
	 * @throws NumberFormatException
	 * @throws WriterException
	 * @throws IOException
	 * @throws:
	 * @time: 2018年2月11日 上午10:54:33
	 */
	public Popu createQRCode(Popu popu, String agentId) throws NumberFormatException, WriterException, IOException {
		popu.setUpdatetime(new Timestamp(new Date().getTime()));
		popu.setCheckcode(MD5Utils.getUpperMD5(popu.getUpdatetime().getTime() + popu.getUserid()));

		// 生成二维码路径
		String domain = PropertyUtil.getProperty("domain");
		String path = PropertyUtil.getProperty("upload_qr_path") + popu.getUpdatetime().getTime() + "."
				+ PropertyUtil.getProperty("qr_ext");
		String qr_path = domain + path;

		String qr_popu_url = null;

		if (popu.getType() == 1) {
			// 二维码推广
			// 推广链接模板
			qr_popu_url = PropertyUtil.getProperty("reg_popu_url");
			// http://admin.huijingschool.com/LiveAdmin/qr?type=regqr&uid=^&t=^&c=^
			String[] data = { popu.getUserid(), popu.getUpdatetime().getTime() + "", popu.getCheckcode() };
			for (int i = 0; i < data.length; i++) {
				qr_popu_url = qr_popu_url.replaceFirst("@", data[i]);
			}
			// 生成推广链接
			popu.setPopuurl(qr_popu_url);
		} else if (popu.getType() == 2) {
			// 直播间推广
			// 推广链接模板
			qr_popu_url = PropertyUtil.getProperty("live_popu_url");
			// http://admin.huijingschool.com/LiveAdmin/popu/qr?type=live&agent=@&uid=@&t=@&c=@
			String[] data = { agentId, popu.getUserid(), popu.getUpdatetime().getTime() + "", popu.getCheckcode() };
			for (int i = 0; i < data.length; i++) {
				qr_popu_url = qr_popu_url.replaceFirst("@", data[i]);
			}
			// 生成推广链接
			popu.setPopuurl(qr_popu_url);
		}
		File file = new File(qr_path);
		if (!file.exists()) {
			file.mkdirs();
		}
		// 生成二维码
		BarAndQrUtil.encode2(qr_popu_url, Integer.parseInt(PropertyUtil.getProperty("qr_width")),
				Integer.parseInt(PropertyUtil.getProperty("qr_height")), qr_path);
		popu.setQrurl(path);

		// 已生成
		return popu;
	}

	/**
	 * @Title: createWxQRCode
	 * @Description: 获取二维码ticket
	 * @param agentId
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 * @throws:
	 * @time: 2018年2月28日 下午4:37:02
	 */
	public String createWxQRCode(String agentId) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		// 生成微信推广二维码
//		String access_token = HttpClientHelper.sendGet(PropertyUtil.getProperty("access_token_url"), null, "UTF-8");
//		try {
//			Map token = mapper.readValue(access_token, Map.class);
//			access_token = (String) token.get("access_token");
//		} catch (IOException e1) {
//			return "";
//		}
		String access_token = redisTokenService.getWxAccessToken();
		
		Map<String, String> scene_str = new HashMap<>();
		scene_str.put("scene_str", agentId);
		Map<String, Map<String, String>> scene = new HashMap<>();
		scene.put("scene", scene_str);
		Map<String, Object> params = new HashMap<>();
		params.put("action_name", PropertyUtil.getProperty("action_name"));
		params.put("action_info", scene);
		try {
			String json = mapper.writeValueAsString(params);
			// 创建二维码ticket
			String ticket = HttpClientHelper.sendPostJson(PropertyUtil.getProperty("qrcode_url") + access_token, json);
			Map readValue = mapper.readValue(ticket, Map.class);
			// 已生成
			return (String) readValue.get("ticket");
		} catch (IOException e) {
			return "";
		}

	}

}