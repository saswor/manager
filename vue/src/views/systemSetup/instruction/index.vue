<template>
 <div class="div-warp">
      <div class="search-warp">
        <el-row :gutter="20" >
            <el-col :span="4">
                  <el-input
                  placeholder="字典名称"
                  v-model="searchObj.dictLabel"
                  clearable>
                </el-input>

            </el-col>

            <el-col :span="6">
                  <el-button type="success" @click.native.prevent="search" v-permission="['sys:base:instruction:select']">查询</el-button>
            </el-col>
        </el-row>
      </div>
    <div class="block-warp">
            <wTable :data="tableData" ref="multipleTable" :header="tableHeader" :option="tableOption">
              <el-table-column slot="fixed"
                fixed="right"
                label="操作">
                  <template  scope="scope">
                    <div>
                        <el-button type="success" size="mini"  @click="download(scope.row)" v-permission="['sys:base:instruction:view']">下载</el-button>
                        <el-upload
                            class="upload-inline"
                            action="/system/dict/data/upload"
                            :data="param"
                            :show-file-list="false"
                            :before-upload="beforeUpload"
                            :on-success="upSuccess">
                            <el-button type="success" size="mini" @click="chooseRow(scope.row)" v-permission="['sys:base:instruction:edit']">更新</el-button>
                        </el-upload>
                        
                        <!-- <el-button type="success" size="mini"  @click="deleteRow(scope.row)" v-permission="['sys:base:instruction:delete']">删除</el-button> -->
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
import { getMachineSelect } from '@/api/equipment'
import NProgress from 'nprogress'
import wTable from '@/components/table/w-table.vue'
import permission from '@/directive/permission/index.js'
export default {
  directives: { permission },
  data() {
        return {
          total:1,
          PointTotal:1,
          currentPage:1, 
          pagesize:10,
          param:{
            dictCode:"",
          },
          formLabelWidth: '120px',
          searchObj:{dictLabel:''},
          tableOption:{
            border: true
          },
          tableHeader: [
              {
                type: 'index',
                label: '序号 ',
                width: '60'
              },{
                prop: 'dictLabel',
                label: '文件名称 ',
              },{
                prop: 'updateTime',
                label: '更新时间',
              },{
                prop: 'updateByName',
                label: '更新者',
              },{
                prop: 'createTime',
                label: '创建时间',
              },{
                prop: 'createByName',
                label: '创建者',
              }
          ],
          tableData: [],
        }
  },
  watch: {
   
  },
  methods: {
    beforeUpload(file){
        if(file.size>500*1024*1024){
            this.$notify({
            title: '失败',
            message: "上传文件不能大于500m",
            type: 'warning'
            });
            return false;
        }
        // this.param.dictCode=row.dictCode;
    },
    chooseRow(row){
        this.param.dictCode=row.dictCode;
    },
    upSuccess(response, file, fileList){
        if(response.zhead.reTCode==="0000"){
            NProgress.done();
            this.$notify({
              title: '成功',
              message: response.zhead.retMsg,
              type: 'success'
            });
            this.getInstructionListData(this.currentPage)
         }else{
             this.$notify({
              title: '失败',
              message: response.zhead.retMsg,
              type: 'error'
            });
         }
    },
    search(){//查询
      this.getInstructionListData(1)
    },
    download(row){
        var _this = this
        window.location.href=this.ImgUrl+row.dictValue
    },
    getInstructionListData(page){
      var _this = this;
      const listQuery = {
        dictLabel:_this.searchObj.dictLabel,
        dictType:'sys_instruction_address',
        pageSize:_this.pagesize,
        pageNum:page,
        orderByColumn:'dictSort',
        isAsc:'asc'
      }
      getMachineSelect(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
            NProgress.done();
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
    handleSizeChange: function (size) { 
       this.pagesize = size; 
    }, 
    handleCurrentChange: function(currentPage){ 
     this.currentPage = currentPage;
     this.getInstructionListData(currentPage)
    },
  },

  mounted () {
    this.getInstructionListData("1")
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
  .upload-inline{display: inline-block;}
</style>