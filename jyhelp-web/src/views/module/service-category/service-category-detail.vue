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
            服务名称
          </template>
          {{ form.name }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            服务编号
          </template>
          {{ form.code }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            服务图标
          </template>
          <el-avatar shape="square" size="large" :src="form.icon" />
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            服务类型
          </template>
          {{ getNameByCode(typeOptions, form.type) }}
        </el-descriptions-item>
        <el-descriptions-item :span="2">
          <template slot="label">
            是否启用
          </template>
          <el-tag v-if="form.status === 1" size="mini" effect="plain" type="success"> <i class="el-icon-success" /> 启 用</el-tag>
          <el-tag v-if="form.status === 0" size="mini" effect="plain" type="danger"> <i class="el-icon-error" /> 禁 用</el-tag>
        </el-descriptions-item>
        <el-descriptions-item :span="2">
          <template slot="label">
            服务描述
          </template>
          {{ form.description }}
        </el-descriptions-item>
      </el-descriptions>
    </div>
    <span slot="footer" class="dialog-footer">
      <el-button @click="tmpVisible = false">关 闭</el-button>
    </span>
  </el-dialog>
</template>

<script>
import serviceCategoryApi from '@/api/module/service-category/service-category-api'
export default {
  name: 'ServiceCategoryDetail',
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
      imgUrlPrefix: '/api/file-process/download/',
      typeOptions: [],
      form: {
        id: null,
        name: null,
        code: null,
        icon: null,
        type: null,
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
  created() {
    this.getDictByCode('module_shop_service_category_type').then(res => {
      this.typeOptions = res.data
    })
  },
  methods: {
    getById(id) {
      this.initloading = true
      serviceCategoryApi.getById(id).then(response => {
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
