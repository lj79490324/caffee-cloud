<template>
  <header>
    <div class="left-box">
      <!-- 收缩按钮 -->
      <div class="menu-icon" @click="openStateChange">
        <svg-icon :name="isCollapse ?'open':'shrink'"></svg-icon>
      </div>
      <Breadcrumb />
    </div>
    <div class="right-box">
      <!-- 快捷功能按钮 -->
      <div class="function-list">
        <div class="function-list-item hidden-sm-and-down"><Full-screen /></div>
        <div class="function-list-item"><Word /></div>
        <div class="function-list-item"><SizeChange /></div>
        <div class="function-list-item hidden-sm-and-down"><Theme /></div>
      </div>
      <!-- 用户信息 -->
      <div class="user-info">
        <el-dropdown>
          <span class="el-dropdown-link">
            {{ info?info.nickName:"匿名用户" }}
            <i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="showPasswordLayer">{{ $t('message.system.changePassword') }}</el-dropdown-item>
              <el-dropdown-item @click="loginOut">{{ $t('message.system.loginOut') }}</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
      <PasswordLayer style="background-color: #409eff" :layer="layer" v-if="layer.show" />
    </div>
  </header>
</template>

<script>
import {defineComponent, computed, reactive, ref} from 'vue'
import {useUserStore,useAppStore,useKeepAliveStore} from "@/store/pinia"
import { storeToRefs } from 'pinia'
import { useRouter } from 'vue-router'
import { modules,asyncRoutes} from '@/router'
import FullScreen from './functionList/fullscreen.vue'
import Word from './functionList/word.vue'
import SizeChange from './functionList/sizeChange.vue'
import Theme from './functionList/theme.vue'
import Breadcrumb from './Breadcrumb.vue'
import PasswordLayer from './passwordLayer.vue'
import SvgIcon from '@/components/SvgIcon.vue'
export default defineComponent({
  components: {
    FullScreen,
    Breadcrumb,
    Word,
    SizeChange,
    Theme,
    PasswordLayer,
    SvgIcon
  },
  setup() {
    const userStore = useUserStore()
    const appStore = useAppStore()
    const keepAliveStore = useKeepAliveStore()
    const router = useRouter()
    const layer = reactive({
      show: false,
      showButton: true
    })
    const {isCollapse} = storeToRefs(appStore)
    const {info} = storeToRefs(userStore)
    const openStateChange = () => {
      appStore.isCollapseChange(!isCollapse.value)
    }

    // login out the system
    const loginOut = () => {
      userStore.loginOut().then(resolve=>{

        keepAliveStore.delAllTable()
        //需要清空动态路由
        router.removeRoute(asyncRoutes)
        router.removeRoute(modules)
        router.replace({path: '/login'})
      },resolve=>{
        console.log("退出登录失败")
      })

    }

    const showPasswordLayer = () => {
      layer.show = !layer.show
      // router.push("/pwd")
    }
    return {
      isCollapse,
      info,
      layer,
      openStateChange,
      loginOut,
      showPasswordLayer
    }
  }
})
</script>

<style lang="scss" scoped>
  header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 60px;
    background-color: var(--system-header-background);
    padding-right: 22px;
  }
  .left-box {
    height: 100%;
    display: flex;
    align-items: center;
    .menu-icon {
      width: 60px;
      height: 100%;
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 25px;
      font-weight: 100;
      cursor: pointer;
      margin-right: 10px;
      &:hover {
        background-color: var(--system-header-item-hover-color);
      }
      svg{
        width: 1em;
      }
      i {
        color: var(--system-header-text-color);
      }
    }
  }
  .right-box {
    display: flex;
    justify-content: center;
    align-items: center;
    .function-list{
      display: flex;
      .function-list-item {
        width: 30px;
        display: flex;
        justify-content: center;
        align-items: center;
        margin:0 5px;
        :deep(i) {
          color: var(--system-header-text-color);
        }
      }
    }
    .user-info {
      margin-left: 20px;
      .el-dropdown-link {
        color: var(--system-header-breadcrumb-text-color);
      }
    }
  }
</style>
