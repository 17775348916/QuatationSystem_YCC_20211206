<template>
  <div>
    <IdentityCheck></IdentityCheck>
    <el-container>
      <el-header style="text-align: right; font-size: 16px">
        <div class="title">
          ChemEagle Brain 1.0
        </div>
        <el-dropdown trigger="click" @command="handleCommand">
          <span class="el-dropdown-link" style="font-size:15px;color:black">
            <i class="el-icon-user-solid"></i>
            <span style="font-family:华文楷体;font-size:18px">欢迎您，{{ accountid }}号管理员</span>
            <i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item>注销登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-header>

      <el-container>
        <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
          <el-menu
            default-active="2"
            class="el-menu-vertical-demo" @select="menuClick">
            <el-menu-item index="/Bindex">
              <i class="el-icon-s-custom"></i>
              <span slot="title">商务人员页面</span>
            </el-menu-item>
            <el-menu-item index="/Tindex">
              <i class="el-icon-mouse"></i>
              <span slot="title">技术人员页面</span>
            </el-menu-item>
            <el-menu-item index="/Cindex">
              <i class="el-icon-phone-outline"></i>
              <span slot="title">客服人员页面</span>
            </el-menu-item>
            <div v-if="typeid === '1'">
              <el-menu-item index="/M1">
                <i class="el-icon-postcard"></i>
                <span slot="title">用户账号管理</span>
              </el-menu-item>
              <el-menu-item index="/M3">
                <i class="el-icon-turn-off"></i>
                <span slot="title">报价模式管理</span>
              </el-menu-item>
              <el-menu-item index="/M2">
                <i class="el-icon-notebook-2"></i>
                <span slot="title">历史项目查看</span>
              </el-menu-item>
              <el-menu-item index="/M5">
                <i class="el-icon-s-data"></i>
                <span slot="title">原料数据库管理</span>
              </el-menu-item>
            </div>
          </el-menu>
        </el-aside>
        <el-main>
          <el-breadcrumb v-if="this.$router.currentRoute.path!=='/manageindex'" separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/manageindex' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>{{this.$router.currentRoute.name}}</el-breadcrumb-item>
          </el-breadcrumb>
          <div class="welcomeTitle" v-if="this.$router.currentRoute.path==='/manageindex'">
            欢迎您来到ChemEagle Brain 1.0 !
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
  name: 'manageindex',
  inject: ['reload'],
  data () {
    return {
      accountid: window.sessionStorage.getItem('account_id'),
      typeid: window.sessionStorage.getItem('usertype')
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
      this.$router.replace('/managelogin')
      this.reload()
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
  display: flex;
  align-items: center;
  /*text-align: center;*/
  /*line-height: 50px;*/
  justify-content: space-between;
  padding: 0 15px;
  box-sizing: border-box;

}

.title {
  font-size: 30px;
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

.welcomeTitle {
  font-size: 30px;
  font-family: 华文楷体;
  margin-top: 50px;
  color: #409eff
}

.el-dropdown-link {

}
</style>
