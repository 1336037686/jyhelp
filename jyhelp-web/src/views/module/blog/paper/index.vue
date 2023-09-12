<template>
  <div
    v-loading="deleteLoading"
    style="margin: 10px"
    element-loading-text="删除中，请稍后..."
    element-loading-spinner="el-icon-loading"
  >
    <el-card class="box-card" shadow="always">
      <el-form v-show="queryFormVisiable" :inline="true" size="mini" :model="queryForm" label-width="100px">
        <el-form-item label="文章名称：">
          <el-input v-model="queryForm.title" placeholder="文章名称" />
        </el-form-item>
        <el-form-item label="类别：">
          <el-select v-model="queryForm.category" placeholder="类别" style="width: 100%">
            <el-option v-for="item in categoryOptions" :key="item.code" :label="item.name" :value="item.code" />
          </el-select>
        </el-form-item>
        <el-form-item label="标签：">
          <el-select v-model="queryForm.tag" placeholder="标签" style="width: 100%">
            <el-option v-for="item in tagOptions" :key="item.code" :label="item.name" :value="item.code" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态：">
          <el-select v-model="queryForm.status" placeholder="状态" style="width: 100%">
            <el-option v-for="item in blogStatusOptions" :key="item.code" :label="item.name" :value="item.code" />
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
        <span><i class="el-icon-caret-right" /> 博客文章记录</span>
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
        <el-table-column v-if="checkColumnDisplayed('title', columnsData.columns)" prop="title" label="文章" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-button type="text" @click="handleTitleClick(scope.row.content)"> {{ scope.row.title }} </el-button>
          </template>
        </el-table-column>
        <el-table-column v-if="checkColumnDisplayed('category', columnsData.columns)" prop="category" label="类别" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            {{ getNameByCode(categoryOptions, scope.row.category) }}
          </template>
        </el-table-column>
        <el-table-column v-if="checkColumnDisplayed('tag', columnsData.columns)" prop="tag" label="标签" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            {{ getNameByCode(tagOptions, scope.row.tag) }}
          </template>
        </el-table-column>
        <el-table-column v-if="checkColumnDisplayed('cover', columnsData.columns)" prop="cover" label="封面" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-avatar shape="square" size="large" :src="imgUrlPrefix + scope.row.cover" />
          </template>
        </el-table-column>
        <el-table-column v-if="checkColumnDisplayed('status', columnsData.columns)" prop="status" label="状态" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            {{ getNameByCode(blogStatusOptions, scope.row.status) }}
          </template>
        </el-table-column>
        <el-table-column v-if="checkColumnDisplayed('createTime', columnsData.columns)" prop="createTime" label="创建时间" align="center" show-overflow-tooltip />
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
    <BlogForm :id="editData.id" :title="editData.title" :visible.sync="editData.visiable" />
    <BlogDetail :id="showData.id" :title="showData.title" :visible.sync="showData.visiable" />
    <select-columns :title="columnsData.title" :columns="columnsData.columns" :visible.sync="columnsData.visiable" />
    <el-dialog title="文章查看" :visible.sync="blogData.dialogVisible" class="jy-dialog" width="60%">
      <div v-html="blogData.content" />
    </el-dialog>
  </div>
</template>

<script>
import tagApi from '@/api/module/tag/tag-api'
import categoryApi from '@/api/module/category/category-api'
import blogApi from '@/api/module/blog/blog-api'
import BlogForm from '@/views/module/blog/paper/blog-form'
import BlogDetail from '@/views/module/blog/paper/blog-detail'
import SelectColumns from '@/components/SelectColumns'
export default {
  components: { SelectColumns, BlogDetail, BlogForm },
  data() {
    return {
      deleteLoading: false,
      imgUrlPrefix: '/api/file-process/download/',
      queryFormVisiable: true,
      queryForm: {
        title: null,
        category: null,
        tag: null,
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
      blogData: {
        dialogVisible: false,
        content: null
      },
      columnsData: {
        visiable: false,
        columns: [
          { key: 'title', label: '文章', _showed: true },
          { key: 'category', label: '类别', _showed: true },
          { key: 'tag', label: '标签', _showed: true },
          { key: 'cover', label: '封面', _showed: true },
          { key: 'status', label: '状态', _showed: true },
          { key: 'createTime', label: '创建时间', _showed: true }
        ]
      },
      blogStatusOptions: [],
      tagOptions: [],
      categoryOptions: []
    }
  },
  created() {
    this.getDict()
    this.getList()
  },
  methods: {
    getDict() {
      this.getDictByCode('module_blog_status').then(res => {
        this.blogStatusOptions = res.data
      })
      tagApi.list().then(res => {
        this.tagOptions = []
        for (let i = 0; i < res.data.length; i++) {
          this.tagOptions.push({ name: res.data[i].name, code: res.data[i].id })
        }
      })
      categoryApi.list().then(res => {
        this.categoryOptions = []
        for (let i = 0; i < res.data.length; i++) {
          this.categoryOptions.push({ name: res.data[i].name, code: res.data[i].id })
        }
      })
    },
    getList() {
      this.tableData.loading = true
      const queryForm = { ...this.queryForm, pageNumber: this.tableData.pageNumber, pageSize: this.tableData.pageSize }
      blogApi.getList(queryForm).then(response => {
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
      this.queryForm.title = null
      this.queryForm.category = null
      this.queryForm.tag = null
      this.queryForm.content = null
      this.queryForm.status = null
      this.queryForm.author = null
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
        blogApi.remove(ids).then(response => {
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
    handleTitleClick(content) {
      this.blogData.dialogVisible = true
      this.blogData.content = content
    }
  }
}
</script>
<style scoped>

</style>
