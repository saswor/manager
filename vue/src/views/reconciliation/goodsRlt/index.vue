<template>
 <div class="div-warp">
    <el-dialog
      title="对账"
      :visible.sync="dialogVisible"
      :close-on-click-modal="false"
      width="500px"
      >
      <el-form :model="form">
        <el-form-item label="支付类型" :label-width="formLabelWidth">
          <el-select v-model="form.payType" size="small" placeholder="支付类型">
            <el-option label="微信" value="1"></el-option>
            <el-option label="支付宝" value="2"></el-option>
            <el-option label="无" value="3"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="时间" size="small" :label-width="formLabelWidth">
          <el-date-picker
            v-model="form.tradeSTime"
            size="small"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="开始日期"
            style="width:150px;float:left; margin-right:20px;"
            >
          </el-date-picker>
          <el-date-picker
            v-model="form.tradeEtime"
            type="date"
            size="small"
            value-format="yyyy-MM-dd"
            placeholder="结束日期"
            style="width:150px;float:left;"
            >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="导入账单" :label-width="formLabelWidth">
            <el-upload
              class="upload-demo"
              action="/system/statementSupply/importStatement"
              :data="form"
              ref="upload"
              :auto-upload="false"
              size="small"
              :on-preview="handlePreview"
              :on-remove="handleRemove"
              :before-remove="beforeRemove"
              :on-success="upSuccess"
              multiple
              :limit="1"
              :on-exceed="handleExceed"
              :file-list="fileList">
              <el-button size="small"  type="primary">导入账单</el-button>
            </el-upload>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="dialogVisible = false">取 消</el-button>
        <el-button size="small" type="primary" @click="submitUpload">确定对账</el-button>
      </span>
    </el-dialog>
    <div class="search-warp">
                  <el-row :gutter="10">
                    <el-col :span="4">
                          <el-date-picker
                           style="width:100%"
                            v-model="searchObj.tradeSTime"
                            type="date"
                            size="small"
                            value-format="yyyy-MM-dd"
                            clearable
                            placeholder="交易开始时间">
                          </el-date-picker>
                    
                      </el-col>
                     <el-col :span="4">
                          <el-date-picker
                          clearable
                           style="width:100%"
                            v-model="searchObj.tradeEtime"
                            type="date"
                            size="small"
                            value-format="yyyy-MM-dd"
                            placeholder="交易结束时间">
                          </el-date-picker>
                     </el-col>
                    <el-col :span="4">
                          <el-select v-model="searchObj.districtId" clearable size="small"  @change="getVendingLineData" class="searchInput" placeholder="请选择区域">
                            <el-option
                              v-for="item in regionList"
                              :key="item.districtId"
                              :label="item.name"
                              :value="item.districtId">
                            </el-option>
                          </el-select>
                     </el-col>
                    <el-col :span="4">
                          <el-select v-model="searchObj.lineId" clearable size="small"  @change="getVendingData" class="searchInput" placeholder="请选择线路">
                            <el-option
                            v-for="item in lineList"
                              :key="item.lineId"
                              :label="item.name"
                              :value="item.lineId">
                            </el-option>
                          </el-select>
                    </el-col>
                    <el-col :span="4">
                          <el-select v-model="searchObj.siteId"  size="small" clearable class="searchInput" placeholder="请选择售卖机">
                            <el-option
                            v-for="item in vendingList"
                              :key="item.siteId"
                              :label="item.siteName"
                              :value="item.siteId">
                            </el-option>
                          </el-select>
                    </el-col>
                    <el-col :span="4">
                          <el-select v-model="searchObj.statementState" clearable size="small" class="searchInput" placeholder="对账状态">
                            <el-option
                            v-for="item in statementStateList"
                              :key="item.value"
                              :label="item.label"
                              :value="item.value">
                            </el-option>
                          </el-select>
                    </el-col>
                    
                  </el-row>
                  <el-row :gutter="10" style="margin-top:20px;">
                    <el-col :span="24">
                        <el-button type="success" size="small" @click.native.prevent="search" v-permission="['sys:statement:supply:select']">查询</el-button>
                        <el-button type="primary" size="small" @click="dialogVisible = true" v-permission="['sys:statement:supply:edit']">导入账单</el-button>
                         <el-button type="success" size="small" @click="postBalance" v-permission="['sys:statement:supply:edit']">结算</el-button>
                    </el-col>
                   
                  </el-row>
                 

    </div>
    <div class="block-warp">
            <el-table
            :data="tableData"
            @selection-change="handleSelectionChange"
            border
            style="width: 100%">
             <el-table-column
              type="selection"
              width="55">
            </el-table-column>
            <el-table-column
              prop="sorderId"
              :show-overflow-tooltip="true"
              label="补货编号">
            </el-table-column>
            <el-table-column
              prop="pointName"
              :show-overflow-tooltip="true"
              label="点位名称"
              width="200">
            </el-table-column>
            <el-table-column
              prop="siteId"
              :show-overflow-tooltip="true"
              label="售卖机编号">
            </el-table-column>
            <el-table-column
              prop="tradeSTime"
              :show-overflow-tooltip="true"
              label="开始时间">
            </el-table-column>
           
            <el-table-column
              prop="createTime"
              :show-overflow-tooltip="true"
              label="补货时间">
            </el-table-column>
            <el-table-column
              prop="salteStateName"
              :show-overflow-tooltip="true"
              label="销售状态">
              
            </el-table-column>
            
            <el-table-column
              prop="abnomarlNum"
              :show-overflow-tooltip="true"
              label="异常数量">
              <template scope="scope">
                  <div class="state_red" >{{scope.row.abnomarlNum}}</div>
              </template>
            </el-table-column>
            <el-table-column
              prop="abnomarlMoney"
              :show-overflow-tooltip="true"
              label="异常金额">
              <template scope="scope">
              <div class="state_red" >{{scope.row.abnomarlMoney}}</div>
              </template>
            </el-table-column>
            <el-table-column
              fixed="right"
              label="操作"
              width="60">
               <template scope="scope">
                    <el-button type="success" size="mini" icon="el-icon-view" circle  @click.native.prevent="handleSee(scope.row)" v-permission="['sys:statement:supply:view']"></el-button>
                  </template>
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
import { getVendingLine,getVendingDistrict,getVendingPoint,getVending,getWaringSelect } from '@/api/dictionaries'
import { getStatementSupplyList,balance } from '@/api/reconciliation'
import NProgress from 'nprogress'
import wTable from '@/components/table/w-table.vue'
import permission from '@/directive/permission/index.js'
export default {
  directives: { permission },
  data() {
        return {
          multipleSelection:"",
          form:{
            tradeSTime:'',
            tradeEtime:'',
            payType:''
          },
          fileList:[],
          total:1,
          currentPage:1, 
          pagesize:10, 
          regionList:[],//查询区域下拉
          lineList:[],//线路下拉
          pointList:[],//点位下拉
          vendingList:[],//售卖机下拉
          searchObj:{
            tradeSTime:"",
            tradeEtime:"",
            districtId:"",
            lineId:"",
            siteId:"",
            statementState:"",
          },
          dialogFormVisible:false,
          dialogVisible:false,
          statementStateList: [{
            value: '1',
            label: '等待售完'
            }, {
              value: '2',
              label: '等待对账'
            }, {
              value: '3',
              label: '对账正常'
            }],
          formLabelWidth: '120px',
          tableData: [],
       
        }
  },
  watch: {
    filterText(val) {
      this.$refs.tree2.filter(val)
    }
  },
  methods: {
    search(){
      this.getStatementSupplyListData("1")
    },
    handleSelectionChange(rows) {//全选
        const _this = this;
        _this.multipleSelection = ""
        if (rows) {
          rows.forEach(row => {
             _this.multipleSelection += row.logid+ ",";
      
          });
        } 

    },
    reconciliations(row) {
        this.$router.push(
          {
            path:'/reconciliation/goodsRlt/reconciliations',
            query:{
              sorderId:row.sorderId,
              siteId:row.siteId,
              logid:row.logid
          }
          }
        );
    },
    handleSee(row) {
        this.$router.push(
          {
            path:'/reconciliation/goodsRlt/details',
            query:{
              logid:row.logid
          }
          }
        );
    },
    handleSizeChange: function (size) { 
       this.pagesize = size; 
    }, 
    handleCurrentChange: function(currentPage){ 
     this.currentPage = currentPage;
     this.getStatementSupplyListData(currentPage)
    },
    getVendingDistrictData() {//查询所有区域
      var _this = this;
      const listQuery = {
      }
      getVendingDistrict(listQuery).then(response => {
        if(response.zhead.reTCode==="0000"){
        _this.regionList = response.zbody.datas.rows;
        console.log("所有区域",_this.regionList)
        }else{
           this.$notify({
              title: '失败',
              message: res.zhead.retMsg,
              type: 'error'
            });
        }

      })
    },
    getVendingLineData(){//查所有线路
        var _this = this;
        const listQuery = {
          districtId:_this.searchObj.districtId
        }
        getVendingLine(listQuery).then(response => {
          if(response.zhead.reTCode==="0000"){
          _this.lineList = response.zbody.datas.rows;
          console.log("所有线路",_this.lineList)
          }else{
             this.$notify({
                title: '失败',
                message: res.zhead.retMsg,
                type: 'error'
              });
          }

        })
    },
    getVendingData(){//查询售卖机
        var _this = this;
        const listQuery = {
          lineId:_this.searchObj.lineId
        }
        getVending(listQuery).then(response => {
          if(response.zhead.reTCode==="0000"){
          _this.vendingList = response.zbody.datas.rows;
          console.log("售卖机",_this.vendingList)
          }else{
             this.$notify({
                title: '失败',
                message: res.zhead.retMsg,
                type: 'error'
              });
          }

        })
    },
    getStatementSupplyListData(page){//补货对账列表：：：
        var _this = this;
        const listQuery = {
          districtId:_this.searchObj.districtId,
          lineId:_this.searchObj.lineId,
          siteId:_this.searchObj.siteId,
          statementState:_this.searchObj.statementState,
          tradeSTime:_this.searchObj.tradeSTime,
          tradeEtime:_this.searchObj.tradeEtime,
          pageSize:_this.pagesize,
          pageNum:page,
          orderByColumn:"createtime",
          isAsc:'desc'
        }
        getStatementSupplyList(listQuery).then(response => {
          if(response.zhead.reTCode==="0000"){
          _this.total = response.zbody.datas.total; 
          _this.tableData = response.zbody.datas.rows;
          console.log("补货对账列表：：：",_this.tableData)
          }else{
             this.$notify({
                title: '失败',
                message: res.zhead.retMsg,
                type: 'error'
              });
          }

        })
    },
    postBalance(){
        let para = {
          ids:this.multipleSelection
        }
        NProgress.start();
        balance(para).then((res) => {
              if(res.zhead.reTCode=="0000"){
                NProgress.done();
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
    //上传附件的回调
    submitUpload() {
        this.$refs.upload.submit();
    },
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
        if(file.zhead.reTCode=="0000"){
          this.dialogVisible = false
          this.fileList = []
          this.$notify({
            title: '成功',
            message: file.zhead.retMsg,
            type: 'success'
          });
          this.$refs['addForm'].resetFields();
          this.$router.push(
                {path:'/pointposition/area'}
          );
          }else{
            this.$notify({
              title: '失败',
              message: file.zhead.retMsg,
              type: 'error'
            });
        }
    }
  },

  mounted () {
    this.getVendingDistrictData()
    this.getStatementSupplyListData("1")
  },
  components: {
    wTable
  }
}
</script>
<style scoped>
  .state_red{color:red}
  .state_green{color:green}
  .state_black{color:black}
  .state-box{text-align: center; }
  .state-box i{color:#37C448; margin-left:5px;}
  .operation-box{padding-top:4px;}
  .screen-box{padding: 0px 0 20px 0}
  .selectBox{padding:10px 0;}
  .selectBox strong{font-size:14px;color:#333; font-weight: normal; display: inline-block; }
  .selectBox .select-left{ padding-left:10px; }
  .selectBox .inputW{width: 150px; }
  .searchBox{ padding: 10px 0; }
  .searchBox strong{ display: inline-block;float: left; line-height: 35px; font-size:14px; font-weight: normal;}

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