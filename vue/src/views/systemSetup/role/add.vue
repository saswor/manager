<template>
 <div class="div-warp">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>新增角色</span>
        </div>
        <el-form :model="addForm" :rules="Rules" class="formWidth" ref="addForm" @submit.prevent="onSubmit" >
          <el-form-item label="角色名称" prop="roleName" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.roleName" auto-complete="off" placeholder="请输入1-30位数字、中文或英文字符，示例：终端管理员"></el-input>
          </el-form-item>
          <el-form-item label="权限字符" prop="roleKey" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.roleKey" auto-complete="off" placeholder="请输入1-100位数字、中文或英文字符，示例：terminalRole"></el-input>
          </el-form-item>
          <el-form-item label="显示顺序" prop="roleSort"  size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.roleSort" auto-complete="off" placeholder="请输入1-4位数字，示例：3"></el-input>
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
         <el-form-item label="备注" prop="remark"  size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.remark" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="菜单权限"   size="small" :label-width="formLabelWidth">
              <el-tree
                :data="menuList"
                show-checkbox
                node-key="id"
                @check="dataLengt"
                :props="defaultProps">
              </el-tree>
          </el-form-item>
        </el-form>
        <div class="dialog-footer">
          <el-button type="primary" @click="addSubmitForm('addForm')">确 定</el-button>
        </div>
      </el-card>
  </div>
</template>
<script>
import {addRole,getMenuList} from '@/api/systemSetup'
import {getCompany} from '@/api/dictionaries'
import NProgress from 'nprogress'
export default {
  data() {
        return {
          menuList:[],
          timeout:  null,
          formLabelWidth: '120px',
          addForm: {
              loginName:'',
              userName:'',
              password: '',
              email:'',
              phonenumber:'',
              sex:'',
              status:'',
              role:'',
              corpId:'',
              corpName:''
          },
          menuList: [],
          defaultProps: {
            children: 'children',
            label: 'label'
          },
          Rules:{
              roleName:[
                { required: true, message: '角色名不能为空', trigger: 'blur' },
              ],
              roleKey:[
                 { required: true, message: '权限字符不能为空', trigger: 'blur' }
              ],
              roleSort:[
                 { required: true, message: '显示顺序不能为空', trigger: 'blur' }
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
                      addRole(para).then((res) => {
                            if(res.zhead.reTCode=="0000"){
                            NProgress.done();
                            this.$notify({
                              title: '成功',
                              message: res.zhead.retMsg,
                              type: 'success'
                            });
                            this.$refs['addForm'].resetFields();
                            this.$router.push(
                                  {path:'/systemSetup/role/index'}
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
    getMenuListData(page) {
      var _this = this;
      const listQuery = {
       
      }
      getMenuList(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
          NProgress.done();
          _this.menuList = response.zbody.datas;
         }else{
             this.$notify({
              title: '失败',
              message: res.zhead.retMsg,
              type: 'error'
            });
         }
      })
    },
    dataLengt(index,vals){
      this.addForm.menuIds = vals.checkedKeys
    }
  
  },

  mounted () {
    this.getMenuListData()
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