<template>
 <div class="div-warp">
    <!-- 批量导入 -->
    <el-dialog title="批量导入售卖机"  :visible.sync="dialogFormVisible">
        <el-upload
          class="upload-demo"
          action="https://jsonplaceholder.typicode.com/posts/"
          :on-change="handleChange"
          :file-list="fileList3">
          <el-button size="small" type="primary">点击上传</el-button>
        </el-upload>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="dialogFormVisible = false">确 定</el-button>
        </div>
    </el-dialog>
    <!-- 批量导入 end -->
    <div class="search-warp">
          <el-row :gutter="10">
            <el-col :span="4">
                <el-select v-model="searchObj.districtId" size="small" clearable class="searchInput" placeholder="请选择区域">
                  <el-option
                    v-for="item in regionList"
                    :key="item.districtId"
                    :label="item.name"
                    :value="item.districtId">
                  </el-option>
                </el-select>
            </el-col>
            <el-col :span="4">
              <el-select v-model="searchObj.lineId" class="searchInput" clearable size="small" placeholder="请选择线路">
                <el-option
                v-for="item in lineList"
                  :key="item.lineId"
                  :label="item.name"
                  :value="item.lineId">
                </el-option>
              </el-select>
            </el-col>
            <el-col :span="4">
              <el-select v-model="searchObj.country"  class="searchInput" size="small" clearable @change="getChildData('1')" placeholder="---国家---">
                <el-option
                  v-for="item in cityList.countryData"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
                </el-option>
              </el-select>
            </el-col>
            <el-col :span="4">
              <el-select v-model="searchObj.province"  class="searchInput" clearable size="small" @change="getChildData('2')" placeholder="---省---">
                <el-option
                  v-for="item in cityList.ProvinceData"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
                </el-option>
              </el-select>
            </el-col>
            <el-col :span="4">
                <el-select v-model="searchObj.city"  class="searchInput" size="small" clearable @change="getChildData('3')" placeholder="---市---">
                   <el-option
                    v-for="item in cityList.cityData"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id">
                  </el-option>
                </el-select>
            </el-col>
            <el-col :span="4">
                <el-select v-model="searchObj.district"  class="searchInput" clearable size="small" placeholder="---区县---">
                  <el-option
                    v-for="item in cityList.DistrictData"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id">
                  </el-option>
                </el-select>
            </el-col>
          </el-row>
    </div>
    <div class="search-warp" style="padding-top:0;" >
      <el-row :gutter="10">
            <!-- <el-col :span="4">
                <el-select v-model="searchObj.netSate" class="searchInput" size="small" clearable placeholder="请选择网络状态">
                    <el-option
                      v-for="item in netSateList"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                </el-select>
            </el-col> -->
            <el-col :span="4">
              <el-input placeholder="请输入机器编号" v-model="searchObj.siteCode" size="small" class="searchInput"  clearable>
              </el-input>
            </el-col>
            <el-col :span="4">
              <el-button type="success" size="small" @click.native.prevent="search" v-permission="['sys:vending:site:select']">查询</el-button>
            </el-col>
        </el-row>
    </div>
    <div class="block-warp">
            <div class="bntBox">
              <el-row :gutter="20">
                <el-col :span="5.5">
                  <div class="grid-content bg-purple">
                     <el-radio-group v-model="AtcRadio" size="medium">
                      <el-radio-button label="1" >已认证</el-radio-button>
                      <el-radio-button label="2">未认证</el-radio-button>
                      <el-radio-button label="3">已删除</el-radio-button>
                      <el-radio-button label="4">在线</el-radio-button>
                      <el-radio-button label="5">离线</el-radio-button>
                    </el-radio-group>
                  </div>
                </el-col>
                <el-col :span="15">
                 <div class="operation-box">
                    <el-button type="primary" @click="handleAdd" size="mini" v-permission="['sys:vending:site:add']">新增</el-button>
                    <!-- <el-button type="warning" @click="importBnt" size="mini">批量导入</el-button> -->
                    <el-upload
                      style="display:inline-block; margin:0 8px;"
                      accept=".xls"
                      action="/system/vending/importExcel"
                      :show-file-list="false"
                      multiple
                      :on-success="handleSuccess"
                      >
                      <el-button size="small" type="warning" v-permission="['sys:vending:site:edit']">导入</el-button>
                      
                    </el-upload>
                    <el-button size="small" type="primary" @click="downLoadModel" v-permission="['sys:vending:site:edit']">下载模板</el-button>
                    <el-button size="small" type="warning" @click="exportExcelFile" v-permission="['sys:vending:site:edit']">导出</el-button>
                    <el-button type="danger" v-if="searchObj.curState==1||searchObj.curState==2" size="mini" @click="deleteRow" v-permission="['sys:vending:site:del']">删除机器</el-button>
                    <el-button type="danger" v-else-if="searchObj.curState==3" size="mini" @click="deleteRowDelet" v-permission="['sys:vending:site:del']">删除机器</el-button>


                    <!-- searchObj.curState -->
                  </div>
                </el-col>
              </el-row>
            </div>
            <div class="bntBox">
                  <div class="num-right">
                      <span>所有机器<i>{{VendingNum.allNum}}</i></span>
                      <span>在线机器<i>{{VendingNum.onlineNum}}</i></span>
                      <span>离线机器<i class="Danger">{{VendingNum.outlineNum}}</i></span>
                      <span>未认证<i>{{VendingNum.wzNum}}</i></span>
                      <span>已删除<i>{{VendingNum.delNum}}</i></span>
                  </div>
            </div>
        
             <el-table
                :data="tableData"
                size="small"
                border
                 @selection-change="handleSelectionChange"
               >
                <el-table-column
                  type="selection"
                  width="55">
                </el-table-column>
                <el-table-column
                  prop="siteCode"
                  label="售货机编码"
                  width="200">
                </el-table-column>
                <el-table-column
                  prop="siteName"
                  label="售卖机名称"
                  width="200">
                </el-table-column>
                <el-table-column
                  prop="pointName"
                  label="点位名称"
                  :show-overflow-tooltip="true"
                  width="200">
                </el-table-column>
                <el-table-column
                  prop="lineName"
                  :show-overflow-tooltip="true"
                  label="线路名称"
                  width="200">
                </el-table-column>
                <el-table-column
                  prop="addresses"
                  label="所属行政区"
                  :show-overflow-tooltip="true"
                  width="250">
                </el-table-column>
                <el-table-column
                  prop="cabinetTypeName"
                  label="货柜类型"
                  width="100">
                </el-table-column>
                <el-table-column
                  prop="sellStateName"
                  label="售卖状态"
                  width="100">
                </el-table-column>
               
                <el-table-column
                  prop="netSateName"
                  label="网络状态">
                </el-table-column>
               
                <!-- <el-table-column
                  width="200"
                  label="库存状况"
                  >
                  <template slot-scope="scope">
                    <div v-if="scope.row.pmaxNum==0">
                    <el-progress color="#323232" size="mini"   :stroke-width="18" :percentage="0"></el-progress>
                    </div>
                    <div v-else>
                    <el-progress  size="mini" :stroke-width="12"  :percentage="parseFloat((scope.row.pcurNum/scope.row.pmaxNum).toFixed(1))"></el-progress>
                    </div>
                    
                  </template>
                </el-table-column> 
                <el-table-column
                  fixed="right"
                  label="操作"
                  width="200"> -->
                  <el-table-column
                  align="center"
                  label="操作"
                  width="200">
                  <template slot-scope="scope">
                    <div style="text-align:center;">
                    <el-button type="success" v-if="searchObj.curState!=3" size="mini" icon="el-icon-view" circle  @click.native.prevent="rowClick(scope.row)" v-permission="['sys:vending:site:view']"></el-button>
                    <el-button type="danger" v-if="searchObj.curState==1||searchObj.curState==2" size="mini" icon="el-icon-delete" circle @click.native.prevent="deleteRow(scope.row)" v-permission="['sys:vending:site:del']"></el-button>
                    <el-button type="danger" v-else-if="searchObj.curState==3"  size="mini" icon="el-icon-delete" circle @click.native.prevent="deleteRowDelet(scope.row)" v-permission="['sys:vending:site:del']"></el-button>

                    
                  </div>

                  <!--   <el-button  @click.native.prevent="dialogTableVisible=true" type="primary" size="mini">货道配置</el-button>
                    <el-button  @click.native.prevent="handleAdd_Cabinet" type="success" size="mini">新增挂载机</el-button>
                    <el-button type="warning" size="mini" @click.native.prevent="handleEdit">编辑</el-button> -->
                    
                 </template>
                </el-table-column>
              </el-table>   
            <div class="pageBox">
              <el-pagination
                   background
                 
                  @current-change="handleCurrentChange"
                  layout="prev, pager, next"
                  :page-size="pagesize"
                  :total="totalList">
              </el-pagination>
            </div>
      </div>
  </div>
</template>
<script>
import {getSelectNVendingList,delMachine,getChild,getVendingNum,getVendingLine,getVendingDistrict,delMachineDel,exportVendingExcel,downLoadVendingExcelModel} from '@/api/equipment'
import permission from '@/directive/permission/index.js'
import NProgress from 'nprogress'
export default {
  directives: { permission },
  data() {
        return {
          cityList:{//省市区
               countryData:[],//国家
               ProvinceData:[],//省份列表
               cityData:[],//市
               DistrictData:[]//区县
          },
          regionList:[],//查询区域下拉
          lineList:[],//线路下拉
          spwidth:"120px",
          multipleSelection:'',
          PlistVisible:false,
          isIndeterminate: true,
          checkAll: false,
          checkedCities: [],
          innerVisible:false,
          total:"",
          totalList:null,
          currentPage:1,
          pagesize:10,//每页显示多少条
          searchObj:{
              districtId:"",//区域id
              country:"",//g国
              province:"",//省市区
              city:"",//  市区
              district:"",//  区
              lineId:"",//线:""
              siteCode:"",//售卖机编号
              netSate:"",//网络状态
              curState:"1",//已认证未认证
              

          },
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
          VendingNum:{},
          input:'',
          fileList3: [],
          form:{},
          dialogFormVisible:false,
          dialogTableVisible:false,
          AtcRadio:'1',//1已认证/2未认证/3已删除
          formLabelWidth: '120px',
          dialogAddTableVisible:false,//控制新增弹出显示隐藏
          dialogEditTableVisible:false,//控制编辑弹出显示隐藏
          input10:'',
          tableOption:{
            border: true
          },
          gridData:[],
          filterText: '',
          tableData: []
        }
  },
  watch: {
    AtcRadio(val){
        if(this.AtcRadio<='3'){
          this.searchObj.netSate = ''
          this.searchObj.curState = this.AtcRadio
        }else{
          let netSate = (Number(this.AtcRadio)-4)+'';
          this.searchObj.netSate = netSate;
          this.searchObj.curState = '';
        }
        this.getListData("1")
    }
  },

  methods: {
    handleSuccess(response, file, fileList){
      if(response.zhead.reTCode=="0000"){
        this.$notify({
          title: '成功',
          message: "导入excel成功",
          type: 'success'
        });
        window.location.href=this.ImgUrl+response.zhead.retMsg;
        this.getData("1")
      }else{
          this.$notify({
            title: '失败',
            message: response.zhead.retMsg,
            type: 'error'
          });
       this.getData("1")
      }
    },
    //从首页跳转过来获取是在线还是离线
    getQueryData(){
      this.AtcRadio=this.$route.query.AtcRadio||'1';
    },
    exportExcelFile(){
      var _this=this;
      NProgress.start();
      var para = {
        name:_this.keyword,
      }
      exportVendingExcel(para).then((res) => {
        if(res.zhead.reTCode=="0000"){
          window.location.href=res.zhead.retMsg
        NProgress.done();
        }else{
          this.$notify({
            title: '失败',
            message: res.zhead.retMsg,
            type: 'error'
          });
        }
      });
    },
    downLoadModel(){
      var _this=this;
      NProgress.start();
      var para = {}
      downLoadVendingExcelModel(para).then((res) => {
        if(res.zhead.reTCode=="0000"){
          window.location.href=_this.ImgUrl+res.zhead.retMsg
        NProgress.done();
        }else{
          this.$notify({
            title: '失败',
            message: res.zhead.retMsg,
            type: 'error'
          });
        }
      });
    },
    getVendingNumData(){
      var _this = this;
      const listQuery = {}
      getVendingNum(listQuery).then(response => {
           if(response.zhead.reTCode==="0000"){
            _this.VendingNum=response.zbody.datas
            console.log("概要信息：：",response.zbody.datas)
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
        this.getListData(currentPage)
    },
    rowClick(row){
        this.$router.push(
          {
            path:'/equipment/machine/detail/details',
            query:{logid:row.logid}
          }
        );
    },
    importBnt(){
      this.dialogFormVisible= true
    },
    search(){//查询
      this.getListData("1")
      console.log('---搜索----')
    },
    handleSelectionChange(rows) {


        const _this = this;
        _this.multipleSelection = ""
        if(_this.searchObj.curState==3){
          if (rows) {
              rows.forEach(row => {
                 _this.multipleSelection += row.siteId+ ",";
          
              });
            } 
        }else{
            if (rows) {
              rows.forEach(row => {
                 _this.multipleSelection += row.logid+ ",";
          
              });
            } 
        }
        


    },
    deleteRowDelet( row) {//删除已删除的数据
      var _this = this;
      if(_this.multipleSelection.length==0 && row.bubbles){
          this.$message({
            message: '请选择要删除的数据',
            type: 'warning'
          });
          return false
      }
      this.$confirm('确认提交吗？', '提示', {}).then(() => {
           //rows.splice(index, 1);
                  let para ={
                    ids:_this.multipleSelection==""||undefined ? row.siteId : _this.multipleSelection
                  }
                  delMachineDel(para).then((res) => {
                        if(res.zhead.reTCode=="0000"){
                        NProgress.done();
                        this.$notify({
                          title: '成功',
                          message: res.zhead.retMsg,
                          type: 'success'
                        });
                        _this.getListData("1")
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
    deleteRow( row) {//删除数据
      var _this = this;
      if(_this.multipleSelection.length==0 && row.bubbles){
          this.$message({
            message: '请选择要删除的数据',
            type: 'warning'
          });
          return false
      }
      this.$confirm('确认提交吗？', '提示', {}).then(() => {
           //rows.splice(index, 1);
                  let para ={
                    ids:_this.multipleSelection==""||undefined ? row.logid : _this.multipleSelection
                  }
                  delMachine(para).then((res) => {
                        if(res.zhead.reTCode=="0000"){
                        NProgress.done();
                        this.$notify({
                          title: '成功',
                          message: res.zhead.retMsg,
                          type: 'success'
                        });
                        _this.getListData("1")
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
    handleAdd() {//显示新增窗口
        this.$router.push(
          {path:'/equipment/machine/add'}
        );
    },
    handleAdd_Cabinet() {//新增挂载柜
        this.$router.push(
          {path:'/equipment/machine/add_Cabinet'}
        );
    },
    handleEdit(index, row) {//显示编辑窗口
      this.$router.push(
          {path:'/equipment/machine/edit'}
      );
     //this.editForm = Object.assign({}, row);
    },
    handleEdit_gui(index, row){
      this.$router.push(
          {path:'/equipment/machine/edit_Cabinet'}
      );
    },
    addSubmitForm(formName) {//提交新增窗口
        this.$refs[formName].validate((valid) => {
          if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {


          });
            //this.dialogAddTableVisible = false
          } else {
            console.log('error submit!!');
            return false;
          }
        });
    },
    editSubmitForm(formName) {//提交新增窗口
        this.$refs[formName].validate((valid) => {
          if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {


          });
            //this.dialogAddTableVisible = false
          } else {
            console.log('error submit!!');
            return false;
          }
        });
    },
    resetForm(formName) {//重置表单
        this.$refs[formName].resetFields();
    },
    handleClick(tab, event) {
        console.log(tab, event);
    },
    handleChange(file, fileList) {
      this.fileList3 = fileList.slice(-3);
    },

    getListData(page){
      var _this = this;
      const listQuery ={
        districtId:_this.searchObj.districtId,
        country:_this.searchObj.country,
        province:_this.searchObj.province, 
        searchName:_this.$route.query.keyWord,
        city:_this.searchObj.city,  
        district:_this.searchObj.district,  
        lineId:_this.searchObj.lineId,  
        siteCode:_this.searchObj.siteCode, 
        netSate:_this.searchObj.netSate,
        curState:_this.searchObj.curState, 
        pageSize:_this.pagesize,  
        pageNum:page, 
        orderByColumn:"createtime", 
        isAsc:"desc", 

      } 
      getSelectNVendingList(listQuery).then(response => {
           if(response.zhead.reTCode==="0000"){
            _this.tableData=response.zbody.datas.rows
            _this.totalList = response.zbody.datas.total
            console.log("售卖机数据：：",response.zbody.datas)
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
    // 货到
    handleCheckAllChange(val) {
      this.checkedCities = val ? cityOptions : [];
      this.isIndeterminate = false;
    },
    handleCheckedCitiesChange(value) {
      let checkedCount = value.length;
      this.checkAll = checkedCount === this.cities.length;
      this.isIndeterminate = checkedCount > 0 && checkedCount < this.cities.length;
    },
    getChildData(type){//查询行政区域
        var _this = this;
        let cityId="0"
         if(type =="1"){
            cityId = _this.searchObj.country
         }else if(type =="2"){
           cityId = _this.searchObj.province
         }else if(type == "3"){
           cityId = _this.searchObj.city
         }
        const listQuery = {
          parentId:cityId
        }
        getChild(listQuery).then(response => {
          if(response.zhead.reTCode==="0000"){
              if(type=="1"){
                  _this.cityList.ProvinceData = response.zbody.datas.rows;//省份列表
              }else if(type=="2"){
                 _this.cityList.cityData = response.zbody.datas.rows;//市
              }else if(type=="3"){
                 _this.cityList.DistrictData = response.zbody.datas.rows;//区县
              }else{
                 _this.cityList.countryData=response.zbody.datas.rows;//国家
              }
          console.log("行政区111",_this.cityList)
          }else{
             this.$notify({
                title: '失败',
                message: res.zhead.retMsg,
                type: 'error'
              });
          }

        })
    },
    getVendingDistrictData() {
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
  },

  mounted () {
    // this.getQueryData();
    // this.getVendingLineData()
    this.getVendingDistrictData()
    this.getVendingNumData()
    this.getChildData()
    this.getListData("1")
  },
  components: {
  },
  created (){
    this.getQueryData();
  }
}
</script>
<style>

</style>

<style scoped>
.layerClass{background:rgba(0,0,0,0.2);}
.layerBox{padding:0 30px;}
.operation-box{padding-top:4px;}
.screen-box{padding: 0px 0 20px 0}
.selectBox{padding:10px 0; overflow:hidden}
.selectBox strong{font-size:14px;color:#333; line-height:25px; font-weight: normal; display: inline-block; }
.selectBox .select-left{ padding-left:10px; }
.selectBox .select-l{float:left;}
.tit{font-size:12px; line-height: 30px;}
.num-right{display: inline-block;float: left; margin-top: 11px; font-size:12px;}
.num-right span{ display: inline-block; padding-right: 20px; }
.num-right span i{ display: inline-block; padding-left:10px; font-style:normal; color:#006BC2; }
.num-right span i.Danger{color:#F56C6C;}

.bntBox{overflow: hidden; padding:0px 0 10px 0;}
.div-warp{padding:0 20px;  overflow: hidden;}
.pageBox{background: #fff; padding:20px 0; margin:20px 0;}

.block-top{background: #efefef; overflow: hidden;}
.block-top .delBnt{float: right;  margin-right:4px;}
.block-top .checkbox-left{float: left; margin-top: 4px; margin-left: 4px;}
.imgStyel{width:145px; height:135px; float:left; margin:5px 5px; border:1px solid #dddddd; border-radius:5px; overflow:hidden; padding:5px; }
.imgStyel:hover{border:1px solid #006BC2}
.imgStyel img{width:50px; height:50px; display: block; float:left; margin:5px 0 0 0;}

.layerBox{padding:0 30px; }
  .top-20{ margin-top: 20px; }
  .el-autocomplete{width:100%;}

  .dialog-footer{ padding-left:120px;  }
  .el-dialog__header{border-bottom: 1px solid #000}
  .add-class{width:100%;  text-align: center;}
.icon-size{ font-size: 60px; color:#efefef; margin-top: 20px; cursor: pointer;}
.icon-size:hover{color:#006CC1;}
.layerbottom{text-align: center; overflow: hidden; padding:30px 0; }
.icon-size-circle{ font-size: 60px; color:red; margin-top: 20px;}
.blockBox{ cursor:pointer}
.price {
    font-size: 13px;
    color: #999; margin-top: 10px;
  }
  .price i{ color:#F56C6C; font-size:20px; }
  .titles {
    font-size: 14px; margin-top: 10px;
    color: #333;
  }
.bottom {width:75px;line-height: 12px; float:right; display:inline-block; margin-top:5px;}

.bottom .titels{font-size:12px;color:#333;}
.InputFStyle{width:100%;float:left; margin-top:10px;}
.InputFStyle .i1{width:60px; float:left;}
.InputFStyle .i2{width:60px; float:right;}



.imgStyel1{width:100px;  float:left; margin:5px 5px; cursor:pointer; border:1px solid #dddddd; border-radius:5px; overflow:hidden; padding:5px; }
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