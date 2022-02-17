<template>
  <div>
    <CIdentityCheck></CIdentityCheck>

    <el-container >
      <el-header style="text-align: right; font-size: 16px">
        <div class="title">
          ChemEagle Brain 1.0
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
      usertype: ''
    }
  },
  components: {
    CIdentityCheck,
    Chead
  },
  methods: {
    menuClick (index) {
      this.$router.push(index)
    },
    handleCommand (command) {
      window.sessionStorage.removeItem('account_id')
      window.sessionStorage.removeItem('usertype')
      window.sessionStorage.removeItem('islogin')
      this.$router.replace('/A')
    },
    showunzt () {
      this.$axios
        .post('/unevaluatedPage', {
          interval: this.interval1,
          page: this.currentPage1,
          size: this.pageSize1,
          status: '未评估'
        })
        .then(successResponse => {
          if (successResponse.data.success) {
            this.list1 = successResponse.data.data.content
            if (this.list1.length < 1) {
              this.$message('查询时间段内无项目')
            }
            this.total1 = successResponse.data.data.totalElements
            this.$axios
              .post('/materialMissionNum', {
                interval: this.interval1,
                status: '未评估'
              })
              .then(successResponse => {
                if (successResponse.data.success) {
                  this.provideNum = JSON.parse(successResponse.data.data).provideNum
                  this.noProvideNum = JSON.parse(successResponse.data.data).noProvideNum
                }
              })
              .catch(failResponse => {})
          } else {
            this.$message(successResponse.data.msg)
          }
        })
        .catch(failResponse => {
        })
    },
    showhavezt () {
      this.$axios.post('/evaluatedPage', {
        interval: this.interval2,
        resultkf: '所有',
        page: this.currentPage2,
        size: this.pageSize2,
        status: '已评估'
      })
        .then(successResponse => {
          if (successResponse.data.success) {
            this.list2 = successResponse.data.data.content
            if (this.list2.length < 1) {
              this.$message('查询时间段内无项目')
            }
            this.total2 = successResponse.data.data.totalElements
          } else {
            this.$message(successResponse.data.msg)
          }
        })
        .catch(failResponse => {
        })
    },
    feedback (projectid) {
      window.sessionStorage.setItem(this.account_id, projectid)
      // console.log(projectid)
      this.$router.replace('/C1')
    },
    handleCurrentChange1 (currentPage) {
      this.getList1(currentPage)
    },
    handleSizeChange1 (pageSize) {
      this.pageSize1 = pageSize
      this.getList1(this.currentPage1)
    },
    getList1 (currentPage) {
      this.currentPage1 = currentPage
      this.$axios
        .post('/unevaluatedPage', {
          interval: this.interval1,
          page: currentPage,
          size: this.pageSize1,
          status: '未评估'
        })
        .then(successResponse => {
          // console.log(successResponse.data)
          if (successResponse.data.success) {
            console.log(successResponse.data.data)
            this.list1 = successResponse.data.data.content
            if (this.list1.length < 1) {
              this.$message('查询时间段内无项目')
            }
          } else {
            this.$message(successResponse.data.msg)
          }
        })
        .catch(failResponse => {
        })
    },
    handleSizeChange2 (pageSize) {
      this.pageSize2 = pageSize
      this.getList2(this.currentPage2)
    },
    handleCurrentChange2 (currentPage) {
      this.getList2(currentPage)
    },
    getList2 (currentPage) {
      this.currentPage2 = currentPage
      this.$axios.post('/evaluatedPage', {
        interval: this.interval2,
        resultkf: '所有',
        page: this.currentPage2,
        size: this.pageSize2,
        status: '已评估'
      })
        .then(successResponse => {
          if (successResponse.data.success) {
            this.list2 = successResponse.data.data.content
            if (this.list2.length < 1) {
              this.$message('查询时间段内无项目')
            }
          } else {
            this.$message(successResponse.data.msg)
          }
        })
        .catch(failResponse => {
        })
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
.el-dropdown-link{

}
</style>
