<template>
 <div class="div-warp">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>新增区域</span>
        </div>
        <el-form :model="addForm" :rules="Rules" class="formWidth" ref="addForm" @submit.prevent="onSubmit" >
          <el-form-item label="区域编码" prop="code" size="small" :label-width="formLabelWidth">
            <el-input placeholder="请输入1-30位数字或英文字符，示例：430000" v-model="addForm.code" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="区域名称" prop="name" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.name" auto-complete="off" placeholder="请输入1-50位数字、中文或英文字符，示例：湖北省武汉市"></el-input>
          </el-form-item>
          <el-form-item label="负责人姓名" prop="manager" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.manager" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="联系方式" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.mobile" auto-complete="off"></el-input>
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
import {getCompany,addArea} from '@/api/pointposition'
import NProgress from 'nprogress'
export default {
  data() {
        var ValidationCompany = (rule, value, callback) => {
            if (!value) {
              return callback(new Error(' '));
            }else{
               callback();
            }
        };
        return {
          restaurants: [],
          companyName: '',
          timeout:  null,
          formLabelWidth: '120px',
          formInputWidth:'200px;',
          addForm: {
              name:'',
              code:'',
              manager: '',
              longitude:'',
              latitude:'',
              mobile: '',
              description: '',
              corpId: '',
              corpName:''
          },
          Rules:{
              code:[
                { required: true, message: '区域编码不能为空 ', trigger: 'blur' },
              ],
              name:[
                 { required: true, message: '区域名称不能为空 ', trigger: 'change' }
              ], 
              corpName: [
                 {  required: true, message: '商户名称不能为空 ', validator: ValidationCompany, trigger: 'change' }
              ]
          }
        }
  },
  watch: {
   
  },
  methods: {
    ReturnLevel(){
      this.$router.push(
              {path:'/pointposition/area'}
      );
    },
    addSubmitForm(formName) {//提交新增窗口
        this.$refs[formName].validate((valid) => {
          if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
                      NProgress.start();
                      let para = Object.assign({}, this.addForm);
                      addArea(para).then((res) => {
                            if(res.zhead.reTCode=="0000"){
                            NProgress.done();
                            this.$notify({
                              title: '成功',
                              message: res.zhead.retMsg,
                              type: 'success'
                            });
                            this.$refs['addForm'].resetFields();
                            this.$router.push(
                                  {path:'/pointposition/area'}
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
        console.log(item);
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