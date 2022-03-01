<template>
  <div align="center">
    <IdentityCheck></IdentityCheck>
    <div align="left" style="color:red;margin-top:10px">*仅展示有成交可能的项目，没有成交可能性的项目请到商务人员系统中查看</div>
    下拉菜单选择时间范围：
    <select v-model="interval" placeholder="时间范围" style="margin-right:20px">
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
    <el-button size="small" v-on:click="queryprojects()">查询</el-button>
      <el-row v-for="(x, index) in list" :key="x.projectid" style="margin-top:20px">
      <el-row>
        <el-col :span="4">
          <div>编号：{{ x.projectid }}</div>
        </el-col>
        <el-col :span="4">
          <div>客户：{{ x.khname }} - {{x.khryname}}</div>
        </el-col>
        <el-col :span="4">
          <div>询单产品：{{ x.projectname }}</div>
        </el-col>
        <el-col :span="6">
          <div>询单日期：{{ x.createdate }}</div>
        </el-col>
        <el-col :span="5">
          <div>成交结果：{{ x.projectZt.projectresultkf }}</div>
        </el-col>
      </el-row>
      <el-row style="margin-top:10px">
        <el-col :span="4" >
          <div v-if="x.isacceptsw === '有' && x.projectZt.fkztkf === '已反馈-能买到'" >
            <el-button type="primary" v-on:click="dialogTableVisible[index].mflag = true" plain>原料信息</el-button>
          </div>
          <div v-else>
            <el-button type="warning" plain>
              不存在原料信息
            </el-button>
          </div>
        </el-col>
        <el-col :span="3">
          <el-button type="primary" v-on:click="dialogTableVisible[index].cflag = true" plain>客户信息</el-button>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" v-on:click="dialogTableVisible[index].productflag = true" plain>询单产品信息</el-button>
        </el-col>
        <el-col :span="4">
          <div v-if="x.isacceptsw === '有' && x.projectZt.projectztjs !== '未评估'" >
            <el-button type="primary" v-on:click="dialogTableVisible[index].pgflag = true" plain>项目评估信息</el-button>
          </div>
          <div v-else>
            <el-button type="warning" plain>
              技术人员尚未评估
            </el-button>
          </div>
        </el-col>
        <el-col :span="4">
            <el-button type="primary" v-on:click="dialogTableVisible[index].fkflag = true" plain>客户反馈</el-button>
        </el-col>
        <el-col :span="4">
          <div v-if="x.projectZt.projectztjs === '已评估-可行'" >
          <el-button type="primary" v-on:click="dialogTableVisible[index].priceflag = true" plain>报价计算器计算信息</el-button>
          </div>
          <div v-else>
            <el-button type="warning" plain>
              暂无报价结果
            </el-button>
          </div>
        </el-col>
      </el-row>
      <br>
      <el-dialog title="原料信息" :visible.sync="dialogTableVisible[index].mflag">
        <el-table :data="materials[index]">
          <el-table-column label="原料名称" property="materialname"></el-table-column>
          <el-table-column label="cas" property="cas"></el-table-column>
          <el-table-column label="采购量（g）" property="cgsl"></el-table-column>
          <el-table-column label="价格（元）" property="price"></el-table-column>
          <el-table-column label="供应商" property="provider"></el-table-column>
        </el-table>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="dialogTableVisible[index].mflag = false">确 定</el-button>
        </div>
      </el-dialog>
      <el-dialog title="客户信息" :visible.sync="dialogTableVisible[index].cflag">
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
              询单人员联系方式
            </template>
            {{ x.khrycontact }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-coordinate"></i>
              询单人身份是否真实
            </template>
            {{ x.khryisreal }}
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
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-solid"></i>
              录入人
            </template>
            {{ x.createname }}
          </el-descriptions-item>
        </el-descriptions>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="dialogTableVisible[index].cflag = false">确 定</el-button>
        </div>
      </el-dialog >
        <el-dialog title="询单产品信息" :visible.sync="dialogTableVisible[index].productflag">
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
                  <img alt="图片加载中" v-bind:src="projectdetailList[index]" style="max-width:600px"/>
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
            <el-button type="primary" @click="dialogTableVisible[index].productflag = false">确 定</el-button>
          </div>
        </el-dialog>
      <el-dialog title="项目评估信息" :visible.sync="dialogTableVisible[index].pgflag">
        <div v-if="x.projectZt.projectztjs === '已评估-不可行'">
          <el-descriptions :column="3" border>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-bell"></i>
                不可行原因
              </template>
              {{ x.ztjs.reasonsjs }}
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
              {{ x.ztjs.isdifficultjs }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-time"></i>
                完成项目时间(天)
              </template>
              {{ x.ztjs.timeneeded}}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-s-order"></i>
                完成项目的特殊要求
              </template>
              {{ x.ztjs.bz }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-price-tag"></i>
                最终价格(元)
              </template>
              {{ x.priceinfo.finalprice }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-date"></i>
                评估日期
              </template>
              {{ x.ztjs.evaluationdate }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-user"></i>
                评估人
              </template>
              {{ x.ztjs.evaluationname }}
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
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="dialogTableVisible[index].pgflag = false">确 定</el-button>
        </div>
      </el-dialog>
      <el-dialog title="客户反馈" :visible.sync="dialogTableVisible[index].fkflag">
        <el-descriptions :column="3" border>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-help"></i>
              反馈信息
            </template>
            {{ x.projectZt.khfeedback }}
          </el-descriptions-item>
        </el-descriptions>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="dialogTableVisible[index].fkflag = false">确 定</el-button>
        </div>
      </el-dialog>
      <el-dialog title="报价计算器计算信息" :visible.sync="dialogTableVisible[index].priceflag">
        <el-descriptions :column="3" border>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-coordinate"></i>
              报价模式
            </template>
            {{ x.priceinfo.pricemodel }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-document"></i>
              原料成本(元)
            </template>
            {{ x.priceinfo.materialcost }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-money"></i>
              测试成本(元)
            </template>
            {{ x.priceinfo.csmaterialcost }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-goods"></i>
              溶剂耗材成本(元)
            </template>
            {{ x.priceinfo.rjmaterialcost }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-price-tag"></i>
              总成本(元)
            </template>
            {{ x.priceinfo.allcost }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-wallet"></i>
              外包报价(元)
            </template>
            {{ x.priceinfo.wbprice }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-coin"></i>
              最终价格(元)
            </template>
            {{ x.priceinfo.finalprice }}
          </el-descriptions-item>
        </el-descriptions>
              <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="dialogTableVisible[index].priceflag = false">确 定</el-button>
              </div>
            </el-dialog>
    </el-row>
    <el-pagination class="fy1"
                   layout="total, sizes, prev, pager, next, jumper"
                   :total="total"
                   :page-sizes="[3, 5]"
                   :page-size= "pageSize"
                   v-show="total>3"
                   @size-change="handleSizeChange"
                   @current-change="handleCurrentChange"
                   background style="margin-top:20px">
    </el-pagination>
  </div>
</template>

<script>
import IdentityCheck from './IdentityCheck'
export default {
  name: 'M2',
  data () {
    return {
      list: [],
      dialogTableVisible: [],
      url: '',
      accountid: window.sessionStorage.getItem('account_id'),
      currentPage: 1,
      pageSize: 3,
      total: 0,
      papersjs: [],
      interval: '',
      project_result_kf: '',
      projectdetailList: [],
      projectIds: [],
      deleteflags: [],
      materials: []
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
    queryprojects () {
      this.$axios
        .post('/allproj', {
          page: this.currentPage,
          size: this.pageSize,
          interval: this.interval,
          resultkf: this.project_result_kf
        })
        .then(successResponse => {
          if (successResponse.data.success) {
            this.list = successResponse.data.data.content
            this.total = successResponse.data.data.totalElements
            if (this.list.length < 1) {
              this.$message('无项目记录')
            }
            this.projectIds = []
            this.projectdetailList = []
            this.deleteflags = []
            for (let m in this.list) {
              this.dialogTableVisible.push({
                mflag: false,
                cflag: false,
                pgflag: false,
                fkflag: false,
                priceflag: false,
                productflag: false
              })
              this.list[m].ztjs = {
                reasonjs: '',
                isdifficultjs: '',
                timeneeded: '',
                bz: '',
                papersjs: '',
                evaluationname: '',
                evaluationdate: ''
              }
              this.list[m].priceinfo = {
                pricemodel: '',
                materialcost: '',
                csmaterialcost: '',
                rjmaterialcost: '',
                wbprice: '',
                allcost: '',
                finalprice: ''
              }
              // 为了进行动态处理图片
              this.projectdetailList.push(this.list[m].projectdetails)
              this.projectIds.push(this.list[m].projectid)
              this.deleteflags.push('1')
              if (this.list[m].projectZt.projectztjs === '已评估-不可行') {
                this.$axios
                  .post('/querynofeasible', {
                    projectid: this.list[m].projectid
                  })
                  .then(successResponse => {
                    if (successResponse.data.success) {
                      console.log(successResponse.data.data)
                      this.list[m].ztjs = successResponse.data.data
                      this.list[m].ztjs.evaluationdate = successResponse.data.data.evaluationdate.substr(0, 10)
                    } else {
                      this.$message(successResponse.data.msg)
                    }
                  })
                  .catch(failResponse => {
                  })
              } else if (this.list[m].projectZt.projectztjs === '已评估-可行') {
                this.$axios
                  .post('/queryfeasible', {
                    projectid: this.list[m].projectid
                  })
                  .then(successResponse => {
                    if (successResponse.data.success) {
                      // console.log(successResponse.data.data)
                      this.list[m].ztjs = successResponse.data.data
                      this.list[m].ztjs.evaluationdate = successResponse.data.data.evaluationdate.substr(0, 10)
                      let strs = successResponse.data.data.papersjs.split('||')
                      let paperType = []
                      strs.forEach(item => {
                        let strss = item.split('&&')
                        paperType.push(strss[strss.length - 1])
                      })
                      this.$set(this.list[m], 'paperType', paperType)
                      this.$set(this.list[m], 'papersjs', strs)
                      this.$axios
                        .post('/querypriceinfo', {
                          projectid: this.list[m].projectid
                        })
                        .then(successResponse => {
                          if (successResponse.data.success) {
                            console.log(successResponse.data.data)
                            this.list[m].priceinfo = successResponse.data.data
                            this.list[m].priceinfo.finalprice = successResponse.data.data.finalprice.toFixed(2)
                            this.list[m].priceinfo.wbprice = successResponse.data.data.wbprice.toFixed(2)
                            this.list[m].priceinfo.allcost = successResponse.data.data.allcost.toFixed(2)
                            if (successResponse.data.data.pricemodel === 'A') {
                              this.list[m].priceinfo.pricemodel = 'A' + '（最高价）'
                            } else if (successResponse.data.data.pricemodel === 'B') {
                              this.list[m].priceinfo.pricemodel = 'B' + '（正常价）'
                            } else if (successResponse.data.data.pricemodel === 'C') {
                              this.list[m].priceinfo.pricemodel = 'C' + '（低价）'
                            } else {
                              this.list[m].priceinfo.pricemodel = 'D' + '（特价）'
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
            this.$axios
              .post('/lookpics', {
                imgpath: JSON.stringify(this.projectdetailList)
              })
              .then(successResponse => {
                if (successResponse.data.success) {
                  this.projectdetailList = successResponse.data.data
                  for (let i = 0; i < this.projectdetailList.length; i++) {
                    this.projectdetailList[i] = 'data:image/png;base64,' + this.projectdetailList[i]
                  }
                } else {
                  this.$message(successResponse.data.msg)
                }
              })
              .catch(failResponse => {
              })
            this.$axios
              .post('/queryMaterialByProjectIdAndDeleteflag', {
                projectIds: JSON.stringify(this.projectIds),
                deleteflags: JSON.stringify(this.deleteflags)
              })
              .then(successResponse => {
                if (successResponse.data.success) {
                  this.materials = successResponse.data.data
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
    },
    menuClick (index) {
      this.$router.push(index)
    },
    handleCommand (command) {
      window.sessionStorage.removeItem('account_id')
      window.sessionStorage.removeItem('usertype')
      window.sessionStorage.removeItem('islogin')
      this.$router.replace('/managelogin')
    },
    handleSizeChange (pageSize) {
      this.pageSize = pageSize
      this.getList(this.currentPage)
    },
    handleCurrentChange (currentPage) {
      this.getList(currentPage)
    },
    getList (currentPage) {
      this.currentPage = currentPage
      this.$axios
        .post('/allproj', {
          page: this.currentPage,
          size: this.pageSize,
          interval: this.interval,
          resultkf: this.project_result_kf
        })
        .then(successResponse => {
          if (successResponse.data.success) {
            this.list = successResponse.data.data.content
            this.total = successResponse.data.data.totalElements
            if (this.list.length < 1) {
              this.$message('无项目记录')
            }
            this.projectdetailList = []
            this.projectIds = []
            this.deleteflags = []
            for (let m in this.list) {
              this.dialogTableVisible.push({
                mflag: false,
                cflag: false,
                pgflag: false,
                fkflag: false,
                priceflag: false,
                productflag: false
              })
              this.list[m].ztjs = {
                reasonjs: '',
                isdifficultjs: '',
                timeneeded: '',
                bz: '',
                papersjs: '',
                evaluationname: '',
                evaluationdate: ''
              }
              this.list[m].priceinfo = {
                pricemodel: '',
                materialcost: '',
                csmaterialcost: '',
                rjmaterialcost: '',
                wbprice: '',
                allcost: '',
                finalprice: ''
              }
              // 为了做图片的显示
              this.projectdetailList.push(this.list[m].projectdetails)
              // 用于查询原料信息
              this.projectIds.push(this.list[m].projectid)
              this.deleteflags.push('1')
              if (this.list[m].projectZt.projectztjs === '已评估-不可行') {
                this.$axios
                  .post('/querynofeasible', {
                    projectid: this.list[m].projectid
                  })
                  .then(successResponse => {
                    if (successResponse.data.success) {
                      console.log(successResponse.data.data)
                      this.list[m].ztjs = successResponse.data.data
                      this.list[m].ztjs.evaluationdate = successResponse.data.data.evaluationdate.substr(0, 10)
                    } else {
                      this.$message(successResponse.data.msg)
                    }
                  })
                  .catch(failResponse => {
                  })
              } else if (this.list[m].projectZt.projectztjs === '已评估-可行') {
                this.$axios
                  .post('/queryfeasible', {
                    projectid: this.list[m].projectid
                  })
                  .then(successResponse => {
                    if (successResponse.data.success) {
                      // console.log(successResponse.data.data)
                      this.list[m].ztjs = successResponse.data.data
                      this.list[m].ztjs.evaluationdate = successResponse.data.data.evaluationdate.substr(0, 10)
                      let strs = successResponse.data.data.papersjs.split('||')
                      let paperType = []
                      console.log('strs', strs)
                      strs.forEach(item => {
                        let strss = item.split('&&')
                        paperType.push(strss[strss.length - 1])
                      })
                      this.$set(this.list[m], 'paperType', paperType)
                      this.$set(this.list[m], 'papersjs', strs)
                      this.$axios
                        .post('/querypriceinfo', {
                          projectid: this.list[m].projectid
                        })
                        .then(successResponse => {
                          if (successResponse.data.success) {
                            console.log(successResponse.data.data)
                            this.list[m].priceinfo = successResponse.data.data
                            this.list[m].priceinfo.finalprice = successResponse.data.data.finalprice.toFixed(2)
                            this.list[m].priceinfo.wbprice = successResponse.data.data.wbprice.toFixed(2)
                            this.list[m].priceinfo.allcost = successResponse.data.data.allcost.toFixed(2)
                            if (successResponse.data.data.pricemodel === 'A') {
                              this.list[m].priceinfo.pricemodel = 'A' + '（最高价）'
                            } else if (successResponse.data.data.pricemodel === 'B') {
                              this.list[m].priceinfo.pricemodel = 'B' + '（正常价）'
                            } else if (successResponse.data.data.pricemodel === 'C') {
                              this.list[m].priceinfo.pricemodel = 'C' + '（低价）'
                            } else {
                              this.list[m].priceinfo.pricemodel = 'D' + '（特价）'
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
            this.$axios
              .post('/lookpics', {
                imgpath: JSON.stringify(this.projectdetailList)
              })
              .then(successResponse => {
                if (successResponse.data.success) {
                  this.projectdetailList = successResponse.data.data
                  for (let i = 0; i < this.projectdetailList.length; i++) {
                    this.projectdetailList[i] = 'data:image/png;base64,' + this.projectdetailList[i]
                  }
                } else {
                  this.$message(successResponse.data.msg)
                }
              })
              .catch(failResponse => {
              })
            this.$axios
              .post('/queryMaterialByProjectIdAndDeleteflag', {
                projectIds: JSON.stringify(this.projectIds),
                deleteflags: JSON.stringify(this.deleteflags)
              })
              .then(successResponse => {
                if (successResponse.data.success) {
                  this.materials = successResponse.data.data
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
  },
  components: {
    IdentityCheck
  }
}
</script>

<style scoped>
.el-header, .el-footer {
  background-color: #409eff;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 15px;
  box-sizing: border-box;

}

.title {
  font-size: 30px;
  color: black;
  font-family: 华文楷体;
}
</style>
