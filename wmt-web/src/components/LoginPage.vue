
<template>
  <div class="loginBody">
    <div class="loginDiv">
      <div class="login-content">
        <h1 class="login-title">Login</h1>
        <el-form :model="loginForm" label-width="100px"
                 :rules="rules" ref="loginForm">
          <el-form-item label="Account Number" prop="no">
            <el-input style="width: 200px; padding-top: 5%" type="text" v-model="loginForm.no"
                      autocomplete="off" size="small"></el-input>
          </el-form-item>
          <el-form-item label="Passward" prop="password">
            <el-input style="width: 200px" type="password" v-model="loginForm.password"
                      show-password autocomplete="off" size="small" @keyup.enter.native="confirm"></el-input>
          </el-form-item>
          <el-form-item style="padding-left: 13%">
            <el-button type="primary" @click="confirm" :disabled="confirm_disabled" >Confirm</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>


<script>
export default {
  name: "LoginPage",
  data(){
    return{
      confirm_disabled:false,
      loginForm:{
        no:'',
        password:''
      },
      rules:{
        no: [
          { required: true, message: 'Please input account number', trigger: 'blur' }
        ],
        password: [
          { required: true, message: 'Please input password', trigger: 'blur' }
        ],
      }
    }
  },
  methods:{

    confirm(){
      this.confirm_disabled=true;
      this.$refs.loginForm.validate((valid) => {
        if (valid) { //valid成功为true，失败为false
          //去后台验证用户名密码
          this.$axios.post(this.$httpUrl+'/user/login',this.loginForm).then(res=>res.data).then(res=>{
            if(res.code==200){
              //存储
              // console.log("123"+res.data)
              sessionStorage.setItem("CurUser",JSON.stringify(res.data.user))
              // console.log("555"+res.data)
              // console.log("333"+res.data.menu)
              this.$store.commit('setMenu',res.data.menu1)
              //跳转到主页
              this.$router.replace('/MyTemp');
            }else{
              this.confirm_disabled=false;
              alert('Account Number or Password is invalid!');
              return false;
            }
          });
        } else {
          this.confirm_disabled=false;
          console.log('校验失败');
          return false;
        }
      });

    }
  }
}
</script>

<style scoped>
.loginBody {
  position: absolute;
  width: 100%;
  height: 100%;
  background-color: #B3C0D1;
}
.loginDiv {
  position: absolute;
  top: 50%;
  left: 50%;
  margin-top: -200px;
  margin-left: -250px;
  width: 450px;
  height: 330px;
  background: #fff;
  border-radius: 5%;

}
.login-title {
  margin: 20px 0;
  text-align: center;
}
.login-content {
  width: 400px;
  height: 250px;
  position: absolute;
  top: 25px;
  left: 25px;
}
</style>