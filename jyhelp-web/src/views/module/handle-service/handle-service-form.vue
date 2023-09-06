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
        label-width="120px"
        element-loading-text="加载中，请稍后..."
        element-loading-spinner="el-icon-loading"
      >
        <el-form-item label="服务记录编号：" prop="handleCode">
          <el-input v-model="form.handleCode" />
        </el-form-item>
        <el-form-item label="会员服务ID：" prop="customerServiceId">
          <el-input v-model="form.customerServiceId" />
        </el-form-item>
        <el-form-item label="服务时间：" prop="handleTime">
          <el-input v-model="form.handleTime" />
        </el-form-item>
        <el-form-item label="服务人员：" prop="handleUser">
          <el-input v-model="form.handleUser" />
        </el-form-item>
        <el-form-item label="服务记录：" prop="handleRemark">
          <el-input v-model="form.handleRemark" />
        </el-form-item>
        <el-form-item label="服务附件：" prop="attachment">
          <el-input v-model="form.attachment" />
        </el-form-item>
        <el-form-item label="服务状态：" prop="handleStatus">
          <el-input v-model="form.handleStatus" />
        </el-form-item>
        <el-form-item label="用户反馈：" prop="userFeedback">
          <el-input v-model="form.userFeedback" />
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
import handleServiceApi from '@/api/module/handle-service/handle-service-api'
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
        id: [
          { required: true, message: '请输入ID', trigger: 'blur' }
        ],
        handleCode: [
          { required: true, message: '请输入服务记录编号', trigger: 'blur' }
        ],
        customerServiceId: [
          { required: true, message: '请输入会员服务ID', trigger: 'blur' }
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
      }
    }
  },
  watch: {
    async visible(newVal) {
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
    getById(id) {
      this.initloading = true
      handleServiceApi.getById(id).then(response => {
        this.initloading = false
        this.form = response.data
      }).catch(e => {
        this.initloading = false
      })
    },
    resetForm(formName) {
      this.form.id = null
      this.$refs[formName].resetFields()
      this.tmpVisible = false
    }
  }
}
</script>

<style scoped>

</style>
