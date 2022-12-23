import Layout from '@/layout/index.vue'
import { createNameComponent } from '../createNode'
const route = [
  {
    path: '/system',
    component: Layout,
    redirect: '/404',
    hidden: true,
    meta: { title: 'message.menu.system.name' },
    children: [
      {
        path: '/404',
        component: createNameComponent(() => import('@/views/pages/404.vue')),
        meta: { title: 'message.menu.system.404', hideTabs: true }
      },
      {
        path: '/401',
        component: createNameComponent(() => import('@/views/pages/401.vue')),
        meta: { title: 'message.menu.system.401', hideTabs: true }
      },
      {
        path: '/redirect/:path(.*)',
        component: createNameComponent(() => import('@/views/pages/redirect.vue')),
        meta: { title: 'message.menu.system.redirect', hideTabs: true }
      }
    ]
  },
  {
    path: '/login',
    component: createNameComponent(() => import('@/views/pages/login.vue')),
    hidden: true,
    meta: { title: 'message.system.login', hideTabs: true }
  },
  {
    // 找不到路由重定向到404页面
    path: "/:pathMatch(.*)",
    component: Layout,
    redirect: "/404",
    hidden: true
  },
]

export default route
