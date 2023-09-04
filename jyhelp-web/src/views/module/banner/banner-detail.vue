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
      <el-descriptions :column="1" border>
        <el-descriptions-item>
          <template slot="label">
            Banner图片
          </template>
          <el-avatar shape="square" size="large" :src="form.bannerImg" />
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            排序
          </template>
          {{ form.sort }}
        </el-descriptions-item>
      </el-descriptions>
    </div>
    <span slot="footer" class="dialog-footer">
      <el-button @click="tmpVisible = false">关 闭</el-button>
    </span>
  </el-dialog>
</template>

<script>
import bannerApi from '@/api/module/banner/banner-api'
export default {
  name: 'BannerDetail',
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
      form: {
        id: null,
        bannerImg: null,
        sort: null
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
      bannerApi.getById(id).then(response => {
        this.initloading = false
        this.form = response.data
        this.form.bannerImg = this.imgUrlPrefix + this.form.bannerImg
      }).catch(e => {
        this.initloading = false
      })
    }
  }
}
</script>

<style scoped>

</style>
