<template>
 <div class="div-warp">
      <div class="search-warp">
        <el-row :gutter="20" >
            <el-col :span="4">
              <div class="grid-content bg-purple">
                  <el-input
                  size="small"
                  placeholder="请输入商品名称"
                  v-model="keyword"
                  clearable>
                </el-input>

              </div>
            </el-col>
            <el-col :span="16">
                  <el-button type="success" size="small" @click.native.prevent="search" v-permission="['sys:stock:product:select']">查询</el-button>
                  <el-button type="primary" size="small" @click.native.prevent="exportExcelPost">导出</el-button>
            </el-col>
            <el-col :span="4"><div class="grid-content bg-purple"></div></el-col>
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
import { getList,supplExportExcel } from '@/api/stock'
import NProgress from 'nprogress'
import wTable from '@/components/table/w-table.vue'
import FileSaver from 'file-saver'
import permission from '@/directive/permission/index.js'
import XLSX from 'xlsx'
export default {
  directives: { permission },
  data() {
        return {
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
                prop: 'productId',
                label: '商品编号',
                width: '200',
                sortable: true,
                sortMethod: this.handleNameSort
              },
              {
                prop: 'productName',
                label: '商品名称',
                 minWidth: '150'
             
              }, {
                prop: 'totalNum',
                label: '历史库存总数量',
                minWidth: '80'
              }, {
                prop: 'curNum',
                label: '当前库存总数量',
                minWidth: '100'
              }, {
                prop: 'overNum',
                label: '过期总数量',
              }, {
                prop: 'createTime',
                label: '创建时间 ',
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
      this.getData("")
    },
    getData(page) {
      var _this = this;
      this.listLoading = true
      const listQuery = {
        productName:_this.keyword,
        pageSize:_this.pagesize,
        pageNum:page,
        orderByColumn:'createtime',
        isAsc:'desc'//desc
      }
      getList(listQuery).then(response => {
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
    exportExcel () {
         /* generate workbook object from table */
         var wb = XLSX.utils.table_to_book(document.querySelector('.block-warp'))
         /* get binary string as output */
         var wbout = XLSX.write(wb, { bookType: 'xlsx', bookSST: true, type: 'array' })
         try {
             FileSaver.saveAs(new Blob([wbout], { type: 'application/octet-stream' }), '系统库存.xlsx')
         } catch (e) { if (typeof console !== 'undefined') console.log(e, wbout) }
         return wbout
    },
    exportExcelPost(){
          NProgress.start();
          var para = {
            productName:this.keyword,
          }
          supplExportExcel(para).then((res) => {
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
    }
  },

  mounted () {
    this.getData(1)
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