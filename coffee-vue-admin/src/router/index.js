/**
 * @description 所有人可使用的参数配置列表
 * @params hideMenu: 是否隐藏当前路由结点不在导航中展示
 * @params alwayShow: 只有一个子路由时是否总是展示菜单，默认false
 */
import {createRouter, createWebHashHistory} from 'vue-router'
import {storeToRefs} from 'pinia'
import {useKeepAliveStore, useUserStore} from "@/store/pinia"
import i18n from '@/locale'
import NProgress from '@/utils/system/nprogress'
import {changeTitle} from '@/utils/system/title'
import { getUserMenu } from '@/api/user'

// 动态路由相关引入数据
import Layout from '@/layout/index.vue'
const modulesComponent = import.meta.glob('../views/**/**.vue');
import MenuBox from '@/components/menu/index.vue'
import MenuBox2 from '@/components/menu/index2.vue'
import {createNameComponent} from './createNode'

// 引入modules
import Dashboard from './modules/dashboard'
import Pages from './modules/pages'
import System from './modules/system'


let modules = [
  ...Dashboard,...System,
]

// const userStore = useUserStore()
const { t } = i18n.global

const routes = modules
const router = createRouter({
  history: createWebHashHistory(),
  routes
})
let asyncRoutes = [
  ...Pages,
]
// 动态路由的权限新增，供登录后调用
export function addRoutes() {

  const keepAliveStore = useKeepAliveStore()

   getUserMenu().then(res=>{
    if (res.code === 0){
      let menus = res.data
      let routerData = getTreeData(convert2Router(menus))
      eachData1(routerData)

      keepAliveStore.setRoutes(routerData)
      keepAliveStore.getAddRoutes.forEach(item=>{
        router.addRoute(item)
      })
    }
  })

  console.log("-------------函数执行结束------------")

}

// 重置匹配所有路由的解决方案，todo
function eachData1(menuData){
  menuData.forEach(item =>{
    if (item.children && item.children.length > 0) {
      if (item.sysMenuType === 0) {
        item.component = createNameComponent(modulesComponent[`../views/${item.componentUrl}.vue`])
      } else if (item.sysMenuType === 1){
        if (item.deep === 1){
          item.component = Layout
        }else if (item.deep === 2) {
          item.component = MenuBox
        }else {
          item.component = MenuBox2
        }
      }
      eachData1(item.children)
    }else {
      if (item.sysMenuType === 0){
        item.component = createNameComponent(modulesComponent[`../views/${item.componentUrl}.vue`])
      }

    }
  })
}

function getTreeData(data){
  return data.filter(parent=>{
    let branchArr = data.filter(child => parent['id'] === child['parentId']);
    branchArr.length>0 ? parent['children'] = branchArr : '';
    return parent['parentId'] === 1 ;
  })
}

function convert2Router(data){
  let routers = []
  data.forEach(item=>{
    if (item.sysMenuType === 0 || item.sysMenuType === 1){
      let routerItem = {}
      routerItem.id = item.id
      routerItem.parentId = item.parentId
      routerItem.path = item.sysMenuUrl
      if (item.sysMenuComponent !== null && item.sysMenuComponent !== ''){
        routerItem.componentUrl = item.sysMenuComponent
      }

      routerItem.sysMenuType = item.sysMenuType
      // 菜单深度，几级菜单
      routerItem.deep = item.path.split("_").length - 1
      let meta = {}
      meta.title = item.sysMenuName
      meta.icon = item.sysMenuIcon
      routerItem.name = item.sysMenuCode
      routerItem.meta = meta
      routerItem.hidden = item.status === 0
      routers.push(routerItem)
    }
  })

  return routers
}

const whiteList = ['/login']
let load = 0
router.beforeEach( (to, _from, next) => {
  NProgress.start();
  const {token}= storeToRefs(useUserStore())
  if (token.value ) {
    if (load === 0){
      const keepAliveStore = useKeepAliveStore()
      let asyncRoute = keepAliveStore.getAddRoutes
      eachData1(asyncRoute)
      asyncRoute.forEach(item =>{
        router.addRoute(item)
      })
      next({ path: '/' });
    }
    load++
    if (to.path === "/login"){
      next({ path: '/' });
      NProgress.done();
    }else {
      to.meta.title ? (changeTitle(to.meta.title)) : ""; // 动态title
      next()
    }

  } else {
    to.meta.title ? (changeTitle(to.meta.title)) : ""; // 动态title
    if ( whiteList.indexOf(to.path) !== -1){
      next()
    }else {
      next("/login"); // 全部重定向到登录页
      NProgress.done()
    }
  }
});

router.afterEach((to, _from) => {
  const keepAliveStore = useKeepAliveStore();
  const {keepAliveComponentsName}= storeToRefs(keepAliveStore)
  const name = to.matched[to.matched.length - 1].components.default.name
  if (to.meta && to.meta.cache && name && !keepAliveComponentsName.value.includes(name)) {
    keepAliveStore.addKeepAliveComponentsName(name)
  }
  NProgress.done();
});

export {
  modules,asyncRoutes
}

export default router
