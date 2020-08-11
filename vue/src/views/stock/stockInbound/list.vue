<template>
 <div class="div-warp">
      <div class="search-warp">
                  <el-row :gutter="10" >
                      <el-col :span="4">
                         <el-input
                            size="small"
                            placeholder="入库人姓名"
                            v-model="searchObj.inboundName"
                            clearable>
                          </el-input>
                      </el-col>
                      <el-col :span="4">
                        <div class="grid-content bg-purple">
                           <el-date-picker
                           size="small"
                           style="width:100%"
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
                      <el-col :span="4">
                        <div class="grid-content bg-purple">
                            <el-input
                            size="small"
                            placeholder="入库单号"
                            v-model="searchObj.wInboundId"
                            clearable>
                          </el-input>
                        </div>
                      </el-col>
                       <el-col :span="4">
                        <div class="grid-content bg-purple">
                          <el-select clearable size="small" v-model="searchObj.curState" placeholder="请选择入库状态" class="Input-100">
                            <el-option
                              key="1"
                              label="未入库"
                              value="1">
                            </el-option>
                            <el-option
                              key="2"
                              label="已入库"
                              value="2">
                            </el-option>
                          </el-select>

                        </div>
                      </el-col>
                      <el-col :span="2">
                          <div class="grid-content bg-purple"> 
                            <el-button type="success" size="small" @click.native.prevent="search" v-permission="['sys:stock:inbound:select']">查询</el-button>

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
                     <el-button type="success" size="mini" icon="el-icon-view" circle  @click="handleSee(scope.$index, scope.row)" v-permission="['sys:stock:inbound:view']"></el-button>
                    
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
import { getStockInlist } from '@/api/stock'
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
          formLabelWidth: '120px',
          tableOption:{
            border: true
          },
          tableHeader:[
              {
                prop: 'winboundId',
                label: '入库单号',
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
                prop: 'pnum',
                label: '总入库数量',
                minWidth: '100'
              }, {
                prop: 'nopNum',
                label: '未入库数量',
              },
              {
                prop: 'tnum',
                label: '入库商品种类数',
              },
              {
                prop: 'totalPrice',
                label: '入库采购总价',
              },{
                prop: 'inboundName',
                label: '入库人',
              }, {
                prop: 'curStateName',
                label: '入库状态',
              },{
                prop: 'inboundTypeName',
                label: '入库类型',
              }, {
                prop: 'createTime',
                label: '创建时间',
              }
          ],
          tableData: [],
          searchObj:{
            inboundName:'',
            createTime:'',
            stokcName:'',
            wpurchaseId:"",
            wInboundId:'',
            curState:''

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
        this.$router.push(
            {
                path:'/stock/stockInbound/see',
                query: {
                  wpurchaseId: row.wpurchaseId,
                  winboundId:row.winboundId
                }
            }
        );
         
    },
    search(){
      this.getList("1")
    },
    getList(page) {
      var _this = this;
      this.listLoading = true
      const listQuery = {
        stokcName:_this.searchObj.stokcName,
        createTime:_this.searchObj.createTime,
        wInboundId:_this.searchObj.wInboundId,
        wpurchaseId:_this.searchObj.wpurchaseId,
        curState:_this.searchObj.curState,
        inboundName:_this.searchObj.inboundName,
        pageSize:this.pagesize,
        pageNum:page,
        orderByColumn:'createtime',
        isAsc:'desc'//desc
      }
      getStockInlist(listQuery).then(response => {
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