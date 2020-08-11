import request from '@/utils/request'
import axios from 'axios'
let base = process.env.API_HOST///manage

import Vue from 'vue'
import {getUrl} from '@/utils/config'
// function getUrl(){
//   return Vue.prototype.ApiUrl;
// }

//警告列表
export function getDictList(params) {
  return request({
    url: getUrl()+'/vendingWarn/list',
    method: 'get',
    params
  })
}
//事件列表
export function getEventList(params) {
  return request({
    url: getUrl()+'/vendingEvent/list',
    method: 'get',
    params
  })
}
