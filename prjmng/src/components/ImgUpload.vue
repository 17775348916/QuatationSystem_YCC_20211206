<template>
  <el-upload
    class="img-upload"
    ref="upload"
    action="http://39.101.135.32:8443/api/imgupload"
    :on-preview="handlePreview"
    :on-remove="handleRemove"
    :before-remove="beforeRemove"
    :on-success="handleSuccess"
    multiple
    :limit="1"
    :on-exceed="handleExceed"
    :file-list="fileList"
    list-type="picture"
    accept=".jpg,.jpeg,.png,.bmp,.gif">
    <el-button size="small" type="primary">点击上传</el-button>
<!--    <div slot="tip" class="el-upload__tip">只能上传jpg/png文件</div>-->
  </el-upload>
</template>

<script>
export default {
  name: 'ImgUpload',
  data () {
    return {
      fileList: [],
      url: '',
      originname: ''
    }
  },
  methods: {
    handleRemove (file, fileList) {
      console.log(file, fileList)
    },
    handlePreview (file) {
      console.log(file)
    },
    handleExceed (files, fileList) {
      this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`)
    },
    beforeRemove (file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`)
    },
    handleSuccess (response, file, fileList) {
      if (response.success) {
        this.url = response.data.fileurl
        this.originname = response.data.originname
        this.$emit('onUpload')
        this.$message.warning(this.originname + ' 上传成功! ')
        console.log(this.url)
      } else {
        console.log(response)
        this.$message.warning(this.originname + ' 上传失败! ')
      }
    },
    clear () {
      this.$refs.upload.clearFiles()
    }
  }
}
</script>
