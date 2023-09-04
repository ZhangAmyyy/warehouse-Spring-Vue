<template>
  <div>
    <div style="margin-bottom: 10px;text-align: center">
      <el-input v-model="name" placeholder="Please enter item name" suffix-icon="el-icon-search" style="width:200px"
                @keyup.enter.native="loadPost"></el-input>

      <el-select v-model="storage" placeholder="Select warehouse" style="margin-left: 5px;">
        <el-option
            v-for="item in storageData"
            :key="item.id"
            :label="item.name"
            :value="item.id">
        </el-option>
      </el-select>
      <el-select v-model="goodstype" placeholder="Select Classification" style="margin-left: 5px;">
        <el-option
            v-for="item in goodstypeData"
            :key="item.id"
            :label="item.name"
            :value="item.id">
        </el-option>
      </el-select>


      <el-button type="primary" style="margin-left: 10px" @click="loadPost">Search</el-button>
      <el-button type="success" @click="resetParam">Reset</el-button>

      <el-button type="primary" style="margin-left: 10px" @click="add" v-if="user.roleId!=2">Add</el-button>
      <el-button type="primary" style="margin-left: 10px" @click="Inbound " v-if="user.roleId!=2">Inbound</el-button>
      <el-button type="primary" style="margin-left: 10px" @click="Outbound" v-if="user.roleId!=2">Outbound</el-button>


    </div>
    <el-table :data="tableData"
              :header-cell-style="{ background:'#f2f5f2', color:'#555555'}"
              border
              highlight-current-row
              @current-change="selectCurrentChange"

    >
      <el-table-column prop="id" label="ID" width="200">
      </el-table-column>
      <el-table-column prop="name" label="Item Name" width="280">
      </el-table-column>
      <el-table-column prop="storage" label="Warehouse" width="280" :formatter="formatStorage">
      </el-table-column>
      <el-table-column prop="goodstype" label="Classification" width="280" :formatter="formatGoodsType">
      </el-table-column>
      <el-table-column prop="count" label="Volume" width="280">
      </el-table-column>
      <el-table-column prop="remark" label="remark" width="500">
      </el-table-column>

      <el-table-column prop="operate" label="Operate" v-if="user.roleId!=2" width="200" >
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
        <el-form-item label="Item Name" prop="name">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="Warehouse" prop="name">
          <el-select v-model="storage" placeholder="Select warehouse" style="margin-left: 5px;">
            <el-option
                v-for="item in storageData"
                :key="item.id"
                :label="item.name"
                :value="item.id">
            </el-option>
          </el-select>

        </el-form-item>
        <el-form-item label="Classification" prop="name">
          <el-select v-model="goodstype" placeholder="Select Classification" style="margin-left: 5px;">
            <el-option
                v-for="item in goodstypeData"
                :key="item.id"
                :label="item.name"
                :value="item.id">
            </el-option>
          </el-select>

        </el-form-item>
        <el-form-item label="volume" prop="name">
          <el-input v-model="form.count"></el-input>
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


    <el-dialog
        title="Inbound"
        :visible.sync="inDialogVisible"
        width="30%"
        center>

      <el-dialog
          width="75%"
          title="Select Applicant"
          :visible.sync="innerVisible"
          append-to-body>
        <SelectUser @doSelectUser="doSelectUser"></SelectUser>
        <span slot="footer" class="dialog-footer">
                    <el-button @click="innerVisible = false">Cancel</el-button>
                    <el-button type="primary" @click="confirmUser">Confirm</el-button>
                  </span>
      </el-dialog>

      <el-form ref="form1" :rules="rules1" :model="form1" label-width="120px">
        <el-form-item label="Item Name" >
          <el-input v-model="form1.goodsname" readonly></el-input>
        </el-form-item>
        <el-form-item label="Applicant" >
        <el-input v-model="form1.username" readonly @click.native="selectUser"></el-input>
      </el-form-item>
        <el-form-item label="volume" prop="name">
          <el-input v-model="form1.count"></el-input>
        </el-form-item>
        <el-form-item label="remark" prop="remark">
          <el-input type="textarea" v-model="form1.remark"></el-input>
        </el-form-item>

      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="inDialogVisible = false">Cancel</el-button>
    <el-button type="primary" @click="doInbound">Confirm</el-button>
  </span>
    </el-dialog>

    <el-dialog
        title="Inbound"
        :visible.sync="outDialogVisible"
        width="30%"
        center>

      <span slot="footer" class="dialog-footer">
    <el-button @click="outDialogVisible = false">Cancel</el-button>
    <el-button type="primary" @click="save">Confirm</el-button>
  </span>
    </el-dialog>

  </div>
</template>


<script>
import SelectUser from "@/components/user/SelectUser";
export default {
  name: "GoodsManagement",
  components: {SelectUser},
  data() {

    return{
      user: JSON.parse(sessionStorage.getItem('CurUser')),
      goodstypeData:[],
      storageData:[],
      storage: '',
      goodstype: '',
      tableData: [],
      pageSize:10,
      pageNum:1,
      total:0,
      name:'',//refer v-model里的name
      centerDialogVisible:false,
      inDialogVisible: false,
      outDialogVisible:false,
      innerVisible: false,
      currentRow:{},
      tempUser:{},
      form:{
        id:'',
        name:'',
        storage:'',
        goodstype:'',
        count:'',
        remark:''
      },
      form1:{
        goods:'',
        goodsname:'',
        count:'',
        userid:'',
        username:'',
        adminId:'',
        remark: '',
        action:'1'
      },
      rules1:{

      },
      rules: {
        name: [
          {required: true, message: 'Please input  item name', trigger: 'blur'},
        ],
        count: [
          {required: true, message: 'Please input volume', trigger: 'blur'},
          {pattern: /^([1-9][0-9]*){1,4}$/,message: 'must be positive integer',trigger: "blur"}
        ],
      }
    }
  },
  methods: {
    formatStorage(row){
      let temp = this.storageData.find(item=>{
        return item.id ==row.storage
      })

      return temp && temp.name
    },
    formatGoodsType(row){
      let temp = this.goodstypeData.find(item=>{
        // console.log(item.id)
        // console.log(item.name)
        console.log(row)
        return item.id ==row.goodstype
      })
      // console.log(temp)
      return temp && temp.name
    },
    resetForm() {
      this.$refs.form.resetFields();
    },
    resetInForm() {
      this.$refs.form1.resetFields();
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
    selectCurrentChange(val) {
      this.currentRow = val;
    },
    resetParam(){
      this.name=''
      this.storage=''
      this.goodstype=''
    },
    add(){
      this.centerDialogVisible=true;
      this.$nextTick(()=>{
        this.resetForm()
        this.form.id=''
      })
    },
    Inbound(){
      if(!this.currentRow.id){
        alert('Please select one item')
        return;
      }

      this.inDialogVisible=true;
      this.$nextTick(()=>{
        this.resetInForm()
      })

      this.form1.goodsname=this.currentRow.name
      this.form1.goods=this.currentRow.id
      this.form1.adminId=this.user.id
      this.form1.action='1'
    },
    selectUser(){
      this.innerVisible=true
    },
    doSelectUser(val){
      console.log(val)
      this.tempUser=val
    },
    confirmUser(){
      this.form1.username=this.tempUser.name
      this.form1.userid=this.tempUser.id

      this.innerVisible=false
    },
    doInbound(){
      this.$axios.post(this.$httpUrl+'/record/save',this.form1).then(res=>res.data).then(res=>{
        console.log(res)
        if(res.code==200){

          this.$message({
            message: 'Save Successed!',
            type: 'success'
          });
          this.inDialogVisible = false
          this.loadPost()
          this. resetInForm()
        }else{
          this.$message({
            message: 'Operation Failed！',
            type: 'error'
          });
        }

      })
    },
    Outbound(){
      if(!this.currentRow.id){
        alert('Please select one item')
        return;
      }

      this.inDialogVisible=true;
      this.$nextTick(()=>{
        this.resetInForm()
      })

      this.form1.goodsname=this.currentRow.name
      this.form1.goods=this.currentRow.id
      this.form1.adminId=this.user.id
      this.form1.action='2'
    },
    mod(row){
      this.centerDialogVisible=true
      this.$nextTick(()=>{
        this.form.id=row.id
        this.form.name=row.name
        this.form.storage=row.storage
        this.form.goodstype=row.goodstype
        this.form.count=row.count
        this.form.remark=row.remark
      })
    },
    del(id){
      this.$axios.get(this.$httpUrl+'/goods/del?id='+id).then(res=>res.data).then(res=>{
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
      this.$axios.post(this.$httpUrl+'/goods/save',this.form).then(res=>res.data).then(res=>{
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
      this.$axios.post(this.$httpUrl+'/goods/update',this.form).then(res=>res.data).then(res=>{
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
      this.$axios.post(this.$httpUrl+'/goods/listPageEn',{
        pageSize: this.pageSize,
        pageNum:this.pageNum,
        param:{
          name:this.name,//refer data里的name
          goodstype: this.goodstype+'',
          storage: this.storage+''
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
    loadStorage(){
      this.$axios.get(this.$httpUrl+'/storage/list').then(res=>res.data).then(res=>{
        // console.log("storage")
      console.log(res)
      if(res.code==200){
        this.storageData=res.data
      }else{
        alert("Resource Get Failed")
      }

    })},
    loadGoodsType(){
      this.$axios.get(this.$httpUrl+'/goodstype/list').then(res=>res.data).then(res=>{
        console.log(res)
        if(res.code==200){
          this.goodstypeData=res.data
        }else{
          alert("Resource Get Failed")
        }

      })},
  },

  beforeMount() {
    this.loadStorage()
    this.loadGoodsType()
    this.loadPost()
  }

}
</script>

<style scoped>

</style>