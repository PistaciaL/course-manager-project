<template>
  <div class="container">
    <el-container>
      <el-header ref="header" class="header-container" height="40px">
        <Header></Header>
      </el-header>
      <el-container ref="body">
        <el-aside class="aside-container" width="200px">
          <Aside @changeAsideHeight="changeAsideHeight"></Aside>
        </el-aside>
        <el-main class="main-container" ref="container" :style="{ height: containerHeight + 'px' }">
          <router-view />
        </el-main>
      </el-container>
      <el-footer ref="footer" class="footer-container" height="100px">
        <Footer></Footer>
      </el-footer>
    </el-container>
  </div>
</template>

<script>
import Aside from "@/components/Aside.vue";
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";
export default {
  components: { Aside, Header, Footer },
  data() {
    return {
      componentHeight: {
        totalHeight: 0,
        headerHeight: 0,
        asideHeight: 0,
        footerHeight: 0
      },
      list: [[1, 2, 3], [4, 5, 6], [7, 8, 9]]
    };
  },
  computed: {
    containerHeight() {
      return Math.max(this.componentHeight.totalHeight - this.componentHeight.headerHeight - this.componentHeight.footerHeight, this.componentHeight.asideHeight);
    },
  },
  mounted() {
    var that = this;
    // 获取浏览器可视区域高度
    this.componentHeight.totalHeight = `${document.documentElement.clientHeight}`;
    this.componentHeight.headerHeight = this.$refs.header.height.toString().slice(0, -2);
    this.componentHeight.footerHeight = this.$refs.footer.height.toString().slice(0, -2);
    window.onresize = function () {
      that.componentHeight.totalHeight = `${document.documentElement.clientHeight}`;
    };
    // this.axios({
    //     method: 'get',
    //     url: '/users/1'
    // }).then(function(res){
    //     console.log(res)
    // })
  },
  methods: {
    changeAsideHeight(height) {
      this.componentHeight.asideHeight = height;
    },
  },
};
</script>

<style scoped>
.header-container {
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
}

.aside-container {
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
}

.main-container {
  background: #eee;
}

.footer-container {
  padding: 0;
}
</style>