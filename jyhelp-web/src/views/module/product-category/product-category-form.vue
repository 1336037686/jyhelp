<template>
  <el-dialog
    :title="title"
    :visible.sync="tmpVisible"
    :close-on-press-escape="false"
    :close-on-click-modal="false"
    :show-close="false"
    width="30%"
    class="jy-dialog"
  >
    <div>
      <el-form
        ref="form"
        v-loading="initloading"
        :rules="rules"
        :model="form"
        label-width="80px"
        element-loading-text="加载中，请稍后..."
        element-loading-spinner="el-icon-loading"
      >
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="编号" prop="code">
          <el-input v-model="form.code" />
        </el-form-item>
        <el-form-item label="图标" prop="icon">
          <el-upload
            ref="fileUpload"
            name="file"
            :auto-upload="false"
            list-type="picture-card"
            accept="image/jpeg,image/jpg,image/png,image/gif"
            action="Fake Action"
            :limit="1"
            :file-list="fileList"
            :on-exceed="handleFileExceed"
            :on-remove="handleFileRemove"
            :on-change="handleFileUploadChange"
            :http-request="handleFileUploadRequest"
          >
            <i class="el-icon-plus" />
          </el-upload>
        </el-form-item>
        <el-form-item label="是否启用" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio-button :label="0">禁用</el-radio-button>
            <el-radio-button :label="1">启用</el-radio-button>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="form.description" type="textarea" :rows="2" maxlength="200" />
        </el-form-item>
      </el-form>
    </div>
    <span slot="footer" class="dialog-footer">
      <el-button @click="resetForm('form')">取 消</el-button>
      <el-button type="primary" :loading="submitLoading" @click="handleSubmit('form')">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { getIdempotentToken } from '@/api/system/auth/jy-auth'
import productCategoryApi from '@/api/module/product-category/product-category-api'
import fileProcessApi from '@/api/system/file/jy-file-process'
import fileRecordApi from '@/api/system/file/jy-file-record'
export default {
  name: 'ProductCategoryForm',
  props: {
    title: {
      type: String,
      default: 'Demo'
    },
    id: {
      type: String,
      default: null
    },
    visible: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      tmpVisible: this.visible,
      idempotentToken: null,
      initloading: false,
      submitLoading: false,
      type: 'insert',
      form: {
        id: null,
        name: null,
        code: null,
        icon: null,
        description: null,
        status: 1
      },
      rules: {
        id: [
          { required: true, message: '请输入ID', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '请输入名称', trigger: 'blur' }
        ],
        code: [
          { required: true, message: '请输入编号', trigger: 'blur' }
        ],
        icon: [
          { required: true, message: '请输入图标', trigger: 'blur' }
        ],
        description: [
          { required: true, message: '请输入描述', trigger: 'blur' }
        ],
        status: [
          { required: true, message: '请输入是否启用', trigger: 'blur' }
        ]
      },
      imgUrlPrefix: '/api/file-process/download/',
      fileList: [],
      fileDisabled: false
    }
  },
  watch: {
    async visible(newVal) {
      this.fileList = []
      this.fileDisabled = false
      this.tmpVisible = newVal
      if (newVal) {
        await this.getIdempotentToken()
        // 如果有ID则为修改操作
        if (this.id) {
          this.type = 'update'
          this.getById(this.id)
        } else {
          // 否则为新增操作
          this.type = 'insert'
        }
      }
    },
    tmpVisible(newVal) {
      this.$emit('update:visible', newVal)
    },
    deep: true
  },
  methods: {
    async getIdempotentToken() {
      this.initloading = true
      await getIdempotentToken().then(res => {
        this.idempotentToken = res.data
        this.initloading = false
      }).catch(e => {
        this.initloading = false
      })
    },
    handleSubmit(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.type === 'insert') this.handleCreate()
          else this.handleUpdate()
        }
      })
    },
    handleCreate() {
      this.submitLoading = true
      productCategoryApi.add(this.form, this.idempotentToken).then(response => {
        this.submitLoading = false
        this.$notify.success({ title: '成功', message: '添加成功' })
        this.$parent.getList()
        this.tmpVisible = false

        this.resetForm('form')
        this.form.id = null
      }).catch(e => {
        this.submitLoading = false
      })
    },
    handleUpdate() {
      this.submitLoading = true
      productCategoryApi.update(this.form, this.idempotentToken).then(response => {
        this.submitLoading = false
        this.$notify.success({ title: '成功', message: '修改成功' })
        this.$parent.getList()
        this.tmpVisible = false

        this.resetForm('form')
        this.form.id = null
      }).catch(e => {
        this.submitLoading = false
      })
    },
    async getById(id) {
      this.initloading = true
      await productCategoryApi.getById(id).then(response => {
        this.form = response.data
      }).catch(e => {
        this.initloading = false
      })
      if (this.form.icon === null) {
        this.initloading = false
        return
      }
      await fileRecordApi.getById(this.form.icon).then(response => {
        this.initloading = false
        this.fileList.push({ name: response.data.realName, url: this.imgUrlPrefix + response.data.id })
      }).catch(e => {
        this.initloading = false
      })
    },
    resetForm(formName) {
      this.form.id = null
      this.$refs[formName].resetFields()
      this.tmpVisible = false
    },
    handleFileExceed() {
      this.$notify.warning({ title: '提示', message: '最多上传一张图片' })
    },
    handleFileRemove(file, fileList) {
      this.form.icon = null
    },
    handleFileUploadChange(file, fileList) {
      const isIMAGE = (file.raw.type === 'image/jpeg' || file.raw.type === 'image/png' || file.raw.type === 'image/gif')
      const isLt1M = file.size / 1024 / 1024 < 1
      if (!isIMAGE) {
        this.$notify.error('上传文件只能是图片格式!')
        fileList.splice(0, 1)
        this.fileList = fileList
        return false
      }
      if (!isLt1M) {
        this.$notify.error('上传文件大小不能超过 1MB!')
        fileList.splice(0, 1)
        this.fileList = fileList
        return false
      }
      this.fileList = fileList
      // 上传文件
      if (!this.fileList || this.fileList.length === 0) {
        this.$notify.error('请选择上传文件!')
        return
      }
      const relevance = 'product-category' // 测试模块
      const formData = new FormData()
      this.fileList.forEach(item => {
        formData.append('file', item.raw)
      })
      fileProcessApi.upload(relevance, formData).then(res => {
        this.form.icon = res.data.fileRecordId
      })
    },
    handleFileUploadRequest() {
      console.log('handleFileUploadRequest')
    }
  }
}
</script>

<style scoped>

</style>
