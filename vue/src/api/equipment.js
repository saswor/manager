import request from '@/utils/request'
import axios from 'axios'
// let getUrl() = process.env.API_HOST///manage

import Vue from 'vue'
import {getUrl} from '@/utils/config'
// function getUrl(){
//   return Vue.prototype.ApiUrl;
// }


/*
		售后机管理
*/ 
export function getRevenue(params) {
  return request({
    url: getUrl()+'/index/selectMSaleSummry',
    method: 'get',
    params
  })
}


//新增售货机
export const addMachine = params =>{
	  return axios.post(getUrl() + `/vending/add`, params).then(res => res.data)
}
//查询所有省市区
export function getChild(params) {
  return request({
    url: getUrl()+'/dispatch/getChild',
    method: 'get',
    params
  })
}
//厂家、货柜类型、网络类型、售卖状态查询接口
/*
字典类型,厂商：sys_factory；货柜类型sys_cabinet_type；网络类型：sys_net_work；售卖状态：sys_sell_state
*/ 

export function getMachineSelect(params) {
  return request({
    url: getUrl()+'/dict/data/list',
    method: 'get',
    params
  })
}
//查询机型
export function getVendingModel(params) {
  return request({
    url: getUrl()+'/vendingModel/list',
    method: 'get',
    params
  })
}
//查询点位
export function getPositionList(params) {
  return request({
    url: getUrl()+'/vendingPoint/list',
    method: 'get',
    params
  })
}
//售卖机查询
export function getSelectNVendingList(params) {
  return request({
    url: getUrl()+'/vending/selectNVendingList',
    method: 'get',
    params
  })
}

//删除售卖机

export const delMachine = params =>{
    return axios.post(getUrl() + `/vending/remove`, params).then(res => res.data)
}
//删除售卖机

export const delMachineDel = params =>{
    return axios.post(getUrl() + `/vending/delete`, params).then(res => res.data)
}
//获取一个机型查询接口

export function vendingModelList(params) {
  return request({
    url: getUrl()+'/vendingModel/get',
    method: 'get',
    params
  })
}
//查商品
export function getCommodity(params) {
  return request({
    url: getUrl()+'/productInfo/list',
    method: 'get',
    params
  })
}

//售货机详情
export function getVendingView(params) {
  return request({
    url: getUrl()+'/vending/getVendingView',
    method: 'get',
    params
  })
}
//概要  vending/getVendingNum
//售货机详情
export function getVendingNum(params) {
  return request({
    url: getUrl()+'/vending/getVendingNum',
    method: 'get',
    params
  })
}

//查询所有线路
export function getVendingLine(params) {
  return request({
    url: getUrl()+'/vendingLine/listAll',
    method: 'get',
    params
  })
}
//获取全部区域
export function getVendingDistrict(params) {
  return request({
    url: getUrl()+'/vendingDistrict/listAll',
    method: 'get',
    params
  })
}


//编辑获得售货机信息
export function getVendingViewByLogid(params) {
  return request({
    url: getUrl()+'/vending/getVendingViewByLogid',
    method: 'get',
    params
  })
}

//修改售后机查货道信息
export function selectVendingLanepByCabinetLogid(params) {
  return request({
    url: getUrl()+'/vendingLanep/selectVendingLanepByCabinetId',
    method: 'get',
    params
  })
}

//保存编辑

export const editMachine = params =>{
    return axios.post(getUrl() + `/vending/edit`, params).then(res => res.data)
}


/**********
                  机型管理
***********/
//厂商列表
export function findFactoryList(params) {
  return request({
    url: getUrl()+'/dict/data/findFactoryList',
    method: 'get',
    params
  })
}
//查询机型列表
export function getVendingModelList(params) {
  return request({
    url: getUrl()+'/vendingModel/list',
    method: 'get',
    params
  })
}
//删除机型


export const delvendingModel = params =>{
    return axios.post(getUrl() + `/vendingModel/remove`, params).then(res => res.data)
}
//新增机型
export const addVendingModel = params =>{
    return axios.post(getUrl() + `/vendingModel/add`, params).then(res => res.data)
}
//编辑机型
export const editVendingModel = params =>{
    return axios.post(getUrl() + `/vendingModel/edit`, params).then(res => res.data)
}

/******************
                配货模板
**************/ 


//查询机型列表
export function getVendingPconfigList(params) {
  return request({
    url: getUrl()+'/vendingPconfig/list',
    method: 'get',
    params
  })
}
// 查询一个模板信息接口
export function getVendingPconfig(params) {
  return request({
    url: getUrl()+'/vendingPconfig/get',
    method: 'get',
    params
  })
}
//根据机型code构造货道接口
export function getLaneList(params) {
  return request({
    url: getUrl()+'/vendingModel/getLaneList',
    method: 'get',
    params
  })
}
//  新增模板
export const addVendingPconfig = params =>{
    return axios.post(getUrl() + `/vendingPconfig/add`, params).then(res => res.data)
}
//  编辑模板
export const editVendingPconfig = params =>{
    return axios.post(getUrl() + `/vendingPconfig/edit`, params).then(res => res.data)
}
//  删除模板
export const delVendingPconfig = params =>{
    return axios.post(getUrl() + `/vendingPconfig/remove`, params).then(res => res.data)
}
// 查询一个模板信息接口

export function vendingPconfigGet(params) {
  return request({
    url: getUrl()+'/vendingPconfig/get',
    method: 'get',
    params
  })
}

//关联查询列表接口
export function selectRelationVending(params) {
  return request({
    url: getUrl()+'/vending/selectRelationVending',
    method: 'get',
    params
  })
}


// 获取补货记录历史交易记录等信息接口(外边几个数字)
export function VendingRecord(params) {
  return request({
    url: getUrl()+'/vending/getVendingRecordBySiteId',
    method: 'get',
    params
  })
}
// 获取补货记录历史交易记录等信息接口(外边几个数字)
export function SupplyOrderBySiteId(params) {
  return request({
    url: getUrl()+'/vending/getSupplyOrderBySiteId',
    method: 'get',
    params
  })
}


//获取历史交易总览
export function selectTotalSale(params) {
  return request({
    url: getUrl()+'/orderApply/selectTotalSale',
    method: 'get',
    params
  })
}
//获取历史交易列表
export function orderApplyList(params) {
  return request({
    url: getUrl()+'/orderApply/selectOrderList',
    method: 'get',
    params
  })
}

//地图查看详情
export function getMapDetal(params) {
  return request({
    url: getUrl()+'/vending/getVendingViewByPoint',
    method: 'get',
    params
  })
}
//地图查看详情-下面四块
export function MapVendingRecordByPoint(params) {
  return request({
    url: getUrl()+'/vending/getVendingRecordByPoint',
    method: 'get',
    params
  })
}
//补货记录详情
export function getRecord_details(params) {
  return request({
    url: getUrl()+'/supply/supplyVorder/detail',
    method: 'get',
    params
  })
}

//  注册售货机
export const register = params =>{
    return axios.post(getUrl() + `/vending/register`, params).then(res => res.data)
}

//修正货道库存
export const submitStockingEdit = params =>{
  return axios.post(getUrl() + `/vendingLsdiffer/siteInventory`, params).then(res => res.data)
}

//获取售货机库存差异修正列表
export function vendingLsdifferList(params) {
  return request({
    url: getUrl()+'/vendingLsdiffer/selectVendingLsdifferList',
    method: 'get',
    params
  })
}

//获取售货机库存差异修正详情
export function vendingLsdifferDetail(params) {
  return request({
    url: getUrl()+'/vendingLsdiffer/getVendingLsdifferView',
    method: 'get',
    params
  })
}

//获取货道商品出货信息
export function getOrderBoxList(params) {
  return request({
    url: getUrl()+'/vendingLsdiffer/selectOrderBoxList',
    method: 'get',
    params
  })
}

//修正货道库存
export const updateOrderOutState = params => {
  return axios.post(getUrl() + `/vendingLsdiffer/orderLane`, params).then(res => res.data)
}

//获取货道商品出货信息详情
export function getOrderBoxDetail(params) {
  return request({
    url: getUrl()+'/vendingLsdiffer/getOrderBoxView',
    method: 'get',
    params
  })
}

// 修正货道库存
export const updateVendingProduct = params => {
  return axios.post(getUrl() + `/vending/updateProduct`, params).then(res => res.data)
}
//查询设备信息列表
export function getVendingStateList(params) {
  return request({
    url: getUrl()+'/vendingState/list',
    method: 'get',
    params
  })
}
//查询设备详细信息
export function getVendingStateDetails(params) {
  return request({
    url: getUrl()+'/vendingState/detail',
    method: 'get',
    params
  })
}
//查询设备命令
export function getVendingCmd(params) {
  return request({
    url: getUrl()+'/vendingCmd/list',
    method: 'get',
    params
  })
}
//远程控制设备
export const submitRemoteController = params => {
  return axios.post(getUrl() + `/vendingCmd/remoteController`, params).then(res => res.data)
}
//查询升级信息列表
export function getVendingUpgradeList(params) {
  return request({
    url: getUrl()+'/vendingUpgrade/list',
    method: 'get',
    params
  })
}
//查询升级信息详情
export function upgradeInfo(params) {
  return request({
    url: getUrl()+'/vendingUpgrade/detail',
    method: 'get',
    params
  })
}
//新增升级信息
export const addVendingUpgrade = params => {
  return axios.post(getUrl() + `/vendingUpgrade/add`, params).then(res => res.data)
}
//修改升级信息
export const updateVendingUpgrade = params => {
  return axios.post(getUrl() + `/vendingUpgrade/edit`, params).then(res => res.data)
}
//删除升级信息
export const deleteVendingUpgrade = params => {
  return axios.post(getUrl() + `/vendingUpgrade/remove`, params).then(res => res.data)
}
//查询升级任务下发售货机列表
export function upgradeTaskList(params) {
  return request({
    url: getUrl()+'/vendingUpgradeTask/list',
    method: 'get',
    params
  })
}
//查询版本信息列表
export function getVendingVersionList(params) {
  return request({
    url: getUrl()+'/softwareVersion/list',
    method: 'get',
    params
  })
}
//查询版本信息详情
export function getVendingVersionDetails(params) {
  return request({
    url: getUrl()+'/softwareVersion/detail',
    method: 'get',
    params
  })
}
//新增版本信息
export const addVendingVersion = params => {
  return axios.post(getUrl() + `/softwareVersion/add`, params).then(res => res.data)
}
//修改版本信息
export const updateVendingVersion = params => {
  return axios.post(getUrl() + `/softwareVersion/edit`, params).then(res => res.data)
}
//删除版本信息
export const deleteVendingVersion = params => {
  return axios.post(getUrl() + `/softwareVersion/remove`, params).then(res => res.data)
}

//售货机日志文件列表
export function vendingLogfileList(params) {
  return request({
    url: getUrl()+'/vendingLogfile/list',
    method: 'get',
    params
  })
}

//通知终端上传售货机日志文件
export const addVendingLogfile = params => {
  return axios.post(getUrl() + `/vendingLogfile/add`, params).then(res => res.data)
}

//下载日志文件
export const downloadVendingLogfile = params => {
  return axios.post(getUrl() + `/vendingLogfile/download`, params).then(res => res.data)
}

//导出配货模板
export const exportVendingPconfig = params =>{
  return axios.post(getUrl() + `/vendingPconfig/export`, params).then(res => res.data)
}

// 导出设备状态模板
export const exportVendingState = params =>{
  return axios.post(getUrl() + `/vendingState/exportVendingState`, params).then(res => res.data)
}

//导出售货机
export const exportVendingExcel = params =>{
  return axios.post(getUrl() + `/vending/exportExcel`, params).then(res => res.data)
}
//下载售货机模板
export const downLoadVendingExcelModel = params =>{
  return axios.post(getUrl() + `/vending/downLoadExcelModel`, params).then(res => res.data)
}
// 锁定货道
export const lockLane = params =>{
  return axios.post(getUrl() + `/vending/lockLane`, params).then(res => res.data)
}
// 解锁货道
export const unlockLane = params =>{
  return axios.post(getUrl() + `/vending/unlockLane`, params).then(res => res.data)
}

//查询售货机列表
export function getVendingList(params) {
  return request({
    url: getUrl()+'/vending/list',
    method: 'get',
    params
  })
}