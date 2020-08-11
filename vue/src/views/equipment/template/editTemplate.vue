<template>
 <div class="div-warp">
      <!-- 商品列表 -->
          <el-dialog
            width="920px"
            title="商品列表"
            :visible.sync="planesVisible"
            >

            <div class="searchBox">
              
                 
                <el-input placeholder="请输入商品名称" v-model="productName" style="width:150px;" size="mini" clearable>
                  </el-input>
                  <el-button type="success" @click.native.prevent="productSearch" size="mini">查询</el-button>
            </div>

            <el-row :gutter="24" style="margin-top: 10px">
              <el-col :span="24" style="margin-top: 20px;" >
                  <div class="productBox" v-for="o in cocmodityList" @click="selectGoods(o)">
                    <div class="Pic-box">
                    <img :src="ImgUrl+o.pic" >
                    </div>
                    <div class="bottom1">
                      <div class="titles1">{{o.name}}</div>
                      <div class="price1">售价：¥<i>{{o.salePrice}}</i></div>
                    </div>
                  </div>
                 
             
              </el-col>
            </el-row>
             <div class="pageBox">
              <el-pagination
                @current-change="handleCurrentChange"
                background
                :page-size="pagesize"
                layout="prev, pager, next"
                :total="p_total">
              </el-pagination>
            </div>
          </el-dialog>
      <!-- 商品列表 end -->
      <!-- 关联售卖机弹出  -->
        <el-dialog
          :visible.sync="dialogFormVisible"
          width="63%"
          :before-close="handleClose">
                <!-- <div class="searchBox" style="margin-bottom: 20px;">
                    <el-select v-model="pointId" class="inputStyle" placeholder="请选择区域" size="mini" >
                      <el-option
                        v-for="item in regionList"
                        :key="item.districtId"
                        :label="item.name"
                        :value="item.districtId">
                      </el-option>
                    </el-select>
                    <el-button type="success" @click.native.prevent="search" size="mini">查询</el-button>
                </div> -->
                <el-table
                    :data="VendingList"
                    stripe
                    style="width: 100%">
                    <el-table-column type="expand">
                      <template slot-scope="props">
                        <div >
                          <el-table
                            :data="props.row.cabinets"
                            stripe
                            style="width: 100%">
                            <el-table-column
                              prop="cabinetId"
                              label="编号"
                              >
                            </el-table-column>
                            <el-table-column
                              prop="cabinetName"
                              label="挂载柜"
                             >
                            </el-table-column>
                            <el-table-column
                              prop="cabinetType"
                              >
                                <template scope="scope">
                                  <el-button
                                    @click.native.prevent="guan(scope.$index, scope.row)"
                                    type="primary"
                                    size="small">
                                    关联
                                  </el-button>
                                </template>

                            </el-table-column>
                          </el-table>
                        </div>
                      </template>
                    </el-table-column>
                    <el-table-column
                      prop="siteId"
                      label="售卖机编号"
                      width="120">
                    </el-table-column>
                    <el-table-column
                      prop="siteName"
                      label="售卖机名称"
                      :show-overflow-tooltip="true"
                      width="100">
                    </el-table-column>
                    <el-table-column
                      prop="lineName"
                      :show-overflow-tooltip="true"
                      label="线路名称">
                    </el-table-column>

                    <el-table-column
                      prop="pointId"
                      :show-overflow-tooltip="true"
                      label="区域名称">
                    </el-table-column>
                    <el-table-column
                      prop="address"
                      :show-overflow-tooltip="true"
                      label="所属行政区">
                    </el-table-column>
                    <el-table-column
                      prop="createTime"
                      label="创建时间">
                    </el-table-column>
                    
              </el-table>
              <!-- <div style="text-align: center; margin-top: 40px;">
                <el-pagination
                  background
                  layout="prev, pager, next"
                  :total="1000">
                </el-pagination>
              </div> -->
        </el-dialog>
      <!-- 关联售卖机弹出  end-->

      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>编辑模版</span>
        </div>
         <el-form :model="editForm"  :rules="Rules"  ref="editForm" @submit.prevent="onSubmit">
              <el-form-item label="模版名称" prop="name" size="small" :label-width="formLabelWidth">
                <el-input v-model="editForm.name" class="inputStyle" placeholder="请输入50位以内的模板名称"></el-input>
              </el-form-item>
              <el-form-item label="厂家" prop="factoryId" size="small" :label-width="formLabelWidth">
                 <el-select v-model="editForm.factoryId" disabled @change="getCabinetType(editForm.factoryId,'slect')" size="small" placeholder="请选择厂家">
                    <el-option
                      v-for="item in ManufactorOptions"
                      :key="item.dictValue"
                      :label="item.dictLabel"
                      :value="item.dictValue">
                    </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="货柜类型" prop="cabinetType" size="small" :label-width="formLabelWidth">
                <el-select v-model="editForm.cabinetType" disabled @change="getDeviceByCabinetType" size="small" placeholder="请选择货柜类型">
                   <el-option
                      v-for="item in CabinetOptions"
                      :key="item.cabinetType"
                      :label="item.cabinetTypeName"
                      :value="item.cabinetType">
                    </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="型号" prop="deviceId" size="small" :label-width="formLabelWidth">
                  <el-select  v-model="editForm.device" disabled  @change="changeDev" size="small" placeholder="请选择型号">
                      <el-option
                        v-for="i in modelList"
                        :key="i.deviceCode"
                        :label="i.deviceId"
                        :value="i.deviceCode">
                      </el-option>
                  </el-select>
              </el-form-item>
        </el-form>
     
      </el-card>
      <el-card class="box-card top-20" style="overflow:auto">
        <!-- 货道配置列表 -->
        <el-checkbox-group v-model="checkedCities" v-show="hdVisible"  @change="handleCheckedCitiesChange">
                  <el-row type="flex" :gutter="10" justify="left" align="middle" v-for="(row,index) in editForm.lanes"  >
                    <el-col v-for="col in row.cols">
                        <div class="imgStyel" >

                          <!-- 操作区域 -->
                          <div class="block-top"> 
                             <el-checkbox :label="col" :key="col.laneId" class="checkbox-left">{{col.laneId}}</el-checkbox>
                            <el-button type="text" class="delBnt" @click="del_hd(col)" size="mini">删除</el-button>
                          </div>
                        
                          <!-- 添加按钮 -->
                          <div  class="add-class blockBox" v-if="col.lanep.laneSate==1&&col.lanep.productId==''">


                            <i class="el-icon-circle-plus-outline icon-size"  @click="addProduct(col)" ></i>
                          </div>

                          <!-- 禁用 -->
                          <div  class="add-class blockBox"  v-else-if="col.lanep.laneSate==2">
                              <i class="el-icon-circle-close-outline icon-size-circle"></i>
                          </div>


                          <!-- 有商品 -->
                          <div  class="blockBox" v-else>
                              <img :src="ImgUrl+col.lanep.pic" >
                              <div class="bottom clearfix">
                                <div class="titels">{{col.lanep.productName}}</div>
                                <div class="price"> ¥<i>{{col.lanep.salePrice}}</i><em>库存{{col.lanep.curCap}}</em></div>
                              </div>
                              <div class="InputFStyle" >
                                  <el-input v-model="col.lanep.capacity" size="mini" class="i1" placeholder="容量"></el-input>
                                  <el-input v-model="col.lanep.warnCap" size="mini" class="i2" placeholder="阀值"></el-input>
                              </div>
                          </div>
                        </div>  
                    </el-col>
                  </el-row>
        </el-checkbox-group>
        <!-- 货道配置列表 end-->
      </el-card>
      <div class="bntBox">
          <!-- <el-checkbox :indeterminate="isIndeterminate" style="float:left; margin-top: 10px;" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox> -->
          <el-button @click="enableUp(item)">启用</el-button>
          <el-button type="primary" @click="disableUp(item)">禁用</el-button>
          <el-button type="primary" @click="dialogFormVisible=true">关联售卖机</el-button>
          <el-button type="primary" @click="addSubmitForm('editForm')">保存</el-button>
          <el-button type="primary" @click="handleExport">导出</el-button>
      </div>
       
  </div>
</template>
<script>
import {getVendingModelList,getVendingPconfig,getCommodity,getLaneList,editVendingPconfig,vendingPconfigGet,selectRelationVending,exportVendingPconfig} from '@/api/equipment'
import {getVending,getVendingDistrict,selectDeviceByCabinetType,selectCabinetTypeByFactoryId,getMachineSelect} from '@/api/dictionaries'
import NProgress from 'nprogress'

export default {
  data() {
        return {
          productName:'',
          VendingList:[],
          p_total:'',
          pagesize:40,
          currentPage:1,
          cocmodityList:[],//商品列表
          planesVisible:false,//货到显示隐藏
          showPlanes:1,
          hdVisible:true,
          activeName: 'first',
          innerVisible:false,
          ManufactorOptions:[],//厂家
          CabinetOptions:[],//型号
          modelOptions:[],//货柜类型
          modelList:[],//型号

          rows:0,
          cols:[],
          isTable:"",
          value:"",
          checkAll: false,
          checkedCities: [],
          isIndeterminate: true,
          currentDate: new Date(),
          dialogFormVisible:false,
          radio10: '1',
          restaurants: [],
          options:[],
          input10:"",
          state4: '',
          timeout:  null,
          formLabelWidth: '120px',
          editForm: {
              cabinetType:'',
              deviceId:'',
              factoryId:'',
              name: '',
              lanes:[],
              cabs:""
          },
          deviceId:"",
          name:"",
          Rules:{
             deviceId:[
                { required: true, message: ' ', trigger: 'change' }
              
              ],
              cabinetType:[
                { required: true, message: ' ', trigger: 'change' }
              
              ]
              ,
              factoryId:[
                 { required: true, message: ' ', trigger: 'change' }
              ],
              name:[
                { required: true, message: '模板名称不能为空 ', trigger: 'change' }
               
              ]
          },
          hd_id:"",
          lineData:{
            factoryName: "",
            fullName: "",
            logid: "",
            name: "",
            pic: "",
            productCode: "",
            productId: "",
            remark: null,
            salePrice: "",
            laneSate:1
        
          },
          guanArr:[]
        }
  },
  watch: {
   
  },
  created() {
   
  },
  methods: {
    //导出
    handleExport(){
      var _this=this;
      let para = {
            mConfigId:this.editForm.mconfigId,
        }
        exportVendingPconfig(para).then((res) => {
              if(res.zhead.reTCode=="0000"){
                window.location.href=_this.ImgUrl+res.zhead.retMsg
              }else{
                this.$notify({
                  title: '失败',
                  message: res.zhead.retMsg,
                  type: 'error'
                });
            }
        });
    },
    enableUp(row){//启用
        var _this = this;
        this.editForm.lanes.forEach(function(row){
            for(var i =0; i<row.cols.length; i++){
              for(var j=0; j<_this.checkedCities.length; j++){
                    if(_this.checkedCities[j].laneId==row.cols[i].laneId){
                        row.cols[i].lanep.laneSate= "1"
                        _this.checkedCities[j].lanep.laneSate = "1"
                        _this.checkedCities.splice(j--,1)
                    }
              }
            }
        })
    },
    disableUp(row){//禁用
        var _this = this;
        _this.editForm.lanes.forEach(function(row){
            for(var i =0; i<row.cols.length; i++){
              for(var j=0; j<_this.checkedCities.length; j++){
                    if(_this.checkedCities[j].laneId==row.cols[i].laneId){
                    
                        row.cols[i].lanep.laneSate = "2"
                      
                    }
              }
            }
        })
        _this.checkedCities=[]
    },
    addProduct(row){
      this.hd_id=row.laneCode
      this.planesVisible=true
    },
    selectGoods(data,row){
          var _this = this;
   
          _this.editForm.lanes.forEach(function(row,index){
              for(var i = 0; i<row.cols.length; i++){
                  if(_this.hd_id == row.cols[i].laneId){
                      // data.laneSate = 3
                      // row.cols[i].lanep = data
                      row.cols[i].lanep.productName = data.name;
                      row.cols[i].lanep.productId=data.productId;
                      row.cols[i].lanep.productPic = data.picJson;
                      row.cols[i].lanep.salePrice=data.salePrice;
                      row.cols[i].lanep.pic=data.pic;
                      row.cols[i].lanep.pStateTime=data.validTime;
                      row.cols[i].lanep.capacity="";
                      row.cols[i].lanep.warnCap="";
                      row.cols[i].lanep.laneSate =1;
                      return
                  }
              }
          })
          this.planesVisible = false;
    },
    del_hd(data,c_type){//删除本地货道商品
      var _this = this;
      //data.lanep = _this.lineData
      data.lanep={
            factoryName: "",
            fullName: "",
            logid: "",
            name: "",
            pic: "",
            productCode: "",
            productId: "",
            remark: null,
            salePrice: "",
            laneSate:1
        
          }
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done();
        })
        .catch(_ => {});
    },
    handleClick(tab, event) {
        console.log(tab, event);
    },
    handleCheckAllChange(val) {
      this.checkedCities = val ? cityOptions : [];
      this.isIndeterminate = false;
    },
    handleCheckedCitiesChange(value) {
      let checkedCount = value.length;
      this.checkAll = checkedCount === this.cities.length;
      this.isIndeterminate = checkedCount > 0 && checkedCount < this.cities.length;
    },
    changeDev(deviceId){
      this.getLaneListData(deviceId)
    },
    //根据机型code构造货道接口
    getLaneListData(deviceId) {
        var _this = this;
        const listQuery = {
          deviceCode:deviceId
        }
        getLaneList(listQuery).then(response => {
           if(response.zhead.reTCode==="0000"){
            _this.editForm.lanes=response.zbody.datas.lanes
            //this.editForm.name=_this.name
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
    handleCurrentChange: function(currentPage){ 
     this.currentPage = currentPage;
     this.getCommodityList(currentPage)
    },
    productSearch(){
      this.getCommodityList("1")
    },
    //查询商品列表
    getCommodityList(page){
      var _this = this;
      const listQuery = {
        typeId:'',
        name:_this.productName,
        productCode:'',
        pageSize:_this.pagesize,
        pageNum:page,
        orderByColumn:'createtime',
        isAsc:'desc'
      }
      getCommodity(listQuery).then(response => {
           if(response.zhead.reTCode==="0000"){
            _this.cocmodityList=response.zbody.datas.rows
            _this.p_total = response.zbody.datas.total
            console.log("商品列表：：",response.zbody.datas)
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
    addSubmitForm(formName) {//提交新增窗口
        var _this =this;
        this.$refs[formName].validate((valid) => {
          if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
               NProgress.start();
                      let para = Object.assign({}, this.editForm);
                      para.cabs =_this.guanArr.join(',')
                      //para.lanes = this.checkedCities
                      editVendingPconfig(para).then((res) => {
                            if(res.zhead.reTCode=="0000"){
                            NProgress.done();
                            this.$notify({
                              title: '成功',
                              message: res.zhead.retMsg,
                              type: 'success'
                            });
                            this.$refs['editForm'].resetFields();
                            this.$router.push(
                                  {path:'/template/index'}
                            );
                            }else{
                              this.$notify({
                                title: '失败',
                                message: res.zhead.retMsg,
                                type: 'error'
                              });
                          }
                      });
          });
            //this.dialogAddTableVisible = false
          } else {
            console.log('error submit!!');
            return false;
          }
        });
    },
    guan(indexr,row){
      const _this = this;
      this.$confirm('确认关联吗？', '提示', {}).then(() => {
          _this.guanArr.push(row.cabinetId)
          _this.$notify({
            title: '成功',
            message: "关联成功",
            type: 'success'
          });
      })
    },
    //查售卖机
    getVendingList(){
      var _this = this;
      const listQuery = {
        cabinetType:"01",//_this.addForm.cabinetType,
        name:"",
      }
      selectRelationVending(listQuery).then(response => {
           if(response.zhead.reTCode==="0000"){
            _this.VendingList=response.zbody.datas.rows
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
    //查询编辑信息
    getCommodityListdata(){
      var _this = this;
      const listQuery = {
        logid:_this.$route.query.logid
      }
      vendingPconfigGet(listQuery).then(response => {
           if(response.zhead.reTCode==="0000"){
            _this.editForm = response.zbody.datas

            _this.getCabinetType(response.zbody.datas.factoryId,"edit")
            //_this.deviceId = _this.editForm.deviceId
            //_this.name = _this.editForm.name
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
    //查询厂家
    getMachineSelectList(type) {
      var _this = this;
      const listQuery = {
        dictType:type,
      }
      getMachineSelect(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
          NProgress.done();
          if(type==="sys_factory")
          {
            _this.ManufactorOptions= response.zbody.datas.rows;
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
    getCabinetType(factoryId,type){
      var _this = this;
      const listQuery = {
        factoryId:factoryId
      }
      selectCabinetTypeByFactoryId(listQuery).then(response => {
           if(response.zhead.reTCode==="0000"){
            _this.CabinetOptions=response.zbody.datas
            if(type!="edit"){
              _this.editForm.cabinetType="";
              _this.editForm.deviceId="";
            }
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
    getDeviceByCabinetType(cabinetType){
      var _this = this;
      const listQuery = {
        cabinetType:cabinetType
      }
      selectDeviceByCabinetType(listQuery).then(response => {
           if(response.zhead.reTCode==="0000"){
            _this.modelList=response.zbody.datas
         
            _this.editForm.deviceId="";
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
 
  },

  mounted () {
   this.getVendingList()
   this.getCommodityList("1")
   this.getCommodityListdata()
   this.getMachineSelectList("sys_factory")
 
   // this.getLaneListData(this.editForm.deviceId) 
  },
  components: {

  }
}
</script>
<style scoped>
.pageBox{background: #fff; margin-top: 30px;}
.layerbottom{text-align: center; overflow: hidden; padding:30px 0;}
/*new css*/
.block-warp{background: #fff; margin: 20px 0; padding:10px; border-radius: 3px;}
.bntBox{overflow: hidden; padding:30px 0; text-align:center;}
.div-warp{padding:0 20px;  overflow: hidden;}
.pageBox{background: #fff; padding:20px 0; margin:20px 0;}


.layerBox{padding:0 30px; }
  .top-20{ margin-top: 20px; }
  .inputStyle{width:208px;}
  .el-autocomplete{width:100%;}
  .div-warp{margin: 20px;}
  .dialog-footer{ padding-left:120px;  }
  .searchBox{ padding: 0; }
  .searchBox .inputStyle{width: 200px; display: inline-block;float:left; margin-right: 15px; }
  .el-dialog__header{border-bottom: 1px solid #000}
.layerbottom{text-align: center; overflow: hidden; padding:30px 0; }
.blockBox{ cursor:pointer}


.imgStyel1{width:100px;  float:left; margin:5px 5px; cursor:pointer; border:1px solid #dddddd; border-radius:5px; overflow:hidden; padding:5px; }
.imgStyel1 .Pic-box{height:60px; overflow:hidden;}
.imgStyel1 img{width:100%; display: block; float:left; }

.bottom1 {line-height: 12px; float:right; display:inline-block;}

.bottom1 .titels1{font-size:12px;color:#333;}

.price1 {
    font-size: 13px;
    color: #999; margin-top: 10px;
  }
  .price1 i{ color:#F56C6C; font-size:20px; }
  .titles1 {
    font-size: 14px; margin-top: 10px;
    color: #333;
  }
  .Bnt-footer{padding:10px 0 0 0;}
</style>