package dianfan.entities;
/**
 * @ClassName BashMap
 * @Description 基本key-val对（对应查询数据库多条单字段数据）
 * @author cjy
 * @date 2017年12月26日 上午9:23:44
 */
public class BashMap {

	private String id;
	private String name;
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
	@Override
	public String toString() {
		return "BashMap [id=" + id + ", name=" + name + "]";
	}
}
