import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import { baidu } from './utils/system/statistics'
import 'element-plus/theme-chalk/index.css'
import 'element-plus/theme-chalk/display.css' // 引入基于断点的隐藏类
import 'normalize.css' // css初始化
import './assets/style/common.scss' // 公共css
import App from './App.vue'
import { createPinia } from 'pinia'
import router from './router'
import i18n from './locale'
import 'virtual:svg-icons-register'
import piniaPersist from 'pinia-plugin-persist'
if (import.meta.env.MODE !== 'development') { // 非开发环境调用百度统计
  baidu()
}
const app = createApp(App)
const pStore = createPinia()
pStore.use(piniaPersist)
app.use(ElementPlus, { i18n: i18n.global.t })
app.use(pStore)
app.use(router)
app.use(i18n)
// app.config.performance = true
app.mount('#app')
