<template>
  <el-container style="height: 100%; border: 1px solid #eee; text-align: center">
    <el-aside :width="asideWidth" style="background-color: rgb(238, 241, 246); height: 100vh">
      <AsideMenu :isCollapse="isCollapse"></AsideMenu>
    </el-aside>

    <el-container>
      <el-header style="text-align: right; font-size: 12px">
        <HeadContent @doCollapse="doCollapse" :icon="icon"></HeadContent>
      </el-header>

      <el-main>
<!--        <MainContent></MainContent>-->
        <router-view/>
      </el-main>
    </el-container>


  </el-container>
</template>


<script>
import AsideMenu from "@/components/AsideMenu";
import HeadContent from "@/components/HeadContent";
// import MainContent from "@/components/MainContent";

export default {
  name: "MyTemp",
  components:{ HeadContent, AsideMenu},
  data(){
    return{
       isCollapse: false,
      asideWidth: '280px',
      icon:'el-icon-s-fold'
    }
  },
  methods:{
    doCollapse(){
      this.isCollapse =!this.isCollapse
      if(!this.isCollapse){
        this.asideWidth='280px',
        this.icon='el-icon-s-fold'
      }else {
        this.asideWidth= '64px',
        this.icon='el-icon-s-unfold'
      }
    }
  }

};
</script>

<style scoped>
.el-header {
  background-color: #B3C0D1;
  color: #333;
  line-height: 60px;
}

.el-aside {
  color: #333;
}

</style>