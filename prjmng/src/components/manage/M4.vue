<template>
  <div>
    <IdentityCheck></IdentityCheck>
    <el-container>
      <el-main>
          <el-table
            :data="material"
            style="width: 100%"
            border>
            <el-table-column
              prop="materialname"
              label="原料名称">
            </el-table-column>
            <el-table-column
              prop="cas"
              label="cas">
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
        <el-pagination class="fy1"
                       layout="total, sizes, prev, pager, next, jumper"
                       :total="total"
                       :page-sizes="[5, 10, 20]"
                       :page-size= "pageSize"
                       v-show="total>5"
                       @size-change="handleSizeChange"
                       @current-change="handleCurrentChange"
                       background style="margin-top:20px">
        </el-pagination>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import IdentityCheck from './IdentityCheck'
export default {
  name: 'M4',
  data: function () {
    return {
      material: [],
      accountid: window.sessionStorage.getItem('account_id'),
      currentPage: 1,
      pageSize: 5,
      total: 0
    }
  },
  components: {
    IdentityCheck
  },
  created () {
    this.$axios
      .post('/lookallmaterial', {
        page: this.currentPage,
        size: this.pageSize
      })
      .then(successResponse => {
        if (successResponse.data.success) {
          this.material = successResponse.data.data.content
          this.total = successResponse.data.data.totalElements
          console.log(this.material)
        }
      })
      .catch(failResponse => {
        // console.log(this.loginForm)
      })
  },
  methods: {
    menuClick (index) {
      this.$router.push(index)
    },
    handleCommand (command) {
      window.sessionStorage.removeItem('account_id')
      window.sessionStorage.removeItem('usertype')
      window.sessionStorage.removeItem('islogin')
      this.$router.replace('/managelogin')
    },
    handleSizeChange (pageSize) {
      this.pageSize = pageSize
      this.getList(this.currentPage)
    },
    handleCurrentChange (currentPage) {
      this.getList(currentPage)
    },
    getList (currentPage) {
      this.currentPage = currentPage
      this.$axios
        .post('/lookallmaterial', {
          page: this.currentPage,
          size: this.pageSize
        })
        .then(successResponse => {
          if (successResponse.data.success) {
            this.material = successResponse.data.data.content
            console.log(this.material)
          }
        })
        .catch(failResponse => {
          // console.log(this.loginForm)
        })
    }
  }
}
</script>

<style scoped>
.el-header, .el-footer {
  background-color: #409eff;
  /*background-color: #545c64;*/
  /*color: #fff;*/
  display: flex;
  align-items: center;
  /*text-align: center;*/
  /*line-height: 50px;*/
  justify-content: space-between;
  padding: 0 15px;
  box-sizing: border-box;

}

.title {
  font-size: 30px;
  color: black;
  font-family: 华文楷体;
}

.el-main {
  /*background-color: #E9EEF3;*/
  /*color: #333;*/
  text-align: center;
  /*line-height: 160px;*/
}
.el-aside {
  background-color: #D3DCE6;
  color: #333;
  text-align: center;
  /*line-height: 200px;*/
}
</style>
