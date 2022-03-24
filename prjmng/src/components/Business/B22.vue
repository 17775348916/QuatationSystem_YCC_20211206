<template>
  <div >
    <IdentityCheck></IdentityCheck>
    <div>
      <span>下拉菜单选择时间范围：</span>
      <select v-model="interval2" placeholder="时间范围" style="margin-right:20px">
        <option label="当天" value="1">当天</option>
        <option label="近3天" value="3">近3天</option>
        <option label="近1周" value="7">近1周</option>
        <option label="近1月" value="30">近1月</option>
        <option label="所有" value="30000">所有</option>
      </select>
      <span>下拉菜单可选择“成交结果”：</span>
      <select v-model="project_result_kf" placeholder="成交结果">
        <option value="所有">所有</option>
        <option value="成交">成交</option>
        <option value="未成交">未成交</option>
        <option value="待定">待定</option>
      </select>
      <el-button type="primary" size="small" v-on:click="showhavezt()" style="margin-left:10px">查询</el-button>
    </div>
    <el-collapse accordion :data="list2">
      <div v-for="(x, index) in list2" :key="index">
        <el-collapse-item>
          <span slot="title" style="width:100%;">
            <el-row>
              <el-col :span="2"><div>编号{{ x.projectid }}</div></el-col>
              <el-col :span="12"><div>询单日期{{ x.createdate }}</div></el-col>
              <el-col :span="5"><div>已经过{{ x.projectZt.timed }}小时</div></el-col>
              <el-col :span="5"><div>已评估</div></el-col>
            </el-row>
          </span>
          <hr>
          <el-descriptions class="margin-top" title="询单人联系信息" :column="4" style="margin-bottom:20px" border>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-user"></i>
                客户姓名
              </template>
              {{ x.khryname }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-mobile-phone"></i>
                联系方式
              </template>
              {{ x.khrycontact }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-location-outline"></i>
                客户身份
              </template>
              {{ x.khrytype }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-office-building"></i>
                客户单位
              </template>
              {{ x.khname }}
            </el-descriptions-item>
          </el-descriptions>

          <el-descriptions class="margin-top1" title="询单产品信息" :column="4" style="margin-bottom:20px" border>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-goods"></i>
                产品名称
              </template>
              {{ x.projectname }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-info"></i>
                cas号
              </template>
              {{ x.cas }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-shopping-cart-full"></i>
                需求量
              </template>
              {{ x.projectsl }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-view"></i>
                产品结构照片
              </template>
              <div>
                <el-popover
                  placement="top-end"
                  width="150px"
                  trigger="click">
                  <img alt="图片加载中" v-bind:src="x.projectdetails" style="max-width:600px"/>
                  <el-button slot="reference">预览</el-button>
                </el-popover>
              </div>
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-warning"></i>
                备注
              </template>
              {{ x.bz }}
            </el-descriptions-item>
          </el-descriptions>

          <el-descriptions class="margin-top1" title="询单人判断信息" :column="5" style="margin-bottom:20px" border>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-office-building"></i>
                询单单位性质
              </template>
              {{ x.khtype }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-info"></i>
                询单单位是否有成交机会
              </template>
              {{ x.isdeal }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-s-check"></i>
                询单人身份是否真实
              </template>
              {{ x.khryisreal }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-money"></i>
                询单人是否有钱
              </template>
              {{ x.ismoney }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-warning"></i>
                客户合作历史
              </template>
              {{ x.cohistory }}
            </el-descriptions-item>
          </el-descriptions>
          <el-descriptions class="margin-top" title="技术评估状态与结果" :column="2" style="margin-bottom:20px" border>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-star-on"></i>
                项目技术评估状态
              </template>
              {{ x.projectZt.projectztjs }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-message-solid"></i>
                温馨提示
              </template>
              <div style="color:red">
                请您及时将技术评估结果告知客户，并告知客户我们会安排专门的客服人员为其报价，请客户留意邮箱或微信。如果客户有疑问，请您将客户的疑问转告公司客服人员，客服人员会与客户进行进一步沟通。
              </div>
            </el-descriptions-item>
          </el-descriptions>

          <el-descriptions class="margin-top" title="客户反馈与项目成交状态" :column="2" style="margin-bottom:20px;" border>
            <el-descriptions-item>
              <template slot="label" style="width:200px">
                <i class="el-icon-star-on"></i>
                项目成交状态
              </template>
              {{ x.projectZt.projectresultkf }}
            </el-descriptions-item>
            <el-descriptions-item >
              <template slot="label" style="width:200px">
                <i class="el-icon-help"></i>
                客户反馈
              </template>
              {{ x.projectZt.khfeedback }}
            </el-descriptions-item>
          </el-descriptions>
          <hr>
        </el-collapse-item>
      </div>
      <el-pagination class="fy1"
                     layout="total, sizes, prev, pager, next, jumper"
                     :total="total2"
                     :page-sizes="[5, 10]"
                     :page-size="pageSize2"
                     :current-page.sync = currentPage2
                     v-show="total2>5"
                     @size-change="handleSizeChange2"
                     @current-change="handleCurrentChange2"
                     background style="margin-top:10px">
      </el-pagination>
    </el-collapse>
  </div>
</template>
<script>
import IdentityCheck from './IdentityCheck'

export default {
  name: 'B22',
  data: function () {
    return {
      currentPage2: 1,
      total2: 0,
      pageSize2: 5,
      activeName: '1',
      date: new Date(),
      account_id: '',
      usertype: '',
      interval2: '',
      project_result_kf: '',
      list2: [],
      count_unevaluated: '',
      count_evaluated: ''
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
  },
  methods: {
    showhavezt () {
      this.currentPage2 = 1
      var _this = this
      this.$axios.post('/evaluatedPage', {
        interval: _this.interval2,
        resultkf: _this.project_result_kf,
        page: this.currentPage2,
        size: this.pageSize2,
        status: '已评估'
      })
        .then(successResponse => {
          if (successResponse.data.success) {
            this.list2 = successResponse.data.data.content
            this.total2 = successResponse.data.data.totalElements
            if (this.list2.length < 1) {
              this.$message('查询时间段内无项目')
            } else {
              for (let m in this.list2) {
                this.$axios
                  .post('/lookpic', {
                    imgpath: this.list2[m].projectdetails
                  })
                  .then(successResponse => {
                    if (successResponse.data.success) {
                      console.log(successResponse.data.data)
                      this.list2[m].projectdetails = 'data:image/png;base64,' + successResponse.data.data.base64id
                    } else {
                      this.$message(successResponse.data.msg)
                    }
                  })
                  .catch(failResponse => {
                  })
              }
              this.count_evaluated = '当前时间段内下，技术已评估项目总计：' + successResponse.data.data.totalElements + '条'
            }
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
        resultkf: this.project_result_kf,
        page: currentPage,
        size: this.pageSize2,
        status: '已评估'
      })
        .then(successResponse => {
          if (successResponse.data.success) {
            this.list2 = successResponse.data.data.content
            if (this.list2.length < 1) {
              this.$message('查询时间段内无项目')
            } else {
              for (let m in this.list2) {
                this.$axios
                  .post('/lookpic', {
                    imgpath: this.list2[m].projectdetails
                  })
                  .then(successResponse => {
                    if (successResponse.data.success) {
                      console.log(successResponse.data.data)
                      this.list2[m].projectdetails = 'data:image/png;base64,' + successResponse.data.data.base64id
                    } else {
                      this.$message(successResponse.data.msg)
                    }
                  })
                  .catch(failResponse => {
                  })
              }
            }
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
