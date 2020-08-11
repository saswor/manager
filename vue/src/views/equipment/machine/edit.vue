<template>
 <div class="div-warp">
      <!-- 生成模板 -->
      <el-dialog
        title="生成模板"
        :visible.sync="templateDialogVisible"
        width="30%"
       >
        <el-input v-model="TemplateName" placeholder="请输入模版名称"></el-input>
        <span slot="footer" class="dialog-footer">
          <el-button @click="templateDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="TemplateOk">确定生成</el-button>
        </span>
      </el-dialog>
      <!-- 生成模板 end -->
      <!-- 选择模板 -->
      <el-dialog
        title="选择模板"
        :visible.sync="templateVisible"
        :close-on-click-modal="false"
        width="900px"
        >
          <el-table :data="templateData">
            <el-table-column property="name" label="模板名" ></el-table-column>
            <el-table-column property="factoryName" label="厂家" width="150"></el-table-column>
            <el-table-column property="cabinetTypeName" width="150" label="模板类型"></el-table-column>
            <el-table-column
              fixed="right"
              label="操作"
              width="120">
              <template slot-scope="scope">
                <el-button
                  @click.native.prevent="selectTemplateOn(scope.$index, templateData)"
                  type="text"
                  size="small">
                  选择
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <div class="pageBox">
              <el-pagination
                @current-change="handleCurrentChange"
                background
                :page-size="pagesize"
                layout="prev, pager, next"
                :total="templatetotal">
              </el-pagination>
            </div>
      </el-dialog>
      <!-- 选择模板结束 -->

      <!-- 更换商品 -->
      <el-dialog
        title="更换商品"
        :visible.sync="productDialogVisible"
        width="1000px"
        >
          <el-form :model="updateProductForm" ref="updateProductForm" @submit.prevent="updateProductFormSubmit">
              <el-card class="box-card productCard">
                <div class="productDiv" v-if="updateProductForm.srcProductId!=''" >
                  <img :src="ImgUrl+updateProductForm.srcPic" class="productPic">
                </div>
                
                <el-form-item label="原有商品编号" prop="srcProductId" size="small" :label-width="formLabelWidth">
                  <el-input v-model="updateProductForm.srcProductId" class="inputStyle" readonly="true"></el-input>
                </el-form-item>
                <el-form-item label="原有商品名称" prop="srcProductName" size="small" :label-width="formLabelWidth">
                  <el-input v-model="updateProductForm.srcProductName" class="inputStyle" readonly="true"></el-input>
                </el-form-item>
                <el-form-item label="下架数量" prop="curCap" size="small" :label-width="formLabelWidth">
                  <el-input-number :min="0" v-model="updateProductForm.curCap" class="inputStyle" ></el-input-number>
                </el-form-item>
              </el-card>
              <el-card class="box-card productCard"> 
                <div class="productDiv" v-if="updateProductForm.productId!=''" >
                  <img :src="ImgUrl+updateProductForm.pic" class="productPic">
                </div>
                <!-- 添加按钮 -->
                <div class="productDiv" v-if="updateProductForm.productId==''" >
                  <i class="el-icon-circle-plus-outline icon-size"  @click="add_hd_show" ></i>
                </div>
                <el-form-item label="商品编号" prop="productId" size="small" :label-width="formLabelWidth">
                  <el-input v-model="updateProductForm.productId" class="inputStyle" readonly="true"></el-input>
                </el-form-item>
                <el-form-item label="商品名称" prop="productName" size="small" :label-width="formLabelWidth">
                  <el-input v-model="updateProductForm.productName" class="inputStyle" readonly="true"></el-input>
                </el-form-item>
                <el-form-item label="最大容量" prop="capacity" size="small" :label-width="formLabelWidth">
                  <el-input-number :min="0" v-model="updateProductForm.capacity" class="inputStyle" ></el-input-number>
                </el-form-item>
                <el-form-item label="阈值" prop="warnCap" size="small" :label-width="formLabelWidth">
                  <el-input-number :min="0" :max="updateProductForm.capacity" v-model="updateProductForm.warnCap" class="inputStyle" ></el-input-number>
                </el-form-item>
              </el-card>
            <el-button @click="hd_product_cancel" size="mini">取 消</el-button>
            <el-button @click="hd_product_refresh" size="mini">清 除</el-button>
            <el-button type="primary" size="mini" @click="submitUpdateProduct">提 交</el-button>


            <el-dialog
              width="920px"
              title="商品列表"
              :visible.sync="updateProduct_PlistVisible"
              append-to-body>

              <div class="searchBox">
                    <el-input placeholder="请输入商品名称" v-model="goodsWord" style="width:150px;" size="mini" clearable>
                      </el-input>
                      <el-button type="success" @click.native.prevent="goodsSearch" size="mini">查询</el-button>
              </div>

              <el-row :gutter="24" style="margin-top: 10px">
                <el-col :span="24" style="margin-top: 20px;" >
                    <div class="imgStyel1" v-for="o in cocmodityList" @click="selectUpdateGoods(o)">
                      <div class="Pic-box">
                      <img :src="ImgUrl+o.pic" >
                      </div>
                      <div class="bottom1">
                        <!-- <div class="titels">{{o.name}}</div> -->
                        <div class="titles1">{{o.name}}</div>
                        <div class="price1">售价：¥<i>{{o.salePrice}}</i></div>
                      </div>
                    </div>
                    
                
                </el-col>
              </el-row>
              <div class="pageBox">
                  <el-pagination
                  @current-change="handleCurrentChange_p"
                    background
                    layout="prev, pager, next"
                    :page-size="pagesize"
                    :total="p_total">
                  </el-pagination>
              </div>
            </el-dialog>
            

            
          </el-form>

      </el-dialog>

      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>编辑售货机</span>
        </div>
          <el-form :model="addForm" :rules="Rules" class="formWidth"  ref="addForm" @submit.prevent="onSubmit">
        <el-tabs v-model="editableTabsValue" type="card" closable @tab-remove="removeTab" @tab-click="tab_tab">
           <el-tab-pane label="主柜信息" name="1">
                 <!-- 主柜具体的货到配置弹层 -->
                    <el-dialog
                          title="主柜配置货道"
                          :visible.sync="main_innerVisible"
                          :fullscreen="true"
                          append-to-body>
                              <el-dialog
                                width="920px"
                                title="商品列表"
                                :visible.sync="main_PlistVisible"
                                append-to-body>

                                <div class="searchBox">
                                      <el-input placeholder="请输入商品名称" v-model="goodsWord" style="width:150px;" size="mini" clearable>
                                        </el-input>
                                        <el-button type="success" @click.native.prevent="goodsSearch" size="mini">查询</el-button>
                                </div>

                                <el-row :gutter="24" style="margin-top: 10px">
                                  <el-col :span="24" style="margin-top: 20px;" >
                                      <div class="imgStyel1" v-for="o in cocmodityList" @click="selectGoods(o)">
                                        <div class="Pic-box">
                                        <img :src="ImgUrl+o.pic" >
                                        </div>
                                        <div class="bottom1">
                                          <!-- <div class="titels">{{o.name}}</div> -->
                                          <div class="titles1">{{o.name}}</div>
                                          <div class="price1">售价：¥<i>{{o.salePrice}}</i></div>
                                        </div>
                                      </div>
                                     
                                 
                                  </el-col>
                                </el-row>
                                <div class="pageBox">
                                    <el-pagination
                                    @current-change="handleCurrentChange_p"
                                      background
                                      layout="prev, pager, next"
                                      :page-size="pagesize"
                                      :total="p_total">
                                    </el-pagination>
                                </div>
                              </el-dialog>
                    <div class="demo-bs-wrapper">
                      <div style="position:relative;" >
                          <el-checkbox-group v-model="checkedCities" @change="handleCheckedCitiesChange_hd">
                              <el-row type="flex" justify="left"  v-for="(row,index) in addForm.lanes">
                                <el-col  v-for="clo in row.cols">
                                    <div class="imgStyel">
                                            <!-- 操作区域 -->
                                            <div class="block-top"> 
                                              <el-checkbox :label="clo" :key="clo.laneId" class="checkbox-left">{{clo.laneId}}</el-checkbox>

                                              <!-- <el-button type="text" class="delBnt" size="mini" @click="del_hd(clo)">删除</el-button> -->
                                              <el-button type="text" v-if="clo.lanep.allowDel=='2'" class="delBnt" size="mini" @click="updateLaneProduct(clo)">更换商品</el-button>
                                              <el-button type="text" v-else class="delBnt" size="mini" @click="del_hd(clo)">删除</el-button>
                                            </div>
                                        
                                            <!-- 添加按钮 -->
                                            <div  class="add-class blockBox" v-if="(clo.lanep.laneSate=='1'||clo.lanep.laneSate=='2')&&clo.lanep.productId==''" >
                                              <i class="el-icon-circle-plus-outline icon-size"  @click="add_hd(clo)" ></i>
                                            </div>
                                              

                                            <!-- 禁用 -->
                                            <div v-else-if="clo.lanep.laneSate >'2'" class="add-class blockBox">
                                                <i class="el-icon-circle-close-outline icon-size-circle"></i>
                                            </div>


                                            <!-- 有商品 -->
                                            <div else class="blockBox" v-else>
                                                  <img :src="ImgUrl+clo.lanep.pic" >
                                                  <div class="bottom clearfix">
                                                    <div class="titels">{{clo.lanep.productName}}</div>
                                                    <div class="price"> ¥<i>{{clo.lanep.salePrice}}</i><em>库存{{clo.lanep.curCap}}</em></div>
                                                  </div>
                                                  <div class="InputFStyle" >
                                                      <el-input v-model="clo.lanep.capacity"  size="mini" class="i1" placeholder="容量"></el-input>
                                                      <el-input v-model="clo.lanep.warnCap" size="mini" class="i2" placeholder="阈值"></el-input>
                                                  </div>
                                            </div>
                                    </div>

                                </el-col>
                              </el-row>
                          </el-checkbox-group>
                      </div>
                    </div>
                    <div slot="footer" class="Bnt-footer" style="text-align:center">
                                <!-- <el-checkbox :indeterminate="isIndeterminate" style="float:left; margin: 4px 10px 0 0;" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox> -->
                                <el-button @click="hd_cancel" size="mini">取 消</el-button>
                                <!-- <el-button type="primary" size="mini" @click="enableUp">启用</el-button>
                                <el-button type="primary" size="mini" @click="disableUp">禁用</el-button> -->
                                <el-button type="primary" size="mini" v-if="isShowTemaplate" @click="selectTemplate">选择模版</el-button>

                                <el-button type="primary" size="mini" disabled v-if="isShowTemaplate==false" >选择模版</el-button>
                                <el-button type="primary" size="mini" @click="addTemplate('1',addForm)">生成模版</el-button>
                                <el-button type="primary" size="mini" @click="saveLanes">保存</el-button>
                               
                          </div>
                    </el-dialog>
                 <!-- 主柜具体的货到配置弹层 end -->


                  <!-- 主柜 -->
                          <el-form-item label="售货机编码" prop="siteCode" size="small" :label-width="formLabelWidth">
                            <el-input v-model="addForm.siteCode" placeholder="请输入30位以内的售货机编码"></el-input>
                          </el-form-item>
                          <el-form-item label="售货机名称" prop="siteName" size="small" :label-width="formLabelWidth">
                            <el-input v-model="addForm.siteName" auto-complete="off" placeholder="请输入50位以内的售货机名称"></el-input>
                          </el-form-item>
                          <el-form-item label="厂家" prop="factoryId" size="small" :label-width="formLabelWidth">
                            <el-select v-model="addForm.factoryId" disabled @change="getCabinetType('0',addForm.factoryId)" size="small" placeholder="请选择厂家">
                                <el-option
                                  v-for="item in ManufactorOptions"
                                  :key="item.dictValue"
                                  :label="item.dictLabel"
                                  :value="item.dictValue">
                                </el-option>
                            </el-select>
                          </el-form-item>
                          <el-form-item label="货柜类型" prop="cabinetType" size="small" :label-width="formLabelWidth">
                            <el-select v-model="addForm.cabinetType" disabled @change="getDeviceByCabinetType('0',addForm.cabinetType)" size="small" placeholder="请选择货柜类型">
                               <el-option
                                  v-for="item in CabinetOptions"

                                  :key="item.cabinetType"
                                  :label="item.cabinetTypeName"
                                  :value="item.cabinetType">
                                </el-option>
                            </el-select>
                          </el-form-item>

                          <el-form-item label="机型" prop="deviceId" size="small" :label-width="formLabelWidth">
                            <el-select v-model="addForm.device" disabled  @change="changeDevice('0',addForm.deviceId)" size="small" placeholder="请选择机型">
                              <el-option
                                  v-for="item in modelOptions"
                                  :key="item"
                                  :label="item"
                                  :value="item">
                              </el-option>
                            </el-select>
                          </el-form-item>
                          

                          <el-form-item label="点位"  prop="pointId" size="small"  :label-width="formLabelWidth">
                            <el-autocomplete
                              v-model="addForm.pointName"
                              :fetch-suggestions="querySearchAsync"
                              value-key="name"
                              placeholder="输入搜索点位"
                              @select="handleSelect"

                            ></el-autocomplete>
                          </el-form-item>
                          <el-form-item label="网络类型" prop="netWork" size="small" :label-width="formLabelWidth">
                            <el-select v-model="addForm.netWork" size="small" placeholder="请选择网络类型">
                              <el-option
                                  v-for="item in networkOptions"
                                  :key="item.dictValue"
                                  :label="item.dictLabel"
                                  :value="item.dictValue">
                              </el-option>
                            </el-select>
                          </el-form-item>
                          <el-form-item label="售卖状态" prop="sellState" size="small" :label-width="formLabelWidth">
                            <el-select v-model="addForm.sellState" size="small" placeholder="请选择售卖状态">
                              <el-option
                                  v-for="item in stateOptions"
                                  :key="item.dictValue"
                                  :label="item.dictLabel"
                                  :value="item.dictValue">
                              </el-option>
                            </el-select>
                          </el-form-item>
                          <el-form-item prop="payTypeList" label="支付方式" size="small" :label-width="formLabelWidth">

                             <el-checkbox-group v-model="addForm.payTypeList">
                              <el-checkbox label="01">支付宝扫码支付</el-checkbox>
                              <el-checkbox label="02">微信扫码支付</el-checkbox>
                            <!--   <el-checkbox label="03">微信公众号支付</el-checkbox> -->

                            <!--   <el-checkbox label="04">百度钱包</el-checkbox>
                              <el-checkbox label="05">翼支付</el-checkbox>
                              <el-checkbox label="06">京东钱包</el-checkbox>
                              <el-checkbox label="07">会员支付</el-checkbox>
                              <el-checkbox label="08">银联支付</el-checkbox>
                              <el-checkbox label="09">聚合支付</el-checkbox> -->

                            </el-checkbox-group>
                          </el-form-item>
                          <el-form-item label="视频播放"  size="small" prop="mediaType" :label-width="formLabelWidth">
                        
                              <el-radio v-model="addForm.mediaType" label="1" border>支持</el-radio>
                              <el-radio v-model="addForm.mediaType" label="2" border>不支持</el-radio>
                        
                          </el-form-item>

                         
                  <!-- 主柜结束 -->
            
                   <div style="margin:40px 0 100px 10px;">
                    <el-button type="danger"  @click="showLines('1')">配置货道</el-button>
                  </div>


           </el-tab-pane>

          <el-tab-pane
            v-for="(item, index) in addForm.cabinets"
            :key="item.viewName"
            :label="item.cabinetName"
            :name="item.viewName"
          >

          <!-- 副柜表单  -->
           <template  scope="scope">

                
      <!-- 具体的货到配置弹层 -->
        <el-dialog
              title="配置货道"
              :visible.sync="innerVisible==item.viewName"
              :fullscreen="true"
              :show-close="false"
              append-to-body>
                  <el-dialog
                    width="920px"
                    title="商品列表"
                    :close-on-click-modal="false"
                    :show-close="false"
                    :visible.sync="PlistVisible==item.viewName"
                    append-to-body>

                    <div class="searchBox">
                        <el-input placeholder="请输入商品名称" v-model="goodsWord" style="width:150px;" size="mini" clearable>
                          </el-input>
                          <el-button type="success" @click.native.prevent="goodsSearch" size="mini">查询</el-button>
                          <el-button style="position:absolute; z-index:100; top:20px; right:20px;"  size="mini" icon="el-icon-close" circle @click="PlistVisible=false"></el-button>
                    </div>

                    <el-row :gutter="24" style="margin-top: 10px">
                      <el-col :span="24" style="margin-top: 20px;" >
                          <div class="imgStyel1" v-for="o in cocmodityList" @click="selectGoods(o,item,'2')">
                            <div class="Pic-box">
                            <img :src="ImgUrl+o.pic">
                            </div>
                            <div class="bottom1">
                              <div class="titles1">{{o.name}}</div>
                              <div class="price1">售价：¥<i>{{o.salePrice}}</i></div>
                            </div>
                          </div>
                         
                     
                      </el-col>
                    </el-row>
                    <div class="pageBox">
                      <el-pagination
                        @current-change="handleCurrentChange_p"
                        background
                        :page-size="pagesize"
                        :current-page="currentPage"
                        layout="prev, pager, next"
                        :total="p_total_c">
                      </el-pagination>
                    </div>
                  </el-dialog>
                  <div class="demo-bs-wrapper">
                      <div style="position:relative; ">
                            <el-checkbox-group v-model="checkedCities" @change="handleCheckedCitiesChange_hd">
                                <el-row type="flex"  justify="left"  v-for="(row,index) in item.lanes">
                                  <el-col  v-for="clo in row.cols">
                                      <div class="imgStyel"  >
                                              <!-- 操作区域 -->
                                              <div class="block-top"> 
                                                <el-checkbox :label="clo" :key="clo.laneId" class="checkbox-left">{{clo.laneId}}</el-checkbox>

                                                <!-- <el-button type="text" class="delBnt" size="mini" @click="del_hd(clo,'2',item)">删除</el-button> -->
                                                <el-button type="text" v-if="clo.lanep.allowDel=='2'" class="delBnt" size="mini" @click="updateLaneProduct(clo)">更换商品</el-button>
                                                <el-button type="text" v-else class="delBnt" size="mini" @click="del_hd(clo,'2',item)">删除</el-button>

                                              </div>
                                          
                                              <!-- 添加按钮 -->
                                              <div  class="add-class blockBox" v-if="(clo.lanep.laneSate=='1'||clo.lanep.laneSate=='2')&&clo.lanep.productId==''" >
                                                <i class="el-icon-circle-plus-outline icon-size"  @click="add_hd(item,clo)" ></i>
                                              </div>
                                                

                                              <!-- 禁用 -->
                                              <div v-else-if="clo.lanep.laneSate >'2'" class="add-class blockBox">
                                                  <i class="el-icon-circle-close-outline icon-size-circle"></i>
                                              </div>


                                              <!-- 有商品 -->
                                              <div else class="blockBox" v-else>
                                                    <img :src="ImgUrl+clo.lanep.pic" >
                                                    <div class="bottom clearfix">
                                                      <div class="titels">{{clo.lanep.productName}}</div>
                                                      <div class="price"> ¥<i>{{clo.lanep.salePrice}}</i><em>库存{{clo.lanep.curCap}}</em></div>
                                                    </div>
                                                    <div class="InputFStyle" >
                                                        <el-input v-model="clo.lanep.capacity" size="mini" class="i1" placeholder="容量"></el-input>
                                                        <el-input v-model="clo.lanep.warnCap" size="mini" class="i2" placeholder="阈值"></el-input>
                                                    </div>
                                              </div>
                                      </div>

                                  </el-col>
                                </el-row>
                            </el-checkbox-group>
                            
                      </div>
                  </div>
                  <div slot="footer" class="Bnt-footer" style="text-align:center">
                                  <!-- <el-checkbox :indeterminate="isIndeterminate" style="float:left; margin: 4px 10px 0 0;" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox> -->
                                  <el-button @click="hd_cancel" size="mini">取 消</el-button>
                                  <!-- <el-button type="primary" size="mini" @click="enableUp">启用</el-button>
                                  <el-button type="primary" size="mini" @click="disableUp('2',item)">禁用</el-button> -->
                                  <el-button type="primary" size="mini" v-if="item.isShowTemplate" @click="selectTemplate(item)">选择模版</el-button>
                                  <el-button type="primary" v-else disabled size="mini">选择模版</el-button>
                                  <el-button type="primary" size="mini" @click="addTemplate('2',item)">生成模版</el-button>
                                  <el-button type="primary" size="mini" @click="saveLanes">保存</el-button>
                            </div>
        </el-dialog>
      <!-- 具体的货到配置弹层 end -->


                <!-- <el-form-item label="货柜编号"  prop="area_id" size="small" :label-width="formLabelWidth">
                  <el-input v-model="item.deviceId" class="inputStyle" ></el-input>
                </el-form-item>
                <el-form-item label="货柜名称" prop="area" size="small" :label-width="formLabelWidth">
                  <el-input v-model="addForm.area" auto-complete="off"  class="inputStyle" ></el-input>
                </el-form-item> -->
                <el-form-item label="外挂"  size="small" :label-width="formLabelWidth">
                  <el-select v-model="item.hangType" size="small" placeholder="请选择外挂">
                    <el-option label="是" value="1">是</el-option>
                    <el-option label="否" value="2">否</el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="串口号" size="small" :label-width="formLabelWidth">
                  <el-input v-model="item.pointCode" class="inputStyle" ></el-input>
                </el-form-item>

                <el-form-item label="厂家"  size="small" :label-width="formLabelWidth">
                        <el-select v-model="item.factoryId" :disabled="item.dis" @change="getCabinetType(item,item.factoryId)" size="small" placeholder="请选择厂家">
                            <el-option
                              v-for="i in ManufactorOptions"
                              :key="i.dictValue"
                              :label="i.dictLabel"
                              :value="i.dictValue">
                            </el-option>
                        </el-select>
                </el-form-item>
                <el-form-item label="货柜类型"   size="small" :label-width="formLabelWidth">
                            <el-select v-model="item.cabinetType" :disabled="item.dis" @change="getDeviceByCabinetType(item,item.cabinetType)" size="small" placeholder="请选择货柜类型">
                               <el-option
                                  v-for="i in CabinetOptions"
                                  :key="i.cabinetType"
                                  :label="i.cabinetTypeName"
                                  :value="i.cabinetType">
                                </el-option>
                            </el-select>
                </el-form-item>
                <el-form-item label="机型" size="small" :label-width="formLabelWidth">
                            <el-select v-model="item.deviceId" :disabled="item.dis" @change="changeDevice(item,item.deviceId)" size="small" placeholder="请选择机型">
                              <el-option
                                  v-for="i in modelOptions"
                                  :key="i.deviceCode"
                                  :label="i.deviceId"
                                  :value="i.deviceCode">
                              </el-option>
                            </el-select>
                </el-form-item>
              <div style="margin:40px 0 100px 10px;" data-form = "item.name">
                    <el-button type="danger" @click="showLines('2',item)">配置货道</el-button>
              </div>

           </template>
                
          <!-- 副柜表单end -->
          <!--  <cabinet></cabinet> -->
          </el-tab-pane>
        </el-tabs>
        </el-form>
      </el-card>

      <div class="bntBox">
         <el-button   @click="addTab(editableTabsValue)">新增挂载柜</el-button>
          <el-button @click="dialogAddTableVisible = false">取 消</el-button>
          <el-button type="primary" @click="addSubmitForm('addForm')">提交</el-button>
      </div>
  </div>
</template>       
     

<script>
import cabinet from './cabinetForm'
import { editMachine,getMachineSelect,getVendingModel,getPositionList,vendingModelList,getCommodity,getVendingViewByLogid,selectVendingLanepByCabinetLogid,getVendingPconfigList,addVendingPconfig,updateVendingProduct} from '@/api/equipment'
import {vendingPconfigGet,selectCabinetTypeByFactoryId,selectDeviceByCabinetType} from '@/api/dictionaries'
import NProgress from 'nprogress'

export default {
  data() {
        return {
          productDialogVisible:false,
          updateProduct_PlistVisible:false,
          updateProductForm:{
            // srcPic:"",
            // srcProductId:"",
            // srcProductName:"",
            // curCap:0,
            // pic:"",
            // productId:"",
            // productName:"",
          },
          newTabName:'1',
          currentPage:1,
          TemplateName:"",
          total:1,
          p_total:1,//商品总页数
          p_total_c:1,
          pagesize:40,
          templatetotal:1,//模板总页数
          cocmodityList:[],//商品列表
          isIndeterminate: true,
          vendingModeData:{},//货到行和列的数据
          checkedCities: [],
          cabinets_name:"0",
          checkAll: false,
          disabled:true,
          input10:'',
          goodsWord:'',
          PlistVisible:false,
          innerVisible:false,
          main_innerVisible:false,
          main_PlistVisible:false,
          templateVisible:false,//选择模板弹出
          templateDialogVisible:false,//生成模板弹出 
          editableTabsValue: '1',
          editableTabs: [],
          tabIndex: 1,
          ManufactorOptions:[],//厂家列表---下拉
          CabinetOptions:[],//货柜类型---下拉
          stateOptions:[],//售卖状态--下拉
          networkOptions:[],//网络--下拉
          modelOptions:[],//机型希腊
          templateData:[],//选择模板列表
          options:[],
          palychek:[],
          restaurants:[],//点位
          companyName: '',
          value:"",
          input:'',
          formLabelWidth: '120px',
          addForm: { 
            siteCode:"",  //售货机编码
            siteName:"",  // 售货机名称
            factoryId:"", // 厂家编号
            cabinetType:"", // 货柜类型 01:商店机 02:弹簧机 03:格子机
            deviceId:"",  // 机型编码
            pointId:"", // pointId
            payTypeList:[], //支付类型
            mediaType:1, // 播放视频 1:支持 2:无
            netWork:"",  // 网络制式 1:GPRS  2:网口
            sellState:"",  // 售卖状态 1:可售卖 2:不可售卖
            description:"", // 描述
            lanes:[],//主柜子货到
            cabinets:[  // 副柜
              
            ]
          },
          cabinetsObj:"",
          lanes:[],
          Rules:{
              siteCode:[
                { required: true, message: '售货机编码不能为空 ', trigger: 'change' }
              ],
              siteName:[
                { required: true, message: '售货机名称不能为空 ', trigger: 'change' }
              ],
              dictLabel:[
                { required: true, message: ' ', trigger: 'change' },
               
              ],
              factoryId:[
                { required: true, message: ' ', trigger: 'change' },
              ],
              cabinetType:[
                 { required: true, message: ' ', trigger: 'change' },
              ],
              netWork:[
                 { required: true, message: ' ', trigger: 'change' },
              ],
              sellState:[
                 { required: true, message: ' ', trigger: 'change' },
              ],
              deviceId:[
                 { required: true, message: ' ', trigger: 'change' },
              ],
              pointId:[
                { required: true, message: '点位不能为空 ', trigger: 'change' },
              ],
              payTypeList:[
                { required: true, message: '支付方式不能为空 ', trigger: 'change' },
              ],
              mediaType:[
                { required: true, message: ' ', trigger: 'change' },
              ],
              // hangType:[
              //   { required: true, message: ' ', trigger: 'change' },
              // ],
              // pointCode:[
              //   { required: true, message: ' ', trigger: 'change' },
              // ],
          },
          hd_id:'',
          hd_dx:[],//单选临时存放
          select_hd_list:[],//最终拼接的货道数据
          hd_all:[],
          lineData:{
            factoryName: "",
            fullName: "",
            logid: "",
            name: "",
            pic: "",
            productCode: "",
            productId: "",
            remark: null,
            salePrice: "",
            laneSate:1
          },
          Template:{},
          isShowTemaplate:true,
         
        }
  },
  watch: {
   
  },
  methods: {
    hd_product_cancel(){
      this.updateProductForm={};
      this.productDialogVisible=false;
    },
    hd_product_refresh(){
      this.updateProductForm.productId="";
      this.updateProductForm.productName="";
      this.updateProductForm.pic="";
      this.updateProductForm.capacity=0;
      this.updateProductForm.warnCap=0;
    },
    //提交更换商品
    submitUpdateProduct(){
      var _this=this;
      this.$confirm('确认提交吗？', '提示', {}).then(() => {
        if(!Number.isInteger(_this.updateProductForm.curCap)){
          this.$notify({
            title: '警告',
            message: "下架数量必须是整数",
            type: 'error'
          });
          return;
        }
        if(!Number.isInteger(_this.updateProductForm.capacity)){
          this.$notify({
            title: '警告',
            message: "最大容量必须是整数",
            type: 'error'
          });
          return;
        }
        if(!Number.isInteger(_this.updateProductForm.warnCap)){
          this.$notify({
            title: '警告',
            message: "阈值必须是整数",
            type: 'error'
          });
          return;
        }
        NProgress.start();
          let para = Object.assign({}, this.updateProductForm);
          //para.lanes = this.checkedCities
          updateVendingProduct(para).then((res) => {
                if(res.zhead.reTCode=="0000"){
                NProgress.done();
                this.$notify({
                  title: '成功',
                  message: res.zhead.retMsg,
                  type: 'success'
                });
                this.$refs['updateProductForm'].resetFields();
                this.$router.push(
                      {path:'/equipment/machine/index'}
                );
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
    //是否能选择模板
    isSelectTemplate(lanes,type,row){
        var _this = this;
        var DataArr=[]
        lanes.forEach(function(item,index){
              for(var i = 0; i < item.cols.length; i++){
                  DataArr.push(item.cols[i].lanep)
              }
        })
        for(var i = 0; i<DataArr.length; i++){
          if(DataArr[i].productId!=""){
            if(type==2){
                row.isShowTemplate= false
                return
            }else{
               _this.isShowTemaplate = false
                return
            }
            
          }else{
            if(type==2){
              row.isShowTemplate= true
            }else{
              _this.isShowTemaplate = true
            }
            
          }
        }
    },
    //显示货道
    showLines(type,lanes){
      if (type==1) {
        this.main_innerVisible = true
        this.vendingModeData = this.addForm.lanes
        this.isSelectTemplate(this.addForm.lanes)
      }else{
        this.innerVisible = lanes.viewName
        this.vendingModeData = lanes.lanes
        this.isSelectTemplate(lanes.lanes,'2',lanes.lanes)
      }
    },
    // 货道选择
    handleCheckAllChange(val) {//全选
      this.checkedCities = val ? this.vendingModeData.rows : [];
      this.isIndeterminate = false;
    },
    handleCheckedCitiesChange_hd(value) {//单选
      let checkedCount = value.length;
      this.checkAll = checkedCount === this.vendingModeData.length;
      this.isIndeterminate = checkedCount > 0 && checkedCount < this.vendingModeData.length;
     
    },
    enableUp(c_type,row){//启用
        var _this = this;
        if(c_type =="2"){
          row.lanes.forEach(function(item){
                  for(var i =0; i<item.cols.length; i++){
                    for(var j=0; j<_this.checkedCities.length; j++){
                          if(_this.checkedCities[j].laneId==item.cols[i].laneId){
                              item.cols[i].lanep.laneSate= "1"
                              _this.checkedCities[j].lanep.laneSate = "1"
                              _this.checkedCities.splice(j--,1)
                          }
                    }
                  }
            })

        }else{
            this.addForm.lanes.forEach(function(row){
                  for(var i =0; i<row.cols.length; i++){
                    for(var j=0; j<_this.checkedCities.length; j++){
                          if(_this.checkedCities[j].laneId==row.cols[i].laneId){
                              row.cols[i].lanep.laneSate= "1"
                              _this.checkedCities[j].lanep.laneSate = "1"
                              _this.checkedCities.splice(j--,1)
                          }
                    }
                  }
            })
        }
        this.addForm.lanes.forEach(function(row){
            for(var i =0; i<row.cols.length; i++){
              for(var j=0; j<_this.checkedCities.length; j++){
                    if(_this.checkedCities[j].laneId==row.cols[i].laneId){
                        row.cols[i].lanep.laneSate= "1"
                        _this.checkedCities[j].lanep.laneSate = "1"
                        _this.checkedCities.splice(j--,1)
                    }
              }
            }
        })
    },
    disableUp(c_type,row){//禁用
        var _this = this;
        if(c_type =="2"){
          row.lanes.forEach(function(item){
              for(var i =0; i<item.cols.length; i++){
                for(var j=0; j<_this.checkedCities.length; j++){
                      if(_this.checkedCities[j].laneId==item.cols[i].laneId){
                          item.cols[i].lanep.laneSate = "2"
                      }
                }
              }
          })
          _this.checkedCities=[]
        }else{
          _this.addForm.lanes.forEach(function(row){
              for(var i =0; i<row.cols.length; i++){
                for(var j=0; j<_this.checkedCities.length; j++){
                      if(_this.checkedCities[j].laneId==row.cols[i].laneId){
                          row.cols[i].lanep.laneSate = "2"
                      }
                }
              }
          })
          _this.checkedCities=[]
        }
        
    },
    add_hd(row,line){
      (line==undefined)?this.hd_id = row.laneId:this.hd_id=line.laneId
      this.PlistVisible = row.viewName  
      this.main_PlistVisible = true
    },
    add_hd_show(){
      this.updateProduct_PlistVisible = true
    },
    selectUpdateGoods(data){
      console.log(this.updateProductForm);
      this.updateProductForm.productName=data.name;
      this.updateProductForm.productId=data.productId;
      this.updateProductForm.productPic = data.picJson;
      this.updateProductForm.salePrice=data.salePrice;
      this.updateProductForm.pic=data.pic;
      this.updateProductForm.pStateTime=data.validTime;
      this.updateProductForm.laneSate =1;
      console.log("选中商品后:");
      
      this.updateProduct_PlistVisible = false;
    },
    selectGoods(data,row,c_type){//选择货到商品
  
      var _this = this;
      if(c_type==2){
        row.lanes.forEach(function(item,index){//便利所有货到找到选择的货到并赋值
              for(var i = 0; i<item.cols.length; i++){
                    if(_this.hd_id==item.cols[i].laneId){
                      data.laneSId = item.cols[i].lanep.laneSId;
                        item.cols[i].lanep.productName = data.name;
                        item.cols[i].lanep.productId=data.productId;
                        item.cols[i].lanep.productPic = data.picJson;
                        item.cols[i].lanep.salePrice=data.salePrice;
                        item.cols[i].lanep.pic=data.pic;
                        item.cols[i].lanep.pStateTime=data.validTime;
                        item.cols[i].lanep.capacity="";
                        item.cols[i].lanep.warnCap="";
                        item.cols[i].lanep.laneSate =1;
                       return
                    }
              }
          })
         _this.isSelectTemplate(row.lanes,"2",row)
      }else{
          _this.addForm.lanes.forEach(function(row,index){//便利所有货到找到选择的货到并赋值
              for(var i = 0; i<row.cols.length; i++){
                  if(_this.hd_id == row.cols[i].laneId){
                      data.laneSId = row.cols[i].lanep.laneSId
                      row.cols[i].lanep.productName = data.name;
                      row.cols[i].lanep.productId=data.productId;
                      row.cols[i].lanep.salePrice=data.salePrice;
                      row.cols[i].lanep.pic=data.pic;
                      row.cols[i].lanep.productPic = data.picJson;
                      row.cols[i].lanep.pStateTime=data.validTime;
                      row.cols[i].lanep.capacity="";
                      row.cols[i].lanep.warnCap="";
                      row.cols[i].lanep.laneSate =1;
                      
                      console.log(row.cols[i].lanep)
                      return
                  }

              }
          })
          _this.isSelectTemplate(_this.addForm.lanes)
      }
      this.PlistVisible = false;
      this.main_PlistVisible = false;
    },
    del_hd(data,c_type,row){//删除本地货道商品
      var _this = this;
      if(c_type=="2"){
          //data.lanep = _this.lineData
          data.lanep.productName = "";
          data.lanep.productId="";
          data.lanep.productPic = "";
          data.lanep.salePrice="";
          data.lanep.pic="";
          data.lanep.pStateTime="";
          data.lanep.capacity=0;
          data.lanep.warnCap=0;
          data.lanep.laneSate =1;
          this.isSelectTemplate(row.lanes,"2",row)
      }else{
        
          //data.lanep = _this.lineData
          data.lanep.productName = "";
          data.lanep.productId="";
          data.lanep.productPic = "";
          data.lanep.salePrice="";
          data.lanep.pic="";
          data.lanep.pStateTime="";
          data.lanep.capacity=0;
          data.lanep.warnCap=0;
          data.lanep.laneSate =1;
          this.isSelectTemplate(this.addForm.lanes)
          
      }
    },
    hd_cancel(){
        var _this = this;
        this.$confirm('确认要放弃配置吗？', '提示', {}).then(() => {
          _this.innerVisible = false;
          _this.main_innerVisible=false;
          _this.vendingModeData.rows.forEach(function(row,index){
            for(var i = 0; i<row.cols.length; i++){
                    row.cols[i].lanep.laneSate = 1
            }
          })
          _this.cabinets_name="0"//清空副柜的唯一id
          _this.checkedCities = []
        })
    },
    //售货机换货
    updateLaneProduct(clo){
      var _this = this;
      this.productDialogVisible=true;
      this.updateProductForm=Object.assign({},clo.lanep);
      this.updateProductForm.srcPic=this.updateProductForm.pic;
      this.updateProductForm.srcProductId=clo.lanep.productId;
      this.updateProductForm.srcProductName=clo.lanep.productName;
      this.updateProductForm.pic="";
      this.updateProductForm.productId="";
      this.updateProductForm.productName="";
      // this.updateProductForm.warnCap=0;
      // this.updateProductForm.capacity=0;
    },
    //编辑售货机获得信息
    getVendingView(){
      var _this = this;
      const listQuery = {
        logid:_this.$route.query.logid
      }
      getVendingViewByLogid(listQuery).then(response => {
           if(response.zhead.reTCode==="0000"){
            _this.addForm=response.zbody.datas
            debugger
            _this.tabIndex = parseInt(response.zbody.datas.cabinets[response.zbody.datas.cabinets.length - 1].viewName)
            _this.palychek=response.zbody.datas.payType.split(',')
            console.log("编辑获得信息",response.zbody.datas)
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
    //新增tab
    addTab(targetName) {
        let _this =this;
        let newTabName = ++this.tabIndex + '';
        newTabName++
        this.newTabName = newTabName;
        this.addForm.cabinets.push({
          cabinetName: '挂载柜',//+newTabName,
          viewName: parseInt(newTabName),
          //副柜信息
          hangType:"1", //外挂类型 1:是 2:否
          pointCode:"", // 串口号
          isShowTemplate:true,
          factoryId:"", // 厂家编号
          cabinetType:"", // 货柜类型 01:商店机 02:弹簧机 03:格子机
          deviceId:"", // 机型编码
          lanes:[ // 货道
          {
            laneId:1, // 货道编号(从左到右、从上到下)
            row:1, // 行数
            col:1,  // 列数
            arrange:1,  // 排数
            lanep: {  // 货道商品
              laneSId:"",  // 货道开始编号(一个商品占多个货道)
              laneEId:"", // 货道结束编号(如果只有一个商品则只有开始货道编号)
              productId:"", //商品编号
              productPic:"",  // 商品图片
              salePrice:"", // 售卖价格
              capacity:"",  // 存放容量
              warnCap:"",  // 缺货阈值
              laneSate:"1"  // 货道状态 1:正常 2:停用
            }
          }
          ]

        });
        debugger
        this.editableTabsValue = newTabName;
    },
    //减少tab
    removeTab(targetName) {
        //let tabs = this.editableTabs;
        let tabs = this.addForm.cabinets
        let activeName = this.editableTabsValue;
        if (activeName === targetName) {
          tabs.forEach((tab, index) => {
            if (tab.viewName === targetName) {
              let nextTab = tabs[index + 1] || tabs[index - 1];
              if (nextTab) {
                activeName = nextTab.viewName;
              }
            }
          });
        }
        this.editableTabsValue = activeName;
        this.addForm.cabinets = tabs.filter(tab => tab.viewName !== targetName);
    },
    //切换tab
    tab_tab(e){
      //this.newTabName= e.name
    },
    handleCheckedCitiesChange(value) {
        this.addForm.payType = value.join(',')
    },
    saveLanes(){
      var _this =this;
      //副柜的数据暂存 this.cabinetsOb

      this.innerVisible = false;
      this.main_innerVisible = false;

      // if(_this.cabinets_name=="0"){//当前配置的是主柜货道
      //     this.addForm.lanes = this.checkedCities
       
      // }else{//当前配置的是副柜货道
      //   this.addForm.cabinets.forEach(function(item){
      //      if(_this.cabinets_name==item.name){
      //         item.lanes=_this.checkedCities
      //      }
      //   })
      // }
      // this.cabinets_name="0"//清空副柜的唯一id
      // this.checkedCities = [];//清空货道
    },
    //检查售货机货道最大容量和阈值
    checkLanes(lanes){
      for(var i=0;i<lanes.length;i++){
            var cols=lanes[i].cols
            for(var j=0;j<cols.length;j++){
                var lanep=cols[j].lanep;
                var capacity=lanep.capacity;
                var warnCap=lanep.warnCap;
                if(lanep.productId!=null&&lanep.productId!=undefined&&lanep.productId!=''){
                  if(capacity==null||capacity==undefined||capacity==''){
                    this.$notify({
                      title: '失败',
                      message: '最大容量不能为空',
                      type: 'error'
                    });
                    return false;
                  }
                  if(!Number.isInteger(Number(capacity))){
                    this.$notify({
                      title: '失败',
                      message: '最大容量必须是整数',
                      type: 'error'
                    });
                    return false;
                  }
                  if(Number(capacity)<=0){
                    this.$notify({
                      title: '失败',
                      message: '最大容量不能小于等于0',
                      type: 'error'
                    });
                    return false;
                  }

                  if(warnCap==null||warnCap==undefined||warnCap==''){
                    this.$notify({
                      title: '失败',
                      message: '阈值不能为空',
                      type: 'error'
                    });
                    return false;
                  }
                  if(!Number.isInteger(Number(warnCap))){
                    this.$notify({
                      title: '失败',
                      message: '阈值必须是整数',
                      type: 'error'
                    });
                    return false;
                  }
                  if(Number(warnCap)<=0){
                    this.$notify({
                      title: '失败',
                      message: '阈值不能小于等于0',
                      type: 'error'
                    });
                    return false;
                  }

                }
          }
        }
      return true;       
    },
    addSubmitForm(formName) {//提交新增窗口
        var _this=this;
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if(!_this.checkLanes(_this.addForm.lanes)){
              return;
            }
            var cabinets=_this.addForm.cabinets;
            if(cabinets!=null&&cabinets!=undefined&&cabinets.length!=0){
              for(var i=0;i<cabinets.length;i++){
                if(!_this.checkLanes(cabinets[i].lanes)){
                  return;
                }
              }
            }
            
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
               NProgress.start();
                      let para = Object.assign({}, this.addForm);
                      //para.lanes = this.checkedCities
                      editMachine(para).then((res) => {
                            if(res.zhead.reTCode=="0000"){
                            NProgress.done();
                            this.$notify({
                              title: '成功',
                              message: res.zhead.retMsg,
                              type: 'success'
                            });
                            this.$refs['addForm'].resetFields();
                            this.$router.push(
                                  {path:'/equipment/machine/index'}
                            );
                            }else{
                              this.$notify({
                                title: '失败',
                                message: res.zhead.retMsg,
                                type: 'error'
                              });
                          }
                      });
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
    getCabinetType(row,factoryId){
      var _this = this;
      const listQuery = {
        factoryId:factoryId
      }
      selectCabinetTypeByFactoryId(listQuery).then(response => {
           if(response.zhead.reTCode==="0000"){
            _this.CabinetOptions=response.zbody.datas
            if(row==0){
                _this.addForm.cabinetType="";
                _this.addForm.deviceId="";
            }else{
                _this.addForm.cabinets.forEach(function(item){
                  if(item.viewName ==row.viewName ){
                    item.cabinetType = ''
                    item.deviceId = ''
                  }
                })
            }
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
    getDeviceByCabinetType(row,cabinetType){
      var _this = this;
      const listQuery = {
        cabinetType:cabinetType
      }
      selectDeviceByCabinetType(listQuery).then(response => {
           if(response.zhead.reTCode==="0000"){
            _this.modelOptions=response.zbody.datas
            if(row==0){
                _this.addForm.deviceId="";
            }else{
                _this.addForm.cabinets.forEach(function(item){
                  if(item.viewName ==row.viewName ){
                    item.deviceId = ''
                  }
                })
            }

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
    getMachineSelectList(type) {
      var _this = this;
      const listQuery = {
        dictType:type,
      }
      getMachineSelect(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
         
          NProgress.done();
          /*
          字典类型,厂商：sys_factory；货柜类型sys_cabinet_type；网络类型：sys_net_work；售卖状态：sys_sell_state
          */ 

          if(type==="sys_factory")
          {
            _this.ManufactorOptions= response.zbody.datas.rows;//厂家列表---下拉
             console.log("厂家列表：：",_this.ManufactorOptions)
          }
          else if(type==="sys_cabinet_type")
          {
             _this.CabinetOptions= response.zbody.datas.rows;//货柜类型---下拉
             console.log("货柜类型：：",_this.CabinetOptions)
          }
          else if(type==="sys_net_work")
          {
              _this.networkOptions= response.zbody.datas.rows;//网络类型---下拉
              console.log("网络类型：：",_this.networkOptions)
          }
          else if(type==="sys_sell_state")
          {
             _this.stateOptions= response.zbody.datas.rows;//售卖状态--下拉
             console.log("售卖状态：：",_this.stateOptions)
          }
         }else{
             this.$notify({
              title: '失败',
              message: res.zhead.retMsg,
              type: 'error'
            });
         }
      })
    },
    //机型查询接口
    getModel(){
      var _this = this;
      const listQuery = {
      }
      getVendingModel(listQuery).then(response => {
           if(response.zhead.reTCode==="0000"){
            _this.modelOptions=response.zbody.datas.rows
            console.log("机型：：",response.zbody.datas.rows)
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
    changeDevice(cid,deviceId){
      this.disabled = false
      this.getvendingModel(cid,deviceId)
    },
    //获取一个机型里的货道查询接口
    getvendingModel(row,deviceCode){
      this.cabinets_name = row=="0"? "0" : row.viewName//每个副柜的唯一id 0是主柜
      this.cabinetsObj = Object.assign({}, row);
      var _this = this;
      this.innerVisible = true
      const listQuery = {
        deviceCode:deviceCode
      }
      vendingModelList(listQuery).then(response => {
           if(response.zhead.reTCode==="0000"){
            //_this.modelOptions=response.zbody.datas.rows
            _this.vendingModeData = response.zbody.datas

             if (row == 0) {
              _this.addForm.lanes = response.zbody.datas.rows
            }else{
              _this.addForm.cabinets.forEach(function(item){
                  if(item.viewName ==row.viewName ){
                    item.lanes = response.zbody.datas.rows
                  }
              })

               _this.addForm.cabinets.lanes = response.zbody.datas
            }
            console.log("一个机型里的货道：：",response.zbody.datas)
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
    querySearchAsync(queryString, cb) {//输入搜索点位
        var _this = this;
        const listQuery = {
          name:_this.companyName
        }
        getPositionList(listQuery).then(response => {
          _this.restaurants = response.zbody.datas.rows
          console.log(_this.restaurants)
          var restaurants = this.restaurants;
          var results = queryString ? restaurants.filter(this.createStateFilter(queryString)) : restaurants;
          cb(results);
        })
    },
    createStateFilter(queryString) {
        return (state) => {
          return (state.name.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
        };
    },
    handleSelect(item) {
        this.addForm.pointId = item.pointId
        this.addForm.pointName = item.name
        console.log(item.pointId);
    },
    //查询商品列表
    getCommodityList(page){
      this.currentPage = page
      var _this = this;
      const listQuery = {
        typeId:'',
        name:_this.goodsWord,
        productCode:_this.productCode,
        pageSize:40,//_this.pagesize,
        pageNum:page,
        orderByColumn:'createtime',
        isAsc:'desc'
      }
      getCommodity(listQuery).then(response => {
           if(response.zhead.reTCode==="0000"){
            _this.cocmodityList=response.zbody.datas.rows
            _this.p_total = response.zbody.datas.total
            _this.p_total_c = response.zbody.datas.total
            console.log("商品列表：：",response.zbody.datas)
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
    handleSizeChange: function (size) { 
       this.pagesize = size; 
    }, 
    handleCurrentChange: function(currentPage){ 
     this.currentPage = currentPage;
     this.selectTemplateList(currentPage)
     },
    handleCurrentChange_p: function(currentPage){ 
     this.currentPage = currentPage;
     this.getCommodityList(currentPage)
    },
    //选择模板弹出
    selectTemplate(row){
      if(row.deviceId==""){
         var deviceId = row.device==undefined?this.addForm.deviceId:row.device
       }else{
         var deviceId = row.deviceId==undefined?this.addForm.deviceId:row.deviceId
       }
     
      this.templateVisible = true
      this.selectTemplateList("1",deviceId)
    },
    //选择模板
    selectTemplateOn(index,rows){
      var _this = this;
      const listQuery = {
        logid:rows[index].logid
      }
      vendingPconfigGet(listQuery).then(response => {
           if(response.zhead.reTCode==="0000"){
             this.$confirm('确认选择此模版吗？', '提示', {}).then(() => {
                if(_this.newTabName!="1"){
                  this.addForm.cabinets.forEach(function(item,index){
                       if(_this.newTabName == item.viewName){
                         // item.lanes = response.zbody.datas.lanes
                          for(var i = 0; i<item.lanes.length; i++){
                            for(var j=0; j<item.lanes[i].cols.length; j++){
                               item.lanes[i].cols[j].lanep.productName=response.zbody.datas.lanes[i].cols[j].lanep.productName;
                               item.lanes[i].cols[j].lanep.productPic=response.zbody.datas.lanes[i].cols[j].lanep.productPic;
                               item.lanes[i].cols[j].lanep.productId=response.zbody.datas.lanes[i].cols[j].lanep.productId;
                               item.lanes[i].cols[j].lanep.pic=response.zbody.datas.lanes[i].cols[j].lanep.pic;
                               item.lanes[i].cols[j].lanep.warnCap=response.zbody.datas.lanes[i].cols[j].lanep.warnCap;
                               item.lanes[i].cols[j].lanep.salePrice=response.zbody.datas.lanes[i].cols[j].lanep.salePrice;
                               item.lanes[i].cols[j].lanep.capacity=response.zbody.datas.lanes[i].cols[j].lanep.capacity;
                            }
                          }
                       }
                  })
                }else{
                  var lanes = _this.addForm.lanes;
                  for(var i = 0; i<lanes.length; i++){
                            for(var j=0; j<lanes[i].cols.length; j++){
                               lanes[i].cols[j].lanep.productName=response.zbody.datas.lanes[i].cols[j].lanep.productName;
                               lanes[i].cols[j].lanep.productPic=response.zbody.datas.lanes[i].cols[j].lanep.productPic;
                               lanes[i].cols[j].lanep.productId=response.zbody.datas.lanes[i].cols[j].lanep.productId;
                               lanes[i].cols[j].lanep.pic=response.zbody.datas.lanes[i].cols[j].lanep.pic;
                               lanes[i].cols[j].lanep.warnCap=response.zbody.datas.lanes[i].cols[j].lanep.warnCap;
                               lanes[i].cols[j].lanep.salePrice=response.zbody.datas.lanes[i].cols[j].lanep.salePrice;
                               lanes[i].cols[j].lanep.capacity=response.zbody.datas.lanes[i].cols[j].lanep.capacity;
                            }
                  }



                 //  = response.zbody.datas.lanes


                }
                _this.templateVisible = false
                // _this.addForm.lanes = response.zbody.datas.lanes
                // _this.templateVisible = false
               
                // NProgress.done();
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
    selectTemplateList(page,deviceId) {
      var _this = this;
      const listQuery = {
        deviceId:deviceId,
        name:_this.keword,
        pageSize:_this.pagesize,
        pageNum:page,
        orderByColumn:"createtime",
        isAsc:"desc"
      }
      getVendingPconfigList(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
          _this.templatetotal = response.zbody.datas.total;  
          _this.templateData= response.zbody.datas.rows
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
    goodsSearch(){
      this.getCommodityList("1")
    },
    //生成模板
    addTemplate(type,row){
      this.Template = row
      this.templateDialogVisible = true
    },
    TemplateOk(){
      const _this = this;
      this.$confirm('确认提交吗？', '提示', {}).then(() => {
                      NProgress.start();
                      let para = {
                        cabinetType:_this.Template.cabinetType,
                        deviceId:_this.Template.deviceId,
                        factoryId:_this.Template.factoryId,
                        name:_this.TemplateName,
                        lanes:_this.Template.lanes
                      }
                      addVendingPconfig(para).then((res) => {
                            if(res.zhead.reTCode=="0000"){
                            NProgress.done();
                            this.$notify({
                              title: '成功',
                              message: res.zhead.retMsg,
                              type: 'success'
                            });
                            _this.templateDialogVisible = false
                            }else{
                              this.$notify({
                                title: '失败',
                                message: res.zhead.retMsg,
                                type: 'error'
                              });
                          }
                      });
      });
    }
  },

  mounted () {
        this.getVendingView()
        this.getMachineSelectList("sys_factory")
        this.getMachineSelectList("sys_net_work")
        this.getMachineSelectList("sys_sell_state")
        this.getCommodityList("1")

        this.getCabinetType()
     
  },
  components: {
    cabinet
  }
}
</script>
<style scoped>
.bntBox{ margin-top:50px;}
.pageBox{background: #fff; margin-top: 30px;}
.layerbottom{text-align: center; overflow: hidden; padding:30px 0;}
.input-left{width:70px;float:left;}
.input-right{width:70px;float:right;}
.layerBox{padding:0 30px; }
  .top-20{ margin-top: 20px; }
  .inputStyle{width:215px;}
  .el-autocomplete{width:100%;}
  .div-warp{margin: 20px;}
  .dialog-footer{ padding-left:120px;  }
  .searchBox{ padding: 0; }
  .searchBox .inputStyle{width: 200px; display: inline-block;float:left; margin-right: 15px; }
  .el-dialog__header{border-bottom: 1px solid #000}
.layerbottom{text-align: center; overflow: hidden; padding:30px 0; }

.bottom1 {line-height: 12px;}

.bottom1 .titles1{font-size:12px;color:#333;}

.productPic{height: 140px;}

.productCard{margin: 10px}

.productDiv{text-align: center;width: 400px;margin: 10px}

.price1 {
    font-size: 13px;
    color: #999; margin-top: 5px;
  }
  .price1 i{ color:#F56C6C; font-size:20px; }
</style>