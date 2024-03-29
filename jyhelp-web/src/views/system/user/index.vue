<template>
  <div
    v-loading="deleteLoading"
    style="margin: 10px;"
    element-loading-text="删除中，请稍后..."
    element-loading-spinner="el-icon-loading"
  >
    <el-row style="margin-top: 10px">
      <!-- 左 -->
      <el-col :span="5" style="padding-right: 15px">
        <el-card
          v-loading="deptTreeData.loading"
          class="box-card"
          shadow="always"
          style="margin-top: 5px"
          element-loading-text="加载中，请稍后..."
          element-loading-spinner="el-icon-loading"
        >
          <div slot="header" class="clearfix">
            <span><i class="el-icon-caret-right" /> 部门列表</span>
          </div>
          <el-tree
            ref="deptTree"
            :data="deptTreeData.records"
            node-key="id"
            empty-text="暂无数据"
            highlight-current
            :check-strictly="true"
            :props="{children: 'children', label: 'name' }"
            @node-click="clickDeptTreeNode"
          >
            <span slot-scope="{ node, data }" class="custom-tree-node">
              <span v-if="data.type === 'showAll'" style="font-size: 14px;color: #4A9FF9"><b>{{ node.label }}</b></span>
              <span v-else style="font-size: 14px;">{{ node.label }}</span>
            </span>
          </el-tree>
        </el-card>
      </el-col>
      <!-- 右 -->
      <el-col :span="19" style="padding-right: 15px">
        <el-card class="box-card" shadow="always" style="margin-top: 5px">
          <div slot="header" class="clearfix">
            <span><i class="el-icon-caret-right" /> 用户列表</span>
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

            <div style="margin-top: 5px">
              <el-button v-permission="['user:show']" type="primary" size="mini" icon="el-icon-view" @click="handleShow">查 看</el-button>
              <el-button v-permission="['user:add']" type="success" size="mini" icon="el-icon-plus" @click="handleCreate">新 增</el-button>
              <el-button v-permission="['user:role']" type="success" size="mini" icon="el-icon-user-solid" @click="handleRole">角色分配</el-button>
              <el-button v-permission="['user:update']" type="warning" size="mini" icon="el-icon-edit-outline" @click="handleUpdate">修 改</el-button>
              <el-button v-permission="['user:resetpwd']" type="warning" size="mini" icon="el-icon-s-tools" @click="handleUpdatePassword">重置密码</el-button>
              <el-button v-permission="['user:delete']" type="danger" size="mini" icon="el-icon-delete" @click="handleRemove">删 除</el-button>
            </div>
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
            <el-table-column type="index" width="55" label="序号" align="center" fixed />
            <el-table-column v-if="checkColumnDisplayed('username', columnsData.columns)" prop="username" label="用户名" align="center" width="180" fixed show-overflow-tooltip />
            <el-table-column v-if="checkColumnDisplayed('nickname', columnsData.columns)" prop="nickname" label="昵称" align="center" width="180" fixed show-overflow-tooltip />
            <el-table-column v-if="checkColumnDisplayed('phone', columnsData.columns)" prop="phone" label="电话" align="center" fixed width="180" show-overflow-tooltip />
            <el-table-column v-if="checkColumnDisplayed('roles', columnsData.columns)" prop="roles" label="当前角色" width="180" align="center">
              <template slot-scope="scope">
                {{ scope.row.roleNames.join('、') }}
              </template>
            </el-table-column>
            <el-table-column v-if="checkColumnDisplayed('departmentName', columnsData.columns)" prop="departmentName" label="所属部门" width="180" align="center" show-overflow-tooltip />
            <el-table-column v-if="checkColumnDisplayed('postName', columnsData.columns)" prop="postName" label="所属岗位" align="center" width="180" show-overflow-tooltip />
            <el-table-column v-if="checkColumnDisplayed('type', columnsData.columns)" prop="type" label="账号类型" width="100" align="center">
              <template slot-scope="scope">
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

    <user-form :id="editData.id" :title="editData.title" :visible.sync="editData.visiable" />
    <user-detail :id="showData.id" :title="showData.title" :visible.sync="showData.visiable" />
    <user-role :id="roleEditData.id" :name="roleEditData.name" :title="roleEditData.title" :visible.sync="roleEditData.visiable" />
    <user-password :id="passwordEditData.id" :name="passwordEditData.name" :title="passwordEditData.title" :visible.sync="passwordEditData.visiable" />
    <select-columns :title="columnsData.title" :columns="columnsData.columns" :visible.sync="columnsData.visiable" />
  </div>
</template>

<script>
import deptApi from '@/api/system/department/jy-department'
import userApi from '@/api/system/user/jy-user'
import UserDetail from './user-detail'
import UserForm from './user-form'
import UserRole from './user-role'
import UserPassword from './user-password'
import SelectColumns from '@/components/SelectColumns'
export default {
  components: { UserPassword, UserRole, UserForm, UserDetail, SelectColumns },
  data() {
    return {
      queryFormVisiable: true,
      deleteLoading: false,
      queryForm: {
        username: '',
        nickname: '',
        phone: '',
        excludeRoles: 'member',
        department: null
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
      roleEditData: {
        visiable: false,
        title: '',
        name: '',
        id: null
      },
      passwordEditData: {
        visiable: false,
        title: '',
        name: '',
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
          { key: 'username', label: '用户名', _showed: true },
          { key: 'nickname', label: '昵称', _showed: true },
          { key: 'phone', label: '电话', _showed: true },
          { key: 'roles', label: '当前角色', _showed: true },
          { key: 'departmentName', label: '所属部门', _showed: true },
          { key: 'postName', label: '所属岗位', _showed: true },
          { key: 'type', label: '用户类型', _showed: true },
          { key: 'status', label: '用户状态', _showed: true },
          { key: 'lastLoginTime', label: '最后一次登陆时间', _showed: true },
          { key: 'lastLoginIp', label: '最后一次登陆IP', _showed: true },
          { key: 'createTime', label: '创建时间', _showed: true }
        ]
      },
      deptTreeData: {
        loading: false,
        records: [
          {
            id: null,
            name: '默认展示',
            type: 'showAll'
          }
        ]
      }
    }
  },
  created() {
    this.getDeptTree()
  },
  methods: {
    getDeptTree() {
      this.deptTreeData.loading = true
      deptApi.layer({ status: 1 }).then(res => {
        const datas = [{ id: null, name: '默认展示', type: 'showAll' }]
        if (res.data && res.data.length > 0) for (let i = 0; i < res.data.length; i++) datas.push(res.data[i])
        this.deptTreeData.records = datas
        this.deptTreeData.loading = false
        // 默认加载全部
        this.clickDeptTreeNode(datas[0])
      })
    },
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
    clickDeptTreeNode(data) {
      this.queryForm.department = data.id
      this.handleReset()
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
    handleShow() {
      if (!this.selectData.current) {
        this.$notify.warning({ title: '警告', message: '请先选择一条数据' })
        return
      }
      this.showData.title = '查看用户'
      this.showData.id = this.selectData.current.id
      this.showData.visiable = true
    },
    handleCreate() {
      this.editData.title = '新增用户'
      this.editData.id = null
      this.editData.visiable = true
    },
    handleRole() {
      if (!this.selectData.current) {
        this.$notify.warning({ title: '警告', message: '请先选择一条数据' })
        return
      }
      this.roleEditData.title = '角色分配'
      this.roleEditData.id = this.selectData.current.id
      this.roleEditData.name = this.selectData.current.username
      this.roleEditData.visiable = true
    },
    handleUpdate() {
      if (!this.selectData.current) {
        this.$notify.warning({ title: '警告', message: '请先选择一条数据' })
        return
      }
      this.editData.title = '修改用户'
      this.editData.id = this.selectData.current.id
      this.editData.visiable = true
    },
    handleUpdatePassword() {
      if (!this.selectData.current) {
        this.$notify.warning({ title: '警告', message: '请先选择一条数据' })
        return
      }

      this.passwordEditData.title = '重置密码'
      this.passwordEditData.id = this.selectData.current.id
      this.passwordEditData.name = this.selectData.current.username
      this.passwordEditData.visiable = true
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
        userApi.remove(ids).then(response => {
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
