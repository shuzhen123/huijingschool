package dianfan.constant;

public interface ConstantIF {
	/** 设置Token过期时间 */
	public final static long TOKEN_EXPIRES_HOUR = 36000;
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
	
	/** 图片验证码key */
	public final static String VERFY_CODE_KEY = "verCode";
	
	/** Root */
	public final static String ROOTPATH = "/webapps/ROOT";
	
	/** 分页偏移量 */
	public final static Integer PAGE_OFFSET = 1000;
}
