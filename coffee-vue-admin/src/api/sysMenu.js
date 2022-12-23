import request from '@/utils/system/request'

// 获取用户信息Api
export function getUserMenu() {
    return request({
        url: '/coffee-system/sysMenu/get',
        method: 'get'
    })
}
