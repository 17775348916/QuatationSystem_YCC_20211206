<template>
  <div>
    <IdentityCheckAd></IdentityCheckAd>
    <el-header>原料数据库内容管理</el-header>
    <el-table
      :data="material"
      style="width: 100%">
      <el-table-column
        prop="materialname"
        label="原料名称"
        width="180">
      </el-table-column>
      <el-table-column
        prop="cas"
        label="cas"
        width="180">
      </el-table-column>
      <el-table-column
        prop="price"
        label="价格">
      </el-table-column>
      <el-table-column
        prop="provider"
        label="供应商">
      </el-table-column>
      <el-table-column
        prop="providerphone"
        label="供应商联系方式">
      </el-table-column>
      <el-table-column
        prop="quantities"
        label="规格">
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import IdentityCheckAd from './IdentityCheckAd'
export default {
  name: 'M4',
  data: function () {
    return {
      material: ''
    }
  },
  components: {
    IdentityCheckAd
  },
  created () {
    this.$axios
      .post('/lookallmaterial', {})
      .then(successResponse => {
        if (successResponse.data.success) {
          this.material = successResponse.data.data
          console.log(this.material)
        }
      })
      .catch(failResponse => {
        // console.log(this.loginForm)
      })
  }
}
</script>

<style scoped>

</style>
