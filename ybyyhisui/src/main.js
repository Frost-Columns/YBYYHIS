import Vue from 'vue'
import './plugins/axios'
import App from './App.vue'
import router from './router'
import store from './store'
import './plugins/element.js'
import './assets/fonts/iconfont.css'
import axios from 'axios'
import moment from "moment";

Vue.prototype.$http = axios
axios.defaults.baseURL = 'http://localhost:8081'

Vue.config.productionTip = false

Vue.filter("dateFromat", function (date){
  return moment(date).format("YYYY-MM-DD HH:mm:ss");
})
Vue.filter("sexFromat",function (sex){
  return sex == 1 ? '男' : '女';
})

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
