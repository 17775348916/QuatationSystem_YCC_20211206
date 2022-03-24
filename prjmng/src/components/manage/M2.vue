<template>
  <div>
    <IdentityCheck></IdentityCheck>
    <div align="left" style="color:red;margin-top:10px">
      *仅展示有成交可能的项目，没有成交可能性的项目请到商务人员系统中查看
      <div>
        *关键字搜索功能：功能支持全部或部分关键字的组合搜索，使用精准匹配查询
        </div>
    </div>
      <el-row style="margin-top:10px" type="flex" align="left">
        <div style="margin-top:10px">询单单位名称:</div>
        <el-input style="width:12%;margin-left:10px" v-model="khName" clearable placeholder="询单单位名称" @keydown.enter.native="queryprojects"></el-input>

          <div style="margin-top:10px;margin-left:20px">询单人员姓名:</div>
          <el-input style="width:12%;margin-left:10px" v-model="customerName" clearable placeholder="询单人员姓名" @keydown.enter.native="queryprojects"></el-input>

        <div style="margin-top:10px;margin-left:20px">产品名称:</div>
          <el-input style="width:12%;margin-left:10px" v-model="productName"  clearable placeholder="产品名称" @keydown.enter.native="queryprojects"></el-input>

        <div style="margin-top:10px;margin-left:20px">cas号:</div>
          <el-input style="width:12%;margin-left:10px" v-model="casName"  clearable placeholder="请输入cas号" @keydown.enter.native="queryprojects"></el-input>
        <el-button style="margin-left:20px; width:10%" type="primary" v-on:click="queryprojects()" icon="el-icon-search" round>查询</el-button>
      </el-row>
    <el-row style="margin-top:20px" type="flex" algin="left">
      <div style="margin-top:10px">选择时间范围:</div>
          <el-select v-model="interval" clearable placeholder="时间范围" style="margin-left:10px; width:12%" type="flex">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
      <div style="margin-top:10px; margin-left:20px">选择成交结果:</div>
          <el-select style="margin-left:10px;width:12%" v-model="project_result_kf" type="flex" clearable placeholder="成交结果">
            <el-option
              v-for="item in options2"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        <div style="margin-top:10px;margin-left:20px" class="demonstration">起止日期：</div>
      <el-date-picker
        v-model="value1"
        type="daterange"
        value-format="yyyy-MM-dd"
        align="right"
        start-placeholder="开始日期"
        range-separator="至"
        end-placeholder="结束日期"
        style="width:30%"
        :picker-options="setDisabled"
      @change="dateFormat">
      </el-date-picker>
    </el-row>
    <hr style="margin-top: 10px">
      <div v-for="(x, index) in list" :key="x.projectid" style="margin-top:20px">
      <el-row>
        <el-col :span="3">
          <div>编号：{{ x.projectid }}</div>
        </el-col>
        <el-col :span="4">
          <div>客户：{{ x.khname }}-{{x.khryname}}</div>
        </el-col>
        <el-col :span="4">
          <div>询单产品：{{ x.projectname }}</div>
        </el-col>
        <el-col :span="6">
          <div>询单日期：{{ x.createdate }}</div>
        </el-col>
        <el-col :span="4">
          <div>成交结果：{{ x.projectZt.projectresultkf }}</div>
        </el-col>
        <el-col :span="2">
          <el-button style="margin-top:-5px" size="small" type="primary" icon="el-icon-download" @click="download(x,index)">下载</el-button>
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
              <i class="el-icon-office-building"></i>
              询单单位名称
            </template>
            {{ x.khname }}
          </el-descriptions-item>
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
              <div v-for ="(item,index) in x.papersjsList" v-bind:key="index">
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
    </div>
    <el-pagination class="fy1"
                   layout="total, sizes, prev, pager, next, jumper"
                   :total="total"
                   :page-sizes="[3, 5]"
                   :page-size= "pageSize"
                   :current-page.sync= "currentPage"
                   v-show="total>3"
                   @size-change="handleSizeChange"
                   @current-change="handleCurrentChange"
                   background style="margin-top:20px">
    </el-pagination>
  </div>
</template>

<script>
import Docxtemplater from 'docxtemplater'
import PizZip from 'pizzip'
import JSZipUtils from 'jszip-utils'
import {saveAs} from 'file-saver'
import JSZip from 'jszip'
import IdentityCheck from './IdentityCheck'
import expressions from 'angular-expressions'
import ImageModule from 'docxtemplater-image-module-free'
export default {
  name: 'M2',
  inject: ['reload'],
  data () {
    return {
      list: [],
      dialogTableVisible: [],
      url: '',
      accountid: window.sessionStorage.getItem('account_id'),
      currentPage: 1,
      pageSize: 3,
      total: 0,
      papersjList: [],
      interval: '',
      project_result_kf: '',
      projectdetailList: [],
      projectIds: [],
      deleteflags: [],
      materials: [],
      khName: '',
      customerName: '',
      productName: '',
      casName: '',
      value1: '',
      startTime: '',
      endTime: '',
      setDisabled: {
        disabledDate (time) {
          return time.getTime() > Date.now()
        }
      },
      options: [
        {
          value: '1',
          label: '当天'
        },
        {
          value: '3',
          label: '近3天'
        },
        {
          value: '7',
          label: '近1周'
        },
        {
          value: '30',
          label: '近1个月'
        },
        {
          value: '365',
          label: '近1年'
        },
        {
          value: '30000',
          label: '所有'
        }
      ],
      options2: [
        {
          value: '所有',
          label: '所有'
        },
        {
          value: '成交',
          label: '成交'
        },
        {
          value: '未成交',
          label: '未成交'
        },
        {
          value: '待定',
          label: '待定'
        }
      ]
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
    dateFormat (picker) {
      this.startTime = picker[0].toString()
      this.endTime = picker[1].toString()
    },
    queryprojects () {
      this.currentPage = 1
      this.$axios
        .post('/queryByKeywords', {
          page: this.currentPage,
          size: this.pageSize,
          interval: this.interval,
          resultkf: this.project_result_kf,
          customerName: this.customerName,
          productName: this.productName,
          casName: this.casName,
          khName: this.khName,
          startTime: this.startTime,
          endTime: this.endTime
        })
        .then(successResponse => {
          if (successResponse.data.success) {
            this.list = successResponse.data.data.content
            this.total = successResponse.data.data.totalElements
            if (this.list.length < 1) {
              this.$message.success('无项目记录')
            } else {
              this.$message.success('查询成功')
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
                      this.$set(this.list[m], 'papersjsList', strs)
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
                  this.$message.error(successResponse.data.msg)
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
    download (x, index) {
      let _this = this
      let promises = []
      const zips = new JSZip()
      let cache = {}
      const promise = new Promise((resolve, reject) => {
        JSZipUtils.getBinaryContent('./static/input.docx', (error, content) => {
          // input.docx是模板。我们在导出的时候，会根据此模板来导出对应的数据
          // 抛出异常
          if (error) {
            this.$message.error('下载异常，请重新下载')
            throw error
          }
          expressions.filters.size = function (input, width, height) {
            return {
              data: input,
              size: [width, height]
            }
          }
          let opts = {}
          opts = { centered: false }
          opts.getImage = function (chartId) {
            return _this.base64DataURLToArrayBuffer(chartId)
          }
          opts.getSize = function () {
            return [580, 300]
          }
          // 创建一个JSZip实例，内容为模板的内容
          let zip = new PizZip(content)
          // 创建并加载docxtemplater实例对象
          let doc = new Docxtemplater()
          doc.attachModule(new ImageModule(opts))
          doc.loadZip(zip)
          // 设置模板变量的值
          doc.setData({
            ...x,
            isdifficultjs: x.ztjs.isdifficultjs,
            ztjsbz: x.ztjs.bz,
            timeneeded: x.ztjs.timeneeded,
            evaluationdate: x.ztjs.evaluationdate,
            evaluationname: x.ztjs.evaluationname,
            pricemodel: x.priceinfo.pricemodel,
            materialcost: x.priceinfo.materialcost,
            csmaterialcost: x.priceinfo.csmaterialcost,
            rjmaterialcost: x.priceinfo.rjmaterialcost,
            allcost: x.priceinfo.allcost,
            wbprice: x.priceinfo.wbprice,
            finalprice: x.priceinfo.finalprice,
            projectresultkf: x.projectZt.projectresultkf,
            ztkhfeedback: x.projectZt.khfeedback,
            downloadTime: new Date(),
            image1: _this.projectdetailList[index],
            table: _this.materials[index]
          })
          try {
            // 用模板变量的值替换所有模板变量
            doc.render()
          } catch (error) {
            // 抛出异常
            let e = {
              message: error.message,
              name: error.name,
              stack: error.stack,
              properties: error.properties
            }
            console.log(JSON.stringify({ error: e }))
            throw error
          }
          // 生成一个代表docxtemplater对象的zip文件（不是一个真实的文件，而是在内存中的表示）
          let out = doc.getZip().generate({
            type: 'blob',
            mimeType:
              'application/vnd.openxmlformats-officedocument.wordprocessingml.document'
          })
          zips.file(x.projectid + '号项目详细信息.docx', out, {binary: true})
          cache[x.projectid + '号项目详细信息.docx'] = out
          resolve()
        })
      })
      promises.push(promise)
      if (x.ztjs.papersjs !== '' && x.ztjs.papersjs !== null && x.projectZt.projectresultkf !== '未成交') {
        for (let i = 0; i < x.papersjsList.length; i++) {
          const promise1 = this.getImgArrayBuffer(x.papersjsList[i]).then((data) => {
            zips.file(x.paperType[i], data, {binary: true})
            cache[x.paperType[i]] = data
          })
          promises.push(promise1)
        }
      }
      Promise.all(promises).then(() => {
        zips.generateAsync({type: 'blob'}).then((content) => {
          saveAs(content, x.projectid + '号项目详细信息.zip')
        })
      })
      this.$message.success(`开始下载${x.projectid}号项目的相关信息`)
    },
    // 通过url 转为blob格式
    getImgArrayBuffer (url) {
      return new Promise((resolve, reject) => {
        let xmlhttp = new XMLHttpRequest()
        xmlhttp.open('GET', url, true)
        xmlhttp.responseType = 'blob'
        xmlhttp.onload = function () {
          if (this.status === 200) {
            resolve(this.response)
          } else {
            reject(this.status)
          }
        }
        xmlhttp.send()
      })
    },
    base64DataURLToArrayBuffer (dataURL) {
      var Buffer = require('safe-buffer').Buffer
      const base64Regex = /^data:image\/(png|jpg|svg|svg\+xml);base64,/
      if (!base64Regex.test(dataURL)) {
        return false
      }
      const stringBase64 = dataURL.replace(base64Regex, '')
      let binaryString
      if (typeof window !== 'undefined') {
        binaryString = window.atob(stringBase64)
      } else {
        binaryString = new Buffer(stringBase64, 'base64').toString('binary')
      }
      const len = binaryString.length
      const bytes = new Uint8Array(len)
      for (let i = 0; i < len; i++) {
        bytes[i] = binaryString.charCodeAt(i)
      }
      return bytes.buffer
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
        .post('/queryByKeywords', {
          page: this.currentPage,
          size: this.pageSize,
          interval: this.interval,
          resultkf: this.project_result_kf,
          customerName: this.customerName,
          productName: this.productName,
          casName: this.casName,
          khName: this.khName,
          startTime: this.startTime,
          endTime: this.endTime
        })
        .then(successResponse => {
          if (successResponse.data.success) {
            this.list = successResponse.data.data.content
            this.total = successResponse.data.data.totalElements
            if (this.list.length < 1) {
              this.$message.success('无项目记录')
            } else {
              this.$message.success('查询成功')
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
                      this.$set(this.list[m], 'papersjsList', strs)
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
                  this.$message.error(successResponse.data.msg)
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
