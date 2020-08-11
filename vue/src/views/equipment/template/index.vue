<template>
 <div class="div-warp">
    <div class="search-warp">
        <el-row :gutter="10">
          <el-col :span="4">
            <el-input placeholder="请输入模板名称" v-model="keword"  size="small" clearable>
                      </el-input>
          </el-col>
          <el-col :span="8">
            <el-button type="success" @click.native.prevent="search" size="small" v-permission="['sys:vending:mconfig:select']">查询</el-button>
            <el-button type="primary" @click="handleAdd" size="small" v-permission="['sys:vending:mconfig:add']">新增</el-button>
            <el-button type="danger"  size="small" @click="deleteRow" v-permission="['sys:vending:mconfig:del']">删除</el-button>
          </el-col>
        </el-row>
               
    </div>
    <div class="block-warp">
            <wTable :data="tableData" ref="multipleTable" :header="tableHeader" :handleSelectionChange="handleSelectionChange" :option="tableOption">
              <el-table-column slot="fixed"
                fixed="right"
                label="操作"
                width="180">
                  <template  scope="scope">
                    <div>
                      <el-button type="primary" size="mini" icon="el-icon-edit" circle @click="handleEdit(scope.$index, scope.row)" v-permission="['sys:vending:mconfig:edit']"></el-button>
                      <el-button type="danger"  size="mini" icon="el-icon-delete" circle @click.native.prevent="deleteRow(scope.$index, tableData)" v-permission="['sys:vending:mconfig:del']"></el-button>
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
import wTable from '@/components/table/w-table.vue'
import {findFactoryList,getVendingPconfigList,delVendingPconfig} from '@/api/equipment'
import permission from '@/directive/permission/index.js'
import NProgress from 'nprogress'
export default {
  directives: { permission },
  data() {
        return {
          form:{},
          total:1,
          currentPage:1, 
          pagesize:10, 
          multipleSelection:"",
          formLabelWidth: '120px',
          dialogAddTableVisible:false,//控制新增弹出显示隐藏
          dialogEditTableVisible:false,//控制编辑弹出显示隐藏
          keword:'',
          tableOption:{
            border: true
          },
          tableHeader: [
              {
                type:"selection"
              },
              {
                prop: 'name',
                label: '模版名称',
                width: '178',
                sortable: true,
                sortMethod: this.handleNameSort
              },
              {
                prop: 'factoryName',
                label: '厂家',
                minWidth: '120'
              }, {
                prop: 'cabinetTypeName',
                label: '货柜类型',
                 minWidth: '200'
             
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
             _this.multipleSelection += row.logid+ ",";
      
          });
        } 

    },
    search(){//查询
      this.getList("1")
      console.log('---搜索----')
    },
    deleteRow(index, rows) {//删除数据
      var _this = this;
      if(_this.multipleSelection.length==0 && index.bubbles){
          this.$message({
            message: '请选择要删除的数据',
            type: 'warning'
          });
          return false
      }
      this.$confirm('确认提交吗？', '提示', {}).then(() => {
                let para ={
                    ids:_this.multipleSelection==""||undefined ? rows[index].logid : _this.multipleSelection
                  }
                  delVendingPconfig(para).then((res) => {
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


           //rows.splice(index, 1);
      });
    },
    handleAdd() {//显示新增窗口
        this.$router.push(
          {path:'/template/addTemplate'}
        );
    },
    handleEdit(index, row) {//显示编辑窗口
     localStorage.setItem("editData",JSON.stringify(row) );
     this.$router.push(
        {
          path:'/template/editTemplate',
          query: {
            logid: row.logid
          }
        }
     );
    },
    getList(page) {
      var _this = this;
      const listQuery = {
        name:_this.keword,
        pageSize:_this.pagesize,
        pageNum:page,
        orderByColumn:"createtime",
        isAsc:"desc"
      }
      getVendingPconfigList(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
          _this.total = response.zbody.datas.total;  
          _this.tableData= response.zbody.datas.rows
          NProgress.done();
          console.log("配货模板",response.zbody.datas)
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
    this.getList("1")
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

  .tit{font-size:12px; line-height: 30px;}
  .num-right{display: inline-block;float: right; margin-top: 11px; font-size:12px;}
  .num-right span{ display: inline-block; padding-left: 20px; }
  .num-right span i{ display: inline-block; padding-left:10px; font-style:normal; color:#006BC2; }
  .num-right span i.Danger{color:#F56C6C;}
  .bntBox{overflow: hidden; padding:0px 0 10px 0;}
  .div-warp{padding:0 20px;  overflow: hidden;}
  .pageBox{background: #fff; padding:20px 0; margin:20px 0;}
</style>