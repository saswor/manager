<template>
 <div class="div-warp">
      <!-- 查看广告 -->
       <el-dialog
          title="查看立减"
          :visible.sync="dialogVisibleInfo"
          :close-on-click-modal="false"
          width="900px"
          
          >
          <el-tabs v-model="activeAdv" @tab-click="handleClickAdv">
            <el-tab-pane label="广告信息" name="advInfo">
                <el-row align="middle"  :gutter="10" class="m-top">
                  <el-col :span="5">
                      优惠立减名称：
                  </el-col>
                  <el-col :span="14">
                      {{infoData.name}}
                  </el-col>
                </el-row>
                <el-row align="middle"  :gutter="10" class="m-top">
                  <el-col :span="5">
                      参与优惠支付方式：
                  </el-col>
                  <el-col :span="14">
                      {{infoData.payTypeName}}
                  </el-col>
                </el-row>
                <el-row align="middle"  :gutter="10" class="m-top">
                  <el-col :span="5">
                      优惠方式：
                  </el-col>
                  <el-col :span="14">
                      {{infoData.favTypeName}}
                  </el-col>
                </el-row>
                <el-row align="middle"  :gutter="10" class="m-top">
                  <el-col :span="5">
                      有效时间段：
                  </el-col>
                  <el-col :span="14">
                      {{infoData.validSTime}}至{{infoData.validETime}}
                  </el-col>
                </el-row>
                <el-row align="middle"  :gutter="10" class="m-top">
                  <el-col :span="5">
                      立减：
                  </el-col>
                  <el-col :span="14">
                     <div v-for="item in infoData.ftimes" class="tiems">
                       <span><em>开始时间：</em>{{item.favSTime}}</span>
                       <span><em>结束时间：</em>{{item.favETime}}</span>
                       <span><em>立减：</em>{{item.discount}}</span>
                     </div>
                  </el-col>
                </el-row>
                    
            </el-tab-pane>
            <el-tab-pane :label="labelSeeText" name="advTInfo">
              <div v-show="this.$route.query.favTarget==1">
                <el-table
                      :data="tfData.slice((currentPage3-1)*pagesize3,currentPage3*pagesize3)"
                      style="width: 100%">
                      <el-table-column
                        prop="netSateName"
                        label="网络状态"
                        width="180">
                      </el-table-column>
                      <el-table-column
                        prop="siteName"
                        label="名称"
                        width="180">
                      </el-table-column>
                      <el-table-column
                        prop="siteId"
                        label="编号">
                      </el-table-column>
                      <el-table-column
                        prop="lineName"
                        label="线路">
                      </el-table-column>
                      <el-table-column
                        prop="sellStateName"
                        label="售卖状态">
                      </el-table-column>
                </el-table>
                <div style="text-align:center; margin-top:20px;">
                 <el-pagination
                 background
                  @size-change="handleSizeChange3"
                  @current-change="handleCurrentChange3"
                  layout="prev, pager, next"
                  :total="total3">
                </el-pagination>
                </div>
              </div>
              <div v-show="this.$route.query.favTarget==2">
                  <el-table
                      :data="ProductList.slice((currentPage3-1)*pagesize3,currentPage3*pagesize3)"
                      style="width: 100%">
                      <el-table-column
                        prop="name"
                        label="商品名称"
                       >
                      </el-table-column>
                      <el-table-column
                        prop="bagTypeName"
                        label="包装类型"
                        >
                      </el-table-column>
                </el-table>
                  <!-- ProductList -->
              </div>
            </el-tab-pane>
            
          </el-tabs>
        </el-dialog>
      <!-- 素材弹出 -->
      <el-dialog
          :visible.sync="dialogVisible_s"
          width="900px"
          :close-on-click-modal="false"
          >
             <el-table
                :data="advData"
                border
                style="width: 100%">
                <el-table-column
                  prop="mediaName"
                  label="素材名称"
                 >
                </el-table-column>
                <el-table-column
                  prop="mediaType"
                  label="类型"
                  width="80">
                </el-table-column>
                <el-table-column
                  prop="mediaSize"
                  label="文件大小"
                  width="100">
                </el-table-column>
                <el-table-column
                  prop="mediaPX"
                  label="文件预览"
                  width="100">
                </el-table-column>
                <el-table-column
                  prop="createTime"
                  label="创建时间"
                  width="155">
                </el-table-column>
                <el-table-column
                  fixed="right"
                  label="操作"
                  width="80">
                  <template slot-scope="scope">
                    <el-button @click="handleAdvClick(scope.row)" size="small" type="primary">选择 </el-button>
                  </template>
                </el-table-column>
            </el-table>
            <div class="pageBox">
              <el-pagination
                @current-change="handleCurrentChange1"
                background
                :page-size="pagesize1"
                layout="prev, pager, next"
                :total="total1">
              </el-pagination>
            </div>
      </el-dialog>
      <!-- 新增广告 -->
      <el-dialog
          title="新增立减"
          :visible.sync="dialogVisible"
          width="900px"
          :close-on-click-modal="false"
          >
                 <el-form :model="addForm" :rules="Rules" ref="addForm"  label-width="200px">
                    <el-tabs v-model="activ" @tab-click="handleClicka">
                      <el-tab-pane label="基本信息" name="set1" disabled>
                        <el-form-item label="优惠活动名称：" prop="name" size="small" :label-width="formLabelWidth">
                            <el-input v-model="addForm.name"></el-input>
                        </el-form-item>
                        <el-form-item label="参与优惠支付方式：" prop="payType" size="small" :label-width="formLabelWidth">
                            <el-select v-model="addForm.payType" placeholder="请选择">
                              <el-option
                                key="1"
                                label="支付宝"
                                value="1">
                              </el-option>
                              <el-option
                                key="2"
                                label="微信"
                                value="2">
                              </el-option>
                            </el-select>  
                        </el-form-item>
                        <el-form-item label="优惠方式：" prop="favType" size="small" :label-width="formLabelWidth">
                            <el-select v-model="addForm.favType" placeholder="请选择">
                              <el-option
                                key="1"
                                label="统一优惠"
                                value="1">
                              </el-option>
                              <el-option
                                key="2"
                                label="分时段优惠"
                                value="2">
                              </el-option>
                            </el-select>  
                        </el-form-item>
                        <el-form-item label="有效时间段" prop="validSTime" size="small" :label-width="formLabelWidth">
                                     <el-date-picker
                                        v-model="addForm.validSTime"
                                        type="date"
                                        value-format="yyyy-MM-dd"
                                        placeholder="开始时间">
                                      </el-date-picker>


                                      -
                                      <el-date-picker
                                        v-model="addForm.validETime"
                                        type="date"
                                        value-format="yyyy-MM-dd"
                                        placeholder="结束时间">
                                      </el-date-picker>
                         </el-form-item>
                        <el-form-item label="立减：" prop="discount" size="small" :label-width="formLabelWidth" v-if="addForm.favType=='1'">
                            <div style="display:inline-block;float:left; margin-right:5px;" v-for="(z,index) in addForm.ftimes">

                            <el-input style="width:100px;"  v-model="z.discount"></el-input>
                            </div>
                            

                        </el-form-item>

                        <el-form-item label="优惠时间段：" prop="favSTime" v-else size="small" :label-width="formLabelWidth">
                            <div style="display:inline-block;float:left; margin-right:5px;" v-for="(z,index) in addForm.ftimes">
                              <el-time-picker
                                  v-model="z.favSTime"
                                  value-format="HH:mm:ss"
                                  placeholder="开始时间">
                                </el-time-picker>
                                      -
                                <el-time-picker
                                  v-model="z.favETime"
                                 value-format="HH:mm:ss"
                                  placeholder="结束时间">
                                </el-time-picker>


                            <el-input style="width:100px;"  v-model="z.discount"></el-input>
                             <el-button icon="el-icon-minus"  @click="removftimes(index,addForm.ftimes)" size="small" type="danger" circle></el-button>
                            </div>
                            <el-button icon="el-icon-plus" type="success" @click="addftimes" size="small" circle></el-button>

                        </el-form-item>
                        <div class="dialog-prevNext">
                          <el-button style="margin-top: 12px;" @click="prev">上一步</el-button>
                          <el-button style="margin-top: 12px;" @click="next">下一步</el-button>
                        </div>
                      </el-tab-pane>
                      <el-tab-pane :label="labelText" name="set2" disabled>
                          <!-- 选择投放设备 -->
                              <el-transfer
                                style="text-align: left; display: inline-block"
                                v-model="adevicesValue"
                                filterable
                                :left-default-checked="[2, 3]"
                                :right-default-checked="[1]"
                                :render-content="renderFunc"
                                :titles="['售卖机列表', '下发的广告售卖机']"
                                :button-texts="['到左边', '到右边']"
                                :format="{
                                  noChecked: '${total}',
                                  hasChecked: '${checked}/${total}'
                                }"
                                :props="{
                                  key: 'favObjId',
                                  label: 'favObjName'
                                }"
                                @change="handleChange"
                                :data="data">
                              </el-transfer>
                          <!-- 选择投放设备 end -->
                        <div class="dialog-prevNext">
                          <el-button style="margin-top: 12px;" @click="prev">上一步</el-button>
                          <el-button style="margin-top: 12px;" @click="addSubmitForm('addForm','1',favTarget)" >暂存</el-button>
                          <el-button style="margin-top: 12px;" @click="addSubmitForm('addForm','3',favTarget)" >投放</el-button>
                        </div>
                      </el-tab-pane>
                    </el-tabs>
                  </el-form>
      </el-dialog>
      <!-- 编辑广告 -->
      <el-dialog
          title="编辑立减"
          :visible.sync="editdialogVisible"
          :close-on-click-modal="false"
          width="900px"
          >
                 <el-form :model="editForm" :rules="Rules" ref="editForm"  label-width="200px">
                    <el-tabs v-model="activ" @tab-click="handleClicka">
                      <el-tab-pane label="基本信息" disabled name="set1">
                        <el-form-item label="优惠活动名称：" prop="name" size="small" :label-width="formLabelWidth">
                            <el-input v-model="editForm.name"></el-input>
                        </el-form-item>
                        <el-form-item label="参与优惠支付方式：" prop="payType" size="small" :label-width="formLabelWidth">
                            <el-select v-model="editForm.payType" placeholder="请选择">
                              <el-option
                                key="1"
                                label="支付宝"
                                value="1">
                              </el-option>
                              <el-option
                                key="2"
                                label="微信"
                                value="2">
                              </el-option>
                            </el-select>  
                        </el-form-item>
                        <el-form-item label="优惠方式：" prop="favType" size="small" :label-width="formLabelWidth">
                            <el-select v-model="editForm.favType" placeholder="请选择">
                              <el-option
                                key="1"
                                label="统一优惠"
                                value="1">
                              </el-option>
                              <el-option
                                key="2"
                                label="分时段优惠"
                                value="2">
                              </el-option>
                            </el-select>  
                        </el-form-item>
                        <el-form-item label="有效时间段" prop="validSTime" size="small" :label-width="formLabelWidth">
                                     <el-date-picker
                                        v-model="editForm.validSTime"
                                        type="date"
                                        value-format="yyyy-MM-dd"
                                        placeholder="开始时间">
                                      </el-date-picker>


                                      -
                                      <el-date-picker
                                        v-model="editForm.validETime"
                                        type="date"
                                        value-format="yyyy-MM-dd"
                                        placeholder="结束时间">
                                      </el-date-picker>
                         </el-form-item>
                        <el-form-item label="立减：" prop="discount" size="small" :label-width="formLabelWidth" v-if="editForm.favType=='1'">
                            <div style="display:inline-block;float:left; margin-right:5px;" v-for="(z,index) in editForm.ftimes">

                            <el-input style="width:100px;"  v-model="z.discount"></el-input>
                            </div>
                            

                        </el-form-item>

                        <el-form-item label="优惠时间段：" prop="favSTime" v-else size="small" :label-width="formLabelWidth">
                            <div style="display:inline-block;float:left; margin-right:5px;" v-for="(z,index) in editForm.ftimes">
                              <el-time-picker
                                  v-model="z.favSTime"
                                  value-format="HH:mm:ss"
                                  placeholder="开始时间">
                                </el-time-picker>
                                      -
                                <el-time-picker
                                  v-model="z.favETime"
                                 value-format="HH:mm:ss"
                                  placeholder="结束时间">
                                </el-time-picker>


                            <el-input style="width:100px;"  v-model="z.discount"></el-input>
                             <el-button  @click="removftimes(index,editForm.ftimes)" size="small">删除</el-button>
                            </div>
                            <el-button type="primary" @click="addftimes" size="small">添加</el-button>

                        </el-form-item>
                        <div class="dialog-prevNext">
                          <el-button style="margin-top: 12px;" @click="prev">上一步</el-button>
                          <el-button style="margin-top: 12px;" @click="next">下一步</el-button>
                        </div>
                      </el-tab-pane>
                      <el-tab-pane :label="labelText" disabled name="set2">
                          <!-- 选择投放设备 -->
                              <el-transfer
                                style="text-align: left; display: inline-block"
                                v-model="adevicesValueEdit"
                                filterable
                                :left-default-checked="[2, 3]"
                                :right-default-checked="[1]"
                                :render-content="renderFuncEdit"
                                :titles="['售卖机列表', '下发的广告售卖机']"
                                :button-texts="['到左边', '到右边']"
                                :format="{
                                  noChecked: '${total}',
                                  hasChecked: '${checked}/${total}'
                                }"
                                :props="{
                                  key: 'favObjId',
                                  label: 'favObjName'
                                }"
                                @change="handleChange"
                                :data="dataEdit">
                              </el-transfer>
                          <!-- 选择投放设备 end -->
                        <div class="dialog-prevNext">
                          <el-button style="margin-top: 12px;" @click="prev">上一步</el-button>
                          <el-button style="margin-top: 12px;" @click="addSubmitForm('editForm','1',favTarget)" >暂存</el-button>
                          <el-button style="margin-top: 12px;" @click="addSubmitForm('editForm','3',favTarget)" >投放</el-button>
                        </div>
                      </el-tab-pane>
                    </el-tabs>
                  </el-form>
      </el-dialog>
      <div class="block-warp" style="margin-top:20px">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="整机立减" name="1">
          <div class="search-warp">
              <el-row :gutter="20" >
                  <el-col :span="4">
                    <div class="grid-content bg-purple">
                        <el-input
                        placeholder="名称"
                        size="small"
                        v-model="keyword"
                        clearable>
                      </el-input>

                    </div>
                  </el-col>
                  <el-col :span="16">
                      <div class="grid-content bg-purple"> 
                        <el-button type="success" size="small" @click.native.prevent="search" v-permission="['sys:advert:favourable:select']">查询</el-button>
                        <el-button type="primary" size="small" @click="handleAdd" v-permission="['sys:advert:favourable:add']">新增</el-button>
                        <el-button type="danger" size="small" @click="deleteRow" v-permission="['sys:advert:favourable:del']">删除</el-button>
                      </div>
                  </el-col>
                 
              </el-row>
          </div>
           <wTable :data="tableData" :header="tableHeader" :handleSelectionChange="handleSelectionChange" :option="tableOption">
              <el-table-column slot="fixed"
                fixed="right"
                label="操作"
                width="150">
                  <template  scope="scope">
                    <div style="text-align:center;">
                      <el-button type="success" size="mini" icon="el-icon-view" circle @click="getAdvInfo(scope.row)" v-permission="['sys:advert:favourable:view']"></el-button>
                      <el-button type="primary" size="mini"icon="el-icon-edit" circle @click="handleEdit(scope.$index, scope.row)" v-permission="['sys:advert:favourable:edit']"></el-button>
                      <el-button type="danger"  size="mini" icon="el-icon-delete" circle @click.native.prevent="deleteRow(scope.row)" v-permission="['sys:advert:favourable:del']"></el-button>
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
        </el-tab-pane>
        <el-tab-pane label="单品立减" name="2">
            <div class="search-warp">
              <el-row :gutter="20" >
                  <el-col :span="4">
                    <div class="grid-content bg-purple">
                        <el-input
                        placeholder="名称"
                        size="small"
                        v-model="keyword"
                        clearable>
                      </el-input>

                    </div>
                  </el-col>
                  <el-col :span="16">
                      <div class="grid-content bg-purple"> 
                        <el-button type="success" size="small" @click.native.prevent="search" v-permission="['sys:advert:favourable:select']">查询</el-button>
                        <el-button type="primary" size="small" @click="handleAdd" v-permission="['sys:advert:favourable:add']">新增</el-button>
                        <el-button type="danger" size="small" @click="deleteRow" v-permission="['sys:advert:favourable:del']">删除</el-button>
                      </div>
                  </el-col>
                  <el-col :span="4"><div class="grid-content bg-purple"></div></el-col>
              </el-row>
            </div>
            <wTable :data="tableData_D" :header="tableHeader1" :handleSelectionChange="handleSelectionChange" :option="tableOption">
              <el-table-column slot="fixed"
                fixed="right"
                label="操作"
                width="150">
                  <template  scope="scope">
                    <div style="text-align:center;">
                      <el-button type="success" size="mini" icon="el-icon-view" circle @click="getAdvInfo(scope.row)" v-permission="['sys:advert:favourable:view']"></el-button>
                      <el-button type="primary" size="mini"icon="el-icon-edit" circle @click="handleEdit(scope.$index, scope.row)" v-permission="['sys:advert:favourable:edit']"></el-button>
                      <el-button type="danger"  size="mini" icon="el-icon-delete" circle @click.native.prevent="deleteRow(scope.row)" v-permission="['sys:advert:favourable:del']"></el-button>
                    </div>
                  </template>
              </el-table-column>
            </wTable>
            <div class="pageBox">
              <el-pagination
                @current-change="handleCurrentChange2"
                background
                :page-size="pagesize2"
                layout="prev, pager, next"
                :total="total2">
              </el-pagination>
            </div>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>
<script>
import {favourableConfigList,advertMaterialList,addfavourableConfig,selectAdvertDeviceRwList,advInfo,tflist,removefavourableConfig,favourableConfigInfo,selectFavUseList,getEditFav,editFavourableConfig,selectFavProductList} from '@/api/adv'
import {getVendingList,productInfo} from '@/api/dictionaries'
import NProgress from 'nprogress'
import wTable from '@/components/table/w-table.vue'
export default {
  data() {
        return {
          labelText:"",
          labelSeeText:"",
          data:[],
          dataEdit:[],
          adevicesValue: [],
          adevicesValueEdit:[],
          renderFunc(h, option) {
            if(this.$route.query.favTarget==1){
               return <span>{option.netSate}-{ option.favObjId } - { option.favObjName }-{option.lineName}-{option.cabinetTypeName}-{option.mediaType}</span>;
              }else if(this.$route.query.favTarget==2){
               return <span>{ option.favObjName }</span>;
            }
          },
          renderFuncEdit(h, option) {
            if(this.$route.query.favTarget==1){
               return <span>{ option.favObjId } - { option.favObjName }</span>;
              }else if(this.$route.query.favTarget==2){
               return <span>{ option.favObjId } - { option.favObjName }</span>;
            }
          },
          DeviceRwData:[],
          tableData_D:[],
          addForm:{
            curState:"",
            favTarget:this.$route.query.favTarget,
            favType:"",
            favWay:this.$route.query.favWay,
            name:"",
            ftimes:[
              {
                discount:"",
                favETime:'',
                favSTime:"",
                favWay:this.$route.query.favWay,
              }
            ],
            fobjects:[],
            payType:"",
            validETime:"",
            validSTime:''
          },
          editForm:{},
          activeAdv:"advInfo",
          activ:"set1",
          activeName: this.$route.query.favTarget,
          total:1,
          total1:1,
          total2:1,
          total3:1,
          total4:1,
          currentPage:1, 
          currentPage1:1,
          currentPage2:1,
          currentPage3:1,
          pagesize:10, 
          pagesize1:5,
          pagesize2:10,
          pagesize3:10,
          formLabelWidth: '150px',
          dialogVisible:false,
          dialogVisible_s:false,
          dialogVisibleInfo:false,
          editdialogVisible:false,
          keyword:'',
          r_deviceId:"",
          r_keyword:"",
          advkeyword:'',
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
                prop: 'name',
                label: '名称 ',
                sortable: true,
                sortMethod: this.handleNameSort
              },
              {
                prop: 'favTypeName',
                label: '优惠方式',
                 minWidth: '80'
             
              },
              {
                prop: 'validSTime',
                label: '优惠开始时间',
                minWidth: '120'
              },
              {
                prop: 'validETime',
                label: '优惠结束时间'
              },
              {
                prop: 'createTime',
                label: '创建时间'
              },
              {
                prop: 'createTime',
                label: '更新时间',
                minWidth: '120'
              },
              {
                prop: 'curStateName',
                label: '状态',
                minWidth: '120'
              }
          ],
          tableHeader1: [
              {
                type:"selection",
                width:"55"
              },
              {
                prop: 'name',
                label: '名称 ',
                sortable: true,
                sortMethod: this.handleNameSort
              },
              {
                prop: 'favTypeName',
                label: '优惠方式',
                 minWidth: '80'
             
              },
              {
                prop: 'validSTime',
                label: '优惠开始时间',
                minWidth: '120'
              },
              {
                prop: 'validETime',
                label: '优惠结束时间'
              },
              {
                prop: 'createTime',
                label: '创建时间'
              },
              {
                prop: 'createTime',
                label: '更新时间',
                minWidth: '120'
              },
              {
                prop: 'curStateName',
                label: '状态',
                minWidth: '120'
              }
          ],
          tableData: [],
          filterText: '',
          advData:[],
          rowAdv:{},
          adevices:[],
          infoData:[],
          tfData:[],
          ProductList:[],
          favTarget:this.$route.query.favTarget,
          Rules:{
              name:[
                { required: true, message: ' ', trigger: 'change' },
              ],
              favSTime:[
                { required: true, message: ' ', trigger: 'change' },
              ],
              validSTime: [
                 {  required: true, message:' ',  trigger: 'change' }
              ],
              favType: [
                 {  required: true, message:' ',  trigger: 'change' }
              ],
              payType: [
                 {  required: true, message:' ',  trigger: 'change' }
              ]
          }

        }
  },
  watch: {
    filterText(val) {
      this.$refs.tree2.filter(val)
    }
  },
  created(){
    if(this.$route.query.favTarget==1){
      this.labelText= "选择售卖机"
      this.labelSeeText = "投放设备信息"
    }else{
      this.labelText= "选择商品"
      this.labelSeeText = "选择商品信息"
    }
  },
  methods: {
    tableRowClassName({row, rowIndex}) {
        if (rowIndex === 1) {
          return 'warning-row';
        } else if (rowIndex === 3) {
          return 'success-row';
        }
        return '';
      },
    handleChange(value, direction, movedKeys) {
        this.adevices=value
        console.log(value, direction, movedKeys);
    },
    deleteAdvRow(index, rows){
        rows.splice(index, 1);
    },
    deleteAdvStrategies(index, rows){
        rows.splice(index, 1);
    },
    handleAdvClick(row){
      this.$notify({
        title: '成功',
        message: "选择成功",
        type: 'success'
      });
      this.rowAdv.mstrategies.push(row)
    },
    addAdv(row){
      this.rowAdv = row;
      this.getAdvertMaterialList("1")
      this.dialogVisible_s=true;
    },
    removftimes(index,rows){
      rows.splice(index, 1);
    },
    addftimes(){
        var _this = this;
        this.addForm.ftimes.push({
          discount: "",
          favETime: "", 
          favSTime: "", 
          favWay: _this.addForm.favType  
        })
    },
    next() {
        if (this.activ=="set1") {
            this.activ = "set2";
        }else if(this.activ=="set2"){
            this.activ = "set3";
        }
    },
    prev(){
      if (this.activ=="set3") {
            this.activ = "set2";
        }else if(this.activ=="set2"){
            this.activ = "set1";
        }
    },
    handleClicka(tab, event) {
        console.log(tab, event);
    },
    handleClick(tab, event) {
          if(tab.name==1){
            this.labelText= "选择售卖机"
            this.labelSeeText="投放设备信息"
          }else{
            this.labelText= "选择商品"
             this.labelSeeText="选择商品信息"
          }
        this.$router.push({
          path: '/adv/Discount/consumption/list',
          query:{ 
            favWay:'02',
            favTarget:tab.name
          }
        });
        this.favTarget = tab.name
        this.getData("1")
        this.getVendingListData("1")
        console.log(tab, event);
    },
    handleClickAdv(tab, event) {
        console.log(tab, event);
    },
    handdetails(row){//查看
      this.dialogVisible = true
      this.editForm = Object.assign({}, row);
    },
    handleSizeChange: function (size) { 
     this.pagesize = size; 
    }, 
    handleSizeChange3: function (size) { 
     this.pagesize3 = size; 
    }, 
    handleCurrentChange: function(currentPage){ 
     this.currentPage = currentPage;
     this.getData(currentPage)
    } ,
    handleCurrentChange1: function(currentPage){ 
     this.currentPage1 = currentPage;
     this.getAdvertMaterialList(currentPage)
    } ,
    handleCurrentChange2: function(currentPage){ 
     this.currentPage2 = currentPage;
     this.getSelectAdvertDeviceRwList(currentPage)
    } ,
    handleCurrentChange3: function(currentPage){ 
     this.currentPage3 = currentPage;
    } ,
    handleSelectionChange(rows) {//全选
        const _this = this;
        _this.multipleSelection = ""
        if (rows) {
          rows.forEach(row => {
             _this.multipleSelection += row.favourableId+ ",";
      
          });
        } 
    },
    search(){
      this.getData("1")
    },
    search_r(){
       this.getSelectAdvertDeviceRwList("1")
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
                    ids:_this.multipleSelection==""||undefined ? row.favourableId : _this.multipleSelection
                  }
                  removefavourableConfig(para).then((res) => {
                        if(res.zhead.reTCode=="0000"){
                          
                        NProgress.done();
                        this.$notify({
                          title: '成功',
                          message: res.zhead.retMsg,
                          type: 'success'
                        });
                        _this.getData("1")
                        //rows.splice(index, 1);
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
      this.dialogVisible= true
    },
    addSubmitForm(formName,curState,favTarget) {//提交新增窗口
        var _this= this;
        this.editForm.fobjects=[]//清空重新赋值
        this.$refs[formName].validate((valid) => {
          if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
                      NProgress.start();
                      if(formName=="addForm"){
                          _this.adevicesValue.forEach((item, index) => {
                              _this.addForm.fobjects.push({
                              discount:_this.addForm.ftimes[0].discount,
                              favObjId:item,
                              favWay:_this.$route.query.favWay,
                              favType:_this.addForm.favType,
                              validETime:_this.addForm.ftimes[0].favETime,
                              validSTime:_this.addForm.ftimes[0].favSTime,
                              });
                          })
                          let para = Object.assign({}, this.addForm);
                          para.curState=curState
                          para.favTarget=favTarget
                          addfavourableConfig(para).then((res) => {
                                if(res.zhead.reTCode=="0000"){
                                NProgress.done();
                                this.dialogVisible=false
                                this.$notify({
                                  title: '成功',
                                  message: res.zhead.retMsg,
                                  type: 'success'
                                });
                                _this.addForm={
                                    curState:"",
                                    favTarget:_this.$route.query.favTarget,
                                    favType:"",
                                    favWay:_this.$route.query.favWay,
                                    name:"",
                                    ftimes:[
                                      {
                                        discount:"",
                                        favETime:'',
                                        favSTime:"",
                                        favWay:_this.$route.query.favWay,
                                      }
                                    ],
                                    fobjects:[],
                                    payType:"",
                                    validETime:"",
                                    validSTime:''
                                },
                                this.getData("1")
                                }else{
                                  this.$notify({
                                    title: '失败',
                                    message: res.zhead.retMsg,
                                    type: 'error'
                                  });
                              }
                          });
                      }else if(formName == "editForm"){
                          _this.adevicesValueEdit.forEach((item, index) => {
                              _this.editForm.fobjects.push({
                              discount:_this.editForm.ftimes[0].discount,
                              favObjId:item,
                              favWay:_this.$route.query.favWay,
                              favType:_this.editForm.favType,
                              validETime:_this.editForm.ftimes[0].favETime,
                              validSTime:_this.editForm.ftimes[0].favSTime,
                              });
                          })
                          let para = Object.assign({}, this.editForm);
                          para.curState=curState
                          para.favTarget=favTarget
                          editFavourableConfig(para).then((res) => {
                                if(res.zhead.reTCode=="0000"){
                                NProgress.done();
                                this.editdialogVisible=false
                                this.$notify({
                                  title: '成功',
                                  message: res.zhead.retMsg,
                                  type: 'success'
                                });
                                _this.editForm={},
                                this.getData("1")
                                }else{
                                  this.$notify({
                                    title: '失败',
                                    message: res.zhead.retMsg,
                                    type: 'error'
                                  });
                              }
                          });
                      }
                      
          });
          } else {
            console.log('error submit!!');
            return false;
          }
        });
    },
    handleEdit(index, row) {//显示编辑窗口
      var _this = this;
      _this.adevicesValueEdit = []
      const listQuery = {
        favourableId:row.favourableId,
        favTarget:row.favTarget
      }
      getEditFav(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
            _this.editdialogVisible = true
            _this.editForm = response.zbody.datas
            response.zbody.datas.fobjects.forEach(function(item,index){
            _this.adevicesValueEdit.push(item.favObjId)
            })
            NProgress.done();
            console.log("获得编辑信息：：",_this.editForm)
         }else{
             this.$notify({
              title: '失败',
              message: res.zhead.retMsg,
              type: 'error'
            });
         }
      })
    },
    handleNameSort () {
      console.log('handleNameSort')
    },
    getData(page) {
      NProgress.start();
      var _this = this;
      var a =_this.favTarget
      this.listLoading = true
      const listQuery = {
        name:_this.keyword,
        favWay:(_this.$route.query.favWay).toString(),
        favTarget:_this.favTarget,  
        pageSize:_this.pagesize,
        pageNum:page,
        orderByColumn:'createtime',
        isAsc:'desc'//desc
      }
  
      favourableConfigList(listQuery).then(response => {
    
         if(response.zhead.reTCode==="0000"){
            NProgress.done();
          if(_this.favTarget == 1){
            _this.total = response.zbody.datas.total;  
            _this.tableData = response.zbody.datas.rows;

          }else if(_this.favTarget == 2){
            _this.total4 = response.zbody.datas.total;  
            _this.tableData_D = response.zbody.datas.rows;
          }


          


          console.log(_this.tableData)
         }else{
             this.$notify({
              title: '失败',
              message: res.zhead.retMsg,
              type: 'error'
            });
         }
       
      })
    },
    getAdvertMaterialList(page) {
      var _this = this;
      const listQuery = {
        mediaName:_this.advkeyword,
        mediaType:"",
        pageSize:_this.pagesize1,
        pageNum:page,
        orderByColumn:'createtime',
        isAsc:'desc'//desc
      }
      advertMaterialList(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
            NProgress.done();
          _this.total1 = response.zbody.datas.total;  
          _this.advData = response.zbody.datas.rows;
          console.log("列表：：：：：：",_this.tableData)
         }else{
             this.$notify({
              title: '失败',
              message: res.zhead.retMsg,
              type: 'error'
            });
         }
      })
    },
    getVendingListData(page) {
      var _this = this;
      this.data =[];
      this.dataEdit = [];
      const listQuery = {
        pageSize:_this.pagesize1,
        pageNum:page,
        orderByColumn:'createtime',
        isAsc:'desc'//desc
      }
      if(_this.favTarget==1){
          getVendingList(listQuery).then(response => {
                 if(response.zhead.reTCode==="0000"){
                    NProgress.done();
                    console.log("获得穿梭列表：：",response.zbody.datas.rows)
                    response.zbody.datas.rows.forEach((item, index) => {
                        _this.data.push({
                              netSate:item.netSate,
                              favObjId: item.siteId,
                              favObjName: item.siteName,
                              lineName:item.lineName,
                              cabinetTypeName:item.cabinetTypeName,
                              corpName:item.corpName,
                              mediaType:item.mediaType,
                        });
                        _this.dataEdit.push({
                              netSate:item.netSate,
                              favObjId: item.siteId,
                              favObjName: item.siteName,
                              lineName:item.lineName,
                              cabinetTypeName:item.cabinetTypeName,
                              corpName:item.corpName,
                              mediaType:item.mediaType,
                        });
                    })

                    console.log("穿梭数据源啊：",_this.data)
                 }else{
                     this.$notify({
                      title: '失败',
                      message: res.zhead.retMsg,
                      type: 'error'
                    });
                 }
          })
      }else if(_this.favTarget==2){
          productInfo(listQuery).then(response => {
             if(response.zhead.reTCode==="0000"){
                NProgress.done();
                response.zbody.datas.rows.forEach((item, index) => {
                    _this.data.push({
                         // netSate:item.netSate,
                          favObjId:item.productId,
                          favObjName:item.name
                          //lineName:item.lineName,
                          //cabinetTypeName:item.cabinetTypeName,
                         // corpName:item.corpName,
                          //mediaType:item.mediaType,
                    });
                    _this.dataEdit.push({
                         // netSate:item.netSate,
                          favObjId:item.productId,
                          favObjName:item.name,
                          pic:item.pic
                          //lineName:item.lineName,
                          //cabinetTypeName:item.cabinetTypeName,
                         // corpName:item.corpName,
                          //mediaType:item.mediaType,
                    });

                })
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
   
    getAdvInfo(row) {
      var _this = this;
      const listQuery = {
        favourableId:row.favourableId
      }
      favourableConfigInfo(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
            _this.dialogVisibleInfo = true
            _this.infoData =response.zbody.datas
            NProgress.done();
         }else{
             this.$notify({
              title: '失败',
              message: res.zhead.retMsg,
              type: 'error'
            });
         }
      })
      
      if(this.$route.query.favTarget=="1"){
          this.getSelectFavUseList(row.favourableId)
      }else if(this.$route.query.favTarget=="2"){
          this.getselectFavProductList(row.favourableId)
      }
    },
    //投放得商品查询
    getselectFavProductList(favourableId) {
      var _this = this;
      const listQuery = {
        favourableId:favourableId
      }

      selectFavProductList(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
            _this.dialogVisibleInfo = true
            _this.ProductList =response.zbody.datas
            console.log("商品列表：：：",_this.ProductList)
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
    //投放设备列表
    getSelectFavUseList(favourableId){
      const _this = this;
      const listQuery1 = {
        districtId:'',
        lineId:'',
        favObjId:'',
        favourableId:favourableId,
        pageSize:_this.pagesize,
        pageNum:"1",
        orderByColumn:"createtime",
        isAsc:"desc"
      }

      selectFavUseList(listQuery1).then(response => {
         if(response.zhead.reTCode==="0000"){
            _this.total3 = response.zbody.datas.total
            _this.tfData =response.zbody.datas
            NProgress.done();
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
    this.getVendingListData()
  },
  components: {
    wTable
  }
}
</script>
<style>
.el-transfer-panel:nth-child(1){width: 520px!important;}
 .el-table .warning-row {
    background: oldlace;
  }

  .el-table .success-row {
    background: #f0f9eb;
  }
</style>
<style scoped>
  .tiems{margin-bottom:10px;}
  .tiems span{ display:inline-block; padding-right:30px;}
  .tiems span em{ display:inline-block; padding-right:5px; font-style:normal}
  .grid-left{ display:flex; justify-content:center; margin-top:20px; }
  .grid-left span{ color:#000 }
  .grid-right{display:flex; justify-content:center; text-align:center; margin-top:20px;}
  .grid-right span{ color:#000 }
  .imgbox{width:50px; height:50px;}
  .imgbox img{width:100%;}
  .dialog-prevNext{text-align:right;}
  .div-warp{padding:0 20px;}
  .pageBox{background: #fff; padding:20px 0;}
  .m-top{margin-top:20px;}

</style>