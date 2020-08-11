<template>
 <div class="div-warp">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>编辑用户</span>
        </div>
        <el-form :model="addForm" :rules="Rules" class="formWidth" ref="addForm" @submit.prevent="onSubmit" >
          <el-form-item label="登录名称" prop="loginName" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.loginName" disabled auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="用户名称" prop="userName" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.userName" auto-complete="off" placeholder="请输入1-30位数字、中文或英文字符，示例：张三"></el-input>
          </el-form-item>
          <el-form-item label="密码"  size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.password" disabled type="password" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="手机" prop="phonenumber" size="small" :label-width="formLabelWidth">
            <el-input  v-model="addForm.phonenumber" auto-complete="off" placeholder="请输入11位有效的手机号码，示例：13476082236"></el-input>
          </el-form-item>
          <el-form-item label="邮箱" prop="email" size="small" :label-width="formLabelWidth">
            <el-input  v-model="addForm.email" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="性别" prop="sex" size="small" :label-width="formLabelWidth">
             <el-radio-group v-model="addForm.sex">
                <el-radio label="1">男</el-radio>
                <el-radio label="2">女</el-radio>
              </el-radio-group>
          </el-form-item>
          <el-form-item label="状态" prop="status" size="small" :label-width="formLabelWidth">
             <el-switch
              v-model="addForm.status"
              active-value='0'
              inactive-value='1'
              active-color="#13ce66"
              inactive-color="#ff4949">
            </el-switch>
          </el-form-item>
          <el-form-item label="角色" prop="roleIds" size="small" :label-width="formLabelWidth">
               <!-- <el-checkbox-group v-model="addForm.roleIds">
                <el-checkbox  v-for="item in roles" :label="item.roleId">
                  {{item.roleName}}
                </el-checkbox>
                
              </el-checkbox-group> -->
              <!-- <el-radio v-model="addForm.roleIds" v-for="item in roles" :label="item.roleId" style="margin-left:0; margin-right:10px;"  border size="small">{{item.roleName}}</el-radio> -->
              <el-radio-group v-model="addForm.roleIds">
               <el-radio v-for="item in roles" :label="item.roleId" style="margin-left:0; margin-right:10px;" border size="small">{{item.roleName}}</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="商户名称"  prop="corpName" size="small"  :label-width="formLabelWidth">
            <el-autocomplete
              v-model="addForm.corpName"
              :fetch-suggestions="querySearchAsync"
              value-key="corpName"
              placeholder="输入搜索公司"
              @select="handleSelect"

            ></el-autocomplete>
          </el-form-item>
        </el-form>
        <div class="dialog-footer">
          <el-button type="primary" @click="addSubmitForm('addForm')">确 定</el-button>
        </div>
      </el-card>
  </div>
</template>
<script>
import {editUser,getRoleList,getByUserId} from '@/api/systemSetup'
import {getCompany} from '@/api/dictionaries'
import NProgress from 'nprogress'
export default {
  data() {
        return {
          regionList:[],//区域列表
          restaurants: [],
          companyName: '',
          roles:[],
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
              roleIds:[],
              corpId:'',
              corpName:''

          },
          Rules:{
              loginName:[
                { required: true, message: ' ', trigger: 'blur' },
              ],
              userName:[
                 { required: true, message: ' ', trigger: 'blur' }
              ],
              password:[
                 { required: true, message: ' ', trigger: 'blur' },
                 { min: 6, max: 12, message: '长度在 6 到 12 个字符', trigger: 'blur' }
              ],
              phonenumber:[
                 { required: true, message: ' ', trigger: 'blur' }
              ], 
              corpName:[
               { required: true, message: ' ', trigger: 'blur' }
              ],
              roleIds:[
               { required: true, message: '请选择角色', trigger: 'change' }
              ],
          }
        }
  },
  watch: {
   
  },
  created() {
      getByUserId({userId:this.$route.query.userId}).then(response => {
         if(response.zhead.reTCode==="0000"){
          this.addForm= response.zbody.datas;
          console.log("用户信息：：",response.zbody.datas)
         }else{
             this.$notify({
              title: '失败',
              message: res.zhead.retMsg,
              type: 'error'
            });
         }
      })
   
  },
  methods: {
    addSubmitForm(formName) {//提交新增窗口
        this.$refs[formName].validate((valid) => {
          if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
                      NProgress.start();
                      let para = Object.assign({}, this.addForm);
                      editUser(para).then((res) => {
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
    querySearchAsync(queryString, cb) {//输入搜索托管公司
        var _this = this;
        const listQuery = {
          corpName:_this.companyName
        }
        getCompany(listQuery).then(response => {
          _this.restaurants = response.zbody.datas
           var restaurants = this.restaurants;
        var results = queryString ? restaurants.filter(this.createStateFilter(queryString)) : restaurants;
          cb(results);
        })
    },
    createStateFilter(queryString) {
        return (state) => {
          return (state.corpName.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
        };
    },
    handleSelect(item) {
        this.addForm.corpId = item.corpId
        this.addForm.corpName = item.corpName
        
    },
    getRole(page) {
      var _this = this;
      this.listLoading = true
      const listQuery = {
        roleName:"",
        status:"",
        pageSize:1000,
        pageNum:page,
        orderByColumn:'createtime',
        isAsc:'desc'
      }
      getRoleList(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
            NProgress.done();
          _this.roles = response.zbody.datas.rows;
          console.log("角色：：：",response.zbody.datas)
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
    this.getRole()
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