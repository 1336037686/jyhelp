import request from '@/utils/request-api'

export default {
  getList(query) {
    return request({
      url: '/api/blog/query',
      method: 'get',
      params: query
    })
  },

  getById(id) {
    return request({
      url: '/api/blog/query/' + id,
      method: 'get'
    })
  },

  add(data, idempotentToken) {
    return request({
      url: '/api/blog/create',
      method: 'post',
      params: { 'idempotent-token': idempotentToken },
      data
    })
  },

  update(data, idempotentToken) {
    return request({
      url: '/api/blog/update',
      method: 'put',
      params: { 'idempotent-token': idempotentToken },
      data
    })
  },

  remove(data) {
    return request({
      url: '/api/blog/remove',
      method: 'delete',
      data
    })
  }
}
