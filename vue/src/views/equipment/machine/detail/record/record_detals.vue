<template>
 <div class="div-warp">
    <div style="text-align:right">
      <el-button type="primary" size="mini" @click="printContent">打印</el-button>
    </div>
    <div id="printTest" style="overflow:hidden">
        <div class="block-warp">
           <div class="tit" style="margin-bottom:20px;">补货记录信息</div>
              <el-form :model="supplyOrder"  label-width="120px" :label-position="labelPosition" :inline="true"  ref="supplyOrder" >
              <el-form-item label="补货编号：" size="small"  :label-width="formLabelWidth">
                <span>{{supplyOrder.vorderId}}</span>
              </el-form-item>
              <el-form-item label="完成时间：" size="small"  :label-width="formLabelWidth">
                <span>{{supplyOrder.supplyFTime}}</span>
              </el-form-item>
              <el-form-item label="线路编号：" size="small"  :label-width="formLabelWidth">
                <span>{{supplyOrder.lineId}}</span>
              </el-form-item>
              <el-form-item label="线路名称："  size="small" :label-width="formLabelWidth">
               <span>{{supplyOrder.lineName}}</span>
              </el-form-item>
              <el-form-item label="仓库编号：" size="small" :label-width="formLabelWidth">
                <span>{{supplyOrder.wmId}}</span>
              </el-form-item>
              <el-form-item label="仓库名称：" size="small"  :label-width="formLabelWidth">
                <span>{{supplyOrder.wmName}}</span>
              </el-form-item>
             
              <el-form-item label="补货员名："  size="small"  :label-width="formLabelWidth">
                <span>{{supplyOrder.supplierName}}</span>
              </el-form-item>
              
              <el-form-item label="需补货总数："  size="small"  :label-width="formLabelWidth">
                <span>{{supplyOrder.supplyNum}}</span>
              </el-form-item>
              <el-form-item label="补货状态："  size="small"  :label-width="formLabelWidth">
                <span>{{supplyOrder.curStateName}}</span>
              </el-form-item>
             
            </el-form>
        </div>
   
    </div>
    <div class="block-warp">
       <div class="tit">点位补货记录</div>
       <div v-for="(itme,index) in siteInfoData">
       <el-row class="row-bcg">
          <el-col :span="8"><div class="name-box">点位编号：{{itme.siteId}}</div></el-col>
          <el-col :span="8"><div class="name-box">点位名称：{{itme.siteName}}</div></el-col>
          
       </el-row>

       <el-table
          :data="itme.supplyInfo"
          style="width: 100%"
          >
          <el-table-column
            prop="productId"
            label="商品编号"
            width="200">
          </el-table-column>
          <el-table-column
            prop="productName"
            label="商品名称"
            >
          </el-table-column>
          <el-table-column
            prop="supplyNum"
            label="缺货数量"
           >
          </el-table-column>
          <el-table-column
            prop="rSupplyNum"
            label="已补数量"
           >
          </el-table-column>
       </el-table>
       </div>
    </div>
  </div>
</template>
<script>
import { getRecord_details} from '@/api/equipment'
import Print from 'print-js'
import NProgress from 'nprogress'
export default {
  data() {
        return {
          labelPosition:"right",
          formLabelWidth:"",
          tableData3: [],
          siteInfoData:[],
          supplyOrder:{}
        }
  },
  watch: {
  },
  created() {
    
  },
  methods: {
     printContent(e){
        let subOutputRankPrint = document.getElementById('printTest');
        console.log(subOutputRankPrint.innerHTML);
        let newContent =subOutputRankPrint.innerHTML;
        let oldContent = document.body.innerHTML;
        document.body.innerHTML = newContent;
        window.print();
        window.location.reload();
        document.body.innerHTML = oldContent;
        return false;
    },

    getSupplyOrderDetailData(){
      var _this = this;
      this.listLoading = true
      const listQuery = {
        sOrderId:this.$route.query.sOrderId,
        vorderId:this.$route.query.vorderId
      }
      getRecord_details(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
           
            NProgress.done();
            _this.siteInfoData = response.zbody.datas.siteInfo;
            _this.supplyOrder = response.zbody.datas.supplyVorder;
          console.log("捕货记录详细信息：：",response.zbody.datas)
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
    this.getSupplyOrderDetailData()
  },
  components: {
  
  }
}
</script>
<style scoped>
  .block-warp span{width:190px; display:block;}
  .row-bcg{background: #efefef; padding:5px; margin-top: 15px;}
  .name-box{padding:10px 0; text-align: left; text-indent: 4px;  font-size:12px;}
  .sub_title{ background: #efefef; margin-left:9px; font-size:14px; padding:10px 0; text-indent: 10px; }
  .tit{font-size:16px; text-align: left; text-indent:8px; font-weight: bold; color:#006CC1; line-height: 30px;}
  .block-warp{background: #fff; margin: 20px 0; padding:10px; border-radius: 3px;}
  .bntBox{overflow: hidden; padding:0px 0 10px 0;}
  .div-warp{padding:0 20px;  overflow: hidden;}
  .pageBox{background: #fff; padding:20px 0; margin:20px 0;}
  .icon-size-circle{ font-size: 60px; color:red; margin-top: 50px;}
.pageBox{background: #fff; margin-top: 30px;}
.add-class{width:100%; height: 168px; text-align: center;}
.icon-size{ font-size: 60px; color:#efefef; margin-top: 50px; cursor: pointer;}
.icon-size:hover{color:#006CC1;}
.layerbottom{text-align: center; overflow: hidden; padding:30px 0;}
.input-left{width:70px;float:left;}
.input-right{width:70px;float:right;}
.block-top{background: #efefef; overflow: hidden;}
.block-top .delBnt{float: right;  margin-right:4px;}
.block-top .checkbox-left{float: left; margin-top: 4px; margin-left: 4px;}
.price {
    font-size: 13px;
    color: #999; margin-top: 10px;
  }
  .price i{ color:#F56C6C; font-size:20px; }
  .titles {
    font-size: 14px; margin-top: 10px;
    color: #333;
  }
  .bottom {
    line-height: 12px;
  }

  .button {
    padding: 0;
    float: right;
  }

  .image {
    width: 100%;
    display: block;
  }

  .clearfix:before,
  .clearfix:after {
      display: table;
      content: "";
  }
  
  .clearfix:after {
      clear: both
  }
.imgStyel{}
.imgStyel img{width: 100%; display: block;}
.layerBox{padding:0 30px; min-width: 1200px;}
  .bntBox{text-align: center; margin-top: 30px;}
  .top-20{ margin-top: 20px; }
  .inputStyle{width:215px;}
  .el-autocomplete{width:100%;}
  .div-warp{margin: 20px;}
  .dialog-footer{ padding-left:120px;  }
  .searchBox{ padding: 0; }
  .searchBox strong{ display: inline-block;float: left; line-height: 35px; font-size:14px; font-weight: normal; }
  .searchBox .inputStyle{width: 200px; display: inline-block;float:left; margin-right: 15px; }
  .el-dialog__header{border-bottom: 1px solid #000}
</style>