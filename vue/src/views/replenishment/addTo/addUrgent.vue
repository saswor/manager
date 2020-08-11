<template>
 <div class="div-warp">
      <div class="block-warp">
            <el-dialog title="选择关联线路"  width="60%" :visible.sync="dialogAddTableVisible">
              <div class="LayersearchBox">
                    <el-select v-model="linSearchObj.districtId" clearable class="inputStyle" size="mini"  placeholder="请选择区域">
                      <el-option
                        v-for="item in regionList"
                        :key="item.districtId"
                        :label="item.name"
                        :value="item.districtId">
                      </el-option>
                    </el-select>
                    <el-button type="success" @click.native.prevent="search" size="mini">查询</el-button>
              </div>
               <el-table
                    :data="tableData"
                    :header-cell-style="{background:'#efefef'}"
                    style="width: 100%; margin-top:20px;">
                    </el-table-column>
                    <el-table-column
                      :show-overflow-tooltip="true"
                      prop="lineId"
                      label="线路编号"
                      width="180">
                    </el-table-column>
                    <el-table-column
                      :show-overflow-tooltip="true"
                      prop="name"
                      label="线路名称"
                      width="180">
                    </el-table-column>
                    <el-table-column
                      :show-overflow-tooltip="true"
                      prop="districtName"
                      label="区域名称">
                    </el-table-column>
                    <el-table-column
                      :show-overflow-tooltip="true"
                      prop="createTime"
                      label="创建时间">
                    </el-table-column>
                    <el-table-column
                      label="操作">
                       <template  scope="scope">
                        <div>
                          <el-button type="primary" size="mini"  @click.native.prevent="Relation(scope.$index, tableData)">关联</el-button>
                        </div>
                      </template>
                    </el-table-column>
              </el-table>
             <div class="pageBox">
                <el-pagination
                  @current-change="handleCurrentChange"
                  background
                  :page-size="pagesize"
                  layout="prev, pager, next"
                  :total="total">
                </el-pagination>
              </div>
            </el-dialog>
            <div class="search-warp">
                  <el-row :gutter="20" class="row-top">
                      <el-col :span="3">
                        <div class="labelstyle">配置名称</div>
                      </el-col>
                      <el-col :span="6">
                          <el-input placeholder="配置名称" v-model="addForm.name"  size="medium" clearable>
                          </el-input>
                      </el-col>
                  </el-row>
                  <el-row :gutter="20" class="row-top">
                      <el-col :span="3">
                        <div class="labelstyle">描述</div>
                      </el-col>
                       <el-col :span="6">
                          <el-input placeholder="描述" v-model="addForm.description"  size="medium" clearable>
                          </el-input>
                      </el-col>
                  </el-row>
                  <el-row :gutter="20" class="row-top">
                      <el-col :span="3">
                        <div class="labelstyle">仓库</div>
                      </el-col>
                     <el-col :span="6">
                          <el-autocomplete
                          size="medium"
                          style="width:100%;"
                          v-model="stockName"
                          :fetch-suggestions="querySearchAsync"
                           value-key="stockName"
                          placeholder="搜索仓库"
                          clearable
                          @select="handleSelect"
                        ></el-autocomplete>
                      </el-col>
                  </el-row>
                  <el-row :gutter="20" class="row-top">
                    <el-col :span="3">
                        <div class="labelstyle">补货人员</div>
                    </el-col>
                    <el-col :span="6">
                          <el-autocomplete
                          size="medium"
                          style="width:100%;"
                          v-model="userName"
                            :fetch-suggestions="querySearchAsync1"
                            value-key="userName"
                            placeholder="搜索补货人员"
                            clearable
                            @select="handleSelect1"
                          ></el-autocomplete>
                      </el-col>
                  </el-row>
                  <el-row :gutter="20" class="row-top">
                      <el-col :span="3">
                        <div class="labelstyle">补货类型</div>
                      </el-col>
                      <el-col :span="6">
                          <el-select v-model="addForm.type" style="width:100%;" clearable size="medium" placeholder="补货类型">
                            <el-option
                              v-for="item in options"
                              :key="item.value"
                              :label="item.label"

                              :value="item.value">
                            </el-option>
                          </el-select>
                      </el-col>
                  </el-row>
                  <el-row class="row-top">
                    <el-col :span="3">
                      <div class="labelstyle">库存三级划分</div>
                    </el-col>
                    <el-col :span="12">
                        <el-input placeholder="0-30" class="levelStyle" v-model="addForm.fristlevel"  size="medium" clearable>
                          </el-input>
                          <el-input placeholder="31-60" class="levelStyle" v-model="addForm.twolevel"  size="medium" clearable>
                          </el-input>
                          <el-input placeholder="61-100" class="levelStyle" v-model="addForm.threelevel"  size="medium" clearable>
                          </el-input>
                          <div style="color:#F56C6C; line-height:16px; font-size:12px;">
                          库存划分必须以0开始,100结束,中间两级连接的地方必须衔接上
                          </div>
                    </el-col>
                  </el-row>
                  <el-row class="row-top">
                    <el-col :span="3">
                      <div class="labelstyle">补货策略</div>
                    </el-col>
                    <el-col :span="16">
                      <div class="every" v-for="(item,index) in addForm.strategy">
                            <span v-if="item.every==1">每周</span>
                            <span v-if="item.every==2">每月</span>
                            <span v-if="item.every==1">星期{{item.value}}</span>
                            <span v-if="item.every==2">{{item.value}}号</span>
                            <span>{{item.time}}</span>
                            <span> 
                             
                              <el-button type="danger" size="mini" icon="el-icon-delete" @click="everyeRow(index, addForm.strategy)" circle></el-button>
                            </span>
                      </div>

                      <div class="addEvery" v-show="StrategyVisible">
                        <el-select class="everyWidth" v-model="StrategyObj.every" @change="everyDate" clearable size="mini" placeholder="请选择周">
                           <el-option
                              v-for="item in everyList"
                              :key="item.value"
                              :label="item.label"
                              :value="item.value">
                            </el-option>
                        </el-select>
                        <el-select class="everyWidth" v-model="StrategyObj.value"  clearable size="mini" placeholder="请选择周">
                           <el-option
                              v-for="item in dateList"
                              :key="item.value"
                              :label="item.label"
                              :value="item.value">
                            </el-option>
                        </el-select>
                        <el-time-picker
                          class="everyWidth"
                          size="mini"
                          value-format="HH:mm:ss"
                          v-model="StrategyObj.time"
                          placeholder="任意时间点">
                        </el-time-picker>
                         <el-button type="success" size="mini" @click="saveEvery">保存</el-button> <el-button size="mini" @click="StrategyVisible=false">取消</el-button>
                      </div>
                    </el-col>
                   
                  </el-row>
                  <el-row style="margin-top:10px;">
                    <el-col :span="3">&nbsp;</el-col>
                     <el-col :span="4">
                      <el-button type="primary" size="mini" @click="StrategyVisible=true">新增策略</el-button>
                    </el-col>
                  </el-row>
            </div>

            <div class="searchBox">
                    <strong>补货单生成时间：</strong>
                    <strong>补货时间早</strong>
                    <el-input v-model="addForm.advTime" size="mini" placeholder="请输入时间"  style="width:100px; float:left;  margin:0 5px; margin-top:2px;"></el-input>
                    <strong>分钟</strong>
                    <el-button type="primary" size="mini" style="float:right;margin-top:2px;" @click="dialogAddTableVisible = true">选择关联线路</el-button>
            </div>
            <wTable :data="VendingList" ref="multipleTable" :header="VendingHeader"  :option="tableOption">
            </wTable>
          
            <div class="pageBox">
                <el-pagination
                  @current-change="handleCurrentChange1"
                  background
                  :page-size="pagesize1"
                  layout="prev, pager, next"
                  :total="total1">
                </el-pagination>
              </div>
      </div>
      <div class="dialog-footer">
          <el-button type="primary" @click="addSubmitForm('addForm')">提 交</el-button>
           <el-button @click="ReturnLevel">取 消</el-button>
        </div>
  </div>
</template>
<script>
import { getVendingLine,getVendingDistrict,getStockInfo,getVendingList,getUserByName} from '@/api/dictionaries'
import { addSupplyConfig,guanLineList} from '@/api/replenishment'
import NProgress from 'nprogress'
import echarts from 'echarts'
import wTable from '@/components/table/w-table.vue'
export default {
  data() {
        return {
          total:1,
          currentPage:1, 
          pagesize:10, 
          total1:1,
          currentPage1:1, 
          pagesize1:10, 
          regionList:[],//查询区域下拉
          lineList:[],//线路下拉,
          everyList: [
            {
              value:'1',
              label:'每周'
            }, 
            // {
            //   value: '2',
            //   label: '每月'
            // }
          ],
          dateList:[],
          stockName:"",
          userName:"",
          StockList:[],
          ByNameList:[],
          searchObj:{
            supplyName:"",
            startime:"",
            endTime:"",
            wmId:"",
            supplierId:"",
          },
          linSearchObj:{
            districtId:"",
            lineId:""

          },
          options: [{
            value: '1',
            label: '全品补齐'
            }, {
              value: '2',
              label: '阈值补齐'
            }],
          value: '',
          formLabelWidth: '120px',
          dialogAddTableVisible:false,//控制新增弹出显示隐藏
          dialogEditTableVisible:false,//控制编辑弹出显示隐藏
          tableOption:{
            border: true
          },
          VendingHeader:[
              {
                type:"index"
              },
              {
                prop: 'name',
                label: '线路名称 ',
                minWidth: '200',
                sortable: true,
                sortMethod: this.handleNameSort
              },
              {
                prop: 'districtName',
                label: '区域名称',
                 minWidth: '200'
             
              }, {
                prop: 'corpName',
                label: '所属公司',
              }
          ],
        
          tableData: [],
          VendingList:[],
          filterText: '',
          StrategyVisible:true,
          StrategyObj:{
            every:"",
            value:'',
            time: ""
          },
          addForm: {
            name:"",
            description:"",
            strategy: [],
            fristlevel:'',
            twolevel:'',
            threelevel:'',
            type:'',
            wmId:'',
            supplierId:'',
            advTime:'',
            lineId:''
          }
        }
  },
  watch: {
    filterText(val) {
      this.$refs.tree2.filter(val)
    }
  },
  methods: {
    Relation(index, rows){
      let _this =this;
      this.$confirm('确认关联？', '提示', {}).then(() => {
            _this.dialogAddTableVisible=false;
           this.$notify({
              title: '成功',
              message: "关联成功",
              type: 'success'
            });
          _this.addForm.lineId=rows[index].lineId
          _this.getVendingData("1",_this.addForm.lineId)
      });
    },
    search(){
        this.getVendingLineData("1")
    },
    handleSizeChange: function (size) { 
       this.pagesize = size; 
    }, 
    handleCurrentChange: function(currentPage){ 
     this.currentPage = currentPage;
     this.getVendingLineData(currentPage)
    },


    handleSizeChange1: function (size) { 
       this.pagesize1 = size; 
    }, 
    handleCurrentChange1: function(currentPage){ 
     this.currentPage1 = currentPage;
     this.getVendingData(currentPage,this.addForm.lineId)
    },
    ReturnLevel(){
      this.$router.push(
        {path:'/replenishment/addTo/index'}
      );
    },
    saveEvery(){
        this.StrategyVisible=false
        this.addForm.strategy.push(this.StrategyObj)
        this.StrategyObj={every:"",value:'',time:''}
    },
    everyDate(value){
      if(value==1){//每周
        this.dateList = [
              {
              value: '1',
              label: '星期一'
              }, 
              {
                value: '2',
                label: '星期二'
              }
              , 
              {
                value: '3',
                label: '星期三'
              }
              , 
              {
                value: '4',
                label: '星期四'
              }
              , 
              {
                value: '5',
                label: '星期五'
              }
              , 
              {
                value: '6',
                label: '星期六'
              }
              , 
              {
                value: '7',
                label: '星期日'
              }
            ]
      }else{//每月
        this.dateList = [
              {value: '1',label: '1'},{value: '2',label: '2'},{value: '3',label: '3'},{value: '4',label: '4'}, {value: '5',label: '5'}, {value: '6',label: '6'},{value: '7',label: '7'},{value: '8',label: '8'},{value: '9',label: '9'},{value: '10',label: '10'},{value: '11',label: '11'},{value: '12',label: '12'},{value: '13',label: '13'},{value: '14',label: '14'},{value: '15',label: '15'},{value: '16',label: '16'},{value: '17',label: '17'},{value: '18',label: '18'},{value: '19',label: '19'},{value: '20',label: '20'},{value: '21',label: '21'},{value: '22',label: '22'},{value: '23',label: '23'},{value: '24',label: '24'},{value: '25',label: '25'},{value: '26',label: '26'},{value: '27',label: '27'},{value: '28',label: '28'},{value: '29',label: '29'},{value: '30',label: '30'},{value: '31',label: '31'}
            ]
      }
      
    },
    everyeRow(index, rows) {//删除数据
      this.$confirm('确认提交吗？', '提示', {}).then(() => {
           rows.splice(index, 1);
      });
    },
    deleteRow(index, rows) {//删除数据
      this.$confirm('确认提交吗？', '提示', {}).then(() => {
           rows.splice(index, 1);
      });
    },
    handleAdd() {//显示新增窗口
        this.$router.push(
          {path:'/model/addModel'}
        );
    },

    handleEdit(index, row) {//显示编辑窗口
     this.dialogEditTableVisible= true
     this.editForm = Object.assign({}, row);
    },
    addSubmitForm(formName) {//提交新增窗口
      this.$confirm('确认提交吗？', '提示', {}).then(() => {
        let para = Object.assign({}, this.addForm);
         para.strategy= JSON.stringify(para.strategy)
        addSupplyConfig(para).then((res) => {
              if(res.zhead.reTCode=="0000"){
                  NProgress.done();
                   this.$router.push(
                      {path:'/replenishment/addTo/index'}
                    );
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
    },
    createStateFilter(queryString) {
        return (state) => {
          return (state.stockName.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
        };
    },
    handleSelect(item) {
        this.searchObj.wmId =item.stockId
        this.addForm.wmId = item.stockId
        console.log(item);
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
        this.addForm.supplierId = item.loginName
        console.log(item);
    },

    getVendingDistrictData() {//查询所有区域
      var _this = this;
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
    getVendingLineData(page){//查所有线路
        var _this = this;
        const listQuery = {
          districtId:_this.linSearchObj.districtId,
          pageSize:_this.pagesize,
          pageNum:page,
          orderByColumn:'createtime',
          isAsc:'desc'//desc

        }
        getVendingLine(listQuery).then(response => {
          if(response.zhead.reTCode==="0000"){
          _this.total=response.zbody.datas.total
          _this.lineList = response.zbody.datas.rows;
          _this.tableData = response.zbody.datas.rows
          console.log("所有线路",_this.lineList)
          }else{
             this.$notify({
                title: '失败',
                message: res.zhead.retMsg,
                type: 'error'
              });
          }

        })
    },
    getVendingData(page,lineId){//根据线路查询售卖机
        var _this = this;
        const listQuery = {
          lineId:lineId,
          pageSize:10,
          pageNum:page,
          orderByColumn:'createtime',
          isAsc:'desc'

        }
        guanLineList(listQuery).then(response => {
          if(response.zhead.reTCode==="0000"){
          _this.total1=response.zbody.datas.total
          _this.VendingList = response.zbody.datas.rows;
          console.log("根据线路查询售卖机列表：：",_this.lineList)
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
    this.getVendingDistrictData()
    this.getVendingLineData()
  },
  components: {
    wTable
  }
}
</script>
<style scoped>
  .row-top{margin-bottom:20px}
  .levelStyle{width:95px;float:left; text-align:center; margin-right:10px;}
  .labelstyle{font-size:14px; font-weight:bold; color:#333; line-height:30px;}
  .everyWidth{width:150px;}
  .every{line-height:30px; overflow:hidden; margin-bottom:10px;}
  .every span{width:25%; display:block; font-size:14px; float:left;}
  .addEvery{ overflow:hidden; }
  .LayersearchBox{}
  .LayersearchBox .inputStyle{width: 200px; display: inline-block;float:left; margin-right: 15px; }
  .operation-box{padding-top:4px;}
  .screen-box{padding: 0px 0 20px 0}
  .selectBox{padding:10px 0;}
  .selectBox strong{font-size:14px;color:#333; font-weight: normal; display: inline-block; }
  .selectBox .select-left{ padding-left:10px; }
  .searchBox{ padding: 10px 0; overflow:hidden; }
  .searchBox strong{ display: inline-block;float: left; line-height: 35px; font-size:14px; font-weight: normal; }
  .searchBox .inputStyle{width: 300px; display: inline-block;float:left; margin-right: 15px; }

 .timeDate{ padding: 10px 0; overflow:hidden; }
.timeDate strong{ display: inline-block;float: left; line-height: 25px; font-size:14px; font-weight: normal;}
.timeDate span{ display: inline-block;float: left; line-height: 25px; font-size:14px; font-weight: normal;}
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