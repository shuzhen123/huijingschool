package dianfan.entities;
/**
 * @ClassName CourseCondition
 * @Description 课程筛选条件
 * @author cjy
 * @date 2017年12月26日 上午10:47:19
 */
public class CourseCondition {

	private String userid; //当前用户id
	
	private String coursetype; //课程类型
	private String peopleunm; //人数排序
	private String evaluate; //评价排序
	
	private String teacherid; //按名师筛选
	private String type; //按类型筛选
	private String price; //按价格区间筛选
	private String hprice; //高价
	private String lprice; //低价
	
	private Integer page; //请求页
	private Integer offset; //请求页偏移量
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getCoursetype() {
		return coursetype;
	}
	public void setCoursetype(String coursetype) {
		this.coursetype = coursetype;
	}
	public String getPeopleunm() {
		return peopleunm;
	}
	public void setPeopleunm(String peopleunm) {
		this.peopleunm = peopleunm;
	}
	public String getEvaluate() {
		return evaluate;
	}
	public void setEvaluate(String evaluate) {
		this.evaluate = evaluate;
	}
	public String getTeacherid() {
		return teacherid;
	}
	public void setTeacherid(String teacherid) {
		this.teacherid = teacherid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getHprice() {
		return hprice;
	}
	public void setHprice(String hprice) {
		this.hprice = hprice;
	}
	public String getLprice() {
		return lprice;
	}
	public void setLprice(String lprice) {
		this.lprice = lprice;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getOffset() {
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	@Override
	public String toString() {
		return "CourseCondition [userid=" + userid + ", coursetype=" + coursetype + ", peopleunm=" + peopleunm
				+ ", evaluate=" + evaluate + ", teacherid=" + teacherid + ", type=" + type + ", price=" + price
				+ ", hprice=" + hprice + ", lprice=" + lprice + ", page=" + page + ", offset=" + offset + "]";
	}
}
