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
                                      <p class="lane-state">{{col.lanep.laneSateName}}</p>
                                      <!-- <a style="text-align:right" @click.prevent="lock(col.lanep)" v-if="col.lanep.laneSate=='1'||col.lanep.laneSate=='2'">锁定</a>
                                      <a style="text-align:right" @click.prevent="unlock(col.lanep)" v-else>解锁</a> -->
                                      <el-button type="text" v-if="col.lanep.laneSate=='1'||col.lanep.laneSate=='2'" class="delBnt" size="mini" @click="lock(col.lanep)">锁定</el-button>
                                      <el-button type="text" v-else class="delBnt" size="mini" @click="unlock(col.lanep)">解锁</el-button>
                                    </div>
                                    <!-- 添加按钮 -->
                                    <div  class="add-class blockBox" v-if="(col.lanep.laneSate==1||col.lanep.laneSate=='2')&&col.lanep.productId==''">
                                      <i class="el-icon-circle-plus-outline icon-size"  ></i>
                                    </div>
                                    <!-- 禁用 -->
                                    <div  class="add-class blockBox"  v-else-if="col.lanep.laneSate>'2'">
                                        <i class="el-icon-circle-close-outline icon-size-circle"></i>
                                    </div>
                                    <!-- 有商品 -->
                                    <div  class="blockBox" v-else-if="(col.lanep.laneSate==1||col.lanep.laneSate=='2')&&col.lanep.productId!=''">
                                        <img :src="ImgUrl+col.lanep.pic" >
                                        <div class="bottom clearfix">
                                          <div class="titels">{{col.lanep.productName}}</div>
                                          <div class="price"> ¥<i>{{col.lanep.salePrice}}</i>
                                             <span style="float:right; margin-right:5px; color:#333;">库存:{{col.lanep.curCap}}</span>
                                          </div>
                                        </div>
                                        <div class="InputFStyle" >
                                            <el-input v-model="col.lanep.capacity" size="mini" class="i1" placeholder="容量"></el-input>
                                            <el-input v-model="col.lanep.warnCap" size="mini" class="i2" placeholder="阀值"></el-input>
                                        </div>
                                    </div>
                                  </div>  
                              </el-col>
                            </el-row>
                          </el-card>
                      </el-tab-pane>
                 
            </el-tabs>
            
          </div>
    <!-- 货道配置 end -->

       
  </div>
</template>
<script>
 import {getCabinetByPointId} from '@/api/replenishment'
 import {lockLane,unlockLane} from '@/api/equipment'
 import NProgress from 'nprogress'
export default {
  data() {
        return {
          cabinetInfo:{},
          activeName:"",
          formLabelWidth: '120px',
         
        }
  },
  watch: {
   
  },
  methods: {
    //锁定货道
    lock(lanep){
      this.$confirm('确认要锁定该货道吗？', '提示', {}).then(() => {
        var _this = this;
        const listQuery = {
          slaneId:lanep.slaneId,
          laneSId:lanep.laneSId,
          laneEId:lanep.laneEId
        }
        lockLane(listQuery).then(response => {
          if(response.zhead.reTCode==="0000"){
            this.$notify({
                title: '成功',
                message: response.zhead.retMsg,
                type: 'success'
              });
            this.getCabinetByPointIdData();
            NProgress.done();
          }else{
            this.$notify({
              title: '失败',
              message: response.zhead.retMsg,
              type: 'error'
            });
          }
        })
      });
    },
    //解锁货道
    unlock(lanep){
      this.$confirm('确认要锁定该货道吗？', '提示', {}).then(() => {
        var _this = this;
        const listQuery = {
          slaneId:lanep.slaneId,
          laneSId:lanep.laneSId,
          laneEId:lanep.laneEId
        }
        unlockLane(listQuery).then(response => {
          if(response.zhead.reTCode==="0000"){
            this.$notify({
                title: '成功',
                message: response.zhead.retMsg,
                type: 'success'
              });
            this.getCabinetByPointIdData();
            NProgress.done();
          }else{
            this.$notify({
              title: '失败',
              message: response.zhead.retMsg,
              type: 'error'
            });
          }
        })
      });
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
.lane-state{
  font-size: 10px;
  display: inline-block;
}
</style>