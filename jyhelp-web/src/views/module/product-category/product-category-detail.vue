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
            名称
          </template>
          {{ form.name }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            编号
          </template>
          {{ form.code }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            图标
          </template>
          <el-avatar shape="square" size="large" :src="form.icon" />
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            描述
          </template>
          {{ form.description }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            是否启用
          </template>
          {{ form.status }}
        </el-descriptions-item>
      </el-descriptions>
    </div>
    <span slot="footer" class="dialog-footer">
      <el-button @click="tmpVisible = false">关 闭</el-button>
    </span>
  </el-dialog>
</template>

<script>
import productCategoryApi from '@/api/module/product-category/product-category-api'
export default {
  name: 'ProductCategoryDetail',
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
        name: null,
        code: null,
        icon: null,
        description: null,
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
      productCategoryApi.getById(id).then(response => {
        this.initloading = false
        this.form = response.data
        this.form.icon = this.imgUrlPrefix + this.form.icon
      }).catch(e => {
        this.initloading = false
      })
    }
  }
}
</script>

<style scoped>

</style>
