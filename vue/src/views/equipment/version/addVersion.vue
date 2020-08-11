<template>
 <div class="div-warp">       
    <el-form :model="addForm" :rules="Rules"  ref="addForm" @submit.prevent="onSubmit">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>基本信息</span>
        </div>
      
          <el-form-item label="版本号" prop="vName" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.vName" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="类型" prop="uType" size="small" :label-width="formLabelWidth">
            <el-select style="width:215px;"  v-model="addForm.uType" size="small" placeholder="请选择类型">
              <el-option
                key="1"
                label="app升级"
                value="1">
              </el-option>
              <el-option
                key="2"
                label="固件升级"
                value="2">
              </el-option>
            </el-select> 
          </el-form-item>
          <el-form-item label="版本描述" prop="description" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.description" class="inputStyle" ></el-input>
          </el-form-item>
      </el-card>
     <el-card class="box-card top-20"
        v-loading="uploadFlag"
        element-loading-text="上传中">
        <div slot="header" class="clearfix">
          <span>上传文件</span>
        </div>
            <el-upload
            class="upload-demo"
            action="/system/common/versionUpload"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :data="picParam"
            list-type="picture"
            accept=".apk"
            :before-remove="beforeRemove"
            :on-success="upSuccess"
            :on-error="upError"
            :before-upload="beforeUpload"
            multiple
            :on-exceed="handleExceed"
            :show-file-list="false"
            :file-list="fileList">
              <el-button size="mini" type="primary">上传文件</el-button>
            </el-upload>
            <el-table
            :data="addForm.softwareFileList"
            border
            style="margin: 10px">
            <el-table-column
              prop="seqId"
              type="index"
              label="序号"
              width="50">
            </el-table-column>
            <el-table-column
              prop="fileName"
              :show-overflow-tooltip="true"
              label="文件名称">
            </el-table-column>
            <el-table-column
              prop="size"
              :show-overflow-tooltip="true"
              label="文件大小(M)">
            </el-table-column>
            <el-table-column
              prop="fileType"
              :show-overflow-tooltip="true"
              label="文件格式">
            </el-table-column>
            <el-table-column
              fixed="right"
              label="操作"
              width="200">
               <template scope="scope">
                  <el-button type="danger" size="mini" icon="el-icon-delete" circle @click.native.prevent="deleteRow(scope.$index,addForm.softwareFileList)"></el-button>
                </template>
            </el-table-column>
          </el-table>
      </el-card>

      </el-form>

      <div class="bntBox">
          <el-button type="primary" @click="addSubmitForm('addForm')">提交</el-button>
      </div>
       
  </div>
</template>
<script>
import {addVendingVersion} from '@/api/equipment'
import NProgress from 'nprogress'
export default {
  data() {
        return {
          uploadFlag:false,
          picParam:{
            picType:"goods"
          },
          radio:"02",
          picType:"1",
          input10:"",
          tableData3: [],
          multipleSelection: [],
          dialogVisible:false,
          fileList: [],
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
          addForm:{
            vName:"",
            vType:"",
            description:"",
            softwareFileList:[]
          },
          Rules:{
              vName:[
                { required: true, message: ' ', trigger: 'blur' }
            
              ],
              vType:[
                { required: true, message: ' ', trigger: 'blur' }
              ]
          }
        }
  },
  watch: {
   
  },
  created: function() {
     
  },
  methods: {
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
    deleteRow(index,rows){
       this.$confirm('确认删除吗？', '提示', {}).then(() => {
          rows.splice(index, 1);
       })
    },
    addSubmitForm(formName) {//提交新增窗口
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$confirm('确认提交吗？', '提示', {}).then(() => {
                    let para =Object.assign({}, this.addForm);
                    addVendingVersion(para).then((res) => {
                          if(res.zhead.reTCode=="0000"){
                          NProgress.done();
                          this.$notify({
                            title: '成功',
                            message: res.zhead.retMsg,
                            type: 'success'
                          });
                          this.$router.push(
                             {path:'/equipment/version/index'}
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
      // return this.$confirm(`确定移除 ${ file.name }？`);
    },
  
    upSuccess(file, fileList){
      
      if(file.zhead.reTCode=="0000"){
        var file=file.zbody.datas;
        file.size=(file.fileSize/(1024*1024)).toFixed(2);
         this.addForm.softwareFileList.push(file)
       }else{
         this.$message({  
              message: file.zhead.retMsg,  
              type: 'warning'  
          });  
       }
     this.uploadFlag=false;
    },
    upError(){
      this.uploadFlag=false;
    },
    beforeUpload(file){
      var regex=/^[0-9A-Za-z_\-\.]+[\\.]apk$/;
      var fileName=file.name.toLowerCase()
      if(!regex.test(fileName)){
        this.$notify({
          title: '失败',
          message: "文件名只允许数字,字母,小数点,横杠和下划线,且必须是apk文件",
          type: 'warning'
        });
        return false;
      }
      if(file.size>500*1024*1024){
        this.$notify({
          title: '失败',
          message: "上传文件不能大于500m",
          type: 'warning'
        });
        return false;
      }
      this.uploadFlag=true;
    }
  },

  mounted () {

  },
  components: {

  }
}
</script>
<style>
.el-scrollbar{display:block !important}
</style>
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
  .inputStyle{width:214px;}
  .inputDStyle{width:80px; text-align:center}
  .el-autocomplete{width:100%;}
  .div-warp{margin: 20px;}
  .dialog-footer{  }
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