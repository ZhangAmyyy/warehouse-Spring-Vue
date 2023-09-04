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

<!--      <el-button type="primary" style="margin-left: 10px" @click="add">Add</el-button>-->
    </div>
    <el-table :data="tableData"
              :header-cell-style="{ background:'#f2f5f2', color:'#555555'}"
              border
    >
      <el-table-column prop="id" label="ID" width="200">
      </el-table-column>
      <el-table-column prop="goodsname" label="Item Name" width="280">
      </el-table-column>
      <el-table-column prop="storagename" label="Warehouse" width="280" >
      </el-table-column>
      <el-table-column prop="goodstypename" label="Classification" width="280" >
      </el-table-column>
      <el-table-column prop="adminname" label="Operator" width="280">
      </el-table-column>
      <el-table-column prop="username" label="Applicant" width="280">
      </el-table-column>
      <el-table-column prop="count" label="Volume" width="280">
      </el-table-column>
      <el-table-column prop="createtime" label="Operate Time" width="280">
      </el-table-column>

      <el-table-column prop="remark" label="remark" width="500">
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

  </div>
</template>


<script>
export default {
  name: "RecordManagement",
  data() {

    return{
      user : JSON.parse(sessionStorage.getItem('CurUser')),
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
      form:{
        id:'',
        name:'',
        storage:'',
        goodstype:'',
        count:'',
        remark:''
      },
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
      this.storage=''
      this.goodstype=''
    },

    loadPost() {
      this.$axios.post(this.$httpUrl+'/record/listPageEn',{
        pageSize: this.pageSize,
        pageNum:this.pageNum,
        param:{
          name:this.name,
          goodstype:this.goodstype+'',
          storage:this.storage+'',
          roleId:this.user.roleId+'',
          userId:this.user.id+''
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
        console.log("storage")
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