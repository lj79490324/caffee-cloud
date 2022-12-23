<template>
  <el-dropdown @command="handleCommand" size="medium">
    <span class="el-dropdown-link">
       <svg-icon name="language"></svg-icon>
    </span>
    <template #dropdown>
      <el-dropdown-menu>
        <el-dropdown-item
          v-for="(locale, key) in $i18n.messages"
          :key="`locale-${locale.message.language}`"
          :command="key"
          :disabled="name === key"
        >
          {{ locale.message.language }}
        </el-dropdown-item>
      </el-dropdown-menu>
    </template>
  </el-dropdown>
</template>

<script>
import { defineComponent } from 'vue'
import { useRoute } from 'vue-router'
import {useAppStore} from "@/store/pinia"
import { useI18n } from 'vue-i18n'
import { changeTitle } from '@/utils/system/title'
import SvgIcon from '@/components/SvgIcon.vue'
export default defineComponent({
  components: { 'svg-icon':SvgIcon },
  setup() {
    const { locale, t } = useI18n()
    const route = useRoute()
    const appStore = useAppStore()
    // 国际化语言切换
    const handleCommand = (command) => {
      locale.value = command
      appStore.statechange({ name: 'lang', value: command })
      changeTitle(route.meta.title)
      document.querySelector('html').setAttribute('lang', command)
    }
    return {
      handleCommand,
      name: locale
    }
  }
})
</script>

<style lang="scss" scoped>

</style>
