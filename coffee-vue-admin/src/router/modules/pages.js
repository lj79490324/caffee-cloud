import Layout from '@/layout/index.vue'
import { createNameComponent } from '../createNode'
const route = [
  {
    path: '/pages',
    component: Layout,
    redirect: '/pages/crudTable',
    meta: { title: 'message.menu.page.name', icon: 'full_screen_full' },
    alwayShow: true,
    children: [
      {
        path: 'crudTable',
        component: createNameComponent(() => import('@/views/main/pages/crudTable/index.vue')),
        meta: { title: 'message.menu.page.crudTable',icon: 'full_screen_full' , cache: true, roles: ['admin', 'editor'] }
      },
      {
        path: 'categoryTable',
        component: createNameComponent(() => import('@/views/main/pages/categoryTable/index.vue')),
        meta: { title: 'message.menu.page.categoryTable',icon: 'full_screen_full' ,cache: true, roles: ['admin'] }
      },
      {
        path: 'treeTable',
        component: createNameComponent(() => import('@/views/main/pages/treeTable/index.vue')),
        meta: { title: 'message.menu.page.treeTable',icon: 'full_screen_full' , cache: true }
      }
    ]
  }
]

export default route
