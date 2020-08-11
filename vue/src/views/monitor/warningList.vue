<template>
 <div class="div-warp">
    <div class="block-warp">
            <div class="search-warp">
                <el-row :gutter="10">
                  <el-col :span="4">
                      <el-select v-model="searchObj.districtId"  @change="getVendingLineData" clearable class="searchInput" size="small" placeholder="请选择区域">
                            <el-option
                              v-for="item in regionList"
                              :key="item.districtId"
                              :label="item.name"
                              :value="item.districtId">
                            </el-option>
                          </el-select>
                  </el-col>
                  <el-col :span="4">
                      <el-select v-model="searchObj.lineId" @change="getVendingPointData" clearable size="small" class="searchInput" placeholder="请选择线路">
                            <el-option
                            v-for="item in lineList"
                              :key="item.lineId"
                              :label="item.name"
                              :value="item.lineId">
                            </el-option>
                          </el-select>
                  </el-col>
                  <el-col :span="4">
                        <el-select v-model="searchObj.pointId" @change="getVendingData" clearable size="small" class="searchInput" placeholder="请选择点位">
                            <el-option
                            v-for="item in pointList"
                              :key="item.pointId"
                              :label="item.name"
                              :value="item.pointId">
                            </el-option>
                          </el-select>
                  </el-col>
                  <el-col :span="4">
                        <el-select v-model="searchObj.siteId" class="searchInput" clearable size="small" placeholder="请选择售卖机">
                            <el-option
                            v-for="item in vendingList"
                              :key="item.siteId"
                              :label="item.siteName"
                              :value="item.siteId">
                            </el-option>
                          </el-select>
                  </el-col>
                  <el-col :span="4">
                    <el-date-picker type="date" clearable style="width:100%" size="small" placeholder="发生时间开始" value-format="yyyy-MM-dd" v-model="searchObj.btime"></el-date-picker>
                  </el-col>
                  <el-col :span="4">
                    <el-date-picker type="date" clearable style="width:100%" size="small" placeholder="发生时间结束" value-format="yyyy-MM-dd"  v-model="searchObj.etime" ></el-date-picker>
                  </el-col>
                </el-row>
            </div>
           
   
            <div class="search-warp" style="padding-top:0px;">
                <el-row :gutter="10">
                  <el-col :span="4">
                        <el-select v-model="searchObj.warnType"  class="searchInput" clearable size="small" placeholder="警告类型">
                            <el-option
                              v-for="item in warnType"
                              :key="item.dictValue"
                              :label="item.dictLabel"
                              :value="item.dictValue">
                            </el-option>
                          </el-select>
                  </el-col>
                  <el-col :span="4">
                    <el-select v-model="searchObj.level" size="small" class="searchInput" clearable placeholder="警告级别">
                            <el-option
                              v-for="item in warnLevel"
                              :key="item.dictValue"
                              :label="item.dictLabel"
                              :value="item.dictValue">
                            </el-option>
                          </el-select>
                  </el-col>
                  <el-col :span="4">
                    <el-select v-model="searchObj.warnState" size="small" class="searchInput" clearable placeholder="警告状态">
                            <el-option
                              v-for="item in warnState"
                              :key="item.dictValue"
                              :label="item.dictLabel"
                              :value="item.dictValue">
                            </el-option>
                          </el-select>
                  </el-col>
                  <el-col :span="4">
                     <el-button type="success" size="small" @click.native.prevent="search" v-permission="['sys:event:event:select']">查询</el-button>
                  </el-col>
                </el-row>
            </div>
            <el-table
              :data="tableData"
              border
              style="width: 100%"
              :header="tableHeader" :handleSelectionChange="handleSelectionChange" >
              <el-table-column
                prop="siteId"
                label="售卖机编号"
                :show-overflow-tooltip="true"
                width="100">
              </el-table-column>
              <el-table-column
                prop="siteName"
                label="售卖机名称"
                :show-overflow-tooltip="true"
                width="100">
              </el-table-column>
              <el-table-column
                prop="pointName"
                label="点位名称"
                :show-overflow-tooltip="true"
               >
              </el-table-column>
              <el-table-column
                prop="pointId"
                label="点位编号"
                :show-overflow-tooltip="true"
                width="100">
              </el-table-column>
              <el-table-column
                prop="lineName"
                label="线路名称"
                :show-overflow-tooltip="true"
                width="120">
              </el-table-column>
              <el-table-column
                prop="warnType"
                label="告警类型"
                width="80">
              </el-table-column>
              <el-table-column
                label="告警级别"
                width="120"
                >
                 <template scope="scope">
                    <el-button round type="danger" size="mini" v-if="scope.row.level=='1'">{{scope.row.levelName}}</el-button>
                    <el-button round type="warning" size="mini" v-else-if="scope.row.level=='2'">{{scope.row.levelName}}</el-button>
                    <el-button round  size="mini" disabled v-else> {{scope.row.levelName}}</el-button>
                  </template>
              </el-table-column>
              <el-table-column
                prop="createTime"
                label="开始时间"
                :show-overflow-tooltip="true"
                width="120">
              </el-table-column>


              <el-table-column
                prop="continuedTime"
                label="持续时间"
                :show-overflow-tooltip="true"
                width="120">
              </el-table-column>

              <el-table-column
                label="状态">
                 <template scope="scope">
                    <div class="state-box" v-if="scope.row.curState=='2'">
                     {{scope.row.curStateName}}<i class="el-icon-success"></i>
                    </div>
                     <div class="state-box" v-else>
                     {{scope.row.curStateName}}
                    </div>
                  </template>
              </el-table-column>
            </el-table>

            <div class="pageBox">
                <el-pagination
                  @size-change="handleSizeChange"
                  @current-change="handleCurrentChange"
                  background
                  :page-size="pagesize"
                  layout="prev, pager, next"
                  :total="total">
                </el-pagination>
            </div>
      </div>
  </div>
</template>
<script>
import { getVendingLine,getVendingDistrict,getVendingPoint,getVending,getWaringSelect } from '@/api/dictionaries'
import { getDictList} from '@/api/monitor'
import permission from '@/directive/permission/index.js'
import NProgress from 'nprogress'
export default {
  directives: { permission },
  data() {
        return {
          input:'',
          form:"",
          regionList:[],//查询区域下拉
          lineList:[],//线路下拉
          pointList:[],//点位下拉
          vendingList:[],//售卖机下拉
          warnType:[],//警告类型
          warnLevel:[],//警告级别
          warnState:[],//警告状态
          dialogFormVisible:false,
          total:1,
          currentPage:1, 
          pagesize:10, 
          searchObj:{
              districtId:"",//区域id
              btime:"",//开始时间
              etime:"",//结束时间
              warnType:'',//警告类型
              warnState:"",//警告状态
              level:"",//警告级别
              lineId:"",//线:""
              pointId:"",//点位名称
              siteId:"",//售货机编号
              isAsc:"asc",//查询升序降序，asc, desc
              pageSize:30,
              pageNum:1

          },
      
          formLabelWidth: '120px',
          tableOption:{
            border: true
          },
          tableHeader: [
              {
               type:"siteId",
                label: '售卖机编号 ',
                width: '120',
                sortable: true,
                sortMethod: this.handleNameSort
              },
              {
                prop: 'siteName',
                label: '售卖机名称',
                 minWidth: '200'
             
              }, {
                prop: 'pointName',
                label: '点位名称',
                minWidth: '120'
              }, {
                prop: 'pointId',
                label: '点位编号',
                minWidth: '120'
              },{
                prop: 'lineName',
                label: '线路名称',
                minWidth: '120'
              }, {
                prop: 'address',
                label: '告警类型',
                minWidth: '150'
              }, {
                prop: 'levelName',
                label: '告警级别',
                minWidth: '120'
              },{
                prop: 'createTime',
                label: '开始时间 ',
                minWidth: '120'
              },{
                prop: 'continuedTime',
                label: '持续时间 ',
                minWidth: '120'
              }

          ],
          tableData: [],
          filterText: '',
        }
  },
  watch: {
    filterText(val) {
      this.$refs.tree2.filter(val)
    }
  },
  methods: {
    search(){//查询
      this.getList("1")
    },
    getList(page) {
      var _this = this;
      const listQuery = {
        districtId:_this.searchObj.districtId,
        lineId:_this.searchObj.lineId,
        pointId:_this.searchObj.pointId,
        siteId:_this.searchObj.siteId,
        btime:_this.searchObj.btime,
        etime:_this.searchObj.etime,
        warnType:_this.searchObj.warnType,
        warnState:_this.searchObj.warnState,
        level:_this.searchObj.level,
        pageSize:_this.pagesize,
        pageNum:page,
        orderByColumn:"createTime",
        isAsc:"asc"
      }
      getDictList(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
          _this.total = response.zbody.datas.total;  
          _this.tableData= response.zbody.datas.rows
          NProgress.done();
          console.log("预警列表",response.zbody.datas)
         }else{
             this.$notify({
              title: '失败',
              message: res.zhead.retMsg,
              type: 'error'
            });
         }
      })
    },
    handleSizeChange: function (size) { 
      this.pagesize = size; 
    }, 
    handleCurrentChange: function(currentPage){ 
     this.currentPage = currentPage;
     this.getList(currentPage)
    },
    handleSelectionChange(rows) {//全选
        const _this = this;
        _this.multipleSelection = ""
        if (rows) {
          rows.forEach(row => {
             _this.multipleSelection += row.logid+ ",";
      
          });
        } 
    },
    getVendingDistrictData() {//查询所有区域
      var _this = this;
      const listQuery = {
      }
      getVendingDistrict(listQuery).then(response => {
        if(response.zhead.reTCode==="0000"){
        _this.regionList = response.zbody.datas.rows;
        console.log("所有区域",_this.regionList)
        }else{
           this.$notify({
              title: '失败',
              message: res.zhead.retMsg,
              type: 'error'
            });
        }

      })
    },
    getVendingLineData(){//查所有线路
        var _this = this;
        const listQuery = {
          districtId:_this.searchObj.districtId
        }
        getVendingLine(listQuery).then(response => {
          if(response.zhead.reTCode==="0000"){
          _this.lineList = response.zbody.datas.rows;
          console.log("所有线路",_this.lineList)
          }else{
             this.$notify({
                title: '失败',
                message: res.zhead.retMsg,
                type: 'error'
              });
          }

        })
    },
    getVendingPointData(){//查询点位
        var _this = this;
        const listQuery = {
          lineId:_this.searchObj.lineId
        }
        getVendingPoint(listQuery).then(response => {
          if(response.zhead.reTCode==="0000"){
          _this.pointList = response.zbody.datas.rows;
          console.log("查询点位",_this.lineList)
          }else{
             this.$notify({
                title: '失败',
                message: res.zhead.retMsg,
                type: 'error'
              });
          }

        })
    },
    getVendingData(){//查询售卖机
        var _this = this;
        const listQuery = {
          pointId:_this.searchObj.pointId
        }
        getVending(listQuery).then(response => {
          if(response.zhead.reTCode==="0000"){
          _this.vendingList = response.zbody.datas.rows;
          console.log("售卖机",_this.vendingList)
          }else{
             this.$notify({
                title: '失败',
                message: res.zhead.retMsg,
                type: 'error'
              });
          }

        })
    },
    getWaringList(type){//警告下拉查询
        var _this = this;
        const listQuery = {
          dictType:type
        }
        getWaringSelect(listQuery).then(response => {
          if(response.zhead.reTCode==="0000"){
          if (type=="sys_warn_type") {
          _this.warnType=response.zbody.datas.rows;//警告类型
         
          }else if(type=="sys_warn_level"){
             _this.warnLevel=response.zbody.datas.rows//警告级别

          }else if(type=="sys_warn_state"){
             _this.warnState=response.zbody.datas.rows//警告状态

          }
        
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
    this.getVendingDistrictData()
    this.getWaringList("sys_warn_type")
    this.getWaringList("sys_warn_level")
    this.getWaringList("sys_warn_state")
    this.getList("1")
  },
  components: {
  }
}
</script>
<style scoped>
  .state-box{text-align: center; }
  .state-box i{color:#37C448; margin-left:5px;}
  .operation-box{padding-top:4px;}
  .screen-box{padding: 0px 0 20px 0}
  .selectBox{padding:10px 0;}
  .selectBox strong{font-size:14px;color:#333; font-weight: normal; display: inline-block; }
  .selectBox .select-left{ padding-left:10px; }
  .selectBox .inputW{width: 150px; }
  .searchBox{ padding: 10px 0; }
  .searchBox strong{ display: inline-block;float: left; line-height: 35px; font-size:14px; font-weight: normal;}
  .tit{font-size:12px; line-height: 30px;}
  .num-right{display: inline-block;float: right; margin-top: 11px; font-size:12px;}
  .num-right span{ display: inline-block; padding-left: 20px; }
  .num-right span i{ display: inline-block; padding-left:10px; font-style:normal; color:#006BC2; }
  .num-right span i.Danger{color:#F56C6C;}
  .block-warp{background: #fff; margin: 20px 0; padding:10px; border-radius: 3px;}
  .bntBox{overflow: hidden; padding:0px 0 10px 0;}
  .div-warp{padding:0 20px;  overflow: hidden;}
  .pageBox{background: #fff; padding:20px 0; margin:20px 0;}
</style>