<template>
 <div class="div-warp">
    <div class="search-warp">
            <el-row :gutter="10">
              <el-col :span="4">
                <el-select v-model="dictCode" class="searchInput" size="small" clearable placeholder="请选择厂家">
                    <el-option
                      v-for="i in ManufactorOptions"
                      :key="i.dictValue"
                      :label="i.dictLabel"
                      :value="i.dictValue">
                    </el-option>
                </el-select>
              </el-col>
              <el-col :span="8">
                <el-button type="success" @click.native.prevent="search" size="small" v-permission="['sys:vending:device:select']">查询</el-button>
                <el-button type="primary" @click="handleAdd" size="small" v-permission="['sys:vending:device:add']">新增</el-button>
                <el-button type="danger"  size="small" @click="deleteRow" v-permission="['sys:vending:device:del']">删除</el-button>
              </el-col>
            </el-row>
            
          
    </div>
    <div class="block-warp">
            <wTable :data="tableData" ref="multipleTable" :handleSelectionChange="handleSelectionChange" :header="tableHeader"  :option="tableOption">
              <el-table-column slot="fixed"
                fixed="right"
                label="操作"
                width="180">
                  <template  scope="scope">
                    <div>
                      <!-- <el-button type="success" size="mini" icon="el-icon-view" circle></el-button> -->
                      <el-button type="primary" size="mini"icon="el-icon-edit" circle @click="handleEdit(scope.$index, scope.row)" v-permission="['sys:vending:device:edit']"></el-button>
                      <el-button type="danger"  size="mini" icon="el-icon-delete" circle @click.native.prevent="deleteRow(scope.row)" v-permission="['sys:vending:device:del']"></el-button>
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
  </div>
</template>
<script>
import echarts from 'echarts'
import wTable from '@/components/table/w-table.vue'
import {findFactoryList,getVendingModelList,delvendingModel} from '@/api/equipment'
import permission from '@/directive/permission/index.js'
import NProgress from 'nprogress'
export default {
  directives: { permission },
  data() {
        return {
          value: '',
          total:1,
          currentPage:1, 
          pagesize:10, 
          deviceId:"",
          formLabelWidth: '120px',
          dialogAddTableVisible:false,//控制新增弹出显示隐藏
          dialogEditTableVisible:false,//控制编辑弹出显示隐藏
          multipleSelection:"",//全选列表
          ManufactorOptions:[],//厂家列表
          dictCode:'',
          tableOption:{
            border: true
          },
          tableHeader: [
              {
                type:"selection"
              },
              {
                prop: 'deviceId',
                label: '型号 ',
                width: '200',
                sortable: true,
                sortMethod: this.handleNameSort
              },
              {
                prop: 'cabinetTypeName',
                label: '货柜类型',
                minWidth: '200'
             
              }, {
                prop: 'factoryName',
                label: '厂家',
                minWidth: '120'
              }, {
                prop: 'createTime',
                label: '创建时间',
                minWidth: '120'
              }
          ],
          tableData: [],
          filterText: ''
      
        }
  },
  watch: {
    filterText(val) {
      this.$refs.tree2.filter(val)
    }
  },
  methods: {
    importBnt(){
      this.dialogFormVisible= true
    },
    search(){//查询
      this.getList("1")
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
                    ids:_this.multipleSelection =="" ? row.deviceCode : _this.multipleSelection
                  }
                  delvendingModel(para).then((res) => {
                        if(res.zhead.reTCode=="0000"){
                        NProgress.done();
                        this.$notify({
                          title: '成功',
                          message: res.zhead.retMsg,
                          type: 'success'
                        });
                        _this.getList("1")
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
          {path:'/model/addModel'}
        );
    },
    handleEdit(index, row) {//显示编辑窗口
     //this.editForm = Object.assign({}, row);
     if(window.localStorage){
            localStorage.setItem("editData",JSON.stringify(row) );
      }else{

      }
     this.$router.push(
        {path:'/model/editModel'}
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
    resetForm(formName) {//重置表单
        this.$refs[formName].resetFields();
    },
    handleClick(tab, event) {
        console.log(tab, event);
    },
    handleChange(file, fileList) {
      this.fileList3 = fileList.slice(-3);
    },
    getList(page) {
      var _this = this;
      const listQuery = {
        factoryId:_this.dictCode,
        pageSize:_this.pagesize,
        pageNum:page,
        orderByColumn:"createtime",
        isAsc:"desc"
      }
      getVendingModelList(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
          _this.total = response.zbody.datas.total;  
          _this.tableData= response.zbody.datas.rows
          NProgress.done();
          console.log("机型",response.zbody.datas)
         }else{
             this.$notify({
              title: '失败',
              message: res.zhead.retMsg,
              type: 'error'
            });
         }
      })
    },
    getFindFactoryList(){
      var _this = this;
      const listQuery = {
      }
      findFactoryList(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
          _this.ManufactorOptions= response.zbody.datas
          NProgress.done();
          console.log("厂家列表",response.zbody.datas)
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
             _this.multipleSelection += row.deviceCode+ ",";
      
          });
        } 
    },
  },

  mounted () {
    this.getList("1")
    this.getFindFactoryList()
    
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

  .bntBox{overflow: hidden; padding:0px 0 10px 0;}
  .div-warp{padding:0 20px;  overflow: hidden;}
  .pageBox{background: #fff; padding:20px 0; margin:20px 0;}
</style>