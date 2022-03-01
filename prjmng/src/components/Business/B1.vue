<template>
  <el-form>
    <IdentityCheck></IdentityCheck>
    <el-form-item>
        <el-row>
          <el-col align="center" style="font-size:19px">
            （一）询单人联系方式
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="5">
            询单单位名称:
            <el-input v-model="contact.KH_name"></el-input>
          </el-col>
          <el-col :offset="1" :span="5">
            询单人员姓名:
            <el-input v-model="contact.KHRY_name"></el-input>
          </el-col>
          <el-col :offset="1" :span="5">
            <div>询单人员身份</div>
            <el-select v-model="contact.KHRY_Type">
              <el-option value="老师">老师</el-option>
              <el-option value="学生">学生</el-option>
              <el-option value="公司职员">公司职员</el-option>
            </el-select>
          </el-col>
          <el-col :offset="1" :span="5">
            询单人联系方式:
            <el-input v-model="contact.KHRY_Contact"></el-input>
          </el-col>
        </el-row>
      </el-form-item>
      <el-form-item>
        <el-row>
          <el-col align="center" style="font-size:19px">
            （二）询单产品信息
          </el-col>
        </el-row>
        <el-row>
          <el-col  :span="4">
            需求产品名称:
            <el-input v-model="product.Project_name"></el-input>
          </el-col>
          <el-col :offset="1" :span="4">
            cas号:
            <el-input v-model="product.Cas"></el-input>
          </el-col>
          <el-col :offset="1" :span="4">
            <div>需求量:</div>
            <el-input v-model="product.Project_SL"></el-input>
          </el-col>
          <el-col :offset="1" :span="4">
            备注:
            <el-input v-model="product.BZ"></el-input>
          </el-col>
          <el-col  :span="4">
            产品图片上传
            <img-upload ref="imgUpload" @onUpload="uploadImg"></img-upload>
          </el-col>
        </el-row>
      </el-form-item>
      <el-form-item>
        <el-row>
          <el-col align="center" style="font-size:19px">
            （三）请对该项目询单人的身份进行评价判断，完成该条项目信息的提交任务。
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="4">
            <div>(1) 询单单位性质</div>
            <el-select v-model="judge.KH_type">
              <el-option value="高校">高校</el-option>
              <el-option value="企业">企业</el-option>
            </el-select>
          </el-col>
          <el-col :offset="1" :span="4">
            <div>(2) 询单单位是否有成交机会?</div>
            <el-select v-model="judge.Is_deal">
              <el-option value="有">有</el-option>
              <el-option value="无">无</el-option>
            </el-select>
          </el-col>
          <el-col :offset="1" :span="4">
            <div>(3) 询单人身份是否真实</div>
            <el-select v-model="judge.KHRY_IsReal">
              <el-option value="真实">真实</el-option>
              <el-option value="不真实">不真实</el-option>
            </el-select>
          </el-col>
          <el-col :offset="1" :span="4">
            <div>(4) 询单人是否有钱</div>
            <el-select v-model="judge.Is_money">
              <el-option value="有钱">有钱</el-option>
              <el-option value="一般">一般</el-option>
              <el-option value="没钱">没钱</el-option>
            </el-select>
          </el-col>
          <el-col :offset="1" :span="4">
            <div>(5) 客户合作历史</div>
            <el-select v-model="judge.Co_history">
              <el-option value="新客户">新客户</el-option>
              <el-option value="老客户">老客户</el-option>
            </el-select>
          </el-col>
        </el-row>
        <el-row style="margin-top:20px">
          <div align="left" style="font-size:16px;font-family:黑体;color:red;margin-left:10px">如果判定为"无成交机会"、"询单者身份不真实"或"询单者没钱"，后台对该条信息仅做记录处理，不进入下一工作环节，请您务必认真判断!</div>
        </el-row>
      </el-form-item>
      <el-form-item style="margin-top:90px">
        <el-row>
          <el-col :offset="16" :span="3">
            <el-button type="primary" v-on:click="submit" style="width:100%">提交</el-button>
          </el-col>
          <el-col :span="3">
            <el-button v-on:click="b1save" >暂存</el-button>
          </el-col>
          <el-col :span="1">
            <el-button v-on:click="$router.replace('/Bindex')">返回</el-button>
          </el-col>
        </el-row>
      </el-form-item>
    </el-form>
    <!--    <button type="button">下一步</button>-->
</template>

<script>
import IdentityCheck from './IdentityCheck'
import ImgUpload from '../ImgUpload'

export default {
  name: 'B1',
  data: function () {
    return {
      message: '',
      account_id: '',
      usertype: '',
      contact:
        {
          KHRY_name: '',
          KHRY_Type: '',
          KHRY_Contact: '',
          KH_name: ''
        },
      product:
        {
          Project_name: '',
          Cas: '',
          Project_SL: '',
          Project_details: '',
          BZ: ''
        },
      judge:
        {
          KH_type: '',
          Is_deal: '',
          KHRY_IsReal: '',
          Is_money: '',
          Co_history: ''
        },
      // fileList: [{name: 'food.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}, {name: 'food2.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}],
      url: ''
    }
  },
  components: {
    IdentityCheck,
    ImgUpload
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
    if (localStorage.getItem('b1projectname')) {
      this.product.Project_name = localStorage.getItem('b1projectname')
    }
    if (localStorage.getItem('b1projectdetails')) {
      this.product.Project_details = localStorage.getItem('b1projectdetails')
    }
    if (localStorage.getItem('b1projectsl')) {
      this.product.Project_SL = localStorage.getItem('b1projectsl')
    }
    if (localStorage.getItem('b1cas')) {
      this.product.Cas = localStorage.getItem('b1cas')
    }
    if (localStorage.getItem('b1khname')) {
      this.contact.KH_name = localStorage.getItem('b1khname')
    }
    if (localStorage.getItem('b1khryname')) {
      this.contact.KHRY_name = localStorage.getItem('b1khryname')
    }
    if (localStorage.getItem('b1khrytype')) {
      this.contact.KHRY_Type = localStorage.getItem('b1khrytype')
    }
    if (localStorage.getItem('b1khrycontact')) {
      this.contact.KHRY_Contact = localStorage.getItem('b1khrycontact')
    }
    if (localStorage.getItem('b1khtype')) {
      this.judge.KH_type = localStorage.getItem('b1khtype')
    }
    if (localStorage.getItem('b1khryisreal')) {
      this.judge.KHRY_IsReal = localStorage.getItem('b1khryisreal')
    }
    if (localStorage.getItem('b1cohistory')) {
      this.judge.Co_history = localStorage.getItem('b1cohistory')
    }
    if (localStorage.getItem('b1ismoney')) {
      this.judge.Is_money = localStorage.getItem('b1ismoney')
    }
    if (localStorage.getItem('b1isdeal')) {
      this.judge.Is_deal = localStorage.getItem('b1isdeal')
    }
    if (localStorage.getItem('b1bz')) {
      this.product.BZ = localStorage.getItem('b1bz')
    }
  },
  methods: {
    submit () {
      var _this = this
      if (!(_this.contact.KH_name)) {
        this.$message('询单单位名称缺失，请补充')
        return
      }
      if (!(_this.contact.KHRY_name)) {
        this.$message('询单人员姓名缺失，请补充')
        return
      }
      if (!(_this.contact.KHRY_Type)) {
        this.$message('询单人员身份缺失，请补充')
        return
      }
      if (!(_this.contact.KHRY_Contact)) {
        this.$message('询单人联系方式缺失，请补充')
        return
      }
      if (!(_this.product.Project_name)) {
        this.$message('产品名称有缺失，请补充')
        return
      }
      if (!(_this.product.Project_details)) {
        this.$message('产品内容，产品结构式图片缺失，请补充')
        return
      }
      if (!(_this.product.Project_SL)) {
        this.$message('需求量缺失，请补充')
        return
      }
      if (!(_this.judge.Is_deal) || !(_this.judge.Is_money) || !(_this.judge.KH_type) || !(_this.judge.KHRY_IsReal) || !(_this.judge.Co_history)) {
        this.$message('身份评价判断有缺失，请补充')
        return
      }
      this.$axios
        .post('/SWAddNewProj', {
          projectname: _this.product.Project_name,
          projectdetails: _this.product.Project_details,
          projectsl: _this.product.Project_SL,
          cas: _this.product.Cas,
          khname: _this.contact.KH_name,
          khtype: _this.judge.KH_type,
          khryname: _this.contact.KHRY_name,
          khrytype: _this.contact.KHRY_Type,
          khrycontact: _this.contact.KHRY_Contact,
          khryisreal: _this.judge.KHRY_IsReal,
          cohistory: _this.judge.Co_history,
          ismoney: _this.judge.Is_money,
          isdeal: _this.judge.Is_deal,
          bz: _this.product.BZ,
          createname: window.sessionStorage.getItem('account_id'),
          updatename: window.sessionStorage.getItem('account_id')
        })
        .then(successResponse => {
          if (successResponse.data.success) {
            if (localStorage.getItem('b1projectname') != null) {
              localStorage.removeItem('b1projectname')
              localStorage.removeItem('b1projectdetails')
              localStorage.removeItem('b1projectsl')
              localStorage.removeItem('b1cas')
              localStorage.removeItem('b1khname')
              localStorage.removeItem('b1khryname')
              localStorage.removeItem('b1khrytype')
              localStorage.removeItem('b1khrycontact')
              localStorage.removeItem('b1khtype')
              localStorage.removeItem('b1khryisreal')
              localStorage.removeItem('b1cohistory')
              localStorage.removeItem('b1ismoney')
              localStorage.removeItem('b1isdeal')
              localStorage.removeItem('b1bz')
              this.product.Project_name = ''
              this.product.Project_details = ''
              this.product.Project_SL = ''
              this.product.Cas = ''
              this.contact.KH_name = ''
              this.contact.KHRY_name = ''
              this.contact.KHRY_Type = ''
              this.contact.KHRY_Contact = ''
              this.judge.KH_type = ''
              this.judge.KHRY_IsReal = ''
              this.judge.Co_history = ''
              this.judge.Is_money = ''
              this.judge.Is_deal = ''
              this.product.BZ = ''
              this.product.Project_details = ''
              this.$refs.imgUpload.originname = ''
              this.$refs.imgUpload.fileList = []
            } else {
              this.product.Project_name = ''
              this.product.Project_details = ''
              this.product.Project_SL = ''
              this.product.Cas = ''
              this.contact.KH_name = ''
              this.contact.KHRY_name = ''
              this.contact.KHRY_Type = ''
              this.contact.KHRY_Contact = ''
              this.judge.KH_type = ''
              this.judge.KHRY_IsReal = ''
              this.judge.Co_history = ''
              this.judge.Is_money = ''
              this.judge.Is_deal = ''
              this.product.BZ = ''
              this.product.Project_details = ''
              this.$refs.imgUpload.originname = ''
              this.$refs.imgUpload.fileList = []
            }
          } else {
            this.$message('添加失败,' + successResponse.data.msg)
          }
        })
        .catch(failResponse => {
          // console.log(this.loginForm)
        }).then(() => {
          this.open()
        })
    },
    open () {
      this.$confirm('项目信息已提交，继续录入新项目？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        window.location.reload()
      }).catch(() => {
        this.$router.replace('/Bindex')
        window.location.reload()
      })
    },
    uploadImg () {
      this.product.Project_details = this.$refs.imgUpload.url
    },
    b1save () {
      var _this = this
      if (!(_this.contact.KH_name)) {
        this.$message('询单单位名称缺失，请补充')
        return
      }
      if (!(_this.contact.KHRY_name)) {
        this.$message('询单人员姓名缺失，请补充')
        return
      }
      if (!(_this.contact.KHRY_Type)) {
        this.$message('询单人员身份缺失，请补充')
        return
      }
      if (!(_this.contact.KHRY_Contact)) {
        this.$message('询单人联系方式缺失，请补充')
        return
      }
      if (!(_this.product.Project_name)) {
        this.$message('产品名称有缺失，请补充')
        return
      }
      if (!(_this.product.Project_details)) {
        this.$message('产品内容，产品结构式图片缺失，请补充')
        return
      }
      if (!(_this.product.Project_SL)) {
        this.$message('需求量缺失，请补充')
        return
      }
      if (!(_this.judge.Is_deal) || !(_this.judge.Is_money) || !(_this.judge.KH_type) || !(_this.judge.KHRY_IsReal) || !(_this.judge.Co_history)) {
        this.$message('身份评价判断有缺失，请补充')
        return
      }
      localStorage.setItem('b1projectname', _this.product.Project_name)
      localStorage.setItem('b1projectdetails', _this.product.Project_details)
      localStorage.setItem('b1projectsl', _this.product.Project_SL)
      localStorage.setItem('b1cas', _this.product.Cas)
      localStorage.setItem('b1khname', _this.contact.KH_name)
      localStorage.setItem('b1khryname', _this.contact.KHRY_name)
      localStorage.setItem('b1khrytype', _this.contact.KHRY_Type)
      localStorage.setItem('b1khrycontact', _this.contact.KHRY_Contact)
      localStorage.setItem('b1khtype', _this.judge.KH_type)
      localStorage.setItem('b1khryisreal', _this.judge.KHRY_IsReal)
      localStorage.setItem('b1cohistory', _this.judge.Co_history)
      localStorage.setItem('b1ismoney', _this.judge.Is_money)
      localStorage.setItem('b1isdeal', _this.judge.Is_deal)
      localStorage.setItem('b1bz', _this.product.BZ)
      this.$message('暂存成功！')
    }
  }
}
</script>

<style scoped>

</style>
