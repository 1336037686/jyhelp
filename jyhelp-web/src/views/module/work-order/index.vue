<template>
  <div
    v-loading="deleteLoading"
    style="margin: 10px"
    element-loading-text="删除中，请稍后..."
    element-loading-spinner="el-icon-loading"
  >
    <el-card class="box-card" shadow="always">
      <el-form v-show="queryFormVisiable" :inline="true" size="mini" :model="queryForm" label-width="120px">
        <el-form-item label="创建用户：">
          <el-input v-model="queryForm.userId" placeholder="创建用户" />
        </el-form-item>
        <el-form-item label="工单类别：">
          <el-input v-model="queryForm.type" placeholder="工单类别" />
        </el-form-item>
        <el-form-item label="工单标题：">
          <el-input v-model="queryForm.title" placeholder="工单标题" />
        </el-form-item>
        <el-form-item label="联系方式：">
          <el-input v-model="queryForm.phone" placeholder="联系方式" />
        </el-form-item>
        <el-form-item label="联系邮箱：">
          <el-input v-model="queryForm.email" placeholder="联系邮箱" />
        </el-form-item>
        <el-form-item label="工单状态：">
          <el-input v-model="queryForm.status" placeholder="工单状态" />
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
        <span><i class="el-icon-caret-right" /> 工单记录</span>
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
        <el-table-column v-if="checkColumnDisplayed('userId', columnsData.columns)" prop="userId" label="创建用户" align="center" show-overflow-tooltip />
        <el-table-column v-if="checkColumnDisplayed('type', columnsData.columns)" prop="type" label="工单类别" align="center" show-overflow-tooltip />
        <el-table-column v-if="checkColumnDisplayed('title', columnsData.columns)" prop="title" label="工单标题" align="center" show-overflow-tooltip />
        <el-table-column v-if="checkColumnDisplayed('content', columnsData.columns)" prop="content" label="工单内容" align="center" show-overflow-tooltip />
        <el-table-column v-if="checkColumnDisplayed('phone', columnsData.columns)" prop="phone" label="联系方式" align="center" show-overflow-tooltip />
        <el-table-column v-if="checkColumnDisplayed('email', columnsData.columns)" prop="email" label="联系邮箱" align="center" show-overflow-tooltip />
        <el-table-column v-if="checkColumnDisplayed('status', columnsData.columns)" prop="status" label="工单状态" align="center" show-overflow-tooltip />
        <el-table-column v-if="checkColumnDisplayed('createTime', columnsData.columns)" prop="createTime" label="创建时间" align="center" show-overflow-tooltip />
        <el-table-column v-if="checkColumnDisplayed('handleRemark', columnsData.columns)" prop="handleRemark" label="处理备注" align="center" show-overflow-tooltip />
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
    <WorkOrderForm :id="editData.id" :title="editData.title" :visible.sync="editData.visiable" />
    <WorkOrderDetail :id="showData.id" :title="showData.title" :visible.sync="showData.visiable" />
    <select-columns :title="columnsData.title" :columns="columnsData.columns" :visible.sync="columnsData.visiable" />
  </div>
</template>

<script>
import workOrderApi from '@/api/module/work-order/work-order-api'
import WorkOrderForm from '@/views/module/work-order/work-order-form'
import WorkOrderDetail from '@/views/module/work-order/work-order-detail'
import SelectColumns from '@/components/SelectColumns'
export default {
  components: { SelectColumns, WorkOrderDetail, WorkOrderForm },
  data() {
    return {
      deleteLoading: false,
      queryFormVisiable: true,
      queryForm: {
        userId: null,
        type: null,
        title: null,
        phone: null,
        email: null,
        status: null
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
          { key: 'userId', label: '创建用户', _showed: true },
          { key: 'type', label: '工单类别', _showed: true },
          { key: 'title', label: '工单标题', _showed: true },
          { key: 'content', label: '工单内容', _showed: true },
          { key: 'phone', label: '联系方式', _showed: true },
          { key: 'email', label: '联系邮箱', _showed: true },
          { key: 'status', label: '工单状态', _showed: true },
          { key: 'createTime', label: '创建时间', _showed: true },
          { key: 'handleRemark', label: '处理备注', _showed: true }
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
      workOrderApi.getList(queryForm).then(response => {
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
      this.queryForm.id = null
      this.queryForm.userId = null
      this.queryForm.type = null
      this.queryForm.title = null
      this.queryForm.content = null
      this.queryForm.phone = null
      this.queryForm.email = null
      this.queryForm.status = null
      this.queryForm.handleRemark = null
      this.tableData.pageNumber = 1
      this.getList()
    },
    handleShow() {
      if (!this.selectData.current) {
        this.$notify.warning({ title: '警告', message: '请先选择一条数据' })
        return
      }
      this.showData.title = '查看${field.fieldRemark}'
      this.showData.id = this.selectData.current.id
      this.showData.visiable = true
    },
    handleCreate() {
      this.editData.title = '新增${field.fieldRemark}'
      this.editData.id = null
      this.editData.visiable = true
    },
    handleUpdate() {
      if (!this.selectData.current) {
        this.$notify.warning({ title: '警告', message: '请先选择一条数据' })
        return
      }
      this.editData.title = '修改${field.fieldRemark}'
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
        workOrderApi.remove(ids).then(response => {
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
