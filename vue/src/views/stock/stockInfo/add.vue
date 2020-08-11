<template>
 <div class="div-warp">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>新增仓库</span>
        </div>
        <el-form :model="addForm" :rules="Rules" class="formWidth" ref="addForm" @submit.prevent="onSubmit" >
          <el-form-item label="仓库名称" prop="stockName" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.stockName" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="仓库联系人" prop="managerName" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.managerName" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="联系人电话" prop="mobile" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.mobile" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="区域名称" prop="districtId"  size="small" :label-width="formLabelWidth">
            <el-select v-model="addForm.districtId" placeholder="请选择">
              <el-option
                v-for="item in regionList"
                :key="item.districtId"
                :label="item.name"
                :value="item.districtId">
              </el-option>
            </el-select>
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
          <el-button @click="ReturnLevel">取 消</el-button>
          <el-button type="primary" @click="addSubmitForm('addForm')">确 定</el-button>
        </div>
      </el-card>
  </div>
</template>
<script>
import {getVendingDistrict,getCompany} from '@/api/dictionaries'
import {addStockInfo} from '@/api/stock'
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
          regionList:[],//区域列表
          restaurants: [],
          companyName: '',
          timeout:  null,
          formLabelWidth: '120px',
          formInputWidth:'200px;',
          addForm: {
              stockName:'',
              managerName: '',
              mobile:'',
              districtId:'',
              corpId:''
          },
          Rules:{
              stockName:[
                { required: true, message: ' ', trigger: 'blur' },
              ],
              managerName:[
                 { required: true, message: ' ', trigger: 'change' }
              ], 
              mobile: [
                 {  required: true, message:' ', trigger: 'change' }
              ],
              districtId: [
                 {  required: true, message: ' ', trigger: 'change' }
              ],
              corpName: [
                 {  required: true, message: '', trigger: 'change' }
              ]
          }
        }
  },
  watch: {
   
  },
  methods: {
    ReturnLevel(){
      this.$router.push(
              {path:'/stock/stockInfo/list'}
      );
    },
    addSubmitForm(formName) {//提交新增窗口
        this.$refs[formName].validate((valid) => {
          if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
                      NProgress.start();
                      let para = Object.assign({}, this.addForm);
                      addStockInfo(para).then((res) => {
                            if(res.zhead.reTCode=="0000"){
                            NProgress.done();
                            this.$notify({
                              title: '成功',
                              message: res.zhead.retMsg,
                              type: 'success'
                            });
                            this.$refs['addForm'].resetFields();
                            this.$router.push(
                                  {path:'/stock/stockInfo/list'}
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
    },
    getDistrictIdList() {
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
   this.getDistrictIdList()
 
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