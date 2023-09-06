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
            服务记录编号
          </template>
          {{ form.handleCode }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            会员服务ID
          </template>
          {{ form.customerServiceId }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            服务时间
          </template>
          {{ form.handleTime }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            服务人员
          </template>
          {{ form.handleUser }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            服务记录
          </template>
          {{ form.handleRemark }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            服务附件
          </template>
          {{ form.attachment }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            服务状态 （已完成、进行中、待处理）
          </template>
          {{ form.handleStatus }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            用户反馈
          </template>
          {{ form.userFeedback }}
        </el-descriptions-item>
      </el-descriptions>
    </div>
    <span slot="footer" class="dialog-footer">
      <el-button @click="tmpVisible = false">关 闭</el-button>
    </span>
  </el-dialog>
</template>

<script>
import handleServiceApi from '@/api/module/handle-service/handle-service-api'
export default {
  name: 'HandleServiceDetail',
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
        handleCode: null,
        customerServiceId: null,
        handleTime: null,
        handleUser: null,
        handleRemark: null,
        attachment: null,
        handleStatus: null,
        userFeedback: null
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
      handleServiceApi.getById(id).then(response => {
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
