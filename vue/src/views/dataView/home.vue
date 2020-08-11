<template>
    
  <div class="data_bodey">
    <!-- <el-tooltip effect="dark" content="全屏" placement="bottom" class="screen">
        <nx-full-screen class="screenfull right-menu-item"></nx-full-screen>
      </el-tooltip> -->
      <div class="home-top" @click="fullScreen">
        <ul>
          <li>
              <strong>累计销售额</strong>
              <span>{{OverviewData.totalSale}}</span>
          </li>
          <li>
              <strong>累计销售量</strong>
              <span>{{OverviewData.totalSaleNum}}</span>
          </li>
          <li>
              <strong>累计毛利润</strong>
              <span>{{OverviewData.totalProfit}}</span>
          </li>
          <li>
              <strong>在线设备</strong>
              <span>{{OverviewData.onlineMachine}}</span>
          </li>
          <li>
              <strong>离线设备</strong>
              <span>{{OverviewData.outlineMachine}}</span>
          </li>
        </ul>
      </div>
      <div class="home-line-column">
        <div class="line-column-l" >
          <h2>实时分析</h2>
          <div class="dataLayer">
              <strong> <span> 今日销售额：</span> {{Today.totalSale}}</strong>
              <strong> <span>今日销售量：</span>{{Today.totalSaleNum}}</strong>
              <strong> <span>今日净利润：</span>{{Today.totalProfit}}</strong>
          </div>
          <div id="DataAnalysis" style="width:100%;height:100%;"></div>
        </div>
        <div class="line-column-r">
            <h2>营收分析</h2>
            <div class="tab">
            <ul>
              <li v-bind:class="{ on: isActive }" @click="getData('1')">近半年</li>
              <li v-bind:class="{ on: isActive1 }" @click="getData('2')">近一年</li>
            </ul>
          </div>
            <div id="RevenueAnalysis" style="width:100%;height:100%;"></div>
        </div>
      </div>
      <div class="home-bar">
        <div class="left_cage">
          <h2>今日销售点位TOP10</h2>
          <div style="width:100%;height:100%;" id="BarTiao"></div>
        </div>
        <div class="center_cage">
            <h2>全国站点分布图</h2>
            <div style="width:100%;height:100%;" id="map">
              
            </div>
        </div>
        <div class="right_cage">
          <h2>今日销售商品TOP10</h2>
           <div id="dataSales" style="width:100%;height:100%;">
             
           </div> 
        </div>
      </div>
  </div>
</template>
<script>
import { mapGetters } from 'vuex'
import echarts from 'echarts'
import ReconnectingWebSocket from 'reconnecting-websocket'
import nxCountUp from '@/components/nx-count-up/index.vue'
import nxFullScreen from '@/components/nx-full-screen/index'
import { getRevenue,getMapData} from '@/api/dataView'
import { getPositionList } from '@/api/pointposition'
import { getToken } from '@/utils/auth'
import { login,logout } from '@/api/login'
const viewName = 'i18nView'
export default {
  name: 'dataView',
  data() {
      var renderItem=(params, api)=> {
          var coords = [
              [116.7,39.53],
              [103.73,36.03],
              [112.91,27.87],
              [120.65,28.01],
              [119.57,39.95]
          ];
          var points = [];
          for (var i = 0; i < coords.length; i++) {
              points.push(api.coord(coords[i]));
          }
          var color = api.visual('color');

          return {
              type: 'polygon',
              shape: {
                  points: echarts.graphic.clipPointsByRect(points, {
                      x: params.coordSys.x,
                      y: params.coordSys.y,
                      width: params.coordSys.width,
                      height: params.coordSys.height
                  })
              },
              style: api.style({
                  fill: color,
                  stroke: echarts.color.lift(color)
              })
          };
      };
      return {
       loginToken:'',//登录
       loginCorpId:'',//公司
       loginUserId:'',//登录人
       lastReceiveWebsocketTime:0,//上次接收到websocket推送时间
       isActive:true,
       isActive1:false,
       startTime:'2018-01',//开始时间
       endTime:'2018-12',//结束时间
       markers:[],
       pointData:{},
       OverviewData:{
          totalSale:0, // 累计销售额
          totalSaleNum:0,  // 累计销售量
          totalProfit:0, // 累计净利润
          onlineMachine:0, // 在线设备
          outlineMachine:0  // 离线设备
        },
        Today:{//近一个月总览----今日数据
            totalProfit:0,//净利润
            totalSale:0,//销售额
            totalSaleNum:0//销售量
        },
        yesterday:{
            totalProfit:0,//净利润
            totalSale:0,//销售额
            totalSaleNum:0//销售量
        },
        sevenDays:{
            totalProfit:0,//净利润
            totalSale:0,//销售额
            totalSaleNum:0//销售量
        },
        ThirtyDays:{
            totalProfit:0,//净利润
            totalSale:0,//销售额
            totalSaleNum:0//销售量
        },
        DataAnalysisData:{//数据分析线图数据
          sales:[],//销售额
          salesNum:[],//销售量
          times:[]//时间
        },
        monthsData: [],//营收分析X轴数据
        AnalysisData:{//营收分析数据
          profitList:[],
          salesList:[],
          salesNumList:[]
        },
        websock: null,
        userInfo:{},
        mapData:[]
      };
  },
  computed: {
    ...mapGetters([
      'name',
      'roles',
      'corpId',
      'userinfo'
    ])
  },
  beforeMount(){
    //页面刚进入时开启长连接
    this.loginToken=getToken();
    this.loginCorpId=this.userInfo.corpId;
    this.loginUserId=this.userInfo.userId;
    this.initWebSocket()
  },
  watch: {
   
  },
  computed: {
  },
  created() {
      var _this = this;

      //设置全屏
      this.fullScreen();

      //创建定时器检查websocket连接是否中断
      clearInterval(this.timer);
      this.timer = null;
      this.setTimer();

      this.userInfo = JSON.parse(localStorage.getItem("userInfo"))
      const listQuery = {
      }
      getMapData(listQuery).then(response => {
        if(response.zhead.reTCode==="0000"){
          var data = response.zbody.datas
          _this.map(data.resultData,data.result)
       
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
  methods: {
      //检查websocket连接是否中断
        setTimer() {
            var _this=this;
            if(this.timer == null) {
                this.timer = setInterval( () => {
                    console.log('开始定时...每过1秒执行一次,时间:'+_this.lastReceiveWebsocketTime)
                    //如果30s以上未收到websocket通知,重连websocket
                    if(_this.lastReceiveWebsocketTime>4){
                         _this.websocketclose();
                         //_this.initWebSocket();
                        _this.lastReceiveWebsocketTime=-1;
                    }else if(_this.lastReceiveWebsocketTime<0){
                        logout().then((res) => {
                            if(res.zhead.reTCode=="0000"){
                                console.log("登出成功")
                                login('ledshow','123456').then((result) => {
                                    if(result.zhead.reTCode=="0000"){
                                        console.log("登录成功")
                                        _this.initWebSocket();
                                        _this.lastReceiveWebsocketTime=0;
                                    }
                                });
                            }     
                        });
                        
                        
                    }else{
                        _this.lastReceiveWebsocketTime++;
                    }
                    
                }, 4000)
            }
        },
        //设置浏览器全屏
        fullScreen(){
            var docElm = document.documentElement;
            //W3C 
            if (docElm.requestFullscreen) { 
                docElm.requestFullscreen(); 
            }
            //FireFox 
            else if (docElm.mozRequestFullScreen) { 
                docElm.mozRequestFullScreen(); 
            }
            //Chrome等 
            else if (docElm.webkitRequestFullScreen) { 
                docElm.webkitRequestFullScreen(); 
            }
            //IE11
            else if (docElm.msRequestFullscreen) {
                docElm.msRequestFullscreen();
            }
        },
        map(data,geoCoordMap){//地图
        var _this= this;
        var data = data;
        var geoCoordMap = geoCoordMap;
        var convertData = function (data) {
            var res = [];
            for (var i = 0; i < data.length; i++) {
                var geoCoord = geoCoordMap[data[i].name];
                if (geoCoord) {
                    res.push({
                        name: data[i].name,
                        value: geoCoord.concat(data[i].value)
                    });
                }
            }
            return res;
        };
        this.mapId = echarts.init(document.getElementById('map'));
        this.mapId.setOption({
          //backgroundColor: "none",
          title: false,
          tooltip:false,
          // tooltip : {
          //     trigger: 'item'
          // },
          bmap: {
              center: [104.114129, 37.550339],
              zoom:4,
              roam: true,
              mapStyle: {
                  styleJson: [
                          {
                              "featureType": "water",
                              "elementType": "all",
                              "stylers": {
                                  "color": "#044161"
                              }
                          },
                          {
                              "featureType": "land",
                              "elementType": "all",
                              "stylers": {
                                  "color": "#004981"
                              }
                          },
                          {
                              "featureType": "boundary",
                              "elementType": "geometry",
                              "stylers": {
                                  "color": "#064f85"
                              }
                          },
                          {
                              "featureType": "railway",
                              "elementType": "all",
                              "stylers": {
                                  "visibility": "off"
                              }
                          },
                          {
                              "featureType": "highway",
                              "elementType": "geometry",
                              "stylers": {
                                  "color": "#004981"
                              }
                          },
                          {
                              "featureType": "highway",
                              "elementType": "geometry.fill",
                              "stylers": {
                                  "color": "#005b96",
                                  "lightness": 1
                              }
                          },
                          {
                              "featureType": "highway",
                              "elementType": "labels",
                              "stylers": {
                                  "visibility": "off"
                              }
                          },
                          {
                              "featureType": "arterial",
                              "elementType": "geometry",
                              "stylers": {
                                  "color": "#004981"
                              }
                          },
                          {
                              "featureType": "arterial",
                              "elementType": "geometry.fill",
                              "stylers": {
                                  "color": "#00508b"
                              }
                          },
                          {
                              "featureType": "poi",
                              "elementType": "all",
                              "stylers": {
                                  "visibility": "off"
                              }
                          },
                          {
                              "featureType": "green",
                              "elementType": "all",
                              "stylers": {
                                  "color": "#056197",
                                  "visibility": "off"
                              }
                          },
                          {
                              "featureType": "subway",
                              "elementType": "all",
                              "stylers": {
                                  "visibility": "off"
                              }
                          },
                          {
                              "featureType": "manmade",
                              "elementType": "all",
                              "stylers": {
                                  "visibility": "off"
                              }
                          },
                          {
                              "featureType": "local",
                              "elementType": "all",
                              "stylers": {
                                  "visibility": "off"
                              }
                          },
                          {
                              "featureType": "arterial",
                              "elementType": "labels",
                              "stylers": {
                                  "visibility": "off"
                              }
                          },
                          {
                              "featureType": "boundary",
                              "elementType": "geometry.fill",
                              "stylers": {
                                  "color": "#029fd4"
                              }
                          },
                          {
                              "featureType": "building",
                              "elementType": "all",
                              "stylers": {
                                  "color": "#1a5787"
                              }
                          },
                          {
                              "featureType": "label",
                              "elementType": "all",
                              "stylers": {
                                  "visibility": "off"
                              }
                          }
                  ]
              }
          },
          series : [
              {
                  name: '',
                  type: 'scatter',
                  coordinateSystem: 'bmap',
                  data: convertData(data),
                  symbolSize: function (val) {
                      return val[2] * 5;
                  },
                  label: {
                      normal: {
                          formatter: '{b}',
                          position: 'right',
                          show: false
                      },
                      emphasis: {
                          show: true
                      }
                  },
                  itemStyle: {
                      normal: {
                          color: '#ddb926'
                      }
                  }
              },
              {
                  name: 'Top 5',
                  type: 'effectScatter',
                  coordinateSystem: 'bmap',
                  data: convertData(data.sort(function (a, b) {
                      return b.value - a.value;
                  }).slice(0, 0)),//不显示top5
                  symbolSize: function (val) {
                      return val[2] * 5;
                  },
                  showEffectOn: 'emphasis',
                  rippleEffect: {
                      brushType: 'stroke'
                  },
                  hoverAnimation: true,
                  label: {
                      normal: {
                          formatter: '{b}',
                          position: 'right',
                          show: true
                      }
                  },
                  itemStyle: {
                      normal: {
                          color: '#f4e925',
                          shadowBlur: 10,
                          shadowColor: '#333'
                      }
                  },
                  zlevel: 1
              },
              {
                  type: 'custom',
                  coordinateSystem: 'bmap',
                  renderItem: _this.renderItem,
                  itemStyle: {
                      normal: {
                          opacity: 0.5
                      }
                  },
                  animation: false,
                  silent: true,
                  data: [0],
                  z: -10
              }
          ]
        })
      },
      dataSales (){//今日销售商品排行TOP10
        var _this = this;
        this.chartLine = echarts.init(document.getElementById('dataSales'));
        this.chartLine.setOption({
              title: false,
              tooltip: {
                     trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
              },
              legend: {
                   orient: "vertical",
                    x: '40%',
                    top:'10%',
                    textStyle: {
                        color: '#fff' 
                    }
              },
        
              series: [
               {
                    name: '销售量',
                    type: 'pie',
                    radius: "50%",
                    center: ["25%", "50%"],
                    data:_this.chartData2,
                    itemStyle: {
                        normal:{
                          label:{show:false}
                        }
                       
                    },
                    labelLine:{
                          normal: {
                                show: false
                            }
                    }
                }
                ],
                 color: ['#f56236','#3949ab','#3593e5','#13adc2','#54a157','#c1c947','#ffba1a','#fff45c','#d80000','#8eaff9']

            });
      },
      bar(){//今日销售点位TOP10
        var _this = this;
        this.chartLine = echarts.init(document.getElementById('BarTiao'));
        this.chartLine.setOption({
              title: false,
              tooltip: {
                    trigger: 'axis'
              },
              legend: false,
              grid: {
                    top:'10%',
                    left: '3%',
                    right: '8%',
                    bottom: '3%',
                    containLabel: true,
              },
              xAxis: {
                    type: 'value',
                    boundaryGap: [0, 0.01],
                    axisPointer: {
                          type: 'shadow'
                    },
                    axisLabel: {
                            show: true,
                            textStyle: {
                                color: '#ffffff'
                            }
                    },
                    splitLine: {
                        show: false,
                        lineStyle:{
                             color: ['#1c2b40'],
                             width: 1,
                             type: 'solid'
                        }
                    }
              },
              yAxis: {
                    type: 'category',
                    axisLabel:{
                      color:'#fff'
                    },
                    data:_this.pointData.pointNames,//["2018-1","2018-2","2018-3","2018-4","2018-5","2018-6","2018-7","2018-8","2018-9","2018-10"],
                    splitLine: {
                        show: false,
                        lineStyle:{
                             color: ['#1c2b40'],
                             width: 1,
                             type: 'solid'
                        }
                    }
              },
              series: [
                    {
                      data:_this.pointData.sales,//[23,65,78,98,54,22,23,45,65,78],//_this.AnalysisData.salesList,//[23,65,78,98,54,22,23,45,65,78,98,54],//_this.AnalysisData.salesList,
                      name:"销售额",
                      type:"bar",
                      itemStyle: {
                          normal: {
                              label: {
                                show: true, //开启显示
                                position: 'right', //在上方显示
                                textStyle: { //数值样式
                                  color: '#fff',
                                  //fontSize: 16
                                  fontSize: 12
                                }
                              },
                              color: new echarts.graphic.LinearGradient(
                                  0, 0, 0, 1,
                                  [
                                      {offset: 0, color: '#83bff6'},
                                      {offset: 0.5, color: '#188df0'},
                                      {offset: 1, color: '#188df0'}
                                  ]
                              )
                          },
                          emphasis: {
                              color: new echarts.graphic.LinearGradient(
                                  0, 0, 0, 1,
                                  [
                                      {offset: 0, color: '#2378f7'},
                                      {offset: 0.7, color: '#2378f7'},
                                      {offset: 1, color: '#83bff6'}
                                  ]
                              )
                          }
                      }

                    }
                   
                   
                ],
                 color: ['#8eaff9','#d9363a','20e5b0']
            });
      },
      getData(type) {//按时间查营收分析
          if(type==1){
            this.isActive=true
            this.isActive1=false
          }else if(type==2){
            this.isActive1=true
            this.isActive=false
          }
          let _this = this;
          let para = {
               bdate:_this.startTime,
               edate:_this.endTime,
               type:type
          };
          getRevenue(para).then((res) => {
             if(res.zhead.reTCode ==="0000"){
                _this.monthsData = res.zbody.datas.months
                _this.AnalysisData.profitList=res.zbody.datas.profitList
                _this.AnalysisData.salesList=res.zbody.datas.salesList
                _this.AnalysisData.salesNumList=res.zbody.datas.salesNumList
                _this.RevenueAnalysis()
              }else{
                this.$message({
                      message:res.zhead.retMsg,
                      type:'success'
                });
              }
             
          });
      },
      RevenueAnalysis(){//营收分析
        var _this = this;
        this.chartLine = echarts.init(document.getElementById('RevenueAnalysis'));
        this.chartLine.setOption({
              title: false,
              tooltip: {
                    trigger: 'axis'
              },
              legend: {
                    data: ['销售额', '销售量', '净利润'],
                    x:"right",
                    y:"5",
                    textStyle: {
                        color: '#fff' 
                    }

              },
              grid: {
                    top:'30%',
                    left: '3%',
                    right: '3%',
                    bottom: '3%',
                    containLabel: true,
              },
              xAxis: {
                    type: 'category',
                    data:_this.monthsData,//["2018-1","2018-2","2018-3","2018-4","2018-5","2018-6","2018-7","2018-8","2018-9","2018-10","2018-11","2018-12",],//_this.monthsData,
                      axisPointer: {
                          type: 'shadow'
                      },
                      axisLabel: {
                            show: true,
                            textStyle: {
                                color: '#ffffff'
                            }
                        }
              },
              yAxis: {
                    type: 'value',
                    axisLabel:{
                      color:'#fff'
                    },
                    splitLine: {
                        show: true,
                        lineStyle:{
                             color: ['#1c2b40'],
                             width: 1,
                             type: 'solid'
                        }
                    }
              },
              series: [
                    {
                      data:_this.AnalysisData.salesList,//[23,65,78,98,54,22,23,45,65,78,98,54],//_this.AnalysisData.salesList,//[23,65,78,98,54,22,23,45,65,78,98,54],//_this.AnalysisData.salesList,
                      name:"销售额",
                    
                      type:"bar",
                      itemStyle: {
                          normal: {
                              label: {
                                      show: true,
                                      position: 'top',
                                      textStyle: {
                                        color: '#fff'
                                      }
                              },
                              color: new echarts.graphic.LinearGradient(
                                  0, 0, 0, 1,
                                  [
                                      {offset: 0, color: '#83bff6'},
                                      {offset: 0.5, color: '#188df0'},
                                      {offset: 1, color: '#188df0'}
                                  ]
                              )
                          },
                          emphasis: {
                              color: new echarts.graphic.LinearGradient(
                                  0, 0, 0, 1,
                                  [
                                      {offset: 0, color: '#2378f7'},
                                      {offset: 0.7, color: '#2378f7'},
                                      {offset: 1, color: '#83bff6'}
                                  ]
                              )
                          }
                      },
                    }
                    ,
                    {
                      data:_this.AnalysisData.salesNumList,//[22,23,21,38,34,62,43,35,55,68,78,84],//,
                      name:"销售量",
                      
                      type:"bar",
                      itemStyle: {
                          normal: {
                              color: new echarts.graphic.LinearGradient(
                                  0, 0, 0, 1,
                                  [
                                      {offset: 0, color: '#20e5b0'},
                                      {offset: 0.5, color: '#15d4a0'},
                                      {offset: 1, color: '#078f6a'}
                                  ]
                              )
                          },
                          emphasis: {
                              color: new echarts.graphic.LinearGradient(
                                  0, 0, 0, 1,
                                  [
                                      {offset: 0, color: '#20e5b0'},
                                      {offset: 0.7, color: '#15d4a0'},
                                      {offset: 1, color: '#078f6a'}
                                  ]
                              )
                          }
                      },
                    },
                    {
                      data:_this.AnalysisData.profitList,//[23,35,48,56,44,52,63,75,45,75,48,34],//,
                      name:"净利润",
                      type:"line",
                      color:'#d9363a',
                      smooth:true,
                      areaStyle: {
                        normal: {
                            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, 
                              [
                              {
                                  offset: 0,
                                  color: '#5f253b'
                              },
                              {
                                  offset: 0.5,
                                  color: '#742d2f'
                              },
                               {
                                  offset: 1,
                                  color: '#482440'
                              }
                            ]
                            )
                        }
                      },
                    }
                ],
                 color: ['#8eaff9','#d9363a','20e5b0']
            });
      },
      DataAnalysis(){//数据分析线图
        var _this = this;
        this.chartLine = echarts.init(document.getElementById('DataAnalysis'));
        this.chartLine.setOption({  
              title: false,
              tooltip: {
                    trigger: 'axis',

                    axisPointer: {
                          type: 'cross',
                          label: {
                              backgroundColor: '#6a7985'
                          }
                    }
              },
              legend: {
                    data: ['销售额', '销售量'],
                    x:"right",
                    y:"5",
                    textStyle: {
                        color: '#fff' 
                    }
              },
              toolbox: {
                 
              },
              grid: {
                    top:'30%',
                    left: '3%',
                    right: '3%',
                    bottom: '3%',
                    containLabel: true,
              },
              xAxis: {
                    type: 'category',
                    boundaryGap : false,
                    data:_this.DataAnalysisData.times,//["2018-1","2018-2","2018-3","2018-4","2018-5","2018-6","2018-7","2018-8","2018-9","2018-10","2018-11","2018-12"],//
                    axisLabel: {
                            show: true,
                            textStyle: {
                                color: '#ffffff'
                            }
                        }
              },
              yAxis: {
                    type: 'value',
                    axisLabel:{
                      color:'#fff'
                    },
                    splitLine: {
                        show: true,
                        lineStyle:{
                             color: ['#1c2b40'],
                             width: 1,
                             type: 'solid'
                        }
                    }
              },
              series: [
                    {
                      data:_this.DataAnalysisData.sales,//[23,65,78,98,54,22,23,45,65,78,98,54],//,
                      name:"销售额",
                      areaStyle: {
                           normal: {
                                
                                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, 
                                  [
                                      {
                                          offset: 0,
                                          color: '#3e4abb'
                                      },
                                      {
                                        offset: 0.5,
                                        color: '#222f77'
                                      },
                                      {
                                          offset: 1,
                                          color: '#182250'
                                      }
                                  ]
                                )
                            }
                      },
                      type:"line",
                      smooth:true,
                      itemStyle : {
                        normal : {
                          label: {
                                  show: true,
                                  position: 'top',
                                  textStyle: {
                                    color: '#fff'
                                  }
                           }
                        },
                      },
                    }
                    ,
                    {
                      data:_this.DataAnalysisData.salesNum,//[65,78,98,54,23,65,18,28,32,12,12,43],//,
                      name:"销售量",
                      smooth:true,
                      areaStyle: {
                        normal: {
                            
                            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, 
                              [
                              {
                                  offset: 0,
                                  color: '#5f253b'
                              },
                              {
                                  offset: 0.5,
                                  color: '#742d2f'
                              },
                               {
                                  offset: 1,
                                  color: '#482440'
                              }
                            ]
                            )
                        }
                      },
                      type:"line",
                      smooth:true,
                      itemStyle : {
                        normal : {
                          label: {
                                  show: true,
                                  position: 'top',
                                  textStyle: {
                                    color: '#fff'
                                  }
                           }
                        },
                      },
                    }
                ],
              color: ['#8eaff9','#d9363a']
            });
      },
      initWebSocket(){ //初始化weosocket 
          var _this=this;
          console.log("WebSocket初始化");
        //   const wsuri =this.WS_API+"/system/indexWebsocket?JSESSIONID="+_this.loginToken+"&corpId="+_this.loginCorpId+"&userId="+_this.loginUserId;//
          const wsuri =this.WS_API+"/system/indexWebsocket?JSESSIONID="+getToken()+"&corpId="+this.userInfo.corpId+"&userId="+this.userInfo.userId;//
          this.websock = new WebSocket(wsuri); 
        //   this.websock.onopen = this.websocketonopen;
          //this.websocket.onerror = this.websocketonerror;
          this.websock.onmessage = this.websocketonmessage; 
          this.websock.onclose = this.websocketclose;
　　　 },
　　　 websocketonopen() {
        console.log("WebSocket连接成功");
　　　 },
　　　 websocketonerror(e) { //错误
 　　　　console.log("WebSocket连接发生错误");
　　　 },
      websocketonmessage(e){ //数据接收 
                console.log("WebSocket接收消息");
                const _this = this;
                _this.lastReceiveWebsocketTime=0;
                const redata = JSON.parse(e.data);
                console.log("12345",redata)
                if(redata.type ==1){//运营总览
                   _this.OverviewData.totalSale = parseFloat(redata.data.totalSale).toFixed(2)
                   _this.OverviewData.totalSaleNum = parseFloat(redata.data.totalSaleNum)
                   _this.OverviewData.totalProfit = parseFloat(redata.data.totalProfit).toFixed(2)
                   _this.OverviewData.onlineMachine = parseFloat(redata.data.onlineMachine)
                   _this.OverviewData.outlineMachine = parseFloat(redata.data.outlineMachine)

                }else if(redata.type == 2){//近一个月总览
                    redata.data.forEach(function (c) {
                        if(c.type==1){//今日
                           _this.Today.totalProfit = parseFloat(c.totalProfit)
                           _this.Today.totalSale = parseFloat(c.totalSale)
                           _this.Today.totalSaleNum = parseFloat(c.totalSaleNum)
                        }else if(c.type==2){//昨日
                           _this.yesterday.totalProfit = parseFloat(c.totalProfit)
                           _this.yesterday.totalSale = parseFloat(c.totalSale)
                           _this.yesterday.totalSaleNum = parseFloat(c.totalSaleNum)
                        }else if(c.type==3){//近7天
                           _this.sevenDays.totalProfit = parseFloat(c.totalProfit)
                           _this.sevenDays.totalSale = parseFloat(c.totalSale)
                           _this.sevenDays.totalSaleNum = parseFloat(c.totalSaleNum)
                        }else if(c.type==4){//近一个月
                           _this.ThirtyDays.totalProfit = parseFloat(c.totalProfit)
                           _this.ThirtyDays.totalSale = parseFloat(c.totalSale)
                           _this.ThirtyDays.totalSaleNum = parseFloat(c.totalSaleNum)

                        }
                    })
                }else if(redata.type == 3){//营收分析
                     _this.monthsData =redata.data.months;
                     _this.AnalysisData.salesList = redata.data.salesList;
                     _this.AnalysisData.salesNumList = redata.data.salesNumList;
                     _this.AnalysisData.profitList = redata.data.profitList;
                     this.RevenueAnalysis()
                }else if(redata.type == 5){
                     this.DataAnalysisData.sales = redata.data.sales;
                     this.DataAnalysisData.salesNum = redata.data.salesNum;
                     this.DataAnalysisData.times = redata.data.times;
                     this.DataAnalysis()
                }else if(redata.type == 6){//今日销售点位排行TOP10
                    // this.pointData = redata.data
                    this.bar()
                }else if(redata.type == 7){//今日销售商品排行TOP10
                    _this.chartData2 = redata.data
                    this.dataSales()
                }else if(redata.type==8){//点位排行TOP10
                    this.pointData = redata.data
                }
　　　　　     console.log("WebSocket",redata); 
　　　 }, 
  　   websocketsend(agentData){//数据发送 
  　　　　　this.websock.send(agentData); 
  　　 }, 
　　　websocketclose(e){ //关闭 
            console.log("WebSocket关闭连接");
          　this.websock.close();
　　　　　//console.log("connection closed (" + e.code + ")"); 
　　　}
  },
  destroyed: function() {
　　　　 //页面销毁时关闭长连接
　　　　 this.websocketclose();
        //销毁定时器
        clearInterval(this.timer);
        this.timer = null;
　},
  mounted () {
    this.getData('1');
    
  },
  components: {
    nxCountUp,
    nxFullScreen
  },
}
</script>
<style>
.anchorBL{display:none;}
</style>
<style rel="stylesheet/scss" lang="scss" scoped>  
html{overflow: hidden;}
.data_bodey{
    width: 100%;
    height: 100%;
    min-width:1024px;
    background-image: url("../../assets/images/bcg.png");
    background-position:center;
    background-repeat:no-repeat;
    background-size: 100% 100%;
    //background-size:contain;
    position: absolute;
}
.home-top{height:30%; overflow:hidden;}
.home-top ul{margin:0; padding:0;}
.home-top ul li{width:20%;float:left; list-style:none; color:#fff; margin-top:6%}
.home-top ul li strong{display:block; font-size:14px; color:#fff; text-align:center; color:#a6daff;font-weight:normal; border-bottom:1px solid #334552; margin:0 3%; padding-bottom:5%;}
.home-top ul li span{display:block; color:#fff; text-align:center; font-size:38px;margin:0 20px; margin-top:2%;}

.home-line-column{height:30%;}
.line-column-l{width:48.5%;float:left; height: 100%; border:1px solid #5c81c0;border-radius:2px; margin-left:1%; position:relative}
.line-column-l h2{width:167px;height:36px; line-height:36px; font-weight:normal; background:url("../../assets/images/title_bcg.png") no-repeat; padding:0; text-align:center; font-size:14px;color:#fff;  margin:0; position:absolute; left:50%; top:-18px; margin-left:-84px;}
.line-column-r{width:48.5%;float:right; height: 100%;  border:1px solid #5c81c0;border-radius:2px;margin-right:1%; position:relative}
.line-column-r h2{width:167px;height:36px; line-height:36px; font-weight:normal; background:url("../../assets/images/title_bcg.png") no-repeat; padding:0; text-align:center; font-size:14px;color:#fff;  margin:0; position:absolute; left:50%; top:-18px; margin-left:-84px;}



.home-bar{height:38%; padding-top:2%}
.left_cage{
    width: 29%;
    height: 98%;
    margin-left: 1%;
    float: left; 
  
    position:relative;
    border:1px solid #5c81c0;
    border-radius:2px;

}
.left_cage h2{width:167px;height:36px; line-height:36px; font-weight:normal; background:url("../../assets/images/title_bcg.png") no-repeat; padding:0; text-align:center; font-size:14px;color:#fff;  margin:0; position:absolute; left:50%; top:-18px; margin-left:-84px;}
.center_cage{
    width: 38%;
    height: 98%;
    margin-left: 1%;
    margin-right:1%;
    float: left;
     position:relative;
    border:1px solid #5c81c0;
    border-radius:2px;
}
.center_cage h2{width:167px;height:36px; line-height:36px; font-weight:normal; background:url("../../assets/images/title_bcg.png") no-repeat; padding:0; text-align:center; font-size:14px;color:#fff;  margin:0; position:absolute; z-index:100; left:50%; top:-18px; margin-left:-84px;}
.right_cage{
    width: 29%;
    height: 98%;
    margin-right: 1%;
    float: right;
      position:relative;
    border:1px solid #5c81c0;
    border-radius:2px;
}
.right_cage h2{width:167px;height:36px; line-height:36px; font-weight:normal; background:url("../../assets/images/title_bcg.png") no-repeat; padding:0; text-align:center; font-size:14px;color:#fff;  margin:0; position:absolute; left:50%; top:-18px; margin-left:-84px;}

.tab{ position:absolute; left:3%; top:10px; z-index:10;}
.tab ul{margin:0; padding:0}
.tab ul li{list-style:none; float:left; background:#fff; margin-right:1px; font-size:12px;border-radius:3p; padding:2px 5px; color:#333; cursor:pointer}
.tab ul li.on{list-style:none; float:left;  color:#fff;   background:linear-gradient(#28d735,#13a810,);}
.dataLayer{ position:absolute; text-align:center; z-index:10;width:100%; top:15%; left:0;right:0;}
.dataLayer strong{ font-size:12px;color:#fff; font-weight:normal; padding:0 2%; display:inline-block}
.dataLayer strong span{color:#a6daff}
.screen{ position:absolute; z-index:10; right:20px; top:3px}
</style>