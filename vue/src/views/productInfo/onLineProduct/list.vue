<template>
 <div class="div-warp">
      <el-dialog
            title="商品下架"
            :visible.sync="dialogVisible"
            width="900px"
            >
            <div class="productName">请选择要下架<span>{{productName}}</span>的商品机</div>
            <el-row :gutter="10" style="margin-bottom:10px;">
                <el-col :span="4">
                    <el-select v-model="upSearchObj.districtId" size="small" clearable class="searchInput" placeholder="请选择区域">
                      <el-option
                        v-for="item in regionList"
                        :key="item.districtId"
                        :label="item.name"
                        :value="item.districtId">
                      </el-option>
                    </el-select>
                </el-col>
                <el-col :span="4">
                  <el-select v-model="upSearchObj.lineId" class="searchInput" clearable size="small" placeholder="请选择线路">
                    <el-option
                    v-for="item in lineList"
                      :key="item.lineId"
                      :label="item.name"
                      :value="item.lineId">
                    </el-option>
                  </el-select>
                </el-col>
                <el-col :span="4">
                  <el-input placeholder="售卖机名称/编号" v-model="upSearchObj.siteId" size="small" class="searchInput"  clearable>
                  </el-input>
                </el-col>
                <el-col :span="4">
                  <el-button type="success" size="small" @click.native.prevent="upFrom" >查询</el-button>
                  <el-button type="warning" size="small" @click.native.prevent="under" >下架</el-button>
                </el-col>
           
            </el-row>

            <el-table
                :data="upData"
                @selection-change="handleSelectionChange"
                border
                style="width: 100%">
                <el-table-column
                  type="selection"
                  width="55">
                </el-table-column>
                <el-table-column
                  prop="siteId"
                  :show-overflow-tooltip="true"
                  label="售卖机编号"
                  width="100">
                </el-table-column>
                <el-table-column
                  prop="siteName"
                  :show-overflow-tooltip="true"
                  label="售卖机名称"
                  width="130">
                </el-table-column>
                <el-table-column
                  prop="lineName"
                  width="130"
                  :show-overflow-tooltip="true"
                  label="线路名称">
                </el-table-column>
                <el-table-column
                  prop="districtName"
                  width="130"
                  :show-overflow-tooltip="true"
                  label="区域名称">
                </el-table-column>
                <el-table-column
                  prop="dispatch"
                  width="130"
                  :show-overflow-tooltip="true"
                  label="所属行政区">
                </el-table-column>
                <el-table-column
                  :show-overflow-tooltip="true"
                  prop="createTime"
                  label="创建时间">
                </el-table-column>
                <el-table-column
                  prop="address"
                  label="操作">
                          <template  scope="scope">
                            <div style="text-align:center;">
                              <el-button type="warning"  size="mini"  @click.native.prevent="under(scope.row)">下架</el-button>
                             
                            </div>
                          </template>
                </el-table-column>
            </el-table>
            <div class="pageBox" style="padding:0;">
              <el-pagination
                @current-change="handleCurrentChange1"
                background
                :page-size="pagesize1"
                layout="prev, pager, next"
                :total="LayerTotal">
              </el-pagination>
            </div>
        
      </el-dialog>


      <div class="search-warp">
        <el-row :gutter="20" >
          <el-col :span="4">
             <el-select v-model="searchObj.typeId" size="small" clearable placeholder="商品分类">
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
                  v-model="searchObj.productName"
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
            <el-col :span="2">
                <div class="grid-content bg-purple"> 
                  <el-button type="success" size="small" @click.native.prevent="search" v-permission="['sys:product:on:select']">查询</el-button>
                </div>
            </el-col>
        </el-row>
      </div>
      <div class="block-warp">
      <wTable :data="tableData" :header="tableHeader"  :option="tableOption">
          <el-table-column slot="fixed"
            fixed="right"
            label="操作"
            width="150">
              <template  scope="scope">
                <div style="text-align:center;">
                  <el-button type="warning"  size="mini"  @click.native.prevent="upSarch(scope.row)" v-permission="['sys:product:on:edit']">下架</el-button>
                 
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
import { lineproduct,selectUnderProductSite,under,getProductClassify} from '@/api/productInfo'
import {getVendingLine,getVendingDistrict} from '@/api/dictionaries'
import NProgress from 'nprogress'
import wTable from '@/components/table/w-table.vue'
import permission from '@/directive/permission/index.js'
export default {
  directives: { permission },
  data() {
        return {
          productName:"",
          multipleSelection:"",
          regionList:[],
          lineList:[],
          upSearchObj:{
            productId:'',
            districtId:'',
            siteId:'',
            lineId:''
          },
          searchObj:{
            typeId:'',
            name:"",
            productCode:""
          },
          dialogVisible:false,
          options: [],
          upData:[],
          value:"",
          keyword:'',
          total:1,
          LayerTotal:1,
          currentPage:1, 
          currentPage1:1,
          pagesize:10, 
          pagesize1:5, 
          formLabelWidth: '120px',
          tableOption:{
            border: true
          },
          tableHeader: [
              {
                prop: 'name',
                label: '商品名称',
                 minWidth: '150'
             
              },{
                prop: 'productId',
                label: '商品编号',
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
              }
          ],
          tableData: [],
        }
  },
  watch: {
    
  },
  methods: {
    under(row){
        const _this = this;
        if(_this.multipleSelection.length==0 && row.bubbles){
          this.$message({
            message: '请选择要下架的商品',
            type: 'warning'
          });
          return false
        } 
        this.$confirm('确认下架吗？', '提示', {}).then(() => {
                  let para ={
                    productId:_this.upSearchObj.productId,
                    ids:_this.multipleSelection =="" ? row.logid : _this.multipleSelection
                  }
                  under(para).then((res) => {
                        if(res.zhead.reTCode=="0000"){
                            _this.upFrom()
                            NProgress.done();
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
                  })
      });
    },
    upSarch(row){
        this.productName =row.name
        this.upSearchObj.productId = row.productId
        this.upFrom()
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
    upFrom(){
      var _this = this;
      const listQuery = {
        productId:_this.upSearchObj.productId,
        districtId:_this.upSearchObj.districtId,
        siteId:_this.upSearchObj.siteId,
        lineId:_this.upSearchObj.lineId,
        pageSize:_this.pagesize1,
        pageNum:_this.currentPage1,
        orderByColumn:'createtime',
        isAsc:'desc'
      }
      selectUnderProductSite(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
            NProgress.done();
          _this.dialogVisible = true;
          _this.LayerTotal = response.zbody.datas.total;  
          _this.upData = response.zbody.datas.rows;
          console.log("查下架商品",response.zbody.datas.rows)
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
     this.getData(currentPage)
     },
    handleCurrentChange1: function(currentPage){ 
     this.currentPage1 = currentPage;
     this.upFrom()
     },
    search(){
      this.getData("1")
    },
    getData(page) {
      var _this = this;
      this.listLoading = true
      const listQuery = {
        typeId:_this.searchObj.typeId,//分类
        name:_this.searchObj.productName,
        productCode:_this.searchObj.productCode,
        pageSize:_this.pagesize,
        pageNum:page,
        orderByColumn:'createtime desc,productid',
        isAsc:'desc'//desc
      }
      lineproduct(listQuery).then(response => {
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
    getVendingDistrictData() {
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
    getVendingLineData(){//查所有线路
        var _this = this;
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
    },
    getProductClass(){//商品分类查询
      var _this = this;
      const listQuery = {
        pageSize:"5100000",
        pageNum:"1",
        orderByColumn:'createtime',
        isAsc:'desc'//desc
      }
      getProductClassify(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
          NProgress.done();
          _this.options = response.zbody.datas.rows;
          console.log("分类：：：",_this.tableData3)
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
    this.getData("1")
    this.getVendingDistrictData()
    this.getVendingLineData()
    this.getProductClass()
  },
  components: {
    wTable
  }
}
</script>
<style scoped>
  .productName{font-size:14px; margin-bottom:10px;}
  .productName span{font-size:15px; display:inline-block;padding:0 5px; color:#c00}
  .div-warp{padding:0 20px;}
  .pageBox{background: #fff; padding:20px 0;}

</style>