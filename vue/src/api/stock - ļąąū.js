import request from '@/utils/request'
import axios from 'axios'
let base = process.env.API_HOST///manage
//系统库存
export function getList(params) {
  return request({
    url: base+'/stockProduct/list',
    method: 'get',
    params
  })
}
//系统库存
export function getStockWarehouseListSurvey(params) {
  return request({
    url: base+'/stockWarehouse/listSurvey',
    method: 'get',
    params
  })
}
//生成采购单列表--操作
export function getStockWarehouseList(params) {
  return request({
    url: base+'/stockWarehouse/list',
    method: 'get',
    params
  })
}
//仓库管理-采购管理
export function getStockPurchaseList(params) {
  return request({
    url: base+'/stockPurchase/list',
    method: 'get',
    params
  })
}
//仓库库存列表
export function stockInfoList(params) {
  return request({
    url: base+'/stockInfo/list',
    method: 'get',
    params
  })
}
//仓库库存-生成采购单
export function getAllByStockIdList(params) {
  return request({
    url: base+'/stockWarehouse/getAllByStockId',
    method: 'get',
    params
  })
}
//仓库库存-提交采购单
export const addStockPurchase = params =>{
    return axios.post(base + `/stockPurchase/add`, params).then(res => res.data)
}
//审核列表
export function getStockPpurchaseList(params) {
  return request({
    url: base+'/stockPpurchase/list',
    method: 'get',
    params
  })
}
//审核
export const addCheck = params =>{
    return axios.post(base + `/stockPurchase/check`, params).then(res => res.data)
}
//采购记录
export function getStockInboundList(params) {
  return request({
    url: base+'/stockInbound/list',
    method: 'get',
    params
  })
}
//采购记录详情
export function getPinboundList(params) {
  return request({
    url: base+'/stockPinbound/list',
    method: 'get',
    params
  })
}

//入库管理
export function getStockInlist(params) {
  return request({
    url: base+'/stockInbound/stockInlist',
    method: 'get',
    params
  })
}
//入库详情
export function getStockDetailList(params) {
  return request({
    url: base+'/stockPinbound/list',
    method: 'get',
    params
  })
}
//提交入库
export const submitInbound = params =>{
    return axios.post(base + `/stockInbound/submitInbound`, params).then(res => res.data)
}
//仓库管理列表
export function getStockInfoList(params) {
  return request({
    url: base+'/stockInfo/list',
    method: 'get',
    params
  })
}
//新增仓库
export const addStockInfo = params =>{
    return axios.post(base + `/stockInfo/add`, params).then(res => res.data)
}
//编辑仓库
export const editStockInfo = params =>{
    return axios.post(base + `/stockInfo/edit`, params).then(res => res.data)
}
//删除仓库
export const removeStockInfo = params =>{
    return axios.post(base + `/stockInfo/remove`, params).then(res => res.data)
}
//出库管理
export function getStockOutlist(params) {
  return request({
    url: base+'/supply/supplyOut/list',
    method: 'get',
    params
  })
}
//出库详情
export function supplyoutDetail(params) {
  return request({
    url: base+'/supply/supplyOut/detail',
    method: 'get',
    params
  })
}
//修改出库
export const supplyoutEdit = params =>{
    return axios.post(base + `/supply/supplyOut/edit`, params).then(res => res.data)
}

//历史价格
export function supplyOutListAll(params) {
  return request({
    url: base+'/supply/supplyOut/listAll',
    method: 'get',
    params
  })
}
//系统库存导出

export const supplExportExcel = params =>{
    return axios.post(base + `/stockProduct/exportExcel`, params).then(res => res.data)
}

//审核详情
export function stockPurchaseDetail(params) {
  return request({
    url: base+'/stockPurchase/detail',
    method: 'get',
    params
  })
}
//入库详情
export function stockInboundDetail(params) {
  return request({
    url: base+'/stockInbound/detail',
    method: 'get',
    params
  })
}
