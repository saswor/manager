<template>
  <el-scrollbar wrapClass="scrollbar-wrapper">
    <div style="height:48px;">
    <div class="logos">
      <logo :isCollapse="isCollapse" ></logo>
    </div>
    </div>
    <el-menu
      mode="vertical"
      :show-timeout="200"
      :default-active="$route.path"
      :collapse="isCollapse"
       background-color="#272727"
      text-color="#ffffff"
      active-text-color="#1f9cff"
    >
      <sidebar-item :routes="permission_routers"></sidebar-item>
    </el-menu>

  </el-scrollbar>
</template>

<script>
import { mapGetters } from 'vuex'
import SidebarItem from './SidebarItem'
import logo from './logo'
export default {
  components: { SidebarItem, logo },
  computed: {
    ...mapGetters([
      'sidebar',
      'permission_routers'
    ]),
    isCollapse() {
      return !this.sidebar.opened
    }
  }
}
</script>
<style>
.logos{position: fixed; z-index: 101000;top:0px; left:0px;  }
.hideSidebar .logos{width:40px; position: fixed; z-index: 101000;top:0px; left:0px;  }
</style>
