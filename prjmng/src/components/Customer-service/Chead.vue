<template>
  <div>
    <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
      <el-menu-item index="1">提供原料信息任务</el-menu-item>
      <el-menu-item index="2">原料数据库维护</el-menu-item>
      <el-menu-item index="3">下单客户沟通</el-menu-item>
      <el-menu-item index="4">接单客户沟通</el-menu-item>
      <el-menu-item index="5">返回</el-menu-item>
    </el-menu>
  </div>
</template>

<script>
export default {
  name: 'Chead',
  data () {
    return {
      activeIndex: ''
    }
  },
  methods: {
    handleSelect (key, keyPath) {
      // console.log(key, keyPath)
      switch (key) {
        case '1':
          this.$router.replace({path: '/Cindex'})
          break
        case '2':
          this.$router.replace({path: '/C2'})
          break
        case '3':
          this.$router.replace({path: '/C3'})
          break
        case '4':
          this.$router.replace({path: '/C4'})
          break
        case '5':
          window.sessionStorage.removeItem('account_id')
          window.sessionStorage.removeItem('usertype')
          this.$router.replace('/A')
      }
    }
  }
}
</script>

<style scoped>

</style>
