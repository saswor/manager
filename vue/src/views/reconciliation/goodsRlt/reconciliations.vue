<template>
 <div class="div-warp">
  <div class="div-warp" style="margin-top:30px;">
    <el-form :model="addForm"  inline ref="addForm" @submit.prevent="onSubmit">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>对账</span>
        </div>
          <el-form-item label="对账异常商品数" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.abnomarlNum" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="对账异常金额"  size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.abnomarlMoney" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="销售状态" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.salteState" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="对账状态" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.statementState" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="结算状态" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.curState" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="结算状态" size="small" :label-width="formLabelWidth"> 
              <el-upload
              class="upload-demo"
              action="/system/statementSupply/importStatement"
              :data="pppss"
              :on-preview="handlePreview"
              :on-remove="handleRemove"
              :before-remove="beforeRemove"
              :on-success="upSuccess"
              multiple
              :limit="1"
              :on-exceed="handleExceed"
              :file-list="fileList">
              <el-button size="small" type="primary">导入对账</el-button>
              </el-upload>
          </el-form-item>
      </el-card>
      <el-card class="box-card" style="margin-top:20px;">
          <el-table
            :data="tableData"
            border
            style="width: 100%">
            <el-table-column
              prop="productId"
              :show-overflow-tooltip="true"
              label="商品编号"
              width="100">
            </el-table-column>
            <el-table-column
              prop="orderId"
              :show-overflow-tooltip="true"
              label="订单编号"
              width="100">
            </el-table-column>
            <el-table-column
              prop="siteId"
              :show-overflow-tooltip="true"
              label="售卖机编号">
            </el-table-column>

            <el-table-column
              prop="laneSId"
              :show-overflow-tooltip="true"
              label="货道开始编号">
            </el-table-column>
            <el-table-column
              prop="laneEId"
              :show-overflow-tooltip="true"
              label="货道结束编号">
            </el-table-column>
            <el-table-column
              prop="seqId"
              :show-overflow-tooltip="true"
              label="当前货道排序">
            </el-table-column>
            <el-table-column
              prop="sorderId"
              :show-overflow-tooltip="true"
              label="补货编号">
            </el-table-column>
            <el-table-column
              prop="siteId"
              :show-overflow-tooltip="true"
              label="售卖机编号">
            </el-table-column>
            <el-table-column
              prop="outType"
              :show-overflow-tooltip="true"
              label="出柜类型">
            </el-table-column>
            <el-table-column
              prop="siteId"
              :show-overflow-tooltip="true"
              label="售卖机编号">
            </el-table-column>
            <el-table-column
              prop="payType"
              :show-overflow-tooltip="true"
              label="支付类型">
            </el-table-column>
            <el-table-column
              prop="rtradeNo"
              :show-overflow-tooltip="true"
              label="平台交易流水号">
            </el-table-column>
            <el-table-column
              prop="tradeNo"
              :show-overflow-tooltip="true"
              label="平台支付流水号">
            </el-table-column>
            <el-table-column
              prop="saleRMoney"
              :show-overflow-tooltip="true"
              label="支付金额">
            </el-table-column>
            <el-table-column
              prop="saleReturn"
              :show-overflow-tooltip="true"
              label="退款金额 ">
            </el-table-column>
            <el-table-column
              prop="curState"
              :show-overflow-tooltip="true"
              label="对账状态">
            </el-table-column>
            <el-table-column
              prop="createTime"
              :show-overflow-tooltip="true"
              label="创建时间">
            </el-table-column>
          </el-table>
      </el-card>
      </el-form>
      <div class="pageBox">
              <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                background
                :page-size="pagesize"
                layout="prev, pager, next"
                :total="total">
              </el-pagination>
            </div>
      <div class="bntBox">
          <el-button type="primary" @click="addSubmitForm">确定</el-button>
      </div>
       
  </div>
  </div>
</template>
<script>
import NProgress from 'nprogress'
import {getStatementProductList,importExcel,getStatementProductDetail} from '@/api/reconciliation'
export default {
  data() {
        return {
          pppss:{
            sOrderId:this.$route.query.sorderId,
            siteId:this.$route.query.siteId,
          },
          fileList:[],
          addForm: {},
          formLabelWidth:"200px",
          tableData: [],
          total:1,
          currentPage:1, 
          pagesize:10, 
        }
  },

  methods: {
    addSubmitForm(page){
       this.$router.push({path:'/reconciliation/goodsRlt/index'})
     },
    handleSizeChange: function (size) { 
       this.pagesize = size; 
    }, 
    handleCurrentChange: function(currentPage){ 
     this.currentPage = currentPage;
     this.getStatementSupplyListData(currentPage)
    },
    //对账明细列表显示接口上传完附件后调用
    getStatementProductListData(page){
        var _this = this;
        const listQuery = {
          sOrderId:_this.$route.query.sOrderId,
          siteId:_this.$route.query.siteId,
          pageSize:_this.pagesize,
          pageNum:page,
          orderByColumn:"createtime",
          isAsc:"desc"

        }
        getStatementProductList(listQuery).then(response => {
          if(response.zhead.reTCode==="0000"){
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
    //对账结果显示上传完附件后调用
    getStatementProductDetailData(logid){
        var _this = this;
        const listQuery = {
          logid:_this.$route.query.logid
        }
        getStatementProductDetail(listQuery).then(response => {
          if(response.zhead.reTCode==="0000"){
            this.addForm= response.zbody.datas
          }else{
             this.$notify({
                title: '失败',
                message: res.zhead.retMsg,
                type: 'error'
              });
          }

        })
    },
    //上传附件的回调
    handleRemove(file, fileList) {
        console.log(file, fileList);
    },
    handlePreview(file) {
        console.log(file);
    },
    handleExceed(files, fileList) {
        this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
    },
    beforeRemove(file, fileList) {
        return this.$confirm(`确定移除 ${ file.name }？`);
    },
    upSuccess(file, fileList){

      this.getStatementProductListData("1")
      this.getStatementProductDetailData()
    }
  },
  mounted () {
  },
  components: {
  }
}
</script>
<style scoped>
 .bntBox{overflow: hidden; padding:30px 0; text-align:center; margin-top:20px;}
  .pageBox{background: #fff; padding:20px 0; margin:20px 0;}
</style>