<template>
  <div
    v-loading="deleteLoading"
    style="margin: 10px"
    element-loading-text="删除中，请稍后..."
    element-loading-spinner="el-icon-loading"
  >
    <el-card class="box-card" shadow="always">
      <el-form v-show="queryFormVisiable" :inline="true" size="mini" :model="queryForm" label-width="100px">
        <el-form-item label="类别名称：">
          <el-input v-model="queryForm.name" placeholder="名称" />
        </el-form-item>
        <el-form-item label="类别编号：">
          <el-input v-model="queryForm.code" placeholder="编号" />
        </el-form-item>
        <el-form-item label="是否启用：">
          <el-select v-model="queryForm.status" placeholder="请选择">
            <el-option
              v-for="item in statusOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
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
        <span><i class="el-icon-caret-right" /> 商品类别记录</span>
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
        <el-table-column v-if="checkColumnDisplayed('name', columnsData.columns)" prop="name" label="类别名称" align="center" show-overflow-tooltip />
        <el-table-column v-if="checkColumnDisplayed('code', columnsData.columns)" prop="code" label="类别编号" align="center" show-overflow-tooltip />
        <el-table-column v-if="checkColumnDisplayed('icon', columnsData.columns)" prop="icon" label="类别图标" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-avatar shape="square" size="large" :src="imgUrlPrefix + scope.row.icon" @click.native="handleIconClick(scope.row.icon)" />
          </template>
        </el-table-column>
        <el-table-column v-if="checkColumnDisplayed('status', columnsData.columns)" prop="status" label="是否启用" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status === 1" size="mini" effect="plain" type="success"> <i class="el-icon-success" /> 启 用</el-tag>
            <el-tag v-if="scope.row.status === 0" size="mini" effect="plain" type="danger"> <i class="el-icon-error" /> 禁 用</el-tag>
          </template>
        </el-table-column>
        <el-table-column v-if="checkColumnDisplayed('description', columnsData.columns)" prop="description" label="描述" align="center" show-overflow-tooltip />
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

    <ProductCategoryForm :id="editData.id" :title="editData.title" :visible.sync="editData.visiable" />
    <ProductCategoryDetail :id="showData.id" :title="showData.title" :visible.sync="showData.visiable" />
    <select-columns :title="columnsData.title" :columns="columnsData.columns" :visible.sync="columnsData.visiable" />
    <el-dialog title="图标查看" :visible.sync="imgData.dialogVisible" class="jy-dialog" width="35%">
      <img width="100%" :src="imgData.dialogImageUrl" alt="">
    </el-dialog>
  </div>
</template>

<script>
import productCategoryApi from '@/api/module/product-category/product-category-api'
import ProductCategoryForm from '@/views/module/product-category/product-category-form'
import ProductCategoryDetail from '@/views/module/product-category/product-category-detail'
import SelectColumns from '@/components/SelectColumns'
export default {
  components: { SelectColumns, ProductCategoryDetail, ProductCategoryForm },
  data() {
    return {
      deleteLoading: false,
      queryFormVisiable: true,
      imgUrlPrefix: '/api/file-process/download/',
      queryForm: {
        name: null,
        code: null,
        status: null
      },
      statusOptions: [
        { value: '0', label: '禁用' },
        { value: '1', label: '启用' }
      ],
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
          { key: 'name', label: '类别名称', _showed: true },
          { key: 'code', label: '类别编号', _showed: true },
          { key: 'icon', label: '类别图标', _showed: true },
          { key: 'status', label: '是否启用', _showed: true },
          { key: 'description', label: '描述', _showed: true }
        ]
      },
      imgData: {
        dialogVisible: false,
        dialogImageUrl: null
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
      productCategoryApi.getList(queryForm).then(response => {
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
      this.queryForm.name = null
      this.queryForm.code = null
      this.queryForm.status = null
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
        productCategoryApi.remove(ids).then(response => {
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
    },
    handleIconClick(icon) {
      this.imgData.dialogVisible = true
      this.imgData.dialogImageUrl = this.imgUrlPrefix + icon
    }
  }
}
</script>
<style scoped>

</style>
