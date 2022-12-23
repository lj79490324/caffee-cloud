<template>
  <div class="tabs">
    <el-scrollbar class="scroll-container tags-view-container" ref="scrollbarDom">
      <Item
        v-for="menu in tabMenus"
        :key="menu.meta.title"
        :menu="menu"
        :active="activeMenu.path === menu.path"
        @close="delMenu(menu)"
        @reload="pageReload"
      />
    </el-scrollbar>
    <div class="handle">
      <el-dropdown placement="bottom">
        <div class="el-dropdown-link">
         <svg-icon class="el-icon-arrow-down el-icon--right" name="down"></svg-icon>
        </div>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item  @click="pageReload"><svg-icon name="reload"></svg-icon>{{ $t('message.system.tab.reload') }}</el-dropdown-item>
            <el-dropdown-item :disabled="currentDisabled" @click="closeCurrentRoute"><svg-icon name="eye_close"></svg-icon>{{ $t('message.system.tab.closeCurrent') }}</el-dropdown-item>
            <el-dropdown-item :disabled="tabMenus.length < 3" @click="closeOtherRoute"><svg-icon name="eye_close"></svg-icon>{{ $t('message.system.tab.closeOther') }}</el-dropdown-item>
            <el-dropdown-item :disabled="tabMenus.length <= 1" @click="closeAllRoute"> <svg-icon name="eye_close"></svg-icon>{{ $t('message.system.tab.closeAll') }}</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
      <el-tooltip class="item" effect="dark" :content="contentFullScreen ? $t('message.system.fullScreenBack'):$t('message.system.fullScreen')" placement="bottom">
        <svg-icon name="full_screen_full"  @click="onFullscreen"></svg-icon>
        <i class="el-icon-full-screen"></i>
      </el-tooltip>
    </div>
  </div>
</template>

<script>
import Item from './item.vue'
import {defineComponent, computed, watch, reactive, ref, nextTick, onMounted, toRefs} from 'vue'
import { useRoute, useRouter } from 'vue-router'
import SvgIcon from '@/components/SvgIcon.vue'
import {useAppStore,useKeepAliveStore} from "@/store/pinia"
import {storeToRefs} from "pinia";
export default defineComponent({
  components: {
    Item,
    SvgIcon
  },
  setup() {
    const keepAliveStore = useKeepAliveStore()
    const route = useRoute()
    const router = useRouter()
    const scrollbarDom = ref(null)

    const defaultMenu = {
      path: '/dashboard',
      meta: { title: 'message.menu.dashboard.index', hideClose: true }
    }
    const appStore=useAppStore();
    const {contentFullScreen}= storeToRefs(appStore)
    const currentDisabled = computed(() => route.path === defaultMenu.path)
    //const closeOtherDisabled = computed(() => )
    let activeMenu = reactive({ path: '' })
    const {tabMenus}= storeToRefs(keepAliveStore)
    if (keepAliveStore.getTabMenusSize === 0){ // 判断之前有没有调用过
      keepAliveStore.addTabs(defaultMenu)
      addMenu(defaultMenu)
    }
    watch(tabMenus,(newVal)=>{
      keepAliveStore.addTabs(newVal)
      tabMenus.add(newVal)
    })
    router.afterEach(() => {
      addMenu(route)
      initMenu(route)
    })

    // 全屏（无缓存）
    function onFullscreen() {
      appStore.contentFullScreenChange( !contentFullScreen.value)
    }
    // 当前页面组件重新加载（无缓存）
    function pageReload() {
      const self = route.matched[route.matched.length-1].instances.default
      self.handleReload();
    }


    // 关闭当前标签，首页不关闭
    function closeCurrentRoute() {
      if (route.path !== defaultMenu.path) {
        delMenu(route.matched[route.matched.length-1])
      }
    }
    // 关闭除了当前标签之外的所有标签（无缓存）
    function closeOtherRoute() {
      keepAliveStore.resetTabs(defaultMenu)
      if (route.path !== defaultMenu.path) {
        addMenu(route)
      }
      setKeepAliveData()
    }

    // 关闭所有的标签，除了首页（无缓存）
    function closeAllRoute() {
      keepAliveStore.resetTabs(defaultMenu)
      setKeepAliveData()
      router.push(defaultMenu.path)
    }

    // 添加新的菜单项（无缓存）
    function addMenu(menu) {
      let { path, meta, name } = menu
      if (meta.hideTabs) {
        return
      }
      let hasMenu = false;
      tabMenus.value.forEach((item)=>{
        if (item.path === path){
          hasMenu = true
          return false
        }
      })

      if (!hasMenu) {
        keepAliveStore.addTabs({
          path,
          meta,
          name
        })
      }
    }

    // 删除菜单项（无缓存）
    function delMenu(menu) {
      let index = 0
      if (!menu.meta.hideClose) {
        if (menu.meta.cache && menu.name) {
          keepAliveStore.delKeepAliveComponentsName(menu.name)
        }
        index = tabMenus.value.findIndex((item) => item.path === menu.path)
        keepAliveStore.delTabs(menu)
      }
      if (menu.path === activeMenu.path) {
        index - 1 > 0 ? router.push(tabMenus.value[index - 1].path) : router.push(defaultMenu.path)
      }
    }

    // 初始化activeMenu（无缓存）
    function initMenu(menu) {
      activeMenu = menu
      nextTick(() => {
        setPosition()
      })
    }
    // 设置当前滚动条应该在的位置（无缓存）
    function setPosition() {
      if (scrollbarDom.value) {
        let scrollbarFindDom = toRefs(scrollbarDom.value).wrapRef.value
        const domBox = {
         // scrollbar: scrollbarDom.value.wrap$.querySelector('.el-scrollbar__wrap'),
          scrollbar:scrollbarFindDom,
          activeDom: scrollbarFindDom.querySelector('.active'),
          activeFather: scrollbarFindDom.querySelector('.el-scrollbar__view')
        }
        for (let i in domBox) {
          if (!domBox[i]) {
            return
          }
        }
        const domData = {
          scrollbar: domBox.scrollbar.getBoundingClientRect(),
          activeDom: domBox.activeDom.getBoundingClientRect(),
          activeFather: domBox.activeFather.getBoundingClientRect()
        }
        const num = domData.activeDom.x - domData.activeFather.x + 1/2 * domData.activeDom.width - 1/2 * domData.scrollbar.width
        domBox.scrollbar.scrollLeft = num
      }
    }

    // 配置需要缓存的数据（无缓存）
    function setKeepAliveData() {
      let keepAliveNames = []
      tabMenus.value.forEach((menu) => {
        menu.meta && menu.meta.cache && menu.name && keepAliveNames.push(menu.name)
      })
      keepAliveStore.setKeepAliveComponentsName(keepAliveNames)
    }

    // 初始化时调用：1. 新增菜单 2. 初始化activeMenu
    addMenu(route)
    initMenu(route)
    return {
      contentFullScreen,
      onFullscreen,
      pageReload,
      scrollbarDom,
      // 菜单相关
      tabMenus,
      activeMenu,
      delMenu,
      closeCurrentRoute,
      closeOtherRoute,
      closeAllRoute,
      currentDisabled
    }
  }
})
</script>

<style lang="scss" scoped>
.tabs {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 40px;
  background: var(--system-header-background);
  border-bottom: 1px solid var(--system-header-border-color);
  border-top: 1px solid var(--system-header-border-color);
  box-shadow: 0 1px 4px 0 rgba(0, 0, 0, .1);
  .handle {
    min-width: 95px;
    height: 100%;
    display: flex;
    align-items: center;
    .el-dropdown-link {
      margin-top: 5px;
      border-left: 1px solid var(--system-header-border-color);
      height: 25px;
      width: 40px;
      display: flex;
      justify-content: center;
      align-items: center;
    }
    i {
      color: var(--system-header-text-color);
    }
  }
}
.scroll-container {
  white-space: nowrap;
  position: relative;
  overflow: hidden;
  width: 100%;
  :deep(*) {
    .el-scrollbar__bar {
      bottom: 0px;
    }
    .el-scrollbar__wrap {
      height: 50px;
    }
  }
}
.tags-view-container {
  height: 40px;
  flex: 1;
  width: 100%;
  display: flex;
}
.el-icon-full-screen {
  cursor: pointer;
  &:hover {
    background: rgba(0,0,0,.025);
  }
  &:focus {
    outline: none;
  }
}
</style>
