<template>
  <el-upload
    action="http://localhost:8443/api/upload"
    :on-preview="handlePreview"
    :limit="10"
    :on-success="handleSuccess"
    accept=".jpg,.jpeg,.png,.pdf,.doc,.docx,.xls,.xlsx,.rar">
    <el-button size="small" type="primary">点击上传</el-button>
<!--    <div slot="tip" class="el-upload__tip">目前仅支持上传单个pdf文件</div>-->
  </el-upload>
</template>
<!--accept=".pdf"-->

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
    handlePreview (file) {
      console.log(file)
      this.url = file.response.url
      window.open(this.url)
    },
    handleSuccess (response, file, fileList) {
      console.log(response)
      if (fileList.length === 1) {
        this.url = response.url
      } else {
        this.url += '||' + response.url
      }
      this.fileList = fileList
      console.log('papersupload中的url', this.url)
    }
  }
}
</script>

<style scoped>

</style>
