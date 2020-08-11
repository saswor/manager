<template>
 <div class="div-warp">
      <div class="search-warp">
                  <el-row :gutter="20" >
                      <el-col :span="4">
                        <div class="grid-content bg-purple">
                           <el-date-picker
                           style="width:100%"
                           size="small"
                            v-model="searchObj.createTime"
                            type="date"
                            value-format="yyyy-MM-dd"
                            clearable
                            placeholder="选择创建日期">
                          </el-date-picker>
                        </div>
                      </el-col>
                      <el-col :span="4">
                        <div class="grid-content bg-purple">
                          <!--   <el-input
                            placeholder="补货人"
                            size="small"
                            v-model="searchObj.supplierName"
                            clearable>
                          </el-input> -->


                          <el-autocomplete
                            size="small"
                            style="width:100%"
                            v-model="userName"
                              :value="userName"
                              :fetch-suggestions="querySearchAsync1"
                              value-key="userName"
                              placeholder="搜索补货人员"
                              clearable
                              @focus="handeFocus1"
                              @select="handleSelect1"
                            ></el-autocomplete>

                        </div>
                      </el-col>
                      <el-col :span="4">
                        <div class="grid-content bg-purple">
                            <!-- <el-input
                            size="small"
                            placeholder="仓库名称"
                            v-model="searchObj.wmName"
                            clearable>
                          </el-input> -->
                           <el-autocomplete
                              size="small"
                              style="width:100%"
                              v-model="stockName"
                              :fetch-suggestions="querySearchAsync"
                               value-key="stockName"
                               :value="stockName"
                              placeholder="搜索仓库"
                              clearable
                              @focus="handeFocus"
                              @select="handleSelect"
                            ></el-autocomplete>

                        </div>
                      </el-col>
                      <el-col :span="4">
                        <div class="grid-content bg-purple">
                          <el-select clearable size="small" v-model="searchObj.stockState" placeholder="出库状态">
                            <el-option
                              v-for="item in options1"
                              :key="item.value"
                              :label="item.label"
                              :value="item.value">
                            </el-option>
                          </el-select>

                        </div>
                      </el-col>
                      
                      <el-col :span="4">
                        <div class="grid-content bg-purple">
                            <el-input
                            size="small"
                            placeholder="补货编号"
                            v-model="searchObj.sOrderId"
                            clearable>
                          </el-input>

                        </div>
                      </el-col>
                      <el-col :span="4">
                        <div class="grid-content bg-purple">
                            <el-input
                            size="small"
                            placeholder="线路编号"
                            v-model="searchObj.lineId"
                            clearable>
                          </el-input>

                        </div>
                      </el-col>
                      <el-col :span="2">
                          <div class="grid-content bg-purple"> 
                            <el-button type="success" size="small" @click.native.prevent="search" v-permission="['sys:stock:outbound:select']">查询</el-button>

                          </div>
                      </el-col>
                  </el-row>
      </div>
      <div class="block-warp" style="margin-top:20px;">
          <wTable :data="tableData" :header="tableHeader"  :option="tableOption">
            <el-table-column slot="fixed"
              fixed="right"
              label="操作"
              width="50">
                <template  scope="scope">
                  <div style="text-align:center;">
                    <el-button type="success" size="mini" icon="el-icon-view" circle  @click="handleSee(scope.$index, scope.row)" v-permission="['sys:stock:outbound:view']"></el-button>
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
import { getStockOutlist } from '@/api/stock'
import { getStockInfo,getVendingLine,getUserByName} from '@/api/dictionaries'
import NProgress from 'nprogress'
import wTable from '@/components/table/w-table.vue'
import permission from '@/directive/permission/index.js'
export default {
  directives: { permission },
  data() {
        return {
          stockName:"",
          userName:'',
          activeName: 'first',
          keyword:'',
          total:1,
          currentPage:1, 
          pagesize:10, 
          formLabelWidth: '120px',
          tableOption:{
            border: true
          },
          tableHeader:[
              {
                prop: 'sOrderId',
                label: '补货编号',
                width: '200',
                sortable: true,
                sortMethod: this.handleNameSort
              },
              {
                prop: 'lineId',
                label: '线路编号',
                 minWidth: '150'
             
              }, {
                prop: 'wmId',
                label: '仓库编号',
                minWidth: '80'
              }, {
                prop: 'wmName',
                label: '仓库名称',
                minWidth: '100'
              }, {
                prop: 'supplierName',
                label: '补货员名称',
              },
              {
                prop: 'stockState',
                label: '库存状态',
              },
              {
                prop: 'totalPrice',
                label: '出库总价格',
              },{
                prop: 'outNum',
                label: '出货数量',
              },{
                prop: 'createTime',
                label: '创建时间',
              }
          ],
          tableData: [],
          options: [{
          value: '1',
          label: '正常'
          }, {
            value: '2',
            label: '删除'
          }
          ],
          options1: [{
          value: '1',
          label: '未审核'
          }, {
            value: '2',
            label: '已出库'
          }
          ],
          searchObj:{
            supplierId:"",
            sOrderId:'',
            createTime:'',
            wmId:'',
            lineId:'',
            wmId:'',
            stockState:''
          },
          }
  },
  watch: {
    
  },
  methods: {
    handleClick(tab, event) {
        console.log(tab, event);
    },
    handleSizeChange: function (size) { 
       this.pagesize = size; 
    }, 
    handleCurrentChange: function(currentPage){ 
     this.currentPage = currentPage;
     //this.getSupplyWarnLineList(currentPage)
     this.getList(currentPage)
    },
    handleSee(index, row) {//跳转查看
      if(window.localStorage){
        localStorage.setItem("editData",JSON.stringify(row) );
        this.$router.push(
            {
                path:'/stock/stockOut/see',
                query: {
                  sorderId: row.sOrderId
                }
            }
        );
      }
         
    },
    search(){
      this.getList("1")
    },
    getList(page) {
      var _this = this;
      this.listLoading = true
      const listQuery = {
        sOrderId:_this.searchObj.sOrderId,
        createTime:_this.searchObj.createTime,
        supplierId:_this.searchObj.supplierId,
        wmId:_this.searchObj.wmId,
        lineId:_this.searchObj.lineId,
        wmName:_this.searchObj.wmName,
        stockState:_this.searchObj.stockState,
        pageSize:this.pagesize,
        pageNum:page,
        orderByColumn:'createtime',
        isAsc:'desc'
      }
      getStockOutlist(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
            NProgress.done();
          _this.total = response.zbody.datas.total;  
          _this.tableData = response.zbody.datas.rows;
          console.log("llll",_this.tableData)
         }else{
             this.$notify({
              title: '失败',
              message: res.zhead.retMsg,
              type: 'error'
            });
         }
      })
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
    //补货仓库
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
  
  },
  mounted () {
    this.getList("1")
  },
  components: {
    wTable
  }
}
</script>
<style scoped>
  .div-warp{padding:0 20px;}
  .pageBox{background: #fff; padding:20px 0;}

</style>