import Vue from 'vue'
import Router from 'vue-router'

// const _import = require('./_import_' + process.env.NODE_ENV)
// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
// detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading

Vue.use(Router)

/* Layout */
import Layout from '../views/layout/Layout'
import LayoutLevel from '../views/layout/Layout_level'
/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if `redirect:noredirect` will no redirct in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
  }
**/
export const constantRouterMap = [
  // {
  //   path: '/',
  //   component: Layout,
  //   redirect: '/login',
  //   name: 'login',
  //   hidden: true
  // },
  {
    path: '',
    component: Layout,
    redirect: '/dashboard/dashboard'
  },
  { path: '/login', component: () => import('@/views/login/'), name: '登录', hidden: true, meta: { keepAlive: false }},
  { path: '/dataView', component: () => import('@/views/dataView/home'), name: '数据概况', hidden: true },
  { path: '/404', component: () => import('@/views/errorPage/404'), hidden: true },
  { path: '/401', component: () => import('@/views/errorPage/401'), hidden: true },
  // 锁屏
  // {
  //   path: '/lock',
  //   hidden: true,
  //   name: '锁屏页',
  //   component: () => import('@/views/common/lock')
  // },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    name: '首页',
    hidden: true,
    meta: { title: '首页', icon: 'index', keepAlive: false },
    children: [
      {
        path: 'dashboard',
        name: '首页',
        component: () => import('@/views/dashboard/dashboard'),
      },
      {
        path: '/sitemap',
        name: '首页',
        component: () => import('@/views/sitemap/index')
      }
    ]
  },
  {
    path: '/dashboard',
    component: Layout,
    children: [
      {
        path: 'dashboard',
        name: 'dashboard',
        component: () => import('@/views/dashboard/dashboard'),
        meta: { title: 'index', icon: 'index' }
      }
    ]
  },
  // 报表
  // {
  //   path: '/dashboard',
  //   component: Layout,
  //   meta: { title: 'dashboard', icon: 'dashboard' },
  //   children: [
  //     {
  //       path: 'dashboard',
  //       name: 'dashboard',
  //       component: () => import('@/views/dashboard/dashboard'),
  //       meta: { title: 'dashboard', icon: 'index' }
  //     }
  //   ]
  // },


]

export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})
export const asyncRouterMap = [
  {
    path: '/pointposition',//点位管理
    component: Layout,
    redirect: '/pointposition/area',
    name: 'pointposition',
    meta: { title: 'pointposition', icon: 'example', roles: ['sys:point'] },
    children: [
      {
        path: '/pointposition/area',
        name: 'area',
        component: LayoutLevel,
        redirect: '/pointposition/area/area',
        //component: () => import('@/views/pointposition/area'),
        meta: { title: 'region', icon: 'area', roles: ['sys:point:district'], keepAlive: true },
        children: [
          {
            path: 'area',
            name: '',
            hidden: false,
            component: () => import('@/views/pointposition/area/area'),
            meta: { title: 'region', icon: 'area', keepAlive: true }
          },
          {
            path: '/pointposition/area/add',
            name: 'add',
            hidden: true,
            component: () => import('@/views/pointposition/area/add'),
            meta: { title: 'add', icon: 'add', keepAlive: false }
          },
          {
            path: '/pointposition/area/edit',
            name: 'edit',
            hidden: true,
            component: () => import('@/views/pointposition/area/edit'),
            meta: { title: 'edit', icon: 'edit', keepAlive: false }
          },

        ]
      },
      {
        path: '/pointposition/line',
        name: 'line',
        component: LayoutLevel,
        redirect: '/pointposition/line/line',
        //component: () => import('@/views/pointposition/area'),
        meta: { title: 'line', icon: 'line', roles: ['sys:point:line'], keepAlive: false },
        children: [
          {
            path: 'line',
            name: '',
            hidden: false,
            component: () => import('@/views/pointposition/line/line'),
            meta: { title: 'line', icon: 'line', keepAlive: false }
          },
          {
            path: '/pointposition/line/add',
            name: 'add',
            hidden: true,
            component: () => import('@/views/pointposition/line/add'),
            meta: { title: 'add', icon: 'add', keepAlive: false }
          },
          {
            path: '/pointposition/line/edit',
            name: 'edit',
            hidden: true,
            component: () => import('@/views/pointposition/line/edit'),
            meta: { title: 'edit', icon: 'edit', keepAlive: false }
          },

        ]
      },
      {
        path: '/pointposition/position',
        name: 'position',
        component: LayoutLevel,
        redirect: '/pointposition/position/position',
        //component: () => import('@/views/pointposition/area'),
        meta: { title: 'point', icon: 'point', roles: ['sys:point:point'], keepAlive: false },
        children: [
          {
            path: 'position',
            name: '',
            hidden: false,
            component: () => import('@/views/pointposition/position/position'),
            meta: { title: 'point', icon: 'point' }
          },
          {
            path: '/pointposition/position/add',
            name: 'add',
            hidden: true,
            component: () => import('@/views/pointposition/position/add'),
            meta: { title: 'add', icon: 'add' }
          },
          {
            path: '/pointposition/position/edit',
            name: 'edit',
            hidden: true,
            component: () => import('@/views/pointposition/position/edit'),
            meta: { title: 'edit', icon: 'edit' }
          }

        ]
      }
    ]
  },
  {
    path: '/equipment',//设备管理
    component: Layout,
    redirect: '/equipment/machine',
    name: 'equipment',
    meta: { title: 'equipment', icon: 'equipment', roles: ['sys:vending'] },
    children: [
      {//售卖机管理
        path: '/equipment/machine',
        component: LayoutLevel,
        name: 'machine',
        redirect: '/equipment/machine/index',
        // component: () => import('@/views/equipment/machine/index'),
        meta: { title: 'machine', icon: 'machine', roles: ['sys:vending:site'] },
        children: [
          {
            path: 'index',
            name: '',
            hidden: false,
            component: () => import('@/views/equipment/machine/index'),
            meta: { title: 'machine', icon: 'machine' }
          },
          {
            path: '/equipment/machine/add',
            name: 'add',
            hidden: true,
            component: () => import('@/views/equipment/machine/add'),
            meta: { title: 'add', icon: 'add' }
          },
          {
            path: '/equipment/machine/edit',
            name: 'edit',
            hidden: true,
            component: () => import('@/views/equipment/machine/edit'),
            meta: { title: 'edit', icon: 'edit' }
          },
          {
            path: '/equipment/machine/add_Cabinet',
            name: 'add',
            hidden: true,
            component: () => import('@/views/equipment/machine/add_Cabinet'),
            meta: { title: 'add', icon: 'add' }
          },
          {
            path: '/equipment/machine/edit_Cabinet',
            name: 'edit',
            hidden: true,
            component: () => import('@/views/equipment/machine/edit_Cabinet'),
            meta: { title: 'edit', icon: 'edit' }
          }
          , {
            path: '/equipment/machine/detail',
            name: 'details',
            component: LayoutLevel,
            redirect: '/equipment/machine',
            // redirect: '/equipment/machine/detail/details',
            hidden: true,
            // component: () => import('@/views/equipment/machine/details'),
            meta: { title: 'details', icon: 'details' },
            children: [
              {
                path: 'details',
                name: '',
                hidden: false,
                component: () => import('@/views/equipment/machine/detail/details'),
                meta: { title: 'details', icon: 'details' }
              }, {
                path: '/equipment/machine/detail/record/record',
                name: 'record',
                hidden: true,
                component: () => import('@/views/equipment/machine/detail/record/record'),
                meta: { title: 'record', icon: 'record' },
              }, {
                path: '/equipment/machine/detail/record/record_detals',
                name: 'record_detals',
                hidden: true,
                component: () => import('@/views/equipment/machine/detail/record/record_detals'),
                meta: { title: 'record_detals', icon: 'record_detals' },
              }, {
                path: '/equipment/machine/detail/toconfigure/toconfigure',
                name: 'toconfigure',
                hidden: true,
                component: () => import('@/views/equipment/machine/detail/toconfigure/to_configure'),
                meta: { title: 'to_configure', icon: 'to_configure' },
              }, {
                path: '/equipment/machine/detail/history/history',
                name: 'history',
                hidden: true,
                component: () => import('@/views/equipment/machine/detail/history/history'),
                meta: { title: 'history', icon: 'history' },
              }, {
                path: '/equipment/machine/detail/warning/warning',
                name: 'warning',
                hidden: true,
                component: () => import('@/views/equipment/machine/detail/warning/warning'),
                meta: { title: 'warning', icon: 'warning' },
              }, {
                path: '/equipment/machine/detail/stockingEdit/edit',
                name: 'edit',
                hidden: true,
                component: () => import('@/views/equipment/machine/detail/stockingEdit/edit'),
                meta: { title: 'edit', icon: 'edit' },
              }, {
                path: '/equipment/machine/detail/stockingEdit/list',
                name: 'list',
                hidden: true,
                component: () => import('@/views/equipment/machine/detail/stockingEdit/list'),
                meta: { title: 'stockingEdit', icon: 'stockingEdit' },
              }, {
                path: '/equipment/machine/detail/stockingEdit/detail',
                name: 'detail',
                hidden: true,
                component: () => import('@/views/equipment/machine/detail/stockingEdit/detail'),
                meta: { title: 'stockingEditDetail', icon: 'stockingEditDetail' },
              }, {
                path: '/equipment/machine/detail/logfile/list',
                name: 'list',
                hidden: true,
                component: () => import('@/views/equipment/machine/detail/logfile/list'),
                meta: { title: 'logfile', icon: 'logfile' },
              }

            ]
          }
        ]
      },
      {
        // 机型管理
        path: '/model',
        component: LayoutLevel,
        name: 'model',
        redirect: '/model/index',
        // component: () => import('@/views/equipment/model/index'),
        meta: { title: 'model', icon: 'model', roles: ['sys:vending:device'] },
        children: [
          {
            path: 'index',
            name: '',
            hidden: false,
            component: () => import('@/views/equipment/model/index'),
            meta: { title: 'model', icon: 'model' }
          },
          {
            path: 'addModel',
            name: 'addModel',
            hidden: true,
            component: () => import('@/views/equipment/model/addModel'),
            meta: { title: 'add_model', icon: 'add_model' }
          },
          {
            path: 'editModel',
            name: 'editModel',
            hidden: true,
            component: () => import('@/views/equipment/model/editModel'),
            meta: { title: 'editModel', icon: 'editModel' }
          }
        ]
      },
      {// 配货模版
        path: '/template',
        component: LayoutLevel,
        name: 'template',
        redirect: '/template/index',
        // component: () => import('@/views/equipment/model/index'),
        meta: { title: 'template', icon: 'template', roles: ['sys:vending:mconfig'] },
        children: [
          {
            path: 'index',
            name: '',
            hidden: false,
            component: () => import('@/views/equipment/template/index'),
            meta: { title: 'template', icon: 'template' }
          },
          {
            path: 'addTemplate',
            name: 'addTemplate',
            hidden: true,
            component: () => import('@/views/equipment/template/addTemplate'),
            meta: { title: 'addTemplate', icon: 'addTemplate' }
          },
          {
            path: 'editTemplate',
            name: 'editTemplate',
            hidden: true,
            component: () => import('@/views/equipment/template/editTemplate'),
            meta: { title: 'editTemplate', icon: 'editTemplate' }
          }
        ]
      },
      {// 远程控制
        path: '/equipment/remote',
        component: LayoutLevel,
        name: 'remote',
        redirect: '/equipment/remote/index',
        // component: () => import('@/views/equipment/model/index'),
        meta: { title: 'remote', icon: 'remote', roles: ['sys:vending:remote'] },
        children: [
          {
            path: 'index',
            name: '',
            hidden: false,
            component: () => import('@/views/equipment/remote/index'),
            meta: { title: 'remote', icon: 'remote' }
          },
          {
            path: 'detail',
            name: 'detail',
            hidden: true,
            component: () => import('@/views/equipment/remote/detail'),
            meta: { title: 'details', icon: 'details' }
          }
        ]
      },
      {// 升级管理
        path: '/equipment/upgrade',
        component: LayoutLevel,
        name: 'upgrade',
        redirect: '/equipment/upgrade/index',
        // component: () => import('@/views/equipment/model/index'),
        meta: { title: 'upgrade', icon: 'upgrade', roles: ['sys:vending:upgrade'] },
        children: [
          {
            path: 'index',
            name: '',
            hidden: false,
            component: () => import('@/views/equipment/upgrade/index'),
            meta: { title: 'upgrade', icon: 'upgrade' }
          },
          {
            path: 'detail',
            name: 'detail',
            hidden: true,
            component: () => import('@/views/equipment/upgrade/detail'),
            meta: { title: 'details', icon: 'details' }
          }
        ]
      },
      {// 远程控制
        path: '/equipment/version',
        component: LayoutLevel,
        name: 'version',
        redirect: '/equipment/version/index',
        // component: () => import('@/views/equipment/model/index'),
        meta: { title: 'version', icon: 'version', roles: ['sys:vending:version'] },
        children: [
          {
            path: 'index',
            name: '',
            hidden: false,
            component: () => import('@/views/equipment/version/index'),
            meta: { title: 'version', icon: 'version' }
          },
          {
            path: 'detail',
            name: 'detail',
            hidden: true,
            component: () => import('@/views/equipment/version/detail'),
            meta: { title: 'details', icon: 'details' }
          },
          {
            path: 'addVersion',
            name: 'addVersion',
            hidden: true,
            component: () => import('@/views/equipment/version/addVersion'),
            meta: { title: 'addVersion', icon: 'addVersion' }
          },
          {
            path: 'editVersion',
            name: 'editVersion',
            hidden: true,
            component: () => import('@/views/equipment/version/editVersion'),
            meta: { title: 'editVersion', icon: 'editVersion' }
          }
        ]
      }
    ]
  },
  {
    path: '/monitor',// 设备监控
    component: Layout,
    redirect: '/monitor/warningList',
    name: 'monitor',
    meta: { title: 'monitor', icon: 'monitor', roles: ['sys:event'] },
    children: [
      {
        path: 'warningList',
        name: 'warning',
        component: () => import('@/views/monitor/warningList'),
        meta: { title: 'warning', icon: 'warning', roles: ['sys:event:warn'] }
      },
      {
        path: 'eventList',
        name: 'eventList',
        component: () => import('@/views/monitor/eventList'),
        meta: { title: 'event', icon: 'event', roles: ['sys:event:event'] }
      }
    ]
  },
  {
    path: '/replenishment', // 补货管理
    component: Layout,
    redirect: '/replenishment/outOfStock',
    name: 'replenishment',
    meta: { title: 'replenishment', icon: 'replenishment', roles: ['sys:supply'] },
    children: [
      {
        path: '/replenishment/outOfStock',
        component: LayoutLevel,
        name: 'outOfStock',
        redirect: '/replenishment/outOfStock/index',
        meta: { title: 'outOfStock', icon: 'outOfStock', roles: ['sys:supply:warn'] },
        children: [
          {
            path: 'index',
            name: '',
            hidden: false,
            component: () => import('@/views/replenishment/outOfStock/index'),
            meta: { title: 'outOfStock', icon: 'outOfStock' }
          }, {
            path: '/replenishment/outOfStock/seeLine',
            name: 'seeLine',
            hidden: true,
            component: () => import('@/views/replenishment/outOfStock/seeLine'),
            meta: { title: 'seeLine', icon: 'seeLine' },
          }, {
            path: '/replenishment/outOfStock/seeDian',
            name: 'seeLine',
            hidden: true,
            component: () => import('@/views/replenishment/outOfStock/seeDian'),
            meta: { title: 'seeDian', icon: 'seeDian' },
          }
        ]
      },
      {
        path: '/replenishment/addTo',
        component: LayoutLevel,
        redirect: 'index',
        name: 'addTo',
        // component: () => import('@/views/replenishment/addTo/index'),
        meta: { title: 'addTo', icon: 'addTo', roles: ['sys:supply:config'] },
        children: [
          {
            path: 'index',
            hidden: false,
            name: '',
            component: () => import('@/views/replenishment/addTo/index'),
            meta: { title: 'addTo', icon: 'addTo' },
          },
          {
            hidden: true,
            path: 'addLine',
            name: 'addLine',
            component: () => import('@/views/replenishment/addTo/addLine'),
            meta: { title: 'addLine', icon: 'addLine' },
          },
          {
            hidden: true,
            path: 'addUrgent',
            name: 'addUrgent',
            component: () => import('@/views/replenishment/addTo/addUrgent'),
            meta: { title: 'addUrgent', icon: 'addUrgent' },
          },
          {
            hidden: true,
            path: 'editUrgent',
            name: 'editUrgent',
            component: () => import('@/views/replenishment/addTo/editUrgent'),
            meta: { title: 'editUrgent' },
          },
          {
            hidden: true,
            path: 'seeUrgent',
            name: 'editUrgent',
            component: () => import('@/views/replenishment/addTo/seeUrgent'),
            meta: { title: 'seeUrgent' },
          }
        ]
      },
      {
        path: '/goodsRecord',
        component: LayoutLevel,
        redirect: 'index',
        name: 'goodsRecord',
        // component: () => import('@/views/replenishment/addTo/index'),
        meta: { title: 'goodsRecord', icon: 'goodsRecord', roles: ['sys:supply:order'] },
        children: [
          {
            path: 'index',
            hidden: false,
            name: '',
            component: () => import('@/views/replenishment/goodsRecord/index'),
            meta: { title: 'goodsRecord', icon: 'goodsRecord' },
          },
          {
            hidden: true,
            path: '/goodsRecord/orderdetail',
            name: 'orderdetail',
            component: () => import('@/views/replenishment/goodsRecord/orderdetail'),
            meta: { title: 'orderdetail', icon: 'orderdetail' },
          },

        ]
      }
    ]
  },
  {
    // 仓库管理
    path: '/stock',
    component: Layout,
    redirect: '/stock/stockProduct',
    name: 'stock',
    meta: { title: 'stock', icon: 'stock', roles: ['sys:stock'] },
    children: [
      {
        path: '/stock/stockProduct',
        name: 'stockProduct',
        component: LayoutLevel,
        redirect: '/stock/stockProduct/list',
        meta: { title: 'stockProduct', icon: 'stockProduct', roles: ['sys:stock:product'] },
        children: [
          {
            path: 'list',
            name: '',
            hidden: false,
            component: () => import('@/views/stock/stockProduct/list'),
            meta: { title: 'stockProduct', icon: 'stockProduct' }
          }

        ]
      },
      {
        path: '/stock/stockWarehouse',
        name: 'stockWarehouse',
        component: LayoutLevel,
        redirect: '/stock/stockWarehouse/listSurvey',
        meta: { title: 'stockWarehouse', icon: 'stockWarehouse', roles: ['sys:stock:warehouse'] },
        children: [
          {
            path: 'listSurvey',
            name: '',
            hidden: false,
            component: () => import('@/views/stock/stockWarehouse/listSurvey'),
            meta: { title: 'stockWarehouse', icon: 'stockWarehouse' }
          },
          {
            path: '/stock/stockWarehouse/list',
            name: 'list',
            hidden: true,
            component: () => import('@/views/stock/stockWarehouse/list'),
            meta: { title: '', icon: 'add' }
          }

        ]
      },
      {
        path: '/stock/stockInfo',
        name: 'stockInfo',
        component: LayoutLevel,
        redirect: '/stock/stockInfo/list',
        meta: { title: 'stockInfo', icon: 'stockInfo', roles: ['sys:stock:info'] },
        children: [
          {
            path: 'list',
            name: '',
            hidden: false,
            component: () => import('@/views/stock/stockInfo/list'),
            meta: { title: 'stockInfo', icon: 'stockInfo' }
          },
          {
            path: 'add',
            name: 'add',
            hidden: true,
            component: () => import('@/views/stock/stockInfo/add'),
            meta: { title: 'add' }
          },
          {
            path: 'edit',
            name: 'edit',
            hidden: true,
            component: () => import('@/views/stock/stockInfo/edit'),
            meta: { title: 'edit' }
          }
        ]
      },
      {
        path: '/stock/stockPurchase',
        name: 'stockPurchase',
        component: LayoutLevel,
        redirect: '/stock/stockPurchase/list',
        meta: { title: 'stockPurchase', icon: 'stockPurchase', roles: ['sys:stock:purchase'] },
        children: [
          {
            path: 'list',
            name: '',
            hidden: false,
            component: () => import('@/views/stock/stockPurchase/list'),
            meta: { title: 'stockPurchase', icon: 'stockPurchase' }
          },
          {
            path: 'toExamine',
            name: 'toExamine',
            hidden: true,
            component: () => import('@/views/stock/stockPurchase/toExamine'),
            meta: { title: 'toExamine', icon: 'toExamine' }
          },
          {
            path: 'see',
            name: 'see',
            hidden: true,
            component: () => import('@/views/stock/stockPurchase/see'),
            meta: { title: 'see', icon: 'see' }
          }

        ]
      },
      {
        path: '/stock/stockInbound',
        name: 'stockInbound',
        component: LayoutLevel,
        redirect: '/stock/stockInbound/list',
        meta: { title: 'stockInbound', icon: 'stockInbound', roles: ['sys:stock:inbound'] },
        children: [
          {
            path: 'list',
            name: '',
            hidden: false,
            component: () => import('@/views/stock/stockInbound/list'),
            meta: { title: 'stockInbound', icon: 'stockInbound' }
          },
          {
            path: 'see',
            name: 'see',
            hidden: true,
            component: () => import('@/views/stock/stockInbound/see'),
            meta: { title: 'see', icon: 'see' }
          }

        ]
      },
      {
        path: '/stock/stockOut',
        name: 'stockOut',
        component: LayoutLevel,
        redirect: '/stock/stockOut/list',
        meta: { title: 'stockOut', icon: 'stockOut', roles: ['sys:stock:outbound'] },
        children: [
          {
            path: 'list',
            name: '',
            hidden: false,
            component: () => import('@/views/stock/stockOut/list'),
            meta: { title: 'stockOut', icon: 'stockOut' }
          },
          {
            path: 'see',
            name: 'see',
            hidden: true,
            component: () => import('@/views/stock/stockOut/see'),
            meta: { title: 'see', icon: 'see' }
          }

        ]
      }
    ]
  },
  {
    path: '/reconciliation', // 对账管理
    component: Layout,
    redirect: '/reconciliation/goodsRlt',
    name: 'reconciliation',
    meta: { title: 'reconciliation', icon: 'reconciliation', roles: ['sys:statement'], keepAlive: true },
    children: [
      {
        path: '/reconciliation/goodsRlt',
        name: 'goodsRlt',
        component: LayoutLevel,
        redirect: '/reconciliation/goodsRlt/index',
        meta: { title: 'goodsRlt', icon: 'goodsRlt', roles: ['sys:statement:supply'] },
        children: [
          {
            path: 'index',
            name: '',
            hidden: false,
            component: () => import('@/views/reconciliation/goodsRlt/index'),
            meta: { title: 'goodsRlt', icon: 'goodsRlt' }
          },
          {
            path: '/reconciliation/goodsRlt/details',
            name: 'details',
            hidden: true,
            component: () => import('@/views/reconciliation/goodsRlt/details'),
            meta: { title: 'details' }
          },
          {
            path: '/reconciliation/goodsRlt/reconciliations',
            name: 'reconciliations',
            hidden: true,
            component: () => import('@/views/reconciliation/goodsRlt/reconciliations'),
            meta: { title: 'reconciliations' }
          }
        ]
      },
      {
        path: '/reconciliation/orderRlt',
        name: 'orderRlt',
        component: LayoutLevel,
        redirect: '/reconciliation/orderRlt/index',
        meta: { title: 'orderRlt', icon: 'orderRlt', roles: ['sys:statement:order'], keepAlive: true },
        children: [
          {
            path: 'index',
            name: '',
            hidden: false,
            component: () => import('@/views/reconciliation/orderRlt/index'),
            meta: { title: 'orderRlt', icon: 'orderRlt', keepAlive: true }
          },
          {
            path: '/reconciliation/orderRlt/details',
            name: 'details',
            hidden: true,
            component: () => import('@/views/reconciliation/orderRlt/details'),
            meta: { title: 'details', keepAlive: true }
          }

        ]
      }
    ]
  },
  {
    path: '/analysis', // 数据分析
    component: Layout,
    redirect: '/analysis/order',
    name: 'analysis',
    meta: { title: 'analysis', icon: 'analysis', roles: ['sys:rpt'] },
    children: [
      {
        path: '/analysis/order',
        name: 'order',
        component: LayoutLevel,
        redirect: '/analysis/order/list',
        meta: { title: 'order', icon: 'order', roles: ['sys:rpt:order'] },
        children: [
          {
            path: 'list',
            name: '',
            hidden: false,
            component: () => import('@/views/analysis/order/list'),
            meta: { title: 'order', icon: 'order' }
          },
          {
            path: 'list_new',
            name: '',
            hidden: false,
            component: () => import('@/views/analysis/order/list_new'),
            meta: { title: 'order', icon: 'order' }
          }
        ]
      },
      {
        path: '/analysis/ranking',
        name: 'ranking',
        component: LayoutLevel,
        redirect: '/analysis/ranking/list',
        // component: () => import('@/views/pointposition/area'),
        meta: { title: 'ranking', icon: 'ranking', roles: ['sys:rpt:sale'] },
        children: [
          {
            path: 'list',
            name: '',
            hidden: false,
            component: () => import('@/views/analysis/ranking/list'),
            meta: { title: 'ranking', icon: 'ranking' }
          },
          {
            path: 'list_new',
            name: '',
            hidden: false,
            component: () => import('@/views/analysis/ranking/list_new'),
            meta: { title: 'ranking', icon: 'ranking' }
          }
        ]
      },
      {
        path: '/analysis/reportForm',
        name: 'reportForm',
        component: LayoutLevel,
        redirect: '/analysis/reportForm/list',
        meta: { title: 'reportForm', icon: 'reportForm', roles: ['sys:rpt:report'] },
        children: [
          {
            path: 'list',
            name: '',
            hidden: false,
            component: () => import('@/views/analysis/reportForm/list'),
            meta: { title: 'reportForm', icon: 'reportForm' }
          }
        ]
      },

    ]
  },
  {
    path: '/productInfo', // 商品管理
    component: Layout,
    redirect: '/productInfo/goods',
    name: 'productInfo',
    meta: { title: 'productInfo', icon: 'productInfo', roles: ['sys:product'] },
    children: [
      {
        path: '/productInfo/goods',
        name: 'goods',
        component: LayoutLevel,
        redirect: '/productInfo/goods/list',
        meta: { title: 'productInfoList', icon: 'productInfoList' },
        children: [
          {
            path: 'list',
            name: '',
            hidden: false,
            component: () => import('@/views/productInfo/goods/list'),
            meta: { title: 'goods', icon: 'goods' }
          },
          {
            path: '/productInfo/goods/add',
            name: 'add',
            hidden: true,
            component: () => import('@/views/productInfo/goods/add'),
            meta: { title: 'add', icon: 'add' }
          },
          {
            path: '/productInfo/goods/edit',
            name: 'edit',
            hidden: true,
            component: () => import('@/views/productInfo/goods/edit'),
            meta: { title: 'edit', icon: 'edit' }
          }
        ]
      },
      {
        path: '/productInfo/onLineProduct',
        name: 'fromSale',
        component: LayoutLevel,
        redirect: '/productInfo/onLineProduct/list',
        meta: { title: 'onLineProduct', icon: 'onLineProduct' },
        children: [
          {
            path: 'list',
            name: '',
            hidden: false,
            component: () => import('@/views/productInfo/onLineProduct/list'),
            meta: { title: 'onLineProduct', icon: 'onLineProduct' }
          }

        ]
      },
      {
        path: '/productInfo/fromSale',
        name: 'fromSale',
        component: LayoutLevel,
        redirect: '/productInfo/fromSale/list',
        meta: { title: 'fromSale', icon: 'fromSale' },
        children: [
          {
            path: 'list',
            name: '',
            hidden: false,
            component: () => import('@/views/productInfo/fromSale/list'),
            meta: { title: 'fromSale', icon: 'fromSale' }
          }

        ]
      }


    ]
  },
  {
    path: '/adv', // 广告增值
    component: Layout,
    redirect: '/adv/MMedia',
    name: 'adv',
    meta: { title: 'adv', icon: 'adv', roles: ['sys:advert'] },
    children: [
      {
        path: '/adv/MMedia',
        name: 'MMedia',
        component: LayoutLevel,
        redirect: '/adv/MMedia/list',
        meta: { title: 'MMedia', icon: 'adv', roles: ['sys:advert:material'] },
        children: [
          {
            path: 'list',
            name: '',
            hidden: false,
            component: () => import('@/views/adv/MMedia/list'),
            meta: { title: 'MMedia', icon: 'MMedia', }
          }

        ]
      },
      {
        path: '/adv/Launch',
        name: 'Launch',
        component: LayoutLevel,
        redirect: '/adv/Launch/list',
        meta: { title: 'Launch', icon: 'Launch', roles: ['sys:advert:device'] },
        children: [
          {
            path: 'list',
            name: '',
            hidden: false,
            component: () => import('@/views/adv/Launch/list'),
            meta: { title: 'Launch', icon: 'Launch' }
          },
          // {
          //   path: '/pointposition/line/add',
          //   name: 'add',
          //   hidden: true,
          //   component: () => import('@/views/pointposition/line/add'),
          //   meta: { title: 'add', icon: 'add' }
          // },


        ]
      },
      {
        path: '/adv/Discount',
        name: 'Discount',
        component: LayoutLevel,
        redirect: '/adv/Discount/index',
        meta: { title: 'Discount', icon: 'Discount', roles: ['sys:advert:favourable'] },
        children: [
          {
            path: 'index',
            name: '',
            hidden: false,
            component: () => import('@/views/adv/Discount/index'),
            meta: { title: 'Discount', icon: 'Discount' }
          },
          {
            path: '/adv/Discount/buy/list',
            name: 'buy',
            hidden: true,
            component: () => import('@/views/adv/Discount/buy/list'),
            meta: { title: 'buy', icon: 'buy' }
          },
          {
            path: '/adv/Discount/consumption/list',
            name: 'consumption',
            hidden: true,
            component: () => import('@/views/adv/Discount/consumption/list'),
            meta: { title: 'consumption', icon: 'consumption' }
          },


        ]
      },

    ]
  },
  {
    path: '/systemSetup', // 系统设置
    component: Layout,
    redirect: '/systemSetup/user',
    name: 'user',
    meta: {
      title: 'systemSetup',
      icon: 'systemSetup',
      roles: ['sys:base']
    },
    children: [
      {
        path: '/systemSetup/user',
        name: 'user',
        component: LayoutLevel,
        redirect: '/systemSetup/user/index',
        meta: {
          title: 'userMgt',
          icon: 'userMgt',
          roles: ['sys:base:user']
        },
        children: [
          {
            path: 'index',
            name: '',
            hidden: false,
            component: () => import('@/views/systemSetup/user/index'),
            meta: { title: 'userMgt', icon: 'userMgt' }
          },
          {
            path: 'add',
            name: '',
            hidden: true,
            component: () => import('@/views/systemSetup/user/add'),
            meta: { title: 'add' }
          },
          {
            path: 'edit',
            name: '',
            hidden: true,
            component: () => import('@/views/systemSetup/user/edit'),
            meta: { title: 'edit' }
          },{
            path: 'editPass',
            name: '',
            hidden: true,
            component: () => import('@/views/systemSetup/editPass/edit'),
            meta: { title: 'editPass' }
          }
        ]
      },
      {
        path: '/systemSetup/role',
        name: 'role',
        component: LayoutLevel,
        redirect: '/systemSetup/role/index',
        meta: {
          title: 'role',
          icon: 'role',
          roles: ['sys:base:role']
        },
        children: [
          {
            path: 'index',
            name: '',
            hidden: false,
            component: () => import('@/views/systemSetup/role/index'),
            meta: { title: 'role', icon: 'role' }
          },
          {
            path: 'add',
            name: '',
            hidden: true,
            component: () => import('@/views/systemSetup/role/add'),
            meta: { title: 'add' }
          },
          {
            path: 'edit',
            name: '',
            hidden: true,
            component: () => import('@/views/systemSetup/role/edit'),
            meta: { title: 'edit' }
          },
        ]
      },
      {
        path: '/systemSetup/dict',
        name: 'dict',
        component: LayoutLevel,
        redirect: '/systemSetup/dict/index',
        meta: {
          title: 'dict',
          icon: 'dict',
          roles: ['sys:base:dict']
        },
        children: [
          {
            path: 'index',
            name: '',
            hidden: false,
            component: () => import('@/views/systemSetup/dict/index'),
            meta: { title: 'dict', icon: 'dict' }
          },
          {
            path: 'add',
            name: 'add',
            hidden: true,
            component: () => import('@/views/systemSetup/dict/add'),
            meta: { title: 'add' }
          },
          {
            path: 'edit',
            name: 'edit',
            hidden: true,
            component: () => import('@/views/systemSetup/dict/edit'),
            meta: { title: 'edit' }
          },
        ]
      },
      {
        path: '/systemSetup/corp',
        name: 'corp',
        component: LayoutLevel,
        redirect: '/systemSetup/corp/index',
        meta: { title: 'corp', icon: 'corp', roles: ['sys:base:corp'] },
        children: [
          {
            path: 'index',
            name: '',
            hidden: false,
            component: () => import('@/views/systemSetup/corp/index'),
            meta: { title: 'corp', icon: 'corp' }
          },
          {
            path: 'add',
            name: 'add',
            hidden: true,
            component: () => import('@/views/systemSetup/corp/add'),
            meta: { title: 'add' }
          },
          {
            path: 'edit',
            name: 'edit',
            hidden: true,
            component: () => import('@/views/systemSetup/corp/edit'),
            meta: { title: 'edit' }
          },
        ]
      },
      {
        path: '/systemSetup/setPay',
        name: 'setPay',
        component: LayoutLevel,
        redirect: '/systemSetup/setPay/index',
        meta: { title: 'setPay', icon: 'setPay', roles: ['sys:base:pay'] },
        children: [
          {
            path: 'index',
            name: '',
            hidden: false,
            component: () => import('@/views/systemSetup/setPay/index'),
            meta: { title: 'setPay', icon: 'setPay' }
          }
        ]
      },
      {
        path: '/systemSetup/instruction',
        name: 'instruction',
        component: LayoutLevel,
        redirect: '/systemSetup/instruction/index',
        meta: { title: 'instruction', icon: 'instruction', roles: ['sys:base:instruction'] },
        children: [
          {
            path: 'index',
            name: '',
            hidden: false,
            component: () => import('@/views/systemSetup/instruction/index'),
            meta: { title: 'instruction', icon: 'instruction' }
          }
        ]
      }
    ]
  },
  // {
  //   path: '/permission',
  //   component: Layout,
  //   redirect: '/permission/page',
  //   name: 'permission',
  //   alwaysShow: true, // will always show the root menu
  //   meta: {
  //     title: 'permission',
  //     icon: 'lock',
  //     //roles: ['admin', 'editor'] // you can set roles in root nav
  //   },
  //   children: [{
  //     path: 'page',
  //     component: () => import('@/views/permission/page'),
  //     name: 'pagePermission',
  //     meta: {
  //       title: 'pagePermission',
  //      // roles: ['admin'] // or you can only set roles in sub nav
  //     }
  //   }, {
  //     path: 'directive',
  //     component: () => import('@/views/permission/directive'),
  //     name: 'directivePermission',
  //     meta: {
  //       title: 'directivePermission'
  //       // if do not set roles, means: this page does not require permission
  //     }
  //   }]
  // },
  // 错误日志
  // {
  //   path: '/errorLog',
  //   component: Layout,

  //   children: [
  //     {
  //       path: 'errorLog',
  //       name: 'errorLog',
  //       component: () => import('@/views/errorLog/errorLog'),
  //       meta: { title: 'Errorlog', icon: 'errorLog' }
  //     }
  //   ]
  // },
  { path: '*', redirect: '/404', hidden: true }]
