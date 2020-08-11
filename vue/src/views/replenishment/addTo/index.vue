<template>
 <div class="div-warp">
    <div class="search-warp">
          <el-row :gutter="20" >
              <el-col :span="4">
                  <el-input placeholder="配置名称" v-model="searchObj.name"  size="small" clearable>
                  </el-input>
              </el-col>
              <el-col :span="4">
                <el-date-picker type="date" size="small" placeholder="开始时间" value-format="yyyy-MM-dd" v-model="searchObj.startTime" style="width:100%"></el-date-picker>
             
              </el-col>
              <el-col :span="4">
                   <el-date-picker type="date" size="small" placeholder="结束时间"  value-format="yyyy-MM-dd"  v-model="searchObj.endTime" style="width:100%"></el-date-picker>
              </el-col>
              <el-col :span="4">
                <el-autocomplete
                size="small"
                class="Input-100"
                :value="userName"
                v-model="userName"
                  :fetch-suggestions="querySearchAsync1"
                  value-key="userName"
                  placeholder="搜索补货人员"
                  clearable
                  @focus="handeFocus1"
                  @select="handleSelect1"
                ></el-autocomplete>
              </el-col>
              <el-col :span="4">
                <el-autocomplete
                  class="Input-100"
                  size="small"
                  v-model="stockName"
                  :fetch-suggestions="querySearchAsync"
                  value-key="stockName"
                  :value="stockName"
                  placeholder="搜索仓库"
                  clearable
                  @focus="handeFocus"
                  @select="handleSelect"
                ></el-autocomplete>
              </el-col>
               <el-col :span="4">
                   <el-button type="success" @click.native.prevent="search" size="small"  v-permission="['sys:supply:config:select']">查询</el-button>

              </el-col>
          </el-row>
          <el-row style="margin-top:20px;">
            <el-col>
              <el-button type="primary" @click.native.prevent="addUrgent" size="small" v-permission="['sys:supply:config:add']">新建补货配置</el-button>
            </el-col>
          </el-row>
    </div>
    <div class="block-warp">
      <wTable :data="tableData" :header="tableHeader"  :option="tableOption">
           <el-table-column
              slot="fixed"
              fixed="right"
              label="操作"
              width="133">
              <template  scope="scope">
                <div>
                  <el-button type="success" size="mini" icon="el-icon-view" circle @click="handleSee(scope.$index, scope.row)" v-permission="['sys:supply:config:view']"></el-button>
                  <el-button type="primary" size="mini"icon="el-icon-edit" circle @click="handleEdit(scope.$index, scope.row)" v-permission="['sys:supply:config:edit']"></el-button>
                  <el-button type="danger"  size="mini" icon="el-icon-delete" circle @click.native.prevent="deleteRow(scope.$index, tableData)" v-permission="['sys:supply:config:del']"></el-button>
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
import { getStockInfo,getUserByName} from '@/api/dictionaries'
import { supplyConfigList,delSupplyConfig} from '@/api/replenishment'
import NProgress from 'nprogress'
import wTable from '@/components/table/w-table.vue'
import permission from '@/directive/permission/index.js'
export default {
  directives: { permission },
 
  data() {
        return {
          form:{},
          activeName2: 'first',
          activeName:'first',
          value: '',
          formLabelWidth: '120px',
          dialogAddTableVisible:false,//控制新增弹出显示隐藏
          dialogEditTableVisible:false,//控制编辑弹出显示隐藏
          input10:'',
          tableOption:{
            border: true
          },
          tableHeader: [
              {
                prop: 'supplyName',
                label: '配置名称',
                 minWidth: '150'
             
              }, {
                prop: 'wmName',
                label: '仓库名称',
                minWidth: '80'
              }, {
                prop: 'supplierName',
                label: '补货员名称',
                minWidth: '100'
              }, {
                prop: 'everyTime',
                label: '每周补货数量',
              }, {
                prop: 'num',
                label: '补货站点数量',
              }, {
                prop: 'createTime',
                label: '配置时间',
              }
          ],
          tableOption:{
            border: true
          },
          lineList:[],
          StockList:[],//仓库列表
          tableData: [],
          total:1,
          timeout:  null,
          PointTotal:1,
          currentPage:1, 
          pagesize:10,
          stockName:"",
          userName:"",
          ByNameList:[],
          searchObj:{
            name:"",
            startTime:"",
            endTime:"",
            wmId:"",
            supplierId:"",
          },
          filterText: '',
          addForm: {
              area_id:'',
              name:'',
              contact:'',
              region: '',
              area: '',
              date2: '',
              delivery: false,
              type: [],
              resource: '',
              desc: ''
          },
          Rules:{
              area_id:[
                { required: true, message: '请输入活动名称', trigger: 'blur' },
                { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
              ],
              area:[
                { required: true, message: '请输入活动名称', trigger: 'blur' },
                { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
              ]
              ,
              name:[
                 { required: true, message: '请选择活负责人姓名', trigger: 'change' }
              ],
              contact:[
                { required: true, message: '请输入活动名称', trigger: 'blur' },
                { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
              ]
          },
          editForm: {
              area_id:'',
              name:'',
              contact:'',
              region: '',
              area: '',
              date2: '',
              delivery: false,
              type: [],
              resource: '',
              desc: ''
          }
        }
  },
  watch: {
    filterText(val) {
      this.$refs.tree2.filter(val)
    }
  },
  methods: {
    addLine(){
       this.$router.push(
          {path:'/replenishment/addTo/addLine'}
        );
    },
    addUrgent(){
        this.$router.push(
          {path:'/replenishment/addTo/addUrgent'}
        );
    },
    search(){//查询
      var startTime = new Date(this.searchObj.startTime).getTime()
      var endTime = new Date(this.searchObj.endTime).getTime()
      if(startTime>endTime){
        this.$message({message:"开始时间不得大于结束时间",type:'warning'});
        this.searchObj.endTime = ""
        this.searchObj.startTime = ""
      }else{
        this.getSupplyConfigList("1")
      }
    },
    deleteRow(index, rows) {//删除数据
      this.$confirm('确认提交吗？', '提示', {}).then(() => {
        let para = {
          supplyId:rows[index].supplyId
        }
        delSupplyConfig(para).then((res) => {
              if(res.zhead.reTCode=="0000"){
                  NProgress.done();
                  rows.splice(index, 1);
                  this.getSupplyConfigList("1")
                  this.$notify({
                    title: '成功',
                    message: res.zhead.retMsg,
                    type: 'success'
                  });
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
     this.$router.push(
          {
            path:'/replenishment/addTo/editUrgent',
            query: {
              supplyId:row.supplyId
            }
          }
        );
    },
    handleSee(index, row) {//查看
     this.$router.push(
          {
            path:'/replenishment/addTo/seeUrgent',
            query: {
              supplyId:row.supplyId
            }
          }
        );
    },
    resetForm(formName) {//重置表单
        this.$refs[formName].resetFields();
    },
    handleClickUrl(tab, event) {
        this.$router.push(
          {path:'/outOfStock/seeLine'}
        );
        console.log(tab, event);
    },
    handleClickDianUrl(tab, event) {
         this.$router.push(
          {path:'/outOfStock/seeDian'}
        );
        console.log(tab, event);
    },
    handleClick(tab, event) {
        console.log(tab, event);
    },
    handleChange(file, fileList) {
      this.fileList3 = fileList.slice(-3);
    },
    handleSizeChange: function (size) { 
       this.pagesize = size; 
    }, 
    handleCurrentChange: function(currentPage){ 
     this.currentPage = currentPage;
     //this.getSupplyWarnLineList(currentPage)
     this.getSupplyConfigList(currentPage)
    },
    getSupplyConfigList(page){
      var _this = this;
      const listQuery = {
        name:_this.searchObj.name,
        startTime:_this.searchObj.startTime,
        endTime:_this.searchObj.endTime,
        wmId:_this.searchObj.wmId,
        supplierId:_this.searchObj.supplierId,
        pageSize:_this.pagesize,
        pageNum:page,
        orderByColumn:'createtime',
        isAsc:'desc'//desc
      }
      supplyConfigList(listQuery).then(response => {
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
    getStockInfoData(name){
      var _this = this;
      const listQuery = {
        stockName:name,
        pageSize:"",
        pageNum:'',
        orderByColumn:'createtime',
        isAsc:'desc'//desc
      }
      getStockInfo(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
            NProgress.done();
          _this.StockList = response.zbody.datas.rows;
         }else{
             this.$notify({
              title: '失败',
              message: res.zhead.retMsg,
              type: 'error'
            });
         }
      })
    },
    querySearchAsync(queryString, cb) {
        var _this = this;
        const listQuery = {
          stockName:_this.stockName
        }
        getStockInfo(listQuery).then(response => {
          _this.StockList = response.zbody.datas.rows
          var StockList = _this.StockList;
          var results = queryString ? StockList.filter(this.createStateFilter(queryString)) : StockList;
          cb(results);
        })

        // clearTimeout(this.timeout);
        // this.timeout = setTimeout(() => {


        //   cb(results);
        // }, 3000 * Math.random());
    },
    createStateFilter(queryString) {
        return (state) => {
          return (state.stockName.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
        };
    },
    handleSelect(item) {
        this.searchObj.wmId =item.stockId
        this.stockName = item.stockName
        console.log(item);
    },
    handeFocus(item){
      this.stockName=""
      this.searchObj.wmId=""
    },

    //补货员搜索
    querySearchAsync1(queryString, cb) {
        var _this = this;
        const listQuery = {
          userName:_this.userName,
          roleId:3
        }
        getUserByName(listQuery).then(response => {
          _this.ByNameList = response.zbody.datas
          var ByNameList = _this.ByNameList;
          var results = queryString ? ByNameList.filter(this.createStateFilter1(queryString)) : ByNameList;
          cb(results);
        })
    },
    createStateFilter1(queryString) {
        return (state) => {
          return (state.userName.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
        };
    },
    handleSelect1(item) {
        this.searchObj.supplierId = item.loginName
        this.userName = item.userName
        console.log(item);
    },
    handeFocus1(item){
      this.userName=""
      this.searchObj.supplierId=""
    },
  },

  mounted () {
    this.getSupplyConfigList("1")
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
  .searchBox .inputStyle{width:220px; display: inline-block;float:left; margin-right: 15px; }
  .tit{font-size:12px; line-height: 30px;}
  .num-right{display: inline-block;float: right; margin-top: 11px; font-size:12px;}
  .num-right span{ display: inline-block; padding-left: 20px; }
  .num-right span i{ display: inline-block; padding-left:10px; font-style:normal; color:#006BC2; }
  .num-right span i.Danger{color:#F56C6C;}
  .block-warp{background: #fff;  padding:10px; border-radius: 3px;}
  .bntBox{overflow: hidden; padding:0px 0 10px 0;}
  .div-warp{padding:0 20px;  overflow: hidden;}
  .pageBox{background: #fff; padding:20px 0; margin:20px 0;}
</style>