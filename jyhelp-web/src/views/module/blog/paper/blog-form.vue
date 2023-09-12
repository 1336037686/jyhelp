<template>
  <el-dialog
    :title="title"
    :visible.sync="tmpVisible"
    :close-on-press-escape="false"
    :close-on-click-modal="false"
    :show-close="false"
    width="60%"
    class="jy-dialog"
  >
    <div>
      <el-form
        ref="form"
        v-loading="initloading"
        :rules="rules"
        :model="form"
        label-width="120px"
        element-loading-text="加载中，请稍后..."
        element-loading-spinner="el-icon-loading"
      >
        <el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="文章名称：" prop="title">
                <el-input v-model="form.title" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="作者：" prop="author">
                <el-input v-model="form.author" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-col :span="12">
            <el-form-item label="摘要：" prop="intro">
              <el-input v-model="form.intro" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态：" prop="status">
              <el-select v-model="form.status" placeholder="状态" style="width: 100%">
                <el-option v-for="item in blogStatusOptions" :key="item.code" :label="item.name" :value="item.code" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="类别：" prop="category">
              <el-select v-model="form.category" placeholder="状态" style="width: 100%">
                <el-option v-for="item in categoryOptions" :key="item.code" :label="item.name" :value="item.code" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="标签：" prop="tag">
              <el-select v-model="form.tag" placeholder="状态" style="width: 100%">
                <el-option v-for="item in tagOptions" :key="item.code" :label="item.name" :value="item.code" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="封面：" prop="cover">
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
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="文章内容：" prop="content">
              <Tinymce ref="editor" v-model="form.content" :height="400" width="100%" />
            </el-form-item>
          </el-col>
        </el-row>
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
import blogApi from '@/api/module/blog/blog-api'
import Tinymce from '@/components/Tinymce'
import fileProcessApi from '@/api/system/file/jy-file-process'
import tagApi from '@/api/module/tag/tag-api'
import categoryApi from '@/api/module/category/category-api'
import fileRecordApi from '@/api/system/file/jy-file-record'
export default {
  name: 'BlogForm',
  components: { Tinymce },
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
        title: null,
        intro: null,
        category: null,
        tag: null,
        cover: null,
        content: null,
        status: null,
        author: null
      },
      rules: {
        title: [
          { required: true, message: '请输入文章名称', trigger: 'blur' }
        ],
        intro: [
          { required: true, message: '请输入摘要', trigger: 'blur' }
        ],
        category: [
          { required: true, message: '请输入类别', trigger: 'blur' }
        ],
        tag: [
          { required: true, message: '请输入标签', trigger: 'blur' }
        ],
        cover: [
          { required: true, message: '请输入封面', trigger: 'blur' }
        ],
        content: [
          { required: true, message: '请输入文章内容', trigger: 'blur' }
        ],
        status: [
          { required: true, message: '请输入状态', trigger: 'blur' }
        ],
        author: [
          { required: true, message: '请输入作者', trigger: 'blur' }
        ]
      },
      blogStatusOptions: [],
      tagOptions: [],
      categoryOptions: [],
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
  created() {
    this.getDict()
  },
  methods: {
    getDict() {
      this.getDictByCode('module_blog_status').then(res => {
        this.blogStatusOptions = res.data
      })
      tagApi.list().then(res => {
        this.tagOptions = []
        for (let i = 0; i < res.data.length; i++) {
          this.tagOptions.push({ name: res.data[i].name, code: res.data[i].id })
        }
      })
      categoryApi.list().then(res => {
        this.categoryOptions = []
        for (let i = 0; i < res.data.length; i++) {
          this.categoryOptions.push({ name: res.data[i].name, code: res.data[i].id })
        }
      })
    },
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
      blogApi.add(this.form, this.idempotentToken).then(response => {
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
      blogApi.update(this.form, this.idempotentToken).then(response => {
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
      await blogApi.getById(id).then(response => {
        this.initloading = false
        this.form = response.data
        this.$refs.editor.setContent(this.form.content)
      }).catch(e => {
        this.initloading = false
        return
      })
      if (this.form.cover === null) {
        this.initloading = false
        return
      }
      this.fileList = []
      await fileRecordApi.getById(this.form.cover).then(response => {
        this.initloading = false
        this.fileList.push({ name: response.data.realName, url: this.imgUrlPrefix + response.data.id })
      }).catch(e => {
        this.initloading = false
      })
    },
    resetForm(formName) {
      this.form.id = null
      this.$refs[formName].resetFields()
      this.$refs.editor.setContent('')
      this.tmpVisible = false
    },
    handleFileExceed() {
      this.$notify.warning({ title: '提示', message: '最多上传一张图片' })
    },
    handleFileRemove(file, fileList) {
      this.form.cover = null
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
      const relevance = 'blog' // 测试模块
      const formData = new FormData()
      this.fileList.forEach(item => {
        formData.append('file', item.raw)
      })
      fileProcessApi.upload(relevance, formData).then(res => {
        this.form.cover = res.data.fileRecordId
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
