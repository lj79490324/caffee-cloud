import request from '@/utils/system/request'

// 登录api
export function loginApi(data) {
  return request({
    url: '/auth/jwt/login',
    method: 'post',
    data:data
  })
}

// 获取用户信息Api
export function getInfoApi() {
  return request({
    url: '/auth/user/info',
    method: 'get'
  })
}

// 获取用户信息Api
export function getUserMenu() {
  return request({
    url: '/auth/user/menu',
    method: 'get'
  })
}

// 退出登录Api
export function loginOutApi() {
  return request({
    url: '/auth/jwt/logout',
    method: 'get'
  })
}

// 获取用户信息Api
export function passwordChange(data) {
  return request({
    url: '/user/passwordChange',
    method: 'post',
    baseURL: '/mock',
    data
  })
}
