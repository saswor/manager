<template>
 <div class="div-warp">
      <!-- 查看升级 -->
       <el-dialog
          title="查看升级"
          :visible.sync="dialogVisibleInfo"
          width="1300px"
          >

          <el-tabs v-model="activeAdv" @tab-click="handleClickAdv">
            <el-tab-pane label="升级信息" name="upgradeInfo">
                <el-form :model="viewForm" :rules="Rules" ref="addForm"  label-width="200px" disabled>
                        <el-form-item label="升级名称：" prop="name" size="small" :label-width="formLabelWidth" >
                            <el-input v-model="viewForm.uName"></el-input>
                        </el-form-item>
                        <el-form-item label="升级类型：" prop="uType" size="small" :label-width="formLabelWidth">
                            <el-select v-model="viewForm.uType" placeholder="请选择">
                              <el-option
                                v-for="item in uTypeList"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                              </el-option>
                            </el-select>  
                        </el-form-item>
                        <el-form-item label="下发类型：" prop="issuedType" size="small" :label-width="formLabelWidth">
                            <el-select v-model="viewForm.issuedType" placeholder="请选择">
                              <el-option
                                v-for="item in issuedTypeList"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                              </el-option>
                            </el-select>  
                        </el-form-item>
                        <el-form-item   label="延迟时间：" prop="planTime" size="small" :label-width="formLabelWidth">
                          <el-date-picker
                            v-model="viewForm.planTime"
                            type="datetime"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            placeholder="延迟时间">
                          </el-date-picker>
                        </el-form-item>
                        <el-form-item label="升级版本：" prop="versionId" size="small" :label-width="formLabelWidth">
                            <el-select v-model="viewForm.versionId" placeholder="请选择">
                              <el-option
                                v-for="item in versionList"
                                :key="item.versionId"
                                :label="item.vName"
                                :value="item.versionId">
                              </el-option>
                            </el-select>  
                        </el-form-item>

                        <el-table
                          :data="viewForm.softwareVersionList"
                          v-if="viewForm.versionId!=''&&viewForm.versionId!=null&&viewForm.versionId!=undefined"
                          border
                          style="margin: 10px">
                          <el-table-column
                            prop="vName"
                            :show-overflow-tooltip="true"
                            label="版本号">
                          </el-table-column>
                          <el-table-column
                            prop="uTypeName"
                            :show-overflow-tooltip="true"
                            label="类型">
                          </el-table-column>
                          <el-table-column
                            prop="fileNum"
                            :show-overflow-tooltip="true"
                            label="文件数">
                          </el-table-column>
                          <el-table-column
                            prop="description"
                            :show-overflow-tooltip="true"
                            label="描述">
                          </el-table-column>
                        </el-table>
                </el-form>


            </el-tab-pane>
            <el-tab-pane label="售卖机信息" name="advTInfo">
                <el-table
                      :data="tfData.slice((currentPage3-1)*pagesize3,currentPage3*pagesize3)"
                      style="width: 100%">
                      <el-table-column
                        prop="netSateName"
                        label="网络状态">
                      </el-table-column>
                      <el-table-column
                        prop="siteName"
                        label="名称">
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
                        prop="seqId"
                        label="设备序列号">
                      </el-table-column>
                      <!-- <el-table-column
                        prop="factoryName"
                        label="厂家">
                      </el-table-column> -->
                      <el-table-column
                        prop="signalValue"
                        label="信号强度">
                      </el-table-column>
                      <el-table-column
                        prop="stateName"
                        label="升级状态">
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
            </el-tab-pane>
            
          </el-tabs>
        </el-dialog>

      <el-dialog
          title="新增升级"
          v-if="dialogVisible"
          :visible.sync="dialogVisible"
          width="1300px"
          >
                 <el-form :model="addForm" :rules="Rules" ref="addForm"  label-width="200px">
                    <el-tabs v-model="activ" @tab-click="handleClicka">
                      <el-tab-pane label="基本信息" disabled name="set1">
                        <el-form-item label="升级名称：" prop="uName" size="small" :label-width="formLabelWidth">
                            <el-input v-model="addForm.uName"></el-input>
                        </el-form-item>
                        <el-form-item label="升级类型：" prop="uType" size="small" :label-width="formLabelWidth">
                            <el-select v-model="addForm.uType" placeholder="请选择">
                              <el-option
                                v-for="item in uTypeList"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                              </el-option>
                            </el-select>  
                        </el-form-item>
                        <el-form-item label="下发类型：" prop="issuedType" size="small" :label-width="formLabelWidth">
                            <el-select v-model="addForm.issuedType" placeholder="请选择">
                              <el-option
                                v-for="item in issuedTypeList"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                              </el-option>
                            </el-select>  
                        </el-form-item>
                        <el-form-item label="延迟时间：" v-if="addForm.issuedType==2" prop="planTime" size="small" :label-width="formLabelWidth">
                          <el-date-picker
                            v-model="addForm.planTime"
                            type="datetime"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            placeholder="延迟时间">
                          </el-date-picker>
                        </el-form-item>
                        <el-form-item label="升级版本：" prop="versionId" size="small" :label-width="formLabelWidth">
                            <el-select v-model="addForm.versionId" placeholder="请选择" @change="addVersionChange(addForm)">
                              <el-option
                                v-for="item in versionList"
                                :key="item.versionId"
                                :label="item.vName"
                                :value="item.versionId">
                              </el-option>
                            </el-select>  
                        </el-form-item>
                        <el-table
                          :data="addForm.softwareVersionList"
                          v-if="addForm.versionId!=''&&addForm.versionId!=null&&addForm.versionId!=undefined"
                          border
                          style="margin: 10px">
                          <el-table-column
                            prop="vName"
                            :show-overflow-tooltip="true"
                            label="版本号">
                          </el-table-column>
                          <el-table-column
                            prop="uTypeName"
                            :show-overflow-tooltip="true"
                            label="类型">
                          </el-table-column>
                          <el-table-column
                            prop="fileNum"
                            :show-overflow-tooltip="true"
                            label="文件数">
                          </el-table-column>
                          <el-table-column
                            prop="description"
                            :show-overflow-tooltip="true"
                            label="描述">
                          </el-table-column>
                        </el-table>
                        <div class="dialog-prevNext">
                          <!-- <el-button style="margin-top: 12px;" @click="prev">上一步</el-button> -->
                          <el-button style="margin-top: 12px;" @click="next('addForm')">下一步</el-button>
                        </div>
                      </el-tab-pane>

                      <el-tab-pane label="选择升级设备" disabled name="set2">
                          <!-- 选择升级设备 -->
                              <el-transfer
                                style="text-align: left; display: inline-block"
                                v-model="adevicesValue"
                                filterable
                                :left-default-checked="[2, 3]"
                                :right-default-checked="[1]"
                                :render-content="renderFunc"
                                :titles="['售卖机列表', '下发升级的售卖机']"
                                :button-texts="['到左边', '到右边']"
                                :format="{
                                  noChecked: '${total}',
                                  hasChecked: '${checked}/${total}'
                                }"
                                @change="handleChange"
                                :props="{
                                  key: 'deviceId',
                                  label: 'deviceName'
                                }"
                                :data="data">
                              </el-transfer>
                          <!-- 选择投放设备 end -->
                        <div class="dialog-prevNext">
                          <el-button style="margin-top: 12px;" @click="prev">上一步</el-button>
                          <!-- <el-button style="margin-top: 12px;" @click="addSubmitForm('addForm')" >暂存</el-button> -->
                          <el-button style="margin-top: 12px;" @click="addSubmitForm('addForm')" >升级</el-button>
                        </div>
                      </el-tab-pane>
                    </el-tabs>
                  </el-form>
      </el-dialog>
      <!-- 编辑弹框 -->
      <el-dialog
          title="编辑升级"
          v-if="editdialogVisible"
          :visible.sync="editdialogVisible"
          width="1300px"
          >
                 <el-form :model="editForm" :rules="Rules" ref="editForm"  label-width="200px">
                    <el-tabs v-model="activ" @tab-click="handleClicka">
                      <el-tab-pane label="基本信息" disabled name="set1">
                        <el-form-item label="升级名称：" prop="uName" size="small" :label-width="formLabelWidth">
                            <el-input v-model="editForm.uName"></el-input>
                        </el-form-item>
                        <el-form-item label="升级类型：" prop="uType" size="small" :label-width="formLabelWidth">
                            <el-select v-model="editForm.uType" placeholder="请选择">
                              <el-option
                                v-for="item in uTypeList"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                              </el-option>
                            </el-select>  
                        </el-form-item>
                        <el-form-item label="下发类型: " prop="issuedType" size="small" :label-width="formLabelWidth">
                            <el-select v-model="editForm.issuedType" placeholder="请选择">
                              <el-option
                                v-for="item in issuedTypeList"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                              </el-option>
                            </el-select>  
                        </el-form-item>
                        <el-form-item label="升级文件：" prop="versionId" size="small" :label-width="formLabelWidth">
                            <el-select v-model="editForm.versionId" placeholder="请选择" @change="editVersionChange(editForm)">
                              <el-option
                                v-for="item in versionList"
                                :key="item.versionId"
                                :label="item.vName"
                                :value="item.versionId">
                              </el-option>
                            </el-select>  
                        </el-form-item>
                        <el-table
                          :data="editForm.softwareVersionList"
                          v-if="editForm.versionId!=''&&editForm.versionId!=null&&editForm.versionId!=undefined"
                          border
                          style="margin: 10px">
                          <el-table-column
                            prop="vName"
                            :show-overflow-tooltip="true"
                            label="版本号">
                          </el-table-column>
                          <el-table-column
                            prop="uTypeName"
                            :show-overflow-tooltip="true"
                            label="类型">
                          </el-table-column>
                          <el-table-column
                            prop="fileNum"
                            :show-overflow-tooltip="true"
                            label="文件数">
                          </el-table-column>
                          <el-table-column
                            prop="description"
                            :show-overflow-tooltip="true"
                            label="描述">
                          </el-table-column>
                        </el-table>
                        <div class="dialog-prevNext">
                          <!-- <el-button style="margin-top: 12px;" @click="prev">上一步</el-button> -->
                          <el-button style="margin-top: 12px;" @click="next('editForm')">下一步</el-button>
                        </div>
                      </el-tab-pane>
                      
                      <el-tab-pane label="选择升级设备" disabled name="set2">

                          <!-- 选择升级设备 -->
                              <el-transfer
                                style="text-align: left; display: inline-block"
                                v-model="adevicesValue"
                                filterable
                                :left-default-checked="[2, 3]"
                                :right-default-checked="[1]"
                                :render-content="renderFunc"
                                :titles="['售卖机列表', '下发升级的售卖机']"
                                :button-texts="['到左边', '到右边']"
                                :format="{
                                  noChecked: '${total}',
                                  hasChecked: '${checked}/${total}'
                                }"
                                :props="{
                                  key: 'deviceId',
                                  label: 'deviceName'
                                }"
                                @change="handleChange"
                                :data="data">
                              </el-transfer>
                          <!-- 选择投放设备 end -->



                        <div class="dialog-prevNext">
                          <el-button style="margin-top: 12px;" @click="prev">上一步</el-button>
                          <!-- <el-button style="margin-top: 12px;" @click="addSubmitForm('editForm')" >暂存</el-button> -->
                          <el-button style="margin-top: 12px;" @click="addSubmitForm('editForm')">升级</el-button>
                        </div>
                      </el-tab-pane>
                    </el-tabs>
                  </el-form>
                  
      </el-dialog>



      <!-- 编辑弹框结束 -->
      <div class="block-warp" style="margin-top:20px">
        
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="升级管理" name="first">
          <div class="search-warp">
              <el-row :gutter="20" >
                  <el-col :span="4">
                    <div class="grid-content bg-purple">
                        <el-input
                        placeholder="请输名称"
                        size="small"
                        v-model="keyword"
                        clearable>
                      </el-input>

                    </div>
                  </el-col>
                  <el-col :span="16">
                      <div class="grid-content bg-purple"> 
                        <el-button type="success" size="small" @click.native.prevent="search" v-permission="['sys:vending:upgrade:select']">查询</el-button>
                        <el-button type="primary" size="small" @click="handleAdd" v-permission="['sys:vending:upgrade:add']">新增</el-button>
                        <el-button type="danger" size="small" @click="deleteRow" v-permission="['sys:vending:upgrade:del']">删除</el-button>
                      </div>
                  </el-col>
                  <el-col :span="4"><div class="grid-content bg-purple"></div></el-col>
              </el-row>
          </div>
           <wTable :data="tableData" :header="tableHeader" :handleSelectionChange="handleSelectionChange" :option="tableOption">
              <el-table-column slot="fixed"
                fixed="right"
                label="操作"
                width="150">
                  <template  scope="scope">
                    <div style="text-align:center;">
                      <el-button type="success" size="mini" icon="el-icon-view" circle @click="getUpgradeInfo(scope.row)" v-permission="['sys:vending:upgrade:view']"></el-button>
                      <el-button type="primary" size="mini" icon="el-icon-edit" circle @click="handleEdit(scope.$index, scope.row)" v-permission="['sys:vending:upgrade:edit']"></el-button>
                      <el-button type="danger"  size="mini" icon="el-icon-delete" circle @click.native.prevent="deleteRow(scope.row)" v-permission="['sys:vending:upgrade:del']"></el-button>
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
        <el-tab-pane label="升级任务列表" name="second">
            <div class="search-warp">
                <el-row :gutter="20" >
                    <el-col :span="4">
                      <div class="grid-content bg-purple">
                          <el-input
                          placeholder="任务名称"
                          size="small"
                          v-model="r_keyword"
                          clearable>
                        </el-input>

                      </div>
                    </el-col>
                    <el-col :span="4">
                      <div class="grid-content bg-purple">
                          <el-input
                          placeholder="售卖机编号"
                          size="small"
                          v-model="r_siteId"
                          clearable>
                        </el-input>

                      </div>
                    </el-col>
                    <el-col :span="16">
                        <div class="grid-content bg-purple"> 
                          <el-button type="success" size="small" @click.native.prevent="search_r" v-permission="['sys:advert:device:view']">查询</el-button>
                        </div>
                    </el-col>
                   
                </el-row>
            </div>
            <el-table
              :data="DeviceRwData"
              border
              style="width: 100%">
                  <el-table-column
                    prop="uName"
                    label="任务名称"
                    >
                  </el-table-column>
                  <el-table-column
                    prop="siteId"
                    label="售货机编号"
                    >
                  </el-table-column>
                  <el-table-column
                    prop="siteName"
                    label="售货机名称"
                    >
                  </el-table-column>
                  <el-table-column
                    prop="seqId"
                    label="设备序列号"
                    >
                  </el-table-column>
                  <el-table-column
                    prop="upgradeId"
                    label="升级编号"
                    >
                  </el-table-column>
                  <el-table-column
                    prop="stateName"
                    label="升级状态"
                    >
                  </el-table-column>
                  <el-table-column
                    prop="createTime"
                    label="创建时间">
                  </el-table-column>
            </el-table>
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
import {upgradeInfo,getVendingVersionList,getVendingUpgradeList,addVendingUpgrade,updateVendingUpgrade,deleteVendingUpgrade,upgradeTaskList,getVendingVersionDetails} from '@/api/equipment'
import {getVendingList} from '@/api/dictionaries'
import NProgress from 'nprogress'
import wTable from '@/components/table/w-table.vue'
import permission from '@/directive/permission/index.js'
export default {
  directives: { permission },
  data() {
        return {
          data:[],
          adevicesValue: [],
          renderFunc(h, option) {
            return <span>{option.netSate}-{ option.deviceId } - { option.deviceName }-{option.lineName}-{option.cabinetTypeName}-{option.mediaType}</span>;
          },
          DeviceRwData:[],
          addForm:{
            uName:"",
            uType:"",
            issuedType:"",
            planTime:"",
            versionId:"",
            vendingUpgradeTaskList:[]
          },
          viewForm:{},
          editForm:{
            uName:"",
            uType:"",
            issuedType:"",
            planTime:"",
            versionId:"",
            vendingUpgradeTaskList:[]
          },
          activeAdv:"upgradeInfo",
          issuedTypeList: [
            {
              value: '1',
              label: '立即升级'
            }, {
              value: '2',
              label: '延迟升级'
            }
          ],
          uTypeList:[
            {
              value: '1',
              label: 'app升级'
            }, {
              value: '2',
              label: '固件升级'
            }
          ],
          versionList:[],
          activ:"set1",
          activeName: 'first',
          total:1,
          total1:1,
          total2:1,
          total3:1,
          currentPage:1, 
          currentPage1:1,
          currentPage1:1,
          currentPage3:1,
          pagesize:10, 
          pagesize1:5,
          pagesize2:10,
          pagesize3:10,
          formLabelWidth: '120px',
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
                prop: 'uName',
                label: '升级名称 ',
                sortable: true,
                sortMethod: this.handleNameSort
              },
              {
                prop: 'uTypeName',
                label: '升级类型'
             
              },
              {
                prop: 'issuedTypeName',
                label: '下发类型'
              },
              {
                prop: 'planTime',
                label: '升级时间'
              },
              {
                prop: 'createTime',
                label: '创建时间'
              }
          ],
          tableData: [],
          filterText: '',
          advData:[],
          rowAdv:{},
          adevices:[],
          infoData:[],
          tfData:[],
          seqId:0,
          Rules:{
              uName:[
                { required: true, message: '升级名称不能为空', trigger: 'change' },
                // { max: 50, message: '升级名称最多50个字符', trigger: 'change' },
              ],
              uType: [
                 {  required: true, message:'升级类型不能为空',  trigger: 'change' }
              ],
              issuedType: [
                 {  required: true, message:'下发类型不能为空',  trigger: 'change' }
              ],
              versionId: [
                 {  required: true, message:'升级版本不能为空',  trigger: 'change' }
              ]
          }

        }
  },
  watch: {
    filterText(val) {
      this.$refs.tree2.filter(val)
    }
  },
  methods: {
    getVendingVersionData(){//查询升级版本
        var _this = this;
        const listQuery = {

        }
        getVendingVersionList(listQuery).then(response => {
          if(response.zhead.reTCode==="0000"){
            _this.versionList = response.zbody.datas.rows;
          }else{
             this.$notify({
                title: '失败',
                message: response.zhead.retMsg,
                type: 'error'
              });
          }

        })
    },
    addVersionChange(){
      this.addForm.softwareVersionList=[];
      var _this = this;
      const listQuery = {
        versionId:this.addForm.versionId
      }
      getVendingVersionDetails(listQuery).then(response => {
          if(response.zhead.reTCode==="0000"){
            _this.addForm.softwareVersionList.push(response.zbody.datas);
          }else{
             this.$notify({
                title: '失败',
                message: response.zhead.retMsg,
                type: 'error'
              });
          }

        })
    },
     editVersionChange(){
      this.editForm.softwareVersionList=[];
      var _this = this;
      const listQuery = {
        versionId:this.editForm.versionId
      }
      getVendingVersionDetails(listQuery).then(response => {
          if(response.zhead.reTCode==="0000"){
            _this.editForm.softwareVersionList.push(response.zbody.datas);
          }else{
             this.$notify({
                title: '失败',
                message: response.zhead.retMsg,
                type: 'error'
              });
          }

        })
    },
    viewVersionChange(){
      this.viewForm.softwareVersionList=[];
      var _this = this;
      const listQuery = {
        versionId:this.viewForm.versionId
      }
      getVendingVersionDetails(listQuery).then(response => {
          if(response.zhead.reTCode==="0000"){
            _this.viewForm.softwareVersionList.push(response.zbody.datas);
          }else{
             this.$notify({
                title: '失败',
                message: response.zhead.retMsg,
                type: 'error'
              });
          }

        })
    },
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
        console.log("ead:::",this.editForm.adevices)
        console.log(this.adevices)
    },
    deleteAdvRow(index, rows){
        rows.splice(index, 1);
    },
    deleteAdvStrategies(index, rows){
        this.seqId=this.seqId-1
        rows.splice(index, 1);
    },
    handleAdvClick(row){
      const _this = this;
      if(this.rowAdv.mstrategies.length!=0){
          this.rowAdv.mstrategies.forEach(function(item,index){
              if(item.materialId==row.materialId){
                    _this.$message({
                      showClose: true,
                      message: '不可重复添加！',
                      type: 'error',
                      duration:2000
                    });
                    throw new Error("end")
                }else{
                    _this.$message({
                      showClose: true,
                      message: '选择成功！',
                      type: 'success',
                      duration:2000
                    });
                    _this.rowAdv.mstrategies.push(row)
                }
          })
      }else{
            _this.$message({
              showClose: true,
              message: '选择成功！',
              type: 'success',
              duration:2000
            });
            this.rowAdv.mstrategies.push(row)
      }
     
    },
    addAdv(row){
      this.rowAdv = row;
      this.getAdvertMaterialList("1")
      this.dialogVisible_s=true;
    },
    next(formName) {
      this.$refs[formName].validate((valid) => {
          if (valid) {
            if (this.activ=="set1") {
                this.activ = "set2";
            }else if(this.activ=="set2"){
                this.activ = "set3";
            }
          }
      });
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
     this.getUpgradeTaskList(currentPage)
    } ,
    handleCurrentChange3: function(currentPage){ 
     this.currentPage3 = currentPage;
    } ,
    handleSelectionChange(rows) {//全选
        const _this = this;
        _this.multipleSelection = ""
        if (rows) {
          rows.forEach(row => {
             _this.multipleSelection += row.upgradeId+ ",";
      
          });
        } 

    },
    search(){
      this.getData("1")
    },
    search_r(){
       this.getUpgradeTaskList("1")
    },
    changeType(item){
      item.playEtime=""
      item.playSTime=""
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
                    ids:_this.multipleSelection=="" ? row.upgradeId : _this.multipleSelection
                  }
                  deleteVendingUpgrade(para).then((res) => {
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
      this.activ = "set1"
      this.addForm={
            uName:"",
            uType:"",
            issuedType:"",
            planTime:"",
            versionId:"",
            vendingUpgradeTaskList:[]
          };
    },
    addSubmitForm(formName) {//提交新增编辑窗口
        var _this= this;
        console.log(this.adevices)
        
        this.$refs[formName].validate((valid) => {
          if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
                      NProgress.start();
                      if(formName=="addForm"){
                        _this.adevicesValue.forEach((item, index) => {
                                _this.addForm.vendingUpgradeTaskList.push({
                                  siteId:item,
                                });

                        })
                        let para = Object.assign({}, this.addForm);
                        addVendingUpgrade(para).then((res) => {
                              if(res.zhead.reTCode=="0000"){
                              
                              NProgress.done();
                              this.$notify({
                                title: '成功',
                                message: res.zhead.retMsg,
                                type: 'success'
                              });
                              _this.addForm={
                                  uName:"",
                                  uType:"",
                                  issuedType:"",
                                  planTime:"",
                                  versionId:"",
                                  vendingUpgradeTaskList:[]
                              },
                              _this.dialogVisible = false;
                              _this.adevicesValue = []
                              _this.getData("1")
                              _this.getUpgradeTaskList(1)
                              }else{
                                this.$notify({
                                  title: '失败',
                                  message: res.zhead.retMsg,
                                  type: 'error'
                                });
                            }
                        });
                      }else if(formName=="editForm"){
                        // _this.editForm.adevices=[]
                        _this.editForm.vendingUpgradeTaskList=[];
                        _this.adevicesValue.forEach((item, index) => {
                                _this.editForm.vendingUpgradeTaskList.push({
                                 siteId:item,
                                });
                        })
                        let para = Object.assign({}, this.editForm);
                        updateVendingUpgrade(para).then((res) => {
                              if(res.zhead.reTCode=="0000"){
                              NProgress.done();
                              this.$notify({
                                title: '成功',
                                message: res.zhead.retMsg,
                                type: 'success'
                              });
                              _this.editdialogVisible = false;
                              _this.adevicesValue = []
                              _this.editForm={};
                              _this.getData("1")
                              _this.getUpgradeTaskList(1)
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
     _this.adevicesValue = []
      const listQuery = {
        upgradeId:row.upgradeId
      }
      upgradeInfo(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
            _this.editdialogVisible = true
            _this.activ = "set1"
            _this.editForm = response.zbody.datas
            _this.editVersionChange()
            NProgress.done();
            response.zbody.datas.vendingUpgradeTaskList.forEach(function(item,index){
                _this.adevicesValue.push(item.siteId)
            })
         }else{
             this.$notify({
              title: '失败',
              message: response.zhead.retMsg,
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
      this.listLoading = true
      const listQuery = {
        uName:_this.keyword,
        pageSize:_this.pagesize,
        pageNum:page,
        orderByColumn:'createtime',
        isAsc:'desc'//desc
      }
      getVendingUpgradeList(listQuery).then(response => {
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
              message: response.zhead.retMsg,
              type: 'error'
            });
         }
      })
    },
    getVendingListData(page) {
      var _this = this;
      const listQuery = {
        pageSize:_this.pagesize1,
        pageNum:page,
        orderByColumn:'createtime',
        isAsc:'desc'//desc
      }
      getVendingList(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
            NProgress.done();
            response.zbody.datas.rows.forEach((item, index) => {
                _this.data.push({
                      netSate:item.netSate,
                      deviceId: item.siteId,
                      deviceName: item.siteName,
                      lineName:item.lineName,
                      cabinetTypeName:item.cabinetTypeName,
                      corpName:item.corpName,
                      mediaType:item.mediaType,
                    });

                  })
         }else{
             this.$notify({
              title: '失败',
              message: response.zhead.retMsg,
              type: 'error'
            });
         }
      })
    },
    getUpgradeTaskList(page) {
      var _this = this;
      const listQuery = {
        site:_this.r_siteId,
        uName:_this.r_keyword,
        pageSize:_this.pagesize2,
        pageNum:page,
        orderByColumn:'createtime',
        isAsc:'desc'//desc
      }
      upgradeTaskList(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
            NProgress.done();
            _this.total2= response.zbody.datas.total
            _this.DeviceRwData = response.zbody.datas.rows
         }else{
             this.$notify({
              title: '失败',
              message: response.zhead.retMsg,
              type: 'error'
            });
         }
      })
    },
    getUpgradeInfo(row) {
      var _this = this;
      const listQuery = {
        upgradeId:row.upgradeId
      }
      upgradeInfo(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
            _this.dialogVisibleInfo = true
            _this.viewForm =response.zbody.datas
            _this.viewVersionChange()
            NProgress.done();
            
         }else{
             this.$notify({
              title: '失败',
              message: response.zhead.retMsg,
              type: 'error'
            });
         }
      })
      //投放设备信息
      const listQuery1 = {
        upgradeId:row.upgradeId
      }
      upgradeTaskList(listQuery1).then(response => {
         if(response.zhead.reTCode==="0000"){
            _this.total3 = response.zbody.datas.total
            _this.tfData =response.zbody.datas.rows
            NProgress.done();
         }else{
             this.$notify({
              title: '失败',
              message: response.zhead.retMsg,
              type: 'error'
            });
         }
      })


    },
    gettflist(){


    }

    
  },

  mounted () {
    this.getData(1)
    this.getVendingListData()
    this.getUpgradeTaskList(1)
    this.getVendingVersionData();
  },
  components: {
    wTable
  }
}
</script>
<style>
.el-transfer-panel:nth-child(1){width: 520px!important;}
.el-transfer-panel:nth-child(3){width: 520px!important;}
 .el-table .warning-row {
    background: oldlace;
  }

  .el-table .success-row {
    background: #f0f9eb;
  }
</style>
<style scoped>
  .adv_celue_tit{width:100%; height:48px; line-height:48px; text-align:center; color:#666; background: rgb(248, 248, 248); border-bottom:1px solid #ebeef5}
  .adv_celue_tit strong{float:left; margin-left:20px;}
  .adv_celue_tit span{float:right;margin-right:20px;}



  .adv_Title{ font-size:14px; padding:10px 0;}
  .adv_Title span{color:red; display:inline-block; padding-left:10px;}
  .grid-left{ display:flex; justify-content:center; margin-top:20px; }
  .grid-left span{ color:#000 }
  .grid-right{display:flex; justify-content:center; text-align:center; margin-top:20px;}
  .grid-right span{ color:#000 }
  .imgbox{width:50px; height:50px;}
  .imgbox img{width:100%;}
  .dialog-prevNext{text-align:right;}
  .div-warp{padding:0 20px;}
  .pageBox{background: #fff; padding:20px 0;}

</style>