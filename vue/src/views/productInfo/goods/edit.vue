<template>
 <div class="div-warp">
      <!-- 分类 -->
        <el-dialog
          title="商品分类管理"
          :visible.sync="dialogVisible"
          width="400"
          :before-close="handleClose">
          <el-dialog
              width="30%"
              :visible.sync="innerVisibleHide"
              append-to-body>
                <div style="padding-top:30px;">
                    <el-input
                      placeholder="请输入内容"
                      v-model="editFormClassify.classifyName"
                      clearable>
                    </el-input>
                </div>
                <span slot="footer" class="dialog-footer">
                <el-button size="mini" @click="innerVisibleHide = false">取 消</el-button>
                <el-button size="mini" type="primary" @click="editSubmitFormClassify">确 定</el-button>
                </span>
          </el-dialog>
          <el-dialog
              width="30%"
              :visible.sync="addinnerVisibleHide"
              append-to-body>
                <div style="padding-top:10px;">
                    <el-input
                      placeholder="请输入分类关键字"
                      v-model="addFormClassify.classifyName"
                      clearable>
                    </el-input>
                </div>
                <div style="padding-top:10px;">
                    <el-input
                      placeholder="请输入分类描述"
                      v-model="addFormClassify.classifyDesc"
                      clearable>
                    </el-input>
                </div>
                <div style="padding-top:10px;">
                    <el-input
                      placeholder="请输入父级分类标识"
                      v-model="addFormClassify.parentId"
                      clearable>
                    </el-input>
                </div>
                <div style="padding-top:10px;">
                    <el-input
                      placeholder="请输入同级排序"
                      v-model="addFormClassify.sortBy"
                      clearable>
                    </el-input>
                </div>
                <span slot="footer" class="dialog-footer">
                <el-button size="mini" @click="addinnerVisibleHide = false">取 消</el-button>
                <el-button size="mini" type="primary" @click="addSubmitFormClassify">确 定</el-button>
                </span>
          </el-dialog>
          <el-row>
              <el-col :span="12">
                  <el-input
                      placeholder="商品类型名"
                      v-model="input10"
                      size="mini"
                      clearable>
                  </el-input>
              </el-col>
              <el-col :span="1">
                &nbsp;
              </el-col>
              <el-col :span="3"><el-button size="mini" type="success">查询</el-button></el-col>
              <el-col :span="3"><el-button size="mini" type="primary" @click="addinnerVisibleHide=true">新增</el-button></el-col>
              <el-col :span="3"><el-button size="mini" type="danger">删除</el-button></el-col>
          </el-row>
          <el-table
            ref="multipleTable"
            :data="tableData3"
            tooltip-effect="dark"
            style="width: 100%"
            >
               
                <el-table-column
                  label="商品分类名称"
                 >
                  <template slot-scope="scope">{{ scope.row.classifyName }}</template>
                </el-table-column>
                <el-table-column
                  label="操作"
                  width="100">
                      <template  scope="scope">
                          <div>
                              <el-button type="primary" size="mini"icon="el-icon-edit" circle @click.native.prevent="editClassify(scope.$index, scope.row)"></el-button>
                              <el-button type="danger"  size="mini" icon="el-icon-delete" circle @click.native.prevent="deleteRow(scope.row)"></el-button>
                          </div>
                      </template>
                </el-table-column>
          </el-table>
          <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
          </span>
        </el-dialog>
    <!-- 分类 end-->
    <el-form :model="addForm" :rules="Rules"  ref="addForm" @submit.prevent="onSubmit">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>基本信息</span>
        </div>
      
          <el-form-item label="商品编码" prop="productCode" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.productCode" class="inputStyle" placeholder="请输入30位以内的商品编码"></el-input>
          </el-form-item>
          <el-form-item label="商品全名" prop="fullName" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.fullName" class="inputStyle" placeholder="请输入50位以内的商品全名"></el-input>
          </el-form-item>
          <el-form-item label="商品名称" prop="name" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.name" class="inputStyle" placeholder="请输入50位以内的商品名称"></el-input>
          </el-form-item>
          <el-form-item label="分类" prop="typeId" size="small" :label-width="formLabelWidth">
            <el-select v-model="addForm.typeId" size="small" placeholder="请选择分类">
              <el-option
                v-for="item in productClassifyOptions"
                :key="item.classifyId"
                :label="item.classifyName"
                :value="item.classifyId">
              </el-option>
              <span class="editBnt" @click="editGetData">编辑分类</span>
            </el-select>
          </el-form-item>
          <el-form-item label="零售价"  prop="salePrice" size="small" :label-width="formLabelWidth">
            <!-- <el-input v-model="addForm.salePrice" class="inputStyle" > </el-input> -->
            <el-input-number :min="0" style="width:168px;" v-model="addForm.salePrice" :step="1"></el-input-number>
            <el-button>元</el-button>
          </el-form-item>
         <!--  <el-form-item label="成本价" prop="deviceId" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.deviceId" class="inputStyle" ></el-input>
          </el-form-item> -->
          <el-form-item label="包装形式" prop="bagType" size="small" :label-width="formLabelWidth">
            <el-select v-model="addForm.bagType" size="small" placeholder="请选择包装形式">
              <el-option
                key="1"
                label="瓶装"
                value="1">
              </el-option>
              <el-option
                key="2"
                label="罐装"
                value="2">
              </el-option>
              <el-option
                key="3"
                label="袋装"
                value="3">
              </el-option>
              <el-option
                key="4"
                label="盒装"
                value="4">
              </el-option>
              <el-option
                key="5"
                label="杯装"
                value="5">
              </el-option>
            </el-select> 
          </el-form-item>
           <el-form-item label="净含量" prop="spec" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.spec" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="商品图片" size="small" prop="picJson" :label-width="formLabelWidth">



            <el-upload
            class="upload-demo"
            accept=".png"
            action="/system/common/upload"
            list-type="picture"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :before-remove="beforeRemove"
            :on-success="upSuccess"
            :data="picParam"
            multiple
            :limit="1"
            :on-exceed="handleExceed"
            :file-list="fileList">
            <el-button size="mini" type="primary">上传图片</el-button>
            <div slot="tip" class="el-upload__tip">要求图片格式为png，分辨率为140X140，且不超过200kb</div>
          </el-upload>
          </el-form-item>

      </el-card>
     <el-card class="box-card top-20">
        <div slot="header" class="clearfix">
          <span>扩展信息</span>
        </div>
          <el-form-item label="厂家"  size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.factoryName" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="营养成分" size="small" :label-width="formLabelWidth">
            <div v-for="(item,index) in addForm.nutrition" class="nutritionBox">
              <el-input v-model="item.nape" class="stylew" placeholder="项目"></el-input>
              <el-input v-model="item.every" class="stylew" placeholder="每100g"></el-input>
              <el-input v-model="item.value" class="stylew" placeholder="参考值"></el-input>
              <el-button type="danger" @click="deleteNutrition(index)" size="mini" icon="el-icon-minus" circle></el-button>
            </div>
          
            <el-button type="success" @click="addNutrition"  size="mini" icon="el-icon-plus" circle></el-button>
          </el-form-item>
          <el-form-item label="有效天数" @input="handleInputDate" prop="validTime" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.validTime" @input="handleInputDate" class="inputStyle" placeholder="请输入11位以内的整数型有效天数"></el-input>
          </el-form-item>
          <el-form-item label="描述1"  size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.desOne" class="inputStyle" ></el-input>
          </el-form-item>
         <el-form-item label="描述2"  size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.desTwo" class="inputStyle" ></el-input>
          </el-form-item>
          <!-- <el-form-item label="上传附件" prop="laneNum" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.desThree" class="inputStyle" ></el-input>
          </el-form-item> -->
      </el-card>

      </el-form>

      <div class="bntBox">
          <el-button type="primary" @click="addSubmitForm('addForm')">提交</el-button>
      </div>
       
  </div>
</template>
<script>
import { getProductClassifyList ,getMachineSelect} from '@/api/dictionaries'
import {editProductInfo,addProductClassify,getProductClassify,editproductClassify,removeProductClassify,getEditInfo} from '@/api/productInfo'
import NProgress from 'nprogress'
export default {
  data() {
        return {
          picParam:{
            picType:"goods"
          },
          addPicUrl:"",
          radio:"",
          picType:"",
          input10:"",
          tableData3: [],
          multipleSelection: [],
          dialogVisible:false,
          fileList: [],
          ManufactorOptions:[],//厂家列表---下拉
          CabinetOptions:[],//货柜类型---下拉
          modelOptions:[],//机型希腊
          productClassifyOptions:[],//商品分类
          hdVisible:false,
          innerVisible:false,
          innerVisibleHide:false,
          addinnerVisibleHide:false,
          checkAll: false,
          isIndeterminate: true,
          currentDate: new Date(),
          dialogFormVisible:false,
          timeout:  null,
          formLabelWidth: '120px',
          formLabelWidthB: '60px',
          rowValue:0,
          colValue:0,
          editFormClassify:{
            classifyName:''
          },
          addFormClassify:{
            classifyName:'',
            classifyDesc:'',
            parentId:'',
            sortBy:''
          },
          addForm:{
              productCode:"", 
              name:"",  
              fullName:"",    
              typeId:"", 
              salePrice:"", 
              bagType:"",
              spec:"",
              picJson:"",
              factoryName:"",   //  生产厂家
              nutrition:[],    // 营养成分
              validTime:"",     // 过期时间(天)
              desOne:"",      // 描述1 
              desTwo:"",      // 描述1 
              desThree:""   //   描述3(图片地址，上传图片)

          },
          Rules:{
              productCode:[
                { required: true, message: '商品编码不能为空 ', trigger: 'blur' }
            
              ],
              name:[
                { required: true, message: '商品名称不能为空 ', trigger: 'blur' }
              ]
              ,
              fullName:[
                 { required: true, message: '商品全民不能为空 ', trigger: 'blur' }
              ],
              bagType:[
                { required: true, message: '包装形式不能为空 ', trigger: 'blur' }
              ],
              typeId:[
                { required: true, message: '分类不能为空 ', trigger: 'change' }
              ],
              salePrice:[
                { required: true, message: '零售价不能为空 ', trigger: 'blur' }
              ],
              spec:[
                { required: true, message: '净含量不能为空 ', trigger: 'blur' }
              ],
              productImg:[
                { required: true, message: ' ', trigger: 'blur' }
              ],
              picJson:[
                { required: true, message: '必须上传图片', trigger: 'blur' }
              ],
              validTime:[
                { required: true, message: '有效天数不能为空 ', trigger: 'blur' }
              ]
          }
        }
  },
  watch: {
   
  },
  created: function() {
    //  if(window.localStorage){
    //     var addData = JSON.parse(localStorage.getItem("editData"));
    //         addData.nutrition = JSON.parse(addData.nutrition)
    //         this.addForm = addData
    // }
  },
  methods: {
    handleInput(e){
    this.addForm.salePrice=e.replace(/[^\d]/g,'');
    },
    handleInputDate(e){
    this.addForm.validTime=e.replace(/[^\d]/g,'');
    },
    deleteNutrition(row){
      this.addForm.nutrition.splice(row, 1);
    },
    addNutrition(){
      this.addForm.nutrition.push(
          {
            nape:'',
            every:'',
            value:''
          }
        )
    },
    inputFunc(){
        this.addForm.laneNum = this.addForm.row*this.addForm.col
    },
    pzClick(){
      this.rowValue = parseInt(this.addForm.row);
      this.colValue = parseInt(this.addForm.col);
      this.hdVisible =true
    },
    handleClose(){
      this.dialogVisible=false
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
    deleteRow(row) {//删除数据
      const _this  = this;
      this.$confirm('确认提交吗？', '提示', {}).then(() => {
                  let para ={
                    ids:_this.multipleSelection=="" ? row.logid : _this.multipleSelection
                  }
                  removeProductClassify(para).then((res) => {
                        if(res.zhead.reTCode=="0000"){
                        NProgress.done();
                        this.$notify({
                          title: '成功',
                          message: res.zhead.retMsg,
                          type: 'success'
                        });
                        this.editGetData()
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
    editClassify(index,row){
        this.innerVisibleHide=true
        this.editFormClassify.classifyName=row.classifyName
    },
    editSubmitFormClassify() {//编辑新增窗口
        let para =Object.assign({}, this.editFormClassify);
        editproductClassify(para).then((res) => {
              if(res.zhead.reTCode=="0000"){
                  NProgress.done();
                  this.innerVisibleHide=false
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
    addSubmitFormClassify() {//提交新增窗口
        let para =Object.assign({}, this.addFormClassify);
           
        addProductClassify(para).then((res) => {
              if(res.zhead.reTCode=="0000"){
                  NProgress.done();
                  this.addinnerVisibleHide=false
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
    editGetData(){
      var _this = this;
      const listQuery = {
        pageSize:"5100000",
        pageNum:"1",
        orderByColumn:'createtime',
        isAsc:'desc'//desc
      }
      getProductClassify(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
          this.dialogVisible = true;
          NProgress.done();
          _this.total = response.zbody.datas.total;  
          _this.tableData3 = response.zbody.datas.rows;
          console.log("分类：：：",_this.tableData3)
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
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$confirm('确认提交吗？', '提示', {}).then(() => {
                    let para =Object.assign({}, this.addForm);
                        para.picJson = this.addPicUrl
                        para.nutrition= JSON.stringify(para.nutrition)
                    editProductInfo(para).then((res) => {
                          if(res.zhead.reTCode=="0000"){
                          NProgress.done();
                          this.$notify({
                            title: '成功',
                            message: res.zhead.retMsg,
                            type: 'success'
                          });
                          this.fileList = []
                          this.$router.push(
                             {path:'/productInfo/goods/list'}
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
    resetForm(formName) {//重置表单
        this.$refs[formName].resetFields();
    },
    handleSelect(item) {
        console.log(item);
    },
    getProductClassifyOptions() {
      var _this = this;
      const listQuery = {
      }
      getProductClassifyList(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
          NProgress.done();
          _this.productClassifyOptions= response.zbody.datas.rows
          console.log("商品分类：：：：",response.zbody.datas.rows)
         }else{
             this.$notify({
              title: '失败',
              message: res.zhead.retMsg,
              type: 'error'
            });
         }
      })
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
         this.addForm.picJson = file.zbody.datas.pic
          this.addPicUrl = file.zbody.datas.pic
       }else{
         this.fileList=[]
         this.$message({  
              message: file.zhead.retMsg,  
              type: 'warning'  
          });  
       }
    },
    getEdit(){
      var _this = this;
      const listQuery = {
        logid:this.$route.query.logid,
      }
      getEditInfo(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
          NProgress.done();
          response.zbody.datas.nutrition=JSON.parse(response.zbody.datas.nutrition)
          this.addPicUrl = response.zbody.datas.managePic.url;
          response.zbody.datas.managePic.url= _this.ImgUrl+response.zbody.datas.managePic.url
          this.fileList.push(response.zbody.datas.managePic)
          this.fileList[0].url = this.fileList[0].url+"?v="+new Date().getTime()
          _this.addForm = response.zbody.datas;
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
    this.getProductClassifyOptions()
    this.getEdit()
    //this.getMachineSelectList("sys_factory")
  },
  components: {

  }
}
</script>
<style scoped>
.nutritionBox{ overflow:hidden}
.nutritionBox .stylew{width:100px;float:left;}
.editBnt{float: left; line-height:40px; color: #8492a6; font-size: 13px; cursor:pointer; padding-left:20px;}
.icon-size-circle{ font-size: 60px; color:red; margin-top: 50px;}
.pageBox{background: #fff; margin-top: 30px;}
.add-class{width:100%; height: 143px; text-align: center;}
.icon-size{ font-size: 60px; color:#efefef; margin-top: 50px; cursor: pointer;}
.icon-size:hover{color:#006CC1;}
.layerbottom{text-align: center; overflow: hidden; padding:30px 0;}
.input-left{width:70px;float:left;}
.input-right{width:70px;float:right;}
.block-top{background: #efefef; height:30px; line-height:30px; padding-left:10px; font-size:14px;  overflow: hidden;}

/*new css*/
.block-warp{background: #fff; margin: 20px 0; padding:10px; border-radius: 3px;}
.bntBox{overflow: hidden; padding:30px 0; text-align:center; margin-top:20px;}
.div-warp{padding:0 20px;  overflow: hidden;}
.pageBox{background: #fff; padding:20px 0; margin:20px 0;}

.imgStyel{width:80px; height:80px; float:left; margin:5px 5px; border:1px solid #dddddd; border-radius:5px; overflow:hidden; padding:5px; }
.imgStyel:hover{border:1px solid #006BC2}
.imgStyel img{width:50px; height:50px; display: block; float:left; margin:5px 0 0 0;}

.layerBox{padding:0 30px; }
  .top-20{ margin-top: 20px; }
  .inputStyle{width:230px;}
  .inputDStyle{width:80px; text-align:center}
  .el-autocomplete{width:100%;}
  .div-warp{margin: 20px;}
  .dialog-footer{ padding-left:120px;  }
  .searchBox{ padding: 0; }
  .searchBox .inputStyle{width: 200px; display: inline-block;float:left; margin-right: 15px; }
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