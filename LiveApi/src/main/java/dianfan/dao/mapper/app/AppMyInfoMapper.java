package dianfan.dao.mapper.app;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.Coupon;
import dianfan.entities.CourseList;
import dianfan.entities.CourseSutdy;
import dianfan.entities.FeedBack;
import dianfan.entities.UserInfo;
import dianfan.exception.SQLExecutorException;
/**
 * @ClassName AppMyInfoMapper
 * @Description 我的部分dao
 * @author cjy
 * @date 2018年3月29日 上午9:47:04
 */
@Repository
public interface AppMyInfoMapper {

	/**
	 * @Title: getUserInfo
	 * @Description: 根据id获取用户信息
	 * @param userId
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月29日 上午10:03:02
	 */
	@Select("select * from t_userinfo where userid=#{userId}")
	UserInfo getUserInfo(String userId) throws SQLExecutorException;

	/**
	 * @Title: myCouponList
	 * @Description: 我的代金券、体验券列表
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月29日 上午11:03:12
	 */
	List<Coupon> myCouponList(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: checkEvaluating
	 * @Description: 风险评测检测
	 * @param userId
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月29日 下午2:14:54
	 */
	@Select("select riskratingfalg from t_userinfo where userid=#{userId}")
	Integer checkEvaluating(String userId) throws SQLExecutorException;

	/**
	 * @Title: passEvaluating
	 * @Description: 通过风险评测检测
	 * @param userId
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月29日 下午5:26:48
	 */
	@Update("update t_userinfo set riskratingfalg=1 where userid=#{userId}")
	void passEvaluating(String userId) throws SQLExecutorException;

	/**
	 * @Title: saveFeedBack
	 * @Description: 保存用户反馈
	 * @param fb
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月30日 下午12:23:17
	 */
	@Insert("insert into t_userfeedback (id, userid, content, picurl, teloremail, createtime, entkbn) "
			+ "values (replace(uuid(), '-', ''), #{userid}, #{content}, #{picurl}, #{teloremail}, now(), 0)")
	void saveFeedBack(FeedBack fb) throws SQLExecutorException;

	/**
	 * @Title: changeAvator
	 * @Description: 修改头像
	 * @param data
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月30日 下午2:29:22
	 */
	@Update("update t_userinfo set iconurl=#{iconurl} where userid=#{userid}")
	void changeAvator(Map<String, String> data) throws SQLExecutorException;

	/**
	 * @Title: getMyCourseCount
	 * @Description: 根据条件获取我购买的课程总数
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月12日 下午3:58:43
	 */
	int getMyCourseCount(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: getMyCourseList
	 * @Description: 根据条件获取我购买的课程数据
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月12日 下午4:01:59
	 */
	List<CourseList> getMyCourseList(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: checkUserIsSaler
	 * @Description: 内部员工检测
	 * @param userid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月17日 上午11:29:38
	 */
	@Select("select count(*) from t_userinfo where telno=(select telno from t_userinfo where userid=#{userid}) and role <> 1 and entkbn=0")
	boolean checkUserIsSaler(String userid) throws SQLExecutorException;
	
	/**
	 * @Title: checkUserVip
	 * @Description: vip等级检测
	 * @param userid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月17日 上午11:18:20
	 */
	@Select("select count(*) from t_user_viplevel_time where userid=#{userid} and endtime >= now()")
	boolean checkUserVip(String userid) throws SQLExecutorException;

	/**
	 * @Title: getMyCourseStudyCount
	 * @Description: 获取我的课程学习总数量
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年6月6日 上午11:55:54
	 */
	int getMyCourseStudyCount(String userid) throws SQLExecutorException;

	/**
	 * @Title: getMyCourseStudyList
	 * @Description: 获取我的课程学习数据
	 * @param param
	 * @return
	 * @throws:
	 * @time: 2018年6月6日 上午11:56:47
	 */
	List<CourseSutdy> getMyCourseStudyList(Map<String, Object> param) throws SQLExecutorException;
}
