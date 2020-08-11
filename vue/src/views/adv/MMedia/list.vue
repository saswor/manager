<template>
 <div class="div-warp">
      <!-- 新增 -->
      <el-dialog
        title="新增素材"
        @close="cleanAddData"
        :close-on-click-modal="false"
        v-if="dialogVisible"
        :visible.sync="dialogVisible"
        width="600px"
        >
                <el-form :model="addForm" :rules="Rules"  class="formWidth" ref="addForm" @submit.prevent="onSubmit">
                <el-form-item label="素材名称" prop="mediaName" size="small" :label-width="formLabelWidth">
                  <el-input v-model="addForm.mediaName" auto-complete="off" autofocus="true" ref="addName"></el-input>
                </el-form-item>
                <el-form-item label="类型" prop="mediaType" size="small" :label-width="formLabelWidth">
                    <el-select v-model="addForm.mediaType" placeholder="请选择" @change="changeAddMediaType">
                      <el-option
                        v-for="item in mediaTypeList"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                      </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="上传文件" prop="manager" size="small" :label-width="formLabelWidth">
                  <el-upload
                  ref="addUpload"
                  v-loading="addLoadFlag"
                  element-loading-text="上传中"
                  class="upload-demo"
                  action="/system/common/adUpload"
                  :on-preview="handlePreview"
                  :limit="1"
                  :accept="addType"
                  :on-remove="handleAddRemove"
                  :file-list="fileList2"
                  :on-exceed="handleExceed"
                  :on-success="handleUpsuccess"
                  :before-upload="forbiddenAddSubmit"
                  :on-error="enableAddSubmit"
                  list-type="picture">
                  <el-button size="small" type="primary" @click="checkUpload(addForm)" v-if="addForm.mediaType!=''&&addForm.mediaType!=null&&addForm.mediaType!=undefined">点击上传</el-button>
                  <div slot="tip" class="el-upload__tip">单个文件上传，且不超过500m</div>
                </el-upload>
                </el-form-item>
              </el-form>
              <div slot="footer" class="dialog-footer">
                <el-button @click="addCancel">取 消</el-button>
                <el-button type="primary" @click="addSubmitForm('addForm')" :disabled="addFlag">提交</el-button>
              </div>
      </el-dialog>
      <!-- 编辑 -->
      <el-dialog
        title="编辑素材"
        v-if="editdialogVisible"
        :visible.sync="editdialogVisible"
        @close="cleanEditData"
        :close-on-click-modal="false"
        width="600px"
        >
                <el-form :model="editForm" :rules="Rules" class="formWidth" ref="editForm" @submit.prevent="onSubmit">
                <el-form-item label="素材名称" prop="mediaName" size="small" :label-width="formLabelWidth">
                  <el-input v-model="editForm.mediaName" auto-complete="off"  ref="editName"></el-input>
                </el-form-item>
                <el-form-item label="类型" prop="mediaType" size="small" :label-width="formLabelWidth">
                    <el-select v-model="editForm.mediaType" placeholder="请选择" @change="changeEditMediaType">
                      <el-option
                        v-for="item in mediaTypeList"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                      </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="上传文件" prop="manager" size="small" :label-width="formLabelWidth">
                  <el-upload
                  ref="editUpload"
                  v-loading="editLoadFlag"
                  element-loading-text="上传中"
                  class="upload-demo"
                  action="/system/common/adUpload"
                  :on-preview="handlePreview"
                  :limit="1"
                  :accept="editType"
                  :on-remove="handleEditRemove"
                  :file-list="fileList2"
                  :on-exceed="handleExceed"
                  :on-success="edithandleUpsuccess"
                  :before-upload="forbiddenEditSubmit"
                  :on-error="enableEditSubmit"
                  list-type="picture">
                  <el-button size="small" type="primary" @click="checkUpload(editForm)" v-if="editForm.mediaType!=''&&editForm.mediaType!=null&&editForm.mediaType!=undefined">点击上传</el-button>
                  <div slot="tip" class="el-upload__tip">单个文件上传，且不超过500m</div>
                </el-upload>
                </el-form-item>
              </el-form>
              <div slot="footer" class="dialog-footer">
                <el-button @click="editCancel">取 消</el-button>
                <el-button type="primary" @click="editSubmitForm('editForm')" :disabled="editFlag">提交</el-button>
              </div>
      </el-dialog>
      <div class="search-warp">
        <el-row :gutter="20" >
            <el-col :span="4">
              <div class="grid-content bg-purple">
                  <el-input
                  placeholder="素材名"
                  size="small"
                  v-model="keyword"
                  clearable>
                </el-input>

              </div>
            </el-col>
            <el-col :span="4">
              <div class="grid-content bg-purple">
                  <el-select v-model="typeValue" clearable size="small" placeholder="类型">
                    <el-option
                      v-for="item in mediaTypeList"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
              </div>
            </el-col>
            <el-col :span="16">
                <div class="grid-content bg-purple"> 
                  <el-button type="success" size="small" @click.native.prevent="search" v-permission="['sys:advert:material:select']">查询</el-button>
                  <el-button type="primary" size="small" @click="handleAdd" v-permission="['sys:advert:material:add']">新增</el-button>
                  <el-button type="danger" size="small" @click="deleteRows" v-permission="['sys:advert:material:del']">删除</el-button>
                </div>
            </el-col>
        
        </el-row>
      </div>
      <div class="block-warp">
        <el-table
          :data="tableData"
          @selection-change="handleSelectionChange"
          border
          style="width: 100%">
           <el-table-column
            type="selection"
            width="55">
          </el-table-column>
          <el-table-column
            prop="mediaName"
            label="素材名"
            width="180">
          </el-table-column>
          <el-table-column
            prop="mediaTypeName"
            label="类型"
            width="180">
          </el-table-column>
          <el-table-column
            prop="mediaFileSize"
            label="大小">
          </el-table-column>

          <el-table-column
            prop="mediaUrl"
            label="文件预览">
             <template scope="scope">
               <div class="PicBox">
                 <img width="100px" :src="httpPrefix+scope.row.mediaUrl+'?date='+new Date()" alt="">
               </div>
             </template>
          </el-table-column>
          <el-table-column
            prop="createTime"
            label="创建时间">
          </el-table-column>
          <el-table-column 
            fixed="right"
            label="操作"
            width="100">
              <template  scope="scope">
                <div style="text-align:center;">
                  <el-button type="primary" size="mini" icon="el-icon-edit" circle @click="handleEdit(scope.$index, scope.row)" v-permission="['sys:advert:material:edit']"></el-button>
                  <el-button type="danger"  size="mini" icon="el-icon-delete" circle @click.native.prevent="deleteRow(scope.row)" v-permission="['sys:advert:material:del']"></el-button>
                </div>
              </template>
          </el-table-column>
        </el-table>
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
import { advertMaterialList,dictList,addAdvertMaterial,editAdvertMaterial,removeAdvertMaterial} from '@/api/adv'
import NProgress from 'nprogress'
import wTable from '@/components/table/w-table.vue'
import permission from '@/directive/permission/index.js'
export default {
  directives: { permission },
  data() {
        return {
          httpPrefix:"http://",
          fileList2: [],
          dialogVisible:false,
          editdialogVisible:false,
          addFlag:false,
          editFlag:false,
          addLoadFlag:false,
          editLoadFlag:false,
          keyword:'',
          typeValue:"",
          total:1,
          currentPage:1, 
          pagesize:10, 
          formLabelWidth: '120px',
          multipleSelection:"",
          options:[],
          addType:'',
          editType:'',
          mediaTypeList:[
              {
                value: '1',
                label: '视频'
              }
              ,
              {
                value: '2',
                label: '图片'
              }
              // ,
              // {
              //   value: '3',
              //   label: '文本'
              // }
          ],
          tableOption:{
            border: true
          },
          tableHeader: [
              {
                type:"selection",
                width:"55"
              },
              {
                prop: 'mediaName',
                label: '素材名 ',
                sortable: true,
                // sortMethod: this.handleNameSort
              },
              {
                prop: 'mediaType',
                label: '类型',
                 minWidth: '150'
             
              }, {
                prop: 'mediaSize',
                label: '文件大小',
                minWidth: '80'
              }, {
                prop: 'mediaPX',
                label: '文件预览',
                minWidth: '100'
              }, {
                prop: 'createTime',
                label: '创建时间',
              }
          ],
          tableData: [],
          filterText: '',
          editForm:{},
          addForm:{
            mediaName:"",
            mediaUrl:"",
            mediaType:"",
            mediaPX:'',
            mediaSize:""
          },
          Rules:{
              mediaName:[
                { required: true, message: '素材名称不能为空', trigger: 'change' },
                { max: 30, message: '素材名称最多30个字符', trigger: 'change' },
              ],
              mediaType: [
                 {  required: true, message:'类型不能为空',  trigger: 'change' }
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
    checkUpload(formName){
      console.log(this.addType)
      var type=formName.mediaType;
      if(type==''||type==undefined||type==null){
        this.$notify({
          title: '警告',
          message: "上传文件前请先选择类型",
          type: 'warning'
        });
        return false;
      }
    },
    changeAddMediaType(){
      if(this.addForm.mediaType=='1'){
        this.addType=".mp4,.3gp"
      }else if(this.addForm.mediaType=='2'){
        this.addType=".jpg,.png"
      }
    },
    changeEditMediaType(){
      if(this.editForm.mediaType=='1'){
        this.editType=".mp4,.3gp"
      }else if(this.editForm.mediaType=='2'){
        this.editType=".jpg,.png"
      }
    },
    addCancel(){
      this.dialogVisible = false;
      this.disableFlag=false;
      this.addLoadFlag=false
      this.$refs.addUpload.abort();
    },
    editCancel(){
      this.editdialogVisible = false;
      this.disableFlag=false;
      this.editLoadFlag=false
      this.$refs.editUpload.abort();
    },
    forbiddenAddSubmit(file){
      var type=this.addForm.mediaType;
      if(type==''||type==undefined||type==null){
        this.$notify({
          title: '警告',
          message: "上传文件前请先选择类型",
          type: 'warning'
        });
        return false;
      }
      //文件不能大于500m
      if(file.size>500*1024*1024){
        this.$notify({
          title: '失败',
          message: "上传文件不能大于500m",
          type: 'warning'
        });
        return false;
      }
      this.addFlag=true;
      this.addLoadFlag=true;
    },
    enableAddSubmit(){
      this.addFlag=false;
      this.addLoadFlag=false;
      this.$notify({
        title: '失败',
        message: "上传文件失败",
        type: 'error'
      });
    },
    forbiddenEditSubmit(file){
      var type=this.editForm.mediaType;
      if(type==''||type==undefined||type==null){
        this.$notify({
          title: '警告',
          message: "上传文件前请先选择类型",
          type: 'warning'
        });
        return false;
      }
       if(file.size>500*1024*1024){
        this.$notify({
          title: '失败',
          message: "上传文件不能大于500m",
          type: 'warning'
        });
        return false;
      }
      this.editFlag=true;
      this.editLoadFlag=true;
    },
    enableEditSubmit(){
      this.editFlag=false;
      this.editLoadFlag=false;
      this.$notify({
        title: '失败',
        message: "上传文件失败",
        type: 'error'
      });
    },
    handleAddRemove(file, fileList) {
        console.log(file, fileList);
        this.addForm.mediaUrl='';
        this.addForm.mediaSUrl='';
    },
    handleEditRemove(file, fileList) {
        console.log(file, fileList);
        this.editForm.mediaUrl='';
        this.editForm.mediaSUrl='';
    },
    handlePreview(file) {
        console.log(file);
    },
    handleUpsuccess(files, fileList){
      if(files.zhead.reTCode=='0000'){
        this.addForm.mediaUrl=files.zbody.datas.pic;
        this.addForm.mediaPX=files.zbody.datas.px;
        this.addForm.mediaSize=files.zbody.datas.size;
      }else{
        this.$notify({
          title: '失败',
          message: '文件上传失败',
          type: 'error'
        });
        this.$refs.addUpload.clearFiles();
      }      
      this.addFlag=false;
      this.addLoadFlag=false;
    },
    edithandleUpsuccess(files, fileList){
      if(files.zhead.reTCode=='0000'){
        this.editForm.mediaUrl=files.zbody.datas.pic;
        this.editForm.mediaPX=files.zbody.datas.px;
        this.editForm.mediaSize=files.zbody.datas.size;
      }else{
        this.$notify({
          title: '失败',
          message: '文件上传失败',
          type: 'error'
        });
        this.$refs.editUpload.clearFiles();
      }
      
      this.editFlag=false;
      this.editLoadFlag=false;
    },
    handleExceed(files, fileList) {
        this.$message.warning(`请先删除已存在图片再进行添加`);
    },
    handdetails(row){//查看
      this.dialogVisible = true
      this.editForm = Object.assign({}, row);
    },
    handleSizeChange: function (size) { 
       this.pagesize = size; 
    }, 
    handleCurrentChange: function(currentPage){ 
     this.currentPage = currentPage;
     this.getAdvertMaterialList(currentPage)
     },
    handleSelectionChange(rows) {//全选
    debugger
        const _this = this;
        _this.multipleSelection = ""
        if (rows) {
          rows.forEach(row => {
             _this.multipleSelection += row.materialId+ ",";
      
          });
        } 

    },
    search(){
      this.getAdvertMaterialList("1")
    },
    deleteRow(row) {//删除数据
      this.deleteMedia(row.materialId);
    },
    deleteRows() {//删除数据
      this.deleteMedia(this.multipleSelection);
    },
    deleteMedia(ids){
      const _this  = this;
      if(ids==undefined||ids==null||ids==''){
          this.$message({
            message: '请选择要删除的数据',
            type: 'warning'
          });
          return false
      }
      this.$confirm('确认提交吗？', '提示', {}).then(() => {
                  let para ={
                    ids:ids
                  }
                  removeAdvertMaterial(para).then((res) => {
                        if(res.zhead.reTCode=="0000"){
                        NProgress.done();
                        this.$notify({
                          title: '成功',
                          message: res.zhead.retMsg,
                          type: 'success'
                        });
                        _this.getAdvertMaterialList("1")
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
    this.addForm={
            mediaName:"",
            mediaUrl:"",
            mediaType:"",
            mediaPX:'',
            mediaSize:""
          };
      this.fileList2=[];
      this.dialogVisible= true
      this.$nextTick(function(){
        this.$refs.addName.focus()
      });
    },
    cleanAddData(){
      this.fileList2=[]
      this.addForm ={
            mediaName:"",
            mediaUrl:"",
            mediaType:"",
            mediaPX:'',
            mediaSize:""
          }
      this.addCancel();
    },
    cleanEditData(){
      this.fileList2=[]
      this.editForm ={
            mediaName:"",
            mediaUrl:"",
            mediaType:"",
            mediaPX:'',
            mediaSize:""
          }
      this.editCancel(); 
    },
    addSubmitForm(formName) {//提交新增窗口
        this.$refs[formName].validate((valid) => {
          if (valid) {
            var url=this.addForm.mediaUrl
            if(url==''||url==null||url==undefined){
              this.$notify({
                title: '提示',
                message: '上传文件不能为空',
                type: 'error'
              });
              return false;
            }
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
                      NProgress.start();
                      let para = Object.assign({}, this.addForm);
                      addAdvertMaterial(para).then((res) => {
                            if(res.zhead.reTCode=="0000"){
                                  this.dialogVisible= false;
                                  NProgress.done();
                                  this.$notify({
                                    title: '成功',
                                    message: res.zhead.retMsg,
                                    type: 'success'
                                  });
                                  this.$refs['addForm'].resetFields();
                                  this.getAdvertMaterialList("1")
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
    editSubmitForm(formName) {//提交新增窗口
        this.$refs[formName].validate((valid) => {
          if (valid) {
            var url=this.editForm.mediaUrl
            if(url==''||url==null||url==undefined){
              this.$notify({
                title: '提示',
                message: '上传文件不能为空',
                type: 'error'
              });
              return false;
            }
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
                      NProgress.start();
                      let para = Object.assign({}, this.editForm);
                      editAdvertMaterial(para).then((res) => {
                            if(res.zhead.reTCode=="0000"){
                                  this.editdialogVisible= false;
                                  NProgress.done();
                                  this.$notify({
                                    title: '成功',
                                    message: res.zhead.retMsg,
                                    type: 'success'
                                  });
                                  this.$refs['editForm'].resetFields();
                                  this.getAdvertMaterialList("1")
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
    handleEdit(index, row) {//显示编辑窗口
     this.fileList2=[]
     this.editdialogVisible= true
    //  this.fileList2.push({name:row.mediaName,url:this.ImgUrl+row.mediaSUrl})
     this.fileList2.push({name:row.mediaName,url:this.httpPrefix+row.mediaUrl+'?date='+new Date()})

     this.editForm = Object.assign({}, row);
     this.changeEditMediaType()
     this.$nextTick(function(){
        this.$refs.editName.focus()
      });
    },
    handleNameSort () {
      console.log('handleNameSort')
    },
    getAdvertMaterialList(page) {
      var _this = this;
      this.listLoading = true
      const listQuery = {
        mediaName:_this.keyword,
        mediaType:_this.typeValue,
        pageSize:_this.pagesize,
        pageNum:page,
        orderByColumn:'createtime',
        isAsc:'desc'//desc
      }
      advertMaterialList(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
            NProgress.done();
          _this.total = response.zbody.datas.total;  
          _this.tableData = response.zbody.datas.rows;
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
    getDictList() {
      var _this = this;
      const listQuery = {
        dictType:'sys_media_type'
      }
      dictList(listQuery).then(response => {
         if(response.zhead.reTCode==="0000"){
          NProgress.done();
          _this.options = response.zbody.datas.rows;
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
    this.getAdvertMaterialList("1")
    this.getDictList()
  },
  components: {
    wTable
  }
}
</script>
<style scoped>
  .PicBox{width:100px;height:100px; margin:0;}
  .PicBox img{width:100%}
  .div-warp{padding:0 20px;}
  .pageBox{background: #fff; padding:20px 0;}

</style>