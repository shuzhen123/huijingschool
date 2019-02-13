package dianfan.service.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.constant.ConstantIF;
import dianfan.dao.mapper.app.AppShopCartMapper;
import dianfan.entities.CartCourse;
import dianfan.exception.SQLExecutorException;

/**
 * @ClassName AppShopCartService
 * @Description 我的购物车服务
 * @author cjy
 * @date 2018年4月9日 下午1:22:15
 */
@Service
public class AppShopCartService {
	@Autowired
	private AppShopCartMapper appShopCartMapper;

	/**
	 * @Title: addMyCart
	 * @Description: 课程添加到我的购物车
	 * @param userId
	 * @param courseid
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月9日 下午1:19:49
	 */
	@Transactional
	public void addMyCart(CartCourse cart) throws SQLExecutorException {
		appShopCartMapper.addMyCart(cart);
	}

	/**
	 * @Title: findMyCart
	 * @Description: 我的购物车列表
	 * @param userId
	 * @return
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年4月9日 下午1:28:28
	 */
	public Map<String, Object> findMyCart(String userid, Integer page) throws SQLExecutorException {
		// 响应数据
		Map<String, Object> data = new HashMap<>();

		//获取我的购物车中课程总数量
		int count = appShopCartMapper.getMyCartCount(userid);
		// 总页数
		int totalPage;
		if (count % ConstantIF.PAGE_OFFSET != 0) {
			totalPage = count / ConstantIF.PAGE_OFFSET + 1;
		} else {
			totalPage = count / ConstantIF.PAGE_OFFSET;
		}

		// 总条数
		data.put("counts", count);
		// 总页数
		data.put("totalpage", totalPage);
		// 当前页
		data.put("currentpage", page);

		// 查看是否已超总页数
		if (totalPage < page) {
			data.put("cartlist", new ArrayList<>());
			return data;
		}

		// 设置请求参数
		Map<String, Object> param = new HashMap<>();
		param.put("userid", userid);
		param.put("start", (page - 1) * ConstantIF.PAGE_OFFSET);
		param.put("offset", ConstantIF.PAGE_OFFSET);
		
		// 获取我的购物车中课程列表
		List<CartCourse> cart = appShopCartMapper.findMyCart(param);
		// 处理图片链接
		for (CartCourse cc : cart) {
			cc.setCoursepic(ConstantIF.PROJECT + cc.getCoursepic());
		}

		data.put("cartlist", cart);
		return data;
	}
	public Map<String, Object> findMyCart1(String userid, Integer page) throws SQLExecutorException {
		// 响应数据
		Map<String, Object> data = new HashMap<>();
		
		//获取我的购物车中课程总数量
		int count = appShopCartMapper.getMyCartCount(userid);
		// 总页数
		int totalPage;
		if (count % ConstantIF.PAGE_OFFSET != 0) {
			totalPage = count / ConstantIF.PAGE_OFFSET + 1;
		} else {
			totalPage = count / ConstantIF.PAGE_OFFSET;
		}
		
		// 总页数
		data.put("totalpage", totalPage);
		// 当前页
		data.put("currentpage", page);
		
		// 查看是否已超总页数
		if (totalPage < page) {
			data.put("cartlist", new ArrayList<>());
			return data;
		}
		
		// 设置请求参数
		Map<String, Object> param = new HashMap<>();
		param.put("userid", userid);
		param.put("start", (page - 1) * ConstantIF.PAGE_OFFSET);
		param.put("offset", ConstantIF.PAGE_OFFSET);
		
		// 获取我的购物车中课程列表
		List<CartCourse> cart = appShopCartMapper.findMyCart(param);
		// 处理图片链接
		for (CartCourse cc : cart) {
			cc.setCoursepic(ConstantIF.PROJECT + cc.getCoursepic());
		}
		
		data.put("cartlist", cart);
		return data;
	}

	/**
	 * @Title: delShopCatCourse
	 * @Description: 删除购物车中的课程
	 * @param userid
	 * @param courseid
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年4月9日 下午2:43:35
	 */
	@Transactional
	public void delShopCatCourse(String userid, String courseid) throws SQLExecutorException {
		Map<String, String> param = new HashMap<>();
		param.put("userid", userid);
		param.put("courseid", courseid);
		appShopCartMapper.delShopCatCourse(param);
	}

}
