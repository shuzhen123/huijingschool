package dianfan.dao.mapper.app;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import dianfan.entities.CartCourse;
import dianfan.exception.SQLExecutorException;
/**
 * @ClassName AppShopCartMapper
 * @Description 我的购物车相关dao
 * @author cjy
 * @date 2018年4月9日 下午1:22:00
 */
@Repository
public interface AppShopCartMapper {

	/**
	 * @Title: checkAddCart
	 * @Description: 检测课程是否已添加到购物车
	 * @param cc
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月9日 下午2:14:15
	 */
	@Select("select count(*) from t_shopping_cart where userid=#{userid} and courseid=#{courseid} and entkbn=0")
	Boolean checkAddCart(CartCourse cc) throws SQLExecutorException;
	
	/**
	 * @Title: addMyCart
	 * @Description: 课程添加到购物车
	 * @param data
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月9日 下午1:24:30
	 */
	@Insert("insert into t_shopping_cart (id, userid, courseid, createtime, entkbn) values "
			+ "(replace(uuid(),'-',''), #{userid}, #{courseid}, now(), 0)")
	void addMyCart(CartCourse cart) throws SQLExecutorException;

	/**
	 * @Title: getMyCartCount
	 * @Description: 获取我的购物车中课程总数量
	 * @param userid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月9日 下午2:03:41
	 */
	@Select("select count(*) from t_shopping_cart where userid=#{userid} and entkbn=0")
	int getMyCartCount(String userid) throws SQLExecutorException;

	/**
	 * @Title: findMyCart
	 * @Description: 获取我的购物车中课程列表
	 * @param param
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月9日 下午2:06:46
	 */
	@Select("select cart.courseid, cour.coursename, cour.coursepic, cour.coursemoney from t_shopping_cart cart, t_course cour " + 
			"where cart.courseid=cour.courseid and cart.userid=#{userid} and cart.entkbn=0 order by cart.createtime desc limit #{start}, #{offset}")
	List<CartCourse> findMyCart(Map<String, Object> param) throws SQLExecutorException;

	/**
	 * @Title: delShopCatCourse
	 * @Description: 删除购物车中的课程
	 * @param param
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月9日 下午2:57:07
	 */
	@Select("update t_shopping_cart set entkbn=9 where userid=#{userid} and courseid = #{courseid}")
	void delShopCatCourse(Map<String, String> param) throws SQLExecutorException;

	


	
}
