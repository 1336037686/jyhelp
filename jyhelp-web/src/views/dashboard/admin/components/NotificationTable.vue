<template>
  <div>
    <el-scrollbar :style="{height:height,width:width}">
      <h4 style="color: rgba(0, 0, 0, 0.45)">系统公告</h4>
      <el-collapse v-model="activeName" :style="{height:height,width:width}">
        <el-collapse-item v-for="(item, index) in tableData" :key="'notification_' + index" :name="'' + index">
          <template slot="title">
            <div style="width: 100%">
              <span style="font-size: 14px;padding-left: 10px">{{ item.title }}</span>
              <span style="float: right;margin-right: 10px">{{ item.createTime.substring(0, 10) }}</span>
            </div>
          </template>
          <div style="padding: 10px">公告内容：{{ item.content }}</div>
        </el-collapse-item>
      </el-collapse>
    </el-scrollbar>
  </div>
</template>

<script>
import resize from './mixins/resize'
import notificationApi from '@/api/module/notification/notification-api'
export default {
  mixins: [resize],
  props: {
    className: {
      type: String,
      default: 'chart'
    },
    width: {
      type: String,
      default: '100%'
    },
    height: {
      type: String,
      default: '300px'
    }
  },
  data() {
    return {
      activeName: null,
      queryForm: {
        id: null,
        title: null,
        content: null,
        pinned: null,
        status: 1
      },
      tableData: []
    }
  },
  mounted() {
    const queryForm = { ...this.queryForm, pageNumber: 1, pageSize: 5 }
    notificationApi.getList(queryForm).then(response => {
      this.tableData = response.records
    })
  }
}
</script>

<style scoped>
 /deep/ .el-scrollbar__wrap {
   overflow-x: hidden;
 }
</style>
