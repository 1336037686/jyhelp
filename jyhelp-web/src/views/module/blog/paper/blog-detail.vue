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
            文章名称
          </template>
          {{ form.title }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            摘要
          </template>
          {{ form.intro }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            类别
          </template>
          {{ form.category }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            标签
          </template>
          {{ form.tag }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            封面
          </template>
          {{ form.cover }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            文章内容
          </template>
          {{ form.content }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            状态（草稿、发布）
          </template>
          {{ form.status }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            作者
          </template>
          {{ form.author }}
        </el-descriptions-item>
      </el-descriptions>
    </div>
    <span slot="footer" class="dialog-footer">
      <el-button @click="tmpVisible = false">关 闭</el-button>
    </span>
  </el-dialog>
</template>

<script>
import blogApi from '@/api/module/blog/blog-api'
export default {
  name: 'BlogDetail',
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
        intro: null,
        category: null,
        tag: null,
        cover: null,
        content: null,
        status: null,
        author: null
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
      blogApi.getById(id).then(response => {
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
