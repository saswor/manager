import request from '@/utils/request'
import axios from 'axios'
// let getUrl() = process.env.API_HOST///manage

import Vue from 'vue'
import {getUrl} from '@/utils/config'
// function getUrl(){
//   return Vue.prototype.ApiUrl;
// }

//获取全部区域
export function getVendingDistrict(params) {
  return request({
    url: getUrl()+'/vendingDistrict/listAll',
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
//查询点位
export function getVendingPoint(params) {
  return request({
    url: getUrl()+'/vendingPoint/listAll',
    method: 'get',
    params
  })
}
//查询售卖机根据区域id
export function getVending(params) {
  return request({
    url: getUrl()+'/vending/listAll',
    method: 'get',
    params
  })
}
//售卖机查询可以根据线路区域点位等
export function getVendingList(params) {
  return request({
    url: getUrl()+'/vending/selectNVendingList',
    method: 'get',
    params
  })
}

//查询全部仓库
export function getStockInfo(params) {
  return request({
    url: getUrl()+'/stockInfo/list',
    method: 'get',
    params
  })
}
//	获取告警类型、告警级别、告警状态接口
export function getWaringSelect(params) {
  return request({
    url: getUrl()+'/dict/data/list',
    method: 'get',
    params
  })
}

//查询商品分类列表
export function getProductClassifyList(params) {
  return request({
    url: getUrl()+'/productClassify/list',
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
//查询补货员
export function getUserByName(params) {
  return request({
    url: getUrl()+'/user/getUserByName',
    method: 'get',
    params
  })
}
//托管公司查询
export function getCompany(params) {
  return request({
    url: getUrl()+'/corp/vaguelist',
    method: 'get',
    params
  })
}

//查商品列表
export function productInfo(params) {
  return request({
    url: getUrl()+'/productInfo/list',
    method: 'get',
    params
  })
}
//根据点位查询货柜及货道信息接口
export function CabinetByPointId(params) {
  return request({
    url: getUrl()+'/vending/getCabinetByPointId',
    method: 'get',
    params
  })
}
// 查询一个模板信息接口

export function vendingPconfigGet(params) {
  return request({
    url: getUrl()+'/vendingPconfig/get',
    method: 'get',
    params
  })
}
//厂家》货柜类型》机型
export function selectCabinetTypeByFactoryId(params) {
  return request({
    url: getUrl()+'/vending/selectCabinetTypeByFactoryId',
    method: 'get',
    params
  })
}
export function selectDeviceByCabinetType(params) {
  return request({
    url: getUrl()+'/vending/selectDeviceByCabinetType',
    method: 'get',
    params
  })
}
//厂家》货柜类型》机型  end//////