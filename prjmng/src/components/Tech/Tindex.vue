<template>
  <div>
    <TIdentityCheck></TIdentityCheck>
    <div>
      请查看项目信息，并对项目进行技术评估。
      <!--      你目前有xx个项目未完成技术评估，已经完成xx个项目评估， 可行项目x个，不可行项目x个。-->
    </div>
    <h4>（一）请查看项目信息（技术未评估）：</h4>
    <br>下拉菜单选择时间范围：
    <select v-model="interval1" placeholder="时间范围">
      <option label="当天" value="1">当天</option>
      <option label="近3天" value="3">近3天</option>
      <option label="近1周" value="7">近1周</option>
      <option label="近1月" value="30">近1月</option>
      <option label="所有" value="30000">所有</option>
    </select>
    <el-button size="small" v-on:click="showunzt()">查询</el-button>
    <table v-if="Object.keys(list1).length>0" class="table">
      <thead>
      <tr>
        <th>项目编号</th>
        <th>询单日期</th>
        <th>已经过时间</th>
        <th>向客服人员下达查询原料任务</th>
        <th></th>
        <th>反馈状态</th>
        <th></th>
      </tr>
      </thead>
      <tbody v-for="x in list1" :key="x.projectid">
      <tr>
        <th>{{ x.projectid }}</th>
        <th>{{ x.createdate.substr(0, 10) }}</th>
        <th>{{ x.dura }}小时</th>
        <th>{{ x.istaskjs }}</th>
        <th>
          <div v-if="x.istaskjs === '未下达'">
            <el-button v-on:click="lookMaterial(x.projectid)">查看项目（确定原料）</el-button>
          </div>
          <div v-else>
            已下达原料信息
          </div>
        </th>
        <th>{{ x.fkztkf }}</th>
        <th>
          <div v-if="x.fkztkf !== '未反馈'">
            <el-button v-on:click="feedback(x.projectid)">查看原料可行性反馈</el-button>
          </div>
          <div v-else>
            <el-button v-on:click="pgdirect(x.projectid)">查看项目（确定技术不可行）</el-button>
          </div>
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
    <br>下拉菜单可选择“成交结果”：
    <select v-model="project_result_kf" placeholder="成交结果">
      <option value="所有">所有</option>
      <option value="成交">成交</option>
      <option value="未成交">未成交</option>
      <option value="待定">待定</option>
    </select>
    <el-button size="small" v-on:click="showhavezt()">查询</el-button>
    <el-collapse accordion>
      <div v-for="(x, index) in list2" :key="index">
        <el-collapse-item>
          <span slot="title" style="width:100%;">
            <el-row>
              <el-col :span="2"><div>项目编号：{{ x.projectid }}</div></el-col>
              <el-col :span="8"><div>询单日期：{{ x.createdate }}</div></el-col>
              <el-col :span="5"><div>评估结果：{{ x.projectztjs }}</div></el-col>
              <el-col :span="5"><div>实验结果：{{ x.testresult }}</div></el-col>
              <el-col :span="4"><div>成交结果：{{ x.projectresultkf }}</div></el-col>
            </el-row>
          </span>
          <br>
          <el-row>
            <el-col :span="5">
              <div>（一）询单产品信息：</div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="7">
              <div>需求产品名称：{{ x.projectname }}</div>
            </el-col>
            <el-col :span="7">
              <div>cas号：{{ x.cas }}</div>
            </el-col>
            <el-col :span="7">
              <div>需求量：{{ x.projectsl }}</div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <div>产品结构图片：</div>
              <img alt="图片未上传" v-bind:src="x.projectdetails"/>
            </el-col>
            <el-col :span="12">
              <div>备注：{{ x.bz }}</div>
            </el-col>
          </el-row>
          <br>
          <el-row>
            <el-col :span="5">
              <div>（二）技术评估状态与结果</div>
            </el-col>
          </el-row>
          <div v-if="x.projectztjs === '已评估-可行'">
            <el-row>
              <el-col :offset="3" :span="4">
                需要进行测试信息：
              </el-col>
            </el-row>
            <el-row>
              <el-col :offset="3" :span="6">
                <div>氢谱:{{ x.ztjs.hsl }}</div>
              </el-col>
              <el-col :span="6">
                <div>碳谱:{{ x.ztjs.csl }}</div>
              </el-col>
              <el-col :span="6">
                <div>质谱:{{x.ztjs.msl}}</div>
              </el-col>
            </el-row>
            <el-row>
              <el-col :offset="3" :span="4">
                <div>测试总时间:{{x.ztjs.zsjcs}}</div>
              </el-col>
            </el-row>
            <el-row>
              <el-col :offset="3" :span="6">
                <div>打通路线时间:{{ x.ztjs.dtlxsjcs }}天</div>
              </el-col>
              <el-col :span="6">
                <div>积累量时间:{{ x.ztjs.jllsjcs }} 天</div>
              </el-col>
            </el-row>
            <el-row>
              <el-col :offset="3" :span="4">
                技术难度：{{ x.ztjs.isdifficultjs }}
              </el-col>
            </el-row>
            <el-row>
              <el-col :offset="3" :span="8">
                完成项目时间:{{ x.ztjs.timeneeded }}
              </el-col>
            </el-row>
            <el-row>
              <el-col :offset="3" :span="8">
                完成项目的特殊要求:{{ x.ztjs.bz }}
              </el-col>
            </el-row>
            <el-row>
              <el-col :offset="3" :span="4">
                完成项目相关文档:{{x.ztjs.papersjs}}
              </el-col>
            </el-row>
          </div>
          <div v-else>
            <el-row>
              <el-col :offset="3" :span="12">
                <div>技术不可行原因:</div>
              </el-col>
            </el-row>
            <el-row>
              <el-col :offset="3" :span="12">
                <div>{{ x.ztjs.reasonsjs }}</div>
              </el-col>
            </el-row>
          </div>
        </el-collapse-item>
      </div>
    </el-collapse>
    <br><br>
    <div>
      <el-button v-on:click="goback">返回</el-button>
    </div>
  </div>
</template>

<script>
import TIdentityCheck from './TIdentityCheck'

export default {
  name: 'Tindex',
  data: function () {
    return {
      activeName: '1',
      date: new Date(),
      account_id: window.sessionStorage.getItem('account_id'),
      usertype: '',
      interval1: '',
      interval2: '',
      project_result_kf: '',
      list1: [],
      list2: []
    }
  },
  components: {
    TIdentityCheck
  },
  created () {

  },
  methods: {
    showunzt () {
      var _this = this
      this.$axios
        .post('/unzt', {
          interval: _this.interval1
        })
        .then(successResponse => {
          if (successResponse.data.success) {
            this.list1 = successResponse.data.data
            if (this.list1.length < 1) {
              this.$message('查询时间段内无项目')
            }
          } else {
            this.$message(successResponse.data.msg)
          }
        })
        .catch(failResponse => {
        })
      this.$forceUpdate()
    },
    showhavezt () {
      var _this = this
      this.$axios.post('/havezt', {
        interval: _this.interval2,
        resultkf: _this.project_result_kf
      })
        .then(successResponse => {
          if (successResponse.data.success) {
            console.log(successResponse.data.data)
            this.list2 = successResponse.data.data
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
                // this.list2[m].testresult = ''
                // this.$axios
                //   .post('/querytestresult', {
                //     projectid: this.list2[m].projectid
                //   })
                //   .then(successResponse => {
                //     if (successResponse.data.success) {
                //       // console.log(successResponse.data.data)
                //       this.list2[m].testresult = successResponse.data.data
                //     } else {
                //       this.$message(successResponse.data.msg)
                //     }
                //   })
                //   .catch(failResponse => {
                //   })
                this.list2[m].ztjs = {
                  reasonjs: '',
                  isdifficultjs: '',
                  timeneeded: '',
                  bz: '',
                  papersjs: ''
                }
                if (this.list2[m].projectztjs === '已评估-不可行') {
                  this.$axios
                    .post('/querynofeasible', {
                      projectid: this.list2[m].projectid
                    })
                    .then(successResponse => {
                      if (successResponse.data.success) {
                        // console.log(successResponse.data.data)
                        this.list2[m].ztjs = successResponse.data.data
                      } else {
                        this.$message(successResponse.data.msg)
                      }
                    })
                    .catch(failResponse => {
                    })
                } else if (this.list2[m].projectztjs === '已评估-可行') {
                  this.$axios
                    .post('/queryfeasible', {
                      projectid: this.list2[m].projectid
                    })
                    .then(successResponse => {
                      if (successResponse.data.success) {
                        this.list2[m].ztjs = successResponse.data.data
                      } else {
                        this.$message(successResponse.data.msg)
                      }
                    })
                    .catch(failResponse => {
                    })
                }
              }
            }
          } else {
            this.$message(successResponse.data.msg)
          }
        })
        .catch(failResponse => {
        })
      this.$forceUpdate()
    },
    lookMaterial: function (x) {
      window.sessionStorage.setItem(this.account_id, x)
      this.$router.replace({path: '/T1'})
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
                this.$router.replace('/T2')
              }).catch(() => {
              })
            } else {
              this.$confirm(successResponse.data.data, '提示', {
                confirmButtonText: '进行项目评估',
                cancelButtonText: '取消'
              }).then(() => {
                window.sessionStorage.setItem(this.account_id, x)
                this.$router.replace('/T2')
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
    },
    pgdirect (x) {
      window.sessionStorage.setItem(this.account_id, x)
      this.$router.replace('/T2')
    },
    goback () {
      window.sessionStorage.removeItem('account_id')
      window.sessionStorage.removeItem('usertype')
      this.$router.replace('/A')
    }
  }
}
</script>

<style>
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
