/* eslint-disable */
<template>
  <div style="font-size: 17px">
    <hr>
    <IdentityCheck></IdentityCheck>
    <h4>（一）请查看项目信息（技术未评估）：</h4>
    <br>下拉菜单选择时间范围：
    <select v-model="interval1" placeholder="时间范围">
      <option label="当天" value="1">当天</option>
      <option label="近3天" value="3">近3天</option>
      <option label="近1周" value="7">近1周</option>
      <option label="近1月" value="30">近1月</option>
      <option label="所有" value="30000">所有</option>
    </select>
    <!--    <h4>此处需要从后端数据库中输出相应信息</h4>-->
    <el-button size="small" v-on:click="showunzt()">查询</el-button>
    <el-collapse accordion>
      <div v-for="(x, index) in list1" :key="index">
        <el-collapse-item>
          <span slot="title" style="width:100%;">
            <el-row>
              <el-col :span="2"><div>编号{{ x.projectid }}</div></el-col>
              <el-col :span="12"><div>询单日期{{ x.createdate }}</div></el-col>
              <el-col :span="5"><div>已经过{{ x.dura }}小时</div></el-col>
              <el-col :span="5"><div>未评估</div></el-col>
            </el-row>
          </span>
          <br>
          <el-row>
            <el-col :span="5">
              <div>（一）询单人联系信息</div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="5">
              <div>询单单位名称：{{ x.khname }}</div>
            </el-col>
            <el-col :span="5">
              <div>询单人员姓名：{{ x.khryname }}</div>
            </el-col>
            <el-col :span="5">
              <div>询单人员身份：{{ x.khrytype }}</div>
            </el-col>
            <el-col :span="5">
              <div>询单人联系方式：{{ x.khrycontact }}</div>
            </el-col>
          </el-row>
          <br>
          <el-row>
            <el-col :span="5">
              <div>（二）询单产品信息</div>
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
              <img alt="图片未上传" v-bind:src="x.projectdetails" style="max-width: 600px"/>
            </el-col>
            <el-col :span="12">
              <div>备注：{{ x.bz }}</div>
            </el-col>
          </el-row>
          <br>
          <el-row>
            <el-col :span="5">
              <div>（三）技术评估状态与结果</div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="5">
              <div>该项目技术评估状态为：{{ x.projectztjs }}</div>
            </el-col>
          </el-row>
          <br>
          <el-row>
            <el-col :span="5">
              <div>（四）客户反馈与项目成交状态</div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="5">
              <div>项目成交状态为：{{ x.projectresultkf }}</div>
            </el-col>
          </el-row>
        </el-collapse-item>
      </div>
    </el-collapse>

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
    <!--    <h4>此处需要从后端数据库中输出相应信息</h4>-->
    <el-button size="small" v-on:click="showhavezt()">查询</el-button>

    <el-collapse accordion>
      <div v-for="(x, index) in list2" :key="index">
        <el-collapse-item>
          <span slot="title" style="width:100%;">
            <el-row>
              <el-col :span="2"><div>编号{{ x.projectid }}</div></el-col>
              <el-col :span="12"><div>询单日期{{ x.createdate }}</div></el-col>
              <el-col :span="5"><div>已经过{{ x.dura }}小时</div></el-col>
              <el-col :span="5"><div>已评估</div></el-col>
            </el-row>
          </span>
          <br>
          <el-row>
            <el-col :span="5">
              <div>（一）询单人联系信息</div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="5">
              <div>询单单位名称：{{ x.khname }}</div>
            </el-col>
            <el-col :span="5">
              <div>询单人员姓名：{{ x.khryname }}</div>
            </el-col>
            <el-col :span="5">
              <div>询单人员身份：{{ x.khrytype }}</div>
            </el-col>
            <el-col :span="5">
              <div>询单人联系方式：{{ x.khrycontact }}</div>
            </el-col>
          </el-row>
          <br>
          <el-row>
            <el-col :span="5">
              <div>（二）询单产品信息</div>
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
              <img alt="图片未上传" v-bind:src="x.projectdetails" style="max-width:600px"/>
            </el-col>
            <el-col :span="12">
              <div>备注：{{ x.bz }}</div>
            </el-col>
          </el-row>
          <br>
          <el-row>
            <el-col :span="5">
              <div>（三）技术评估状态与结果</div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="5">
              <div>该项目技术评估状态为：{{ x.projectztjs }}</div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <div>请你及时将评估状态或结果告知客户。如果客户有疑问，请你将客户的疑问转告公司“客服人员”，客服人员会与客户进行进一步沟通。</div>
            </el-col>
          </el-row>
          <br>
          <el-row>
            <el-col :span="5">
              <div>（四）客户反馈与项目成交状态</div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="5">
              <div>项目成交状态为：{{ x.projectresultkf }}</div>
            </el-col>
            <el-col :span="10">
              <div>客户反馈：{{ x.khfeedback }}</div>
            </el-col>
          </el-row>
        </el-collapse-item>
      </div>
    </el-collapse>
    <br>
    <el-button v-on:click="$router.replace('/Bindex')">返回</el-button>

  </div>
</template>

<script>
import IdentityCheck from './IdentityCheck'

export default {
  name: 'B2',
  data: function () {
    return {
      activeName: '1',
      date: new Date(),
      account_id: '',
      usertype: '',
      interval1: '',
      interval2: '',
      project_result_kf: '',
      list1: [],
      list2: []
    }
  },
  components: {
    IdentityCheck
  },
  created () {
    // let strOrig, lastIndex
    // strOrig = axios.defaults.baseURL
    // lastIndex = strOrig.lastIndexOf('/')
    // this.url = strOrig.substr(0, lastIndex) + '/'
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
            // console.log(successResponse.data.data)
            this.list1 = successResponse.data.data
            if (this.list1.length < 1) {
              this.$message('查询时间段内无项目')
            } else {
              for (let m in this.list1) {
                this.$axios
                  .post('/lookpic', {
                    imgpath: this.list1[m].projectdetails
                  })
                  .then(successResponse => {
                    if (successResponse.data.success) {
                      console.log(successResponse.data.data)
                      this.list1[m].projectdetails = 'data:image/png;base64,' + successResponse.data.data.base64id
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
    showhavezt () {
      var _this = this
      this.$axios.post('/havezt', {
        interval: _this.interval2,
        resultkf: _this.project_result_kf
      })
        .then(successResponse => {
          if (successResponse.data.success) {
            // console.log(successResponse.data.data)
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
