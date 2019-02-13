package dianfan.dao.mapper.app;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.Course;
import dianfan.entities.Video;
import dianfan.exception.SQLExecutorException;

/**
 * @ClassName VCloudNotifyMapper
 * @Description 视频直播回调dao
 * @author cjy
 * @date 2018年5月3日 上午9:45:17
 */
@Repository
public interface VCloudNotifyMapper {

	/**
	 * @Title: getTeacherIdByCid
	 * @Description: 根据频道id获取讲师id
	 * @param cid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月3日 上午10:04:17
	 */
	@Select("select accid from t_channel where cid=#{cid}")
	String getTeacherIdByCid(String cid) throws SQLExecutorException;

	/**
	 * @Title: findLiveCourseByTeacherid
	 * @Description: 根据讲师id获取讲师正在直播的课程
	 * @param teacherid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月3日 上午10:30:31
	 */
	@Select("select * from t_course where userid=#{teacherid} and (coursekind=1 or coursekind=2) and auth=2 and flag=1 and liveflag=1 and entkbn=0")
	Course findLiveCourseByTeacherid(String teacherid) throws SQLExecutorException;

	/**
	 * @Title: updateLiveCourseInfo
	 * @Description: 更改直播课程状态信息
	 * @param courseid
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月3日 上午10:37:03
	 */
	@Update("update t_course set liveflag=2 where courseid=#{courseid}")
	void updateLiveCourseInfo(String courseid) throws SQLExecutorException;

	/**
	 * @Title: insertLiveCourseVideo
	 * @Description: 插入直播课程视频
	 * @param v
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月3日 上午10:45:31
	 */
	@Insert("insert into t_videos (videoid, courseid, videoppicurl, videourl, videoname, videointroduce, hits, videotype, enableflag, userid, createtime, entkbn) values "
			+ "(replace(uuid(),'-',''), #{courseid}, #{videoppicurl}, #{videourl}, #{videoname}, #{videointroduce}, 0, #{videotype}, 1, #{userid}, now(), 0)")
	void insertLiveCourseVideo(Video v) throws SQLExecutorException;

	/**
	 * @Title: getVideoInfoByVideoName
	 * @Description: 根据视频名称查找视频信息
	 * @param videoname
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月16日 下午5:11:32
	 */
	@Select("select * from t_videos where videourl=#{videoname} and entkbn=1")
	Video getVideoInfoByVideoName(String videoname) throws SQLExecutorException;

	/**
	 * @Title: updateCourseVideoInfo
	 * @Description: 更新视频信息
	 * @param videoInfo
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月16日 下午5:16:56
	 */
	@Update("update t_videos set vid=#{vid}, videourl=#{videourl}, entkbn=#{entkbn} where videoid=#{videoid}")
	void updateCourseVideoInfo(Video videoInfo) throws SQLExecutorException;

	/**
	 * @Title: getVideoInfoByVideoVid
	 * @Description: 根据视频vid查找视频信息
	 * @param vid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月16日 下午5:37:29
	 */
	@Select("select * from t_videos where vid=#{vid}")
	Video getVideoInfoByVideoVid(String vid) throws SQLExecutorException;

}
