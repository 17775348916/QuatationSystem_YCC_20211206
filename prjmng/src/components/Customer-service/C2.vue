<template>
  <div>
    <CIdentityCheck></CIdentityCheck>
    <Chead></Chead>
    <br><br>
    <div>（一）请查询或更新原料数据库中的原料信息：</div>
    <br>
    <el-form>
      <el-form-item>
        <el-col :offset="6" :span="6">
          <el-input v-model="info" placeholder="请输入原料名称或cas号进行查询"></el-input>
        </el-col>
        <el-col :span="2">
          <el-button v-on:click="search">查询</el-button>
        </el-col>
      </el-form-item>
    </el-form>
    <div v-if="newm.askflag===true">
      <div v-if="Object.keys(list1).length>0">
        <div>
          查询的原料信息如下：
        </div>
        <br><br>
        <table class="table">
          <thead>
          <tr>
            <th>原料名称</th>
            <th>cas号</th>
            <th>数量</th>
            <th>纯度</th>
            <th>价格</th>
            <th>供应商</th>
            <th>联系方式</th>
            <th>录入时间</th>
            <th>备注</th>
            <th></th>
            <!--        <th></th>-->
          </tr>
          </thead>
          <tbody>
          <tr v-for="x in list1" :key="x.accountid">
            <td>
              <el-input v-model="x.materialname"></el-input>
            </td>
            <td>
              <el-input v-model="x.cas"></el-input>
            </td>
            <td>
              <el-input v-model="x.quantities"></el-input>
            </td>
            <td>
              <el-input v-model="x.qurity"></el-input>
            </td>
            <td>
              <el-input v-model="x.price"></el-input>
            </td>
            <td>
              <el-input v-model="x.provider"></el-input>
            </td>
            <td>
              <el-input v-model="x.providerphone"></el-input>
            </td>
            <td>
              <div v-text="x.createdate"></div>
            </td>
            <td>
              <el-input v-model="x.bz"></el-input>
            </td>
            <td>
              <el-button round type="primary" v-on:click="update(x)">修改</el-button>
            </td>
            <td>
              <el-button circle icon="el-icon-delete" type="info" v-on:click="deletem(x, index)"></el-button>
            </td>
            <!--        <td><el-button v-on:click="dele(x)">删除</el-button></td>-->
          </tr>
          </tbody>
        </table>
      </div>
      <div v-else>
        未查询到相关结果
      </div>
    </div>
    <br><br>
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
    <br>
    <el-button type="primary" v-on:click="insert">新建该原料条目</el-button>
  </div>
</template>

<script>
import CIdentityCheck from './CIdentityCheck'
import Chead from './Chead'

export default {
  name: 'C2',
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
            // console.log(this.askflag)
            // for(x in this.list1) {
            //   x.updatename = windows.sessionStorage.getItem('account_id')
            // }
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
      // if (!(_this.newm.quantities)) {
      //   this.$message('原料数量缺失，请补充')
      //   return
      // }
      // if (!(_this.newm.qurity)) {
      //   this.$message('原料纯度缺失，请补充')
      //   return
      // }
      // if (!(_this.newm.price)) {
      //   this.$message('原料价格缺失，请补充')
      //   return
      // }
      // if (!(_this.newm.provider)) {
      //   this.$message('原料供应商缺失，请补充')
      //   return
      // }
      // if (!(_this.newm.providerphone)) {
      //   this.$message('原料供应商联系方式缺失，请补充')
      //   return
      // }
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
  components: {
    CIdentityCheck,
    Chead
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
