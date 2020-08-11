<template>
 <div class="div-warp">
    <div class="search-warp">
          <el-row :gutter="20" style="margin-top:20px;">
              <el-col :span="4">
                <el-autocomplete
                  size="medium"
                  v-model="userName"
                  :fetch-suggestions="querySearchAsync"
                   value-key="userName"
                  placeholder="创建人"
                  clearable
                   @focus="handeFocus"
                  @select="handleSelect"
                ></el-autocomplete>
              </el-col>
               <el-col :span="4">
                <el-date-picker type="date" size="medium" placeholder="开始时间" value-format="yyyy-MM-dd" v-model="searchObj.bdate" style="width:100%"></el-date-picker>
              </el-col>
              <el-col :span="4">
                <el-date-picker type="date" size="medium" placeholder="结束时间" value-format="yyyy-MM-dd" v-model="searchObj.edate" style="width:100%"></el-date-picker>
              </el-col>
              <el-col :span="4">
                <el-select v-model="searchObj.curState" clearable size="medium" class="searchInput" placeholder="状态">
                  <el-option
                  v-for="item in curStateList"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-col>
              <el-col :span="4">
                   <el-button type="success" @click.native.prevent="search" size="medium">查询</el-button>

              </el-col>
          </el-row>
    </div>
    <div class="block-warp">
            <wTable :data="tableData" ref="multipleTable" :header="tableHeader"  :option="tableOption">
              <el-table-column slot="fixed"
                fixed="right"
                label="操作"
                width="50">
                  <template  scope="scope">
                    <div>
                      <el-button type="success" size="mini" icon="el-icon-view"  @click="getDetail(scope.row)" circle></el-button>

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
import { vendingLsdifferList} from '@/api/equipment'
import NProgress from 'nprogress'
import wTable from '@/components/table/w-table.vue'
export default {
  data() {
        return {
          userName:'',
          StockList:[],//仓库列表
          curStateList: [{
            value: '1',
            label: '待处理'
            }, {
              value: '2',
              label: '超期'
            }, {
              value: '3',
              label: '已处理'
            }
          ],
          searchObj:{
            createrId:'',
            bdate:'',
            edate:'',
            curState:''
           
          },
          form:{},
          total:1,
          PointTotal:1,
          currentPage:1, 
          pagesize:10,
          formLabelWidth: '120px',
          dialogAddTableVisible:false,//控制新增弹出显示隐藏
          dialogEditTableVisible:false,//控制编辑弹出显示隐藏
          input10:'',
          tableOption:{
            border: true
          },
          tableHeader: [
              {
                prop: 'lsdifferId',
                label: '差异编号 ',
                width: '120',
                sortable: true,
                sortMethod: this.handleNameSort
              },
              {
                prop: 'siteId',
                label: '售货机编号',
                 minWidth: '120'
             
              }, {
                prop: 'siteName',
                label: '售货机名称',
                minWidth: '120'
              }, {
                prop: 'productId',
                label: '商品编号',
                minWidth: '120'
              }, {
                prop: 'productName',
                label: '商品名称',
                minWidth: '120'
              }, {
                prop: 'differNum',
                label: '差异数',
                minWidth: '120'
              }, {
                prop: 'handleTypeName',
                label: '处理类型',
                minWidth: '120'
              }, {
                prop: 'curStateName',
                label: '状态',
                minWidth: '120'
              }, {
                prop: 'createrName',
                label: '创建人',
                minWidth: '120'
              }, {
                prop: 'createTime',
                label: '创建时间',
                minWidth: '120'
              }
          ],
          tableData: [],
        }
  },
  watch: {
   
  },
  methods: {
    search(){//查询
      this.getVendingLsdifferList("1")
      console.log('---搜索----')
    },
    handleAdd() {//显示新增窗口
        this.$router.push(
          {path:'/model/addModel'}
        );
    },
    querySearchAsync(queryString, cb) {
        var _this = this;
        const listQuery = {
          userName:_this.userName
        }
        getUserByName(listQuery).then(response => {
          _this.ByNameList = response.zbody.datas
          var ByNameList = _this.ByNameList;
          var results = queryString ? ByNameList.filter(this.createStateFilter(queryString)) : ByNameList;
          cb(results);
        })
    },
    createStateFilter(queryString) {
        return (state) => {
          return (state.userName.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
        };
    },
    handleSelect(item) {
        this.searchObj.createrId = item.loginName
    },
    handeFocus(item){
      this.userName=""
      this.searchObj.createrId=""
    },
    getVendingLsdifferList(page){
      var _this = this;
      const listQuery = {
        siteId:this.$route.query.siteId,
        createrId:_this.searchObj.createrId,
        bdate:_this.searchObj.bdate,
        edate:_this.searchObj.edate,
        curState:_this.searchObj.curState,
        pageSize:_this.pagesize,
        pageNum:page,
        orderByColumn:'createtime',
        isAsc:'desc'//desc
      }
      vendingLsdifferList(listQuery).then(response => {
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
    handleSizeChange: function (size) { 
       this.pagesize = size; 
    }, 
    handleCurrentChange: function(currentPage){ 
     this.currentPage = currentPage;
     this.getVendingLsdifferList(currentPage)
    },
    getDetail(row) {
      var _this=this
        localStorage.setItem("editData",JSON.stringify(row) );
        this.$router.push(
          {
            path:'/equipment/machine/detail/stockingEdit/detail',
            query:{
              lsdifferId:row.lsdifferId,
              siteId:row.siteId,
              laneSId:row.laneSId,
              laneEId:row.laneEId,
              productId:row.productId
            }
          }
        );
    
    },
  },

  mounted () {
    this.getVendingLsdifferList("1")
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
</style>