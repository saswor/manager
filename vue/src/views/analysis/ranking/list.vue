<template>
 <div class="div-warp">
      <div style="margin-bottom:20px;">
          <el-row>
                <el-col :span="24">
                  <div class="grid-content bg-purple bcg card-box">
                    <h2 class="block-title block-bai">今日销售线路排行TOP10</h2>
                    <div id="RevenueAnalysis" style="width:100%; height: 300px;"></div>
                  </div>
                </el-col>
              </el-row>
      </div>
      <div>
        <el-row :gutter="12">
        <el-col :span="12">
          <div class="grid-content bg-purple bcg card-box">
          <h2 class="block-title block-lv"><i class="ph-icon"></i>今日销售点位排行TOP10</h2>
          <wTable :data="tableData" :header="tableHeader"  :option="tableOption">
            <el-table-column slot="fixed"
              fixed
              type="index"
              label="序号"
              width="80">
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
      </div>
  </div>
</template>
<script>
import {getShowLineRank,getShowPointRank,getShowProductRank} from '@/api/analysis'
import echarts from 'echarts'
// import charts from '@/components/charts/charts.vue'
import NProgress from 'nprogress'
import wTable from '@/components/table/w-table.vue'
export default {
  data() {
      return {
        picData:[
              {"name":"可口可乐","value":22},
              {"name":"美年达","value":43},
              {"name":"雪碧","value":32},
              {"name":"红茶","value":42},
              {"name":"绿茶","value":12},
              {"name":"果粒橙","value":22},
              {"name":"酸奶","value":19}
        ],
        barData:{
          coordinate:[],//坐标
          saleMoney:[],//销售额
          saleNum:[],//销售量
          profit:[],//利润
        },
        AnalysisData:{
          profitList:[],
          salesList:[],
          salesNumList:[],
          coordinateList:[]
        },
        tableOption: {
            border: true,
            maxHeight: 350,
            height:300,
        },        
        tableHeader: [{//今日销售站点排行TOP10 ---表头
            prop: 'name',
            label: '点位名称',
            sortable: true,
            minWidth:'300',
            sortMethod: this.handleNameSort
            }, {
              prop: 'saleMoney',
              label: '销售额',
              minWidth: '120'
        }],

        tableData: []
      }
  },
  methods: {
    getShowLineRankData() {
          var _this = this;
          const listQuery = {
          }
          getShowLineRank(listQuery).then(response => {
            if(response.zhead.reTCode==="0000"){
              //数据倒转
              var tempList=response.zbody.datas.saleNum
              var tempListItem=tempList.pop();
              _this.barData.saleNum=[];
              while(tempListItem!=null&&tempListItem!=undefined&&tempListItem!=''){
                _this.barData.saleNum.push(tempListItem);
                tempListItem=tempList.pop();
              }

              _this.barData.coordinate=response.zbody.datas.coordinate
              // _this.barData.saleMoney=response.zbody.datas.saleMoney
              // _this.barData.saleNum=response.zbody.datas.saleNum
              // _this.barData.profit=response.zbody.datas.profit
              _this.Bar()
            }else{
               this.$notify({
                  title: '失败',
                  message: res.zhead.retMsg,
                  type: 'error'
                });
            }

          })
    },
    getShowPointRankData() {
          var _this = this;
          const listQuery = {
          }
          getShowPointRank(listQuery).then(response => {
            if(response.zhead.reTCode==="0000"){
              _this.tableData=response.zbody.datas
            }else{
               this.$notify({
                  title: '失败',
                  message: res.zhead.retMsg,
                  type: 'error'
                });
            }

          })
    },
    getShowProductRankData() {
          var _this = this;
          const listQuery = {
          }
          getShowProductRank(listQuery).then(response => {
            if(response.zhead.reTCode==="0000"){
              _this.picData=response.zbody.datas
            _this.Pic()
            }else{
               this.$notify({
                  title: '失败',
                  message: res.zhead.retMsg,
                  type: 'error'
                });
            }

          })
    },
    handleNameSort () {
      console.log('handleNameSort')
    },
    Bar(){//今日销售线路排行TOP10
        var _this = this;
        this.chartLine = echarts.init(document.getElementById('RevenueAnalysis'));
        this.chartLine.setOption({
              title: false,
              tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow'
                    }
              },
              legend: {
                  data: ['销售量']
              },
              grid: {
                    top:'15%',
                    left: '3%',
                    right: '3%',
                    bottom: '3%',
                    containLabel: true,
              },
              xAxis: {
                     type: 'value',
                      boundaryGap: [0, 0.01]
              },
              yAxis: {
                    type: 'category',
                    data:  _this.barData.coordinate
              },
              series: [
                    
                    {
                        name: '销售量',
                        type: 'bar',
                        data:  _this.barData.saleNum
       
                    }
                    
                    
                ]
            });
    },
    Pic(){//今日销售商品排行TOP10
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
                    data:_this.picData,
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
   
  },

  mounted () {
    this.getShowProductRankData()
    this.getShowPointRankData()
    this.getShowLineRankData()
  },
  components: {
    wTable
  }
}
</script>
<style scoped>
.ys-icon{ display:block; width:15px; height:15px; background:url("../../../assets/images/ys-icon.png"); background-size:100%; float:left; margin:17px 5px 0 0;}
.dateTab{position:absolute; right:10px; top:15px; z-index:10;}
.dateabslute{position:absolute; right:170px; top:15px; z-index:10;}
.bcg{background:#fff; border-radius:3px; padding:0 0px 20px 0px; box-shadow:0 2px 12px 0 rgba(0, 0, 0, 0.1)}
.block-title{ background:#fff; border-radius:3px; border-bottom-left-radius:0px;border-bottom-right-radius:0px; font-size:14px; line-height:50px; color:#333; font-weight:normal; padding-left:10px; }
.block-lv{ background:#fff}
.block-bai{ background:#fff; color:#333;}
.num-Div{text-align:center; color:#fff; position:relative; font-size:35px;}
.block-Title-Div{text-align:center; color:#fff; font-size:14px; margin-top:10px;}

.realTime{ position :absolute; background:rgba(255,255,255,0.5);  border-radius:3px; padding:2px 4px; font-size:12px; z-index:1; right:-10px;; top:-13px;}
.num-Div{text-align:center; color:#fff; position:relative; font-size:35px;}
.block-Title-Div{text-align:center; color:#fff; font-size:14px; margin-top:10px;}
  .div-warp{padding:0 20px;}
  .pageBox{background: #fff; padding:20px 0;}

</style>