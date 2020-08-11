<template>
 <div class="div-warp">
      <!-- 货道配置 -->
          <div class="layerBox">
          
             <el-tabs v-model="activeName" @tab-click="handleClick">
               
                      <el-tab-pane v-for="(item,index) in cabinetInfo" :label="item.cabinetName" :name="item.cabinetId">
                          <!-- 货道 -->
                          <el-card class="box-card top-20" style="overflow:auto">
                           <el-row type="flex" justify="space-between" v-for="row in item.lanes"  >
                              <el-col :span="4" v-for="col in row.cols">
                                  <div class="imgStyel" >
                                    <!-- 操作区域 -->
                                    <div class="block-top"> 
                                      {{col.laneId}}
                                     
                                    </div>
                                    <!-- 添加按钮 -->
                                    <div  class="add-class blockBox" v-if="col.lanep.laneSate==1&&col.lanep.productId==''">
                                      <i class="el-icon-circle-plus-outline icon-size"  ></i>
                                    </div>
                                    <!-- 禁用 -->
                                    <div  class="add-class blockBox"  v-else-if="col.lanep.laneSate==2">
                                        <i class="el-icon-circle-close-outline icon-size-circle"></i>
                                    </div>
                                    <!-- 有商品 -->
                                    <div  class="blockBox" v-else-if="col.lanep.laneSate==1&&col.lanep.productId!=''||col.lanep.laneSate==3">
                                        <img :src="ImgUrl+col.lanep.pic" >
                                        <div class="bottom clearfix">
                                          <div class="titels" style="position:absolute; bottom:40px;">{{col.lanep.productName}}</div>
                                          <div class="price" style="position:absolute; bottom:5px;"> 
                                              <!-- <el-input size="mini" v-model="col.lanep.resetCap" @change="addToEditList(col.lanep)" class="i1" placeholder="修"></el-input> -->
                                             <el-input v-model="col.lanep.resetCap" @change="addToEditList(col)" @keyup.native="checkInt(scope.row)" size="mini" placeholder="修" style="width:50%"></el-input>
                                             <span style="float:right; margin-right:5px;margin-top:8px; color:#333;font-size:12px;">库存:{{col.lanep.curCap}}</span>
                                          </div>
                                          <el-input v-model="col.lanep.pushState" type="hidden"></el-input>
                                        </div>
                                        <div class="InputFStyle" >
                                            <el-input v-model="col.lanep.capacity" size="mini" class="i1" placeholder="容量" readonly="true"></el-input>
                                            <el-input v-model="col.lanep.warnCap" size="mini" class="i2" placeholder="阀值" readonly="true"></el-input>
                                        </div>
                                    </div>
                                  </div>  
                              </el-col>
                            </el-row>
                          </el-card>
                      </el-tab-pane>
                 
            </el-tabs>

            <div class="bntBox">
                <el-button type="primary" @click="commit">提交</el-button>
            </div>
            
          </div>
    <!-- 货道配置 end -->

       
  </div>
</template>
<script>
 import {getCabinetByPointId} from '@/api/replenishment'
 import {submitStockingEdit} from '@/api/equipment'
 import NProgress from 'nprogress'
export default {
  data() {
        return {
          cabinetInfo:{},
          activeName:"",
          formLabelWidth: '120px',
          lanepList:[],
        }
  },
  watch: {
   
  },
  methods: {
      checkInt: function(row){
        row.pNum=row.pNum.replace(/[^\.\d]/g,'');
        row.pNum=row.pNum.replace('.','');

      },
      handleClick(tab, event) {
        console.log(tab, event);
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
    addToEditList(col){
      var _this =this;
      if(col.lanep.pushState!=true){
        if(col.lanep.resetCap!=null&&col.lanep.resetCap!=undefined){
          _this.lanepList.push(col.lanep)
          col.lanep.pushState=true;
        }        
      }else{
        if(col.lanep.resetCup==null||col.lanep.resetCap==undefined){
          _this.lanepList.pop(col.lanep)
          col.lanep.pushState=false;
        }
      }
    },
    commit(type){
      this.$confirm('确认提交吗？', '提示', {}).then(() => {
        let para = this.lanepList
        submitStockingEdit(para).then((res) => {
              if(res.zhead.reTCode=="0000"){
                  NProgress.done();
                   this.$router.push(
                      {path:'/equipment/machine/index'}
                    );
                  this.$notify({
                    title: '成功',
                    message: res.zhead.retMsg,
                    type: 'success'
                  });
             
              }else{
                NProgress.done();
                this.$router.push(
                  {path:'/equipment/machine/index'}
                );
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
    this.getCabinetByPointIdData()
  },
  components: {

  }
}
</script>
<style scoped>
.layerbottom{text-align: center; overflow: hidden; padding:30px 0;}
.input-left{width:70px;float:left;}
.input-right{width:70px;float:right;}
.block-top{background: #efefef; overflow: hidden;}
.block-top .delBnt{float: right;  margin-right:4px;}
.block-top .checkbox-left{float: left; margin-top: 4px; margin-left: 4px;}
.layerBox{ }
  .top-20{ margin-top: 20px; }
  .inputStyle{width:215px;}
  .el-autocomplete{width:100%;}
  .div-warp{margin: 20px;}
  .dialog-footer{ padding-left:120px;  }
  .searchBox{ padding: 0; }
  .searchBox .inputStyle{width: 200px; display: inline-block;float:left; margin-right: 15px; }
  .el-dialog__header{border-bottom: 1px solid #000}

.layerbottom{text-align: center; overflow: hidden; padding:30px 0; }
.blockBox{ cursor:pointer}


.bottom1 {line-height: 12px; float:right; display:inline-block;}

.bottom1 .titels1{font-size:12px;color:#333;}

  .Bnt-footer{padding:10px 0 0 0;}
.top-20{ margin-top: 20px; }
.inputStyle{width:215px;}
.el-autocomplete{width:100%;}
.div-warp{ margin: 20px; padding:20px 20px 0 20px; border-radius: 3px; background: #fff}
.el-scrollbar__wrap {
  overflow-x: auto;
}
</style>