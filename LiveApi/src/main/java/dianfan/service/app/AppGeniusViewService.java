package dianfan.service.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.constant.ConstantIF;
import dianfan.dao.mapper.app.AppGeniusViewMapper;
import dianfan.entities.DiagnoseStock;
import dianfan.entities.Infomation;
import dianfan.exception.SQLExecutorException;

/**
 * @ClassName AppGeniusViewService
 * @Description 
 * @author cjy
 * @date 2018年4月2日 上午11:01:28
 */
@Service
public class AppGeniusViewService {
	@Autowired
	private AppGeniusViewMapper appGeniusViewMapper;

	/**
	 * @Title: findGeniusViewList
	 * @Description: 牛人观点列表
	 * @param page
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年4月2日 上午10:55:44
	 */
	public Map<String, Object> findGeniusViewList(int page) throws SQLExecutorException {
		// 响应数据
		Map<String, Object> data = new HashMap<>();

		// 牛人观点总条数
		int count = appGeniusViewMapper.findGeniusViewCount();
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
			data.put("gvlist", new ArrayList<>());
			return data;
		}

		// 设置请求条件
		Map<String, Object> param = new HashMap<>();
		param.put("start", (page - 1) * ConstantIF.PAGE_OFFSET);
		param.put("offset", ConstantIF.PAGE_OFFSET);

		// 牛人观点列表
		List<Infomation> list = appGeniusViewMapper.findGeniusViewList(param);

		data.put("gvlist", list);
		return data;
	}


}
