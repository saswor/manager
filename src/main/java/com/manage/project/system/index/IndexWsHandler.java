package com.manage.project.system.index;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.adapter.standard.StandardWebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.manage.common.utils.CacheUtils;
import com.manage.common.utils.DateUtils;
import com.manage.common.utils.spring.SpringUtils;
import com.manage.framework.redis.RedisOps;
import com.manage.project.common.Constant;
import com.manage.project.system.base.domain.Corp;
import com.manage.project.system.index.domain.ReportOsale;
import com.manage.project.system.index.service.IndexService;
import com.manage.project.system.index.vo.IndexDataVo;
import com.manage.project.system.index.vo.OneMonthReviewVo;
import com.manage.project.system.index.vo.OperateReviewVo;
import com.manage.project.system.index.vo.column.YsColChartVo;
import com.manage.project.system.index.vo.line.TodaySummaryVo;
import com.manage.project.system.util.BussinessCacheService;

/**
 * 首页websocket处理类
 * @author xufeng
 *
 */
@Component
public class IndexWsHandler extends TextWebSocketHandler {
	private static Logger log = LoggerFactory.getLogger(IndexWsHandler.class); 
	
	@Autowired
	private RedisOps redisOps;
	
	private static AtomicInteger count = new AtomicInteger(0);  
    private static Map<String,WebSocketSession> sessionMap = new HashedMap();
    private static Map<String, String> corp_session = new HashMap<String, String>();	//	商户与session的关系
    private static Map<String, String> user_session = new HashMap<String, String>();	//	用户与session的关系
    
    @Autowired
	private IndexService indexService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        session.sendMessage(new TextMessage(session.getPrincipal().getName()+",你是第" + (sessionMap.size()) + "位访客")); //p2p

//        Object parse = JSONUtils.parse(message.getPayload());
//
//        Collection<WebSocketSession> sessions = sessionMap.values();
//        for (WebSocketSession ws : sessions) {//广播
//            ws.sendMessage(message);
//        }
//
//        sendMessage(sessionMap.keySet(),"你好");
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {        
        super.afterConnectionEstablished(session);
        
        StandardWebSocketSession websession = (StandardWebSocketSession)session;
        javax.websocket.Session wsession = websession.getNativeSession();
        
        List<String> corpIds = wsession.getRequestParameterMap().get("corpId");
        String corpId = "";
        if (corpIds != null && !corpIds.isEmpty()) {
        	corpId = corpIds.get(0);
        }
        List<String> userIds = wsession.getRequestParameterMap().get("userId");
        String userId = "";
        if (userIds != null && !userIds.isEmpty()) {
        	userId = userIds.get(0);
        }
        log.info("首页websocket连接被建立：" + "corpId=" + corpId + "user=" + session.getPrincipal().getName());
        
        sessionMap.put(session.getPrincipal().getName(),session);
        corp_session.put(session.getPrincipal().getName(), corpId);
        user_session.put(session.getPrincipal().getName(), userId);
        count.incrementAndGet();	// 连接数+1
        log.info("当前websocket连接session:"+session.getPrincipal().getName()+",连接数:"+count.get());
        // 发送首页运营总览
//        OperateReviewVo operateReviewVo = indexService.getOperateReviewFromCache(corpId);
        OperateReviewVo operateReviewVo = (OperateReviewVo) redisOps.getObject(Constant.INDEX_SUMMARY_TOTALREVIEW_CACHE_ + corpId);
		//log.debug("首页总览缓存:公司"+corpId+","+JSONObject.toJSONString(CacheUtils.get("serverCache", Constant.INDEX_SUMMARY_TOTALREVIEW_CACHE_+corpId))+",时间"+DateUtils.getTime());
        if(Integer.parseInt(operateReviewVo.getOnlineMachine())<0) {
        	operateReviewVo.setOnlineMachine("0");
        }
        if(Integer.parseInt(operateReviewVo.getOutlineMachine())<0) {
        	operateReviewVo.setOutlineMachine("0");
        }
        IndexDataVo review1 = new IndexDataVo();
        review1.setType(Constant.INDEX_DATA_TYPE_1);
        review1.setData(operateReviewVo);
        sendMessage(session.getPrincipal().getName(), JSON.toJSONString(review1));
        log.info("首页运营总览websocket:" + JSON.toJSONString(review1));
        
        // 发送首页近一个月总览
//        List<OneMonthReviewVo> oneMonthList = (List<OneMonthReviewVo>)CacheUtils.get("serverCache",Constant.INDEX_SUMMARY_ONEMONTH_CACHE_+corpId);
        List<OneMonthReviewVo> oneMonthList = (List<OneMonthReviewVo>) redisOps.getObject(Constant.INDEX_SUMMARY_ONEMONTH_CACHE_  + corpId);
        IndexDataVo review2 = new IndexDataVo();
        review2.setType(Constant.INDEX_DATA_TYPE_2);
        review2.setData(oneMonthList);
        String oneMonthJson = JSON.toJSONString(review2);
        sendMessage(session.getPrincipal().getName(), oneMonthJson);
        log.info("首页近一个月总览websocket:" + oneMonthJson);
        // 发送首页营收分析,近半年
        YsColChartVo y6vo = (YsColChartVo)CacheUtils.get(Constant.INDEX_SUMMARY_YS6_CACHE_+corpId);
        IndexDataVo review3 = new IndexDataVo();
        review3.setType(Constant.INDEX_DATA_TYPE_3);
        review3.setData(y6vo);
        String y6voJson = JSON.toJSONString(review3);
        sendMessage(session.getPrincipal().getName(), y6voJson);
        log.info("首页营收分析,近半年websocket:" + y6voJson);
        // 发送首页营收分析,近1年
        YsColChartVo y12vo = (YsColChartVo)CacheUtils.get(Constant.INDEX_SUMMARY_YS12_CACHE_+corpId);
        IndexDataVo review4 = new IndexDataVo();
        review4.setType(Constant.INDEX_DATA_TYPE_4);
        review4.setData(y12vo);
        String y12voJson = JSON.toJSONString(review4);
        sendMessage(session.getPrincipal().getName(), y12voJson);
        log.info("首页营收分析,近1年websocket:" + y12voJson);
        // 发送首页今日销售汇总 显示曲线图使用
        TodaySummaryVo todaysale = (TodaySummaryVo)CacheUtils.get(Constant.INDEX_SUMMARY_TODAYSALE_CACHE_+corpId);
        IndexDataVo review5 = new IndexDataVo();
        review5.setType(Constant.INDEX_DATA_TYPE_5);
        review5.setData(todaysale);
        String todaysaleJson = JSON.toJSONString(review5);
        sendMessage(session.getPrincipal().getName(), todaysaleJson);
        log.info("首页今日销售汇总 显示曲线图使用websocket:" + todaysaleJson);
        // 发送首页今日销售点位top10
        List<ReportOsale> todayLinesale = (List<ReportOsale>)CacheUtils.get(Constant.INDEX_SUMMARY_TODAYDW_CACHE_+corpId);
        IndexDataVo review6 = new IndexDataVo();
        review6.setType(Constant.INDEX_DATA_TYPE_6);
        review6.setData(todayLinesale);
        String todayLinesaleJson = JSON.toJSONString(review6);
        sendMessage(session.getPrincipal().getName(), todayLinesaleJson);
        log.info("首页今日销售汇总 显示曲线图使用websocket:" + todayLinesaleJson);
        // 发送首页今日销售商品top10
        List<Map<String, Object>> todayProductsale = (List<Map<String, Object>>)CacheUtils.get(Constant.INDEX_SUMMARY_TODAYSP_CACHE_+corpId);
        IndexDataVo review7 = new IndexDataVo();
        review7.setType(Constant.INDEX_DATA_TYPE_7);
        review7.setData(todayProductsale);
        String todayProductsaleJson = JSON.toJSONString(review7);
        sendMessage(session.getPrincipal().getName(), todayProductsaleJson);
        log.info("首页今日销售汇总 显示曲线图使用websocket:" + todayProductsaleJson);
        // 大屏今日销售点位top10
        IndexDataVo review8 = new IndexDataVo();
        review8.setType(Constant.INDEX_DATA_TYPE_8);
        List<String> pointNames = new ArrayList<String>();
        List<Float> sales = new ArrayList<Float>();
        for (ReportOsale ro : todayLinesale) {
        	pointNames.add(ro.getSaleName());
        	sales.add(ro.getSaleMoney());
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pointNames", pointNames);
        map.put("sales", sales);
        review8.setData(map);
        String dpLinesaleJson = JSON.toJSONString(review8);
        sendMessageToCorp(dpLinesaleJson, corpId);
        log.debug("大屏今日销售点位top10使用websocket:" + dpLinesaleJson);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {        
        super.afterConnectionClosed(session, status);
        
        log.info("首页websocket断开：" + session.getPrincipal().getName());
        sessionMap.remove(session.getPrincipal().getName());
        corp_session.remove(session.getPrincipal().getName());
        count.decrementAndGet();	// 连接数+1
    }

    /**
     * 发送消息
     */
    public static void sendMessage(String username,String message) throws IOException {
        sendMessage(Arrays.asList(username),Arrays.asList(message));
    }

    /**
     * 发送消息
     */
    public static void sendMessage(Collection<String> acceptorList,String message) throws IOException {
        sendMessage(acceptorList,Arrays.asList(message));
    }

    /**
     * 发送消息，p2p 群发都支持
     */
    public static void sendMessage(Collection<String> acceptorList, Collection<String> msgList) throws IOException {
        if (acceptorList != null && msgList != null) {
            for (String acceptor : acceptorList) {
                WebSocketSession session = sessionMap.get(acceptor);
                if (session != null) {
                    for (String msg : msgList) {
                        session.sendMessage(new TextMessage(msg.getBytes("utf-8")));
                    }
                }
            }
        }
    }
    
    /**
     * 群发消息
     * @param msg
     */
    public static void sendMessageToAll(String msg) {
    	Collection<WebSocketSession> sessions = sessionMap.values();
    	TextMessage text = null;
		try {
			text = new TextMessage(msg.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			log.error(e1.getMessage());
		}
        for (WebSocketSession ws : sessions) {//广播
            try {
				ws.sendMessage(text);
			} catch (IOException e) {
				log.error("websocket发送消息失败:" + msg + " ----to:" + ws.getPrincipal().getName());
			}
        }
    }
    
    /**
     * 向指定商户发送消息
     * @param msg	消息
     * @param corpId	商户id
     * @throws IOException
     */
    public static void sendMessageToCorp(String msg, String corpId) throws IOException {
    	log.debug("websocket向商户"+corpId+"发送消息:" + msg);
    	Set<String> sessionNames = corp_session.keySet();
    	for (String sessionName : sessionNames) {
    		if (corp_session.get(sessionName).equals(corpId)) {
    			WebSocketSession session = sessionMap.get(sessionName);
    			sendMessage(session.getPrincipal().getName(), msg);
    		}
    	}
    }
    
    /**
     * 发送首页数据
     * @throws IOException 
     */
    public void sendIndex() throws IOException {
    	// 更新首页缓存
//    	BussinessCacheService bussinessCacheService = (BussinessCacheService)SpringUtils.getBean("bussinessCacheService");
//    	bussinessCacheService.initIndexCache();
    	// 更新首页缓存
    	IndexService indexService = (IndexService)SpringUtils.getBean("indexServiceImpl");
    	// 获取所有商户
    	Object o = CacheUtils.get(Constant.CORP_CACHE);
		if (o == null) {
			return;
		}
		Map<String, Corp> corps = (Map<String, Corp>)o;
		if (corps == null || corps.isEmpty()) {
			return;
		}
		Set<String> corpIds = corps.keySet();
		
		for (String corpId: corpIds) {
			// 获取首页总览数据
			//OperateReviewVo operateReviewVo = indexService.getOperateReviewFromCache(corpId);
			OperateReviewVo operateReviewVo = (OperateReviewVo) redisOps.getObject(Constant.INDEX_SUMMARY_TOTALREVIEW_CACHE_ + corpId);
			//log.debug("首页总览缓存:公司"+corpId+","+JSONObject.toJSONString(CacheUtils.get("serverCache", Constant.INDEX_SUMMARY_TOTALREVIEW_CACHE_+corpId))+",时间"+DateUtils.getTime());
	        if(Integer.parseInt(operateReviewVo.getOnlineMachine())<0) {
	        	operateReviewVo.setOnlineMachine("0");
	        }
	        if(Integer.parseInt(operateReviewVo.getOutlineMachine())<0) {
	        	operateReviewVo.setOutlineMachine("0");
	        }
			IndexDataVo review1 = new IndexDataVo();
	        review1.setType(Constant.INDEX_DATA_TYPE_1);
	        review1.setData(operateReviewVo);
	        sendMessageToCorp(JSON.toJSONString(review1), corpId);
	        log.debug("首页运营总览websocket:" + JSON.toJSONString(review1));
	        // 发送首页近一个月总览
	        List<OneMonthReviewVo> oneMonthList = (List<OneMonthReviewVo>) redisOps.getObject(Constant.INDEX_SUMMARY_ONEMONTH_CACHE_  + corpId);
	        //List<OneMonthReviewVo> oneMonthList = (List<OneMonthReviewVo>)CacheUtils.get("serverCache",Constant.INDEX_SUMMARY_ONEMONTH_CACHE_+corpId);
			//log.info("近一月总览缓存:公司"+corpId+","+JSONObject.toJSONString(CacheUtils.get("serverCache", Constant.INDEX_SUMMARY_ONEMONTH_CACHE_+corpId))+",时间"+DateUtils.getTime());
	        IndexDataVo review2 = new IndexDataVo();
	        review2.setType(Constant.INDEX_DATA_TYPE_2);
	        review2.setData(oneMonthList);
	        String oneMonthJson = JSON.toJSONString(review2);
	        sendMessageToCorp(oneMonthJson, corpId);
	        if("1008".equals(corpId)) {
	        	log.debug("首页近一个月总览websocket:" + oneMonthJson);
	        }else {
	        	log.debug("首页近一个月总览websocket:" + oneMonthJson);
	        }
//	        // 发送首页营收分析,近半年
//	        YsColChartVo y6vo = (YsColChartVo)CacheUtils.get(Constant.INDEX_SUMMARY_YS6_CACHE_+corpId);
//	        IndexDataVo review3 = new IndexDataVo();
//	        review3.setType(Constant.INDEX_DATA_TYPE_3);
//	        review3.setData(y6vo);
//	        String y6voJson = JSON.toJSONString(review3);
//	        sendMessageToCorp(y6voJson, corpId);
//	        log.debug("首页营收分析,近半年websocket:" + y6voJson);
//	        // 发送首页营收分析,近1年
//	        YsColChartVo y12vo = (YsColChartVo)CacheUtils.get(Constant.INDEX_SUMMARY_YS12_CACHE_+corpId);
//	        IndexDataVo review4 = new IndexDataVo();
//	        review4.setType(Constant.INDEX_DATA_TYPE_4);
//	        review4.setData(y12vo);
//	        String y12voJson = JSON.toJSONString(review4);
//	        sendMessageToCorp(y12voJson, corpId);
//	        log.debug("首页营收分析,近1年websocket:" + y12voJson);
	        // 发送首页今日销售汇总 显示曲线图使用
	        TodaySummaryVo todaysale = (TodaySummaryVo)CacheUtils.get(Constant.INDEX_SUMMARY_TODAYSALE_CACHE_+corpId);
	        IndexDataVo review5 = new IndexDataVo();
	        review5.setType(Constant.INDEX_DATA_TYPE_5);
	        review5.setData(todaysale);
	        String todaysaleJson = JSON.toJSONString(review5);
	        sendMessageToCorp(todaysaleJson, corpId);
	        log.debug("首页今日销售汇总 显示曲线图使用websocket:" + todaysaleJson);
	        // 发送首页今日销售点位top10
	        List<ReportOsale> todayLinesale = (List<ReportOsale>)CacheUtils.get(Constant.INDEX_SUMMARY_TODAYDW_CACHE_+corpId);
	        IndexDataVo review6 = new IndexDataVo();
	        review6.setType(Constant.INDEX_DATA_TYPE_6);
	        review6.setData(todayLinesale);
	        String todayLinesaleJson = JSON.toJSONString(review6);
	        sendMessageToCorp(todayLinesaleJson, corpId);
	        log.debug("首页今日销售点位top10 websocket:" + todayLinesaleJson);
	        // 发送首页今日销售商品top10
	        List<Map<String, Object>> todayProductsale = (List<Map<String, Object>>)CacheUtils.get(Constant.INDEX_SUMMARY_TODAYSP_CACHE_+corpId);
	        IndexDataVo review7 = new IndexDataVo();
	        review7.setType(Constant.INDEX_DATA_TYPE_7);
	        review7.setData(todayProductsale);
	        String todayProductsaleJson = JSON.toJSONString(review7);
	        sendMessageToCorp(todayProductsaleJson, corpId);
	        log.debug("首页今日销售汇总 显示曲线图使用websocket:" + todayProductsaleJson);
	        // 大屏今日销售点位top10
	        IndexDataVo review8 = new IndexDataVo();
	        review8.setType(Constant.INDEX_DATA_TYPE_8);
	        List<String> pointNames = new ArrayList<String>();
	        List<Float> sales = new ArrayList<Float>();
	        for (ReportOsale ro : todayLinesale) {
	        	pointNames.add(ro.getSaleName());
	        	sales.add(ro.getSaleMoney());
	        }
	        Map<String, Object> map = new HashMap<String, Object>();
	        map.put("pointNames", pointNames);
	        map.put("sales", sales);
	        review8.setData(map);
	        String dpLinesaleJson = JSON.toJSONString(review8);
	        sendMessageToCorp(dpLinesaleJson, corpId);
	        log.debug("大屏今日销售点位top10使用websocket:" + dpLinesaleJson);
		}
		
    }

    /**
     * 发送信息给指定用户
     * 
     * @param userId 用户编号
     * @param msg 信息
     * @throws IOException 
     */
	public static void sendMessageToUser(String userId, String msg) throws IOException {
		log.debug("websocket向用户"+userId+"发送消息:" + msg);
    	Set<String> sessionNames = user_session.keySet();
    	for (String sessionName : sessionNames) {
    		if (user_session.get(sessionName).equals(userId)) {
    			WebSocketSession session = sessionMap.get(sessionName);
    			sendMessage(session.getPrincipal().getName(), msg);
    		}
    	}
	}
}
