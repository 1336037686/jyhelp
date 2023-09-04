import request from '@/utils/request-api'

export default {
  getList(query) {
    return request({
      url: '/api/service-category/query',
      method: 'get',
      params: query
    })
  },

  getById(id) {
    return request({
      url: '/api/service-category/query/' + id,
      method: 'get'
    })
  },

  list(query) {
    return request({
      url: '/api/service-category/list',
      method: 'get',
      params: query
    })
  },

  add(data, idempotentToken) {
    return request({
      url: '/api/service-category/create',
      method: 'post',
      params: { 'idempotent-token': idempotentToken },
      data
    })
  },

  update(data, idempotentToken) {
    return request({
      url: '/api/service-category/update',
      method: 'put',
      params: { 'idempotent-token': idempotentToken },
      data
    })
  },

  remove(data) {
    return request({
      url: '/api/service-category/remove',
      method: 'delete',
      data
    })
  }
}
