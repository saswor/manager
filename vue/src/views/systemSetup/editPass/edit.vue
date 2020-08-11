<template>
 <div class="div-warp">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>修改密码</span>
        </div>
        <el-form :model="addForm" :rules="Rules" class="formWidth" ref="addForm" @submit.prevent="onSubmit" >
          <el-form-item label="新密码" prop="password" size="small" :label-width="formLabelWidth">
            <el-input type="password" v-model="addForm.password" auto-complete="off" placeholder="请输入由6-12位的字母和数字组成的密码"></el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop="okpassword" size="small" :label-width="formLabelWidth">
            <el-input type="password" v-model="addForm.okpassword" auto-complete="off"></el-input>
          </el-form-item>
         
        </el-form>
        <div class="dialog-footer">
          <el-button type="primary" @click="addSubmitForm('addForm')">确 定</el-button>
        </div>
      </el-card>
  </div>
</template>
<script>
import {resetPwd} from '@/api/systemSetup'
import NProgress from 'nprogress'
export default {
  data() {
      var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'));
        } else {
          if (this.addForm.okpassword !== '') {
            this.$refs.addForm.validateField('okpassword');
          }
          callback();
        }
      };
      var validatePass2 = (rule, value, callback) => {
          if (value === '') {
            callback(new Error('请再次输入密码'));
          } else if (value !== this.addForm.password) {
            callback(new Error('两次输入密码不一致!'));
          } else {
            callback();
          }
        };
        return {
          regionList:[],//区域列表
          restaurants: [],
          companyName: '',
          roles:[],
          timeout:  null,
          formLabelWidth: '120px',
          addForm: {
              okpassword:"",
              password: ''
          },
          Rules:{
              password: [
                { validator: validatePass, trigger: 'blur' },
                { min: 6, max: 12, message: '长度在 6 到 12 个字符', trigger: 'blur' }
              ],
              okpassword: [
                { validator: validatePass2, trigger: 'blur' },
                { min: 6, max: 12, message: '长度在 6 到 12 个字符', trigger: 'blur' }
              ]
          }
        }
  },
  watch: {
   
  },
  created() {
    
   
    
   
  },
  methods: {
    addSubmitForm(formName) {//提交新增窗口
    var _this=this;
        this.$refs[formName].validate((valid) => {
          if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
                      NProgress.start();
                      let para = {
                        userId:_this.$route.query.userId,
                        password:_this.addForm.password
                      }
                      resetPwd(para).then((res) => {
                            if(res.zhead.reTCode=="0000"){
                            NProgress.done();
                            this.$notify({
                              title: '成功',
                              message: res.zhead.retMsg,
                              type: 'success'
                            });
                            this.$refs['addForm'].resetFields();
                            this.$router.push(
                                  {path:'/systemSetup/user/index'}
                            );
                            // this.$router.push({'path': '/login'})
                            //  this.$store.dispatch('LogOut').then(() => {
                            //    location.reload() // 为了重新实例化vue-router对象 避免bug
                            // })
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
    }
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