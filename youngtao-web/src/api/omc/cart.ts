import request from '@/common/utils/request'

export function addCart(data: any) {
  return request({
    url: 'omc/cart/add',
    method: 'post',
    data: JSON.stringify(data)
  })
}

export function getUserCart() {
  return request({
    url: `omc/cart/getUserCart`,
    method: 'get'
  })
}

export function updateNum(data: any) {
  return request({
    url: `omc/cart/updateNum`,
    method: 'post',
    data: JSON.stringify(data)
  })
}

export function deleteCart(data: any) {
  return request({
    url: `omc/cart/delete`,
    method: 'post',
    data: JSON.stringify(data)
  })
}

export function batchDelete(data: any) {
  return request({
    url: `omc/cart/batchDelete`,
    method: 'post',
    data: JSON.stringify(data)
  })
}
