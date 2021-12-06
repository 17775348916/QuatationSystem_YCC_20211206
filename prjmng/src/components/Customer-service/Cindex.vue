<template>
  <div>
    <CIdentityCheck></CIdentityCheck>
    <Chead></Chead>
    <h4>（一）请查看项目信息（技术未评估）：</h4>
    <br>下拉菜单选择时间范围：
    <select v-model="interval1" placeholder="时间范围">
      <option label="当天" value="1">当天</option>
      <option label="近3天" value="3">近3天</option>
      <option label="近1周" value="7">近1周</option>
      <option label="近1月" value="30">近1月</option>
      <option label="所有" value="30000">所有</option>
    </select>
    <button v-on:click="showunzt()">查询</button>

    <table class="table" v-if="Object.keys(list1).length>0">
      <thead>
      <tr>
        <th>项目编号</th>
        <th>询单日期</th>
        <th>已经过时间（小时）</th>
        <th>状态</th>
        <th>原料任务</th>
        <!--          <th></th>-->
        <th>反馈状况</th>
        <th></th>
      </tr>
      </thead>
      <tbody v-for="x in list1" :key="x.projectid">
      <tr>
        <th>{{ x.projectid }}</th>
        <th>{{ x.createdate.substr(0, 10) }}</th>
        <th>{{ x.dura }}小时</th>
        <th>未评估</th>
        <th>{{ x.istaskjs }}</th>
        <th>{{ x.fkztkf }}</th>
        <th>
          <div v-if="x.istaskjs === '已下达' && x.fkztkf === '未反馈'">
            <el-button small v-on:click="feedback(x.projectid)">查看</el-button>
          </div>
<!--          <div v-else>-->
<!--            <el-button v-on:click="lookMaterial(x.projectid)">确认原料信息</el-button>-->
<!--          </div>-->
        </th>
      </tr>
      </tbody>
    </table>

    <hr>
    <h4>（二）请查看项目信息（技术已评估）：</h4>
    <br>下拉菜单选择时间范围：
    <select v-model="interval2" placeholder="时间范围">
      <option label="当天" value="1">当天</option>
      <option label="近3天" value="3">近3天</option>
      <option label="近1周" value="7">近1周</option>
      <option label="近1月" value="30">近1月</option>
      <option label="所有" value="30000">所有</option>
    </select>
    <!--    <h4>此处需要从后端数据库中输出相应信息</h4>-->
    <button v-on:click="showhavezt()">查询</button>

    <table class="table" v-if="Object.keys(list2).length>0">
      <thead>
      <tr>
        <th>项目编号</th>
        <th>询单日期</th>
        <th>完成时间</th>
        <th>状态</th>
        <th>结果</th>
        <th></th>
      </tr>
      </thead>
      <tbody v-for="x in list2" :key="x.projectid">
      <tr>
        <th>{{ x.projectid }}</th>
        <th>{{ x.createdate }}</th>
        <th>{{ x.evaluationdate }}</th>
        <th>已评估</th>
        <th>{{ x.projectztjs }}</th>
        <th>
            <el-button v-on:click="feedback(x.projectid)">原料信息</el-button>
        </th>
      </tr>
      </tbody>
    </table>

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
      usertype: '',
      interval1: '',
      interval2: '',
      list1: [],
      list2: []
    }
  },
  components: {
    CIdentityCheck,
    Chead
  },
  methods: {
    showunzt () {
      var _this = this
      this.$axios
        .post('/unzt', {
          interval: _this.interval1
        })
        .then(successResponse => {
          // console.log(successResponse.data)
          if (successResponse.data.success) {
            console.log(successResponse.data.data)
            this.list1 = successResponse.data.data
          } else {
            this.$message(successResponse.data.msg)
          }
        })
        .catch(failResponse => {
        })
    },
    showhavezt () {
      var _this = this
      this.$axios.post('/havezt', {
        interval: _this.interval2,
        resultkf: '所有'
      })
        .then(successResponse => {
          if (successResponse.data.success) {
            console.log(successResponse.data.data)
            this.list2 = successResponse.data.data
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

.table td, .table th {
  border: 1px solid #ccc;
  padding: 5px;
}
</style>
