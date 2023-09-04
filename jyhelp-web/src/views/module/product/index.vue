<template>
  <div
    v-loading="deleteLoading"
    style="margin: 10px"
    element-loading-text="删除中，请稍后..."
    element-loading-spinner="el-icon-loading"
  >
    <el-card class="box-card" shadow="always">
      <el-form v-show="queryFormVisiable" :inline="true" size="mini" :model="queryForm" label-width="100px">
        <el-form-item label="商品名称：">
          <el-input v-model="queryForm.name" placeholder="商品名称" />
        </el-form-item>
        <el-form-item label="商品编码：">
          <el-input v-model="queryForm.code" placeholder="商品编码" />
        </el-form-item>
        <el-form-item label="商品类别：">
          <el-select v-model="queryForm.productCategoryId" placeholder="商品类别" style="width: 100%">
            <el-option v-for="item in productCategoryOptions" :key="item.code" :label="item.name" :value="item.code" />
          </el-select>
        </el-form-item>
        <el-form-item label="服务类别：">
          <el-select v-model="queryForm.serviceCategoryId" placeholder="服务类别" style="width: 100%">
            <el-option v-for="item in serviceCategoryOptions" :key="item.code" :label="item.name" :value="item.code" />
          </el-select>
        </el-form-item>
        <el-form-item label="是否热门：">
          <el-select v-model="queryForm.hot" placeholder="是否热门" style="width: 100%">
            <el-option v-for="item in hotOptions" :key="'hot_' + item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="上架状态：">
          <el-select v-model="queryForm.status" placeholder="上架状态" style="width: 100%">
            <el-option v-for="item in statusOptions" :key="'status_' + item.value" :label="item.label" :value="item.value" />
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
        <span><i class="el-icon-caret-right" /> 商品记录</span>
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
        <el-table-column v-if="checkColumnDisplayed('name', columnsData.columns)" prop="name" label="商品名称" align="center" show-overflow-tooltip />
        <el-table-column v-if="checkColumnDisplayed('code', columnsData.columns)" prop="code" label="商品编码" align="center" show-overflow-tooltip />
        <el-table-column v-if="checkColumnDisplayed('productCategoryId', columnsData.columns)" prop="productCategoryId" label="商品类别" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            {{ getNameByCode(productCategoryOptions, scope.row.productCategoryId) }}
          </template>
        </el-table-column>
        <el-table-column v-if="checkColumnDisplayed('serviceCategoryId', columnsData.columns)" prop="serviceCategoryId" label="服务类别" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            {{ getNameByCode(serviceCategoryOptions, scope.row.serviceCategoryId) }}
          </template>
        </el-table-column>
        <el-table-column v-if="checkColumnDisplayed('productImg', columnsData.columns)" prop="productImg" label="商品图片" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-avatar shape="square" size="large" :src="imgUrlPrefix + scope.row.productImg" @click.native="handleIconClick(scope.row.productImg)" />
          </template>
        </el-table-column>
        <el-table-column v-if="checkColumnDisplayed('stock', columnsData.columns)" prop="stock" label="商品库存" align="center" width="100" show-overflow-tooltip />
        <el-table-column v-if="checkColumnDisplayed('unit', columnsData.columns)" prop="unit" label="商品单位" align="center" width="100" show-overflow-tooltip />
        <el-table-column v-if="checkColumnDisplayed('initialPrice', columnsData.columns)" prop="initialPrice" label="原始价格" width="120" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            {{ (scope.row.initialPrice / 100).toFixed(2) }} 元
          </template>
        </el-table-column>
        <el-table-column v-if="checkColumnDisplayed('discount', columnsData.columns)" prop="discount" label="商品折扣" width="100" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            {{ (scope.row.discount * 10).toFixed(1) }}
          </template>
        </el-table-column>
        <el-table-column v-if="checkColumnDisplayed('currentPrice', columnsData.columns)" prop="currentPrice" label="当前价格" width="120" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            {{ (scope.row.currentPrice / 100).toFixed(2) }} 元
          </template>
        </el-table-column>
        <el-table-column v-if="checkColumnDisplayed('hot', columnsData.columns)" prop="hot" label="是否热门" width="100" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag v-if="scope.row.hot === 1" size="mini" effect="plain" type="success"> <i class="el-icon-success" /> 是</el-tag>
            <el-tag v-if="scope.row.hot === 0" size="mini" effect="plain" type="danger"> <i class="el-icon-error" /> 否</el-tag>
          </template>
        </el-table-column>
        <el-table-column v-if="checkColumnDisplayed('status', columnsData.columns)" prop="status" label="上架状态" width="100" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status === 1" size="mini" effect="plain" type="success"> <i class="el-icon-success" /> 上架中</el-tag>
            <el-tag v-if="scope.row.status === 0" size="mini" effect="plain" type="danger"> <i class="el-icon-error" /> 已下架</el-tag>
          </template>
        </el-table-column>
        <el-table-column v-if="checkColumnDisplayed('createTime', columnsData.columns)" prop="createTime" label="创建时间" width="180" align="center" />
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
    <ProductForm :id="editData.id" :title="editData.title" :visible.sync="editData.visiable" />
    <ProductDetail :id="showData.id" :title="showData.title" :visible.sync="showData.visiable" />
    <select-columns :title="columnsData.title" :columns="columnsData.columns" :visible.sync="columnsData.visiable" />
    <el-dialog title="图片查看" :visible.sync="imgData.dialogVisible" class="jy-dialog" width="35%">
      <img width="100%" :src="imgData.dialogImageUrl" alt="">
    </el-dialog>
  </div>
</template>

<script>
import productApi from '@/api/module/product/product-api'
import ProductForm from '@/views/module/product/product-form'
import ProductDetail from '@/views/module/product/product-detail'
import SelectColumns from '@/components/SelectColumns'
import serviceCategoryApi from '@/api/module/service-category/service-category-api'
import productCategoryApi from '@/api/module/product-category/product-category-api'
export default {
  components: { SelectColumns, ProductDetail, ProductForm },
  data() {
    return {
      deleteLoading: false,
      imgUrlPrefix: '/api/file-process/download/',
      queryFormVisiable: true,
      queryForm: {
        name: null,
        code: null,
        productCategoryId: null,
        serviceCategoryId: null,
        hot: null,
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
          { key: 'name', label: '商品名称', _showed: true },
          { key: 'code', label: '商品编码', _showed: true },
          { key: 'productCategoryId', label: '商品类别', _showed: true },
          { key: 'serviceCategoryId', label: '服务类别', _showed: true },
          { key: 'productImg', label: '商品图片', _showed: true },
          { key: 'initialPrice', label: '原始价格（元）', _showed: true },
          { key: 'stock', label: '商品库存', _showed: true },
          { key: 'unit', label: '商品单位', _showed: true },
          { key: 'discount', label: '商品折扣', _showed: true },
          { key: 'currentPrice', label: '当前价格（元）', _showed: true },
          { key: 'hot', label: '是否热门', _showed: true },
          { key: 'status', label: '上架状态', _showed: true },
          { key: 'createTime', label: '创建时间', _showed: true }
        ]
      },
      imgData: {
        dialogVisible: false,
        dialogImageUrl: null
      },
      serviceCategoryOptions: [],
      productCategoryOptions: [],
      statusOptions: [
        { value: '0', label: '下架' },
        { value: '1', label: '上架' }
      ],
      hotOptions: [
        { value: '0', label: '否' },
        { value: '1', label: '是' }
      ]
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
      productCategoryApi.list({ status: 1 }).then(res => {
        this.productCategoryOptions = []
        for (let i = 0; i < res.data.length; i++) {
          this.productCategoryOptions.push({ code: res.data[i].id, name: res.data[i].name })
        }
      })
    },
    getList() {
      this.tableData.loading = true
      const queryForm = { ...this.queryForm, pageNumber: this.tableData.pageNumber, pageSize: this.tableData.pageSize }
      productApi.getList(queryForm).then(response => {
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
      this.queryForm.productCategoryId = null
      this.queryForm.serviceCategoryId = null
      this.queryForm.hot = null
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
        productApi.remove(ids).then(response => {
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
    handleIconClick(imgId) {
      this.imgData.dialogVisible = true
      this.imgData.dialogImageUrl = this.imgUrlPrefix + imgId
    }
  }
}
</script>
<style scoped>

</style>
