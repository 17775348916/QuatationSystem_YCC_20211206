<template>
  <div>
    <IdentityCheck></IdentityCheck>
    <el-form class="container">
      <el-form-item>
        <div>
          <el-button style="width: 250px" v-on:click="sw()">商务人员页面</el-button>
        </div>
      </el-form-item>
      <el-form-item>
        <div>
          <el-button style="width: 250px" v-on:click="js()">技术人员页面</el-button>
        </div>
      </el-form-item>
      <el-form-item>
        <div>
          <el-button style="width: 250px" v-on:click="kf()">客服人员页面</el-button>
        </div>
      </el-form-item>
      <div v-if="typeid === '1'">
        <el-form-item>
          <div>
            <el-button style="width: 250px" v-on:click="employee()">人员管理</el-button>
          </div>
        </el-form-item>
        <el-form-item>
          <div>
            <el-button style="width: 250px" v-on:click="judgment()">报价模式管理</el-button>
          </div>
        </el-form-item>
        <el-form-item>
          <div>
            <el-button style="width: 250px" v-on:click="material()">原料数据库管理</el-button>
          </div>
        </el-form-item>
        <el-form-item>
          <div>
            <el-button style="width: 250px" v-on:click="project()">历史项目查看</el-button>
          </div>
        </el-form-item>
      </div>
    </el-form>
  </div>
</template>

<script>
import IdentityCheck from './IdentityCheck'

export default {
  name: 'manageindex',
  data () {
    return {
      accountid: window.sessionStorage.getItem('account_id'),
      typeid: window.sessionStorage.getItem('usertype')
    }
  },
  methods: {
    sw () {
      this.$router.replace({path: '/Bindex'})
    },
    js () {
      this.$router.replace({path: '/Tindex'})
    },
    kf () {
      this.$router.replace({path: '/Cindex'})
    },
    employee () {
      this.$router.replace({path: '/M1'})
    },
    project () {
      this.$router.replace({path: '/M2'})
    },
    judgment () {
      this.$router.replace({path: '/M3'})
    },
    material () {
      this.$router.replace({path: '/M4'})
    }
  },
  components: {
    IdentityCheck
  }
}
</script>

<style scoped>
.container {
  border-radius: 15px;
  background-clip: padding-box;
  margin: 90px auto;
  width: 350px;
  padding: 35px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}
</style>
