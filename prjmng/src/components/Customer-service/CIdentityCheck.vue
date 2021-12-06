<template>
  <div>
    欢迎你，工号{{ account_id }}客服人员
  </div>
</template>

<script>
export default {
  name: 'CdentityCheck',
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
        case '4':
        case '5':
          break
        default:
          this.$message('您的身份信息有误，无权操作客服人员页面！')
          // this.$message(account_id)
          // this.$message(usertype)
          this.$router.replace({path: '/A'})
          break
      }
    }
  }
}
</script>

<style scoped>

</style>
