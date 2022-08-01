<template>
  <el-container>
    <el-aside width="200px" ref="aside" class="aside">
      <div class="logo" @click="gotoHome">
        <h1 style="color: #409eff">LOGO</h1>
      </div>
      <el-menu
        :default-active="pageRouter"
        class="el-menu-vertical-demo"
        @open="handleOpen"
        @close="handleClose"
        :style="{ 'min-height': containerHeight + 'px' }"
        router
      >
        <el-menu-item v-for="item in asideItems" :key="item.url" :index="'/rules'+item.url">
          <span>{{item.name}}</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-main
      class="main-container"
      :style="{ 'min-height': containerHeight + 'px' }"
      ><router-view
    /></el-main>
  </el-container>
</template>

<script>
export default {
  data() {
    return {
      asideItems: [{url:'/courseselect',name:'选课规则'},{url:'/guide',name:'学业指导计划说明'}],
      componentHeight: {
        totalHeight: 0,
        asideHeight: 0,
      },
    };
  },
  computed: {
    containerHeight() {
      return Math.max(
        this.componentHeight.totalHeight,
        this.componentHeight.asideHeight
      );
    },
    pageRouter(){
      return this.$router.currentRoute.fullPath
    }
  },
  mounted() {
    this.componentHeight.asideHeight = this.$refs.aside.$el.offsetHeight;
    var that = this;
    // 获取浏览器可视区域高度
    this.componentHeight.totalHeight = `${document.documentElement.clientHeight}`;
    window.onresize = function () {
      that.componentHeight.totalHeight = `${document.documentElement.clientHeight}`;
    };
  },
  methods: {
    handleOpen() {
      var that = this;
      setTimeout(function () {
        that.componentHeight.asideHeight = that.$refs.aside.$el.offsetHeight;
      }, 350);
    },
    handleClose() {
      var that = this;
      setTimeout(function () {
        that.componentHeight.asideHeight = that.$refs.aside.$el.offsetHeight;
      }, 350);
    },
    gotoHome() {
      this.$router.push("/");
    },
  },
};
</script>

<style scoped>
.aside {
  overflow: hidden;
}
.logo {
  user-select: none;
  margin: 0;
  text-align: center;
  border-right: 1px solid;
  border-color: #DCDFE6;
}
.logo > h1 {
  padding: 0.5em 0;
  margin: 0;
  font-size: 24px;
}
</style>
