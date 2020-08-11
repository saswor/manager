<template>
 <div class="div-warp">

    <div style="margin-top:20px;">

      <el-dialog
        title="入口冲正单"
        :visible.sync="dialogVisible"
        width="800px"
        >
        <el-table
            :data="reInboundData"
            style="width: 100%">
            <el-table-column
              prop="productName"
              label="商品名称"
              width="180">
            </el-table-column>
             <el-table-column
              prop="reInboundNum"
              label="冲正数量">
              <template  scope="scope">
              <div >
                <el-input-number size="mini" :min="0" :max="scope.row.pnum" style="text-align:center" v-model="scope.row.reInboundNum" ></el-input-number>
                <div style="display:none">
                  <el-input size="mini" style="text-align:center" v-model="scope.row.reInboundNum"></el-input>
                </div>
              </div>
            </template>
            </el-table-column>
            <el-table-column
              label="操作">
              <template  scope="scope">
              <div>
                <el-button type="danger"  size="mini" icon="el-icon-delete" circle  @click.native.prevent="deleteRow(scope.$index,StockData)"></el-button>
              </div>
            </template>
            </el-table-column>
          </el-table>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible=false">取 消</el-button>
          <el-button type="primary" @click="subReInbound">提交</el-button>
        </div>
      </el-dialog>

       
    <el-card class="box-card">
          <div slot="header" class="clearfix">
            <el-row class="row-bcg">
              <el-col :span="12"><div class="name-box">入库详情</div></el-col>
              <el-col :span="12">
                <div style="text-align: right; margin-top: 3px;"> 
                  <el-button type="success" size="mini" @click="getLinkInbound" v-if="addForm.linkWinboundId!=null&&addForm.linkWinboundId!=undefined">关联入库单</el-button>
                </div>
              </el-col>
            </el-row>
            <!-- <span>入库详情</span>
            <span style="text-align: right; margin-top: 3px;"> 
              <el-button type="success" size="mini" @click="getCabinetByPointIdData(itme.pointId)">关联入库单</el-button>
            </span> -->
          </div>
          <el-form :model="addForm"  :inline="true">
              <el-form-item label="入库单号" prop="productCode" size="small" :label-width="formLabelWidth">
                <el-input v-model="addForm.winboundId"  class="inputStyle" ></el-input>
              </el-form-item>

              <el-form-item label="采购单号" prop="productCode" size="small" :label-width="formLabelWidth">
                <el-input v-model="addForm.wpurchaseId"  class="inputStyle" ></el-input>
              </el-form-item>

              <el-form-item label="仓库编号" prop="productCode" size="small" :label-width="formLabelWidth">
                <el-input v-model="addForm.stockId"  class="inputStyle" ></el-input>
              </el-form-item>

              <el-form-item label="仓库名称" prop="productCode" size="small" :label-width="formLabelWidth">
                <el-input v-model="addForm.stokcName"  class="inputStyle" ></el-input>
              </el-form-item>

              <el-form-item label="总入库数量" prop="productCode" size="small" :label-width="formLabelWidth">
                <el-input v-model="addForm.pnum"  class="inputStyle" ></el-input>
              </el-form-item>

              <el-form-item label="未入库数量" prop="productCode" size="small" :label-width="formLabelWidth">
                <el-input v-model="addForm.nopNum"  class="inputStyle" ></el-input>
              </el-form-item>

              <el-form-item label="入库商品总类" prop="productCode" size="small" :label-width="formLabelWidth">
                <el-input v-model="addForm.tnum" class="inputStyle" ></el-input>
              </el-form-item>

              <el-form-item label="入库采购总价" prop="productCode" size="small" :label-width="formLabelWidth">
                <el-input v-model="addForm.totalPrice" class="inputStyle" ></el-input>
              </el-form-item>

              <el-form-item label="入库人" prop="productCode" size="small" :label-width="formLabelWidth">
                <el-input v-model="addForm.inboundName" class="inputStyle" ></el-input>
              </el-form-item>
              <el-form-item label="入库状态" prop="productCode" size="small" :label-width="formLabelWidth">
                <el-input v-model="addForm.curStateName" class="inputStyle" ></el-input>
              </el-form-item>
              <el-form-item label="入库类型" prop="productCode" size="small" :label-width="formLabelWidth">
                <el-input v-model="addForm.inboundTypeName" class="inputStyle" ></el-input>
              </el-form-item>
              <el-form-item label="创建时间" prop="productCode" size="small" :label-width="formLabelWidth">
                <el-input v-model="addForm.createTime" class="inputStyle" ></el-input>
              </el-form-item>
              
          </el-form>
      </el-card>
      <el-card class="box-card top-20">
       <el-table
          :data="tableData"
          border
          size="mini"
          style="width: 100%">
          <el-table-column
            prop="productId"
            label="商品编号"
            width="180">
          </el-table-column>
          <el-table-column
            prop="productName"
            :show-overflow-tooltip="true"
            label="商品名称"
            width="180">
          </el-table-column>
          <el-table-column
            prop="buyNum"
            label="采购数量">
          </el-table-column>
          <el-table-column
            label="入库数量">
            <template scope="scope">
              <div>
                 <el-input-number size="mini"  style="width:100%" v-model="scope.row.pnum" @change="(value) => valChange(value, scope.$index, scope.row)" :min="0" :max="scope.row.buyNum"  label="数量"></el-input-number>
              
              </div>
            </template>
          </el-table-column>
          <el-table-column
            prop="nopNum"
            label="未入数量">
          </el-table-column>
          <el-table-column
            prop="buyPrice"
            label="采购单价">
          </el-table-column>
          <el-table-column
            prop="totalPrice"
            label="入库采购总价">
          </el-table-column>
        </el-table>
      </el-card>
    </div>
    <div class="bntBox">
          <el-button type="primary" @click="addCheckSub" :disabled="disableFlag">提交</el-button>
          <el-button type="primary" @click="reInboundList" :disabled="addForm.curState!='2'||addForm.inboundType=='2'">入库冲正</el-button>
      </div>
  </div>
</template>
<script>
import {getStockDetailList,submitInbound,stockInboundDetail,submitReInbound} from '@/api/stock'
import NProgress from 'nprogress'
import wTable from '@/components/table/w-table.vue'
export default {
  data() {
        return {
          addStock:{
            wInboundId:"",
            stockPinbounds:[]
          },
          disableFlag:false,
          dialogVisible:false,
          addForm:{},
          activeName: 'first',
          keyword:'',
          total:1,
          currentPage:1, 
          pagesize:100, 
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
    this.disableFlag=false;
  },
  methods: {
    handleClick(tab, event) {
        console.log(tab, event);
    },
    ReturnLevel(){
      this.$router.push(
            {
                path:'/stock/stockPurchase/list'
            }
        );
    },
    getLinkInbound(){
      var _this=this;
      this.$router.push(
          {
              path:'/stock/stockInbound/see',
              query: {
                wpurchaseId: _this.addForm.wpurchaseId,
                winboundId:_this.addForm.linkWinboundId
              }
          }
      );
    },
    valChange(value,index,row){
        var _this =this;
        row.nopNum = row.buyNum-value
        var ifOk = true;
        if(parseInt(row.pnum) > 0){
          if(_this.addStock.stockPinbounds.length>0){
              _this.addStock.stockPinbounds.forEach((item, index) => {
                if(row.logid===item.logid){
                 item.outNum = row.outNum
                 ifOk =false
                }
              })
              if(ifOk){
                 _this.addStock.stockPinbounds.push(row)
              }
            }else{
              _this.addStock.stockPinbounds.push(row)
            }
        }
    },
    
    addCheckSub(type) {//提交新增窗口
      this.$confirm('确认提交吗？', '提示', {}).then(() => {
        this.disableFlag=true;
        this.addStock.wInboundId = this.addForm.winboundId
        let para = this.addStock
        submitInbound(para).then((res) => {
              if(res.zhead.reTCode=="0000"){
                  this.addStock.stockPinbounds=[]
                  NProgress.done();
                   this.$router.push(
                      {path:'/stock/stockInbound/list'}
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
        this.disableFlag=false;
      });
       
    },
   reInboundList() {//显示入库冲正单
     var _this = this;
      this.listLoading = true
      const listQuery = {
        winboundId: this.addForm.winboundId
      }
      getStockDetailList(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
            NProgress.done();
            this.dialogVisible = true
            this.reInboundData = response.zbody.datas.rows

         }else{
             this.$notify({
              title: '失败',
              message: res.zhead.retMsg,
              type: 'error'
            });
         }
      })
    },
    //提交入库冲正单 
    subReInbound(type) {
      this.$confirm('确认提交吗？', '提示', {}).then(() => {
        let para = this.reInboundData
        submitReInbound(para).then((res) => {
              if(res.zhead.reTCode=="0000"){
                  NProgress.done();
                   this.$router.push(
                      {path:'/stock/stockInbound/list'}
                    );
                  this.$notify({
                    title: '成功',
                    message: res.zhead.retMsg,
                    type: 'success'
                  });
             
              }else{
                NProgress.done();
                this.$router.push(
                  {path:'/stock/stockInbound/list'}
                );
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
      this.listLoading = true
      const listQuery = {
        winboundId: this.$route.query.winboundId,
        pageSize:_this.pagesize,
        pageNum:page,
        orderByColumn:'createtime',
        isAsc:'desc'//desc
      }
      getStockDetailList(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
            NProgress.done();
          _this.total = response.zbody.datas.total;  
          _this.tableData = response.zbody.datas.rows;
          console.log("入库商品列表：：",_this.tableData)
         }else{
             this.$notify({
              title: '失败',
              message: res.zhead.retMsg,
              type: 'error'
            });
         }
      })
    },
    getDetail() {
      var _this = this;
      const listQuery = {
        wInboundId: this.$route.query.winboundId,
      }
      stockInboundDetail(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
            NProgress.done();
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
   this.getList("1")
   this.getDetail()
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