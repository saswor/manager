package com.manage.project.system.index.service;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.manage.common.utils.CacheUtils;
import com.manage.common.utils.DateUtils;
import com.manage.project.common.Constant;
import com.manage.project.system.index.IndexWsHandler;
import com.manage.project.system.index.domain.ReportBoard;
import com.manage.project.system.index.domain.ReportDsale;
import com.manage.project.system.index.mapper.IndexMapper;
import com.manage.project.system.index.vo.IndexDataVo;
import com.manage.project.system.index.vo.OneMonthReviewVo;
import com.manage.project.system.index.vo.OperateReviewVo;
import com.manage.project.system.index.domain.ReportMsale;
import com.manage.project.system.index.domain.ReportOsale;

/**
 * 首页 服务层实现
 * 
 */
@Service
public class IndexServiceImpl implements IndexService {
	private static final Logger log = LoggerFactory.getLogger(IndexServiceImpl.class);
	
	@Autowired
    private IndexMapper indexMapper;
	
	@Override
	public ReportBoard selectReportBoardById(String corpId) {
		ReportBoard reportBoard = indexMapper.selectReportBoardById(corpId);
		return reportBoard;
	}
	
	@Override
	public OperateReviewVo getOperateReviewFromCache(String corpId) {
	    return (OperateReviewVo)CacheUtils.get("serverCache",Constant.INDEX_SUMMARY_TOTALREVIEW_CACHE_+corpId);
	}

	@Override
	public OneMonthReviewVo getTodayResume() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OneMonthReviewVo get7dResume() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OneMonthReviewVo get30dResume() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendOperateReviewToAll(String corpId) throws IOException {
		OperateReviewVo operateReviewVo = this.getOperateReviewFromCache(corpId);
        IndexDataVo review1 = new IndexDataVo();
        review1.setType(Constant.INDEX_DATA_TYPE_1);
        review1.setData(operateReviewVo);
        IndexWsHandler.sendMessageToCorp(JSON.toJSONString(review1), corpId);
	}
	
	@Override
	public void sendOneMonthToAll(String corpId) throws IOException {		
		List<OneMonthReviewVo> list = (List<OneMonthReviewVo>)CacheUtils.get("serverCache",Constant.INDEX_SUMMARY_ONEMONTH_CACHE_+corpId);
        IndexDataVo review1 = new IndexDataVo();
        review1.setType(Constant.INDEX_DATA_TYPE_2);
        review1.setData(list);
        IndexWsHandler.sendMessageToCorp(JSON.toJSONString(review1), corpId);
	}

	/**
     * 根据时间段取得某托管公司的月营收分析
     * @param corpId
     * @param byear
     * @param eyear
     * @param bmonth
     * @param emonth
     * @return
     */
	@Override
    public List<ReportMsale> selectReportMsaleByDate(String corpId, List<String> months) {
    	List<ReportMsale> list = indexMapper.selectReportMsaleByDate(corpId, months);
		Calendar calendar = Calendar.getInstance();
		int curYear=calendar.get(Calendar.YEAR);
		int curMonth=calendar.get(Calendar.MONTH)+1;
		for (int i = 0; i < months.size(); i++) {
			String month = months.get(i);
			if(month!=null&&curYear==Integer.parseInt(month.split("-")[0])&&curMonth==Integer.parseInt(month.split("-")[1])) {
				ReportMsale curReportMsale = indexMapper.selectCurMonthReportMsale(curYear,curMonth,corpId);
				if(curReportMsale!=null) {
					list.add(curReportMsale);
				}
			}
		}
		return list;
    }
	
	/**
     * 根据时间段取得所有公司的月营收分析
     * @param byear
     * @param eyear
     * @param bmonth
     * @param emonth
     * @return
     */
	@Override
    public List<ReportMsale> selectReportMsaleForZhi(List<String> months) {
		List<ReportMsale> list = indexMapper.selectReportMsaleForZhi(months);
    	return list;
	}

	/**
     * 根据时间段取得某托管公司的近一月总览
     * @param corpId		托管公司
     * @param byear	开始年
     * @param eyear	结束年
     * @param bmonth	开始月
     * @param emonth	结束月
     * @param bday	开始日
     * @param eday	结束日
     * @return	totalSaleNum该时间段销售量, totalSale该时间段销售额， totalProfit该时间段净利润
     */
	@Override
    public OneMonthReviewVo selectSDsaleByDate(String corpId, List<String> days) {
		List<OneMonthReviewVo> list = indexMapper.selectSDsaleByDate(corpId, days);
		if (list == null || list.isEmpty()) {
			return null;
		}		
		return list.get(0);
	}

	@Override
	public List<ReportDsale> selectTodayReportDsale(String corpId, int syear, int smonth, int sday) {
		return indexMapper.selectTodayReportDsale(corpId, syear, smonth, sday);
	}

	/**
	 * 
	 */
	@Override
	public List<ReportOsale> getTodayLineTop10(String corpId) {
		Date now = new Date();
		List<ReportOsale> list = indexMapper.getTodayLineTop10(corpId, DateUtils.getYYYY(now), DateUtils.getMM(now), DateUtils.getDD(now), Constant.SALE_TYPE_POINT);
		return list;
	}

	@Override
	public List<ReportOsale> getTodayProductTop10(String corpId) {
		Date now = new Date();
		List<ReportOsale> list = indexMapper.getTodayLineTop10(corpId, DateUtils.getYYYY(now), DateUtils.getMM(now), DateUtils.getDD(now), Constant.SALE_TYPE_PRODUCT);
		return list;
	}

	@Override
	public List<ReportDsale> selectTodayReportZhi(int syear, int smonth, int sday) {
		return indexMapper.selectTodayReportZhi(syear, smonth, sday);
	}

	/**
     * 查询今日运营总览
     * 
     * @param corpId 公司编号
     * @return
     */
	@Override
	public OneMonthReviewVo selectTodayReview(String corpId) {
		Calendar calendar = Calendar.getInstance();
		int syear=calendar.get(Calendar.YEAR);
		int smonth=calendar.get(Calendar.MONTH)+1;
		int sday=calendar.get(Calendar.DAY_OF_MONTH);
		return indexMapper.selectTodayReview(corpId, syear, smonth, sday); 
	}

	
}
