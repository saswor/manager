<template>
 <div class="div-warp">
      <div class="search-warp" v-if="searchVisible">
        <el-row :gutter="20" >
            <el-col :span="4">
                <el-input placeholder="线路名称" v-model="searchObj.lineName"  size="medium" clearable>
                  </el-input>
            </el-col>
            <el-col :span="4">
                <el-date-picker
                  v-model="searchObj.date"
                  type="month"
                  size="medium" 
                  clearable
                  style="width:100%"
                   value-format="yyyy-MM"
                  placeholder="选择月">
                </el-date-picker>
              </el-col>
            <el-col :span="4">
                  <el-button type="success" size="medium" @click.native.prevent="search" v-permission="['sys:rpt:report:select']">查询</el-button>
                  <el-button type="primary" size="medium" @click="exportLineFile" v-permission="['sys:rpt:report:edit']">导出</el-button>
                
            </el-col>
        </el-row>
      </div>
      <div class="search-warp" v-if="!searchVisible">
        <el-row :gutter="20" >
            <el-col :span="4">
                <el-input placeholder="点位名称" v-model="searchPointObj.pointName"  size="medium" clearable>
                  </el-input>
            </el-col>
            <el-col :span="4">
                <el-date-picker
                  v-model="searchPointObj.date"
                  type="month"
                  size="medium" 
                  clearable
                  style="width:100%"
                   value-format="yyyy-MM"
                  placeholder="选择月">
                </el-date-picker>
              </el-col>
            <el-col :span="4">
                  <el-button type="success" size="medium" @click.native.prevent="searchPoint">查询</el-button>
                  <el-button type="primary" size="medium" @click="exportPointFile">导出</el-button>
                
            </el-col>
        </el-row>
      </div>

      <div class="block-warp">
            <el-tabs v-model="activeName" @tab-click="handleClick">
              <el-tab-pane label="线路分析" name="activeLine">
                  <el-table
                    :data="LineData"
                    style="width: 100%">
                    <el-table-column type="expand">
                      <template slot-scope="props">
                        <el-form label-position="top" inline class="demo-table-expand">
                          <el-form-item v-for="item in props.row.details" :label="item.date">
                            <span>{{item.saleMoney }}</span>
                          </el-form-item>
                        </el-form>
                      </template>
                    </el-table-column>
                    <el-table-column
                      label="线路名称"
                      prop="lineName">
                    </el-table-column>
                    <el-table-column
                      label="点位数量"
                      prop="pointNum">
                    </el-table-column>
                    <el-table-column
                      label="销售额合计"
                      prop="totalSaleMoney">
                    </el-table-column>
                  </el-table>
                  <div class="pageBox">
                    <el-pagination
                      @current-change="handleLineCurrentChange"
                      background
                      :page-size="pagesize"
                      layout="prev, pager, next"
                      :total="total">
                    </el-pagination>
                  </div>
              </el-tab-pane>
              <el-tab-pane label="点位分析" name="activeDian">
                  <el-table
                    :data="pointData"
                    style="width: 100%">
                    <el-table-column type="expand">
                      <template slot-scope="props">
                        <el-form label-position="top" inline class="demo-table-expand">
                          <el-form-item v-for="item in props.row.details" :label="item.date">
                            <span>{{item.saleMoney }}</span>
                          </el-form-item>
                        </el-form>
                      </template>
                    </el-table-column>
                    <el-table-column
                      label="点位名称"
                      prop="pointName">
                    </el-table-column>
                    <el-table-column
                      label="销售额合计"
                      prop="totalSaleMoney">
                    </el-table-column>
                  </el-table>
                  <div class="pageBox">
                    <el-pagination
                      @current-change="handlePointCurrentChange"
                      background
                      :page-size="pagesize"
                      layout="prev, pager, next"
                      :total="total1">
                    </el-pagination>
                  </div>
              </el-tab-pane>
            </el-tabs>
          
    </div>





  </div>
</template>
<script>
import {getLineSaleList,getPointSaleList,exportLine,exportPoint} from '@/api/analysis'
import permission from '@/directive/permission/index.js'
import NProgress from 'nprogress'
export default {
  directives: { permission },
  data() {
        return {
          searchVisible:true,
          activeName:"activeLine",
          LineData: [],
          pointData:[],
          total:1,
          total1:1,
          searchObj:{
            lineName:"",
            date:'',
          },
          searchPointObj:{
            pointName:'',
            date:'',
          },
          currentPage:1, 
          currentPage1:1, 
          pagesize:10, 
          formLabelWidth: '120px',
        }
  },
  methods: {
    handleClick(tab, event) {
      if(tab.name=="activeDian"){
          this.searchVisible  = false;
      }else{
          this.searchVisible  = true;
      }
      console.log(tab, event);
    },
    handleLineCurrentChange: function(currentPage){ 
      this.currentPage = currentPage;
      this.getLineSaleListData(currentPage)
    },
    handlePointCurrentChange: function(currentPage){ 
      this.currentPage1 = currentPage;
      this.getPointSaleListData(currentPage)
    },
   
    search(){
      this.getLineSaleListData("1")
    },
    searchPoint(){
      this.getPointSaleListData("1")
    },
    handleNameSort () {
      console.log('handleNameSort')
    },
    
    getLineSaleListData(page) {
      var _this = this;
      const listQuery = {
       lineName:_this.searchObj.lineName,
       date:_this.searchObj.date,
       pageSize:_this.pagesize,
       pageNum:page,
       orderByColumn:"a.lineid",
       isAsc:"desc"
      }
      getLineSaleList(listQuery).then(response => {
        
         if(response.zhead.reTCode==="0000"){
            NProgress.done();
            _this.total = response.zbody.datas.total
            _this.LineData=response.zbody.datas.rows
         }else{
             this.$notify({
              title: '失败',
              message: res.zhead.retMsg,
              type: 'error'
            });
         }
      })
    },
    getPointSaleListData(page) {
      var _this = this;
      const listQuery = {
       pointName:_this.searchPointObj.pointName,
       date:_this.searchPointObj.date,
       pageSize:_this.pagesize,
       pageNum:page,
       orderByColumn:"a.pointid",
       isAsc:"desc"
      }
      getPointSaleList(listQuery).then(response => {
        
         if(response.zhead.reTCode==="0000"){
            NProgress.done();
            _this.total1 = response.zbody.datas.total
            _this.pointData=response.zbody.datas.rows
         }else{
             this.$notify({
              title: '失败',
              message: res.zhead.retMsg,
              type: 'error'
            });
         }
      })
    },
    exportPointFile(){
      let para = {
          pointName:this.searchPointObj.pointName,
          date:this.searchPointObj.date
      }
      exportPoint(para).then((res) => {
            if(res.zhead.reTCode=="0000"){
            window.location.href=res.zhead.retMsg
            
            
            }else{
              this.$notify({
                title: '失败',
                message: res.zhead.retMsg,
                type: 'error'
              });
          }
      });
    },
    exportLineFile(){
      let para = {
          lineName:this.searchObj.lineName,
          date:this.searchObj.date
      }
      exportLine(para).then((res) => {
            if(res.zhead.reTCode=="0000"){
              window.location.href=res.zhead.retMsg
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
   this.getLineSaleListData("1")
   this.getPointSaleListData("1")
  },
  components: {
  }
}
</script>
<style scoped>
.ys-icon{ display:block; width:15px; height:15px; background:url("../../../assets/images/ys-icon.png"); background-size:100%; float:left; margin:17px 5px 0 0;}
.dateTab{position:absolute; right:10px; top:15px; z-index:10;}
.dateabslute{position:absolute; right:170px; top:15px; z-index:10;}
.bcg{background:#fff; border-radius:3px; padding:0 0px 20px 0px; box-shadow:0 2px 12px 0 rgba(0, 0, 0, 0.1)}
.block-title{ background:#fff; border-radius:3px; border-bottom-left-radius:0px;border-bottom-right-radius:0px; font-size:14px; line-height:50px; color:#333; font-weight:normal; padding-left:10px; }
.block-lv{ background:#fff}
.block-bai{ background:#fff; color:#333;}
.num-Div{text-align:center; color:#fff; position:relative; font-size:35px;}
.block-Title-Div{text-align:center; color:#fff; font-size:14px; margin-top:10px;}

.realTime{ position :absolute; background:rgba(255,255,255,0.5);  border-radius:3px; padding:2px 4px; font-size:12px; z-index:1; right:-10px;; top:-13px;}
.num-Div{text-align:center; color:#fff; position:relative; font-size:35px;}
.block-Title-Div{text-align:center; color:#fff; font-size:14px; margin-top:10px;}
  .div-warp{padding:0 20px;}
  .pageBox{background: #fff; padding:20px 0;}

</style>