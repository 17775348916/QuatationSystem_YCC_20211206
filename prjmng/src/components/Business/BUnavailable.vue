<template>
  <div>
    <IdentityCheck></IdentityCheck>
    查看无成交可能项目，请选择时间范围：
    <select v-model="interval" placeholder="时间范围">
      <option label="当天" value="1">当天</option>
      <option label="近3天" value="3">近3天</option>
      <option label="近1周" value="7">近1周</option>
      <option label="近1月" value="30">近1月</option>
      <option label="所有" value="300000">所有</option>
    </select>
    <button v-on:click="showUnavailables()">查询</button>
    <el-collapse accordion :data="list" style="margin-top:20px">
      <div v-for="(x, index) in list" :key="index">
        <el-collapse-item>
          <span slot="title" style="width:100%;">
            <el-row>
              <el-col :span="2"><div>编号{{ x.projectid }}</div></el-col>
              <el-col :span="12"><div>询单日期{{ x.createdate }}</div></el-col>
              <el-col :span="5"><div>目前尚无成交可能</div></el-col>
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
          <hr>
        </el-collapse-item>
      </div>
      <el-pagination class="fy"
                     layout="total, sizes, prev, pager, next, jumper"
                     :total="total"
                     :page-sizes="[5, 10]"
                     :page-size= "pageSize"
                     v-show="total>5"
                     @size-change="handleSizeChange"
                     @current-change="handleCurrentChange"
                     background style="margin-top:10px">
      </el-pagination>
    </el-collapse>
  </div>
</template>

<script>
import IdentityCheck from './IdentityCheck'
export default {
  name: 'BUnavailable',
  data: function () {
    return {
      account_id: '',
      usertype: '',
      interval: '',
      total: 0,
      pageSize: 5,
      currentPage: 1,
      list: []
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
    if (window.sessionStorage.getItem('CUnevaluatedInterval') != null) {
      window.sessionStorage.removeItem('CUnevaluatedInterval')
    }
  },
  methods: {
    showUnavailables () {
      this.$axios
        .post('/showUnavailables', {
          page: this.currentPage,
          size: this.pageSize,
          interval: this.interval
        })
        .then(successResponse => {
          if (successResponse.data.success) {
            this.list = successResponse.data.data.content
            this.total = successResponse.data.data.totalElements
            if (this.list.length < 1) {
              this.$message('查询时间段内无项目')
            } else {
              for (let m in this.list) {
                this.$axios
                  .post('/lookpic', {
                    imgpath: this.list[m].projectdetails
                  })
                  .then(successResponse => {
                    if (successResponse.data.success) {
                      console.log(successResponse.data.data)
                      this.list[m].projectdetails = 'data:image/png;base64,' + successResponse.data.data.base64id
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
    },
    handleCurrentChange (currentPage) {
      this.getList(currentPage)
    },
    handleSizeChange (pageSize) {
      this.pageSize = pageSize
      this.getList(this.currentPage)
    },
    getList (currentPage) {
      this.currentPage = currentPage
      this.showUnavailables()
    }
  },
  components: {
    IdentityCheck
  }
}
</script>

<style scoped>

</style>
