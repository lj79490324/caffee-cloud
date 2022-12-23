<template>
  <el-dropdown @command="handleCommand" :size="elementSize">
    <span class="el-dropdown-link">
      <svg-icon name="font-size"></svg-icon>
    </span>
    <template #dropdown>
      <el-dropdown-menu>
        <el-dropdown-item
          v-for="d in list"
          :key="d.size"
          :command="d.size"
          :disabled="elementSize === d.size"
        >
          {{ $t(d.name) }}
        </el-dropdown-item>
      </el-dropdown-menu>
    </template>
  </el-dropdown>
</template>

<script>
import { defineComponent, unref } from 'vue'
import { useRoute } from 'vue-router'
import { storeToRefs } from 'pinia'
import {useAppStore} from "@/store/pinia"
import SvgIcon from '@/components/SvgIcon.vue'
export default defineComponent({
  components: { 'svg-icon':SvgIcon },
  setup() {
    const appStore = useAppStore()
    const {elementSize} = storeToRefs(appStore)

    const route = useRoute()
    // const elementSize = computed(() => store.state.app.elementSize)
    const list = [
      { size: 'default', name: 'message.system.size.default' },
      { size: 'large', name: 'message.system.size.large' },
      { size: 'small', name: 'message.system.size.small' },
    ]
    const handleCommand = (command) => {
      appStore.stateChange({name: 'elementSize', value: command})
    }
    const { fullPath } = unref(route)
    return {
      list,
      elementSize,
      fullPath,
      handleCommand
    }
  },
  // methods: {
  //   // handleCommand(command) {
  //   //   console.error(command)
  //   //   this.$store.commit('app/stateChange', {
  //   //     name: 'elementSize',
  //   //     value: command
  //   //   })
  //   //   // this.setElementSize()
  //   // },
  //   setElementSize() {
  //     console.log(this.elementSize)
  //     this.$ELEMENT.size = this.elementSize
  //     this.$router.replace({
  //       path: "/redirect" + this.fullPath
  //     })
  //   }
  // }
})
</script>

<style lang="scss" scoped>

</style>
