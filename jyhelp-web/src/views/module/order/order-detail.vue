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
            订单编号
          </template>
          {{ form.orderCode }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            商品名称
          </template>
          {{ form.productName }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            购买数量
          </template>
          {{ form.quantity }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            购买用户账号
          </template>
          {{ form.username }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            购买用户昵称
          </template>
          {{ form.nickname }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            所需金额
          </template>
          {{ form.totalAmount }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            实际金额
          </template>
          {{ form.finalTotalAmount }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            订单状态
          </template>
          {{ getNameByCode(orderStatusOptions, form.orderStatus) }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            下单时间
          </template>
          {{ form.orderTime }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            付款时间
          </template>
          {{ form.paymentTime }}
        </el-descriptions-item>
        <el-descriptions-item span="2">
          <template slot="label">
            付款方式
          </template>
          {{ getNameByCode(orderPaymethodOptions, form.peymentMethod) }}
        </el-descriptions-item>
        <el-descriptions-item span="2">
          <template slot="label">
            备注
          </template>
          {{ form.remark }}
        </el-descriptions-item>
      </el-descriptions>
    </div>
    <span slot="footer" class="dialog-footer">
      <el-button @click="tmpVisible = false">关 闭</el-button>
    </span>
  </el-dialog>
</template>

<script>
import orderApi from '@/api/module/order/order-api'
export default {
  name: 'OrderDetail',
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
        orderCode: null,
        productId: null,
        productName: null,
        quantity: null,
        userId: null,
        username: null,
        nickname: null,
        totalAmount: null,
        finalTotalAmount: null,
        orderStatus: null,
        orderTime: null,
        paymentTime: null,
        peymentMethod: null,
        remark: null
      },
      orderStatusOptions: [],
      orderPaymethodOptions: []
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
      this.getDictByCode('module_order_status').then(res => {
        this.orderStatusOptions = res.data
      })
      this.getDictByCode('module_order_paymethod').then(res => {
        this.orderPaymethodOptions = res.data
      })
    },
    getById(id) {
      this.initloading = true
      orderApi.getById(id).then(response => {
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
