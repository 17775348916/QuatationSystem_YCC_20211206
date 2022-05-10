<template>
  <div>
    <IdentityCheck></IdentityCheck>
        <el-main>
          <el-table
            :data="model"
            style="width: 100%"  border>
            <el-table-column
              prop="modelname"
              label="报价模式">
            </el-table-column>
            <el-table-column label="氢谱单价">
              <template slot-scope="scope">
                <el-input v-model="scope.row.hprice" oninput="value=value.replace(/[^0-9.]/g,'')"></el-input>
              </template>
            </el-table-column>
            <el-table-column
              label="碳谱报价">
              <template slot-scope="scope">
                <el-input v-model="scope.row.cprice" oninput="value=value.replace(/[^0-9.]/g,'')"></el-input>
              </template>
            </el-table-column>
            <el-table-column
              label="质谱报价">
              <template slot-scope="scope">
                <el-input v-model="scope.row.mprice" oninput="value=value.replace(/[^0-9.]/g,'')"></el-input>
              </template>
            </el-table-column>
            <el-table-column
              label="(测试/溶剂)比率">
              <template slot-scope="scope">
                <el-input v-model="scope.row.rate" oninput="value=value.replace(/[^0-9.]/g,'')"></el-input>
              </template>
            </el-table-column>
            <el-table-column
              label="企业月工资">
              <template slot-scope="scope">
                <el-input v-model="scope.row.firmwage" oninput="value=value.replace(/[^0-9.]/g,'')"></el-input>
              </template>
            </el-table-column>
            <el-table-column
              label="高校月工资">
              <template slot-scope="scope">
                <el-input v-model="scope.row.collegewage" oninput="value=value.replace(/[^0-9.]/g,'')"></el-input>
              </template>
            </el-table-column>
            <el-table-column
              label="企业报价系数">
              <template slot-scope="scope">
                <el-input v-model="scope.row.firmcoefficient" oninput="value=value.replace(/[^0-9.]/g,'')"></el-input>
              </template>
            </el-table-column>
            <el-table-column
              label="产品报价系数">
              <template slot-scope="scope">
                <el-input v-model="scope.row.collegecoefficient" oninput="value=value.replace(/[^0-9.]/g,'')"></el-input>
              </template>
            </el-table-column>
            <el-table-column
              label="高校报价系数">
              <template slot-scope="scope">
                <el-input v-model="scope.row.productcoefficient" oninput="value=value.replace(/[^0-9.]/g,'')"></el-input>
              </template>
            </el-table-column>
            <el-table-column
              prop="updatedate"
              label="修改时间">
            </el-table-column>
          </el-table>
        </el-main>
    <div align="center">
      <el-button type="primary" v-on:click="submit">提交修改</el-button>
      <el-button type="primary" v-on:click="$router.replace('/manageindex')">返回</el-button>
    </div>
  </div>
</template>

<script>
import IdentityCheck from './IdentityCheck'

export default {
  name: 'M3',
  data () {
    return {
      model: [],
      accountid: window.sessionStorage.getItem('account_id')
    }
  },
  components: {
    IdentityCheck
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
      .post('/lookmodel', {})
      .then(successResponse => {
        if (successResponse.data.success) {
          this.model = successResponse.data.data
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
            this.$axios
              .post('/lookmodel', {})
              .then(successResponse => {
                if (successResponse.data.success) {
                  this.model = successResponse.data.data
                }
              })
              .catch(failResponse => {
              })
          }
        })
        .catch(failResponse => {
          // console.log(this.loginForm)
        })
    },
    menuClick (index) {
      this.$router.push(index)
    },
    handleCommand (command) {
      window.sessionStorage.removeItem('account_id')
      window.sessionStorage.removeItem('usertype')
      window.sessionStorage.removeItem('islogin')
      this.$router.replace('/managelogin')
    }
  }
}
</script>

<style scoped>
/*.table {*/
/*  width: 300px;*/
/*  height: 100px;*/
/*  border: 1px solid #ccc;*/
/*  border-collapse: collapse;*/
/*}*/

/*.table td, .table th {*/
/*  border: 1px solid #ccc;*/
/*  padding: 5px;*/
/*}*/
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
