<template>
 <div class="div-warp">
      <div class="search-warp">
        <el-row :gutter="20" >
            <el-col :span="4">
              <el-input
                  placeholder="请输入仓库名称"
                  size="small"
                  v-model="stockName"
                  clearable>
                </el-input>
            </el-col>
            <!-- <el-col :span="4">
                  <el-input
                  placeholder="请输入区域名称"
                  v-model="keyword"
                  clearable>
                </el-input>
            </el-col> -->
            <el-col :span="16">
                <div class="grid-content bg-purple"> 
                  <el-button type="success" size="small" @click.native.prevent="search" v-permission="['sys:stock:info:select']">查询</el-button>
                  <el-button type="primary" size="small" @click="handleAdd" v-permission="['sys:stock:info:add']">新增</el-button>
                </div>
            </el-col>
        </el-row>
      </div>
      <div class="block-warp">
      <wTable :data="tableData" :header="tableHeader"  :option="tableOption">
       <el-table-column slot="fixed"
          fixed="right"
          label="操作"
          width="120">
            <template  scope="scope">
              <div style="text-align:center;">
                <el-button type="primary" size="mini"icon="el-icon-edit" circle @click="handleEdit(scope.$index, scope.row)" v-permission="['sys:stock:info:edit']"></el-button>
                <el-button type="danger"  size="mini" icon="el-icon-delete" circle @click.native.prevent="deleteRow(scope.row)" v-permission="['sys:stock:info:del']"></el-button>
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
import { getStockInfoList,removeStockInfo } from '@/api/stock'
import NProgress from 'nprogress'
import wTable from '@/components/table/w-table.vue'
import permission from '@/directive/permission/index.js'
export default {
  directives: { permission },
  data() {
        return {
          multipleSelection:[],
          stockName:"",
          keyword:'',
          total:1,
          currentPage:1, 
          pagesize:10, 
          formLabelWidth: '120px',
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
                label: '仓库联系人',
                minWidth: '80'
              }, {
                prop: 'mobile',
                label: '联系人电话',
                minWidth: '100'
              }, {
                prop: 'districtName',
                label: '区域名称',
              }, {
                prop: 'createTime',
                label: '创建时间',
                minWidth: '100'
              }
          ],
          tableData: [],
        }
  },
  watch: {
    
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
    },
    handdetails(){

    },
    deleteRow(row) {//删除数据
      debugger
      const _this  = this;
      this.$confirm('确认提交吗？', '提示', {}).then(() => {
                  let para ={
                    ids:_this.multipleSelection =="" ? row.logid : _this.multipleSelection
                  }
                  removeStockInfo(para).then((res) => {
                        if(res.zhead.reTCode=="0000"){
                        NProgress.done();
                        this.$notify({
                          title: '成功',
                          message: res.zhead.retMsg,
                          type: 'success'
                        });
                        _this.getData("1")
                        }else{
                          this.$notify({
                            title: '失败',
                            message: res.zhead.retMsg,
                            type: 'error'
                          });
                  }
              });

          

      });
    },
    handleEdit(index,row) {//显示新增窗口
      localStorage.setItem("editData",JSON.stringify(row) );
      this.$router.push(
          {path:'/stock/stockInfo/edit'}
      );
   
    },
    handleAdd(index,row) {//显示新增窗口
      this.$router.push(
          {path:'/stock/stockInfo/add'}
      );
   
    },
    getData(page) {
      var _this = this;
      this.listLoading = true
      const listQuery = {
        stockName:_this.stockName,
        pageSize:_this.pagesize,
        pageNum:page,
        orderByColumn:'createtime',
        isAsc:'desc'//desc
      }
      getStockInfoList(listQuery).then(response => {
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
    this.getData('1')
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