<template>
    <el-dialog
      ref="dialog"
      v-model="layer.show"
      :title="layer.title"
      :width="layer.width"
      center
      draggable
    >
      <slot></slot>
      <template #footer v-if="layer.showButton">
        <div>
          <el-button type="primary" @click="confirm">确认</el-button>
          <el-button @click="close">取消</el-button>
        </div>
      </template>
    </el-dialog>
</template>

<script>
import { defineComponent, ref } from 'vue'
export default defineComponent({
  props: {
    layer: {
      type: Object,
      default: () => {
        return {
          show: false,
          title: '',
          showButton: false
        }
      },
      required: true
    }
  },
  setup(props, ctx) {
    const dialog = ref(null)
    function confirm() {
      ctx.emit('confirm')
    }
    function close() {
      props.layer.show = false
    }
    return {
      dialog,
      confirm,
      close
    }
  }
})
</script>

<style lang="scss" scoped>

</style>
