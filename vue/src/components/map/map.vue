<template>
  <div class="map" v-show="visible">
    <div id="map-core"></div>
    <div class="search">
      <div id="r-result">
        <p>搜索定位:</p>
        <input type="text" id="suggestId" value="百度"/>
      </div>
      <div class="lng-lat">
        <div class="item">
          <p>当前经度:</p>
          <input type="text" v-model="location.lng"/>
        </div>
        <div class="item">
          <p>当前纬度:</p>
          <input type="text" v-model="location.lat"/>
        </div>
        <div class="item btn"><button @click="selectLocation">确定</button></div>
      </div>
    </div>
  </div>
</template>

<script>
/* eslint-disable */
import BMap from 'BMap'
export default {
  data () {
    return {
      location: {
        lng: '',
        lat: ''
      },
      map: {},
      ac: {}
    }
  },
  mounted () {
    this.setMap()
    this.setSearch()
  },
  methods: {
    // 初始化地图
    setMap () {
      this.map = new BMap.Map('map-core')
      this.map.centerAndZoom(new BMap.Point(113.275, 23.117), 10)
      // 地图缩放控件
      const topLeftControl = new BMap.ScaleControl({anchor: BMAP_ANCHOR_BOTTOM_LEFT})
      // 城市选择控件
      const cityListControl = new BMap.CityListControl({anchor: BMAP_ANCHOR_TOP_RIGHT})
      // 比例尺控件
      const topLeftNavigation = new BMap.NavigationControl()
      this.map.addControl(topLeftControl)
      this.map.addControl(topLeftNavigation)
      this.map.addControl(cityListControl)
      const _this = this
      // 鼠标缩放
      setTimeout(function () {
        _this.map.setZoom(11)
      }, 2000) // 2秒后放大到11级
      this.map.enableScrollWheelZoom(true)
      // 点击获取经纬度
      this.map.addEventListener('click', function (e) {
        _this.location.lng = parseFloat(e.point.lng).toFixed(3)
        _this.location.lat = parseFloat(e.point.lat).toFixed(3)
      })
    },
    // 根据经纬度绘制地图中的坐标点
    drawLocation () {
      if(this.location.lng !== "" && this.location.lat !== ""){
        this.map.clearOverlays()
        const new_point = new BMap.Point(this.location.lng, this.location.lat)
        const marker = new BMap.Marker(new_point)
        this.map.addOverlay(marker)
        this.map.panTo(new_point) 
      }
    },
    // 搜索位置功能实现
    setSearch () {
      const _this = this
      //建立一个自动完成的对象
      this.ac = new BMap.Autocomplete({"input" : "suggestId", "location" : _this.map})
      //鼠标放在下拉列表上的事件
      this.ac.addEventListener("onhighlight", function(e) {
        let str = ""
        let _value = e.fromitem.value
        let value = ""
        if (e.fromitem.index > -1) {
          value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business
        }
        value = ""
        if (e.toitem.index > -1) {
          _value = e.toitem.value
          value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business
        }
      })
      let myValue
      //鼠标点击下拉列表后的事件
      this.ac.addEventListener("onconfirm", function(e) {
      let _value = e.item.value
        myValue = _value.province +  _value.city +  _value.district +  _value.street +  _value.business
        _this.setPlace(myValue)
      });
    },
    setPlace (myValue) {
      const _this = this
      //清除地图上所有覆盖物
      this.map.clearOverlays()
      //智能搜索
      this.local = new BMap.LocalSearch(_this.map, {
        onSearchComplete: _this.onSearchComplete
      });
      this.local.search(myValue);
    },
    onSearchComplete () {
      //获取第一个智能搜索的结果
      let pp = this.local.getResults().getPoi(0).point
      this.location.lng = parseFloat(pp.lng).toFixed(3)
      this.location.lat = parseFloat(pp.lat).toFixed(3)
      this.map.centerAndZoom(pp, 18)
      //添加标注
      this.map.addOverlay(new BMap.Marker(pp))
    },
    // 向父组件传递经纬度
    selectLocation () {
      this.$emit('selectLocation', this.location)
    }
  },
  watch: {
    'location': {
      handler () {
        this.drawLocation()
      },
      deep: true
    },
    visible () {
      console.log('ddd')
    }
  }
}
</script>

<style lang="less" scoped>
  .map {
    width: 100%;
    height: 100%;
    font-size: 14px;
    #map-core {
      width: 100%;
      height: 90%;
    }
    .search {
      display: flex;
      margin-top: 10px;
      height: 40px;
      #r-result {
        display: flex;
        height: 20px;
        line-height: 20px;
        p {
          height: 20px;
          padding-right: 10px;
        }
        input {
          width: 180px;
          height: 20px;
        }
      }
      .lng-lat {
        display: flex;
        .item {
          display: flex;
          padding-left: 10px;
          height: 20px;
          line-height: 20px;
          p {
            height: 20px;
            padding-right: 10px;
          }
          input {
            width: 100px;
            height: 20px;
          }
          button {
            color: #fff;
            height: 28px;
            width: 60px;
            background: #40B0FF;
            border: 1px solid #40B0FF;
            border-radius: 2px;
            &:hover {
              background: #10B0FF;
              border: 1px solid #10B0FF;
              cursor: pointer;
            }
          }
        }
      }
    }
  }
</style>
