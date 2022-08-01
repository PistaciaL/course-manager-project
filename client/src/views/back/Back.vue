<template>
  <div class="back-container">
    <el-container>
      <el-header ref="header" class="header-container" height="40px">
        <Header></Header>
        <el-divider></el-divider>
      </el-header>
      <el-container ref="body">
        <el-aside class="aside-container" width="200px">
          <Aside @changeAsideHeight="changeAsideHeight" :containerHeight="containerHeight"></Aside>
        </el-aside>
        <el-main class="main-container" ref="container" :style="{ 'min-height': containerHeight + 'px' }">
          <router-view ref="mainContainer" @changeHeight='childChangeHeight'/>
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
      addHeight:1,
      componentHeight: {
        totalHeight: 0,
        headerHeight: 0,
        asideHeight: 0,
        footerHeight: 0
      },
    };
  },
  computed: {
    containerHeight() {
      return this.addHeight*Math.max(this.componentHeight.totalHeight - this.componentHeight.headerHeight - this.componentHeight.footerHeight, this.componentHeight.asideHeight);
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
    childChangeHeight(e){
      this.addHeight=e
    },
    changeAsideHeight(height) {
      this.componentHeight.asideHeight = height;
    },
  },
};
</script>

<style scoped>
.back-container {
  min-width: 1200px;
}
.header-container {
  padding: 0;
}
.main-container{
  border-left: 1px solid #DCDFE6;
  overflow: hidden;
}

.el-divider{
  margin: 0;
}

.footer-container {
  padding: 0;
}
</style>