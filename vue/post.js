import Vue from 'vue'
import VueResource from 'vue-resource'
Vue.use(VueResource)
Vue.prototype.getConfigJson = () => {
    Vue.http.get('./static/config.json').then((result) => {
        console.log(result)
        Vue.prototype.ApiUrl = result.body.ApiUrl
        Vue.prototype.WS_API = result.body.WS_API
        Vue.prototype.ImgUrl = result.body.ImgUrl
    }).catch((error) => {
        console.log(error)
    })
}
