import Vue from 'vue'
import '../assets/css/global.css'
import App from '../App'
import VueRouter from 'vue-router'
import Login from '../views/Login'
import Main from '../views/Main'
import Home from '../views/Home'
import User from '../views/User'
import Dept from '../views/Dept'
import Perm from '../views/Perm'
import Patient from '../views/Patient'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect: '/home'
  },
  {
    path: '/login',
    name: '登录',
    component: Login
  },
  {
    path: '/main',
    name: 'Main',
    component: Main,
    children: [
      {
        path: '/home',
        name: 'Home',
        component: Home
      },
      {
        path: '/user',
        name: 'User',
        component: User
      },
      {
        path: '/dept',
        name: 'Dept',
        component: Dept
      },
      {
        path: '/perm',
        name: 'Perm',
        component: Perm
      },
      {
        path: '/patient',
        name: 'Patient',
        component: Patient
      }
    ]
  }
]
const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  if(to.path === '/login') return next()
  const tokenStr = sessionStorage.getItem('token')
  if(!tokenStr) return next('/login')
  next()
})

export default router
