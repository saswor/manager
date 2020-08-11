<template>
 <div class="div-warp">
        <el-dialog
                  title="查看点位详情"
                  :visible.sync="dialogVisible"
                  width="50%"
                  >
                 <el-form :model="editForm" class="formWidth" >
                    <el-form-item label="点位编码" prop="code" size="small" :label-width="formLabelWidth">
                      <el-input v-model="editForm.code" auto-complete="off" :disabled="true"></el-input>
                    </el-form-item>
                    <el-form-item label="点位名称" prop="name" size="small" :label-width="formLabelWidth">
                      <el-input v-model="editForm.name" auto-complete="off" :disabled="true"></el-input>
                    </el-form-item>
                    <el-form-item label="线路名称" prop="lineId" size="small" :label-width="formLabelWidth">
                      <el-input v-model="editForm.lineName" auto-complete="off" :disabled="true"></el-input>
                    </el-form-item>
                    <el-form-item label="所属商户"  prop="corpName" size="small"  :label-width="formLabelWidth">
                      <el-input v-model="editForm.corpName" auto-complete="off" :disabled="true"></el-input>
                    </el-form-item>
                    <el-form-item label="所属行政区域" prop="country" size="small" :label-width="formLabelWidth">
                        <el-input v-model="editForm.addresses" auto-complete="off" :disabled="true"></el-input>
                    </el-form-item>
                    <el-form-item label="详细地址" prop="adderss" size="small" :label-width="formLabelWidth">
                      <el-input v-model="editForm.adderss" auto-complete="off" :disabled="true"></el-input>
                    </el-form-item>
                     <el-form-item label="经度" prop="longitude" size="small" :label-width="formLabelWidth">
                      <el-input v-model="editForm.longitude" auto-complete="off" :disabled="true"></el-input>
                    </el-form-item>
                    <el-form-item label="维度" prop="latitude" size="small" :label-width="formLabelWidth">
                      <el-input v-model="editForm.latitude" auto-complete="off" :disabled="true"></el-input>
                    </el-form-item>
                    <el-form-item label="描述" prop="remark" size="small" :label-width="formLabelWidth">
                      <el-input type="textarea" v-model="editForm.description" :disabled="true"></el-input>
                    </el-form-item>
                  </el-form>
      </el-dialog>
      <div class="search-warp">
        <el-row :gutter="24" >
            <el-col :span="4">
                <el-input
                  placeholder="请输入点位"
                  v-model="keyword"
                  size="small"
                  clearable>
                </el-input>
                
         
            </el-col>
             <el-col :span="20">
                <el-button type="success" size="small"  @click.native.prevent="search" v-permission="['sys:point:point:select']">查询</el-button>
                  <el-button type="primary" size="small" @click="handleAdd" v-permission="['sys:point:point:add']">新增</el-button>
                  <el-button type="danger"  size="small" @click="deleteRow" v-permission="['sys:point:point:del']">删除</el-button>
                  <el-upload
                  style="display:inline-block; margin:0 8px;"
                  accept=".xls"
                  action="/system/vendingPoint/importExcel"
                  :show-file-list="false"
                  multiple
                  :on-success="handleSuccess"
                  >
                  <el-button size="small" type="warning" v-permission="['sys:point:point:edit']">导入</el-button>
                  
                </el-upload>
                <el-button size="small" type="primary" @click="downLoadModel" v-permission="['sys:point:point:edit']">下载模板</el-button>
                <el-button size="small" type="warning" @click="exportExcelFile" v-permission="['sys:point:point:edit']">导出</el-button>
             </el-col>
        </el-row>
      </div>
      <div class="block-warp">
      <wTable :data="tableData" :header="tableHeader" :handleSelectionChange="handleSelectionChange" :option="tableOption">
        <el-table-column slot="fixed"
          fixed="right"
          label="操作"
          width="150">
            <template  scope="scope">
              <div style="text-align:center;">
                <el-button type="success" size="mini" icon="el-icon-view" circle @click="handdetails(scope.row)" v-permission="['sys:point:point:view']"></el-button>
                <el-button type="primary" size="mini" icon="el-icon-edit" circle @click="handleEdit(scope.$index, scope.row)" v-permission="['sys:point:point:edit']"></el-button>
                <el-button type="danger"  size="mini" icon="el-icon-delete" circle @click.native.prevent="deleteRow(scope.row)" v-permission="['sys:point:point:del']"></el-button>
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
import { getPositionList,delPosition,getVendingDistrict,getVendingLine,exportPointExcel,downLoadPointExcelModel} from '@/api/pointposition'
import NProgress from 'nprogress'
import permission from '@/directive/permission/index.js'
import wTable from '@/components/table/w-table.vue'
export default {
  directives: { permission },
  data() {
        return {
          keyword:'',
          options:[],
          LineValue:'',//选中的线路
          optionsVlaue:'',//选中的区域
          regionValue:'',//选择区域后的值
          regionList:[],//区域列表
          lineList:[],//所有线路列表
          total:1,
          currentPage:1, 
          pagesize:10, 
          formLabelWidth: '120px',
          dialogVisible:false,
          input10:'',
          multipleSelection:"",
          tableOption:{
            border: true
          },
          tableHeader: [
              {
                type:"selection",
                width:"55"
              },
              {
                prop: 'code',
                label: '点位编码 ',
                width: '78',
                sortable: true,
                sortMethod: this.handleNameSort
              },
              {
                prop: 'name',
                label: '点位名称',
                 minWidth: '150'
             
              }, {
                prop: 'districtName',
                label: '区域名称',
                minWidth: '120'
              }, {
                prop: 'lineName',
                label: '线路名称',
                minWidth: '120'
              }, {
                prop: 'addresses',
                label: '所属行政区',
                minWidth: '200'
              }, {
                prop: 'adderss',
                label: '详细地址',
              }, 
              // {
              //   prop: 'longitude',
              //   label: '经度',
              //   minWidth: '120'
              // },{
              //   prop: 'latitude',
              //   label: '纬度',
              //   minWidth: '120'
              // },
               {
                prop: 'createTime',
                label: '创建时间 ',
                minWidth: '120'
              }
          ],
          tableData: [],
          filterText: '',
          editForm:{}
        }
  },
  watch: {
    filterText(val) {
      this.$refs.tree2.filter(val)
    }
  },
  methods: {
    handleSuccess(response, file, fileList){
      if(response.zhead.reTCode=="0000"){
        this.$notify({
          title: '成功',
          message: "导入excel成功",
          type: 'success'
        });
        window.location.href=this.ImgUrl+response.zhead.retMsg;
        this.getData("1")
      }else{
          this.$notify({
            title: '失败',
            message: response.zhead.retMsg,
            type: 'error'
          });
       this.getData("1")
      }
    },
    exportExcelFile(){
      var _this=this;
      NProgress.start();
      var para = {
        name:_this.keyword,
        districtId:_this.optionsVlaue,
        lineId:_this.LineValue,
      }
      exportPointExcel(para).then((res) => {
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
      downLoadPointExcelModel(para).then((res) => {
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
    handdetails(row){//查看
      this.dialogVisible = true
      this.editForm = Object.assign({}, row);
    },
     handleSizeChange: function (size) { 
     this.pagesize = size; 
     }, 
     handleCurrentChange: function(currentPage){ 

     this.currentPage = currentPage;
     this.getData(currentPage)
     } ,
    handleSelectionChange(rows) {//全选
        const _this = this;
        _this.multipleSelection = ""
        if (rows) {
          rows.forEach(row => {
             _this.multipleSelection += row.pointId+ ",";
      
          });
        } 

    },
    search(){
      this.getData('1')
    },
    deleteRow(row) {//删除数据
      const _this  = this;
      if(_this.multipleSelection.length==0 && row.bubbles){
          this.$message({
            message: '请选择要删除的数据',
            type: 'warning'
          });
          return false
      }
      this.$confirm('确认提交吗？', '提示', {}).then(() => {
                  let para ={
                    ids:_this.multipleSelection=="" ? row.pointId : _this.multipleSelection
                  }
                  delPosition(para).then((res) => {
                        if(res.zhead.reTCode=="0000"){
                        NProgress.done();
                        this.$notify({
                          title: '成功',
                          message: res.zhead.retMsg,
                          type: 'success'
                        });
                        _this.getData("1")
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
          {path:'/pointposition/position/add'}
      );
      //this.dialogAddTableVisible= true
    },
    handleEdit(index, row) {//显示编辑窗口{
          if(window.localStorage){
            localStorage.setItem("editData",JSON.stringify(row) );
          }else{

          }
          this.$router.push(
              {path:'/pointposition/position/edit'}
          );
    // this.dialogEditTableVisible= true
     this.editForm = Object.assign({}, row);
    },
    handleNameSort () {
      console.log('handleNameSort')
    },
    getData(page) {
      var _this = this;
      this.listLoading = true
      const listQuery = {
        name:_this.keyword,
        districtId:_this.optionsVlaue,
        lineId:_this.LineValue,
        pageSize:_this.pagesize,
        pageNum:page,
        orderByColumn:'createtime',
        isAsc:'desc'//desc
      }
      getPositionList(listQuery).then(response => {
        _this.total = response.zbody.datas.total;  
        _this.tableData = response.zbody.datas.rows;
        console.log('查询点位列表',_this.tableData)
      })
    },
    getDistrictData() {
      var _this = this;
      this.listLoading = true
      const listQuery = {
      }
      getVendingDistrict(listQuery).then(response => {
        if(response.zhead.reTCode==="0000"){
        _this.regionList = response.zbody.datas.rows;
        console.log("所有区域",_this.regionList)
        }else{
           this.$notify({
              title: '失败',
              message: res.zhead.retMsg,
              type: 'error'
            });
        }

      })
    },
    getLine(){
        var _this = this;
        this.listLoading = true
        const listQuery = {
        }
        getVendingLine(listQuery).then(response => {

          if(response.zhead.reTCode==="0000"){
          _this.lineList = response.zbody.datas.rows;
          console.log("所有线路",_this.lineList)
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
    this.getData("1")
    this.getDistrictData()
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