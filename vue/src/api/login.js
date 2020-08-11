import request from '@/utils/request'
import {getUrl} from '@/utils/config'
import axios from 'axios'
import Vue from 'vue'
// let getUrl() = process.env.API_HOST//''///manage


// function getUrl(){
//   return Vue.prototype.ApiUrl;
// }


export function login(username, password) {
  console.log("getUrl():"+getUrl())
  return request({
    url:getUrl()+'/login',
    method: 'post',
    data: {
      username,
      password
    }
  })
}
// export function getInfo(token) {
//   return request({
//     url: getUrl()+'/user/getCurrUser',
//     method: 'post',
//     params: { token }
//   })
// }

export function getInfo(token) {//
  var requestUrl = getUrl()
  if (requestUrl === undefined) {
    requestUrl = "/system";
    return request({
      url: requestUrl + '/user/getCurrUser',
      method: 'get',
      params: { token }
    })
  } else {
    return request({
      url: requestUrl + '/user/getCurrUser',
      method: 'get',
      params: { token }
    })
  }
  // while(true){

  // }
  // return request({
  //   url: getUrl()+'/user/getCurrUser',
  //   method: 'get',
  //   params:{ token }
  // })
}

export function logout() {

  return request({
    url: getUrl()+'/ajaxLogout',
    method: 'post'
  })
}

//重新登录
export const relogin = params =>{
  return axios.post(getUrl() + `/login`, params).then(res => res.data)
}
