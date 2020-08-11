<template>
 <div class="div-warp">

      <div class="block-warp" style="margin-top:20px;">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="采购审核" name="first">
          <div class="search-warp">
            <el-row :gutter="20" >
                <el-col :span="4">
                  <div class="grid-content bg-purple">
                     <el-date-picker
                     style="width:100%"
                     size="small"
                     clearable
                      v-model="searchObj.createTime"
                      type="date"
                      value-format="yyyy-MM-dd"
                      placeholder="选择创建日期">
                    </el-date-picker>
                  </div>
                </el-col>
                 <el-col :span="4">
                  <div class="grid-content bg-purple">
                      <el-input
                      size="small"
                      placeholder="申请人"
                      v-model="searchObj.supplyName"
                      clearable>
                    </el-input>

                  </div>
                </el-col>
                <el-col :span="4">
                  <div class="grid-content bg-purple">
                      <el-input
                      size="small"
                      placeholder="仓库名称"
                      v-model="searchObj.stokcName"
                      clearable>
                    </el-input>

                  </div>
                </el-col>
                 <el-col :span="4">
                  <div class="grid-content bg-purple">
                      <el-input
                      size="small"
                      placeholder="采购单号"
                      v-model="searchObj.wpurchaseId"
                      clearable>
                    </el-input>

                  </div>
                </el-col>
                 <!-- <el-col :span="4">
                  <div class="grid-content bg-purple">
                    <el-select clearable size="small" v-model="searchObj.checkState" placeholder="请选择审核状态">
                      <el-option
                        key="1"
                        label="未审核"
                        value="1">
                      </el-option>
                      <el-option
                        key="2"
                        label="审核通过"
                        value="2">
                      </el-option>
                      <el-option
                        key="3"
                        label="审核失败"
                        value="3">
                      </el-option>
                    </el-select>

                  </div>
                </el-col> -->
                <el-col :span="2">
                    <div class="grid-content bg-purple"> 
                      <el-button type="success" size="small" @click.native.prevent="search" v-permission="['sys:stock:purchase:select']" >查询</el-button>

                    </div>
                </el-col>
            </el-row>
          </div>
          <wTable :data="tableData" :header="tableHeader"  :option="tableOption">
            <el-table-column slot="fixed"
              fixed="right"
              label="操作"
              width="150">
                <template  scope="scope">
                  <div style="text-align:center;">
                    <el-button type="primary" size="mini"  @click="handleEdit(scope.$index, scope.row)" v-permission="['sys:stock:purchase:edit']" :disabled="scope.row.checkState!='1'">审核</el-button>
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
        </el-tab-pane>
        <el-tab-pane label="采购记录" name="second">
            <div class="search-warp">
                  <el-row :gutter="20" >
                      <el-col :span="4">
                        <div class="grid-content bg-purple">
                           <el-date-picker
                           style="width:100%"
                            v-model="searchObj1.createTime"
                            type="date"
                            size="small"
                            value-format="yyyy-MM-dd"
                            clearable
                            placeholder="选择创建日期">
                          </el-date-picker>
                        </div>
                      </el-col>
                       <el-col :span="4">
                        <div class="grid-content bg-purple">
                            <el-input
                            placeholder="申请人"
                            size="small"
                            
                            v-model="searchObj1.supplyName"
                            clearable>
                          </el-input>

                        </div>
                      </el-col>
                      <el-col :span="4">
                        <div class="grid-content bg-purple">
                            <el-input
                            
                            size="small"
                            placeholder="仓库名称"
                            v-model="searchObj1.stokcName"
                            clearable>
                          </el-input>

                        </div>
                      </el-col>
                      <el-col :span="4">
                        <div class="grid-content bg-purple">
                          <el-select clearable size="small" style="Input-100" v-model="searchObj1.buyState" placeholder="请选择采购状态">
                            <el-option
                              v-for="item in options"
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
                          placeholder="采购单号"
                          v-model="searchObj1.wpurchaseId"
                          clearable>
                        </el-input>

                        </div>
                      </el-col>
                      <el-col :span="4">
                        <div class="grid-content bg-purple search-div-margin">
                          <el-select clearable size="small" v-model="searchObj1.checkState" placeholder="请选择审核状态">
                            <el-option
                              key="1"
                              label="未审核"
                              value="1">
                            </el-option>
                            <el-option
                              key="2"
                              label="审核通过"
                              value="2">
                            </el-option>
                            <el-option
                              key="3"
                              label="审核失败"
                              value="3">
                            </el-option>
                          </el-select>

                        </div>
                      </el-col>
                       <el-col :span="4">
                        <div class="grid-content bg-purple">
                          <el-select clearable size="small" v-model="searchObj1.stockState" placeholder="请选择入库状态">
                            <el-option
                              v-for="item in options1"
                              :key="item.value"
                              :label="item.label"
                              :value="item.value">
                            </el-option>
                          </el-select>

                        </div>
                      </el-col>
                      <el-col :span="16">
                          <div class="grid-content bg-purple"> 
                            <el-button type="success" size="small" @click.native.prevent="search1" v-permission="['sys:stock:purchase:select']">查询</el-button>
                             <el-button type="primary" size="small" @click.native.prevent="exportExcelPost" v-permission="['sys:stock:purchase:edit']">导出</el-button>

                          </div>
                      </el-col>
                      
                  </el-row>
          </div>

          <wTable :data="RecordtableData" :header="RecordtableHeader"  :option="tableOption">
            <el-table-column slot="fixed"
              fixed="right"
              label="操作"
              width="150">
                <template  scope="scope">
                  <div style="text-align:center;">
                    <el-button type="primary" size="mini"  @click="handleSee(scope.$index, scope.row)" v-permission="['sys:stock:purchase:view']">查看</el-button>
                  </div>
                </template>
            </el-table-column>
          </wTable>

          <div class="pageBox">
          <el-pagination
            @size-change="handleSizeChange1"
            @current-change="handleCurrentChange1"
            background
            :page-size="pagesize1"
            layout="prev, pager, next"
            :total="total1">
          </el-pagination>
        </div>
        </el-tab-pane>
      </el-tabs>
  
        
     
    </div>
  </div>
</template>
<script>
import { getStockPurchaseList,getStockInboundList,stockPurchaseExportExcel } from '@/api/stock'
import NProgress from 'nprogress'
import wTable from '@/components/table/w-table.vue'
import permission from '@/directive/permission/index.js'
export default {
  directives: { permission },
  data() {
        return {
          activeName: 'first',
          keyword:'',
          total:1,
          currentPage:1, 
          pagesize:10, 
          total1:1,
          currentPage1:1, 
          pagesize1:10, 
          formLabelWidth: '120px',
          tableOption:{
            border: true
          },
          RecordtableHeader:[
              {
                prop: 'wpurchaseId',
                label: '采购单号',
                width: '200',
                sortable: true,
                sortMethod: this.handleNameSort
              },
              {
                prop: 'stockId',
                label: '仓库编号',
                 minWidth: '150'
             
              }, {
                prop: 'stokcName',
                label: '仓库名称',
                minWidth: '80'
              }, {
                prop: 'tnum',
                label: '采购商品种类数',
                minWidth: '100'
              }, {
                prop: 'pnum',
                label: '总采购数量',
              },
              {
                prop: 'totalPrice',
                label: '总采购价',
              },
              {
                prop: 'supplyName',
                label: '申请人',
              },{
                prop: 'curStateName',
                label: '采购状态',
              }, {
                prop: 'checkStateName',
                label: '审核状态',
              }, {
                prop: 'stockStateName',
                label: '库存状态',
              }, {
                prop: 'checkTime',
                label: '审核时间',
              }
          ],
          RecordtableData:[],
          tableHeader: [
              {
                prop: 'wpurchaseId',
                label: '采购单号',
                width: '200',
                sortable: true,
                sortMethod: this.handleNameSort
              },
              {
                prop: 'stockId',
                label: '仓库编号',
                 minWidth: '150'
             
              }, {
                prop: 'stokcName',
                label: '仓库名称',
                minWidth: '80'
              }, {
                prop: 'tnum',
                label: '采购商品种类数',
                minWidth: '100'
              }, {
                prop: 'pnum',
                label: '总采购数量',
              }, {
                prop: 'totalPrice',
                label: '总采购价',
              }, {
                prop: 'supplyName',
                label: '申请人',
              }, {
                prop: 'checkStateName',
                label: '审核状态',
              }, {
                prop: 'checkTime',
                label: '审核时间',
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
          label: '未入库'
          }, {
            value: '2',
            label: '已入库'
          }
          ],
          searchObj:{
            stokcName:'',
            createTime:'',
            supplyName:'',
            wpurchaseId:'',
            checkState:''

          },
          searchObj1:{
            buyState:"",
            checkState:"",
            stockState:"",
            stokcName:'',
            createTime:'',
            supplyName:'',
            wpurchaseId:'',
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
     this.getList(currentPage)
    },

    handleSizeChange1: function (size) { //记录
       this.pagesize1 = size; 
    }, 
    handleCurrentChange1: function(currentPage){ //记录
     this.currentPage1 = currentPage;
     this.getRecordList(currentPage)
    },
    handleEdit(index, row) {//跳转审核
      if(window.localStorage){
        localStorage.setItem("editData",JSON.stringify(row) );
        this.$router.push(
            {
                path:'/stock/stockPurchase/toExamine',
                query: {
                  wpurchaseId: row.wpurchaseId
                }
            }
        );
      }
         
    },
    handleSee(index, row) {//跳转查看
      if(window.localStorage){
        localStorage.setItem("editData",JSON.stringify(row) );
        this.$router.push(
            {
                path:'/stock/stockPurchase/see',
                query: {
                  wpurchaseId: row.wpurchaseId
                }
            }
        );
      }
         
    },
    search(){
      this.getList("1")
    },
    search1(){
      this.getRecordList("1")
    },
    getList(page) {
      var _this = this;
      this.listLoading = true
      const listQuery = {
        stokcName:_this.searchObj.stokcName,
        createTime:_this.searchObj.createTime,
        supplyName:_this.searchObj.supplyName,
        wpurchaseId:_this.searchObj.wpurchaseId,
        checkState:'1',
        pageSize:_this.pagesize,
        pageNum:page,
        orderByColumn:'createtime',
        isAsc:'desc'//desc
      }
      getStockPurchaseList(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
            NProgress.done();
          _this.total = response.zbody.datas.total;  
          _this.tableData = response.zbody.datas.rows;
          console.log(_this.tableData)
         }else{
             this.$notify({
              title: '失败',
              message: res.zhead.retMsg,
              type: 'error'
            });
         }
      })
    },
    getRecordList(page) {
      var _this = this;
      this.listLoading = true
      const listQuery = {
        stokcName:_this.searchObj1.stokcName,
        createTime:_this.searchObj1.createTime,
        supplyName:_this.searchObj1.supplyName,
        wpurchaseId:_this.searchObj1.wpurchaseId,
        stockState:_this.searchObj1.stockState,
        checkState:_this.searchObj1.checkState,
        buyState:_this.searchObj1.buyState,
        pageSize:_this.pagesize1,
        pageNum:page,
        orderByColumn:'createtime',
        isAsc:'desc'//desc
      }
      getStockPurchaseList(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
            NProgress.done();
          _this.total1 = response.zbody.datas.total;  
          _this.RecordtableData = response.zbody.datas.rows;
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
             FileSaver.saveAs(new Blob([wbout], { type: 'application/octet-stream' }), '仓库库存.xlsx')
         } catch (e) { if (typeof console !== 'undefined') console.log(e, wbout) }
         return wbout
    },
    exportExcelPost(){
          NProgress.start();
          var para = {
            stokcName:this.searchObj1.stokcName,
            createTime:this.searchObj1.createTime,
            supplyName:this.searchObj1.supplyName,
            wpurchaseId:this.searchObj1.wpurchaseId,
            stockState:this.searchObj1.stockState
          }
          stockPurchaseExportExcel(para).then((res) => {
                if(res.zhead.reTCode=="0000"){
                  window.location.href=res.zhead.retMsg
                NProgress.done();
                }else{
                  this.$notify({
                    title: '失败',
                    message: res.zhead.retMsg,
                    type: 'error'
                  });
              }
          });
    },
  },

  mounted () {
    this.getList("1")
    this.getRecordList("1")
  },
  components: {
    wTable
  }
}
</script>
<style scoped>
  .div-warp{padding:0 20px;}
  .pageBox{background: #fff; padding:20px 0;}
  .search-div-margin{margin: 5px}
</style>