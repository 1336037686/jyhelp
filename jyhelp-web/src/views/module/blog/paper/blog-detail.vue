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
            类别
          </template>
          {{ getNameByCode(categoryOptions, form.category) }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            标签
          </template>
          {{ getNameByCode(tagOptions, form.tag) }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            封面
          </template>
          <el-avatar shape="square" size="large" :src="form.cover" />
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            状态
          </template>
          {{ getNameByCode(blogStatusOptions, form.status) }}
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
import tagApi from '@/api/module/tag/tag-api'
import categoryApi from '@/api/module/category/category-api'
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
      imgUrlPrefix: '/api/file-process/download/',
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
      },
      blogStatusOptions: [],
      tagOptions: [],
      categoryOptions: []
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
      this.getDictByCode('module_blog_status').then(res => {
        this.blogStatusOptions = res.data
      })
      tagApi.list().then(res => {
        this.tagOptions = []
        for (let i = 0; i < res.data.length; i++) {
          this.tagOptions.push({ name: res.data[i].name, code: res.data[i].id })
        }
      })
      categoryApi.list().then(res => {
        this.categoryOptions = []
        for (let i = 0; i < res.data.length; i++) {
          this.categoryOptions.push({ name: res.data[i].name, code: res.data[i].id })
        }
      })
    },
    getById(id) {
      this.initloading = true
      blogApi.getById(id).then(response => {
        this.initloading = false
        this.form = response.data
        this.form.cover = this.imgUrlPrefix + this.form.cover
      }).catch(e => {
        this.initloading = false
      })
    }
  }
}
</script>

<style scoped>

</style>
