<template>
  <div>
    <CIdentityCheck></CIdentityCheck>
    查看技术已评估项目，请选择时间范围：
    <select v-model="interval2" placeholder="时间范围">
      <option label="当天" value="1">当天</option>
      <option label="近3天" value="3">近3天</option>
      <option label="近1周" value="7">近1周</option>
      <option label="近1月" value="30">近1月</option>
      <option label="所有" value="30000">所有</option>
    </select>
    <!--    <h4>此处需要从后端数据库中输出相应信息</h4>-->
    <button v-on:click="showhavezt()">查询</button>

    <el-table v-if="Object.keys(list2).length>0"
              :data="list2"
              border
              style="width: 100%;margin-top:10px">
      <el-table-column
        prop= "projectid"
        label="项目编号">
      </el-table-column>
      <el-table-column
        prop="createdate"
        label="询单日期">
      </el-table-column>
      <el-table-column
        prop="projectZt.evaluationdate"
        label="完成时间">
      </el-table-column>
      <el-table-column
        label="状态">
        已评估
      </el-table-column>
      <el-table-column
        prop="projectZt.projectztjs"
        label="结果" width="195">
      </el-table-column>
      <el-table-column
        label="操作">
        <template slot-scope="scope">
          <el-button size="mini" v-on:click="feedback(scope.row.projectid)">原料信息</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination class="fy1"
                   layout="total, sizes, prev, pager, next, jumper"
                   :total="total2"
                   :page-sizes="[5, 10]"
                   :page-size= "pageSize2"
                   v-show="total2>5"
                   @size-change="handleSizeChange2"
                   @current-change="handleCurrentChange2"
                   background style="margin-top:20px">
    </el-pagination>
  </div>
</template>

<script>
import CIdentityCheck from './CIdentityCheck'

export default {
  name: 'CEvaluated',
  data () {
    return {
      currentPage2: 1,
      total2: 0,
      pageSize2: 5,
      account_id: window.sessionStorage.getItem('account_id'),
      usertype: '',
      interval2: '',
      list2: []
    }
  },
  components: {
    CIdentityCheck
  },
  methods: {
    showhavezt () {
      this.$axios.post('/evaluatedPage', {
        interval: this.interval2,
        resultkf: '所有',
        page: this.currentPage2,
        size: this.pageSize2,
        status: '已评估'
      })
        .then(successResponse => {
          if (successResponse.data.success) {
            this.list2 = successResponse.data.data.content
            if (this.list2.length < 1) {
              this.$message('查询时间段内无项目')
            }
            this.total2 = successResponse.data.data.totalElements
          } else {
            this.$message(successResponse.data.msg)
          }
        })
        .catch(failResponse => {
        })
    },
    handleSizeChange2 (pageSize) {
      this.pageSize2 = pageSize
      this.getList2(this.currentPage2)
    },
    handleCurrentChange2 (currentPage) {
      this.getList2(currentPage)
    },
    getList2 (currentPage) {
      this.currentPage2 = currentPage
      this.$axios.post('/evaluatedPage', {
        interval: this.interval2,
        resultkf: '所有',
        page: this.currentPage2,
        size: this.pageSize2,
        status: '已评估'
      })
        .then(successResponse => {
          if (successResponse.data.success) {
            this.list2 = successResponse.data.data.content
            if (this.list2.length < 1) {
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
