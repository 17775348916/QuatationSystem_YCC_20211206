<!--原料下达页面-->
<template>
  <div>
    <TIdentityCheck></TIdentityCheck>
    <br>
    <el-descriptions class="margin-top" title="订单信息" :column="4"  style="margin-bottom:20px" border>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-user"></i>
          订单编号
        </template>
        {{ project.projectid }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-mobile-phone"></i>
          询单日期
        </template>
        {{ project.createdate }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-location-outline"></i>
          耗费时间(小时)
        </template>
        {{ project.dura }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-office-building"></i>
          当前状态
        </template>
        尚未评估
      </el-descriptions-item>
    </el-descriptions>

    <el-descriptions class="margin-top1" title="需求产品信息" :column="4"  style="margin-bottom:20px" border>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-goods"></i>
          产品名称
        </template>
        {{ project.projectname }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-info"></i>
          cas号
        </template>
        {{ project.cas }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-shopping-cart-full"></i>
          需求量
        </template>
        {{ project.projectsl }}
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
            <img alt="图片未上传" v-bind:src="project.projectdetails" style="max-width:600px"/>
            <el-button slot="reference" size="mini">预览</el-button>
          </el-popover>
        </div>
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-warning"></i>
          客户对产品纯度，货期等方面特殊要求：
        </template>
        {{ project.bz }}
      </el-descriptions-item>
    </el-descriptions>
    <hr>
    <div align="left" style="font-size:16px;font-family:黑体;font-weight:bold;">
      请录入项目需要关键原料信息：
    </div>
    <el-row style="margin-top:20px">
      <el-col :span="1" ><div >编号</div></el-col>
      <el-col :span="3"><span style="color:red" >*</span><span>原料名称</span></el-col>
      <el-col :span="3"><div>Cas号</div></el-col>
      <el-col :span="2"><span style="color:red">*</span><span>需要数量</span></el-col>
      <el-col :span="2"><div>备注</div></el-col>
      <el-col :span="2"><div></div></el-col>
    </el-row>
    <el-row v-for="(item,index) in list1" :key="item.uuid" style="margin-top:10px;" align="right">
      <el-col :span="1" ><div style="margin-top:5px;">{{ index + 1 }}</div></el-col>
      <el-col :span="3"><el-input v-model="item.materialname" placeholder="原料名称(必填)"></el-input></el-col>
      <el-col :span="3"><el-input v-model="item.cas"></el-input></el-col>
      <el-col :span="2"><el-input v-model="item.materialsl"></el-input></el-col>
      <el-col :span="2"><el-input v-model="item.bz"></el-input></el-col>
      <el-col :span="1"><el-button circle icon="el-icon-delete" type="info" v-on:click="deletem(index)"></el-button></el-col>
      <el-col :span="2"><el-button :span="5" v-on:click="addnew" v-if="index === 0">+添加</el-button></el-col>
      <el-button type="primary" v-on:click="submit" style="margin-left:350px" v-if="index === 0">提交原料信息</el-button>
    </el-row>
    <hr>
    <div align="left" style="font-size:16px;font-family:黑体;font-weight:bold;">
      从原料信息库查询添加:
    </div>
    <el-row style="margin-top:10px;">
      <el-col :span="11">
        <el-input v-model="info" placeholder="请输入原料名称或cas号进行查询" @keydown.enter.native="search"></el-input>
      </el-col>
      <el-col :span="3" style="margin-left:20px">
        <el-button v-on:click="search">查询</el-button>
      </el-col>
    </el-row>
    <el-row style="margin-top:5px;">
      <div >
        <el-table :span="12" v-if="Object.keys(list2).length>0"
                  :data="list2"
                  height="220"
                  style="width:55%;">
          <el-table-column label="原料名称" prop="materialname"> </el-table-column>
          <el-table-column label="cas" prop="cas"> </el-table-column>
          <el-table-column label="规格" prop="quantities"> </el-table-column>
          <el-table-column label="纯度" prop="qurity"> </el-table-column>
          <el-table-column label="备注" prop="bz"> </el-table-column>
        </el-table>
      </div>
    </el-row>
<!--      <div align="right">-->
<!--        <el-button type="primary" v-on:click="submit">提交原料信息</el-button>-->
<!--      </div>-->
  </div>
</template>

<script>
import TIdentityCheck from './TIdentityCheck'

export default {
  name: 'T1',
  data: function () {
    return {
      account_id: window.sessionStorage.getItem('account_id'),
      projectid: '',
      project: '',
      info: '',
      list1: [],
      list2: [],
      cnt: Number(0)
    }
  },
  created () {
    if (window.sessionStorage.getItem(this.account_id)) {
      this.projectid = window.sessionStorage.getItem(this.account_id)
    } else {
      this.$router.replace('/Tindex')
    }
    let _this = this
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
                // console.log(successResponse.data.data)
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
    this.cnt = this.cnt + 1
    this.list1.push({
      materialname: '',
      cas: '',
      materialsl: '',
      bz: '',
      uuid: this.cnt,
      createname: window.sessionStorage.getItem('account_id'),
      projectid: this.projectid
    })
  },
  components: {
    TIdentityCheck
  },
  methods: {
    search () {
      var _this = this
      if (_this.info) {
        this.$axios
          .post('/searchmaterial', {
            info: _this.info
          })
          .then(successResponse => {
            if (successResponse.data.success) {
              this.list2 = successResponse.data.data
              if (this.list2.length <= 0) {
                this.$message(_this.info + '查询结果为空')
              }
              console.log(this.list2)
            } else {
              this.$message(successResponse.data.msg)
            }
          })
          .catch(failResponse => {
          })
      }
    },
    deletem (index) {
      if (index === 0) {
        this.$message.error('当前只添加一种原料，请勿删除')
      } else {
        this.list1.splice(index, 1)
      }
    },
    addnew () {
      this.cnt = this.cnt + 1
      this.list1.push({
        materialname: '',
        cas: '',
        materialsl: '',
        bz: '',
        createname: window.sessionStorage.getItem('account_id'),
        uuid: this.cnt,
        projectid: this.projectid
      })
    },
    submit () {
      // console.log(this.list1)
      for (let m in this.list1) {
        // console.log(this.list1[m].materialname, this.list1[m].cas, this.list1[m].materialsl)
        if (!(this.list1[m].materialname && this.list1[m].materialsl)) {
          this.$message('原料名 或 需要数量 有缺失，请再次确认')
          return
        }
      }
      this.$axios
        .post('/xiada', this.list1)
        .then(successResponse => {
          if (successResponse.data.success) {
            this.$message('下达成功')
          } else {
            this.$message(successResponse.data.msg)
          }
        })
        .catch(failResponse => {
        })
      this.$router.replace('/TUnevaluated')
    },
    addfromlist2 (x) {
      this.cnt = this.cnt + 1
      this.list1.push({
        projectid: this.projectid,
        createname: this.account_id,
        materialname: x.materialname,
        materialid: x.materialid,
        cas: x.cas,
        bz: '',
        uuid: this.cnt,
        materialsl: ''
      })
      console.log(this.list1)
    }
  }
}
</script>

<style scoped>
</style>
