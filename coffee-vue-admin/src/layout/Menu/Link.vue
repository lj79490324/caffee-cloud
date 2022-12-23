<template>
  <component :is="type" v-bind="linkProps(to)" @click="hideMenu" >
    <slot>
    </slot>
  </component>
</template>
<script>
import { defineComponent} from 'vue'
import {useAppStore} from "@/store/pinia"
import {storeToRefs} from "pinia";
export default defineComponent({
  name: 'appLink',
  props: {
    to: {
      type: String,
      required: true
    }
  },
  setup(props) {
    const  appStore = useAppStore()
    const {isCollapse} = storeToRefs(appStore)
    const linkProps = (to) => {
     return {
       to: to
     }
    }
    const hideMenu = () => {
      if (document.body.clientWidth <= 1000 && !isCollapse.value) {
        appStore.isCollapseChange(true)
      }
    };
    return {
      type: "router-link",
      linkProps,
      hideMenu
    }
  }
})
</script>
<style lang="">

</style>
