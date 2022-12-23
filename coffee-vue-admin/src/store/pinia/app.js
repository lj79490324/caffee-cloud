import { defineStore } from 'pinia'
export default  defineStore('app', {
    state: () => {
        return{
            isCollapse: false, // 侧边栏是否收缩展示
            contentFullScreen: false, // 内容是否可全屏展示
            showLogo: true, // 是否显示Logo
            fixedTop: false, // 是否固定顶部, todo，暂未使用
            showTabs: true, // 是否显示导航历史
            expandOneMenu: true, // 一次是否只能展开一个菜单
            elementSize: 'default', // element默认尺寸，支持官网四个大小参数
            lang: 'zh', // 默认采用的国际化方案
            theme: {
                state: {
                    style: 'default',
                    primaryColor: '#409eff',
                    menuType: 'side'
                }
            }
        }
    },
    actions: {
        isCollapseChange(type) {
            this.isCollapse = type
        },
        contentFullScreenChange(type) {
            this.contentFullScreen = type
        },
        menuListChange(arr) {
            this.menuList = arr
        },
        stateChange(option) {
            this[option.name] = option.value
        }
    }
})
