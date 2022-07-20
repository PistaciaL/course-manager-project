import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '../store/index'
import Home from '@/views/Home'
import Login from '@/views/Login'
import Register from '@/views/Register'

Vue.use(VueRouter)

const routes = [{
    path: '/',
    name: 'index',
    component: Home
}, {
    path: '/login',
    name: 'login',
    component: Login
}, {
    path: '/register',
    name: 'register',
    component: Register
}, {
    path: '/home',
    name: 'home',
    component: Home,
    redirect: '/',
    children: [{
        path: 'page1',
        component: () =>
            import ("@/views/Page1")
    }, {
        path: 'page2',
        component: () =>
            import ("@/views/Page2")
    }]
}]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

// router.beforeEach((to, from, next) => {
//     console.log(to)
//     console.log(from)
//     console.log(next)
//     next()
// })

// router.beforeResolve((to, from, next) => {
//     if (to.fullPath != '/login' && !store.state.userInfo.isLogin) {
//         next('login')
//     } else {
//         next()
//     }
// })
const originalPush = VueRouter.prototype.push

VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
}

export default router;