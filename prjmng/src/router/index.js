import Vue from 'vue'
import Router from 'vue-router'
// 导入刚才编写的组件
import AppIndex from '@/components/home/AppIndex'
import A from '@/components/A'
import managelogin from '../components/manage/managelogin'
import Bindex from '../components/Business/Bindex'
import B1 from '../components/Business/B1'
import B2 from '../components/Business/B2'
import Cindex from '../components/Customer-service/Cindex'
import Tindex from '../components/Tech/Tindex'
import T1 from '../components/Tech/T1'
import T2 from '../components/Tech/T2'
import manageindex from '../components/manage/manageindex'
import M1 from '../components/manage/M1'
import M2 from '../components/manage/M2'
import M3 from '../components/manage/M3'
import M4 from '../components/manage/M4'
import C1 from '../components/Customer-service/C1'
import C2 from '../components/Customer-service/C2'
import C3 from '../components/Customer-service/C3'
import C4 from '../components/Customer-service/C4'
import B11 from '../components/Business/B11'
Vue.use(Router)

export default new Router({
  routes: [
    // 下面都是固定的写法
    {
      path: '/A',
      name: 'A',
      component: A
    },
    {
      path: '/Bindex',
      name: 'Bindex',
      component: Bindex
    },
    {
      path: '/B1',
      name: 'B1',
      component: B1
    },
    {
      path: '/B11',
      name: 'B11',
      component: B11
    },
    {
      path: '/B2',
      name: 'B2',
      component: B2
    },
    {
      path: '/Tindex',
      name: 'Tindex',
      component: Tindex
    },
    {
      path: '/T1',
      name: 'T1',
      component: T1
    },
    {
      path: '/T2',
      name: 'T2',
      component: T2
    },
    {
      path: '/Cindex',
      name: 'Cindex',
      component: Cindex
    },
    {
      path: '/C1',
      name: 'C1',
      component: C1
    },
    {
      path: '/C2',
      name: 'C2',
      component: C2
    },
    {
      path: '/C3',
      name: 'C3',
      component: C3
    },
    {
      path: '/C4',
      name: 'C4',
      component: C4
    },
    {
      path: '/managelogin',
      name: 'managelogin',
      component: managelogin
    },
    {
      path: '/manageindex',
      name: 'manageindex',
      component: manageindex
    },
    {
      path: '/M1',
      name: 'M1',
      component: M1
    },
    {
      path: '/M2',
      name: 'M2',
      component: M2
    },
    {
      path: '/M3',
      name: 'M3',
      component: M3
    },
    {
      path: '/M4',
      name: 'M4',
      component: M4
    },
    {
      path: '/index',
      name: 'AppIndex',
      component: AppIndex
    }
  ]
  // mode: 'history'
})
