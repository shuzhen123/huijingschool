package dianfan.constant;

/**
 * @ClassName ConstantIF
 * @Description 常量
 * @author cjy
 * @date 2018年4月16日 下午5:05:24
 */
public interface ConstantIF {
	/** 设置Token过期时间 */
	public final static long TOKEN_EXPIRES_HOUR = 36000L;
	/** accesstoken */
	public final static String ACCESSTOKEN = "accesstoken";
	/** pc_session_key */
	public final static String PC_SESSION_KEY = "pc_session_key";
	/** pc_roel */
	public final static String PC_ROLE = "pc_role";
	/** log_type 1:app接口 2:后台pc */
	public final static String LOG_TYPE_1 = "1";
	/** log_type 1:app接口 2:后台pc */
	public final static String LOG_TYPE_2 = "2";
	/** 默认代理商课程分润key */
	public final static String DEF_AGENT_PROFIT_KEY = "agentprofit";
	/** 默认业务员课程分润key */
	public final static String DEF_SALER_PROFIT_KEY = "salerprofit";
	/** 课程购买时长 */
	public final static String COURSE_BY_TIME = "course_by_time";

	/** 分页偏移量 */
	public final static Integer PAGE_OFFSET = 10;

	/** 资源域名 */
	public final static String PROJECT = "http://www.huijingschool.com";
	public final static String DEFAULT_ICON = "/upload/img/default.jpg";

	/** 支付类型-支付宝 */
	public final static String ALIPAY = "alipay";
	/** 支付类型-微信 */
	public final static String WXPAY = "wxpay";
	/** 支付类型-银联 */
	public final static String UPOP = "upop";

	/** 支付截止时长（分钟） */
	public final static int PAY_TIME_OUT = 30;

	/** 监督电话 */
	public final static String SUPERVISION_PHONE = "supervision_phone";
	/** 联系我们电话 */
	public final static String CONTACT_PHONE = "contact_phone";

	/** 微信access_token */
	public final static String WX_ACCESS_TOKEN = "wx_access_token";
	/** 微信jsapi_ticket */
	public final static String WX_JSAPI_TICKET = "wx_jsapi_ticket";
	/** 微信token缓存时间（s） */
	public final static long WX_CACHE_TIME = 7150L;

	/** 课程缓存key */
	public final static String USER_COURSE_CACHE_SUFFIX = "_course_suffix";
	/** vip等级缓存key */
	public final static String USER_VIP_LEVEL_SUFFIX = "_vip_level";
	
	/** 新用户注册代金券id */
	public final static String REG_COUPON_ID = "bce8999a491e11e8aeb500163e0028c4";
	/** 新用户注册体验券id */
	public final static String REG_EXP_ID = "b38d5bf9492c11e8aeb500163e0028c4";
	/** agentid */
	public final static String AGENT_ID = "e63d9327f1f211e782df52540054a904";
	
	/** 评测分数线key */
	public final static String EVALUATE_SCORE = "evaluate_score";
	
	/** 视频转码模板Id */
	public final static String TRANSCODE_TPL_ID = "104254630";

}
