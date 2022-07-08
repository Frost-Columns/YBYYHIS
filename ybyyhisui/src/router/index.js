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
import Case from '../views/Case'
import CaseInfo from "../views/CaseInfo"
import Cure from "@/views/Cure";
import CureLogs from "@/views/CureLogs";
import Dist from "@/views/Dist";
import Drugs from "@/views/Drugs";
import Recipel from "@/views/Recipel";
import Medicals from "@/views/Medicals";
import Register from "@/views/Register";
import CaseModify from "@/views/CaseModify";
import RegisterGroup from "@/views/RegisterGroup";
import CureCheck from "@/views/CureCheck";
import CureInfo from "@/views/CureInfo";
import CureMana from "@/views/CureMana";

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
      },
      {
        path: '/case',
        name: 'Case',
        component: Case
      },
      {
        path: '/case/info',
        name: 'CaseInfo',
        component: CaseInfo
      },
      {
        path: '/cure',
        name: 'Cure',
        component: Cure
      },
      {
        path: '/curelogs',
        name: 'CureLogs',
        component: CureLogs
      },
      {
        path: '/dist',
        name: 'Dist',
        component: Dist
      },
      {
        path: '/drugs',
        name: 'Drugs',
        component: Drugs
      },
      {
        path: '/recipel',
        name: 'Recipel',
        component: Recipel
      },
      {
        path: '/medicals',
        name: 'Medicals',
        component: Medicals
      },
      {
        path: '/register',
        name: 'Register',
        component: Register
      },
      {
        path: '/registerGroup',
        name: 'RegisterGroup',
        component: RegisterGroup
      },
      {
        path: '/curecheck',
        name: 'CureCheck',
        component: CureCheck
      },
      {
        path: '/curemana',
        name: 'CureMana',
        component: CureMana
      },
      {
        path: '/cureinfo',
        name: 'CureInfo',
        component: CureInfo
      },
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
