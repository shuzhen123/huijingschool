package dianfan.service.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.constant.ConstantIF;
import dianfan.dao.mapper.admin.GoodsMapper;
import dianfan.entities.DataTable;
import dianfan.entities.Goods;
import dianfan.entities.UserInfo;
import dianfan.exception.SQLExecutorException;
import dianfan.util.UUIDUtil;

/**
 * @ClassName GoodsService
 * @Description 礼物服务
 * @author cjy
 * @date 2018年2月12日 下午4:42:43
 */
@Service 
public class GoodsService {
	@Autowired
	private GoodsMapper goodsMapper;
 

	/**
	 * @Title: findAdminUser
	 * @Description: 获取礼物列表
	 * @param start
	 * @param length
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年2月12日 下午4:44:36
	 */
	public DataTable findGoods(int start, int length) throws SQLExecutorException {
		DataTable dt = new DataTable();

		Map<String, Object> param = new HashMap<>();
		param.put("start", start);
		param.put("length", length < 0 ? 9999999 : length);
		
		// 1、获取总条数
		int count = goodsMapper.findGoodsCount(param);
		if (count == 0) {
			// 无数据
			return dt;
		} else {
			// 设置总条数
			dt.setRecordsTotal(count);
			// 设置请求条数
			dt.setRecordsFiltered(count);
		}

		// 2、获取需求数据
		List<Goods> goods = goodsMapper.findGoods(param);
		
		for(Goods g : goods) {
			g.setIcon(ConstantIF.PROJECT + g.getIcon());
			g.setAgentprofit((float) Math.round(g.getAgentprofit()*100));
			g.setSalerprofit((float)Math.round(g.getSalerprofit()*100));
		}

		// 设置数据
		dt.setData(goods);
		return dt;
	}

	/**
	 * @Title: checkGoodsName
	 * @Description: 根据礼物名称查询可用数量
	 * @param goods
	 * @return
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年2月23日 上午9:26:53
	 */
	public int checkGoodsName(Goods goods) throws SQLExecutorException {
		if(goods.getGoodsid() != null && !goods.getGoodsid().isEmpty()) {
			return goodsMapper.checkGoodsNameById(goods);
		}else {
			return goodsMapper.checkGoodsName(goods);
		}
	}

	/**
	 * @Title: addGoods
	 * @Description: 添加礼物信息
	 * @param goods
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年2月23日 上午9:18:29
	 */
	@Transactional
	public void addGoods(Goods goods) throws SQLExecutorException {
		goods.setGoodsid(UUIDUtil.getUUID());
		goodsMapper.addGoods(goods);
	}

	/**
	 * @Title: editGoods
	 * @Description: 修改礼物信息
	 * @param goods
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年2月23日 上午10:11:24
	 */
	@Transactional
	public void editGoods(Goods goods) throws SQLExecutorException {
		goodsMapper.editGoods(goods);
	}

	/**
	 * @Title: delGoods
	 * @Description: 删除礼物
	 * @param lids
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年2月23日 上午10:45:42
	 */
	@Transactional
	public void delGoods(List<String> lids) throws SQLExecutorException {
		goodsMapper.delGoods(lids);
	}


	
}












