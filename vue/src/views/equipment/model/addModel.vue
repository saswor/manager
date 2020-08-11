<template>
 <div class="div-warp">
    <el-form :model="addForm" :rules="Rules"  ref="addForm" @submit.prevent="onSubmit">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>新增机型</span>
        </div>

          <el-form-item label="厂家" prop="factoryId" size="small" :label-width="formLabelWidth">
            <el-select v-model="addForm.factoryId" @change="getCabinetType" size="small" placeholder="请选择厂家">
              <el-option
                v-for="item in ManufactorOptions"
                :key="item.dictValue"
                :label="item.dictLabel"
                :value="item.dictValue">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="货柜类型" prop="cabinetType" size="small" :label-width="formLabelWidth">
            <el-select v-model="addForm.cabinetType"  @change="getDeviceByCabinetType" size="small" placeholder="请选择货柜类型">
               <el-option
                  v-for="i in CabinetOptions"
                  :key="i.cabinetType"
                  :label="i.cabinetTypeName"
                  :value="i.cabinetType">
                </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="型号" prop="deviceId" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.deviceId" class="inputStyle" placeholder="请输入30位以内的型号"></el-input>
          </el-form-item>
      </el-card>


      <el-card class="box-card top-20">
        <div slot="header" class="clearfix">
          <span>货道配置</span>
        </div>
        <el-row>
          <el-col :span="8">
          <el-form-item label="行数" prop="row" size="small" :label-width="formLabelWidth">
             <el-input-number v-model="addForm.row" :precision="0" :min="1" :max="9999" class="inputStyle" v-on:input ="inputFunc"></el-input-number>
          </el-form-item>
          <el-form-item label="列数" prop="col" size="small" :label-width="formLabelWidth">
             <el-input-number v-model="addForm.col" :precision="0" :min="1" :max="9999" class="inputStyle" v-on:input ="inputFunc"></el-input-number>
          </el-form-item>
          <el-form-item label="货道数量" prop="laneNum" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.laneNum" class="inputStyle" disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="最大容量" prop="capacity" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.capacity" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="阈值" prop="warnCap" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.warnCap" class="inputStyle" ></el-input>
          </el-form-item>
          <el-button type="primary" size="small" @click="pzClick" style="margin-left:120px;">配置</el-button>
          </el-col>
          <el-col :span="16">
             <!-- 货道配置列表 -->
              <el-row type="flex" justify="center" v-show="hdVisible" v-for="item in rowValue">
                <el-col :span="2" v-for="i in colValue">
                    <div class="imgStyel">
                    </div>
                </el-col>
              </el-row>
            <!-- 货道配置列表 end-->
          </el-col>
        </el-row>
      </el-card>
      </el-form>

      <div class="bntBox">
          <el-button type="primary" @click="addSubmitForm('addForm')">提交</el-button>
      </div>

  </div>
</template>
<script>
import { getMachineSelect,addVendingModel } from '@/api/equipment'
import {selectDeviceByCabinetType,selectCabinetTypeByFactoryId} from '@/api/dictionaries'
import NProgress from 'nprogress'
export default {
  data() {
        return {
          stateOptions:[],//售卖状态--下拉
          networkOptions:[],//网络--下拉
          ManufactorOptions:[],//厂家列表---下拉
          CabinetOptions:[],//货柜类型---下拉
          modelOptions:[],//机型希腊
          hdVisible:false,
          innerVisible:false,
          checkAll: false,
          isIndeterminate: true,
          currentDate: new Date(),
          dialogFormVisible:false,
          timeout:  null,
          formLabelWidth: '120px',
          formLabelWidthB: '60px',
          rowValue:0,
          colValue:0,
          addForm: {
              factoryId:"", //是 varchar 厂家编号
              deviceId:"",  //是 varchar 机型编码
              cabinetType:"",    //货柜类型
              startId:"", //是 varchar 放 起始编号
              row:"", //是 varchar 平 行数
              col:"", //是 varchar    列数
              laneNum:"" //是 varchar    总货道数(根据行、列、排、货道自动计算)
          },
          Rules:{
              factoryId:[
                { required: true, message: '厂家不能为空 ', trigger: 'blur' }

              ],
              cabinetType:[
                 { required: true, message: '货柜类型不能为空 ', trigger: 'change' }
              ],
              deviceId:[
                { required: true, message: '型号不能为空 ', trigger: 'blur' }
              ]
              ,
              row:[
                { required: true, message: '行数不能为空 ', trigger: 'blur' }
              ],
              col:[
                { required: true, message: '列数不能为空 ', trigger: 'blur' }
              ]
          }
        }
  },
  watch: {

  },
  created: function() {

  },
  methods: {
    inputFunc(){
        this.addForm.laneNum = this.addForm.row*this.addForm.col
    },
    pzClick(){
      this.rowValue = parseInt(this.addForm.row);
      this.colValue = parseInt(this.addForm.col);
      this.hdVisible =true
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
    addSubmitForm(formName) {//提交新增窗口
        this.$refs[formName].validate((valid) => {
          if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
                  let para =Object.assign({}, this.addForm);
                  addVendingModel(para).then((res) => {
                        if(res.zhead.reTCode=="0000"){
                        NProgress.done();
                        this.$notify({
                          title: '成功',
                          message: res.zhead.retMsg,
                          type: 'success'
                        });
                        this.$router.push(
                           {path:'/model/index'}
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
            _this.ManufactorOptions= response.zbody.datas.rows;//厂家列表---下拉
             console.log("厂家列表：：",_this.ManufactorOptions)
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
    getCabinetType(factoryId){
      var _this = this;
      const listQuery = {
        factoryId:factoryId
      }
      selectCabinetTypeByFactoryId(listQuery).then(response => {
           if(response.zhead.reTCode==="0000"){
            debugger
            _this.CabinetOptions=response.zbody.datas
            _this.addForm.cabinetType="";
            _this.addForm.deviceId="";
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
            _this.modelOptions=response.zbody.datas
            _this.addForm.deviceId="";
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
    this.getMachineSelectList("sys_factory")
  },
  components: {

  }
}
</script>
<style scoped>
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

.imgStyel{width:50px; height:50px; float:left; margin:5px 5px; border:1px solid #dddddd; border-radius:2px; overflow:hidden; padding:5px; background:#006BC2 }
.imgStyel:hover{border:1px solid #006BC2}


.layerBox{padding:0 30px; }
  .top-20{ margin-top: 20px; }
  .inputStyle{width:208px;}
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
