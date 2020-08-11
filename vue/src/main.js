import Vue from 'vue'

import VCharts from 'v-charts'
// 地图
import BaiduMap from 'vue-baidu-map'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import locale from 'element-ui/lib/locale/lang/zh-CN' // lang i18n
import '../static/css/iconfont/1.0.0/index.css' /* icofont*/
// css
import './styles/element-variables.scss' // global css
import 'normalize.css/normalize.css'// A modern alternative to CSS resets
import '@/styles/index.scss' // global css
// 右键菜单
import contentmenu from 'v-contextmenu'
// import 'v-contextmenu/dist/index.css'
import './mock' // simulation data

import './errorLog'// error log
// font-awesome
import '@/assets/library/font-awesome-4.7.0/css/font-awesome.min.css'
import App from './App'

import router from './router'

import store from './store'

import '../post.js'

// Internationalization
import i18n from './lang'
import { global } from '@/global/global'
import {
  loadStyle
} from './utils/util'
import {
  iconfontUrl,
  iconfontVersion
} from '@/config/env'

import '@/icons' // icon

import '@/permission' // permission control

import moment from 'moment'
import * as filters from './filters' // global filters
// register global utility filters.
Object.keys(filters).forEach(key => {
  Vue.filter(key, filters[key])
})
// 引入moment.js工具

Vue.use(VCharts)
Vue.use(contentmenu)
Vue.use(ElementUI, { locale })
iconfontVersion.forEach(ele => {
  loadStyle(iconfontUrl.replace('$key', ele))
})
Vue.use(ElementUI, {
  size: 'medium', // set element-ui default size
  i18n: (key, value) => i18n.t(key, value)
})
// 地图
Vue.use(BaiduMap, {
  // ak 是在百度地图开发者平台申请的密钥 详见 http://lbsyun.baidu.com/apiconsole/key */
  // ak: 'oW2UEhdth2tRbEE4FUpF9E5YVDCIPYih'
  ak: 'zSYINVllUIO08NwLwDQRYVVgBdIKcdtf'
})
// 加载用户主题
if (localStorage.getItem('themeValue')) {
  global.changeTheme(localStorage.getItem('themeValue'))
} else {
  global.changeTheme('default')
}
Vue.config.productionTip = false
Vue.prototype.$moment = moment


// Vue.prototype.WS_API = "ws://localhost:8081"//测试

// Vue.prototype.WS_API = "ws://localhost:80"//生产

// Vue.prototype.WS_API = "ws://localhost:8989"//本地
// Vue.prototype.WS_API = "ws://localhost:8082"//本地

// Vue.prototype.WS_API = "ws://vm.zhilai.com/"
// Vue.prototype.WS_API = "ws://vending.zhilai.com/"
// Vue.prototype.WS_API = "wss://vending.zhilai.com/"
// Vue.prototype.WS_API = "ws://vending.zhilai.com:8081/"



// Vue.prototype.WS_API = "ws://192.168.1.103:8081"



// Vue.prototype.WS_API = "ws://localhost:8081"

// Vue.prototype.ImgUrl="http://localhost:80/"
// Vue.prototype.ImgUrl="http://localhost:8989/"
// Vue.prototype.ImgUrl="http://localhost:8081/"

// Vue.prototype.ImgUrl="http://vm.zhilai.com/"
// Vue.prototype.ImgUrl="http://vending.zhilai.com:80/"
// Vue.prototype.ImgUrl="http://vending.zhilai.com:8081/"
// Vue.prototype.ImgUrl="https://vending.zhilai.com/"



import VueResource from 'vue-resource'
Vue.use(VueResource)
Vue.prototype.getConfigJson = () => {
    // Vue.http.get('./static/config.json').then((result) => {
      Vue.http.get('../config/config.json').then((result) => {
        Vue.prototype.ApiUrl = result.body.ApiUrl
        Vue.prototype.WS_API = result.body.WS_API
        Vue.prototype.ImgUrl = result.body.ImgUrl
    }).catch((error) => {
        console.log(error)
    })
}

new Vue({
  el: '#app',
  router,
  store,
  i18n,

  // template: '<App/>',
  // components: { App }
  render: h => h(App)
})
