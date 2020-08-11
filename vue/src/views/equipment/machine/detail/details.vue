、
<template>
 <div class="div-warp">
    <div class="block-warp">
          <el-row>
            <el-col :span="24">
              <div class="grid-content bg-purple-dark">
                <div class="title-top">
                  <h1>{{vView.address}}
                    <el-button type="primary"  icon="el-icon-check" style="float:right; margin-top:5px; margin-right:10px;" @click="handleRegister" >注册</el-button>
                    <el-button type="primary"  icon="el-icon-check" style="float:right; margin-top:5px; margin-right:10px;" @click="handleStockingEdit">库存修正</el-button>
                    <el-button type="success" v-if="this.$route.query.type!='map'" icon="el-icon-edit" style="float:right; margin-right:20px; margin-top:5px;" @click="handleEdit" v-permission="['sys:vending:site:edit']">编辑</el-button>

                    
                  </h1>
                  <p>归属：{{vView.corpName}} <br/>点位地址：{{vView.address}}</p>
                </div>
                <div class="titles">基本信息</div>
                <el-row>
                  <el-col :span="8">
                    <div class="row-style"><label>售卖机编码：</label><span>{{vView.siteCode}}</span></div>
                    <div class="row-style"><label>售卖机编号：</label><span>{{vView.siteId}}</span></div>
                    <div class="row-style"><label>区域名称：</label><span>{{vView.districtName}}</span></div>
                    <div class="row-style"><label>机型：</label><span>{{vView.device}}</span></div>
                    <div class="row-style"><label>售卖状态：</label><span>{{vView.sellStateName}}</span></div>
                  </el-col>
                  <el-col :span="8">
                    <div class="row-style"><label>点位名称：</label><span>{{vView.pointName}}</span></div>
                    <div class="row-style"><label>货柜类型：</label><span>{{vView.cabinetTypeName}}</span></div>
                    <div class="row-style"><label>网络类型：</label><span>{{vView.netWorkName}}</span></div>
                    <div class="row-style"><label>网络状态：</label><span>{{vView.netSateName}}</span></div>

                  </el-col>
                  <el-col :span="8">
                    <div class="row-style"><label>线路名称：</label><span>{{vView.lineName}}</span></div>
                    <div class="row-style"><label>厂家：</label><span>{{vView.factoryName}}</span></div>
                    <div class="row-style"><label>支付方式：</label><span>{{vView.payTypeName}}</span></div>
                    <div class="row-style"><label>持续时间：</label><span>{{vView.continueTime}}</span></div>
                  </el-col>
                </el-row>
              </div>
            </el-col>
          </el-row>
    </div>
    <div class="block-warp" v-for="item in vView.cabinets">
          <el-row>
            <el-col :span="24">
              <div class="grid-content bg-purple-dark">
                <div class="titles">挂载柜配置</div>
                <el-row>
                  <el-col :span="8">
                    <div class="row-style"><label>货柜编号：</label><span>{{item.cabinetId}}</span></div>
                    <div class="row-style"><label>机型：</label><span>{{item.deviceId}}</span></div>
                  </el-col>
                  <el-col :span="8">
                    <div class="row-style"><label>外挂：</label><span>{{item.hangTypeName}}</span></div>
                    <div class="row-style"><label>货柜类型：</label><span>{{item.cabinetTypeName}}</span></div>
                  </el-col>
                  <el-col :span="8">
                    <div class="row-style"><label>串口号：</label><span>{{item.pointCode}}</span></div>
                    <div class="row-style"><label>厂家：</label><span>{{item.factoryName}}</span></div>
                  </el-col>
                </el-row>
              </div>
            </el-col>
          </el-row>
    </div>
    <el-row :gutter="20">
      <!-- <el-col :span="6" >
        <div class="grid-content grid-block bg-powder-shadow" @click="record">
          <div class="grid-num">{{NumInfo.supplyNum}}</div>
          <div class="grid-font">补货记录</div>
      </div>
      </el-col>
      <el-col :span="6"><div class="grid-content grid-block bg-success-shadow" @click="ToConfigure">
           <div class="grid-num">{{NumInfo.pCurNum}}/{{NumInfo.pMaxNum}}</div>
          <div class="grid-font">货道当前商品量</div>
      </div></el-col>
      <el-col :span="6"><div class="grid-content grid-block bg-purple-shadow" @click="history">
           <div class="grid-num">{{NumInfo.orderNum}}</div>
            <div class="grid-font">历史交易记录</div>
      </div></el-col>
      <el-col :span="6"><div class="grid-content grid-block bg-orange-shadow" @click="warning">
          <div class="grid-num">{{NumInfo.warnNum}}</div>
          <div class="grid-font">当前告警数</div>
      </div></el-col> -->

      <el-col :span="4" >
        <div class="grid-content grid-block bg-powder-shadow" @click="record">
          <div class="grid-num">{{NumInfo.supplyNum}}</div>
          <div class="grid-font">补货记录</div>
      </div>
      </el-col>
      <el-col :span="4"><div class="grid-content grid-block bg-success-shadow" @click="ToConfigure">
           <div class="grid-num">{{NumInfo.pCurNum}}/{{NumInfo.pMaxNum}}</div>
          <div class="grid-font">货道当前商品量</div>
      </div></el-col>
      <el-col :span="4"><div class="grid-content grid-block bg-purple-shadow" @click="history">
           <div class="grid-num">{{NumInfo.orderNum}}</div>
            <div class="grid-font">历史交易记录</div>
      </div></el-col>
      <el-col :span="4"><div class="grid-content grid-block bg-orange-shadow" @click="warning">
          <div class="grid-num">{{NumInfo.warnNum}}</div>
          <div class="grid-font">当前告警数</div>
      </div></el-col>
      <el-col :span="4"><div class="grid-content grid-block bg-green-shadow" @click="stockingEdit">
          <div class="grid-num">{{NumInfo.lsdifferNum}}</div>
          <div class="grid-font">库存修正数</div>
      </div></el-col>
      <el-col :span="4"><div class="grid-content grid-block bg-blue-shadow" @click="logfile">
          <div class="grid-num">{{NumInfo.logfileNum}}</div>
          <div class="grid-font">日志记录数</div>
      </div></el-col>
    </el-row>
    <div style="height:30px;width:100%;"></div>
  </div>
</template>
<script>
import {getVendingView,VendingRecord,getMapDetal,MapVendingRecordByPoint,register} from '@/api/equipment'
import permission from '@/directive/permission/index.js'
import NProgress from 'nprogress'
export default {
  directives: { permission },
  data() {
        return {
            vView:{},
            NumInfo:{}
        }
  },
  watch: {

  },
  methods: {
    record(){//补货记录
       this.$router.push(
          {
            path:'/equipment/machine/detail/record/record',
            query:{
              siteId:this.vView.siteId
            }
          }
        );
    },
    ToConfigure(){//货道商品
      this.$router.push(
        {
            path:'/equipment/machine/detail/toconfigure/toconfigure',
            query: {
            pointId: this.vView.pointId
            }
        }
      );
    },
    history(){
      this.$router.push(
          {
            path:'/equipment/machine/detail/history/history',
            query: {
              siteId: this.vView.siteId
            }
          }
      );
    },
    warning(){
      this.$router.push(
          {
            path:'/equipment/machine/detail/warning/warning',
            query: {
              siteId: this.vView.siteId
            }
        }
      );
    },
    stockingEdit(){
      this.$router.push(
          {
            path:'/equipment/machine/detail/stockingEdit/list',
            query: {
              siteId: this.vView.siteId
            }
        }
      );
    },
    logfile(){
      this.$router.push(
          {
            path:'/equipment/machine/detail/logfile/list',
            query: {
              siteId: this.vView.siteId
            }
        }
      );
    },
    handleAdd() {//显示新增窗口
        this.$router.push(
          {path:'/equipment/add'}
        );
    },
    handleEdit(index, row) {//显示编辑窗口
     this.dialogEditTableVisible= true
     this.editForm = Object.assign({}, row);
     this.$router.push({
      path:'/equipment/machine/edit?logid='+this.$route.query.logid
     })
    },
    handleStockingEdit() {//显示库存修正窗口
     this.$router.push({
      path:'/equipment/machine/detail/stockingEdit/edit',
      query: {
            pointId: this.vView.pointId
            }
     });
    },
    handleRegister(){
        this.$confirm('确认注册吗？', '提示', {}).then(() => {
                    NProgress.start();
                    let para = {
                      siteId:this.vView.siteId
                    }
                    register(para).then((res) => {
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
    },
    getView(){
      var _this = this;
      const listQuery = {
        logid:this.$route.query.logid,
      }
      getVendingView(listQuery).then(response => {
           if(response.zhead.reTCode==="0000"){
            _this.vView = response.zbody.datas
            _this.getVendingRecord()
            console.log("详情：",response.zbody.datas)
            NProgress.done();
           }else{
               this.$notify({
                title: '失败',
                message: response.zhead.retMsg,
                type: 'error'
              });
           }
        })
    },
    //获取下面四块信息，
    getVendingRecord(){
      var _this = this;
      const listQuery = {
        siteId:_this.vView.siteId,
      }
      VendingRecord(listQuery).then(response => {
           if(response.zhead.reTCode==="0000"){
            _this.NumInfo = response.zbody.datas
            console.log("历史交易记录：：：：",response.zbody.datas)
            NProgress.done();
           }else{
               this.$notify({
                title: '失败',
                message: response.zhead.retMsg,
                type: 'error'
              });
           }
        })
    },
    //地图获取下面四块信息，
    getVendingRecordByPoint(){
      var _this = this;
      const listQuery = {
        pointId:this.$route.query.pointId
      }
      MapVendingRecordByPoint(listQuery).then(response => {
           if(response.zhead.reTCode==="0000"){
            _this.NumInfo = response.zbody.datas
            console.log("历史交易记录：：：：",response.zbody.datas)
            NProgress.done();
           }else{
               this.$notify({
                title: '失败',
                message: response.zhead.retMsg,
                type: 'error'
              });
           }
        })
    },
    //地图点击点位查询
    getMapdetal(){
      var _this = this;
      const listQuery = {
        pointId:this.$route.query.pointId
      }
      getMapDetal(listQuery).then(response => {
           if(response.zhead.reTCode==="0000"){
            _this.vView = response.zbody.datas
            NProgress.done();
           }else{
               this.$notify({

                title: '失败',
                message: response.zhead.retMsg,
                type: 'error'
              });
           }
        })
    }
  },
  mounted () {
    if(this.$route.query.type == "map")
    {
        this.getMapdetal()
        this.getVendingRecordByPoint()
    }
    else
    {
        this.getView()
    }
  }
}
</script>
<style scoped>
  .grid-content{ background: #fff; }
  .grid-block{padding:30px 0; text-align: center; cursor: pointer;}
  .grid-num{font-size: 36px; color:#fff;}
  .grid-font{font-size:16px; color:#fff; margin-top: 10px;}
  .titles{padding:20px 0; }
  .row-style{padding:10px 0;}
  .row-style label{ font-size:14px; color: #000;}
  .row-style span{ font-size:14px;color:#666; }
  .title-top{border-bottom: 1px solid #efefef;}
  .block-warp{background: #fff; margin: 20px 0; padding:10px; border-radius: 3px;}
  .div-warp{padding:0 30px;  overflow: hidden;}
</style>