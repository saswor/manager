<template>
 <div class="div-warp">
      <div class="search-warp">
        <el-row :gutter="20" >
            <el-col :span="3">
              <el-select size="small" v-model="searchObj.reportType" @change="timeType" clearable  placeholder="汇总类型">
                <el-option
                  key="1"
                  label="日报"
                  value="1">
                </el-option>
                <el-option
                  key="2"
                  label="月报"
                  value="2">
                </el-option>
                <el-option
                  key="3"
                  label="年报"
                  value="3">
                </el-option>
              </el-select>
            </el-col>
            <el-col :span="4" v-if="searchObj.reportType==3">
                <el-date-picker type="year" size="small" style="width:100%"  placeholder="选择年份" value-format="yyyy" v-model="searchObj.date"></el-date-picker>
            </el-col>
            <el-col :span="4" v-if="searchObj.reportType==2">
                <el-date-picker type="month" size="small" style="width:100%"  placeholder="选择月份" value-format="yyyy-MM" v-model="searchObj.date"></el-date-picker>
            </el-col>
            <el-col :span="4" v-if="searchObj.reportType==1">
                 <el-date-picker
                  style="width:100%" 
                  size="small"
                  type="date"
                  v-model="searchObj.date"
                  value-format="yyyy-MM-dd"
                  placeholder="选择时间">
                </el-date-picker>
            </el-col>
            <el-col :span="3">
              <el-select v-model="searchObj.districtId" size="small"   @change="getVendingLineData"  placeholder="请选择区域">
                <el-option
                  v-for="item in regionList"
                  :key="item.districtId"
                  :label="item.name"
                  :value="item.districtId">
                </el-option>
              </el-select>
            </el-col>
            <el-col :span="3">
              <el-select v-model="searchObj.lineId" size="small"  @change="getVendingPointData" placeholder="请选择线路">
                <el-option
                v-for="item in lineList"
                  :key="item.lineId"
                  :label="item.name"
                  :value="item.lineId">
                </el-option>
              </el-select>
            </el-col>
            
            <el-col :span="3">
              <div class="grid-content bg-purple">
                  <el-input
                  placeholder="售卖机编号"
                  size="small" 
                  v-model="searchObj.siteId"
                  clearable>
                </el-input>

              </div>
            </el-col>
            <el-col :span="5">
                  <el-button type="success" size="small"  @click.native.prevent="search" v-permission="['sys:rpt:order:select']">查询</el-button>
                  <el-button type="primary" size="small"  @click="exportOrderFile" v-permission="['sys:rpt:order:edit']">导出</el-button>
                
            </el-col>
        </el-row>
      </div>
      <div style="margin-bottom:20px">
         <el-row :gutter="12">
              <el-col :span="8">
                <el-card shadow="always bcblue">
                  <div class="num-Div counter-anim">
                    {{OverviewData.totalSale}}
                  </div>
                  <div class="block-Title-Div">累计销售额</div>
                </el-card>
              </el-col>
              <el-col :span="8">
                <el-card shadow="always bg-orange">
                  
                  <div class="num-Div">
                    <nx-count-up :start="1" :end="OverviewData.totalSaleNum"/>
                  </div>
                  <div class="block-Title-Div">累计销售量</div>
                </el-card>
              </el-col>
              <el-col :span="8">
                <el-card shadow="always bg-purple">
                  <div class="num-Div">
                      {{OverviewData.totalProfit}}
                  </div>
                  <div class="block-Title-Div">累计净利润</div>
                </el-card>
              </el-col>
          </el-row>
      </div>
      <div style="margin-bottom:20px;">
          <el-row>
                <el-col :span="24">
                  <div class="grid-content bg-purple bcg card-box">
                    <h2 class="block-title block-bai"><i class="ys-icon"></i>营收分析</h2>
                    <div id="RevenueAnalysis" style="width:100%; height: 300px;"></div>
                  </div>
                </el-col>
              </el-row>
      </div>
      <div style="font-size:14px; font-weight:bold; margin-bottom:10px;">订单明细</div>
      <div class="block-warp" >

      <wTable :data="tableData" :header="tableHeader"  :option="tableOption">
        
      </wTable>
        
      <div class="pageBox">
        <el-pagination
          @current-change="handleCurrentChange"
          background
          :page-size="pagesize"
          layout="prev, pager, next"
          :total="total">
        </el-pagination>
      </div>
    </div>
  </div>
</template>
<script>
import { getVendingLine,getVendingDistrict,getVendingPoint,getVending,getWaringSelect } from '@/api/dictionaries'
import {getOrderCollect,getIncomeAnalyze,getOrderDetail,exportOrder} from '@/api/analysis'
import echarts from 'echarts'
import nxCountUp from '@/components/nx-count-up/index.vue'
// import charts from '@/components/charts/charts.vue'
import NProgress from 'nprogress'
import wTable from '@/components/table/w-table.vue'
import FileSaver from 'file-saver'
import permission from '@/directive/permission/index.js'
import XLSX from 'xlsx'
export default {
  directives: { permission },
  data() {
        return {
          startTime:'',//开始时间
          endTime:'',//结束时间
          sarchDate: '近半年',
          keyword:'',
          total:1,
          searchObj:{
            lineId:"",
            reportType:"1",
            date:'',
            districtId:"",
            pointId:'',
            siteId:''
          },
          OverviewData:{
          totalSale:0, // 累计销售额
          totalSaleNum:0,// 累计销售量
          totalProfit:0 // 累计净利润

          },
          AnalysisData:{
            profitList:[],
            salesList:[],
            salesNumList:[],
            coordinateList:[]
          },
          regionList:[],//所有区域
          lineList:[],//线路
          pointList:[],//点位
          currentPage:1, 
          pagesize:10, 
          formLabelWidth: '120px',
          tableOption:{
            border: true
          },
          tableHeader: [
              {
                prop: 'orderId',
                label: '订单编号 ',
                width: '78',
                sortable: true,
                sortMethod: this.handleNameSort
              },
              {
                prop: 'pointName',
                label: '点位名称',
                 minWidth: '150'
             
              }, {
                prop: 'pointId',
                label: '点位编号',
                minWidth: '80'
              }, {
                prop: 'productName',
                label: '商品名称 ',
                minWidth: '100'
              }, {
                prop: 'payPrice',
                label: '出售价 ',
                minWidth: '100'
              }, {
                prop: 'profit',
                label: '利润 ',
                minWidth: '100'
              }, {
                prop: 'payTypeName',
                label: '方式 ',
                minWidth: '100'
              }, {
                prop: 'payStateName',
                label: '支付 ',
                minWidth: '100'
              }, {
                prop: 'outStateName',
                label: '出货状态 ',
                minWidth: '100'
              }, {
                prop: 'returnTypeName',
                label: '退款状态 ',
                minWidth: '100'
              }, {
                prop: 'createTime',
                label: '交易时间 ',
                minWidth: '100'
              }
                
          ],
          tableData: [],
        }
  },
  watch: {
    filterText(val) {
      this.$refs.tree2.filter(val)
    }
  },
  methods: {
      exportOrderFile(){
        let para = {
            reportType:this.searchObj.reportType,
            date:this.searchObj.date,
            districtId:this.searchObj.districtId,
            pointId:this.searchObj.pointId,
            siteId:this.searchObj.siteId
        }
        exportOrder(para).then((res) => {
              if(res.zhead.reTCode=="0000"){
                window.location.href=res.zhead.retMsg
              }else{
                this.$notify({
                  title: '失败',
                  message: res.zhead.retMsg,
                  type: 'error'
                });
            }
        });
      },
     timeType(){
        this.searchObj.date=""
     },
     handleCurrentChange: function(currentPage){ 
     this.currentPage = currentPage;
     this.getOrderDetailData(currentPage)
     },
   
    search(){
      this.getTotalReviewData()
      this.getIncomeAnalyzeData()
      this.getOrderDetailData("1")
    },
    handleNameSort () {
      console.log('handleNameSort')
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
                    data:_this.AnalysisData.coordinateList,
                    axisPointer: {type: 'shadow'}
              },
              yAxis: {
                    type: 'value',
                    axisLabel:{
                      color:'#000'
                    }
              },
              series: [
                    {
                      data:_this.AnalysisData.salesList,
                      name:"销售额",
                      color:"#FCC100",
                      type:"bar"
                    }
                    ,
                    {
                      data:_this.AnalysisData.salesNumList,
                      name:"销售量",
                      color:"#0CC2AA",
                      type:"bar"
                    },
                    {
                      data:_this.AnalysisData.profitList,
                      color:"#00FF00",
                      name:"净利润",
                      type:"line"
                    }
                ]
            });
    },

    getTotalReviewData() {//  销售统计接口
      var _this = this;
      const listQuery = {
        lineId:_this.searchObj.lineId,
       reportType:_this.searchObj.reportType,
       date:_this.searchObj.date,
       districtId:_this.searchObj.districtId,
       pointId:_this.searchObj.pointId,
       siteId:_this.searchObj.siteId
      }
      getOrderCollect(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
            NProgress.done();
            this.OverviewData.totalSale=response.zbody.datas.saleMoney// 累计销售额
            this.OverviewData.totalSaleNum=response.zbody.datas.saleNum  // 累计销售量
            this.OverviewData.totalProfit=response.zbody.datas.profit // 累计净利润
         }else{
             this.$notify({
              title: '失败',
              message: res.zhead.retMsg,
              type: 'error'
            });
         }
      })
    },
    getIncomeAnalyzeData() {//营收分析数据
      var _this = this;
      const listQuery = {
        lineId:_this.searchObj.lineId,
       reportType:_this.searchObj.reportType,
       date:_this.searchObj.date,
       districtId:_this.searchObj.districtId,
       pointId:_this.searchObj.pointId,
       siteId:_this.searchObj.siteId
      }
      getIncomeAnalyze(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
            NProgress.done();
            _this.AnalysisData.profitList=response.zbody.datas.profit
            _this.AnalysisData.salesList=response.zbody.datas.saleMoney
            _this.AnalysisData.salesNumList=response.zbody.datas.saleNum
            _this.AnalysisData.coordinateList=response.zbody.datas.coordinate
            _this.RevenueAnalysis()
         }else{
             this.$notify({
              title: '失败',
              message: res.zhead.retMsg,
              type: 'error'
            });
         }
      })
    },
    getOrderDetailData(page) {// 订单明细接口
      var _this = this;
      const listQuery = {
        lineId:_this.searchObj.lineId,
       reportType:_this.searchObj.reportType,
       date:_this.searchObj.date,
       districtId:_this.searchObj.districtId,
       pointId:_this.searchObj.pointId,
       siteId:_this.searchObj.siteId,
       pageSize:_this.pagesize,
       pageNum:page,
       orderByColumn:"createtime",
       isAsc:"desc"

      }
      getOrderDetail(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
            NProgress.done();
            _this.total = response.zbody.datas.total
            _this.tableData=response.zbody.datas.rows
         }else{
             this.$notify({
              title: '失败',
              message: res.zhead.retMsg,
              type: 'error'
            });
         }
      })
    },
    getVendingDistrictData() {//查询所有区域
      var _this = this;
      const listQuery = {
      }
      getVendingDistrict(listQuery).then(response => {
        if(response.zhead.reTCode==="0000"){
        _this.regionList = response.zbody.datas.rows;
        console.log("所有区域",_this.regionList)
        }else{
           this.$notify({
              title: '失败',
              message: res.zhead.retMsg,
              type: 'error'
            });
        }

      })
    },
    getVendingLineData(){//查所有线路
        var _this = this;
        const listQuery = {
          districtId:_this.searchObj.districtId
        }
        getVendingLine(listQuery).then(response => {
          if(response.zhead.reTCode==="0000"){
          _this.lineList = response.zbody.datas.rows;
          console.log("所有线路",_this.lineList)
          }else{
             this.$notify({
                title: '失败',
                message: res.zhead.retMsg,
                type: 'error'
              });
          }

        })
    },
    getVendingPointData(){//查询点位
        var _this = this;
        const listQuery = {
          lineId:_this.searchObj.lineId
        }
        getVendingPoint(listQuery).then(response => {
          if(response.zhead.reTCode==="0000"){
          _this.pointList = response.zbody.datas.rows;
          console.log("查询点位",_this.lineList)
          }else{
             this.$notify({
                title: '失败',
                message: res.zhead.retMsg,
                type: 'error'
              });
          }

        })
    },
    exportExcel () {
         /* generate workbook object from table */
         var wb = XLSX.utils.table_to_book(document.querySelector('.block-warp'))
         /* get binary string as output */
         var wbout = XLSX.write(wb, { bookType: 'xlsx', bookSST: true, type: 'array' })
         try {
             FileSaver.saveAs(new Blob([wbout], { type: 'application/octet-stream' }), '订单明细.xlsx')
         } catch (e) { if (typeof console !== 'undefined') console.log(e, wbout) }
         return wbout
    }
  },

  mounted () {
    this.getVendingDistrictData()
    this.getTotalReviewData()
    this.getIncomeAnalyzeData()
    this.getOrderDetailData("1")

  },
  components: {
    wTable,nxCountUp
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