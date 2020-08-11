package com.manage.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.manage.project.system.base.PayConfigWsHandler;
import com.manage.project.system.index.IndexWsHandler;

import java.util.concurrent.Executors;

@Configuration
@EnableWebSocket
public class WsConfigure implements WebSocketConfigurer {
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		System.out.println("==========================注册websocket处理实例===========");
        registry.addHandler(indexWsHandler(), "/system/indexWebsocket").setAllowedOrigins("*");
        //支付配置websocket
        registry.addHandler(payConfigWsHandler(), "/system/payConfigWebsocket").setAllowedOrigins("*");
	}

	@Bean
    public IndexWsHandler indexWsHandler()
    {
        return new IndexWsHandler();
    }
	
	@Bean
    public PayConfigWsHandler payConfigWsHandler()
    {
        return new PayConfigWsHandler();
    }

    @Bean
    public TaskScheduler taskScheduler() {
        return new ConcurrentTaskScheduler(Executors.newSingleThreadScheduledExecutor());
    }
}
