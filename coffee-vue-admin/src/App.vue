<template>
  <el-config-provider :size="config.size" :locale="locale">
    <router-view></router-view>
  </el-config-provider>
</template>

<script>
import { defineComponent, computed,reactive} from 'vue'
import { useI18n } from 'vue-i18n'
import { storeToRefs } from 'pinia'
import {useAppStore} from "@/store/pinia"
export default defineComponent({
  name: 'App',
  setup() {
    const i18n = useI18n()
    const {elementSize} = storeToRefs(useAppStore())
    const config = reactive({
      size: elementSize
    })
    const locale = computed(() => {
      return {
        name: i18n.locale.value,
        el: i18n.messages.value[i18n.locale.value].el
      }
    })
    return {
      locale,
      config,
    }
  }
})
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  width: 100%;
  height: 100vh;
}
</style>
