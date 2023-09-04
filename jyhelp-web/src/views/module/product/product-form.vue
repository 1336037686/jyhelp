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
        label-width="150px"
        element-loading-text="加载中，请稍后..."
        element-loading-spinner="el-icon-loading"
      >
        <el-divider content-position="left"><i class="el-icon-menu" /> 基本信息</el-divider>
        <el-row>
          <el-col :span="8">
            <el-form-item label="商品名称：" prop="name">
              <el-input v-model="form.name" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="商品编码：" prop="code">
              <el-input v-model="form.code" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="商品类别：" prop="productCategoryId">
              <el-select v-model="form.productCategoryId" placeholder="请选择" style="width: 100%">
                <el-option v-for="item in productCategoryOptions" :key="item.id" :label="item.name" :value="item.id" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="服务类别：" prop="serviceCategoryId">
              <el-select v-model="form.serviceCategoryId" placeholder="请选择" style="width: 100%">
                <el-option v-for="item in serviceCategoryOptions" :key="item.id" :label="item.name" :value="item.id" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="商品库存：" prop="stock">
              <el-input-number v-model="form.stock" :step="1" :min="1" :max="999999" style="width: 100%" />
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="商品单位：" prop="unit">
              <el-input v-model="form.unit" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="原始价格（元）：" prop="initialPrice">
              <el-input-number v-model="form.initialPrice" :precision="2" :step="0.1" :min="0.01" :max="999999" style="width: 100%" @change="handleInitialPrice" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="商品折扣：" prop="discount">
              <el-input-number v-model="form.discount" :precision="1" :step="0.1" :max="1" :min="0.1" style="width: 100%" @change="handleDiscountChange" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="当前价格（元）：" prop="currentPrice">
              <el-input-number v-model="form.currentPrice" :precision="2" :step="0.1" :min="0.01" :max="999999" style="width: 100%" :disabled="true" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="上架状态：" prop="status">
              <el-radio-group v-model="form.status">
                <el-radio-button :label="0">下架</el-radio-button>
                <el-radio-button :label="1">上架</el-radio-button>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="是否热门：" prop="hot">
              <el-radio-group v-model="form.hot">
                <el-radio-button :label="0">否</el-radio-button>
                <el-radio-button :label="1">是</el-radio-button>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="商品图片：" prop="productImg">
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
        <el-divider content-position="left"><i class="el-icon-menu" /> 商品详情</el-divider>
        <el-row>
          <el-col :span="24">
            <el-form-item prop="description" label-width="0px">
              <Tinymce ref="editor" v-model="form.description" :height="400" width="100%" />
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
import productApi from '@/api/module/product/product-api'
import serviceCategoryApi from '@/api/module/service-category/service-category-api'
import productCategoryApi from '@/api/module/product-category/product-category-api'
import { deepClone } from '@/utils'
import fileProcessApi from '@/api/system/file/jy-file-process'
import fileRecordApi from '@/api/system/file/jy-file-record'
import Tinymce from '@/components/Tinymce'

export default {
  name: 'ProductForm',
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
        name: null,
        code: null,
        productCategoryId: null,
        serviceCategoryId: null,
        productImg: null,
        description: null,
        initialPrice: 0,
        stock: 0,
        unit: null,
        discount: 1,
        currentPrice: 0,
        hot: 1,
        status: 1
      },
      rules: {
        id: [
          { required: true, message: '请输入ID', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '请输入商品名称', trigger: 'blur' }
        ],
        code: [
          { required: true, message: '请输入商品编码', trigger: 'blur' }
        ],
        productCategoryId: [
          { required: true, message: '请输入商品类别', trigger: 'blur' }
        ],
        serviceCategoryId: [
          { required: true, message: '请输入服务类别', trigger: 'blur' }
        ],
        productImg: [
          { required: true, message: '请选择商品图片', trigger: 'blur' }
        ],
        description: [
          { required: true, message: '请输入商品描述', trigger: 'blur' }
        ],
        initialPrice: [
          { required: true, message: '请输入原始价格（元）', trigger: 'blur' }
        ],
        stock: [
          { required: true, message: '请输入商品库存', trigger: 'blur' }
        ],
        unit: [
          { required: true, message: '请输入商品单位', trigger: 'blur' }
        ],
        discount: [
          { required: true, message: '请输入商品折扣', trigger: 'blur' }
        ],
        currentPrice: [
          { required: true, message: '请输入当前价格（元）', trigger: 'blur' }
        ],
        hot: [
          { required: true, message: '请输入是否热门', trigger: 'blur' }
        ],
        status: [
          { required: true, message: '请输入上架状态', trigger: 'blur' }
        ]
      },
      serviceCategoryOptions: [],
      productCategoryOptions: [],
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
      serviceCategoryApi.list({ status: 1 }).then(res => {
        this.serviceCategoryOptions = res.data
      })
      productCategoryApi.list({ status: 1 }).then(res => {
        this.productCategoryOptions = res.data
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
      const tmpForm = deepClone(this.form)
      tmpForm.initialPrice = tmpForm.initialPrice * 100
      tmpForm.currentPrice = tmpForm.currentPrice * 100
      productApi.add(tmpForm, this.idempotentToken).then(response => {
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
      const tmpForm = deepClone(this.form)
      tmpForm.initialPrice = tmpForm.initialPrice * 100
      tmpForm.currentPrice = tmpForm.currentPrice * 100
      productApi.update(tmpForm, this.idempotentToken).then(response => {
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
      await productApi.getById(id).then(response => {
        this.form = response.data
        this.form.initialPrice = this.form.initialPrice / 100
        this.form.currentPrice = this.form.currentPrice / 100
        this.$refs.editor.setContent(this.form.description)
      }).catch(e => {
        this.initloading = false
        return
      })
      if (this.form.productImg === null) {
        this.initloading = false
        return
      }
      await fileRecordApi.getById(this.form.productImg).then(response => {
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
    handleInitialPrice(currentValue, oldValue) {
      if (this.form.initialPrice != null && this.form.discount == null) {
        this.form.currentPrice = this.form.initialPrice
      }
      if (this.form.initialPrice != null && this.form.discount != null) {
        this.form.currentPrice = this.form.initialPrice * this.form.discount
      }
    },
    handleDiscountChange(currentValue, oldValue) {
      if (this.form.initialPrice != null && this.form.discount == null) {
        this.form.currentPrice = this.form.initialPrice
      }
      if (this.form.initialPrice != null && this.form.discount != null) {
        this.form.currentPrice = this.form.initialPrice * this.form.discount
      }
    },
    handleFileExceed() {
      this.$notify.warning({ title: '提示', message: '最多上传一张图片' })
    },
    handleFileRemove(file, fileList) {
      this.form.productImg = null
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
      const relevance = 'product' // 测试模块
      const formData = new FormData()
      this.fileList.forEach(item => {
        formData.append('file', item.raw)
      })
      fileProcessApi.upload(relevance, formData).then(res => {
        this.form.productImg = res.data.fileRecordId
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
