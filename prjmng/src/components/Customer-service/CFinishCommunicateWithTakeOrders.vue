<template>
  <div>
    <span>查看已经完成接单客户沟通任务的项目,请选择时间范围：</span>
    <select v-model="interval2" placeholder="时间范围">
      <option label="当天" value="1">当天</option>
      <option label="近3天" value="3">近3天</option>
      <option label="近1周" value="7">近1周</option>
      <option label="近1月" value="30">近1月</option>
      <option label="所有" value="30000">所有</option>
    </select>
    <button size="mini" v-on:click="showhavereceive()">查询</button>
    <el-row v-for="(item, index) in list2" :key="item.projectid" style="margin-top:10px">
      <el-row>
        <el-col :span="2">编号：{{ item.projectid }}</el-col>
        <el-col :span="5">询单日期：{{ item.createdate }}</el-col>
        <el-col :span="5">完成时间：{{ item.projectZt.evaluationdate }}</el-col>
        <el-col :span="4">耗时：{{ item.projectZt.timed }}小时</el-col>
        <el-col :span="4">状态：{{ item.projectZt.projectztjs }}</el-col>
        <el-col :span="4">成交结果：{{ item.projectZt.projectresultkf }}</el-col>
      </el-row>

      <el-row style="margin-top:10px">
          <el-col :offset="1" :span="5">
            <el-button  type="primary" v-on:click="dialogTableVisible2[index].flag1 = true" plain>项目及外包价格
            </el-button>
          </el-col>
          <el-col :span="5">
            <el-button  type="primary" v-on:click="dialogTableVisible2[index].flag2 = true" plain>
              接单客户意向
            </el-button>
          </el-col>
          <el-col :span="5">
            <el-button  type="primary" v-on:click="dialogTableVisible2[index].flag3 = true" plain>
              确认接单客户与条件
            </el-button>
          </el-col>
          <el-col :span="5">
            <el-button  type="info">
              实验情况：{{ item.testresult }}
            </el-button>
          </el-col>
      </el-row>
      <br>
      <el-dialog :visible.sync="dialogTableVisible2[index].flag1" title="项目及外包价格">
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
        <el-descriptions :column="3" border>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-user"></i>
              接单客户名称
            </template>
            {{ item.finalreceive.receivekhname}}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-mobile-phone"></i>
              接单客户联系方式
            </template>
            {{ item.finalreceive.receivekhphone}}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-money"></i>
              接单价格
            </template>
            {{ item.finalreceive.receiveprice}}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-time"></i>
              所需时间(天)
            </template>
            {{ item.finalreceive.neededtime}}
          </el-descriptions-item>
        </el-descriptions>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="dialogTableVisible2[index].flag3 = false">确 定</el-button>
        </div>
      </el-dialog>
    </el-row>
    <el-pagination class="fy"
                   layout="total, sizes, prev, pager, next, jumper"
                   :total="total2"
                   :page-sizes="[ 5, 10]"
                   :page-size= "pageSize2"
                   v-show="total2>5"
                   @size-change="handleSizeChange2"
                   @current-change="handleCurrentChange2"
                   background>
    </el-pagination>
  </div>
</template>

<script>
import CIdentityCheck from './CIdentityCheck'
export default {
  name: 'FinishCommunicateWithTakeOrders',
  data () {
    return {
      currentPage2: 1,
      pageSize2: 5,
      total2: 0,
      account_id: window.sessionStorage.getItem('account_id'),
      interval2: '',
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
    }
  },
  components: {
    CIdentityCheck
  },
  methods: {
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
