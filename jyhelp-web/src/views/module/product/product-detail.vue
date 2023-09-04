<template>
  <el-dialog
    :title="title"
    :visible.sync="tmpVisible"
    :close-on-press-escape="false"
    :close-on-click-modal="false"
    :show-close="false"
    width="60%"
    class="jy-dialog"
  >
    <div
      v-loading="initloading"
      element-loading-text="加载中，请稍后..."
      element-loading-spinner="el-icon-loading"
    >
      <el-divider content-position="left"><i class="el-icon-menu" /> 基本信息</el-divider>
      <el-descriptions :column="3" border>
        <el-descriptions-item>
          <template slot="label">
            商品名称
          </template>
          {{ form.name }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            商品编码
          </template>
          {{ form.code }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            商品类别
          </template>
          {{ form.productCategoryId }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            服务类别
          </template>
          {{ form.serviceCategoryId }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            商品图片
          </template>
          {{ form.productImg }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            原始价格（单位分）
          </template>
          {{ form.initialPrice }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            商品库存
          </template>
          {{ form.stock }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            商品折扣
          </template>
          {{ form.discount }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            当前价格（单位分）
          </template>
          {{ form.currentPrice }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            是否热门
          </template>
          {{ form.hot }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            上架状态
          </template>
          {{ form.status }}
        </el-descriptions-item>
      </el-descriptions>
      <el-row style="margin-top: 5px">
        <el-col :span="24">
          <el-divider content-position="left"><i class="el-icon-menu" /> 商品详情</el-divider>
          <div style="margin-top: 10px" v-html="form.description" />
        </el-col>
      </el-row>
    </div>
    <span slot="footer" class="dialog-footer">
      <el-button @click="tmpVisible = false">关 闭</el-button>
    </span>
  </el-dialog>
</template>

<script>
import productApi from '@/api/module/product/product-api'
export default {
  name: 'ProductDetail',
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
        name: null,
        code: null,
        productCategoryId: null,
        serviceCategoryId: null,
        productImg: null,
        description: null,
        initialPrice: null,
        stock: null,
        unit: null,
        discount: null,
        currentPrice: null,
        hot: null,
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
      productApi.getById(id).then(response => {
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
