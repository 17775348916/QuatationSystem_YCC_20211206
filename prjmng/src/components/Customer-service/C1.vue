<template>
  <div>
    <CIdentityCheck></CIdentityCheck>
    <Chead></Chead>
    <br><br>
    <div v-if="project.projectztjs === '未评估'">
      <el-row>
        <el-col :span="4">
          项目编号：{{ project.projectid }}
        </el-col>
        <el-col :span="6">
          询单日期：{{ project.createdate }}
        </el-col>
        <el-col :span="4">
          已经等待时间：{{ project.dura }} 小时
        </el-col>
        <el-col :span="4">
          状态：{{ project.projectztjs }}
        </el-col>
        <el-col :span="4">
          原料任务：{{ project.istaskjs }}
        </el-col>
      </el-row>
      <br>
      <el-row v-for="(item, index) in mlist" :key="index">
        <br>
        <el-row>
          <el-col :span="4">原料{{ index + 1 }}</el-col>
          <el-col :span="4">原料名称：{{ item.materialname }}</el-col>
          <el-col :span="4">Cas号：{{ item.cas }}</el-col>
          <el-col :span="4">需求量：{{ item.materialsl }}</el-col>
          <el-col :span="4">备注：{{ item.bz }}</el-col>
        </el-row>
        <br>
        <el-row>
          <el-col :span="4">
            <el-input v-model="other[index].nmaterialname" placeholder="原料名称"></el-input>
          </el-col>
          <el-col :span="4">
            <el-input v-model="other[index].ncas" placeholder="Cas号"></el-input>
          </el-col>
          <el-col :span="4">
            <el-input v-model="other[index].cgsl"
                      placeholder="采购量"></el-input>
          </el-col>
          <el-col :span="4">
            <el-input v-model="other[index].price" onkeyup="value=value.replace(/[^0-9.]/g,'')"
                      placeholder="价格（元）"></el-input>
          </el-col>
          <el-col :span="4">
            <el-input v-model="other[index].provider" placeholder="供应商"></el-input>
          </el-col>
<!--          <el-col :span="3">-->
<!--            <el-button v-on:click="changeflag(index)">从数据库</el-button>-->
<!--          </el-col>-->
        </el-row>
        <br>
<!--        <el-row v-if="other[index].flag === true">-->
<!--          <el-col :offset="1" :span="8">-->
<!--            <el-input v-model="other[index].info" placeholder="请输入原料名称或cas号进行查询"></el-input>-->
<!--          </el-col>-->
<!--          <el-col :span="4">-->
<!--            <el-button v-on:click="search(index)">查询</el-button>-->
<!--          </el-col>-->
<!--        </el-row>-->
<!--        <div v-if="other[index].sflag && other[index].flag === true">-->
<!--          <el-row v-if="Object.keys(other[index].slist).length>0">-->
<!--            <el-col :offset="1" :span="20">-->
<!--              <el-table-->
<!--                :data="other[index].slist"-->
<!--                height="250"-->
<!--                style="width: 95%">-->
<!--                <el-table-column label="原料名称" prop="materialname"></el-table-column>-->
<!--                <el-table-column label="cas" prop="cas"></el-table-column>-->
<!--                <el-table-column label="规格" prop="quantities"></el-table-column>-->
<!--                <el-table-column label="纯度" prop="qurity"></el-table-column>-->
<!--                <el-table-column label="价格（元）" prop="price"></el-table-column>-->
<!--                <el-table-column label="供应商" prop="provider"></el-table-column>-->
<!--                <el-table-column label="录入时间" prop="createdate"></el-table-column>-->
<!--&lt;!&ndash;                录入时间&ndash;&gt;-->
<!--                <el-table-column label="备注" prop="bz"></el-table-column>-->
<!--&lt;!&ndash;                <el-table-column fixed="right" label="操作" width="100">&ndash;&gt;-->
<!--&lt;!&ndash;                  <template slot-scope="scope">&ndash;&gt;-->
<!--&lt;!&ndash;                    <el-button size="small" type="text" @click="addfromlist2(index,scope.row)">添加</el-button>&ndash;&gt;-->
<!--&lt;!&ndash;                  </template>&ndash;&gt;-->
<!--&lt;!&ndash;                </el-table-column>&ndash;&gt;-->
<!--              </el-table>-->
<!--            </el-col>-->
<!--          </el-row>-->
<!--          <el-row v-else>-->
<!--            <el-col :offset="1" :span="16">-->
<!--              未查询到相关结果！-->
<!--            </el-col>-->
<!--          </el-row>-->
<!--        </div>-->
        <br>
      </el-row>
      <br>
      <el-row>
        <el-col :offset="1" :span="8">
          <div align="left">从原料信息库查询添加</div>
        </el-col>
      </el-row>
      <el-row>
        <el-col :offset="1" :span="8">
          <el-input v-model="info" placeholder="请输入原料名称或cas号进行查询"></el-input>
        </el-col>
        <el-col :span="4">
          <el-button v-on:click="search">查询</el-button>
        </el-col>
      </el-row>
      <el-row v-if="Object.keys(searchlist).length>0">
        <el-col :offset="1" :span="20">
          <el-table
            :data="searchlist"
            height="350"
            style="width: 95%">
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
      <br><br><br>
      <el-row>
        <el-col :offset="2" :span="5">
          <el-button type="primary" @click="submit">完成所有信息提交</el-button>
        </el-col>
        <el-col :offset="1" :span="5">
          <el-button type="primary" @click="cantbuy">买不到原料反馈</el-button>
        </el-col>
        <el-col :offset="1" :span="2">
          <el-button type="primary" @click="$router.replace('/Cindex')">返回</el-button>
        </el-col>
      </el-row>
    </div>
    <div v-else-if="project.projectztjs === '已评估-可行' || project.projectztjs === '已评估-不可行'">
      <table v-if="Object.keys(haslist).length>0" class="table">
        <thead>
        <tr>
          <th>编号</th>
          <th>原料名称</th>
          <th>Cas号</th>
          <th>采购量</th>
          <th>价格</th>
          <th>供应商</th>
          <th>是否可以买到</th>
          <th>买不到原因</th>
          <th>是否删除</th>
        </tr>
        </thead>
        <tbody v-for="(x, index) in haslist" :key="index">
        <tr>
          <th>{{ index + 1 }}</th>
          <th>{{ x.materialname }}</th>
          <th>{{ x.cas }}</th>
          <th>{{ x.cgsl }}</th>
          <th>{{ x.price }}</th>
          <th>{{ x.provider }}</th>
          <th>{{ x.isbuy }}</th>
          <th>
            <div v-if="x.isbuy === '买不到'">{{ x.notreason }}</div>
          </th>
          <th>{{ x.deleteflag }}</th>
        </tr>
        </tbody>
      </table>
      <br>
      <el-button type="primary" @click="$router.replace('/Cindex')">返回</el-button>
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
          console.log(this.project)
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
          this.$message('原料' + (it + 1) + '原料名称缺失，请补充')
          return
        }
        if (!(_this.other[it].ncas)) {
          this.$message('原料' + (it + 1) + 'Cas号缺失，请补充')
          return
        }
        if (!(_this.other[it].cgsl)) {
          this.$message('原料' + (it + 1) + '采购数量缺失，请补充')
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
