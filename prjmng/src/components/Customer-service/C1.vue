<template>
  <div>
    <CIdentityCheck></CIdentityCheck>
    <br><br>
    <div v-if="project.projectztjs === '未评估'">
      <el-descriptions class="margin-top" title="项目信息" :column="3" :size="medium" style="margin-bottom:20px" border>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-s-opportunity"></i>
            项目编号
          </template>
          {{ project.projectid }}
        </el-descriptions-item>
        <el-descriptions-item >
          <template slot="label" style="width:200px">
            <i class="el-icon-date"></i>
            询单日期
          </template>
          {{ project.createdate }}
        </el-descriptions-item>
        <el-descriptions-item >
          <template slot="label" style="width:200px">
            <i class="el-icon-time"></i>
            已经等待时间(小时)
          </template>
          {{ project.dura }}
        </el-descriptions-item>
        <el-descriptions-item >
          <template slot="label" style="width:200px">
            <i class="el-icon-s-check"></i>
            状态
          </template>
          {{ project.projectztjs }}
        </el-descriptions-item>
        <el-descriptions-item >
          <template slot="label" style="width:200px">
            <i class="el-icon-document"></i>
            原料任务
          </template>
          {{ project.istaskjs }}
        </el-descriptions-item>
      </el-descriptions>
      <br>
      <el-row v-for="(item, index) in mlist" :key="index">
        <el-descriptions class="margin-top" title="原料信息" :column="5" :size="medium" style="margin-bottom:20px" border>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-finished"></i>
              原料序号
            </template>
            {{ index + 1 }}
          </el-descriptions-item>
          <el-descriptions-item >
            <template slot="label" style="width:200px">
              <i class="el-icon-s-opportunity"></i>
              原料名称
            </template>
            {{ item.materialname }}
          </el-descriptions-item>
          <el-descriptions-item >
            <template slot="label" style="width:200px">
              <i class="el-icon-s-data"></i>
              Cas号
            </template>
            {{ item.cas }}
          </el-descriptions-item>
          <el-descriptions-item >
            <template slot="label" style="width:200px">
              <i class="el-icon-s-order"></i>
              需求量
            </template>
            {{ item.materialsl }}
          </el-descriptions-item>
          <el-descriptions-item >
            <template slot="label" style="width:200px">
              <i class="el-icon-help"></i>
              备注
            </template>
            {{ item.bz }}
          </el-descriptions-item>
          <el-descriptions-item >
            <template slot="label" style="width:200px">
              <i class="el-icon-tickets"></i>
              采购量
            </template>
            <el-input v-model="other[index].cgsl"
                      placeholder="采购量"></el-input>
          </el-descriptions-item>
          <el-descriptions-item >
            <template slot="label" style="width:200px">
              <i class="el-icon-money"></i>
              价格（元）
            </template>
            <el-input v-model="other[index].price" onkeyup="value=value.replace(/[^0-9.]/g,'')"
                      placeholder="价格（元）"></el-input>
          </el-descriptions-item>
          <el-descriptions-item >
            <template slot="label" style="width:200px">
              <i class="el-icon-office-building"></i>
              供应商
            </template>
            <el-input v-model="other[index].provider" placeholder="供应商"></el-input>
          </el-descriptions-item>
        </el-descriptions>
      </el-row>
      <el-row style="margin-top:25px">
        <el-col :span="8">
          <div align="left" style="font-family:黑体;font-weight:bold;">查询原料信息</div>
        </el-col>
      </el-row>
      <el-row style="margin-top:20px">
        <el-col :span="8">
          <el-input v-model="info" placeholder="请输入原料名称或cas号进行查询"></el-input>
        </el-col>
        <el-col :span="3">
          <el-button v-on:click="search">查询</el-button>
        </el-col>
        <div align="right">
          <el-button  type="primary" @click="submit">完成所有信息提交</el-button>
          <el-button align="right" type="primary" @click="cantbuy">买不到原料反馈</el-button>
          <el-button align="right" type="primary" v-on:click="$router.replace('/CUnevaluated')">返回</el-button>
        </div>
      </el-row>
      <br>

      <el-row v-if="Object.keys(searchlist).length>0">
        <el-col :span="20">
          <el-table
            :data="searchlist"
            height="350"
            style="width: 50%">
            <el-table-column label="原料名称" prop="materialname"> </el-table-column>
            <el-table-column label="cas" prop="cas"> </el-table-column>
            <el-table-column label="规格" prop="quantities"> </el-table-column>
            <el-table-column label="纯度" prop="qurity"> </el-table-column>
            <el-table-column label="价格" prop="price"> </el-table-column>
            <el-table-column label="供应商" prop="provider"> </el-table-column>
            <el-table-column label="联系方式" prop="providerphone"> </el-table-column>
            <el-table-column label="更新日期" prop="createdate"> </el-table-column>
            <el-table-column label="备注" prop="bz"> </el-table-column>
          </el-table>
        </el-col>
      </el-row>
    </div>
    <div v-else-if="project.projectztjs === '已评估-可行' || project.projectztjs === '已评估-不可行'">
      <el-table v-if="Object.keys(haslist).length>0"
                :data="haslist"
                border
                style="width: 100%">
        <el-table-column
          prop= "materialname"
          label="原料名称">
        </el-table-column>
        <el-table-column
          prop= "cas"
          label="cas号">
        </el-table-column>
        <el-table-column
          prop= "cgsl"
          label="采购量">
        </el-table-column>
        <el-table-column
          prop= "price"
          label="价格">
        </el-table-column>
        <el-table-column
          prop= "provider"
          label="供应商">
        </el-table-column>
        <el-table-column
          prop= "isbuy"
          label="是否可以买到">
        </el-table-column>
        <el-table-column
          prop= "notreason"
          label="买不到原因" >
        </el-table-column>
        <el-table-column
          prop= "deleteflag"
          label="是否删除" >
        </el-table-column>
      </el-table>
      <div v-if="Object.keys(haslist).length===0">原料买不到，无原料信息</div>
      <div align="right" style="margin-top:20px;">
        <el-button align="right" type="primary" v-on:click="$router.replace('/CEvaluated')">返回</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import CIdentityCheck from './CIdentityCheck'
import Chead from './Chead'

export default {
  name: 'C1',
  data () {
    return {
      account_id: window.sessionStorage.getItem('account_id'),
      project: '',
      projectid: '',
      info: '',
      other: [],
      mlist: [],
      haslist: [],
      searchlist: []
    }
  },
  components: {
    CIdentityCheck,
    Chead
  },
  created () {
    if (window.sessionStorage.getItem(this.account_id)) {
      this.projectid = window.sessionStorage.getItem(this.account_id)
    } else {
      this.$router.replace('/Cindex')
    }
    let _this = this
    this.$axios
      .post('/queryproj', {
        projectid: _this.projectid
      })
      .then(successResponse => {
        if (successResponse.data.success) {
          this.project = successResponse.data.data
        } else {
          this.$message(successResponse.data.msg)
        }
      })
      .catch(failResponse => {
      })
    console.log(this.projectid)
    this.$axios
      .post('/querymaterialneed', {
        projectid: _this.projectid
      })
      .then(successResponse => {
        if (successResponse.data.success) {
          this.mlist = successResponse.data.data
          console.log(this.mlist)
          let m = 0
          while (m < this.mlist.length) {
            this.other.push({
              flag: false,
              slist: [],
              info: '',
              sflag: false,
              nmaterialname: this.mlist[m].materialname,
              ncas: this.mlist[m].cas,
              materialid: '0'
            })
            m += 1
          }
        } else {
          this.$message(successResponse.data.msg)
        }
      })
      .catch(failResponse => {
      })
    this.$axios
      .post('/querymateriallr', {
        projectid: _this.projectid
      })
      .then(successResponse => {
        if (successResponse.data.success) {
          this.haslist = successResponse.data.data
          console.log(this.haslist)
        } else {
          this.$message(successResponse.data.msg)
        }
      })
      .catch(failResponse => {
      })
    // let strOrig, lastIndex
    // strOrig = axios.defaults.baseURL
    // lastIndex = strOrig.lastIndexOf('/')
    // this.url = strOrig.substr(0, lastIndex) + '/'
  },
  methods: {
    changeflag (index) {
      if (this.other[index].flag) {
        this.other[index].flag = false
      } else {
        this.other[index].flag = true
      }
    },
    addfromlist2 (index, x) {
      this.other[index].nmaterialname = x.materialname
      this.other[index].ncas = x.cas
      this.other[index].materialid = x.materialid
      this.other[index].provider = x.provider
      console.log(this.other[index])
    },
    search () {
      if (this.info) {
        this.$axios
          .post('/searchmaterial', {
            info: this.info
          })
          .then(successResponse => {
            if (successResponse.data.success) {
              this.searchlist = successResponse.data.data
              console.log(this.searchlist)
              for (let m in this.searchlist) {
                // this.searchlist[m].materialname = this.searchlist[m].materialname ? this.searchlist[m].materialname : ''
                // this.searchlist[m].cas = this.searchlist[m].cas ? this.searchlist[m].cas : ''
                // this.searchlist[m].provider = this.searchlist[m].provider ? this.searchlist[m].provider : ''
                this.searchlist[m].createdate = this.searchlist[m].createdate.substr(0, 10)
              }
              console.log(this.searchlist)
            } else {
              this.$message(successResponse.data.msg)
            }
          })
          .catch(failResponse => {
          })
      }
    },
    cantbuy () {
      this.$prompt('请输入买不到原料的具体原因', '提示', {
        inputType: 'textarea',
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(({value}) => {
        this.$axios
          .post('/cantbuy', {
            projectid: this.project.projectid,
            reason: value,
            fkname: this.account_id
          })
          .then(successResponse => {
            if (successResponse.data.success) {
              this.$message('反馈成功')
              this.$router.replace('/Cindex')
            } else {
              this.$message(successResponse.data.msg)
            }
          })
          .catch(failResponse => {
          })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消输入'
        })
      })
    },
    submit () {
      let _this = this
      for (let it in _this.mlist) {
        if (!(_this.other[it].nmaterialname)) {
          this.$message('原料' + (++it) + '原料名称缺失，请补充')
          return
        }
        if (!(_this.other[it].price)) {
          this.$message('原料' + (++it) + '价格缺失，请补充')
          return
        }
        if (!(_this.other[it].cgsl)) {
          this.$message('原料' + (++it) + '采购数量缺失，请补充')
          return
        }
        if (!(_this.other[it].provider)) {
          this.$message('原料' + (it + 1) + '供应商信息缺失，请补充')
          return
        }
      }
      let resultlist = []
      for (let it in _this.mlist) {
        resultlist.push({
          projectid: _this.projectid,
          materialneedid: _this.mlist[it].materialneedid,
          materialid: _this.other[it].materialid,
          materialname: _this.other[it].nmaterialname,
          cas: _this.other[it].ncas,
          cgsl: _this.other[it].cgsl,
          price: _this.other[it].price,
          provider: _this.other[it].provider,
          is_buy: '能买到',
          deleteflag: '1',
          createname: _this.account_id
        })
      }
      this.$axios
        .post('/fankui', resultlist)
        .then(successResponse => {
          if (successResponse.data.success) {
            this.$message('已告知技术人员项目所需的原料均可获得，原料价格已经提交给后台进行项目报价计算')
            this.$router.replace('/Cindex')
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
