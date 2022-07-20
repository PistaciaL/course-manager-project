import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        userInfo: {
            isLogin: true,
            userName: '张三',
            avatarUrl: 'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201710%2F17%2F20171017215814_fwPMQ.jpeg&refer=http%3A%2F%2Fb-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1660373349&t=8bea948d2fd988a86470d9c5e8441a7b'
        }
    },
    getters: {},
    mutations: {},
    actions: {},
    modules: {}
})