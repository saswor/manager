<template>
   <div class="div-warp">
      <div class="search-warp">
        <el-row :gutter="20" >
            <el-col :span="4">
                  <el-input
                  placeholder="角色名称"
                  v-model="searchObj.roleName"
                  clearable>
                </el-input>

            </el-col>
           
            <el-col :span="4">
                  <el-select clearable v-model="searchObj.status" placeholder="角色状态">
                      <el-option
                        key="0"
                        label="正常"
                        value="0">
                      </el-option>
                      <el-option
                        key="1"
                        label="停用"
                        value="1">
                      </el-option>
                </el-select>
            </el-col>
            <el-col :span="6">
                  <el-button type="success" @click.native.prevent="search" v-permission="['sys:base:role:select']">查询</el-button>
                  <el-button type="primary" @click="handleAdd" v-permission="['sys:base:role:add']">新增</el-button>
                  <el-button type="danger" @click="deleteRow" v-permission="['sys:base:role:delete']">删除</el-button>
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
                <el-button type="primary" size="mini" icon="el-icon-edit" circle @click="handleEdit(scope.$index, scope.row)" v-permission="['sys:base:role:edit']"></el-button>
                <el-button type="danger"  size="mini" icon="el-icon-delete" circle @click.native.prevent="deleteRow(scope.$index, tableData)" v-permission="['sys:base:role:delete']"></el-button>
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
import {getRoleList,removeRole} from '@/api/systemSetup'
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
            roleName:"",
            status:""
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
                prop: 'roleName',
                label: '角色名称',
                 minWidth: '150'
             
              }, {
                prop: 'roleKey',
                label: '权限字符',
                minWidth: '150'
              },
              {
                prop: 'statusName',
                label: '状态'
              
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
          {path:'/systemSetup/role/add'}
      );
     
    },
    handleEdit(index, row) {
          if(window.localStorage){
            localStorage.setItem("editData",JSON.stringify(row) );
          }else{

          }
          this.$router.push(
              {path:'/systemSetup/role/edit'}
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
          _this.multipleSelection = rows[index].roleId
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
                  removeRole(para).then((res) => {
                        if(res.zhead.reTCode=="0000"){
                        NProgress.done();
                        this.$notify({
                          title: '成功',
                          message: res.zhead.retMsg,
                          type: 'success'
                        });
                        _this.getRole("1")
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
      this.getRole("1")
    },
    handleSizeChange: function (size) { 
     this.pagesize = size; 
    }, 
    handleCurrentChange: function(currentPage){ 
     this.currentPage = currentPage;
     this.getRole(currentPage)
    } ,
    handleSelectionChange(rows) {//全选
        const _this = this;
        _this.multipleSelection = ""
        if (rows) {
          rows.forEach(row => {
             _this.multipleSelection += row.roleId+ ",";
      
          });
        } 

    },
    getRole(page) {
      var _this = this;
      this.listLoading = true
      const listQuery = {
        roleName:_this.searchObj.roleName,
        status:_this.searchObj.status,
        pageSize:_this.pagesize,
        pageNum:page,
        orderByColumn:'createtime',
        isAsc:'desc'//desc
      }
      getRoleList(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
            NProgress.done();
          _this.total = response.zbody.datas.total;  
          _this.tableData = response.zbody.datas.rows;
          console.log(_this.tableData)
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
    this.getRole("1")
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
