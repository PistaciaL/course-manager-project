<template>
  <div class="container">
    <el-container>
      <el-header ref="header" class="header-container" height="40px">
        <Header></Header>
        <el-divider></el-divider>
      </el-header>
      <el-main class="main-container" ref="container" :style="{ 'min-height': containerHeight + 'px' }">
        <HomeIndex></HomeIndex>
      </el-main>
      <el-footer ref="footer" class="footer-container" height="100px">
        <Footer></Footer>
      </el-footer>
    </el-container>
  </div>
</template>

<script>
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";
import HomeIndex from '@/components/HomeIndex.vue'
export default {
  components: { Header, Footer, HomeIndex },
  data() {
    return {
      componentHeight: {
        totalHeight: 0,
        headerHeight: 0,
        footerHeight: 0
      },
    }
  },
  computed: {
    containerHeight() {
      return this.componentHeight.totalHeight - this.componentHeight.headerHeight - this.componentHeight.footerHeight
    },
  },
  mounted() {
    var that = this;
    // 获取浏览器可视区域高度
    this.componentHeight.headerHeight = this.$refs.header.height.toString().slice(0, -2);
    this.componentHeight.footerHeight = this.$refs.footer.height.toString().slice(0, -2);
    this.componentHeight.totalHeight = `${document.documentElement.clientHeight}`;
    window.onresize = function () {
      that.componentHeight.totalHeight = `${document.documentElement.clientHeight}`;
    };
  },
}
</script>

<style scoped>
.container{
  min-width: 1200px;
}
.header-container {
  padding: 0;
}
.main-container{
  padding: 0;
}
.footer-container {
  padding: 0;
}
.el-divider{
  margin: 0;
}
</style>