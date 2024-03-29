import request from '@/utils/request-api'

export default {
  getList(query) {
    return request({
      url: '/api/product-category/query',
      method: 'get',
      params: query
    })
  },

  getById(id) {
    return request({
      url: '/api/product-category/query/' + id,
      method: 'get'
    })
  },

  list(query) {
    return request({
      url: '/api/product-category/list',
      method: 'get',
      params: query
    })
  },

  add(data, idempotentToken) {
    return request({
      url: '/api/product-category/create',
      method: 'post',
      params: { 'idempotent-token': idempotentToken },
      data
    })
  },

  update(data, idempotentToken) {
    return request({
      url: '/api/product-category/update',
      method: 'put',
      params: { 'idempotent-token': idempotentToken },
      data
    })
  },

  remove(data) {
    return request({
      url: '/api/product-category/remove',
      method: 'delete',
      data
    })
  }
}
