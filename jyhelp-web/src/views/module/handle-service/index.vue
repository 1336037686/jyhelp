<template>
  <div
    v-loading="deleteLoading"
    style="margin: 10px"
    element-loading-text="删除中，请稍后..."
    element-loading-spinner="el-icon-loading"
  >
    <el-card class="box-card" shadow="always">
      <el-form v-show="queryFormVisiable" :inline="true" size="mini" :model="queryForm" label-width="150px">
        <el-form-item label="服务记录编号：">
          <el-input v-model="queryForm.handleCode" placeholder="服务记录编号" />
        </el-form-item>
        <el-form-item label="会员服务编号：">
          <el-input v-model="queryForm.serviceCode" placeholder="会员服务编号" />
        </el-form-item>
        <el-form-item label="服务人员账号：">
          <el-input v-model="queryForm.handleUserName" placeholder="服务人员账号" />
        </el-form-item>
        <el-form-item label="服务人员名称：">
          <el-input v-model="queryForm.handleUserNickname" placeholder="服务人员名称" />
        </el-form-item>
        <el-form-item label="服务人员账号：">
          <el-input v-model="queryForm.username" placeholder="服务人员账号" />
        </el-form-item>
        <el-form-item label="服务人员名称：">
          <el-input v-model="queryForm.nickname" placeholder="服务人员名称" />
        </el-form-item>
        <el-form-item label="服务状态：">
          <el-select v-model="queryForm.handleStatus" style="width: 90%">
            <el-option v-for="(item, index) in handleServiceStatusOptions" :key="'handleStatus_' + index" :label="item.name" :value="item.code" />
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
        <el-table-column type="index" width="80" label="序号" fixed align="center" />
        <el-table-column v-if="checkColumnDisplayed('handleCode', columnsData.columns)" prop="handleCode" fixed label="服务处理编号" width="180" align="center" show-overflow-tooltip />
        <el-table-column v-if="checkColumnDisplayed('serviceCode', columnsData.columns)" prop="serviceCode" fixed label="会员服务编号" width="180" align="center" show-overflow-tooltip />
        <el-table-column v-if="checkColumnDisplayed('handleTime', columnsData.columns)" prop="handleTime" label="服务时间" width="180" align="center" show-overflow-tooltip />
        <el-table-column v-if="checkColumnDisplayed('handleUserName', columnsData.columns)" prop="handleUserName" label="服务人员账号" width="180" align="center" show-overflow-tooltip />
        <el-table-column v-if="checkColumnDisplayed('handleUserNickname', columnsData.columns)" prop="handleUserNickname" label="服务人员昵称" width="180" align="center" show-overflow-tooltip />
        <el-table-column v-if="checkColumnDisplayed('username', columnsData.columns)" prop="username" label="会员账号" width="180" align="center" show-overflow-tooltip />
        <el-table-column v-if="checkColumnDisplayed('nickname', columnsData.columns)" prop="nickname" label="会员昵称" width="180" align="center" show-overflow-tooltip />
        <el-table-column v-if="checkColumnDisplayed('handleRemark', columnsData.columns)" prop="handleRemark" label="服务记录" width="180" align="center" show-overflow-tooltip />
        <el-table-column v-if="checkColumnDisplayed('attachment', columnsData.columns)" prop="attachment" label="服务附件" width="100" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-button type="text" @click="handleShowServiceAttachment(scope.row.attachment)">查看</el-button>
          </template>
        </el-table-column>
        <el-table-column v-if="checkColumnDisplayed('createTime', columnsData.columns)" prop="createTime" label="创建时间" width="180" align="center" />
        <el-table-column v-if="checkColumnDisplayed('userScore', columnsData.columns)" prop="userScore" label="用户评分" width="180" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-rate v-model="scope.row.userScore" disabled />
          </template>
        </el-table-column>
        <el-table-column v-if="checkColumnDisplayed('userFeedback', columnsData.columns)" prop="userFeedback" label="用户反馈" width="180" align="center" show-overflow-tooltip />
        <el-table-column v-if="checkColumnDisplayed('handleStatus', columnsData.columns)" prop="handleStatus" label="服务状态" fixed="right" width="150" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag v-if="scope.row.handleStatus === 'await'" size="mini" effect="plain" type="warning"> <i class="el-icon-success" /> {{ getNameByCode(handleServiceStatusOptions, scope.row.handleStatus) }}</el-tag>
            <el-tag v-if="scope.row.handleStatus === 'completed'" size="mini" effect="plain" type="success"> <i class="el-icon-error" /> {{ getNameByCode(handleServiceStatusOptions, scope.row.handleStatus) }}</el-tag>
            <el-tag v-if="scope.row.handleStatus === 'reject'" size="mini" effect="plain" type="danger"> <i class="el-icon-error" /> {{ getNameByCode(handleServiceStatusOptions, scope.row.handleStatus) }}</el-tag>
          </template>
        </el-table-column>
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
    <el-dialog title="图片查看" :visible.sync="imgData.dialogVisible" class="jy-dialog" width="40%">
      <el-carousel :interval="5000" arrow="always" height="400px">
        <el-carousel-item v-for="(item, index) in imgData.dialogImageUrls" :key="'img_' + index">
          <img height="400px" :src="item" alt="">
        </el-carousel-item>
      </el-carousel>
    </el-dialog>
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
        serviceCode: null,
        handleTime: null,
        handleUserName: null,
        handleUserNickname: null,
        username: null,
        nickname: null,
        handleStatus: null
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
          { key: 'handleCode', label: '服务处理编号', _showed: true },
          { key: 'serviceCode', label: '会员服务编号', _showed: true },
          { key: 'handleTime', label: '服务时间', _showed: true },
          { key: 'handleUserName', label: '服务人员账号', _showed: true },
          { key: 'handleUserNickname', label: '服务人员昵称', _showed: true },
          { key: 'username', label: '会员账号', _showed: true },
          { key: 'nickname', label: '会员昵称', _showed: true },
          { key: 'handleRemark', label: '服务记录', _showed: true },
          { key: 'attachment', label: '服务附件', _showed: true },
          { key: 'userScore', label: '用户评分', _showed: true },
          { key: 'userFeedback', label: '用户反馈', _showed: true },
          { key: 'createTime', label: '创建时间', _showed: true },
          { key: 'handleStatus', label: '服务状态', _showed: true }
        ]
      },
      imgUrlPrefix: '/api/file-process/download/',
      imgData: {
        dialogVisible: false,
        dialogImageUrls: []
      },
      handleServiceStatusOptions: []
    }
  },
  created() {
    this.getDict()
    this.getList()
  },
  methods: {
    getDict() {
      this.getDictByCode('module_handle_service_status').then(res => {
        this.handleServiceStatusOptions = res.data
      })
    },
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
      this.queryForm.serviceCode = null
      this.queryForm.handleTime = null
      this.queryForm.handleUserName = null
      this.queryForm.handleUserNickname = null
      this.queryForm.username = null
      this.queryForm.nickname = null
      this.queryForm.handleStatus = null
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
    handleShowServiceAttachment(attachment) {
      this.imgData.dialogVisible = true
      if (attachment === null || attachment === '') this.imgData.dialogImageUrl = []
      else this.imgData.dialogImageUrls = attachment.split(',').map(x => this.imgUrlPrefix + x)
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
