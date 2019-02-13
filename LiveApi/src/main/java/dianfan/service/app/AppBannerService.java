package dianfan.service.app;

import java.util.List;

import org.springframework.stereotype.Service;

import dianfan.entities.DynamicNews;
import dianfan.exception.SQLExecutorException;

/**
 * @ClassName BannerService
 * @Description Banner图相关服务
 * @author cjy
 * @date 2018年1月23日 下午2:22:37
 */
@Service
public class AppBannerService {
	/**
	 * @throws SQLExecutorException 
	 * @Title: getDynamicNews
	 * @Description: 最新动态
	 * @throws:
	 * @time: 2018年1月23日 下午2:28:07
	 */
	public List<DynamicNews> getDynamicNews() throws SQLExecutorException {
			return null;
	}

}
