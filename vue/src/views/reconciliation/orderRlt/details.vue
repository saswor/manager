<template>
 <div class="div-warp">
  <div class="div-warp" style="margin-top:30px;">
    <el-form :model="addForm"  inline ref="addForm" @submit.prevent="onSubmit">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>订单信息</span>
        </div>
          <el-form-item label="订单编号" size="small" :label-width="formLabelWidth">
             <el-input v-model="addForm.orderApplyInfo[0].orderId" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="售后机编号" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.orderApplyInfo[0].siteId" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="站点名称" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.orderApplyInfo[0].siteName" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="商品数量" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.orderApplyInfo[0].pNum" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="商品总售价" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.orderApplyInfo[0].salePrice" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="商品总支付价格" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.orderApplyInfo[0].payPrice" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="总优惠金额" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.orderApplyInfo[0].favPrice" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="毛利总额" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.orderApplyInfo[0].profitMoney" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="退款状态" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.orderApplyInfo[0].returnType" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="总退款金额" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.orderApplyInfo[0].returnMoney" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="支付方式" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.orderApplyInfo[0].payType" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="支付状态" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.orderApplyInfo[0].payState" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="异常状态" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.orderApplyInfo[0].abnomarlStateName" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="支付流水号" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.orderApplyInfo[0].pTradeNo" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
      </el-card>
      </el-form>

      <el-card class="box-card" style="margin-top:20px;">
        <div slot="header" class="clearfix">
          <span>订单商品</span>
        </div>
        <el-table
            ref="singleTable"
            :data="addForm.orderProductInfo"
            highlight-current-row
            style="width: 100%">
            <el-table-column
              type="index"
              width="50">
            </el-table-column>
            <el-table-column
              property="productId"
              width="200"
              label="商品编号"
              >
            </el-table-column>
            <el-table-column
              property="productName"
              width="300"
              :show-overflow-tooltip="true"
              label="商品名称"
              >
            </el-table-column>
            <el-table-column
              property="salePrice"
              label="支付单价">
            </el-table-column>
            <el-table-column
              property="saleNum"
              label="售卖数量">
            </el-table-column>
          </el-table>
      </el-card>
      <el-card class="box-card" style="margin-top:20px;">
        <div slot="header" class="clearfix">
          <span>订单出货</span>
        </div>
        <el-table
            ref="singleTable"
            :data="addForm.orderBoxInfo"
            highlight-current-row
            style="width: 100%">
            <el-table-column
              label="出柜顺序"
              type="index"
              width="100">
            </el-table-column>
            <el-table-column
              property="productId"
              width="200"
              label="商品编号"
             >
            </el-table-column>
            <el-table-column
              property="productName"
              width="300"
              :show-overflow-tooltip="true"
              label="商品名称"
              >
            </el-table-column>
            <el-table-column
              property="laneSId"
              label="货道开始号">
            </el-table-column>
            <el-table-column
              property="laneEId"
              label="货道结束号">
            </el-table-column>
            <el-table-column
              property="outState"
              label="出柜状态">
            </el-table-column>
          </el-table>
      </el-card>
      <div class="bntBox">
          <el-button type="primary" @click="addSubmitForm">确定</el-button>
      </div>
       
  </div>
  </div>
</template>
<script>
import {getOrderApplyetails} from '@/api/reconciliation'
export default {
  data() {
        return {
          addForm: {},
          formLabelWidth:"200px"
        }
  },

  methods: {
     addSubmitForm(){
       var _this = this;
      //  this.$router.go(-1);
       this.$router.push(
         {
           path:'/reconciliation/orderRlt/index',
           query:{
              // orderId:_this.$route.query.orderId,
              districtId:_this.$route.query.districtId,
              lineId:_this.$route.query.lineId,
              siteId:_this.$route.query.siteId,
              curState:_this.$route.query.curState,
              abnomarlState:_this.$route.query.abnomarlState,
              startDate:_this.$route.query.startDate,
              endDate:_this.$route.query.endDate,
              currentPage:_this.$route.query.currentPage,
              reload:_this.$route.query.reload,
            }
         }
      )
     },
     getOrderApplyetailsData(page){
        var _this = this;
        const listQuery = {
          orderId:_this.$route.query.orderId,
        }
        getOrderApplyetails(listQuery).then(response => {
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
    this.getOrderApplyetailsData()
  },
  components: {
  },
}
</script>
<style scoped>
 .bntBox{overflow: hidden; padding:30px 0; text-align:center; margin-top:20px;}
</style>