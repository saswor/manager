<template>
 <div class="div-warp">
    <!-- 补货单列表 -->
    <el-dialog title="补货单" class="excel" :visible.sync="dialogFormVisible">
      <el-table :data="gridData" size="mini">
        <el-table-column property="productId" label="商品id"  width="200"></el-table-column>
        <el-table-column property="productName" label="商品名称"></el-table-column>
        <el-table-column property="supplyNum" label="缺货数量" width="100"></el-table-column>
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addSubmitForm" size="mini">确认生成</el-button>
        <el-button type="primary" @click.native.prevent="exportExcelPost" size="mini">导出</el-button>
      </span>
    </el-dialog>
    <div class="block-warp">
       <div class="tit" style="margin-bottom:20px;">告警记录信息</div>
          <el-form  label-width="120px"  :inline="true"  >
          <el-form-item label="点位编号" size="small" :label-width="formLabelWidth">
            <el-input v-model="detalsInfo.pointId" disabled auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="点位名称"  size="small" :label-width="formLabelWidth">
            <el-input v-model="detalsInfo.pointName" disabled auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="售卖机编号" size="small" :label-width="formLabelWidth">
            <el-input v-model="detalsInfo.siteId" disabled auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="售卖机名称" size="small" :label-width="formLabelWidth">
            <el-input v-model="detalsInfo.siteName" disabled auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="区域名称" size="small"  :label-width="formLabelWidth">
            <el-input v-model="detalsInfo.districtName" disabled auto-complete="off"></el-input>
          </el-form-item>

          <el-form-item label="归属行政区"  size="small"  :label-width="formLabelWidth">
            <el-input v-model="detalsInfo.dispatchName" disabled auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="缺货紧急度"  prop="corpName" size="small"  :label-width="formLabelWidth">
            <el-input v-model="detalsInfo.warnLevelName" disabled auto-complete="off"></el-input>
          </el-form-item>
           <el-form-item label="待补货数量"  prop="corpName" size="small"  :label-width="formLabelWidth">
            <el-input v-model="detalsInfo.waitSPNum" disabled auto-complete="off"></el-input>
          </el-form-item>
        </el-form>
    </div>
    <div class="block-warp" style="margin-top:20px;">
            <div class="layerBox">
              {{cabinetInfo.cabinetTypeName}}
            </div>
            <div style="margin:auto;">
              <!-- 货道配置列表 -->
                   <el-tabs v-model="activeName" @tab-click="handleClick">
                      <el-tab-pane v-for="(item,index) in cabinetInfo" :label="item.cabinetName" :name="item.cabinetId">
                          <!-- 货道 -->
                          <div class="demo-bs-wrapper">
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
                                    <div  class="blockBox" v-else-if="col.lanep.laneSate==1&&col.lanep.productId!=''||col.lanep.laneSate==3">
                                        <img :src="ImgUrl+col.lanep.pic" >
                                        <div class="bottom clearfix">
                                          <div class="titels">{{col.lanep.productName}}</div>
                                          <div class="price"> ¥<i>{{col.lanep.salePrice}}</i><em>库存{{col.lanep.curCap}}</em></div>
                                        </div>
                                        <div class="InputFStyle" >
                                            <el-input v-model="col.lanep.capacity" size="mini" class="i1" placeholder="容量"></el-input>
                                            <el-input v-model="col.lanep.warnCap" size="mini" class="i2" placeholder="阀值"></el-input>
                                        </div>
                                    </div>
                                  </div>  
                              </el-col>
                            </el-row> 
                          </div>
                        <div style="text-align:right; padding:40px 0;"> <el-button size="medium" @click="getSupplyWarnPointDetai(item)" type="success">生成补货单</el-button></div>
                      </el-tab-pane>
                    
                    </el-tabs>
                 
            </div>
            
    </div>
  
  </div>
</template>
<script>
import {vendingModelList,getCabinetByPointId,SupplyWarnPointDetai,addsupplyOrder,warnDetail,exportExcel} from '@/api/replenishment'
import { getVending} from '@/api/dictionaries'
import XLSX from 'xlsx'
import FileSaver from 'file-saver'
import NProgress from 'nprogress'
export default {
  data() {
        return {
            activeName:'',
            input:'',
            checkAll: false,
            isIndeterminate: true,
            currentDate: new Date(),
            input10:'',
            dialogFormVisible:false,
            value:'',
            options:[],
            gridData: [],
            cabinetInfo:[],
            Lane:[],//货道列表
            addForm:{},
            inFoData:{},
            formLabelWidth:"",
            supplyOrder:[],
            detalsInfo:{}
        }
  },
  watch: {
    filterText(val) {
      this.$refs.tree2.filter(val)
    }
  },
  created() {
    if(window.localStorage){
        this.inFoData = JSON.parse(localStorage.getItem("editData"));
    }
  },
  methods: {
     handleClick(tab, event) {
        console.log(tab, event);
      },
     handleCheckAllChange(val) {
      this.checkedCities = val ? cityOptions : [];
      this.isIndeterminate = false;
    },
    handleCheckedCitiesChange(value) {
      let checkedCount = value.length;
      this.checkAll = checkedCount === this.cities.length;
      this.isIndeterminate = checkedCount > 0 && checkedCount < this.cities.length;
    },
    getCabinetByPointIdData(){
          var _this = this;
          const listQuery = {
            pointId:this.$route.query.pointId
          }
          getCabinetByPointId(listQuery).then(response => {
               if(response.zhead.reTCode==="0000"){
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
    getSupplyWarnPointDetai(row){
          this.supplyOrder = row.lanes
          var _this = this;
          const listQuery = {
            siteId:row.siteId
          }
          SupplyWarnPointDetai(listQuery).then(response => {
               if(response.zhead.reTCode==="0000"){
                 _this.dialogFormVisible = true
                _this.gridData=response.zbody.datas
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
    addSubmitForm() {//提交新增窗口
          var _this = this;
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
                      NProgress.start();
                          var para = {
                            siteId:"",
                            supplyInfo:[]
                          }
                          para.siteId = this.$route.query.siteId
                          this.cabinetInfo.forEach(function(item, index){
                              item.lanes.forEach(function(col,i){
                                    col.cols.forEach(function(e,z){
                                        para.supplyInfo.push(
                                            {
                                              laneSId:e.lanep.laneSId,
                                              laneEId:e.lanep.laneEId,
                                              productId:e.lanep.productId,
                                              productName:e.lanep.productName,
                                              supplyNum:e.lanep.warnCap
                                            }
                                          )
                                    })
                              })

                          })

                          console.log(JSON.stringify(para) )
                      addsupplyOrder(para).then((res) => {
                            if(res.zhead.reTCode=="0000"){
                            NProgress.done();
                            this.centerDialogVisible = false
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
    //查看基础信息
    getWarnDetail(){
          var _this = this;
          const listQuery = {
            siteId:this.$route.query.siteId
          }
          warnDetail(listQuery).then(response => {
               if(response.zhead.reTCode==="0000"){
                _this.detalsInfo=response.zbody.datas
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
    exportExcel () {
         /* generate workbook object from table */
         var wb = XLSX.utils.table_to_book(document.querySelector('.excel'))
         /* get binary string as output */
         var wbout = XLSX.write(wb, { bookType: 'xlsx', bookSST: true, type: 'array' })
         try {
             FileSaver.saveAs(new Blob([wbout], { type: 'application/octet-stream' }), '补货单.xlsx')
         } catch (e) { if (typeof console !== 'undefined') console.log(e, wbout) }
         return wbout
    },
    exportExcelPost(){
          NProgress.start();
          var para = {
            siteId:this.$route.query.siteId,
          }
          exportExcel(para).then((res) => {
                if(res.zhead.reTCode=="0000"){
                  window.location.href=res.zhead.retMsg
                NProgress.done();
                }else{
                  this.$notify({
                    title: '失败',
                    message: res.zhead.retMsg,
                    type: 'error'
                  });
              }
          });
    }

  },

  mounted () {
    this.getCabinetByPointIdData()
    this.getWarnDetail()
  },
  components: {
  
  }
}
</script>
<style scoped>
.demo-bs-wrapper{margin:auto;}
  .row-bcg{background: #efefef; padding:5px; margin-top: 15px;}
  .name-box{padding:10px 0; text-align: left; text-indent: 4px;  font-size:12px;}
  .sub_title{ background: #efefef; margin-left:9px; font-size:14px; padding:10px 0; text-indent: 10px; }
  .tit{font-size:16px; text-align: left; text-indent:8px; font-weight: bold; color:#006CC1; line-height: 30px;}
  .block-warp{background: #fff; padding:10px; border-radius: 3px;}
  .bntBox{overflow: hidden; padding:0px 0 10px 0;}
  .pageBox{background: #fff; padding:20px 0; margin:20px 0;}
  .icon-size-circle{ font-size: 60px; color:red; margin-top: 50px;}
.pageBox{background: #fff; margin-top: 30px;}

.layerbottom{text-align: center; overflow: hidden; padding:30px 0;}
.input-left{width:70px;float:left;}
.input-right{width:70px;float:right;}


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

.layerBox{}
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