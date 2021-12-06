<template>
  <el-upload
    action="http://localhost:8443/api/upload"
    :on-preview="handlePreview"
    accept=".pdf"
    :limit="10"
    :on-success="handleSuccess">
    <el-button size="small" type="primary">点击上传</el-button>
    <div slot="tip" class="el-upload__tip">目前仅支持上传单个pdf文件</div>
  </el-upload>
</template>

<script>
export default {
  name: 'PapersUpload',
  data () {
    return {
      url: ''
    }
  },
  created () {
  },
  methods: {
    handlePreview (file) {
      console.log(file)
      this.url = file.response.url
      window.sessionStorage.setItem('PaperUrl', file.response.url)
      window.open(this.url)
    },
    handleSuccess (response) {
      console.log(response)
      this.url = response.url
      window.sessionStorage.setItem('PaperUrl', response.url)
    }
  }
}
</script>

<style scoped>

</style>
