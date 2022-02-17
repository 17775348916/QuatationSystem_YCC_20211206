<template>
  <el-form :rules="rules" ref="loginForm" class="login-container" label-width="60px" :model="loginForm">
    <h3 class="login_title">Chem Eagle Brain 1.0 (2021)</h3>
    <el-form-item label="工号" prop="accountid">
      <el-input type="text" v-model="loginForm.accountid" placehoder="请输入工号" @keydown.enter.native="login"></el-input>
    </el-form-item>
    <el-form-item label="密码" prop="Keywords">
      <el-input type="password" v-model="loginForm.Keywords" placeholder="请输入密码" @keydown.enter.native="login"/>
    </el-form-item>
    <el-form-item>
      <div align="center">
        <el-button align="center" v-on:click="login">登录</el-button>
      </div>
    </el-form-item>
  </el-form>
</template>

<script>

export default {
  name: 'managelogin',
  data () {
    return {
      loginForm: {
        accountid: '',
        Keywords: ''
      },
      status: '',
      responseResult: [],
      rules: {
        accountid: [{required: true, message: '请输入工号', trigger: 'blur'}],
        Keywords: [{required: true, message: '请输入密码', trigger: 'blur'}]
      }
    }
  },
  create () {
    window.sessionStorage.removeItem('account_id')
    window.sessionStorage.removeItem('usertype')
    window.sessionStorage.removeItem('islogin')
    location.reload()
  },
  methods: {
    login () {
      // console.log(this.loginForm)
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          this.$axios
            .post('/adminlogin', {
              accountid: this.loginForm.accountid,
              keywords: this.loginForm.Keywords
            })
            .then(successResponse => {
              if (successResponse.data.success) {
                console.log(successResponse.data)
                this.$message.success('登陆成功')
                window.sessionStorage.setItem('usertype', successResponse.data.data.typeid)
                window.sessionStorage.setItem('islogin', true)
                window.sessionStorage.setItem('account_id', successResponse.data.data.accountid)
                this.$router.push({path: '/manageindex'})
              } else {
                this.$message.error(successResponse.data.msg)
              }
            })
            .catch(failResponse => {
            })
        } else {
          this.$message.error('请输入所有字段')
        }
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
  padding: 15px 45px 15px 35px;
  background: #fff;
  /*border: 0px solid #eaeaea;*/
  box-shadow: 0 0 25px #cac6c6;
}

.login_title {
  margin: 0px auto 40px auto;
  text-align: center;
  color: #505458;
}

</style>
