<template>
 <div class="div-warp">

   <el-dialog
    title="选择商品"
    :visible.sync="vendingLanepVisible"
    :fullscreen="true">
    
    <!-- 货道配置 -->
          <div class="layerBox">
          
             <el-tabs v-model="activeName" @tab-click="handleClick">
               
                      <el-tab-pane v-for="(item,index) in cabinetInfo" :label="item.cabinetName" :name="item.cabinetId">
                          <!-- 货道 -->
                          <el-card class="box-card top-20" style="overflow:auto">
                           <el-row type="flex" justify="space-between" v-for="row in item.lanes"  >
                              <el-col :span="4" v-for="col in row.cols">
                                  <div class="imgStyel grid-block" @click="selectLanep(col.lanep)">
                                    <!-- 操作区域 -->
                                    <div class="block-top"> 
                                      {{col.laneId}}
                                    </div>
                                    <!-- 添加按钮 -->
                                    <div  class="add-class blockBox" v-if="(col.lanep.laneSate==1||col.lanep.laneSate=='2')&&col.lanep.productId==''">
                                      <i class="el-icon-circle-plus-outline icon-size"  ></i>
                                    </div>
                                    <!-- 禁用 -->
                                    <div  class="add-class blockBox"  v-else-if="col.lanep.laneSate>'2'">
                                        <i class="el-icon-circle-close-outline icon-size-circle"></i>
                                    </div>
                                    <!-- 有商品 -->
                                    <div  class="blockBox" v-else-if="(col.lanep.laneSate==1||col.lanep.laneSate=='2')&&col.lanep.productId!=''">
                                        <img :src="ImgUrl+col.lanep.pic" >
                                        <div class="bottom clearfix">
                                          <div class="titels">{{col.lanep.productName}}</div>
                                          <div class="price"> ¥<i>{{col.lanep.salePrice}}</i>
                                             <span style="float:right; margin-right:5px; color:#333;">库存:{{col.lanep.curCap}}</span>
                                          </div>
                                        </div>
                                        <div class="InputFStyle" >
                                            <el-input v-model="col.lanep.capacity" size="mini" class="i1" placeholder="容量"></el-input>
                                            <el-input v-model="col.lanep.warnCap" size="mini" class="i2" placeholder="阀值"></el-input>
                                        </div>
                                    </div>
                                  </div>  
                              </el-col>
                            </el-row>
                          </el-card>
                      </el-tab-pane>
                 
            </el-tabs>
            
          </div>
    <!-- 货道配置 end -->

  </el-dialog>

  <el-dialog
    title="重新出货"
    :visible.sync="reOrderApplyVisible"
    width="600px">
    
    <el-form :model="addForm" :rules="Rules"  ref="addForm" @submit.prevent="onSubmit">

          <el-form-item label="货道开始号" prop="laneSId" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.laneSId" class="inputStyle" :readonly="true"></el-input>
          </el-form-item>
          <el-form-item label="货道结束号" prop="laneEId" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.laneEId" class="inputStyle" :readonly="true"></el-input>
          </el-form-item>
          <el-form-item label="商品名称" prop="productName" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.productName" class="inputStyle" :readonly="true"></el-input>
          </el-form-item>
           <el-form-item label="最大库存" prop="capacity" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.capacity" class="inputStyle" :readonly="true"></el-input>
          </el-form-item>
          <el-form-item label="阈值" prop="warnCap" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.warnCap" class="inputStyle" :readonly="true"></el-input>
          </el-form-item>
          <el-form-item label="当前库存" prop="curCap" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.curCap" class="inputStyle" :readonly="true"></el-input>
          </el-form-item>
          <el-form-item label="售卖价格" prop="curCap" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.salePrice" class="inputStyle" :readonly="true"></el-input>
          </el-form-item>
          <el-form-item label="货道状态" prop="laneSate" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.laneSate" class="inputStyle" :readonly="true"></el-input>
          </el-form-item>
          <el-form-item label="出货数量" prop="outNum" size="small" :label-width="formLabelWidth">
            <el-input-number :min="1" :max="addForm.curCap" :precision="0" v-model="addForm.outNum" class="inputStyle"></el-input-number>
          </el-form-item>

        <div class="bntBox">
          <el-button type="primary" @click="addSubmitForm('addForm')">提交</el-button>
        </div>
      </el-form>

  </el-dialog>

  <el-dialog
    title="选择售货机"
    :visible.sync="vendingListVisible"
    width="1200px">
    <div class="search-warp">
      <el-row :gutter="10">
        <el-col :span="4">
          <el-select v-model="searchVendingObj.netSate" class="searchInput" size="small" clearable placeholder="请选择网络状态">
            <el-option
              v-for="item in netSateList"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="4">
          <el-input placeholder="请输入售货机名称" v-model="searchVendingObj.siteName" size="small" class="searchInput"  clearable>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="success" size="small" @click.native.prevent="searchVendingLanep">查询</el-button>
        </el-col>
      </el-row>
    </div>
    <div>
      <el-table
        :data="vendingData"
        size="small"
        border
        >
        <el-table-column
          prop="siteId"
          label="售货机编号">
        </el-table-column>
        <el-table-column
          prop="siteName"
          label="售卖机名称">
        </el-table-column>
        <el-table-column
          prop="pointName"
          label="点位名称"
          :show-overflow-tooltip="true">
        </el-table-column>
        <el-table-column
          prop="lineName"
          :show-overflow-tooltip="true"
          label="线路名称">
        </el-table-column>
        <el-table-column
          prop="cabinetTypeName"
          label="货柜类型">
        </el-table-column>
        <el-table-column
          prop="sellStateName"
          label="售卖状态">
        </el-table-column>
        <el-table-column
          prop="netSateName"
          label="网络状态">
        </el-table-column>

          <el-table-column
          align="center"
          label="操作">
          <template slot-scope="scope">
            <div style="text-align:center;">
            <el-button type="success" size="mini"  @click.native.prevent="searchVendingLanep(scope.row)">选择</el-button>       
          </div>
            
          </template>
        </el-table-column>
      </el-table>   
      <div class="pageBox">
        <el-pagination
              background
            @current-change="handleVendingCurrentChange"
            layout="prev, pager, next"
            :page-size="pagesize"
            :total="vendingTotal">
        </el-pagination>
      </div>
    </div>
  </el-dialog>
    <div class="search-warp">
      <el-row :gutter="10">
      <el-col :span="4">
            <el-select v-model="searchObj.districtId" clearable  @change="getVendingLineData" size="small" class="searchInput" placeholder="请选择区域">
              <el-option
                v-for="item in regionList"
                :key="item.districtId"
                :label="item.name"
                :value="item.districtId">
              </el-option>
            </el-select>
      </el-col>
      <el-col :span="4">
            <el-select v-model="searchObj.lineId" size="small" clearable @change="getVendingData" class="searchInput" placeholder="请选择线路">
              <el-option
              v-for="item in lineList"
                :key="item.lineId"
                :label="item.name"
                :value="item.lineId">
              </el-option>
            </el-select>
      </el-col>
      <el-col :span="4">
            <el-select v-model="searchObj.siteId" size="small" clearable class="searchInput" placeholder="请选择售卖机">
              <el-option
              v-for="item in vendingList"
                :key="item.siteId"
                :label="item.siteName"
                :value="item.siteId">
              </el-option>
            </el-select>
      </el-col>
      <el-col :span="4">
            <el-select v-model="searchObj.curState" clearable size="small" class="searchInput" placeholder="订单状态">
              <el-option
              v-for="item in operActionList"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
      </el-col>
      <el-col :span="4">
            <el-select v-model="searchObj.abnomarlState" clearable size="small" class="searchInput" placeholder="异常状态">
              <el-option
              v-for="item in abnomarlStateList"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
      </el-col>
      <el-col :span="4">
        <el-input v-model="searchObj.orderId" size="small" placeholder="订单编号" clearable></el-input>
      </el-col>
    </el-row>
    <el-row :gutter="10">
      <el-col :span="4">
        <el-input v-model="searchObj.pTradeNo" size="small" placeholder="支付流水号" clearable></el-input>
      </el-col>
      <el-col :span="4">
            <el-date-picker
            clearable
              style="width:100%"
              v-model="searchObj.startDate"
              type="date"
              size="small"
              value-format="yyyy-MM-dd"
              placeholder="开始时间">
            </el-date-picker>
        </el-col>
      <el-col :span="4">
            <el-date-picker
            clearable
              style="width:100%"
              v-model="searchObj.endDate"
              type="date"
              size="small"
              value-format="yyyy-MM-dd"
              placeholder="结束时间">
            </el-date-picker>
        </el-col>
      <el-col :span="4">
          <el-button type="success" size="small" @click.native.prevent="search" v-permission="['sys:statement:order:select']">查询</el-button>
          <el-button type="primary" size="small"  @click="exportOrderFile" v-permission="['sys:statement:order:edit']">导出</el-button>
          <el-button type="primary" size="small"  @click="reOrderApply" v-permission="['sys:statement:order:add']">远程出货</el-button>
      </el-col>

      </el-row>
    </div>
    <div class="block-warp">
          <el-table
            :data="tableData"
            border
            style="width: 100%">
            <el-table-column
              prop="orderId"
              :show-overflow-tooltip="true"
              label="订单编号"
              width="130">
            </el-table-column>
            <el-table-column
              prop="pointName"
              :show-overflow-tooltip="true"
              label="点位名称"
              width="180">
            </el-table-column>
            <el-table-column
              prop="pnum"
              :show-overflow-tooltip="true"
              label="商品数量"
              width="90">
            </el-table-column>
            <el-table-column
              prop="salePrice"
              :show-overflow-tooltip="true"
              label="总价"
              width="90">
            </el-table-column>
            <el-table-column
              prop="profitMoney"
              :show-overflow-tooltip="true"
              label="利润"
              width="90">
            </el-table-column>
            <el-table-column
            prop="payTypeName"
              :show-overflow-tooltip="true"
              label="方式"
              width="120">
            </el-table-column>
            <el-table-column
              prop="payStateName"
              :show-overflow-tooltip="true"
              label="支付"
              width="100">
            </el-table-column>
            <el-table-column
              prop="curStateName"
              :show-overflow-tooltip="true"
              label="订单状态"
              width="100">
            </el-table-column>
            <el-table-column
              prop="abnomarlStateName"
              :show-overflow-tooltip="true"
              label="异常状态"
              width="100">
            </el-table-column>
            <el-table-column
              prop="returnTypeName"
              :show-overflow-tooltip="true"
              label="退款状态"
              width="100">
            </el-table-column>
            <el-table-column
              prop="pTradeNo"
              :show-overflow-tooltip="true"
              label="支付流水号">
            </el-table-column>
            <el-table-column
              prop="createTime"
              :show-overflow-tooltip="true"
              label="交易时间"
              width="200">
            </el-table-column>
            <el-table-column
              fixed="right"
              label="操作"
              width="150">
               <template scope="scope">
                    <el-button  size="mini" @click.native.prevent="handleSee(scope.row)" v-permission="['sys:statement:order:view']">查看</el-button>
                    <el-button type="danger" v-if="scope.row.returnType!='2'" size="mini" @click="addReturnMoney(scope.row)" v-permission="['sys:statement:order:edit']">退款</el-button>
                  </template>
            </el-table-column>
          </el-table>
          <div class="pageBox">
            <el-pagination
            v-if="total != 0"
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              background
              :current-page.sync="currentPage"
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
import { getOrderApplyList,returnMoney,exportOrder,reOrderApply} from '@/api/reconciliation'
import { getVendingList } from '@/api/equipment'
import {getCabinetByPointId} from '@/api/replenishment'
import NProgress from 'nprogress'
import wTable from '@/components/table/w-table.vue'
import permission from '@/directive/permission/index.js'
export default {
  directives: { permission },
  data() {
        return {
          total:0,
          currentPage:1, 
          pagesize:10, 
          vendingTotal:0,
          vendingCurrentPage:1, 
          addForm:{},
          vendingData:[],
          regionList:[],//查询区域下拉
          lineList:[],//线路下拉
          pointList:[],//点位下拉
          vendingList:[],//售卖机下拉
          reloadFlag:false,//重复加载标志
          activeName:'',
          cabinetInfo:{},
          searchObj:{
            districtId:"",
            lineId:"",
            siteId:"",
            pointId:"",
            curState:"",
            orderId:"",
            createDate:""
          },
          searchVendingObj:{
            siteName:"",
            netSate:"",
          },
          dialogFormVisible:false,
          vendingListVisible:false,
          vendingLanepVisible:false,
          reOrderApplyVisible:false,
          operActionList: [
            {
              value: '1',
              label: '申请'
            }, 
            {
              value: '2',
              label: '提前出柜'
            }, 
            {
              value: '3',
              label: '已取货'
            }, 
            {
              value: '4',
              label: '已回收'
            }, 
            {
              value: '5',
              label: '取消'
            }, 
           
            ],
          abnomarlStateList: [
            {
              value: '0',
              label: '无'
            }, 
            {
              value: '1',
              label: '取货故障'
            }, 
            {
              value: '2',
              label: '客户取消'
            }, 
            {
              value: '3',
              label: '未取取消'
            },           
            ],
          netSateList:[//网络状态
            {
              value:"0",
              label:"在线"
            },
            {
              value:"1",
              label:"离线"
            }
          ],
          formLabelWidth: '120px',
          tableData: [],
          Rules:{
              outNum:[
                { required: true, message: '出货数量不能为空', trigger: 'change' },
              ]
          }
        }
        
  },
  
  watch: {
    filterText(val) {
      this.$refs.tree2.filter(val)
    }
  },
  methods: {
    addSubmitForm(formName){
      const _this = this;
      this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$confirm('确认提交吗？', '提示', {}).then(() => {
            NProgress.start();
            let para = Object.assign({},_this.addForm)
            reOrderApply(para).then((res) => {
                  if(res.zhead.reTCode=="0000"){
                  NProgress.done();
                  this.$notify({
                    title: '成功',
                    message: res.zhead.retMsg,
                    type: 'success'
                  });
                  _this.getOrderApplyListData("1")
                  }else{
                    this.$notify({
                      title: '失败',
                      message: res.zhead.retMsg,
                      type: 'error'
                    });
                }
            });
          });
        }
      });
      
    },
    handleClick(tab, event) {
      console.log(tab, event);
    },
    selectLanep(lanep){
      if(lanep.laneSate!='1'){
        this.$notify({
            title: '提示',
            message: "只有正常状态的货道才能出货",
            type: 'error'
          });
          return false;
      }
      if(lanep.curCap==0){
        this.$notify({
            title: '提示',
            message: "当前货道库存为0,请选择其他货道",
            type: 'error'
          });
          return false;
      }
      this.addForm=lanep;
      this.reOrderApplyVisible=true;
    },
    //查询售货机货道
    searchVendingLanep(row){
      console.log("点位编号1:"+row.pointId)
      var _this = this;
      const listQuery = {
        pointId:row.pointId,
      }
      console.log("点位编号2:"+row.pointId)
      getCabinetByPointId(listQuery).then(response => {
        if(response.zhead.reTCode==="0000"){
        _this.activeName =response.zbody.datas[0].cabinetId
        _this.cabinetInfo=response.zbody.datas
        _this.vendingLanepVisible=true
        NProgress.done();
        }else{
            this.$notify({
            title: '失败',
            message: res.zhead.retMsg,
            type: 'error'
          });
        }
      })
    },
    //远程出货
    reOrderApply(){
      this.searchVending();
      this.searchVendingObj={
            siteName:"",
            netSate:"",
          }
      this.vendingListVisible=true;
    },
    searchVending(){
      this.getVendingListData("1");
    },
    //查询售货机列表
    getVendingListData(page){
      var _this = this;
      const listQuery ={  
        siteName:_this.searchVendingObj.siteName, 
        netSate:_this.searchVendingObj.netSate,
        curState:'1', 
        pageSize:_this.pagesize,  
        pageNum:page, 
        orderByColumn:"createtime", 
        isAsc:"desc", 

      } 
      getVendingList(listQuery).then(response => {
           if(response.zhead.reTCode==="0000"){
            _this.vendingData = response.zbody.datas.rows
            _this.vendingTotal = response.zbody.datas.total
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
  //从详情页面回退到本页面给参数赋值
  paramInit(){
    var _this=this;
    var temp_districtId=_this.$route.query.districtId;
    if(temp_districtId!=null&&temp_districtId!=undefined&&temp_districtId!=''){
      this.searchObj.districtId=temp_districtId;
    }
    var temp_siteId=_this.$route.query.siteId;
    if(temp_siteId!=null&&temp_siteId!=undefined&&temp_siteId!=''){
      this.searchObj.siteId=temp_siteId;
    }
    var temp_lineId=_this.$route.query.lineId;
    if(temp_lineId!=null&&temp_lineId!=undefined&&temp_lineId!=''){
      this.searchObj.lineId=temp_lineId;
    }
    var temp_curState=_this.$route.query.curState;
    if(temp_curState!=null&&temp_curState!=undefined&&temp_curState!=''){
      // this.searchObj.curState=temp_curState;
      this.$set(_this.searchObj,'curState',temp_curState);
    }
    var temp_abnomarlState=_this.$route.query.abnomarlState;
    if(temp_abnomarlState!=null&&temp_abnomarlState!=undefined&&temp_abnomarlState!=''){
      // this.searchObj.abnomarlState=temp_abnomarlState;
      this.$set(_this.searchObj,'abnomarlState',temp_abnomarlState);
    }
    var temp_startDate=_this.$route.query.startDate;
    if(temp_startDate!=null&&temp_startDate!=undefined&&temp_startDate!=''){
      // this.searchObj.startDate=temp_startDate;
      this.$set(_this.searchObj,'startDate',temp_startDate);
    }
    var temp_endDate=_this.$route.query.endDate;
    if(temp_endDate!=null&&temp_endDate!=undefined&&temp_endDate!=''){
      // this.searchObj.endDate=temp_endDate;
      this.$set(_this.searchObj,'endDate',temp_endDate);
    }
    var temp_reload=_this.$route.query.reload;
    if(temp_reload!=null&&temp_reload!=undefined&&temp_reload!=''){
      this.reloadFlag=temp_reload;
    }
    
    // this.searchObj.lineId=_this.$route.query.lineId;
    // this.searchObj.siteId=_this.$route.query.siteId;
    // this.searchObj.curState=_this.$route.query.curState;
    // this.searchObj.abnomarlState=_this.$route.query.abnomarlState;
    // this.searchObj.startDate=_this.$route.query.startDate;
    // this.searchObj.endDate=_this.$route.query.endDate;
    this.currentPage=Number(_this.$route.query.currentPage)||1;
    //handleCurrentChange(this.currentPage)    
  },
	//导出补货单
	exportOrderFile(){
        let para = {
            orderId:this.searchObj.orderId,
            districtId:this.searchObj.districtId,
            lineId:this.searchObj.lineId,
            siteId:this.searchObj.siteId,
            curState:this.searchObj.curState,
            abnomarlState:this.searchObj.abnomarlState,
            startDate:this.searchObj.startDate,
            endDate:this.searchObj.endDate,
        }
        exportOrder(para).then((res) => {
              if(res.zhead.reTCode=="0000"){
                window.location.href=res.zhead.retMsg
              }else{
                this.$notify({
                  title: '失败',
                  message: res.zhead.retMsg,
                  type: 'error'
                });
            }
        });
    },
    search(){
      this.getOrderApplyListData("1")
    },
    reconciliations(row) {
        this.$router.push(
          {
            path:'/reconciliation/orderRlt/details',
            query:{
              sorderId:row.sorderId,
              siteId:row.siteId,
              logid:row.logid
          }
          }
        );
    },
    handleSee(row) {
        var _this=this
        this.$router.push(
          {
            path:'/reconciliation/orderRlt/details',
            query:{
              orderId:row.orderId,
              districtId:_this.searchObj.districtId,
              lineId:_this.searchObj.lineId,
              siteId:_this.searchObj.siteId,
              curState:_this.searchObj.curState,
              abnomarlState:_this.searchObj.abnomarlState,
              pointId:_this.searchObj.pointId,
              operAction:_this.searchObj.operAction,
              createDate:_this.searchObj.createDate,
              startDate:_this.searchObj.startDate,
              endDate:_this.searchObj.endDate,
              currentPage:_this.currentPage,
              reload:true,
            }
          }
        );
    },
    handleSizeChange: function (size) { 
      this.pagesize = size; 
    }, 
    handleCurrentChange: function(currentPage){ 
     this.currentPage = currentPage;
     this.getOrderApplyListData(currentPage)
    },
    handleVendingSizeChange: function (size) { 
      this.pagesize = size; 
    }, 
    handleVendingCurrentChange: function(currentPage){ 
     this.vendingCurrentPage = currentPage;
     
    },
    getVendingDistrictData() {//查询所有区域
      var _this = this;
      const listQuery = {
      }
      getVendingDistrict(listQuery).then(response => {
        if(response.zhead.reTCode==="0000"){
        _this.regionList = response.zbody.datas.rows;
        console.log("所有区域",_this.regionList)
        //从详情页调回
        if(_this.reloadFlag){
          _this.getVendingLineData(_this.searchObj.districtId)
        }
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
            //从详情页调回
            if(_this.reloadFlag){
              _this.getVendingData(_this.searchObj.lineId)
            }
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
    getOrderApplyListData(page){//订单对账列表：：：
        this.currentPage=page
        var _this = this;
        const listQuery = {
          districtId:_this.searchObj.districtId,
          lineId:_this.searchObj.lineId,
          siteId:_this.searchObj.siteId,
          curState:_this.searchObj.curState,
          abnomarlState:_this.searchObj.abnomarlState,
          pointId:_this.searchObj.pointId,
          operAction:_this.searchObj.operAction,
          orderId:_this.searchObj.orderId,
          pTradeNo:_this.searchObj.pTradeNo,
          createDate:_this.searchObj.createDate,
          startDate:_this.searchObj.startDate,
          endDate:_this.searchObj.endDate,
          pageSize:_this.pagesize,
          pageNum:page,
          // orderByColumn:"abnomarlstate desc,curstate desc,createtime",
          orderByColumn:"createtime",
          isAsc:'desc'
        }
        getOrderApplyList(listQuery).then(response => {
          if(response.zhead.reTCode==="0000"){
          _this.total = response.zbody.datas.total; 
          _this.tableData = response.zbody.datas.rows;
          console.log("订单对账列表：：：",_this.tableData)
          }else{
             this.$notify({
                title: '失败',
                message: res.zhead.retMsg,
                type: 'error'
              });
          }

        })
    },
    addReturnMoney(row) {//确认退款
      const _this = this;
      this.$confirm('确认退款吗？', '提示', {}).then(() => {
                  NProgress.start();
                  let para ={
                    siteId:row.siteId,
                    orderId:row.orderId
                  }
                  returnMoney(para).then((res) => {
                        if(res.zhead.reTCode=="0000"){
                        NProgress.done();
                        this.$notify({
                          title: '成功',
                          message: res.zhead.retMsg,
                          type: 'success'
                        });
                        _this.getOrderApplyListData("1")
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
  },
  
  mounted () {
    var _this=this;
    this.paramInit();
    this.currentPage=Number(_this.$route.query.currentPage)||1;

    this.getVendingDistrictData();
    //   const listQuery = {
    //   }
    //   getVendingDistrict(listQuery).then(response => {
    //     if(response.zhead.reTCode==="0000"){
    //     _this.regionList = response.zbody.datas.rows;
    //     console.log("所有区域",_this.regionList)

    //     var temp_districtId=_this.$route.query.districtId;
    //     if(temp_districtId!=null&&temp_districtId!=undefined&&temp_districtId!=''){
    //     _this.searchObj.districtId=temp_districtId;
    //     _this.getVendingLineData(temp_districtId)
    //     var temp_siteId=_this.$route.query.siteId;
    //     if(temp_siteId!=null&&temp_siteId!=undefined&&temp_siteId!=''){
    //       this.searchObj.siteId=temp_siteId;
    //     }

    // }
    //     }else{
    //        this.$notify({
    //           title: '失败',
    //           message: res.zhead.retMsg,
    //           type: 'error'
    //         });
    //     }

    //   })
    // this.paramInit()
    //handleCurrentChange(this.currentPage)
    this.getOrderApplyListData(this.currentPage)
    //this.handleCurrentChange(this.currentPage)
    console.log("页码:"+this.currentPage)
  },
  created(){
    // var _this=this;
    // this.paramInit();
    // this.currentPage=Number(_this.$route.query.currentPage)||1;
  },
  activated (){
    console.log("缓存加载")
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
  .grid-block{cursor: pointer;}

  .el-row {
    margin-bottom: 5px;
  }

</style>