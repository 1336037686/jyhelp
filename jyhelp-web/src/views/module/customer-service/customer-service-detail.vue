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
    <div
      v-loading="initloading"
      element-loading-text="加载中，请稍后..."
      element-loading-spinner="el-icon-loading"
    >
      <el-descriptions :column="2" border>
        <el-descriptions-item>
          <template slot="label">
            服务项目编号
          </template>
          {{ form.serviceCode }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            用户名
          </template>
          {{ form.username }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            昵称
          </template>
          {{ form.nickname }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            服务类别
          </template>
          {{ form.serviceCategoryName }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            剩余服务库存
          </template>
          {{ form.serviceStock }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            服务状态
          </template>
          {{ getNameByCode(serviceStatusOptions, form.serviceStatus) }}
        </el-descriptions-item>
      </el-descriptions>
    </div>
    <span slot="footer" class="dialog-footer">
      <el-button @click="tmpVisible = false">关 闭</el-button>
    </span>
  </el-dialog>
</template>

<script>
import customerServiceApi from '@/api/module/customer-service/customer-service-api'
export default {
  name: 'CustomerServiceDetail',
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
      initloading: false,
      form: {
        id: null,
        serviceCode: null,
        userId: null,
        username: null,
        nickname: null,
        serviceCategoryId: null,
        serviceCategoryName: null,
        serviceStock: null,
        serviceStatus: null
      },
      serviceStatusOptions: []
    }
  },
  watch: {
    visible(newVal) {
      this.tmpVisible = newVal
      if (newVal) {
        this.getById(this.id)
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
      this.getDictByCode('module_service_status').then(res => {
        this.serviceStatusOptions = res.data
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
    }
  }
}
</script>

<style scoped>

</style>
