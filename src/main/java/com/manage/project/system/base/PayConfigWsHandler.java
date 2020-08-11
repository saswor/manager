package com.manage.project.system.base;

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
 * 支付配置websocket处理类
 * @author xufeng
 *
 */
@Component
public class PayConfigWsHandler extends TextWebSocketHandler {
	private static Logger log = LoggerFactory.getLogger(PayConfigWsHandler.class);  
	
	private static AtomicInteger count = new AtomicInteger(0);  
    private static Map<String,WebSocketSession> sessionMap = new HashedMap();
    private static Map<String, String> corp_session = new HashMap<String, String>();	//	商户与session的关系
    private static Map<String, String> user_session = new HashMap<String, String>();	//	用户与session的关系

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
        log.info("支付配置websocket连接被建立：" + "corpId=" + corpId + "user=" + session.getPrincipal().getName());
        
        sessionMap.put(session.getPrincipal().getName(),session);
        corp_session.put(session.getPrincipal().getName(), corpId);
        user_session.put(session.getPrincipal().getName(), userId);
        count.incrementAndGet();	// 连接数+1
        
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {        
        super.afterConnectionClosed(session, status);
        
        log.info("支付配置websocket断开：" + session.getPrincipal().getName());
        sessionMap.remove(session.getPrincipal().getName());
        corp_session.remove(session.getPrincipal().getName());
        user_session.remove(session.getPrincipal().getName());
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
    			log.info("发送支付结果给用户:"+userId+",信息:"+msg+",时间:"+DateUtils.getTime());
    		}
    	}
	}
}
