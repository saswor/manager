import request from '@/utils/request'

import Vue from 'vue'
function getUrl(){
  return Vue.prototype.ApiUrl;
}

export function getToken() {
  return request({
    url: '/qiniu/upload/token', // 假地址 自行替换
    method: 'get'
  })
}
