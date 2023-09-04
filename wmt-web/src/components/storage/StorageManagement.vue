<template>
  <div>
    <div style="margin-bottom: 10px;text-align: center">
      <el-input v-model="name" placeholder="Please enter warehouse name" suffix-icon="el-icon-search" style="width:200px"
                @keyup.enter.native="loadPost"></el-input>
      <el-button type="primary" style="margin-left: 10px" @click="loadPost">Search</el-button>
      <el-button type="success" @click="resetParam">Reset</el-button>

      <el-button type="primary" style="margin-left: 10px" @click="add">Add</el-button>
    </div>
    <el-table :data="tableData"
              :header-cell-style="{ background:'#f2f5f2', color:'#555555'}"
              border
    >
      <el-table-column prop="id" label="ID" width="200">
      </el-table-column>
      <el-table-column prop="name" label="Warehouse Name" width="280">
      </el-table-column>
      <el-table-column prop="remark" label="remark" width="500">
      </el-table-column>

      <el-table-column prop="operate" label="Operate" width="200">
        <template slot-scope="scope">
          <el-button size="mini" type="success" @click="mod(scope.row)"> Edit</el-button>
          <el-popconfirm
              title="Are you sure to delete this?"
              @confirm="del(scope.row.id)"
              style="margin-left: 5px"
          >
            <!--              confirm 以后才调用del-->

            <el-button slot="reference" size="mini" type="danger" >Delete</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[5, 10, 20,30]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>

    <el-dialog
        title="Adding account"
        :visible.sync="centerDialogVisible"
        width="30%"
        center>
      <el-form ref="form" :rules="rules" :model="form" label-width="100px">
        <el-form-item label="Warehouse Name" prop="name">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="remark" prop="remark">
          <el-input type="textarea" v-model="form.remark"></el-input>
        </el-form-item>

      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="centerDialogVisible = false">Cancel</el-button>
    <el-button type="primary" @click="save">Confirm</el-button>
  </span>
    </el-dialog>

  </div>
</template>


<script>
export default {
  name: "StorageManagement",
  data() {

    return{
      tableData: [],
      pageSize:10,
      pageNum:1,
      total:0,
      name:'',//refer v-model里的name
      centerDialogVisible:false,
      form:{
        id:'',
        name:'',
        remark:''
      },
      rules: {
        name: [
          {required: true, message: 'Please input  name', trigger: 'blur'},
        ],
      }
    }
  },
  methods: {
    resetForm() {
      this.$refs.form.resetFields();
    },
    handleSizeChange(val) {
      console.log(`${val} items per page`);
      this.pageNum=1
      this.pageSize=val
      this.loadPost()
    },
    handleCurrentChange(val) {
      console.log(`current page: ${val}`);
      this.pageNum=val
      this.loadPost()
    },
    resetParam(){
      this.name=''
    },
    add(){
      this.centerDialogVisible=true;
      this.$nextTick(()=>{
        this.resetForm()
      })
    },
    mod(row){
      this.centerDialogVisible=true
      this.$nextTick(()=>{
        this.form.id=row.id
        this.form.name=row.name
        this.form.remark=row.remark
      })
    },
    del(id){
      this.$axios.get(this.$httpUrl+'/storage/del?id='+id).then(res=>res.data).then(res=>{
        console.log(res)
        if(res.code==200){

          this.$message({
            message: 'Delete Successed!',
            type: 'success'
          });
          this.loadPost()
        }else{
          this.$message({
            message: 'Operation Failed！',
            type: 'error'
          });
        }

      })
    },
    doSave(){
      this.$axios.post(this.$httpUrl+'/storage/save',this.form).then(res=>res.data).then(res=>{
        console.log(res)
        if(res.code==200){

          this.$message({
            message: 'Save Successed!',
            type: 'success'
          });
          this.centerDialogVisible = false
          this.loadPost()
          this. resetForm()
        }else{
          this.$message({
            message: 'Operation Failed！',
            type: 'error'
          });
        }

      })
    },
    doMod(){
      this.$axios.post(this.$httpUrl+'/storage/update',this.form).then(res=>res.data).then(res=>{
        console.log(res)
        if(res.code==200){

          this.$message({
            message: 'Save Successed!',
            type: 'success'
          });
          this.centerDialogVisible = false
          this.loadPost()
          this. resetForm()
        }else{
          this.$message({
            message: 'Operation Failed！',
            type: 'error'
          });
        }

      })
    },
    save(){
      this.$refs.form.validate((valid) => {
        if (valid) {
          if(this.form.id){
            this.doMod();
          }else{
            this.doSave();
          }
        } else {
          console.log('error submit!!');
          return false;
        }
      });

    },
    loadPost() {
      this.$axios.post(this.$httpUrl+'/storage/listPageEn',{
        pageSize: this.pageSize,
        pageNum:this.pageNum,
        param:{
          name:this.name,//refer data里的name
        }
      }).then(res=>res.data).then(res=>{
        console.log(res)
        if(res.code==200){
          this.tableData=res.data
          this.total=res.total
        }else{
          alert("Resource Get Failed")
        }

      })
    },
  },

  beforeMount() {
    this.loadPost()
  }

}
</script>

<style scoped>

</style>