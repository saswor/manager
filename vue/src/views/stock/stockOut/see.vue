<template>
 <div class="div-warp">

   <el-dialog
        title="生成采购单"
        :visible.sync="inDialogVisible"
        width="800px"
        >
        <el-table
            :data="extraInboundData"
            style="width: 100%">
            <el-table-column
              prop="productName"
              label="商品名称"
              width="180">
            </el-table-column>
            <el-table-column
              prop="buyPrice"
              label="采购单价">
              <template  scope="scope">
              <div style="text-align:center;">
                <el-input-number size="mini" :min="0" clearable @change="CalculationTotal(scope.row)" style="text-align:center" v-model="scope.row.buyPrice" ></el-input-number>
              </div>
            </template>
            </el-table-column>
            <el-table-column
              prop="pNum"
              label="采购数量">
              <template  scope="scope">
              <div style="text-align:center;">
                <el-input-number size="mini" :min="0" clearable @change="CalculationTotal(scope.row)" @keyup.native="checkInt(scope.row)" style="text-align:center" v-model="scope.row.pNum"></el-input-number>
              </div>
            </template>
            </el-table-column>
            <el-table-column
              prop="sumPrice"
              label="采购总价">
            </el-table-column>
            <el-table-column
              label="操作">
              <template  scope="scope">
              <div style="text-align:left;">
                <el-button type="danger"  size="mini" icon="el-icon-delete" circle @click.native.prevent="deleteRow(scope.$index,extraInboundData)"></el-button>
              </div>
            </template>
            </el-table-column>
          </el-table>
          

    <!-- subStockPurchase -->
         <div slot="footer" class="dialog-footer">
          <el-button @click="inDialogVisible=false">取 消</el-button>
          <el-button type="primary" @click="subExtraInbound">提交</el-button>
        </div>
      </el-dialog>

      <el-dialog
        title="追加出库"
        :visible.sync="outDialogVisible"
        width="800px"
        >
        <el-table
            :data="extraOutData"
            style="width: 100%">
            <el-table-column
              prop="productName"
              label="商品名称"
              width="180">
            </el-table-column>
            <el-table-column
              prop="buyPrice"
              label="采购单价">
              <template  scope="scope">
              <div style="text-align:center;">
                <el-input size="mini" :min="0.00" clearable @change="CalculationTotal(scope.row)" style="text-align:center" v-model="scope.row.buyPrice" :readonly="true"></el-input>
              </div>
            </template>
            </el-table-column>
            <el-table-column
              prop="pNum"
              label="追加出库数量">
              <template  scope="scope">
              <div style="text-align:center;">
                <el-input-number size="mini" :min="0" clearable @change="CalculationTotal(scope.row)" @keyup.native="checkInt(scope.row)" style="text-align:center" v-model="scope.row.pNum"></el-input-number>
              </div>
            </template>
            </el-table-column>
            <el-table-column
              prop="sumPrice"
              label="采购总价">
            </el-table-column>
            <el-table-column
              label="操作">
              <template  scope="scope">
              <div style="text-align:left;">
                <el-button type="danger"  size="mini" icon="el-icon-delete" circle @click.native.prevent="deleteRow(scope.$index,extraOutData)"></el-button>
              </div>
            </template>
            </el-table-column>
          </el-table>
          

    <!-- subStockPurchase -->
         <div slot="footer" class="dialog-footer">
          <el-button @click="outDialogVisible=false">取 消</el-button>
          <el-button type="primary" @click="subExtraOut">提交</el-button>
        </div>
      </el-dialog>

    <div style="margin-top:20px;">
    <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>出库详情</span>
          </div>
          <el-form :inline="true">
              <el-form-item label="补货编号"  size="small" :label-width="formLabelWidth">
                <el-input v-model="addForm[0].sOrderId"  class="inputStyle" readonly></el-input>
              </el-form-item>

              <el-form-item label="仓库编号" prop="productCode" size="small" :label-width="formLabelWidth">
                <el-input v-model="addForm[0].wmId"  class="inputStyle"  readonly></el-input>
              </el-form-item>

              <el-form-item label="仓库名称" prop="productCode" size="small" :label-width="formLabelWidth">
                <el-input v-model="addForm[0].wmName"  class="inputStyle"  readonly></el-input>
              </el-form-item>
              <el-form-item label="线路编号" prop="productCode" size="small" :label-width="formLabelWidth">
                <el-input v-model="addForm[0].lineId"  class="inputStyle"  readonly></el-input>
              </el-form-item>
              <el-form-item label="出库数量" prop="productCode" size="small" :label-width="formLabelWidth">
                <el-input v-model="addForm[0].outNum"  class="inputStyle" readonly ></el-input>
              </el-form-item>

              <el-form-item label="出库总价格" prop="productCode" size="small" :label-width="formLabelWidth">
                <el-input v-model="addForm[0].totalPrice"  class="inputStyle" readonly ></el-input>
              </el-form-item>

              <el-form-item label="实际补货数量" prop="productCode" size="small" :label-width="formLabelWidth">
                <el-input v-model="addForm[0].supplyrNum"  class="inputStyle" readonly ></el-input>
              </el-form-item>
              <el-form-item label="剩余数量" prop="productCode" size="small" :label-width="formLabelWidth">
                <el-input v-model="addForm[0].supplynNum"  class="inputStyle" readonly ></el-input>
              </el-form-item>
              <el-form-item label="损失数量" prop="productCode" size="small" :label-width="formLabelWidth">
                <el-input v-model="addForm[0].supplydNum"  class="inputStyle" readonly ></el-input>
              </el-form-item>
              <el-form-item label="丢失数量" prop="productCode" size="small" :label-width="formLabelWidth">
                <el-input v-model="addForm[0].supplylNum"  class="inputStyle" readonly ></el-input>
              </el-form-item>

              <el-form-item label="补货员" prop="productCode" size="small" :label-width="formLabelWidth">
                <el-input v-model="addForm[0].supplierName" class="inputStyle" readonly ></el-input>
              </el-form-item>
              <el-form-item label="出库状态" prop="productCode" size="small" :label-width="formLabelWidth">
                <el-input v-model="addForm[0].stockState" class="inputStyle"  readonly></el-input>
              </el-form-item>

              <el-form-item label="异常状态" prop="productCode" size="small" :label-width="formLabelWidth">
                <el-input v-model="addForm[0].supplydType=='2'?'补货异常':'正常'" class="inputStyle"  readonly></el-input>
              </el-form-item>

              <el-form-item label="创建时间" prop="productCode" size="small" :label-width="formLabelWidth">
                <el-input v-model="addForm[0].createTime" class="inputStyle" readonly ></el-input>
              </el-form-item>
              
          </el-form>
      </el-card>
      <el-card class="box-card top-20">
       <el-table
          :data="tableData"
          border
          style="width: 100%">
          <el-table-column
            prop="productId"
            :show-overflow-tooltip="true"
            label="商品编号"
            width="180">
          </el-table-column>
          <el-table-column
            prop="productName"
            :show-overflow-tooltip="true"
            label="商品名称"
            >
          </el-table-column>
          <el-table-column
            width="140"
            label="出库数量">
            <template scope="scope">
              <div>
                 <!-- <el-input-number size="mini" style="width:100%" v-model="scope.row.outNum" @change="(value) => valChange(value, scope.$index, scope.row)" :min="0" label="数量"></el-input-number> -->
                 <el-input-number size="mini" @change="Calculation(scope.row)" style="width:100%" v-model="scope.row.outNum" :min="0" label="数量" :disabled="addForm[0].stockState!='未审核'"></el-input-number>
              
              </div>
            </template>
          </el-table-column>
          <el-table-column
            prop="buyPrice"
             width="140"

            label="采购单价">
            <template scope="scope">
              <div>
              <el-select v-model="scope.row.buyPrice" @change="Calculation(scope.row)" @visible-change="getbuyPriceList(scope.$index, tableData)" size="mini" placeholder="请选择" :disabled="addForm[0].stockState!='未审核'">
                <el-option
                  v-for="item in buyPriceList"
                  :label="item"
                  :value="item">
                </el-option>
              </el-select>
              </div>
            </template>
          </el-table-column>
          <el-table-column
           width="140"
            prop="totalPrice"
            label="采购总价">
          </el-table-column>

          <el-table-column
            width="140"
            label="实际补货数量">
            <template scope="scope">
              <div>
                 <!-- <el-input-number size="mini" style="width:100%" v-model="scope.row.outNum" @change="(value) => valChange(value, scope.$index, scope.row)" :min="0" label="数量"></el-input-number> -->
                 <el-input-number size="mini"  style="width:100%" v-model="scope.row.supplyrNum" :min="0" label="数量" disabled="disabled"></el-input-number>
              </div>
            </template>
          </el-table-column>

           <el-table-column
            width="140"
            label="剩余数量">
            <template scope="scope">
              <div>
                 <!-- <el-input-number size="mini" style="width:100%" v-model="scope.row.outNum" @change="(value) => valChange(value, scope.$index, scope.row)" :min="0" label="数量"></el-input-number> -->
                 <el-input-number size="mini"  style="width:100%" v-model="scope.row.supplynNum" :min="0" label="数量" :disabled="addForm[0].supplydType!='2'"></el-input-number>
              </div>
            </template>
          </el-table-column>

           <el-table-column
            width="140"
            label="损失数量">
            <template scope="scope">
              <div>
                 <!-- <el-input-number size="mini" style="width:100%" v-model="scope.row.outNum" @change="(value) => valChange(value, scope.$index, scope.row)" :min="0" label="数量"></el-input-number> -->
                 <el-input-number size="mini"  style="width:100%" v-model="scope.row.supplydNum" :min="0" label="数量" :disabled="addForm[0].supplydType!='2'"></el-input-number>
              
              </div>
            </template>
          </el-table-column>

          <el-table-column
            width="140"
            label="丢失数量">
            <template scope="scope">
              <div>
                 <!-- <el-input-number size="mini" style="width:100%" v-model="scope.row.outNum" @change="(value) => valChange(value, scope.$index, scope.row)" :min="0" label="数量"></el-input-number> -->
                 <el-input-number size="mini"  style="width:100%" v-model="scope.row.supplylNum" :min="0" label="数量" :disabled="addForm[0].supplydType!='2'"></el-input-number>
              
              </div>
            </template>
          </el-table-column>

        </el-table>
      </el-card>
    </div>
    <div class="bntBox">
          <el-button type="primary" @click="addCheckSub">提交</el-button>
          <el-button type="primary" @click="restocking" v-if="addForm[0].supplydType=='2'">剩余商品回收</el-button>
          <el-button type="primary" @click="extraInboundShow" v-if="addForm[0].supplydType=='2'">额外采购</el-button>
          <el-button type="primary" @click="extraOutShow" v-if="addForm[0].supplydType=='2'">追加出库</el-button>
      </div>
  </div>
</template>
<script>
import {supplyoutDetail,submitInbound,supplyoutEdit,supplyOutListAll,restocking,extraInbound,extraOut} from '@/api/stock'
import NProgress from 'nprogress'
import wTable from '@/components/table/w-table.vue'
export default {
  data() {
        return {
          extraInboundData:{},
          extraOutData:{},
          inDialogVisible:false,
          outDialogVisible:false,
          addStock:[],
          addForm:{},
          activeName: 'first',
          disableFlag:true,
          keyword:'',
          total:1,
          currentPage:1, 
          pagesize:10000, 
          formLabelWidth: '120px',
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
          tableData:[],
          buyPriceList:[],
          detailsInfo:{},
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
    
  },
  methods: {
    restocking(type) {//提交新增窗口
      this.$confirm('确认提交吗？', '提示', {}).then(() => {
        let para = {
          sOrderId:this.addForm[0].sOrderId,
          supplyProductInfo:this.tableData
        }

        restocking(para).then((res) => {
              if(res.zhead.reTCode=="0000"){
                  NProgress.done();
                  this.$router.push(
                    {path:'/stock/stockOut/list'}
                  );
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
      });
       
    },
    checkInt: function(row){
      row.pNum=row.pNum.replace(/[^\.\d]/g,'');
      row.pNum=row.pNum.replace('.','');

    },
    //显示生成采购单
    extraInboundShow() {
      var _this = this;
      this.listLoading = true
      const listQuery = {
        sOrderId:this.$route.query.sorderId
      }
      supplyoutDetail(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
            NProgress.done();
            this.inDialogVisible = true
            this.extraInboundData = response.zbody.datas.supplyOutProductInfo

         }else{
             this.$notify({
              title: '失败',
              message: res.zhead.retMsg,
              type: 'error'
            });
         }
      })
    },
    //显示最佳出库单
    extraOutShow() {
      var _this = this;
      this.listLoading = true
      const listQuery = {
        sOrderId:this.$route.query.sorderId
      }
      supplyoutDetail(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
            NProgress.done();
            this.outDialogVisible = true
            this.extraOutData = response.zbody.datas.supplyOutProductInfo

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
      row.totalPrice =  (parseFloat(row.buyPrice) * parseFloat(row.outNum)).toFixed(2)

    },
    // CalculationExtraOut(row){
    //   row.totalPrice =  (parseFloat(row.buyPrice) * parseFloat(row.outNum)).toFixed(2)

    // },
    CalculationTotal(row){
      row.sumPrice =  (parseFloat(row.buyPrice) * parseFloat(row.pNum)).toFixed(2)

    },
    deleteRow(index,rows){
       this.$confirm('确认删除吗？', '提示', {}).then(() => {
          rows.splice(index, 1);
       })
    },
    subExtraInbound(type) {//提交新增窗口
      this.$confirm('确认提交吗？', '提示', {}).then(() => {
        let para = {
          stockId:this.addForm[0].wmId,
          stockWarehouses:this.extraInboundData
        }
        extraInbound(para).then((res) => {
              if(res.zhead.reTCode=="0000"){
                  NProgress.done();
                  this.$router.push(
                    {path:'/stock/stockOut/list'}
                  );
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
      });
       
    },
    subExtraOut(type) {//提交新增窗口
      this.$confirm('确认提交吗？', '提示', {}).then(() => {
        let para = {
          sOrderId:this.addForm[0].sOrderId,
          supplyProductInfo:this.extraOutData
        }

        extraOut(para).then((res) => {
              if(res.zhead.reTCode=="0000"){
                  NProgress.done();
                  this.$router.push(
                    {path:'/stock/stockOut/list'}
                  );
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
      });
       
    },
    handleClick(tab, event) {
        console.log(tab, event);
    },
    handleSizeChange: function (size) { 
       this.pagesize = size; 
    }, 
    handleCurrentChange: function(currentPage){ 
     this.currentPage = currentPage;
     this.getData(currentPage)
     },
    search(){
      this.getList("1")
    },
    ReturnLevel(){
      this.$router.push(
            {
                path:'/stock/stockPurchase/list'
            }
        );
    },
           
    valChange(value,index,row){
        var _this =this;
        var ifOk = true;
        if(parseInt(row.outNum) > 0){
          if(_this.addStock.length>0){
              _this.addStock.forEach((item, index) => {
                if(row.productId===item.productId){
                  item.outNum = row.outNum
                  ifOk =false
                }
              })
              if(ifOk){
                 _this.addStock.push({
                    productId:row.productId,
                    buyPrice:row.buyPrice,
                    outNum:row.outNum
                  })
              }
            }else{
               _this.addStock.push({
                    productId:row.productId,
                    buyPrice:row.buyPrice,
                    outNum:row.outNum
                  })
            }
        }
  

    },
    addCheckSub(type) {//
    var _this=this;
      this.$confirm('确认提交吗？', '提示', {}).then(() => {
        for(var i=0;i<_this.tableData.length;i++){
          var stockOutItem=_this.tableData[i];
          if(stockOutItem.outNum>0){
            if(stockOutItem.buyPrice==null||stockOutItem.buyPrice==undefined||stockOutItem.buyPrice==''){
              this.$notify({
                  title: '失败',
                  message: '出库商品的采购价不能为空',
                  type: 'error'
                });
                return;
            }
            if(stockOutItem.buyPrice<=0){
              this.$notify({
                  title: '失败',
                  message: '出库商品的采购价不能小于等于0',
                  type: 'error'
                });
                return;
            }
          }
        }
        let para = {
          sOrderId:this.addForm[0].sOrderId,
          supplyProductInfo:this.tableData
        }

        supplyoutEdit(para).then((res) => {
              if(res.zhead.reTCode=="0000"){
                  NProgress.done();
                  this.$router.push(
                    {path:'/stock/stockOut/list'}
                  );
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
      });
       
    },
    getList(page) {
      var _this = this;
      const listQuery = {
        sOrderId:this.$route.query.sorderId,
        pageSize:_this.pagesize,
        pageNum:page,
        orderByColumn:'createtime',
        isAsc:'desc'
      }
      supplyoutDetail(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
          NProgress.done();
          _this.addForm = response.zbody.datas.supplyOut;
          _this.tableData = response.zbody.datas.supplyOutProductInfo;
          _this.dis
         }else{
             this.$notify({
              title: '失败',
              message: res.zhead.retMsg,
              type: 'error'
            });
         }
      })
    },
    getbuyPriceList(index,rows) {
      var _this = this;
      const listQuery = {
        productId:rows[index].productId
      }
      supplyOutListAll(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
          NProgress.done();
          _this.buyPriceList = response.zbody.datas.rows;
         }else{
             this.$notify({
              title: '失败',
              message: res.zhead.retMsg,
              type: 'error'
            });
         }
      })
    },
    selectVal(value){
        debugger
    }
  },

  mounted () {
   this.getList("1")
  },
  // mounted: function(){
  //   this.getList("1");
  //   if(addForm[0].supplyOut.supplydType=='2'){
  //     disableFlag=false;
  //   }
  //   alert(disableFlag);
  // },
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