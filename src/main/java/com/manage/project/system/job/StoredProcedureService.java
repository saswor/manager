package com.manage.project.system.job;

import com.manage.common.utils.DateUtils;
import com.manage.project.system.index.IndexWsHandler;
import com.manage.project.system.util.SystemUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;


/**
 * @ClassName SupplyWarnTaskService
 * @Dscription TDD
 * @Author 邬思杰
 * @Version 1.0
 * @Date 14:34$ 2018-10-12$
 **/
@Service
@EnableScheduling
public class StoredProcedureService {
    private Logger log=LoggerFactory.getLogger(StoredProcedureService.class);
    /**
     *  每30秒生成一次基表统计数据
     */
    @Scheduled(cron ="0/30 * * * * ?")
    public void genOrderBase(){
        log.info("执行生成统计基表数据");
        Map<String,Object> map=new HashMap();
        SystemUtil.getCallGenOrderBase(map);
    }
    /**
     *  每天凌晨0点生成昨天的点位、线路统计数据
     */
    @Scheduled(cron ="0 1 0 * * ?")
    public void genStatisticalDay(){
        log.info("执行生成站点和线路每天统计数据");
        Calendar calendar=Calendar.getInstance();
        Map<String,Object> map=new HashMap();
        calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)-1);
        map.put("year",calendar.get(Calendar.YEAR));
        map.put("month",calendar.get(Calendar.MONTH)+1);
        map.put("day",calendar.get(Calendar.DAY_OF_MONTH));
        SystemUtil.getCallPointDay(map);
        SystemUtil.getCallLineDay(map);
    }
    /**
     *  每月1号0时生成前一个月的点位、线路统计数据
     */
    @Scheduled(cron ="0 1 0 1 * ?")
    public void genStatisticalMonth(){
        log.info("执行生成每月线路和站点的统计数据");
        Calendar calendar=Calendar.getInstance();
        Map<String,Object> map=new HashMap();
        map.put("year",calendar.get(Calendar.MONTH)==0?(calendar.get(Calendar.YEAR)-1):(calendar.get(Calendar.YEAR)));
        map.put("month",calendar.get(Calendar.MONTH)==0?12:calendar.get(Calendar.MONTH));
        map.put("day",calendar.get(Calendar.DATE));
        SystemUtil.getCallPointMonth(map);
        SystemUtil.getCallLineMonth(map);
    }
    /**
     *  每年1号0时生成前一个月的点位、线路统计数据
     */
    @Scheduled(cron ="0 1 0 1 1 ?")
    public void genStatisticallYear(){
        log.info("执行生成每年的站点和线路的统计数据");
        Calendar calendar=Calendar.getInstance();
        Map<String,Object> map=new HashMap();
        map.put("year",calendar.get(Calendar.YEAR)-1);
        map.put("month",calendar.get(Calendar.MONTH)+1);
        map.put("day",calendar.get(Calendar.DATE));
        SystemUtil.getCallPointYear(map);
        SystemUtil.getCallLineYear(map);
    }
    /**
     *  每天凌晨0点生成昨天的商品和点位时间段统计数据
     */
    @Scheduled(cron ="0 1 0 * * ?")
//    @Scheduled(cron ="0 0/1 * * * ?")
    public void genStatisticalDayQuantum(){
        log.info("执行时间段商品和站点统计数据");
        Calendar calendar=Calendar.getInstance();
        Map<String,Object> map=new HashMap();
        calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)-1);
        map.put("year",calendar.get(Calendar.YEAR));
        map.put("month",calendar.get(Calendar.MONTH)+1);
        map.put("day",calendar.get(Calendar.DATE));
        List<TimeQuantum> list=new ArrayList<>();
        list.add(new TimeQuantum("00:00:00","01:00:00"));
        list.add(new TimeQuantum("01:00:00","02:00:00"));
        list.add(new TimeQuantum("02:00:00","03:00:00"));
        list.add(new TimeQuantum("03:00:00","04:00:00"));
        list.add(new TimeQuantum("04:00:00","05:00:00"));
        list.add(new TimeQuantum("05:00:00","06:00:00"));
        list.add(new TimeQuantum("06:00:00","07:00:00"));
        list.add(new TimeQuantum("07:00:00","08:00:00"));
        list.add(new TimeQuantum("08:00:00","09:00:00"));
        list.add(new TimeQuantum("09:00:00","10:00:00"));
        list.add(new TimeQuantum("10:00:00","11:00:00"));
        list.add(new TimeQuantum("11:00:00","12:00:00"));
        list.add(new TimeQuantum("12:00:00","13:00:00"));
        list.add(new TimeQuantum("13:00:00","14:00:00"));
        list.add(new TimeQuantum("14:00:00","15:00:00"));
        list.add(new TimeQuantum("15:00:00","16:00:00"));
        list.add(new TimeQuantum("16:00:00","17:00:00"));
        list.add(new TimeQuantum("17:00:00","18:00:00"));
        list.add(new TimeQuantum("18:00:00","19:00:00"));
        list.add(new TimeQuantum("19:00:00","20:00:00"));
        list.add(new TimeQuantum("20:00:00","21:00:00"));
        list.add(new TimeQuantum("21:00:00","22:00:00"));
        list.add(new TimeQuantum("22:00:00","23:00:00"));
        list.add(new TimeQuantum("23:00:00","24:00:00"));
        String dateTime=DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD,calendar.getTime());
        Calendar stime=Calendar.getInstance();
        stime.setTime(DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS,dateTime+" 00:00:00"));
        for(TimeQuantum timeQuantum:list){
            String stimeStr=dateTime+" "+timeQuantum.stime;
            String etimeStr=dateTime+" "+timeQuantum.etime;
            Calendar startDate=Calendar.getInstance();
            startDate.setTime(DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS,stimeStr));
            Calendar endDate=Calendar.getInstance();
            endDate.setTime(DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS,etimeStr));
            int sminute= (int) ((startDate.getTimeInMillis()-stime.getTimeInMillis())/60000);
            int eminute= (int) ((endDate.getTimeInMillis()-stime.getTimeInMillis())/60000);
            map.put("stime",stimeStr.substring(11, 16));
            map.put("etime",etimeStr.substring(11, 16));
            map.put("sMinute",sminute);
            map.put("eMinute",eminute);
            SystemUtil.getCallVtimeDay(map);
            SystemUtil.getCallPtimeDay(map);
        }
    }
    /**
     *  每周星期1的0时生成前一周的商品和点位的时间段统计数据
     */
    @Scheduled(cron ="0 1 0 ? * MON")
    public void genStatisticalWeekQuantum(){
        log.info("执行生成每周点位和商品统计数据");
        Calendar calendar=Calendar.getInstance();
        Map<String,Object> map=new HashMap();
        map.put("year",calendar.get(Calendar.YEAR));
        map.put("week",calendar.get(Calendar.WEEK_OF_YEAR)-1);
        List<TimeQuantum> list=new ArrayList<>();
        list.add(new TimeQuantum("00:00:00","01:00:00"));
        list.add(new TimeQuantum("01:00:00","02:00:00"));
        list.add(new TimeQuantum("02:00:00","03:00:00"));
        list.add(new TimeQuantum("03:00:00","04:00:00"));
        list.add(new TimeQuantum("04:00:00","05:00:00"));
        list.add(new TimeQuantum("05:00:00","06:00:00"));
        list.add(new TimeQuantum("06:00:00","07:00:00"));
        list.add(new TimeQuantum("07:00:00","08:00:00"));
        list.add(new TimeQuantum("08:00:00","09:00:00"));
        list.add(new TimeQuantum("09:00:00","10:00:00"));
        list.add(new TimeQuantum("10:00:00","11:00:00"));
        list.add(new TimeQuantum("11:00:00","12:00:00"));
        list.add(new TimeQuantum("12:00:00","13:00:00"));
        list.add(new TimeQuantum("13:00:00","14:00:00"));
        list.add(new TimeQuantum("14:00:00","15:00:00"));
        list.add(new TimeQuantum("15:00:00","16:00:00"));
        list.add(new TimeQuantum("16:00:00","17:00:00"));
        list.add(new TimeQuantum("17:00:00","18:00:00"));
        list.add(new TimeQuantum("18:00:00","19:00:00"));
        list.add(new TimeQuantum("19:00:00","20:00:00"));
        list.add(new TimeQuantum("20:00:00","21:00:00"));
        list.add(new TimeQuantum("21:00:00","22:00:00"));
        list.add(new TimeQuantum("22:00:00","23:00:00"));
        list.add(new TimeQuantum("23:00:00","24:00:00"));
        String dateTime=DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD,calendar.getTime());
        Calendar stime=Calendar.getInstance();
        stime.setTime(DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS,dateTime+" 00:00:00"));
        for(TimeQuantum timeQuantum:list){
            String stimeStr=dateTime+" "+timeQuantum.stime;
            String etimeStr=dateTime+" "+timeQuantum.etime;
            Calendar startDate=Calendar.getInstance();
            startDate.setTime(DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS,stimeStr));
            Calendar endDate=Calendar.getInstance();
            endDate.setTime(DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS,etimeStr));
            int sminute= (int) ((startDate.getTimeInMillis()-stime.getTimeInMillis())/60000);
            int eminute= (int) ((endDate.getTimeInMillis()-stime.getTimeInMillis())/60000);
            map.put("stime",stimeStr.substring(11, 16));
            map.put("etime",etimeStr.substring(11, 16));
            map.put("sMinute",sminute);
            map.put("eMinute",eminute);
            SystemUtil.getCallVtimeWeek(map);
            SystemUtil.getCallPtimeWeek(map);
        }
    }
//    /**
//     *  每月1号0时生成前一个月的商品和点位的时间段统计数据
//     */
//    @Scheduled(cron ="0 1 0 1 * ?")
//    public void genStatisticalMonthQuantum(){
//        log.info("执行生成每月点位和商品时间段统计数据");
//        Calendar calendar=Calendar.getInstance();
//        Map<String,Object> map=new HashMap();
//        map.put("year",calendar.get(Calendar.MONTH)==0?(calendar.get(Calendar.YEAR)-1):(calendar.get(Calendar.YEAR)));
//        map.put("month",calendar.get(Calendar.MONTH)==0?12:calendar.get(Calendar.MONTH));
//        List<TimeQuantum> list=new ArrayList<>();
//        list.add(new TimeQuantum("00:00:00","01:00:00"));
//        list.add(new TimeQuantum("01:00:00","02:00:00"));
//        list.add(new TimeQuantum("02:00:00","03:00:00"));
//        list.add(new TimeQuantum("03:00:00","04:00:00"));
//        list.add(new TimeQuantum("04:00:00","05:00:00"));
//        list.add(new TimeQuantum("05:00:00","06:00:00"));
//        list.add(new TimeQuantum("06:00:00","07:00:00"));
//        list.add(new TimeQuantum("07:00:00","08:00:00"));
//        list.add(new TimeQuantum("08:00:00","09:00:00"));
//        list.add(new TimeQuantum("09:00:00","10:00:00"));
//        list.add(new TimeQuantum("10:00:00","11:00:00"));
//        list.add(new TimeQuantum("11:00:00","12:00:00"));
//        list.add(new TimeQuantum("12:00:00","13:00:00"));
//        list.add(new TimeQuantum("13:00:00","14:00:00"));
//        list.add(new TimeQuantum("14:00:00","15:00:00"));
//        list.add(new TimeQuantum("15:00:00","16:00:00"));
//        list.add(new TimeQuantum("16:00:00","17:00:00"));
//        list.add(new TimeQuantum("17:00:00","18:00:00"));
//        list.add(new TimeQuantum("18:00:00","19:00:00"));
//        list.add(new TimeQuantum("19:00:00","20:00:00"));
//        list.add(new TimeQuantum("20:00:00","21:00:00"));
//        list.add(new TimeQuantum("21:00:00","22:00:00"));
//        list.add(new TimeQuantum("22:00:00","23:00:00"));
//        list.add(new TimeQuantum("23:00:00","24:00:00"));
//        String dateTime=DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD,calendar.getTime());
//        Calendar stime=Calendar.getInstance();
//        stime.setTime(DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS,dateTime+" 00:00:00"));
//        for(TimeQuantum timeQuantum:list){
//            String stimeStr=dateTime+" "+timeQuantum.stime;
//            String etimeStr=dateTime+" "+timeQuantum.etime;
//            Calendar startDate=Calendar.getInstance();
//            startDate.setTime(DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS,stimeStr));
//            Calendar endDate=Calendar.getInstance();
//            endDate.setTime(DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS,etimeStr));
//            int sminute= (int) ((startDate.getTimeInMillis()-stime.getTimeInMillis())/60000);
//            int eminute= (int) ((endDate.getTimeInMillis()-stime.getTimeInMillis())/60000);
//            map.put("stime",stimeStr.substring(11, 16));
//            map.put("etime",etimeStr.substring(11, 16));
//            map.put("sMinute",sminute);
//            map.put("eMinute",eminute);
//            SystemUtil.getCallVtimeMonth(map);
//            SystemUtil.getCallPtimeMonth(map);
//        }
//    }
	 /**
	  *  每天凌晨生成本月的商品和点位的时间段统计数据
	  */
	 @Scheduled(cron ="0 7 0 * * ?")
//    @Scheduled(cron ="0 0/1 * * * ?")
	 public void genStatisticalMonthQuantum(){
	     log.info("执行生成每月点位和商品时间段统计数据");
	     LocalDate now = LocalDate.now();
	     //获取前一天日期
	     LocalDate lastDay = now.minusDays(1);
	     Map<String,Object> map=new HashMap<String,Object>();
	     map.put("year",lastDay.getYear());
	     map.put("month",lastDay.getMonth());
	     List<TimeQuantum> list=new ArrayList<>();
	     list.add(new TimeQuantum("00:00:00","01:00:00"));
	     list.add(new TimeQuantum("01:00:00","02:00:00"));
	     list.add(new TimeQuantum("02:00:00","03:00:00"));
	     list.add(new TimeQuantum("03:00:00","04:00:00"));
	     list.add(new TimeQuantum("04:00:00","05:00:00"));
	     list.add(new TimeQuantum("05:00:00","06:00:00"));
	     list.add(new TimeQuantum("06:00:00","07:00:00"));
	     list.add(new TimeQuantum("07:00:00","08:00:00"));
	     list.add(new TimeQuantum("08:00:00","09:00:00"));
	     list.add(new TimeQuantum("09:00:00","10:00:00"));
	     list.add(new TimeQuantum("10:00:00","11:00:00"));
	     list.add(new TimeQuantum("11:00:00","12:00:00"));
	     list.add(new TimeQuantum("12:00:00","13:00:00"));
	     list.add(new TimeQuantum("13:00:00","14:00:00"));
	     list.add(new TimeQuantum("14:00:00","15:00:00"));
	     list.add(new TimeQuantum("15:00:00","16:00:00"));
	     list.add(new TimeQuantum("16:00:00","17:00:00"));
	     list.add(new TimeQuantum("17:00:00","18:00:00"));
	     list.add(new TimeQuantum("18:00:00","19:00:00"));
	     list.add(new TimeQuantum("19:00:00","20:00:00"));
	     list.add(new TimeQuantum("20:00:00","21:00:00"));
	     list.add(new TimeQuantum("21:00:00","22:00:00"));
	     list.add(new TimeQuantum("22:00:00","23:00:00"));
	     list.add(new TimeQuantum("23:00:00","24:00:00"));
	     String dateTime=lastDay.toString();
	     Calendar stime=Calendar.getInstance();
	     stime.setTime(DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS,dateTime+" 00:00:00"));
	     for(TimeQuantum timeQuantum:list){
	         String stimeStr=dateTime+" "+timeQuantum.stime;
	         String etimeStr=dateTime+" "+timeQuantum.etime;
	         Calendar startDate=Calendar.getInstance();
	         startDate.setTime(DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS,stimeStr));
	         Calendar endDate=Calendar.getInstance();
	         endDate.setTime(DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS,etimeStr));
	         int sminute= (int) ((startDate.getTimeInMillis()-stime.getTimeInMillis())/60000);
	         int eminute= (int) ((endDate.getTimeInMillis()-stime.getTimeInMillis())/60000);
	         map.put("stime",stimeStr.substring(11, 16));
	         map.put("etime",etimeStr.substring(11, 16));
	         map.put("sMinute",sminute);
	         map.put("eMinute",eminute);
	         SystemUtil.getCallVtimeMonth(map);
	         SystemUtil.getCallPtimeMonth(map);
	     }
	 }
	 /**
	  *  每天凌晨生成本季度的商品和点位的时间段统计数据
	  */
	 @Scheduled(cron ="0 8 0 * * ?")
//     @Scheduled(cron ="0 0/1 * * * ?")
	 public void genStatisticalQuarterQuantum(){
	     log.info("执行生成季度点位和商品时间段统计数据");
	     LocalDate now = LocalDate.now();
	     //获取前一天日期
	     LocalDate lastDay = now.minusDays(1);
	     Map<String,Object> map=new HashMap<String,Object>();
	     map.put("year",lastDay.getYear());
	     int month = lastDay.getMonthValue();
	     map.put("quarter",(month-1)/3+1);
	     List<TimeQuantum> list=new ArrayList<>();
	     list.add(new TimeQuantum("00:00:00","01:00:00"));
	     list.add(new TimeQuantum("01:00:00","02:00:00"));
	     list.add(new TimeQuantum("02:00:00","03:00:00"));
	     list.add(new TimeQuantum("03:00:00","04:00:00"));
	     list.add(new TimeQuantum("04:00:00","05:00:00"));
	     list.add(new TimeQuantum("05:00:00","06:00:00"));
	     list.add(new TimeQuantum("06:00:00","07:00:00"));
	     list.add(new TimeQuantum("07:00:00","08:00:00"));
	     list.add(new TimeQuantum("08:00:00","09:00:00"));
	     list.add(new TimeQuantum("09:00:00","10:00:00"));
	     list.add(new TimeQuantum("10:00:00","11:00:00"));
	     list.add(new TimeQuantum("11:00:00","12:00:00"));
	     list.add(new TimeQuantum("12:00:00","13:00:00"));
	     list.add(new TimeQuantum("13:00:00","14:00:00"));
	     list.add(new TimeQuantum("14:00:00","15:00:00"));
	     list.add(new TimeQuantum("15:00:00","16:00:00"));
	     list.add(new TimeQuantum("16:00:00","17:00:00"));
	     list.add(new TimeQuantum("17:00:00","18:00:00"));
	     list.add(new TimeQuantum("18:00:00","19:00:00"));
	     list.add(new TimeQuantum("19:00:00","20:00:00"));
	     list.add(new TimeQuantum("20:00:00","21:00:00"));
	     list.add(new TimeQuantum("21:00:00","22:00:00"));
	     list.add(new TimeQuantum("22:00:00","23:00:00"));
	     list.add(new TimeQuantum("23:00:00","24:00:00"));
	     String dateTime=lastDay.toString();
	     Calendar stime=Calendar.getInstance();
	     stime.setTime(DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS,dateTime+" 00:00:00"));
	     for(TimeQuantum timeQuantum:list){
	         String stimeStr=dateTime+" "+timeQuantum.stime;
	         String etimeStr=dateTime+" "+timeQuantum.etime;
	         Calendar startDate=Calendar.getInstance();
	         startDate.setTime(DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS,stimeStr));
	         Calendar endDate=Calendar.getInstance();
	         endDate.setTime(DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS,etimeStr));
	         int sminute= (int) ((startDate.getTimeInMillis()-stime.getTimeInMillis())/60000);
	         int eminute= (int) ((endDate.getTimeInMillis()-stime.getTimeInMillis())/60000);
	         map.put("stime",stimeStr.substring(11, 16));
	         map.put("etime",etimeStr.substring(11, 16));
	         map.put("sMinute",sminute);
	         map.put("eMinute",eminute);
	         SystemUtil.getCallVtimeQuarter(map);
	     }
	 }
	 /**
	  *  每天凌晨生成半年的商品和点位的时间段统计数据
	  */
	 @Scheduled(cron ="0 9 0 * * ?")
//     @Scheduled(cron ="0 0/1 * * * ?")
	 public void genStatisticalHalfYearQuantum(){
	     log.info("执行生成半年点位和商品时间段统计数据");
	     LocalDate now = LocalDate.now();
	     //获取前一天日期
	     LocalDate lastDay = now.minusDays(1);
	     Map<String,Object> map=new HashMap<String,Object>();
	     map.put("year",lastDay.getYear());
	     int month = lastDay.getMonthValue();
	     map.put("halfYear",(month-1)/6+1);
	     List<TimeQuantum> list=new ArrayList<>();
	     list.add(new TimeQuantum("00:00:00","01:00:00"));
	     list.add(new TimeQuantum("01:00:00","02:00:00"));
	     list.add(new TimeQuantum("02:00:00","03:00:00"));
	     list.add(new TimeQuantum("03:00:00","04:00:00"));
	     list.add(new TimeQuantum("04:00:00","05:00:00"));
	     list.add(new TimeQuantum("05:00:00","06:00:00"));
	     list.add(new TimeQuantum("06:00:00","07:00:00"));
	     list.add(new TimeQuantum("07:00:00","08:00:00"));
	     list.add(new TimeQuantum("08:00:00","09:00:00"));
	     list.add(new TimeQuantum("09:00:00","10:00:00"));
	     list.add(new TimeQuantum("10:00:00","11:00:00"));
	     list.add(new TimeQuantum("11:00:00","12:00:00"));
	     list.add(new TimeQuantum("12:00:00","13:00:00"));
	     list.add(new TimeQuantum("13:00:00","14:00:00"));
	     list.add(new TimeQuantum("14:00:00","15:00:00"));
	     list.add(new TimeQuantum("15:00:00","16:00:00"));
	     list.add(new TimeQuantum("16:00:00","17:00:00"));
	     list.add(new TimeQuantum("17:00:00","18:00:00"));
	     list.add(new TimeQuantum("18:00:00","19:00:00"));
	     list.add(new TimeQuantum("19:00:00","20:00:00"));
	     list.add(new TimeQuantum("20:00:00","21:00:00"));
	     list.add(new TimeQuantum("21:00:00","22:00:00"));
	     list.add(new TimeQuantum("22:00:00","23:00:00"));
	     list.add(new TimeQuantum("23:00:00","24:00:00"));
	     String dateTime=lastDay.toString();
	     Calendar stime=Calendar.getInstance();
	     stime.setTime(DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS,dateTime+" 00:00:00"));
	     for(TimeQuantum timeQuantum:list){
	         String stimeStr=dateTime+" "+timeQuantum.stime;
	         String etimeStr=dateTime+" "+timeQuantum.etime;
	         Calendar startDate=Calendar.getInstance();
	         startDate.setTime(DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS,stimeStr));
	         Calendar endDate=Calendar.getInstance();
	         endDate.setTime(DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS,etimeStr));
	         int sminute= (int) ((startDate.getTimeInMillis()-stime.getTimeInMillis())/60000);
	         int eminute= (int) ((endDate.getTimeInMillis()-stime.getTimeInMillis())/60000);
	         map.put("stime",stimeStr.substring(11, 16));
	         map.put("etime",etimeStr.substring(11, 16));
	         map.put("sMinute",sminute);
	         map.put("eMinute",eminute);
	         SystemUtil.getCallVtimeHalfYear(map);
	     }
	 }
	 /**
	  *  每天凌晨生成本年的商品和点位的时间段统计数据
	  */
	 @Scheduled(cron ="0 10 0 * * ?")
//     @Scheduled(cron ="0 0/1 * * * ?")
	 public void genStatisticalYearQuantum(){
	     log.info("执行生成每年点位和商品时间段统计数据");
	     LocalDate now = LocalDate.now();
	     //获取前一天日期
	     LocalDate lastDay = now.minusDays(1);
	     Map<String,Object> map=new HashMap<String,Object>();
	     map.put("year",lastDay.getYear());
	     List<TimeQuantum> list=new ArrayList<>();
	     list.add(new TimeQuantum("00:00:00","01:00:00"));
	     list.add(new TimeQuantum("01:00:00","02:00:00"));
	     list.add(new TimeQuantum("02:00:00","03:00:00"));
	     list.add(new TimeQuantum("03:00:00","04:00:00"));
	     list.add(new TimeQuantum("04:00:00","05:00:00"));
	     list.add(new TimeQuantum("05:00:00","06:00:00"));
	     list.add(new TimeQuantum("06:00:00","07:00:00"));
	     list.add(new TimeQuantum("07:00:00","08:00:00"));
	     list.add(new TimeQuantum("08:00:00","09:00:00"));
	     list.add(new TimeQuantum("09:00:00","10:00:00"));
	     list.add(new TimeQuantum("10:00:00","11:00:00"));
	     list.add(new TimeQuantum("11:00:00","12:00:00"));
	     list.add(new TimeQuantum("12:00:00","13:00:00"));
	     list.add(new TimeQuantum("13:00:00","14:00:00"));
	     list.add(new TimeQuantum("14:00:00","15:00:00"));
	     list.add(new TimeQuantum("15:00:00","16:00:00"));
	     list.add(new TimeQuantum("16:00:00","17:00:00"));
	     list.add(new TimeQuantum("17:00:00","18:00:00"));
	     list.add(new TimeQuantum("18:00:00","19:00:00"));
	     list.add(new TimeQuantum("19:00:00","20:00:00"));
	     list.add(new TimeQuantum("20:00:00","21:00:00"));
	     list.add(new TimeQuantum("21:00:00","22:00:00"));
	     list.add(new TimeQuantum("22:00:00","23:00:00"));
	     list.add(new TimeQuantum("23:00:00","24:00:00"));
	     String dateTime=lastDay.toString();
	     Calendar stime=Calendar.getInstance();
	     stime.setTime(DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS,dateTime+" 00:00:00"));
	     for(TimeQuantum timeQuantum:list){
	         String stimeStr=dateTime+" "+timeQuantum.stime;
	         String etimeStr=dateTime+" "+timeQuantum.etime;
	         Calendar startDate=Calendar.getInstance();
	         startDate.setTime(DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS,stimeStr));
	         Calendar endDate=Calendar.getInstance();
	         endDate.setTime(DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS,etimeStr));
	         int sminute= (int) ((startDate.getTimeInMillis()-stime.getTimeInMillis())/60000);
	         int eminute= (int) ((endDate.getTimeInMillis()-stime.getTimeInMillis())/60000);
	         map.put("stime",stimeStr.substring(11, 16));
	         map.put("etime",etimeStr.substring(11, 16));
	         map.put("sMinute",sminute);
	         map.put("eMinute",eminute);
	         SystemUtil.getCallVtimeYear(map);
	     }
	 }
    /**
     *  每30秒生成一次商户的销售统计数据
     */
    //@Scheduled(cron ="0/30 * * * * ?")
    public void genReportBoard(){
        log.info("执行生成运营总览统计数据");
        Map<String,Object> map=new HashMap();
        SystemUtil.getCallReportBoard(map);
//        try {
//			IndexWsHandler.sendIndex();
//		} catch (IOException e) {
//			log.error(e.getMessage());
//		}
    }
    /**
     *  每小时生成前一个小时商户的统计信息
     */
    @Scheduled(cron ="0 1 * * * ?")
    public void genReportDsale(){
        log.info("生成rpt_report_dsale报表时间段数据");
        Calendar stime=Calendar.getInstance();
        Calendar calendar=Calendar.getInstance();
        Map<String,Object> map=new HashMap();
        calendar.set(Calendar.HOUR_OF_DAY,calendar.get(Calendar.HOUR_OF_DAY)-1);
        map.put("year",calendar.get(Calendar.YEAR));
        map.put("month",calendar.get(Calendar.MONTH)+1);
        map.put("day",calendar.get(Calendar.DATE));
        String dateTime=DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD,calendar.getTime());
        String mstime=(calendar.get(Calendar.HOUR_OF_DAY)<10?("0"+calendar.get(Calendar.HOUR_OF_DAY)):calendar.get(Calendar.HOUR_OF_DAY))+":00";
        String estime=(stime.get(Calendar.HOUR_OF_DAY)<10?("0"+stime.get(Calendar.HOUR_OF_DAY)):stime.get(Calendar.HOUR_OF_DAY))+":00";
        String stimeStr=dateTime+" "+(calendar.get(Calendar.HOUR_OF_DAY)<10?("0"+calendar.get(Calendar.HOUR_OF_DAY)):calendar.get(Calendar.HOUR_OF_DAY))+":00:00";
        String etimeStr=dateTime+" "+(stime.get(Calendar.HOUR_OF_DAY)<10?("0"+stime.get(Calendar.HOUR_OF_DAY)):stime.get(Calendar.HOUR_OF_DAY))+":00:00";
        log.debug(stimeStr+" "+etimeStr);
        Calendar startDate=Calendar.getInstance();
        startDate.setTime(DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS,stimeStr));
        Calendar endDate=Calendar.getInstance();
        endDate.setTime(DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS,etimeStr));
        Calendar calendarDay=Calendar.getInstance();
        SimpleDateFormat sf=new SimpleDateFormat(DateUtils.YYYY_MM_DD);
        try {
            calendarDay.setTime(sf.parse(dateTime));
        } catch (ParseException e) {
            log.debug("时间转换错误");
        }
        int sminute= (int) ((startDate.getTimeInMillis()-calendarDay.getTimeInMillis())/60000);
        int eminute= (int) ((endDate.getTimeInMillis()-calendarDay.getTimeInMillis())/60000);
        if(eminute==0){
            eminute=1440;
        }
        map.put("stime",mstime);
        map.put("etime",estime);
        map.put("sMinute",sminute);
        map.put("eMinute",eminute);
        log.debug(map.toString());
        SystemUtil.getCallReportDsale(map);
    }
    /**
     *  每月1号0时生成前一个月商户的统计信息
     */
    @Scheduled(cron ="0 1 0 1 * ?")
    public void genReportMsale(){
        log.info("生成每月商户的统计数据");
        Calendar calendar=Calendar.getInstance();
        Map<String,Object> map=new HashMap();
        map.put("year",calendar.get(Calendar.YEAR));
        map.put("month",calendar.get(Calendar.MONTH));
        map.put("day",calendar.get(Calendar.DATE));
        SystemUtil.getCallReportMsale(map);
    }
    /**
     *  每一小时生成一次排行榜数据
     */
    @Scheduled(cron ="0 1 * * * ?")
    public void genReportOsale(){
        log.info("执行生成站点和商品排名前10数据");
        Calendar calendar=Calendar.getInstance();
        Map<String,Object> map=new HashMap();
        map.put("year",calendar.get(Calendar.YEAR));
        map.put("month",calendar.get(Calendar.MONTH)+1);
        map.put("day",calendar.get(Calendar.DATE));
        map.put("beforeNum",10);
        SystemUtil.getCallReportOsale(map);
    }
    
    /**
     *  每一小时生成新每日排行榜数据
     */
    @Scheduled(cron ="0 1 * * * ?")
//    @Scheduled(cron ="0 0/1 * * * ?")
    public void genOsaleDay(){
        log.info("执行生成新站点和商品排名榜数据");
        Calendar calendar=Calendar.getInstance();
        Map<String,Object> map=new HashMap();
        map.put("year",calendar.get(Calendar.YEAR));
        map.put("month",calendar.get(Calendar.MONTH)+1);
        map.put("day",calendar.get(Calendar.DATE));
        SystemUtil.getCallOsaleDay(map);
    }
    
    /**
     *  每天凌晨生成每日排行榜历史数据
     */
    @Scheduled(cron ="0 5 0 * * ?")
//    @Scheduled(cron ="0 0/1 * * * ?")
    public void genOsaleDayHistory(){
        log.info("执行生成新站点和商品排名榜历史数据");
        //获取前一天的日期
        LocalDate now = LocalDate.now();
        LocalDate lastDay = now.minusDays(1);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("year",lastDay.getYear());
        map.put("month",lastDay.getMonthValue());
        map.put("day",lastDay.getDayOfMonth());
        SystemUtil.getCallOsaleDayHistory(map);
    }
    
    /**
     * 每天凌晨生成月度季度年度排行榜数据
     */
    @Scheduled(cron ="30 5 0 * * ?")
//    @Scheduled(cron ="0 0/1 * * * ?")
    public void genOsaleOther(){
        log.info("执行生成月度季度年度排行榜数据");
        LocalDate now = LocalDate.now();
        LocalDate lastDay = now.minusDays(1);
        int month = lastDay.getMonthValue();
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("year",lastDay.getYear());
        map.put("month",month);
        map.put("quarter",(month-1)/3+1);
        SystemUtil.getCallOsaleOther(map);
    }
    
    /**
     *  每月凌晨生成新月度季度年度排行榜历史数据
     */
    @Scheduled(cron ="0 6 0 * * ?")
//    @Scheduled(cron ="0 0/1 * * * ?")
    public void genOsaleOtherHistory(){
        log.info("执行生成月度季度年度排行榜历史数据");
        LocalDate now = LocalDate.now();
        LocalDate lastDay = now.minusDays(1);
        int month = lastDay.getMonthValue();
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("year",lastDay.getYear());
        map.put("month",month);
        map.put("quarter",(month-1)/3+1);
        SystemUtil.getCallOsaleOtherHistory(map);
    }

    class TimeQuantum{
        private String stime;
        private String etime;

        public TimeQuantum(String stime, String etime) {
            this.stime = stime;
            this.etime = etime;
        }

        public String getStime() {
            return stime;
        }

        public void setStime(String stime) {
            this.stime = stime;
        }

        public String getEtime() {
            return etime;
        }

        public void setEtime(String etime) {
            this.etime = etime;
        }
    }
}
