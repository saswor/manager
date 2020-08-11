<template>
 <div class="div-warp">
   <el-dialog
    title="百度地图"
    :visible.sync="mapVisible"
    v-if="mapVisible"
    :fullscreen="true">
     <div class="dashboard-container">
      <mapView @mapConfirm="mapConfirm"></mapView>
    </div>
  </el-dialog>
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>新增点位</span>
        </div>
        <el-form :model="addForm" :rules="Rules" class="formWidth" ref="addForm" @submit.prevent="onSubmit" >
          <el-form-item label="点位编码" prop="code" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.code" auto-complete="off" placeholder="请输入点位编码，示例：423001"></el-input>
          </el-form-item>
          <el-form-item label="点位名称" prop="name" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.name" auto-complete="off" placeholder="请输入点位名称，示例：武大园路武大园二路"></el-input>
          </el-form-item>
          <el-form-item label="线路名称" prop="lineId" size="small" :label-width="formLabelWidth">
              <el-select v-model="addForm.lineId" auto-complete="off" placeholder="请选择">
                <el-option
                v-for="item in lineList"
                  :key="item.logid"
                  :label="item.name"
                  :value="item.logid">
                </el-option>
              </el-select>
          </el-form-item>
          <el-form-item label="所属商户"  prop="corpName" size="small"  :label-width="formLabelWidth">
            <el-autocomplete
              v-model="addForm.corpName"
              :fetch-suggestions="querySearchAsync"
              value-key="corpName"
              placeholder="输入搜索公司"
              @select="handleSelect"

            ></el-autocomplete>
          </el-form-item>
          <el-form-item label="所属行政区域" prop="country" size="small" :label-width="formLabelWidth">
              <el-select v-model="addForm.country" @change="getChildData('1')" placeholder="请选择国家">
                <el-option
                  v-for="item in cityList.countryData"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
                </el-option>
              </el-select>
              <el-select v-model="addForm.province"  @change="getChildData('2')" placeholder="请选择省份或州">
                <el-option
                  v-for="item in cityList.ProvinceData"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
                </el-option>
              </el-select>
              <el-select v-model="addForm.city"  @change="getChildData('3')" placeholder="请选择城市">
                <el-option
                  v-for="item in  cityList.cityData"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
                </el-option>
              </el-select>
              <el-select v-model="addForm.district" placeholder="请选择区">
                <el-option
                  v-for="item in  cityList.DistrictData"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
                </el-option>
              </el-select>

          </el-form-item>
          <el-form-item label="详细地址" prop="adderss" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.adderss" auto-complete="off" placeholder="请输入详细地址"></el-input>
          </el-form-item>
           <el-form-item label="经度" prop="longitude" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.longitude" style="width:360px" auto-complete="off" placeholder="东经为正，西经为负，0-180最多6位小数，示例：11.23"></el-input>
            <el-button @click="mapVisible=true">地图</el-button>
            <!-- <img class="baidu-map-class" :src="mapUrl"> -->
          </el-form-item>
          <el-form-item label="纬度" prop="latitude" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.latitude" auto-complete="off" placeholder="北纬为正数，南纬为负数，0-90最多6位小数，示例：-26.25"></el-input>
          </el-form-item>
          <el-form-item label="描述" prop="remark" size="small" :label-width="formLabelWidth">
            <el-input type="textarea" v-model="addForm.description"></el-input>
          </el-form-item>
        </el-form>
        <div class="dialog-footer">
          <el-button @click="ReturnLevel">取 消</el-button>
          <el-button type="primary" @click="addSubmitForm('addForm')">确 定</el-button>
        </div>
      </el-card>
  </div>
</template>
<script>
import {getCompany,addPosition,getVendingDistrict,getVendingLine,getChild} from '@/api/pointposition'
import {BaiduMap, BmControl, BmView, BmAutoComplete, BmLocalSearch, BmMarker} from 'vue-baidu-map'
import NProgress from 'nprogress'
import mapView from '@/components/map/mapView.vue'
export default {
  data() {
        var ValidationCompany = (rule, value, callback) => {
            if (!value) {
              return callback(new Error(' '));
            }else{
               callback();
            }
        };
        return {
          cityId:'0',
          corpName:'',
          lineList:[],//所有线路
          regionList:[],//区域列表
          restaurants: [],
          cityList:{//省市区
               countryData:[],//国家
               ProvinceData:[],//省份列表
               cityData:[],//市
               DistrictData:[]//区县
          },
          companyName: '',
          timeout:  null,
          formLabelWidth: '120px',
          mapVisible:false,
          addForm: {
              districtId:'',
              lineId:'',
              code:'',
              name:'',
              country:'',
              province:'',
              city:'',
              district:'',
              adderss:'',
              longitude:'',
              latitude:'',
              corpId:'',
              description:""
          },
          Rules:{
              code:[
                { required: true, message: '点位编码不能为空 ', trigger: 'blur' }
              ],
              name:[
                 { required: true, message: '点位名称不能为空 ', trigger: 'blur' }
              ],
              lineId:[
                 { required: true, message: '线路名称不能为空 ', trigger: 'blur' }
              ],
              corpName: [
                 {  required: true, message: '所属商户不能为空 ', validator: ValidationCompany, trigger: 'change' }
              ],
              country:[
                 { required: true, message: '所属行政区域不能为空 ', trigger: 'blur' }
              ],
              adderss:[
                 { required: true, message: '详细地址不能为空 ', trigger: 'blur' }
              ],
              longitude:[
                 { required: true, message: '经度不能为空 ', trigger: 'blur' }
              ],
              latitude:[
                 { required: true, message: '纬度不能为空 ', trigger: 'blur' }
              ],
          }
        }
  },
  watch: {
   
  },
  methods: {
      mapConfirm(center){
        this.addForm.longitude=center.lng
        this.addForm.latitude=center.lat
        this.mapVisible=false;
      },

    ReturnLevel(){
      this.$router.push(
              {path:'/pointposition/position'}
      );
    },
    addSubmitForm(formName) {//提交新增窗口
        this.$refs[formName].validate((valid) => {
          if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
                      NProgress.start();
                      let para = Object.assign({}, this.addForm);
                      addPosition(para).then((res) => {
                            if(res.zhead.reTCode=="0000"){
                            NProgress.done();
                            this.$notify({
                              title: '成功',
                              message: res.zhead.retMsg,
                              type: 'success'
                            });
                            this.$refs['addForm'].resetFields();
                            this.$router.push(
                                  {path:'/pointposition/position'}
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
          } else {
            console.log('error submit!!');
            return false;
          }
        });
    },
   
    resetForm(formName) {//重置表单
        this.$refs[formName].resetFields();
    },
    getData() {
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
    getLine(){//查所有线路
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
    },
    querySearchAsync(queryString, cb) {//输入搜索托管公司
       
       
        var _this = this;
        const listQuery = {
          corpName:_this.companyName
        }
        getCompany(listQuery).then(response => {
          _this.restaurants = response.zbody.datas
           var restaurants = this.restaurants;
           var results = queryString ? restaurants.filter(this.createStateFilter(queryString)) : restaurants;
          cb(results);
        })
    },
    createStateFilter(queryString) {
        return (state) => {
          return (state.corpName.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
        };
    },
    handleSelect(item) {
        this.addForm.corpId = item.corpId
        this.addForm.corpName = item.corpName
        console.log(item);
    },
    getChildData(type){//查询行政区域
        var _this = this;
        let cityId="0"
         if(type =="1"){
            cityId = _this.addForm.country
         }else if(type =="2"){
           cityId = _this.addForm.province
         }else if(type == "3"){
           cityId = _this.addForm.city
         }
        const listQuery = {
          parentId:cityId
        }
        getChild(listQuery).then(response => {
          if(response.zhead.reTCode==="0000"){
              if(type=="1"){
                  _this.cityList.ProvinceData = response.zbody.datas.rows;//省份列表
              }else if(type=="2"){
                 _this.cityList.cityData = response.zbody.datas.rows;//市
              }else if(type=="3"){
                 _this.cityList.DistrictData = response.zbody.datas.rows;//区县
              }else{
                 _this.cityList.countryData=response.zbody.datas.rows;//国家
              }
          console.log("行政区111",_this.cityList)
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
   this.getData()
   this.getLine()
   this.getChildData()
 
  },
  components: {
    mapView,
  }
}
</script>
<style scoped>
  .el-autocomplete{width:100%;}
  .div-warp{margin: 20px;}
  .dialog-footer{ padding-left:120px;  }
  .baidu-map-class{
    display: inline-block;
  }
</style>