<template>
  <div class="logo">
    <transition name="fade">
     <img :src="logo">
    </transition>
    
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import logo from '@/assets/images/logo.png'
export default {
  name: 'logo',
  data() {
    return {
      logo:logo
    }
  },
  props: ['isCollapse'],
  created() {},
  computed: {
    ...mapGetters(['website']),
    type: function(val) {
      return this.website.logo.indexOf('static') !== -1
    }
  },
  methods: {}
}
</script>

<style scoped="scoped" lang="scss">
.fade-leave-active {
  transition: opacity 0.2s;
}
.fade-enter-active {
  transition: opacity 2.5s;
}
.fade-enter,
.fade-leave-to {
  opacity: 0;
}
.logo {
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  // width: 230px;
  height: 48px;
  background: #006BC2;
  color: #fff;
  text-align: center;
  font-size: 20px;
  font-weight: 600;
  overflow: hidden;
  box-sizing: border-box;
}
.logo img{width:60%}
.logo_title {
  padding: 0 5px 0 0;
  color: #42b983;
  font-size: 20px;
  &.is-bold {
    font-weight: 700;
  }
}
.is-text {
  position: absolute;
  top: 0;
  // left: 20px;
}
.is-img {
  position: absolute;
  top: 10px;
  left: 10px;
}
.logo_subtitle {
  font-size: 16px;
  padding-top: 5px;
}
</style>