<template>
  <div>
    查看需要与接单客户沟通的项目,请选择时间范围：
    <select v-model="interval1" placeholder="时间范围">
      <option label="当天" value="1">当天</option>
      <option label="近3天" value="3">近3天</option>
      <option label="近1周" value="7">近1周</option>
      <option label="近1月" value="30">近1月</option>
      <option label="所有" value="30000">所有</option>
    </select>
    <button size="mini" v-on:click="showunreceive()">查询</button>
    <el-row v-for="(item, index) in list1" :key="index" style="margin-top:10px">
      <el-row>
        <el-col :span="2">编号：{{ item.projectid }}</el-col>
        <el-col :span="5">询单日期：{{ item.createdate }}</el-col>
        <el-col :span="7">技术评估完成时间：{{ item.projectZt.evaluationdate }}</el-col>
        <el-col :span="3">耗时：{{ item.projectZt.timed }}小时</el-col>
        <el-col :span="3">状态：{{ item.projectZt.projectztjs }}</el-col>
        <el-col :span="3">成交结果：{{ item.projectZt.projectresultkf }}</el-col>
      </el-row>
      <el-row style="margin-top:10px">
        <el-col :offset="1" :span="5">
          <el-button type="primary" v-on:click="dialogTableVisible1[index].flag1 = true" plain>项目及外包价格
          </el-button>
        </el-col>
        <el-col :span="3">
          <el-button type="primary" v-on:click="dialogTableVisible1[index].productflag = true" plain>询单产品信息</el-button>
        </el-col>
        <el-col :span="5">
          <el-button type="primary" v-on:click="dialogTableVisible1[index].flag2 = true" plain>
            接单客户意向
          </el-button>
        </el-col>
        <el-col :span="5">
          <el-button type="primary" v-on:click="dialogTableVisible1[index].flag3 = true" plain>
            确认接单条件
          </el-button>
        </el-col>
        <el-col :span="5">
          <el-button type="primary" v-on:click="dialogTableVisible1[index].flag4 = true" plain>实验情况</el-button>
        </el-col>
      </el-row>
      <br>
      <el-dialog :visible.sync="dialogTableVisible1[index].flag1" title="项目及外包价格">
        <el-descriptions :column="3" border>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-bell"></i>
              技术难度
            </template>
            {{ feasibleProjects[index].isdifficultjs }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-time"></i>
              完成项目时间(天)
            </template>
            {{ feasibleProjects[index].timeneeded }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-s-order"></i>
              完成项目的特殊要求
            </template>
            {{ feasibleProjects[index].bz }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-s-order"></i>
              外包价
            </template>
            {{ wbprices[index] }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-document"></i>
              项目技术文档
            </template>
            <div v-for="(x,index1) in item.papersjs" v-bind:key="index1">
              <div v-if="index1 === 0">
                <div v-if="x.includes('.pdf')">
                  <a :href="x" target="_blank">{{ item.paperType[index1] }}</a>
                </div>
                <div v-else-if="x.includes('.jpg')">
                  <a :href="x" target="_blank">{{ item.paperType[index1] }}</a>
                </div>
                <div v-else-if="x.includes('.jpeg')">
                  <a :href="x" target="_blank">{{ item.paperType[index1] }}</a>
                </div>
                <div v-else-if="x.includes('.png')">
                  <a :href="x" target="_blank">{{ item.paperType[index1] }}</a>
                </div>
                <div v-else>
                  <a :href="x">{{ item.paperType[index1] }}</a>
                </div>
              </div>
              <div v-else-if="x.includes('.pdf')">
                <a :href="x" target="_blank">{{ item.paperType[index1] }}</a>
              </div>
              <div v-else-if="x.includes('.jpg')">
                <a :href="x" target="_blank">{{ item.paperType[index1] }}</a>
              </div>
              <div v-else-if="x.includes('.jpeg')">
                <a :href="x" target="_blank">{{ item.paperType[index1] }}</a>
              </div>
              <div v-else-if="x.includes('.png')">
                <a :href="x" target="_blank">{{ item.paperType[index1] }}</a>
              </div>
              <div v-else>
                <a :href="x">{{ item.paperType[index1] }}</a>
              </div>
            </div>
          </el-descriptions-item>
        </el-descriptions>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="dialogTableVisible1[index].flag1 = false">确 定</el-button>
        </div>
      </el-dialog>
      <el-dialog title="询单产品信息" :visible.sync="dialogTableVisible1[index].productflag">
        <el-descriptions class="margin-top1" title="询单产品信息" :column="3" style="margin-bottom:20px" border>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-goods"></i>
              产品名称
            </template>
            {{ item.projectname }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-info"></i>
              cas号
            </template>
            {{ item.cas }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-shopping-cart-full"></i>
              需求量
            </template>
            {{ item.projectsl }}
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
            {{ item.bz }}
          </el-descriptions-item>
        </el-descriptions>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="dialogTableVisible1[index].productflag = false">确 定</el-button>
        </div>
      </el-dialog>
      <el-dialog :visible.sync="dialogTableVisible1[index].flag2" title="接单客户意向">
        <el-form>
          <el-form-item>接单客户名称：
            <el-input v-model="customerIntension[index].receivekhname" clearable></el-input>
          </el-form-item>
          <el-form-item>接单客户联系方式：
            <el-input v-model="customerIntension[index].receivekhphone" clearable></el-input>
          </el-form-item>
          <el-form-item>接单客户意向：
            <el-input v-model="customerIntension[index].receiveintention" clearable></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogTableVisible1[index].flag2 = false">取 消</el-button>
          <el-button type="primary" v-on:click="submityx(index)">提 交</el-button>
        </div>
      </el-dialog>
      <el-dialog :visible.sync="dialogTableVisible1[index].flag3" title="确认接单客户与条件">
        <el-form>
          <el-form-item>接单价（元）：
            <el-input v-model="customerConfirm[index].receiveprice" onkeyup="value=value.replace(/[^\d]/g,'')" placeholder="请填写接单价"></el-input>
          </el-form-item>
          <el-form-item>需要时间（天）：
            <el-input v-model="customerConfirm[index].neededtime" onkeyup="value=value.replace(/[^\d]/g,'')" placeholder="请填写需要时间"></el-input>
          </el-form-item>
          <div align="left" style="color:red">
            *没有历史数据的情况下，默认显示0。有历史数据则显示历史数据！
          </div>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogTableVisible1[index].flag3 = false">取 消</el-button>
          <el-button type="primary" v-on:click="submitfinal(index)">提 交</el-button>
        </div>
      </el-dialog>
      <el-dialog :visible.sync="dialogTableVisible1[index].flag4" title="实验情况">
        <div align="center" style="color:red;margin-top:-30px">
          请您与接单客户保持密切沟通，随时关注实验进展
        </div>
        <div align="center" style="color:red">
          项目结束后，请及时提交最终实验结果，完成该项目的沟通任务
        </div>
        <el-form style="margin-top:10px;">
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
                   :page-sizes="[ 3,5, 10]"
                   :page-size="pageSize1"
                   v-show="total1>3"
                   @size-change="handleSizeChange1"
                   @current-change="handleCurrentChange1"
                   background>
    </el-pagination>
  </div>
</template>

<script>
import CIdentityCheck from './CIdentityCheck'

export default {
  name: 'NeedCommunicateWithTakeOrders',
  data () {
    return {
      currentPage1: 1,
      pageSize1: 3,
      total1: 0,
      account_id: window.sessionStorage.getItem('account_id'),
      interval1: '',
      list1: [],
      dialogTableVisible1: [],
      dialogTableVisible2: [],
      itemkey: '',
      testresult: '',
      projectdetailList: [],
      projectIds: [],
      wbprices: [],
      feasibleProjects: [],
      customerIntension: [],
      customerConfirm: []
    }
  },
  components: {
    CIdentityCheck
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
    submitfinal (index) {
      if (!(this.customerIntension[index].receivekhname) || !(this.customerIntension[index].receivekhphone)) {
        this.$message('请先填写接单客户意向')
        return
      }
      if (!(this.customerConfirm[index].receiveprice)) {
        this.$message('请填写接单价格')
        return
      }
      if (!(this.customerConfirm[index].neededtime)) {
        this.$message('请填写需要时间')
        return
      }
      this.$axios
        .post('/updatePriceAndTime', {
          projectid: this.list1[index].projectid,
          receivekhname: this.customerIntension[index].receivekhname,
          receivekhphone: this.customerIntension[index].receivekhphone,
          neededtime: this.customerConfirm[index].neededtime,
          receiveprice: this.customerConfirm[index].receiveprice,
          isfinalreceive: '是',
          updatename: this.account_id
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
      if (!(this.customerIntension[index].receivekhname)) {
        this.$message('请填写接单客户名称')
        return
      }
      if (!(this.customerIntension[index].receivekhphone)) {
        this.$message('请填写接单客户联系方式')
        return
      }
      if (!(this.customerIntension[index].receivekhname)) {
        this.$message('请填写接单客户意向')
        return
      }
      var _this = this
      // this.$message('end')
      this.$axios
        .post('/updatereceivekh', {
          projectid: _this.list1[index].projectid,
          receivekhname: _this.customerIntension[index].receivekhname,
          receivekhphone: _this.customerIntension[index].receivekhphone,
          receiveintention: _this.customerIntension[index].receiveintention,
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
      this.$axios
        .post('/unreceive', {
          interval: this.interval1,
          page: this.currentPage1,
          size: this.pageSize1
        })
        .then(successResponse => {
          if (successResponse.data.success) {
            this.list1 = successResponse.data.data.content
            this.total1 = successResponse.data.data.totalElements
            if (this.list1.length < 1) {
              this.$message('查询时间段内无项目')
            }
            // eslint-disable-next-line
            this.projectdetailList = []
            this.projectIds = []
            this.wbprices = []
            for (let m in this.list1) {
              this.dialogTableVisible1.push({
                flag1: false,
                flag2: false,
                flag3: false,
                flag4: false,
                productflag: false
              })
              this.projectdetailList.push(this.list1[m].projectdetails)
              this.projectIds.push(this.list1[m].projectid)
            }
            this.$axios
              .post('/queryfeasibleByProjectIds', {
                projectIds: JSON.stringify(this.projectIds)
              })
              .then(successResponse => {
                if (successResponse.data.success) {
                  this.feasibleProjects = successResponse.data.data
                  // 处理其中的参考文献问题
                  for (var i = 0; i < this.feasibleProjects.length; i++) {
                    let strs = this.feasibleProjects[i].papersjs.split('||')
                    let paperType = []
                    strs.forEach(item => {
                      let strss = item.split('&&')
                      paperType.push(strss[strss.length - 1])
                    })
                    this.$set(this.list1[i], 'paperType', paperType)
                    this.$set(this.list1[i], 'papersjs', strs)
                  }
                } else {
                  this.$message(successResponse.data.msg)
                }
              })
              .catch(failResponse => {
              })
            // 批量查看图片
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
              .post('/querywbprices', {
                projectIds: JSON.stringify(this.projectIds)
              })
              .then(successResponse => {
                if (successResponse.data.success) {
                  this.wbprices = successResponse.data.data
                } else {
                  this.$message(successResponse.data.msg)
                }
              })
              .catch(failResponse => {
              })
            this.$axios
              .post('/queryCustomerIntension', {
                projectIds: JSON.stringify(this.projectIds)
              })
              .then(successResponse => {
                if (successResponse.data.success) {
                  this.customerIntension = successResponse.data.data
                } else {
                  this.$message(successResponse.data.msg)
                }
              })
              .catch(failResponse => {
              })
            this.$axios
              .post('/queryCustomerConfirm', {
                projectIds: JSON.stringify(this.projectIds)
              })
              .then(successResponse => {
                if (successResponse.data.success) {
                  this.customerConfirm = successResponse.data.data
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
            this.list1 = successResponse.data.data.content
            this.total1 = successResponse.data.data.totalElements
            if (this.list1.length < 1) {
              this.$message('查询时间段内无项目')
            }
            this.projectIds = []
            this.projectdetailList = []
            this.wbprices = []
            // eslint-disable-next-line
            for (let m in this.list1) {
              this.dialogTableVisible1.push({
                flag1: false,
                flag2: false,
                flag3: false,
                flag4: false,
                productflag: false
              })
              this.projectdetailList.push(this.list1[m].projectdetails)
              this.projectIds.push(this.list1[m].projectid)
            }
            this.$axios
              .post('/queryfeasibleByProjectIds', {
                projectIds: JSON.stringify(this.projectIds)
              })
              .then(successResponse => {
                if (successResponse.data.success) {
                  this.feasibleProjects = successResponse.data.data
                  // 处理其中的参考文献问题
                  for (var i = 0; i < this.feasibleProjects.length; i++) {
                    let strs = this.feasibleProjects[i].papersjs.split('||')
                    let paperType = []
                    strs.forEach(item => {
                      let strss = item.split('&&')
                      paperType.push(strss[strss.length - 1])
                    })
                    this.$set(this.list1[i], 'paperType', paperType)
                    this.$set(this.list1[i], 'papersjs', strs)
                  }
                } else {
                  this.$message(successResponse.data.msg)
                }
              })
              .catch(failResponse => {
              })
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
              .post('/querywbprices', {
                projectIds: JSON.stringify(this.projectIds)
              })
              .then(successResponse => {
                if (successResponse.data.success) {
                  this.wbprices = successResponse.data.data
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
    submittest (index) {
      if (!(this.customerConfirm[index].neededtime) || !(this.customerConfirm[index].receiveprice)) {
        this.$message('请先输入接单客户意向与确认接单条件')
        return
      }
      this.$axios
        .post('/updatetestresult', {
          projectid: this.list1[index].projectid,
          testresult: this.testresult,
          updatename: this.account_id
        })
        .then(successResponse => {
          if (successResponse.data.success) {
            this.$message('提交成功')
            this.dialogTableVisible1[index].flag4 = false
            this.testresult = ''
            this.showunreceive()
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
