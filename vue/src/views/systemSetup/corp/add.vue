<template>
 <div class="div-warp">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>新增商户</span>
        </div>
        <el-form :model="addForm" :rules="Rules" class="formWidth" ref="addForm" @submit.prevent="onSubmit" >
          <el-form-item label="商户名称" prop="corpName" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.corpName" auto-complete="off" placeholder="请输入1-30位数字、中文或英文字符，示例：耐克"></el-input>
          </el-form-item>
          <el-form-item label="商户Id" prop="corpId" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.corpId" auto-complete="off" placeholder="请输入4位为数字类型的商户Id，示例：8801"></el-input>
          </el-form-item>
          <el-form-item label="负责人" prop="leader" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.leader" auto-complete="off" placeholder="请输入1-30位中文或英文字符，示例：张三"></el-input>
          </el-form-item>
          <el-form-item label="负责人电话" prop="leaderMobile" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.leaderMobile" auto-complete="off" placeholder="请输入11位位数字类型的负责人电话，示例：15327161255"></el-input>
          </el-form-item>
          <el-form-item label="服务电话" prop="tel" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.tel" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="状态" prop="phonenumber" size="small" :label-width="formLabelWidth">
             <el-switch
              v-model="addForm.curState"
              active-value='1'
              inactive-value='2'
              active-color="#13ce66"
              inactive-color="#ff4949">
            </el-switch>
          </el-form-item>
          <el-form-item label="详细地址" prop="address" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.address" auto-complete="off"></el-input>
          </el-form-item>
        </el-form>
        <div class="dialog-footer">
          <el-button type="primary" @click="addSubmitForm('addForm')">确 定</el-button>
        </div>
      </el-card>
  </div>
</template>
<script>
import {AddCorp} from '@/api/systemSetup'
import {getCompany} from '@/api/dictionaries'
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
              corpName:'',
              leader:'',
              leaderMobile: '',
              tel: '',
              curState: '',
              address: '',
              corpId:''
          },
          Rules:{
              corpName:[
                { required: true, message: '商户名称不能为空 ', trigger: 'blur' },
              ],
			  corpId:[
                { required: true, message: '商户Id不能为空 ', trigger: 'blur' },
              ],
              leader:[
                 { required: true, message: '负责人不能为空 ', trigger: 'blur' }
              ],
              leaderMobile:[
                 { required: true, message: '负责人电话不能为空 ', trigger: 'blur' }
              ]
          }
        }
  },
  watch: {
   
  },
  methods: {
    addSubmitForm(formName) {//提交新增窗口
        this.$refs[formName].validate((valid) => {
          if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
                      NProgress.start();
                      let para = Object.assign({}, this.addForm);
                      AddCorp(para).then((res) => {
                            if(res.zhead.reTCode=="0000"){
                            NProgress.done();
                            this.$notify({
                              title: '成功',
                              message: res.zhead.retMsg,
                              type: 'success'
                            });
                            this.$refs['addForm'].resetFields();
                            this.$router.push(
                                  {path:'/systemSetup/corp/index'}
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
  },

  mounted () {
 
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