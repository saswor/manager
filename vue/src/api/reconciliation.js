import request from '@/utils/request'
import axios from 'axios'
// let getUrl() = process.env.API_HOST//''///manage

import Vue from 'vue'
import {getUrl} from '@/utils/config'
// function getUrl(){
//   return Vue.prototype.ApiUrl;
// }
/*
    补货对账列表显示接口
*/
export function getStatementSupplyList(params) {
  return request({
    url: getUrl()+'/statementSupply/list',
    method: 'get',
    params
  })
}
/*
    补货对账详情显示接口
*/
export function getStatementSupplyDetails(params) {
  return request({
    url: getUrl()+'/statementSupply/detail',
    method: 'get',
    params
  })
}
// 对账明细列表显示接口
export function getStatementProductList(params) {
  return request({
    url: getUrl()+'/statementProduct/list',
    method: 'get',
    params
  })
}

// 对账结果显示接口
export function getStatementProductDetail(params) {
  return request({
    url: getUrl()+'/statementSupply/detail',
    method: 'get',
    params
  })
}
//导入对账信息excel
export const importExcel = params =>{
    return axios.post(getUrl() + `/statement/import`, params).then(res => res.data)
}
//
//结算
export const balance = params =>{
    return axios.post(getUrl() + `/statementSupply/balance`, params).then(res => res.data)
}

//订单对账列表显示接口
export function getOrderApplyList(params) {
  return request({
    url: getUrl()+'/orderApply/list',
    method: 'get',
    params
  })
}
/*
    订单对账详情显示接口
*/
export function getOrderApplyetails(params) {
  return request({
    url: getUrl()+'/orderApply/detail',
    method: 'get',
    params
  })
}
//添加补货配置
export const addSupplyConfig = params =>{
	  return axios.post(getUrl() + `/supply/supplyConfig/add`, params).then(res => res.data)
}
//退款
export const returnMoney = params =>{
    return axios.post(getUrl() + `/orderApply/returnMoney`, params).then(res => res.data)
}

//导出订单报表
export const exportOrder = params =>{
    return axios.post(getUrl() + `/orderApply/export`, params).then(res => res.data)
}

// 重新出货
export const reOrderApply = params => {
  return axios.post(getUrl() + `/orderApply/reOrderApply`, params).then(res => res.data)
}