<template>
 <div class="div-warp">
    <div class="search-warp">
          <el-row :gutter="20" style="margin-top:20px;">
               <el-col :span="4">
                <el-date-picker
                  v-model="searchObj.sTime"
                  type="datetime"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  size="medium"
                  style="width:100%"
                  placeholder="开始时间">
                </el-date-picker>
              </el-col>
              <el-col :span="4">
                <el-date-picker
                  v-model="searchObj.eTime"
                  type="datetime"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  size="medium"
                  style="width:100%"
                  placeholder="结束时间">
                </el-date-picker>
              </el-col>
              <el-col :span="4">
                <el-select v-model="searchObj.curState" clearable size="medium" class="searchInput" placeholder="状态">
                  <el-option
                  v-for="item in curStateList"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-col>
              <el-col :span="4">
                  <el-button type="success" @click.native.prevent="search" size="medium">查询</el-button>
                  <el-button type="success" @click.native.prevent="upload" size="medium">上传</el-button>
              </el-col>
          </el-row>
    </div>
    <div class="block-warp">
            <wTable :data="tableData" ref="multipleTable" :header="tableHeader"  :option="tableOption">
              <el-table-column slot="fixed"
                fixed="right"
                label="操作">
                  <template  scope="scope">
                    <div>
                      <el-button type="success" size="mini"  @click="download(scope.row)">下载</el-button>

                    </div>
                  </template>
              </el-table-column>
            </wTable>
          
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
  </div>
</template>
<script>
import { vendingLogfileList,addVendingLogfile,downloadVendingLogfile} from '@/api/equipment'
import NProgress from 'nprogress'
import wTable from '@/components/table/w-table.vue'
export default {
  data() {
        return {
          userName:'',
          curStateList: [{
            value: '1',
            label: '等待上传'
            }, {
              value: '2',
              label: '上传中'
            }, {
              value: '3',
              label: '上传完成'
            }
          ],
          searchObj:{
            sTime:'',
            eTime:'',
            curState:''
           
          },
          form:{},
          total:1,
          PointTotal:1,
          currentPage:1, 
          pagesize:10,
          formLabelWidth: '120px',
          dialogAddTableVisible:false,//控制新增弹出显示隐藏
          dialogEditTableVisible:false,//控制编辑弹出显示隐藏
          input10:'',
          tableOption:{
            border: true
          },
          tableHeader: [
              {
                prop: 'fileId',
                label: '日志文件编号 ',
                width: '120',
              },
              {
                prop: 'siteId',
                label: '售货机编号',
                minWidth: '120'
              }, {
                prop: 'siteName',
                label: '售货机名称',
                minWidth: '120'
              }, {
                prop: 'sTime',
                label: '日志开始时间',
                minWidth: '120'
              }, {
                prop: 'eTime',
                label: '日志结束时间',
                minWidth: '120'
              }, {
                prop: 'curStateName',
                label: '状态',
                minWidth: '120'
              }, {
                prop: 'createTime',
                label: '创建时间',
                minWidth: '120'
              }
          ],
          tableData: [],
        }
  },
  watch: {
   
  },
  methods: {
    search(){//查询
      this.getVendingLogfileList(1)
    },
    upload(){
      var _this = this;
      const listQuery = {
        siteId:this.$route.query.siteId,
        sTime:_this.searchObj.sTime,
        eTime:_this.searchObj.eTime,
      }
      addVendingLogfile(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
            NProgress.done();
            this.$notify({
              title: '成功',
              message: response.zhead.retMsg,
              type: 'success'
            });
            _this.searchObj.sTime='';
            _this.searchObj.eTime='';
            _this.getVendingLogfileList(1)
         }else{
             this.$notify({
              title: '失败',
              message: response.zhead.retMsg,
              type: 'error'
            });
         }
      })
    },
    download(row){
      var _this = this;
      const listQuery = {
        fileId:row.fileId
      }
      downloadVendingLogfile(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
            window.location.href=this.ImgUrl+"/"+response.zhead.retMsg
            NProgress.done();
         }else{
             this.$notify({
              title: '失败',
              message: response.zhead.retMsg,
              type: 'error'
            });
         }
         _this.getVendingLogfileList(1)
      })
    },
    getVendingLogfileList(page){
      var _this = this;
      const listQuery = {
        siteId:this.$route.query.siteId,
        sTime:_this.searchObj.sTime,
        eTime:_this.searchObj.eTime,
        curState:_this.searchObj.curState,
        pageSize:_this.pagesize,
        pageNum:page,
        orderByColumn:'createtime',
        isAsc:'desc'//desc
      }
      vendingLogfileList(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
            NProgress.done();
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
    handleSizeChange: function (size) { 
       this.pagesize = size; 
    }, 
    handleCurrentChange: function(currentPage){ 
     this.currentPage = currentPage;
     this.getVendingLogfileList(currentPage)
    },
    getDetail(row) {
      var _this=this
        localStorage.setItem("editData",JSON.stringify(row) );
        this.$router.push(
          {
            path:'/equipment/machine/detail/stockingEdit/detail',
            query:{
              lsdifferId:row.lsdifferId,
              siteId:row.siteId,
              laneSId:row.laneSId,
              laneEId:row.laneEId,
              productId:row.productId
            }
          }
        );
    
    },
  },

  mounted () {
    this.getVendingLogfileList("1")
  },
  components: {
    wTable
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
  .searchBox .inputStyle{width: 300px; display: inline-block;float:left; margin-right: 15px; }
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