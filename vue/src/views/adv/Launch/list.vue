<template>
 <div class="div-warp">
      <!-- 查看广告 -->
       <el-dialog
          title="查看广告"
          :visible.sync="dialogVisibleInfo"
          width="900px"
          >

          <el-tabs v-model="activeAdv" @tab-click="handleClickAdv">
            <el-tab-pane label="广告信息" name="advInfo">
                <div class="adv_Title">
                  <strong> {{infoData.name}}</strong>
                  <span> {{infoData.curStateName}}</span>
                </div>
                <el-card shadow="always" style="margin-top:10px;" v-for="item in infoData.advStrategies">
                  <div class="adv_celue_tit">
                      <strong>
                      {{item.strategyPointName}}
                      </strong>
                      <span>{{item.strateStr}}</span>
                  </div>
                    <el-table
                      border
                      :data="item.advMstrategies"
                      :header-cell-style="{background:'#f8f8f8'}"
                      style="width: 100%">
                      <el-table-column
                        prop="mediaName"
                        label="素材名称"
                        width="180">
                      </el-table-column>
                      <el-table-column
                        prop="mediaTypeName"
                        label="类型"
                        width="180">
                      </el-table-column>
                      <el-table-column
                        label="文件预览">
                        <template scope="scope">
                          <div class="imgbox"><img :src="httpPrefix+scope.row.mediaUrl" alt=""></div>
                        </template>
                      </el-table-column>
                      <el-table-column
                        prop="playerTimes"
                        label="播放次数">
                      </el-table-column>
                      <el-table-column
                        prop="playerTime"
                        label="播放时间">
                      </el-table-column>
                    </el-table>

                
                </el-card>


            </el-tab-pane>
            <el-tab-pane label="投放设备信息" name="advTInfo">
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
            </el-tab-pane>
            
          </el-tabs>
        </el-dialog>
      <!-- 素材弹出 -->
      <el-dialog
          v-if="dialogVisible_s"
          :visible.sync="dialogVisible_s"
          width="900px"
          >

      
    <div class="search-warp" >
      <el-row :gutter="10">
            <el-col :span="4" v-if="!sonPlatFlag">
                <el-select v-model="mediaSearchObj.mediaType" class="searchInput" size="small" clearable placeholder="请选择素材类型">
                    <el-option
                      v-for="item in mediaTypeList"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                </el-select>
            </el-col>
            <el-col :span="4">
              <el-input placeholder="请输入素材名称" v-model="mediaSearchObj.mediaName" size="small" class="searchInput"  clearable>
              </el-input>
            </el-col>
            <el-col :span="4">
              <el-button type="success" size="small" @click.native.prevent="searchMedia">查询</el-button>
            </el-col>
        </el-row>
    </div>

             <el-table
                :data="advData"
                border
                style="width: 100%">
                <el-table-column
                  label="序号"
                  type="index"
                  width="50">
                </el-table-column>
                <el-table-column
                  prop="mediaName"
                  label="素材名称"
                 >
                </el-table-column>
                <el-table-column
                  prop="mediaTypeName"
                  label="类型"
                  width="80">
                </el-table-column>
                <el-table-column
                  prop="mediaFileSize"
                  label="文件大小"
                  width="100">
                </el-table-column>
                <el-table-column
                  prop="mediaPX"
                  label="文件预览"
                  width="100">
                  <template scope="scope">
                    <div class="imgbox">
                      <img :src="httpPrefix+scope.row.mediaUrl"/>
                    </div>
                  </template>
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
                    <el-button @click="handleAdvClick(scope.row)" size="small" type="primary">选择</el-button>
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
      <el-dialog
          title="新增广告"
          v-if="dialogVisible"
          :visible.sync="dialogVisible"
          width="1300px"
          >
                 <el-form :model="addForm" :rules="Rules" ref="addForm"  label-width="200px">
                    <el-tabs v-model="activ" @tab-click="handleClicka">
                      <el-tab-pane label="基本信息" disabled name="set1">
                        <el-form-item label="广告名称：" prop="name" size="small" :label-width="formLabelWidth">
                            <el-input v-model="addForm.name" :autofocus="true" ref="addName"></el-input>
                            <!-- <input type="text" ref="testFocus" v-focus> -->
                        </el-form-item>
                        <el-form-item label="投放类型：" prop="deliveryType" size="small" :label-width="formLabelWidth">
                            <el-select v-model="addForm.deliveryType" placeholder="请选择">
                              <el-option
                                v-for="item in deliveryTypeList"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                              </el-option>
                            </el-select>  
                        </el-form-item>
                        <el-form-item v-if="addForm.deliveryType=='2'" label="延迟时间：" prop="lazyTime" size="small" :label-width="formLabelWidth">
                          <el-date-picker
                            v-model="addForm.lazyTime"
                            type="datetime"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            placeholder="延迟时间">
                          </el-date-picker>
                        </el-form-item>
                        <el-form-item label="投放平台：" prop="playerPlat" size="small" :label-width="formLabelWidth">
                            <el-select v-model="addForm.playerPlat" placeholder="请选择">
                              <el-option
                                v-for="item in playerPlatList"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                              </el-option>
                            </el-select>  
                        </el-form-item>
                        <el-form-item label="操作类型：" prop="operType" size="small" :label-width="formLabelWidth">
                            <el-select v-model="addForm.operType" placeholder="请选择">
                              <el-option
                                v-for="item in operTypeList"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                              </el-option>
                            </el-select>  
                        </el-form-item>
                        <div class="dialog-prevNext">
                          <!-- <el-button style="margin-top: 12px;" @click="prev">上一步</el-button> -->
                          <el-button style="margin-top: 12px;" @click="next('addForm')">下一步</el-button>
                        </div>
                      </el-tab-pane>
                      <el-tab-pane label="编辑广告内容" disabled name="set2">

                        <div style="margin-bottom: 20px;">
                          <el-button
                            size="small"
                            @click="addTabOfAdd(editableTabsValue2)">
                            新增广告位
                          </el-button>
                        </div>

                        <el-tabs type="border-card" v-model="editableTabsValue" addable @edit="handleAddTabsEdit">

                          <el-tab-pane label="首页主广告位" name="1">
                            <div>
                              <el-button type="primary" @click="addStrategies('0101','add')" size="mini">新增广告策略</el-button>
                            </div>
                            <el-card class="box-card" style="margin-top:20px;" v-for="(item,index) in addForm.strategies">

                              <div slot="header" class="clearfix">
                                <span>首页主广告位策略</span>
                                
                                     <el-button style="float: right; padding: 3px 0" type="text" @click.native.prevent="deleteAddAdvStrategies(index, '0101')"><i class="el-icon-delete"></i></el-button>
                                
                              </div>
                              
                              <div class="text item">
                                <el-form-item label="策略类型" prop="strategyType" size="small" :label-width="formLabelWidth">
                                    <el-select v-model="item.strategyType" @change="changeType(item)" placeholder="请选择策略类型">
                                      <el-option
                                        key="1"
                                        label="每天"
                                        value="1">
                                      </el-option>
                                      <el-option
                                        key="2"
                                        label="特定时间"
                                        value="2">
                                      </el-option>
                                    </el-select>
                                </el-form-item>
                                <el-form-item label="播放时间" prop="playSTime" size="small" :label-width="formLabelWidth">
                                     <div  v-if="item.strategyType==1">
                                      <el-time-picker
                                        v-model="item.playSTime"
                                        value-format="HH:mm:ss"
                                        placeholder="开始时间"
                                        @change="checkEverydayTime(item)">
                                      </el-time-picker>
                                      -
                                      <el-time-picker
                                        v-model="item.playEtime"
                                        value-format="HH:mm:ss"
                                        placeholder="结束时间"
                                        @change="checkEverydayTime(item)">
                                      </el-time-picker>
                                     </div>
                                     <div  v-if="item.strategyType==2">
                                      <el-date-picker
                                        v-model="item.playSTime"
                                        type="datetime"
                                        value-format="yyyy-MM-dd HH:mm:ss"
                                        placeholder="开始时间"
                                        @change="checkSpecialTime(item)">
                                      </el-date-picker>
                                      -
                                      <el-date-picker
                                        v-model="item.playEtime"
                                        type="datetime"
                                        value-format="yyyy-MM-dd HH:mm:ss"
                                        placeholder="结束时间"
                                        @change="checkSpecialTime(item)">
                                      </el-date-picker>
                                     </div>
                                    
                                </el-form-item>
                                <el-form-item label="素材总览" prop="media" size="small" :label-width="formLabelWidth">
                                  <template scope="scope">
                                  <div >
                                    <el-button size="mini" @click="addAdv(item)"  type="primary">新增</el-button>
                                  </div>
                                  <el-table
                                      :data="item.mstrategies"
                                      stripe
                                      style="width: 100%">
                                      <el-table-column
                                        type="index"
                                        width="50">
                                      </el-table-column>
                                      <el-table-column
                                        prop="mediaName"
                                        :show-overflow-tooltip="true"
                                        label="素材名称"
                                        width="150">
                                      </el-table-column>
                                      <el-table-column
                                        prop="mediaTypeName"
                                        label="类型"
                                        :show-overflow-tooltip="true"
                                        width="80">
                                      </el-table-column>
                                      <el-table-column
                                        label="文件预览">
                                        <template scope="scope">
                                        <div class="imgbox">
                                          <img :src="httpPrefix+scope.row.mediaUrl"/>
                                          </div>
                                        </template>
                                      </el-table-column>
                                      <el-table-column
                                        prop="name"
                                        label="播放次数">
                                        <template  scope="scope">
                                          <div>
                                            <el-input-number :min="1" :max="10000" :precision="0" size="mini" clearable v-model="scope.row.playerTimes"></el-input-number>
                                          </div>
                                        </template>
                                      </el-table-column>
                                      <el-table-column
                                        prop="name"
                                        label="播放时间(秒)">
                                        <template  scope="scope">
                                          <div>
                                            <el-input-number :min="1" :max="10000" :precision="0" size="mini" clearable v-model="scope.row.playerTime" :disabled="scope.row.mediaType=='1'"></el-input-number>
                                          </div>
                                        </template>
                                      </el-table-column>
                                      <el-table-column
                                        fixed="right"
                                        label="操作"
                                        width="100">
                                        <template slot-scope="scope">
                                          <el-button type="text" size="small" @click.native.prevent="deleteAdvRow(scope.$index, item.mstrategies)">删除</el-button>
                                        </template>
                                      </el-table-column>
                                  </el-table>
                                  </template>
                                </el-form-item>
                              </div>
                            </el-card>
                          </el-tab-pane>
                          <el-tab-pane
                            :key="item.name"
                            v-for="(item, index) in addForm.advertPlats"
                            :label="item.strategyPointName"
                            :name="item.strategyPoint"
                            closable
                            >
                             <div>
                              <el-button type="primary" @click="addStrategies(item,'add')" size="mini">新增广告策略</el-button>
                            </div>
                            <el-card class="box-card" style="margin-top:20px;" v-for="(item,index) in item.strategies">

                              <div slot="header" class="clearfix">
                                <span>广告位策略</span>
                                
                                     <el-button style="float: right; padding: 3px 0" type="text" @click.native.prevent="deleteAddAdvStrategies(index, item)"><i class="el-icon-delete"></i></el-button>
                                
                              </div>
                              
                              <div class="text item">
                                <el-form-item label="策略类型" prop="strategyType" size="small" :label-width="formLabelWidth">
                                    <el-select v-model="item.strategyType" @change="changeType(item)" placeholder="请选择策略类型">
                                      <el-option
                                        key="1"
                                        label="每天"
                                        value="1">
                                      </el-option>
                                      <el-option
                                        key="2"
                                        label="特定时间"
                                        value="2">
                                      </el-option>
                                    </el-select>
                                </el-form-item>
                                <el-form-item label="播放时间" prop="playSTime" size="small" :label-width="formLabelWidth">
                                     <div  v-if="item.strategyType==1">
                                      <el-time-picker
                                        v-model="item.playSTime"
                                        value-format="HH:mm:ss"
                                        placeholder="开始时间"
                                        @change="checkEverydayTime(item)">
                                      </el-time-picker>
                                      -
                                      <el-time-picker
                                        v-model="item.playEtime"
                                        value-format="HH:mm:ss"
                                        placeholder="结束时间"
                                        @change="checkEverydayTime(item)">
                                      </el-time-picker>
                                     </div>
                                     <div  v-if="item.strategyType==2">
                                      <el-date-picker
                                        v-model="item.playSTime"
                                        type="datetime"
                                        value-format="yyyy-MM-dd HH:mm:ss"
                                        placeholder="开始时间"
                                        @change="checkSpecialTime(item)">
                                      </el-date-picker>
                                      -
                                      <el-date-picker
                                        v-model="item.playEtime"
                                        type="datetime"
                                        value-format="yyyy-MM-dd HH:mm:ss"
                                        placeholder="结束时间"
                                        @change="checkSpecialTime(item)">
                                      </el-date-picker>
                                     </div>
                                    
                                </el-form-item>
                                <el-form-item label="素材总览" prop="media" size="small" :label-width="formLabelWidth">
                                  <template scope="scope">
                                  <div >
                                    <el-button size="mini" @click="addAdv(item,'2')"  type="primary">新增</el-button>
                                  </div>
                                  <el-table
                                      :data="item.mstrategies"
                                      stripe
                                      style="width: 100%">
                                      <el-table-column
                                        type="index"
                                        width="50">
                                      </el-table-column>
                                      <el-table-column
                                        prop="mediaName"
                                        :show-overflow-tooltip="true"
                                        label="素材名称"
                                        width="150">
                                      </el-table-column>
                                      <el-table-column
                                        prop="mediaTypeName"
                                        label="类型"
                                        :show-overflow-tooltip="true"
                                        width="80">
                                      </el-table-column>
                                      <el-table-column
                                        label="文件预览">
                                        <template scope="scope">
                                        <div class="imgbox">
                                          <img :src="httpPrefix+scope.row.mediaUrl"/>
                                          </div>
                                        </template>
                                      </el-table-column>
                                      <el-table-column
                                        prop="name"
                                        label="播放次数">
                                        <template  scope="scope">
                                          <div>
                                            <el-input-number :min="1" :max="10000" :precision="0" size="mini" clearable v-model="scope.row.playerTimes"></el-input-number>
                                          </div>
                                        </template>
                                      </el-table-column>
                                      <el-table-column
                                        prop="name"
                                        label="播放时间(秒)">
                                        <template  scope="scope">
                                          <div>
                                            <el-input-number :min="1" :max="10000" :precision="0" size="mini" clearable v-model="scope.row.playerTime" :disabled="scope.row.mediaType=='1'"></el-input-number>
                                          </div>
                                        </template>
                                      </el-table-column>
                                      <el-table-column
                                        fixed="right"
                                        label="操作"
                                        width="100">
                                        <template slot-scope="scope">
                                          <el-button type="text" size="small" @click.native.prevent="deleteAdvRow(scope.$index, item.mstrategies)">删除</el-button>
                                        </template>
                                      </el-table-column>
                                  </el-table>
                                  </template>
                                </el-form-item>
                              </div>
                            </el-card>
                          </el-tab-pane>
                        </el-tabs>
                        <div class="dialog-prevNext">
                          <el-button style="margin-top: 12px;" @click="prev">上一步</el-button>
                          <el-button style="margin-top: 12px;" @click="next('addForm')">下一步</el-button>
                        </div>
                      </el-tab-pane>
                      <el-tab-pane label="选择投放设备" disabled name="set3">
                          <!-- 选择投放设备 -->
                              <el-transfer
                                style="text-align: left; display: inline-block"
                                v-model="adevicesValue"
                                filterable
                                filter-placeholder="请输入售货机名称搜索"
                                :left-default-checked="[2, 3]"
                                :right-default-checked="[1]"
                                :render-content="renderFunc"
                                :titles="['售卖机列表', '下发的广告售卖机']"
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
                          <!-- <el-button style="margin-top: 12px;" @click="addSubmitForm('addForm','1')" >暂存</el-button> -->
                          <el-button style="margin-top: 12px;" @click="addSubmitForm('addForm','2')" >投放</el-button>
                        </div>
                      </el-tab-pane>
                    </el-tabs>
                  </el-form>
      </el-dialog>
      <!-- 编辑弹框 -->
      <el-dialog
          title="编辑广告"
          v-if="editdialogVisible"
          :visible.sync="editdialogVisible"
          width="1300px"
          >
                 <el-form :model="editForm" :rules="Rules" ref="editForm"  label-width="200px">
                    <el-tabs v-model="activ" @tab-click="handleClicka">
                      <el-tab-pane label="基本信息" disabled name="set1">
                        <el-form-item label="广告名称：" prop="name" size="small" :label-width="formLabelWidth">
                            <el-input v-model="editForm.name" :autofocus="true" ref="editName"></el-input>
                        </el-form-item>
                        <el-form-item label="投放类型：" prop="deliveryType" size="small" :label-width="formLabelWidth">
                            <el-select v-model="editForm.deliveryType" placeholder="请选择">
                              <el-option
                                v-for="item in deliveryTypeList"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                              </el-option>
                            </el-select>  
                        </el-form-item>
                        <el-form-item v-if="editForm.deliveryType=='2'" label="延迟时间：" prop="lazyTime" size="small" :label-width="formLabelWidth">
                          <el-date-picker
                            v-model="editForm.lazyTime"
                            type="datetime"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            placeholder="延迟时间">
                          </el-date-picker>
                        </el-form-item>
                        <el-form-item label="投放平台：" prop="playerPlat" size="small" :label-width="formLabelWidth">
                            <el-select v-model="editForm.playerPlat" placeholder="请选择">
                              <el-option
                                v-for="item in playerPlatList"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                              </el-option>
                            </el-select>  
                        </el-form-item>
                        <el-form-item label="操作类型：" prop="operType" size="small" :label-width="formLabelWidth">
                            <el-select v-model="editForm.operType" placeholder="请选择">
                              <el-option
                                v-for="item in operTypeList"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                              </el-option>
                            </el-select>  
                        </el-form-item>
                        <div class="dialog-prevNext">
                          <!-- <el-button style="margin-top: 12px;" @click="prev">上一步</el-button> -->
                          <el-button style="margin-top: 12px;" @click="next('editForm')">下一步</el-button>
                        </div>
                      </el-tab-pane>
                      <el-tab-pane label="编辑广告内容" disabled name="set2">

                        <div style="margin-bottom: 20px;">
                          <el-button
                            size="small"
                            @click="addTabOfEdit(editableTabsValue2)">
                            新增广告位
                          </el-button>
                        </div>

                        <el-tabs type="border-card" v-model="editableTabsValue2" addable @edit="handleEditTabsEdit">
                          <el-tab-pane label="首页主广告位" name="1">
                            <div>
                              <el-button type="primary" @click="addStrategies('0101','edit')" size="mini">新增广告策略</el-button>
                            </div>
                            <el-card class="box-card" style="margin-top:20px;" v-for="(item,index) in editForm.strategies" v-if="item.strategyPoint=='0101'">

                              <div slot="header" class="clearfix">
                                <span>首页主广告位策略</span>
                                
                                     <el-button style="float: right; padding: 3px 0" type="text" @click.native.prevent="deleteEditAdvStrategies(index, '0101')"><i class="el-icon-delete"></i></el-button>
                                
                              </div>
                              
                              <div  class="text item">
                                <el-form-item label="策略类型" prop="strategyType" size="small" :label-width="formLabelWidth">
                                    <el-select v-model="item.strategyType" 
                                        @change="changeType(item)" 

                                    placeholder="请选择策略类型">
                                      <el-option
                                        key="1"
                                        label="每天"
                                        value="1">
                                      </el-option>
                                      <el-option
                                        key="2"
                                        label="特定时间"
                                        value="2">
                                      </el-option>
                                    </el-select>
                                </el-form-item>
                                <el-form-item label="播放时间" prop="playSTime" size="small" :label-width="formLabelWidth">
                                     <div  v-if="item.strategyType==1">
                                      <el-time-picker
                                        v-model="item.playSTime"
                                        value-format="HH:mm:ss"
                                        placeholder="开始时间"
                                        @change="checkEverydayTime(item)">
                                      </el-time-picker>
                                      -
                                      <el-time-picker
                                        v-model="item.playEtime"
                                        value-format="HH:mm:ss"
                                        placeholder="结束时间"
                                        @change="checkEverydayTime(item)">
                                      </el-time-picker>
                                     </div>
                                     <div  v-if="item.strategyType==2">
                                      <el-date-picker
                                        v-model="item.playSTime"
                                        type="datetime"
                                        value-format="yyyy-MM-dd HH:mm:ss"
                                        placeholder="开始时间"
                                        @change="checkSpecialTime(item)">
                                      </el-date-picker>
                                      -
                                      <el-date-picker
                                        v-model="item.playEtime"
                                        type="datetime"
                                        value-format="yyyy-MM-dd HH:mm:ss"
                                        placeholder="结束时间"
                                        @change="checkSpecialTime(item)">
                                      </el-date-picker>
                                     </div>
                                </el-form-item>
                                <el-form-item label="素材总览" prop="media" size="small" :label-width="formLabelWidth">
                                  <template scope="scope">
                                  <div >
                                    <el-button size="mini" @click="addAdv(item)"  type="primary">新增</el-button>
                                  </div>
                                  <el-table
                                      :data="item.mstrategies"
                                      stripe
                                      style="width: 100%">
                                      <el-table-column
                                        prop="mediaName"
                                        :show-overflow-tooltip="true"
                                        label="素材名称"
                                        width="150">
                                      </el-table-column>
                                      <el-table-column
                                        prop="mediaTypeName"
                                        label="类型"
                                        :show-overflow-tooltip="true"
                                        width="80">
                                      </el-table-column>
                                      <el-table-column                                      
                                        label="文件预览">
                                         <template scope="scope">
                                        <div class="imgbox">
                                          <img :src="httpPrefix+scope.row.mediaUrl"/>
                                          </div>
                                        </template>
                                      </el-table-column>
                                      <el-table-column
                                        prop="playerTimes"
                                        label="播放次数">
                                        <template  scope="scope">
                                          <div>
                                            <el-input-number :min="1" :max="10000" :precision="0" size="mini" clearable v-model="scope.row.playerTimes"></el-input-number>
                                          </div>
                                        </template>
                                      </el-table-column>
                                      <el-table-column
                                        prop="playerTime"
                                        label="播放时间(秒)">
                                        <template  scope="scope">
                                          <div>
                                            <el-input-number :min="1" :max="10000" :precision="0" size="mini" clearable v-model="scope.row.playerTime" :disabled="scope.row.mediaType=='1'"></el-input-number>
                                          </div>
                                        </template>
                                      </el-table-column>
                                      <el-table-column
                                        fixed="right"
                                        label="操作"
                                        width="100">
                                        <template slot-scope="scope">
                                          <el-button type="text" size="small" @click.native.prevent="deleteAdvRow(scope.$index, item.mstrategies)">删除</el-button>
                                        </template>
                                      </el-table-column>
                                  </el-table>
                                  </template>
                                </el-form-item>
                              </div>
                            </el-card>
                          </el-tab-pane>

                          <el-tab-pane
                            :key="item.name"
                            v-for="(item, index) in editForm.advertPlats"
                            :label="item.strategyPointName"
                            :name="item.strategyPoint"
                            closable
                            >
                             <div>
                              <el-button type="primary" @click="addStrategies(item,'edit')" size="mini">新增广告策略</el-button>
                            </div>
                            <el-card class="box-card" style="margin-top:20px;" v-for="(item,index) in item.strategies">

                              <div slot="header" class="clearfix">
                                <span>广告位策略</span>
                                
                                     <el-button style="float: right; padding: 3px 0" type="text" @click.native.prevent="deleteEditAdvStrategies(index, item)"><i class="el-icon-delete"></i></el-button>
                                
                              </div>
                              
                              <div class="text item">
                                <el-form-item label="策略类型" prop="strategyType" size="small" :label-width="formLabelWidth">
                                    <el-select v-model="item.strategyType" @change="changeType(item)" placeholder="请选择策略类型">
                                      <el-option
                                        key="1"
                                        label="每天"
                                        value="1">
                                      </el-option>
                                      <el-option
                                        key="2"
                                        label="特定时间"
                                        value="2">
                                      </el-option>
                                    </el-select>
                                </el-form-item>
                                <el-form-item label="播放时间" prop="playSTime" size="small" :label-width="formLabelWidth">
                                     <div  v-if="item.strategyType==1">
                                      <el-time-picker
                                        v-model="item.playSTime"
                                        value-format="HH:mm:ss"
                                        placeholder="开始时间"
                                        @change="checkEverydayTime(item)">
                                      </el-time-picker>
                                      -
                                      <el-time-picker
                                        v-model="item.playEtime"
                                        value-format="HH:mm:ss"
                                        placeholder="结束时间"
                                        @change="checkEverydayTime(item)">
                                      </el-time-picker>
                                     </div>
                                     <div  v-if="item.strategyType==2">
                                      <el-date-picker
                                        v-model="item.playSTime"
                                        type="datetime"
                                        value-format="yyyy-MM-dd HH:mm:ss"
                                        placeholder="开始时间"
                                        @change="checkSpecialTime(item)">
                                      </el-date-picker>
                                      -
                                      <el-date-picker
                                        v-model="item.playEtime"
                                        type="datetime"
                                        value-format="yyyy-MM-dd HH:mm:ss"
                                        placeholder="结束时间"
                                        @change="checkSpecialTime(item)">
                                      </el-date-picker>
                                     </div>
                                    
                                </el-form-item>
                                <el-form-item label="素材总览" prop="media" size="small" :label-width="formLabelWidth">
                                  <template scope="scope">
                                  <div >
                                    <el-button size="mini" @click="addAdv(item,'2')"  type="primary">新增</el-button>
                                  </div>
                                  <el-table
                                      :data="item.mstrategies"
                                      stripe
                                      style="width: 100%">
                                      <el-table-column
                                        type="index"
                                        width="50">
                                      </el-table-column>
                                      <el-table-column
                                        prop="mediaName"
                                        :show-overflow-tooltip="true"
                                        label="素材名称"
                                        width="150">
                                      </el-table-column>
                                      <el-table-column
                                        prop="mediaTypeName"
                                        label="类型"
                                        :show-overflow-tooltip="true"
                                        width="80">
                                      </el-table-column>
                                      <el-table-column
                                        label="文件预览">
                                        <template scope="scope">
                                        <div class="imgbox">
                                          <img :src="httpPrefix+scope.row.mediaUrl"/>
                                          </div>
                                        </template>
                                      </el-table-column>
                                      <el-table-column
                                        prop="playerTimes"
                                        label="播放次数">
                                        <template  scope="scope">
                                          <div>
                                            <el-input-number :min="1" :max="10000" :precision="0" size="mini" clearable v-model="scope.row.playerTimes"></el-input-number>
                                          </div>
                                        </template>
                                      </el-table-column>
                                      <el-table-column
                                        prop="playerTime"
                                        label="播放时间(秒)">
                                        <template  scope="scope">
                                          <div>
                                            <el-input-number :min="1" :max="10000" :precision="0" size="mini" clearable v-model="scope.row.playerTime" :disabled="scope.row.mediaType=='1'"></el-input-number>
                                          </div>
                                        </template>
                                      </el-table-column>
                                      <el-table-column
                                        fixed="right"
                                        label="操作"
                                        width="100">
                                        <template slot-scope="scope">
                                          <el-button type="text" size="small" @click.native.prevent="deleteAdvRow(scope.$index, item.mstrategies)">删除</el-button>
                                        </template>
                                      </el-table-column>
                                  </el-table>
                                  </template>
                                </el-form-item>
                              </div>
                            </el-card>
                          </el-tab-pane>
                          
                        </el-tabs>
                        <div class="dialog-prevNext">
                          <el-button style="margin-top: 12px;" @click="prev">上一步</el-button>
                          <el-button style="margin-top: 12px;" @click="next('editForm')">下一步</el-button>
                        </div>
                      </el-tab-pane>
                      <el-tab-pane label="选择投放设备" disabled name="set3">

                          <!-- 选择投放设备 -->
                              <el-transfer
                                style="text-align: left; display: inline-block"
                                v-model="adevicesValue"
                                filterable
                                filter-placeholder="请输入售货机名称搜索"
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
                                  key: 'deviceId',
                                  label: 'deviceName'
                                }"
                                @change="handleChange"
                                :data="data">
                              </el-transfer>
                          <!-- 选择投放设备 end -->



                        <div class="dialog-prevNext">
                          <el-button style="margin-top: 12px;" @click="prev">上一步</el-button>
                          <!-- <el-button style="margin-top: 12px;" @click="addSubmitForm('editForm','1')" >暂存</el-button> -->
                          <el-button style="margin-top: 12px;" @click="addSubmitForm('editForm','2')" >投放</el-button>
                        </div>
                      </el-tab-pane>
                    </el-tabs>
                  </el-form>
                  
      </el-dialog>

      <!-- 编辑弹框 -->
      <el-dialog
          title="查看广告"
          v-if="viewVisible"
          :visible.sync="viewVisible"
          width="1300px"
          >
                 <el-form :model="editForm" :rules="Rules" ref="editForm" label-width="200px" >
                    <el-tabs v-model="activ" @tab-click="handleClicka">
                      <el-tab-pane label="基本信息" disabled name="set1">
                        <el-form-item label="广告名称：" prop="name" size="small" :label-width="formLabelWidth" >
                            <el-input v-model="editForm.name" :autofocus="true" ref="editFocus" :disabled="true"></el-input>
                        </el-form-item>
                        <el-form-item label="投放类型：" prop="deliveryType" size="small" :label-width="formLabelWidth">
                            <el-select v-model="editForm.deliveryType" placeholder="请选择" :disabled="true">
                              <el-option
                                v-for="item in deliveryTypeList"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                              </el-option>
                            </el-select>  
                        </el-form-item>
                        <el-form-item v-if="editForm.deliveryType=='2'" label="延迟时间：" prop="lazyTime" size="small" :label-width="formLabelWidth">
                          <el-date-picker
                            v-model="editForm.lazyTime"
                            type="datetime"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            placeholder="延迟时间"
                             :disabled="true">
                          </el-date-picker>
                        </el-form-item>
                        <el-form-item label="投放平台：" prop="playerPlat" size="small" :label-width="formLabelWidth">
                            <el-select v-model="editForm.playerPlat" placeholder="请选择" :disabled="true">
                              <el-option
                                v-for="item in playerPlatList"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                              </el-option>
                            </el-select>  
                        </el-form-item>
                        <el-form-item label="操作类型：" prop="operType" size="small" :label-width="formLabelWidth">
                            <el-select v-model="editForm.operType" placeholder="请选择" :disabled="true">
                              <el-option
                                v-for="item in operTypeList"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                              </el-option>
                            </el-select>  
                        </el-form-item>
                        <div class="dialog-prevNext">
                          <!-- <el-button style="margin-top: 12px;" @click="prev">上一步</el-button> -->
                          <el-button style="margin-top: 12px;" @click="next('editForm')" name="ck">下一步</el-button>
                        </div>
                      </el-tab-pane>
                      <el-tab-pane label="编辑广告内容" disabled name="set2">

                        <!-- <div style="margin-bottom: 20px;">
                          <el-button
                            size="small"
                            @click="addTabOfEdit(editableTabsValue2)" 
                            :disabled="true">
                            新增广告位
                          </el-button>
                        </div> -->

                        <el-tabs type="border-card" v-model="editableTabsValue2" @edit="handleEditTabsEdit">
                          <el-tab-pane label="首页主广告位" name="1">
                            <!-- <div>
                              <el-button type="primary" @click="addStrategies('0101','edit')" size="mini" :disabled="true">新增广告策略</el-button>
                            </div> -->
                            <el-card class="box-card" style="margin-top:20px;" v-for="(item,index) in editForm.strategies" v-if="item.strategyPoint=='0101'">

                              <div slot="header" class="clearfix">
                                <span>首页主广告位策略</span>
                                
                                     <!-- <el-button style="float: right; padding: 3px 0" type="text" @click.native.prevent="deleteEditAdvStrategies(index, '0101')"><i class="el-icon-delete"></i></el-button> -->
                                
                              </div>
                              
                              <div  class="text item">
                                <el-form-item label="策略类型" prop="strategyType" size="small" :label-width="formLabelWidth">
                                    <el-select v-model="item.strategyType" 
                                        @change="changeType(item)" 
                                         :disabled="true"
                                    placeholder="请选择策略类型">
                                      <el-option
                                        key="1"
                                        label="每天"
                                        value="1">
                                      </el-option>
                                      <el-option
                                        key="2"
                                        label="特定时间"
                                        value="2">
                                      </el-option>
                                    </el-select>
                                </el-form-item>
                                <el-form-item label="播放时间" prop="playSTime" size="small" :label-width="formLabelWidth">
                                     <div  v-if="item.strategyType==1">
                                      <el-time-picker
                                        v-model="item.playSTime"
                                        value-format="HH:mm:ss"
                                        placeholder="开始时间"
                                         :disabled="true"
                                        @change="checkEverydayTime(item)">
                                      </el-time-picker>
                                      -
                                      <el-time-picker
                                        v-model="item.playEtime"
                                        value-format="HH:mm:ss"
                                        placeholder="结束时间"
                                         :disabled="true"
                                        @change="checkEverydayTime(item)">
                                      </el-time-picker>
                                     </div>
                                     <div  v-if="item.strategyType==2">
                                      <el-date-picker
                                        v-model="item.playSTime"
                                        type="datetime"
                                        value-format="yyyy-MM-dd HH:mm:ss"
                                        placeholder="开始时间"
                                         :disabled="true"
                                        @change="checkSpecialTime(item)">
                                      </el-date-picker>
                                      -
                                      <el-date-picker
                                        v-model="item.playEtime"
                                        type="datetime"
                                        value-format="yyyy-MM-dd HH:mm:ss"
                                        placeholder="结束时间"
                                         :disabled="true"
                                        @change="checkSpecialTime(item)">
                                      </el-date-picker>
                                     </div>
                                </el-form-item>
                                <el-form-item label="素材总览" prop="media" size="small" :label-width="formLabelWidth">
                                  <template scope="scope">
                                  <!-- <div >
                                    <el-button size="mini" @click="addAdv(item)"  type="primary">新增</el-button>
                                  </div> -->
                                  <el-table
                                      :data="item.mstrategies"
                                      stripe
                                      style="width: 100%">
                                      <el-table-column
                                        prop="mediaName"
                                        :show-overflow-tooltip="true"
                                        label="素材名称"
                                        width="150">
                                      </el-table-column>
                                      <el-table-column
                                        prop="mediaTypeName"
                                        label="类型"
                                        :show-overflow-tooltip="true"
                                        width="80">
                                      </el-table-column>
                                      <el-table-column                                      
                                        label="文件预览">
                                         <template scope="scope">
                                        <div class="imgbox">
                                          <img :src="httpPrefix+scope.row.mediaUrl"/>
                                          </div>
                                        </template>
                                      </el-table-column>
                                      <el-table-column
                                        prop="playerTimes"
                                        label="播放次数">
                                        <template  scope="scope">
                                          <div>
                                            <el-input-number :min="1" :max="10000" :precision="0" size="mini" clearable v-model="scope.row.playerTimes"  :disabled="true"></el-input-number>
                                          </div>
                                        </template>
                                      </el-table-column>
                                      <el-table-column
                                        prop="playerTime"
                                        label="播放时间(秒)">
                                        <template  scope="scope">
                                          <div>
                                            <el-input-number :min="1" :max="10000" :precision="0" size="mini" clearable v-model="scope.row.playerTime"  :disabled="true"></el-input-number>
                                          </div>
                                        </template>
                                      </el-table-column>
                                      <!-- <el-table-column
                                        fixed="right"
                                        label="操作"
                                        width="100">
                                        <template slot-scope="scope">
                                          <el-button type="text" size="small" @click.native.prevent="deleteAdvRow(scope.$index, item.mstrategies)">删除</el-button>
                                        </template>
                                      </el-table-column> -->
                                  </el-table>
                                  </template>
                                </el-form-item>
                              </div>
                            </el-card>
                          </el-tab-pane>

                          <el-tab-pane
                            :key="item.name"
                            v-for="(item, index) in editForm.advertPlats"
                            :label="item.strategyPointName"
                            :name="item.strategyPoint"
                            >
                             <!-- <div>
                              <el-button type="primary" @click="addStrategies(item,'edit')" size="mini">新增广告策略</el-button>
                            </div> -->
                            <el-card class="box-card" style="margin-top:20px;" v-for="(item,index) in item.strategies">

                              <div slot="header" class="clearfix">
                                <span>广告位策略</span>
                                
                                     <!-- <el-button style="float: right; padding: 3px 0" type="text" @click.native.prevent="deleteEditAdvStrategies(index, item)"><i class="el-icon-delete"></i></el-button> -->
                                
                              </div>
                              
                              <div class="text item">
                                <el-form-item label="策略类型" prop="strategyType" size="small" :label-width="formLabelWidth">
                                    <el-select v-model="item.strategyType" @change="changeType(item)" placeholder="请选择策略类型" :disabled="true">
                                      <el-option
                                        key="1"
                                        label="每天"
                                        value="1">
                                      </el-option>
                                      <el-option
                                        key="2"
                                        label="特定时间"
                                        value="2">
                                      </el-option>
                                    </el-select>
                                </el-form-item>
                                <el-form-item label="播放时间" prop="playSTime" size="small" :label-width="formLabelWidth">
                                     <div  v-if="item.strategyType==1">
                                      <el-time-picker
                                        v-model="item.playSTime"
                                        value-format="HH:mm:ss"
                                        placeholder="开始时间"
                                         :disabled="true"
                                        @change="checkEverydayTime(item)">
                                      </el-time-picker>
                                      -
                                      <el-time-picker
                                        v-model="item.playEtime"
                                        value-format="HH:mm:ss"
                                        placeholder="结束时间"
                                         :disabled="true"
                                        @change="checkEverydayTime(item)">
                                      </el-time-picker>
                                     </div>
                                     <div  v-if="item.strategyType==2">
                                      <el-date-picker
                                        v-model="item.playSTime"
                                        type="datetime"
                                        value-format="yyyy-MM-dd HH:mm:ss"
                                        placeholder="开始时间"
                                         :disabled="true"
                                        @change="checkSpecialTime(item)">
                                      </el-date-picker>
                                      -
                                      <el-date-picker
                                        v-model="item.playEtime"
                                        type="datetime"
                                        value-format="yyyy-MM-dd HH:mm:ss"
                                        placeholder="结束时间"
                                         :disabled="true"
                                        @change="checkSpecialTime(item)">
                                      </el-date-picker>
                                     </div>
                                    
                                </el-form-item>
                                <el-form-item label="素材总览" prop="media" size="small" :label-width="formLabelWidth">
                                  <template scope="scope">
                                  <!-- <div >
                                    <el-button size="mini" @click="addAdv(item)"  type="primary">新增</el-button>
                                  </div> -->
                                  <el-table
                                      :data="item.mstrategies"
                                      stripe
                                      style="width: 100%">
                                      <el-table-column
                                        type="index"
                                        width="50">
                                      </el-table-column>
                                      <el-table-column
                                        prop="mediaName"
                                        :show-overflow-tooltip="true"
                                        label="素材名称"
                                        width="150">
                                      </el-table-column>
                                      <el-table-column
                                        prop="mediaTypeName"
                                        label="类型"
                                        :show-overflow-tooltip="true"
                                        width="80">
                                      </el-table-column>
                                      <el-table-column
                                        label="文件预览">
                                        <template scope="scope">
                                        <div class="imgbox">
                                          <img :src="httpPrefix+scope.row.mediaUrl"/>
                                          </div>
                                        </template>
                                      </el-table-column>
                                      <el-table-column
                                        prop="playerTimes"
                                        label="播放次数">
                                        <template  scope="scope">
                                          <div>
                                            <el-input-number :min="1" :max="10000" :precision="0" size="mini" clearable v-model="scope.row.playerTimes" :disabled="true"></el-input-number>
                                          </div>
                                        </template>
                                      </el-table-column>
                                      <el-table-column
                                        prop="playerTime"
                                        label="播放时间(秒)">
                                        <template  scope="scope">
                                          <div>
                                            <el-input-number :min="1" :max="10000" :precision="0" size="mini" clearable v-model="scope.row.playerTime" :disabled="true"></el-input-number>
                                          </div>
                                        </template>
                                      </el-table-column>
                                      <!-- <el-table-column
                                        fixed="right"
                                        label="操作"
                                        width="100">
                                        <template slot-scope="scope">
                                          <el-button type="text" size="small" @click.native.prevent="deleteAdvRow(scope.$index, item.mstrategies)">删除</el-button>
                                        </template>
                                      </el-table-column> -->
                                  </el-table>
                                  </template>
                                </el-form-item>
                              </div>
                            </el-card>
                          </el-tab-pane>
                          
                        </el-tabs>
                        <div class="dialog-prevNext">
                          <el-button style="margin-top: 12px;" @click="prev">上一步</el-button>
                          <el-button style="margin-top: 12px;" @click="next('editForm')" >下一步</el-button>
                        </div>
                      </el-tab-pane>
                      <el-tab-pane label="选择投放设备" disabled name="set3">

                          <!-- 选择投放设备 -->
                              <el-transfer
                                style="text-align: left; display: inline-block"
                                v-model="adevicesValue"
                                filterable
                                filter-placeholder="请输入售货机名称搜索"
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
                                  key: 'deviceId',
                                  label: 'deviceName'
                                }"
                                @change="handleChange"
                                :data="data">
                              </el-transfer>
                          <!-- 选择投放设备 end -->

                        <div class="dialog-prevNext">
                          <el-button style="margin-top: 12px;" @click="prev">上一步</el-button>
                          <!-- <el-button style="margin-top: 12px;" @click="addSubmitForm('editForm','1')" >暂存</el-button> -->
                          <!-- <el-button style="margin-top: 12px;" @click="addSubmitForm('editForm','2')" >投放</el-button> -->
                        </div>
                      </el-tab-pane>
                    </el-tabs>
                  </el-form>
                  
      </el-dialog>



      <!-- 编辑弹框结束 -->
      <div class="block-warp" style="margin-top:20px">
        
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="广告管理" name="first">
          <div class="search-warp">
              <el-row :gutter="20" >
                  <el-col :span="4">
                    <div class="grid-content bg-purple">
                        <el-input
                        placeholder="请输广告名称"
                        size="small"
                        v-model="keyword"
                        clearable>
                      </el-input>

                    </div>
                  </el-col>
                  <el-col :span="16">
                      <div class="grid-content bg-purple"> 
                        <el-button type="success" size="small" @click.native.prevent="search" v-permission="['sys:advert:device:select']">查询</el-button>
                        <el-button type="primary" size="small" @click="handleAdd" v-permission="['sys:advert:device:add']">新增</el-button>
                        <el-button type="danger" size="small" @click="deleteRow" v-permission="['sys:advert:device:del']">删除</el-button>
                      </div>
                  </el-col>
                  <el-col :span="4"><div class="grid-content bg-purple"></div></el-col>
              </el-row>
          </div>
           <wTable :data="tableData" :header="tableHeader" :handleSelectionChange="handleSelectionChange" :option="tableOption">
              <el-table-column slot="fixed"
                fixed="right"
                label="操作"
                width="350">
                  <template  scope="scope">
                    <div style="text-align:center;">
                      <!-- <el-button type="success" size="mini" icon="el-icon-view" circle @click="getAdvInfo(scope.row)" v-permission="['sys:advert:device:view']"></el-button> -->
                      <el-button type="primary" size="mini" @click="handleEdit(scope.$index, scope.row,'1')" v-permission="['sys:advert:device:view']">查看</el-button>
                      <el-button type="primary" size="mini" @click="handleEdit(scope.$index, scope.row,'2')" v-permission="['sys:advert:device:edit']" :disabled="scope.row.curState!='1'">编辑</el-button>
                      <el-button type="primary" size="mini" @click="handleInvalid(scope.row)" v-permission="['sys:advert:device:edit']">失效</el-button>
                      <el-button type="danger"  size="mini" @click.native.prevent="deleteRow(scope.row)" v-permission="['sys:advert:device:del']">删除</el-button>
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
        <el-tab-pane label="广告任务列表" name="second">
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
                          v-model="r_deviceId"
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
                    prop="advName"
                    label="广告名称"
                    >
                  </el-table-column>
                  <el-table-column
                    prop="deviceId"
                    label="售货机编号"
                    >
                  </el-table-column>
                  <el-table-column
                    prop="siteName"
                    label="售货机名称">
                  </el-table-column>
                  <el-table-column
                    prop="curStateName"
                    label="状态">
                  </el-table-column>
                  <!-- <el-table-column
                    prop="createTime"
                    label="创建时间">
                  </el-table-column> -->
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
import {advertConfigList,advertMaterialList,addAdvertConfig,editAdvertConfig,selectAdvertDeviceRwList,advInfo,tflist,removeAdvertConfig,getEditAdv,invalidAdvertConfig} from '@/api/adv'
import {getVendingList} from '@/api/dictionaries'
import NProgress from 'nprogress'
import wTable from '@/components/table/w-table.vue'
import permission from '@/directive/permission/index.js'
export default {
  directives: { permission,
    focus: {
      inserted: function (el, {value}) {
          if (value) {
              el.focus();
          }
      }
    }
  },
  data() {
        return {
          num1:1,
          addFocus:'',
          sonPlatFlag:false,  //是否是子广告位
          editableTabsValue: '1',
          editableTabsValue2:'1',
          editableTabs: [],
          editableTabs2: [],
          tabIndex: 1,
          tabIndex2: 1,
          data:[],
          httpPrefix:'http://',
          adevicesValue: [],
          renderFunc(h, option) {
            // return <span>{option.netSate}-{ option.deviceId } - { option.deviceName }-{option.lineName}-{option.cabinetTypeName}-{option.mediaType}</span>;
            return <span>{ option.deviceId } - { option.deviceName }</span>;
          },
          mediaSearchObj:{
            mediaType:'',
            mediaName:''
          },
          DeviceRwData:[],
          addForm:{
            name:"",
            deliveryType:null,
            lazyTime:"",
            operType:null,
            playerPlat:null,
            curState:"",
            adevices:[],
            strategies:[],
            advertPlats:[]
          },
          editForm:{
            name:"",
            deliveryType:null,
            lazyTime:"",
            operType:null,
            playerPlat:null,
            curState:"",
            adevices:[],
            strategies:[],
            advertPlats:[]
          },
          activeAdv:"advInfo",
          deliveryTypeList: [
            {
              value: '1',
              label: '立即投放'
            }, {
              value: '2',
              label: '延迟投放'
            }
          ],
          playerPlatList:[
            // {
            //   value: '01',
            //   label: '柜子设备端'
            // }, {
            //   value: '02',
            //   label: '微信公众号'
            // }, {
            //   value: '03',
            //   label: '售卖app'
            // }
                        {
              value: '01',
              label: '售货机终端'
            }, {
              value: '02',
              label: '公众号购买程序'
            }
          ],
          operTypeList:[
            {
              value: '1',
              label: '全量'
            }, {
              value: '2',
              label: '增量'
            }
          ],
          mediaTypeList:[
            {
              value: '1',
              label: '视频'
            }, {
              value: '2',
              label: '图片'
            }
            // , {
            //   value: '3',
            //   label: '文本'
            // }
          ],
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
          viewVisible:false,
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
                label: '广告名称 ',
                sortable: true,
                sortMethod: this.handleNameSort
              },
              {
                prop: 'operTypeName',
                label: '操作类型',
                 minWidth: '80'
             
              },
              {
                prop: 'curStateName',
                label: '状态',
                minWidth: '120'
              },
              {
                prop: 'lazyTime',
                label: '延迟投放日期'
              },
              // {
              //   prop: 'createTime',
              //   label: '投放日期'
              // },
              {
                prop: 'createTime',
                label: '创建时间',
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
          seqId:0,
          Rules:{
              name:[
                { required: true, message: '广告名称不能为空', trigger: 'blur' },
                { min: 0,max: 50, message: '广告名称最多50个字符', trigger: 'blur' }
              ],
              deliveryType: [
                 {  required: true, message:'投放类型不能为空',  trigger: 'blur' }
              ],
              playerPlat: [
                 {  required: true, message:'投放平台不能为空',  trigger: 'blur' }
              ],
              operType: [
                 {  required: true, message:'操作类型不能为空',  trigger: 'blur' }
              ]
          }
          //     strategyType: [
          //        {  required: true, message:'策略类型不能为空',  trigger: 'change' }
          //     ],
          //     playSTime: [
          //        {  required: true, message:'开始时间不能为空',  trigger: 'change' }
          //     ],
          //     playEtime: [
          //        {  required: true, message:'结束时间不能为空',  trigger: 'change' }
          //     ],
          //     media: [
          //        {  required: true, message:'素材不能为空',  trigger: 'change' }
          //     ]
          // }

        }
  },
  watch: {
    filterText(val) {
      this.$refs.tree2.filter(val)
    }
  },
  methods: {
    checkStrEmpty(str){
      if(str==undefined||str==null||str==''){
        return true;
      }else{
        return false;
      }
    },
    checkEverydayTime(item){
      if(!this.checkStrEmpty(item.playEtime)&&!this.checkStrEmpty(item.playSTime)){
        if(item.playEtime<=item.playSTime){
          this.$notify({
            title: '警告',
            message: "结束时间必须晚于开始时间",
            type: 'error'
          });
        }
      }
    },
    checkSpecialTime(item){
      if(!this.checkStrEmpty(item.playSTime)&&new Date(item.playSTime)<new Date()){
          this.$notify({
            title: '警告',
            message: "开始时间必须晚于当前时间",
            type: 'error'
          });
          return;
        }
        if(!this.checkStrEmpty(item.playEtime)&&new Date(item.playEtime)<=new Date()){
          this.$notify({
            title: '警告',
            message: "结束时间必须晚于当前时间",
            type: 'error'
          });
          return;
        }
        debugger
      if(!this.checkStrEmpty(item.playEtime)&&!this.checkStrEmpty(item.playSTime)){
        if(item.playEtime<=item.playSTime){
          this.$notify({
            title: '警告',
            message: "结束时间必须晚于开始时间",
            type: 'error'
          });
          return;
        }
        
      }
    },
    searchMedia(){
      this.getAdvertMaterialList(1)
    },
    addTabOfAdd(targetName) {
        let newTabName = ++this.tabIndex + '';
        this.addForm.advertPlats.push({
          strategyPoint:newTabName,
          strategyPointName:'平台广告位'+this.tabIndex,
          strategies:[]
        })
        this.editableTabsValue = newTabName;
      },
      addTabOfEdit(targetName) {
        let newTabName = ++this.tabIndex2 + '';
        this.editForm.advertPlats.push({
          strategyPoint:newTabName,
          strategyPointName:'平台广告位'+this.tabIndex2,
          strategies:[]
        })
        this.editableTabsValue2 = newTabName;
      },
    handleAddTabsEdit(targetName, action) {
      if (action === 'add') {
        let newTabName = ++this.tabIndex + '';
        this.addForm.advertPlats.push({
          strategyPoint:newTabName,
          strategyPointName:'平台广告位'+this.tabIndex,
          strategies:[]
        })
        this.editableTabsValue = newTabName;
      }
      if (action === 'remove') {
        let tabs = this.addForm.advertPlats;
        let activeName = this.editableTabsValue;
        if (activeName === targetName) {
          tabs.forEach((tab, index) => {
            if (tab.strategyPoint === targetName) {
              let nextTab = tabs[index + 1] || tabs[index - 1];
              if (nextTab) {
                activeName = nextTab.strategyPoint;
              }
            }
          });
        }
        
        this.editableTabsValue = activeName;
        this.addForm.advertPlats = tabs.filter(tab => tab.strategyPoint !== targetName);
      }
    },
    handleEditTabsEdit(targetName, action) {
      if (action === 'add') {
        let newTabName = ++this.tabIndex2 + '';
        this.editForm.advertPlats.push({
          strategyPoint:newTabName,
          strategyPointName:'平台广告位'+this.tabIndex2,
          strategies:[]
        })
        this.editableTabsValue2 = newTabName;
      }
      if (action === 'remove') {
        let tabs = this.editForm.advertPlats;
        let activeName = this.editableTabsValue2;
        if (activeName === targetName) {
          tabs.forEach((tab, index) => {
            debugger
            if (tab.strategyPoint === targetName) {
              let nextTab = tabs[index + 1] || tabs[index - 1];
              if (nextTab) {
                activeName = nextTab.strategyPoint;
              }
            }
          });
        }
        
        this.editableTabsValue2 = activeName;
        this.editForm.advertPlats = tabs.filter(tab => tab.strategyPoint !== targetName);
      }
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
    deleteAddAdvStrategies(index, rows){
      if(rows=='0101'){
        this.seqId=this.seqId-1
        this.addForm.strategies.splice(index, 1);
      }else{
        this.addForm.advertPlats.forEach((advertPlat, index) => {
          if(advertPlat.strategyPoint==rows.strategyPoint){
            advertPlat.strategies.splice(index, 1);
          }
        })
      }  
    },
    deleteEditAdvStrategies(index, rows){
      if(rows=='0101'){
        this.seqId=this.seqId-1
        this.editForm.strategies.splice(index, 1);
      }else{
        this.editForm.advertPlats.forEach((advertPlat, index) => {
          if(advertPlat.strategyPoint==rows.strategyPoint){
            advertPlat.strategies.splice(index, 1);
          }
        })
      }  
    },
    handleAdvClick(row){
      const _this = this;
      var existFlag=false;
      if(this.rowAdv.mstrategies.length!=0){
          this.rowAdv.mstrategies.forEach(function(item,index){
            if(item.materialId==row.materialId){
                  existFlag=true
                  _this.$message({
                    showClose: true,
                    message: '不可重复添加！',
                    type: 'error',
                    duration:2000
                  });
                  return
              }
          })
          if(!existFlag){
             _this.$message({
                showClose: true,
                message: '选择成功！',
                type: 'success',
                duration:2000
              });
              debugger
              var row_temp=Object.assign({}, row)
              row_temp.playerTimes=1;
              row_temp.playerTime=1;
              _this.rowAdv.mstrategies.push(row_temp)
          }
      }else{
            _this.$message({
              showClose: true,
              message: '选择成功！',
              type: 'success',
              duration:2000
            });
            var row_temp=Object.assign({}, row)
              row_temp.playerTimes=1;
              row_temp.playerTime=1;
              _this.rowAdv.mstrategies.push(row_temp)
            // this.rowAdv.mstrategies.push(row)
      }
     
    },
    addAdv(row,type){
      this.rowAdv = row;
      this.mediaSearchObj.mediaType='';
      this.mediaSearchObj.mediaName='';
      //平台子广告位只能播放图片
      if('2'==type){
        this.mediaSearchObj.mediaType='2';
        this.sonPlatFlag=true;
      }else{
        this.sonPlatFlag=false;
      }
      this.getAdvertMaterialList("1")
      this.dialogVisible_s=true;
    },
    // addStrategies(strategyPoint,type){
    //     if(type=="add"){
    //     this.addForm.strategies.push({
    //             mstrategies:[],
    //             playEtime:"",
    //             playSTime:"",
    //             seqId:"",
    //             strategyPoint:strategyPoint,
    //             strategyType:''

    //     })
    //     }else if(type=="edit"){
    //       this.editForm.strategies.push({
    //             mstrategies:[],
    //             playEtime:"",
    //             playSTime:"",
    //             seqId:"",
    //             strategyPoint:strategyPoint,
    //             strategyType:''

    //     })
    //     }
    // },
    addStrategies(advertPlat,type){
        if(type=="add"){
          if(advertPlat=='0101'){
            this.addForm.strategies.push({
              mstrategies:[],
              playEtime:"",
              playSTime:"",
              seqId:"",
              strategyPoint:"0101",
              strategyPointName:"首页主广告位",
              strategyType:''
            })
          }else{
            advertPlat.strategies.push({
                mstrategies:[],
                playEtime:"",
                playSTime:"",
                seqId:"",
                strategyPoint:advertPlat.strategyPoint,
                strategyPointName:advertPlat.strategyPointName,
                strategyType:''
              })
          }
          
        }else if(type=="edit"){
          if(advertPlat=='0101'){
            this.editForm.strategies.push({
              mstrategies:[],
              playEtime:"",
              playSTime:"",
              seqId:"",
              strategyPoint:"0101",
              strategyPointName:"首页主广告位",
              strategyType:''
            })
          }else{
            advertPlat.strategies.push({
                mstrategies:[],
                playEtime:"",
                playSTime:"",
                seqId:"",
                strategyPoint:advertPlat.strategyPoint,
                strategyPointName:advertPlat.strategyPointName,
                strategyType:''
              })
          }
        }
    },
    next(formName) {
      debugger
      this.$refs[formName].validate((valid) => {
          if (!valid) {
            return false;
          }else{
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
             _this.multipleSelection += row.advertId+ ",";
      
          });
        } 

    },
    search(){
      this.getData("1")
    },
    search_r(){
       this.getSelectAdvertDeviceRwList("1")
    },
    changeType(item){
      item.playEtime=""
      item.playSTime=""
    },
    handleInvalid(row){
      var _this=this
      this.$confirm('确认要失效该广告吗吗？', '提示', {}).then(() => {
                  let para ={
                    ids:row.advertId
                  }
                  invalidAdvertConfig(para).then((res) => {
                        if(res.zhead.reTCode=="0000"){
                        NProgress.done();
                        this.$notify({
                          title: '成功',
                          message: res.zhead.retMsg,
                          type: 'success'
                        });
                        _this.getData(_this.currentPage)
                        _this.getSelectAdvertDeviceRwList("1")
                        // this.$router.push(
                        //     {path:'/adv/Launch/list'}
                        // );
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
                    ids:_this.multipleSelection=="" ? row.advertId : _this.multipleSelection
                  }
                  removeAdvertConfig(para).then((res) => {
                        if(res.zhead.reTCode=="0000"){
                        NProgress.done();
                        this.$notify({
                          title: '成功',
                          message: res.zhead.retMsg,
                          type: 'success'
                        });
                        _this.getData(_this.currentPage)
                        _this.getSelectAdvertDeviceRwList("1")
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
      this.adevicesValue=[];
      this.addForm={
            name:"",
            deliveryType:"",
            lazyTime:"",
            operType:"",
            playerPlat:"",
            curState:"",
            adevices:[],
            strategies:[],
            advertPlats:[]
          };
      this.editableTabsValue= '1';
      this.editableTabs= [];
      this.tabIndex= 1;
      this.$nextTick(function(){
        this.$refs.addName.focus()
      });
    },
    addSubmitForm(formName,curState) {//提交新增编辑窗口
        var _this= this;
        console.log(this.adevices)
        
        this.$refs[formName].validate((valid) => {
          if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
                      NProgress.start();
                      if(formName=="addForm"){
                        _this.addForm.adevices=[];
                        _this.adevicesValue.forEach((item, index) => {
                                _this.addForm.adevices.push({
                                curState:curState,
                                deviceId:item,
                                playerPlat:_this.addForm.playerPlat
                                });

                        })
                        _this.addForm.strategies.forEach((item, index) => {
                               item.seqId=index;
                               item.mstrategies.forEach((e,i)=>{
                                  e.seqId = i
                               })
                        })
                        let para = Object.assign({}, this.addForm);
                        para.curState=curState
                        addAdvertConfig(para).then((res) => {
                              if(res.zhead.reTCode=="0000"){
                              
                              NProgress.done();
                              this.$notify({
                                title: '成功',
                                message: res.zhead.retMsg,
                                type: 'success'
                              });
                              _this.addForm={
                                  name:"",
                                  deliveryType:"",
                                  lazyTime:"",
                                  operType:"",
                                  playerPlat:"",
                                  curState:"",
                                  adevices:[],
                                  strategies:[]
                              },
                              _this.dialogVisible = false;
                              _this.adevicesValue = []
                              _this.getData("1")
                              _this.getSelectAdvertDeviceRwList("1")
                              }else{
                                this.$notify({
                                  title: '失败',
                                  message: res.zhead.retMsg,
                                  type: 'error'
                                });
                            }
                        });
                      }else if(formName=="editForm"){
                        _this.editForm.adevices=[]
                        _this.adevicesValue.forEach((item, index) => {
                                _this.editForm.adevices.push({
                                curState:curState,
                                deviceId:item,
                                playerPlat:_this.addForm.playerPlat
                                });
                        })
                        _this.editForm.strategies.forEach((item, index) => {
                               item.seqId=index;
                               item.mstrategies.forEach((e,i)=>{
                                  e.seqId = i
                               })
                        })
                        let para = Object.assign({}, this.editForm);
                        para.curState=curState
                        editAdvertConfig(para).then((res) => {
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
                              _this.getSelectAdvertDeviceRwList("1")
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
    handleEdit(index, row, type) {//显示编辑窗口
     var _this = this;
      
     _this.adevicesValue = []
      const listQuery = {
        advertId:row.advertId,
        type:type
      }
      getEditAdv(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
           if(type=='1'){
             _this.viewVisible = true
           }else{
             _this.editdialogVisible = true
           }
            
            _this.activ = "set1"
            _this.editForm = response.zbody.datas
            NProgress.done();
            response.zbody.datas.adevices.forEach(function(item,index){
                _this.adevicesValue.push(item.deviceId)
            })
            // this.editableTabsValue2= _this.editForm.advertPlats.length+1+"";
            this.editableTabsValue2="1";
            this.editableTabs2= [];
            this.tabIndex2= _this.editForm.advertPlats.length+1;
            this.$nextTick(function(){
              this.$refs.editName.focus()
            });
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
      this.listLoading = true
      const listQuery = {
        name:_this.keyword,
        pageSize:_this.pagesize,
        pageNum:page,
        orderByColumn:'createtime',
        isAsc:'desc'//desc
      }
      advertConfigList(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
            NProgress.done();
          _this.total = response.zbody.datas.total;  
          _this.tableData = response.zbody.datas.rows;
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
        mediaName:_this.mediaSearchObj.mediaName,
        mediaType:_this.mediaSearchObj.mediaType,
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
              message: res.zhead.retMsg,
              type: 'error'
            });
         }
      })
    },
    getSelectAdvertDeviceRwList(page) {
      var _this = this;
      const listQuery = {
        deviceId:_this.r_deviceId,
        name:_this.r_keyword,
        pageSize:_this.pagesize2,
        pageNum:page,
        orderByColumn:'createtime',
        isAsc:'desc'//desc
      }
      selectAdvertDeviceRwList(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
            NProgress.done();
            _this.total2= response.zbody.datas.total
            _this.DeviceRwData = response.zbody.datas.rows
         }else{
             this.$notify({
              title: '失败',
              message: res.zhead.retMsg,
              type: 'error'
            });
         }
      })
    },
    getAdvInfo(row) {
      var _this = this;
      const listQuery = {
        advertId:row.advertId
      }
      advInfo(listQuery).then(response => {
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
      //投放设备信息
      const listQuery1 = {
        districtId:row.districtId,
        lineId:row.lineId,
        deviceId:row.deviceId,
        advertId:row.advertId
      }
      tflist(listQuery1).then(response => {
         if(response.zhead.reTCode==="0000"){
            _this.total3 = response.zbody.datas.total
            _this.tfData =response.zbody.datas.rows
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
    gettflist(){


    }

    
  },

  mounted () {
    this.getData("1")
    this.getVendingListData()
    this.getSelectAdvertDeviceRwList("1")
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