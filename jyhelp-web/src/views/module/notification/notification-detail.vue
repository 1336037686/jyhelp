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
        <el-descriptions-item span="2">
          <template slot="label">
            公告标题
          </template>
          {{ form.title }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            是否置顶
          </template>
          <el-tag v-if="form.pinned === 1" size="mini" effect="plain" type="success"> <i class="el-icon-success" /> 置 顶</el-tag>
          <el-tag v-if="form.pinned === 0" size="mini" effect="plain" type="danger"> <i class="el-icon-error" /> 不置顶</el-tag>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            公告状态
          </template>
          <el-tag v-if="form.status === 1" size="mini" effect="plain" type="success"> <i class="el-icon-success" /> 显 示</el-tag>
          <el-tag v-if="form.status === 0" size="mini" effect="plain" type="danger"> <i class="el-icon-error" /> 隐 藏</el-tag>
        </el-descriptions-item>
        <el-descriptions-item span="2">
          <template slot="label">
            公告内容
          </template>
          {{ form.content }}
        </el-descriptions-item>
      </el-descriptions>
    </div>
    <span slot="footer" class="dialog-footer">
      <el-button @click="tmpVisible = false">关 闭</el-button>
    </span>
  </el-dialog>
</template>

<script>
import notificationApi from '@/api/module/notification/notification-api'
export default {
  name: 'NotificationDetail',
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
        title: null,
        content: null,
        pinned: null,
        status: null
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
      notificationApi.getById(id).then(response => {
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
