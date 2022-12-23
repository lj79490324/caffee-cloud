import request from '@/utils/system/request'
//获取用户列表
export function getUserPage(data) {
    return request({
        url: '/coffee-system/sysUser/page',
        method: 'post',
        data
    })
}
