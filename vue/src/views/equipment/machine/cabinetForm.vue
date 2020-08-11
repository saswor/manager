<template>
 <div>
        <el-form :model="addForm" :rules="Rules" class="formWidth"   ref="addForm" @submit.prevent="onSubmit">
          <el-form-item label="货柜名称" prop="area_id" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.area_id" ></el-input>
          </el-form-item>
          <el-form-item label="售后机名称" prop="area" size="small" :label-width="formLabelWidth">
            <el-input v-model="addForm.area" auto-complete="off"  ></el-input>
          </el-form-item>
          <el-form-item label="外挂" prop="name" size="small" :label-width="formLabelWidth">
            <el-select v-model="addForm.name" size="small" placeholder="请选择负责人姓名">
              <el-option label="张三" value="shanghai"></el-option>
              <el-option label="李四" value="beijing"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="串口号" prop="name" size="small" :label-width="formLabelWidth">
            <el-select v-model="addForm.name" size="small" placeholder="请选择负责人姓名">
              <el-option label="张三" value="shanghai"></el-option>
              <el-option label="李四" value="beijing"></el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="厂家" prop="name" size="small" :label-width="formLabelWidth">
            <el-select v-model="addForm.name" size="small" placeholder="请选择负责人姓名">
              <el-option label="张三" value="shanghai"></el-option>
              <el-option label="李四" value="beijing"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="货柜类型" prop="name" size="small" :label-width="formLabelWidth">
            <el-select v-model="addForm.name" size="small" placeholder="请选择负责人姓名">
              <el-option label="张三" value="shanghai"></el-option>
              <el-option label="李四" value="beijing"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="机型" prop="name" size="small" :label-width="formLabelWidth">
            <el-select v-model="addForm.name" size="small" placeholder="请选择负责人姓名">
              <el-option label="张三" value="shanghai"></el-option>
              <el-option label="李四" value="beijing"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div style="margin:40px 0 100px 10px;"><el-button type="danger">配置货道</el-button></div>
  </div>
</template>
<script>

export default {
  data() {
        return {
          editableTabsValue: '2',
          editableTabs: [{
            title: 'Tab 1',
            name: '1',
            content: 'Tab 1 content'
          }, {
            title: 'Tab 2',
            name: '2',
            content: 'Tab 2 content'
          }],
          tabIndex: 2,
          input10:"",
          options:[],
          value:"",
          innerVisible:false,
          input:'',
          checkAll: false,
          checkedCities: ['上海', '北京'],
          isIndeterminate: true,
          currentDate: new Date(),
          dialogFormVisible:false,
          radio10: '1',
          restaurants: [],
          state4: '',
          timeout:  null,
          formLabelWidth: '120px',
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
   
  },
  methods: {

  },

  mounted () {
   
  },
  components: {
  }
}
</script>
<style scoped>
.icon-size-circle{ font-size: 60px; color:red; margin-top: 50px;}
.pageBox{background: #fff; margin-top: 30px;}
.add-class{width:100%; height: 168px; text-align: center;}
.icon-size{ font-size: 60px; color:#efefef; margin-top: 50px; cursor: pointer;}
.icon-size:hover{color:#006CC1;}
.layerbottom{text-align: center; overflow: hidden; padding:30px 0;}
.input-left{width:70px;float:left;}
.input-right{width:70px;float:right;}
.block-top{background: #efefef; overflow: hidden;}
.block-top .delBnt{float: right;  margin-right:4px;}
.block-top .checkbox-left{float: left; margin-top: 4px; margin-left: 4px;}
.price {
    font-size: 13px;
    color: #999; margin-top: 10px;
  }
  .price i{ color:#F56C6C; font-size:20px; }
  .titles {
    font-size: 14px; margin-top: 10px;
    color: #333;
  }
  .bottom {
    line-height: 12px;
  }

  .button {
    padding: 0;
    float: right;
  }

  .image {
    width: 100%;
    display: block;
  }

  .clearfix:before,
  .clearfix:after {
      display: table;
      content: "";
  }
  
  .clearfix:after {
      clear: both
  }
.imgStyel{}
.imgStyel img{width: 100%; display: block;}
.layerBox{padding:0 30px; min-width: 1200px;}
  .bntBox{text-align: center; margin-top: 30px;}
  .top-20{ margin-top: 20px; }
  .inputStyle{width:215px;}
  .el-autocomplete{width:100%;}
  .div-warp{}
  .dialog-footer{ padding-left:120px;  }
  .searchBox{ padding: 0; }
  .searchBox strong{ display: inline-block;float: left; line-height: 35px; font-size:14px; font-weight: normal; }
  .searchBox .inputStyle{width: 200px; display: inline-block;float:left; margin-right: 15px; }
  .el-dialog__header{border-bottom: 1px solid #000}
</style>