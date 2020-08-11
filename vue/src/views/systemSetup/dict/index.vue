<template>
   <div class="div-warp">
      <el-dialog
        title="新增"
        :visible.sync="dialogVisible"
        width="30%"
        >
           <el-form :model="addForm" :rules="Rules" ref="addForm" @submit.prevent="onSubmit" >
            <el-form-item label="字典标签" :label-width="formLabelWidth">
              <el-input v-model="addForm.dictLabel" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="字典键值" :label-width="formLabelWidth">
              <el-input v-model="addForm.dictValue" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="字典类型" :label-width="formLabelWidth">
              <el-input v-model="addForm.dictType" disabled autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="字典排序" :label-width="formLabelWidth">
              <el-input v-model="addForm.dictSort" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="状态" :label-width="formLabelWidth">
              <el-select v-model="addForm.status" placeholder="状态">
                <el-option label="正常" value="0"></el-option>
                <el-option label="禁用" value="1"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="备注" :label-width="formLabelWidth">
              <el-input v-model="addForm.remark" autocomplete="off"></el-input>
            </el-form-item>
          </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="addSubmitForm('addForm')">确 定</el-button>
        </span>
      </el-dialog>
      <el-dialog
        title="编辑"
        :visible.sync="dialogVisibleEdit"
        width="30%"
        >
           <el-form :model="editForm" :rules="Rules" ref="editForm" @submit.prevent="onSubmit" >
            <el-form-item label="字典标签" :label-width="formLabelWidth">
              <el-input v-model="editForm.dictLabel" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="字典键值" :label-width="formLabelWidth">
              <el-input v-model="editForm.dictValue" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="字典类型" :label-width="formLabelWidth">
              <el-input v-model="editForm.dictType" disabled autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="字典排序" :label-width="formLabelWidth">
              <el-input v-model="editForm.dictSort" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="状态" :label-width="formLabelWidth">
              <el-select v-model="editForm.status" placeholder="状态">
                <el-option label="正常" value="0"></el-option>
                <el-option label="禁用" value="1"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="备注" :label-width="formLabelWidth">
              <el-input v-model="editForm.remark" autocomplete="off"></el-input>
            </el-form-item>
          </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisibleEdit = false">取 消</el-button>
          <el-button type="primary" @click="editSubmitForm('editForm')">确 定</el-button>
        </span>
      </el-dialog>
      <div class="search-warp">
        <el-row :gutter="20" >
            <el-col :span="4">
                  <el-input
                  placeholder="字典名称"
                  v-model="searchObj.dictName"
                  clearable>
                </el-input>

            </el-col>
            <el-col :span="4">
                  <el-select clearable v-model="searchObj.status" placeholder="字典状态">
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
                  <el-button type="success" @click.native.prevent="search" v-permission="['sys:base:dict:select']">查询</el-button>
                  <el-button type="primary" @click="handleAdd" v-permission="['sys:base:dict:add']">新增</el-button>
                  <el-button type="danger" @click="deleteRow" v-permission="['sys:base:dict:delete']">删除</el-button>
            </el-col>
        </el-row>
      </div>
       <el-table
          :data="tableData"
          @expand-change="getDistList"
          :handleSelectionChange="handleSelectionChange"
          style="width: 100%">
          <el-table-column type="expand">
            <template slot-scope="props">
              <div>
                 

                   <el-table
                      ref="singleTable"
                      :data="distList"
                      highlight-current-row
                     border
                      style="width: 100%">
                      <el-table-column
                        type="index"
                        width="50">
                      </el-table-column>
                      <el-table-column
                        property="dictCode"
                        label="字典编码"
                        width="200">
                      </el-table-column>
                      <el-table-column
                        property="dictType"
                        label="字典类型"
                        width="200">
                      </el-table-column>
                      <el-table-column
                        property="dictLabel"
                        label="字典标签"
                        width="120">
                      </el-table-column>
                      <el-table-column
                        property="statusName"
                        label="状态">
                      </el-table-column>
                      <el-table-column 
                        fixed="right"
                        label="操作"
                        width="200">
                          <template  scope="scope">
                            <div style="text-align:center">
                              <el-button type="text" size="mini"  @click="handleEditlevel(scope.$index, scope.row)" v-permission="['sys:base:dict:edit']">编辑</el-button>
                              <el-button type="text"  size="mini"  @click.native.prevent="deleteDistlevel(scope.$index, distList)"  v-permission="['sys:base:dict:delete']">删除</el-button>
                            </div>
                          </template>
                    </el-table-column>
                    </el-table>
              </div>
            </template>
          </el-table-column>
           <el-table-column
              type="selection"
              width="55">
            </el-table-column>
          <el-table-column
            label="字典名称"
            prop="dictName">
          </el-table-column>
          <el-table-column
            label="字典类型"
            prop="dictType">
          </el-table-column>
          <el-table-column 
              fixed="right"
              label="操作"
              width="150">
                <template  scope="scope">
                  <div>
                     <el-button type="success" size="mini" icon="el-icon-circle-plus" circle @click="handleAddlevel(scope.$index, scope.row)"> </el-button>
                    <el-button type="primary" size="mini" icon="el-icon-edit" circle @click="handleEdit(scope.$index, scope.row)"></el-button>
                    <el-button type="danger"  size="mini" icon="el-icon-delete" circle @click.native.prevent="deleteRow(scope.$index, tableData)"></el-button>
                  </div>
                </template>
          </el-table-column>
        </el-table>


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
import {getDistList,delDist,getDataCorpList,addDistlevel,editDistlevel,removeDistlevel} from '@/api/systemSetup'
import NProgress from 'nprogress'
import wTable from '@/components/table/w-table.vue'
import permission from '@/directive/permission/index.js'
export default {
  directives: { permission },
  name: 'sitemap',
  data() {
      return {
          dialogVisible:false,
          dialogVisibleEdit:false,
          total:1,
          pagesize:10,
          currentPage:1,
          searchObj:{
            dictName:"",
            status:""
          },
          multipleSelection:[],
          formLabelWidth: '120px',
          tableData: [],
          distList:[],
          dictType:"",
          addForm:{
            dictLabel:'',
            dictValue:'',
            dictType:'',
            dictSort:'',
            status:'',
            remark:''
          },
          editForm:{
            
          },
          Rules:{
              loginName:[
                { required: true, message: ' ', trigger: 'blur' },
              ],
              userName:[
                 { required: true, message: ' ', trigger: 'blur' }
              ],
              password:[
                 { required: true, message: ' ', trigger: 'blur' }
              ]
          }
      };
  },
  computed: {
   
  },
  created() {
  },
  methods: {
    handleAddlevel(index, row){
        this.addForm.dictType = row.dictType
        this.dictType = row.dictType
        this.dialogVisible = true
    },
    handleEditlevel(index, row){
        this.editForm = row
        this.dialogVisibleEdit = true
    },
    editSubmitForm(formName) {//提交新增窗口
        this.$refs[formName].validate((valid) => {
          if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
                      NProgress.start();
                      let para = Object.assign({}, this.editForm);
                      editDistlevel(para).then((res) => {
                            if(res.zhead.reTCode=="0000"){
                            NProgress.done();
                            this.$notify({
                              title: '成功',
                              message: res.zhead.retMsg,
                              type: 'success'
                            });
                            this.dialogVisibleEdit= false
                            this.$refs[formName].resetFields();
                            this.getDistList(_this.dictType,2)
                            }else{
                              this.$notify({
                                title: '失败',
                                message: res.zhead.retMsg,
                                type: 'error'
                              });
                          }
                      });
          });
          } else {
            console.log('error submit!!');
            return false;
          }
        });
    },
    addSubmitForm(formName) {//提交新增窗口
        const _this =  this;
        this.$refs[formName].validate((valid) => {
          if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
                      NProgress.start();
                      let para = Object.assign({}, this.addForm);
                      addDistlevel(para).then((res) => {
                            if(res.zhead.reTCode=="0000"){
                            NProgress.done();
                            this.$notify({
                              title: '成功',
                              message: res.zhead.retMsg,
                              type: 'success'
                            });
                            this.dialogVisible= false
                            this.$refs['addForm'].resetFields();
                            this.getDistList(_this.dictType,2)
                            }else{
                              this.$notify({
                                title: '失败',
                                message: res.zhead.retMsg,
                                type: 'error'
                              });
                          }
                      });
          });
          } else {
            console.log('error submit!!');
            return false;
          }
        });
    },
    handleAdd() {//显示新增窗口
      this.$router.push(
          {path:'/systemSetup/dict/add'}
      );
    },
    handleEdit(index, row) {
          if(window.localStorage){
            localStorage.setItem("editData",JSON.stringify(row) );
          }else{

          }
          this.$router.push(
              {path:'/systemSetup/dict/edit'}
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
          _this.multipleSelection = rows[index].dictId
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
                  delDist(para).then((res) => {
                        if(res.zhead.reTCode=="0000"){
                        NProgress.done();
                        this.$notify({
                          title: '成功',
                          message: res.zhead.retMsg,
                          type: 'success'
                        });
                        _this.getDist("1")
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
    deleteDistlevel(index, rows) {//删除数据
      const _this  = this;
      this.$confirm('确认提交吗？', '提示', {}).then(() => {
                  let para ={
                    ids:rows[index].dictCode
                  }
                  removeDistlevel(para).then((res) => {
                        if(res.zhead.reTCode=="0000"){
                        NProgress.done();
                        this.$notify({
                          title: '成功',
                          message: res.zhead.retMsg,
                          type: 'success'
                        });
                        this.getDistList(_this.dictType,2)
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
      this.getDist("1")
    },
    handleSizeChange: function (size) { 
     this.pagesize = size; 
    }, 
    handleCurrentChange: function(currentPage){ 
     this.currentPage = currentPage;
     this.getDist(currentPage)
    } ,
    handleSelectionChange(rows) {//全选
        const _this = this;
        _this.multipleSelection = ""
        if (rows) {
          rows.forEach(row => {
             _this.multipleSelection += row.dictId+ ",";
      
          });
        } 
    },
    getDist(page) {
      var _this = this;
      const listQuery = {
        dictName:_this.searchObj.dictName,
        status:_this.searchObj.status,
        pageSize:_this.pagesize,
        pageNum:page,
        orderByColumn:'createtime',
        isAsc:'desc'
      }
      getDistList(listQuery).then(response => {
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
    },
    getDistList(row,expandedRows) {
      var _this = this;
      this.dictType = row.dictType==undefined?_this.dictType:row.dictType
      if(expandedRows.length>1){
          expandedRows.shift()
        }
      const listQuery = {
        dictType:_this.dictType
      }
      getDataCorpList(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
            NProgress.done();
            _this.distList = response.zbody.datas.rows;
            console.log("字典列表子",response.zbody.datas.rows)
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
    this.getDist("1")
  },
  components: {
   wTable
  },
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>  
  .div-warp{padding:0 20px;}
  .pageBox{background: #fff; padding:20px 0;}
  .list-title{ text-align:center; background:#efefef; padding:10px 0;}
</style>
