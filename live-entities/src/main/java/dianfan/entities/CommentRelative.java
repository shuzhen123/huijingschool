package dianfan.entities;
/**
 * @ClassName CommentRelative
 * @Description 评论关系
 * @author cjy
 * @date 2018年3月5日 上午10:33:54
 */
public class CommentRelative {
	private String id;
	private String parentid; //父评论id
	private String childid; //子评论id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public String getChildid() {
		return childid;
	}
	public void setChildid(String childid) {
		this.childid = childid;
	}
	@Override
	public String toString() {
		return "CommentRelative [id=" + id + ", parentid=" + parentid + ", childid=" + childid + "]";
	}
}
