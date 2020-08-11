<template>
 <div class="div-warp">
  <div class="div-warp" style="margin-top:30px;">
    <el-form :model="addForm"  inline ref="addForm" @submit.prevent="onSubmit">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>设备信息</span>
        </div>
          <el-form-item label="售后机编号" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.siteId" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="售后机名称" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.siteName" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="设备序列号" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.seqId" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="系统类型" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.platTypeName" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="固件版本" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.vfirmware" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="基带版本" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.vbaseband" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="VCS版本" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.vvcs" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
          <!-- <el-form-item label="IP" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.ipAddress" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item> -->
          <el-form-item label="信号值" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.signalValue" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="ICCID" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.iccid" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="网络状态" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.netSateName" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="屏幕类型" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.screenTypeName" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
          <el-form-item label="屏幕分辨率" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.resoution" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item>
          <!-- <el-form-item label="持续时间" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.contime" :disabled="true" class="inputStyle" ></el-input>
          </el-form-item> -->
      </el-card>
      </el-form>

      <div class="bntBox">
          <el-button type="primary" @click="addSubmitForm">确定</el-button>
      </div>
       
  </div>
  </div>
</template>
<script>
import {getVendingStateDetails} from '@/api/equipment'
export default {
  data() {
        return {
          addForm: {},
          formLabelWidth:"200px"
        }
  },

  methods: {
     addSubmitForm(){
       var _this = this;
      //  this.$router.go(-1);
       this.$router.push(
         {
           path:'/equipment/remote/index',
         }
      )
     },
     getVendingStateDetailsData(page){
        var _this = this;
        const listQuery = {
          siteId:_this.$route.query.siteId,
        }
        getVendingStateDetails(listQuery).then(response => {
          if(response.zhead.reTCode==="0000"){
          _this.addForm = response.zbody.datas;
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
    this.getVendingStateDetailsData()
  },
  components: {
  },
}
</script>
<style scoped>
 .bntBox{overflow: hidden; padding:30px 0; text-align:center; margin-top:20px;}
</style>