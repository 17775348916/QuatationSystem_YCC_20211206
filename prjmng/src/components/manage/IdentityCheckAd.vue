<template>
  <div>
    欢迎您，系统管理员{{account_id}}
  </div>
</template>

<script>
export default {
  name: 'IdentityCheckAd',
  data: function () {
    return {
      account_id: '',
      usertype: ''
    }
  },
  created () {
    if (window.sessionStorage.getItem('islogin') == null) {
      this.$message('未登录，请先登录')
      this.$router.replace({path: '/managelogin'})
    } else {
      this.account_id = window.sessionStorage.getItem('account_id')
      this.usertype = window.sessionStorage.getItem('usertype')
      switch (this.usertype) {
        case '1':
          break
        default:
          this.$message('您的身份信息有误，无权操作页面！')
          this.$router.replace({path: '/managelogin'})
          break
      }
    }
  }
}
</script>

<style scoped>

</style>
