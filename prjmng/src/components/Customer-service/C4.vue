<template>
  <div>
    <Chead></Chead>
    <br>
    <div>（一）需要与接单客户沟通任务</div>
    下拉菜单选择时间范围：
    <select v-model="interval1" placeholder="时间范围">
      <option label="当天" value="1">当天</option>
      <option label="近3天" value="3">近3天</option>
      <option label="近1周" value="7">近1周</option>
      <option label="近1月" value="30">近1月</option>
      <option label="所有" value="30000">所有</option>
    </select>
    <button v-on:click="showunreceive()">查询</button>
    <br>
    <el-row v-for="(item, index) in list1" :key="item.projectid">
      <el-row>
        <el-col  :span="3">编号：{{ item.projectid }}</el-col>
        <el-col :span="4">询单日期：{{ item.createdate }}</el-col>
        <el-col :span="5">完成时间：{{ item.projectZt.evaluationdate }}</el-col>
        <el-col :span="4">耗时：{{ item.projectZt.timed }}小时</el-col>
        <el-col :span="4">状态：{{ item.projectZt.projectztjs }}</el-col>
        <el-col :span="4">成交结果：{{ item.projectZt.projectresultkf }}</el-col>
      </el-row>
      <br>
      <el-row>
        <el-row>
          <el-col :offset="3" :span="4">
            <el-button type="primary" v-on:click="dialogTableVisible1[index].flag1 = true" plain>项目及外包价格
            </el-button>
          </el-col>
          <el-col :span="4">
            <el-button type="primary" v-on:click="dialogTableVisible1[index].flag2 = true" plain>
              接单客户意向
            </el-button>
          </el-col>
          <el-col :span="4">
            <el-button type="primary" v-on:click="dialogTableVisible1[index].flag3 = true" plain>
              确认接单客户与条件
            </el-button>
          </el-col>
          <el-col :span="4">
            <el-button type="primary" v-on:click="dialogTableVisible1[index].flag4 = true" plain>实验情况</el-button>
          </el-col>
        </el-row>
      </el-row>
      <br>
      <el-dialog :visible.sync="dialogTableVisible1[index].flag1" title="项目及外包价格">
        <el-row>
          <div align="center">技术难度：{{ item.isdifficultjs }}</div>
        </el-row>
        <el-row>
          <div align="center">完成项目时间(天)：{{ item.timeneeded }}</div>
        </el-row>
        <el-row>
          <div align="center">完成项目的特殊要求：{{ item.bz }}</div>
        </el-row>
        <el-row>
          <div align="center">项目技术文档：</div>
        </el-row>
        <el-row>
          <el-col v-for ="(x,ind) in item.papersjs" v-bind:key="ind">
            <div v-if="x.includes('.pdf')">
              <a :href="x" target="_blank">{{item.paperType[ind]}}</a>
            </div>
            <div v-else-if="x.includes('.jpg')">
              <a :href="x" target="_blank">{{item.paperType[ind]}}</a>
            </div>
            <div v-else-if="x.includes('.jpeg')">
              <a :href="x" target="_blank">{{item.paperType[ind]}}</a>
            </div>
            <div v-else-if="x.includes('.png')">
              <a :href="x" target="_blank">{{item.paperType[ind]}}</a>
            </div>
            <div v-else-if="x.includes('.doc')">
              <a :href="x">{{item.paperType[ind]}}</a>
            </div>
            <div v-else-if="x.includes('.docx')">
              <a :href="x">{{item.paperType[ind]}}</a>
            </div>
            <div v-else-if="x.includes('.xls')">
              <a :href="x">{{item.paperType[ind]}}</a>
            </div>
            <div v-else-if="x.includes('.xlsx')">
              <a :href="x">{{item.paperType[ind]}}</a>
            </div>
            <div v-else-if="x.includes('.rar')">
              <a :href="x">{{item.paperType[ind]}}</a>
            </div>
          </el-col>
        </el-row>
        <el-row>
          <div align="center">外包价：{{ item.wbprice }}</div>
        </el-row>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="dialogTableVisible1[index].flag1 = false">确 定</el-button>
        </div>
      </el-dialog>
      <el-dialog :visible.sync="dialogTableVisible1[index].flag2" title="接单客户意向">
        <el-form>
          <el-form-item>接单客户名称：
            <el-input v-model="form.receivekhname"></el-input>
          </el-form-item>
          <el-form-item>接单客户联系方式：
            <el-input v-model="form.receivekhphone"></el-input>
          </el-form-item>
          <el-form-item>接单客户意向：
            <el-input v-model="form.receiveintention"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogTableVisible1[index].flag2 = false">取 消</el-button>
          <el-button type="primary" v-on:click="submityx(index)">提 交</el-button>
        </div>
      </el-dialog>
      <el-dialog :visible.sync="dialogTableVisible1[index].flag3" title="确认接单客户与条件">
        <el-form>
          <el-form-item>接单客户名称：
            <el-input v-model="form.receivekhname"></el-input>
          </el-form-item>
          <el-form-item>接单客户联系方式：
            <el-input v-model="form.receivekhphone"></el-input>
          </el-form-item>
          <el-form-item>接单价：
            <el-input v-model="form.receiveprice" onkeyup="value=value.replace(/[^\d]/g,'')"></el-input>
          </el-form-item>
          <el-form-item>需要时间：
            <el-input v-model="form.neededtime" onkeyup="value=value.replace(/[^\d]/g,'')"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogTableVisible1[index].flag3 = false">取 消</el-button>
          <el-button type="primary" v-on:click="submitfinal(index)">提 交</el-button>
        </div>
      </el-dialog>
      <el-dialog :visible.sync="dialogTableVisible1[index].flag4" title="实验情况">
        <el-form>
          <el-form-item>
            <el-select v-model="testresult">
              <el-option value="成功"></el-option>
              <el-option value="失败"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogTableVisible1[index].flag4 = false">取 消</el-button>
          <el-button type="primary" v-on:click="submittest(index)">提 交</el-button>
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
    <br>
    <div>（二）已经完成接单客户沟通任务</div>
    下拉菜单选择时间范围：
    <select v-model="interval2" placeholder="时间范围">
      <option label="当天" value="1">当天</option>
      <option label="近3天" value="3">近3天</option>
      <option label="近1周" value="7">近1周</option>
      <option label="近1月" value="30">近1月</option>
      <option label="所有" value="30000">所有</option>
    </select>
    <button v-on:click="showhavereceive()">查询</button>
    <br>
    <el-row v-for="(item, index) in list2" :key="item.projectid">
      <el-row>
        <el-col :span="3">编号：{{ item.projectid }}</el-col>
        <el-col :span="4">询单日期：{{ item.createdate }}</el-col>
        <el-col :span="5">完成时间：{{ item.projectZt.evaluationdate }}</el-col>
        <el-col :span="4">耗时：{{ item.projectZt.timed }}小时</el-col>
        <el-col :span="4">状态：{{ item.projectZt.projectztjs }}</el-col>
        <el-col :span="4">成交结果：{{ item.projectZt.projectresultkf }}</el-col>
      </el-row>
      <br>
      <el-row>
        <el-row>
          <el-col :offset="3" :span="4">
            <el-button type="primary" v-on:click="dialogTableVisible2[index].flag1 = true" plain>项目及外包价格
            </el-button>
          </el-col>
          <el-col :span="4">
            <el-button type="primary" v-on:click="dialogTableVisible2[index].flag2 = true" plain>
              接单客户意向
            </el-button>
          </el-col>
          <el-col :span="4">
            <el-button type="primary" v-on:click="dialogTableVisible2[index].flag3 = true" plain>
              确认接单客户与条件
            </el-button>
          </el-col>
          <el-col :span="4">
            <el-button type="info">
              实验情况：{{ item.testresult }}
            </el-button>
          </el-col>
        </el-row>
      </el-row>
      <br>
      <el-dialog :visible.sync="dialogTableVisible2[index].flag1" title="项目及外包价格">
        <el-row>
          <div align="center">技术难度：{{ item.isdifficultjs }}</div>
        </el-row>
        <el-row>
          <div align="center">完成项目时间(天)：{{ item.timeneeded }}</div>
        </el-row>
        <el-row>
          <div align="center">完成项目的特殊要求：{{ item.bz }}</div>
        </el-row>
        <el-row>
          <div align="center">项目技术文档：</div>
        </el-row>
        <el-row>
          <el-col v-for ="(x,ind) in item.papersjs" v-bind:key="ind">
            <div v-if="x.includes('.pdf')">
              <a :href="x" target="_blank">{{item.paperType[ind]}}</a>
            </div>
            <div v-else-if="x.includes('.jpg')">
              <a :href="x" target="_blank">{{item.paperType[ind]}}</a>
            </div>
            <div v-else-if="x.includes('.jpeg')">
              <a :href="x" target="_blank">{{item.paperType[ind]}}</a>
            </div>
            <div v-else-if="x.includes('.png')">
              <a :href="x" target="_blank">{{item.paperType[ind]}}</a>
            </div>
<!--            .jpg,.jpeg,.png,.pdf,.doc,.docx,.xls,.xlsx,.rar-->
            <div v-else-if="x.includes('.doc')">
              <a :href="x">{{item.paperType[ind]}}</a>
            </div>
            <div v-else-if="x.includes('.xls')">
              <a :href="x">{{item.paperType[ind]}}</a>
            </div>
            <div v-else-if="x.includes('.xlsx')">
              <a :href="x">{{item.paperType[ind]}}</a>
            </div>
            <div v-else-if="x.includes('.rar')">
              <a :href="x">{{item.paperType[ind]}}</a>
            </div>
          </el-col>
        </el-row>
        <el-row>
          <div align="center">外包价：{{ item.wbprice }}</div>
        </el-row>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="dialogTableVisible2[index].flag1 = false">确 定</el-button>
        </div>
      </el-dialog>
      <el-dialog :visible.sync="dialogTableVisible2[index].flag2" title="接单客户意向">
        <el-table :data="item.receive" style="width: 100%">
          <el-table-column label="接单客户名称" prop="receivekhname"></el-table-column>
          <el-table-column label="接单客户联系方式" prop="receivekhphone"></el-table-column>
          <el-table-column label="接单意向" prop="receiveintention"></el-table-column>
        </el-table>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="dialogTableVisible2[index].flag2 = false">确 定</el-button>
        </div>
      </el-dialog>
      <el-dialog :visible.sync="dialogTableVisible2[index].flag3" title="确认接单客户与条件">
        <el-row>
          <div align="center">接单客户名称：{{ item.finalreceive.receivekhname }}</div>
        </el-row>
        <el-row>
          <div align="center">接单客户联系方式：{{ item.finalreceive.receivekhphone }}</div>
        </el-row>
        <el-row>
          <div align="center">接单价格：{{ item.finalreceive.receiveprice }}</div>
        </el-row>
        <el-row>
          <div align="center">所需时间：{{ item.finalreceive.neededtime }}</div>
        </el-row>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="dialogTableVisible2[index].flag3 = false">确 定</el-button>
        </div>
      </el-dialog>
    </el-row>
    <el-pagination class="fy"
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
  name: 'C4',
  data () {
    return {
      currentPage1: 1,
      currentPage2: 1,
      pageSize1: 3,
      pageSize2: 3,
      total1: 0,
      total2: 0,
      account_id: window.sessionStorage.getItem('account_id'),
      interval1: '',
      interval2: '',
      list1: [],
      list2: [],
      dialogTableVisible1: [],
      dialogTableVisible2: [],
      itemkey: '',
      form: {
        receivekhname: '',
        receivekhphone: '',
        receiveintention: '',
        receiveprice: '',
        neededtime: ''
      },
      testresult: ''
      // url: ''
    }
  },
  components: {
    CIdentityCheck,
    Chead
  },
  created () {
    // 退出T1 T2 TUnevaluated界面后，就不做自动查询
    if (window.sessionStorage.getItem('TUnevaluatedInterval') != null) {
      window.sessionStorage.removeItem('TUnevaluatedInterval')
    }
    if (window.sessionStorage.getItem('CUnevaluatedInterval') != null) {
      window.sessionStorage.removeItem('CUnevaluatedInterval')
    }
    if (window.sessionStorage.getItem('CEvaluatedInterval') != null) {
      window.sessionStorage.removeItem('CEvaluatedInterval')
    }
  },
  methods: {
    submittest (index) {
      this.$axios
        .post('/updatetestresult', {
          projectid: this.list1[index].projectid,
          testresult: this.testresult,
          updatename: this.account_id
        })
        .then(successResponse => {
          if (successResponse.data.success) {
            this.$message('提交成功')
            this.list1.splice(index, 1)
            this.dialogTableVisible1[index].flag4 = false
          } else {
            this.$message(successResponse.data.msg)
          }
        })
        .catch(failResponse => {
        })
    },
    submitfinal (index) {
      var _this = this
      // this.$message('end')
      this.$axios
        .post('/updatePriceAndTime', {
          projectid: _this.list1[index].projectid,
          receivekhname: _this.form.receivekhname,
          receivekhphone: _this.form.receivekhphone,
          neededtime: _this.form.neededtime,
          receiveprice: _this.form.receiveprice,
          isfinalreceive: '是',
          updatename: _this.account_id
        })
        .then(successResponse => {
          if (successResponse.data.success) {
            this.dialogTableVisible1[index].flag3 = false
            this.$message('提交成功')
          } else {
            this.$message(successResponse.data.msg)
          }
        })
        .catch(failResponse => {
        })
    },
    submityx (index) {
      var _this = this
      // this.$message('end')
      this.$axios
        .post('/updatereceivekh', {
          projectid: _this.list1[index].projectid,
          receivekhname: _this.form.receivekhname,
          receivekhphone: _this.form.receivekhphone,
          receiveintention: _this.form.receiveintention,
          isfinalreceive: '否',
          createname: _this.account_id
        })
        .then(successResponse => {
          if (successResponse.data.success) {
            this.dialogTableVisible1[index].flag2 = false
            this.$message('提交成功')
          } else {
            this.$message(successResponse.data.msg)
          }
        })
        .catch(failResponse => {
        })
    },
    showunreceive () {
      var _this = this
      this.$axios
        .post('/unreceive', {
          interval: _this.interval1,
          page: this.currentPage1,
          size: this.pageSize1
        })
        .then(successResponse => {
          if (successResponse.data.success) {
            console.log(successResponse.data.data)
            this.list1 = successResponse.data.data.content
            this.total1 = successResponse.data.data.totalElements
            if (this.list1.length < 1) {
              this.$message('查询时间段内无项目')
            }
            // eslint-disable-next-line
            for (let m in this.list1) {
              this.dialogTableVisible1.push({
                flag1: false,
                flag2: false,
                flag3: false,
                flag4: false
              })
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
                      .post('/querywbprice', {
                        projectid: this.list1[m].projectid
                      })
                      .then(successResponse => {
                        if (successResponse.data.success) {
                          console.log(successResponse.data.data)
                          this.list1[m].wbprice = successResponse.data.data
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
          } else {
            this.$message(successResponse.data.msg)
          }
        })
        .catch(failResponse => {
        })
    },
    showhavereceive () {
      this.$axios
        .post('/havereceive', {
          interval: this.interval2,
          page: this.currentPage2,
          size: this.pageSize2
        })
        .then(successResponse => {
          if (successResponse.data.success) {
            this.list2 = successResponse.data.data.content
            this.total2 = successResponse.data.data.totalElements
            if (this.list2.length < 1) {
              this.$message('查询时间段内无项目')
            }
            // eslint-disable-next-line
            for (let m in this.list2) {
              this.dialogTableVisible2.push({
                flag1: false,
                flag2: false,
                flag3: false,
                flag4: false
              })

              this.$axios
                .post('/queryfeasible', {
                  projectid: this.list2[m].projectid
                })
                .then(successResponse => {
                  if (successResponse.data.success) {
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
                      .post('/querywbprice', {
                        projectid: this.list2[m].projectid
                      })
                      .then(successResponse => {
                        if (successResponse.data.success) {
                          // console.log(successResponse.data.data)
                          this.list2[m].wbprice = successResponse.data.data
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
              this.list2[m].receive = []
              this.list2[m].finalreceive = {
                receiveprice: '',
                neededtime: '',
                testresult: '',
                receivekhname: '',
                receivekhphone: ''
              }
              this.$axios
                .post('/queryreceive', {
                  projectid: this.list2[m].projectid
                })
                .then(successResponse => {
                  if (successResponse.data.success) {
                    this.list2[m].receive = successResponse.data.data
                  } else {
                    this.$message(successResponse.data.msg)
                  }
                })
                .catch(failResponse => {
                })
              this.$axios
                .post('/queryfinalreceive', {
                  projectid: this.list2[m].projectid
                })
                .then(successResponse => {
                  if (successResponse.data.success) {
                    this.list2[m].finalreceive = successResponse.data.data
                    this.$set(this.list2[m], 'testresult', successResponse.data.data.testresult)
                    console.log('finalreceive', this.list2[m].finalreceive)
                  } else {
                    this.$message(successResponse.data.msg)
                  }
                })
                .catch(failResponse => {
                })
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
        .post('/unreceive', {
          interval: this.interval1,
          page: this.currentPage1,
          size: this.pageSize1
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
                flag1: false,
                flag2: false,
                flag3: false,
                flag4: false
              })
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
                      .post('/querywbprice', {
                        projectid: this.list1[m].projectid
                      })
                      .then(successResponse => {
                        if (successResponse.data.success) {
                          console.log(successResponse.data.data)
                          this.list1[m].wbprice = successResponse.data.data
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
        .post('/havereceive', {
          interval: this.interval2,
          page: this.currentPage2,
          size: this.pageSize2
        })
        .then(successResponse => {
          if (successResponse.data.success) {
            this.list2 = successResponse.data.data.content
            if (this.list2.length < 1) {
              this.$message('查询时间段内无项目')
            }
            // eslint-disable-next-line
            for (let m in this.list2) {
              this.dialogTableVisible2.push({
                flag1: false,
                flag2: false,
                flag3: false,
                flag4: false
              })

              this.$axios
                .post('/queryfeasible', {
                  projectid: this.list2[m].projectid
                })
                .then(successResponse => {
                  if (successResponse.data.success) {
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
                      .post('/querywbprice', {
                        projectid: this.list2[m].projectid
                      })
                      .then(successResponse => {
                        if (successResponse.data.success) {
                          // console.log(successResponse.data.data)
                          this.list2[m].wbprice = successResponse.data.data
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
              this.list2[m].receive = []
              this.list2[m].finalreceive = {
                receiveprice: '',
                neededtime: '',
                testresult: '',
                receivekhname: '',
                receivekhphone: ''
              }
              this.$axios
                .post('/queryreceive', {
                  projectid: this.list2[m].projectid
                })
                .then(successResponse => {
                  if (successResponse.data.success) {
                    this.list2[m].receive = successResponse.data.data
                  } else {
                    this.$message(successResponse.data.msg)
                  }
                })
                .catch(failResponse => {
                })
              this.$axios
                .post('/queryfinalreceive', {
                  projectid: this.list2[m].projectid
                })
                .then(successResponse => {
                  if (successResponse.data.success) {
                    this.list2[m].finalreceive = successResponse.data.data
                    this.$set(this.list2[m], 'testresult', successResponse.data.data.testresult)
                    console.log('finalreceive', this.list2[m].finalreceive)
                  } else {
                    this.$message(successResponse.data.msg)
                  }
                })
                .catch(failResponse => {
                })
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
