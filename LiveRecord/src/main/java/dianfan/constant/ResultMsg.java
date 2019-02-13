package dianfan.constant;

public interface ResultMsg {
	public static final String C_200 = "OK";

	public static final String C_500 = "服务器忙";
	public static final String C_501 = "参数错误";

	public static final String C_001 = "用户名或密码不正确";
	public static final String C_002 = "您的账号已被封";
	public static final String C_003 = "验证码不正确";
	public static final String C_004 = "当前联系的客户非自己维护的客户，无法添加通话记录";
	public static final String C_005 = "无效的通话录音文件";
	public static final String C_006 = "通话录音上传失败，请稍后重试";
	public static final String C_007 = "当前通话未接听，不能上传录音";
	public static final String C_008 = "您已上传过通话录音，无须重复上传";
	public static final String C_009 = "通话记录非自己所有，无法上传通话录音";
	
}
