import request from '@/utils/system/request'
//获取用户列表
export function getAllRole() {
    return request({
        url: '/coffee-system/sysRole/get',
        method: 'get'
    })
}
