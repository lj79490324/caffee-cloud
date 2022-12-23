<template>
  <div :title="isFullscreen ? $t('message.system.fullScreenBack') : $t('message.system.fullScreen')">
    <svg-icon :name="isFullscreen ?'fullscreen-exit':'fullscreen'" class="sfont" :class="isFullscreen ? 'system-quanping':'system-quanping1'" @click="toggle"></svg-icon>
  </div>
</template>

<script>
import { defineComponent } from 'vue'
import { useFullscreen } from '@vueuse/core'
import SvgIcon from '@/components/SvgIcon.vue'
export default defineComponent({
  name: 'fullscreen',
  components: { 'svg-icon':SvgIcon },
  setup() {
    const { isFullscreen, toggle } = useFullscreen()

    return {
      isFullscreen,
      toggle
    }
  }
})
</script>

<style lang="scss" scoped>
  i {
    cursor: pointer;
    font-size: 18px;
    &:focus {
      outline: none;
    }
  }
</style>
