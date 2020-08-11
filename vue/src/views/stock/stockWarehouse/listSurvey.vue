<template>
 <div class="div-warp">
      <div class="search-warp">
        <el-row :gutter="20" >
            <el-col :span="4">
              <div class="grid-content bg-purple">
                  <el-input
                    placeholder="请输入仓库名称"
                    size="small"
                    v-model="keyword"
                    clearable>
                  </el-input>
              </div>
            </el-col>
            <el-col :span="4">
              <div class="grid-content bg-purple">
                  <el-input
                  size="small"
                    placeholder="请输入仓库编号"
                    v-model="stockId"
                    clearable>
                  </el-input>

              </div>
            </el-col>
            <el-col :span="4">
              <div class="grid-content bg-purple">
                  <el-input
                  size="small"
                    placeholder="请输入联系人"
                    v-model="managerName"
                    clearable>
                  </el-input>

              </div>
            </el-col>
            <el-col :span="4">
                <div class="grid-content bg-purple"> 
                  <el-button type="success" size="small" @click.native.prevent="search" v-permission="['sys:stock:warehouse:select']">查询</el-button>
                </div>
            </el-col>
            <el-col :span="4"><div class="grid-content bg-purple"></div></el-col>
        </el-row>
      </div>
      <div class="block-warp">
      <wTable :data="tableData" :header="tableHeader"  :option="tableOption">
          <el-table-column slot="fixed"
          fixed="right"
          label="操作"
          width="150">
            <template  scope="scope">
              <div style="text-align:center;">
                <el-button type="primary" size="mini"  @click="handleEdit(scope.$index, scope.row)" v-permission="['sys:stock:warehouse:edit']">操作</el-button>
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
import { getStockWarehouseListSurvey,stockInfoList } from '@/api/stock'
import NProgress from 'nprogress'
import wTable from '@/components/table/w-table.vue'
import permission from '@/directive/permission/index.js'
export default {
  directives: { permission },
  data() {
        return {
          dialogVisible:false,
          keyword:'',
          stockId:"",
          managerName:'',
          total:1,
          currentPage:1, 
          pagesize:10, 
          formLabelWidth: '120px',
          input10:'',
          multipleSelection:"",
          tableOption:{
            border: true
          },
          tableHeader: [
              {
                prop: 'stockId',
                label: '仓库编号',
                width: '200',
                sortable: true,
                sortMethod: this.handleNameSort
              },
              {
                prop: 'stockName',
                label: '仓库名称',
                 minWidth: '150'
             
              }, {
                prop: 'managerName',
                label: '联系人',
                minWidth: '80'
              }, {
                prop: 'createTime',
                label: '创建时间 ',
                minWidth: '100'
              }
          ],
          tableData: [],
          filterText: ''
        }
  },
  watch: {
    filterText(val) {
      this.$refs.tree2.filter(val)
    }
  },
  methods: {
    handleSizeChange: function (size) { 
       this.pagesize = size; 
    }, 
    handleCurrentChange: function(currentPage){ 
     this.currentPage = currentPage;
     this.getData(currentPage)
     },
    search(){
      this.getData("1")
      console.log('---搜索----')
    },
    handleEdit(index, row) {//显示编辑窗口{
          this.$router.push(
              {
                path:'/stock/stockWarehouse/list',
                query: {
                  stockId: row.stockId
                }
            }
          );
    },
    handleNameSort () {
      console.log('handleNameSort')
    },
    getData(page) {
      var _this = this;
      const listQuery = {
        stockName:_this.keyword,
        stockId:_this.stockId,
        managerName:_this.managerName,
        pageSize:_this.pagesize,
        pageNum:page,
        orderByColumn:'createtime',
        isAsc:'desc'//desc
      }
      stockInfoList(listQuery).then(response => {
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
    }
  },

  mounted () {
    this.getData("1")
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