package dianfan.constant;

public interface ConstantIF {
	/** 设置Token过期时间 */
	public final static long TOKEN_EXPIRES_HOUR = 2160;
	/** pc_session_key */
	public final static String PC_SESSION_KEY = "pc_session_key";
	/** pc_roel个人权限 */
	public final static String PC_ROLE = "pc_role";
	/** 所有权限 */
	public final static String PC_ROLE_ALL = "pc_role_all";
	/** pc_agent_role代理商个人权限 */
	public final static String PC_AGENT_ROLE = "pc_agent_role";
	/** 代理商所有权限 */
	public final static String PC_AGENT_ROLE_ALL = "pc_agent_role_all";
	/** log_type 1:app接口 2:后台pc */
	public final static String LOG_TYPE_1 = "1";
	/** log_type 1:app接口 2:后台pc */
	public final static String LOG_TYPE_2 = "2";
	/** log_type 3:和讯人员登录专用 */
	public final static String LOG_TYPE_3 = "3";
	/** 后台系统账号key */
	public final static String USERNAME_KEY = "usernamekey";
	/** 默认任务数key */
	public final static String DEF_TASK_KEY = "salerTaskNum";
	/** 默认代理商课程分润key */
	public final static String DEF_AGENT_PROFIT_KEY = "agentprofit";
	/** 默认业务员课程分润key */
	public final static String DEF_SALER_PROFIT_KEY = "salerprofit";
	/** vip策略审核key */
	public final static String DEF_VIP_NEWS_KEY = "vipnewscheck";
	/** 课程购买时长 */
	public final static String COURSE_BY_TIME = "course_by_time";

	/** 分页偏移量 */
	public final static Integer PAGE_OFFSET = 2;

	/** 图片验证码key */
	public final static String VERFY_CODE_KEY = "verCode";
	/** pc端cookie account */
	public final static String COOKIE_NAME = "liveaccount";
	/** pc端cookie password */
	public final static String COOKIE_PWD = "livepwd";
	
	/** 微信access_token */
	public final static String WX_ACCESS_TOKEN = "wx_access_token";
	/** 微信jsapi_ticket */
	public final static String WX_JSAPI_TICKET = "wx_jsapi_ticket";
	/** 微信token缓存时间（s） */
	public final static long WX_CACHE_TIME = 7150L;

	/** 监督电话 */
	public final static String SUPERVISION_PHONE = "supervision_phone";
	/** 联系我们电话 */
	public final static String CONTACT_PHONE = "contact_phone";

	/** 项目名称 */
	public final static String PROJECT = "http://www.huijingschool.com";
	public final static String DEFAULT_ICON = "/upload/img/default.jpg";

	/** pc模块管理 */
	public final static String ERROR_500 = "500";// 500错误

	/** pc模块管理员后台 */
	public final static String ADMIN_PATH = "admin/";// 用户管理根路径
	public final static String ADMIN_MEMBER = ADMIN_PATH + "member/";// 用户管理
	public final static String ADMIN_LIVE = ADMIN_PATH + "livestream/"; // 直播管理
	public final static String ADMIN_COURSE = ADMIN_PATH + "course/"; // 课程管理
	public final static String ADMIN_COUPON = ADMIN_PATH + "coupons/"; // 卡券管理
	public final static String ADMIN_GOODS = ADMIN_PATH + "goods/"; // 礼物管理
	public final static String ADMIN_FEEDBACK = ADMIN_PATH + "feedback/"; // 用户反馈管理
	public final static String ADMIN_NEWS = ADMIN_PATH + "news/"; // 咨讯管理
	public final static String ADMIN_EVALUATE = ADMIN_PATH + "evaluate/"; // 风险测评管理
	public final static String ADMIN_AGENT = ADMIN_PATH + "agent/"; // 代理商管理
	public final static String ADMIN_SENSITIVE = ADMIN_PATH + "sensitive/"; // 敏感文字管理
	public final static String ADMIN_STATISTICS = ADMIN_PATH + "statistics/"; // 统计管理
	public final static String ADMIN_BANNER = ADMIN_PATH + "banner/"; // 轮播图管理
	public final static String ADMIN_BASEDATA = ADMIN_PATH + "basedata/"; // 基础数据管理
	public final static String ADMIN_ROLE = ADMIN_PATH + "role/"; // 权限管理
	public final static String ADMIN_ADMIN = ADMIN_PATH + ADMIN_PATH + ""; // 后台管理员管理
	public final static String ADMIN_SHARES = ADMIN_PATH + "shares/"; // 诊股管理
	public final static String ADMIN_VIP_LEVEL = ADMIN_PATH + "viplevel/"; // vip等级管理
	/** pc模块代理商后台 */
	public final static String AGENT_PATH = "agent/"; // 代理商管理根路径
	public final static String AGENT_CUSTOMER = AGENT_PATH + "customer/"; // 代理商客户管理
	public final static String AGENT_COURSE = AGENT_PATH + "course/"; // 代理商课程管理
	public final static String AGENT_TEA = AGENT_PATH + "teacher/"; // 教师管理
	public final static String AGENT_VIPNEWS = AGENT_PATH + "vipnews/"; // vip咨讯管理
	public final static String AGENT_POPU = AGENT_PATH + "popu/"; // 推广管理
	public final static String AGENT_CHAT = AGENT_PATH + "chat/"; // 通讯管理
	public final static String AGENT_SALER = AGENT_PATH + "saler/"; // 人事管理
	public final static String AGENT_STA = AGENT_PATH + "statistics/"; // 报表管理
	/** pc模块业务员后台 */
	public final static String SALER_PATH = "saler/"; // 业务员管理根路径
	public final static String SALER_CUSTOMER = SALER_PATH + "customer/"; // 业务员客户管理
	public final static String SALER_CHAT = SALER_PATH + "chat/"; // 业务员通话管理
	/** pc模块老师后台 */
	public final static String TEA_PATH = "tea/"; // 老师管理根路径
	public final static String TEA_COURSE = TEA_PATH + "course/"; // 老师课程管理
	public final static String TEA_VIPNEWS = TEA_PATH + "vipnews/"; // vip咨讯管理
	/** pc模块和讯后台 */
	public final static String HX_PATH = "hexun/"; // 和讯管理根路径
	public final static String HX_CHAT = HX_PATH + "chat/"; // 和讯通话管理

	/** datables 关键key */
	public final static String DT_DRAW = "draw";
	public final static String DT_SEARCH = "search[value]";
	public final static String DT_START = "start";
	public final static String DT_LENGTH = "length";
	
	/** 课程缓存key */
	public final static String USER_COURSE_CACHE_SUFFIX = "_course_suffix";
	/** vip等级缓存key */
	public final static String USER_VIP_LEVEL_SUFFIX = "_vip_level";

	/** agentid */
	public final static String AGENT_ID = "e63d9327f1f211e782df52540054a904";
	
	/** 评测分数线key */
	public final static String EVALUATE_SCORE = "evaluate_score";
}
