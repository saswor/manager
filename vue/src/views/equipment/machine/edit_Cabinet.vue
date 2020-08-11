<template>
 <div class="div-warp">
      <el-card class="box-card top-20">
        <div slot="header" class="clearfix">
          <span>编辑挂载柜</span>
        </div>
        <el-tabs v-model="editableTabsValue" type="card" closable @tab-remove="removeTab" >
          <el-tab-pane
            :key="item.name"
            v-for="(item, index) in editableTabs"
            :label="item.title"
            :name="item.name"
          >
            <!-- {{item.content}} -->
            <cabinet></cabinet>
          </el-tab-pane>
        </el-tabs>

    
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
export default {
  data() {
        return {
          editableTabsValue: '1',
          editableTabs: [{
            title: '挂载柜1',
            name: '1',
            content: ''
          }],
          tabIndex: 1,
          input10:"",
          options:[],
          value:"",
          innerVisible:false,
          input:'',
          checkAll: false,
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
          }
        }
  },
  watch: {
   
  },
  methods: {
    handleTabsEdit(targetName, action) {
        if (action === 'add') {
          let newTabName = ++this.tabIndex + '';
          this.editableTabs.push({
            title: '挂载贵'+newTabName,
            name: newTabName,
            content: '22'
          });
          this.editableTabsValue = newTabName;
        }
        if (action === 'remove') {
          let tabs = this.editableTabs;
          let activeName = this.editableTabsValue;
          if (activeName === targetName) {
            tabs.forEach((tab, index) => {
              if (tab.name === targetName) {
                let nextTab = tabs[index + 1] || tabs[index - 1];
                if (nextTab) {
                  activeName = nextTab.name;
                }
              }
            });
          }
          
          this.editableTabsValue = activeName;
          this.editableTabs = tabs.filter(tab => tab.name !== targetName);
        }
    },

    addTab(targetName) {
        let newTabName = ++this.tabIndex + '';
        this.editableTabs.push({
          title: '挂载柜'+newTabName,
          name: newTabName,
          content: 'New Tab content'
        });
        this.editableTabsValue = newTabName;
    },
    removeTab(targetName) {
        let tabs = this.editableTabs;
        let activeName = this.editableTabsValue;
        if (activeName === targetName) {
          tabs.forEach((tab, index) => {
            if (tab.name === targetName) {
              let nextTab = tabs[index + 1] || tabs[index - 1];
              if (nextTab) {
                activeName = nextTab.name;
              }
            }
          });
        }
        
        this.editableTabsValue = activeName;
        this.editableTabs = tabs.filter(tab => tab.name !== targetName);
    },
    addSubmitForm(formName) {//提交新增窗口
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


    handleSelect(item) {
        console.log(item);
    }
  },

  mounted () {
   
  },
  components: {
    cabinet
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
  .div-warp{margin: 20px;}
  .dialog-footer{ padding-left:120px;  }
  .searchBox{ padding: 0; }
  .searchBox strong{ display: inline-block;float: left; line-height: 35px; font-size:14px; font-weight: normal; }
  .searchBox .inputStyle{width: 200px; display: inline-block;float:left; margin-right: 15px; }
  .el-dialog__header{border-bottom: 1px solid #000}
</style>