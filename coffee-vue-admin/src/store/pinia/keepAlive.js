import { defineStore } from 'pinia'
import { modules } from '../../router';
export default defineStore('keepAlive',{
    state: () => {
        // const setMenu = new Set();
        return {
            keepAliveComponentsName: [],
            tabMenus: [],
            //全局路由
            routers:[],
            //动态路由
            addRoutes: []
        }
    },
    getters:{
        keepAliveComponentsNames(state) {
            return state.keepAliveComponentsName
        },
        getTabMenusSize(state){
            return state.tabMenus.length
        },
        getTabMenus(state){
            return state.tabMenus
        },
        getRouter(state){
            return state.routers
        },
        getAddRoutes(state){
            return state.addRoutes
        }
    },
    actions:{
        setRoutes(routes){
            console.log("-------------setRoutes1----------------")
            console.log(routes)
            console.log("-------------setRoutes2----------------")
            this.addRoutes = this.addRoutes.concat(routes)
            if (this.routers.length > 0){
                this.routers = this.routers.concat(routes)
            }else {
                this.routers= modules.concat(routes)
            }

        },
        // 重置，Push, splice keep-alive对象
        setKeepAliveComponentsName(nameArr) {
            this.keepAliveComponentsName = nameArr
        },
        addKeepAliveComponentsName(name) {
            this.keepAliveComponentsName.push(name)
        },
        delKeepAliveComponentsName(name) {
            const key = this.keepAliveComponentsName.indexOf(name)
            if (key !== -1) {
                this.keepAliveComponentsName.splice(key, 1)
                console.warn(this.keepAliveComponentsName)
            }
        },
        addTabs(menu){
            let isNotExist = true
            this.getTabMenus.forEach(item=>{
                if (item.path === menu.path){
                    isNotExist = false
                }
            })
            if (this.getTabMenusSize < 1 || isNotExist){
                this.getTabMenus.push(menu)
            }
        },
        delTabs(menu){
          removeArray(this.getTabMenus,menu)
        },
        resetTabs(menu){
            this.getTabMenus.splice(0,this.getTabMenusSize);
            this.addTabs(menu)
        },
        delAllTable(){
            // this.tabMenus=this.getTabMenus.splice(0,1);
            removeAll(this.getTabMenus,this.keepAliveComponentsNames)
            removeAll(this.addRoutes,this.routers)
        }
    },persist: {
        enabled: true,
        strategies:[
             {storage:sessionStorage,paths:["keepAliveComponentsName","tabMenus"]},
             {storage:localStorage,paths:["routers","addRoutes"]}
        ]
    }
})

function removeAll(_arr,_arr2){
    let length = _arr.length
    _arr.splice(0,length)
    let length2 = _arr2.length
    _arr2.splice(0,length2)
}
/**
 * 从数组中删除指定对象
 *  _arr:数组
 *  _obj:需删除的对象
 * */
function removeArray(_arr, _obj) {
    let length = _arr.length
    for (let i = 0; i < length; i++) {
        if (_arr[i].path === _obj.path) {
            if (i === 0) {
                _arr.shift() //删除并返回数组的第一个元素
                return _arr
            } else if (i === length - 1) {
                _arr.pop()  //删除并返回数组的最后一个元素
                return _arr
            } else {
                _arr.splice(i, 1) //删除下标为i的元素
                return _arr
            }
        }
    }
}
