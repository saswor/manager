<template>
 <div class="div-warp">

    <!-- 货道配置 -->
      <el-dialog title="缺货详情" :fullscreen="true" center :visible.sync="dialogFormVisible">
          
             
              <!-- 货道配置列表 -->
                   <el-tabs v-model="activeName" @tab-click="handleClick">
                      <el-tab-pane v-for="(item,index) in cabinetInfo" :label="item.cabinetName" :name="item.cabinetId">
                          <!-- 货道 -->
                          <div class="demo-bs-wrapper">
                            <div style="position:relative"></div>
                           <el-row type="flex" justify="left" v-for="row in item.lanes"  >
                              <el-col  v-for="col in row.cols">
                                  <div class="imgStyel" >
                                    <!-- 操作区域 -->
                                    <div class="block-top"> 
                                      {{col.laneId}}
                                    </div>
                                    <!-- 添加按钮 -->
                                    <div  class="add-class blockBox" v-if="col.lanep.laneSate==1&&col.lanep.productId==''">
                                      <i class="el-icon-circle-plus-outline icon-size"></i>
                                    </div>
                                    <!-- 禁用 -->
                                    <div  class="add-class blockBox"  v-else-if="col.lanep.laneSate==2">
                                        <i class="el-icon-circle-close-outline icon-size-circle"></i>
                                    </div>
                                    <!-- 有商品 -->
                                    <div  class="blockBox" v-else>
                                        <img :src="ImgUrl+col.lanep.pic" >
                                        <div class="bottom clearfix">
                                          <div class="titels">{{col.lanep.productName}}</div>
                                          <div class="price"> ¥<i>{{col.lanep.salePrice}}</i><em>库存{{col.lanep.curCap}}</em></div>
                                        </div>
                                        <div class="InputFStyle">
                                            <el-input v-model="col.lanep.capacity" size="mini" class="i1" placeholder="容量"></el-input>
                                            <el-input v-model="col.lanep.warnCap" size="mini" class="i2" placeholder="阀值"></el-input>
                                        </div>
                                    </div>
                                  </div>  
                              </el-col>
                            </el-row> 
                            </div>
                      </el-tab-pane>
                    
                    </el-tabs>
        
      </el-dialog>  
    <!-- 货道配置 end -->
    <div class="block-warp">
       <div class="tit" style="margin-bottom:20px;">警告记录信息</div>
          <el-form :model="addForm"  label-width="120px" :label-position="labelPosition" :inline="true"  ref="addForm" >
          <el-form-item label="线路编号" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.lineId" disabled auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="线路名称"   size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.lineName"  disabled auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="区域名称" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.districtName" disabled auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="仓库编号" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.wmId" disabled auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="仓库名称" size="small"  :label-width="formLabelWidth">
            <el-input v-model="addForm.wmName" disabled auto-complete="off"></el-input>
          </el-form-item>

          <el-form-item label="待补货数量"  size="small"  :label-width="formLabelWidth">
            <el-input v-model="addForm.waitSPNum" disabled auto-complete="off"></el-input>
          </el-form-item>

          <el-form-item label="补货员名称" size="small"  :label-width="formLabelWidth">
            <el-input v-model="addForm.supplierName" disabled auto-complete="off"></el-input>
          </el-form-item>

          <el-form-item label="补货员编号" size="small"  :label-width="formLabelWidth">
            <el-input v-model="addForm.supplierId" disabled auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="下次补货时间" size="small"  :label-width="formLabelWidth">
            <el-input v-model="addForm.lastSTime"  disabled auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="缺货紧急度"  size="small"  :label-width="formLabelWidth">
            <el-input v-model="addForm.storyLevelName" disabled auto-complete="off"></el-input>
          </el-form-item>
        </el-form>
    </div>

    <div class="block-warp">
       <div class="tit">线路缺货汇总<el-button style="float:right" type="primary" @click="addto" size="mini">生成补货单</el-button></div>
       <el-table
          :data="tableData3"
          style="width: 100%"
          >
          <el-table-column
            prop="productId"
            label="商品编号"
            width="200">
          </el-table-column>
          <el-table-column
            prop="productName"
            label="商品名称"
            >
          </el-table-column>
          <el-table-column
            prop="supplyNum"
            label="缺货数量"
           >
          </el-table-column>
        </el-table>
    </div>
    <div class="block-warp">
       <div class="tit">缺货点位</div>
       <div v-for="(itme,index) in siteInfoData">
       <el-row class="row-bcg">
          <el-col :span="8"><div class="name-box">点位编号：{{itme.pointId}}</div></el-col>
          <el-col :span="8"><div class="name-box">点位名称：{{itme.pointName}}</div></el-col>
          <el-col :span="8">
            <div style="text-align: right; margin-top: 3px;"> 
              <el-button type="success" size="mini" @click="getCabinetByPointIdData(itme.pointId)">可视化视图</el-button>
            </div>
          </el-col>
       </el-row>

       <el-table
          :data="itme.supplyInfo"
          style="width: 100%"
          >
          <el-table-column
            prop="productId"
            label="商品编号"
            width="200">
          </el-table-column>
          <el-table-column
            prop="productName"
            label="商品名称"
            >
          </el-table-column>
          <el-table-column
            prop="supplyNum"
            label="缺货数量"
           >
          </el-table-column>
       </el-table>
       </div>
    </div>
  </div>
</template>
<script>

import { getSupplyWarnLineDetail,LinewarnDetail,getCabinetByPointId,addLineSupplyOrder} from '@/api/replenishment'
import NProgress from 'nprogress'

export default {
  data() {
        return {
            cabinetInfo:[],
            activeName:'',
            labelPosition:"right",
            addForm:{},
            input:'',
            formLabelWidth:"",
            checkAll: false,

            isIndeterminate: true,
            currentDate: new Date(),
            input10:'',
            dialogFormVisible:false,
            innerVisible:false,
            value:'',
            options:[],
            tableData3: [],
            siteInfoData:[]
       
        }
  },
  watch: {
    filterText(val) {
      this.$refs.tree2.filter(val)
    }
  },
  created() {
  },
  methods: {
    handleClick(){},
    handleCheckAllChange(val) {
      this.checkedCities = val ? cityOptions : [];
      this.isIndeterminate = false;
    },
    handleCheckedCitiesChange(value) {
      let checkedCount = value.length;
      this.checkAll = checkedCount === this.cities.length;
      this.isIndeterminate = checkedCount > 0 && checkedCount < this.cities.length;
    },
    getSupplyWarnLineDetailData(page){
      var _this = this;
      const listQuery = {
        supplyId:this.$route.query.supplyId,
      }
      getSupplyWarnLineDetail(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
            NProgress.done();
            _this.total = response.zbody.datas.total;  
            _this.tableData3 = response.zbody.datas.sumInfo;
            _this.siteInfoData = response.zbody.datas.siteInfo
          console.log(_this.tableData)
         }else{
             this.$notify({
              title: '失败',
              message: res.zhead.retMsg,
              type: 'error'
            });
         }
      })
    },
    getWarnLineDetail(){
      var _this = this;
      const listQuery = {
        supplyId:this.$route.query.supplyId,
      }
      LinewarnDetail(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){

           NProgress.done();
           this.addForm = response.zbody.datas
            console.log("线路基础信息：：",response.zbody.datas)
         }else{
             this.$notify({
              title: '失败',
              message: res.zhead.retMsg,
              type: 'error'
            });
         }
      })
    },
    getCabinetByPointIdData(pointId){
          var _this = this;
          const listQuery = {
            pointId:pointId
          }
          getCabinetByPointId(listQuery).then(response => {
               if(response.zhead.reTCode==="0000"){
                _this.dialogFormVisible=true
                _this.activeName =response.zbody.datas[0].cabinetId
                _this.cabinetInfo=response.zbody.datas
                console.log("根_thisid查货道信息：：",response.zbody.datas)
                NProgress.done();
               }else{
                   this.$notify({
                    title: '失败',
                    message: res.zhead.retMsg,
                    type: 'error'
                  });
               }
            })
    },
    addto(){
       this.$confirm('确认生成补货单吗？', '提示', {}).then(() => {
        let para = {
          supplyId:this.$route.query.supplyId
        };
        addLineSupplyOrder(para).then((res) => {
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
    }
  },

  mounted () {
    this.getSupplyWarnLineDetailData()
    this.getWarnLineDetail()
  },
  components: {
  
  }
}
</script>
<style scoped>
  .row-bcg{background: #efefef; padding:5px; margin-top: 15px;}
  .name-box{padding:10px 0; text-align: left; text-indent: 4px;  font-size:12px;}
  .sub_title{ background: #efefef; margin-left:9px; font-size:14px; padding:10px 0; text-indent: 10px; }
  .tit{font-size:16px; text-align: left; text-indent:8px; font-weight: bold; color:#006CC1; line-height: 30px;}
  .block-warp{background: #fff; margin: 20px 0; padding:10px; border-radius: 3px;}
  .bntBox{overflow: hidden; padding:0px 0 10px 0;}
  .div-warp{padding:0 20px;  overflow: hidden;}
  .pageBox{background: #fff; padding:20px 0; margin:20px 0;}
  
.layerbottom{text-align: center; overflow: hidden; padding:30px 0;}


  .button {
    padding: 0;
    float: right;
  }

  .image {
    width: 100%;
    display: block;
  }

  .clearfix:before,
  .clearfix:after {
      display: table;
      content: "";
  }
  
  .clearfix:after {
      clear: both
  }
.layerBox{padding:0 30px; min-width: 1200px;}
  .bntBox{text-align: center; margin-top: 30px;}
  .top-20{ margin-top: 20px; }
  .inputStyle{width:215px;}
  .el-autocomplete{width:100%;}
  .div-warp{margin: 20px;}
  .dialog-footer{ padding-left:120px;  }
  .searchBox{ padding: 0; }
  .searchBox strong{ display: inline-block;float: left; line-height: 35px; font-size:14px; font-weight: normal; }
  .searchBox .inputStyle{width: 200px; display: inline-block;float:left; margin-right: 15px; }
  .el-dialog__header{border-bottom: 1px solid #000}
</style>