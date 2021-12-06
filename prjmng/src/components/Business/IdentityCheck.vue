<template>
  <div>
    欢迎您，工号：{{ account_id }} 身份：商务人员
  </div>
</template>

<script>
export default {
  name: 'IdentityCheck',
  data: function () {
    return {
      account_id: '',
      usertype: ''
    }
  },
  created () {
    if (window.sessionStorage.getItem('islogin') == null) {
      this.$message('未登录，请先登录')
      this.$router.replace({path: '/A'})
    } else {
      this.account_id = window.sessionStorage.getItem('account_id')
      this.usertype = window.sessionStorage.getItem('usertype')
      switch (this.usertype) {
        case '1':
        case '2':
        case '5':
          break
        default:
          this.$message('您的身份信息有误，无权操作商务人员页面！')
          this.$router.replace({path: '/A'})
          break
      }
    }
  }
}
</script>

<style scoped>

</style>
