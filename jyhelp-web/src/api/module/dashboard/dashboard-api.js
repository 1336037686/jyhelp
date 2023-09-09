import request from '@/utils/request-api'

export default {
  getPanelGroup() {
    return request({
      url: '/api/dashboard/query/panel-group',
      method: 'get'
    })
  }
}
