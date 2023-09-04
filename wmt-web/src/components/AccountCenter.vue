<template>
  <div style="text-align: center;background-color: #f1f1f3;height: 100%;padding: 0px;margin: 0px;">
    <h1 style="font-size: 50px;">{{'Welcome！'+user.name}}</h1>
    <el-descriptions  title="Account Center" :column="2" size="40" border border-width:10px>
      <el-descriptions-item >
        <template slot="label" >
          <i class="el-icon-s-custom"></i>
          Account Number
        </template>
        {{user.no}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-mobile-phone"></i>
          Phone Number
        </template>
        {{user.phone}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-location-outline"></i>
          Gender
        </template>
        <el-tag
            :type="user.gender === '1' ? 'primary' : 'danger'"
            disable-transitions><i :class="user.gender==1?'el-icon-male':'el-icon-female'"></i>{{user.gender==1?"Male":"Female"}}</el-tag>
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-tickets"></i>
          Role
        </template>
        <el-tag
            type="success"
            disable-transitions>{{user.roleId==0?"Super":(user.roleId==1?"Manager":"General")}}</el-tag>

      </el-descriptions-item>
    </el-descriptions>

    <DateUtils></DateUtils>
  </div>
</template>

<script>
import DateUtils from "./DateUtils";
export default {
  name: "AccountCenter",
  components: {DateUtils},
  data() {

    return {
      user:{}
    }
  },
  computed:{

  },
  methods:{
    init(){
      this.user = JSON.parse(sessionStorage.getItem('CurUser'))
      console.log(this.user)
    }
  },
  created(){
    console.log("11")
    this.init()
  }
}
</script>

<style scoped>
.el-descriptions{
  width:90%;

  margin: 0 auto;
  text-align: center;
}
</style>