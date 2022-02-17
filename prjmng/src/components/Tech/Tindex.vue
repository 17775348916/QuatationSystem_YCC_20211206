<template>
  <div>
    <TIdentityCheck></TIdentityCheck>
    <el-container>
      <el-header style="text-align: right; font-size: 16px">
        <div class="title">
          ChemEagle Brain 1.0
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
      usertype: window.sessionStorage.getItem('usertype')
    }
  },
  components: {
    TIdentityCheck
  },
  created () {

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
      var _this = this
      this.$axios
        .post('/unevaluatedPage', {
          page: 1,
          size: 5,
          interval: _this.interval1,
          status: '未评估'
        })
        .then(successResponse => {
          if (successResponse.data.success) {
            this.list1 = successResponse.data.data.content
            if (this.list1.length < 1) {
              this.$message('查询时间段内无项目')
            }
            this.total1 = successResponse.data.data.totalElements
          } else {
            this.$message(successResponse.data.msg)
          }
        })
        .catch(failResponse => {
        })
      this.$forceUpdate()
    },
    showhavezt () {
      var _this = this
      this.$axios.post('/evaluatedPage', {
        interval: _this.interval2,
        resultkf: _this.project_result_kf,
        page: 1,
        size: 5,
        status: '已评估'
      })
        .then(successResponse => {
          if (successResponse.data.success) {
            this.list2 = successResponse.data.data.content
            // console.log(this.list2)
            if (this.list2.length < 1) {
              this.$message('查询时间段内无项目')
            } else {
              this.total2 = successResponse.data.data.totalElements
              for (let m in this.list2) {
                this.$axios
                  .post('/lookpic', {
                    imgpath: this.list2[m].projectdetails
                  })
                  .then(successResponse => {
                    if (successResponse.data.success) {
                      this.list2[m].projectdetails = 'data:image/png;base64,' + successResponse.data.data.base64id
                    } else {
                      this.$message(successResponse.data.msg)
                    }
                  })
                  .catch(failResponse => {
                  })
                if (this.list2[m].projectZt.projectztjs === '已评估-不可行') {
                  this.$axios
                    .post('/querynofeasible', {
                      projectid: this.list2[m].projectid
                    })
                    .then(successResponse => {
                      if (successResponse.data.success) {
                        this.$set(this.list2[m], 'reasonsjs', successResponse.data.data.reasonsjs)
                      } else {
                        this.$message(successResponse.data.msg)
                      }
                    })
                    .catch(failResponse => {
                    })
                } else if (this.list2[m].projectZt.projectztjs === '已评估-可行') {
                  this.$axios
                    .post('/queryfeasible', {
                      projectid: this.list2[m].projectid
                    })
                    .then(successResponse => {
                      if (successResponse.data.success) {
                        // console.log(successResponse.data.data)
                        this.$set(this.list2[m], 'hsl', successResponse.data.data.hsl)
                        this.$set(this.list2[m], 'msl', successResponse.data.data.msl)
                        this.$set(this.list2[m], 'csl', successResponse.data.data.csl)
                        this.$set(this.list2[m], 'isdifficultjs', successResponse.data.data.isdifficultjs)
                        this.$set(this.list2[m], 'timeneeded', successResponse.data.data.timeneeded)
                        this.$set(this.list2[m], 'bz', successResponse.data.data.bz)
                        this.$set(this.list2[m], 'jllsjcs', successResponse.data.data.jllsjcs)
                        this.$set(this.list2[m], 'dtlxsjcs', successResponse.data.data.dtlxsjcs)
                        this.$set(this.list2[m], 'zsjcs', successResponse.data.data.zsjcs)
                        let strs = successResponse.data.data.papersjs.split('||')
                        let paperType = []
                        console.log('strs', strs)
                        strs.forEach(item => {
                          let strss = item.split('&&')
                          paperType.push(strss[strss.length - 1])
                        })
                        this.$set(this.list2[m], 'paperType', paperType)
                        this.$set(this.list2[m], 'papersjs', strs)
                        console.log(paperType)
                      } else {
                        this.$message(successResponse.data.msg)
                      }
                    })
                    .catch(failResponse => {
                    })
                }
              }
              console.log(this.list2)
            }
          } else {
            this.$message(successResponse.data.msg)
          }
        })
        .catch(failResponse => {
        })
    },
    lookMaterial: function (x) {
      window.sessionStorage.setItem(this.account_id, x)
      this.$router.replace({path: '/T1'})
    },
    feedback (x) {
      this.$axios
        .post('/JS/feedback', {
          projectid: x
        })
        .then(successResponse => {
          if (successResponse.data.success) {
            if (successResponse.data.code === '100') {
              this.$confirm(successResponse.data.msg, '提示', {
                confirmButtonText: '直接进入评估-项目不可行',
                cancelButtonText: '取消'
              }).then(() => {
                window.sessionStorage.setItem(this.account_id, x)
                this.$router.replace('/T2')
              }).catch(() => {
              })
            } else {
              this.$confirm(successResponse.data.data, '提示', {
                confirmButtonText: '进行项目评估',
                cancelButtonText: '取消'
              }).then(() => {
                window.sessionStorage.setItem(this.account_id, x)
                this.$router.replace('/T2')
              }).catch(() => {
              })
            }
            // this.$message()
          } else {
            this.$message(successResponse.data.msg)
          }
        })
        .catch(failResponse => {
        })
    },
    pgdirect (x) {
      window.sessionStorage.setItem(this.account_id, x)
      this.$router.replace('/T2')
    },
    goback () {
      window.sessionStorage.removeItem('account_id')
      window.sessionStorage.removeItem('usertype')
      this.$router.replace('/A')
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
          if (successResponse.data.success) {
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
      this.$axios.post('/evaluatedPage', {
        interval: this.interval2,
        resultkf: this.project_result_kf,
        page: currentPage,
        size: this.pageSize2,
        status: '已评估'
      })
        .then(successResponse => {
          if (successResponse.data.success) {
            this.list2 = successResponse.data.data.content
            // console.log(this.list2)
            if (this.list2.length < 1) {
              this.$message('查询时间段内无项目')
            } else {
              for (let m in this.list2) {
                this.$axios
                  .post('/lookpic', {
                    imgpath: this.list2[m].projectdetails
                  })
                  .then(successResponse => {
                    if (successResponse.data.success) {
                      // console.log(successResponse.data.data)
                      this.list2[m].projectdetails = 'data:image/png;base64,' + successResponse.data.data.base64id
                    } else {
                      this.$message(successResponse.data.msg)
                    }
                  })
                  .catch(failResponse => {
                  })
                if (this.list2[m].projectZt.projectztjs === '已评估-不可行') {
                  this.$axios
                    .post('/querynofeasible', {
                      projectid: this.list2[m].projectid
                    })
                    .then(successResponse => {
                      if (successResponse.data.success) {
                        this.$set(this.list2[m], 'reasonsjs', successResponse.data.data.reasonsjs)
                      } else {
                        this.$message(successResponse.data.msg)
                      }
                    })
                    .catch(failResponse => {
                    })
                } else if (this.list2[m].projectZt.projectztjs === '已评估-可行') {
                  this.$axios
                    .post('/queryfeasible', {
                      projectid: this.list2[m].projectid
                    })
                    .then(successResponse => {
                      if (successResponse.data.success) {
                        // console.log(successResponse.data.data)
                        this.$set(this.list2[m], 'hsl', successResponse.data.data.hsl)
                        this.$set(this.list2[m], 'msl', successResponse.data.data.msl)
                        this.$set(this.list2[m], 'csl', successResponse.data.data.csl)
                        this.$set(this.list2[m], 'isdifficultjs', successResponse.data.data.isdifficultjs)
                        this.$set(this.list2[m], 'timeneeded', successResponse.data.data.timeneeded)
                        this.$set(this.list2[m], 'bz', successResponse.data.data.bz)
                        this.$set(this.list2[m], 'jllsjcs', successResponse.data.data.jllsjcs)
                        this.$set(this.list2[m], 'dtlxsjcs', successResponse.data.data.dtlxsjcs)
                        this.$set(this.list2[m], 'zsjcs', successResponse.data.data.zsjcs)
                        let strs = successResponse.data.data.papersjs.split('||')
                        let paperType = []
                        console.log('strs', strs)
                        strs.forEach(item => {
                          let strss = item.split('&&')
                          paperType.push(strss[strss.length - 1])
                        })
                        this.$set(this.list2[m], 'paperType', paperType)
                        this.$set(this.list2[m], 'papersjs', strs)
                        console.log(paperType)
                      } else {
                        this.$message(successResponse.data.msg)
                      }
                    })
                    .catch(failResponse => {
                    })
                }
              }
              console.log(this.list2)
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

.welcomeTitle{
  font-size:30px;
  font-family:华文楷体;
  margin-top:50px;
  color:#409eff
}
.el-dropdown-link{

}
</style>
