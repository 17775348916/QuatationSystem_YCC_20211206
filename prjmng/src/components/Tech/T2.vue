<template>
  <div>
    <TIdentityCheck></TIdentityCheck>
    <el-descriptions :column="4" style="margin-top:20px" border>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-user"></i>
          订单编号
        </template>
        {{ project.projectid }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-date"></i>
          询单日期
        </template>
        {{ project.createdate }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-time"></i>
          经过时间(小时)
        </template>
        {{ project.dura }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-document"></i>
          需求产品名称
        </template>
        {{ project.projectname }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-coordinate"></i>
          Cas号
        </template>
        {{ project.cas }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-coordinate"></i>
          需求量
        </template>
        {{ project.projectsl }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-picture"></i>
          产品结构式图片
        </template>
        <div>
          <el-popover
            placement="top-end"
            width="150px"
            trigger="click">
            <img alt="图片加载中" v-bind:src="project.projectdetails" style="max-width:600px"/>
            <el-button slot="reference">预览</el-button>
          </el-popover>
        </div>
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-help"></i>
          客户对产品纯度，货期等方面特殊要求
        </template>
        {{ project.bz }}
      </el-descriptions-item>
    </el-descriptions>
    <br>
    <el-form>
      <el-form-item>
        技术可行性：
        <el-select v-model="input.evaluationjs" placeholder="请判断技术可行性">
          <el-option v-if="project.fkztkf === '已反馈-能买到'" label="可行" value="已评估-可行"></el-option>
          <el-option label="不可行" value="已评估-不可行"></el-option>
        </el-select>
      </el-form-item>
      <div v-if="input.evaluationjs === '已评估-可行'">
        <el-descriptions :column="3" title="需要进行测试信息" border>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-s-order"></i>
              氢谱(个)
            </template>
            <el-input-number size="small" v-model="input.hsl"></el-input-number>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-s-order"></i>
              碳谱(个)
            </template>
            <el-input-number size="small" v-model="input.csl"></el-input-number>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-s-order"></i>
              质谱(个)
            </template>
            <el-input-number size="small" v-model="input.msl"></el-input-number>
          </el-descriptions-item>
        </el-descriptions>
        <el-descriptions :column="3" title="项目花费时间与技术难度分析" style="margin-top:20px" border>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-time"></i>
              打通路线时间(天)
            </template>
            <el-input size="small" v-model="input.dtlxsjcs" oninput="value=value.replace(/[^0-9.]/g,'')"></el-input>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-s-order"></i>
              积累量时间(天)
            </template>
            <el-input size="small" v-model="input.jllsjcs" oninput="value=value.replace(/[^0-9.]/g,'')"></el-input>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-s-order"></i>
              技术难度
            </template>
            <el-select size="small" v-model="input.isdifficultjs" placeholder="难 or 一般 or 容易">
              <el-option label="难" value="难"></el-option>
              <el-option label="一般" value="一般"></el-option>
              <el-option label="容易" value="容易"></el-option>
            </el-select>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-s-time"></i>
              完成项目时间(天)
            </template>
            <el-input size="small" v-model="input.timeneeded" placeholder="输入项目需要的总时间" oninput="value=value.replace(/[^0-9.]/g,'')"></el-input>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-s-time"></i>
              完成项目的特殊要求
            </template>
            <el-input size="small" v-model="input.bz" type="textarea" placeholder="请在此输入完成项目的特殊要求，例如：需要客户提供特定的原料信息，项目的技术难点（例如产品不稳定、纯度特殊性等）。方便我们的“客服人员”与客户进行沟通。
如果没有，请输入“无”"></el-input>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-s-time"></i>
              项目参考文件(大小限制为50MB)
            </template>
            <Papers-Upload ref="PapersUpload"></Papers-Upload>
          </el-descriptions-item>
        </el-descriptions>
        <br>
        <div align="left" style="font-size:16px;color:red">
          *完成项目时间(天)：此时间用于计算报价，如果技术难度大，完成项目时间可适当延长
        </div>
        <br>
        <div align="left" style="font-size:16px;color:red">
          *完成项目的特殊要求：例如需要客户提供特定的原料信息，项目的技术难点（产品不稳定、纯度特殊性等）。方便客服人员与客户进行沟通。 如果没有，请输入“无”
        </div>
        <br>
        <div align="right">
          <el-button type="primary" v-on:click="feasible_submit">提交评估信息</el-button>
        </div>
      </div>
      <div v-else-if="input.evaluationjs === '已评估-不可行'">
        <el-form-item>
          <el-input v-model="input.reasonsjs" autosize placeholder="请输入“技术不可行”具体原因，以便“客服人员”向客户汇报回复。"
                    type="textarea"></el-input>
        </el-form-item>
        <div align="right">
          <el-button type="primary" v-on:click="unfeasible_submit">提交评估信息</el-button>
        </div>
      </div>
    </el-form>
<!--    <el-button v-on:click="$router.replace('/Tindex')">返回</el-button>-->
  </div>
</template>

<script>
import TIdentityCheck from './TIdentityCheck'
import PapersUpload from '../PapersUpload'

export default {
  name: 'T2',
  data () {
    return {
      account_id: window.sessionStorage.getItem('account_id'),
      projectid: '',
      project: '',
      input:
        {
          hsl: '', // 氢谱数量
          csl: '', // 碳谱
          msl: '', // 质谱
          reasonsjs: '', // 不可行项目中的不可行原因
          dtlxsjcs: '0', // 大于或等于零的数字，可输入小数
          jllsjcs: '0', // 大于或等于零的数字，可输入小数
          zsjcs: '0', // 打通路线时间+积累量时间
          isdifficultjs: '', // “难”、“一般”、“简单”
          papersjs: window.sessionStorage.getItem('PaperUrl'), // 技术判断查阅的相关文献
          timeneeded: '', // 用于客服人员拟合同（比完成项目时间适当多几天）
          bz: '', // 请在此输入完成项目的特殊要求，例如：需要客户提供特定的原料信息，项目的技术难点
          evaluationjs: '未评估', //
          createname: '' //
        }
      // url: ''
    }
  },
  created () {
    if (window.sessionStorage.getItem('CEvaluatedInterval') != null) {
      window.sessionStorage.removeItem('CEvaluatedInterval')
    }
    if (window.sessionStorage.getItem('CUnevaluatedInterval') != null) {
      window.sessionStorage.removeItem('CUnevaluatedInterval')
    }
    if (window.sessionStorage.getItem(this.account_id)) {
      this.projectid = window.sessionStorage.getItem(this.account_id)
    } else {
      this.$router.replace('/Tindex')
    }
    var _this = this
    this.$axios
      .post('/queryproj', {
        projectid: _this.projectid
      })
      .then(successResponse => {
        if (successResponse.data.success) {
          this.project = successResponse.data.data
          this.$axios
            .post('/lookpic', {
              imgpath: this.project.projectdetails
            })
            .then(successResponse => {
              // console.log(successResponse.data)
              if (successResponse.data.success) {
                console.log(successResponse.data.data)
                this.project.projectdetails = 'data:image/png;base64,' + successResponse.data.data.base64id
              } else {
                this.$message(successResponse.data.msg)
              }
            })
            .catch(failResponse => {
            })
          console.log(this.project)
        } else {
          this.$message(successResponse.data.msg)
        }
      })
      .catch(failResponse => {
      })
  },
  components: {
    TIdentityCheck,
    PapersUpload
  },
  methods: {
    feasible_submit () {
      let _this = this
      this.$axios
        .post('/JS/AddNewFeasibleProj', {
          projectid: _this.projectid,
          hsl: _this.input.hsl,
          csl: _this.input.csl,
          msl: _this.input.msl,
          dtlxsjcs: _this.input.dtlxsjcs,
          jllsjcs: _this.input.jllsjcs,
          zsjcs: Number(_this.input.dtlxsjcs) + Number(_this.input.jllsjcs),
          isdifficultjs: _this.input.isdifficultjs,
          papersjs: this.$refs.PapersUpload.url,
          timeneeded: _this.input.timeneeded,
          bz: _this.input.bz,
          evaluationjs: _this.input.evaluationjs,
          evaluationname: window.sessionStorage.getItem('account_id')
        })
        .then(successResponse => {
          if (successResponse.data.success) {
            this.$message('评估成功!')
            this.$router.replace('/TUnevaluated')
          } else {
            this.$message(successResponse.data.msg)
            this.$router.replace('/Tindex')
          }
        })
        .catch(failResponse => {
        })
    },
    unfeasible_submit () {
      let _this = this
      this.$axios
        .post('/JS/AddNewUnfeasibleProj', {
          projectid: _this.projectid,
          evaluationjs: _this.input.evaluationjs,
          reasonsjs: _this.input.reasonsjs,
          evaluationname: window.sessionStorage.getItem('account_id')
        })
        .then(successResponse => {
          if (successResponse.data.success) {
            this.$message('评估成功!')
            this.$router.replace('/TUnevaluated')
          } else {
            this.$message(successResponse.data.msg)
            this.$router.replace('/Tindex')
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
