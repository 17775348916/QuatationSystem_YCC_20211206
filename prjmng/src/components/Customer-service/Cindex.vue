<template>
  <div>
    <CIdentityCheck></CIdentityCheck>

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
            <span style="font-family:华文楷体;font-size:18px">欢迎您，{{account_id}}号客服人员</span>
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
            <el-menu-item index="/Cindex">
              <i class="el-icon-aim"></i>
              <span slot="title">欢迎回到首页</span>
            </el-menu-item>
            <el-menu-item index="/C2">
              <i class="el-icon-s-data"></i>
              <span slot="title">原料数据库维护</span>
            </el-menu-item>
            <el-submenu index="2">
              <template slot="title">
                <i class="el-icon-document"></i>
                <span>提供原料任务</span>
              </template>
              <el-menu-item index="/CUnevaluated">
                <span slot="title">技术未评估</span>
              </el-menu-item>
              <el-menu-item index="/CEvaluated">
                <span slot="title">技术已评估</span>
              </el-menu-item>
            </el-submenu>

            <el-submenu index="3">
              <template slot="title">
                <i class="el-icon-phone-outline"></i>
                <span>下单客户沟通</span>
              </template>
              <el-menu-item index="/CNeedCommunicateWithOrders">
                <span slot="title">需要沟通的项目</span>
              </el-menu-item>
              <el-menu-item index="/CFinishCommunicateWithOrders">
                <span slot="title">已完成沟通的项目</span>
              </el-menu-item>
            </el-submenu>
            <el-submenu index="4">
              <template slot="title">
                <i class="el-icon-phone"></i>
                <span>接单客户沟通</span>
              </template>
              <el-menu-item index="/CNeedCommunicateWithTakeOrders">
                <span slot="title">需要沟通的项目</span>
              </el-menu-item>
              <el-menu-item index="/CFinishCommunicateWithTakeOrders">
                <span slot="title">已完成沟通的项目</span>
              </el-menu-item>
            </el-submenu>

          </el-menu>
        </el-aside>
        <el-main>
          <el-breadcrumb v-if="this.$router.currentRoute.path!=='/Cindex'" separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/Cindex' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>{{this.$router.currentRoute.name}}</el-breadcrumb-item>
          </el-breadcrumb>
          <div v-if="this.$router.currentRoute.path === '/Cindex'">
            <div align="left" style="color:red">
              未提供原料任务：{{noProvideMaterial}} 项； 已提供原料任务：{{provideMaterial}} 项。
            </div>
            <div align="left" style="color:red;margin-top:10px">
              未完成下单客户沟通任务：{{needCommunicateWithOrders}} 项； 已完成下单客户沟通任务：{{finishCommunicateWithOrders}} 项。
            </div>
            <div align="left" style="color:red;margin-top:10px">
              未完成接单客户沟通任务：{{needCommunicateWithTakeOrders}} 项； 已完成接单客户沟通任务：{{finishCommunicateWithTakeOrders}} 项。
            </div>
          </div>
          <div class="welcomeTitle" v-if="this.$router.currentRoute.path==='/Cindex'">
            客服人员您好，欢迎来到ChemEagle Brain 1.0 !
          </div>
          <router-view/>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import CIdentityCheck from './CIdentityCheck'
import Chead from './Chead'
export default {
  name: 'Cindex',
  data () {
    return {
      account_id: window.sessionStorage.getItem('account_id'),
      usertype: window.sessionStorage.getItem('usertype'),
      needCommunicateWithOrders: '',
      finishCommunicateWithOrders: '',
      needCommunicateWithTakeOrders: '',
      finishCommunicateWithTakeOrders: '',
      provideMaterial: '',
      noProvideMaterial: '',
      i: 0
    }
  },
  components: {
    CIdentityCheck,
    Chead
  },
  created () {
    // 退出T1 T2 TUnevaluated界面后，就不做自动查询
    if (window.sessionStorage.getItem('TUnevaluatedInterval') != null) {
      window.sessionStorage.removeItem('TUnevaluatedInterval')
    }
    if (window.sessionStorage.getItem('CUnevaluatedInterval') != null) {
      window.sessionStorage.removeItem('CUnevaluatedInterval')
    }
    if (window.sessionStorage.getItem('CEvaluatedInterval') != null) {
      window.sessionStorage.removeItem('CEvaluatedInterval')
    }
    // 查找未完成下单客户沟通任务
    this.$axios
      .post('/evaluatedPage', {
        page: 1,
        size: 5,
        interval: 300000,
        status: '已评估',
        resultkf: '待定'
      })
      .then(successResponse => {
        if (successResponse.data.success) {
          this.needCommunicateWithOrders = successResponse.data.data.totalElements
        } else {
          this.$message(successResponse.data.msg)
        }
      }).catch(failResponse => {})
    // 查找已完成下单客户沟通任务
    this.$axios
      .post('/finishContactPage', {
        status: '已评估',
        resultkf: '待定',
        interval: 300000,
        page: 1,
        size: 5
      })
      .then(successResponse => {
        if (successResponse.data.success) {
          this.finishCommunicateWithOrders = successResponse.data.data.totalElements
        } else {
          this.$message(successResponse.data.msg)
        }
      }).catch(failResponse => {})
    // 查找未完成接单客户沟通任务
    this.$axios
      .post('/unreceive', {
        interval: 300000,
        page: 1,
        size: 5
      })
      .then(successResponse => {
        if (successResponse.data.success) {
          this.needCommunicateWithTakeOrders = successResponse.data.data.totalElements
        } else {
          this.$message(successResponse.data.msg)
        }
      }).catch(failResponse => {})
    // 查找已完成接单客户沟通任务
    this.$axios
      .post('/havereceive', {
        interval: 300000,
        page: 1,
        size: 5
      })
      .then(successResponse => {
        if (successResponse.data.success) {
          this.finishCommunicateWithTakeOrders = successResponse.data.data.totalElements
        } else {
          this.$message(successResponse.data.msg)
        }
      }).catch(failResponse => {})
    // 查找已提供原料任务
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
          this.provideMaterial = successResponse.data.data.totalElements
          this.$axios
            .post('/materialMissionNum', {
              interval: 30000,
              status: '未评估'
            })
            .then(successResponse => {
              if (successResponse.data.success) {
                this.provideMaterial = this.provideMaterial + JSON.parse(successResponse.data.data).provideNum
                this.noProvideMaterial = JSON.parse(successResponse.data.data).noProvideNum
              }
            })
            .catch(failResponse => {})
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
