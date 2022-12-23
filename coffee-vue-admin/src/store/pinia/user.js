import { loginApi, getInfoApi, loginOutApi } from '@/api/user'
import { defineStore } from 'pinia'
export default defineStore('user', {
    state:()=>{
        return {
            token: "", // 登录token
            info: {},  // 用户信息
        }
    },
    getters:{
        getUserInfo(state){
            return state.info
        },
        getTokens(state){
            return state.token
        }
    },
    actions:{
        tokenChange(token) {
            this.token = token
        },
        infoChange(info) {
            this.info = info
        },
        // login by login.vue
        login(params) {
            const _this = this
            return new Promise((resolve, reject) => {
                loginApi(params)
                    .then(res => {
                        if (res.code === 0){
                            console.log("登录成功")
                            _this.tokenChange("Bearer "+res.data.token)
                            _this.getInfo()
                                .then(infoRes => {
                                    resolve(res.data.token)
                                })
                        }else {
                            reject(res)
                        }
                    })
            })
        },
        // get user info after user logined
        getInfo() {
            const _this = this
            return new Promise((resolve, reject) => {
                getInfoApi()
                    .then(res => {
                        if (res.code === 0){
                            _this.infoChange(res.data)
                            resolve(res)
                        }else {
                            reject(res)
                        }
                    })
            })
        },
        loginOut() {
            const _this = this
            return new Promise((resolve, reject)=>{
                return loginOutApi().then(res => {
                    console.warn("退出登录...")
                    _this.tokenChange(undefined)
                    _this.infoChange({ "nickName": "匿名用户"})
                    resolve(res)
                }).catch(error => {
                    console.warn("退出登录出现异常了...")
                    _this.tokenChange(undefined)
                    _this.infoChange({ "nickName": "匿名用户"})
                    reject(error)
                })
            })

        }
    },
    persist:{
        enabled:true,
        strategies:[
            {
                storage:localStorage,
                paths:["token","info"]
            }
        ]
    }
})
