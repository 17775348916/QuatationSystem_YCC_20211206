<template>
  <el-form class="login-container" label-position="top">
    <h3 class="login_title">Chem Eagle Brain 1.0 (2021)</h3>
    <el-form-item label="工号">
      <el-input v-model="loginForm.accountid" placehoder="请输入工号"></el-input>
    </el-form-item>
    <el-form-item label="密码">
      <el-input type="password" v-model="loginForm.Keywords" placeholder="请输入密码"/>
    </el-form-item>
    <el-form-item>
      <el-button v-on:click="login">登录</el-button>
    </el-form-item>
    <!--    <el-form-item>-->
    <!--      <el-button :plain="true" @click="openVn">VNode</el-button>-->
    <!--    </el-form-item>-->
  </el-form>
</template>

<script>

export default {
  name: 'Login',
  data () {
    return {
      loginForm: {
        accountid: '',
        Keywords: ''
      },
      status: '',
      responseResult: []
    }
  },
  methods: {
    login () {
      // console.log(this.loginForm)
      this.$axios
        .post('/adminlogin', {
          accountid: this.loginForm.accountid,
          keywords: this.loginForm.Keywords
        })
        .then(successResponse => {
          if (successResponse.data.success) {
            // _this.$store.commit('login', _this.loginForm)
            console.log(successResponse.data)
            this.$message('登陆成功')
            window.sessionStorage.setItem('usertype', successResponse.data.data.typeid)
            window.sessionStorage.setItem('islogin', true)
            window.sessionStorage.setItem('account_id', successResponse.data.data.accountid)
            this.$router.replace({path: '/manageindex'})
          } else {
            this.$message(successResponse.data.msg)
          }
        })
        .catch(failResponse => {
          // console.log(this.loginForm)
        })
    }
  }
}
</script>
<style>
.login-container {
  border-radius: 15px;
  background-clip: padding-box;
  margin: 90px auto;
  width: 350px;
  padding: 35px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}

.login_title {
  margin: 0px auto 40px auto;
  text-align: center;
  color: #505458;
}

</style>
