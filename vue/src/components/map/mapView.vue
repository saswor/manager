<template>
  <!-- <div style="padding-top:50px;"> -->
      <div>
    <!-- <Modal @on-cancel="cancel" v-model="showMapComponent" width="800" :closable="false" :mask-closable="false"> -->
      <baidu-map class="bm-view" ak="你的密钥"
      :center="center" 
      :zoom="zoom" 
      :scroll-wheel-zoom="true" 
      @click="getClickInfo"
      @moving="syncCenterAndZoom" 
      @moveend="syncCenterAndZoom" 
      @zoomend="syncCenterAndZoom">
        <bm-view style="width: 100%; height:500px;"></bm-view>
        <bm-marker :position="{lng: center.lng, lat: center.lat}" :dragging="true" animation="BMAP_ANIMATION_BOUNCE">
        </bm-marker>
        <bm-control :offset="{width: '10px', height: '10px'}">
          <bm-auto-complete v-model="keyword" :sugStyle="{zIndex: 999999}">
            <input type="text" placeholder="请输入搜索关键字" class="serachinput">
          </bm-auto-complete>
        </bm-control>
        <bm-local-search :keyword="keyword" :auto-viewport="true" style="width:0px;height:0px;overflow: hidden;"></bm-local-search>
      </baidu-map>
      <div slot="footer" style="margin-top: 0px;">
          <label class="labelFont">经度:</label>
          <input type="text" v-model="center.lng" class="locationShow" readonly>
          <label class="labelFont">纬度:</label>
          <input type="text" v-model="center.lat" class="locationShow" readonly>
        <el-button @click="comfirm" size="mini">确 定</el-button>
      </div>
    <!-- </Modal> -->
  </div>  
</template>
<script>
  import {BaiduMap, BmControl, BmView, BmAutoComplete, BmLocalSearch, BmMarker} from 'vue-baidu-map'
  export default {
    components: {
      BaiduMap,
      BmControl,
      BmView,
      BmAutoComplete,
      BmLocalSearch,
      BmMarker
    },
    data: function () {
      return {
        showMapComponent: this.value,
        keyword: '',
        // mapStyle: {
        //   width: '100%',
        //   height: this.mapHeight + 'px'
        // },
        center: {lng: 116.404, lat: 39.915},
        zoom: 15
      }
    },
    watch: {
      value: function (currentValue) {
        this.showMapComponent = currentValue
        if (currentValue) {
          this.keyword = ''
        }
      }
    },
    props: {
      value: Boolean,
      mapHeight: {
        type: Number,
        default: 500
      }
    },
    methods: {
      /***
       * 地图点击事件。
       */
      getClickInfo (e) {
        this.center.lng = e.point.lng
        this.center.lat = e.point.lat
      },
      syncCenterAndZoom (e) {
        const {lng, lat} = e.target.getCenter()
        this.center.lng = lng
        this.center.lat = lat
        this.zoom = e.target.getZoom()
      },
      /***
       * 确认
       */
      comfirm() {
        this.keyword = ''
        this.$emit('mapConfirm', this.center)
      },
      /***
       * 取消
       */
      cancel () {
        this.$emit('cancel', this.showMapComponent)
      }
    }
  }
</script>
 
<style scoped>
.serachinput{
  width: 300px;
  box-sizing: border-box;
  padding: 9px;
  border: 1px solid #dddee1;
  line-height: 20px;
  font-size: 16px;
  height: 38px;
  color: #333;
  position: relative;
  border-radius: 4px;
  -webkit-box-shadow: #666 0px 0px 10px;
  -moz-box-shadow: #666 0px 0px 10px;
  box-shadow: #666 0px 0px 10px;
}
.locationShow{
  width: 150px;
  box-sizing: border-box;
  padding: 9px;
  border: 1px solid #dddee1;
  line-height: 15px;
  font-size: 16px;
  height: 25px;
  color: #333;
  position: relative;
  border-radius: 4px;
  -webkit-box-shadow: #666 0px 0px 10px;
  -moz-box-shadow: #666 0px 0px 10px;
  box-shadow: #666 0px 0px 10px;
}
.labelFont{
    font-size: 16px;
}
</style>