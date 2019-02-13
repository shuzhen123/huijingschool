package dianfan.entities;

/**
 * @ClassName TokenModel
 * @Description Token的Model类，可以增加字段提高安全性，例如时间戳、url签名
 * @author cjy
 * @date 2017年12月20日 下午4:38:55
 */
public class TokenModel {

	// 用户id
	private String userId;
	// 随机生成的uuid
	private String token;

	public TokenModel(String userId, String token) {
		this.userId = userId;
		this.token = token;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "TokenModel [userId=" + userId + ", token=" + token + "]";
	}
	
	

}
