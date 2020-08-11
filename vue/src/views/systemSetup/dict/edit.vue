<template>
 <div class="div-warp">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>编辑字典</span>
        </div>
        <el-form :model="addForm" :rules="Rules" class="formWidth" ref="addForm" @submit.prevent="onSubmit" >
          <el-form-item label="字典名称" prop="dictName" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.dictName" auto-complete="off" placeholder="请输入1-100位数字、中文或英文字符，示例：货柜状态"></el-input>
          </el-form-item>
          <el-form-item label="字典类型" prop="dictType" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.dictType" auto-complete="off" placeholder="1-100位数字、中文或英文字符包含下划线，示例:container_state"></el-input>
          </el-form-item>
          <el-form-item label="状态" prop="phonenumber" size="small" :label-width="formLabelWidth">
             <el-switch
              v-model="addForm.status"
              active-value='0'
              inactive-value='1'
              active-color="#13ce66"
              inactive-color="#ff4949">
            </el-switch>
          </el-form-item>
        </el-form>
        <div class="dialog-footer">
          <el-button type="primary" @click="addSubmitForm('addForm')">确 定</el-button>
        </div>
      </el-card>
  </div>
</template>
<script>
import {editDist} from '@/api/systemSetup'
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
              dictName:'',
              dictType:'',
              status: ''
          },
          Rules:{
              dictName:[
                { required: true, message: '字典名称不能为空 ', trigger: 'blur' },
              ],
              dictType:[
                 { required: true, message: '字典类型不能为空 ', trigger: 'blur' }
              ]
          }
        }
  },
  watch: {
  },
  created() {
        this.addForm = JSON.parse(localStorage.getItem("editData"));
  },
  methods: {
    addSubmitForm(formName) {//提交新增窗口
        this.$refs[formName].validate((valid) => {
          if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
                      NProgress.start();
                      let para = Object.assign({}, this.addForm);
                      editDist(para).then((res) => {
                            if(res.zhead.reTCode=="0000"){
                            NProgress.done();
                            this.$notify({
                              title: '成功',
                              message: res.zhead.retMsg,
                              type: 'success'
                            });
                            this.$refs['addForm'].resetFields();
                            this.$router.push(
                                  {path:'/systemSetup/dict/index'}
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