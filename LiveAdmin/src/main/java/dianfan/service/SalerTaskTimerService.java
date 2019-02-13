package dianfan.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.constant.ConstantIF;
import dianfan.dao.mapper.TaskMapper;
import dianfan.entities.BashMap;
import dianfan.exception.SQLExecutorException;
/**
 * @ClassName SalerTaskTimer
 * @Description 任务调度
 * @author cjy
 * @date 2018年2月23日 上午11:37:05
 */
/*
	字段      允许值     允许的特殊字符
	秒       0-59        , - * /
	分       0-59        , - * /
	小时      0-23        , - * /
	日期      1-31        , - * ? / L W C
	月份      1-12 或者 JAN-DEC     , - * /
	星期      1-7 或者 SUN-SAT      , - * ? / L C #
	年（可选）       留空, 1970-2099       , - * /
	 */
@Service
public class SalerTaskTimerService {
	@Autowired
	private RedisService redisService;
	@Autowired
	private TaskMapper taskMapper;
	
	/**
	 * @Title: SalerTaskDistribute
	 * @Description: 业务员任务分配任务
	 * @throws SQLExecutorException 
	 * @throws:
	 * @time: 2018年2月23日 上午11:40:29
	 */
    @Scheduled(cron = "0 29 11 * * ?")
	@Transactional
    public void salerTaskDistribute() throws SQLExecutorException {
    	//获取所有的业务员
    	List<String> ids = taskMapper.findAllSaler();
    	
    	//获取默认任务数量
    	String def = redisService.get(ConstantIF.DEF_TASK_KEY);
    	if(def == null || def.trim().isEmpty() || Integer.parseInt(def) < 0) {
    		def = "0";
    	}
    	//获取业务员对应等级的任务数
    	List<BashMap> taskNum = taskMapper.findSalerLevelTaskNum(ids);
    	Map<String, String> nummap = new HashMap<>();
    	if(!taskNum.isEmpty()) {
    		for(BashMap bm : taskNum) {
    			nummap.put(bm.getId(), bm.getName());
    		}
    	}
    	
    	List<Map<String, String>> data = new ArrayList<>();
    	for(String id : ids) {
    		Map<String, String> map = new HashMap<>();
    		map.put("id", id);
    		if(nummap.get(id) != null) {
    			map.put("num", nummap.get(id));
    		}else {
    			map.put("num", def);
    		}
    		data.add(map);
    	}
    	
    	//给所有业务员添加任务数
    	taskMapper.addSalerDistribute(data);
    }
    
    /**
     * @Title: vLiveTask
     * @Description: 直播扫描（检测预告直播）
     * @throws SQLExecutorException
     * @throws:
     * @time: 2018年5月3日 下午7:00:23
     */
    @Scheduled(cron = "1 * * * *  ?")
    @Transactional
    public void vLiveTask() throws SQLExecutorException {
    	//taskMapper.scanLiveCourse();
    }
    
}
