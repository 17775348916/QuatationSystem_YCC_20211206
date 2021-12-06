<template>
  <div>
    <CIdentityCheck></CIdentityCheck>
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
        <el-col :span="5">编号：{{ item.projectid }}</el-col>
        <el-col :span="7">询单日期：{{ item.createdate }}</el-col>
        <el-col :span="7">完成时间：{{ item.evaluationdate }}</el-col>
      </el-row>
      <br>
      <el-row>
        <el-col :span="5">耗时：{{ item.dura }}小时</el-col>
        <el-col :span="4">状态：{{ item.projectztjs }}</el-col>
        <el-col :span="5">成交结果：{{ item.projectresultkf }}</el-col>
      </el-row>
      <br>
      <el-row>
        <el-row>
          <el-col :offset="2" :span="4">
            <el-button style="color: lightskyblue" v-on:click="dialogTableVisible1[index].flag1 = true">项目及外包价格
            </el-button>
          </el-col>
          <el-col :span="5">
            <el-button style="background-color: yellowgreen" v-on:click="dialogTableVisible1[index].flag2 = true">
              接单客户意向
            </el-button>
          </el-col>
          <el-col :span="5">
            <el-button style="background-color: yellowgreen" v-on:click="dialogTableVisible1[index].flag3 = true">
              确认接单客户与条件
            </el-button>
          </el-col>
          <el-col :span="5">
            <el-button type="info" v-on:click="dialogTableVisible1[index].flag4 = true">实验情况</el-button>
          </el-col>
        </el-row>
      </el-row>
      <br>
      <el-dialog :visible.sync="dialogTableVisible1[index].flag1" title="项目及外包价格">
        <el-row>
          <div align="center">技术难度：{{ item.ztjs.isdifficultjs }}</div>
        </el-row>
        <el-row>
          <div align="center">完成项目时间(天)：{{ item.ztjs.timeneeded }}</div>
        </el-row>
        <el-row>
          <div align="center">完成项目的特殊要求：{{ item.ztjs.bz }}</div>
        </el-row>
        <el-row>
          <div align="center">项目技术文档：</div>
        </el-row>
        <el-row><a :href="url +  item.ztjs.papersjs">文档</a></el-row>
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
        <el-col :span="5">编号：{{ item.projectid }}</el-col>
        <el-col :span="7">询单日期：{{ item.createdate }}</el-col>
        <el-col :span="7">完成时间：{{ item.evaluationdate }}</el-col>
      </el-row>
      <br>
      <el-row>
        <el-col :span="5">耗时：{{ item.dura }}小时</el-col>
        <el-col :span="7">状态：{{ item.projectztjs }}</el-col>
        <el-col :span="7">成交结果：{{ item.projectresultkf }}</el-col>
      </el-row>
      <br>
      <el-row>
        <el-row>
          <el-col :offset="2" :span="4">
            <el-button style="color: lightskyblue" v-on:click="dialogTableVisible2[index].flag1 = true">项目及外包价格
            </el-button>
          </el-col>
          <el-col :span="5">
            <el-button style="background-color: yellowgreen" v-on:click="dialogTableVisible2[index].flag2 = true">
              接单客户意向
            </el-button>
          </el-col>
          <el-col :span="5">
            <el-button style="background-color: yellowgreen" v-on:click="dialogTableVisible2[index].flag3 = true">
              确认接单客户与条件
            </el-button>
          </el-col>
          <el-col :span="5">
            <div>实验情况：{{ item.finalreceive.testresult }}</div>
          </el-col>
        </el-row>
      </el-row>
      <br>
      <el-dialog :visible.sync="dialogTableVisible2[index].flag1" title="项目及外包价格">
        <el-row>
          <div align="center">技术难度：{{ item.ztjs.isdifficultjs }}</div>
        </el-row>
        <el-row>
          <div align="center">完成项目时间(天)：{{ item.ztjs.timeneeded }}</div>
        </el-row>
        <el-row>
          <div align="center">完成项目的特殊要求：{{ item.ztjs.bz }}</div>
        </el-row>
        <el-row>
          <div align="center">项目技术文档：</div>
        </el-row>
        <el-row><a :href="url +  item.ztjs.papersjs">文档</a></el-row>
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
          <div align="center">接单客户名称：{{ item.finalreceivereceivekhname }}</div>
        </el-row>
        <el-row>
          <div align="center">接单客户联系方式：{{ item.finalreceivereceivekhphone }}</div>
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
    // let strOrig, lastIndex
    // strOrig = axios.defaults.baseURL
    // lastIndex = strOrig.lastIndexOf('/')
    // this.url = strOrig.substr(0, lastIndex) + '/'
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
        .post('/updatereceivekh', {
          projectid: _this.list1[index].projectid,
          receivekhname: _this.form.receivekhname,
          receivekhphone: _this.form.receivekhphone,
          neededtime: _this.form.neededtime,
          receiveprice: _this.form.receiveprice,
          isfinalreceive: '是',
          createname: _this.account_id
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
          interval: _this.interval1
        })
        .then(successResponse => {
          if (successResponse.data.success) {
            console.log(successResponse.data.data)
            this.list1 = successResponse.data.data
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
              this.list1[m].ztjs = {
                isdifficultjs: '',
                timeneeded: '',
                bz: '',
                papersjs: ''
              }
              this.$axios
                .post('/queryfeasible', {
                  projectid: this.list1[m].projectid
                })
                .then(successResponse => {
                  if (successResponse.data.success) {
                    console.log(successResponse.data.data)
                    this.list1[m].ztjs = successResponse.data.data
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
      var _this = this
      this.$axios
        .post('/havereceive', {
          interval: _this.interval2
        })
        .then(successResponse => {
          if (successResponse.data.success) {
            // console.log(successResponse.data.data)
            this.list2 = successResponse.data.data
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
              this.list2[m].ztjs = {
                isdifficultjs: '',
                timeneeded: '',
                bz: '',
                papersjs: ''
              }
              this.$axios
                .post('/queryfeasible', {
                  projectid: this.list2[m].projectid
                })
                .then(successResponse => {
                  if (successResponse.data.success) {
                    // console.log(successResponse.data.data)
                    this.list2[m].ztjs = successResponse.data.data
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
                    // console.log(this.list2[m].projectid + ' finalprice info ' + successResponse.data.data)
                    this.list2[m].finalreceive = successResponse.data.data
                    console.log(this.list2[m].finalreceive)
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
