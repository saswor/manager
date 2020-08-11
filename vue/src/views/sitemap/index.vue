<template>
  <div class="dashboard-container">
     <baidu-map class="map" center="中国" :scroll-wheel-zoom="true" @ready="handleClick" :style="{height:fullHeight}"> 
      <bml-marker-clusterer :averageCenter="true">
        <bm-marker v-for="marker of markers"  :key="Math.random()" :position="{lng:  marker.lng, lat: marker.lat}" @click="mapClick(this,marker)"></bm-marker>
      </bml-marker-clusterer>
    </baidu-map>
  </div>
</template>
<script>
import { BmlMarkerClusterer } from 'vue-baidu-map'
import { getPositionList } from '@/api/pointposition'
export default {
  name: 'sitemap',
  data() {
      return {
        fullHeight:document.documentElement.clientHeight-50+"px",
        loadding:true,
        markers: [],
        zoom:1,
        infoWindow:null,
        map:null,
        BMap:null,
        info:{
          name:"",
          lineName:"",
          pointName:"",
          addName:""

        },
        opts : {
          width : 400,     // 信息窗口宽度
          height: 150,     // 信息窗口高度
          title : "详细信息" , // 信息窗口标题
          enableMessage:true,//设置允许信息窗发送短息
          message:"亲耐滴，晚上一起吃个饭吧？戳下面的链接看下地址喔~"
        }

      };
  },
  watch: {
      fullHeight (val) {
        if(!this.timer) {
          this.fullHeight = val
          this.timer = true
          let that = this
          setTimeout(function (){
            that.timer = false
          },400)
        }
      }
    },
  computed: {
   
  },
  created() {
  },
  methods: {
      handleClick({BMap, map}) {
          this.map = map
          this.BMap = BMap
          var _this = this;
          const listQuery = {
          }
          getPositionList(listQuery).then(response => {
            if(response.zhead.reTCode==="0000"){
              var data = response.zbody.datas.rows
              var position=null
              for (let i = 0; i < data.length; i++) {
                position = { lng:data[i].longitude, lat:data[i].latitude,db:data[i]}
                _this.markers.push(position)
              }
            console.log("地图数据：",response)
            }else{
               this.$notify({
                  title: '失败',
                  message: res.zhead.retMsg,
                  type: 'error'
                });
            }
         
          })
      },
      mapClick(a,b) {
          console.log(b)
          this.info.name = b.db.name;
          this.info.lineName = b.db.lineName
          this.info.pointName= b.db.districtName;
          this.info.addName = b.db.adderss;
          this.infoWindow = new BMap.InfoWindow("点位名称："+this.info.name+"<br/>线路："+this.info.lineName+"<br/>区域："+this.info.pointName+"<br/>位置："+this.info.addName+"<br/><a href='#/equipment/machine/detail/details?type=map&pointId="+b.db.pointId+"&logid="+b.db.logid+"'>查看详情</a>", this.opts);  // 创建信息窗口对象 
          this.map.openInfoWindow(this.infoWindow,a.point);
      },
  },
  mounted () {
      const that = this
      window.onresize = () => {
        return (() => {
          window.fullHeight = document.documentElement.clientHeight
          that.fullHeight = window.fullHeight
          console.log(that.fullHeight)
        })()
      }

  },
  components: {
     BmlMarkerClusterer
  },
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>  
.map{}
</style>
