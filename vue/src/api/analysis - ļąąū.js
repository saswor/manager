import request from '@/utils/request'
import axios from 'axios'
let base = process.env.API_HOST//''///manage

import Vue from 'vue'
function getUrl(){
  return Vue.prototype.ApiUrl;
}
/*
    线路警告列表
*/
export function getSupplyWarnLine(params) {
  return request({
    url: base+'/supply/supplyWarnLine/list',
    method: 'get',
    params
  })
}
/*
    线路警告详情
*/
export function getSupplyWarnLineDetail(params) {
  return request({
    url: base+'/supply/supplyWarnLine/detail',
    method: 'get',
    params
  })
}

/*
    点位警告列表
*/
export function getSupplyWarnPoint(params) {
  return request({
    url: base+'/supply/supplyWarnPoint/list',
    method: 'get',
    params
  })
}

//获取一个机型里得货道信息

export function vendingModelList(params) {
  return request({
    url: base+'/vendingModel/get',
    method: 'get',
    params
  })
}


/*
    补货配置查询接口
*/ 
export function supplyConfigList(params) {
  return request({
    url: base+'/supply/supplyConfig/list',
    method: 'get',
    params
  })
}
//添加区域
export const abcd = params =>{
	  return axios.post(base + `/vendingDistrict/add`, params).then(res => res.data)
}
