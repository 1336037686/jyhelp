<template>
  <el-dialog
    :title="title"
    :visible.sync="tmpVisible"
    :close-on-press-escape="false"
    :close-on-click-modal="false"
    :show-close="false"
    width="50%"
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
          <el-col :span="12">
            <el-form-item label="服务记录编号：" prop="handleCode">
              <el-input v-model="form.handleCode" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="会员服务：" prop="customerServiceId">
              <el-select v-model="form.customerServiceId" style="width: 100%">
                <el-option v-for="item in customerServiceOptions" :key="'customerService_' + item.id" :label="item.serviceCategoryName + '_' + item.serviceCode + '_' + item.nickname" :value="item.id">
                  <el-row>
                    <el-col :span="8">{{ item.serviceCategoryName }}</el-col>
                    <el-col :span="8" style="color: #8492a6; font-size: 13px; padding-left: 10px">{{ item.serviceCode }}</el-col>
                    <el-col :span="8" style="color: #8492a6; font-size: 13px; padding-left: 10px">{{ item.nickname }}</el-col>
                  </el-row>
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="服务记录：" prop="handleRemark">
              <el-input v-model="form.handleRemark" :rows="2" maxlength="255" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="服务状态：" prop="handleStatus">
              <el-select v-model="form.handleStatus" style="width: 100%">
                <el-option v-for="(item, index) in handleServiceStatusOptions" :key="'handleStatus_' + index" :label="item.name" :value="item.code" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="服务附件：" prop="attachment">
              <el-upload
                ref="fileUpload"
                name="file"
                :auto-upload="false"
                list-type="picture-card"
                accept="image/jpeg,image/jpg,image/png,image/gif"
                action="Fake Action"
                :limit="10"
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
import handleServiceApi from '@/api/module/handle-service/handle-service-api'
import customerServiceApi from '@/api/module/customer-service/customer-service-api'
import fileProcessApi from '@/api/system/file/jy-file-process'
import fileRecordApi from '@/api/system/file/jy-file-record'
export default {
  name: 'HandleServiceForm',
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
        handleCode: null,
        customerServiceId: null,
        handleTime: null,
        handleUser: null,
        handleRemark: null,
        attachment: null,
        handleStatus: null,
        userFeedback: null
      },
      rules: {
        handleCode: [
          { required: true, message: '请输入服务记录编号', trigger: 'blur' }
        ],
        customerServiceId: [
          { required: true, message: '请输入会员服务', trigger: 'blur' }
        ],
        handleTime: [
          { required: true, message: '请输入服务时间', trigger: 'blur' }
        ],
        handleUser: [
          { required: true, message: '请输入服务人员', trigger: 'blur' }
        ],
        handleRemark: [
          { required: true, message: '请输入服务记录', trigger: 'blur' }
        ],
        attachment: [
          { required: true, message: '请输入服务附件', trigger: 'blur' }
        ],
        handleStatus: [
          { required: true, message: '请输入服务状态', trigger: 'blur' }
        ],
        userFeedback: [
          { required: true, message: '请输入用户反馈', trigger: 'blur' }
        ]
      },
      handleServiceStatusOptions: [],
      customerServiceOptions: [],
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
      customerServiceApi.list({ serviceStatus: 'inprogress' }).then(res => {
        this.customerServiceOptions = res.data
      })
      this.getDictByCode('module_handle_service_status').then(res => {
        this.handleServiceStatusOptions = res.data
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
      handleServiceApi.add(this.form, this.idempotentToken).then(response => {
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
      handleServiceApi.update(this.form, this.idempotentToken).then(response => {
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
      await handleServiceApi.getById(id).then(response => {
        this.initloading = false
        this.form = response.data
      }).catch(e => {
        this.initloading = false
      })
      if (this.form.attachment === null) {
        this.initloading = false
        return
      }
      await fileRecordApi.getMultipleByIds(this.form.attachment).then(response => {
        this.initloading = false
        for (let i = 0; i < response.data.length; i++) {
          this.fileList.push({ id: response.data[i].id, name: response.data[i].realName, url: this.imgUrlPrefix + response.data[i].id })
        }
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
      this.$notify.warning({ title: '提示', message: '最多上传10张图片' })
    },
    handleFileRemove(file, fileList) {
      this.fileList = fileList
      if (this.fileList === null || this.fileList.length === 0) this.form.attachment = null
      else this.form.attachment = this.fileList.map(x => x.id).join(',')
    },
    handleFileUploadChange(file, fileList) {
      const isIMAGE = (file.raw.type === 'image/jpeg' || file.raw.type === 'image/png' || file.raw.type === 'image/gif')
      const isLt1M = file.size / 1024 / 1024 < 1
      if (!isIMAGE) {
        this.$notify.error('上传文件只能是图片格式!')
        fileList.splice(fileList.length - 1, 1)
        this.fileList = fileList
        return false
      }
      if (!isLt1M) {
        this.$notify.error('上传文件大小不能超过 1MB!')
        fileList.splice(fileList.length - 1, 1)
        this.fileList = fileList
        return false
      }
      const relevance = 'handle-service' // 模块
      const formData = new FormData()
      formData.append('file', file.raw)
      fileProcessApi.upload(relevance, formData).then(res => {
        fileList[fileList.length - 1].id = res.data.fileRecordId
        this.fileList = fileList
        this.form.attachment = this.fileList.map(x => x.id).join(',')
      }).catch(e => {
        fileList.splice(fileList.length - 1, 1)
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
