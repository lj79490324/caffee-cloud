<template>
  <el-row>
    <el-col :span="11">
      <div>左边标题</div>
      <div class="grid-content ep-bg-purple" >
          <el-tree ref="leftTree" node-key="id" :data="data" :props="defaultProps" show-checkbox default-expand-all highlight-current/>
      </div>
    </el-col>
    <el-col :span="2">
      <div class="grid-content-middle ep-bg-purple-light" >
         <div><el-button @click="toRight" size="large"><el-icon><ArrowRightBold /></el-icon></el-button></div>
         <div><el-button @click="toLeft" size="large"><el-icon><ArrowLeftBold /></el-icon></el-button></div>
      </div>
    </el-col>
    <el-col :span="11">
      <div>右边标题</div>
      <div class="grid-content ep-bg-purple" >
        <el-tree ref="rightTree" node-key="id" :data="selectData"  :props="defaultProps" show-checkbox default-expand-all  highlight-current/>
      </div>
    </el-col>
  </el-row>
</template>
<script>
  import {defineComponent,ref} from "vue";
  import {getTreeData,deepClone} from "@/utils/system/common"
  import { ArrowRightBold,ArrowLeftBold} from '@element-plus/icons-vue'
  export default defineComponent({
    name:'TreeTransfer',
    components:{
      ArrowRightBold,
      ArrowLeftBold
    },
    props:{},
    setup(props, ctx){
      const leftTree = ref(null)
      const rightTree =ref(null)
      const defaultProps = {
        children: 'children',
        label: 'name',
      }
      let selectData = ref([])
      const data = [
        {
          id: 2,
          parentId: 1,
          name: '2 Level one 2',
          children: [
            {
              id: 5,
              parentId:2,
              name: '5 Level two 1-1',
              children: [
                {
                  id: 10,
                  parentId:5,
                  name: '10 Level three 1-1-1',
                },
                {
                  id: 11,
                  parentId:5,
                  name: '11 Level three 1-1-2',
                },
              ],
            },
          ],
        },
        {
          id: 3,
          name: '3 Level one 2',
          parentId: 1,
          children: [
            {
              id: 6,
              parentId: 3,
              name: '6 Level two 2-1',
            },
            {
              id: 7,
              parentId: 3,
              name: '7 Level two 2-2',
            },
          ],
        },
        {
          id: 4,
          parentId: 1,
          name: '4 Level one 3',
          children: [
            {
              id: 8,
              parentId: 4,
              name: '8 Level two 3-1',
            },
            {
              id: 9,
              parentId: 4,
              name: '9 Level two 3-2',
            },
          ],
        },
      ]

      const toRight = () => {
          console.log("--------------------去右边1--------------------")
          // console.log(leftTree)
          let tmp = leftTree.value.getCheckedNodes(false,true)
          selectData.value = getTreeData(deepClone(tmp))
          console.log("--------------------去右边2--------------------")
      }
      const toLeft = () => {
        console.log("--------------------去左边1--------------------")
        //leftTree.value.setCheckedKeys([2,5,10],false)
        let  leftArr = leftTree.value.getCheckedKeys(true)
        //被选中需要删除的节点
        let rightArr = rightTree.value.getCheckedKeys(true)



        console.warn(leftArr)
        console.warn(rightArr)
        let __set = new Set(leftArr)
        rightArr.forEach(item => __set.delete(item))
        console.log(leftArr)
        leftArr =[...__set]
        console.log(leftArr)
        leftTree.value.setCheckedKeys(leftArr)

        //删除左边节点上的数据
        rightArr.forEach(item=>{
          let leafNode= rightTree.value.getNode(item)
          reMoveParent(item,leafNode)
        })
        console.log("--------------------去左边2--------------------")
      }

      /**
       *
       * @param id 所需要移除的id
       * @param node  叶子节点
       */
      const reMoveParent = (id,node) => {
          let parentNode = node.parent
          let index = -1
          if (node.level !== 1){
            index = parentNode.data.children.findIndex(d=>d.id === id)
          }else {
            index = parentNode.data.findIndex(d=>d.id === id)
          }
          if (index !== -1){
            if (node.level !== 1){
              parentNode.data.children.splice(index,1)
            }else {
              parentNode.data.splice(index,1)
            }
          }else {
            return
          }
          if (parentNode.level !== 0 && parentNode.data.children.length === 0 ){
              reMoveParent(parentNode.data.id,parentNode)
          }

      }

      return {
        leftTree,
        rightTree,
        data,
        defaultProps,
        selectData,
        toRight,
        toLeft
      }
    }
  })
</script>

<style lang="scss" scoped>
.el-row {
  margin-bottom: 20px;
}
.el-row:last-child {
  margin-bottom: 0;
}
.el-col {
  border-radius: 4px;
}
//背景颜色需要和主题一起
.grid-content {
  border-radius: 4px;
  border: #97a8be solid 2px;;
  min-height: 144px;
  text-align: center;
}
.grid-content-middle{
  text-align: center;
  height: 100%;
  margin: auto;
  flex-shrink: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
}
</style>
