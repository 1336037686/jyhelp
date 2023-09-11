<template>
  <div
    v-loading="deleteLoading"
    style="margin: 10px"
    element-loading-text="删除中，请稍后..."
    element-loading-spinner="el-icon-loading"
  >
    <el-card class="box-card" shadow="always">
      <el-form v-show="queryFormVisiable" :inline="true" size="mini" :model="queryForm" label-width="100px">
        <el-form-item label="订单编号：">
          <el-input v-model="queryForm.orderCode" placeholder="订单编号" />
        </el-form-item>
        <el-form-item label="用户账号：">
          <el-input v-model="queryForm.username" placeholder="用户账号" />
        </el-form-item>
        <el-form-item label="付款时间：">
          <el-input v-model="queryForm.paymentTime" placeholder="付款时间" />
        </el-form-item>
        <el-form-item label="付款方式：">
          <el-select v-model="queryForm.peymentMethod" style="width: 90%">
            <el-option v-for="item in orderPaymethodOptions" :key="'type_' + item.id" :label="item.name" :value="item.code" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="handleQuery">查 询</el-button>
          <el-button type="info" icon="el-icon-circle-close" @click="handleReset">重 置</el-button>
        </el-form-item>
      </el-form>
      <div style="margin-top: 5px">
        <el-button type="primary" icon="el-icon-view" size="mini" @click="handleShow">查 看</el-button>
      </div>
    </el-card>

    <el-card class="box-card" shadow="always" style="margin-top: 5px">
      <div slot="header" class="clearfix">
        <span><i class="el-icon-caret-right" /> 用户订单记录</span>
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
        <el-table-column v-if="checkColumnDisplayed('orderCode', columnsData.columns)" prop="orderCode" label="订单编号" align="center" show-overflow-tooltip />
        <el-table-column v-if="checkColumnDisplayed('productName', columnsData.columns)" prop="productName" label="商品名称" align="center" show-overflow-tooltip />
        <el-table-column v-if="checkColumnDisplayed('quantity', columnsData.columns)" prop="quantity" label="购买数量" align="center" show-overflow-tooltip />
        <el-table-column v-if="checkColumnDisplayed('username', columnsData.columns)" prop="username" label="购买用户账号" align="center" show-overflow-tooltip />
        <el-table-column v-if="checkColumnDisplayed('nickname', columnsData.columns)" prop="nickname" label="购买用户昵称" align="center" show-overflow-tooltip />
        <el-table-column v-if="checkColumnDisplayed('totalAmount', columnsData.columns)" prop="totalAmount" label="所需金额" align="center" show-overflow-tooltip />
        <el-table-column v-if="checkColumnDisplayed('finalTotalAmount', columnsData.columns)" prop="finalTotalAmount" label="实际金额" align="center" show-overflow-tooltip />
        <el-table-column v-if="checkColumnDisplayed('orderStatus', columnsData.columns)" prop="orderStatus" label="订单状态" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag v-if="scope.row.orderStatus === 'paid'" size="mini" effect="plain" type="success"> <i class="el-icon-success" />{{ getNameByCode(orderStatusOptions, scope.row.orderStatus) }}</el-tag>
            <el-tag v-if="scope.row.orderStatus === 'unpaid'" size="mini" effect="plain" type="warning"> <i class="el-icon-remove" />{{ getNameByCode(orderStatusOptions, scope.row.orderStatus) }}</el-tag>
            <el-tag v-if="scope.row.orderStatus === 'canceled'" size="mini" effect="plain" type="danger"> <i class="el-icon-error" />{{ getNameByCode(orderStatusOptions, scope.row.orderStatus) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column v-if="checkColumnDisplayed('orderTime', columnsData.columns)" prop="orderTime" label="下单时间" align="center" show-overflow-tooltip />
        <el-table-column v-if="checkColumnDisplayed('paymentTime', columnsData.columns)" prop="paymentTime" label="付款时间" align="center" show-overflow-tooltip />
        <el-table-column v-if="checkColumnDisplayed('peymentMethod', columnsData.columns)" prop="peymentMethod" label="付款方式" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            {{ getNameByCode(orderPaymethodOptions, scope.row.peymentMethod) }}
          </template>
        </el-table-column>
        <el-table-column v-if="checkColumnDisplayed('remark', columnsData.columns)" prop="remark" label="备注" align="center" show-overflow-tooltip />
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
    <OrderDetail :id="showData.id" :title="showData.title" :visible.sync="showData.visiable" />
    <select-columns :title="columnsData.title" :columns="columnsData.columns" :visible.sync="columnsData.visiable" />
  </div>
</template>

<script>
import orderApi from '@/api/module/order/order-api'
import OrderDetail from '@/views/module/order/order-detail'
import SelectColumns from '@/components/SelectColumns'
export default {
  components: { SelectColumns, OrderDetail },
  data() {
    return {
      deleteLoading: false,
      queryFormVisiable: true,
      queryForm: {
        id: null,
        orderCode: null,
        productId: null,
        quantity: null,
        userId: null,
        totalAmount: null,
        finalTotalAmount: null,
        orderStatus: 'paid',
        orderTime: null,
        paymentTime: null,
        peymentMethod: null,
        remark: null
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
          { key: 'orderCode', label: '订单编号', _showed: true },
          { key: 'productName', label: '商品名称', _showed: true },
          { key: 'quantity', label: '购买数量', _showed: true },
          { key: 'username', label: '购买用户账号', _showed: true },
          { key: 'nickname', label: '购买用户昵称', _showed: true },
          { key: 'totalAmount', label: '所需金额', _showed: true },
          { key: 'finalTotalAmount', label: '实际金额', _showed: true },
          { key: 'orderStatus', label: '订单状态', _showed: true },
          { key: 'orderTime', label: '下单时间', _showed: true },
          { key: 'paymentTime', label: '付款时间', _showed: true },
          { key: 'peymentMethod', label: '付款方式', _showed: true },
          { key: 'remark', label: '备注', _showed: true }
        ]
      },
      orderStatusOptions: [],
      orderPaymethodOptions: []
    }
  },
  created() {
    this.getDict()
    this.getList()
  },
  methods: {
    getDict() {
      this.getDictByCode('module_order_status').then(res => {
        this.orderStatusOptions = res.data
      })
      this.getDictByCode('module_order_paymethod').then(res => {
        this.orderPaymethodOptions = res.data
      })
    },
    getList() {
      this.tableData.loading = true
      const queryForm = { ...this.queryForm, pageNumber: this.tableData.pageNumber, pageSize: this.tableData.pageSize }
      orderApi.getList(queryForm).then(response => {
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
      this.queryForm.orderCode = null
      this.queryForm.productId = null
      this.queryForm.quantity = null
      this.queryForm.userId = null
      this.queryForm.totalAmount = null
      this.queryForm.finalTotalAmount = null
      this.queryForm.orderStatus = 'paid'
      this.queryForm.orderTime = null
      this.queryForm.paymentTime = null
      this.queryForm.peymentMethod = null
      this.queryForm.remark = null
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
        orderApi.remove(ids).then(response => {
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
