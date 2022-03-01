<template>
  <div>
    <TIdentityCheck></TIdentityCheck>
    <el-container>
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
            <span style="font-family:华文楷体;font-size:18px">欢迎您，{{account_id}}号技术人员</span>
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
            default-active="1"
            class="el-menu-vertical-demo" @select="menuClick">
            <el-menu-item index="/Tindex">
              <i class="el-icon-aim"></i>
              <span slot="title">欢迎回到首页</span>
            </el-menu-item>

            <el-submenu index="1">
              <template slot="title">
                <i class="el-icon-success"></i>
                <span>查看可行项目</span>
              </template>
              <el-menu-item index="/TUnevaluated">
                <span slot="title">技术未评估</span>
              </el-menu-item>
              <el-menu-item index="/TEvaluated">
                <span slot="title">技术已评估</span>
              </el-menu-item>
            </el-submenu>
          </el-menu>
        </el-aside>
        <el-main>
          <el-breadcrumb v-if="this.$router.currentRoute.path!=='/Tindex'" separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/Tindex' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item v-if="this.$router.currentRoute.path==='/T1' || this.$router.currentRoute.path==='/T2'" :to="{ path: '/TUnevaluated' }">技术未评估项目</el-breadcrumb-item>
            <el-breadcrumb-item >{{this.$router.currentRoute.name}}</el-breadcrumb-item>
          </el-breadcrumb>
          <div v-if="this.$router.currentRoute.path === '/Tindex'">
            <div align="left" style="color:red">
              技术已评估：{{evaluated}} 项；
            </div>
            <div align="left" style="color:red;margin-top:10px">
              技术未评估：{{unevaluated}} 项。
            </div>
          </div>
          <div class="welcomeTitle" v-if="this.$router.currentRoute.path==='/Tindex'">
            技术人员您好，欢迎来到ChemEagle Brain 1.0 !
          </div>
          <router-view/>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import TIdentityCheck from './TIdentityCheck'

export default {
  name: 'Tindex',
  data: function () {
    return {
      activeName: '1',
      date: new Date(),
      account_id: window.sessionStorage.getItem('account_id'),
      usertype: window.sessionStorage.getItem('usertype'),
      evaluated: '',
      unevaluated: ''
    }
  },
  components: {
    TIdentityCheck
  },
  created () {
    if (window.sessionStorage.getItem('CEvaluatedInterval') != null) {
      window.sessionStorage.removeItem('CEvaluatedInterval')
    }
    if (window.sessionStorage.getItem('CUnevaluatedInterval') != null) {
      window.sessionStorage.removeItem('CUnevaluatedInterval')
    }
    // 退出T1 T2 TUnevaluated界面后，就不做自动查询
    if (window.sessionStorage.getItem('TUnevaluatedInterval') != null) {
      window.sessionStorage.removeItem('TUnevaluatedInterval')
    }
    // 查找技术未评估任务
    this.$axios
      .post('/unevaluatedPage', {
        page: 1,
        size: 5,
        interval: 300000,
        status: '未评估'
      })
      .then(successResponse => {
        if (successResponse.data.success) {
          this.unevaluated = successResponse.data.data.totalElements
        } else {
          this.$message(successResponse.data.msg)
        }
      }).catch(failResponse => {})
    // 查找技术已评估任务
    this.$axios
      .post('/evaluatedPage', {
        interval: 300000,
        resultkf: '所有',
        page: 1,
        size: 5,
        status: '已评估'
      })
      .then(successResponse => {
        if (successResponse.data.success) {
          this.evaluated = successResponse.data.data.totalElements
        } else {
          this.$message(successResponse.data.msg)
        }
      }).catch(failResponse => {})
  },
  methods: {
    menuClick (index) {
      this.$router.push(index)
      window.location.reload()
    },
    handleCommand (command) {
      window.sessionStorage.removeItem('account_id')
      window.sessionStorage.removeItem('usertype')
      window.sessionStorage.removeItem('islogin')
      this.$router.replace('/A')
    },
    operations (command) {
      if (command === 'a') {
        this.$router.push('/manageindex')
        window.location.reload()
      }
    }
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

.welcomeTitle{
  font-size:30px;
  font-family:华文楷体;
  margin-top:50px;
  color:#409eff
}
.el-dropdown-link{

}
</style>
