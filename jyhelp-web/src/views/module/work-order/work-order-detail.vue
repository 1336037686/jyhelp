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
    <div
      v-loading="initloading"
      element-loading-text="加载中，请稍后..."
      element-loading-spinner="el-icon-loading"
    >
      <el-descriptions :column="2" border>
        <el-descriptions-item>
          <template slot="label">
            创建用户
          </template>
          {{ form.username }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            工单类别
          </template>
          {{ getNameByCode(workOrderCategoryOptions, form.type) }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            工单标题
          </template>
          {{ form.title }}
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
            工单状态
          </template>
          {{ getNameByCode(workOrderStatusOptions, form.type) }}
        </el-descriptions-item>
        <el-descriptions-item span="2">
          <template slot="label">
            工单内容
          </template>
          {{ form.content }}
        </el-descriptions-item>
        <el-descriptions-item span="2">
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
        username: null,
        type: null,
        title: null,
        content: null,
        phone: null,
        email: null,
        status: null,
        handleRemark: null
      },
      workOrderStatusOptions: [],
      workOrderCategoryOptions: []
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
      this.getDictByCode('module_work_order_status').then(res => {
        this.workOrderStatusOptions = res.data
      })
      this.getDictByCode('module_work_order_category').then(res => {
        this.workOrderCategoryOptions = res.data
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
    }
  }
}
</script>

<style scoped>

</style>
