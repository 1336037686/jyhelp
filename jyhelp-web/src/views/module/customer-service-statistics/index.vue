<template>
  <div
    v-loading="deleteLoading"
    style="margin: 10px;"
    element-loading-text="删除中，请稍后..."
    element-loading-spinner="el-icon-loading"
  >
    <el-row style="margin-top: 10px">
      <!-- 右 -->
      <el-col :span="24" style="padding-right: 15px">
        <el-card class="box-card" shadow="always" style="margin-top: 5px">
          <div slot="header" class="clearfix">
            <span><i class="el-icon-caret-right" /> 服务人员列表</span>
            <el-row style="float: right">
              <el-button icon="el-icon-search" circle size="mini" @click="() => this.queryFormVisiable = !this.queryFormVisiable" />
              <el-button icon="el-icon-refresh" circle size="mini" @click="getList()" />
              <el-button icon="el-icon-s-grid" circle size="mini" @click="selectColumns()" />
            </el-row>
          </div>
          <div>
            <el-form v-show="queryFormVisiable" :inline="true" :model="queryForm" size="mini" label-width="80px">
              <el-form-item label="用户名：">
                <el-input v-model="queryForm.username" placeholder="用户名" />
              </el-form-item>
              <el-form-item label="昵称：">
                <el-input v-model="queryForm.nickname" placeholder="昵称" />
              </el-form-item>
              <el-form-item label="电话：">
                <el-input v-model="queryForm.phone" placeholder="电话" />
              </el-form-item>
              <el-form-item>
                <el-button type="primary" icon="el-icon-search" @click="handleQuery">查 询</el-button>
                <el-button type="info" icon="el-icon-circle-close" @click="handleReset">重 置</el-button>
              </el-form-item>
            </el-form>
            <div style="margin-top: 5px" />
          </div>
          <el-table
            ref="table"
            v-loading="tableData.loading"
            element-loading-text="加载中，请稍后..."
            element-loading-spinner="el-icon-loading"
            :data="tableData.records"
            highlight-current-row
            style="width: 100%;margin-top: 10px"
            empty-text="暂无数据"
            :header-cell-style="{background:'#F5F7FA', color: '#303133', fontWeight: 700}"
            @row-click="handleTableRowClick"
            @select="handleTableRowSelect"
          >
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column type="index" width="55" label="序号" align="center" />
            <el-table-column v-if="checkColumnDisplayed('username', columnsData.columns)" prop="username" label="用户名" align="center" show-overflow-tooltip />
            <el-table-column v-if="checkColumnDisplayed('nickname', columnsData.columns)" prop="nickname" label="昵称" align="center" show-overflow-tooltip />
            <el-table-column v-if="checkColumnDisplayed('phone', columnsData.columns)" prop="phone" label="电话" align="center" width="180" show-overflow-tooltip />
            <el-table-column v-if="checkColumnDisplayed('roles', columnsData.columns)" prop="roles" label="当前角色" width="180" align="center">
              <template slot-scope="scope">
                {{ scope.row.roleNames.join('、') }}
              </template>
            </el-table-column>
            <el-table-column v-if="checkColumnDisplayed('type', columnsData.columns)" prop="type" label="账号类型" width="100" align="center">
              <template slot-scope="scope">
                <el-tag v-if="scope.row.type === 3" size="mini" effect="plain" type="warning"> 服务人员 </el-tag>
                <el-tag v-if="scope.row.type === 2" size="mini" effect="plain" type="warning"> 系统会员 </el-tag>
                <el-tag v-if="scope.row.type === 1" size="mini" effect="plain"> 管理员 </el-tag>
                <el-tag v-if="scope.row.type === 0" size="mini" effect="plain" type="success"> 普通用户 </el-tag>
              </template>
            </el-table-column>
            <el-table-column v-if="checkColumnDisplayed('status', columnsData.columns)" prop="status" label="账号状态" width="100" align="center">
              <template slot-scope="scope">
                <el-tag v-if="scope.row.status === 1" size="mini" effect="plain" type="success"> 启 用 </el-tag>
                <el-tag v-if="scope.row.status === 0" size="mini" effect="plain" type="danger"> 禁 用 </el-tag>
              </template>
            </el-table-column>
            <el-table-column v-if="checkColumnDisplayed('lastLoginTime', columnsData.columns)" prop="lastLoginTime" label="最后一次登陆时间" width="180" align="center" />
            <el-table-column v-if="checkColumnDisplayed('lastLoginIp', columnsData.columns)" prop="lastLoginIp" label="最后一次登陆IP" width="180" align="center" />
            <el-table-column v-if="checkColumnDisplayed('createTime', columnsData.columns)" prop="createTime" label="创建时间" width="200" align="center" />
          </el-table>
          <div style="text-align: center;margin-top: 10px">
            <el-pagination
              v-model="tableData.pageNumber"
              background
              layout="total, prev, pager, next"
              :page-size="tableData.pageSize"
              :hide-on-single-page="true"
              :total="tableData.total"
              @current-change="handleChangePage"
            />
          </div>
        </el-card>
      </el-col>
    </el-row>

    <select-columns :title="columnsData.title" :columns="columnsData.columns" :visible.sync="columnsData.visiable" />
  </div>
</template>

<script>
import userApi from '@/api/system/user/jy-user'
import SelectColumns from '@/components/SelectColumns'
export default {
  components: { SelectColumns },
  data() {
    return {
      queryFormVisiable: true,
      deleteLoading: false,
      queryForm: {
        username: '',
        nickname: '',
        phone: '',
        roles: 'waiter'
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
      selectData: {
        current: null,
        record: []
      },
      columnsData: {
        visiable: false,
        columns: [
          { key: 'username', label: '用户名', _showed: true },
          { key: 'nickname', label: '昵称', _showed: true },
          { key: 'phone', label: '电话', _showed: true },
          { key: 'roles', label: '当前角色', _showed: true },
          { key: 'type', label: '用户类型', _showed: true },
          { key: 'status', label: '用户状态', _showed: true },
          { key: 'lastLoginTime', label: '最后一次登陆时间', _showed: true },
          { key: 'lastLoginIp', label: '最后一次登陆IP', _showed: true },
          { key: 'createTime', label: '创建时间', _showed: true }
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
      userApi.getList(queryForm).then(response => {
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
      this.queryForm.username = ''
      this.queryForm.nickname = ''
      this.queryForm.phone = ''
      this.tableData.pageNumber = 1
      this.getList()
    },
    handleTableRowClick(row, column, event) {
      this.selectData.current = row
      this.$refs.table.toggleRowSelection(row)
    },
    handleTableRowSelect(selection, row) {
      this.selectData.current = row
      this.$refs.table.setCurrentRow(row)
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
