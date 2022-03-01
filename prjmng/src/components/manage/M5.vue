<template>
  <div>
    <IdentityCheck></IdentityCheck>
    <br>
    <div>
      （一）请查询或更新原数据库中的原料信息
    </div>
    <br>
    <el-row>
      <el-col style="margin-left:300px" :span="10">
        <el-input v-model="info" placeholder="请输入原料名称或cas号进行查询" @keydown.enter.native="search"></el-input>
      </el-col>
      <el-col :span="3">
        <el-button v-on:click="search">查询</el-button>
      </el-col>
    </el-row>
    <br>
    <div v-if="newm.askflag===true">
      <div v-if="Object.keys(list1).length>0">
        <el-table v-if="Object.keys(list1).length>0"
                  :data="list1"
                  height="400"
                  border
                  style="width: 100%">
          <el-table-column
            prop= "materialid"
            label="序号" width="50px">
          </el-table-column>
          <el-table-column
            prop= "materialname"
            label="原料名称" width="150px">
            <template slot-scope="scope">
              <el-input v-model="scope.row.materialname"></el-input>
            </template>
          </el-table-column>
          <el-table-column
            prop= "cas"
            label="cas号" width="120px">
            <template slot-scope="scope">
              <el-input v-model="scope.row.cas"></el-input>
            </template>
          </el-table-column>
          <el-table-column
            prop= "quantities"
            label="数量" width="90px">
            <template slot-scope="scope">
              <el-input v-model="scope.row.quantities"></el-input>
            </template>
          </el-table-column>
          <el-table-column
            label="纯度" width="80px">
            <template slot-scope="scope">
              <el-input v-model="scope.row.qurity"></el-input>
            </template>
          </el-table-column>
          <el-table-column
            prop= "price"
            label="价格">
            <template slot-scope="scope">
              <el-input v-model="scope.row.price"></el-input>
            </template>
          </el-table-column>

          <el-table-column
            prop= "provider"
            label="供应商">
            <template slot-scope="scope">
              <el-input v-model="scope.row.provider"></el-input>
            </template>
          </el-table-column>
          <el-table-column
            prop= "providerphone"
            label="联系方式" >
            <template slot-scope="scope">
              <el-input v-model="scope.row.providerphone"></el-input>
            </template>
          </el-table-column>
          <el-table-column
            prop= "createdate"
            label="录入时间" width="105px">
          </el-table-column>
          <el-table-column
            prop= "updatedate"
            label="修改时间" width="105px">
          </el-table-column>
          <el-table-column
            label="备注" width="80px">
            <template slot-scope="scope">
              <el-input v-model="scope.row.bz"></el-input>
            </template>
          </el-table-column>
          <el-table-column
            label="操作" width="100px">
            <template slot-scope="scope">
              <el-button round type="primary" v-on:click="update(scope.row)">修改</el-button>
            </template>
          </el-table-column>
          <el-table-column
            label="操作" width="65px">
            <template slot-scope="scope">
              <el-button circle icon="el-icon-delete" type="info" v-on:click="deletem(scope.row, index)"></el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div v-else>
        未查询到相关结果
      </div>
    </div>
    <hr>
    <div>（二）请录入原料数据库中没有的原料信息</div>
    <br>
    <table class="table">
      <tbody>
      <tr>
        <td>
          <el-input v-model="newm.materialname" placeholder="原料名称"></el-input>
        </td>
        <td>
          <el-input v-model="newm.cas" placeholder="Cas号"></el-input>
        </td>
        <td>
          <el-input v-model="newm.quantities" placeholder="数量"></el-input>
        </td>
        <td>
          <el-input v-model="newm.qurity" placeholder="纯度"></el-input>
        </td>
        <td>
          <el-input v-model="newm.price" placeholder="价格"></el-input>
        </td>
        <td>
          <el-input v-model="newm.provider" placeholder="供应商"></el-input>
        </td>
        <td>
          <el-input v-model="newm.providerphone" placeholder="联系方式"></el-input>
        </td>
        <td>
          <el-input v-model="newm.bz" placeholder="备注"></el-input>
        </td>
      </tr>
      </tbody>
    </table>
    <el-button type="primary" v-on:click="insert" style="margin-top:10px">新建该原料条目</el-button>
  </div>
</template>

<script>
import IdentityCheck from './IdentityCheck'

export default {
  name: 'M5',
  data () {
    return {
      info: '',
      list1: [],
      newm: {
        materialname: '',
        cas: '',
        askflag: false,
        quantities: '',
        qurity: '0.95',
        price: '',
        provider: '',
        providerphone: '',
        bz: ''
      }
    }
  },
  methods: {
    search () {
      var _this = this
      this.$axios
        .post('/searchmaterial', {
          info: _this.info
        })
        .then(successResponse => {
          if (successResponse.data.success) {
            this.list1 = successResponse.data.data
            this.newm.askflag = true
            console.log(this.list1)
          } else {
            this.$message(successResponse.data.msg)
          }
        })
        .catch(failResponse => {
        })
    },
    update (x) {
      x.updatename = window.sessionStorage.getItem('account_id')
      this.$confirm('此操作将更新' + x.cas + '  ' + x.materialname + ', 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios
          .post('/updatematerial', x)
          .then(successResponse => {
            if (successResponse.data.success) {
              this.$message('修改成功')
              this.$axios
                .post('/searchmaterial', {
                  info: this.info
                })
                .then(successResponse => {
                  if (successResponse.data.success) {
                    this.list1 = successResponse.data.data
                    this.newm.askflag = true
                    console.log(this.list1)
                  } else {
                    this.$message(successResponse.data.msg)
                  }
                })
                .catch(failResponse => {
                })
            } else {
              this.$message(successResponse.data.msg)
            }
          })
          .catch(failResponse => {
          })
      })
    },
    insert () {
      var _this = this
      if (!(_this.newm.materialname)) {
        this.$message('原料名称缺失，请补充')
        return
      }
      if (!(_this.newm.cas)) {
        this.$message('原料Cas缺失，请补充')
        return
      }
      this.$axios
        .post('/insertmaterial', {
          materialname: _this.newm.materialname,
          cas: _this.newm.cas,
          quantities: _this.newm.quantities,
          qurity: _this.newm.qurity,
          price: _this.newm.price,
          provider: _this.newm.provider,
          providerphone: _this.newm.providerphone,
          bz: _this.newm.bz,
          createname: window.sessionStorage.getItem('account_id')
        })
        .then(successResponse => {
          if (successResponse.data.success) {
            this.$message('添加成功')
            this.newm.materialname = ''
            this.newm.cas = ''
            this.newm.quantities = ''
            this.newm.qurity = '0.95'
            this.newm.price = ''
            this.newm.provider = ''
            this.newm.providerphone = ''
            this.newm.bz = ''
          } else {
            this.$message(successResponse.data.msg)
          }
        })
        .catch(failResponse => {
        })
    },
    deletem (x, index) {
      this.$confirm('此操作将删除' + x.cas + '  ' + x.materialname + ', 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios
          .post('/deletematerial', {
            id: x.materialid
          })
          .then(successResponse => {
            if (successResponse.data.success) {
              this.$message('删除成功')
              for (let m in this.list1) {
                if (this.list1[m] === x) {
                  this.list1.splice(m, 1)
                  break
                }
              }
              this.$options.methods.search()
            } else {
              this.$message(successResponse.data.msg)
            }
          })
          .catch(failResponse => {
          })
      })
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
    if (window.sessionStorage.getItem('CNeedCommunicateWithOrdersInterval') != null) {
      window.sessionStorage.removeItem('CNeedCommunicateWithOrdersInterval')
    }
    if (window.sessionStorage.getItem('CUnevaluatedInterval') != null) {
      window.sessionStorage.removeItem('CUnevaluatedInterval')
    }
  },
  components: {
    IdentityCheck
  }
}
</script>

<style scoped>
</style>
