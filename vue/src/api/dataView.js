import request from '@/utils/request'
import axios from 'axios'
let base = process.env.API_HOST///manage
// export const getRevenue = params =>{
// 	  return axios.post(base + `/index/selectMSaleSummry`, params).then(res => res.data)
// }

import {getUrl} from '@/utils/config'
// import Vue from 'vue'
// function getUrl(){
//   return Vue.prototype.ApiUrl;
// }

export function getRevenue(params) {
  return request({
    url: getUrl()+'/index/selectMSaleSummry',
    method: 'get',
    params
  })
}


//请求地图数据
export function getMapData(params) {//查询区域
  return request({
    url: getUrl()+'/vendingPoint/dpList',
    method: 'get',
    params
  })
}