<template>
  <Layer :layer="layer" @confirm="submit" ref="layerDom">
    <tree-transfer></tree-transfer>
  </Layer>
</template>
<script>
import { defineComponent, ref } from 'vue'
import Layer from '@/components/layer/index.vue'
import TreeTransfer from '@/components/treetransfer/index.vue'
export default defineComponent({
  components: {
    Layer,
    TreeTransfer
  },
  props:{
    layer: {
      type: Object,
      default: () => {
        return {
          show: false,
          title: '',
          showButton: true
        }
      }
    }
  },
  setup(props, ctx){

    const generateData = () => {
      const data  = [
        {
          "key": 1,
          "parentId": 0,
          "label": "Option 1",
          "disabled": false
        },
        {
          "key": 2,
          "parentId": 0,
          "label": "Option 2",
          "disabled": false
        },
        {
          "key": 3,
          "parentId": 0,
          "label": "Option 3",
          "disabled": false
        },
        {
          "key": 4,
          "parentId": 0,
          "label": "Option 4",
          "disabled": false
        },
        {
          "key": 5,
          "parentId": 0,
          "label": "Option 5",
          "disabled": false
        },
        {
          "key": 6,
          "parentId": 0,
          "label": "Option 6",
          "disabled": false
        },
        {
          "key": 7,
          "parentId": 0,
          "label": "Option 7",
          "disabled": false
        },
        {
          "key": 8,
          "parentId": 0,
          "label": "Option 8",
          "disabled": false
        },
        {
          "key": 9,
          "parentId": 0,
          "label": "Option 9",
          "disabled": false
        }
      ]
      // for (let i = 1; i <= 10; i++) {
      //   data.push({
      //     key: i,
      //     label: `Option ${i}`,
      //     disabled: false,
      //   })
      // }
      console.log(data)
      return data
    }

    const data = ref(generateData())
    const value = ref([])
    return {
      data,
      value
    }
  },
  methods:{
    submit(){
      console.log("点击事件")
    }
  }
})
</script>

<style lang="scss" scoped>

</style>
