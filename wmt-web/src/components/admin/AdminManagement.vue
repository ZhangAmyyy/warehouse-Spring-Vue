<template>
  <div>
    <div style="margin-bottom: 10px;text-align: center">
      <el-input v-model="name" placeholder="Please enter name" suffix-icon="el-icon-search" style="width:200px"
                @keyup.enter.native="loadPost"></el-input>
      <el-select v-model="gender" placeholder="Select Gender" style="margin-left: 5px">
        <el-option
            v-for="item in genders"
            :key="item.value"
            :label="item.label"
            :value="item.value"
            :disabled="item.disabled">
        </el-option>
      </el-select>
      <el-button type="primary" style="margin-left: 10px" @click="loadPost">Search</el-button>
      <el-button type="success" @click="resetParam">Reset</el-button>

      <el-button type="primary" style="margin-left: 10px" @click="add">Add</el-button>
    </div>
    <el-table :data="tableData"
              :header-cell-style="{ background:'#f2f5f2', color:'#555555'}"
              border
    >
      <el-table-column prop="id" label="ID" width="100">
      </el-table-column>
      <el-table-column prop="no" label="Account Number" width="140">
      </el-table-column>
      <el-table-column prop="name" label="Name" width="120">
      </el-table-column>
      <el-table-column prop="age" label="Age" width="80">
      </el-table-column>
      <el-table-column prop="gender" label="Gender" width="100">
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.gender===1?'primary':'success'"
              disable-transitions>{{scope.row.gender===1  ?'Male':'Female'}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="roleId" label="Role" width="120">
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.roleId===0?'danger':(scope.row.roleId===1?'primary':'success')"
              disable-transitions>{{scope.row.roleId===0?'super':
              (scope.row.roleId===1?'manager':'general')}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="phone" label="Phone Number" width="160">
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
      <el-form ref="form" :rules="rules" :model="form" label-width="120px">
        <!--      <el-form ref="form" :rules="rules" :model="form" label-width="120px">-->
        <el-form-item label="name" prop="name">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <!--      </el-form>-->
        <!--      <el-form ref="form" :rules="rules" :model="form" label-width="120px">-->
        <el-form-item label="Acc. number" prop="no">
          <el-input v-model="form.no"></el-input>
        </el-form-item>
        <!--      </el-form>-->
        <!--      <el-form ref="form" :rules="rules" :model="form" label-width="120px">-->
        <el-form-item label="password" prop="password">
          <el-input v-model="form.password"></el-input>
        </el-form-item>
        <!--      </el-form>-->
        <!--      <el-form ref="form" :rules="rules" :model="form" label-width="120px">-->
        <el-form-item label="age" prop="age">
          <el-input v-model="form.age"></el-input>
        </el-form-item>
        <!--      </el-form>-->
        <!--      <el-form ref="form" :model="form" label-width="120px">-->
        <el-form-item label="gender">
          <el-radio-group v-model="form.gender">
            <el-radio label="1">Male</el-radio>
            <el-radio label="0">Female</el-radio>
          </el-radio-group>
        </el-form-item>
        <!--      </el-form>-->
        <!--      <el-form ref="form" :rules="rules" :model="form" label-width="120px">-->
        <el-form-item label="phone number" prop="phone">
          <el-input v-model="form.phone"></el-input>
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
  name: "AdminManagement",
  data() {

    let checkAge = (rule, value, callback) => {
      if(value>150){
        callback(new Error('Input is too large')); }else{
        callback();
      }
    };


    let checkDuplicate =(rule,value,callback)=> {
      if (this.form.id) {
        return callback();
      }
      this.$axios.get(this.$httpUrl+"/user/findByNo?no="+this.form.no).then(res=>res.data).then(res=>{
        if (res.code!= 200) {
          callback()
        } else {
          callback(new Error('Account already exist!'));
        }
      })
    };

    return{
      tableData: [],
      pageSize:10,
      pageNum:1,
      total:0,
      name:'',//refer v-model里的name
      gender:'',
      genders:  [{
        value: '1',
        label: 'Male'
      }, {
        value: '2',
        label: 'Female'
      }],
      centerDialogVisible:false,
      form:{
        name:'',
        no:'',
        password:'',
        age:'',
        phone:'',
        gender:'0',
        roleId:'1'
      },
      rules: {
        name: [
          {required: true, message: 'Please input  name', trigger: 'blur'},
        ],
        no: [
          {required: true, message: 'Please input  account number', trigger: 'blur'},
          {min: 3, max: 8, message: 'Length should be 3 to 8', trigger: 'blur'},
          {validator:checkDuplicate,trigger: 'blur'}
        ],
        password: [
          {required: true, message: 'Please input  password', trigger: 'blur'},
          {min: 3, max: 8, message: 'Length should be 3 to 8', trigger: 'blur'}
        ],
        age: [
          {required: true, message: 'Please input  age', trigger: 'blur'},
          {min: 1, max: 3, message: 'Length should be 1 to 3', trigger: 'blur'},
          {pattern: /^([1-9][0-9]*){1,3}$/,message: 'Age must be positive integer',trigger: "blur"},
          {validator:checkAge,trigger: 'blur'}
        ],
        phone: [
          {required: true,message: "Phone number cannot be null",trigger: "blur"},
          {pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: "Please input valid phone number", trigger: "blur"}
        ]
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
      this.gender=''
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
        this.form.no=row.no
        this.form.password=''
        this.form.gender = row.gender +''
        this.form.phone=row.phone
        this.form.age=row.age+''
        this.form.roleId=row.roleId
      })
    },
    del(id){
      this.$axios.get(this.$httpUrl+'/user/del?id='+id).then(res=>res.data).then(res=>{
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
      this.$axios.post(this.$httpUrl+'/user/save',this.form).then(res=>res.data).then(res=>{
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
      this.$axios.post(this.$httpUrl+'/user/update',this.form).then(res=>res.data).then(res=>{
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
    loadGet() {
      this.$axios.get(this.$httpUrl+'/user/list').then(res => res.data).then(res =>{
        console.log(res)
      })
    },
    loadPost() {
      this.$axios.post(this.$httpUrl+'/user/listPageEn',{
        pageSize: this.pageSize,
        pageNum:this.pageNum,
        param:{
          name:this.name,//refer data里的name
          gender: this.gender,
          roleId: '1'
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