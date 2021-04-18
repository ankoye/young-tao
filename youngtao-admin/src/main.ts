import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import './router/permission';


// element-ui
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(ElementUI);

// bootstrap
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.min.js';

// 自定义样式
import '@/style/index.scss';

// 日志
import logger from '@/common/utils/logger';
import Utils from '@/common/utils/utils';

Vue.prototype.$log = logger;
Vue.prototype.$utils = Utils;

declare module 'vue/types/vue' {
  interface Vue {
    $log: any,
    $utils: any
  }
}

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount('#app');
