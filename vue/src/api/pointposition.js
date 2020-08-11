import request from '@/utils/request'
import axios from 'axios'
// let getUrl() = process.env.API_HOST//''///manage

import Vue from 'vue'
import {getUrl} from '@/utils/config'
// function getUrl(){
//   return Vue.prototype.ApiUrl;
// }
/*
    区域管理
*/
export function getList(params) {//查询区域
  return request({
    url: getUrl()+'/vendingDistrict/list',
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
//添加区域
export const addArea = params =>{
	  return axios.post(getUrl() + `/vendingDistrict/add`, params).then(res => res.data)
}
//编辑区域
export const editArea = params =>{
    return axios.post(getUrl() + `/vendingDistrict/edit`, params).then(res => res.data)
}

//删除区域
export const delArea = params =>{
    return axios.post(getUrl() + `/vendingDistrict/remove`, params).then(res => res.data)
}


/*
    线路管理
*/

export function getLineList(params) {//查询线路
  return request({
    url: getUrl()+'/vendingLine/list',
    method: 'get',
    params
  })
}
export function getVendingDistrict(params) {//获取全部区域
  return request({
    url: getUrl()+'/vendingDistrict/listAll',
    method: 'get',
    params
  })
}
//添加线路
export const addLine = params =>{
    return axios.post(getUrl() + `/vendingLine/add`, params).then(res => res.data)
}
//编辑线路
export const editLine = params =>{
    return axios.post(getUrl() + `/vendingLine/edit`, params).then(res => res.data)
}

//删除线路
export const delLine = params =>{
    return axios.post(getUrl() + `/vendingLine/remove`, params).then(res => res.data)
}


/*
      点位管理
*/ 
//查询点位
export function getPositionList(params) {
  return request({
    url: getUrl()+'/vendingPoint/list',
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
//查询所有省市区
export function getChild(params) {
  return request({
    url: getUrl()+'/dispatch/getChild',
    method: 'get',
    params
  })
}
//添加点位
export const addPosition = params =>{
    return axios.post(getUrl() + `/vendingPoint/add`, params).then(res => res.data)
}
//编辑点位
export const editPosition = params =>{
    return axios.post(getUrl() + `/vendingPoint/edit`, params).then(res => res.data)
}

//删除点位
export const delPosition = params =>{
    return axios.post(getUrl() + `/vendingPoint/remove`, params).then(res => res.data)
}

//导出区域
export const exportDistrictExcel = params =>{
  return axios.post(getUrl() + `/vendingDistrict/exportExcel`, params).then(res => res.data)
}
//下载区域模板
export const downLoadDistrictExcelModel = params =>{
  return axios.post(getUrl() + `/vendingDistrict/downLoadExcelModel`, params).then(res => res.data)
}
//导出线路
export const exportLineExcel = params =>{
  return axios.post(getUrl() + `/vendingLine/exportExcel`, params).then(res => res.data)
}
//下载线路模板
export const downLoadLineExcelModel = params =>{
  return axios.post(getUrl() + `/vendingLine/downLoadExcelModel`, params).then(res => res.data)
}
//导出点位
export const exportPointExcel = params =>{
  return axios.post(getUrl() + `/vendingPoint/exportExcel`, params).then(res => res.data)
}
//下载点位模板
export const downLoadPointExcelModel = params =>{
  return axios.post(getUrl() + `/vendingPoint/downLoadExcelModel`, params).then(res => res.data)
}
