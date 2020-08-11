<template>

 <!--  <div class="app-wrapper" :class="classObj">
    <div v-if="device==='mobile'&&sidebar.opened" class="drawer-bg" @click="handleClickOutside"></div>
    <sidebar class="sidebar-container"></sidebar>
    <div class="main-container">
      <navbar></navbar>
      <tags-view></tags-view>
      <app-main></app-main>
    </div>
  </div> -->
  
  <el-container class="app-wrapper" :class="classObj">
      <el-container>
          <el-aside style="width:auto">
            <div v-if="device==='mobile'&&sidebar.opened" class="drawer-bg" @click="handleClickOutside"></div>
            <sidebar class="sidebar-container"></sidebar>
          </el-aside>
          <el-main style="padding:0">
                <div style="height:50px;width:100%;">
                  <div  class="topMan">
                      <navbar></navbar>
                  </div>
                </div>
            
               <!--  <tags-view></tags-view> -->
                <app-main></app-main>
           
          </el-main>
      </el-container>
    </el-container>
</template>

<script>
import { Navbar, Sidebar, AppMain, TagsView } from './components'
import ResizeMixin from './mixin/ResizeHandler'
import { mapGetters } from 'vuex'

import avatar from '@/assets/images/user-avatar.jpg'
export default {
   data(){
    return{
      sysName:'平台管理',
      collapsed:'logo',
      useravatar:avatar
  
    }
  },
  name: 'layout',
  components: {
    Navbar,
    Sidebar,
    AppMain,
    TagsView
  },
  mixins: [ResizeMixin],
  computed: {
    ...mapGetters([
      'avatar',
      'name'
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
        mobile: this.device === 'mobile',

      }
    },
    rightObj() {
      return {
         rightObj:"right_g"
      }
    }
  },
  methods: {
    handleClickOutside() {
      this.$store.dispatch('CloseSideBar', { withoutAnimation: false })
     

    },
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  @import "src/styles/mixin.scss";

  .topMan{
    position: fixed; z-index: 1010;top:0px; left:180px;  right: 0; margin: 0;
  }
  .hideSidebar .topMan{left:35px;}
  .right_z{margin-left:170px;}
  .right_g{margin-left:0px;}
  .app-wrapper {
    @include clearfix;
    position: relative;
    min-width:1200px;
    overflow:hidden;
    height: 100%;
    width: 100%;
  }

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
