package dianfan.entities;
/**
 * @ClassName CourseDirectory
 * @Description 课程视频目录
 * @author cjy
 * @date 2017年12月25日 上午9:33:54
 */
public class CourseDirectory {
	/*t_videos*/
	private String courseid; //课程id
	private String videoid; //视频id
	private String videoname; //视频名称
	private Integer hits; //视频播放量
	private String videoppicurl; //视频展示图
	private String videourl; //视频链接地址
	
	public String getVideoppicurl() {
		return videoppicurl;
	}
	public void setVideoppicurl(String videoppicurl) {
		this.videoppicurl = videoppicurl;
	}
	public String getVideourl() {
		return videourl;
	}
	public void setVideourl(String videourl) {
		this.videourl = videourl;
	}
	public String getCourseid() {
		return courseid;
	}
	public void setCourseid(String courseid) {
		this.courseid = courseid;
	}
	public String getVideoid() {
		return videoid;
	}
	public void setVideoid(String videoid) {
		this.videoid = videoid;
	}
	public String getVideoname() {
		return videoname;
	}
	public void setVideoname(String videoname) {
		this.videoname = videoname;
	}
	public Integer getHits() {
		return hits;
	}
	public void setHits(Integer hits) {
		this.hits = hits;
	}
	@Override
	public String toString() {
		return "CourseDirectory [courseid=" + courseid + ", videoid=" + videoid + ", videoname=" + videoname + ", hits="
				+ hits + ", videoppicurl=" + videoppicurl + ", videourl=" + videourl + "]";
	}
}
