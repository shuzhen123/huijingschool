package dianfan.service.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.constant.ConstantIF;
import dianfan.dao.mapper.admin.NewsMapper;
import dianfan.entities.DataTable;
import dianfan.entities.Infomation;
import dianfan.entities.NewsModel;
import dianfan.exception.SQLExecutorException;
import dianfan.util.UUIDUtil;

/**
 * @ClassName NewsService
 * @Description 咨讯服务
 * @author cjy
 * @date 2018年1月9日 下午5:40:17
 */
@Service
public class NewsService {
	@Autowired
	private NewsMapper newsMapper;

	/**
	 * @Title: findNewsModel
	 * @Description: 咨讯模块列表
	 * @param search
	 * @param start
	 * @param length
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年1月9日 下午5:40:57
	 */
	public DataTable findNewsModel(String search, int start, int length) throws SQLExecutorException {

		DataTable dt = new DataTable();

		// 1、获取总条数
		Map<String, Object> param = new HashMap<>();
		param.put("start", start);
		param.put("length", length);

		int count = newsMapper.findNewsModelCount();
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
		List<NewsModel> model = newsMapper.findNewsModel(param);

		// 设置数据
		dt.setData(model);
		return dt;
	}

	/**
	 * @Title: delNewsModel
	 * @Description: 批量删除模块
	 * @param ids
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年1月9日 下午6:06:03
	 */
	@Transactional
	public void delNewsModel(List<String> ids) throws SQLExecutorException {
		newsMapper.delNewsModelByIds(ids);
	}

	/**
	 * @Title: findModelByName
	 * @Description: 根据name查找重复模块数
	 * @param modelid
	 * @param modelname
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年1月10日 上午10:13:54
	 */
	public boolean findModelByName(String modelid, String modelname) throws SQLExecutorException {
		Map<String, String> param = new HashMap<>();
		param.put("modelname", modelname);
		int count = newsMapper.findCountByName(param);
		if (count > 0) {
			// 查询出多条
			return true;
		} else {
			// 没有条数
			return false;
		}

	}

	/**
	 * @Title: addNewsModel
	 * @Description: 添加新模块
	 * @param modelname
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年1月10日 上午9:32:22
	 */
	@Transactional
	public void addNewsModel(String modelname) throws SQLExecutorException {
		NewsModel nm = new NewsModel();
		nm.setInformationid(UUIDUtil.getUUID());
		nm.setNewskindname(modelname);
		newsMapper.addNewsModel(nm);
	}

	/**
	 * @Title: editNewsModel
	 * @Description: 修改模块
	 * @param id
	 * @param modelname
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年1月10日 上午10:22:01
	 */
	@Transactional
	public void editNewsModel(String id, String modelname) throws SQLExecutorException {
		NewsModel nm = new NewsModel();
		nm.setInformationid(id);
		nm.setNewskindname(modelname);
		newsMapper.editNewsModel(nm);
	}

	/* ************************************ */
	/* *************** 咨讯相关 *************** */
	/* *********************************** */
	
	/**
	 * @Title: findNewsModel
	 * @Description: 咨讯列表
	 * @param search
	 * @param start
	 * @param length
	 * @param vp 牛人观点功能调用(true)
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年1月10日 上午10:52:46
	 */
	public DataTable findNews(Map<String, Object> param) throws SQLExecutorException {
		DataTable dt = new DataTable();
		// 1、获取总条数
		int count = newsMapper.findNewsCount(param);
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
		List<Infomation> model = newsMapper.findNews(param);

		for(Infomation info : model) {
			if(info.getPicurl() != null && !info.getPicurl().isEmpty()) {
				info.setPicurl(ConstantIF.PROJECT + info.getPicurl());
			}
		}
		// 设置数据
		dt.setData(model);
		return dt;
	}

	/**
	 * @Title: addNewsInfo
	 * @Description: 添加咨讯数据
	 * @param info
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年1月11日 下午12:57:38
	 */
	@Transactional
	public void addNewsInfo(Infomation info) throws SQLExecutorException {
		info.setId(UUIDUtil.getUUID());
		if(info.getUserid() == null || info.getUserid().trim().isEmpty()) {
			info.setUserid(null);
		}
		newsMapper.addNewsInfo(info);
	}

	/**
	 * @Title: delNews
	 * @Description: 根据id删除咨讯数据
	 * @param ids
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年1月11日 下午1:56:51
	 */
	@Transactional
	public void delNews(List<String> ids) throws SQLExecutorException {
		newsMapper.delNewsByIds(ids);
	}

	/**
	 * @Title: findNewsInfo
	 * @Description: 获取咨讯数据
	 * @param id
	 * @return
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年1月11日 下午2:21:17
	 */
	public Infomation findNewsInfo(String id) throws SQLExecutorException {
		Infomation infomation = newsMapper.findNewsInfoById(id);
		if(infomation.getPicurl() != null && !infomation.getPicurl().isEmpty()) {
			infomation.setPicurl(ConstantIF.PROJECT + infomation.getPicurl());
		}
		return infomation;
	}

	/**
	 * @Title: editNewsInfo
	 * @Description: 修改咨讯数据
	 * @param info
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年1月11日 下午2:43:44
	 */
	@Transactional
	public void editNewsInfo(Infomation info) throws SQLExecutorException {
		newsMapper.editNewsInfo(info);
	}

	/**
	 * @Title: findViewpoint
	 * @Description: 获取牛人观点列表
	 * @param start
	 * @param length
	 * @param infomationtitle
	 * @param starttime
	 * @param endtime
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月6日 上午11:01:19
	 */
	public DataTable findViewpoint(int start, int length, String infomationtitle, String starttime, String endtime) throws SQLExecutorException {
		DataTable dt = new DataTable();

		Map<String, Object> param = new HashMap<>();
		param.put("start", start);
		param.put("length", length);
		
		param.put("infomationtitle", infomationtitle);
		param.put("starttime", starttime);
		param.put("endtime", endtime);
		
		// 1、获取总条数
		int count = newsMapper.findViewpointCount(param);
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
		List<Infomation> model = newsMapper.findViewpoint(param);

		// 设置数据
		dt.setData(model);
		return dt;
	}

	/**
	 * @Title: editViewpointInfo
	 * @Description: 修改观点数据
	 * @param info
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年3月6日 上午10:12:18
	 */
	@Transactional
	public void editViewpointInfo(Infomation info) throws SQLExecutorException {
		newsMapper.editViewpointInfo(info);
	}
}