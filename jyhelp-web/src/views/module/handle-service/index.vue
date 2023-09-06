<template>
  <div
    v-loading="deleteLoading"
    style="margin: 10px"
    element-loading-text="删除中，请稍后..."
    element-loading-spinner="el-icon-loading"
  >
    <el-card class="box-card" shadow="always">
      <el-form v-show="queryFormVisiable" :inline="true" size="mini" :model="queryForm" label-width="120px">
        <el-form-item label="服务记录编号：">
          <el-input v-model="queryForm.handleCode" placeholder="服务记录编号" />
        </el-form-item>
        <el-form-item label="会员服务ID：">
          <el-input v-model="queryForm.customerServiceId" placeholder="会员服务ID" />
        </el-form-item>
        <el-form-item label="服务时间：">
          <el-input v-model="queryForm.handleTime" placeholder="服务时间" />
        </el-form-item>
        <el-form-item label="服务人员：">
          <el-input v-model="queryForm.handleUser" placeholder="服务人员" />
        </el-form-item>
        <el-form-item label="服务记录：">
          <el-input v-model="queryForm.handleRemark" placeholder="服务记录" />
        </el-form-item>
        <el-form-item label="服务附件：">
          <el-input v-model="queryForm.attachment" placeholder="服务附件" />
        </el-form-item>
        <el-form-item label="服务状态：">
          <el-input v-model="queryForm.handleStatus" placeholder="服务状态" />
        </el-form-item>
        <el-form-item label="用户反馈：">
          <el-input v-model="queryForm.userFeedback" placeholder="用户反馈" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="handleQuery">查 询</el-button>
          <el-button type="info" icon="el-icon-circle-close" @click="handleReset">重 置</el-button>
        </el-form-item>
      </el-form>
      <div style="margin-top: 5px">
        <el-button type="primary" icon="el-icon-view" size="mini" @click="handleShow">查 看</el-button>
        <el-button type="success" icon="el-icon-plus" size="mini" @click="handleCreate">新 增</el-button>
        <el-button type="warning" icon="el-icon-edit-outline" size="mini" @click="handleUpdate">修 改</el-button>
        <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleRemove">删 除</el-button>
      </div>
    </el-card>

    <el-card class="box-card" shadow="always" style="margin-top: 5px">
      <div slot="header" class="clearfix">
        <span><i class="el-icon-caret-right" /> 服务执行记录记录</span>
        <el-row style="float: right">
          <el-button icon="el-icon-search" circle size="mini" @click="() => this.queryFormVisiable = !this.queryFormVisiable" />
          <el-button icon="el-icon-refresh" circle size="mini" @click="handleQuery()" />
          <el-button icon="el-icon-s-grid" circle size="mini" @click="selectColumns()" />
        </el-row>
      </div>
      <el-table
        ref="table"
        v-loading="tableData.loading"
        element-loading-text="加载中，请稍后..."
        element-loading-spinner="el-icon-loading"
        :data="tableData.records"
        highlight-current-row
        style="width: 100%"
        empty-text="暂无数据"
        :header-cell-style="{background:'#F5F7FA', color: '#303133', fontWeight: 700}"
        @row-click="handleTableRowClick"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column type="index" width="80" label="序号" align="center" />
        <el-table-column v-if="checkColumnDisplayed('id', columnsData.columns)" prop="id" label="ID" align="center" show-overflow-tooltip />
        <el-table-column v-if="checkColumnDisplayed('handleCode', columnsData.columns)" prop="handleCode" label="服务记录编号" align="center" show-overflow-tooltip />
        <el-table-column v-if="checkColumnDisplayed('customerServiceId', columnsData.columns)" prop="customerServiceId" label="会员服务ID" align="center" show-overflow-tooltip />
        <el-table-column v-if="checkColumnDisplayed('handleTime', columnsData.columns)" prop="handleTime" label="服务时间" align="center" show-overflow-tooltip />
        <el-table-column v-if="checkColumnDisplayed('handleUser', columnsData.columns)" prop="handleUser" label="服务人员" align="center" show-overflow-tooltip />
        <el-table-column v-if="checkColumnDisplayed('handleRemark', columnsData.columns)" prop="handleRemark" label="服务记录" align="center" show-overflow-tooltip />
        <el-table-column v-if="checkColumnDisplayed('attachment', columnsData.columns)" prop="attachment" label="服务附件" align="center" show-overflow-tooltip />
        <el-table-column v-if="checkColumnDisplayed('handleStatus', columnsData.columns)" prop="handleStatus" label="服务状态" align="center" show-overflow-tooltip />
        <el-table-column v-if="checkColumnDisplayed('userFeedback', columnsData.columns)" prop="userFeedback" label="用户反馈" align="center" show-overflow-tooltip />
      </el-table>
      <div style="text-align: center;margin-top: 10px">
        <el-pagination
          v-model="tableData.pageNumber"
          background
          layout="prev, pager, next"
          :page-size="tableData.pageSize"
          :hide-on-single-page="true"
          :total="tableData.total"
          @current-change="handleChangePage"
        />
      </div>
    </el-card>
    <HandleServiceForm :id="editData.id" :title="editData.title" :visible.sync="editData.visiable" />
    <HandleServiceDetail :id="showData.id" :title="showData.title" :visible.sync="showData.visiable" />
    <select-columns :title="columnsData.title" :columns="columnsData.columns" :visible.sync="columnsData.visiable" />
  </div>
</template>

<script>
import handleServiceApi from '@/api/module/handle-service/handle-service-api'
import HandleServiceForm from '@/views/module/handle-service/handle-service-form'
import HandleServiceDetail from '@/views/module/handle-service/handle-service-detail'
import SelectColumns from '@/components/SelectColumns'
export default {
  components: { SelectColumns, HandleServiceDetail, HandleServiceForm },
  data() {
    return {
      deleteLoading: false,
      queryFormVisiable: true,
      queryForm: {
        handleCode: null,
        customerServiceId: null,
        handleTime: null,
        handleUser: null,
        handleRemark: null,
        attachment: null,
        handleStatus: null,
        userFeedback: null
      },
      tableData: {
        loading: false,
        pageNumber: 1,
        pageSize: 10,
        pages: 1,
        total: 1,
        records: [],
        hasPrevious: false,
        hasNext: false
      },
      editData: {
        visiable: false,
        title: '',
        id: null
      },
      showData: {
        visiable: false,
        title: '',
        id: null
      },
      selectData: {
        current: null,
        record: []
      },
      columnsData: {
        visiable: false,
        columns: [
          { key: 'id', label: 'ID', _showed: true },
          { key: 'handleCode', label: '服务记录编号', _showed: true },
          { key: 'customerServiceId', label: '会员服务ID customer_service id', _showed: true },
          { key: 'handleTime', label: '服务时间', _showed: true },
          { key: 'handleUser', label: '服务人员', _showed: true },
          { key: 'handleRemark', label: '服务记录', _showed: true },
          { key: 'attachment', label: '服务附件', _showed: true },
          { key: 'handleStatus', label: '服务状态', _showed: true },
          { key: 'userFeedback', label: '用户反馈', _showed: true }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.tableData.loading = true
      const queryForm = { ...this.queryForm, pageNumber: this.tableData.pageNumber, pageSize: this.tableData.pageSize }
      handleServiceApi.getList(queryForm).then(response => {
        this.tableData.loading = false
        this.tableData = response
      })
    },
    selectColumns() {
      this.columnsData.visiable = true
    },
    handleQuery() {
      this.tableData.pageNumber = 1
      this.getList()
    },
    handleReset() {
      this.queryForm.handleCode = null
      this.queryForm.customerServiceId = null
      this.queryForm.handleTime = null
      this.queryForm.handleUser = null
      this.queryForm.handleRemark = null
      this.queryForm.attachment = null
      this.queryForm.handleStatus = null
      this.queryForm.userFeedback = null
      this.tableData.pageNumber = 1
      this.getList()
    },
    handleShow() {
      if (!this.selectData.current) {
        this.$notify.warning({ title: '警告', message: '请先选择一条数据' })
        return
      }
      this.showData.title = '查看'
      this.showData.id = this.selectData.current.id
      this.showData.visiable = true
    },
    handleCreate() {
      this.editData.title = '新增'
      this.editData.id = null
      this.editData.visiable = true
    },
    handleUpdate() {
      if (!this.selectData.current) {
        this.$notify.warning({ title: '警告', message: '请先选择一条数据' })
        return
      }
      this.editData.title = '修改'
      this.editData.id = this.selectData.current.id
      this.editData.visiable = true
    },
    handleRemove() {
      const selectRows = this.$refs.table.selection
      if (!selectRows || selectRows.length === 0) {
        this.$notify.warning({ title: '警告', message: '请先选择一条数据' })
        return
      }
      this.$confirm('此操作将永久删除选中数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        customClass: 'jy-message-box'
      }).then(() => {
        const ids = []
        for (let i = 0; i < this.$refs.table.selection.length; i++) ids.push(this.$refs.table.selection[i].id)
        this.deleteLoading = true
        handleServiceApi.remove(ids).then(response => {
          this.deleteLoading = false
          this.getList()
          this.$notify.success({ title: '成功', message: '删除成功' })
        }).catch(e => {
          this.deleteLoading = false
        })
      })
    },
    handleTableRowClick(row, column, event) {
      this.selectData.current = row
      this.$refs.table.toggleRowSelection(row)
    },
    handleChangePage(page) {
      this.tableData.pageNumber = page
      this.getList()
    }
  }
}
</script>
<style scoped>

</style>
