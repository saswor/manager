import request from '@/utils/request'
import axios from 'axios'
// let getUrl() = process.env.API_HOST//''///manage

import Vue from 'vue'
import {getUrl} from '@/utils/config'
// function getUrl(){
//   return Vue.prototype.ApiUrl;
// }
/*
    销售统计接口
*/
export function getOrderCollect(params) {//销售统计接口
  return request({
    url: getUrl()+'/orderCollect/totalReview',
    method: 'get',
    params
  })
}
/*
    营收分析
*/
export function getIncomeAnalyze(params) {
  return request({
    url: getUrl()+'/orderCollect/incomeAnalyze',
    method: 'get',
    params
  })
}
/*
     订单明细接口
*/
export function getOrderDetail(params) {
  return request({
    url: getUrl()+'/orderCollect/orderDetail',
    method: 'get',
    params
  })
}
/*
      今日销售线路排行接口
*/
export function getShowLineRank(params) {
  return request({
    url: getUrl()+'/saleAnalyze/showLineRank',
    method: 'get',
    params
  })
}
/*
       今日销售点位排行接口
*/
export function getShowPointRank(params) {
  return request({
    url: getUrl()+'/saleAnalyze/showPointRank',
    method: 'get',
    params
  })
}
/*
        今日销售商品排行接口
*/
export function getShowProductRank(params) {
  return request({
    url: getUrl()+'/saleAnalyze/showProductRank',
    method: 'get',
    params
  })
}
/*
   线路销售额报表接口
*/
export function getLineSaleList(params) {
  return request({
    url: getUrl()+'/report/lineSaleList',
    method: 'get',
    params
  })
}
/*
   点位销售额报表接口
*/
export function getPointSaleList(params) {
  return request({
    url: getUrl()+'/report/pointSaleList',
    method: 'get',
    params
  })
}

//添加区域
export const addArea = params =>{
	  return axios.post(getUrl() + `/vendingDistrict/add`, params).then(res => res.data)
}
//导出线路
export const exportLine = params =>{
    return axios.post(getUrl() + `/report/lineReportExport`, params).then(res => res.data)
}
//导出点位
export const exportPoint = params =>{
    return axios.post(getUrl() + `/report/pointReportExport`, params).then(res => res.data)
}
//导出订单明细
export const exportOrder = params =>{
    return axios.post(getUrl() + `/orderCollect/exportExcel`, params).then(res => res.data)
}


/*
    销售线路排行接口
*/
export function getLineRank(params) {
  return request({
    url: getUrl()+'/saleAnalyze/lineRank',
    method: 'get',
    params
  })
}
/*
    销售点位排行接口
*/
export function getPointRank(params) {
  return request({
    url: getUrl()+'/saleAnalyze/pointRank',
    method: 'get',
    params
  })
}
/*
    销售商品排行接口
*/
export function getProductRank(params) {
  return request({
    url: getUrl()+'/saleAnalyze/productRank',
    method: 'get',
    params
  })
}