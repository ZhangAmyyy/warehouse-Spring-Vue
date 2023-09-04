<template>
  <div style="display: flex;line-height: 60px;">
    <div style="font-size: 20px">
      <i :class="icon" @click="collapse"></i>
    </div>
    <div style="flex: 1;text-align: center;font-size: 24px;">
      <span> Welcome to Warehouse Management System</span>
    </div>
    <el-dropdown style="font-size: 20px">
      <span>{{user.name}}</span>
      <i class="el-icon-arrow-down" style="margin-right: 15px;margin-left: 15px"></i>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item @click.native="toUser">Account</el-dropdown-item>
        <el-dropdown-item @click.native="logout">Exit</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
export default {
  name: "HeadContent",
  data(){
    return{
      user : JSON.parse(sessionStorage.getItem('CurUser'))
    }
  },
  props:{
    icon:String
  },
  methods:{
    toUser(){
      console.log('to_user')
      this.$router.push("/AccountCenter")
    },
    logout(){
      this.$confirm('Confirm to logout', 'mention', {
        confirmButtonText: 'Confirm',  //确认按钮的文字显示
        type: 'warning',
        center: true, //文字居中显示

      })
          .then(() => {
            this.$message({
              type:'success',
              message:'logout success!'
            })

            this.$router.push("/")
            sessionStorage.clear()
          })
          .catch(() => {
            this.$message({
              type:'info',
              message:'已取消退出登录'
            })
          })

    },
    collapse(){
      this.$emit('doCollapse')
    }
  },
  created() {
    this.$router.push("/AccountCenter")
  }
}
</script>

<style scoped>

</style>