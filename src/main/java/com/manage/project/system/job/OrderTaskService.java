package com.manage.project.system.job;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.manage.common.utils.DateUtils;
import com.manage.project.common.Constant;
import com.manage.project.system.server.service.IServerService;
import com.manage.project.system.statement.domain.OrderApply;
import com.manage.project.system.statement.mapper.OrderApplyMapper;
import com.manage.project.system.statement.service.IOrderApplyService;
import com.manage.project.system.statement.vo.OrderCancelVo;
import com.manage.project.system.statement.vo.OrderVo;

/**
 * 订单定时任务
 * 
 * @author zhangjiawei
 *
 */
@Service
@EnableScheduling
public class OrderTaskService {

	private Logger log = LoggerFactory.getLogger(OrderTaskService.class);
	
	@Autowired
	private OrderApplyMapper orderApplyMapper;
	
	@Autowired
	private IServerService serverService;
	
	/**
	 * 取消半个小时以上,一天以内为支付的订单
	 */
	@Scheduled(cron ="0 0/30 * * * ?")
	public void orderCancelTask() {
		OrderCancelVo orderCancelVo = new OrderCancelVo();
		//orderCancelVo.setCorpId("");
		orderCancelVo.setPayState(Constant.PAYSTATEWAIT);
		orderCancelVo.setAbnomarlState("0");
		orderCancelVo.setCurState("1");
		Calendar begin = Calendar.getInstance();
		begin.add(Calendar.DATE, -1);
		String beginTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(begin.getTime());
		orderCancelVo.setBeginTime(beginTime);
		Calendar end = Calendar.getInstance();
		end.add(Calendar.MINUTE, -30);
		String endTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(end.getTime());
		orderCancelVo.setEndTime(endTime);
		List<OrderApply> list = orderApplyMapper.selectOrderForCancel(orderCancelVo);
		for (OrderApply orderApply : list) {
			serverService.orderCancel(orderApply.getOrderId());
		}
	}
	
}
