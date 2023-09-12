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
            ID
          </template>
          {{ form.id }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            创建用户
          </template>
          {{ form.userId }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            工单类别
          </template>
          {{ form.type }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            工单标题
          </template>
          {{ form.title }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            工单内容
          </template>
          {{ form.content }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            联系方式
          </template>
          {{ form.phone }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            联系邮箱
          </template>
          {{ form.email }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            工单状态（待处理、已处理、不予解决）
          </template>
          {{ form.status }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            处理备注
          </template>
          {{ form.handleRemark }}
        </el-descriptions-item>
      </el-descriptions>
    </div>
    <span slot="footer" class="dialog-footer">
      <el-button @click="tmpVisible = false">关 闭</el-button>
    </span>
  </el-dialog>
</template>

<script>
import workOrderApi from '@/api/module/work-order/work-order-api'
export default {
  name: 'WorkOrderDetail',
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
        userId: null,
        type: null,
        title: null,
        content: null,
        phone: null,
        email: null,
        status: null,
        handleRemark: null
      }
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
  methods: {
    getById(id) {
      this.initloading = true
      workOrderApi.getById(id).then(response => {
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
