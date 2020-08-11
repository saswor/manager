<template>
  
 <div class="div-warp">

   <el-dialog
    title="远程控制"
    :visible.sync="dialogVisible"
    width="120"
    >
    <div class="dialog-footer">
      <el-button size="medium" type="primary" @click="cmd('0101')">系统重启</el-button>
      <el-button size="medium" type="primary" @click="cmd('0102')">应用重启</el-button>
      <el-button size="medium" type="primary" @click="cmd('0103')">下发配置</el-button>
      <el-button size="medium" type="primary" @click="cmd('0104')">停止服务</el-button>
      <el-button size="medium" type="primary" @click="cmd('0105')">恢复服务</el-button>
    </div>
    <el-table
        :data="cmdData"
        style="width: 100%">
        <el-table-column
          type="index"
          label="序号"
          width="100">
        </el-table-column>
        <el-table-column
          prop="cmdId"
          label="命令编号"
          width="240">
        </el-table-column>
        <el-table-column
          prop="cmdName"
          label="命令名称"
          width="160">
        </el-table-column>
        <el-table-column
          prop="stateName"
          label="执行状态"
          width="160">
        </el-table-column>
        <el-table-column
          prop="result"
          label="执行结果"
          width="160">
        </el-table-column>
        <el-table-column
          prop="createTime"
          label="创建时间"
          width="160">
        </el-table-column>
        
      </el-table>

      <div class="pageBox">
        <el-pagination
          @size-change="handleCmdSizeChange"
          @current-change="handleCmdCurrentChange"
          background
          :current-page.sync="currentPageCmd"
          :page-size="pagesizeCmd"
          layout="prev, pager, next"
          :total="totalCmd">
        </el-pagination>
      </div>

  </el-dialog>
    <div class="search-warp">
        <el-row :gutter="10">
          <!-- <el-col :span="4">
                <el-select v-model="searchObj.districtId" clearable  @change="getVendingLineData" size="small" class="searchInput" placeholder="请选择区域">
                  <el-option
                    v-for="item in regionList"
                    :key="item.districtId"
                    :label="item.name"
                    :value="item.districtId">
                  </el-option>
                </el-select>
          </el-col>
          <el-col :span="4">
                <el-select v-model="searchObj.lineId" size="small" clearable @change="getVendingData" class="searchInput" placeholder="请选择线路">
                  <el-option
                  v-for="item in lineList"
                    :key="item.lineId"
                    :label="item.name"
                    :value="item.lineId">
                  </el-option>
                </el-select>
          </el-col>
          <el-col :span="4">
                <el-select v-model="searchObj.siteId" size="small" clearable class="searchInput" placeholder="请选择售卖机">
                  <el-option
                  v-for="item in vendingList"
                    :key="item.siteId"
                    :label="item.siteName"
                    :value="item.siteId">
                  </el-option>
                </el-select>
          </el-col> -->
          <el-col :span="4">
            <el-input v-model="searchObj.site" size="small" placeholder="售货机编号" clearable></el-input>
          </el-col>
          <el-col :span="4">
            <el-input v-model="searchObj.seqId" size="small" placeholder="设备序列号" clearable></el-input>
          </el-col>
          <!-- <el-col :span="4">
                <el-select v-model="searchObj.netSate" clearable size="small" class="searchInput" placeholder="网络状态">
                  <el-option
                  v-for="item in netStateList"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
          </el-col> -->
          <el-col :span="4">
              <el-button type="success" size="small" @click.native.prevent="search" v-permission="['sys:vending:remote:select']">查询</el-button>
              <el-button type="primary" size="small"  @click="exportExcel" v-permission="['sys:vending:remote:edit']">导出</el-button>
          </el-col>

        </el-row>
    </div>
    <div class="block-warp">
          <el-table
            :data="tableData"
            border
            style="width: 100%">
            <el-table-column
              prop="siteId"
              :show-overflow-tooltip="true"
              label="售货机编号"
              width="130">
            </el-table-column>
            <el-table-column
              prop="siteName"
              :show-overflow-tooltip="true"
              label="售货机名称"
              width="180">
            </el-table-column>
            <el-table-column
              prop="seqId"
              :show-overflow-tooltip="true"
              label="设备序列号">
            </el-table-column>
            <el-table-column
              prop="platTypeName"
              :show-overflow-tooltip="true"
              label="系统类型">
            </el-table-column>
            <el-table-column
              prop="vfirmware"
              :show-overflow-tooltip="true"
              label="固件版本">
            </el-table-column>
            <el-table-column
            prop="vbaseband"
              :show-overflow-tooltip="true"
              label="基带版本">
            </el-table-column>
            <el-table-column
              prop="vvcs"
              :show-overflow-tooltip="true"
              label="VCS版本">
            </el-table-column>
            <!-- <el-table-column
              prop="ipAddress"
              :show-overflow-tooltip="true"
              label="IP">
            </el-table-column> -->
            <el-table-column
              prop="signalValue"
              :show-overflow-tooltip="true"
              label="信号值">
            </el-table-column>
            <el-table-column
              prop="iccid"
              :show-overflow-tooltip="true"
              label="ICCID">
            </el-table-column>
            <el-table-column
              prop="netSateName"
              :show-overflow-tooltip="true"
              label="网络状态">
            </el-table-column>
            <!-- <el-table-column
              prop="contime"
              :show-overflow-tooltip="true"
              label="持续时长">
            </el-table-column>
            <el-table-column
              prop="reportTime"
              :show-overflow-tooltip="true"
              label="上报时间">
            </el-table-column> -->
            <el-table-column
              fixed="right"
              label="操作"
              width="200">
               <template scope="scope">
                    <el-button  size="mini" @click.native.prevent="handleSee(scope.row)" v-permission="['sys:vending:remote:view']">查看详情</el-button>
                    <el-button type="danger" size="mini" @click="remoteController(scope.row)" v-permission="['sys:vending:remote:edit']">远程控制</el-button>
                  </template>
            </el-table-column>
          </el-table>
          <div class="pageBox">
            <el-pagination
            v-if="total != 0"
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              background
              :current-page.sync="currentPage"
              :page-size="pagesize"
              layout="prev, pager, next"
              :total="total">
            </el-pagination>
          </div>
      </div>
  </div>
</template>
<script>
import { getVendingLine,getVendingDistrict,getVendingPoint,getVending } from '@/api/dictionaries'
import { getVendingStateList,getVendingCmd,exportVendingState,submitRemoteController } from '@/api/equipment'
import NProgress from 'nprogress'
import wTable from '@/components/table/w-table.vue'
import permission from '@/directive/permission/index.js'
export default {
  directives: { permission },
  data() {
        return {
          total:0,
          currentPage:1, 
          pagesize:10, 
          totalCmd:0,
          currentPageCmd:1,
          pagesizeCmd:10,
          regionList:[],//查询区域下拉
          lineList:[],//线路下拉
          vendingList:[],//售卖机下拉
          reloadFlag:false,//重复加载标志
          tempSiteId:'',
          cmdData:[],
          searchObj:{
            districtId:"",
            lineId:"",
            siteId:"",
            seqId:"",
            netState:""
          },
          dialogVisible:false,
          netStateList: [
            {
              value: '0',
              label: '在线'
            }, 
            {
              value: '1',
              label: '离线'
            },           
            ],
          formLabelWidth: '120px',
          tableData: [],
       
        }
  },
  
  watch: {
    filterText(val) {
      this.$refs.tree2.filter(val)
    }
  },
  methods: {
  handleSelectionChange(){

  },
	//导出
	exportExcel(){
    var _this=this;
        let para = {
          districtId:_this.searchObj.districtId,
          lineId:_this.searchObj.lineId,
          site:_this.searchObj.site,
          netSate:_this.searchObj.netSate,
          seqId:_this.searchObj.seqId,
        }
        exportVendingState(para).then((res) => {
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
    search(){
      this.getVendingStateListData("1")
    },
    handleSee(row) {
        var _this=this
        this.$router.push(
          {
            path:'/equipment/remote/detail',
            query:{
              siteId:row.siteId,
            }
          }
        );
    },
    getVendingCmdData(page){
      var _this = this;
        const listQuery = {
          cmdCode:_this.tempSiteId,
          cmdType:'01',
          pageSize:_this.pagesizeCmd,
          pageNum:page,
          orderByColumn:"createtime",
          isAsc:'desc'
        }
        getVendingCmd(listQuery).then(response => {
          if(response.zhead.reTCode==="0000"){
            _this.cmdData = response.zbody.datas.rows;
            _this.totalCmd = response.zbody.datas.total; 
          }else{
             this.$notify({
                title: '失败',
                message: res.zhead.retMsg,
                type: 'error'
              });
          }

        }) 
    },
    remoteController(row){
      this.tempSiteId=row.siteId;
      this.dialogVisible=true;
      this.getVendingCmdData(1);
    },
    cmd(cmd){
        var _this = this;
        const listQuery = {
          cmdCode:_this.tempSiteId,
          cmdType:'01',
          cmd:cmd,
        }
        submitRemoteController(listQuery).then(response => {
          if(response.zhead.reTCode==="0000"){
            this.$notify({
                title: '成功',
                message: response.zhead.retMsg,
                type: 'success'
              });
            this.getVendingCmdData(this.currentPageCmd);
          }else{
             this.$notify({
                title: '失败',
                message: response.zhead.retMsg,
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
     this.getVendingStateListData(currentPage)
    },
    handleCmdSizeChange: function (size) { 
      this.pagesizeCmd = size; 
    }, 
    handleCmdCurrentChange: function(currentPage){ 
     this.currentPageCmd = currentPage;
     this.getVendingCmdData(currentPage)
    },
    getVendingDistrictData() {//查询所有区域
      var _this = this;
      const listQuery = {
      }
      getVendingDistrict(listQuery).then(response => {
        if(response.zhead.reTCode==="0000"){
        _this.regionList = response.zbody.datas.rows;
        console.log("所有区域",_this.regionList)
        //从详情页调回
        if(_this.reloadFlag){
          _this.getVendingLineData(_this.searchObj.districtId)
        }
        }else{
           this.$notify({
              title: '失败',
              message: response.zhead.retMsg,
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
            //从详情页调回
            if(_this.reloadFlag){
              _this.getVendingData(_this.searchObj.lineId)
            }
          }else{
             this.$notify({
                title: '失败',
                message: response.zhead.retMsg,
                type: 'error'
              });
          }

        })
    },
    getVendingData(){//查询售卖机
        var _this = this;
        const listQuery = {
          lineId:_this.searchObj.lineId
        }
        getVending(listQuery).then(response => {
          if(response.zhead.reTCode==="0000"){
          _this.vendingList = response.zbody.datas.rows;
          console.log("售卖机",_this.vendingList)
          }else{
             this.$notify({
                title: '失败',
                message: response.zhead.retMsg,
                type: 'error'
              });
          }

        })
    },
    getVendingStateListData(page){//设备列表：：：
        this.currentPage=page
        var _this = this;
        const listQuery = {
          districtId:_this.searchObj.districtId,
          lineId:_this.searchObj.lineId,
          site:_this.searchObj.site,
          netSate:_this.searchObj.netSate,
          seqId:_this.searchObj.seqId,
          pageSize:_this.pagesize,
          pageNum:page,
          orderByColumn:"reporttime",
          isAsc:'desc'
        }
        getVendingStateList(listQuery).then(response => {
          if(response.zhead.reTCode==="0000"){
          _this.total = response.zbody.datas.total; 
          _this.tableData = response.zbody.datas.rows;
          }else{
             this.$notify({
                title: '失败',
                message: response.zhead.retMsg,
                type: 'error'
              });
          }

        })
    },
  },
  
  mounted () {
    this.getVendingDistrictData();
    this.getVendingStateListData(this.currentPage)
  },
  created(){

  },
  activated (){
    console.log("缓存加载")
  },
  components: {
    wTable
  }
}
</script>
<style scoped>
  .state_red{color:red}
  .state_green{color:green}
  .state_black{color:black}
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