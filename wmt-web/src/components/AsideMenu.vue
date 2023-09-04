<template>

  <el-menu
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b"
      default-active="/AccountCenter"
      :collapse="isCollapse"
      :collapse-transition="false"
      style="height: 100%;"
      router
  >
    <el-menu-item index="/AccountCenter">
      <i class="el-icon-s-home"></i>
      <span slot="title">Home</span>
    </el-menu-item>

    <el-menu-item :index="'/'+item.menuclick" v-for="(item,i) in menu" :key="i">
      <i :class="item.menuicon"></i>
      <span slot="title">{{item.menuname}}</span>
    </el-menu-item>


    <!--    <el-menu-item index="/AdminManage">-->
<!--      <i class="el-icon-s-home"></i>-->
<!--      <span slot="title">Administer Management</span>-->
<!--    </el-menu-item>-->

<!--    <el-menu-item index="/UserManagement">-->
<!--      <i class="el-icon-s-home"></i>-->
<!--      <span slot="title">User Management</span>-->
<!--    </el-menu-item>-->

  </el-menu>

</template>

<script>
export default {
  name: "AsideMenu",
  data(){
    return{
      // isCollapse:false
      // menu:[
      //   {
      //     menuClick:'AdminManagement',
      //     menuName:'Administer Management',
      //     menuIcon:'el-icon-s-custom'
      //   },{
      //     menuClick:'UserManagement',
      //     menuName:'User Management',
      //     menuIcon:'el-icon-user-solid'
      //   }
      // ]
    }
  },
  computed:{
    "menu" :{
      get(){
        return this.$store.state.menu
      }
    }
  },
  props:{
    isCollapse:Boolean
  }
}
</script>

<style scoped>

</style>