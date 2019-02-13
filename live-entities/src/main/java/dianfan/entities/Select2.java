package dianfan.entities;
/**
 * @ClassName Select2
 * @Description select2 返回值 
 * @author cjy
 * @date 2018年3月13日 下午5:43:11
 */
public class Select2 {
	private String id;
	private String text;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	@Override
	public String toString() {
		return "Select2 [id=" + id + ", text=" + text + "]";
	}
}
