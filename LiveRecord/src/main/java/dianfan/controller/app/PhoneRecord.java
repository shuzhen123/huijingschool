package dianfan.controller.app;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import common.propertymanager.PropertyUtil;
import dianfan.annotations.LogOp;
import dianfan.annotations.UnCheckedFilter;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultMsg;
import dianfan.dao.mapper.app.PhoneRecordMapper;
import dianfan.entities.BashMap;
import dianfan.entities.CallVoice;
import dianfan.entities.Customer;
import dianfan.entities.PhoneRecordInfo;
import dianfan.entities.ResultBean;
import dianfan.entities.TokenModel;
import dianfan.exception.SQLExecutorException;
import dianfan.service.RedisTokenService;
import dianfan.service.app.PhoneRecordService;
import dianfan.util.FileUploadUtils;
import dianfan.util.UUIDUtil;
/**
 * @ClassName PhoneRecord
 * @Description 业务员通话记录管理
 * @author cjy
 * @date 2018年3月14日 上午10:05:51
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/app")
public class PhoneRecord {
	@Autowired
	private PhoneRecordService phoneRecordService;
	@Autowired
	private PhoneRecordMapper phoneRecordMapper;
	@Autowired
	private RedisTokenService redisTokenService;
	
	/**
	 * @Title: customerClassify
	 * @Description: 客户分类列表
	 * @return
	 * @throws: 
	 * @time: 2018年3月14日 上午10:20:02
	 */
	@LogOp(method = "customerClassify", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "客户分类列表")
	@ApiOperation(value = "客户分类列表", httpMethod = "GET", notes = "客户分类列表", response = ResultBean.class)
	@RequestMapping(value = "classify", method=RequestMethod.GET)
	@UnCheckedFilter
	public @ResponseBody ResultBean customerClassify() {
		try {
			//获取客户分类列表
			List<BashMap> classify = phoneRecordMapper.findCustomerClassify();
			return new ResultBean(classify);
		} catch (SQLExecutorException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: collect
	 * @Description: 收藏/取消收藏（0:取消收藏，1:收藏）
	 * @return
	 * @throws:
	 * @time: 2018年3月19日 下午3:18:26
	 */
	@LogOp(method = "collect", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "收藏/取消收藏")
	@ApiOperation(value = "收藏/取消收藏", httpMethod = "POST", notes = "收藏/取消收藏", response = ResultBean.class)
	@RequestMapping(value = "collect", method=RequestMethod.POST)
	@UnCheckedFilter
	public @ResponseBody ResultBean collect(
			@ApiParam("accesstoken") @RequestParam(value = "accesstoken", required = true) String accesstoken,
			@ApiParam("客户id") @RequestParam(value = "customerid", required = true) String customerid,
			@ApiParam("动作类型（0:取消收藏，1:收藏）") @RequestParam(value = "type", required = true) int type) {
		TokenModel token = redisTokenService.getToken(accesstoken);
		try {
			phoneRecordService.collectAction(token.getUserId(), customerid, type);
			return new ResultBean();
		} catch (SQLExecutorException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: phoneRecord
	 * @Description: 业务员联系记录列表
	 * @param accesstoken
	 * @param page
	 * @return
	 * @throws:
	 * @time: 2018年3月14日 上午11:11:38
	 */
	@LogOp(method = "phoneRecord", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "业务员联系记录列表")
	@ApiOperation(value = "业务员联系记录列表", httpMethod = "POST", notes = "业务员联系记录列表", response = ResultBean.class)
	@RequestMapping(value = "phonerecord", method=RequestMethod.POST)
	public @ResponseBody ResultBean phoneRecord(
			@ApiParam("accesstoken") @RequestParam(value = "accesstoken", required = true) String accesstoken,
			
			@ApiParam("业务分区id") @RequestParam(value = "classid", required = false) String classid,
			@ApiParam("联系状态（0:未联系，1:已联系，或此参数为空字符串）") @RequestParam(value = "contacted", required = false) Integer contacted,
			@ApiParam("收藏状态（0:未收藏，1:已收藏，或此参数为空字符串）") @RequestParam(value = "collected", required = false) Integer collected,
			
			@ApiParam("请求的页数") @RequestParam(value = "page", defaultValue = "1", required = false) Integer page) {
		if(page == null) {
			page = 1;
		}
		TokenModel token = redisTokenService.getToken(accesstoken);
		try {
			//获取业务员通话记录列表
			List<PhoneRecordInfo> record = phoneRecordService.findPhoneRecord(token.getUserId(), page, classid, contacted, collected);
			return new ResultBean(record);
		} catch (SQLExecutorException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: phoneRecordDetail
	 * @Description: 客户录详情
	 * @param accesstoken
	 * @param customerid
	 * @return
	 * @throws:
	 * @time: 2018年3月14日 下午1:41:31
	 */
	@LogOp(method = "phoneRecord", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "客户详情")
	@ApiOperation(value = "客户详情", httpMethod = "POST", notes = "客户详情", response = ResultBean.class)
	@RequestMapping(value = "recorddetail", method=RequestMethod.POST)
	public @ResponseBody ResultBean phoneRecordDetail(
			@ApiParam("accesstoken") @RequestParam(value = "accesstoken", required = true) String accesstoken,
			@ApiParam("客户id") @RequestParam(value = "customerid", required = true) String customerid) {
		try {
			//获取客户通话记录详情
			Customer info = phoneRecordMapper.getCustomerInfo(customerid);
			return new ResultBean(info);
		} catch (SQLExecutorException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: updateCustomerNickname
	 * @Description: 更改客户昵称
	 * @param accesstoken
	 * @param customerid
	 * @param nickname
	 * @return
	 * @throws:
	 * @time: 2018年3月14日 下午2:06:07
	 */
	@LogOp(method = "updateCustomerNickname", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "更改客户昵称")
	@ApiOperation(value = "更改客户昵称", httpMethod = "POST", notes = "更改客户昵称", response = ResultBean.class)
	@RequestMapping(value = "changenickname", method=RequestMethod.POST)
	public @ResponseBody ResultBean updateCustomerNickname(
			@ApiParam("accesstoken") @RequestParam(value = "accesstoken", required = true) String accesstoken,
			@ApiParam("客户id") @RequestParam(value = "customerid", required = true) String customerid,
			@ApiParam("新昵称") @RequestParam(value = "nickname", required = true) String nickname) {
		try {
			//更改客户昵称
			phoneRecordService.updateCustomerNickname(customerid, nickname);
			return new ResultBean();
		} catch (SQLExecutorException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: updateCustomerRemark
	 * @Description: 更改客户备注
	 * @param accesstoken
	 * @param customerid
	 * @param nickname
	 * @return
	 * @throws:
	 * @time: 2018年3月14日 下午2:12:12
	 */
	@LogOp(method = "updateCustomerRemark", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "更改客户备注")
	@ApiOperation(value = "更改客户备注", httpMethod = "POST", notes = "更改客户备注", response = ResultBean.class)
	@RequestMapping(value = "changeremark", method=RequestMethod.POST)
	public @ResponseBody ResultBean updateCustomerRemark(
			@ApiParam("accesstoken") @RequestParam(value = "accesstoken", required = true) String accesstoken,
			@ApiParam("客户id") @RequestParam(value = "customerid", required = true) String customerid,
			@ApiParam("新备注") @RequestParam(value = "remark", required = true) String remark) {
		try {
			//更改客户备注
			phoneRecordService.updateCustomerRemark(customerid, remark);
			return new ResultBean();
		} catch (SQLExecutorException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: updateCustomerRemark
	 * @Description: 更改客户类型
	 * @param accesstoken
	 * @param customerid
	 * @param nickname
	 * @return
	 * @throws:
	 * @time: 2018年3月14日 下午2:12:12
	 */
	@LogOp(method = "updateCustomerClass", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "更改客户类型")
	@ApiOperation(value = "更改客户类型", httpMethod = "POST", notes = "更改客户类型", response = ResultBean.class)
	@RequestMapping(value = "changeclass", method=RequestMethod.POST)
	public @ResponseBody ResultBean updateCustomerClass(
			@ApiParam("accesstoken") @RequestParam(value = "accesstoken", required = true) String accesstoken,
			@ApiParam("客户id") @RequestParam(value = "customerid", required = true) String customerid,
			@ApiParam("类型id") @RequestParam(value = "classid", required = true) String classid) {
		try {
			//更改客户类型
			phoneRecordService.updateCustomerClass(customerid, classid);
			return new ResultBean();
		} catch (SQLExecutorException e) {
			// TODO 记录错误日志
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: uploadCallVoice
	 * @Description: 新增通话记录
	 * @param accesstoken
	 * @param telno
	 * @param calltimes
	 * @param starttime
	 * @param endtime
	 * @return
	 * @throws:
	 * @time: 2018年3月14日 下午2:42:59
	 */
	@LogOp(method = "uploadCallVoice", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "新增通话记录")
	@ApiOperation(value = "新增通话记录", httpMethod = "POST", notes = "新增通话记录")
	@RequestMapping(value = "createcallvoice", method=RequestMethod.POST)
	public @ResponseBody ResultBean createCallVoice(
			@ApiParam("accesstoken") @RequestParam(value = "accesstoken", required = true) String accesstoken,
			@ApiParam("拨打的手机号码") @RequestParam(value = "telno", required = true) String telno, 
			@ApiParam("通话状态（0未接听，1已接通，未上传录音）") 
				@RequestParam(value = "status", required = true) Integer status, 
			@ApiParam("通话时长(秒)") @RequestParam(value = "calltimes", required = false) Integer calltimes, 
			@ApiParam("通话开始时间(格式：2018-02-10 12:23:02)") @RequestParam(value = "starttime", required = false) Timestamp starttime,
			@ApiParam("通话结束时间(格式：2018-02-10 12:23:02)") @RequestParam(value = "endtime", required = false) Timestamp endtime) {
		//数据检测
		if(telno == null || telno.trim().isEmpty()) {
			return new ResultBean("501", ResultMsg.C_501 + ":telno");
		}
		if(status == 1 && (calltimes == null || calltimes < 0)) {
			return new ResultBean("501", ResultMsg.C_501 + ":calltimes");
		}
		if(status == 1 && starttime == null) {
			return new ResultBean("501", ResultMsg.C_501 + ":starttime");
		}
		if(status == 1 && endtime == null) {
			return new ResultBean("501", ResultMsg.C_501 + ":endtime");
		}
		
		TokenModel token = redisTokenService.getToken(accesstoken);
		
		//检测手机号码是否为业务员维护的客户
		Map<String, String> param = new HashMap<>();
		param.put("telno", telno);
		param.put("salerid", token.getUserId());
		
		String customerid = null;
		try {
			customerid = phoneRecordMapper.checkCustomer(param);
			if(customerid == null) {
				//不是业务员下的客户
				return new ResultBean("004", ResultMsg.C_004);
			}
		} catch (SQLExecutorException e1) {
			return new ResultBean("500", ResultMsg.C_500);
		}
		
		//数据检测通过
		CallVoice call = new CallVoice();
		call.setId(UUIDUtil.getUUID());
		call.setSalerid(token.getUserId());
		call.setCustomerid(customerid);
		call.setTelno(telno);
		call.setStatus(status);
		call.setCalltimes(calltimes);
		call.setStarttime(starttime);
		call.setEndtime(endtime);
		
		try {
			phoneRecordService.addCallVoice(call);
			return new ResultBean(call.getId());
		} catch (SQLExecutorException e) {
			return new ResultBean("500", ResultMsg.C_500);
		}
	}
	
	/**
	 * @Title: uploadPhoneVoice
	 * @Description: 上传通话录音
	 * @param accesstoken
	 * @param callid
	 * @param callfile
	 * @param session
	 * @return
	 * @throws:
	 * @time: 2018年3月14日 下午3:48:28
	 */
	@LogOp(method = "updateCustomerRemark", logtype = ConstantIF.LOG_TYPE_1, userid = "", description = "上传通话录音")
	@ApiOperation(value = "上传通话录音", httpMethod = "POST", notes = "上传通话录音", response = ResultBean.class)
	@RequestMapping(value = "uploadvoice", method=RequestMethod.POST)
	public @ResponseBody ResultBean uploadPhoneVoice(
			@ApiParam("accesstoken") @RequestParam(value = "accesstoken", required = true) String accesstoken,
			@ApiParam("通话记录id") @RequestParam(value = "callid", required = true) String callid,
			@ApiParam("录音文件") @RequestParam(value = "callfile", required = true) MultipartFile callfile,
			HttpSession session) {
		
		//检测语音文件
		if(callfile == null || callfile.isEmpty()) {
			//无效的通话录音文件
			return new ResultBean("005", ResultMsg.C_005);
		}
		
		//检测通话记录状态是否为：1已接通，未上传录音
		try {
			Integer status = phoneRecordMapper.getCallVoiceStatusById(callid);
			if(status == null) {
				//非自己的语音记录
				return new ResultBean("009", ResultMsg.C_009);
			}else if(status == 0) {
				//0未接听
				return new ResultBean("007", ResultMsg.C_007);
			}else if(status == 2) {
				//2已接通，已上传录音
				return new ResultBean("008", ResultMsg.C_008);
			}
		} catch (SQLExecutorException e1) {
			return new ResultBean("500", ResultMsg.C_500);
		}
		
		
		try {
			//保存录音文件
			Map<String, String> voice = FileUploadUtils.uploadVoice(callfile);
			//更新通话记录状态 
			phoneRecordService.uploadPhoneVoice(callid, voice);
			return new ResultBean();
		} catch (IOException | SQLExecutorException e) {
			return new ResultBean("006", ResultMsg.C_006);
		}
	}
}
