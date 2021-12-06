<!--原料下达页面-->
<template>
  <div>
    <TIdentityCheck></TIdentityCheck>
    <br><br>
    <el-row>
      <el-col :span="6">
        <div>订单编号:{{ project.projectid }}</div>
      </el-col>
      <el-col :span="6">
        <div>询单日期{{ project.createdate }}</div>
      </el-col>
      <el-col :span="6">
        <div>已经过{{ project.dura }}小时</div>
      </el-col>
      <el-col :span="6">
        <div>未评估</div>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="7">
        <div>需求产品名称：{{ project.projectname }}</div>
      </el-col>
      <el-col :span="7">
        <div>Cas号：{{ project.cas }}</div>
      </el-col>
      <el-col :span="7">
        <div>需求量：{{ project.projectsl }}</div>
      </el-col>
    </el-row>
    <!--    <el-row>-->
    <el-row>
      <el-col :span="12">
        <div>产品结构式图片</div>
        <br>
        <img v-bind:src="project.projectdetails" alt="图片未上传" />
      </el-col>
      <el-col :span="12">
        <div>客户对产品纯度，货期等方面特殊要求：</div>
        <div>{{ project.bz }}</div>
      </el-col>
    </el-row>
    <!--    </el-row>-->
    <br>
    <div>
      （一）请录入项目需要关键原料信息：
    </div>
    <br>

    <el-row>
      <el-col :span="2" :offset="2"><div>编号</div></el-col>
      <el-col :span="4"><div>原料名称</div></el-col>
      <el-col :span="4"><div>Cas号</div></el-col>
      <el-col :span="4"><div>需要数量</div></el-col>
      <el-col :span="4"><div>备注</div></el-col>
      <el-col :span="2"><div></div></el-col>
    </el-row>

    <el-row v-for="(item,index) in list1" :key="item.uuid">
      <el-col :span="2" :offset="2"><div>{{ index + 1 }}</div></el-col>
      <el-col :span="4"><el-input v-model="item.materialname"></el-input></el-col>
      <el-col :span="4"><el-input v-model="item.cas"></el-input></el-col>
      <el-col :span="4"><el-input v-model="item.materialsl"></el-input></el-col>
      <el-col :span="4"><el-input v-model="item.bz"></el-input></el-col>
      <el-col :span="2"><el-button circle icon="el-icon-delete" type="info" v-on:click="deletem(index)"></el-button></el-col>
    </el-row>

    <br>
    <el-button align="left" v-on:click="addnew">+添加</el-button>
    <br>
    <el-row>
      <el-col :offset="1" :span="8">
        <div align="left">从原料信息库查询添加</div>
      </el-col>
    </el-row>
    <el-row>
      <el-col :offset="1" :span="8">
        <el-input v-model="info" placeholder="请输入原料名称或cas号进行查询"></el-input>
      </el-col>
      <el-col :span="4">
        <el-button v-on:click="search">查询</el-button>
      </el-col>
    </el-row>
    <el-row v-if="Object.keys(list2).length>0">
      <el-col :offset="1" :span="16">
        <el-table
          :data="list2"
          height="350"
          style="width: 80%">
          <el-table-column label="原料名称" prop="materialname"> </el-table-column>
          <el-table-column label="cas" prop="cas"> </el-table-column>
          <el-table-column label="规格" prop="quantities"> </el-table-column>
          <el-table-column label="纯度" prop="qurity"> </el-table-column>
          <el-table-column label="备注" prop="bz"> </el-table-column>
<!--          <el-table-column fixed="right" label="操作" width="100">-->
<!--            <template slot-scope="scope">-->
<!--              <el-button size="small" type="text" @click="addfromlist2(scope.row)">添加</el-button>-->
<!--            </template>-->
<!--          </el-table-column>-->
        </el-table>
      </el-col>
    </el-row>
    <br><br>
    <div>{{ nm }}</div>
    <el-button type="primary" v-on:click="submit">提交原料信息</el-button>
    <br><br>
    <div>
      <el-button v-on:click="$router.replace('/Tindex')">返回</el-button>
    </div>
  </div>
</template>

<script>
import TIdentityCheck from './TIdentityCheck'

export default {
  name: 'T1',
  data: function () {
    return {
      account_id: window.sessionStorage.getItem('account_id'),
      projectid: '',
      project: '',
      info: '',
      nm: '',
      list1: [],
      list2: [],
      cnt: Number(0)
    }
  },
  created () {
    if (window.sessionStorage.getItem(this.account_id)) {
      this.projectid = window.sessionStorage.getItem(this.account_id)
    } else {
      this.$router.replace('/Tindex')
    }
    let _this = this
    this.$axios
      .post('/queryproj', {
        projectid: _this.projectid
      })
      .then(successResponse => {
        if (successResponse.data.success) {
          this.project = successResponse.data.data
          this.$axios
            .post('/lookpic', {
              imgpath: this.project.projectdetails
            })
            .then(successResponse => {
              // console.log(successResponse.data)
              if (successResponse.data.success) {
                // console.log(successResponse.data.data)
                this.project.projectdetails = 'data:image/png;base64,' + successResponse.data.data.base64id
              } else {
                this.$message(successResponse.data.msg)
              }
            })
            .catch(failResponse => {
            })
          console.log(this.project)
        } else {
          this.$message(successResponse.data.msg)
        }
      })
      .catch(failResponse => {
      })
    this.cnt = this.cnt + 1
    this.list1.push({
      materialname: '',
      cas: '',
      materialsl: '',
      bz: '',
      uuid: this.cnt,
      createname: window.sessionStorage.getItem('account_id'),
      projectid: this.projectid
    })
  },
  components: {
    TIdentityCheck
  },
  methods: {
    search () {
      var _this = this
      if (_this.info) {
        this.$axios
          .post('/searchmaterial', {
            info: _this.info
          })
          .then(successResponse => {
            if (successResponse.data.success) {
              this.list2 = successResponse.data.data
              if (this.list2.length <= 0) {
                this.$message(_this.info + '查询结果为空')
              }
              console.log(this.list2)
            } else {
              this.$message(successResponse.data.msg)
            }
          })
          .catch(failResponse => {
          })
      }
    },
    deletem (index) {
      this.list1.splice(index, 1)
    },
    addnew () {
      this.cnt = this.cnt + 1
      this.list1.push({
        materialname: '',
        cas: '',
        materialsl: '',
        bz: '',
        createname: window.sessionStorage.getItem('account_id'),
        uuid: this.cnt,
        projectid: this.projectid
      })
    },
    submit () {
      // console.log(this.list1)
      for (let m in this.list1) {
        // console.log(this.list1[m].materialname, this.list1[m].cas, this.list1[m].materialsl)
        if (!(this.list1[m].materialname && this.list1[m].cas && this.list1[m].materialsl)) {
          this.$message('原料名、Cas号、需要数量有缺失，请再次确认')
          return
        }
      }
      this.$axios
        .post('/xiada', this.list1)
        .then(successResponse => {
          if (successResponse.data.success) {
            this.$message('下达成功')
          } else {
            this.$message(successResponse.data.msg)
          }
        })
        .catch(failResponse => {
        })
      this.$router.replace('/Tindex')
    },
    addfromlist2 (x) {
      // console.log(index)
      // x.projectid = this.projectid
      // x.createname = this.account_id
      this.cnt = this.cnt + 1
      this.list1.push({
        projectid: this.projectid,
        createname: this.account_id,
        materialname: x.materialname,
        materialid: x.materialid,
        cas: x.cas,
        bz: '',
        uuid: this.cnt,
        materialsl: ''
      })
      console.log(this.list1)
    }
  }
}
</script>

<!--<style scoped>-->
<!--.el-row {-->
<!--  margin-bottom: 20px;-->

<!--&-->
<!--:last-child {-->
<!--  margin-bottom: 0;-->
<!--}-->

<!--}-->
<!--.el-col {-->
<!--  border-radius: 4px;-->
<!--}-->

<!--.bg-purple-dark {-->
<!--  background: #99a9bf;-->
<!--}-->

<!--.bg-purple {-->
<!--  background: #d3dce6;-->
<!--}-->

<!--.bg-purple-light {-->
<!--  background: #e5e9f2;-->
<!--}-->

<!--.grid-content {-->
<!--  border-radius: 4px;-->
<!--  min-height: 36px;-->
<!--}-->

<!--.row-bg {-->
<!--  padding: 10px 0;-->
<!--  background-color: #f9fafc;-->
<!--}-->

<!--.table {-->
<!--  width: 1000px;-->
<!--  height: 100px;-->
<!--  border: 1px solid #ccc;-->
<!--  border-collapse: collapse;-->
<!--  align-content: center;-->
<!--  alignment: center;-->
<!--  margin: auto;-->
<!--}-->

<!--.table td, .table th {-->
<!--  border: 1px solid #ccc;-->
<!--  padding: 5px;-->
<!--}-->
<!--</style>-->
