<template>
  <div class="tags-view-item" :class="active? 'active' : ''">
    <router-link :to="menu.path" v-if="menu.meta.title">
      {{ $t(menu.meta.title) }}
    </router-link>
    <svg-icon class="el-icon-refresh-right" name="reload" @click.stop="reload"  v-if="active"></svg-icon>
    <svg-icon class="el-icon-close" name="close" @click.stop="closeTab" v-if="!menu.meta.hideClose" :alt="$t('message.common.del')"></svg-icon>
  </div>
</template>

<script>
import { defineComponent } from 'vue'
import SvgIcon from '@/components/SvgIcon.vue'
export default defineComponent({
  components: {
    'svg-icon':SvgIcon
  },
  props: {
    menu: {
      type: Object,
      default: () => {
        return {
          path: '',
          meta: {
            label: '',
            hideClose: false
          }
        }
      }
    },
    active: {
      type: Boolean,
      default: false
    }
  },
  setup(props, { emit }) {
    // 关闭按钮
    function closeTab() {
      emit('close')
    }
    // 刷新按钮
    function reload() {
      emit('reload')
    }
    return {
      closeTab,
      reload
    }
  }
})
</script>

<style lang="scss" scoped>
.tags-view-item {
  display: inline-flex;
  position: relative;
  cursor: pointer;
  height: 24px;
  line-height: 26px;
  border: 1px solid var(--system-header-border-color);
  color: var(--system-header-text-color);
  background: var(--system-header-tab-background);
  padding: 0 8px;
  font-size: 12px;
  margin-left: 5px;
  margin-top: 5px;
  border-radius: 6px;
  a {
    color: var(--system-header-text-color);
    height: 26px;
    display: inline-block;
    padding:0 6px;
  }
  .el-icon-refresh-right {
    display: inline-block;
    padding-top: 4px;
    margin-right: 5px;
  }
  .el-icon-close {
    display: inline-block;
    height: 26px;
  }
  &:first-of-type {
    margin-left: 15px;
  }
  &:last-of-type {
    margin-right: 15px;
  }
  &.active {
    background: var(--system-primary-color);
    border-color: var(--system-primary-color);
    color: var(--system-primary-text-color);
    a {
      color: var(--system-primary-text-color);
    }
    &:hover {
      background: var(--system-primary-color);
    }
  }
  &:hover {
    background-color: var(--system-header-item-hover-color);
  }
}
</style>
