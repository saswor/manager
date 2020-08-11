<template>
 <div class="div-warp">
    <div class="block-warp">
            <el-dialog title="选择关联线路"  width="60%" :visible.sync="dialogAddTableVisible">
              <div class="LayersearchBox">
                    <el-select v-model="value" style="float:left; margin-right:10px;" size="mini" placeholder="请选择区域">
                      <el-option
                        v-for="item in options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                      </el-option>
                    </el-select>
                    <el-input v-model="input" class="inputStyle" size="mini" placeholder="线路编号/名称"></el-input>
                    <el-button type="success" @click.native.prevent="search" size="mini">查询</el-button>
              </div>
               <el-table
                :data="tableData"
                :header-cell-style="{background:'#efefef'}"
                style="width: 100%; margin-top:20px;">
                <el-table-column
                  type="selection"
                  width="50">
                </el-table-column>
                <el-table-column
                  prop="date"
                  label="线路编号"
                  width="180">
                </el-table-column>
                <el-table-column
                  prop="name"
                  label="线路名称"
                  width="180">
                </el-table-column>
                <el-table-column
                  prop="address"
                  label="区域名称">
                </el-table-column>
                <el-table-column
                  prop="date"
                  label="创建时间">
                </el-table-column>
                <el-table-column
                  label="操作">
                   <template  scope="scope">
                    <div>
                      <el-button type="primary" size="mini"  @click.native.prevent="deleteRow(scope.$index, tableData)">关联</el-button>
                    </div>
                  </template>
                </el-table-column>
              </el-table>
              <el-pagination
                background
                layout="prev, pager, next"
                :total="1000" style="margin-top:20px;">
              </el-pagination>
            </el-dialog>
            <div class="screen-box">
                <div class="timeDate"><strong>补货时间：</strong></div>
                <div class="timeDate">
                    <el-button  size="mini" style="float:left;" disabled>星期一</el-button>
                    <el-time-picker size="mini" style="width:120px;float:left; margin-right:10px;"
                      v-model="value2"
                      :picker-options="{
                       
                      }"
                      placeholder="时间点">
                    </el-time-picker>
                    <el-button size="mini" style="float:left;" disabled>星期二</el-button> 
                    <el-time-picker size="mini" style="width:120px;float:left;margin-right:10px;"
                      v-model="value2"
                      :picker-options="{
                       
                      }"
                      placeholder="时间点">
                    </el-time-picker>
                    <el-button size="mini" style="float:left;" disabled>星期三</el-button>  
                    <el-time-picker size="mini" style="width:120px;float:left;margin-right:10px;"
                      v-model="value2"
                      :picker-options="{
                       
                      }"
                      placeholder="时间点">
                    </el-time-picker>
                    <el-button size="mini" style="float:left;" disabled>星期四</el-button>
                    <el-time-picker size="mini" style="width:120px;float:left;margin-right:10px;"
                      v-model="value2"
                      :picker-options="{
                       
                      }"
                      placeholder="时间点">
                    </el-time-picker>
                   
                </div>
                <div class="timeDate">
                    <el-button size="mini" style="float:left;" disabled>星期五</el-button>
                    <el-time-picker size="mini" style="width:120px;float:left;margin-right:10px;"
                      v-model="value2"
                      :picker-options="{
                       
                      }"
                      placeholder="时间点">
                    </el-time-picker>
                    <el-button size="mini" style="float:left;" disabled>星期六</el-button>
                    <el-time-picker size="mini" style="width:120px;float:left;margin-right:10px;"
                      v-model="value2"
                      :picker-options="{
                       
                      }"
                      placeholder="时间点">
                    </el-time-picker>
                    <el-button size="mini" style="float:left;" disabled>星期日</el-button>
                    <el-time-picker size="mini" style="width:120px;float:left;"
                      v-model="value2"
                      :picker-options="{
                       
                      }"
                      placeholder="时间点">
                    </el-time-picker>
                </div>
                <div class="searchBox">
                    <strong>出货仓库：</strong>
                    <el-select v-model="value" style="float:left; margin-right: 30px;" size="medium" placeholder="请选择">
                      <el-option
                        v-for="item in options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                      </el-option>
                    </el-select>
                    <strong>补货人员：</strong>
                    <el-select v-model="value" style="float:left; margin-right: 30px;" size="medium" placeholder="请选择">
                      <el-option
                        v-for="item in options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                      </el-option>
                    </el-select>
                    <el-button type="success" @click.native.prevent="search" size="medium">查询</el-button>
                </div>
                <div class="searchBox">
                    <strong>补货单生成时间：</strong>
                    <strong>补货时间早</strong>
                    <el-input v-model="input" size="mini" placeholder="请输入内容"  style="width:100px; float:left;  margin:0 5px; margin-top:2px;"></el-input>
                    <strong>分钟</strong>
                    <el-button type="primary" size="mini" style="float:right;margin-top:2px;" @click="dialogAddTableVisible = true">选择关联线路</el-button>
                </div>
            </div>
            <wTable :data="tableData" ref="multipleTable" :header="tableHeader"  :option="tableOption">
              <el-table-column slot="fixed"
                fixed="right"
                label="操作"
                width="50">
                  <template  scope="scope">
                    <div>
                      <el-button type="danger"  size="mini" icon="el-icon-delete" circle @click.native.prevent="deleteRow(scope.$index, tableData)"></el-button>
                    </div>
                  </template>
              </el-table-column>
            </wTable>
          
            <div class="pageBox">
              <el-pagination
                background
                layout="prev, pager, next"
                :total="1000">
              </el-pagination>
            </div>
      </div>
  </div>
</template>
<script>
import echarts from 'echarts'
import wTable from '@/components/table/w-table.vue'
export default {
  data() {
        return {
          input:'',
          value2:'',
          value6: '',
          form:{},
          options: [{
            value: '选项1',
            label: '黄金糕'
            }, {
              value: '选项2',
              label: '双皮奶'
            }, {
              value: '选项3',
              label: '蚵仔煎'
            }, {
              value: '选项4',
              label: '龙须面'
            }, {
              value: '选项5',
              label: '北京烤鸭'
            }],
          value: '',
          radio:'已认证',
          formLabelWidth: '120px',
          dialogAddTableVisible:false,//控制新增弹出显示隐藏
          dialogEditTableVisible:false,//控制编辑弹出显示隐藏
          input10:'',
          tableOption:{
            border: true
          },
          tableHeader: [
              {
                type:"index"
              },
              {
                prop: 'name',
                label: '线路名称 ',
                width: '78',
                sortable: true,
                sortMethod: this.handleNameSort
              },
              {
                prop: 'name',
                label: '区域名称',
                 minWidth: '200'
             
              }, {
                prop: 'province',
                label: '所属公司',
                minWidth: '120'
              }
          ],
          tableData: [],
          filterText: '',
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
    filterText(val) {
      this.$refs.tree2.filter(val)
    }
  },
  methods: {
    importBnt(){
      this.dialogFormVisible= true
    },
    search(){//查询
      console.log('---搜索----')
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
    }
  },

  mounted () {
    
  },
  components: {
    wTable
  }
}
</script>
<style scoped>
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