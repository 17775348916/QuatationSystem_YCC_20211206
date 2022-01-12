<template>
  <div>
    <IdentityCheckAd></IdentityCheckAd>
    <el-header>用户账号权限密码管理</el-header>
    <table class="table" style="width: 100%;">
      <thead>
      <tr class="table td">
        <th>报价模式</th>
        <th>氢谱单价</th>
        <th>碳谱单价</th>
        <th>质谱单价</th>
        <th>(测试/溶剂)比率</th>
        <th>企业月工资</th>
        <th>高校月工资</th>
        <th>企业报价系数</th>
        <th>高校报价系数</th>
        <th>产品报价系数</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="x in model" :key="x.modleid">
        <th>{{ x.modelname }}</th>
        <th><el-input v-model="x.hprice" oninput="value=value.replace(/[^0-9.]/g,'')"></el-input></th>
        <th><el-input v-model="x.cprice" oninput="value=value.replace(/[^0-9.]/g,'')"></el-input></th>
        <th><el-input v-model="x.mprice" oninput="value=value.replace(/[^0-9.]/g,'')"></el-input></th>
        <th><el-input v-model="x.rate" oninput="value=value.replace(/[^0-9.]/g,'')"></el-input></th>
        <th><el-input v-model="x.firmwage" oninput="value=value.replace(/[^0-9.]/g,'')"></el-input></th>
        <th><el-input v-model="x.collegewage" oninput="value=value.replace(/[^0-9.]/g,'')"></el-input></th>
        <th><el-input v-model="x.firmcoefficient" oninput="value=value.replace(/[^0-9.]/g,'')"></el-input></th>
        <th><el-input v-model="x.collegecoefficient" oninput="value=value.replace(/[^0-9.]/g,'')"></el-input></th>
        <th><el-input v-model="x.productcoefficient" oninput="value=value.replace(/[^0-9.]/g,'')"></el-input></th>
      </tr>
      </tbody>
    </table>
    <el-button v-on:click="submit">提交修改</el-button>
    <el-button v-on:click="$router.replace('/manageindex')">返回</el-button>
  </div>
</template>

<script>
import IdentityCheckAd from './IdentityCheckAd'

export default {
  name: 'M3',
  data: function () {
    return {
      model: ''
    }
  },
  components: {
    IdentityCheckAd
  },
  created () {
    this.$axios
      .post('/lookmodel', {})
      .then(successResponse => {
        if (successResponse.data.success) {
          this.model = successResponse.data.data
          console.log(this.model)
        }
      })
      .catch(failResponse => {
        // console.log(this.loginForm)
      })
  },
  methods: {
    submit () {
      this.$axios
        .post('/updatemodel', this.model)
        .then(successResponse => {
          if (successResponse.data.success) {
            this.$message('修改成功')
          }
        })
        .catch(failResponse => {
          // console.log(this.loginForm)
        })
    }
  }
}
</script>

<style scoped>
.table {
  width: 300px;
  height: 100px;
  border: 1px solid #ccc;
  border-collapse: collapse;
}

.table td, .table th {
  border: 1px solid #ccc;
  padding: 5px;
}
</style>
