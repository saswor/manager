package com.manage.project.system.index.service;

import java.io.IOException;
import java.util.List;

import com.manage.project.system.index.domain.ReportBoard;
import com.manage.project.system.index.domain.ReportDsale;
import com.manage.project.system.index.vo.OneMonthReviewVo;
import com.manage.project.system.index.vo.OperateReviewVo;
import com.manage.project.system.index.domain.ReportMsale;
import com.manage.project.system.index.domain.ReportOsale;

/**
 * 首页 服务层
 * 
 */
public interface IndexService {
	/**
     * 根据托管公司编号查询首页运营总览
     * 
     * @param corpId 托管公司编号
     * @return 仪表盘概要统计报表
     */
    public ReportBoard selectReportBoardById(String corpId);
    
    /**
     * 从缓存获取首页运营总览
     * @return
     */
    public OperateReviewVo getOperateReviewFromCache(String corpId);
    
    /**
     * 获取今日总览
     * @return
     */
    public OneMonthReviewVo getTodayResume();
    
    /**
     * 获取7日总览
     * @return
     */
    public OneMonthReviewVo get7dResume();
    
    /**
     * 获取30日总览
     * @return
     */
    public OneMonthReviewVo get30dResume();
    
    /**
     * 向所有人发送首页运营总览
     */
    public void sendOperateReviewToAll(String corpId) throws IOException;
    
    /**
     * 根据时间段取得某托管公司的月营收分析
     * @param corpId
     * @param byear
     * @param eyear
     * @param bmonth
     * @param emonth
     * @return
     */
    public List<ReportMsale> selectReportMsaleByDate(String corpId, List<String> months);
    
    /**
     * 根据时间段取得某托管公司的近一月总览
     * @param corpId		托管公司
     * @param days	日期列表
     * @return	totalSaleNum该时间段销售量, totalSale该时间段销售额， totalProfit该时间段净利润
     */
    public OneMonthReviewVo selectSDsaleByDate(String corpId, List<String> days);

    /**
     * 取某天的分段销售数据
     * @param corpId	托管公司
     * @param byear
     * @param eyear
     * @param bmonth
     * @param emonth
     * @param bday
     * @param eday
     * @return
     */
    public List<ReportDsale> selectTodayReportDsale(String corpId, int syear, int smonth
    		, int sday);
    
    /**
     * 取得今天点位销售额前10
     * @param corpId	托管公司
     * @return
     */
    public List<ReportOsale> getTodayLineTop10(String corpId);
    
    /**
     * 取得今天商品销售额前10
     * @param corpId	托管公司
     * @return
     */
    public List<ReportOsale> getTodayProductTop10(String corpId);
    
    /**
     * 发送近一月总览，向全体发送websocket消息
     */
	public void sendOneMonthToAll(String corpId) throws IOException;
	
	/**
     * 根据时间段取得所有公司的月营收分析
     * @param months  [{'2018-9'},{'2018-8'}]
     * @return
     */
    public List<ReportMsale> selectReportMsaleForZhi(List<String> months);
    
    /**
     * 统计所有商户某天的分段销售数据
     * @param byear
     * @param eyear
     * @param bmonth
     * @param emonth
     * @param bday
     * @param eday
     * @return
     */
    public List<ReportDsale> selectTodayReportZhi(int syear, int smonth, int sday); 

    /**
     * 查询今日运营总览
     * 
     * @param corpId 公司编号
     * @return
     */
	public OneMonthReviewVo selectTodayReview(String corpId);
}
