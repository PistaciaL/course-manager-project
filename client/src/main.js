import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import axios from 'axios';
import jwt_decode from 'jwt-decode';
import RSA from '@/utils/rsa.js';
import MyUtils from "@/utils/myUtils.js"
import webSocket from './utils/webSocket';

axios.defaults.baseURL = "http://localhost:8080/api"
axios.interceptors.request.use(config => {
    //如果存在token,请求携带这个token
    if (localStorage.getItem('token') != null && localStorage.getItem('token') != '') {
        config.headers['Authorization'] = localStorage.getItem('token')
    }
    return config;
}, error => {
    console.log(error);
})
axios.interceptors.response.use(
    response => {
        const token = response.headers.authorization
        if (token != null) {
            localStorage.setItem('token', token)
        }
        return response
    },
    error => {
        return Promise.reject(error)
    }
)

Vue.prototype.axios = axios;
Vue.prototype.jwt_decode = jwt_decode;
Vue.prototype.RSA = RSA;
Vue.prototype.MyUtils = MyUtils;
Vue.prototype.$webSocket = webSocket;
Vue.config.productionTip = false;
Vue.use(ElementUI);

new Vue({
    router,
    store,
    jwt_decode,
    render: h => h(App)
}).$mount('#app')