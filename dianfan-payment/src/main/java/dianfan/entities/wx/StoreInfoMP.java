package dianfan.entities.wx;
/**
 * @ClassName StoreInfoMP
 * @Description 微信公众号门店场景信息
 * @author cjy
 * @date 2018年5月25日 上午10:00:53
 */
public class StoreInfoMP extends StoreInfo {
	private String id; //门店唯一标识 
	private String name; //门店名称 
	private String area_code; //门店行政区划码 
	private String address; //门店详细地址 
	
	StoreInfoMP(String id, String name, String area_code, String address){
		this.id = id;
		this.name = name;
		this.area_code = area_code;
		this.address = address;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getArea_code() {
		return area_code;
	}
	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "store_info [id=" + id + ", name=" + name + ", area_code=" + area_code + ", address=" + address + "]";
	}
}
