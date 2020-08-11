import Vue from 'vue'

export function getUrl() {
  if (Vue.prototype.ApiUrl == null || Vue.prototype.ApiUrl === undefined || Vue.prototype.ApiUrl === '') {    
    let result = getApiUrl().then(res=>{
      return res;
    });
    // console.log("直接读取:"+result)
    // return result;
  } else {
    console.log("http读取:"+Vue.prototype.ApiUrl)
    return Vue.prototype.ApiUrl;
  }
}

function getApiUrl() {
  var result = new Promise((resolve, reject) => {
    Vue.http.get('../../config/config.json').then((result) => {
      Vue.prototype.ApiUrl = result.body.ApiUrl
      Vue.prototype.WS_API = result.body.WS_API
      Vue.prototype.ImgUrl = result.body.ImgUrl
      // return result.body.ApiUrl;
      resolve(result.body.ApiUrl);
    }).catch((error) => {
      console.log(error);
      // return "";
      reject("");
    })
  })
  return result;
}


function getAsyncApiUrl() {
  let result =  getApiUrl();
  console.log("同步读取:"+result)
  return result;
}
