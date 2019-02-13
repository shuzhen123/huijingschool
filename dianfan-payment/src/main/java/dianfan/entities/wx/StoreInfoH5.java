package dianfan.entities.wx;
/**
 * @ClassName StoreInfoH5
 * @Description 微信H5门店场景信息
 * @author cjy
 * @date 2018年5月25日 上午10:00:53
 */
public class StoreInfoH5 extends StoreInfo{
	private String type; //场景类型 
	private String wap_url; //WAP网站URL地址
	private String wap_name; //WAP 网站名
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getWap_url() {
		return wap_url;
	}
	public void setWap_url(String wap_url) {
		this.wap_url = wap_url;
	}
	public String getWap_name() {
		return wap_name;
	}
	public void setWap_name(String wap_name) {
		this.wap_name = wap_name;
	}
	@Override
	public String toString() {
		return "StoreInfoH5 [type=" + type + ", wap_url=" + wap_url + ", wap_name=" + wap_name + "]";
	}
}
