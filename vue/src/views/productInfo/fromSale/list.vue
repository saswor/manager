<template>
 <div class="div-warp">
      <div class="search-warp">
        <el-row :gutter="20" >
            <el-col :span="4">
                <el-input
                  placeholder="商品名称"
                  size="small"
                  v-model="searchObj.productName"
                  clearable>
                </el-input>
            </el-col>
         <el-col :span="4">
                <el-input
                  placeholder="商品编号"
                  size="small"
                  v-model="searchObj.productId"
                  clearable>
                </el-input>
            </el-col>
            <el-col :span="4">
                <el-input
                  placeholder="售卖机编码"
                  size="small"
                  v-model="searchObj.siteCode"
                  clearable>
                </el-input>
            </el-col>
            <el-col :span="4">
                          <el-date-picker
                           style="width:100%"
                            v-model="searchObj.beginTime"
                            type="date"
                            size="small"
                            value-format="yyyy-MM-dd"
                            clearable
                            placeholder="下架开始时间">
                          </el-date-picker>
                    
                      </el-col>
                     <el-col :span="4">
                          <el-date-picker
                          clearable
                           style="width:100%"
                            v-model="searchObj.endTime"
                            type="date"
                            size="small"
                            value-format="yyyy-MM-dd"
                            placeholder="下架结束时间">
                          </el-date-picker>
                     </el-col>
            <el-col :span="4">
                <div class="grid-content bg-purple"> 
                  <el-button type="success" size="small" @click.native.prevent="search" v-permission="['sys:product:off:select']">查询</el-button>
                  <el-button type="success" size="small" @click.native.prevent="exportProductUnder" v-permission="['sys:product:off:edit']">导出</el-button>
                </div>
            </el-col>
        </el-row>
      </div>
      <div class="block-warp">
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
import { productLunder,exportProductUnderExcel } from '@/api/productInfo'
import NProgress from 'nprogress'
import wTable from '@/components/table/w-table.vue'
import permission from '@/directive/permission/index.js'
export default {
  directives: { permission },
  data() {
        return {
          options: [],
          value:"",
          keyword:'',
          total:1,
          currentPage:1, 
          pagesize:10, 
          searchObj:{
              productId:"",
              productName:"",
              siteCode:'',
              beginTime:'',
              endTime:''
          },
          formLabelWidth: '120px',
          tableOption:{
            border: true
          },
          tableHeader: [
              {
                prop: 'productName',
                label: '商品名称',
                 minWidth: '150'
             
              },{
                prop: 'productCode',
                label: '商品编码',
                width: '100',
                sortable: true,
                sortMethod: this.handleNameSort
              }, {
                prop: 'siteName',
                label: '售卖机名称',
                minWidth: '120'
              }, {
                prop: 'siteCode',
                label: '售卖机编码',
                minWidth: '100'
              }, {
                prop: 'lineCode',
                label: '线路编码',
              }, {
                prop: 'districtCode',
                label: '区域编码',
              }, {
                prop: 'underNum',
                label: '下架数量',
              }, {
                prop: 'laneSId',
                label: '货道开始号',
              }, {
                prop: 'laneEId',
                label: '货道结束号',
              }, {
                prop: 'createTime',
                label: '下架时间',
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
    handleAdd() {//跳转新增
        this.$router.push(
          {path:'/productInfo/add'}
        );
    },
    handleEdit(index, row){//跳转编辑
      if(window.localStorage){
        localStorage.setItem("editData",JSON.stringify(row) );
        this.$router.push(
          {path:'/productInfo/edit'}
        );
      }
      
    },
    search(){
      this.getData("1")
    },
    exportProductUnder(){
      var _this = this;
      this.listLoading = true
      const listQuery = {
        productId:_this.searchObj.productId,
        productName:_this.searchObj.productName,
        siteCode:_this.searchObj.siteCode,
        beginTime:_this.searchObj.beginTime,
        endTime:_this.searchObj.endTime,
      }
      exportProductUnderExcel(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
            NProgress.done();
          window.location.href=response.zhead.retMsg;
         }else{
             this.$notify({
              title: '失败',
              message: response.zhead.retMsg,
              type: 'error'
            });
         }
      })
    },
    getData(page) {
      var _this = this;
      this.listLoading = true
      const listQuery = {
        productId:_this.searchObj.productId,
        productName:_this.searchObj.productName,
        siteCode:_this.searchObj.siteCode,
        beginTime:_this.searchObj.beginTime,
        endTime:_this.searchObj.endTime,
        pageSize:_this.pagesize,
        pageNum:page,
        orderByColumn:'createtime',
        isAsc:'desc'//desc
      }
      productLunder(listQuery).then(response => {
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