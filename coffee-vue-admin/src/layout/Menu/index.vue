<template>
  <el-scrollbar>
    <el-collapse-transition>
      <el-menu
        class="layout-menu system-scrollbar"
        background-color="var(--system-menu-background)"
        text-color="var(--system-menu-text-color)"
        active-text-color="var(--system-primary-color)"
        :default-active="activeMenu"
        :class="isCollapse? 'collapse': ''"
        :collapse="isCollapse"
        :collapse-transition="false"
        :unique-opened="expandOneMenu"
      >
        <menu-item v-for="(menu, key) in allRoutes"
                   :key="key"
                   :menu="menu"
                  :base-path="menu.path"
        />
      </el-menu>
    </el-collapse-transition>
  </el-scrollbar>
</template>

<script>
import { defineComponent, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import {useAppStore,useKeepAliveStore} from "@/store/pinia"
import {storeToRefs} from "pinia";
import MenuItem from './MenuItem.vue'
import { ElCollapseTransition } from 'element-plus'
export default defineComponent({
  components: {
    MenuItem,
    ElCollapseTransition
  },
  setup() {
    const {isCollapse,expandOneMenu} = storeToRefs(useAppStore())
    const {routers} = storeToRefs(useKeepAliveStore())
    const allRoutes = routers
    const route = useRoute()
    const activeMenu = computed(() => {
      const { meta, path } = route;
      if (meta.activeMenu) {
        return meta.activeMenu;
      }
      return path;
    });
    return {
      isCollapse,
      expandOneMenu,
      allRoutes,
      activeMenu,
    }
  }
})
</script>

<style lang="scss" scoped>
  .el-scrollbar {
    background-color: var(--system-menu-background);
  }
  .layout-menu {
    width: 100%;
    &.collapse {
      margin-left: 0px;
    }
    :deep(*) {
      .el-menu-item, .el-submenu {
        background-color: var(--system-menu-background);
      }
      .el-menu-item svg, .el-menu-item-group__title, .el-submenu__title i {
        color: var(--system-menu-text-color);
      }
      .el-menu-item, .el-submenu__title{
        &.is-active {
          background-color: var(--system-primary-color) !important;
          color: var(--system-primary-text-color) !important;
          i {
            color: var(--system-primary-text-color) !important;
          }
          &:hover {
            background-color: var(--system-primary-color) !important;
            color: var(--system-primary-text-color) !important;
          }
        }
        &:hover {
          background-color: var(--system-menu-hover-background) !important;
        }
      }
      .el-submenu {
        &.is-active {
          >.el-submenu__title, >.el-submenu__title i {
            color: var(--system-menu-submenu-active-color) !important;
          }
        }
        .el-menu-item {
          background-color: var(--system-menu-children-background) !important;
          &.is-active {
            background-color: var(--system-primary-color) !important;
            color: var(--system-primary-text-color) !important;
            &:hover {
              background-color: var(--system-primary-color) !important;
              color: var(--system-primary-text-color) !important;
            }
          }
          &:hover {
            background-color: var(--system-menu-hover-background) !important;
          }
        }
        .el-submenu {
          .el-submenu__title {
            background-color: var(--system-menu-children-background) !important;
            &:hover {
              background-color: var(--system-menu-hover-background) !important;
            }
          }
        }
      }
    }
  }
</style>
