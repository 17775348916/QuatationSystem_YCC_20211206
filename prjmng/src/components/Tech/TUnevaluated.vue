<template>
  <div>
    <div style="margin-bottom:10px">
      <TIdentityCheck></TIdentityCheck>
      下拉菜单选择时间范围：
      <select v-model="interval1" placeholder="时间范围">
        <option label="当天" value="1">当天</option>
        <option label="近3天" value="3">近3天</option>
        <option label="近1周" value="7">近1周</option>
        <option label="近1月" value="30">近1月</option>
        <option label="所有" value="30000">所有</option>
      </select>
      <el-button size="small" v-on:click="showunzt(interval1)">查询</el-button>
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
        prop="projectZt.istaskjs"
        label="向客服人员下达查询原料任务">
      </el-table-column>
      <el-table-column
        prop="projectZt.istaskjs"
        label="">
        <template slot-scope="scope">
        <el-button size="small" v-if="scope.row.projectZt.istaskjs === '未下达'" v-on:click="lookMaterial(scope.row.projectid)">查看项目（确定原料)</el-button>
        <span v-if="scope.row.projectZt.istaskjs !== '未下达'">已下达原料信息</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="projectZt.fkztkf"
        label="反馈状态">
      </el-table-column>
      <el-table-column
        prop="projectZt.fkztkf"
        label="操作">
        <template slot-scope="scope">
          <el-button size="small" v-if="scope.row.projectZt.fkztkf === '未反馈'" v-on:click="pgdirect(scope.row.projectid)">查看项目(确定技术不可行)</el-button>
          <el-button size="small" v-if="scope.row.projectZt.fkztkf !== '未反馈'" v-on:click="feedback(scope.row.projectid)">查看原料可行性反馈</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination class="fy"
                   layout="total, sizes, prev, pager, next, jumper"
                   :total="total1"
                   :page-sizes="[5, 10]"
                   :page-size= "pageSize1"
                   :current-page.sync = currentPage1
                   v-show="total1>5"
                   @size-change="handleSizeChange1"
                   @current-change="handleCurrentChange1"
                   background style="margin-top:10px">
    </el-pagination>
  </div>
</template>

<script>
import TIdentityCheck from './TIdentityCheck'

export default {
  name: 'TUnevaluated',
  data: function () {
    return {
      total1: 0,
      pageSize1: 5,
      currentPage1: 1,
      activeName: '1',
      date: new Date(),
      account_id: window.sessionStorage.getItem('account_id'),
      usertype: '',
      interval1: '',
      project_result_kf: '',
      list1: []
    }
  },
  components: {
    TIdentityCheck
  },
  created () {
    if (window.sessionStorage.getItem('CEvaluatedInterval') != null) {
      window.sessionStorage.removeItem('CEvaluatedInterval')
    }
    if (window.sessionStorage.getItem('CUnevaluatedInterval') != null) {
      window.sessionStorage.removeItem('CUnevaluatedInterval')
    }
    if (window.sessionStorage.getItem('TUnevaluatedInterval') != null) {
      this.interval1 = parseInt(window.sessionStorage.getItem('TUnevaluatedInterval'))
      this.pageSize1 = parseInt(window.sessionStorage.getItem('TUnevaluatedPageSize'))
      this.currentPage1 = parseInt(window.sessionStorage.getItem('TUnevaluatedCurrentPage'))
      this.getList1(this.currentPage1)
    }
  },
  methods: {
    showunzt () {
      this.currentPage1 = 1
      this.$axios
        .post('/unevaluatedPage', {
          page: this.currentPage1,
          size: this.pageSize1,
          interval: this.interval1,
          status: '未评估'
        })
        .then(successResponse => {
          if (successResponse.data.success) {
            window.sessionStorage.setItem('TUnevaluatedInterval', this.interval1)
            window.sessionStorage.setItem('TUnevaluatedCurrentPage', this.currentPage1)
            window.sessionStorage.setItem('TUnevaluatedPageSize', this.pageSize1)
            this.list1 = successResponse.data.data.content
            if (this.list1.length < 1) {
              this.$message('查询时间段内无项目')
            }
            this.total1 = successResponse.data.data.totalElements
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
          if (successResponse.data.success) {
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
    lookMaterial: function (x) {
      window.sessionStorage.setItem(this.account_id, x)
      this.$router.push({path: '/T1'})
    },
    pgdirect (x) {
      window.sessionStorage.setItem(this.account_id, x)
      this.$router.push('/T2')
    },
    feedback (x) {
      this.$axios
        .post('/JS/feedback', {
          projectid: x
        })
        .then(successResponse => {
          if (successResponse.data.success) {
            if (successResponse.data.code === '100') {
              this.$confirm(successResponse.data.msg, '提示', {
                confirmButtonText: '直接进入评估-项目不可行',
                cancelButtonText: '取消'
              }).then(() => {
                window.sessionStorage.setItem(this.account_id, x)
                this.$router.push('/T2')
              }).catch(() => {
              })
            } else {
              this.$confirm(successResponse.data.data, '提示', {
                confirmButtonText: '进行项目评估',
                cancelButtonText: '取消'
              }).then(() => {
                window.sessionStorage.setItem(this.account_id, x)
                this.$router.push('/T2')
              }).catch(() => {
              })
            }
            // this.$message()
          } else {
            this.$message(successResponse.data.msg)
          }
        })
        .catch(failResponse => {
        })
    }
  }
}
</script>

<style scoped>

</style>
