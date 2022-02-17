<template>
  <div>
    <CIdentityCheck></CIdentityCheck>
    <span>查看需要与下单客户沟通的项目，请选择时间范围：</span>
    <select v-model="interval1" placeholder="时间范围">
      <option label="当天" value="1">当天</option>
      <option label="近3天" value="3">近3天</option>
      <option label="近1周" value="7">近1周</option>
      <option label="近1月" value="30">近1月</option>
      <option label="所有" value="30000">所有</option>
    </select>
    <button v-on:click="showunfinish()">查询</button>
    <el-row v-for="(x, index) in list1" :key="x.projectid" style="margin-top:10px">
      <el-row>
        <el-col :span="4">
          <div>编号：{{ x.projectid }}</div>
        </el-col>
        <el-col :span="5">
          <div>询单日期：{{ x.createdate }}</div>
        </el-col>
        <el-col :span="5">
          <div>完成时间：{{ x.projectZt.evaluationdate }}</div>
        </el-col>
        <el-col :span="4">
          <div>耗时：{{ x.projectZt.timed }}小时</div>
        </el-col>
        <el-col :span="4">
          <div>状态：{{ x.projectZt.projectztjs }}</div>
        </el-col>
      </el-row>
      <el-row style="margin-top:10px">
        <el-col :span="2" style="margin-left:45px">
          <el-button  type="primary" v-on:click="dialogTableVisible1[index].mflag = true" plain>原料信息</el-button>
        </el-col>
        <el-col :span="3">
          <el-button type="primary" v-on:click="dialogTableVisible1[index].cflag = true" plain>客户信息</el-button>
        </el-col>
        <el-col :span="3">
          <el-button type="primary" v-on:click="dialogTableVisible1[index].productflag = true" plain>询单产品信息</el-button>
        </el-col>
        <el-col :span="3">
          <el-button type="primary" v-on:click="dialogTableVisible1[index].pgflag = true" plain>项目评估信息</el-button>
        </el-col>
        <el-col :span="3">
          <el-button type="primary" v-on:click="dialogTableVisible1[index].fkflag = true" plain>客户反馈</el-button>
        </el-col>
        <el-col :span="7">
          成交结果:
          <el-select v-model="x.projectresultkf">
            <el-option value="成交"></el-option>
            <el-option value="未成交"></el-option>
          </el-select>
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
        <el-descriptions :column="3" border>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-user"></i>
              询单人员姓名
            </template>
            {{ x.khryname }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-postcard"></i>
              询单人员身份
            </template>
            {{ x.khrytype }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-mobile-phone"></i>
              询单人联系方式
            </template>
            {{ x.khrycontact }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-s-check"></i>
              客户类型
            </template>
            {{ x.khtype }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-news"></i>
              客户合作历史
            </template>
            {{ x.cohistory }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-coordinate"></i>
              是否有钱
            </template>
            {{ x.ismoney }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-user"></i>
              商务人员判断成交可能
            </template>
            {{ x.isdeal }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-user"></i>
              系统生成成交可能
            </template>
            {{ x.isacceptsw }}
          </el-descriptions-item>
        </el-descriptions>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="dialogTableVisible1[index].cflag = false">确 定</el-button>
        </div>
      </el-dialog>
      <el-dialog title="询单产品信息" :visible.sync="dialogTableVisible1[index].productflag">
        <el-descriptions class="margin-top1" title="询单产品信息" :column="3" style="margin-bottom:20px" border>
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
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="dialogTableVisible1[index].productflag = false">确 定</el-button>
        </div>
      </el-dialog>
      <el-dialog title="项目评估信息" :visible.sync="dialogTableVisible1[index].pgflag">
        <div v-if="x.projectztjs === '已评估-不可行'">
          <el-descriptions :column="3" border>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-bell"></i>
                不可行原因
              </template>
              {{ x.reasonsjs }}
            </el-descriptions-item>
          </el-descriptions>
        </div>
        <div v-else-if="x.projectZt.projectztjs === '已评估-可行'">
          <el-descriptions :column="3" border>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-bell"></i>
                技术难度
              </template>
              {{ x.isdifficultjs }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-time"></i>
                完成项目时间(天)
              </template>
              {{ x.timeneeded}}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-s-order"></i>
                完成项目的特殊要求
              </template>
              {{ x.bz }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-price-tag"></i>
                最终价格(元)
              </template>
              {{ x.finalprice }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-money"></i>
                外包价格(元)
              </template>
              {{ x.outsourcingPrice }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-guide"></i>
                报价模式
              </template>
              {{ x.pricemodel }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-document"></i>
                项目技术文档
              </template>
              <div v-for ="(item,index) in x.papersjs" v-bind:key="index">
                <div v-if= "index === 0">
                  <div v-if="item.includes('.pdf')">
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
              </div>
            </el-descriptions-item>
          </el-descriptions>
          <el-row><div align="left" style="margin-top:20px">*最终价格：给询单客户的报价,不考虑额外费用的初步报价</div></el-row>
          <el-row><div align="left" style="margin-top:5px">*外包价格：为了促进成交，您可以在权限范围内，依据外报价的金额，调整给客户的报价</div></el-row>
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
                   background style="margin-top:20px">
    </el-pagination>
  </div>
</template>

<script>
import CIdentityCheck from './CIdentityCheck'
export default {
  name: 'CNeedCommunicateWithOrders',
  data () {
    return {
      interval1: '',
      list1: [],
      currentPage1: 1,
      pageSize1: 5,
      total1: 0,
      account_id: window.sessionStorage.getItem('account_id'),
      dialogTableVisible1: [],
      dialogTableVisible2: [],
      itemkey: '',
      feedback: '',
      url: ''
    }
  },
  created () {
  },
  components: {
    CIdentityCheck
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
            this.list1 = successResponse.data.data.content
            if (this.list1.length < 1) {
              this.$message('查询时间段内无项目')
            }
            this.total1 = successResponse.data.data.totalElements
            for (let m in this.list1) {
              this.dialogTableVisible1.push({
                mflag: false,
                cflag: false,
                pgflag: false,
                fkflag: false,
                productflag: false

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
            } else {
              // eslint-disable-next-line
              for (let m in this.list1) {
                this.dialogTableVisible1.push({
                  mflag: false,
                  cflag: false,
                  pgflag: false,
                  fkflag: false,
                  productflag: false
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
