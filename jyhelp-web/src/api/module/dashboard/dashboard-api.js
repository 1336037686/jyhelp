import request from '@/utils/request-api'

export default {
  // 获取首页面板统计数据
  getPanelGroup() {
    return request({
      url: '/api/dashboard/query/panel-group',
      method: 'get'
    })
  },

  // 获取首页订单营收统计数据
  getOrderChart() {
    return request({
      url: '/api/dashboard/query/order-chart',
      method: 'get'
    })
  },

  // 获取首页会员统计数据
  getMemberChart() {
    return request({
      url: '/api/dashboard/query/member-chart',
      method: 'get'
    })
  }

}
