<template>
  <div>
    <el-container>
      <el-header>Header</el-header>
      <el-container>
        <el-aside width="200px">
          <el-menu @select="menuClick">
            <el-submenu index="1">
              <template slot="title"><i class="el-icon-message"></i>导航一</template>
                <el-menu-item index="/B123">选项1</el-menu-item>
                <el-menu-item index="/B2">选项2</el-menu-item>
            </el-submenu>
          </el-menu>
        </el-aside>
          <el-main>
            <router-view/>
          </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
export default {
  name: 'B11',
  methods: {
    menuClick (index) {
      this.$router.replace(index)
    }
  }
}
</script>

<style scoped>

</style>
