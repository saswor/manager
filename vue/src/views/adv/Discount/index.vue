<template>
 <div class="warp-box">

      <div class="main">
        <div class="icon-info"><i class="el-icon-info"></i>促销活动只针对移动支付有效，可以提高客户活跃度，增加交易订单量。</div>
        <div class="fonts">
          <i class="el-icon-arrow-right"></i>同一台售货机的同一种商品在一个时间段内只能配置一种促销活动。<br/>
          <i class="el-icon-arrow-right"></i>同一促销活动同一日期段创建的不同折扣的促销活动，后创建的覆盖之前创建的
        </div>
      <el-row :gutter="20" style="margin-top:20px;">
        <el-col :span="12">
          <el-card shadow="hover">
            <div slot="header" class="clearfix">
              <span>购买折扣</span>
              <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button>
            </div>
            <div class="title">
               <router-link :to="{path:'/adv/Discount/buy/list', query:{favWay:'01',favTarget:'1'}}" class="green">整机折扣>></router-link>
            </div>
            <div class="contBox">设备机器中的所有商品，均可享受改促销活动价格目前已经配置<span>{{getInfo.zjzk}}</span>个，已下发<span>{{getInfo.zjzkxf}}</span>个</div>
            <div class="title">
              <router-link :to="{path:'/adv/Discount/buy/list', query:{favWay:'01',favTarget:'2'}}" class="green">单品折扣>></router-link>
            </div>
            <div class="contBox">设备机器中的某个商品，可享受改促销活动价格目前已经配置<span>{{getInfo.dpzk}}</span>个，已下发<span>{{getInfo.dpzkxf}}</span>个</div>
          </el-card>
        </el-col>
        <el-col :span="12">
          <el-card shadow="hover">
            <div slot="header" class="clearfix">
              <span >消费立减</span>
              <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button>
            </div>
            <div class="title">
               <router-link :to="{path:'/adv/Discount/consumption/list', query:{favWay:'02',favTarget:'1'}}" class="green">整机立减>></router-link>
            </div>
            <div class="contBox">设备机器中的所有商品，均可享受改促销活动价格目前已经配置<span>{{getInfo.zjlj}}</span>个，已下发<span>{{getInfo.zjljxf}}</span>个</div>
            <div class="title">
              <router-link :to="{path:'/adv/Discount/consumption/list', query:{favWay:'02',favTarget:'2'}}" class="green">单品立减>></router-link>
            </div>
            <div class="contBox">设备机器中的某个商品，可享受改促销活动价格目前已经配置<span>{{getInfo.dplj}}</span>个，已下发<span>{{getInfo.dpljxf}}</span>个</div>
          </el-card>
        </el-col>
      </el-row>
     </div>
  </div>
</template>
<script>
import {ConfigTj } from '@/api/adv'
import NProgress from 'nprogress'
export default {
  data() {
        return {
          getInfo:{}
        }
  },
  watch: {
    
  },
  created(){
      NProgress.start();
      var _this = this;
      const listQuery = {
      }
      ConfigTj(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
            NProgress.done();
  
            _this.getInfo = response.zbody.datas;
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
  },

  mounted () {
    
  },
  components: {

  }
}
</script>
<style scoped>
.icon-info{font-size:16px; color:#666;}
.icon-info i{color:#E6A23C; margin-right:5px;}
.fonts{line-height:30px; color:#333; font-size:14px; margin-top:10px;}
.warp-box{}
.main{width:1000px; margin:auto; margin-top:5%;}
.green{color:green;font-weight: bold; text-decoration:underline}
.title{ font-size:20px; }
.contBox{ font-size:14px; padding:10px 0;}
.contBox span{color:red; display: inline-block; padding:0 10px;}
</style>