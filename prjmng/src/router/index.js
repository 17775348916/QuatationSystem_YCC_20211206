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
import B21 from '../components/Business/B21'
import B22 from '../components/Business/B22'
import TUnevaluated from '../components/Tech/TUnevaluated'
import TEvaluated from '../components/Tech/TEvaluated'
import CEvaluated from '../components/Customer-service/CEvaluated'
import CUnevaluated from '../components/Customer-service/CUnevaluated'
import CNeedCommunicateWithOrders from '../components/Customer-service/CNeedCommunicateWithOrders'
import CFinishCommunicateWithOrders from '../components/Customer-service/CFinishCommunicateWithOrders'
import CFinishCommunicateWithTakeOrders from '../components/Customer-service/CFinishCommunicateWithTakeOrders'
import CNeedCommunicateWithTakeOrders from '../components/Customer-service/CNeedCommunicateWithTakeOrders'
import BUnavailable from '../components/Business/BUnavailable'
import M5 from '../components/manage/M5'

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
      component: Bindex,
      children: [
        {
          path: '/B1',
          name: '项目录入',
          component: B1
        },
        {
          path: '/B2',
          name: 'B2',
          component: B2
        },
        {
          path: '/B21',
          name: '技术未评估项目信息',
          component: B21
        },
        {
          path: '/B22',
          name: '技术已评估项目信息',
          component: B22
        },
        {
          path: '/BUnavailable',
          name: '尚无成交可能项目',
          component: BUnavailable
        }
      ]
    },

    {
      path: '/Tindex',
      name: 'Tindex',
      component: Tindex,
      children: [
        {
          path: '/TEvaluated',
          name: '技术已评估项目',
          component: TEvaluated
        },
        {
          path: '/TUnevaluated',
          name: '技术未评估项目',
          component: TUnevaluated
        },
        {
          path: '/T1',
          name: '确定原料',
          component: T1
        },
        {
          path: '/T2',
          name: '技术可行性分析',
          component: T2
        }
      ]
    },

    {
      path: '/Cindex',
      name: 'Cindex',
      component: Cindex,
      children: [
        {
          path: '/C1',
          name: '原料信息',
          component: C1
        },
        {
          path: '/C2',
          name: '原料数据库维护',
          component: C2
        },
        {
          path: '/C3',
          name: 'C3',
          component: C3
        },
        {
          path: '/C4',
          name: '接单客户沟通',
          component: C4
        },
        {
          path: '/CEvaluated',
          name: '技术已评估',
          component: CEvaluated
        },
        {
          path: '/CUnevaluated',
          name: '技术未评估',
          component: CUnevaluated
        },
        {
          path: '/CNeedCommunicateWithOrders',
          name: '需要与下单客户沟通的项目',
          component: CNeedCommunicateWithOrders
        },
        {
          path: '/CFinishCommunicateWithOrders',
          name: '已经与下单客户完成沟通的项目',
          component: CFinishCommunicateWithOrders
        },
        {
          path: '/CNeedCommunicateWithTakeOrders',
          name: '需要与接单客户完成沟通的项目',
          component: CNeedCommunicateWithTakeOrders
        },
        {
          path: '/CFinishCommunicateWithTakeOrders',
          name: '已经与接单客户完成沟通的项目',
          component: CFinishCommunicateWithTakeOrders
        }
      ]
    },

    {
      path: '/managelogin',
      name: 'managelogin',
      component: managelogin
    },
    {
      path: '/manageindex',
      name: 'manageindex',
      component: manageindex,
      children: [
        {
          path: '/M1',
          name: '用户账号管理',
          component: M1
        },
        {
          path: '/M2',
          name: '历史项目查看',
          component: M2
        },
        {
          path: '/M3',
          name: '报价模式管理',
          component: M3
        },
        {
          path: '/M4',
          name: '原料数据库管理1',
          component: M4
        },
        {
          path: '/M5',
          name: '原料数据库管理',
          component: M5
        }
      ]
    },
    {
      path: '/index',
      name: 'AppIndex',
      component: AppIndex
    }
  ]
  // mode: 'history'
})
