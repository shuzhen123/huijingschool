package dianfan.entities;

import java.sql.Timestamp;

/**
 * @ClassName ChannelBean
 * @Description 网易云频道
 * @author cjy
 * @date 2018年4月24日 上午11:42:36
 */
public class ChannelBean {
	private String id; //varchar(50) NOT NULL COMMENT 'id',
	private String accid; //varchar(50) NOT NULL COMMENT '讲师id（网易云id）',
	private String token; //varchar(50) NOT NULL COMMENT '网易云token',
	private String cid; //varchar(50) NOT NULL COMMENT '频道id',
	private String channelname; //varchar(100) NOT NULL COMMENT '频道名称',
	private String roomid; //varchar(50) NOT NULL COMMENT '房间号',
	private String chatroomname; //varchar(50) NOT NULL COMMENT '聊天室名称',
	private String pushUrl; //varchar(500) NOT NULL COMMENT '推流地址',
	private String httpPullUrl; //varchar(500) NOT NULL COMMENT 'http拉流地址',
	private String hlsPullUrl; //varchar(500) NOT NULL COMMENT 'hls拉流地址',
	private String rtmpPullUrl; //varchar(500) NOT NULL COMMENT 'rtmp拉流地址',
	private Timestamp ctime; //datetime NOT NULL COMMENT '创建时间戳',
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAccid() {
		return accid;
	}
	public void setAccid(String accid) {
		this.accid = accid;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getChannelname() {
		return channelname;
	}
	public void setChannelname(String channelname) {
		this.channelname = channelname;
	}
	public String getRoomid() {
		return roomid;
	}
	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}
	public String getChatroomname() {
		return chatroomname;
	}
	public void setChatroomname(String chatroomname) {
		this.chatroomname = chatroomname;
	}
	public String getPushUrl() {
		return pushUrl;
	}
	public void setPushUrl(String pushUrl) {
		this.pushUrl = pushUrl;
	}
	public String getHttpPullUrl() {
		return httpPullUrl;
	}
	public void setHttpPullUrl(String httpPullUrl) {
		this.httpPullUrl = httpPullUrl;
	}
	public String getHlsPullUrl() {
		return hlsPullUrl;
	}
	public void setHlsPullUrl(String hlsPullUrl) {
		this.hlsPullUrl = hlsPullUrl;
	}
	public String getRtmpPullUrl() {
		return rtmpPullUrl;
	}
	public void setRtmpPullUrl(String rtmpPullUrl) {
		this.rtmpPullUrl = rtmpPullUrl;
	}
	public Timestamp getCtime() {
		return ctime;
	}
	public void setCtime(Timestamp ctime) {
		this.ctime = ctime;
	}
	@Override
	public String toString() {
		return "ChannelBean [id=" + id + ", accid=" + accid + ", token=" + token + ", cid=" + cid + ", channelname="
				+ channelname + ", roomid=" + roomid + ", chatroomname=" + chatroomname + ", pushUrl=" + pushUrl
				+ ", httpPullUrl=" + httpPullUrl + ", hlsPullUrl=" + hlsPullUrl + ", rtmpPullUrl=" + rtmpPullUrl
				+ ", ctime=" + ctime + "]";
	}
}

