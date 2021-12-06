<template>
  <div>
    <IdentityCheckAd></IdentityCheckAd>
    <el-header>用户账号权限密码管理</el-header>
    <table class="table">
      <thead>
      <tr>
        <th>工号</th>
        <th>姓名</th>
        <th>密码</th>
        <th>账户类型</th>
        <th>级别</th>
        <th>用户角色</th>
        <th></th>
      </tr>
      </thead>
      <tbody>

      <tr v-for="x in account" :key="x.accountid">
        <td>
          {{x.accountid}}
        </td>
        <td>
          <el-input v-model="x.name"></el-input>
        </td>
        <td>
          <el-input v-model="x.keywords"></el-input>
        </td>
        <td>
          <el-select style="width: 90px" v-model="x.typeid">
            <el-option
              v-for="item in identity"
              :key="item.id"
              :label="item.typeid"
              :value="item.typeid">
            </el-option>
          </el-select>
        </td>
        <td>{{ identity[x.typeid - 1].rank }}</td>
        <td>{{ identity[x.typeid - 1].type }}</td>
        <td>
          <el-button circle icon="el-icon-delete" type="info" v-on:click="deletem(x, index)"></el-button>
        </td>
      </tr>
      </tbody>
    </table>
    <br>
    <el-button v-on:click="addnew">添加新账户</el-button>
    <el-button v-on:click="submit">提交修改</el-button>
    <el-button v-on:click="$router.replace('/manageindex')">返回</el-button>
  </div>
</template>

<script>
import IdentityCheckAd from './IdentityCheckAd'

export default {
  name: 'M1',
  components: {IdentityCheckAd},
  data: function () {
    return {
      account: [],
      identity: [],
      date: new Date()
    }
  },
  created () {
    this.$axios
      .post('/managelogin', {})
      .then(successResponse => {
        if (successResponse.data.success) {
          this.account = successResponse.data.data
          console.log(successResponse.data)
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
          console.log(successResponse.data)
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
            // this.identity = successResponse.data.identity
            // console.log(successResponse)
            // console.log(successResponse.data)
            this.$message('修改已上传')
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
          } else {
            this.$message(successResponse.data.msg)
          }
        })
        .catch(failResponse => {
        })
    },
    components: {
      IdentityCheckAd
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
</style>
