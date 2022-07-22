<template>
  <div class="container">
    <el-menu :default-active="activeItem" class="login-menu" mode="horizontal">
      <el-menu-item
        index="1"
        class="login-menu-item"
        @click="changeComponent('1')"
        >手机号登录</el-menu-item
      >
      <el-menu-item
        index="2"
        class="login-menu-item"
        @click="changeComponent('2')"
        >账号密码登录</el-menu-item
      >
    </el-menu>
    <div v-show="activeItem == '1'">
      <PhoneLogin />
    </div>
    <div v-show="activeItem == '2'">
      <NameLogin />
    </div>
  </div>
</template>

<script>
import NameLogin from "@/components/login/NameLogin.vue";
import PhoneLogin from "@/components/login/PhoneLogin.vue";
export default {
  components: { NameLogin, PhoneLogin },
  data() {
    return {
      activeItem: "1",
    };
  },
  methods: {
    changeComponent(numb) {
      this.activeItem = numb;
    },
  },
};
</script>

<style scoped>
.container {
  width: 550px;
  margin: 50px auto;
}
.login-menu-item {
  width: 50%;
  text-align: center;
}
</style>