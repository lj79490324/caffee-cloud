<template>
  <div class="box">
    我是首页 12---
    <br/>
    <div style="background-color: yellow;height: 100px"> </div>
    <br/>
    <button @click="test(true)">锁定屏幕</button>
    <hr/>
    <el-button type="warning" @click="testLogin()" >测试信息错误</el-button>
    <el-dialog v-model="form.dialogVisible" title="屏幕锁定中" v-bind:close-on-press-escape="false" width="30%" fullscreen show-close custom-class="test">
     <div style="width: 40%;margin: auto">
       <input  placeholder="请输入解锁密码" type="text"/>
     </div>
      <br>
      <el-button type="warning" @click="test(false)" >点击解锁</el-button>
    </el-dialog>
    <hr>
    <el-scrollbar>
      <div class="scrollbar-flex-content">
        <p v-for="item in 50" :key="item" class="scrollbar-demo-item">
          {{ item }}
        </p>
      </div>
    </el-scrollbar>
  </div>
</template>

<script>
import { defineComponent,reactive } from 'vue'
import { getInfoApi } from '@/api/user'
export default defineComponent({
  setup(){

    const form = reactive({dialogVisible:false})
    let test=(flag)=>{
      form.dialogVisible = flag
    }
    let testLogin=()=>{
      getInfoApi().then(()=>{
          console.error("点击测试错误")
      })
    }
    return{
      form,
      test,
      testLogin
    }
  }
})
</script>

<style scoped lang="scss">
  .box {
    padding: 15px;
  }
 .test {
    background: rgba(143, 71, 71, 0) !important;
  }
  .scrollbar-flex-content {
    display: flex;
  }
  .scrollbar-demo-item {
    flex-shrink: 0;
    display: flex;
    align-items: center;
    justify-content: center;
    width: 100px;
    height: 50px;
    margin: 10px;
    text-align: center;
    border-radius: 4px;
    background: var(--el-color-danger-light-9);
    color: var(--el-color-danger);
  }
</style>
