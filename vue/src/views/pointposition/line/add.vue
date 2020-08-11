<template>
 <div class="div-warp">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>新增线路</span>
        </div>
        <el-form :model="addForm" :rules="Rules" class="formWidth" ref="addForm" @submit.prevent="onSubmit" >
          <el-form-item label="线路编码" prop="code" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.code" auto-complete="off" placeholder="请输入线路编码，示例：429301"></el-input>
          </el-form-item>
          <el-form-item label="线路名称" prop="name" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.name" auto-complete="off" placeholder="请输入线路名称，示例：武大园路营养快线供应线路"></el-input>
          </el-form-item>
          <el-form-item label="区域编号" prop="districtId" size="small" :label-width="formLabelWidth">
              <el-select v-model="addForm.districtId" placeholder="请选择">
                <el-option
                  v-for="item in regionList"
                  :key="item.districtId"
                  :label="item.name"
                  :value="item.districtId">
                </el-option>
              </el-select>
          </el-form-item>
         
          <el-form-item label="描述"  size="small" :label-width="formLabelWidth">
            <el-input type="textarea" v-model="addForm.description"></el-input>
          </el-form-item>
        </el-form>
        <div class="dialog-footer">
          <el-button @click="ReturnLevel">取 消</el-button>
          <el-button type="primary" @click="addSubmitForm('addForm')">确 定</el-button>
        </div>
      </el-card>
  </div>
</template>
<script>
import {addLine,getVendingDistrict} from '@/api/pointposition'
import NProgress from 'nprogress'
export default {
  data() {
        return {
          regionList:[],//区域列表
          restaurants: [],
          companyName: '',
          timeout:  null,
          formLabelWidth: '120px',
          addForm: {
              name:'',
              code:'',
              districtId: '',
              longitude:'',
              latitude:'',
              description: ''
          },
          Rules:{
              code:[
                { required: true, message: '线路编码不能为空 ', trigger: 'blur' },
              ],
              name:[
                 { required: true, message: '线路名称不能为空 ', trigger: 'blur' }
              ],
              districtId:[
                 { required: true, message: '区域编号不能为空 ', trigger: 'blur' }
              ]
          }
        }
  },
  watch: {
   
  },
  methods: {
    ReturnLevel(){
      this.$router.push(
              {path:'/pointposition/line'}
      );
    },
    addSubmitForm(formName) {//提交新增窗口
        this.$refs[formName].validate((valid) => {
          if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
                      NProgress.start();
                      let para = Object.assign({}, this.addForm);
                      addLine(para).then((res) => {
                            if(res.zhead.reTCode=="0000"){
                            NProgress.done();
                            this.$notify({
                              title: '成功',
                              message: res.zhead.retMsg,
                              type: 'success'
                            });
                            this.$refs['addForm'].resetFields();
                            this.$router.push(
                                  {path:'/pointposition/line'}
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
          } else {
            console.log('error submit!!');
            return false;
          }
        });
    },
   
    resetForm(formName) {//重置表单
        this.$refs[formName].resetFields();
    },
    getData() {
      var _this = this;
      this.listLoading = true
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
    }
   
  },

  mounted () {
   this.getData()
 
  },
  components: {

  }
}
</script>
<style scoped>
  .el-autocomplete{width:100%;}
  .div-warp{margin: 20px;}
  .dialog-footer{ padding-left:120px;  }
</style>