package dianfan.entities;
/**
 * @ClassName Banner
 * @Description Banner展示图
 * @author cjy
 * @date 2018年1月23日 下午2:20:05
 */
public class Banner {

	private String id; //目标id
	private String imgurl; //目标展示图url
	private String type; //目标类型
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "Banner [id=" + id + ", imgurl=" + imgurl + ", type=" + type + "]";
	}
	
}
