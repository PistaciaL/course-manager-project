import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '@/store/index'
import Back from '@/views/back/Back'
import Login from '@/views/Login'
import Register from '@/views/Register'
import Home from '@/views/Home'
import jwtDecode from 'jwt-decode'

Vue.use(VueRouter)

const routes = [{
    path: '/',
    name: 'home',
    component: Home,
    beforeEnter: (to, from, next) => {
        // reject the navigation
        next()
    },
}, {
    path: '/login',
    name: 'login',
    component: Login
}, {
    path: '/register',
    name: 'register',
    component: Register
}, {
    path: '/back',
    component: Back,
    children: [{
            path: '',
            component: () =>
                import ("@/views/back/Index")
        }, {
            path: 'notice',
            component: () =>
                import ("@/views/back/Notice")
        }, {
            path: 'info',
            component: () =>
                import ("@/views/back/MyInfo")
        }, {
            path: 'course',
            component: () =>
                import ("@/views/back/Course")
        },
        //选课系统
        {
            path: 'select',
            beforeEnter: (to, from, next) => {
                if (isStudent() || isCollegeManager()) {
                    next('/back/select/totalcourse')
                } else {
                    next('/back')
                }
            },
        }, {
            path: 'select/totalcourse',
            component: () =>
                import ("@/views/back/selectCourse/TotalCourse")
        }, {
            path: 'select/myselect',
            component: () =>
                import ("@/views/back/selectCourse/MySelect"),
            beforeEnter: (to, from, next) => {
                if (isStudent()) {
                    next()
                } else {
                    next('/back')
                }
            },
        }, {
            path: 'select/mywithdraw',
            component: () =>
                import ("@/views/back/selectCourse/MyWithdraw"),
            beforeEnter: (to, from, next) => {
                if (isStudent()) {
                    next()
                } else {
                    next('/back')
                }
            },
        }, {
            path: 'select/mysupplementary',
            component: () =>
                import ("@/views/back/selectCourse/MySupplementary"),
            beforeEnter: (to, from, next) => {
                if (isStudent()) {
                    next()
                } else {
                    next('/back')
                }
            },
        }, {
            path: 'select/manager',
            component: () =>
                import ("@/views/back/selectCourse/Manager"),
            beforeEnter: (to, from, next) => {
                if (isSchoolManager()) {
                    next()
                } else {
                    next('/back')
                }
            },
        },

        {
            path: 'exam',
            component: () =>
                import ("@/views/back/Exam")
        },
        // 学业指导计划
        {
            path: 'guide',
            beforeEnter: (to, from, next) => {
                if (isStudent()) {
                    next('/back/guide/myguide')
                } else if (isCollegeManager()) {
                    next('/back/guide/manager')
                } else {
                    next('/back')
                }
            },
        }, {
            path: 'guide/myguide',
            component: () =>
                import ("@/views/back/guide/MyGuide"),
            beforeEnter: (to, from, next) => {
                if (isStudent()) {
                    next()
                } else {
                    next('/back')
                }
            },
        }, {
            path: 'guide/manager',
            component: () =>
                import ("@/views/back/guide/Manager"),
            beforeEnter: (to, from, next) => {
                if (isCollegeManager()) {
                    next()
                } else {
                    next('/back')
                }
            },
        },

        {
            path: 'apply',
            component: () =>
                import ("@/views/back/Apply"),
            beforeEnter: (to, from, next) => {
                if (isTeacher() || isCollegeManager() || isSchoolManager()) {
                    next()
                } else {
                    next('/back')
                }
            },
        },
        // 管理员界面
        {
            path: 'usermng',
            beforeEnter: (to, from, next) => {
                if (isSchoolManager() || isCollegeManager()) {
                    next()
                } else {
                    next('/back')
                }
            },
        }, {
            path: 'roommng',
            component: () =>
                import ("@/views/back/manager/Room"),
            beforeEnter: (to, from, next) => {
                if (isSchoolManager()) {
                    next()
                } else {
                    next('/back')
                }
            },
        }, {
            path: 'coursemng',
            component: () =>
                import ("@/views/back/manager/Course"),
            beforeEnter: (to, from, next) => {
                if (isSchoolManager() || isCollegeManager()) {
                    next()
                } else {
                    next('/back')
                }
            },
        }, {
            path: 'termmng',
            component: () =>
                import ("@/views/back/manager/Term"),
            beforeEnter: (to, from, next) => {
                if (isSchoolManager()) {
                    next()
                } else {
                    next('/back')
                }
            },
        }, {
            path: 'subjectmng',
            component: () =>
                import ("@/views/back/manager/Subject"),
            beforeEnter: (to, from, next) => {
                if (isSchoolManager() || isCollegeManager()) {
                    next()
                } else {
                    next('/back')
                }
            },
        }, {
            path: 'collegemng',
            component: () =>
                import ("@/views/back/manager/College"),
            beforeEnter: (to, from, next) => {
                if (isSchoolManager() || isCollegeManager()) {
                    next()
                } else {
                    next('/back')
                }
            },
        }, {
            path: 'totalmng',
            component: () =>
                import ("@/views/back/manager/Total"),
            beforeEnter: (to, from, next) => {
                if (isSchoolManager() || isCollegeManager()) {
                    next()
                } else {
                    next('/back')
                }
            },
        },
    ]
}, {
    path: '/rules',
    component: () =>
        import ("@/views/rules/Rule"),
    children: [{
        path: '',
        component: () =>
            import ('@/views/rules/Index')
    }, {
        path: 'courseselect',
        component: () =>
            import ("@/views/rules/CourseSelect"),
    }, {
        path: 'guide',
        component: () =>
            import ("@/views/rules/Guide")
    }]
}]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

// 自定义函数
const isStudent = () => {
    return localStorage.getItem('identity') === "学生"
}
const isTeacher = () => {
    return localStorage.getItem('identity') === "教师"
}
const isCollegeManager = () => {
    return localStorage.getItem('permission') === "院级管理员"
}
const isSchoolManager = () => {
    return localStorage.getItem('permission') === "校级管理员"
}



router.beforeResolve((to, from, next) => {
    const token = localStorage.getItem('token')
    if (token != null) {
        const jwt = jwtDecode(localStorage.getItem('token'))
        if (jwt.exp < Math.round(new Date() / 1000)) {
            const username = localStorage.getItem('workNumb')
            let phone = localStorage.getItem('phone')
            localStorage.clear()
            localStorage.setItem('workNumb', username)
            localStorage.setItem('phone', phone.substring(0, 3) + '****' + phone.substring(7))
            next('/login')
        }
    }
    if ((to.fullPath == '/' || to.fullPath.substr(0, 5) == '/back') && (localStorage.getItem('isLogin') == null || localStorage.getItem('isLogin') == 'false')) {
        next('/login')
    } else if ((to.fullPath == '/login' || to.fullPath == '/register') && localStorage.getItem('isLogin') == 'true') {
        next('/')
    } else {
        next()
    }
})
const originalPush = VueRouter.prototype.push

VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
}

export default router;