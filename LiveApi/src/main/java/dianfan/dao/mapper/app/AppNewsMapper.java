package dianfan.dao.mapper.app;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.BashMap;
import dianfan.entities.CommentRelative;
import dianfan.entities.Infomation;
import dianfan.entities.NewsComment;
import dianfan.entities.NewsList;
import dianfan.entities.VipInfomation;
import dianfan.exception.SQLExecutorException;
/**
 * @ClassName AppNewsMapper
 * @Description 咨讯相关dao
 * @author cjy
 * @date 2018年1月24日 上午10:46:04
 */
@Repository
public interface AppNewsMapper {

	/**
	 * @Title: findNewsLimit
	 * @Description: 首页咨讯列表
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年1月24日 上午10:52:02
	 */
	@Select("select id, infomationtitle, picurl, readcounts, createtime from t_infomation where entkbn = 0 order by createtime desc limit 0, 5")
	List<NewsList> findNewsLimit() throws SQLExecutorException;
	
	/**
	 * @Title: findNewsModelList
	 * @Description: 获取咨讯类型列表
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月1日 上午11:02:42
	 */
	@Select("select informationid id, newskindname name from t_informationmodule where entkbn = 0")
	List<BashMap> findNewsModelList() throws SQLExecutorException;

	/**
	 * @Title: getCountNewsListByInfoid
	 * @Description: 根据咨讯模块id获取咨讯总条数
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月1日 上午11:48:57
	 */
	@Select("select count(*) from t_infomation where informationmodelid=#{infoid} and entkbn = 0")
	int getCountNewsListByInfoid(Map<String, Object> param) throws SQLExecutorException;
	
	/**
	 * @Title: findNewsListByInfoid
	 * @Description: 根据咨讯模块id获取咨讯列表
	 * @param param
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月1日 上午11:09:00
	 */
	List<Infomation> findNewsListByInfoid(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: findNewsDetail
	 * @Description: 根据咨讯id获取咨讯详情
	 * @param infoid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月1日 上午11:59:38
	 */
	@Select("select * from t_infomation where id = #{infoid}")
	Infomation findNewsDetail(String infoid) throws SQLExecutorException;
	
	/**
	 * @Title: newsReadCountInc
	 * @Description: 阅读量+1
	 * @param infoid
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月7日 下午4:38:32
	 */
	@Update("update t_infomation set readcounts = readcounts + 1 where id = #{infoid}")
	void newsReadCountInc(String infoid) throws SQLExecutorException;
	
	/**
	 * @Title: newsReadCountInc
	 * @Description: vip 咨询阅读 阅读量+1
	 * @param infoid
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月7日 下午4:38:32
	 */
	@Update("update t_vipstrategy set readcount = readcount + 1 where articalid = #{infoid}")
	void newsVipReadCountInc(String infoid) throws SQLExecutorException;
	
	/**
	 * @Title: getNewsCommentCountByInfoid
	 * @Description: 根据咨讯id获取评论总条数
	 * @param infoid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月1日 下午1:26:40
	 */
	@Select("select count(*) from t_comments comm, t_userinfo info " +
			"where comm.commentuserid=info.userid and comm.infomationid=#{infoid} and comm.entkbn=0")
	int getNewsCommentCountByInfoid(String infoid) throws SQLExecutorException;
	
	/**
	 * @Title: findNewsCommentByInfoid
	 * @Description: 根据咨讯id获取评论列表
	 * @param infoid
	 * @throws:
	 * @time: 2018年3月1日 下午1:06:31
	 */
	List<NewsComment> findNewsCommentByInfoid(Map<String, Object> param) throws SQLExecutorException;
	
	/* ***************************vip咨讯************************** */
	/**
	 * @Title: getVipNewsCount
	 * @Description: 根据条件获取vip咨讯数量
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月17日 上午10:52:40
	 */
	int getVipNewsCount(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: findVipNews
	 * @Description: 获取vip策略列表
	 * @param param
	 * @return
	 * @throws:
	 * @time: 2018年3月2日 上午10:09:16
	 */
	List<VipInfomation> findVipNews(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: findVipNewsDetail
	 * @Description: 获取vip咨讯详情
	 * @param infoid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月2日 上午10:37:54
	 */
	@Select("select vf.articalid, vf.userid, info.realname, vf.tdays, vf.articaltitle, vf.content, vf.readcount from t_vipstrategy vf, t_userinfo info where vf.userid = info.userid and vf.articalid=#{infoid}")
	VipInfomation findVipNewsDetail(String infoid) throws SQLExecutorException;

	/**
	 * @Title: createNewsComment
	 * @Description: 创建评论
	 * @param comm
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月5日 上午10:25:37
	 */
	@Insert("insert into t_comments (commentid, infomationid, commentuserid, commentcontent, createtime, entkbn) values"
			+ "(#{commentid}, #{infomationid}, #{commentuserid}, #{commentcontent}, now(), 0)")
	void createNewsComment(NewsComment comm) throws SQLExecutorException;

	/**
	 * @Title: createCommentRelative
	 * @Description: 创建评论关系
	 * @param cr
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月5日 上午10:52:54
	 */
	@Insert("insert into t_commentrelative (id, parentid, childid) values (#{id}, #{parentid}, #{childid})")
	void createCommentRelative(CommentRelative cr) throws SQLExecutorException;

	/**
	 * @Title: findCommentRelation
	 * @Description: 根据评论id获取评论关系
	 * @param commids
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月5日 上午11:52:49
	 */
	List<CommentRelative> findCommentRelation(List<String> commids) throws SQLExecutorException;

}
