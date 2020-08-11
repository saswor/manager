<template>
 <div class="div-warp">
    <div class="search-warp">
      <el-row :gutter="10">
        <el-col :span="4">
          <el-input v-model="searchObj.name" size="small" placeholder="升级名称" clearable></el-input>
        </el-col>
        <el-col :span="4">
            <el-button type="success" size="small" @click.native.prevent="search" v-permission="['sys:vending:upgrade:select']">查询</el-button>
            <el-button type="primary" size="small"  @click="exportOrderFile" v-permission="['sys:vending:upgrade:edit']">导出</el-button>
        </el-col>

      </el-row>
    </div>
    <div class="block-warp">
          <el-table
            :data="tableData"
            border
            style="width: 100%">
            <el-table-column
              prop="name"
              :show-overflow-tooltip="true"
              label="升级名称"
              width="130">
            </el-table-column>
            <el-table-column
              prop="typeName"
              :show-overflow-tooltip="true"
              label="升级类型"
              width="180">
            </el-table-column>
            <el-table-column
              prop="issuedTypeName"
              :show-overflow-tooltip="true"
              label="下发类型">
            </el-table-column>
            <el-table-column
              prop="stateName"
              :show-overflow-tooltip="true"
              label="状态">
            </el-table-column>
            <el-table-column
              prop="planTime"
              :show-overflow-tooltip="true"
              label="升级时间">
            </el-table-column>
            <el-table-column
            prop="createTime"
              :show-overflow-tooltip="true"
              label="创建时间">
            </el-table-column>
            <el-table-column
              fixed="right"
              label="操作"
              width="200">
               <template scope="scope">
                    <el-button  size="mini" @click.native.prevent="handleSee(scope.row)" v-permission="['sys:vending:upgrade:view']">查看详情</el-button>
                    <el-button type="danger" v-if="scope.row.returnType!='2'" size="mini" @click="addReturnMoney(scope.row)" v-permission="['sys:vending:upgrade:edit']">远程控制</el-button>
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
import { getVendingUpgradeList,exportVendingState} from '@/api/equipment'
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
          regionList:[],//查询区域下拉
          lineList:[],//线路下拉
          vendingList:[],//售卖机下拉
          reloadFlag:false,//重复加载标志
          searchObj:{
            districtId:"",
            lineId:"",
            siteId:"",
            seqId:"",
            netState:""
          },
          dialogFormVisible:false,
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
	//导出
	exportOrderFile(){
        let para = {
            orderId:this.searchObj.orderId,
            districtId:this.searchObj.districtId,
            lineId:this.searchObj.lineId,
            siteId:this.searchObj.siteId,
            curState:this.searchObj.curState,
            abnomarlState:this.searchObj.abnomarlState,
            startDate:this.searchObj.startDate,
            endDate:this.searchObj.endDate,
        }
        exportOrder(para).then((res) => {
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
      this.getVendingUpgradeListData("1")
    },
    handleSee(row) {
        var _this=this
        this.$router.push(
          {
            path:'/equipment/upgrade/detail',
            query:{
              upgradeId:row.upgradeId,
            }
          }
        );
    },
    handleSizeChange: function (size) { 
      this.pagesize = size; 
    }, 
    handleCurrentChange: function(currentPage){ 
     this.currentPage = currentPage;
     this.getVendingUpgradeListData(currentPage)
    },
    getVendingUpgradeListData(page){//升级列表：：：
        this.currentPage=page
        var _this = this;
        const listQuery = {
          name:_this.searchObj.name,
          pageSize:_this.pagesize,
          pageNum:page,
          orderByColumn:"createtime",
          isAsc:'desc'
        }
        getVendingStateList(listQuery).then(response => {
          if(response.zhead.reTCode==="0000"){
          _this.total = response.zbody.datas.total; 
          _this.tableData = response.zbody.datas.rows;
          }else{
             this.$notify({
                title: '失败',
                message: res.zhead.retMsg,
                type: 'error'
              });
          }

        })
    },
    addReturnMoney(row) {//确认退款
      const _this = this;
      this.$confirm('确认退款吗？', '提示', {}).then(() => {
                  NProgress.start();
                  let para ={
                    siteId:row.siteId,
                    orderId:row.orderId
                  }
                  returnMoney(para).then((res) => {
                        if(res.zhead.reTCode=="0000"){
                        NProgress.done();
                        this.$notify({
                          title: '成功',
                          message: res.zhead.retMsg,
                          type: 'success'
                        });
                        _this.getOrderApplyListData("1")
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
  },
  
  mounted () {
    this.getVendingUpgradeListData(this.currentPage)
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