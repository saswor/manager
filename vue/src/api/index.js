import request from '@/utils/request'
import axios from 'axios'
let base = process.env.API_HOST///manage

import Vue from 'vue'
import {getUrl} from '@/utils/config'
// function getUrl(){
//   return Vue.prototype.ApiUrl;
// }

// export const getRevenue = params =>{
// 	  return axios.post(base + `/index/selectMSaleSummry`, params).then(res => res.data)
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
    url: getUrl()+'/index/findAreaByPoint',
    method: 'get',
    params
  })
}