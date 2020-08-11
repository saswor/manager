import request from '@/utils/request'
import axios from 'axios'
// let getUrl() = process.env.API_HOST///manage

import Vue from 'vue'
import {getUrl} from '@/utils/config'
// function getUrl(){
//   return Vue.prototype.ApiUrl;
// }
//商品列表
export function getList(params) {
  return request({
    url: getUrl()+'/productInfo/list',
    method: 'get',
    params
  })
}
//新增商品
export const addProductInfo = params =>{
    return axios.post(getUrl() + `/productInfo/add`, params).then(res => res.data)
}
//查询编辑商品信息
export function getEditInfo(params) {
  return request({
    url: getUrl()+'/productInfo/detail',
    method: 'get',
    params
  })
}
//编辑商品
export const editProductInfo = params =>{
    return axios.post(getUrl() + `/productInfo/edit`, params).then(res => res.data)
}
//删除商品
export const removeProductInfo = params =>{
    return axios.post(getUrl() + `/productInfo/remove`, params).then(res => res.data)
}
//商品分类查询
export function getProductClassify(params) {
  return request({
    url: getUrl()+'/productClassify/list',
    method: 'get',
    params
  })
}
//编辑商品分类
export const editproductClassify = params =>{
    return axios.post(getUrl() + `/productClassify/edit`, params).then(res => res.data)
}
//删除商品分类
export const removeProductClassify = params =>{
    return axios.post(getUrl() + `/productClassify/remove`, params).then(res => res.data)
}
//新增商品分类
export const addProductClassify = params =>{
    return axios.post(getUrl() + `/productClassify/add`, params).then(res => res.data)
}
//在线商品查询
export function lineproduct(params) {
  return request({
    url: getUrl()+'/productOnline/list',
    method: 'get',
    params
  })
}

//在线商品查询
export function selectUnderProductSite(params) {
  return request({
    url: getUrl()+'/vendingLanep/selectUnderProductSite',
    method: 'get',
    params
  })
}
//下架
export const under = params =>{
    return axios.post(getUrl() + `/productUnder/under`, params).then(res => res.data)
}
//下架记录
export function productLunder(params) {
  return request({
    url: getUrl()+'/productLunder/list',
    method: 'get',
    params
  })
}
//导入Excel 
//下架
export const importExcel = params =>{
    return axios.post(getUrl() + `/productInfo/importProductExcel`, params).then(res => res.data)
}

//下载模板
export const downLoadExcelModel = params =>{
  return axios.post(getUrl() + `/productInfo/downLoadExcelModel`, params).then(res => res.data)
}

// 导出商品信息
export const exportProductExcel = params =>{
  return axios.post(getUrl() + `/productInfo/exportExcel`, params).then(res => res.data)
}

//查询商品库中商品
export function getReferenceProductList(params) {
  return request({
    url: getUrl()+'/productInfo/getReferenceProductList',
    method: 'get',
    params
  })
}
//引用商品
export function referenceProductInfo(params) {
  return request({
    url: getUrl()+'/productInfo/referenceProduct',
    method: 'get',
    params
  })
}

// 导出商品信息
export const exportProductUnderExcel = params =>{
  return axios.post(getUrl() + `/productLunder/exportExcel`, params).then(res => res.data)
}