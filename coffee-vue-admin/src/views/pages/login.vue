<template>
  <div class="container">
    <div class="box">
      <h1>{{ $t(systemTitle) }}</h1>
      <el-form class="form">
        <el-input
          size="large"
          v-model="form.username"
          :placeholder="$t('message.system.userName')"
          type="text"
          maxlength="50"
        >
          <template #prepend>
            <i class="sfont system-xingmingyonghumingnicheng"></i>
          </template>
        </el-input>
        <el-input
          size="large"
          ref="password"
          v-model="form.password"
          :type="passwordType"
          :placeholder="$t('message.system.password')"
          name="password"
          maxlength="50"
        >
          <template #prepend>
            <i class="sfont system-mima"></i>
          </template>
          <template #append>
            <i class="sfont password-icon" :class="passwordType ? 'system-yanjing-guan': 'system-yanjing'" @click="passwordTypeChange"></i>
          </template>
        </el-input>
        <el-button type="primary" @click="submit" style="width: 100%;" size="large">{{ $t('message.system.login') }}</el-button>
        <hr>
      </el-form>
      <div class="fixed-top-right">
        <select-lang />
      </div>
    </div>
  </div>
</template>

<script>
import { systemTitle } from '@/config'
import { defineComponent, ref, reactive } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { addRoutes } from '@/router'
import {useUserStore} from "@/store/pinia"
import { ElMessage,ElLoading } from 'element-plus'
import selectLang from '@/layout/Header/functionList/word.vue'
export default defineComponent({
  components: {
    selectLang
  },
  setup() {
    const useStore = useUserStore()
    // const store = useStore()
    const router = useRouter()
    const route = useRoute()
    const loading = ref(false)
    const form = reactive({
      username: 'admin',
      password: '123456'
    })
    const passwordType = ref('password')
    const passwordTypeChange = () => {
      passwordType.value === '' ? passwordType.value = 'password' : passwordType.value = ''
    }
    const checkForm = () => {
      return new Promise((resolve, reject) => {
        if (form.username === '') {
          ElMessage.warning({
            message: '用户名不能为空',
            type: 'warning'
          });
          return;
        }
        if (form.password === '') {
          ElMessage.warning({
            message: '密码不能为空',
            type: 'warning'
          })
          return;
        }
        resolve(true)
      })
    }
    const submit = () => {
      const loadingInstance = ElLoading.service({
        fullscreen: true,
        text:"正在登录中...",
        background:'rgba(245,249,249,0.4)'
      })
      setTimeout(()=>{
        loadingInstance.close()
      },3000)
      checkForm()
      .then(() => {
        let params = {
          username: form.username,
          password: form.password
        }
        useStore.login(params)
        .then(() => {
          //动态添加路由
          addRoutes()
          ElMessage.success({
            message: '登录成功',
            type: 'success',
            showClose: true,
            duration: 1000
          })
          loadingInstance.close()
          router.push(route.query.redirect || '/')
        })

      })
    }

    return {
      systemTitle,
      form,
      passwordType,
      passwordTypeChange,
      submit
    }
  }
})
</script>

<style lang="scss" scoped>
  .container {
    position: relative;
    width: 100vw;
    height: 100vh;
    background-color: #eef0f3;
    .box {
      width: 500px;
      position: absolute;
      left: 50%;
      top: 50%;
      background: white;
      border-radius: 8px;
      transform: translate(-50%, -50%);
      height: 440px;
      overflow: hidden;
      box-shadow: 0 6px 20px 5px rgba(152, 152, 152, 0.1), 0 16px 24px 2px rgba(117, 117, 117, 0.14);
      h1 {
        margin-top: 80px;
        text-align: center;
      }
      .form {
        width: 80%;
        margin: 50px auto 15px;
        .el-input {
          margin-bottom: 20px;
        }
        .password-icon {
          cursor: pointer;
          color: #409EFF;
        }
      }
      .fixed-top-right {
        position: absolute;
        top: 10px;
        right: 10px;
      }
    }
  }
  @media screen and ( max-width: 750px ) {
    .container .box {
      width: 100vw;
      height: 100vh;
      box-shadow: none;
      left: 0;
      top: 0;
      transform: none;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      h1 {
        margin-top: 0;
      }
      .form {

      }
    }
  }
</style>
