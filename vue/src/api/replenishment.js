import request from '@/utils/request'
import axios from 'axios'
// let getUrl() = process.env.API_HOST//''///manage

import Vue from 'vue'
import {getUrl} from '@/utils/config'
// function getUrl(){
//   return Vue.prototype.ApiUrl;
// }
/*
    线路警告列表
*/
export function getSupplyWarnLine(params) {
  return request({
    url: getUrl()+'/supply/supplyWarnLine/list',
    method: 'get',
    params
  })
}
/*
    线路警告详情
*/
export function getSupplyWarnLineDetail(params) {
  return request({
    url: getUrl()+'/supply/supplyWarnLine/detail',
    method: 'get',
    params
  })
}

/*
    点位警告列表
*/
export function getSupplyWarnPoint(params) {
  return request({
    url: getUrl()+'/supply/supplyWarnPoint/list',
    method: 'get',
    params
  })
}
//获得售货机信息
export function getVendingViewByLogid(params) {
  return request({
    url: getUrl()+'/vending/getVendingViewByLogid',
    method: 'get',
    params
  })
}
//获取一个机型里得货道信息

export function vendingModelList(params) {
  return request({
    url: getUrl()+'/vendingModel/get',
    method: 'get',
    params
  })
}


//告警查看货道信息
export function selectVendingLanepByCabinetLogid(params) {
  return request({
    url: getUrl()+'/vendingLanep/selectVendingLanepByCabinetId',
    method: 'get',
    params
  })
}

/*
    补货配置查询接口
*/ 
export function supplyConfigList(params) {
  return request({
    url: getUrl()+'/supply/supplyConfig/list',
    method: 'get',
    params
  })
}
/*
    补货记录查询接口
*/ 
export function supplyorderList(params) {
  return request({
    url: getUrl()+'/supply/supplyOrder/list',
    method: 'get',
    params
  })
}
/*
    补货记录查询详情
*/ 
export function supplyOrderDetail(params) {
  return request({
    url: getUrl()+'/supply/supplyOrder/detail',
    method: 'get',
    params
  })
}

//添加补货配置
export const addSupplyConfig = params =>{
	  return axios.post(getUrl() + `/supply/supplyConfig/add`, params).then(res => res.data)
}
//编辑补货配置
export const editSupplyConfig = params =>{
    return axios.post(getUrl() + `/supply/supplyConfig/edit`, params).then(res => res.data)
}

//删除补货配置
export const delSupplyConfig = params =>{
    return axios.post(getUrl() + `/supply/supplyConfig/remove`, params).then(res => res.data)
}
//查补货配置详情接口
export function getSupplyOrderDetail(params) {
  return request({
    url: getUrl()+'/supply/supplyConfig/detail',
    method: 'get',
    params
  })
}
// 根据点位查询货柜及货道信息接口
export function getCabinetByPointId(params) {
  return request({
    url: getUrl()+'/vending/getCabinetByPointId',
    method: 'get',
    params
  })
}

// 获取站点补货单记录接口
export function SupplyWarnPointDetai(params) {
  return request({
    url: getUrl()+'/supply/supplyWarnPoint/detail',
    method: 'get',
    params
  })
}

//生成补货单接口
export const addsupplyOrder = params =>{
    return axios.post(getUrl() + `/supply/supplyOrder/add`, params).then(res => res.data)
}

//查看点位详情基础信息
export function warnDetail(params) {
  return request({
    url: getUrl()+'/supply/supplyWarnPoint/warnDetail',
    method: 'get',
    params
  })
}
//查看线路详情基础信息
export function LinewarnDetail(params) {
  return request({
    url: getUrl()+'/supply/supplyWarnLine/warnDetail',
    method: 'get',
    params
  })
}
//选择关联线路
export function guanLineList(params) {
  return request({
    url: getUrl()+'/vendingLine/list',
    method: 'get',
    params
  })
}

//导出
export const exportExcel = params =>{
    return axios.post(getUrl() + `/supply/supplyWarnPoint/exportExcel`, params).then(res => res.data)
}
//生成补货单线路告警
export const addLineSupplyOrder = params =>{
    return axios.post(getUrl() + `/supply/supplyOrder/addLine`, params).then(res => res.data)
}
//导出线路补货单报表
export const exportLineSupplyOrder = params =>{
    return axios.post(getUrl() + `/supply/supplyOrder/export`, params).then(res => res.data)
}

//导出线路补货单详情
export const exportSupplyOrderDetail = params =>{
  return axios.post(getUrl() + `/supply/supplyOrder/exportDetail`, params).then(res => res.data)
}