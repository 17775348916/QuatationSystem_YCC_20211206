<template>
  <div>
    <CIdentityCheck></CIdentityCheck>
    查看技术未评估项目，请选择时间范围：
    <select v-model="interval1" placeholder="时间范围">
      <option label="当天" value="1">当天</option>
      <option label="近3天" value="3">近3天</option>
      <option label="近1周" value="7">近1周</option>
      <option label="近1月" value="30">近1月</option>
      <option label="所有" value="30000">所有</option>
    </select>
    <button v-on:click="showunzt()">查询</button>
    <div v-if="Object.keys(list1).length>0">
      <el-row>
        <el-col>
          <div align="left" style="color:red;">已提供原料信息任务: {{provideNum}}</div>
          <div align="left" style="color:red;">未提供原料信息任务: {{noProvideNum}}</div>
        </el-col>
      </el-row>
    </div>

    <el-table v-if="Object.keys(list1).length>0"
              :data="list1"
              border
              style="width: 100%">
      <el-table-column
        prop= "projectid"
        label="项目编号">
      </el-table-column>
      <el-table-column
        prop="createdate"
        label="询单日期">
      </el-table-column>
      <el-table-column
        prop="projectZt.timed"
        label="已经过时间">
      </el-table-column>
      <el-table-column
        label="状态">
        未评估
      </el-table-column>
      <el-table-column
        prop="projectZt.istaskjs"
        label="原料任务">
      </el-table-column>
      <el-table-column
        prop="projectZt.fkztkf"
        label="反馈状况">
      </el-table-column>
      <el-table-column
        prop="projectZt.fkztkf"
        label="操作">
        <template slot-scope="scope">
          <el-button v-if="scope.row.projectZt.istaskjs === '已下达' && scope.row.projectZt.fkztkf === '未反馈'" v-on:click="feedback(scope.row.projectid)">反馈原料信息</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination class="fy"
                   layout="total, sizes, prev, pager, next, jumper"
                   :total="total1"
                   :page-sizes="[5, 10]"
                   :page-size= "pageSize1"
                   v-show="total1>5"
                   @size-change="handleSizeChange1"
                   @current-change="handleCurrentChange1"
                   background style="margin-top:20px">
    </el-pagination>
  </div>
</template>

<script>
import CIdentityCheck from './CIdentityCheck'

export default {
  name: 'CUnevaluated',
  data () {
    return {
      // 未提供原料信息任务数量
      noProvideNum: 0,
      // 提供原料任务数量
      provideNum: 0,
      currentPage1: 1,
      total1: 0,
      pageSize1: 5,
      account_id: window.sessionStorage.getItem('account_id'),
      usertype: '',
      interval1: '',
      list1: []
    }
  },
  components: {
    CIdentityCheck
  },
  methods: {
    showunzt () {
      this.$axios
        .post('/unevaluatedPage', {
          interval: this.interval1,
          page: this.currentPage1,
          size: this.pageSize1,
          status: '未评估'
        })
        .then(successResponse => {
          if (successResponse.data.success) {
            this.list1 = successResponse.data.data.content
            if (this.list1.length < 1) {
              this.$message('查询时间段内无项目')
            }
            this.total1 = successResponse.data.data.totalElements
            this.$axios
              .post('/materialMissionNum', {
                interval: this.interval1,
                status: '未评估'
              })
              .then(successResponse => {
                if (successResponse.data.success) {
                  this.provideNum = JSON.parse(successResponse.data.data).provideNum
                  this.noProvideNum = JSON.parse(successResponse.data.data).noProvideNum
                }
              })
              .catch(failResponse => {})
          } else {
            this.$message(successResponse.data.msg)
          }
        })
        .catch(failResponse => {
        })
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
          page: this.currentPage1,
          size: this.pageSize1,
          status: '未评估'
        })
        .then(successResponse => {
          // console.log(successResponse.data)
          if (successResponse.data.success) {
            console.log(successResponse.data.data)
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
    feedback (projectid) {
      window.sessionStorage.setItem(this.account_id, projectid)
      this.$router.push('/C1')
    }
  }
}
</script>

<style scoped>

</style>
