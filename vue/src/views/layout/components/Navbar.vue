<template>
  <el-menu class="navbar" mode="horizontal">
    <nx-hamburger class="hamburger-container" :toggleClick="toggleSideBar" :isActive="sidebar.opened"></nx-hamburger>

    <nx-breadcrumb class="breadcrumb-container"></nx-breadcrumb>

    <div class="right-menu">
      <div class="top-float">
          <el-input
            placeholder="机器编码/售卖机名称"
            v-model="keyWord"  size="mini">
            <i slot="prefix" class="el-input__icon el-icon-search"></i>
          </el-input>
      </div>
      <div class="top-float">
        <el-button type="success" size="mini" icon="el-icon-search" @click="Search">搜索</el-button>
      </div>
      <div class="top-float" v-if="this.$route.name==='首页'||this.$route.name==='dashboard'">
        <el-button-group>
              <el-button type="primary" size="mini" @click="godashboard">{{generateTitle('dashboard')}}</el-button>
              <el-button type="primary" size="mini" @click="webmap">{{generateTitle('webMap')}}</el-button>
            </el-button-group> 
      </div>
      <div class="top-float">
        <el-select v-model="lang" style="width:100px;" placeholder="切换语言" size="mini" >
            <el-option
              key="zh"
              label="简体中文"
              value="zh">
            </el-option>
            <el-option
              key="en"
              label="English"
              value="en">
            </el-option>
          </el-select>
      </div>
      <div class="top-float">
        <el-dropdown class="avatar-container" trigger="click">
          <div class="avatar-wrapper">
            <!-- <img class="user-avatar" :src="avatar+'?imageView2/1/w/80/h/80'"> -->
            <img class="user-avatar" :src="ImgUrl+avatar">
            <span class="user-name">{{name}}</span>
            <i class="el-icon-caret-bottom"></i>
          </div>
          <el-dropdown-menu class="user-dropdown" slot="dropdown">
            <router-link class="inlineBlock" to="/">
              <el-dropdown-item>
                {{generateTitle("Home")}}
              </el-dropdown-item>
            </router-link>
            <el-dropdown-item divided>
              <span @click="logout" style="display:block;">
              {{generateTitle("LogOut")}}
            </span>
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
      <!--  <error-log class="errLog-container right-menu-item"></error-log> -->

      <!-- <nx-github style="margin-top:2px" class="nx-help right-menu-item"></nx-github> -->
      <!-- <error-log class="errLog-container right-menu-item"></error-log> -->
     <!--  <nx-help class="nx-help right-menu-item" />
      <nx-top-lock style="cursor:pointer" class="nx-help"></nx-top-lock> -->
     <!--  <el-tooltip effect="dark" content="全屏" placement="bottom">
        <nx-full-screen class="screenfull right-menu-item"></nx-full-screen>
      </el-tooltip> -->

      <!-- <lang-select class="international right-menu-item"></lang-select> -->
    <!--   <nx-lang-select class="international right-menu-item"></nx-lang-select> -->

     <!--  <el-tooltip effect="dark" content="主题" placement="bottom"> -->
        <!-- <theme-picker class="theme-switch right-menu-item"></theme-picker> -->
       <!--  <nx-skin class="theme-switch right-menu-item"></nx-skin>
      </el-tooltip> -->

      <!-- <el-dropdown class="avatar-container right-menu-item" trigger="click">
        <div class="avatar-wrapper">
          <img class="user-avatar" src="https://mgbq.github.io/nx-admin-site/home.png">
          <i class="el-icon-caret-bottom"></i>
        </div>
        <el-dropdown-menu slot="dropdown">
          <router-link to="/">
            <el-dropdown-item>
              home
            </el-dropdown-item>
          </router-link>
          <a target='_blank' href="https://github.com/mgbq/nx-admin/">
            <el-dropdown-item>
              github地址
            </el-dropdown-item>
          </a>
          <el-dropdown-item divided>
            <span @click="logout" style="display:block;">退出</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown> -->
    </div>
  </el-menu>
</template>

<script>
import { mapGetters } from 'vuex'
import {generateTitle } from '@/utils/i18n'
import nxHamburger from '@/components/nx-hamburger'
import nxBreadcrumb from '@/components/nx-breadcrumb'

export default {
  name: 'navBar',
  data(){
    return{
      keyWord:''
    }
  },
  components: {
   nxHamburger,
   nxBreadcrumb
  },
  computed: {
    ...mapGetters(['sidebar', 'name', 'avatar']),
    lang: {
      get() {
        return this.$store.state.app.language
      },
      set(lang) {
        this.$i18n.locale = lang
        this.$store.dispatch('setLanguage', lang)
      }
    }
  },
  methods: {
    generateTitle,
    toggleSideBar() {
      this.$store.dispatch('ToggleSideBar')
    },
    logout() {
      this.$router.push({'path': '/login'})
       this.$store.dispatch('LogOut').then(() => {
         location.reload() // 为了重新实例化vue-router对象 避免bug
      })
    },
    Search(){
      this.$router.push(
          {
            path:'/equipment/machine/index',
            query:{
              keyWord:this.keyWord
            }
          }
      );

    },
    godashboard(){
      this.$router.push({
        path:"/dashboard"
      });
    },
    webmap(){
      this.$router.push({
        path:"/sitemap"
      });
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>

.top-float{display:inline-block;float:left; margin-right:10px;}

.user-avatar{width:30px;height:30px;border-radius:3px; float:left; margin-top:0px;}
.user-name{color:#fff; line-height:40px; float:left; margin:0px 0px 0 10px;}


.header-serach{ display:inline-block;float:right; margin:0px 0 0  20px;}
.navbar {
  height: 50px;
  line-height: 50px;
  border-radius: 0px !important;
   background:#006BC2;
  .hamburger-container {
    line-height: 58px;
    height: 50px;
    float: left;
    padding: 0 10px;
  }
  .breadcrumb-container {
    float: left;
  }
  .nx-help {
    display: inline-block;
    vertical-align: top;
  }
  .errLog-container {
    display: inline-block;
    vertical-align: top;
  }
  .right-menu {
    float: right;
    height: 100%;
    &:focus {
      outline: none;
    }
    .right-menu-item {
      display: inline-block;
      margin: 0 8px;
    }
    .screenfull {
      height: 20px;
    }
    .international {
      vertical-align: top;
    }
    .theme-switch {
      vertical-align: 15px;
    }
    .avatar-container {
      height: 50px;
      margin-right: 30px;
      .avatar-wrapper {
        cursor: pointer;
        margin-top: 5px;
        position: relative;
        .user-avatar {
          width: 40px;
          height: 40px;
          border-radius: 10px;
        }
        .el-icon-caret-bottom {
          position: absolute;
          right: -20px;
          top: 14px;
          font-size: 12px;
        }
        .el-icon-caret-bottom:before{color:#fff;}
      }
    }
  }
}
</style>
