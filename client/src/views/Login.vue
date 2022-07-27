<template>
  <div class="container">
    <div class="login-header">
      <div class="logo">
        <h1 style="color:#409EFF">LOGO</h1>
      </div>
    </div>
    <div class="main">
      <div class="login-from">
        <el-menu :default-active="activeLoginItem" class="login-menu" mode="horizontal">
          <el-menu-item index="1" class="login-menu-item" @click="changeComponent('1')">手机号登录</el-menu-item>
          <el-menu-item index="2" class="login-menu-item" @click="changeComponent('2')">账号密码登录</el-menu-item>
        </el-menu>
        <div v-show="activeLoginItem == '1'">
          <PhoneLogin />
        </div>
        <div v-show="activeLoginItem == '2'">
          <NameLogin />
        </div>
      </div>
      <div class="block">
        <el-carousel height="640px" trigger="click" arrow="never" indicator-position="none" :interval="10000">
          <el-carousel-item v-for="item in 4" :key="item">
            <h3 class="small">{{ item }}</h3>
          </el-carousel-item>
        </el-carousel>
      </div>
      
    </div>
    <div class="login-footer">footer</div>
  </div>
</template>

<script>
import NameLogin from "@/components/login/NameLogin.vue";
import PhoneLogin from "@/components/login/PhoneLogin.vue";

export default {
  components: { NameLogin, PhoneLogin },
  data() {
    return {
      activeLoginItem: "1",
    };
  },
  methods: {
    changeComponent(numb) {
      this.activeLoginItem = numb;
    },
  },
};
</script>

<style scoped>
.login-menu{
  border-radius: 10px 10px 0 0;
}
.login-menu-item {
  width: 50%;
  text-align: center;
  border-radius: 10px 10px 0 0;
}

.container {
  min-width: 1200px;
}

.block {
  background: #eee;
}

.login-header {
  height: 60px;
  display: flex;
  padding: 10px 20px 30px;
}

.login-footer {
  height: 200px;
  background: #f4f4f4;
}

.login-from {
  float: right;
  background: #fff;
  margin-top: 115px;
  margin-right: 100px;
  width: 380px;
  height: 400px;
  border-radius: 10px;
}
</style>