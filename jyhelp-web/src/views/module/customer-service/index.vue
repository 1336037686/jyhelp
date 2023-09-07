<template>
  <div
    v-loading="deleteLoading"
    style="margin: 10px"
    element-loading-text="删除中，请稍后..."
    element-loading-spinner="el-icon-loading"
  >
    <el-card class="box-card" shadow="always">
      <el-form v-show="queryFormVisiable" :inline="true" size="mini" :model="queryForm" label-width="120px">
        <el-form-item label="服务项目编号：">
          <el-input v-model="queryForm.serviceCode" placeholder="服务项目编号" />
        </el-form-item>
        <el-form-item label="用户名：">
          <el-input v-model="queryForm.username" placeholder="用户名" />
        </el-form-item>
        <el-form-item label="服务类别：">
          <el-select v-model="queryForm.serviceCategoryId" placeholder="服务类别" style="width: 100%">
            <el-option v-for="item in serviceCategoryOptions" :key="item.code" :label="item.name" :value="item.code" />
          </el-select>
        </el-form-item>
        <el-form-item label="服务状态：">
          <el-select v-model="queryForm.serviceStatus" style="width: 100%">
            <el-option v-for="(item, index) in serviceStatusOptions" :key="'serviceStatus_' + index" :label="item.name" :value="item.code" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="handleQuery">查 询</el-button>
          <el-button type="info" icon="el-icon-circle-close" @click="handleReset">重 置</el-button>
        </el-form-item>
      </el-form>
      <div style="margin-top: 5px">
        <el-button type="primary" icon="el-icon-view" size="mini" @click="handleShow">查 看</el-button>
        <el-button type="warning" icon="el-icon-edit-outline" size="mini" @click="handleUpdate">修 改</el-button>
        <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleRemove">删 除</el-button>
      </div>
    </el-card>

    <el-card class="box-card" shadow="always" style="margin-top: 5px">
      <div slot="header" class="clearfix">
        <span><i class="el-icon-caret-right" /> 会员服务记录</span>
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
        <el-table-column v-if="checkColumnDisplayed('serviceCode', columnsData.columns)" prop="serviceCode" label="服务项目编号" align="center" show-overflow-tooltip />
        <el-table-column v-if="checkColumnDisplayed('username', columnsData.columns)" prop="username" label="用户名" align="center" show-overflow-tooltip />
        <el-table-column v-if="checkColumnDisplayed('nickname', columnsData.columns)" prop="nickname" label="昵称" align="center" show-overflow-tooltip />
        <el-table-column v-if="checkColumnDisplayed('serviceCategoryName', columnsData.columns)" prop="serviceCategoryName" label="服务类别" align="center" show-overflow-tooltip />
        <el-table-column v-if="checkColumnDisplayed('serviceStock', columnsData.columns)" prop="serviceStock" label="剩余服务库存" align="center" show-overflow-tooltip />
        <el-table-column v-if="checkColumnDisplayed('serviceStatus', columnsData.columns)" prop="serviceStatus" label="服务状态" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            {{ getNameByCode(serviceStatusOptions, scope.row.serviceStatus) }}
          </template>
        </el-table-column>
        <el-table-column v-if="checkColumnDisplayed('createTime', columnsData.columns)" prop="createTime" label="创建时间" width="180" align="center" />
        <el-table-column v-if="checkColumnDisplayed('sourceList', columnsData.columns)" prop="sourceList" label="来源明细" align="center" show-overflow-tooltip />
        <el-table-column v-if="checkColumnDisplayed('serviceList', columnsData.columns)" prop="serviceList" label="服务记录" align="center" show-overflow-tooltip />
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
    <CustomerServiceForm :id="editData.id" :title="editData.title" :visible.sync="editData.visiable" />
    <CustomerServiceDetail :id="showData.id" :title="showData.title" :visible.sync="showData.visiable" />
    <select-columns :title="columnsData.title" :columns="columnsData.columns" :visible.sync="columnsData.visiable" />
  </div>
</template>

<script>
import customerServiceApi from '@/api/module/customer-service/customer-service-api'
import CustomerServiceForm from '@/views/module/customer-service/customer-service-form'
import CustomerServiceDetail from '@/views/module/customer-service/customer-service-detail'
import SelectColumns from '@/components/SelectColumns'
import serviceCategoryApi from '@/api/module/service-category/service-category-api'
export default {
  components: { SelectColumns, CustomerServiceDetail, CustomerServiceForm },
  data() {
    return {
      deleteLoading: false,
      queryFormVisiable: true,
      queryForm: {
        serviceCode: null,
        username: null,
        serviceCategoryId: null,
        serviceStatus: null
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
          { key: 'serviceCode', label: '服务项目编号', _showed: true },
          { key: 'username', label: '用户名', _showed: true },
          { key: 'nickname', label: '昵称', _showed: true },
          { key: 'serviceCategoryName', label: '服务类别', _showed: true },
          { key: 'serviceStock', label: '剩余服务库存', _showed: true },
          { key: 'serviceStatus', label: '服务状态', _showed: true },
          { key: 'createTime', label: '创建时间', _showed: true },
          { key: 'sourceList', label: '来源明细', _showed: true },
          { key: 'serviceList', label: '服务记录', _showed: true }
        ]
      },
      serviceStatusOptions: [],
      serviceCategoryOptions: []
    }
  },
  created() {
    this.getDict()
    this.getList()
  },
  methods: {
    getDict() {
      serviceCategoryApi.list({ status: 1 }).then(res => {
        this.serviceCategoryOptions = []
        for (let i = 0; i < res.data.length; i++) {
          this.serviceCategoryOptions.push({ code: res.data[i].id, name: res.data[i].name })
        }
      })
      this.getDictByCode('module_service_status').then(res => {
        this.serviceStatusOptions = res.data
      })
    },
    getList() {
      this.tableData.loading = true
      const queryForm = { ...this.queryForm, pageNumber: this.tableData.pageNumber, pageSize: this.tableData.pageSize }
      customerServiceApi.getList(queryForm).then(response => {
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
      this.queryForm.serviceCode = null
      this.queryForm.userId = null
      this.queryForm.serviceCategoryId = null
      this.queryForm.serviceStock = null
      this.queryForm.serviceStatus = null
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
        customerServiceApi.remove(ids).then(response => {
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
