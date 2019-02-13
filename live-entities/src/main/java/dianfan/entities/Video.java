package dianfan.entities;

import java.sql.Timestamp;

/**
 * @ClassName Video
 * @Description 课程视频
 * @author cjy
 * @date 2018年1月19日 下午12:52:46
 */
public class Video {
	private String videoid; //varchar(50) NOT NULL COMMENT '视频id',
	private String vid; //varchar(50) NOT NULL COMMENT '网易云视频id',
	private String courseid; //varchar(50) DEFAULT NULL COMMENT '课程id',
	private String videoppicurl; //varchar(200) DEFAULT NULL COMMENT '视频展示图',
	private String videourl; //varchar(100) DEFAULT NULL COMMENT '视频链接地址【网易云直播】',
	private String videoname; //varchar(100) DEFAULT NULL COMMENT '视频名称',
	private String videointroduce; //varchar(100) DEFAULT NULL COMMENT '视频介绍',
	private String videocontent; //text COMMENT '视频内容',
	private Integer hits; //int(11) DEFAULT '0' COMMENT '视频播放量',
	private Integer videotype; //varchar(10) DEFAULT NULL COMMENT '视频类型【1:普通视频2:vip视频】',
	private String userid; //varchar(50) DEFAULT NULL COMMENT '上传者',
	private Integer enableflag; //varchar(10) DEFAULT NULL COMMENT '视频可用与否【1：可用2：不可用】',
	private String createtime; //datetime DEFAULT NULL COMMENT '创建时间',
	private String createid; //varchar(50) DEFAULT NULL COMMENT '创建者id',
	private Integer entkbn; //int(1) DEFAULT NULL COMMENT '数据有效区分（0：正常数据1:无效数据9：逻辑删除）',
	public String getVideoid() {
		return videoid;
	}
	public void setVideoid(String videoid) {
		this.videoid = videoid;
	}
	public String getVid() {
		return vid;
	}
	public void setVid(String vid) {
		this.vid = vid;
	}
	public String getCourseid() {
		return courseid;
	}
	public void setCourseid(String courseid) {
		this.courseid = courseid;
	}
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
	public String getVideoname() {
		return videoname;
	}
	public void setVideoname(String videoname) {
		this.videoname = videoname;
	}
	public String getVideointroduce() {
		return videointroduce;
	}
	public void setVideointroduce(String videointroduce) {
		this.videointroduce = videointroduce;
	}
	public String getVideocontent() {
		return videocontent;
	}
	public void setVideocontent(String videocontent) {
		this.videocontent = videocontent;
	}
	public Integer getHits() {
		return hits;
	}
	public void setHits(Integer hits) {
		this.hits = hits;
	}
	public Integer getVideotype() {
		return videotype;
	}
	public void setVideotype(Integer videotype) {
		this.videotype = videotype;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public Integer getEnableflag() {
		return enableflag;
	}
	public void setEnableflag(Integer enableflag) {
		this.enableflag = enableflag;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getCreateid() {
		return createid;
	}
	public void setCreateid(String createid) {
		this.createid = createid;
	}
	public Integer getEntkbn() {
		return entkbn;
	}
	public void setEntkbn(Integer entkbn) {
		this.entkbn = entkbn;
	}
	@Override
	public String toString() {
		return "Video [videoid=" + videoid + ", vid=" + vid + ", courseid=" + courseid + ", videoppicurl="
				+ videoppicurl + ", videourl=" + videourl + ", videoname=" + videoname + ", videointroduce="
				+ videointroduce + ", videocontent=" + videocontent + ", hits=" + hits + ", videotype=" + videotype
				+ ", userid=" + userid + ", enableflag=" + enableflag + ", createtime=" + createtime + ", createid="
				+ createid + ", entkbn=" + entkbn + "]";
	}
}