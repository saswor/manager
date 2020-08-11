<template>
 <div class="div-warp">
    <div class="search-warp">
          <el-row :gutter="20" >
              <el-col :span="4">
                <el-select v-model="searchObj.lineId" size="small" clearable placeholder="请选择线路名称" class="Input-100">
                    <el-option
                      v-for="item in lineList"
                      :key="item.lineId"
                      :label="item.name"
                      :value="item.lineId">
                    </el-option>
                  </el-select>
              </el-col>
              <el-col :span="4">
                  <el-input placeholder="补货编号" v-model="searchObj.sOrderId"  size="small" clearable>
                  </el-input>
              </el-col>
               <el-col :span="4">
                  <el-autocomplete
                size="small"
                v-model="userName"
                  :value="userName"
                  :fetch-suggestions="querySearchAsync1"
                  value-key="userName"
                  placeholder="搜索补货人员"
                  clearable
                  @focus="handeFocus1"
                  @select="handleSelect1"
                ></el-autocomplete>
              </el-col>
              <el-col :span="4">
                 <el-select style="width:100%" v-model="searchObj.curState" size="small" placeholder="补货状态" clearable>
                    <el-option
                      v-for="item in curStateList"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
              </el-col>
              <el-col :span="4">
                <el-autocomplete
                  size="small"
                  style="width:100%"
                  v-model="searchObj.wmId"
                  :fetch-suggestions="querySearchAsync"
                   value-key="stockName"
                   :value="stockName"
                  placeholder="搜索仓库"
                  clearable
                  @focus="handeFocus"
                  @select="handleSelect"
                ></el-autocomplete>
              </el-col>
          </el-row>
          <el-row :gutter="20" style="margin-top:20px;">
              
              
               <el-col :span="4">
                <el-date-picker type="date" size="small" placeholder="开始时间" value-format="yyyy-MM-dd" v-model="searchObj.startDate" style="width:100%"></el-date-picker>
              </el-col>
              <el-col :span="4">
                   <el-date-picker type="date" size="small" placeholder="结束时间"  value-format="yyyy-MM-dd"  v-model="searchObj.endDate" style="width:100%"></el-date-picker>
              </el-col>
               <el-col :span="4">
                   <el-button type="success" @click.native.prevent="search" size="small" v-permission="['sys:supply:order:select']">查询</el-button>

				   <el-button type="primary" size="small"  @click="exportOrderFile" v-permission="['sys:supply:order:select']">导出</el-button>

              </el-col>
          </el-row>
    </div>
    <div class="block-warp">
            <wTable :data="tableData" ref="multipleTable" :header="tableHeader"  :option="tableOption">
              <el-table-column slot="fixed"
                fixed="right"
                label="操作"
                width="50">
                  <template  scope="scope">
                    <div>
                       <el-button type="success" @click="handleClickDianUrl(scope.row)" icon="el-icon-view" size="mini" circle v-permission="['sys:supply:order:view']"></el-button>

                    </div>
                  </template>
              </el-table-column>
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
import { getStockInfo,getVendingLine,getUserByName} from '@/api/dictionaries'
import { supplyorderList,exportLineSupplyOrder} from '@/api/replenishment'
import NProgress from 'nprogress'
import wTable from '@/components/table/w-table.vue'
import permission from '@/directive/permission/index.js'
export default {
  directives: { permission },
  data() {
        return {
          stockName:'',
          userName:"",
          StockList:[],//仓库列表
          curStateList: [{
            value: '1',
            label: '等待补货'
            }, {
              value: '2',
              label: '补货中'
            }, {
              value: '3',
              label: '补货完成'
            }
          ],
          searchObj:{
            sOrderId:"",
            lineId:"",
            supplierId:'',
            curState:'',
            startTime:"",
            endTime:"",
            wmId:"",
          },
          lineList:[],
          form:{},
          total:1,
          PointTotal:1,
          currentPage:1, 
          pagesize:10,
          formLabelWidth: '120px',
          dialogAddTableVisible:false,//控制新增弹出显示隐藏
          dialogEditTableVisible:false,//控制编辑弹出显示隐藏
          tableOption:{
            border: true
          },
          tableHeader: [
              {
                prop: 'sOrderId',
                label: '补货编号 ',
                width: '78',
                sortable: true,
                sortMethod: this.handleNameSort
              },
              {
                prop: 'lineId',
                label: '线路编号',
                 minWidth: '200'
             
              }, {
                prop: 'lineName',
                label: '线路名称',
                minWidth: '120'
              }, {
                prop: 'wmId',
                label: '仓库编号',
                minWidth: '120'
              }, {
                prop: 'wmName',
                label: '仓库名称',
                minWidth: '120'
              }, {
                prop: 'supplierName',
                label: '补货员名称',
                minWidth: '120'
              }, {
                prop: 'curStateName',
                label: '补货状态',
                minWidth: '120'
              }, {
                prop: 'stockStateName',
                label: '库存状态',
                minWidth: '120'
              }, {
                prop: 'supplyNum',
                label: '需补商品数',
                minWidth: '120'
              }, {
                prop: 'num',
                label: '需补站点数',
                minWidth: '120'
              }, {
                prop: 'createTime',
                label: '创建时间',
                minWidth: '120'
              }
          ],
          tableData: [],
        }
  },
  watch: {
   
  },
  methods: {
	//导出补货单
	exportOrderFile(){
        let para = {
            sOrderId:this.searchObj.sOrderId,
			lineId:this.searchObj.lineId,
			startDate:this.searchObj.startDate,
			endDate:this.searchObj.endDate,
			wmId:this.searchObj.wmId,
			supplierId:this.searchObj.supplierId,
        }
        exportLineSupplyOrder(para).then((res) => {
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
    search(){//查询
      this.getSupplyorderList("1")
    },
    handleAdd() {//显示新增窗口
        this.$router.push(
          {path:'/model/addModel'}
        );
    },
    querySearchAsync(queryString, cb) {
        var _this = this;
        const listQuery = {
          stockName:_this.stockName
        }
        getStockInfo(listQuery).then(response => {
          _this.StockList = response.zbody.datas.rows
          var StockList = _this.StockList;
          var results = queryString ? StockList.filter(this.createStateFilter(queryString)) : StockList;
          cb(results);
        })
    },
    createStateFilter(queryString) {
        return (state) => {
          return (state.stockName.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
        };
    },
    handleSelect(item) {
        this.searchObj.wmId =item.stockId
        this.stockName = item.stockName
        console.log(item);
    },
    handeFocus(item){
      this.stockName=""
      this.searchObj.wmId=""
    },
    getSupplyorderList(page){
      var _this = this;
      const listQuery = {
        sOrderId:this.searchObj.sOrderId,
        lineId:this.searchObj.lineId,
        startDate:this.searchObj.startDate,
        endDate:this.searchObj.endDate,
        wmId:this.searchObj.wmId,
        supplierId:this.searchObj.supplierId,
        curState:this.searchObj.curState,
        pageSize:this.pagesize,
        pageNum:page,
        orderByColumn:'createtime',
        isAsc:'desc'//desc
      }
      supplyorderList(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
            NProgress.done();
            _this.total = response.zbody.datas.total;  
            _this.tableData = response.zbody.datas.rows;
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
    handleSizeChange: function (size) { 
       this.pagesize = size; 
    }, 
    handleCurrentChange: function(currentPage){ 
     this.currentPage = currentPage;
     this.getSupplyorderList(currentPage)
    },
    handleClickDianUrl(row) {
        localStorage.setItem("editData",JSON.stringify(row) );
        this.$router.push(
          {
            path:'/goodsRecord/orderdetail',
            query:{sOrderId:row.sOrderId}
          }
        );
       
    },
    //补货员搜索
    querySearchAsync1(queryString, cb) {
        var _this = this;
        const listQuery = {
          userName:_this.userName,
          roleId:3
        }
        getUserByName(listQuery).then(response => {
          _this.ByNameList = response.zbody.datas
          var ByNameList = _this.ByNameList;
          var results = queryString ? ByNameList.filter(this.createStateFilter1(queryString)) : ByNameList;
          cb(results);
        })
    },
    createStateFilter1(queryString) {
        return (state) => {
          return (state.userName.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
        };
    },
    handleSelect1(item) {
        this.searchObj.supplierId = item.loginName
        this.userName = item.userName
        console.log(item);
    },
    handeFocus1(item){
      this.userName=""
      this.searchObj.supplierId=""
    },
  },

  mounted () {
    this.getSupplyorderList("1")
    this.getVendingLineData()
  },
  components: {
    wTable
  }
}
</script>
<style scoped>
  .operation-box{padding-top:4px;}
  .screen-box{padding: 0px 0 20px 0}
  .selectBox{padding:10px 0;}
  .selectBox strong{font-size:14px;color:#333; font-weight: normal; display: inline-block; }
  .selectBox .select-left{ padding-left:10px; }
  .searchBox{ padding: 10px 0; }
  .searchBox strong{ display: inline-block;float: left; line-height: 35px; font-size:14px; font-weight: normal; }
  .searchBox .inputStyle{width: 300px; display: inline-block;float:left; margin-right: 15px; }
  .tit{font-size:12px; line-height: 30px;}
  .num-right{display: inline-block;float: right; margin-top: 11px; font-size:12px;}
  .num-right span{ display: inline-block; padding-left: 20px; }
  .num-right span i{ display: inline-block; padding-left:10px; font-style:normal; color:#006BC2; }
  .num-right span i.Danger{color:#F56C6C;}
  .block-warp{background: #fff; margin: 20px 0; padding:10px; border-radius: 3px;}
  .bntBox{overflow: hidden; padding:0px 0 10px 0;}
  .div-warp{padding:0 20px;  overflow: hidden;}
  .pageBox{background: #fff; padding:20px 0; margin:20px 0;}
</style>