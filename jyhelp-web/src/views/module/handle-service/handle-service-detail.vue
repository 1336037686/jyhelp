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
            会员服务编号
          </template>
          {{ form.serviceCode }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            服务时间
          </template>
          {{ form.handleTime }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            服务人员账号
          </template>
          {{ form.handleUserName }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            服务人员名称
          </template>
          {{ form.handleUserNickname }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            服务记录
          </template>
          {{ form.handleRemark }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            会员账号
          </template>
          {{ form.username }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            会员昵称
          </template>
          {{ form.nickname }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            服务状态
          </template>
          <el-tag v-if="form.handleStatus === 'await'" size="mini" effect="plain" type="warning"> <i class="el-icon-success" /> {{ getNameByCode(handleServiceStatusOptions, form.handleStatus) }}</el-tag>
          <el-tag v-if="form.handleStatus === 'completed'" size="mini" effect="plain" type="success"> <i class="el-icon-error" /> {{ getNameByCode(handleServiceStatusOptions, form.handleStatus) }}</el-tag>
          <el-tag v-if="form.handleStatus === 'reject'" size="mini" effect="plain" type="danger"> <i class="el-icon-error" /> {{ getNameByCode(handleServiceStatusOptions, form.handleStatus) }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            用户评分
          </template>
          <el-rate v-model="form.userScore" disabled />
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
      },
      handleServiceStatusOptions: []
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
      this.getDictByCode('module_handle_service_status').then(res => {
        this.handleServiceStatusOptions = res.data
      })
    },
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
