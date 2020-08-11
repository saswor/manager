<template>
 <div class="div-warp">

      <el-dialog
        title="生成采购单"
        :visible.sync="dialogVisible"
        width="1000px"
        >
        <el-table
            :data="StockData"
            style="width: 100%">
            <el-table-column
              prop="productName"
              label="商品名称"
              width="160">
            </el-table-column>
            <el-table-column
              prop="curNum"
              label="当前库存数量"
              width="160">
            </el-table-column>
            <!-- <el-table-column
              prop="buyPrice"
              label="采购单价"
              align="center">
              <template  scope="scope">
              <div style="text-align:center;">
                <el-input-number :min="0" size="mini" clearable @change="Calculation(scope.row)" style="text-align:center" v-model="scope.row.buyPrice" ></el-input-number>
              </div>
            </template>
            </el-table-column> -->
            <el-table-column
              prop="pNum"
              label="采购数量"
              align="center">
              <template  scope="scope">
              <div style="text-align:center;">
                <el-input-number :min="0" size="mini" clearable @change="Calculation(scope.row)" @keyup.native="checkInt(scope.row)" style="text-align:center" v-model="scope.row.pNum"></el-input-number>
              </div>
            </template>
            </el-table-column>
            <!-- <el-table-column
              prop="totalPrice"
              label="采购总价"
              width="160">
            </el-table-column> -->
            <el-table-column
              label="操作">
              <template  scope="scope">
              <div style="text-align:left;">
                <el-button type="danger"  size="mini" icon="el-icon-delete" circle @click.native.prevent="deleteRow(scope.$index,StockData)"></el-button>
              </div>
            </template>
            </el-table-column>
          </el-table>

    <!-- subStockPurchase -->
         <div slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible=false">取 消</el-button>
          <el-button type="primary" @click="subStockPurchase" :disabled="disableFlag">提交</el-button>
        </div>

      </el-dialog>

      
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
                <div class="grid-content bg-purple">
                  <el-button type="success" size="small" @click.native.prevent="search">查询</el-button>
                  <el-button type="primary" size="small" @click.native.prevent="AllByStock">生成采购单</el-button>
                  <el-button type="primary" size="small" @click.native.prevent="exportExcel">导出</el-button>
                </div>
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
import { getStockWarehouseList,getAllByStockIdList,addStockPurchase,exportStockWarehouse } from '@/api/stock'
import NProgress from 'nprogress'
import wTable from '@/components/table/w-table.vue'
export default {
  data() {
        return {
          dialogVisible:false,
          disableFlag:false,
          keyword:'',
          total:1,
          currentPage:1,
          pagesize:10,
          formLabelWidth: '120px',
          input10:'',
          multipleSelection:"",
          tableOption:{
            border: true
          },
          StockData:[],//采购列表
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
                label: '历史库存数量',
                minWidth: '80'
              }, {
                prop: 'curNum',
                label: '当前库存数量',
                minWidth: '80'
              }, {
                prop: 'overNum',
                label: '过期数量',
                minWidth: '80'
              }, {
                prop: 'warnNum',
                label: '警戒值',
                minWidth: '80'
              }, {
                prop: 'createTime',
                label: '创建时间 ',
                minWidth: '100'
              }
          ],
          tableData: [],
          filterText: '',
          editForm:{}
        }
  },
  watch: {
    filterText(val) {
      this.$refs.tree2.filter(val)
    }
  },
  methods: {
    exportExcel(){
      NProgress.start();
      var para = {
        productName:_this.keyword,
        stockId:this.$route.query.stockId,
      }
      exportStockWarehouse(para).then((res) => {
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
    handleSizeChange: function (size) {
       this.pagesize = size;
    },
    checkInt: function(row){
      row.pNum=row.pNum.replace(/[^\.\d]/g,'');
      row.pNum=row.pNum.replace('.','');

     },
    handleCurrentChange: function(currentPage){
     this.currentPage = currentPage;
     this.getData(currentPage)
     },
    search(){
      this.getData("1")
    },
    AllByStock() {//显示生成采购单
     var _this = this;
      this.listLoading = true
      const listQuery = {
        stockId:this.$route.query.stockId
      }
      getAllByStockIdList(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
            NProgress.done();
            this.dialogVisible = true
            this.StockData = response.zbody.datas.rows

         }else{
             this.$notify({
              title: '失败',
              message: res.zhead.retMsg,
              type: 'error'
            });
         }
      })
    },
    Calculation(row){
      row.totalPrice =  (parseFloat(row.buyPrice) * parseFloat(row.pNum)).toFixed(2)

    },
    deleteRow(index,rows){
       this.$confirm('确认删除吗？', '提示', {}).then(() => {
          rows.splice(index, 1);
       })
    },
    subStockPurchase(){
          NProgress.start();
          //let para = Object.assign({}, this.addForm);
          var _this=this
          for(var i=0;i<_this.StockData.length;i++){
            var buyPrice_temp=_this.StockData[i].buyPrice
            // if(buyPrice_temp==null||buyPrice_temp==undefined){
            //   this.$notify({
            //       title: '警告',
            //       message: '采购单价不能为空',
            //       type: 'error'      
            //     });
            //     return;
            // }
            
            var pNum_temp=_this.StockData[i].pNum;
            if(pNum_temp==null||pNum_temp==undefined){
              this.$notify({
                  title: '警告',
                  message: '采购数量不能为空',
                  type: 'error'      
                });
                return;
            }
            if(!Number.isInteger(pNum_temp)){
              this.$notify({
                  title: '警告',
                  message: '采购数量必须是整数',
                  type: 'error'      
                });
                return;
            }
          }
          let para=this.StockData;
          if(this.disableFlag){
            return;
          }
          this.disableFlag=true;
          addStockPurchase(para).then((res) => {
                if(res.zhead.reTCode=="0000"){
                NProgress.done();
                this.$notify({
                  title: '成功',
                  message: res.zhead.retMsg,
                  type: 'success'      
                });
                this.dialogVisible=false
                this.disableFlag=false;
                
                }else{
                  this.$notify({
                    title: '失败',
                    message: res.zhead.retMsg,
                    type: 'error'
                  });
                  this.disableFlag=false;
              }
          });
          
    },
    getData(page) {
      var _this = this;
      this.listLoading = true
      const listQuery = {
        productName:_this.keyword,
        stockId:this.$route.query.stockId,
        pageSize:_this.pagesize,
        pageNum:page,
        orderByColumn:'createtime',
        isAsc:'desc'//desc
      }
      getStockWarehouseList(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
            NProgress.done();
          _this.total = response.zbody.datas.total;
          _this.tableData = response.zbody.datas.rows;
          console.log('库存操作：：：',_this.tableData)
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
            productName:this.keyword,
            stockId:this.$route.query.stockId,
            orderByColumn:'createtime',
            isAsc:'desc'
          }
          warehouseExportExcel(para).then((res) => {
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
