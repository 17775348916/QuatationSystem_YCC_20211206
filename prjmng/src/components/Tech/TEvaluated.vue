<template>
  <div>
    <div style="margin-bottom:10px">
      <TIdentityCheck></TIdentityCheck>
      下拉菜单选择时间范围：
      <select v-model="interval2" placeholder="时间范围" style="margin-right:20px">
        <option label="当天" value="1">当天</option>
        <option label="近3天" value="3">近3天</option>
        <option label="近1周" value="7">近1周</option>
        <option label="近1月" value="30">近1月</option>
        <option label="所有" value="30000">所有</option>
      </select>
      下拉菜单可选择“成交结果”：
      <select v-model="project_result_kf" placeholder="成交结果">
        <option value="所有">所有</option>
        <option value="成交">成交</option>
        <option value="未成交">未成交</option>
        <option value="待定">待定</option>
      </select>
      <el-button size="small" v-on:click="showhavezt()">查询</el-button>
    </div>

    <el-collapse accordion :data="list2">
      <div v-for="(x, index) in list2" :key="index">
        <el-collapse-item>
          <span slot="title" style="width:100%;">
            <el-row>
              <el-col :span="4"><div>项目编号：{{ x.projectid }}</div></el-col>
              <el-col :span="10"><div>询单日期：{{ x.createdate }}</div></el-col>
              <el-col :span="5"><div>评估结果：{{ x.projectZt.projectztjs }}</div></el-col>
              <el-col :span="4"><div>成交结果：{{ x.projectZt.projectresultkf }}</div></el-col>
            </el-row>
          </span>
          <hr>
          <el-descriptions class="margin-top1" title="询单产品信息" :column="4" :size="mini" style="margin-bottom:20px" border>
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
                  <img alt="图片未上传" v-bind:src="x.projectdetails" style="max-width:600px"/>
                  <el-button slot="reference" size="mini">预览</el-button>
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
          <div v-if="x.projectZt.projectztjs === '已评估-可行'">
          <el-descriptions class="margin-top" title="技术评估状态与结果" :column="4" :size="medium" style="margin-bottom:20px" border>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-document"></i>
                需要进行测试信息
              </template>
              氢谱:{{ x.hsl }}; 碳谱:{{ x.csl }}; 质谱:{{ x.msl }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-time"></i>
                测试总时间(天)
              </template>
              {{ x.zsjcs }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-timer"></i>
                打通路线时间(天)
              </template>
              {{ x.dtlxsjcs }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-date"></i>
                积累量时间(天)
              </template>
              {{ x.jllsjcs }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-warning"></i>
                技术难度
              </template>
              {{ x.isdifficultjs }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-time"></i>
                完成项目时间
              </template>
              {{ x.timeneeded }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-help"></i>
                完成项目的特殊要求
              </template>
              {{ x.bz }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-document"></i>
                项目参考文档
              </template>
              <div v-for ="(item,index) in x.papersjs" v-bind:key="index">
                <div v-if= "index === 0">
                  <div v-if="item.includes('.pdf')" >
                    <a :href="item" target="_blank">{{x.paperType[index]}}</a>
                  </div>
                  <div v-else-if="item.includes('.jpg')">
                    <a :href="item" target="_blank">{{x.paperType[index]}}</a>
                  </div>
                  <div v-else-if="item.includes('.jpeg')">
                    <a :href="item" target="_blank">{{x.paperType[index]}}</a>
                  </div>
                  <div v-else-if="item.includes('.png')">
                    <a :href="item" target="_blank">{{x.paperType[index]}}</a>
                  </div>
                  <div v-if="item.includes('.doc')" >
                    <a :href="item">{{x.paperType[index]}}</a>
                  </div>
                  <div v-else-if="item.includes('.docx')">
                    <a :href="item">{{x.paperType[index]}}</a>
                  </div>
                  <div v-else-if="item.includes('.xls')">
                    <a :href="item">{{x.paperType[index]}}</a>
                  </div>
                  <div v-else-if="item.includes('.xlsx')">
                    <a :href="item">{{x.paperType[index]}}</a>
                  </div>
                  <div v-else-if="item.includes('.rar')">
                    <a :href="item">{{x.paperType[index]}}</a>
                  </div>
                </div>
                <div v-else-if ="item.includes('.pdf')">
                  <a :href="item" target="_blank">{{x.paperType[index]}}</a>
                </div>
                <div v-else-if="item.includes('.jpg')">
                  <a :href="item" target="_blank">{{x.paperType[index]}}</a>
                </div>
                <div v-else-if="item.includes('.jpeg')">
                  <a :href="item" target="_blank">{{x.paperType[index]}}</a>
                </div>
                <div v-else-if="item.includes('.png')">
                  <a :href="item" target="_blank">{{x.paperType[index]}}</a>
                </div>
                <div v-else-if ="item.includes('.xls')">
                  <a :href="item">{{x.paperType[index]}}</a>
                </div>
                <div v-else-if="item.includes('.doc')">
                  <a :href="item">{{x.paperType[index]}}</a>
                </div>
                <div v-else-if="item.includes('.docx')">
                  <a :href="item">{{x.paperType[index]}}</a>
                </div>
                <div v-else-if="item.includes('.rar')">
                  <a :href="item">{{x.paperType[index]}}</a>
                </div>
                <div v-else-if="item.includes('.xlsx')">
                  <a :href="item">{{x.paperType[index]}}</a>
                </div>
              </div>
            </el-descriptions-item>
          </el-descriptions>
          </div>
          <div v-else>
            <el-descriptions class="margin-top1" title="技术评估状态与结果" :column="2" :size="mini" style="margin-bottom:20px" border>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-message-solid"></i>
                  技术不可行原因
                </template>
                {{ x.reasonsjs }}
              </el-descriptions-item>
            </el-descriptions>
          </div>
        </el-collapse-item>
      </div>
    </el-collapse>
    <el-pagination class="fy1"
                   layout="total, sizes, prev, pager, next, jumper"
                   :total="total2"
                   :page-sizes="[5, 10]"
                   :page-size= "pageSize2"
                   v-show="total2>5"
                   @size-change="handleSizeChange2"
                   @current-change="handleCurrentChange2"
                   background style="margin-top:10px">
    </el-pagination>
  </div>
</template>

<script>
import TIdentityCheck from './TIdentityCheck'

export default {
  name: 'TEvaluated',
  data: function () {
    return {
      total2: 0,
      pageSize2: 5,
      currentPage2: 1,
      activeName: '1',
      date: new Date(),
      account_id: window.sessionStorage.getItem('account_id'),
      usertype: '',
      interval2: '',
      project_result_kf: '',
      list2: []
    }
  },
  components: {
    TIdentityCheck
  },
  created () {

  },
  methods: {
    showhavezt () {
      var _this = this
      this.$axios.post('/evaluatedPage', {
        interval: _this.interval2,
        resultkf: _this.project_result_kf,
        page: 1,
        size: 5,
        status: '已评估'
      })
        .then(successResponse => {
          if (successResponse.data.success) {
            this.list2 = successResponse.data.data.content
            // console.log(this.list2)
            if (this.list2.length < 1) {
              this.$message('查询时间段内无项目')
            } else {
              this.total2 = successResponse.data.data.totalElements
              for (let m in this.list2) {
                this.$axios
                  .post('/lookpic', {
                    imgpath: this.list2[m].projectdetails
                  })
                  .then(successResponse => {
                    if (successResponse.data.success) {
                      this.list2[m].projectdetails = 'data:image/png;base64,' + successResponse.data.data.base64id
                    } else {
                      this.$message(successResponse.data.msg)
                    }
                  })
                  .catch(failResponse => {
                  })
                if (this.list2[m].projectZt.projectztjs === '已评估-不可行') {
                  this.$axios
                    .post('/querynofeasible', {
                      projectid: this.list2[m].projectid
                    })
                    .then(successResponse => {
                      if (successResponse.data.success) {
                        this.$set(this.list2[m], 'reasonsjs', successResponse.data.data.reasonsjs)
                      } else {
                        this.$message(successResponse.data.msg)
                      }
                    })
                    .catch(failResponse => {
                    })
                } else if (this.list2[m].projectZt.projectztjs === '已评估-可行') {
                  this.$axios
                    .post('/queryfeasible', {
                      projectid: this.list2[m].projectid
                    })
                    .then(successResponse => {
                      if (successResponse.data.success) {
                        // console.log(successResponse.data.data)
                        this.$set(this.list2[m], 'hsl', successResponse.data.data.hsl)
                        this.$set(this.list2[m], 'msl', successResponse.data.data.msl)
                        this.$set(this.list2[m], 'csl', successResponse.data.data.csl)
                        this.$set(this.list2[m], 'isdifficultjs', successResponse.data.data.isdifficultjs)
                        this.$set(this.list2[m], 'timeneeded', successResponse.data.data.timeneeded)
                        this.$set(this.list2[m], 'bz', successResponse.data.data.bz)
                        this.$set(this.list2[m], 'jllsjcs', successResponse.data.data.jllsjcs)
                        this.$set(this.list2[m], 'dtlxsjcs', successResponse.data.data.dtlxsjcs)
                        this.$set(this.list2[m], 'zsjcs', successResponse.data.data.zsjcs)
                        let strs = successResponse.data.data.papersjs.split('||')
                        let paperType = []
                        console.log('strs', strs)
                        strs.forEach(item => {
                          let strss = item.split('&&')
                          paperType.push(strss[strss.length - 1])
                        })
                        this.$set(this.list2[m], 'paperType', paperType)
                        this.$set(this.list2[m], 'papersjs', strs)
                        console.log(paperType)
                      } else {
                        this.$message(successResponse.data.msg)
                      }
                    })
                    .catch(failResponse => {
                    })
                }
              }
              console.log(this.list2)
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
        page: this.currentPage2,
        size: this.pageSize2,
        status: '已评估'
      })
        .then(successResponse => {
          if (successResponse.data.success) {
            this.list2 = successResponse.data.data.content
            // console.log(this.list2)
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
                      // console.log(successResponse.data.data)
                      this.list2[m].projectdetails = 'data:image/png;base64,' + successResponse.data.data.base64id
                    } else {
                      this.$message(successResponse.data.msg)
                    }
                  })
                  .catch(failResponse => {
                  })
                if (this.list2[m].projectZt.projectztjs === '已评估-不可行') {
                  this.$axios
                    .post('/querynofeasible', {
                      projectid: this.list2[m].projectid
                    })
                    .then(successResponse => {
                      if (successResponse.data.success) {
                        this.$set(this.list2[m], 'reasonsjs', successResponse.data.data.reasonsjs)
                      } else {
                        this.$message(successResponse.data.msg)
                      }
                    })
                    .catch(failResponse => {
                    })
                } else if (this.list2[m].projectZt.projectztjs === '已评估-可行') {
                  this.$axios
                    .post('/queryfeasible', {
                      projectid: this.list2[m].projectid
                    })
                    .then(successResponse => {
                      if (successResponse.data.success) {
                        // console.log(successResponse.data.data)
                        this.$set(this.list2[m], 'hsl', successResponse.data.data.hsl)
                        this.$set(this.list2[m], 'msl', successResponse.data.data.msl)
                        this.$set(this.list2[m], 'csl', successResponse.data.data.csl)
                        this.$set(this.list2[m], 'isdifficultjs', successResponse.data.data.isdifficultjs)
                        this.$set(this.list2[m], 'timeneeded', successResponse.data.data.timeneeded)
                        this.$set(this.list2[m], 'bz', successResponse.data.data.bz)
                        this.$set(this.list2[m], 'jllsjcs', successResponse.data.data.jllsjcs)
                        this.$set(this.list2[m], 'dtlxsjcs', successResponse.data.data.dtlxsjcs)
                        this.$set(this.list2[m], 'zsjcs', successResponse.data.data.zsjcs)
                        let strs = successResponse.data.data.papersjs.split('||')
                        let paperType = []
                        console.log('strs', strs)
                        strs.forEach(item => {
                          let strss = item.split('&&')
                          paperType.push(strss[strss.length - 1])
                        })
                        this.$set(this.list2[m], 'paperType', paperType)
                        this.$set(this.list2[m], 'papersjs', strs)
                        console.log(paperType)
                      } else {
                        this.$message(successResponse.data.msg)
                      }
                    })
                    .catch(failResponse => {
                    })
                }
              }
              console.log(this.list2)
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
