<template>
 <div class="div-warp">
    <div class="num-box">
      <el-row :gutter="20">
        <el-col :span="8"><div class="num-right-border">累计销售数量：<span>{{historyInfo.saleNum}}</span> 件</div></el-col>
        <el-col :span="8"><div class="num-right-border">累计销售金额：<span>{{historyInfo.saleMoney}}</span> 元</div></el-col>
        <el-col :span="8"><div class="num-right-border">累计毛利润：<span>{{historyInfo.profit}}</span> 元</div></el-col>
      </el-row>
    </div>
    <div class="block-warp"> 
            <div class="screen-box">
                <div class="searchBox">
                   <strong> 商品名称：</strong>
                    <el-input placeholder="请输入内容" v-model="searchObj.productName" class="inputStyle" size="small" clearable>
                      </el-input>
                    <strong>支付方式：</strong>
                    <el-select v-model="searchObj.payType" clearable size="small" class="inputStyle" placeholder="支付方式">
                      <el-option
                        key="01"
                        label="支付宝扫码支付"
                        value="01">
                      </el-option>
                      <el-option
                        key="02"
                        label="微信扫码支付"
                        value="02">
                      </el-option>
                      <el-option
                        key="03"
                        label="微信公众号支付"
                        value="03">
                      </el-option>
                    </el-select>
                    <strong> 创建时间：</strong>
                       <el-date-picker
                        v-model="searchObj.createTime"
                        align="right"
                        type="date"
                        clearable
                        size="small"
                        value-format="yyyy-MM-dd"
                        placeholder="选择日期"
                        >
                      </el-date-picker>
                   
                      <el-button type="success" @click.native.prevent="search" size="small">查询</el-button>
                </div>
            </div>
            <el-table
              :data="historyList"
              border
              size="small"
              style="width: 100%">
              <el-table-column
                prop="orderId"
                label="订单号"
                width="180">
              </el-table-column>
              <el-table-column
                prop="laneSId"
                label="货道号"
                width="80">
              </el-table-column>
              <el-table-column
                prop="productName"
                label="商品名称">
              </el-table-column>

              <el-table-column
                prop="salePrice"
                label="售价/元"
                width="100">
              </el-table-column>
              <el-table-column
                prop="profitMoney"
                label="毛利润"
                width="100">
              </el-table-column>
              <el-table-column
                prop="payTypeName"
                label="支付方式"
                width="100">
              </el-table-column>
              <el-table-column
                prop="payStateName"
                label="支付状态"
                width="100">
              </el-table-column>
              <el-table-column
                prop="createTime"
                label="创建时间"
                width="180">
              </el-table-column>
            </el-table>
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
    </div>
  </div>
</template>
<script>

import {selectTotalSale,orderApplyList} from '@/api/equipment'
import NProgress from 'nprogress'
export default {
  data() {
        return {
          formLabelWidth: '120px',
          historyInfo:{},
          input10:"",
          pagesize:10,
          total:1,
          value:'',
          historyList: [],
          searchObj:{
            productName:'',
            payType:'',
            createTime:""
          }
          
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
    search(){
      this.getList("1")
    },
    handleCurrentChange: function(currentPage){ 
     this.currentPage = currentPage;
     this.getList(currentPage)
    },
    getVendingRecord(page){
      var _this = this;
      const listQuery = {
        siteId:_this.$route.query.siteId,
      }
      selectTotalSale(listQuery).then(response => {
           if(response.zhead.reTCode==="0000"){
            _this.historyInfo = response.zbody.datas
            NProgress.done();
           }else{
               this.$notify({
                title: '失败',
                message: response.zhead.retMsg,
                type: 'error'
              });
           }
        })
    },
    getList(page){
      var _this = this;
      const listQuery = {
        productName:_this.searchObj.productName,
        payType:_this.searchObj.payType,
        createTime:_this.searchObj.createTime,
        pageSize:_this.pagesize,
        siteId:_this.$route.query.siteId,
        pageNum:page,
        orderByColumn:'createtime',
        isAsc:"desc",

      }
      orderApplyList(listQuery).then(response => {
           if(response.zhead.reTCode==="0000"){
            _this.historyList = response.zbody.datas.rows
            _this.total= response.zbody.datas.total
            console.log("历史交易记录：：：：",response.zbody.datas)
            NProgress.done();
           }else{
               this.$notify({
                title: '失败',
                message: response.zhead.retMsg,
                type: 'error'
              });
           }
        })
    }
  },

  mounted () {
    this.getVendingRecord()
    this.getList("1")
  },
  components: {

  }
}
</script>
<style scoped>
  .num-box{padding:30px 0; text-align: center; background: #fff; margin-top: 20px; border-radius: 3px;}
  .num-right-border{border-right: 1px solid #ddd}
  .num-right-border span{color:#006BC2; font-size:20px; color:#FF5757;}
  .operation-box{padding-top:4px;}
  .screen-box{padding: 0px 0 20px 0}
  .selectBox{padding:10px 0;}
  .selectBox strong{font-size:14px;color:#333; font-weight: normal; display: inline-block; }
  .selectBox .select-left{ padding-left:10px; }
  .searchBox{ padding: 10px 0; }
  .searchBox strong{ display: inline-block;float: left; line-height: 35px; font-size:14px; font-weight: normal; }
  .searchBox .inputStyle{width: 200px; display: inline-block;float:left; margin-right: 15px; }
  .tit{font-size:12px; line-height: 30px;}
  .num-right{display: inline-block;float: right; margin-top: 11px; font-size:12px;}
  .num-right span{ display: inline-block; padding-left: 20px; }
  .num-right span i{ display: inline-block; padding-left:10px; font-style:normal; color:#006BC2; }
  .num-right span i.Danger{color:#F56C6C;}
  .block-warp{background: #fff; margin: 20px 0; padding:10px; border-radius: 3px;}
  .bntBox{overflow: hidden; padding:0px 0 10px 0;}
  .div-warp{padding:0 20px;  overflow: hidden;}
  .pageBox{background: #fff; padding:20px 0; margin:20px 0;}
</style>