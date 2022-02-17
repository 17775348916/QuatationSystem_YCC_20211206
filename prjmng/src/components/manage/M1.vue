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
                {{ identity[scope.row.typeid-1].rank }}
              </template>
            </el-table-column>
            <el-table-column label="用户角色">
              <template slot-scope="scope">
                {{ identity[scope.row.typeid-1].type }}
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
  components: {IdentityCheck},
  data: function () {
    return {
      account: [],
      identity: [],
      date: new Date(),
      accountid: window.sessionStorage.getItem('account_id')
    }
  },
  created () {
    this.$axios
      .post('/managelogin', {})
      .then(successResponse => {
        if (successResponse.data.success) {
          this.account = successResponse.data.data
          console.log('这是账号', successResponse.data)
        }
      })
      .catch(failResponse => {
        // console.log(this.loginForm)
      })
    this.$axios
      .post('/identity', {})
      .then(successResponse => {
        if (successResponse.data.success) {
          this.identity = successResponse.data.data
          console.log('这是identity', successResponse.data.data)
          // console.log(successResponse.data)
          // this.$message('%%%')
        }
      })
      .catch(failResponse => {
        // console.log(this.loginForm)
      })
  },
  methods: {
    submit () {
      // for (let index in this.oldaccount) {
      //   // console.log(index + "->" + data[index]);
      //   if (this.oldaccount[index] !== this.account[index].name) {
      //     this.account[index].updatedata = this.year + '-' + (this.month + 1) + '-' + this.day + ' ' + this.hour + ':00:00'
      //   }
      // }
      this.$axios
        .post('/updateaccount', this.account)
        .then(successResponse => {
          if (successResponse.data.success) {
            this.$message('修改已上传')
            location.reload()
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
        // createdate: today.substr(0, 10) + ' ' + hour + ':00:00',
        // updatedata: today.substr(0, 10) + ' ' + hour + ':00:00'
      })
      // console.log(this.account)
    },
    deletem: function (x, index) {
      this.$axios
        .post('/deleteaccount', {
          id: x.accountid
        })
        .then(successResponse => {
          if (successResponse.data.success) {
            this.$message('删除成功')
            this.account.splice(index, 1)
            location.reload()
          } else {
            this.$message(successResponse.data.msg)
          }
        })
        .catch(failResponse => {
        })
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
