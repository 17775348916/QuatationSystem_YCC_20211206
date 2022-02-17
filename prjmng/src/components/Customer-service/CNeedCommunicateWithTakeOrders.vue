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
    <el-row v-for="(item, index) in list1" :key="item.projectid" style="margin-top:10px">
      <el-row >
        <el-col :span="2">编号：{{ item.projectid }}</el-col>
        <el-col :span="5">询单日期：{{ item.createdate }}</el-col>
        <el-col :span="5">完成时间：{{ item.projectZt.evaluationdate }}</el-col>
        <el-col :span="4">耗时：{{ item.projectZt.timed }}小时</el-col>
        <el-col :span="4">状态：{{ item.projectZt.projectztjs }}</el-col>
        <el-col :span="4">成交结果：{{ item.projectZt.projectresultkf }}</el-col>
      </el-row>
      <el-row style="margin-top:10px">
          <el-col :offset="1" :span="5">
            <el-button type="primary" v-on:click="dialogTableVisible1[index].flag1 = true" plain>项目及外包价格
            </el-button>
          </el-col>
          <el-col :span="5">
            <el-button type="primary" v-on:click="dialogTableVisible1[index].flag2 = true" plain>
              接单客户意向
            </el-button>
          </el-col>
          <el-col :span="5">
            <el-button type="primary" v-on:click="dialogTableVisible1[index].flag3 = true" plain>
              确认接单客户与条件
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
            {{ item.isdifficultjs }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-time"></i>
              完成项目时间(天)
            </template>
            {{ item.timeneeded}}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-s-order"></i>
              完成项目的特殊要求
            </template>
            {{ item.bz }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-s-order"></i>
              外包价
            </template>
            {{ item.wbprice }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-document"></i>
              项目技术文档
            </template>
            <div v-for ="(x,index) in item.papersjs" v-bind:key="index">
              <div v-if= "index === 0">
                <div v-if="x.includes('.pdf')">
                  <a :href="x" target="_blank">{{item.paperType[index]}}</a>
                </div>
                <div v-else-if="x.includes('.jpg')">
                  <a :href="x" target="_blank">{{item.paperType[index]}}</a>
                </div>
                <div v-else-if="x.includes('.jpeg')">
                  <a :href="x" target="_blank">{{item.paperType[index]}}</a>
                </div>
                <div v-else-if="x.includes('.png')">
                  <a :href="x" target="_blank">{{item.paperType[index]}}</a>
                </div>
                <div v-else>
                  <a :href="x">{{item.paperType[index]}}</a>
                </div>
              </div>
              <div v-else-if="x.includes('.pdf')">
                <a :href="x" target="_blank">{{item.paperType[index]}}</a>
              </div>
              <div v-else-if="x.includes('.jpg')">
                <a :href="x" target="_blank">{{item.paperType[index]}}</a>
              </div>
              <div v-else-if="x.includes('.jpeg')">
                <a :href="x" target="_blank">{{item.paperType[index]}}</a>
              </div>
              <div v-else-if="x.includes('.png')">
                <a :href="x" target="_blank">{{item.paperType[index]}}</a>
              </div>
              <div v-else>
                <a :href="x">{{item.paperType[index]}}</a>
              </div>
            </div>
          </el-descriptions-item>
        </el-descriptions>
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
                   :page-sizes="[ 5, 10]"
                   :page-size= "pageSize1"
                   v-show="total1>5"
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
      pageSize1: 5,
      total1: 0,
      account_id: window.sessionStorage.getItem('account_id'),
      interval1: '',
      list1: [],
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
    CIdentityCheck
  },
  methods: {
    submitfinal (index) {
      // this.$message('end')
      this.$axios
        .post('/updatePriceAndTime', {
          projectid: this.list1[index].projectid,
          receivekhname: this.form.receivekhname,
          receivekhphone: this.form.receivekhphone,
          neededtime: this.form.neededtime,
          receiveprice: this.form.receiveprice,
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
    }

  }
}
</script>

<style scoped>

</style>
