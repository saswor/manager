<template>
 <div class="div-warp">
    <div class="block-warp">
            <el-tabs v-model="activeName" @tab-click="handleClick">
              <el-tab-pane label="线路告警" name="first">
                 <div class="search-warp">
                    <el-row :gutter="20" >
                        <el-col :span="4">
                          <el-select v-model="searchObj.lineId" size="small" clearable placeholder="请选择线路名称" class="Input-100">
                              <el-option
                                v-for="item in lineList"
                                :key="item.lineId"
                                :label="item.name"
                                :value="item.lineId">
                              </el-option>
                            </el-select>
                        </el-col>
                        
                        <el-col :span="4">
                            <el-autocomplete
                          size="small"
                          class="Input-100"
                          v-model="userName"
                            :value="userName"
                            :fetch-suggestions="querySearchAsync1"
                            value-key="userName"
                            placeholder="搜索补货人员"
                            clearable
                             @focus="handeFocus"
                            @select="handleSelect1"
                          ></el-autocomplete>
                        </el-col>
                        <el-col :span="4">
                            <el-select v-model="searchObj.storyLevel" size="small" clearable placeholder="库存紧急度" class="Input-100">
                                    <el-option
                                      key="3"
                                      label="低"
                                      value="3">
                                    </el-option>
                                    <el-option
                                      key="2"
                                      label="中"
                                      value="2">
                                    </el-option>
                                    <el-option
                                      key="1"
                                      label="高"
                                      value="1">
                                    </el-option>
                                  </el-select>
                        </el-col>
                         <el-col :span="4">
                           <el-select v-model="searchObj.wmId"  size="small" clearable placeholder="仓库编号" class="Input-100">
                              <el-option
                                v-for="item in options"
                                :key="item.stockId"
                                :label="item.stockName"
                                :value="item.stockId">
                              </el-option>
                            </el-select>
                        </el-col>
                         <el-col :span="4">
                             <el-button type="success" @click.native.prevent="searchWarnLine" size="small" v-permission="['sys:supply:warn:select']">查询</el-button>
                        </el-col>
                       
                       
                    </el-row>
                  </div>
                    <div>
                      <el-table
                          :data="tableData"
                          border
                          size="small"
                          style="width: 100%">
                          <el-table-column
                            prop="lineId"
                            :show-overflow-tooltip="true"
                            label="线路编号"
                            width="150">
                          </el-table-column>
                          <el-table-column
                            prop="lineName"
                            :show-overflow-tooltip="true"
                            label="线路名称"
                            width="150">
                          </el-table-column>
                          <el-table-column
                            prop="districtName"
                            :show-overflow-tooltip="true"
                            label="区域名称"
                            width="200">
                          </el-table-column>
                          <el-table-column
                            :show-overflow-tooltip="true"
                            prop="wmName"
                            label="仓库名称"
                          >
                          </el-table-column>
                          <el-table-column
                            prop="waitSPNum"
                            :show-overflow-tooltip="true"
                            label="待补数量"
                            width="120">
                          </el-table-column>
                          <el-table-column
                            label="当前库存率"
                            width="120">
                             <template slot-scope="scope">
                              <div style="text-align: center;">
                              <el-progress color="red" :percentage="scope.row.storyPercent"></el-progress>
                              </div>
                            </template>
                           
                          </el-table-column>
                          <el-table-column
                            prop="lastSTime"
                            :show-overflow-tooltip="true"
                            label="下次补货时间"
                            width="120">
                          </el-table-column>
                          <el-table-column
                            fixed="right"
                            label="操作"
                            width="100">
                            <template scope="scope">
                              <div style="text-align: center;">
                              <el-button type="success"  @click="handleClickUrl(scope.$index, tableData)" icon="el-icon-view" size="mini" circle v-permission="['sys:supply:warn:view']"></el-button>
                              </div>
                            </template>
                          </el-table-column>
                      </el-table>
                      <div class="pageBox">
                        <el-pagination
                          @current-change="handleCurrentChange"
                          background
                          :page-size="pagesize"
                          layout="prev, pager, next"
                          :total="total">
                        </el-pagination>
                      </div>
                    </div>

              </el-tab-pane>
              <el-tab-pane label="点位告警" name="second">
                    <div class="search-warp">
                          <el-row :gutter="20" >
                              <el-col :span="4">
                                  <el-input placeholder="售卖机名称" v-model="PointsearchObj.siteName"  size="small" clearable>
                                  </el-input>
                              </el-col>
                              <el-col :span="4">
                                <el-select v-model="PointsearchObj.storyLevel" size="small" clearable placeholder="库存紧急度" class="Input-100">
                                    <el-option
                                      key="3"
                                      label="低"
                                      value="3">
                                    </el-option>
                                    <el-option
                                      key="2"
                                      label="中"
                                      value="2">
                                    </el-option>
                                    <el-option
                                      key="1"
                                      label="高"
                                      value="1">
                                    </el-option>
                                  </el-select>
                              </el-col>
                               
                               <el-col :span="4">
                                   <el-button type="success" @click.native.prevent="searchPoint" size="small" v-permission="['sys:supply:warn:select']">查询</el-button>
                              </el-col>
                             
                             
                          </el-row>
                    </div>
                    <div>
                      <el-table
                          :data="PointData"
                          size="small"
                          border
                          style="width: 100%">
                          <el-table-column
                            prop="pointName"
                            :show-overflow-tooltip="true"
                            label="点位名称"
                            width="200">
                          </el-table-column>
                          <el-table-column
                            prop="siteName"
                            :show-overflow-tooltip="true"
                            label="售卖机名称"
                            width="150">
                          </el-table-column>
                          <el-table-column
                            prop="districtName"
                            :show-overflow-tooltip="true"
                            label="区域名称"
                            width="200">
                          </el-table-column>
                          <el-table-column
                            :show-overflow-tooltip="true"
                            prop="address"
                            label="归属行政区"
                          >
                          </el-table-column>
                          <el-table-column
                            prop="supplyNum"
                            label="待补数量"
                            width="120">
                          </el-table-column>
                          <el-table-column
                            label="当前库存率"
                            width="120">
                             <template slot-scope="scope">
                              <div style="text-align: center;">
                              <el-progress color="red" :percentage="scope.row.storyPercent"></el-progress>
                              </div>
                            </template>
                           
                          </el-table-column>
                          <el-table-column
                            fixed="right"
                            label="操作"
                            width="100">
                            <template slot-scope="scope">
                              <div style="text-align: center;">
                              <el-button type="success"  @click="handleClickDianUrl(scope.row)" icon="el-icon-view" size="mini" circle></el-button>
                              </div>
                            </template>
                          </el-table-column>
                      </el-table>
                      <div class="pageBox">
                        <el-pagination
                          @current-change="handleCurrentChange1"
                          background
                          :page-size="pagesize1"
                          layout="prev, pager, next"
                          :total="PointTotal">
                        </el-pagination>
                      </div>
                    </div>
              </el-tab-pane>
            </el-tabs>
    </div>
  </div>
</template>
<script>
import { getVendingLine,getStockInfo,getUserByName} from '@/api/dictionaries'
import { getSupplyWarnLine,getSupplyWarnPoint} from '@/api/replenishment'
import NProgress from 'nprogress'
import permission from '@/directive/permission/index.js'
export default {
  directives: { permission },
  data() {
        return {
          form:{},
          activeName2: 'first',
          activeName:'first',
          searchObj:{
            lineId:"",
            supplierId:"",
            storyLevel:"",
            wmId:""
          },
          PointsearchObj:{
            siteName:"",
            storyLevel:""
          },
          options: [],
          value: '',
          radio:'已认证',
          formLabelWidth: '120px',
          dialogAddTableVisible:false,//控制新增弹出显示隐藏
          dialogEditTableVisible:false,//控制编辑弹出显示隐藏
          input10:'',
          lineList:[],
          tableOption:{
            border: true
          },
          tableData: [],
          userName:"",
          total:1,
          PointTotal:1,
          currentPage:1, 
          pagesize:10, 
          currentPage1:1, 
          pagesize1:10,
          filterText: '',
          PointData:[],
          ByNameList:[],
        }
  },
  watch: {
    filterText(val) {
      this.$refs.tree2.filter(val)
    }
  },
  methods: {
    searchWarnLine(){//查询
      this.getSupplyWarnLineList("1")
      console.log('---搜索----')
    },
    searchPoint(){
      this.getSupplyWarnPointList("1")
    },
    deleteRow(index, rows) {//删除数据
      this.$confirm('确认提交吗？', '提示', {}).then(() => {
           rows.splice(index, 1);
      });
    },
    handleAdd() {//显示新增窗口
        this.$router.push(
          {path:'/model/addModel'}
        );
    },
    handleEdit(index, row) {//显示编辑窗口
     this.dialogEditTableVisible= true
     this.editForm = Object.assign({}, row);
    },
    
    
    handleClickUrl(index, rows) {
         this.$router.push(
          {
            path:'/replenishment/outOfStock/seeLine',
            query:{
              supplyId:rows[index].configId
            }
        }
        );
 
    },
    handleClickDianUrl(row) {
        localStorage.setItem("editData",JSON.stringify(row) );
        this.$router.push(
          {
            path:'/replenishment/outOfStock/seeDian',
            query:{siteId:row.siteId,pointId:row.pointId}
          }
        );
    },
    handleClick(tab, event) {

    },
    handleChange(file, fileList) {
      this.fileList3 = fileList.slice(-3);
    },
    handleSizeChange: function (size) { 
       this.pagesize = size; 
    }, 
    handleCurrentChange: function(currentPage){ 
     this.currentPage = currentPage;
     this.getSupplyWarnLineList(currentPage)
    },
    handleCurrentChange1: function(currentPage){ 
     this.currentPage1 = currentPage;
     this.getSupplyWarnPointList(currentPage)
    },
    getSupplyWarnLineList(page){//线路警告列表
      var _this = this;
      this.listLoading = true
      const listQuery = {
        lineId:this.searchObj.lineId,
        supplierId:this.searchObj.supplierId,
        storyLevel:this.searchObj.storyLevel,
        wmId:this.searchObj.wmId,
        pageSize:_this.pagesize,
        pageNum:page,
        orderByColumn:'createtime',
        isAsc:'desc'//desc
      }
      getSupplyWarnLine(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
            NProgress.done();
          _this.total = response.zbody.datas.total;  
          _this.tableData = response.zbody.datas.rows;
          console.log("线路警告列表：：：",_this.tableData)
         }else{
             this.$notify({
              title: '失败',
              message: res.zhead.retMsg,
              type: 'error'
            });
         }
      })
    },
    getSupplyWarnPointList(page){//点位警告列表
      var _this = this;
      this.listLoading = true
      const listQuery = {
        siteName:this.PointsearchObj.siteName,
        storyLevel:this.PointsearchObj.storyLevel,
        pageSize:_this.pagesize1,
        pageNum:page,
        orderByColumn:'createtime',
        isAsc:'desc'//desc
      }
      getSupplyWarnPoint(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
            NProgress.done();
          _this.PointTotal = response.zbody.datas.total;  
          _this.PointData = response.zbody.datas.rows;
          console.log("点位警告列表::",_this.PointData)
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
    getStockInfoList(){//查所有仓库
        var _this = this;
        const listQuery = {
        }
        getStockInfo(listQuery).then(response => {
          if(response.zhead.reTCode==="0000"){

          _this.options = response.zbody.datas.rows;

          console.log("所有仓库",_this.lineList)
          }else{
             this.$notify({
                title: '失败',
                message: res.zhead.retMsg,
                type: 'error'
              });
          }

        })
    },
    //补货员搜索
    querySearchAsync1(queryString, cb) {
        var _this = this;
        const listQuery = {
          userName:_this.userName,
          roleId:3
        }
        getUserByName(listQuery).then(response => {
          _this.ByNameList = response.zbody.datas
          var ByNameList = _this.ByNameList;
          var results = queryString ? ByNameList.filter(this.createStateFilter1(queryString)) : ByNameList;
          cb(results);
        })
    },
    createStateFilter1(queryString) {
        return (state) => {
          return (state.userName.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
        };
    },
    handleSelect1(item) {
        this.searchObj.supplierId = item.loginName
        this.userName = item.userName
        console.log(item);
    },
    handeFocus(item){
      this.userName=""
      this.searchObj.supplierId=""
    },
 
  },

  mounted () {
    this.getSupplyWarnLineList("1")
    this.getSupplyWarnPointList("1")
    this.getVendingLineData()
    this.getStockInfoList()

  },
  components: {
  }
}
</script>
<style scoped>
  .operation-box{padding-top:4px;}
  .screen-box{padding: 0px 0 20px 0}
  .selectBox{padding:10px 0;}
  .selectBox strong{font-size:14px;color:#333; font-weight: normal; display: inline-block; }
  .selectBox .select-left{ padding-left:10px; }
  .searchBox{ padding: 10px 0; }
  .searchBox strong{ display: inline-block;float: left; line-height: 35px; font-size:14px; font-weight: normal; }
  .searchBox .inputStyle{width:220px; display: inline-block;float:left; margin-right: 15px; }
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