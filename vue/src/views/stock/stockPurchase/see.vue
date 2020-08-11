<template>
 <div class="div-warp">

    <div style="margin-top:20px;">
       
    <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>详情</span>
          </div>
          <el-form :model="addForm"  :inline="true">
              <el-form-item label="采购单号" prop="productCode" size="small" :label-width="formLabelWidth">
                <el-input v-model="addForm.wpurchaseId" :disabled="true" class="inputStyle" ></el-input>
              </el-form-item>

              <el-form-item label="仓库编号" prop="productCode" size="small" :label-width="formLabelWidth">
                <el-input v-model="addForm.stockId" :disabled="true" class="inputStyle" ></el-input>
              </el-form-item>

              <el-form-item label="仓库名称" prop="productCode" size="small" :label-width="formLabelWidth">
                <el-input v-model="addForm.stokcName" :disabled="true" class="inputStyle" ></el-input>
              </el-form-item>

              <el-form-item label="采购商品种类数" prop="tnum" size="small" :label-width="formLabelWidth">
                <el-input v-model="addForm.tnum" :disabled="true" class="inputStyle" ></el-input>
              </el-form-item>

              <el-form-item label="总采购数量" prop="productCode" size="small" :label-width="formLabelWidth">
                <el-input v-model="addForm.pnum" :disabled="true" class="inputStyle" ></el-input>
              </el-form-item>

              <el-form-item label="总采购价" prop="productCode" size="small" :label-width="formLabelWidth">
                <el-input v-model="addForm.totalPrice" :disabled="true" class="inputStyle" ></el-input>
              </el-form-item>

              <el-form-item label="申请人" prop="productCode" size="small" :label-width="formLabelWidth">
                <el-input v-model="addForm.supplyName" :disabled="true" class="inputStyle" ></el-input>
              </el-form-item>

              <el-form-item label="审核状态" prop="productCode" size="small" :label-width="formLabelWidth">
                <el-input v-model="addForm.checkStateName" :disabled="true" class="inputStyle" ></el-input>
              </el-form-item>

              <el-form-item label="审核人" prop="productCode" size="small" :label-width="formLabelWidth">
                <el-input v-model="addForm.checkName" :disabled="true" class="inputStyle" ></el-input>
              </el-form-item>
              <el-form-item label="审核时间" prop="productCode" size="small" :label-width="formLabelWidth">
                <el-input v-model="addForm.checkTime" :disabled="true" class="inputStyle" ></el-input>
              </el-form-item>
              
          </el-form>
      </el-card>
      <el-card class="box-card top-20">
        <div slot="header" class="clearfix">
          <span>采购列表</span>
        </div>
        <wTable :data="tableData"  :header="tableHeader"  :option="tableOption">
        </wTable>
      </el-card>
    </div>
    <div class="pageBox">
        <el-pagination
          @current-change="handleCurrentChange"
          background
          :page-size="pagesize"
          layout="prev, pager, next"
          :total="total">
        </el-pagination>
      </div>
    <div class="bntBox">
          <el-button  @click="ReturnLevel">取消</el-button>
      </div>
  </div>
</template>
<script>
import {getPinboundList,getStockPpurchaseList} from '@/api/stock'
import NProgress from 'nprogress'
import wTable from '@/components/table/w-table.vue'
export default {
  data() {
        return {
          addForm:{},
          activeName: 'first',
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
                prop: 'curNum',
                label: '当前库存数量',
                minWidth: '80'
              }, {
                prop: 'buyPrice',
                label: '采购单价',
                minWidth: '100'
              }, {
                prop: 'pnum',
                label: '采购数量',
              }, {
                prop: 'totalPrice',
                label: '采购总价',
              }
          ],
          tableData: [],
          options: [{
          value: '1',
          label: '未审核'
          }, {
            value: '2',
            label: '审核通过'
          }, {
            value: '3',
            label: '审核失败'
          }
          ],
          searchObj:{
            stockId:'',
            createTime:'',
            supplyName:'',
            wPurchaseId:'',
            checkState:''

          },
          }
  },
  watch: {
    
  },
  created: function() {
     if(window.localStorage){
        this.addForm = JSON.parse(localStorage.getItem("editData"));
    }
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
    search(){
      this.getList("")
    },
    ReturnLevel(){
      this.$router.push(
            {
                path:'/stock/stockPurchase/list'
            }
        );
    },
    addCheckSub(type) {//提交新增窗口
        let para ={
          wPurchaseId:this.$route.query.wPurchaseId,
          checkState:type
        }
        addCheck(para).then((res) => {
              if(res.zhead.reTCode=="0000"){
                  NProgress.done();
                  this.addinnerVisibleHide=false
                  this.$notify({
                    title: '成功',
                    message: res.zhead.retMsg,
                    type: 'success'
                  });
             
              }else{
                this.$notify({
                  title: '失败',
                  message: res.zhead.retMsg,
                  type: 'error'
                });
              }
        });
    },
    getList(page) {
      var _this = this;
      this.listLoading = true
      const listQuery = {
        wpurchaseId:_this.$route.query.wpurchaseId,
        pageSize:_this.pagesize,
        pageNum:page,
        orderByColumn:'createtime',
        isAsc:'desc'//desc
      }
      getStockPpurchaseList(listQuery).then(response => {
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
   this.getList('1')
  },
  components: {
    wTable
  }
}
</script>
<style scoped>

.bntBox{overflow: hidden; padding:30px 0; text-align:center; margin-top:20px;}
  .top-20{ margin-top: 20px; }
  .div-warp{padding:0 20px;}
  .pageBox{background: #fff; padding:20px 0;}

</style>