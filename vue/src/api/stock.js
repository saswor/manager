import request from '@/utils/request'
import axios from 'axios'
// let getUrl() = process.env.API_HOST///manage

import Vue from 'vue'
import {getUrl} from '@/utils/config'
// function getUrl(){
//   return Vue.prototype.ApiUrl;
// }
//系统库存
export function getList(params) {
  return request({
    url: getUrl()+'/stockProduct/list',
    method: 'get',
    params
  })
}
//系统库存
export function getStockWarehouseListSurvey(params) {
  return request({
    url: getUrl()+'/stockWarehouse/listSurvey',
    method: 'get',
    params
  })
}
//生成采购单列表--操作
export function getStockWarehouseList(params) {
  return request({
    url: getUrl()+'/stockWarehouse/list',
    method: 'get',
    params
  })
}
//仓库管理-采购管理
export function getStockPurchaseList(params) {
  return request({
    url: getUrl()+'/stockPurchase/list',
    method: 'get',
    params
  })
}
//仓库库存列表
export function stockInfoList(params) {
  return request({
    url: getUrl()+'/stockInfo/list',
    method: 'get',
    params
  })
}
//仓库库存-生成采购单
export function getAllByStockIdList(params) {
  return request({
    url: getUrl()+'/stockWarehouse/getAllByStockId',
    method: 'get',
    params
  })
}
//仓库库存-提交采购单
export const addStockPurchase = params =>{
    return axios.post(getUrl() + `/stockPurchase/add`, params).then(res => res.data)
}
//导出仓库库存
export const exportStockWarehouse = params =>{
  return axios.post(getUrl() + `/stockWarehouse/exportExcel`, params).then(res => res.data)
}
//审核列表
export function getStockPpurchaseList(params) {
  return request({
    url: getUrl()+'/stockPpurchase/list',
    method: 'get',
    params
  })
}
//审核
export const addCheck = params =>{
    return axios.post(getUrl() + `/stockPurchase/check`, params).then(res => res.data)
}
//采购记录
export function getStockInboundList(params) {
  return request({
    url: getUrl()+'/stockInbound/list',
    method: 'get',
    params
  })
}
//采购记录详情
export function getPinboundList(params) {
  return request({
    url: getUrl()+'/stockPinbound/list',
    method: 'get',
    params
  })
}

//入库管理
export function getStockInlist(params) {
  return request({
    url: getUrl()+'/stockInbound/stockInlist',
    method: 'get',
    params
  })
}
//入库详情
export function getStockDetailList(params) {
  return request({
    url: getUrl()+'/stockPinbound/list',
    method: 'get',
    params
  })
}
//提交入库
export const submitInbound = params =>{
    return axios.post(getUrl() + `/stockInbound/submitInbound`, params).then(res => res.data)
}
//仓库管理列表
export function getStockInfoList(params) {
  return request({
    url: getUrl()+'/stockInfo/list',
    method: 'get',
    params
  })
}
//新增仓库
export const addStockInfo = params =>{
    return axios.post(getUrl() + `/stockInfo/add`, params).then(res => res.data)
}
//编辑仓库
export const editStockInfo = params =>{
    return axios.post(getUrl() + `/stockInfo/edit`, params).then(res => res.data)
}
//删除仓库
export const removeStockInfo = params =>{
    return axios.post(getUrl() + `/stockInfo/remove`, params).then(res => res.data)
}
//出库管理
export function getStockOutlist(params) {
  return request({
    url: getUrl()+'/supply/supplyOut/list',
    method: 'get',
    params
  })
}
//出库详情
export function supplyoutDetail(params) {
  return request({
    url: getUrl()+'/supply/supplyOut/detail',
    method: 'get',
    params
  })
}
//修改出库
export const supplyoutEdit = params =>{
    return axios.post(getUrl() + `/supply/supplyOut/edit`, params).then(res => res.data)
}

//历史价格
export function supplyOutListAll(params) {
  return request({
    url: getUrl()+'/supply/supplyOut/listAll',
    method: 'get',
    params
  })
}
//系统库存导出

export const supplExportExcel = params =>{
    return axios.post(getUrl() + `/stockProduct/exportExcel`, params).then(res => res.data)
}

//审核详情
export function stockPurchaseDetail(params) {
  return request({
    url: getUrl()+'/stockPurchase/detail',
    method: 'get',
    params
  })
}
//入库详情
export function stockInboundDetail(params) {
  return request({
    url: getUrl()+'/stockInbound/detail',
    method: 'get',
    params
  })
}

// 重新入库
export const restocking = params =>{
  return axios.post(getUrl() + `/supply/supplyOut/repeatInbound`, params).then(res => res.data)
}

//入库冲正
export const submitReInbound = params =>{
  return axios.post(getUrl() + `/stockInbound/releaseInbound`, params).then(res => res.data)
}

//额外采购
export const extraInbound = params =>{
  return axios.post(getUrl() + `/stockPurchase/extraPurchase`, params).then(res => res.data)
}

//追加出库
export const extraOut = params =>{
  return axios.post(getUrl() + `/supply/supplyOut/extraOut`, params).then(res => res.data)
}

//仓库库存导出

export const warehouseExportExcel = params =>{
  return axios.post(getUrl() + `/stockWarehouse/exportExcel`, params).then(res => res.data)
}

//采购记录导出

export const stockPurchaseExportExcel = params =>{
  return axios.post(getUrl() + `/stockPurchase/exportExcel`, params).then(res => res.data)
}
