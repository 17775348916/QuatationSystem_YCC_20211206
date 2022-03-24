<template>
  <el-upload
    action="http://106.12.213.102:8443/api/upload"
    :on-preview="handlePreview"
    :on-remove="handleRemove"
    :before-remove="beforeRemove"
    :limit="10"
    :on-success="handleSuccess"
    accept=".jpg,.jpeg,.png,.pdf,.doc,.docx,.xls,.xlsx,.rar">
    <el-button size="small" type="primary">点击上传</el-button>
<!--    <div slot="tip" class="el-upload__tip">目前仅支持上传单个pdf文件</div>-->
  </el-upload>
</template>
<script>
export default {
  name: 'PapersUpload',
  data () {
    return {
      fileList: [],
      url: ''
    }
  },
  created () {
  },
  methods: {
    beforeRemove (file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`)
    },
    handleRemove (file, fileList) {
      for (let i = 0; i < fileList.length; i++) {
        if (i === 0) {
          this.url = fileList[i].response.data.url
        } else {
          this.url += '||' + fileList[i].response.data.url
        }
      }
      console.log('handleRemove中的信息：', fileList)
      console.log(this.url)
    },
    handlePreview (file) {
      this.url = file.response.data.url
      window.open(this.url)
    },
    handleSuccess (response, file, fileList) {
      if (fileList.length === 1) {
        this.url = response.data.url
      } else {
        this.url += '||' + response.data.url
      }
      this.fileList = fileList
      console.log('papersupload中的url', this.url)
    }
  }
}
</script>

<style scoped>

</style>
