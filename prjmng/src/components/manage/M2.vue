<template>
  <div>
    <IdentityCheckAd></IdentityCheckAd>
    <el-row v-for="(x, index) in list" :key="x.projectid">
      <br>
      <el-row>
        <el-col :span="3">
          <div>编号：{{ x.projectid }}</div>
        </el-col>
        <el-col :span="7">
          <div>客户：{{ x.khname }} - {{x.khryname}}</div>
        </el-col>
        <el-col :span="3">
          <div>询单产品：{{ x.projectname }}</div>
        </el-col>
        <el-col :span="7">
          <div>询单日期：{{ x.createdate }}</div>
        </el-col>
<!--        <el-col :span="4">-->
<!--          <div>完成时间：{{ x.evaluationdate }}</div>-->
<!--        </el-col>-->
<!--        <el-col :span="3">-->
<!--          <div>耗时：{{ x.dura }}小时</div>-->
<!--        </el-col>-->
<!--        <el-col :span="3">-->
<!--          <div>状态：{{ x.projectztjs }}</div>-->
<!--        </el-col>-->
        <el-col :span="3">
          <div>成交结果：{{ x.projectresultkf }}</div>
        </el-col>
      </el-row>
      <br>
      <el-row>
        <el-col :span="4" >
          <div v-if="x.isacceptsw === '有' && x.fkztkf === '已反馈-能买到'" >
            <el-button type="primary" v-on:click="dialogTableVisible[index].mflag = true">原料信息</el-button>
          </div>
          <div v-else>不存在原料信息</div>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" v-on:click="dialogTableVisible[index].cflag = true">客户信息</el-button>
        </el-col>
        <el-col :span="4">
          <div v-if="x.isacceptsw === '有' && x.projectztjs !== '未评估'" >
            <el-button type="primary" v-on:click="dialogTableVisible[index].pgflag = true">项目评估信息</el-button>
          </div>
          <div v-else>不存在评估信息</div>
        </el-col>
        <el-col :span="4">
<!--          <div v-if="x.khfeedback !== null">-->
            <el-button type="primary" v-on:click="dialogTableVisible[index].fkflag = true">客户反馈</el-button>
<!--          </div>-->
        </el-col>
        <el-col :span="4">
          <div v-if="x.projectztjs === '已评估-可行'" >
          <el-button type="primary" v-on:click="dialogTableVisible[index].priceflag = true">报价计算器计算信息</el-button>
          </div>
          <div v-else>暂无报价结果</div>
        </el-col>
      </el-row>
      <br>
      <el-dialog title="原料信息" :visible.sync="dialogTableVisible[index].mflag">
        <el-table :data="x.mlist">
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
        <el-row><div align="left">询单人员姓名：{{ x.khryname }}</div></el-row><br>
        <el-row><div align="left">询单人员身份：{{ x.khrytype }}</div></el-row><br>
        <el-row><div align="left">询单人联系方式：{{ x.khrycontact }}</div></el-row><br>
        <el-row><div align="left">询单人身份是否真实：{{ x.khryisreal }}</div></el-row><br>
        <el-row><div align="left">客户类型：{{ x.khrytype }}</div></el-row><br>
        <el-row><div align="left">客户合作历史：{{ x.cohistory }}</div></el-row><br>
        <el-row><div align="left">是否有钱：{{ x.ismoney }}</div></el-row><br>
        <el-row><div align="left">商务人员判断成交可能：{{ x.isdeal }}</div></el-row><br>
        <el-row><div align="left">系统生成成交可能：{{ x.isacceptsw  }}</div></el-row><br>
        <el-row><div align="left">录入人：{{ x.createname  }}</div></el-row><br>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="dialogTableVisible[index].cflag = false">确 定</el-button>
        </div>
      </el-dialog>
      <el-dialog title="项目评估信息" :visible.sync="dialogTableVisible[index].pgflag">
        <div v-if="x.projectztjs === '已评估-不可行'">
          <el-row>
            <div align="left">不可行原因：</div>
            <div align="center">
              {{ x.ztjs.reasonsjs }}
            </div>
          </el-row>
        </div>
        <div v-else-if="x.projectztjs === '已评估-可行'">
          <el-row><div align="left">技术难度：{{ x.ztjs.isdifficultjs }}</div></el-row><br>
          <el-row><div align="left">完成项目时间(天)：{{ x.ztjs.timeneeded}}</div></el-row><br>
          <el-row><div align="left">完成项目的特殊要求：{{ x.ztjs.bz }}</div></el-row><br>
          <el-row><div align="left">项目技术文档：<a :href="url +  x.ztjs.papersjs" >文档</a></div></el-row><br>
          <el-row><div align="left">报价：{{ x.finalprice }}</div></el-row><br>
        </div>
        <div>
          <el-row><div align="left">评估人：{{ x.ztjs.evaluationname }}</div></el-row><br>
          <el-row><div align="left">评估日期：{{ x.ztjs.evaluationdate}}</div></el-row><br>
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="dialogTableVisible[index].pgflag = false">确 定</el-button>
        </div>
      </el-dialog>
      <el-dialog title="客户反馈" :visible.sync="dialogTableVisible[index].fkflag">
        <el-row>
          <div align="left">反馈信息：</div>
          <div align="center">
            {{ x.khfeedback }}
          </div>
        </el-row>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="dialogTableVisible[index].fkflag = false">确 定</el-button>
        </div>
      </el-dialog>
      <el-dialog title="报价计算机计算过程" :visible.sync="dialogTableVisible[index].priceflag">
              <el-row>
                <el-row><div align="left">报价模式：{{ x.priceinfo.pricemodel }}</div></el-row><br>
                <el-row><div align="left">原料成本：{{ x.priceinfo.materialcost }}</div></el-row><br>
                <el-row><div align="left">测试成本：{{ x.priceinfo.csmaterialcost }}</div></el-row><br>
                <el-row><div align="left">溶剂耗材成本：{{ x.priceinfo.rjmaterialcost}}</div></el-row><br>
                <el-row><div align="left">外包报价：{{ x.priceinfo.wbprice }}</div></el-row><br>
                <el-row><div align="left">总成本：{{ x.priceinfo.allcost }}</div></el-row><br>
                <el-row><div align="left">最终价格：{{ x.priceinfo.finalprice }}</div></el-row><br>
              </el-row>
              <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="dialogTableVisible[index].priceflag = false">确 定</el-button>
              </div>
            </el-dialog>
    </el-row>
  </div>
</template>

<script>
import IdentityCheckAd from './IdentityCheckAd'
// import axios from 'axios'
export default {
  name: 'M2',
  data () {
    return {
      list: [],
      dialogTableVisible: [],
      url: ''
    }
  },
  created () {
    this.$axios
      .post('/allproj')
      .then(successResponse => {
        if (successResponse.data.success) {
          console.log(successResponse.data.data)
          this.list = successResponse.data.data
          if (this.list.length < 1) {
            this.$message('无项目记录')
          }
          for (let m in this.list) {
            this.dialogTableVisible.push({
              mflag: false,
              cflag: false,
              pgflag: false,
              fkflag: false,
              priceflag: false
            })
            this.list[m].mlist = []
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
            this.$axios
              .post('/querymateriallr', {
                projectid: this.list[m].projectid
              })
              .then(successResponse => {
                if (successResponse.data.success) {
                  // console.log(successResponse.data.data)
                  this.list[m].mlist = successResponse.data.data
                } else {
                  this.$message(successResponse.data.msg)
                }
              })
              .catch(failResponse => {
              })
            if (this.list[m].projectztjs === '已评估-不可行') {
              this.$axios
                .post('/querynofeasible', {
                  projectid: this.list[m].projectid
                })
                .then(successResponse => {
                  if (successResponse.data.success) {
                    console.log(successResponse.data.data)
                    this.list[m].ztjs = successResponse.data.data
                  } else {
                    this.$message(successResponse.data.msg)
                  }
                })
                .catch(failResponse => {
                })
            } else if (this.list[m].projectztjs === '已评估-可行') {
              this.$axios
                .post('/queryfeasible', {
                  projectid: this.list[m].projectid
                })
                .then(successResponse => {
                  if (successResponse.data.success) {
                    // console.log(successResponse.data.data)
                    this.list[m].ztjs = successResponse.data.data
                    this.$axios
                      .post('/querypriceinfo', {
                        projectid: this.list[m].projectid
                      })
                      .then(successResponse => {
                        if (successResponse.data.success) {
                          console.log(successResponse.data.data)
                          this.list[m].priceinfo = successResponse.data.data
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
  components: {
    IdentityCheckAd
  }
}
</script>

<style scoped>

</style>
