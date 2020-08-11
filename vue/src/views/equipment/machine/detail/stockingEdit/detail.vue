<template>
 <div class="div-warp">

    <div style="margin-top:20px;">

      <el-dialog
        title="订单货道修改"
        :visible.sync="dialogVisible"
        width="800px"
        >
        <el-form :model="orderBoxForm"  :inline="true">
              <el-form-item label="订单编号" prop="productCode" size="small" :label-width="formLabelWidth">
                <el-input v-model="orderBoxForm.orderId"  class="inputStyle" readonly></el-input>
              </el-form-item>

              <el-form-item label="订单出货编号" prop="productCode" size="small" :label-width="formLabelWidth">
                <el-input v-model="orderBoxForm.proboxId"  class="inputStyle" readonly></el-input>
              </el-form-item>

              <el-form-item label="商品名称" prop="productCode" size="small" :label-width="formLabelWidth">
                <el-input v-model="orderBoxForm.productName"  class="inputStyle" readonly></el-input>
              </el-form-item>

              <el-form-item label="出柜状态" prop="productCode" size="small" :label-width="formLabelWidth">
                <el-select v-model="orderBoxForm.outState" size="small" placeholder="出柜状态">
                  <el-option
                    v-for="item in outStateOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
              <!-- <div slot="footer" class="dialog-footer"> -->
              <el-row type="flex" justify="center">
                <el-button type="primary" @click.prevent="subCommit">提交</el-button>
                <el-button @click.prevent="dialogVisible=false">取 消</el-button>
              </el-row>
              <!-- </div> -->
          </el-form>
        <!-- <el-table
            :data="orderBoxData"
            style="width: 100%">
            <el-table-column
              prop="orderId"
              label="订单编号"
              width="180">
            </el-table-column>
            <el-table-column
              prop="proBoxId"
              label="订单出货编号"
              width="180">
            </el-table-column>
            <el-table-column
              prop="productName"
              label="商品名称"
              width="180">
            </el-table-column>
            <el-table-column
              prop="outState"
              label="出柜状态"
              width="180">
              <el-select v-model="outState" size="small" placeholder="出柜状态">
                <el-option
                  v-for="item in outStateOptions"
                  :key="item.dictValue"
                  :label="item.dictLabel"
                  :value="item.dictValue">
                </el-option>
            </el-select>
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
          <el-button type="primary" @click="subCommit">提交</el-button>
        </div> -->
      </el-dialog>

       
    <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>差异详情</span>
          </div>
          <el-form :model="addForm"  :inline="true">
              <el-form-item label="差异编号" prop="productCode" size="small" :label-width="formLabelWidth">
                <el-input v-model="addForm.lsdifferId"  class="inputStyle" readonly></el-input>
              </el-form-item>

              <el-form-item label="售货机编号" prop="productCode" size="small" :label-width="formLabelWidth">
                <el-input v-model="addForm.siteId"  class="inputStyle" readonly></el-input>
              </el-form-item>

              <el-form-item label="售货机名称" prop="productCode" size="small" :label-width="formLabelWidth">
                <el-input v-model="addForm.siteName"  class="inputStyle" readonly></el-input>
              </el-form-item>

              <el-form-item label="货道开始编号" prop="productCode" size="small" :label-width="formLabelWidth">
                <el-input v-model="addForm.laneSId"  class="inputStyle" readonly></el-input>
              </el-form-item>

              <el-form-item label="货道结束编号" prop="productCode" size="small" :label-width="formLabelWidth">
                <el-input v-model="addForm.laneEId"  class="inputStyle" readonly></el-input>
              </el-form-item>

              <el-form-item label="商品编号" prop="productCode" size="small" :label-width="formLabelWidth">
                <el-input v-model="addForm.productId"  class="inputStyle" readonly></el-input>
              </el-form-item>

              <el-form-item label="商品名称" prop="productCode" size="small" :label-width="formLabelWidth">
                <el-input v-model="addForm.productName" class="inputStyle" readonly></el-input>
              </el-form-item>

              <el-form-item label="当前库存" prop="productCode" size="small" :label-width="formLabelWidth">
                <el-input v-model="addForm.curCap" class="inputStyle" readonly></el-input>
              </el-form-item>

              <el-form-item label="修正后库存" prop="productCode" size="small" :label-width="formLabelWidth">
                <el-input v-model="addForm.resetCap" class="inputStyle" readonly></el-input>
              </el-form-item>
              <el-form-item label="差异数" prop="productCode" size="small" :label-width="formLabelWidth">
                <el-input v-model="addForm.differNum" class="inputStyle" readonly></el-input>
              </el-form-item>
              <el-form-item label="处理类型" prop="productCode" size="small" :label-width="formLabelWidth">
                <el-input v-model="addForm.handleTypeName" class="inputStyle" readonly></el-input>
              </el-form-item>
              <el-form-item label="状态" prop="productCode" size="small" :label-width="formLabelWidth">
                <el-input v-model="addForm.curStateName" class="inputStyle" readonly></el-input>
              </el-form-item>
              <el-form-item label="创建人" prop="productCode" size="small" :label-width="formLabelWidth">
                <el-input v-model="addForm.createrName" class="inputStyle" readonly></el-input>
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
            prop="orderId"
            label="订单编号"
            width="180">
          </el-table-column>
          <el-table-column
            prop="proboxId"
            label="出货编号"
            width="180">
          </el-table-column>
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
            prop="outStateName"
            label="出柜状态">
          </el-table-column>
          <el-table-column
            prop="correctStateName"
            label="矫正状态">
          </el-table-column>
          <el-table-column
            prop="createTime"
            :show-overflow-tooltip="true"
            label="创建时间"
            width="180">
          </el-table-column>
          <el-table-column
              label="操作">
              <template  scope="scope">
              <div>
                <el-button type="primary" size="mini"  @click="handleEdit(scope.$index, scope.row)" :disabled="scope.row.correctState=='2'">操作</el-button>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
    </div>
  </div>
</template>
<script>
import {getOrderBoxList,vendingLsdifferDetail,updateOrderOutState,getOrderBoxDetail} from '@/api/equipment'
import NProgress from 'nprogress'
import wTable from '@/components/table/w-table.vue'
export default {
  data() {
        return {
          editOrder:{
            lsdifferId:"",
            orderBox:{}
          },
          disableFlag:false,
          dialogVisible:false,
          addForm:{},
          orderBoxForm:{},
          activeName: 'first',
          keyword:'',
          total:1,
          currentPage:1, 
          pagesize:100, 
          formLabelWidth: '120px',
          outStateOptions: [{
          value: '1',
          label: '未出柜'
          }, {
            value: '2',
            label: '正常已出柜'
          }, {
            value: '3',
            label: '异常已出柜'
          }, {
            value: '4',
            label: '出柜失败'
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
    handleEdit(index,row) {//显示生成采购单
     var _this = this;
      this.listLoading = true
      const listQuery = {
        proboxId:row.proboxId
      }
      getOrderBoxDetail(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
            NProgress.done();
            _this.dialogVisible = true
            _this.orderBoxForm = response.zbody.datas

         }else{
             this.$notify({
              title: '失败',
              message: res.zhead.retMsg,
              type: 'error'
            });
         }
      })
    },
    addCheckSub(type) {//提交新增窗口
      this.$confirm('确认提交吗？', '提示', {}).then(() => {
        this.disableFlag=true;
        this.editOrder.lsdifferId = this.addForm.lsdifferId
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
    //提交订单出货状态 
    subCommit(type) {
      this.$confirm('确认提交吗？', '提示', {}).then(() => {
        var _this=this;
        this.orderBoxForm.lsdifferId=this.addForm.lsdifferId;
        let para = Object.assign({}, this.orderBoxForm);
        updateOrderOutState(para).then((res) => {
              if(res.zhead.reTCode=="0000"){
                  NProgress.done();
                   this.$router.push(
                      {
                        path:'/equipment/machine/detail/stockingEdit/list',
                      }
                    );
                  this.$notify({
                    title: '成功',
                    message: res.zhead.retMsg,
                    type: 'success'
                  });
             
              }else{
                NProgress.done();
                this.$router.push(
                  {path:'/equipment/machine/index'}
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
        lsdifferId: this.$route.query.lsdifferId,
        siteId: this.$route.query.siteId,
        laneSId: this.$route.query.laneSId,
        laneEId: this.$route.query.laneEId,
        productId: this.$route.query.productId,
        pageSize:_this.pagesize,
        pageNum:page,
        orderByColumn:'createtime',
        isAsc:'desc'//desc
      }
      getOrderBoxList(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
            NProgress.done();
          _this.total = response.zbody.datas.total;  
          _this.tableData = response.zbody.datas.rows;
          console.log("出柜商品列表：：",_this.tableData)
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
        lsdifferId: this.$route.query.lsdifferId,
      }
      vendingLsdifferDetail(listQuery).then(response => {
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