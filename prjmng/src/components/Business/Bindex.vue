/* eslint-disable */
<template>
  <div class="styleBindex" v-cloak>
    <IdentityCheck></IdentityCheck>
    <el-container >
      <el-header style="text-align: right; font-size: 16px">
        <div class="title">
          ChemEagle Brain 1.0
        </div>
        <div v-if="usertype === '1'" style="margin-left:800px">
          <el-dropdown trigger="click" @command="operations">
          <span class="el-dropdown-link" style="font-size:18px;color:black;font-family:华文楷体">
           <i class="el-icon-setting"></i>   快捷操作
          </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="a">管理员首页</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
        <el-dropdown trigger="click" @command="handleCommand">
          <span class="el-dropdown-link" style="font-size:15px;color:black">
            <i class="el-icon-user-solid" ></i>
            <span style="font-family:华文楷体;font-size:18px">欢迎您，{{account_id}}号商务人员</span>
            <i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item >注销登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-header>

      <el-container>
        <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
          <el-menu
            default-active="2"
            class="el-menu-vertical-demo" @select="menuClick" >
            <el-menu-item index="/Bindex">
              <i class="el-icon-aim"></i>
              <span slot="title">欢迎回到首页</span>
            </el-menu-item>
            <el-menu-item index="/B1">
              <i class="el-icon-document"></i>
              <span slot="title">询单项目信息录入</span>
            </el-menu-item>
            <el-submenu index="2">
              <template slot="title">
                <i class="el-icon-success"></i>
                <span>查看有成交可能项目</span>
              </template>
              <el-menu-item index="/B21">
                <span slot="title">技术未评估</span>
              </el-menu-item>
              <el-menu-item index="/B22">
                <span slot="title">技术已评估</span>
              </el-menu-item>
            </el-submenu>
            <el-menu-item index="/BUnavailable">
              <i class="el-icon-error"></i>
              <span slot="title">查看尚无成交可能项目</span>
            </el-menu-item>
          </el-menu>
        </el-aside>
        <el-main>
          <el-breadcrumb v-if="this.$router.currentRoute.path!=='/Bindex'" separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/Bindex' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>{{this.$router.currentRoute.name}}</el-breadcrumb-item>
          </el-breadcrumb>
          <div align="left" style="color:red" v-if="this.$router.currentRoute.path === '/Bindex'">
            <div>
              已录入询单项目共 {{PossibleToHaveADeal + ImpossibleToHaveADeal}} 项：其中有成交可能项目 {{PossibleToHaveADeal}} 项，无成交可能项目 {{ImpossibleToHaveADeal}} 项。
            </div>
          </div>
          <div class="welcomeTitle" v-if="this.$router.currentRoute.path==='/Bindex'">
            商务人员您好，欢迎来到ChemEagle Brain 1.0 !
          </div>
          <router-view/>
        </el-main>
      </el-container>
    </el-container>

  </div>
</template>

<script>
import IdentityCheck from './IdentityCheck'
export default {
  name: 'Bindex',
  inject: ['reload'],
  data: function () {
    return {
      message: '',
      account_id: window.sessionStorage.getItem('account_id'),
      usertype: window.sessionStorage.getItem('usertype'),
      PossibleToHaveADeal: '',
      ImpossibleToHaveADeal: ''
    }
  },
  created () {
    // 退出T1 T2 TUnevaluated界面后，就不做自动查询
    if (window.sessionStorage.getItem('TUnevaluatedInterval') != null) {
      window.sessionStorage.removeItem('TUnevaluatedInterval')
    }
    if (window.sessionStorage.getItem('CEvaluatedInterval') != null) {
      window.sessionStorage.removeItem('CEvaluatedInterval')
    }
    if (window.sessionStorage.getItem('CUnevaluatedInterval') != null) {
      window.sessionStorage.removeItem('CUnevaluatedInterval')
    }
    if (this.$router.currentRoute.path === '/Bindex') {
      // 查找有成交可能的项目
      this.$axios
        .post('/allproj', {
          page: 1,
          size: 5,
          interval: 300000,
          resultkf: '所有'
        })
        .then(successResponse => {
          if (successResponse.data.success) {
            this.PossibleToHaveADeal = successResponse.data.data.totalElements
          } else {
            this.$message(successResponse.data.msg)
          }
        }).catch(failResponse => {})
      // 查找没有成交可能的项目
      this.$axios
        .post('/showUnavailables', {
          page: 1,
          size: 5,
          interval: 300000
        })
        .then(successResponse => {
          if (successResponse.data.success) {
            this.ImpossibleToHaveADeal = successResponse.data.data.totalElements
          } else {
            this.$message(successResponse.data.msg)
          }
        }).catch(failResponse => {})
    }
  },
  methods: {
    menuClick (index) {
      this.$router.push(index)
      this.reload()
    },
    handleCommand (command) {
      window.sessionStorage.removeItem('account_id')
      window.sessionStorage.removeItem('usertype')
      window.sessionStorage.removeItem('islogin')
      this.$router.replace('/A')
    },
    operations (command) {
      if (command === 'a') {
        this.$router.replace('/manageindex')
        this.reload()
      }
    }
  },
  components: {
    IdentityCheck
  }
}
</script>

<style scoped>
.el-header, .el-footer {
  background-color: #409eff;
  /*background-color: #545c64;*/
  /*color: #fff;*/
  display:flex;
  align-items: center;
  /*text-align: center;*/
  /*line-height: 50px;*/
  justify-content: space-between;
  padding:0 15px;
  box-sizing: border-box;

}
.title{
  font-size:30px;
  color: black;
  font-family: 华文楷体;
}

.el-aside {
  background-color: #D3DCE6;
  color: #333;
  text-align: center;
  /*line-height: 200px;*/
}

.el-main {
  /*background-color: #E9EEF3;*/
  /*color: #333;*/
  text-align: center;
  /*line-height: 160px;*/
}

body > .el-container {
  margin-bottom: 40px;
}

.welcomeTitle{
  font-size:30px;
  font-family:华文楷体;
  margin-top:50px;
  color:#409eff
}
</style>
