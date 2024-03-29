import request from '@/utils/request-api'

export default {
  getList(query) {
    return request({
      url: '/api/category/query',
      method: 'get',
      params: query
    })
  },

  getById(id) {
    return request({
      url: '/api/category/query/' + id,
      method: 'get'
    })
  },

  list() {
    return request({
      url: '/api/category/list',
      method: 'get'
    })
  },

  add(data) {
    return request({
      url: '/api/category/create',
      method: 'post',
      data
    })
  },

  update(data) {
    return request({
      url: '/api/category/update',
      method: 'put',
      data
    })
  },

  remove(data) {
    return request({
      url: '/api/category/remove',
      method: 'delete',
      data
    })
  },
  dataExport(query) {
    return request({
      url: '/api/category/data-export',
      method: 'get',
      responseType: 'blob',
      params: query
    })
  }
}
