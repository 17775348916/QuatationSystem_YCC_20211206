<template>
  <div>
    <el-form :rules="rules" ref="loginForm" class="login-container" label-width="60px" :model="loginForm">
      <h3 class="login_title">Chem Eagle Brain 1.0</h3>
      <el-form-item prop="Type_ID" label="身份" >
        <el-select v-model="loginForm.Type_ID" style="width:100%" placeholder="请选择业务身份">
          <el-option label="商务人员" value=2></el-option>
          <el-option label="技术人员" value=3></el-option>
          <el-option label="客服人员" value=4></el-option>
        </el-select>
      </el-form-item>

      <el-form-item prop="rank"  label="级别">
        <el-select v-model="loginForm.rank" style="width:100%" placeholder="请选择级别">
          <el-option label="高级" value="1"></el-option>
          <el-option label="普通" value="2"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="Account_ID" label="工号">
        <el-input type="text" v-model="loginForm.Account_ID"  placeholder="请输入工号" @keydown.enter.native="login"></el-input>
      </el-form-item>
      <el-form-item prop="Keywords" label="密码">
        <el-input type="password" v-model="loginForm.Keywords"  placeholder="请输入密码" @keydown.enter.native="login"></el-input>
      </el-form-item>
      <el-form-item >
        <el-button type="primary" v-on:click="login">立即登录</el-button>
        <el-button v-on:click="$router.push('/managelogin')">后台管理</el-button>
      </el-form-item>
    </el-form>
  </div>

</template>

<script>

import {postRequest} from '../../utils/api'
import {Message} from 'element-ui'

export default {
  name: 'Login',
  data () {
    return {
      loginForm: {
        Account_ID: '',
        Keywords: '',
        rank: '',
        Type_ID: ''
      },
      status: '',
      rules: {
        // Type_ID: [{required: true, message: '请选择业务身份', trigger: 'blur'}],
        // rank: [{required: true, message: '请选择级别', trigger: 'blur'}],
        Account_ID: [{required: true, message: '请输入工号', trigger: 'blur'}],
        Keywords: [{required: true, message: '请输入密码', trigger: 'blur'}]
      }
    }
  },
  created () {
    window.sessionStorage.removeItem('account_id')
    window.sessionStorage.removeItem('usertype')
    window.sessionStorage.removeItem('islogin')
  },
  methods: {
    login () {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          postRequest('/login', this.loginForm).then(resp => {
            if (resp.data.success) {
              var _this = this
              Message.success({message: '登陆成功'})
              window.sessionStorage.setItem('usertype', _this.loginForm.Type_ID)
              window.sessionStorage.setItem('islogin', true)
              window.sessionStorage.setItem('account_id', _this.loginForm.Account_ID)
              switch (this.loginForm.Type_ID) {
                case '2':
                  this.$router.replace({path: '/Bindex'})
                  break
                case '3':
                  this.$router.replace({path: '/Tindex'})
                  break
                case '4':
                  this.$router.replace({path: '/Cindex'})
                  break
              }
            } else {
              this.$message.error(resp.data.msg)
            }
          })
        } else {
          this.$message.error('请输入所有字段')
          return false
        }
      })
    }
  }
}
</script>
<style scoped>
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
