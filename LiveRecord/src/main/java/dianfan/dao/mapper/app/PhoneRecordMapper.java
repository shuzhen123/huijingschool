package dianfan.dao.mapper.app;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.BashMap;
import dianfan.entities.CallVoice;
import dianfan.entities.Customer;
import dianfan.entities.PhoneRecordInfo;
import dianfan.exception.SQLExecutorException;
@Repository
public interface PhoneRecordMapper {

	/**
	 * @Title: findCustomerClassify
	 * @Description: 获取客户分类列表
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月14日 上午10:24:16
	 */
	@Select("select id, name from t_csclassify")
	List<BashMap> findCustomerClassify() throws SQLExecutorException;

	/**
	 * @Title: findPhoneRecordCount
	 * @Description: 根据业务员id获取业务员通话记录总条数
	 * @param salerid
	 * @return
	 * @throws:
	 * @time: 2018年3月14日 上午11:22:26
	 */
	Integer findPhoneRecordCount(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: findPhoneRecord
	 * @Description: 根据条件获取业务员通话记录列表
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月14日 上午11:28:33
	 */
	List<PhoneRecordInfo> findPhoneRecord(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: getCustomerInfo
	 * @Description: 获取客户详情
	 * @param customerid
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月14日 下午1:57:23
	 */
	@Select("select info.nickname, info.telno, rel.allcontent remark from t_userinfo info "
			+ "left join t_csclassify_customer_related rel on info.userid=rel.customerid where info.userid=#{customerid}")
	Customer getCustomerInfo(String customerid) throws SQLExecutorException;

	/**
	 * @Title: updateCustomerNickname
	 * @Description: 更改客户昵称
	 * @param param
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月14日 下午2:10:24
	 */
	@Update("update t_userinfo set nickname=#{nickname} where userid=#{customerid}")
	void updateCustomerNickname(Map<String, String> param) throws SQLExecutorException;

	/**
	 * @Title: updateCustomerRemark
	 * @Description: 更改客户备注
	 * @param param
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月14日 下午2:13:39
	 */
	@Update("update t_csclassify_customer_related set allcontent=#{remark} where customerid=#{customerid}")
	void updateCustomerRemark(Map<String, String> param) throws SQLExecutorException;
	
	/**
	 * @Title: updateCustomerClass
	 * @Description: 更改客户类型
	 * @param customerid
	 * @param classid
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年5月9日 上午11:53:53
	 */
	@Update("update t_csclassify_customer_related set csclassifyid=#{classid} where customerid=#{customerid}")
	void updateCustomerClass(@Param(value="customerid") String customerid, @Param(value="classid") String classid) throws SQLExecutorException;

	/**
	 * @Title: checkCustomer
	 * @Description: 检测手机号码是否为业务员维护的客户
	 * @param param
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月14日 下午2:48:57
	 */
	@Select("select userid from t_customer where userid=(" + 
			"	select userid from t_userinfo where telno=#{telno} and role=1 and entkbn!=9" + 
			") and dtsmuserid=#{salerid} and entkbn=0")
	String checkCustomer(Map<String, String> param) throws SQLExecutorException;

	/**
	 * @Title: getNickNameById
	 * @Description: 根据客户id获取客户昵称
	 * @param customerid
	 * @return
	 * @throws:
	 * @time: 2018年3月14日 下午3:06:23
	 */
	@Select("select nickname from t_userinfo where userid = #{customerid}")
	String getNickNameById(String customerid) throws SQLExecutorException;

	/**
	 * @Title: addCallVoice
	 * @Description: 新增通话记录
	 * @param call
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月14日 下午3:08:33
	 */
	void addCallVoice(CallVoice call) throws SQLExecutorException;

	/**
	 * @Title: getCallVoiceStatusById
	 * @Description: 根据通话记录id获取记录状态
	 * @param callid
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月14日 下午3:52:10
	 */
	@Select("select status from t_salecustomer_tellog where id=#{callid}")
	Integer getCallVoiceStatusById(String callid) throws SQLExecutorException;

	/**
	 * @Title: updateCallVoice
	 * @Description: 上传通话录音
	 * @param param
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月14日 下午3:57:46
	 */
	@Update("update t_salecustomer_tellog set status=2, voicepath=#{voicepath} where id=#{callid}")
	void updateCallVoice(Map<String, String> param) throws SQLExecutorException;

	/**
	 * @Title: checkCollect
	 * @Description: 检测是否已收藏过
	 * @param param
	 * @throws:
	 * @time: 2018年3月20日 下午2:15:17
	 */
	@Select("select count(*) from t_collect where salerid=#{salerid} and userid = #{customerid}")
	Integer checkCollect(Map<String, String> param) throws SQLExecutorException;
	
	/**
	 * @Title: collectAction
	 * @Description: 收藏动作
	 * @param param
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月20日 下午2:17:37
	 */
	@Insert("insert into t_collect (id, salerid, userid) values (replace(uuid(),'-',''), #{salerid}, #{customerid})")
	void collectAction(Map<String, String> param) throws SQLExecutorException;

	/**
	 * @Title: uncollectAction
	 * @Description: 取消收藏
	 * @param param
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月20日 下午2:19:09
	 */
	@Delete("delete from t_collect where salerid=#{salerid} and userid = #{customerid}")
	void uncollectAction(Map<String, String> param) throws SQLExecutorException;

	




}
