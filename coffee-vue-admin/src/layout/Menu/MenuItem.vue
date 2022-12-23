<template>
  <template v-if="!menu.hidden">
    <template v-if="hasOneShowingChild(menu.children, menu)&& (!onlyOneChild.children || onlyOneChild.noShowingChildren)">
        <app-link  v-if="onlyOneChild.meta" :to="pathResolve(onlyOneChild.path)">
          <el-menu-item :index="pathResolve(onlyOneChild.path)">
            <svg-icon :name="onlyOneChild.meta.icon || menu.meta.icon" :color="textColor.color" v-if="onlyOneChild.meta.icon || menu.meta.icon"></svg-icon>
            <template #title>{{ $t(onlyOneChild.meta.title) }}</template>
          </el-menu-item>
        </app-link>
    </template>
    <el-sub-menu v-else :index="pathResolve(menu.path)" popper-append-to-body>
      <template #title>
        <svg-icon :name="menu.meta.icon" :color="textColor.color" v-if="menu.meta.icon"></svg-icon>
        <span>{{ $t(menu.meta.title) }}</span>
      </template>
      <menu-item v-for="(item, key) in menu.children" :key="key" :menu="item" :basePath="pathResolve(item.path)" />
    </el-sub-menu>
  </template>
</template>

<script>
import {defineComponent, computed, reactive,ref} from 'vue'
import appLink from './Link.vue'
import path from 'path-browserify';
import SvgIcon from '@/components/SvgIcon.vue'
import { style } from '@/theme/index'
import {useAppStore} from "@/store/pinia"
import {storeToRefs} from "pinia";
export default defineComponent({
  name: 'menu-item',
  props: {
    menu: {
      type: Object,
      required: true
    },
    basePath: {
      type: String,
      default: ''
    }
  },
  components: {
    appLink,
    SvgIcon
  },
  setup(props) {
    const {theme}=storeToRefs(useAppStore())
    const state = reactive({
      appTheme: theme.value.state.style,
    })
    const textColor = reactive({
      color: style[state.appTheme].menu.textColor
    })
    const onlyOneChild = ref();
    function hasOneShowingChild(children, parent) {
      if (!children) {
        children = [];
      }
      const showingChildren = children.filter((item) => {
        if (item.meta && item.meta.hidden) {
          return false;
        } else {
          // Temp set(will be used if only has one showing child)
          onlyOneChild.value = item;
          return true;
        }
      });
      // When there is only one child router, the child router is displayed by default
      if (showingChildren.length === 1) {
        return true;
      }
      // Show parent if there are no child router to display
      if (showingChildren.length === 0) {
        onlyOneChild.value = { ...parent, path: '', noShowingChildren: true };
        return true;
      }
      return false;
    }

    const pathResolve = (routePath) => {
      if (isExternal(routePath)) {
        return routePath;
      }
      if (isExternal(props.basePath)) {
        return props.basePath;
      }
      return path.resolve(props.basePath, routePath);
    }

    const isExternal = (path) => {
      const isExternal = /^(https?:|http?:|mailto:|tel:)/.test(path);
      return isExternal;
    }
    return {
      pathResolve,
      hasOneShowingChild,
      onlyOneChild,
      textColor
    }
  }
})
</script>

<style lang="scss" scoped>
  .el-submenu {
    text-align: left;
  }
  .el-menu-item {
    text-align: left;
  }
  svg{
   margin-right: 5px;
  }
</style>
