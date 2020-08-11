<template>
   <div class="div-warp">
      <div class="search-warp">
        <el-row :gutter="20" >
            <el-col :span="4">
                  <el-input
                  placeholder="商户名称"
                  v-model="searchObj.corpName"
                  clearable>
                </el-input>

            </el-col>
            <el-col :span="4">
                  <el-select clearable v-model="searchObj.curState" placeholder="商户状态">
                      <el-option
                        key="1"
                        label="正常"
                        value="1">
                      </el-option>
                      <el-option
                        key="2"
                        label="停用"
                        value="2">
                      </el-option>
                </el-select>

            </el-col>
            <el-col :span="6">
                  <el-button type="success" @click.native.prevent="search" v-permission="['sys:base:corp:select']">查询</el-button>
                  <el-button type="primary" @click="handleAdd" v-permission="['sys:base:corp:add']">新增</el-button>
                  <el-button type="primary" @click="exportExcel" v-permission="['sys:base:corp:edit']">导出</el-button>
                  <el-button type="danger" @click="deleteRow" v-permission="['sys:base:corp:delete']">删除</el-button>
            </el-col>
        </el-row>
      </div>

      <wTable :data="tableData" :header="tableHeader" :handleSelectionChange="handleSelectionChange" :option="tableOption">
        <el-table-column slot="fixed"
          fixed="right"
          label="操作"
          width="100">
            <template  scope="scope">
              <div>
                <el-button type="primary" size="mini" icon="el-icon-edit" circle @click="handleEdit(scope.$index, scope.row)" v-permission="['sys:base:corp:edit']"></el-button>
                <el-button type="danger"  size="mini" icon="el-icon-delete" circle @click.native.prevent="deleteRow(scope.$index, tableData)" v-permission="['sys:base:corp:delete']"></el-button>
              </div>
            </template>
        </el-table-column>
      </wTable>
        
      <div class="pageBox">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          background
          stripe 
          :page-size="pagesize"
          layout="prev, pager, next"
          :total="total">
        </el-pagination>
      </div>
  </div>
</template>
<script>
import {getCorpList,removeCorp,exportCorpExcel} from '@/api/systemSetup'
import NProgress from 'nprogress'
import wTable from '@/components/table/w-table.vue'
import permission from '@/directive/permission/index.js'
export default {
  directives: { permission },
  name: 'sitemap',
  data() {
      return {
          total:1,
          pagesize:10,
          currentPage:1,
          searchObj:{
            corpName:"",
            curState:""
          },
          multipleSelection:[],
          formLabelWidth: '120px',
          tableOption:{
            border: true
          },
          tableHeader: [
              {
                type:"selection",
                width:"55"
              },
              {
                prop: 'corpName',
                label: '商户名称',
                width: '200',
                sortable: true,
                sortMethod: this.handleNameSort
              },
              {
                prop: 'leader',
                label: '负责人',
                 minWidth: '200'
             
              },
              {
                prop: 'leaderMobile',
                label: '负责人电话',
                 minWidth: '200'
             
              },
              {
                prop: 'tel',
                label: '服务电话',
                 minWidth: '200'
             
              },
              {
                prop: 'address',
                label: '详细地址',
                 minWidth: '200'
             
              },
              {
                prop: 'curStateName',
                label: '状态',
                 minWidth: '200'
             
              },
              {
                prop: 'createTime',
                width:"250",
                label: '创建时间 ',
                minWidth: '100'
              }
          ],
          tableData: [],
      };
  },
  computed: {
   
  },
  created() {
  },
  methods: {
    handleAdd() {//显示新增窗口
      this.$router.push(
          {path:'/systemSetup/corp/add'}
      );
    },
    exportExcel(){
      var _this = this;
      const listQuery = {
        corpName:_this.searchObj.corpName,
        curState:_this.searchObj.curState,
      }
      exportCorpExcel(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
            NProgress.done();
          window.location.href=response.zhead.retMsg;
         }else{
             this.$notify({
              title: '失败',
              message: response.zhead.retMsg,
              type: 'error'
            });
         }
      })
    },    
    handleEdit(index, row) {
      if(window.localStorage){
        localStorage.setItem("editData",JSON.stringify(row) );
      }else{

      }
      this.$router.push(
          {path:'/systemSetup/corp/edit'}
          );
          this.editForm = Object.assign({}, row);
    },
    deleteRow(index, rows) {//删除数据
      const _this  = this;
      if(_this.multipleSelection.length==0 && index.bubbles){
          this.$message({
            message: '请选择要删除的数据',
            type: 'warning'
          });
          return false
      }
      if(_this.multipleSelection=="")
      {
          _this.multipleSelection = rows[index].corpId
      }
      else
      {
          if ( _this.multipleSelection.length > 0) 
          {
            _this.multipleSelection =  _this.multipleSelection.substr(0, _this.multipleSelection.length - 1);
          }
      }
      this.$confirm('确认提交吗？', '提示', {}).then(() => {
                  let para ={
                    ids:_this.multipleSelection
                  }
                  removeCorp(para).then((res) => {
                        if(res.zhead.reTCode=="0000"){
                        NProgress.done();
                        this.$notify({
                          title: '成功',
                          message: res.zhead.retMsg,
                          type: 'success'
                        });
                        _this.getCorp("1")
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
    search(){
      this.getCorp("1")
    },
    handleSizeChange: function (size) { 
     this.pagesize = size; 
    }, 
    handleCurrentChange: function(currentPage){ 
     this.currentPage = currentPage;
     this.getCorp(currentPage)
    } ,
    handleSelectionChange(rows) {//全选
        const _this = this;
        _this.multipleSelection = ""
        if (rows) {
          rows.forEach(row => {
             _this.multipleSelection += row.corpId+ ",";
      
          });
        } 
    },
    getCorp(page) {
      var _this = this;
      const listQuery = {
        corpName:_this.searchObj.corpName,
        curState:_this.searchObj.curState,
        pageSize:_this.pagesize,
        pageNum:page,
        orderByColumn:'createtime',
        isAsc:'desc'
      }
      getCorpList(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
            NProgress.done();
          _this.total = response.zbody.datas.total;  
          _this.tableData = response.zbody.datas.rows;
          console.log("字典列表",_this.tableData)
         }else{
             this.$notify({
              title: '失败',
              message: res.zhead.retMsg,
              type: 'error'
            });
         }
      })
    }
  },
  mounted () {
    this.getCorp("1")
  },
  components: {
   wTable
  },
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>  
  .div-warp{padding:0 20px;}
  .pageBox{background: #fff; padding:20px 0;}
</style>
