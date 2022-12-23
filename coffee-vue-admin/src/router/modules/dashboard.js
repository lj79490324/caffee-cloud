import Layout from '@/layout/index.vue'
import { createNameComponent } from '../createNode'
const route = [
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    meta: { title: 'message.menu.dashboard.name', icon: 'full_screen_full' },
    children: [
      {
        path: 'dashboard',
        component: createNameComponent(() => import('@/views/main/dashboard/index.vue')),
        meta: { title: 'message.menu.dashboard.index', icon: 'full_screen_full', hideClose: true }
      }
    ]
  } ,{
    path: '/pwd',
    component: createNameComponent(() => import('@/views/main/userinfo/password.vue')),
    hidden: true,
    meta: { title: 'message.system.change', hideTabs: true }
  },
]

export default route
