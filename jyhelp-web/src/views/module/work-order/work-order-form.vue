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
        label-width="100px"
        element-loading-text="加载中，请稍后..."
        element-loading-spinner="el-icon-loading"
      >
        <el-form-item label="ID：" prop="id">
          <el-input v-model="form.id" />
        </el-form-item>
        <el-form-item label="创建用户：" prop="userId">
          <el-input v-model="form.userId" />
        </el-form-item>
        <el-form-item label="工单类别：" prop="type">
          <el-input v-model="form.type" />
        </el-form-item>
        <el-form-item label="工单标题：" prop="title">
          <el-input v-model="form.title" />
        </el-form-item>
        <el-form-item label="工单内容：" prop="content">
          <el-input v-model="form.content" />
        </el-form-item>
        <el-form-item label="联系方式：" prop="phone">
          <el-input v-model="form.phone" />
        </el-form-item>
        <el-form-item label="联系邮箱：" prop="email">
          <el-input v-model="form.email" />
        </el-form-item>
        <el-form-item label="工单状态（待处理、已处理、不予解决）：" prop="status">
          <el-input v-model="form.status" />
        </el-form-item>
        <el-form-item label="处理备注：" prop="handleRemark">
          <el-input v-model="form.handleRemark" />
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
import workOrderApi from '@/api/module/work-order/work-order-api'
export default {
  name: 'WorkOrderForm',
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
        userId: null,
        type: null,
        title: null,
        content: null,
        phone: null,
        email: null,
        status: null,
        handleRemark: null
      },
      rules: {
        id: [
          { required: true, message: '请输入ID', trigger: 'blur' }
        ],
        userId: [
          { required: true, message: '请输入创建用户', trigger: 'blur' }
        ],
        type: [
          { required: true, message: '请输入工单类别', trigger: 'blur' }
        ],
        title: [
          { required: true, message: '请输入工单标题', trigger: 'blur' }
        ],
        content: [
          { required: true, message: '请输入工单内容', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '请输入联系方式', trigger: 'blur' }
        ],
        email: [
          { required: true, message: '请输入联系邮箱', trigger: 'blur' }
        ],
        status: [
          { required: true, message: '请输入工单状态（待处理、已处理、不予解决）', trigger: 'blur' }
        ],
        handleRemark: [
          { required: true, message: '请输入处理备注', trigger: 'blur' }
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
      workOrderApi.add(this.form, this.idempotentToken).then(response => {
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
      workOrderApi.update(this.form, this.idempotentToken).then(response => {
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
      workOrderApi.getById(id).then(response => {
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
