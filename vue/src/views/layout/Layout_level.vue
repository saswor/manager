<template>
  <div>
    <app-main></app-main>
  </div>
</template>

<script>
import {AppMain } from './components'
import ResizeMixin from './mixin/ResizeHandler'
import { mapGetters } from 'vuex'
import local from './local'
const viewName = 'i18nView'
export default {
  data(){
    return{
      sysName:'平台管理',
      collapsed:'logo',
      value:"请输入"
    }
  },
  name: 'layout', 
  components: {
    AppMain
  },
  mixins: [ResizeMixin],
  created() {
    //if (!this.$i18n.getLocaleMessage('en')[viewName]) {
     // this.$i18n.mergeLocaleMessage('en', local.en)
      //this.$i18n.mergeLocaleMessage('zh', local.zh)
 //
    //}
  },
  methods: {
    handleClickOutside() {
      this.$store.dispatch('CloseSideBar', { withoutAnimation: false })
    },
    logout() {
      this.$router.push({'path': '/login'})
      // this.$store.dispatch('LogOut').then(() => {
      //   location.reload() // 为了重新实例化vue-router对象 避免bug
      // })
    }
  },
  mounted(){

  },
   computed: {
    ...mapGetters([
      'avatar'
    ]),
    sidebar() {        
      return this.$store.state.app.sidebar
    },
    device() {
      return this.$store.state.app.device
    },
    classObj() {
      return {
        hideSidebar: !this.sidebar.opened,
        withoutAnimation: this.sidebar.withoutAnimation,
        mobile: this.device === 'mobile'
      }
    },  
    lang: {
      get() {
        return this.$store.state.app.language
      },
      set(lang) {
        this.$i18n.locale = lang
        this.$store.dispatch('setLanguage', lang)
      }
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  @import "src/styles/mixin.scss";
  .header-serach{ display:inline-block;float:left; margin:10px 0 0  20px;}
  .user-avatar{width:30px;height:30px;border-radius:3px; float:left; margin-top:10px;}
  .user-name{color:#fff; line-height:40px; float:left; margin:5px 0px 0 10px;}
  .logo{width:180px; text-align:center; color:#fff; margin-top:12px;}
  .logo img{ display:block; width:60%; margin:auto;}
  .app-wrapper {
    @include clearfix;
    position: relative;
    overflow:hidden;
    height: 100%;
    width: 100%;
  }
  .el-icon-caret-bottom{float:right; margin:17px 10px 0 10px;}
  .el-icon-caret-bottom:before{color:#fff;}
  .drawer-bg {
    background: #000;
    opacity: 0.3;
    width: 100%;
    top: 0;
    height: 100%;
    position: absolute;
    z-index: 999;
  }
</style>
