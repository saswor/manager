<template>
   <div class="div-warp">
     <div class="TabsBox">
     <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="支付宝配置" name="AliPay">
          <el-form :model="AladdForm"  class="formWidth" ref="AladdForm" @submit.prevent="onSubmit" >
             
            
              <el-form-item label="支付宝AppId" prop="appId" size="small" :label-width="formLabelWidth">
                <el-input  v-model="AladdForm.appId" auto-complete="off"></el-input>
              </el-form-item>
              <el-form-item label="支付宝公钥" prop="pubKey" size="small" :label-width="formLabelWidth">
                <el-input  v-model="AladdForm.pubKey" auto-complete="off"></el-input>
              </el-form-item>
               <el-form-item label="支付宝私钥" prop="privKey" size="small" :label-width="formLabelWidth">
                <el-input  v-model="AladdForm.privKey" auto-complete="off"></el-input>
              </el-form-item>
              <el-form-item label="加密方式" prop="signType" size="small" :label-width="formLabelWidth">
                <el-radio-group v-model="AladdForm.signType">
                <el-radio label="1">rsa</el-radio>
                <el-radio label="2">rsa2</el-radio>
                </el-radio-group>
              </el-form-item>
              
               <div class="dialog-footer" >
            <el-button type="pimary" @click="AladdSubmitForm('AladdForm')" v-permission="['sys:base:pay:add']">确 定</el-button>
          </div>
          </el-form>
          
          <div class="configure-right">
            <div class="bnt">
              <el-button type="success" size="small" @click="PostAlipayorderApply" v-permission="['sys:base:pay:view']">支付配置测试</el-button> 
            </div>
            <div class="erweima"><img :src="Alatest.QRUrl" alt=""></div>
            <div class="fonts">支付：{{aliResult.payStateName}}</div>
            <!-- <div class="fonts">退款：{{returnTypeName}}</div>
            <div class="fonts">结果：</div> -->
            <div><el-button type="primary" size="mini" @click="PostreturnMoney(Alatest.OrderId)">退款</el-button></div>
          </div>
          
      </el-tab-pane>
      <el-tab-pane label="微信配置" name="Wxpay">
          <el-form :model="WxaddForm" class="formWidth" ref="WxaddForm" @submit.prevent="onSubmit" >
              <el-form-item label="支付方式" prop="WxaddForm.payType" size="small" :label-width="formLabelWidth">
                <el-select clearable size="small" v-model="WxaddForm.payType" @change="getWxipay" placeholder="请选择支付方式">
                  <el-option
                    key="1"
                    label="微信扫码支付"
                    value="02">
                  </el-option>
                  <el-option
                    key="2"
                    label="微信公众号支付"
                    value="03">
                  </el-option>
                  <el-option
                    key="3"
                    label="微信小程序支付"
                    value="04">
                  </el-option>
                </el-select>
              </el-form-item>

              <el-form-item label="appId" prop="appId" size="small" :label-width="formLabelWidth">
                <el-input  v-model="WxaddForm.appId" auto-complete="off"></el-input>
              </el-form-item>

              <el-form-item label="商户密钥" prop="apiSercret" size="small" :label-width="formLabelWidth">
               <el-input  v-model="WxaddForm.apiSercret" auto-complete="off"></el-input>
              </el-form-item>

              <el-form-item label="微信商户接入号" prop="mchId" size="small" :label-width="formLabelWidth">
               <el-input  v-model="WxaddForm.mchId" auto-complete="off"></el-input>
              </el-form-item>
			  
			  <el-form-item label="微信子商户接入号" prop="subMchId" size="small" :label-width="formLabelWidth">
               <el-input  v-model="WxaddForm.subMchId" auto-complete="off"></el-input>
              </el-form-item>
            
               <el-form-item label="证书云地址" prop="cert" size="small" :label-width="formLabelWidth">
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
            <el-button type="primary" @click="WxaddSubmitForm('WxaddForm')"  v-permission="['sys:base:pay:add']">确 定</el-button>
          </div>
          </el-form>

          <div class="configure-right">
            <div class="bnt">
              <el-button type="success" size="small" @click="PostWechatpayorderApply" v-permission="['sys:base:pay:view']">支付配置测试</el-button> 
            </div>
            <div class="erweima"><img :src="Wechattest.QRUrl" alt=""></div>
            <div class="fonts">支付：{{wechatResult.payStateName}}</div>
            <!-- <div class="fonts">退款：{{returnTypeName}}</div>
            <div class="fonts">结果：</div> -->
            <div><el-button type="primary" size="mini" @click="PostreturnMoney(Wechattest.OrderId)">退款</el-button></div>
          </div>

      </el-tab-pane>
    </el-tabs>
    </div>
  </div>
</template>
<script>
import {payconfigAlipay,AlipaySave,payconfigWx,WxpaySave,AlipayorderApply,WechatpayorderApply,returnMoney,payTest} from '@/api/systemSetup'
import { getToken } from '@/utils/auth'
import { mapGetters } from 'vuex'
import NProgress from 'nprogress'
import permission from '@/directive/permission/index.js'
export default {
  directives: { permission },
  name: 'sitemap',
  data() {
      return {
         formLabelWidth: '120px',
         websock:null,
         fileList:[],
         AladdForm: {},
         param:{
          picType:"license",
          payType:"02"
         },
         WxaddForm: {
           payType:"02"
         },
         Alatest:{},
         Wechattest:{},
         aliResult:{},
         wechatResult:{},
         returnTypeName:'',
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
   ...mapGetters([
      'name',
      'roles',
      'corpId',
      'userinfo'
    ])
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
        payType:this.WxaddForm.payType,
      }
      _this.param.payType=this.WxaddForm.payType;
      payconfigWx(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
            NProgress.done();
            _this.WxaddForm = response.zbody.datas;
            _this.fileList.push({
                  name:res.zbody.datas.cert,
                  url:res.zbody.datas.cert
                })
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
        let para ={};
        const listQuery = {
          payType:'01',
        };
        payTest(listQuery).then((res) => {
              if(res.zhead.reTCode=="0000"){
                this.Alatest = res.zbody.datas.zbody
                NProgress.done();
                // this.$notify({
                //   title: '成功',
                //   message: res.zhead.retMsg,
                //   type: 'success'
                // });
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
        let para ={};
        const listQuery = {
          // payType:this.WxaddForm.payType,
          payType:"02",
        };
        payTest(listQuery).then((res) => {
              if(res.zhead.reTCode=="0000"){
                this.Wechattest = res.zbody.datas.zbody
                
                NProgress.done();
                // this.$notify({
                //   title: '成功',
                //   message: res.zhead.retMsg,
                //   type: 'success'
                // });
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
    PostreturnMoney(order){
      var _this=this;
        NProgress.start();
        let para ={
          //orderId:this.Alatest.OrderId==undefined?this.Wechattest.OrderId:this.Alatest.
          orderId:order
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
        this.WxaddForm.cert = response.zbody.datas.pic
      },

    initWebSocket(){ //初始化weosocket 
    
          const wsuri =this.WS_API+"/system/payConfigWebsocket?JSESSIONID="+getToken()+"&corpId="+this.corpId+"&userId="+this.userinfo.userId;//
          // const wsuri ="ws://localhost:8989/system/payConfigWebsocket?JSESSIONID="+getToken()+"&corpId="+this.corpId+"&userId="+this.userinfo.userId;//
          this.websock = new WebSocket(wsuri); 
          // this.websocket.onopen = this.websocketonopen;
          // this.websocket.onerror = this.websocketonerror;
          this.websock.onmessage = this.websocketonmessage; 
          this.websock.onclose = this.websocketclose;
　　　},
　　　websocketonopen() {
        console.log("WebSocket连接成功");
　　　},
　　　websocketonerror(e) { //错误
 　　　　console.log("WebSocket连接发生错误");
　　　},
      websocketonmessage(e){ //数据接收 
        const _this = this;
        const redata = JSON.parse(e.data);
　　　　 console.log(redata);
        if(redata.orderId==_this.Alatest.OrderId){
            _this.aliResult=redata;
        }
        if(redata.orderId==_this.Wechattest.OrderId){
            _this.wechatResult=redata;
        }
　　　}, 
　    websocketsend(agentData){//数据发送 
　　　　　this.websock.send(agentData); 
　　　}, 
　　　websocketclose(e){ //关闭 
          　this.websock.close();
　　　　　//console.log("connection closed (" + e.code + ")"); 
　　　}
  },
  beforeMount(){
    //页面刚进入时开启长连接
    this.initWebSocket()
  },
  
  mounted () {
    this.getAlipay()
    this.getWxipay()
  },
  destroyed: function() {
　　　　//页面销毁时关闭长连接
　　　　this.websocketclose();
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
