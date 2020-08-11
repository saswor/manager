package com.manage.framework.redis;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.manage.common.utils.CacheUtils;
import com.manage.project.common.Constant;
import com.manage.project.system.index.vo.OneMonthReviewVo;
import com.manage.project.system.index.vo.OperateReviewVo;

import redis.clients.jedis.Jedis;

/**
 * redis持久化数据工具
 * 
 * @author 思杰
 * 
 */
@Component
public class RedisOps {
	
	@Autowired
	private RedisConnection redisConnection;
	
	public void set(String key, String value) {
		Jedis jedis = redisConnection.getJedis();
		jedis.set(key, value);
		jedis.close();
	}

	public String get(String key) {
		Jedis jedis = redisConnection.getJedis();
		String value = jedis.get(key);
		jedis.close();
		return value;
	}

	public void setObject(String key, Object object) {
		Jedis jedis = redisConnection.getJedis();
		jedis.set(key.getBytes(), SerializeUtil.serizlize(object));
		jedis.close();
	}

	public Object getObject(String key) {
		Jedis jedis = redisConnection.getJedis();
		byte[] bytes = jedis.get(key.getBytes());
		jedis.close();
		return SerializeUtil.deserialize(bytes);
	}

	public static void main(String[] args) {
		// OperateReviewVo operateReviewVo=new OperateReviewVo();
		// operateReviewVo.setOnlineMachine("10");
		// operateReviewVo.setOutlineMachine("10");
		// operateReviewVo.setTotalProfit("10.20");
		// operateReviewVo.setTotalSale("124.02");
		// operateReviewVo.setTotalSaleNum("100");
		// RedisOps.setObject(CacheUtils.INDEX_SUMMARY_TOTALREVIEW_CACHE_+"8886",operateReviewVo);
		// List<OneMonthReviewVo> oneMonthReviewVoList=new
		// ArrayList<OneMonthReviewVo>();
		// OneMonthReviewVo oneMonthReviewVo=new OneMonthReviewVo();
		// oneMonthReviewVo.setType(1);
		// oneMonthReviewVo.setTotalProfit("10.20");
		// oneMonthReviewVo.setTotalSale("124.02");
		// oneMonthReviewVo.setTotalSaleNum("100");
		// oneMonthReviewVoList.add(oneMonthReviewVo);
		// RedisOps.setObject(CacheUtils.INDEX_SUMMARY_ONEMONTH_CACHE_+"8886",oneMonthReviewVoList);
		RedisOps redisOps = new RedisOps();
		OperateReviewVo operateReviewVo = (OperateReviewVo) redisOps.getObject(Constant.INDEX_SUMMARY_TOTALREVIEW_CACHE_ + "8888");
		System.out.println(" 总利润:" + operateReviewVo.getTotalProfit() + " 总营业额:" + operateReviewVo.getTotalSale() + " 总销量:" + operateReviewVo.getTotalSaleNum() );
		
		List<OneMonthReviewVo> oneMonthReviewVoList = (List<OneMonthReviewVo>) redisOps.getObject(Constant.INDEX_SUMMARY_ONEMONTH_CACHE_  + "8888");
		OneMonthReviewVo oneMonthReviewVo = null;
		if (oneMonthReviewVoList != null) {
			for (OneMonthReviewVo oneMonthReviewVoT : oneMonthReviewVoList) {
				if (oneMonthReviewVoT.getType() == 1) {
					oneMonthReviewVo = oneMonthReviewVoT;
				}
			}
		} 
		System.out.println(" 总利润:" + oneMonthReviewVo.getTotalProfit() + " 总营业额:" + oneMonthReviewVo.getTotalSale() + " 总销量:" + oneMonthReviewVo.getTotalSaleNum() );
	}
}
