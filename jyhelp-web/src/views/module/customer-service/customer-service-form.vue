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
        <el-form-item label="服务项目编号：" prop="serviceCode">
          <el-input v-model="form.serviceCode" readonly />
        </el-form-item>
        <el-form-item label="用户名：" prop="userId">
          <el-input v-model="form.username" readonly />
        </el-form-item>
        <el-form-item label="用户昵称：" prop="userId">
          <el-input v-model="form.nickname" readonly />
        </el-form-item>
        <el-form-item label="服务类别：" prop="serviceCategoryId">
          <el-select v-model="form.serviceCategoryId" placeholder="请选择" style="width: 100%">
            <el-option v-for="item in serviceCategoryOptions" :key="item.code" :label="item.name" :value="item.code" />
          </el-select>
        </el-form-item>
        <el-form-item label="剩余服务库存：" prop="serviceStock">
          <el-input v-model="form.serviceStock" />
        </el-form-item>
        <el-form-item label="服务状态 ：" prop="serviceStatus">
          <el-select v-model="form.serviceStatus" style="width: 100%">
            <el-option v-for="(item, index) in serviceStatusOptions" :key="'serviceStatus_' + index" :label="item.name" :value="item.code" />
          </el-select>
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
import customerServiceApi from '@/api/module/customer-service/customer-service-api'
import serviceCategoryApi from '@/api/module/service-category/service-category-api'
export default {
  name: 'CustomerServiceForm',
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
        serviceCode: null,
        userId: null,
        username: null,
        nickname: null,
        serviceCategoryId: null,
        serviceStock: null,
        serviceStatus: null
      },
      rules: {
        id: [
          { required: true, message: '请输入ID', trigger: 'blur' }
        ],
        serviceCode: [
          { required: true, message: '请输入服务项目编号', trigger: 'blur' }
        ],
        userId: [
          { required: true, message: '请输入用户ID', trigger: 'blur' }
        ],
        serviceCategoryId: [
          { required: true, message: '请输入服务类别', trigger: 'blur' }
        ],
        serviceStock: [
          { required: true, message: '请输入剩余服务库存', trigger: 'blur' }
        ],
        serviceStatus: [
          { required: true, message: '请输入服务状态', trigger: 'blur' }
        ]
      },
      serviceCategoryOptions: [],
      serviceStatusOptions: []
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
  created() {
    this.getDict()
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
    getDict() {
      serviceCategoryApi.list({ status: 1 }).then(res => {
        this.serviceCategoryOptions = []
        for (let i = 0; i < res.data.length; i++) {
          this.serviceCategoryOptions.push({ code: res.data[i].id, name: res.data[i].name })
        }
      })
      this.getDictByCode('module_service_status').then(res => {
        this.serviceStatusOptions = res.data
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
      customerServiceApi.add(this.form, this.idempotentToken).then(response => {
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
      customerServiceApi.update(this.form, this.idempotentToken).then(response => {
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
      customerServiceApi.getById(id).then(response => {
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
