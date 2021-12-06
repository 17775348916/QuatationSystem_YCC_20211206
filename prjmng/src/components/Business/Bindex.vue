/* eslint-disable */
<template>
  <div>
    <IdentityCheck></IdentityCheck>
    <h5></h5>
<!--    <h5>目前你已录入项目x条，等待技术评估x条，技术已评估x条。待定交易x条，完成交易x条</h5>-->

    <button type="button" name="newProject" @click="gotonewProject">录入新项目</button>
    <button type="button" name="lookProject" @click="gotolookProject">查看所有项目</button>
    <button type="button"  @click="goback">退出</button>

  </div>
</template>

<script>
import IdentityCheck from './IdentityCheck'
export default {
  name: 'Bindex',
  data: function () {
    return {
      message: '',
      account_id: '',
      usertype: ''
      // name: '',
    }
  },
  methods: {
    gotonewProject () {
      // 点击跳转至上次浏览页面
      // this.$router.go(-1)
      // 指定跳转地址
      this.$router.replace('/B1')
    },
    gotolookProject () {
      this.$router.replace('/B2')
    },
    goback () {
      window.sessionStorage.removeItem('account_id')
      window.sessionStorage.removeItem('usertype')
      this.$router.replace('/A')
    }
  },
  components: {
    IdentityCheck
  }
}
</script>

<style scoped>

</style>
