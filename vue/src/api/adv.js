import request from '@/utils/request'
import axios from 'axios'
// let getUrl() = process.env.API_HOST//''///manage

import Vue from 'vue'
import {getUrl} from '@/utils/config'
// function getUrl(){
//   return Vue.prototype.ApiUrl;
// }
/*
    多媒体库
*/
export function advertMaterialList(params) {//查询区域
  return request({
    url: getUrl()+'/advertMaterial/list',
    method: 'get',
    params
  })
}
//获取类型
export function dictList(params) {
  return request({
    url: getUrl()+'/dict/data/list',
    method: 'get',
    params
  })
}
//添加素材
export const addAdvertMaterial = params =>{
	  return axios.post(getUrl() + `/advertMaterial/add`, params).then(res => res.data)
}
//编辑素材
export const editAdvertMaterial = params =>{
    return axios.post(getUrl() + `/advertMaterial/edit`, params).then(res => res.data)
}
//删除素材
export const removeAdvertMaterial = params =>{
    return axios.post(getUrl() + `/advertMaterial/remove`, params).then(res => res.data)
}

// 广告管里列表
export function advertConfigList(params) {
  return request({
    url: getUrl()+'/advertConfig/list',
    method: 'get',
    params
  })
}
//广告删除
export const removeAdvertConfig = params =>{
    return axios.post(getUrl() + `/advertConfig/remove`, params).then(res => res.data)
}
//新增广告
export const addAdvertConfig = params =>{
    return axios.post(getUrl() + `/advertConfig/add`, params).then(res => res.data)
}
//查询广告信息编辑用
export function getEditAdv(params) {
  return request({
    url: getUrl()+'/advertConfig/getEditAdv',
    method: 'get',
    params
  })
}
//编辑广告
export const editAdvertConfig = params =>{
    return axios.post(getUrl() + `/advertConfig/edit`, params).then(res => res.data)
}
//广告任务列表
export function selectAdvertDeviceRwList(params) {
  return request({
    url: getUrl()+'/advertDevice/selectAdvertDeviceRw',
    method: 'get',
    params
  })
}
//广告信息查询
export function advInfo(params) {
  return request({
    url: getUrl()+'/advertConfig/get',
    method: 'get',
    params
  })
}
// 投放设备查询接口
export function tflist(params) {
  return request({
    url: getUrl()+'/advertDevice/tflist',
    method: 'get',
    params
  })
}
//优惠折扣总览
export function ConfigTj(params) {
  return request({
    url: getUrl()+'/favourableConfig/getTj',
    method: 'get',
    params
  })
}
//整机/单品折扣查询接口
export function favourableConfigList(params) {
  return request({
    url: getUrl()+'/favourableConfig/list',
    method: 'get',
    params
  })
}
// 折扣新增接口
export const addfavourableConfig = params =>{
    return axios.post(getUrl() + `/favourableConfig/add`, params).then(res => res.data)
}
// 整机/单品折扣删除接口
export const removefavourableConfig = params =>{
    return axios.post(getUrl() + `/favourableConfig/remove`, params).then(res => res.data)
}
// 广告信息查询接口
export function favourableConfigInfo(params) {
  return request({
    url: getUrl()+'/favourableConfig/get',
    method: 'get',
    params
  })
}
//投放设备信息查询接口
export function selectFavUseList(params) {
  return request({
    url: getUrl()+'/favourableObject/selectFavUseList',
    method: 'get',
    params
  })
}
// 修改时查询一个折扣接口
export function getEditFav(params) {
  return request({
    url: getUrl()+'/favourableConfig/getEditFav',
    method: 'get',
    params
  })
}
// 折扣修改接口
export const editFavourableConfig = params =>{
    return axios.post(getUrl() + `/favourableConfig/edit`, params).then(res => res.data)
}

//查看得商品列表
export function selectFavProductList(params) {
  return request({
    url: getUrl()+'/favourableObject/selectFavProductList',
    method: 'get',
    params
  })
}

//广告失效
export const invalidAdvertConfig = params =>{
  return axios.post(getUrl() + `/advertConfig/invalid`, params).then(res => res.data)
}