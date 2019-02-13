package dianfan.entities.wx;
/**
 * @ClassName StoreInfoApp
 * @Description 微信App门店场景信息
 * @author cjy
 * @date 2018年5月25日 上午10:00:53
 */
public class StoreInfoApp extends StoreInfo {
	private String store_id; //门店唯一标识 
	private String store_name; //门店名称 
	
	StoreInfoApp(String store_id, String store_name){
		this.store_id = store_id;
		this.store_name = store_name;
	}

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	@Override
	public String toString() {
		return "StoreInfoApp [store_id=" + store_id + ", store_name=" + store_name + "]";
	}
}
