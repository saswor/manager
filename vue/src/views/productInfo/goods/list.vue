<template>
 <div class="div-warp">
      <div class="search-warp">
        <el-row :gutter="20" >
          <el-col :span="4">
             <el-select v-model="searchObj.typeId" size="small" clearable style="width:100%" placeholder="商品分类">
                <el-option
                  v-for="item in options"
                  :key="item.classifyId"
                  :label="item.classifyName"
                  :value="item.classifyId">
                </el-option>
              </el-select>
          </el-col>
            <el-col :span="4">
                <el-input
                  placeholder="商品名称"
                  size="small"
                  v-model="searchObj.name"
                  clearable>
                </el-input>
            </el-col>
            <el-col :span="4">
                <el-input
                  placeholder="商品编码"
                  size="small"
                  v-model="searchObj.productCode"
                  clearable>
                </el-input>
            </el-col>
         
            <el-col :span="8">
                <div class="grid-content bg-purple"> 
                  <el-button type="success" size="small" @click.native.prevent="search" v-permission="['sys:product:info:select']">查询</el-button>
                  <el-button type="primary" size="small" @click.native.prevent="handleAdd" v-permission="['sys:product:info:add']">新增</el-button>
                  <el-upload
                  style="display:inline-block; margin:0 8px;"
                  accept=".xls"
                  action="/system/productInfo/importProductExcel"
                  :show-file-list="false"
                  multiple
                  :on-success="handleSuccess"
                  >
                  <el-button size="small" type="warning" v-permission="['sys:product:info:edit']">导入</el-button>
                  
                </el-upload>
                <el-button size="small" type="primary" @click="downLoadModel">下载模板</el-button>
                <el-button size="small" type="warning" @click="exportProduct" v-permission="['sys:product:info:edit']">导出</el-button>
                <el-button type="danger" size="small" @click="deleteRow" v-permission="['sys:product:info:del']">删除</el-button>
              </div>
            </el-col>
        </el-row>
      </div>
      <div class="block-warp">
      <wTable :data="tableData" :header="tableHeader" :option="tableOption" :handleSelectionChange="handleSelectionChange">
          <el-table-column slot="fixed"
            fixed="right"
            label="操作"
            width="150">
              <template  scope="scope">
                <div style="text-align:center;">
                   <el-button type="success" size="mini" icon="el-icon-view" circle @click="handdetails(scope.row)" v-permission="['sys:product:info:viwe']"></el-button>
                  <el-button type="primary" size="mini" icon="el-icon-edit" circle @click="handleEdit(scope.$index, scope.row)" v-permission="['sys:product:info:edit']"></el-button>
                  <el-button type="danger"  size="mini" icon="el-icon-delete" circle @click.native.prevent="deleteRow(scope.row)" v-permission="['sys:product:info:del']"></el-button>
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
import { getList,getProductClassify,removeProductInfo,importExcel,downLoadExcelModel,exportProductExcel } from '@/api/productInfo'
import NProgress from 'nprogress'
import wTable from '@/components/table/w-table.vue'
import permission from '@/directive/permission/index.js'

export default {
  directives: { permission },
  data() {
        return {
          options: [],
          multipleSelection:"",
          value:"",
          keyword:'',
          total:1,
          currentPage:1,
          searchObj:{
            typeId:'',
            name:"",
            productCode:""
          },
          pagesize:10, 
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
                prop: 'name',
                label: '商品名称',
                 minWidth: '150'
             
              },{
                prop: 'productCode',
                label: '商品编码',
                width: '200',
                sortable: true,
                sortMethod: this.handleNameSort
              }, {
                prop: 'typeName',
                label: '商品分类',
                minWidth: '80'
              }, {
                prop: 'spec',
                label: '净含量',
                minWidth: '100'
              }, {
                prop: 'bagTypeName',
                label: '包装形式',
              }, {
                prop: 'factoryName',
                label: '生产厂家',
              }, {
                prop: 'salePrice',
                label: '零售价/元',
              }, {
                prop: 'corpName',
                label: '所属商户',
              }
          ],
          tableData: [],
        }
  },
  watch: {
    
  },
  methods: {  
    exportProduct(){
      var _this=this;
      NProgress.start();
      var para = {
        typeId:_this.searchObj.typeId,//分类
        name:_this.searchObj.name,
        productCode:_this.searchObj.productCode,
      }
      exportProductExcel(para).then((res) => {
        if(res.zhead.reTCode=="0000"){
          window.location.href=res.zhead.retMsg
        NProgress.done();
        }else{
          this.$notify({
            title: '失败',
            message: res.zhead.retMsg,
            type: 'error'
          });
        }
      });
    },
    downLoadModel(){
      var _this=this;
      NProgress.start();
      var para = {}
      downLoadExcelModel(para).then((res) => {
        if(res.zhead.reTCode=="0000"){
          window.location.href=_this.ImgUrl+res.zhead.retMsg
        NProgress.done();
        }else{
          this.$notify({
            title: '失败',
            message: res.zhead.retMsg,
            type: 'error'
          });
        }
      });
    },
    handleSizeChange: function (size) { 
       this.pagesize = size; 
    }, 
    handleCurrentChange: function(currentPage){ 
     this.currentPage = currentPage;
     this.getData(currentPage)
     },
    handleAdd() {//跳转新增
        this.$router.push(
          {path:'/productInfo/goods/add'}
        );
    },
    handleEdit(index, row){//跳转编辑
      this.$router.push({ path: '/productInfo/goods/edit', query: { logid: row.logid } })
    },
    search(){
      this.getData("1")
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
    getData(page) {
      var _this = this;
      const listQuery = {
        typeId:_this.searchObj.typeId,//分类
        name:_this.searchObj.name,
        productCode:_this.searchObj.productCode,
        pageSize:_this.pagesize,
        pageNum:page,
        orderByColumn:'createtime',
        isAsc:'desc'
      }
      getList(listQuery).then(response => {
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
    getProductClass(){
      var _this = this;
      const listQuery = {
        pageSize:"51000",
        pageNum:1,
        orderByColumn:'createtime',
        isAsc:'desc'//desc
      }
      getProductClassify(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
          _this.options = response.zbody.datas.rows;
           NProgress.done();
         }else{
             this.$notify({
              title: '失败',
              message: res.zhead.retMsg,
              type: 'error'
            });
         }
      })
    },
    deleteRow(row) {//删除数据
      const _this  = this;
      this.$confirm('确认提交吗？', '提示', {}).then(() => {
                  let para ={
                    ids:_this.multipleSelection=="" ? row.logid : _this.multipleSelection
                  }
                  removeProductInfo(para).then((res) => {
                        if(res.zhead.reTCode=="0000"){
                        NProgress.done();
                        this.$notify({
                          title: '成功',
                          message: res.zhead.retMsg,
                          type: 'success'
                        });
                        this.getData("1")
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
    handleSuccess(response, file, fileList){
      if(response.zhead.reTCode=="0000"){
        this.$notify({
          title: '成功',
          message: "导入商品成功",
          type: 'success'
        });
        window.location.href=this.ImgUrl+response.zhead.retMsg;
      }else{
          this.$notify({
            title: '失败',
            message: response.zhead.retMsg,
            type: 'error'
          });
       this.getData("1")
      }
    }
  },
  mounted () {
    this.getData("1")
    this.getProductClass()
  },
  components: {
    wTable
  }
}
</script>
<style scoped>
  .div-warp{padding:0 20px;}
  .pageBox{background: #fff; padding:20px 0;}

</style>