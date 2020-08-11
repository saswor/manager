package com.manage.project.system.job;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 定时任务配置类
 * @author xufeng
 *
 */
@Configuration
public class QuartzConfig {
	@Bean
    public JobDetail indexQuartzDetail(){
        return JobBuilder.newJob(IndexJobBean.class).withIdentity("indexJobBean").storeDurably().build();
    }

	/**
	 * 每天凌晨一点更新所有缓存
	 * @return
	 */
    @Bean
    public Trigger indexQuartzTrigger(){
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 1 * * ?");
        return TriggerBuilder.newTrigger().forJob(indexQuartzDetail())
                .withIdentity("indexJobBean")
                .withSchedule(scheduleBuilder)
                .build();
    }

    @Bean
    public JobDetail supplyWarnQuartzDetail(){
        return JobBuilder.newJob(IndexJobBean.class).withIdentity("indexJobBean").storeDurably().build();
    }

    /**
     * 每隔一段时间检查线路的库存情况并及时计算库存等级
     * @return
     */
    @Bean
    public Trigger supplyWarnQuartzTrigger(){
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/30 * * * * ?");
        return TriggerBuilder.newTrigger().forJob(indexQuartzDetail())
                .withIdentity("indexJobBean")
                .withSchedule(scheduleBuilder)
                .build();
    }
}
