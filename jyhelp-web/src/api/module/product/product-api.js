import request from '@/utils/request-api'

export default {
  getList(query) {
    return request({
      url: '/api/product/query',
      method: 'get',
      params: query
    })
  },

  getById(id) {
    return request({
      url: '/api/product/query/' + id,
      method: 'get'
    })
  },

  add(data, idempotentToken) {
    return request({
      url: '/api/product/create',
      method: 'post',
      params: { 'idempotent-token': idempotentToken },
      data
    })
  },

  update(data, idempotentToken) {
    return request({
      url: '/api/product/update',
      method: 'put',
      params: { 'idempotent-token': idempotentToken },
      data
    })
  },

  remove(data) {
    return request({
      url: '/api/product/remove',
      method: 'delete',
      data
    })
  }
}
