import axios from 'axios'
import {useUserStore} from "@/store/pinia"
import { ElMessage } from 'element-plus'
const baseURL = import.meta.env.VITE_BASE_URL

const service = axios.create({
  baseURL: baseURL,
  timeout: 5000
})

// 请求前的统一处理
service.interceptors.request.use(
  (config) => {
      const userStore = useUserStore()
    // JWT鉴权处理
    if (userStore.token) {
      config.headers['Authorization'] = userStore.token
    }
    return config
  },
  (error) => {
    console.log(error) // for debug
    return Promise.reject(error)
  }
)

service.interceptors.response.use(
  (response) => {
    const res = response.data
    if (response.status === 200) {
      return res
    } else {
      showError(res)
      return Promise.reject(res)
    }
  },
  (error)=> {
      const badMessage = error.message || error
      let code = parseInt(badMessage.toString().replace('Error: Request failed with status code ', ''))
      if (error.response.data.code === 500){
          code = 500
      }
      showError({ code, message: badMessage })
      return Promise.reject(error)
  }
)

function showError(error) {

  if (error.code === 500) {
    // to re-login
      const userStore = useUserStore()
      userStore.tokenChange("")
      userStore.infoChange({})
      localStorage.clear()
      location.reload()
  } else {
    ElMessage({
      message: error.msg || error.message || '服务异常',
      type: 'error',
      duration: 3 * 1000
    })
  }

}

export default service
