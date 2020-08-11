<template>
 <div class="div-warp">
    <div class="search-warp">
      <el-row :gutter="10">
        <el-col :span="4">
          <el-input v-model="searchObj.vName" size="small" placeholder="版本号" clearable></el-input>
        </el-col>
        <el-col :span="4">
            <el-button type="success" size="small" @click.native.prevent="search" v-permission="['sys:vending:version:select']">查询</el-button>
            <el-button type="primary" @click="handleAdd" size="small" v-permission="['sys:vending:version:add']">新增</el-button>
            <el-button type="danger"  size="small" @click="deleteRow" v-permission="['sys:vending:version:del']">删除</el-button>
        </el-col>

      </el-row>
    </div>
    <div class="block-warp">
          <el-table
            :data="tableData"
            border
            @selection-change="handleSelectionChange"
            style="width: 100%">
           <el-table-column
              type="selection"
              width="55">
            </el-table-column>
            <el-table-column
              prop="vName"
              :show-overflow-tooltip="true"
              label="版本号"
              width="130">
            </el-table-column>
            <el-table-column
              prop="uTypeName"
              :show-overflow-tooltip="true"
              label="类型"
              width="180">
            </el-table-column>
            <el-table-column
              prop="fileNum"
              :show-overflow-tooltip="true"
              label="文件数">
            </el-table-column>
            <el-table-column
              prop="description"
              :show-overflow-tooltip="true"
              label="描述">
            </el-table-column>
            <el-table-column
              prop="size"
              :show-overflow-tooltip="true"
              label="文件大小(M)">
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
                  <el-button type="primary" size="mini" icon="el-icon-edit" circle @click="handleEdit(scope.$index, scope.row)" v-permission="['sys:vending:version:edit']"></el-button>
                  <el-button type="danger"  size="mini" icon="el-icon-delete" circle @click.native.prevent="deleteRow(scope.row)" v-permission="['sys:vending:version:del']"></el-button>
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
import { getVendingVersionList,deleteVendingVersion} from '@/api/equipment'
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
          multipleSelection:[],
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
      this.getVendingVersionListData(1)
    },
    handleSelectionChange(rows) {//全选
        const _this = this;
        _this.multipleSelection = ""
        if (rows) {
          rows.forEach(row => {
             _this.multipleSelection += row.versionId+ ",";
      
          });
        } 

    },
    deleteRow(row) {//删除数据
      let _this = this;
      if(_this.multipleSelection.length==0 && row.bubbles){
          this.$message({
            message: '请选择要删除的数据',
            type: 'warning'
          });
          return false
      }
      this.$confirm('确认提交吗？', '提示', {}).then(() => {
                  let para ={
                    ids:_this.multipleSelection =="" ? row.versionId : _this.multipleSelection
                  }
                  deleteVendingVersion(para).then((res) => {
                        if(res.zhead.reTCode=="0000"){
                        NProgress.done();
                        this.$notify({
                          title: '成功',
                          message: res.zhead.retMsg,
                          type: 'success'
                        });
                        _this.getVendingVersionListData(1)
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
    handleAdd() {//显示新增窗口
        this.$router.push(
          {path:'/equipment/version/addVersion'}
        );
    },
    handleEdit(index, row) {//显示编辑窗口
     this.$router.push(
        {
          path:'/equipment/version/editVersion',
          query:{
            versionId:row.versionId
          }
        }
      );
    },
    addSubmitForm(formName) {//提交新增窗口
        this.$refs[formName].validate((valid) => {
          if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {


          });
            //this.dialogAddTableVisible = false
          } else {
            console.log('error submit!!');
            return false;
          }
        });
    },
    editSubmitForm(formName) {//提交新增窗口
        this.$refs[formName].validate((valid) => {
          if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {


          });
            //this.dialogAddTableVisible = false
          } else {
            console.log('error submit!!');
            return false;
          }
        });
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
     this.getVendingVersionListData(currentPage)
    },
    getVendingVersionListData(page){//升级列表：：：
        this.currentPage=page
        var _this = this;
        const listQuery = {
          vName:_this.searchObj.vName,
          pageSize:_this.pagesize,
          pageNum:page,
          orderByColumn:"createtime",
          isAsc:'desc'
        }
        getVendingVersionList(listQuery).then(response => {
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
    this.getVendingVersionListData(this.currentPage)
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