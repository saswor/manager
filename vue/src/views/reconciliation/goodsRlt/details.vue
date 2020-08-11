<template>
 <div class="div-warp">
  <div class="div-warp" style="margin-top:30px;">
    <el-form :model="addForm"  inline ref="addForm" @submit.prevent="onSubmit">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>查看</span>
        </div>
          <el-form-item label="线路名称" size="small" :label-width="formLabelWidth">
             <el-input v-model="addForm.lineName" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="线路编号"  size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.lineId" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="点位名称" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.pointName" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="点位编号"  size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.pointId" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="区域名称" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.districtName" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="区域编号"  size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.districtId" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="仓库名称"  size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.wmName" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="仓库编号"  size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.sorderId" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="交易开始时间"  size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.tradeSTime" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="交易结束时间"  size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.tradeEtime" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="实际补货数量"  size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.supplyNum" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="已销售数量"  size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.saleNum" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="已下架数量"  size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.underNum" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="采购总金额"  size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.buyMoney" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="销售总金额"  size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.salePMoney" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="支付总金额"  size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.saleRMoney" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="优惠总金额"  size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.saleFMoney" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="下架总金额"  size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.saleUMoney" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="对账异常商品数"  size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.abnomarlNum" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="对账异常金额"  size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.abnomarlMoney" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="销售状态"  size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.salteStateName" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="对账状态"  size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.statementStateName" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="结算状态"  size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.curStateName" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
      </el-card>
      </el-form>
      <div class="bntBox">
          <el-button type="primary" @click="addSubmitForm">确定</el-button>
      </div>
       
  </div>
  </div>
</template>
<script>
import {getStatementSupplyDetails} from '@/api/reconciliation'
export default {
  data() {
        return {
          addForm: {},
          formLabelWidth:"200px"
        }
  },

  methods: {
     addSubmitForm(){
       this.$router.push({path:'/reconciliation/goodsRlt/index'})
     },
     getStatementSupplyDetailData(page){
        var _this = this;
        const listQuery = {
          logid:_this.$route.query.logid,
        }
        getStatementSupplyDetails(listQuery).then(response => {
          if(response.zhead.reTCode==="0000"){
          _this.addForm = response.zbody.datas;
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
    this.getStatementSupplyDetailData()
  },
  components: {
  }
}
</script>
<style scoped>
 .bntBox{overflow: hidden; padding:30px 0; text-align:center; margin-top:20px;}
</style>