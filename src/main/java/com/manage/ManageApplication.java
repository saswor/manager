package com.manage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 启动程序
 * 
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@MapperScan("com.manage.project.*.*.mapper")
@EnableCaching
@ServletComponentScan
//@EnableScheduling
public class ManageApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(ManageApplication.class, args);
    }
    
//    private CorsConfiguration buildConfig() {  
//        CorsConfiguration corsConfiguration = new CorsConfiguration();  
//        corsConfiguration.addAllowedOrigin("*");  
//        corsConfiguration.addAllowedHeader("*");  
//        corsConfiguration.addAllowedMethod("*");  
//
//
//l
//        return corsConfiguration;  
//    }  
//
//    /** 
//     * 跨域过滤器 
//     * @return 
//     */  
//    @Bean  
//    public CorsFilter corsFilter() {  
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();  
//        source.registerCorsConfiguration("/**", buildConfig()); // 4  
//        return new CorsFilter(source);  
//    } 
}