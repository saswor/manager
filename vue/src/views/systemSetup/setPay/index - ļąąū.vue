<template>
   <div class="div-warp">
     <div class="TabsBox">
     <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="支付宝配置" name="AliPay">
          <el-form :model="AladdForm"  class="formWidth" ref="AladdForm" @submit.prevent="onSubmit" >
              <el-form-item label="payAccept" size="small" :label-width="formLabelWidth">
                <el-switch
                  v-model="AladdForm.payAccept"
                  active-value='1'
                  inactive-value='2'
                  active-color="#13ce66"
                  inactive-color="#999999">
                </el-switch>
              </el-form-item>
              <el-form-item label="自动退款" prop="userName" size="small" :label-width="formLabelWidth">
                  <el-radio-group v-model="AladdForm.autoReturn">
                <el-radio label="1">支持</el-radio>
                <el-radio label="2">否</el-radio>
                </el-radio-group>
              </el-form-item>
              <el-form-item label="支付方式" prop="password"  size="small" :label-width="formLabelWidth">
                <el-select v-model="AladdForm.payType" placeholder="请选择">
                  <el-option
                    key="1"
                    label="扫码支付"
                    value="1">
                  </el-option>
                  <el-option
                    key="2"
                    label="生活号支付"
                    value="2">
                  </el-option>
                </el-select>

              </el-form-item>
              <el-form-item label="支付宝版本" prop="email" size="small" :label-width="formLabelWidth">
                <el-input  v-model="AladdForm.alipayVersion" auto-complete="off"></el-input>
              </el-form-item>
              <el-form-item label="加密方式" prop="email" size="small" :label-width="formLabelWidth">
                <el-radio-group v-model="AladdForm.enType">
                <el-radio label="1">rsa</el-radio>
                <el-radio label="2">rsa2</el-radio>
                </el-radio-group>
              </el-form-item>
              

              <el-form-item label="pid" prop="email" size="small" :label-width="formLabelWidth">
                <el-input  v-model="AladdForm.pid" auto-complete="off"></el-input>
              </el-form-item>

              <el-form-item label="key" prop="email" size="small" :label-width="formLabelWidth">
                <el-input  v-model="AladdForm.key" auto-complete="off"></el-input>
              </el-form-item>

              <el-form-item label="是否入住" prop="email" size="small" :label-width="formLabelWidth">
                <el-radio-group v-model="AladdForm.isCheckIn">
                <el-radio label="1">是</el-radio>
                <el-radio label="2">否</el-radio>
              </el-radio-group>
              </el-form-item>
              <el-form-item label="费率" prop="email" size="small" :label-width="formLabelWidth">
                <el-input  v-model="AladdForm.rate" auto-complete="off"></el-input>
              </el-form-item>
             
              
               <div class="dialog-footer" >
            <el-button type="primary" @click="AladdSubmitForm('AladdForm')">确 定</el-button>
          </div>
          </el-form>
          <div class="configure-right">
            <div class="bnt">
              <el-button type="success" size="small" @click="PostAlipayorderApply">支付配置测试</el-button> 
            </div>
            <div class="erweima"><img :src="Alatest.QRCode" alt=""></div>
            <div class="fonts">支付：{{Alatest.PayPrice}}</div>
            <div class="fonts">退款：</div>
            <div class="fonts">结果：</div>
            <div><el-button type="primary" size="mini" @click="PostreturnMoney">退款</el-button></div>
          </div>
          
      </el-tab-pane>
      <el-tab-pane label="微信配置" name="Wxpay">
          <el-form :model="WxaddForm"  class="formWidth" ref="WxaddForm" @submit.prevent="onSubmit" >
              <el-form-item label="payAccept" size="small" :label-width="formLabelWidth">
                <el-switch
                  v-model="WxaddForm.payAccept"
                  active-value='1'
                  inactive-value='2'
                  active-color="#13ce66"
                  inactive-color="#999999">
                </el-switch>
              </el-form-item>
              <el-form-item label="自动退款" prop="userName" size="small" :label-width="formLabelWidth">
                  <el-radio-group v-model="WxaddForm.autoReturn">
                <el-radio label="1">支持</el-radio>
                <el-radio label="2">否</el-radio>
                </el-radio-group>
              </el-form-item>
              <el-form-item label="支付方式" prop="payType"  size="small" :label-width="formLabelWidth">
                <el-select v-model="WxaddForm.payType" placeholder="请选择">
                  <el-option
                    key="1"
                    label="扫码支付"
                    value="1">
                  </el-option>
                  <el-option
                    key="2"
                    label="生活号支付"
                    value="2">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="商户类型" prop="contactType"  size="small" :label-width="formLabelWidth">
                <el-select v-model="WxaddForm.contactType" placeholder="请选择">
                  <el-option
                    key="1"
                    label="服务商"
                    value="1">
                  </el-option>
                  <el-option
                    key="2"
                    label="子商"
                    value="2">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="商户号" prop="contactCode" size="small" :label-width="formLabelWidth">
                <el-input  v-model="WxaddForm.contactCode" auto-complete="off"></el-input>
              </el-form-item>
              <el-form-item label="AppID" prop="AppID" size="small" :label-width="formLabelWidth">
                <el-input  v-model="WxaddForm.AppID" auto-complete="off"></el-input>
              </el-form-item>
              
              <el-form-item label="API密钥" prop="apiSecret" size="small" :label-width="formLabelWidth">
               <el-input  v-model="WxaddForm.apiSecret" auto-complete="off"></el-input>
              </el-form-item>

              <el-form-item label="授权回调页面域名" prop="authBack" size="small" :label-width="formLabelWidth">
               <el-input  v-model="WxaddForm.authBack" auto-complete="off"></el-input>
              </el-form-item>

              <el-form-item label="退款通知地址" prop="returnNotice" size="small" :label-width="formLabelWidth">
                <el-input  v-model="WxaddForm.returnNotice" auto-complete="off"></el-input>
              </el-form-item>

              <el-form-item label="取货码出货通知地址" prop="fetchCodeNotice" size="small" :label-width="formLabelWidth">
                <el-input  v-model="WxaddForm.fetchCodeNotice" auto-complete="off"></el-input>
              </el-form-item>
              <el-form-item label="费率" prop="email" size="small" :label-width="formLabelWidth">
                <el-input  v-model="WxaddForm.rate" auto-complete="off"></el-input>
              </el-form-item>
               <el-form-item label="上传证书" prop="email" size="small" :label-width="formLabelWidth">
                <el-upload
                action="/system/common/upload"
                :on-preview="handlePreview"
                :on-remove="handleRemove"
                :before-remove="beforeRemove"
                :on-success="handleSuccess"
                accept=".p12"
                multiple
                :limit="1"
                :data="param"
                :on-exceed="handleExceed"
                :file-list="fileList">
                <el-button size="small" >点击上传</el-button>
              </el-upload>
              </el-form-item>
          <div class="dialog-footer">
            <el-button type="primary" @click="WxaddSubmitForm('WxaddForm')">确 定</el-button>
          </div>
          </el-form>
          <div class="configure-right">
            <div class="bnt">
              <el-button type="success" size="small" @click="PostWechatpayorderApply">支付配置测试</el-button> 
            </div>
            <div class="erweima"><img :src="Wechattest.QRCode" alt=""></div>
            <div class="fonts">支付：{{Wechattest.PayPrice}}</div>
            <div class="fonts">退款：</div>
            <div class="fonts">结果：</div>
            <div><el-button type="primary" size="mini" @click="PostreturnMoney">退款</el-button></div>
          </div>

      </el-tab-pane>
    </el-tabs>
    </div>
  </div>
</template>
<script>
import {payconfigAlipay,AlipaySave,payconfigWx,WxpaySave,AlipayorderApply,WechatpayorderApply,returnMoney} from '@/api/systemSetup'
import NProgress from 'nprogress'
export default {
  name: 'sitemap',
  data() {
      return {
         formLabelWidth: '120px',
         fileList:[],
         AladdForm: {},
         param:{
          picType:"license"
         },
         WxaddForm: {},
         Alatest:{},
         Wechattest:{},
         activeName:'AliPay',
         Rules:{
              loginName:[
                { required: true, message: ' ', trigger: 'blur' },
              ],
              userName:[
                 { required: true, message: ' ', trigger: 'blur' }
              ],
              password:[
                 { required: true, message: ' ', trigger: 'blur' }
              ]
         }

      };
  },
  computed: {
   
  },
  created() {
  },
  methods: {
    handleClick(){},
    AladdSubmitForm(formName) {//提交新增窗口
        this.$refs[formName].validate((valid) => {
          if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
                      NProgress.start();
                      let para = Object.assign({}, this.AladdForm);
                      AlipaySave(para).then((res) => {
                            if(res.zhead.reTCode=="0000"){
                            NProgress.done();
                            this.$notify({
                              title: '成功',
                              message: res.zhead.retMsg,
                              type: 'success'
                            });
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
    getAlipay(page) {
      var _this = this;
      const listQuery = {
      }
      payconfigAlipay(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
            NProgress.done();
            _this.AladdForm = response.zbody.datas;
            console.log('阿里信息：：：',response.zbody.datas)
         }else{
             this.$notify({
              title: '失败',
              message: res.zhead.retMsg,
              type: 'error'
            });
         }
      })
    },
    getWxipay(page) {
      var _this = this;
      const listQuery = {
      }
      payconfigWx(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
            NProgress.done();
            _this.WxaddForm = response.zbody.datas;
            console.log('微信信息：：：',response.zbody.datas)
         }else{
             this.$notify({
              title: '失败',
              message: res.zhead.retMsg,
              type: 'error'
            });
         }
      })
    },
    WxaddSubmitForm(formName) {//提交新增窗口
        this.$refs[formName].validate((valid) => {
          if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
                      NProgress.start();
                      let para = Object.assign({}, this.WxaddForm);
                      WxpaySave(para).then((res) => {
                            if(res.zhead.reTCode=="0000"){
                            NProgress.done();
                            this.$notify({
                              title: '成功',
                              message: res.zhead.retMsg,
                              type: 'success'
                            });
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
    PostAlipayorderApply(){
        NProgress.start();
        let para ={}
        AlipayorderApply(para).then((res) => {
              if(res.zhead.reTCode=="0000"){
                this.Alatest = res.zbody.datas
                NProgress.done();
                this.$notify({
                  title: '成功',
                  message: res.zhead.retMsg,
                  type: 'success'
                });
              }else{
                this.$notify({
                  title: '失败',
                  message: res.zhead.retMsg,
                  type: 'error'
                });
            }
        });
    },
    PostWechatpayorderApply(){
        NProgress.start();
        let para ={}
        WechatpayorderApply(para).then((res) => {
              if(res.zhead.reTCode=="0000"){
                this.Wechattest = res.zbody.datas
                this.fileList.push({
                  name:res.zbody.datas.license,
                  url:res.zbody.datas.license
                })
                NProgress.done();
                this.$notify({
                  title: '成功',
                  message: res.zhead.retMsg,
                  type: 'success'
                });
              }else{
                this.$notify({
                  title: '失败',
                  message: res.zhead.retMsg,
                  type: 'error'
                });
            }
        });
    },
    //退款
    PostreturnMoney(){
        NProgress.start();
        let para ={
          orderId:this.Alatest.OrderId==undefined?this.Wechattest.OrderId:this.Alatest.OrderId
        }
        returnMoney(para).then((res) => {
              if(res.zhead.reTCode=="0000"){
                NProgress.done();
                this.$notify({
                  title: '成功',
                  message: res.zhead.retMsg,
                  type: 'success'
                });
              }else{
                this.$notify({
                  title: '失败',
                  message: res.zhead.retMsg,
                  type: 'error'
                });
            }
        });
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
        //return this.$confirm(`确定移除 ${ file.name }？`);
        var _this = this;
        return this.$confirm(`确定移除 ${ file.name }？`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          _this.WxaddForm.license=""
          this.$message({
            type: 'success',
            message: '删除成功!'
          });
        })
      },
    handleSuccess(response, file, fileList){
        this.WxaddForm.license = response.zbody.datas.pic
      }

  },
  mounted () {
    this.getAlipay()
    this.getWxipay()
  },
  components: {
  },
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>  
  .dialog-footer{padding-left:120px;}
  .TabsBox{padding:20px; background:#fff; margin-top:20px;}
  .div-warp{padding:0 20px;}
  .pageBox{background: #fff; padding:20px 0;}
  .formWidth{width:500px;float:left}
  .configure-right{width:300px;float:left; margin-left:50px; border:1px dashed #006BC2; padding:20px 30px; margin-top:10px;}
  .configure-right .bnt{text-align:center;}
  .configure-right .erweima{width:150px;height:150px;  margin:auto; margin-top:10px; margin-bottom:20px;}
  .configure-right .erweima img{width:100%}
  .configure-right .fonts{font-size:14px; line-height:30px;}
</style>
