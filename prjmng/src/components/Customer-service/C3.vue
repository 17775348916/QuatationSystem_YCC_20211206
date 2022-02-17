<template>
  <div>
    <CIdentityCheck></CIdentityCheck>
    <div>（一）需要与下单客户沟通任务</div>
    下拉菜单选择时间范围：
    <select v-model="interval1" placeholder="时间范围">
      <option label="当天" value="1">当天</option>
      <option label="近3天" value="3">近3天</option>
      <option label="近1周" value="7">近1周</option>
      <option label="近1月" value="30">近1月</option>
      <option label="所有" value="30000">所有</option>
    </select>
    <button v-on:click="showunfinish()">查询</button>
    <el-row v-for="(x, index) in list1" :key="x.projectid">
      <br>
      <el-row>
        <el-col :span="4">
          <div>编号：{{ x.projectid }}</div>
        </el-col>
        <el-col :span="5">
          <div>询单日期：{{ x.createdate }}</div>
        </el-col>
        <el-col :span="5">
          <div>完成时间：{{ x.evaluationdate }}</div>
        </el-col>
        <el-col :span="4">
          <div>耗时：{{ x.projectZt.timed }}小时</div>
        </el-col>
        <el-col :span="4">
          <div>状态：{{ x.projectZt.projectztjs }}</div>
        </el-col>

      </el-row>
      <br>
      <el-row>
        <el-col :span="4">
          <el-button  type="primary" v-on:click="dialogTableVisible1[index].mflag = true" plain>原料信息</el-button>
        </el-col>
        <el-col :span="3">
          <el-button type="primary" v-on:click="dialogTableVisible1[index].cflag = true" plain>客户信息</el-button>
        </el-col>
        <el-col :span="3">
          <el-button type="primary" v-on:click="dialogTableVisible1[index].pgflag = true" plain>项目评估信息</el-button>
        </el-col>
        <el-col :span="3">
          <el-button type="primary" v-on:click="dialogTableVisible1[index].fkflag = true" plain>客户反馈</el-button>
        </el-col>
        <el-col :span="6">
          成交结果:
          <el-select v-model="x.projectresultkf">
            <el-option value="成交"></el-option>
            <el-option value="未成交"></el-option>
          </el-select>
        </el-col>
        <el-col :span="3">
          <el-button type="primary" v-on:click="submit(index)" >提 交</el-button>
        </el-col>
      </el-row>
      <br>
      <el-dialog title="原料信息" :visible.sync="dialogTableVisible1[index].mflag">
        <el-table :data="x.mlist">
                    <el-table-column label="原料名称" property="materialname"></el-table-column>
                    <el-table-column label="cas" property="cas"></el-table-column>
                    <el-table-column label="采购量（g）" property="cgsl"></el-table-column>
                    <el-table-column label="价格（元）" property="price"></el-table-column>
                    <el-table-column label="供应商" property="provider"></el-table-column>
        </el-table>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="dialogTableVisible1[index].mflag = false">确 定</el-button>
        </div>
      </el-dialog>
      <el-dialog title="客户信息" :visible.sync="dialogTableVisible1[index].cflag">
        <el-row><div align="center">询单人员姓名：{{ x.khryname }}</div></el-row>
        <el-row><div align="center">询单人员身份：{{ x.khrytype }}</div></el-row>
        <el-row><div align="center">询单人联系方式：{{ x.khrycontact }}</div></el-row>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="dialogTableVisible1[index].cflag = false">确 定</el-button>
        </div>
      </el-dialog>
      <el-dialog title="项目评估信息" :visible.sync="dialogTableVisible1[index].pgflag">
        <div v-if="x.projectZt.projectztjs === '已评估-不可行'">
          <el-row>
            <div align="left">不可行原因：</div>
            <div align="center">
              {{ x.reasonsjs }}
            </div>
          </el-row>
        </div>
        <div v-else-if="x.projectZt.projectztjs === '已评估-可行'">
          <el-row><div align="center">技术难度：{{ x.isdifficultjs }}</div></el-row>
          <el-row><div align="center">完成项目时间(天)：{{ x.timeneeded}}</div></el-row>
          <el-row><div align="center">完成项目的特殊要求：{{ x.bz }}</div></el-row>
<!--          <el-row><div align="center">项目技术文档：</div></el-row>-->
          <el-row>
            <el-col v-for ="(item,index) in x.papersjs" v-bind:key="index">
              <div v-if= "index === 0">
                <div v-if="item.includes('.pdf')" >
                  项目参考文档：<a :href="item" target="_blank">{{x.paperType[index]}}</a>
                </div>
                <div v-else-if="item.includes('.jpg')">
                  项目参考文档：<a :href="item" target="_blank">{{x.paperType[index]}}</a>
                </div>
                <div v-else-if="item.includes('.jpeg')">
                  项目参考文档：<a :href="item" target="_blank">{{x.paperType[index]}}</a>
                </div>
                <div v-else-if="item.includes('.png')">
                  项目参考文档：<a :href="item" target="_blank">{{x.paperType[index]}}</a>
                </div>
                <div v-if="item.includes('.doc')" >
                  项目参考文档：<a :href="item">{{x.paperType[index]}}</a>
                </div>
                <div v-else-if="item.includes('.docx')">
                  项目参考文档：<a :href="item">{{x.paperType[index]}}</a>
                </div>
                <div v-else-if="item.includes('.xls')">
                  项目参考文档：<a :href="item">{{x.paperType[index]}}</a>
                </div>
                <div v-else-if="item.includes('.xlsx')">
                  项目参考文档：<a :href="item">{{x.paperType[index]}}</a>
                </div>
                <div v-else-if="item.includes('.rar')">
                  项目参考文档：<a :href="item">{{x.paperType[index]}}</a>
                </div>
              </div>
<!--              .jpg,.jpeg,.png,.pdf,.doc,.docx,.xls,.xlsx,.rar-->
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
<!--              <div v-else-if ="!item.includes('.pdf')">-->
<!--                <a :href="item">{{x.paperType[index]}}</a>-->
<!--              </div>-->
            </el-col>
          </el-row>
          <el-row><div align="center">最终价格：{{ x.finalprice }}</div></el-row>
          <el-row><div align="center">外包价格：{{ x.outsourcingPrice }}</div></el-row>
          <el-row><div align="center">报价模式：{{ x.pricemodel }}</div></el-row>
          <el-row><div align="left">*最终价格：给询单客户的报价,不考虑额外费用的初步报价</div></el-row>
          <el-row><div align="left">*外包价格：为了促进成交，您可以在权限范围内，依据外报价的金额，调整给客户的报价</div></el-row>
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="dialogTableVisible1[index].pgflag = false">确 定</el-button>
        </div>
      </el-dialog>
      <el-dialog title="客户反馈" :visible.sync="dialogTableVisible1[index].fkflag">
        <el-form>
          <el-form-item>
            <el-input v-model="feedback">
            </el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogTableVisible1[index].fkflag = false">取 消</el-button>
          <el-button type="primary" v-on:click="submitfk(index)">提 交</el-button>
        </div>
      </el-dialog>
    </el-row>
    <el-pagination class="fy"
                   layout="total, sizes, prev, pager, next, jumper"
                   :total="total1"
                   :page-sizes="[3, 5, 10]"
                   :page-size= "pageSize1"
                   v-show="total1>3"
                   @size-change="handleSizeChange1"
                   @current-change="handleCurrentChange1"
                   background>
    </el-pagination>
    <br><br><br>
    <div>（二）已经与下单客户沟通任务</div>
    下拉菜单选择时间范围：
    <select v-model="interval2" placeholder="时间范围">
      <option label="当天" value="1">当天</option>
      <option label="近3天" value="3">近3天</option>
      <option label="近1周" value="7">近1周</option>
      <option label="近1月" value="30">近1月</option>
      <option label="所有" value="30000">所有</option>
    </select>
    <button v-on:click="showhavefinish()">查询</button>
<!--    <table :data="list2">-->
    <el-row v-for="(x, index) in list2" :key="x.projectid">
      <br>
      <el-row>
        <el-col :span="3">
          <div>编号：{{ x.projectid }}</div>
        </el-col>
        <el-col :span="5">
          <div>询单日期：{{ x.createdate }}</div>
        </el-col>
        <el-col :span="5">
          <div>完成时间：{{ x.projectZt.evaluationdate }}</div>
        </el-col>
        <el-col :span="3">
          <div>耗时：{{ x.projectZt.timed }}小时</div>
        </el-col>
        <el-col :span="3">
          <div>状态：{{ x.projectZt.projectztjs }}</div>
        </el-col>
        <el-col :span="4">
          <div>成交结果：{{ x.projectZt.projectresultkf }}</div>
        </el-col>
      </el-row>
      <br>
      <el-row>
        <el-col :span="4">
          <el-button type="primary" v-on:click="dialogTableVisible2[index].mflag = true" plain>原料信息</el-button>
        </el-col>
        <el-col :span="5">
          <el-button type="primary" v-on:click="dialogTableVisible2[index].cflag = true" plain>客户信息</el-button>
        </el-col>
        <el-col :span="5">
          <el-button type="primary" v-on:click="dialogTableVisible2[index].pgflag = true" >项目评估信息</el-button>
        </el-col>
        <el-col :span="5">
          <el-button type="primary" v-on:click="dialogTableVisible2[index].fkflag = true" plain>客户反馈</el-button>
        </el-col>
      </el-row>
      <br>
      <el-dialog title="原料信息" :visible.sync="dialogTableVisible2[index].mflag" type>
        <el-table :data="x.mlist">
          <el-table-column label="原料名称" property="materialname"></el-table-column>
          <el-table-column label="cas" property="cas"></el-table-column>
          <el-table-column label="采购量（g）" property="cgsl"></el-table-column>
          <el-table-column label="价格（元）" property="price"></el-table-column>
          <el-table-column label="供应商" property="provider"></el-table-column>
        </el-table>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="dialogTableVisible2[index].mflag = false">确 定</el-button>
        </div>
      </el-dialog>
      <el-dialog title="客户信息" :visible.sync="dialogTableVisible2[index].cflag">
        <el-row><div align="center">询单人员姓名：{{ x.khryname }}</div></el-row>
        <el-row><div align="center">询单人员身份：{{ x.khrytype }}</div></el-row>
        <el-row><div align="center">询单人联系方式：{{ x.khrycontact }}</div></el-row>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="dialogTableVisible2[index].cflag = false">确 定</el-button>
        </div>
      </el-dialog>
      <el-dialog title="项目评估信息" :visible.sync="dialogTableVisible2[index].pgflag">
        <div v-if="x.projectztjs === '已评估-不可行'">
          <el-row>
            <div align="left">不可行原因：</div>
            <div align="center">
              {{ x.reasonsjs }}
            </div>
          </el-row>
        </div>
        <div v-else-if="x.projectZt.projectztjs === '已评估-可行'">
          <el-row><div align="center">技术难度：{{ x.isdifficultjs }}</div></el-row>
          <el-row><div align="center">完成项目时间(天)：{{ x.timeneeded}}</div></el-row>
          <el-row><div align="center">完成项目的特殊要求：{{ x.bz }}</div></el-row>
          <el-row><div align="center">项目技术文档：</div></el-row>
          <el-row>
            <el-col v-for ="(item,index) in x.papersjs" v-bind:key="index">
              <div v-if= "index === 0">
                <div v-if="item.includes('.pdf')">
                  <a :href="item" target="_blank">{{x.paperType[index]}}</a>
                </div>
                <div v-else-if="item.includes('.jpg')">
                  项目参考文档：<a :href="item" target="_blank">{{x.paperType[index]}}</a>
                </div>
                <div v-else-if="item.includes('.jpeg')">
                  项目参考文档：<a :href="item" target="_blank">{{x.paperType[index]}}</a>
                </div>
                <div v-else-if="item.includes('.png')">
                  项目参考文档：<a :href="item" target="_blank">{{x.paperType[index]}}</a>
                </div>
                <div v-else>
                  项目参考文档：<a :href="item">{{x.paperType[index]}}</a>
                </div>
              </div>
              <div v-else-if="item.includes('.pdf')">
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
              <div v-else>
                <a :href="item">{{x.paperType[index]}}</a>
              </div>
            </el-col>
          </el-row>
          <el-row><div align="center">最终价格：{{ x.finalprice }}</div></el-row>
          <el-row><div align="center">外包价格：{{ x.outsourcingPrice }}</div></el-row>
          <el-row><div align="center">报价模式：{{ x.pricemodel }}</div></el-row>
          <el-row><div align="left">*最终价格：给询单客户的报价,不考虑额外费用的初步报价</div></el-row>
          <el-row><div align="left">*外包价格：为了促进成交，您可以在权限范围内，依据外报价的金额，调整给客户的报价</div></el-row>
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="dialogTableVisible2[index].pgflag = false">确 定</el-button>
        </div>
      </el-dialog>
      <el-dialog title="客户反馈" :visible.sync="dialogTableVisible2[index].fkflag">
        <el-row>
          <div align="left">反馈信息：</div>
          <div align="center">
            {{ x.khfeedback }}
          </div>
        </el-row>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="dialogTableVisible2[index].fkflag = false">确 定</el-button>
        </div>
      </el-dialog>
    </el-row>
<!--    </table>-->
    <el-pagination class="fy1"
                   layout="total, sizes, prev, pager, next, jumper"
                   :total="total2"
                   :page-sizes="[3, 5, 10]"
                   :page-size= "pageSize2"
                   v-show="total2>3"
                   @size-change="handleSizeChange2"
                   @current-change="handleCurrentChange2"
                   background>
    </el-pagination>
  </div>
</template>

<script>
import CIdentityCheck from './CIdentityCheck'
import Chead from './Chead'
// import axios from 'axios'

export default {
  name: 'C3',
  data () {
    return {
      currentPage1: 1,
      currentPage2: 1,
      total1: 0,
      total2: 0,
      pageSize1: 3,
      pageSize2: 3,
      account_id: window.sessionStorage.getItem('account_id'),
      interval1: '',
      interval2: '',
      list1: [],
      dialogTableVisible1: [],
      dialogTableVisible2: [],
      list2: [],
      itemkey: '',
      feedback: '',
      url: ''
    }
  },
  created () {
    // let strOrig, lastIndex
    // strOrig = axios.defaults.baseURL
    // lastIndex = strOrig.lastIndexOf('/')
    // this.url = strOrig.substr(0, lastIndex) + '/'
  },
  components: {
    CIdentityCheck,
    Chead
  },
  methods: {
    submit (index) {
      this.$axios
        .post('/updateprojectresult', {
          projectid: this.list1[index].projectid,
          projectresultkf: this.list1[index].projectresultkf,
          projectresultname: this.account_id
        })
        .then(successResponse => {
          if (successResponse.data.success) {
            this.$message('提交成功')
            this.list1.splice(index, 1)
          } else {
            this.$message(successResponse.data.msg)
          }
        })
        .catch(failResponse => {
        })
    },
    submitfk (index) {
      this.$axios
        .post('/updatekhfeedback', {
          projectid: this.list1[index].projectid,
          khfeedback: this.feedback,
          khfeedbackname: this.account_id
        })
        .then(successResponse => {
          if (successResponse.data.success) {
            this.dialogTableVisible1[index].fkflag = false
            this.$message('提交成功')
            this.feedback = ''
          } else {
            this.$message(successResponse.data.msg)
          }
        })
        .catch(failResponse => {
          // this.$message('连接错误')
        })
    },
    showunfinish () {
      this.$axios
        .post('/evaluatedPage', {
          page: this.currentPage1,
          size: this.pageSize1,
          interval: this.interval1,
          status: '已评估',
          resultkf: '待定'
        })
        .then(successResponse => {
          if (successResponse.data.success) {
            console.log(successResponse.data.data)
            this.list1 = successResponse.data.data.content
            if (this.list1.length < 1) {
              this.$message('查询时间段内无项目')
            }
            this.total1 = successResponse.data.data.totalElements
            // eslint-disable-next-line
            for (let m in this.list1) {
              this.dialogTableVisible1.push({
                mflag: false,
                cflag: false,
                pgflag: false,
                fkflag: false
              })
              this.list1[m].mlist = []
              this.list1[m].ztjs = {
                reasonjs: '',
                isdifficultjs: '',
                timeneeded: '',
                bz: '',
                papersjs: []
              }
              this.$axios
                .post('/querymateriallr', {
                  projectid: this.list1[m].projectid
                })
                .then(successResponse => {
                  if (successResponse.data.success) {
                    console.log(successResponse.data.data)
                    this.list1[m].mlist = successResponse.data.data
                  } else {
                    this.$message(successResponse.data.msg)
                  }
                })
                .catch(failResponse => {
                })
              if (this.list1[m].projectZt.projectztjs === '已评估-不可行') {
                this.$axios
                  .post('/querynofeasible', {
                    projectid: this.list1[m].projectid
                  })
                  .then(successResponse => {
                    if (successResponse.data.success) {
                      this.$set(this.list2[m], 'reasonsjs', successResponse.data.data.reasonsjs)
                      // this.list1[m].ztjs = successResponse.data.data
                    } else {
                      this.$message(successResponse.data.msg)
                    }
                  })
                  .catch(failResponse => {
                  })
              } else if (this.list1[m].projectZt.projectztjs === '已评估-可行') {
                this.$axios
                  .post('/queryfeasible', {
                    projectid: this.list1[m].projectid
                  })
                  .then(successResponse => {
                    if (successResponse.data.success) {
                      console.log(successResponse.data.data)
                      this.$set(this.list1[m], 'hsl', successResponse.data.data.hsl)
                      this.$set(this.list1[m], 'msl', successResponse.data.data.msl)
                      this.$set(this.list1[m], 'csl', successResponse.data.data.csl)
                      this.$set(this.list1[m], 'isdifficultjs', successResponse.data.data.isdifficultjs)
                      this.$set(this.list1[m], 'timeneeded', successResponse.data.data.timeneeded)
                      this.$set(this.list1[m], 'bz', successResponse.data.data.bz)
                      this.$set(this.list1[m], 'jllsjcs', successResponse.data.data.jllsjcs)
                      this.$set(this.list1[m], 'dtlxsjcs', successResponse.data.data.dtlxsjcs)
                      this.$set(this.list1[m], 'zsjcs', successResponse.data.data.zsjcs)
                      let strs = successResponse.data.data.papersjs.split('||')
                      let paperType = []
                      console.log('strs', strs)
                      strs.forEach(item => {
                        let strss = item.split('&&')
                        paperType.push(strss[strss.length - 1])
                      })
                      this.$set(this.list1[m], 'paperType', paperType)
                      this.$set(this.list1[m], 'papersjs', strs)
                      this.$axios
                        .post('/queryfinalprice', {
                          projectid: this.list1[m].projectid
                        })
                        .then(successResponse => {
                          if (successResponse.data.success) {
                            console.log(successResponse.data.data)
                            this.list1[m].finalprice = successResponse.data.data.finalprice.toFixed(2)
                            this.list1[m].outsourcingPrice = successResponse.data.data.wbprice.toFixed(2)
                            if (successResponse.data.data.pricemodel === 'A') {
                              this.list1[m].pricemodel = 'A' + '（最高价）'
                            } else if (successResponse.data.data.pricemodel === 'B') {
                              this.list1[m].pricemodel = 'B' + '（正常价）'
                            } else if (successResponse.data.data.pricemodel === 'C') {
                              this.list1[m].pricemodel = 'C' + '（低价）'
                            } else {
                              this.list1[m].pricemodel = 'D' + '（特价）'
                            }
                          } else {
                            this.$message(successResponse.data.msg)
                          }
                        })
                        .catch(failResponse => {
                        })
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
    showhavefinish () {
      var _this = this
      this.$axios
        .post('/finishContactPage', {
          status: '已评估',
          resultkf: '待定',
          interval: _this.interval2,
          page: this.currentPage2,
          size: this.pageSize2
        })
        .then(successResponse => {
          if (successResponse.data.success) {
            console.log(successResponse.data.data)
            this.list2 = successResponse.data.data.content
            if (this.list2.length < 1) {
              this.$message('查询时间段内无项目')
            }
            this.total2 = successResponse.data.data.totalElements
            // eslint-disable-next-line
            for (let m in this.list2) {
              this.dialogTableVisible2.push({
                mflag: false,
                cflag: false,
                pgflag: false,
                fkflag: false
              })
              this.list2[m].mlist = []
              this.$axios
                .post('/querymateriallr', {
                  projectid: this.list2[m].projectid
                })
                .then(successResponse => {
                  if (successResponse.data.success) {
                    console.log(successResponse.data.data)
                    this.list2[m].mlist = successResponse.data.data
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
                      console.log(successResponse.data.data)
                      this.$set(this.list2[m], 'reasonsjs', successResponse.data.data.reasonsjs)
                      // this.list2[m].ztjs = successResponse.data.data
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
                      console.log(successResponse.data.data)
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
                      this.$axios
                        .post('/queryfinalprice', {
                          projectid: this.list2[m].projectid
                        })
                        .then(successResponse => {
                          if (successResponse.data.success) {
                            // console.log(successResponse.data.data)
                            this.list2[m].finalprice = successResponse.data.data.finalprice.toFixed(2)
                            this.list2[m].outsourcingPrice = successResponse.data.data.wbprice.toFixed(2)
                            if (successResponse.data.data.pricemodel === 'A') {
                              this.list2[m].pricemodel = 'A' + '（最高价）'
                            } else if (successResponse.data.data.pricemodel === 'B') {
                              this.list2[m].pricemodel = 'B' + '（正常价）'
                            } else if (successResponse.data.data.pricemodel === 'C') {
                              this.list2[m].pricemodel = 'C' + '（低价）'
                            } else {
                              this.list2[m].pricemodel = 'D' + '（特价）'
                            }
                          } else {
                            this.$message(successResponse.data.msg)
                          }
                        })
                        .catch(failResponse => {
                        })
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
        .post('/evaluatedPage', {
          page: this.currentPage1,
          size: this.pageSize1,
          interval: this.interval1,
          status: '已评估',
          resultkf: '待定'
        })
        .then(successResponse => {
          if (successResponse.data.success) {
            console.log(successResponse.data.data)
            this.list1 = successResponse.data.data.content
            if (this.list1.length < 1) {
              this.$message('查询时间段内无项目')
            }
            // eslint-disable-next-line
            for (let m in this.list1) {
              this.dialogTableVisible1.push({
                mflag: false,
                cflag: false,
                pgflag: false,
                fkflag: false
              })
              this.list1[m].mlist = []
              this.list1[m].ztjs = {
                reasonjs: '',
                isdifficultjs: '',
                timeneeded: '',
                bz: '',
                papersjs: []
              }
              this.$axios
                .post('/querymateriallr', {
                  projectid: this.list1[m].projectid
                })
                .then(successResponse => {
                  if (successResponse.data.success) {
                    console.log(successResponse.data.data)
                    this.list1[m].mlist = successResponse.data.data
                  } else {
                    this.$message(successResponse.data.msg)
                  }
                })
                .catch(failResponse => {
                })
              if (this.list1[m].projectZt.projectztjs === '已评估-不可行') {
                this.$axios
                  .post('/querynofeasible', {
                    projectid: this.list1[m].projectid
                  })
                  .then(successResponse => {
                    if (successResponse.data.success) {
                      this.$set(this.list2[m], 'reasonsjs', successResponse.data.data.reasonsjs)
                      // this.list1[m].ztjs = successResponse.data.data
                    } else {
                      this.$message(successResponse.data.msg)
                    }
                  })
                  .catch(failResponse => {
                  })
              } else if (this.list1[m].projectZt.projectztjs === '已评估-可行') {
                this.$axios
                  .post('/queryfeasible', {
                    projectid: this.list1[m].projectid
                  })
                  .then(successResponse => {
                    if (successResponse.data.success) {
                      console.log(successResponse.data.data)
                      this.$set(this.list1[m], 'hsl', successResponse.data.data.hsl)
                      this.$set(this.list1[m], 'msl', successResponse.data.data.msl)
                      this.$set(this.list1[m], 'csl', successResponse.data.data.csl)
                      this.$set(this.list1[m], 'isdifficultjs', successResponse.data.data.isdifficultjs)
                      this.$set(this.list1[m], 'timeneeded', successResponse.data.data.timeneeded)
                      this.$set(this.list1[m], 'bz', successResponse.data.data.bz)
                      this.$set(this.list1[m], 'jllsjcs', successResponse.data.data.jllsjcs)
                      this.$set(this.list1[m], 'dtlxsjcs', successResponse.data.data.dtlxsjcs)
                      this.$set(this.list1[m], 'zsjcs', successResponse.data.data.zsjcs)
                      let strs = successResponse.data.data.papersjs.split('||')
                      let paperType = []
                      console.log('strs', strs)
                      strs.forEach(item => {
                        let strss = item.split('&&')
                        paperType.push(strss[strss.length - 1])
                      })
                      this.$set(this.list1[m], 'paperType', paperType)
                      this.$set(this.list1[m], 'papersjs', strs)
                      this.$axios
                        .post('/queryfinalprice', {
                          projectid: this.list1[m].projectid
                        })
                        .then(successResponse => {
                          if (successResponse.data.success) {
                            console.log(successResponse.data.data)
                            this.list1[m].finalprice = successResponse.data.data.finalprice.toFixed(2)
                            this.list1[m].outsourcingPrice = successResponse.data.data.wbprice.toFixed(2)
                            if (successResponse.data.data.pricemodel === 'A') {
                              this.list1[m].pricemodel = 'A' + '（最高价）'
                            } else if (successResponse.data.data.pricemodel === 'B') {
                              this.list1[m].pricemodel = 'B' + '（正常价）'
                            } else if (successResponse.data.data.pricemodel === 'C') {
                              this.list1[m].pricemodel = 'C' + '（低价）'
                            } else {
                              this.list1[m].pricemodel = 'D' + '（特价）'
                            }
                          } else {
                            this.$message(successResponse.data.msg)
                          }
                        })
                        .catch(failResponse => {
                        })
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
    handleSizeChange2 (pageSize) {
      this.pageSize2 = pageSize
      this.getList2(this.currentPage2)
    },
    handleCurrentChange2 (currentPage) {
      this.getList2(currentPage)
    },
    getList2 (currentPage) {
      this.currentPage2 = currentPage
      this.$axios
        .post('/finishContactPage', {
          status: '已评估',
          resultkf: '待定',
          interval: this.interval2,
          page: this.currentPage2,
          size: this.pageSize2
        })
        .then(successResponse => {
          if (successResponse.data.success) {
            console.log(successResponse.data.data)
            this.list2 = successResponse.data.data.content
            if (this.list2.length < 1) {
              this.$message('查询时间段内无项目')
            }
            // eslint-disable-next-line
            for (let m in this.list2) {
              this.dialogTableVisible2.push({
                mflag: false,
                cflag: false,
                pgflag: false,
                fkflag: false
              })
              this.list2[m].mlist = []
              this.$axios
                .post('/querymateriallr', {
                  projectid: this.list2[m].projectid
                })
                .then(successResponse => {
                  if (successResponse.data.success) {
                    console.log(successResponse.data.data)
                    this.list2[m].mlist = successResponse.data.data
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
                      console.log(successResponse.data.data)
                      this.$set(this.list2[m], 'reasonsjs', successResponse.data.data.reasonsjs)
                      // this.list2[m].ztjs = successResponse.data.data
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
                      console.log(successResponse.data.data)
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
                      this.$axios
                        .post('/queryfinalprice', {
                          projectid: this.list2[m].projectid
                        })
                        .then(successResponse => {
                          if (successResponse.data.success) {
                            // console.log(successResponse.data.data)
                            this.list2[m].finalprice = successResponse.data.data.finalprice.toFixed(2)
                            this.list2[m].outsourcingPrice = successResponse.data.data.wbprice.toFixed(2)
                            if (successResponse.data.data.pricemodel === 'A') {
                              this.list2[m].pricemodel = 'A' + '（最高价）'
                            } else if (successResponse.data.data.pricemodel === 'B') {
                              this.list2[m].pricemodel = 'B' + '（正常价）'
                            } else if (successResponse.data.data.pricemodel === 'C') {
                              this.list2[m].pricemodel = 'C' + '（低价）'
                            } else {
                              this.list2[m].pricemodel = 'D' + '（特价）'
                            }
                          } else {
                            this.$message(successResponse.data.msg)
                          }
                        })
                        .catch(failResponse => {
                        })
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
.table {
  width: 1400px;
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
