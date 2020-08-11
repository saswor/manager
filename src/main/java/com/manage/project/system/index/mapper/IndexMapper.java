package com.manage.project.system.index.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.manage.project.system.index.domain.ReportBoard;
import com.manage.project.system.index.domain.ReportDsale;
import com.manage.project.system.index.domain.ReportOsale;
import com.manage.project.system.index.vo.OneMonthReviewVo;
import com.manage.project.system.index.vo.RegionLonLatVo;
import com.manage.project.system.index.domain.ReportMsale;

/**
 * 首页 数据层
 * 
 */
public interface IndexMapper
{
    /**
     * 根据托管公司编号查询首页运营总览
     * 
     * @param corpId 托管公司编号
     * @return 仪表盘概要统计报表
     */
    public ReportBoard selectReportBoardById(String corpId);
    
    /**
     * 根据时间段取得某托管公司的月营收分析
     * @param corpId
     * @param byear
     * @param eyear
     * @param bmonth
     * @param emonth
     * @return
     */
    public List<ReportMsale> selectReportMsaleByDate(@Param("corpId") String corpId, @Param("list") List<String> list);
    
    /**
     * 根据时间段取得某托管公司的近一月总览
     * @param corpId
     * @param byear
     * @param eyear
     * @param bmonth
     * @param emonth
     * @param bday
     * @param eday
     * @return	totalSaleNum该时间段销售量, totalSale该时间段销售额， totalProfit该时间段净利润
     */
    public List<OneMonthReviewVo> selectSDsaleByDate(@Param("corpId") String corpId, @Param("list") List<String> list);
    
    /**
     * 取某天的分段销售数据
     * @param corpId
     * @param byear
     * @param eyear
     * @param bmonth
     * @param emonth
     * @param bday
     * @param eday
     * @return
     */
    public List<ReportDsale> selectTodayReportDsale(@Param("corpId") String corpId, @Param("syear") int syear, @Param("smonth") int smonth
    		, @Param("sday") int sday);
    
    
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
    public List<ReportDsale> selectTodayReportZhi(@Param("syear") int syear, @Param("smonth") int smonth, @Param("sday") int sday);
    
    /**
     * 取得某天点位销售额前10
     * @param corpId
     * @param syear
     * @param smonth
     * @param sday
     * @return
     */
    public List<ReportOsale> getTodayLineTop10(@Param("corpId") String corpId, @Param("syear") int syear, @Param("smonth") int smonth
    		, @Param("sday") int sday,@Param("saleType") String saleType);
    
    /**
     * 根据时间段取得所有公司的月营收分析
     * @param byear
     * @param eyear
     * @param bmonth
     * @param emonth
     * @return
     */
    public List<ReportMsale> selectReportMsaleForZhi(List<String> months);

    /**
     * 查询今日运营总览
     * 
     * @param corpId 公司编号
     * @return
     */
	public OneMonthReviewVo selectTodayReview(@Param("corpId") String corpId, @Param("syear") int syear, @Param("smonth") int smonth
    		, @Param("sday") int sday);

	
	/**
	 * 查询当前月的首页营收分析
	 * 
	 * @param curYear
	 * @param curMonth
	 * @param corpId
	 * @return
	 */
	public ReportMsale selectCurMonthReportMsale(@Param("curYear") int curYear, @Param("curMonth")  int curMonth,@Param("corpId")  String corpId);
    
//    /**
//     * 点位地图使用，查询某商户下的点位在哪些国家
//     * @param corpId
//     * @return
//     */
//    public List<RegionLonLatVo> findOnlyCountryForPoint(@Param("corpId") String corpId);
//    
//    /**
//     * 点位地图使用，查询某商户下的点位在哪些国家
//     * @param corpId
//     * @return
//     */
//    public List<RegionLonLatVo> findOnlyProvinceForPoint(@Param("corpId") String corpId, @Param("country") String country);
//    
//    /**
//     * 点位地图使用，查询某商户下的点位在哪些国家
//     * @param corpId
//     * @return
//     */
//    public List<RegionLonLatVo> findOnlyCityForPoint(@Param("corpId") String corpId, @Param("province") String province);
//    
//    /**
//     * 点位地图使用，查询某商户下的点位在哪些国家
//     * @param corpId
//     * @return
//     */
//    public List<RegionLonLatVo> findOnlyDistrictForPoint(@Param("corpId") String corpId, @Param("city") String city);
}
