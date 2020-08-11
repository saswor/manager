import request from '@/utils/request'
import axios from 'axios'
// let getUrl() = process.env.API_HOST///manage

import Vue from 'vue'
import {getUrl} from '@/utils/config'
// function getUrl(){
//   return Vue.prototype.ApiUrl;
// }

export const getRevenue = params =>{
	  return axios.post(getUrl() + `/index/selectMSaleSummry`, params).then(res => res.data)
}



//获取用户列表
export function getUserList(params) {//查询区域
  return request({
    url: getUrl()+'/user/list',
    method: 'get',
    params
  })
}
//添加用户
export const addUser = params =>{
	  return axios.post(getUrl() + `/user/add`, params).then(res => res.data)
}
//编辑用户
export const editUser = params =>{
	  return axios.post(getUrl() + `/user/edit`, params).then(res => res.data)
}
//删除用户
export const removeUser = params =>{
	  return axios.post(getUrl() + `/user/remove`, params).then(res => res.data)
}
//查询用户编辑信息
export function getByUserId(params) {//查询区域
  return request({
    url: getUrl()+'/user/selectByUserId',
    method: 'get',
    params
  })
}

//获取角色列表
export function getRoleList(params) {//查询区域
  return request({
    url: getUrl()+'/role/list',
    method: 'get',
    params
  })
}
//添加角色
export const addRole = params =>{
	  return axios.post(getUrl() + `/role/add`, params).then(res => res.data)
}
//添加角色
export const editRole = params =>{
    return axios.post(getUrl() + `/role/edit`, params).then(res => res.data)
}
//删除角色
export const removeRole = params =>{
	  return axios.post(getUrl() + `/role/remove`, params).then(res => res.data)
}
//获取菜单列表
export function getMenuList(params) {//查询区域
  return request({
    url: getUrl()+'/menu/getAllMenu',
    method: 'get',
    params
  })
}
//根据角色获取菜单列表
export function getRoleMenuList(params) {//查询区域
  return request({
    url: getUrl()+'/menu/selectMenuIdByRoleId',
    method: 'get',
    params
  })
}
//编辑角色时根据角色id获取该角色的用户权限
export function getRoleiSMenuList(params) {//查询区域
  return request({
    url: getUrl()+'/role/getYuanMenuId',
    method: 'get',
    params
  })
}
//字典管理列表
export function getDistList(params) {
  return request({
    url: getUrl()+'/dict/list',
    method: 'get',
    params
  })
}
//添加字典
export const addDist = params =>{
	  return axios.post(getUrl() + `/dict/add`, params).then(res => res.data)
}
//修改字典
export const editDist = params =>{
	  return axios.post(getUrl() + `/dict/edit`, params).then(res => res.data)
}
//删除字典
export const delDist = params =>{
	  return axios.post(getUrl() + `/dict/remove`, params).then(res => res.data)
}

//字典数据查询列表
export function getDataCorpList(params) {
  return request({
    url: getUrl()+'/dict/data/list',
    method: 'get',
    params
  })
}
//
//新增子字典
export const addDistlevel = params =>{
    return axios.post(getUrl() + `/dict/data/add`, params).then(res => res.data)
}
//编辑子字典
export const editDistlevel = params =>{
    return axios.post(getUrl() + `/dict/data/edit`, params).then(res => res.data)
}
//删除子字典
export const removeDistlevel = params =>{
    return axios.post(getUrl() + `/dict/data/remove`, params).then(res => res.data)
}

//商户列表
export function getCorpList(params) {
  return request({
    url: getUrl()+'/corp/list',
    method: 'get',
    params
  })
}
//新增商户
export const AddCorp = params =>{
    return axios.post(getUrl() + `/corp/add`, params).then(res => res.data)
}
//编辑商户
export const editCorp = params =>{
    return axios.post(getUrl() + `/corp/edit`, params).then(res => res.data)
}
//删除商户
export const removeCorp = params =>{
    return axios.post(getUrl() + `/corp/remove`, params).then(res => res.data)
}


//支付配置查询支付宝
export function payconfigAlipay(params) {
  return request({
    url: getUrl()+'/payconfigAlipay/detail',
    method: 'get',
    params
  })
}
//支付配置保存支付宝
export const AlipaySave = params =>{
    return axios.post(getUrl() + `/payconfigAlipay/saveOrUpdate`, params).then(res => res.data)
}

//支付配置查询支微信
export function payconfigWx(params) {
  return request({
    url: getUrl()+'/payconfigWechat/detail',
    method: 'get',
    params
  })
}
//支付配置保存微信
export const WxpaySave = params =>{
    return axios.post(getUrl() + `/payconfigWechat/saveOrUpdate`, params).then(res => res.data)
}

//微信订单申请接口
export const WechatorderApply = params =>{
    return axios.post(getUrl() + `/payconfigWechat/orderApply`, params).then(res => res.data)
}

//支付宝申请
export const AlipayorderApply = params =>{
    return axios.post(getUrl() + `/payconfigAlipay/orderApply`, params).then(res => res.data)
}
//微信申请
export const WechatpayorderApply = params =>{
    return axios.post(getUrl() + `/payconfigWechat/orderApply`, params).then(res => res.data)
}

//退款
export function returnMoney(params) {
  return request({
    url: getUrl()+'/payConfig/returnMoney',
    method: 'get',
    params
  })
}
//修改密码
export const resetPwd = params =>{
    return axios.post(getUrl() + `/user/resetPwd`, params).then(res => res.data)
}
//支付配置测试
export function payTest(params) {
  return request({
    url: getUrl()+'/payConfig/payTest',
    method: 'get',
    params
  })
}

// 导出用户
export const exportUserExcel = params =>{
  return axios.post(getUrl() + `/user/exportExcel`, params).then(res => res.data)
}

// 导出商户
export const exportCorpExcel = params =>{
  return axios.post(getUrl() + `/corp/exportExcel`, params).then(res => res.data)
}