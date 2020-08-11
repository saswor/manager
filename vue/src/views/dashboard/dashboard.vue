<template>
  <div class="dashboard-container">
      <h2 class="title">运营总览</h2>
      <el-row :gutter="12">
        <el-col :span="5">
          <el-card shadow="always bcblue">
            <div class="num-Div counter-anim">
              <span class="realTime">实时</span>
              {{OverviewData.totalSale}}
            </div>
            <div class="block-Title-Div">累计销售额</div>
          </el-card>
        </el-col>
        <el-col :span="5">
          <el-card shadow="always bg-orange">
            
            <div class="num-Div">
              <span class="realTime">实时</span>
              <nx-count-up :start="0" :end="OverviewData.totalSaleNum"/>
            </div>
            <div class="block-Title-Div">累计销售量</div>
          </el-card>
        </el-col>
        <el-col :span="5">
          <el-card shadow="always bg-purple">
            
            <div class="num-Div">
              <span class="realTime">实时</span>
              {{OverviewData.totalProfit}}
            </div>
            <div class="block-Title-Div">累计毛利润</div>
          </el-card>
        </el-col>
        <el-col :span="5">
          <el-card shadow="always bg-success">
          
            <div class="num-Div grid-block"  @click="toPage('4')">
                <span class="realTime">实时</span>
              <nx-count-up :start="0" :end="OverviewData.onlineMachine"/>
            </div>
            <div class="block-Title-Div">在线设备</div>
          </el-card>
        </el-col>
        <el-col :span="4">
          <el-card shadow="always bg-powder">
            <div class="num-Div grid-block"  @click="toPage('5')">
              <span class="realTime">实时</span>
              <nx-count-up :start="0" :end="OverviewData.outlineMachine"/>
            </div>
            <div class="block-Title-Div">离线设备</div>
          </el-card>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24" >
          <div class="grid-content bg-purple-dark bcg card-box">
              <h2 class="block-title block-bai"><i class="zl-icon"></i>近一个月总览</h2>
              <el-tabs v-model="activeName" @tab-click="handleClick"  tab-position="left">
                <el-tab-pane label="今日" name="first">
                  <el-row>
                    <el-col :span="8">
                        <div class="grid-content bg-purple border-rig">
                              <div class="num-Div-1">
                                  {{Today.totalSale.toFixed(2)}}
                              </div>
                              <div class="block-Title-Div-1">销售额</div>
                        </div>
                      </el-col>
                      <el-col :span="8"><div class="grid-content bg-purple-light border-rig">
                            <div class="num-Div-1">
                                 <nx-count-up :start="0" :end="Today.totalSaleNum"/>
                            </div>
                            <div class="block-Title-Div-1">销售量</div>
                      </div></el-col>
                      <el-col :span="8">
                        <div class="grid-content bg-purple">
                              <div class="num-Div-1">
                               {{Today.totalProfit.toFixed(2)}}
                              </div>
                              <div class="block-Title-Div-1">净利润</div>
                        </div>
                    </el-col>
                  </el-row>

                </el-tab-pane>
                <el-tab-pane label="昨日" name="second">
                    <el-row>
                      <el-col :span="8">
                          <div class="grid-content bg-purple">
                                <div class="num-Div-1">
                                     {{yesterday.totalSale.toFixed(2)}}
                                </div>
                                <div class="block-Title-Div-1">销售额</div>
                          </div>
                        </el-col>
                        <el-col :span="8"><div class="grid-content bg-purple-light">
                              <div class="num-Div-1">
                                  <nx-count-up :start="0" :end="yesterday.totalSaleNum"/>
                              </div>
                              <div class="block-Title-Div-1">销售量</div>
                        </div></el-col>
                        <el-col :span="8">
                          <div class="grid-content bg-purple">
                                <div class="num-Div-1">
                                    {{yesterday.totalProfit.toFixed(2)}}
                                </div>
                                <div class="block-Title-Div-1">净利润</div>
                          </div>
                      </el-col>
                    </el-row>

                </el-tab-pane>
                <el-tab-pane label="近7天" name="third">
                    <el-row>
                      <el-col :span="8">
                          <div class="grid-content bg-purple">
                                <div class="num-Div-1">
                                  {{sevenDays.totalSale.toFixed(2)}}
                                </div>
                                <div class="block-Title-Div-1">销售额</div>
                          </div>
                        </el-col>
                        <el-col :span="8"><div class="grid-content bg-purple-light">
                              <div class="num-Div-1">
                                <nx-count-up :start="0" :end="sevenDays.totalSaleNum"/>
                              </div>
                              <div class="block-Title-Div-1">销售量</div>
                        </div></el-col>
                        <el-col :span="8">
                          <div class="grid-content bg-purple">
                                <div class="num-Div-1">
                                  {{sevenDays.totalProfit.toFixed(2)}}
                                </div>
                                <div class="block-Title-Div-1">净利润</div>
                          </div>
                      </el-col>
                    </el-row>

                </el-tab-pane>
                <el-tab-pane label="近30天" name="fourth">
                      <el-row>
                        <el-col :span="8">
                            <div class="grid-content bg-purple">
                                  <div class="num-Div-1">
                                    {{ThirtyDays.totalSale.toFixed(2)}}
                                  </div>
                                  <div class="block-Title-Div-1">销售额</div>
                            </div>
                          </el-col>
                          <el-col :span="8"><div class="grid-content bg-purple-light">
                                <div class="num-Div-1">
                                  <nx-count-up :start="0" :end="ThirtyDays.totalSaleNum"/>

                                </div>
                                <div class="block-Title-Div-1">销售量</div>
                          </div></el-col>
                          <el-col :span="8">
                            <div class="grid-content bg-purple">
                                  <div class="num-Div-1">
                                    {{ThirtyDays.totalProfit.toFixed(2)}}
                                  </div>
                                  <div class="block-Title-Div-1">净利润</div>
                            </div>
                        </el-col>
                      </el-row>
                </el-tab-pane>
              </el-tabs>


          </div>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <div class="grid-content bg-purple bcg card-box">
                     <div class="dateabslute">
                <el-date-picker
                style="width:110px; float:left;"
                size="mini"
                v-model="startTime"
                clearable
                type="month"
                value-format="yyyy-MM"
                placeholder="开始时间"
               >
               </el-date-picker>
               <div style="float:left; font-size:16px; display:inline-block; line-height:26px; padding:0 10px; color:#333">-</div>
               <el-date-picker
               style="width:110px; float:left;"
                v-model="endTime"
                type="month"
                clearable
                size="mini"
                value-format="yyyy-MM"
                placeholder="结束时间"
               >
              </el-date-picker>
              <el-button  size="mini" icon="el-icon-search" style="margin-left:5px;" @click="searchAnaly">查询</el-button>
            </div>
            <div class="dateTab">
              <el-radio-group v-model="sarchDate" size="mini">
                <el-radio-button label="近半年"></el-radio-button>
                <el-radio-button label="近一年"></el-radio-button>
              </el-radio-group>
            </div>
            <h2 class="block-title block-bai"><i class="ys-icon"></i>营收分析</h2>
            <div id="RevenueAnalysis" style="width:100%; height: 300px;"></div>
          </div>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="24">
          <div class="grid-content bg-purple-light bcg card-box ">
            <h2 class="block-title block-lv"><i class="fx-icon"></i>数据分析</h2>
             <div id="DataAnalysis" style="width:100%; height: 300px;"></div>
            <!-- <charts :width="500" :height="350" :seriesData="seriesData1" :chartData="chartData1" chartType="line" ref="line"></charts> -->
          </div>
        </el-col>
      </el-row>
      <el-row :gutter="12">
        <el-col :span="12">
          <div class="grid-content bg-purple bcg card-box">
          <h2 class="block-title block-lv"><i class="ph-icon"></i>今日销售点位排行TOP10</h2>
          <wTable :data="tableData" :header="tableHeader"  :option="tableOption">
            <el-table-column slot="fixed"
              fixed
              type="index"
              label="序号"
              width="50">
            </el-table-column>
          </wTable>
        </div>
        </el-col>
          <el-col :span="12">
            <div class="grid-content bg-purple bcg card-box">
              <h2 class="block-title block-bai"><i class="sp-icon"></i>今日销售商品排行TOP10</h2>
              <div id="dataSales" style="width:100%; height: 300px;"></div>
             <!--  <charts :width="450" :height="350" :chartData="chartData2" chartType="pie" ref="pie"></charts> -->
            </div>
        </el-col>
      </el-row>
 <!--    <div class="dashboard-text">name:{{name}}</div>
    <div class="dashboard-text">roles:<span v-for='role in roles' :key='role'>{{role}}</span></div>
    <div>corpId:{{corpId}}</div> -->

  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import echarts from 'echarts'
import wTable from '@/components/table/w-table.vue'
import nxCountUp from '@/components/nx-count-up/index.vue'
import { getRevenue } from '@/api/index'
import { getToken } from '@/utils/auth'
const viewName = 'i18nView'
export default {
  name: 'dashboard',
  data() {
      return {
        startTime:'',//开始时间
        endTime:'',//结束时间
        sarchDate: '近半年',
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
        websock: null,
        activeName: 'first',
        tabPosition: "line",
        monthsData: [],//营收分析X轴数据
        AnalysisData:{//营收分析数据
          profitList:[],
          salesList:[],
          salesNumList:[]
        },
        
        chartData2:[],
        chartRefs: ["line", "bar", "pie"],
        _titleText:"111",
     
        seriesData1:[
          {
            name:'销售量',
            color:"#FCC100",
            type:'line',
            data:[]
          },
          {
              name:'销售额',
              type:'line',
              color:"#0CC2AA",
              data:[]
          }
        ],
        tableOption: {
            border: true,
            maxHeight: 350,
            height:300,
        },        
        tableHeader: [{//今日销售点位排行TOP10 ---表头
            prop: 'saleName',
            label: '点位名称',
            sortable: true,
            minWidth:'300',
            sortMethod: this.handleNameSort
            }, {
              prop: 'saleMoney',
              label: '销售额',
              minWidth: '120'
        }],

        tableData: []//今日销售点位排行TOP10 ---数据
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
    this.initWebSocket()
  },
  created(){
     
  },
  watch:{
    'sarchDate'(val){//检测选择半年一年的数据变化
      if(val ==="近半年")
      {   
        this.startTime = "";
        this.endTime = "";
        this.getData("1")
      }
      else if(val ==="近一年")
      {
        this.startTime = "";
        this.endTime = "";
        this.getData("2")
      }
      else
      {
        this.getData("0")
      }
    }
  },
  methods: {
      //跳转到售货机页面
      toPage(data){
        console.log
        this.$router.push({
          path:'/equipment/machine/index',
            query:{
              AtcRadio:data
            }
        });
      },
      searchAnaly(){
        var startTime = new Date(this.startTime).getTime()
        var endTime = new Date(this.endTime).getTime()
        if(this.startTime ==""||this.startTime ==null)
        {
            this.$message({message:"请输入开始时间",type:'warning'});
            this.endTime = ""
        }else if(startTime>endTime){
            this.$message({message:"开始时间不得大于结束时间",type:'warning'});
            this.endTime = ""
        }
        else
        {
            this.getData("0")
        }
      },
      getData(type) {//按时间查营收分析
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
      RevenueAnalysis(){//营收分析混合图
        var _this = this;
        this.chartLine = echarts.init(document.getElementById('RevenueAnalysis'));
        this.chartLine.setOption({
              title: false,
              tooltip: {
                    trigger: 'axis'
              },
              legend: {
                    data: ['销售额', '销售量', '净利润'],
                    textStyle: {
                        color: '#000' 
                    }
              },
              grid: {
                    top:'15%',
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
                      }
              },
              yAxis: {
                    type: 'value',
                    axisLabel:{
                      color:'#000'
                    }
              },
              series: [
                    {
                      data:_this.AnalysisData.salesList,//[23,65,78,98,54,22,23,45,65,78,98,54],//_this.AnalysisData.salesList,
                      name:"销售额",
                      color:"#FCC100",
                      type:"bar"
                    }
                    ,
                    {
                      data:_this.AnalysisData.salesNumList,//[22,23,21,38,34,62,43,35,55,68,78,84],//,
                      name:"销售量",
                      color:"#0CC2AA",
                      type:"bar"
                    },
                    {
                      data:_this.AnalysisData.profitList,//[23,35,48,56,44,52,63,75,45,75,48,34],//,
                      color:"#00FF00",
                      name:"净利润",
                      type:"line"
                    }
                ]
            });
      },
      DataAnalysis(){//数据分析线图
        var _this = this;
        this.chartLine = echarts.init(document.getElementById('DataAnalysis'));
        this.chartLine.setOption({
              title: false,
              tooltip: {
                    trigger: 'axis'
              },
              legend: {
                    data: ['销售额', '销售量'],
                    textStyle: {
                        color: '#000' 
                    }
              },
              grid: {
                    top:'15%',
                    left: '3%',
                    right: '20%',
                    bottom: '3%',
                    containLabel: true,
              },
              xAxis: {
                    type: 'category',
                    data:_this.DataAnalysisData.times,//["2018-1","2018-2","2018-3","2018-4","2018-5","2018-6","2018-7","2018-8","2018-9","2018-10","2018-11","2018-12",]//
              },
              yAxis: {
                    type: 'value',
                    axisLabel:{
                      color:'#000'
                    }
              },
              series: [
                    {
                      data:_this.DataAnalysisData.sales,//[23,65,78,98,54,22,23,45,65,78,98,54],//,
                      name:"销售额",
                      color:"#FCC100",
                      type:"line"
                    }
                    ,
                    {
                      data:_this.DataAnalysisData.salesNum,//[65,78,98,54,23,65,18,28,32,12,12,43],//,
                      name:"销售量",
                      color:"#0CC2AA",
                      type:"line"
                    }
                ]
            });
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
                    x: '80%',
                    top:'50',
              },
        
              series: [
               {
                    name: '销售量',
                    type: 'pie',
                    radius: "55%",
                    center: ["40%", "50%"],
                    data:_this.chartData2,//[
                    // {"name":"可口可乐","value":22},
                    //  {"name":"美年达","value":43},
                    //   {"name":"雪碧","value":32},
                    //    {"name":"红茶","value":42},
                    //     {"name":"绿茶","value":12},
                    //      {"name":"果粒橙","value":22},
                    //       {"name":"酸奶","value":19}

                    // ],//,
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
                ]
            });
      },
      handleClick(tab, event) {
        console.log(tab, event);
      },
     initWebSocket(){ //初始化weosocket 
          console.log("websocket前缀:"+this.WS_API)
          
          console.log("url前缀:"+this.ApiUrl)
          const wsuri =this.WS_API+"/system/indexWebsocket?JSESSIONID="+getToken()+"&corpId="+this.corpId+"&userId="+this.userinfo.userId;//
          this.websock = new WebSocket(wsuri); 
          //this.websocket.onopen = this.websocketonopen;
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
                const _this = this;
                const redata = JSON.parse(e.data);
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
                    var tempList=redata.data
                    var tempListItem=tempList.pop();
                    this.tableData=[];
                    while(tempListItem!=null&&tempListItem!=undefined&&tempListItem!=''){
                      this.tableData.push(tempListItem);
                      tempListItem=tempList.pop();
                    }
                    //this.tableData = redata.data
                }else if(redata.type == 7){//今日销售商品排行TOP10
                    _this.chartData2 = redata.data
                    this.dataSales()
                }
　　　　　console.log(redata); 
　　　}, 
　    websocketsend(agentData){//数据发送 
　　　　　this.websock.send(agentData); 
　　　}, 
　　　websocketclose(e){ //关闭 
          　this.websock.close();
　　　　　//console.log("connection closed (" + e.code + ")"); 
　　　}
  },
  destroyed: function() {
　　　　//页面销毁时关闭长连接
　　　　this.websocketclose();
　},
  mounted(){
     //临时使用
     // this.dataSales()
     // this.DataAnalysis()
     // this.RevenueAnalysis()
     //临时使用结束
  },
  components: {
     wTable,
     nxCountUp
     //charts
  }
}
</script>
<style rel="stylesheet/scss" lang="scss" scoped>
.dashboard-container{min-width:1000px;}
.dateabslute{position:absolute; right:170px; top:15px; z-index:3;}
.dateTab{position:absolute; right:10px; top:15px; z-index:3;}
.zl-icon{ display:block; width:15px; height:15px; background:url("../../assets/images/zl-icon.png"); background-size:100%; float:left; margin:17px 5px 0 0;}

.ys-icon{ display:block; width:15px; height:15px; background:url("../../assets/images/ys-icon.png"); background-size:100%; float:left; margin:17px 5px 0 0;}

.fx-icon{ display:block; width:15px; height:15px; background:url("../../assets/images/fx-icon.png"); background-size:100%; float:left; margin:17px 5px 0 0;}

.ph-icon{ display:block; width:15px; height:15px; background:url("../../assets/images/ph-icon.png"); background-size:100%; float:left; margin:17px 5px 0 0;}

.sp-icon{ display:block; width:15px; height:15px; background:url("../../assets/images/sp-icon.png"); background-size:100%; float:left; margin:17px 5px 0 0;}


.realTime{ position :absolute; background:rgba(255,255,255,0.5);  border-radius:3px; padding:2px 4px; font-size:12px; z-index:1; right:-10px;; top:-13px;}
.border-rig{border-right:1px solid #ddd}
.bcg{background:#fff; border-radius:3px; padding:0 0px 20px 0px; box-shadow:0 2px 12px 0 rgba(0, 0, 0, 0.1)}
.block-title{ background:#fff; border-radius:3px; border-bottom-left-radius:0px;border-bottom-right-radius:0px; font-size:14px; line-height:50px; color:#333; font-weight:normal; padding-left:10px; }
.block-lv{ background:#fff}
.block-bai{ background:#fff; color:#333;}
.num-Div{text-align:center; color:#fff; position:relative; font-size:35px;}
.block-Title-Div{text-align:center; color:#fff; font-size:14px; margin-top:10px;}

.num-Div-1{text-align:center; color:#333; font-size:30px; margin-top:30px;background:url("../../assets/images/minus.jpg") no-repeat center bottom; background-size:50px 1px; padding-bottom:10px; }
.block-Title-Div-1{text-align:center; color:#999; font-size:14px; margin-top:10px;}
  .grid-block{cursor: pointer;}
.title{ font-size:18px; }
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
</style>
