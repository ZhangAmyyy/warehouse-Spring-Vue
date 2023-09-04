import Vue from 'vue';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import locale from 'element-ui/lib/locale/lang/en'
import App from './App.vue';
import './assets/globalStyle.css';
import axios from 'axios';

import VueRouter from 'vue-router';
import router from "@/router/MyTemp";
import store from "@/store/index.js";


Vue.use(ElementUI, { locale });
Vue.prototype.$axios =axios;
Vue.prototype.$store =store;
Vue.prototype.$httpUrl='http://localhost:8090'

Vue.use(VueRouter);
//  Vue.use(store);



new Vue({
  router,
  Store:store,
  el: '#app',
  render: h => h(App)
}).$mount('#app')
