package dianfan.service.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.constant.ConstantIF;
import dianfan.dao.mapper.app.AppMyInfoMapper;
import dianfan.dao.mapper.app.AppNewsMapper;
import dianfan.entities.CommentRelative;
import dianfan.entities.Infomation;
import dianfan.entities.NewsComment;
import dianfan.entities.VipInfomation;
import dianfan.exception.SQLExecutorException;
import dianfan.util.UUIDUtil;

/**
 * @ClassName AppNewsService
 * @Description 咨讯相关服务
 * @author cjy
 * @date 2018年3月2日 上午9:58:37
 */
@Service
public class AppNewsService {
	@Autowired
	private AppNewsMapper appNewsMapper;
	@Autowired
	private AppMyInfoMapper appMyInfoMapper;

	/**
	 * @Title: findNewsList
	 * @Description: 根据咨讯模块id获取咨讯列表
	 * @param modelid
	 * @param page
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月1日 上午11:12:48
	 */
	public Map<String, Object> findNewsList(String modelid, int page) throws SQLExecutorException {
		// 响应数据
		Map<String, Object> data = new HashMap<>();

		Map<String, Object> param = new HashMap<>();
		param.put("infoid", modelid);
		param.put("offset", ConstantIF.PAGE_OFFSET);

		// 根据条件获取总条数
		int count = appNewsMapper.getCountNewsListByInfoid(param);
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
			data.put("newslist", new ArrayList<>());
			return data;
		}

		// 设置请求起始位置
		param.put("page", (page - 1) * ConstantIF.PAGE_OFFSET);

		// 获取咨讯数据
		List<Infomation> infos = appNewsMapper.findNewsListByInfoid(param);
		// 处理图片链接
		if (infos.size() > 0) {
			for (Infomation info : infos) {
				info.setPicurl(ConstantIF.PROJECT + info.getPicurl());
			}
		}

		data.put("newslist", infos);
		return data;
	}

	/**
	 * @Title: findNewsDetail
	 * @Description: 获取咨讯详情
	 * @param infoid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月1日 上午11:58:54
	 */
	@Transactional
	public Infomation findNewsDetail(String infoid) throws SQLExecutorException {
		Infomation newsDetail = appNewsMapper.findNewsDetail(infoid);
		//阅读量+1
		appNewsMapper.newsReadCountInc(infoid);
		return newsDetail;
	}

	
	
	
	/**
	 * @Title: findNewsComment
	 * @Description: 根据咨讯id获取评论列表
	 * @param infoid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月1日 下午1:07:54
	 */
	public Map<String, Object> findNewsComment(String infoid, int page) throws SQLExecutorException {
		// 响应数据
		Map<String, Object> data = new HashMap<>();
		// 根据条件获取总条数
		int count = appNewsMapper.getNewsCommentCountByInfoid(infoid);
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
			data.put("commentlist", new ArrayList<>());
			return data;
		}

		Map<String, Object> param = new HashMap<>();
		param.put("infoid", infoid);
		param.put("offset", ConstantIF.PAGE_OFFSET);
		// 设置请求起始位置
		param.put("page", (page - 1) * ConstantIF.PAGE_OFFSET);

		// 条件内的评论数据
		List<NewsComment> commlist = appNewsMapper.findNewsCommentByInfoid(param);

		// 检出评论id列表
		List<String> commids = new ArrayList<>();
		// 检出commid:{nickname, content}数据
		Map<String, String[]> comm = new HashMap<>();
		for (int i = 0; i < commlist.size(); i++) {
			NewsComment comment = commlist.get(i);
			//评论id
			commids.add(comment.getCommentid());
			//评论数据
			String[] dt = {comment.getNickname(), comment.getCommentcontent()};
			comm.put(comment.getCommentid(), dt);
		}
		
		//根据评论id获取评论关系
		List<CommentRelative> relation = appNewsMapper.findCommentRelation(commids);
		if(relation.size() > 0) {
			//评论关系映射{子id -> 父id}
			Map<String, String> rm = new HashMap<>();
			for(CommentRelative cr : relation) {
				rm.put(cr.getChildid(), cr.getParentid());
			}
			//结束循环标记
			boolean b = true;
			do {
				//继续循环标记
				boolean b1 = false;
				for(Entry<String, String> entry : rm.entrySet()) {
					if(entry.getValue() == null) {
						continue;
					}
					//最后的父id
					String v = entry.getValue().substring(entry.getValue().length() - 32);
					if(rm.get(v) != null) {
						//拼接父ids
						rm.put(entry.getKey(), entry.getValue() + " //@" + rm.get(v));
						b1 = true;
					}
				}
				if(!b1) {
					b=false;
				}
			}while(b);
			
			//评论内容替换评论id
			Pattern pattern = Pattern.compile("[0-9a-z]{32}");
			for(Entry<String, String> entry : rm.entrySet()) {
				String sidstr = entry.getValue();
				
				Matcher matcher = pattern.matcher(sidstr);
				while(matcher.find()){
					String pid = matcher.group();
					
					String[] s = comm.get(pid);
					sidstr = sidstr.replace(pid, s[0]+"："+s[1]);
				}
				rm.put(entry.getKey(), " //@" + sidstr);
			}
			
			//嵌入多级评论
			for(NewsComment nc : commlist) {
				if(rm.get(nc.getCommentid()) != null) {
					nc.setCommentcontent(nc.getCommentcontent() + rm.get(nc.getCommentid()));
				}
				nc.setIconurl(ConstantIF.PROJECT + nc.getIconurl());
			}
		}
		
		
		data.put("commentlist", commlist);

		return data;
	}
	
	/* ***************************vip咨讯************************** */
	/**
	 * @Title: findVipNewsInfo
	 * @Description: 获取vip咨讯列表
	 * @param userid
	 * @param page
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月2日 上午10:24:25
	 */
	public Map<String, Object> findVipNewsInfo(String userid, int page) throws SQLExecutorException {
		// 响应数据
		Map<String, Object> data = new HashMap<>();

		Map<String, Object> param = new HashMap<>();
		param.put("userid", userid);
		
		//检测是否为内部员工
		boolean b = appMyInfoMapper.checkUserIsSaler(userid);
		if(b) {
			param.put("userid", "inner_staff");
		}
		
		// 根据条件获取总条数
		int count = appNewsMapper.getVipNewsCount(param);

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
			data.put("vipnews", new ArrayList<>());
			return data;
		}

		// 设置请求起始位置
		param.put("start", (page - 1) * ConstantIF.PAGE_OFFSET);
		param.put("offset", ConstantIF.PAGE_OFFSET);

		// 获取vip策略列表
		List<VipInfomation> vipNews = appNewsMapper.findVipNews(param);

		for (VipInfomation vi : vipNews) {
			vi.setPicurl(ConstantIF.PROJECT + vi.getPicurl());
		}

		data.put("vipnews", vipNews);

		return data;
	}
	
	/**
	 * @Title: findVipNewsDetail
	 * @Description: 获取vip咨讯详情
	 * @param infoid
	 * @return
	 * @throws SQLExecutorException
	 * @throws:
	 * @time: 2018年3月1日 上午11:58:54
	 */
	@Transactional
	public VipInfomation findVipNewsDetail(String infoid) throws SQLExecutorException  {
		VipInfomation newsDetail = appNewsMapper.findVipNewsDetail(infoid);
		//阅读量+1
		appNewsMapper.newsVipReadCountInc(infoid);
		return newsDetail;
	}
	
	/**
	 * @Title: sendNewsComment
	 * @Description: 发表评论
	 * @param userId
	 *            发布者id
	 * @param infoid
	 *            咨讯id
	 * @param commid
	 *            父评论id
	 * @param content
	 *            评论内容
	 * @throws:
	 * @time: 2018年3月5日 上午9:44:42
	 */
	@Transactional
	public void sendNewsComment(String userId, String infoid, String commid, String content)
			throws SQLExecutorException {
		// 组装评论数据
		NewsComment comm = new NewsComment();
		comm.setCommentid(UUIDUtil.getUUID());
		comm.setInfomationid(infoid);
		comm.setCommentuserid(userId);
		comm.setCommentcontent(content);
		// 将评论写入数据库
		appNewsMapper.createNewsComment(comm);

		// 如果是二级评论，创建评论关系
		if (commid != null && !commid.trim().isEmpty()) {
			CommentRelative cr = new CommentRelative();
			cr.setId(UUIDUtil.getUUID());
			cr.setChildid(comm.getCommentid());
			cr.setParentid(commid);
			// 将评论关系写入数据库
			appNewsMapper.createCommentRelative(cr);
		}
	}


}
