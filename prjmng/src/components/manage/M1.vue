<template>
  <div>
    <IdentityCheck></IdentityCheck>
    <el-container>
        <el-main>
          <el-table
          :data="account" border>
            <el-table-column label="工号" prop="accountid"> </el-table-column>
            <el-table-column label="姓名" prop="name">
              <template slot-scope="scope">
                <el-input v-model="scope.row.name"></el-input>
              </template>
            </el-table-column>
            <el-table-column label="密码" prop="keywords">
              <template slot-scope="scope">
                <el-input v-model="scope.row.keywords"></el-input>
              </template>
            </el-table-column>
            <el-table-column label="账户类型" prop="typeid">
              <template slot-scope="scope">
                <el-select style="width: 90px" v-model="scope.row.typeid">
                  <el-option
                    v-for="item in identity"
                    :key="item.typeid"
                    :label="item.typeid"
                    :value="item.typeid">
                  </el-option>
                </el-select>
              </template>
            </el-table-column>
            <el-table-column label="级别">
              <template slot-scope="scope">
                <span v-if="identity[(scope.row.typeid-1)] && identity[(scope.row.typeid-1)].rank">
                  {{ identity[(scope.row.typeid-1)].rank }}
                </span>
              </template>
            </el-table-column>
            <el-table-column label="用户角色">
              <template slot-scope="scope">
                <span v-if="identity[scope.row.typeid-1] && identity[scope.row.typeid-1].type">
                  {{ identity[scope.row.typeid-1].type }}
                </span>
              </template>
            </el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-button circle icon="el-icon-delete" type="info" v-on:click="deletem(scope.row, scope.row.accountid)"></el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-main>
      </el-container>
    <br>
    <div align="center">
      <el-button type="primary" v-on:click="addnew">添加新账户</el-button>
      <el-button type="primary" v-on:click="submit">提交修改</el-button>
      <el-button type="primary" v-on:click="$router.replace('/manageindex')">返回</el-button>
    </div>
  </div>
</template>

<script>
import IdentityCheck from './IdentityCheck'

export default {
  name: 'M1',
  inject: ['reload'],
  components: {IdentityCheck},
  data () {
    return {
      account: [],
      identity: [],
      date: new Date(),
      accountid: window.sessionStorage.getItem('account_id')
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
    this.$axios
      .post('/managelogin', {})
      .then(successResponse => {
        if (successResponse.data.success) {
          this.account = successResponse.data.data
        }
      })
    this.$axios
      .post('/identity', {})
      .then(successResponse => {
        if (successResponse.data.success) {
          this.identity = successResponse.data.data
        }
      })
  },
  methods: {
    submit () {
      this.$axios
        .post('/updateaccount', this.account)
        .then(successResponse => {
          if (successResponse.data.success) {
            this.$message.success('修改成功')
            this.reload()
          } else {
            this.$message(successResponse.data.msg)
          }
        })
        .catch(failResponse => {
          // console.log(this.loginForm)
        })
    },
    addnew () {
      this.account.push({
        keywords: '',
        accountname: '',
        name: '',
        typeid: 1
      })
    },
    deletem (x, index) {
      if (x.name === '') {
        this.$message.success('删除成功')
        this.reload()
      } else {
        this.$axios
          .post('/deleteaccount', {
            id: x.accountid
          })
          .then(successResponse => {
            if (successResponse.data.success) {
              this.$message.success('删除成功')
              this.account.splice(index, 1)
              this.reload()
            } else {
              this.$message(successResponse.data.msg)
            }
          })
          .catch(failResponse => {
            this.$message.error('请先提交修改后再进行删除')
            this.reload()
          })
      }
    },
    menuClick (index) {
      this.$router.push(index)
    },
    handleCommand (command) {
      window.sessionStorage.removeItem('account_id')
      window.sessionStorage.removeItem('usertype')
      window.sessionStorage.removeItem('islogin')
      this.$router.replace('/A')
    }
  }
}
</script>

<style scoped>
.table {
  width: 1000px;
  height: 100px;
  border: 1px solid #ccc;
  border-collapse: collapse;
  align-content: center;
  alignment: center;
  margin: auto;
}
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
</style>
